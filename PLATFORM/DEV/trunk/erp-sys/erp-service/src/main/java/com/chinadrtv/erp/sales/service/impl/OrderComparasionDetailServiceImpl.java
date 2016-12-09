/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.sales.service.impl;

import com.chinadrtv.erp.core.service.NamesService;
import com.chinadrtv.erp.marketing.core.common.AuditTaskStatus;
import com.chinadrtv.erp.marketing.core.common.UserBpmTaskType;
import com.chinadrtv.erp.marketing.core.exception.MarketingException;
import com.chinadrtv.erp.marketing.core.service.UserBpmTaskService;
import com.chinadrtv.erp.model.*;
import com.chinadrtv.erp.model.agent.Card;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.model.inventory.ProductType;
import com.chinadrtv.erp.model.marketing.UserBpmTask;
import com.chinadrtv.erp.sales.core.service.OrderChangeService;
import com.chinadrtv.erp.sales.core.service.OrderhistService;
import com.chinadrtv.erp.sales.core.util.FieldValueCompareUtil;
import com.chinadrtv.erp.sales.core.util.OrderUtil;
import com.chinadrtv.erp.sales.dto.OrderChangeDto;
import com.chinadrtv.erp.sales.dto.OrderDetailDto;
import com.chinadrtv.erp.sales.dto.OrderhistExtDto;
import com.chinadrtv.erp.sales.service.OrderComparasionDetailService;
import com.chinadrtv.erp.sales.service.OrderInfoService;
import com.chinadrtv.erp.tc.core.dao.WarehouseDao;
import com.chinadrtv.erp.tc.core.service.AddressExtService;
import com.chinadrtv.erp.tc.core.service.CompanyService;
import com.chinadrtv.erp.uc.constants.CustomerConstant;
import com.chinadrtv.erp.uc.service.CardService;
import com.chinadrtv.erp.uc.service.CardtypeService;
import com.chinadrtv.erp.uc.service.ContactService;
import com.chinadrtv.erp.uc.util.ShoppingCartProductValidate;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.*;

/**
 * 2013-6-24 上午10:24:08
 * @version 1.0.0
 * @author yangfei
 *
 */
@Service("orderComparasionDetailService")
public class OrderComparasionDetailServiceImpl implements OrderComparasionDetailService{
	private static final Logger logger = LoggerFactory.getLogger(OrderComparasionDetailServiceImpl.class);
	
	@Autowired
    private UserBpmTaskService userBpmTaskService;

    @Autowired
    private OrderChangeService orderChangeService;
    
    @Autowired
    private OrderhistService orderhistService;
    
    @Autowired
    private NamesService namesService;
    
    @Autowired
    private ContactService contactService;

    @Autowired
    @Qualifier("com.chinadrtv.erp.tc.core.service.impl.AddressExtServiceImpl")
    private AddressExtService addressExtService;
    
    @Autowired
    private CompanyService companyService;
    @Autowired
    @Qualifier("tcWarehouseDao")
    private WarehouseDao warehouseDao;

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private CardService cardService;

    @Autowired
    private CardtypeService cardtypeService;
    
    @Autowired
    private ShoppingCartProductValidate shoppingCartProductValidate;

    public void instancializeOrderChange(ModelAndView mav, String batchId, boolean isAdd) throws MarketingException {
        List<UserBpmTask> tasks = null;
		//list = userBpmTaskService.getChangeObjIdByBatchIdOrInstanceId(batchId,null);
        tasks = userBpmTaskService.queryApprovingTaskByBatchID(batchId);
        Map<String, UserBpmTask> instMap = new TreeMap<String, UserBpmTask>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
        List<UserBpmTask> contactInsts = new ArrayList<UserBpmTask>();
        
        boolean isAllApproved = true;
        String status = String.valueOf(AuditTaskStatus.UNASSIGNED.getIndex());
        String contactComment = null;
        boolean isContactChanged = false;
        boolean isContactChangedAndRejected = false;
        StringBuilder instBuilder = new StringBuilder();
        for(UserBpmTask ubt : tasks) {
            if(!instMap.containsKey(ubt.getBusiType()) ||
                StringUtils.isNotBlank(ubt.getParentInsId())) {
                instMap.put(ubt.getBusiType(), ubt);
            }
        	if(String.valueOf(AuditTaskStatus.UNASSIGNED.getIndex()).equals(ubt.getStatus())) {
        		isAllApproved = false;
        	}
        	if(String.valueOf(UserBpmTaskType.CONTACT_ADDRESS_CHANGE.getIndex()).equals(ubt.getBusiType())
        			|| String.valueOf(UserBpmTaskType.CONTACT_BASE_CHANGE.getIndex()).equals(ubt.getBusiType())
        			|| String.valueOf(UserBpmTaskType.CONTACT_PHONE_CHANGE.getIndex()).equals(ubt.getBusiType()) 
        			|| String.valueOf(UserBpmTaskType.ORDER_RECEIVER_CHANGE.getIndex()).equals(ubt.getBusiType())) {
        		status = ubt.getStatus();
        		contactComment = ubt.getApproverRemark();
        		if(String.valueOf(AuditTaskStatus.UNASSIGNED.getIndex()).equals(status)) {
        			contactInsts.add(ubt);
        		}
            	isContactChanged = true;
            	if(String.valueOf(AuditTaskStatus.REJECTED.getIndex()).equals(status)) {
                	isContactChangedAndRejected = true;
            	}
        	}
        }
		Collections.sort(contactInsts, new Comparator<UserBpmTask>() {
			@Override
			public int compare(UserBpmTask o1,
					UserBpmTask o2) {
				int result = -1;
				if(StringUtils.isNotBlank(o1.getBusiType()) && StringUtils.isNotBlank(o2.getBusiType())) {
					result = o1.getBusiType().compareTo(o2.getBusiType());
				}
				return result;
			}
		});
		for(UserBpmTask userBpmTask : contactInsts) {
			instBuilder.append("_").append(userBpmTask.getBpmInstID());
		}
        mav.addObject("instMap", instMap);
        
        //初始化审批提示
        StringBuffer tipBuffer = null;
        if(isAdd) {
        	tipBuffer = new StringBuffer();
	        tipBuffer.append("<ul>");
	        if(instMap.get("10") != null) {
	        	tipBuffer.append("<li>订购人黑名单</li>");
	        }
	        if(instMap.get("7") != null) {
	        	tipBuffer.append("<li>运费修改</li>");
	        }
	        tipBuffer.append("</ul>");
        }
        mav.addObject("isAllApproved", isAllApproved);
        
    	List<Object> list=userBpmTaskService.getChangeObjIdByBatchIdOrInstanceId(batchId,null);
        List<Long> orderChangeIdList=new ArrayList<Long>();

        if(list!=null&&list.size()>0)
        {
            for(Object obj:list)
            {
                if(obj!=null)
                {
                    Long id=Long.parseLong(obj.toString());
                    if(!orderChangeIdList.contains(id))
                    {
                       orderChangeIdList.add(id);
                    }
                }
            }
        }

        if(orderChangeIdList.size()<=0)
        {
            return;
        }

        OrderChange orderChange=orderChangeService.mergeBatchOrderChanges(orderChangeIdList);

        Order order = orderhistService.getOrderhist(orderChange.getId());

        if(orderChange.getProdprice()==null)
        {
            orderChange.setProdprice(order.getProdprice());
        }
        if(orderChange.getMailprice()==null)
        {
            orderChange.setMailprice(order.getMailprice());
        }
        if(orderChange.getJifen()==null)
        {
            orderChange.setJifen(order.getJifen());
        }
        //校正订单总金额if(orderChange.getTotalprice()==null)
        {
            Integer jifen=0;
            if(StringUtils.isNotEmpty(orderChange.getJifen()))
            {
                jifen=Integer.parseInt(orderChange.getJifen());
            }
            orderChange.setTotalprice(orderChange.getProdprice().add(orderChange.getMailprice()).subtract(new BigDecimal(jifen)));
        }


        List<OrderDetailDto> orderDetailDtoList=null;
        // 订单明细
        if (orderChange.getOrderdetChanges()!=null&&orderChange.getOrderdetChanges().size()>0) {
            orderDetailDtoList=compareDetails(order.getOrderdets(), orderChange.getOrderdetChanges());
        	mav.addObject("changeMap", orderDetailDtoList);
        }

        //preAddress
        // 收货人地址

        if (orderChange.getAddress() != null) {
            AddressExt addressExt=addressExtService.getAddressExt(orderChange.getAddress().getAddressId());
            AddressExtChange addressExtChange=new AddressExtChange();
            BeanUtils.copyProperties(addressExt,addressExtChange);
            FieldValueCompareUtil.copyNotNullFld(orderChange.getAddress(),addressExtChange);
        	mav.addObject("addressChange", addressExtChange);
        }
        //获取订单类型
        List<Names> ordertypeList=namesService.getAllNames("ORDERTYPE");

        OrderhistExtDto orderExtDto=createOrderExtDtoFromOrder(order);
        if(isAdd) {
	        //标识0元搭销产品
	        Map<String, Boolean> productValidationMap = new HashMap<String, Boolean>();
	        for(OrderDetail orderDetail : orderExtDto.getOrderdets()) {
	        	boolean isReviewNeeded = false;;
				try {
					if(OrderUtil.getProdPrice(orderDetail).intValue() == 0) {
						isReviewNeeded = shoppingCartProductValidate.specialPriceValidate(orderDetail, order.getCrusr());
						productValidationMap.put(orderDetail.getProdid(), isReviewNeeded);
						if(isReviewNeeded) {
							tipBuffer.append("<li>产品").append(orderDetail.getProdid()).append("价格不合规</li>");
						}
					}
				} catch (Exception e) {
					logger.error("获取有效性失败", e);
				}
	        }
	        mav.addObject("productValidationMap", productValidationMap);
	        tipBuffer.append("</ul>");
	        mav.addObject("auditTip", tipBuffer.toString());
        }
        //orderExtDto.setOrderdets(null);
        for(Names names:ordertypeList)
        {
             if(names.getId().getId().equals(orderExtDto.getOrdertype()))
             {
                 orderExtDto.setOrdertypeName(names.getDsc());
                 break;
             }
        }

        OrderChangeDto orderChangeDto=new OrderChangeDto();
        BeanUtils.copyProperties(orderChange,orderChangeDto);
        if(StringUtils.isEmpty(orderChangeDto.getOrdertype()))
        {
            orderChangeDto.setOrdertypeName(orderExtDto.getOrdertypeName());
        }
        else
        {
            for(Names names:ordertypeList)
            {
                if(names.getId().getId().equals(orderChangeDto.getOrdertype()))
                {
                    orderChangeDto.setOrdertypeName(names.getDsc());
                    break;
                }
            }
        }

        this.fetchEntityInfo(orderExtDto);
        if(StringUtils.isEmpty(orderExtDto.getReqEMS()))
        {
            orderExtDto.setReqEMS("N");
        }
        if(StringUtils.isEmpty(orderChangeDto.getIsreqems()))
        {
            orderChangeDto.setIsreqems(orderExtDto.getReqEMS());
        }
        if(StringUtils.isEmpty(orderChangeDto.getBill()))
        {
            orderChangeDto.setBill(orderExtDto.getBill());
        }
        mav.addObject("order", orderExtDto);
        mav.addObject("orderChange", orderChangeDto);
        if(orderChangeDto.getProdprice()!=null)
        {
            orderChangeDto.setJifen(this.getJifenFromPrice(orderChangeDto.getProdprice()));
        }
        else
        {
            orderChangeDto.setJifen(orderExtDto.getJifen());
        }

        List<PhoneChange> phoneChangeList=new ArrayList<PhoneChange>();
        if(orderChange.getGetContactChange()!=null)
        {
            if(orderChange.getGetContactChange().getPhoneChanges()!=null)
            {
                for(PhoneChange phoneChange:orderChange.getGetContactChange().getPhoneChanges())
                {
                    phoneChangeList.add(phoneChange);
                }
            }
        }

        //处理信用卡
        if(orderChangeDto.getCardChanges()!=null&&orderChangeDto.getCardChanges().size()>0
                ||StringUtils.isNotEmpty(orderChangeDto.getLaststatus()))
        {
            //现在只处理信用卡，不处理证件
            //Card card2=new Card();
            CardChange cardChange2=null;
            for(CardChange cardChange:orderChangeDto.getCardChanges())
            {
                CardChange cardChange1=null;
                if(cardChange.getCardId()!=null)
                {
                    Card card=cardService.getCard(cardChange.getCardId());
                    //合并改变的数据
                    cardChange1=new CardChange();
                    BeanUtils.copyProperties(card,cardChange1);
                    FieldValueCompareUtil.copyNotNullFld(cardChange,cardChange1);
                }
                else
                {
                    cardChange1=new CardChange();
                    BeanUtils.copyProperties(cardChange,cardChange1);
                }
                Cardtype cardtype=cardtypeService.getCardType(cardChange1.getType());
                if("2".equals(cardtype.getType1())&&cardChange1!=null)
                {
                    cardChange2=cardChange1;
                    cardChange2.setType(cardtype.getCardname());
                    break;
                }
            }
            Card card1= null;
            if(StringUtils.isNotEmpty(orderExtDto.getCardnumber()))
            {
                card1=new Card();
                Card card=cardService.getCard(Long.parseLong(orderExtDto.getCardnumber()));
                BeanUtils.copyProperties(card,card1);
            }

            if(card1!=null)
            {
                //名称、类型名称
                Contact contact= contactService.get(card1.getContactId());
                card1.setContactId(contact.getName());
                Cardtype cardtype= cardtypeService.getCardType(card1.getType());
                card1.setType(cardtype.getCardname());
            }
            if(cardChange2!=null)
            {
                Contact contact= contactService.get(cardChange2.getContactId());
                cardChange2.setContactId(contact.getName());
                //Cardtype cardtype= cardtypeService.getCardType(cardChange2.getType());
                //cardChange2.setType(cardtype.getCardname());
            }
            else
            {
                //从源信用卡获取
                if(card1!=null)
                {
                    cardChange2=new CardChange();
                    BeanUtils.copyProperties(card1,cardChange2);
                }
            }
            if(this.isInAuditFlag(card1.getState()))
            {
                Card card=new Card();
                card.setCardId(card1.getCardId());
                mav.addObject("card", card1);
            }
            else
            {
                mav.addObject("card", card1);
            }
            mav.addObject("cardChange", cardChange2);
        }

        //订购人
        Contact subscriber=contactService.get(order.getContactid());
        mav.addObject("subscriber",subscriber);
        //最后联系人
        Contact contactOrder=contactService.get(order.getGetcontactid());
        if(this.isInAuditFlag(contactOrder.getState()))
        {
            Contact contactPlaceholder=new Contact();
            contactPlaceholder.setContactid(contactOrder.getContactid());
            mav.addObject("contact",contactPlaceholder);
        }
        else
        {
            mav.addObject("contact",contactOrder);
        }
        if(orderChange.getGetContactChange()!=null)
        {
            ContactChange contactChange=new ContactChange();
            BeanUtils.copyProperties(orderChange.getGetContactChange(),contactChange);
            if(StringUtils.isEmpty(contactChange.getName()))
            {
                if(contactOrder.getContactid().equals(contactChange.getContactid()))
                {
                    contactChange.setName(contactOrder.getName());
                }
                else
                {
                    Contact contact1=contactService.get(contactChange.getContactid());
                    contactChange.setName(contact1.getName());
                }
            }
            mav.addObject("contactChange", contactChange);
        }

        /* 测试用数据
        if(phoneChangeList.size()==0)
        {
            PhoneChange phoneChange=new PhoneChange();
            phoneChange.setPhoneNum("123");
            phoneChangeList.add(phoneChange);
        }*/
        mav.addObject("phoneChanges",phoneChangeList);
        mav.addObject("contactInsts",instBuilder.toString());
        mav.addObject("isContactChanged",isContactChanged);
        mav.addObject("isContactChangedAndRejected",isContactChangedAndRejected);
        mav.addObject("contactStatus",status);
        mav.addObject("contactComment",contactComment);
        
        fetchProducttypenames(orderExtDto,orderDetailDtoList);

        //最后处理待审批的关联对象（目前做法都用空的对象替代）
        /*if(this.isInAuditFlag(orderExtDto.getAddress().getAuditState()))
        {
            AddressExt addressExt=new AddressExt();
            addressExt.setAddressId(orderExtDto.getAddress().getAddressId());
            orderExtDto.setAddress(addressExt);
        }*/
    }

    private String getJifenFromPrice(BigDecimal price)
    {
        //
        return price.divide(new BigDecimal("100"),2,BigDecimal.ROUND_HALF_UP).toString();
    }


    private List<OrderDetailDto> compareDetails(Set<OrderDetail> orderdets, Set<OrderdetChange> orderdetChanges) {
        List<OrderDetailDto> result = new ArrayList<OrderDetailDto>();
        outter: for (OrderDetail detail : orderdets) {
            for (OrderdetChange detailChange : orderdetChanges) {
                if (detail.getId().equals(detailChange.getId())) { // 明细id相同，说明是修改明细单
                    OrderDetailDto orderDetailDto=new OrderDetailDto();
                    BeanUtils.copyProperties(detailChange,orderDetailDto);
                    orderDetailDto.setModifyFlag("0");

                    result.add(orderDetailDto);
                    continue outter;
                }
            }
            // 没找到相同的明细单，说明被删除
            OrderDetailDto dto = new OrderDetailDto();
            BeanUtils.copyProperties(detail,dto);
            dto.setModifyFlag("-1");
            result.add(dto);
        }

        for (OrderdetChange detailChange : orderdetChanges) {
            if (detailChange.getId() == null) { // 没有明细id，说明是新增明细
                OrderDetailDto dto=new OrderDetailDto();
                BeanUtils.copyProperties(detailChange,dto);
                dto.setModifyFlag("1");
                result.add(dto);
            }
        }
        return result;
    }
    
    private void fetchEntityInfo(OrderhistExtDto orderhistExtDto)
    {
       if(StringUtils.isNotEmpty(orderhistExtDto.getEntityid()))
       {
           Company company= companyService.findCompany(orderhistExtDto.getEntityid());
           if(company!=null)
           {
               orderhistExtDto.setEntityName(company.getName());
               if(company.getWarehouseId()!=null)
               {
                   Warehouse warehouse= warehouseDao.get(company.getWarehouseId());
                   if(warehouse!=null)
                       orderhistExtDto.setWarehouseName(warehouse.getWarehouseName());
               }
           }
       }
    }

    private OrderhistExtDto createOrderExtDtoFromOrder(Order order)
    {
        OrderhistExtDto orderhistExtDto=new OrderhistExtDto();
        BeanUtils.copyProperties(order,orderhistExtDto);
        orderhistExtDto.setOrderdets(new HashSet<OrderDetail>());
        for(OrderDetail orderDetail:order.getOrderdets())
        {
            OrderDetail orderDetailNew=new OrderDetail();
            BeanUtils.copyProperties(orderDetail,orderDetailNew);

            orderhistExtDto.getOrderdets().add(orderDetailNew);

        }

        orderhistExtDto.setJifen(this.getJifenFromPrice(orderhistExtDto.getProdprice()));
        return orderhistExtDto;
    }

    private void fetchProducttypenames(OrderhistExtDto orderhistExtDto,List<OrderDetailDto> orderDetailDtoList)
    {
        List<OrderDetail> orderDetailList=new ArrayList<OrderDetail>();
        if(orderhistExtDto.getOrderdets()!=null&&orderhistExtDto.getOrderdets().size()>0)
        {
            for(OrderDetail orderDetail:orderhistExtDto.getOrderdets())
            {
                boolean bFind=false;
                for(OrderDetail orderDetail1:orderDetailList)
                {
                    if(orderDetail.getProdid().equals(orderDetail1.getProdid())
                      &&orderDetail.getProducttype().equals(orderDetail1.getProducttype()))
                    {
                        bFind=true;
                        break;
                    }
                }
                if(bFind==false)
                {
                    orderDetailList.add(orderDetail);
                }
            }
        }

        if(orderDetailDtoList!=null&&orderDetailDtoList.size()>0)
        {
            for(OrderDetailDto orderDetailDto:orderDetailDtoList)
            {
                boolean bFind=false;
                for(OrderDetail orderDetail1:orderDetailList)
                {
                    if(orderDetailDto.getProdid().equals(orderDetail1.getProdid())
                            &&orderDetailDto.getProducttype().equals(orderDetail1.getProducttype()))
                    {
                        bFind=true;
                        break;
                    }
                }
                if(bFind==false)
                {
                    orderDetailList.add(orderDetailDto);
                }
            }
        }

        List<ProductType> productTypeList=orderInfoService.getProductTypes(orderDetailList);
        if(productTypeList!=null)
        {
            for(OrderDetail orderDetail:orderhistExtDto.getOrderdets())
            {
                boolean bFind=false;
                for(ProductType productType:productTypeList)
                {
                    if(orderDetail.getProdid().equals(productType.getProdrecid())
                        &&orderDetail.getProducttype().equals(productType.getRecid()))
                    {
                        orderDetail.setProducttype(productType.getDsc());
                        bFind=true;
                        break;
                    }
                }
                if(bFind==false)
                {
                    orderDetail.setProducttype(null);
                }
            }

            if(orderDetailDtoList!=null)
            {
                for(OrderDetail orderDetail:orderDetailDtoList)
                {
                    boolean bFind=false;
                    for(ProductType productType:productTypeList)
                    {
                        if(orderDetail.getProdid().equals(productType.getProdrecid())
                                &&orderDetail.getProducttype().equals(productType.getRecid()))
                        {
                            orderDetail.setProducttype(productType.getDsc());
                            bFind=true;
                            break;
                        }
                    }
                    if(bFind==false)
                    {
                        orderDetail.setProducttype(null);
                    }
                }
            }
        }
    }

    private boolean isInAuditFlag(Integer state)
    {
        if(state!=null&&state.intValue()== CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITING)
            return true;
        return false;
    }
    
    public boolean isOrderExist(String orderId) {
    	boolean isExist = true;
    	Order order = orderhistService.getOrderHistByOrderid(orderId);
    	if(order == null) {
    		isExist = false;
    	}
    	return isExist;
    }
}
