package com.chinadrtv.erp.sales.core.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

import javax.naming.NamingException;
import javax.validation.ConstraintViolationException;

import com.chinadrtv.erp.core.dao.query.*;
import com.chinadrtv.erp.core.service.OrderRuleCheckService;
import com.chinadrtv.erp.exception.service.ServiceTransactionException;
import com.chinadrtv.erp.model.*;
import com.chinadrtv.erp.model.agent.*;
import com.chinadrtv.erp.model.inventory.PlubasInfo;
import com.chinadrtv.erp.sales.core.dao.*;
import com.chinadrtv.erp.sales.core.model.OrderCreateResult;
import com.chinadrtv.erp.sales.core.model.OrderModifyCorrelation;
import com.chinadrtv.erp.sales.core.service.*;
import com.chinadrtv.erp.tc.core.dao.*;
import com.chinadrtv.erp.sales.core.service.InventoryAgentService;
import com.chinadrtv.erp.tc.core.dao.CardtypeDao;
import com.chinadrtv.erp.uc.constants.CustomerConstant;
import com.chinadrtv.erp.uc.service.*;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.exception.BizException;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.exception.ErpException;
import com.chinadrtv.erp.exception.ExceptionConstant;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.exception.service.ServiceParameterException;
import com.chinadrtv.erp.marketing.core.common.AuditTaskStatus;
import com.chinadrtv.erp.marketing.core.common.AuditTaskType;
import com.chinadrtv.erp.marketing.core.common.UserBpmTaskType;
import com.chinadrtv.erp.marketing.core.service.ChangeRequestService;
import com.chinadrtv.erp.marketing.core.service.UserBpmTaskService;
import com.chinadrtv.erp.model.cntrpbank.OrderUrgentApplication;
import com.chinadrtv.erp.model.marketing.UserBpm;
import com.chinadrtv.erp.model.marketing.UserBpmTask;
import com.chinadrtv.erp.model.trade.ShipmentChangeHis;
import com.chinadrtv.erp.model.trade.ShipmentHeader;
import com.chinadrtv.erp.sales.core.constant.LogisticsStatusEnums;
import com.chinadrtv.erp.sales.core.constant.OrderRight;
import com.chinadrtv.erp.sales.core.model.OrderExtDto;
import com.chinadrtv.erp.sales.core.util.DateUtil;
import com.chinadrtv.erp.sales.core.util.ExceptionMsgUtil;
import com.chinadrtv.erp.sales.core.util.FieldValueCompareUtil;
import com.chinadrtv.erp.tc.core.constant.AccountStatusConstants;
import com.chinadrtv.erp.tc.core.constant.OrderAssignConstants;
import com.chinadrtv.erp.tc.core.constant.OrderCode;
import com.chinadrtv.erp.tc.core.constant.SchemaNames;
import com.chinadrtv.erp.tc.core.dto.OrderAuditExtDto;
import com.chinadrtv.erp.tc.core.dto.OrderAuditQueryDto;
import com.chinadrtv.erp.tc.core.dto.OrderQueryDto;
import com.chinadrtv.erp.tc.core.model.OrderAutoChooseInfo;
import com.chinadrtv.erp.tc.core.model.OrderReturnCode;
import com.chinadrtv.erp.tc.core.model.PreTradeRest;
import com.chinadrtv.erp.tc.core.service.AddressExtService;
import com.chinadrtv.erp.tc.core.service.CompanyService;
import com.chinadrtv.erp.tc.core.service.DisHuifangMesService;
import com.chinadrtv.erp.tc.core.service.OrderHistoryService;
import com.chinadrtv.erp.tc.core.service.OrderSkuSplitService;
import com.chinadrtv.erp.tc.core.service.PvService;
import com.chinadrtv.erp.tc.core.utils.OrderException;
import com.chinadrtv.erp.tc.core.utils.OrderdetUtil;
import com.chinadrtv.erp.tc.core.utils.OrderhistUtil;
import com.chinadrtv.erp.uc.dao.AddressExtChangeDao;
import com.chinadrtv.erp.uc.dao.ContactChangeDao;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;

/**
 * 订单服务
 * Date: 13-1-24
 */
@Service
public class OrderhistServiceImpl extends GenericServiceImpl<Order, Long> implements OrderhistService {

    @Autowired
    private InventoryAgentService inventoryAgentService;

    @Value("${preTadeGrpId}")
    private String preTradeGrpId;

    private List<String> creditCardOrderTypeList;

    @Value("${preTradeCreditCardOrderTypes}")
    public void initCreditCardOrderTypes(String creditCardOrderTypes)
    {
        creditCardOrderTypeList=new ArrayList<String>();
        if(StringUtils.isNotEmpty(creditCardOrderTypes))
        {
            String[] tokens = creditCardOrderTypes.split(",");
            for(String str:tokens)
            {
                if(!creditCardOrderTypeList.contains(str))
                {
                    creditCardOrderTypeList.add(str);
                }
            }
        }
    }


    private String emsCompanyId;

    private List<String> cardTypeCheckList;

    @Value("${cardTypeCheck}")
    public void setCardTypeCheck(String cardTypeChecks)
    {
        cardTypeCheckList=new ArrayList<String>();
        if(StringUtils.isNotEmpty(cardTypeChecks))
        {
            String[] strs=StringUtils.split(cardTypeChecks,",");
            for(String str:strs)
            {
                if(StringUtils.isNotEmpty(str))
                {
                    if(!cardTypeCheckList.contains(str))
                    {
                        cardTypeCheckList.add(str);
                    }
                }
            }
        }
    }

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderhistServiceImpl.class);

    public OrderhistServiceImpl()
    {
        synchronized (OrderhistServiceImpl.class)
        {
            orderhistUtil.init();
            orderdetUtil.init();
        }
        logger.debug("orderhist service is created");
    }

    private OrderhistUtil orderhistUtil=new OrderhistUtil();
    private OrderdetUtil orderdetUtil=new OrderdetUtil();

    @Autowired
    private PhoneDao phoneDao;
    @Autowired
    private ContactDao contactDao;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private AddressExtService addressExtService;
    @Autowired
    private OrderdetDao orderdetDao;
    //Agent 历史Dao
    @Autowired
    private OrderhistHistDao orderhistHistDao;
    @Autowired
    private OrderdetHistDao orderdetHistDao;
    @Autowired
    private OrderHistoryService orderHistoryService;
    @Autowired
    private ShipmentHeaderService shipmentHeaderService;
    @Autowired
    private PvService pvService;
    @Autowired
    private DisHuifangMesService disHuifangMesService;
    @Autowired
    private OrderhistDao orderhistDao;
    @Autowired
    private OrderSkuSplitService orderSkuSplitService;
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private EmsDao emsDao;
    @Autowired
    private ProductDao productDao;

    @Autowired
    private OrderChangeDao orderChangeDao;

    @Autowired
    private ShipmentHeaderDao shipmentHeaderDao;

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private SchemaNames schemaNames;
    @Autowired
    private SqlDao sqlDao;

	@Override
	protected GenericDao<Order, Long> getGenericDao() {
		return orderhistDao;
	}

    /**
     * 根据前置订单信息来创建新的订单
     * @param preTrade
     * @throws Exception
     */
    public String addOrderhist(PreTradeRest preTrade) throws Exception {

        //1、判断客户、地址、电话信息，并保存客户信息。(JDBC)

        //2、拆分产品套装：产生单品（12位SKU码）的产品明细。

        //3、调用承运商规则：分配送货公司，仓库。
        //（前提条件：承运商规则服务功能需正式提供服务。）

        //4、调用库存服务。
        //库存服务功能：判断库存是否满足，如果满足则更新库存数量，不满足则返回错误。
        //（前提条件：库存服务功能需正式提供服务。）

        //5、保存订单信息。

        //6、调用积分、券计算规则服务。

        //7、调用结算规则服务。
        //（前提条件：结算规则服务功能需正式提供服务。）

        //8、生成订单快照历史。

        //9、生成发运单。

        //10、根据订单类型，判断是否发送短信通知（异步）。

        //11、生成配送验证码。

        //12、调用其他扩展信息（异步）。


        //首先保存客户相关的信息
        Order orderhist = null;
        try {
            orderhist = convertPreTradToOrderhist(preTrade);
        } catch (NullPointerException nullExp) {
            logger.debug("**********invalid field start**********");
            logger.error("pretrade import error:", nullExp);
            logger.debug("**********invalid field end**********");

            OrderReturnCode returnCode = new OrderReturnCode();
            returnCode.setCode(OrderCode.FIELD_INVALID);
            returnCode.setDesc("前置订单信息不合法");
            throw new OrderException(returnCode);
        }
        int warehouseId = 0;
        if (orderhist.getEntityid() != null) {
            //检查运输公司是否存在，如果不存在，那么不设置，后续走分拣流程
            Company company = companyService.findCompany(orderhist.getEntityid());
            if (company == null) {
                orderhist.setEntityid(null);
                orderhist.setCompanyid(null);
                //OrderReturnCode returnCode = new OrderReturnCode();
                //returnCode.setCode(OrderCode.FIELD_INVALID);
                //returnCode.setDesc("前置订单设置的承运商不存在");
                //throw new OrderException(returnCode);
            }
            else if (company.getWarehouseId() == null) {
                OrderReturnCode returnCode = new OrderReturnCode();
                returnCode.setCode(OrderCode.FIELD_INVALID);
                returnCode.setDesc("前置订单设置的承运商没有对应的仓库");
                throw new OrderException(returnCode);
            }
            else
            {
                orderhist.setMailtype(company.getMailtype());
                warehouseId = company.getWarehouseId().intValue();
                orderhist.setIsassign(OrderAssignConstants.HAND_ASSIGN);
            }
        }

        //然后调用订单保存
        Order orgOderhist = addOrUpdateOrderhist(orderhist, warehouseId);
        //产生发运单
        /*if (warehouseId > 0) {
            shipmentHeaderService.generateShipmentHeader(orgOderhist, orderhist.getEntityid(), String.valueOf(warehouseId));
        } else {
            shipmentHeaderService.generateShipmentHeader(orgOderhist, false);
        }*/

        //TODO:最后执行后面的任务(根据订单类型，判断是否发送短信通知、生成配送验证码)
        return orderhist.getOrderid();
    }

    private class AddressInfo
    {
        public AddressInfo(AddressExt addressExt, Ems ems)
        {
            this.addressExt=addressExt;
            this.ems=ems;
        }
        public AddressExt addressExt;
        public Ems ems;
    }
    private AddressInfo findOrCreateAddressExtFromPreTrade(PreTrade preTrade, String contactId) {
        //首先根据地址信息来查找，如果找到，就不创建
        //如果没有，那么创建（注意省、城市必须存在，区和县街道可以没有）
        AddressExt addressExt = this.addressExtService.findMatchAddressFromNames(preTrade.getReceiverProvince(), preTrade.getReceiverCity(), preTrade.getReceiverCounty(), preTrade.getReceiverArea(), preTrade.getReceiverAddress(), contactId);
        if (addressExt == null) {
            return null;
        } else {
            //检查地址是否一致
            if (!preTrade.getReceiverAddress().equals(addressExt.getAddressDesc())) {
                //创建新的地址
                AddressExt addressExt1 = new AddressExt();
                addressExt1.setProvince(addressExt.getProvince());
                addressExt1.setCity(addressExt.getCity());
                addressExt1.setCounty(addressExt.getCounty());
                addressExt1.setArea(addressExt.getArea());
                addressExt1.setAddressDesc(preTrade.getReceiverAddress());
                addressExt1.setContactId(contactId);

                Integer spellId=this.getSpellIdFromAddressExt(addressExt1);
                Ems ems=null;
                if(spellId!=null)
                {
                    ems = emsDao.getEms(spellId);
                }
                this.addressExtService.createAddressExt(addressExt1, ems);
                return new AddressInfo(addressExt1,ems);
            } else {
                Integer spellId=this.getSpellIdFromAddressExt(addressExt);
                Ems ems=null;
                if(spellId!=null)
                {
                    ems = emsDao.getEms(spellId);
                }
                return new AddressInfo(addressExt,ems);
            }
        }
    }

    private Integer getSpellIdFromAddressExt(AddressExt addressExt)
    {
        Integer spellId=null;
        if(addressExt.getArea()!=null&&addressExt.getArea().getSpellid()!=null)
        {
            spellId=addressExt.getArea().getSpellid();
        }
        if(spellId==null)
        {
            if(addressExt.getCounty()!=null&&addressExt.getCounty().getSpellid()!=null)
            {
                spellId=addressExt.getCounty().getSpellid();
            }
        }
        return spellId;
    }

    @Autowired
    private PluSplitDao pluSplitDao;

    private Order convertPreTradToOrderhist(PreTradeRest preTrade) throws Exception {
        OrderReturnCode returnCode = new OrderReturnCode();
        if (preTrade == null) {
            returnCode.setCode(OrderCode.FIELD_INVALID);
            returnCode.setDesc("没有前置订单信息");
            throw new OrderException(returnCode);
        }
        if (preTrade.getPreTradeDetails() == null || preTrade.getPreTradeDetails().size() == 0) {
            returnCode.setCode(OrderCode.FIELD_INVALID);
            returnCode.setDesc("没有前置订单明细");
            throw new OrderException(returnCode);
        }

        if(StringUtils.isEmpty(preTrade.getReceiverMobile())&&StringUtils.isEmpty(preTrade.getReceiverPhone()))
        {
            returnCode.setCode(OrderCode.PRETRADE_PHONE_NOT_FOUND);
            returnCode.setDesc("前置订单没有提供联系人电话信息");
            throw new OrderException(returnCode);
        }

        boolean bInvalid=true;
        if(StringUtils.isEmpty(preTrade.getReceiverName()))
        {
            bInvalid=false;
        }
        else
        {
            if(preTrade.getReceiverName().length()<2)
            {
                bInvalid=false;
            }
        }
        if(bInvalid==false)
        {
            returnCode.setCode(OrderCode.PRETRADE_CONTACT_NAME_INVALID);
            returnCode.setDesc("客户名称长度不能小于2");
            throw new OrderException(returnCode);
        }
        bInvalid=true;
        if(StringUtils.isEmpty(preTrade.getReceiverAddress()))
        {
            bInvalid=false;
        }
        else
        {
            if(preTrade.getReceiverAddress().length()<2
                    ||preTrade.getReceiverAddress().length()>=128)
                bInvalid=false;
        }
        if(bInvalid==false)
        {
            returnCode.setCode(OrderCode.PRETRADE_ADDRESS_DSC_INVALID);
            returnCode.setDesc("地址长度无效（应该大于等于2，小于128）");
            throw new OrderException(returnCode);
        }


        //根据电话号码获取客户ID(同时判断是否名称一致)
        /* old code
        Phone phone = null;
        if (StringUtils.isNotEmpty(preTrade.getReceiverMobile())) {
            phone = phoneDao.findByPhoneNum(preTrade.getReceiverMobile());
        }

        Contact contact = null;
        if (phone != null) {
            contact = contactDao.get(phone.getContactid());
            if(!preTrade.getReceiverName().equals(contact.getName()))
            {
                contact=null;
            }
        }*/
        //new code start
        Phone phone=null;
        Contact contact=null;
        if(StringUtils.isNotEmpty(preTrade.getReceiverMobile()))
        {
            List<Phone> phoneList=phoneDao.findList("from Phone where phn2 =:phn and (state=2 or state=4 or state is null)", new ParameterString("phn",preTrade.getReceiverMobile()));
            if(phoneList!=null)
            {
                for(Phone phone1:phoneList)
                {
                    Contact contact1=contactDao.get(phone1.getContactid());
                    if((contact1.getState()==null||contact1.getState().intValue()==2) && preTrade.getReceiverName().equals(contact1.getName()))
                    {
                        contact=contact1;
                        phone=phone1;
                        break;
                    }
                }
            }
        }
        //new code end


        AddressExt addressExt = null;
        boolean bHaveContact = false;
        if (contact==null) {
            //获取客户ID号
            //String contactId = contactDao.GetContactId();

            contact = new Contact();
            //contact.setContactid(contactId);
            contact.setName(preTrade.getReceiverName());
            contact.setSex("2");
            contact.setCrdt(new Date());
            contact.setCrusr(preTrade.getCrusr());
            contact.setCrtm(new Date());
            contact.setMembertype("4");
            contact.setTotal(0L);
            contact.setAreacode(preTrade.getReceiverPostCode());

            contact=contactDao.save(contact);
            //新建电话
            if(StringUtils.isNotEmpty(preTrade.getReceiverMobile()))
            {
                phone = new Phone();
                phone.setPhn2(preTrade.getReceiverMobile());
                phone.setPhonetypid("4");
                phone.setPrmphn("Y");

                phone.setContactid(contact.getContactid());
                phoneDao.saveOrUpdate(phone);
            }

        } else {
            bHaveContact = true;
        }
        //最后保存座机
        if (StringUtils.isNotEmpty(preTrade.getReceiverPhone())) {
            String ph1 = null;
            String ph2 = null;
            String ph3 = null;
            //拆分电话号码
            String[] tokens = StringUtils.split(preTrade.getReceiverPhone(), "-");
            if (tokens != null && tokens.length > 0) {
                if (tokens.length == 1) {
                    ph2 = tokens[0].trim();
                } else if (tokens.length == 2) {
                    ph1 = tokens[0].trim();
                    ph2 = tokens[1].trim();
                } else {
                    ph1 = tokens[0].trim();
                    ph2 = tokens[1].trim();
                    ph3 = tokens[2].trim();
                }

                boolean bHavePhone = false;
                if (bHaveContact == true) {
                    //首先检查电话是否已经有了，有的话就不修改
                    if (checkPhoneFromContact(ph1, ph2, ph3, contact.getContactid())) {
                        bHavePhone = true;

                    }
                }
                if (bHavePhone == false) {
                    //保存
                    Phone phone1 = new Phone();
                    phone1.setPhn1(ph1);
                    phone1.setPhn2(ph2);
                    phone1.setPhn3(ph3);
                    phone1.setPhonetypid("2");
                    if(phone!=null)
                        phone1.setPrmphn("N");
                    else
                        phone1.setPrmphn("Y");
                    phone1.setContactid(contact.getContactid());
                    phoneDao.save(phone1);
                }
            }
        }

        AddressInfo addressInfo = this.findOrCreateAddressExtFromPreTrade(preTrade, contact.getContactid());
        Ems ems=null;
        if(addressInfo!=null)
        {
            ems=addressInfo.ems;
            addressExt = addressInfo.addressExt;
        }
        //addressExt = this.findOrCreateAddressExtFromPreTrade(preTrade, contact.getContactid());
        //addressExt=addressExtService.findAddressFromNames(preTrade.getReceiverProvince(),preTrade.getReceiverCity(),preTrade.getReceiverCounty(),preTrade.getReceiverArea(),preTrade.getReceiverAddress());
        if (addressExt == null) {
            //如果没有地址，直接报错
            returnCode.setCode(OrderCode.PRETRADE_ADDRESS_ERROR);
            returnCode.setDesc("前置订单地址信息不正确");
            throw new OrderException(returnCode);
        }

        if (StringUtils.isEmpty(preTrade.getTradeId())) {
            returnCode.setCode(OrderCode.FIELD_INVALID);
            returnCode.setDesc("前置订单没有网络订单号");
            throw new OrderException(returnCode);
        }

        logger.info("pre trade id:"+preTrade.getTradeId());
        Order sameOrder= this.getOrderHistByNetOrderId(preTrade.getTradeId());
        if (sameOrder != null) {
            returnCode.setCode(OrderCode.PRETRADE_HAVE_CREATED);
            returnCode.setDesc(sameOrder.getOrderid());
            //returnCode.setDesc("相同网络订单号已经创建过了");
            throw new OrderException(returnCode);
        }
        //获取订单号
        Date dtNow = new Date();
        Order orderhist = new Order();
        String orderId = orderhistDao.getOrderId();

        if(ems!=null)
        {
            orderhist.setSpellid(ems.getSpellid());
            orderhist.setCityid(ems.getCityid());
        }
        else
        {
            orderhist.setSpellid(getSpellIdFromAddressExt(addressExt));
        }

        orderhist.setResult("1");
        orderhist.setUrgent("0");
        orderhist.setConfirm("0");

        logger.debug("前置订单id：" + orderId);

        orderhist.setOrderid(orderId);
        orderhist.setAddress(addressExt);
        if (preTrade.getTradeType() != null)
            orderhist.setOrdertype(preTrade.getTradeType());
        if (preTrade.getPayType() != null)
            orderhist.setPaytype(preTrade.getPayType());
        if (preTrade.getInvoiceTitle() != null) {
            orderhist.setInvoicetitle(preTrade.getInvoiceTitle());
        }
        orderhist.setMailprice(new BigDecimal(preTrade.getShippingFee() != null ? preTrade.getShippingFee().toString() : "0"));
        orderhist.setTotalprice(new BigDecimal(preTrade.getPayment() != null ? preTrade.getPayment().toString() : "0"));
        orderhist.setProdprice(orderhist.getTotalprice().subtract(orderhist.getMailprice()));
        orderhist.setNowmoney(orderhist.getTotalprice());

        if (preTrade.getBuyerMessage() != null) {
            orderhist.setNote(preTrade.getBuyerMessage());//TODO:字段
        }
        if (preTrade.getCrusr() != null) {
            orderhist.setCrusr(preTrade.getCrusr());
        }
        orderhist.setContactid(contact.getContactid().toString());
        orderhist.setPaycontactid(contact.getContactid().toString());
        orderhist.setGetcontactid(contact.getContactid().toString());

        orderhist.setProvinceid(addressExt.getProvince().getProvinceid());
        //orderhist.setCityid(addressExt.getCity().getCityid().toString());
        //spellid,result
        orderhist.setCrdt(new Date());
        orderhist.setNetorderid(preTrade.getTradeId());
        orderhist.setNetcrdt(preTrade.getOutCrdt() != null ? preTrade.getOutCrdt() : new Date());
        if (preTrade.getAlipayTradeId() != null) {
            orderhist.setAlipayid(preTrade.getAlipayTradeId());
        }
        if (preTrade.getTmsCode() != null) {
            orderhist.setEntityid(preTrade.getTmsCode());
            orderhist.setCompanyid(preTrade.getTmsCode());
        }

        orderhist.setAreacode("HLW");
        if (preTrade.getInvoiceComment() != null) {
            orderhist.setBill(preTrade.getInvoiceComment());
        } else {
            orderhist.setBill("1");
            if (orderhist.getInvoicetitle() == null) {
                orderhist.setInvoicetitle("个人");
            }
        }
        orderhist.setJifen("0");
        orderhist.setTicket(0);
        orderhist.setTicketcount(0);
        orderhist.setGrpid(preTradeGrpId);


        orderhist.setStatus("1");

        OrderDetail orderdetMaxPrice = null;
        for (PreTradeDetail preTradeDetail : preTrade.getPreTradeDetails()) {
            OrderDetail orderdet = new OrderDetail();
            //从序列中获取订单明细号
            orderdet.setOrderdetid(orderdetDao.getOrderdetId());

            logger.debug("前置订单明细id：" + orderdet.getOrderdetid());

            orderdet.setOrderid(orderId);
            orderdet.setOrderhist(orderhist);

            if (preTradeDetail.getOutSkuId() == null) {
                returnCode.setCode(OrderCode.FIELD_INVALID);
                returnCode.setDesc("前置订单明细中没有产品编码");
                throw new OrderException(returnCode);
            }
            orderdet.setProdid(preTradeDetail.getOutSkuId());
            orderdet.setContactid(contact.getContactid());
            //商品名称要使用plubaseinfo中取来的值
            String prodName=getProductNameFromSkuId(preTradeDetail.getOutSkuId());
            if(StringUtils.isEmpty(prodName))
            {
                returnCode.setCode(OrderCode.FIELD_INVALID);
                returnCode.setDesc("前置订单明细中提供的产品编码未找到对应信息："+preTradeDetail.getOutSkuId());
                throw new OrderException(returnCode);
            }
            orderdet.setProdname(prodName);
            /*
            if (preTradeDetail.getSkuName() != null) {
                orderdet.setProdname(preTradeDetail.getSkuName());
            }*/
            if (preTradeDetail.getPayment() != null) {
                orderdet.setPayment(new BigDecimal(preTradeDetail.getPayment().toString()));
            } else {
                orderdet.setPayment(new BigDecimal("0"));
            }

            orderdet.setSpnum(preTradeDetail.getQty() != null ? preTradeDetail.getQty() : 0);
            orderdet.setSprice(new BigDecimal(preTradeDetail.getUpPrice() != null ? preTradeDetail.getUpPrice().toString() : "0"));
            orderdet.setUprice(new BigDecimal(preTradeDetail.getPrice() != null ? preTradeDetail.getPrice().toString() : "0"));
            orderdet.setUpnum(new Integer(0));
            orderdet.setFreight(BigDecimal.ZERO);
            //orderdet.setFreight(new BigDecimal(preTradeDetail.getShippingFee() != null ? preTradeDetail.getShippingFee().toString() : "0"));
            //明细状态
            orderdet.setStatus("1");
            //12位编码到10位编码转换
            if (StringUtils.isNotEmpty(preTradeDetail.getOutSkuId())) {
                Map<String, Object> agentProdInfo = orderSkuSplitService.findIagentProduct(preTradeDetail.getOutSkuId());
                if (agentProdInfo != null && agentProdInfo.containsKey("prodid") && agentProdInfo.containsKey("prodtype")) {
                    String prodId = (String) agentProdInfo.get("prodid");
                    String prodType = (String) agentProdInfo.get("prodtype");

                    if (StringUtils.isNotEmpty(prodId)) {
                        orderdet.setProdid(prodId);
                        orderdet.setProducttype(prodType);
                    }
                }
            }

            if (StringUtils.isEmpty(orderdet.getProdid())) {
                returnCode.setCode(OrderCode.PRETRADE_PROD_INVALID);
                returnCode.setDesc("产品或套件编号不存在");
                throw new OrderException(returnCode);
            }
            orderdet.setSoldwith("2");
            orderdet.setReckoning("N");
            orderdet.setOrderdt(dtNow);
            orderdet.setJifen("0");
            orderdet.setState(orderhist.getAddress().getProvince().getChinese());
            orderdet.setCity(orderhist.getAddress().getCity().getCityname());
            orderdet.setProvinceid(orderhist.getAddress().getProvince().getProvinceid());
            if(ems!=null)
            {
                orderdet.setProvinceid(ems.getProvinceid());
                //orderdet.setCity(ems.getName());
            }
            orderhist.getOrderdets().add(orderdet);

            if (orderdetMaxPrice == null) {
                orderdetMaxPrice = orderdet;
            } else {
                if (getSellPriceFromOrderdet(orderdetMaxPrice).compareTo(getSellPriceFromOrderdet(orderdet)) < 0) {
                    orderdetMaxPrice = orderdet;
                }
            }
        }
        if (orderdetMaxPrice != null) {
            orderdetMaxPrice.setSoldwith("1");
            orderdetMaxPrice.setFreight(orderhist.getMailprice());
        }


        if (orderhist.getTotalprice().compareTo(BigDecimal.ZERO) == 0) {
            //检查特定规则
            if (!orderhist.getOrdertype().equals("111")
                    || (orderhist.getNote() == null || !orderhist.getNote().startsWith("售后返修"))) {
                returnCode.setCode(OrderCode.ORDER_PRICE_ZERO);
                returnCode.setDesc("前置订单总价不正确，只有返修订单类型才允许为0");
                throw new OrderException(returnCode);
            }
        }

        if(orderhist.getTotalprice().compareTo(BigDecimal.ZERO) < 0)
        {
            returnCode.setCode(OrderCode.ORDER_PRICE_ZERO);
            returnCode.setDesc("无效商品总价");
            throw new OrderException(returnCode);
        }

        this.setOrderProductInfo(orderhist);

        orderhist.setMedia("2402");
        String strNote=preTrade.getTradeId();
        if(StringUtils.isNotEmpty(orderhist.getNote()))
        {
            strNote+=" ";
            strNote+=orderhist.getNote();
        }
        orderhist.setNote(strNote);

        //检查信用卡
        checkCreditFromPretrade(preTrade,orderhist);
        return orderhist;
    }

    private String getProductNameFromSkuId(String skuId)
    {
        try
        {
        PlubasInfo plubasInfo=pluSplitDao.find("from PlubasInfo where plucode=:plucode",new ParameterString("plucode",skuId));
        if(plubasInfo==null)
            return null;
        Product product=productDao.get(plubasInfo.getNccode());
        if(product==null)
            return null;
        return product.getProdname();
        }catch (Exception exp)
        {
            logger.error("get product name error:",exp);
            return null;
        }
    }

    @Autowired
    @Qualifier("tc.core.dao.CardDaoImpl")
    private CardDao cardDao;
    @Autowired
    @Qualifier("tc.core.dao.CardtypeDaoImpl")
    private CardtypeDao cardtypeDao;

    private void checkCreditFromPretrade(PreTradeRest preTradeRest, Order order)
    {
        OrderReturnCode returnCode = new OrderReturnCode();

        if(creditCardOrderTypeList.contains(order.getOrdertype()))
        {
            //检查输入参数
            if(preTradeRest.getPreTradeCards()==null||preTradeRest.getPreTradeCards().size()!=1)
            {
                returnCode.setDesc("导入信用卡订单时，需要提供信用卡相关信息");
                returnCode.setCode(OrderCode.PRETRADE_CREDITCARD_IMPORT_INVALID);
                throw new OrderException(returnCode);
            }
            PreTradeCard preTradeCard=null;
            for(PreTradeCard preTradeCard1:preTradeRest.getPreTradeCards())
            {
                preTradeCard=preTradeCard1;
                break;
            }
            if(StringUtils.isEmpty(preTradeCard.getBankCode())||StringUtils.isEmpty(preTradeCard.getAuthCode())||StringUtils.isEmpty(preTradeCard.getIdCardNumber())
                    ||StringUtils.isEmpty(preTradeCard.getCreditCardNumber())||StringUtils.isEmpty(preTradeCard.getCreditCardExpire())||preTradeCard.getCreditCardCycles()==null)
            {
                returnCode.setDesc("导入信用卡订单时，需要提供信用卡相关信息");
                returnCode.setCode(OrderCode.PRETRADE_CREDITCARD_IMPORT_INVALID);
                throw new OrderException(returnCode);
            }
            if(preTradeCard.getIdCardNumber().length()>50)
            {
                returnCode.setCode(OrderCode.PRETRADE_IDCARD_INVALID);
                returnCode.setDesc("身份证长度大于50位了");
                throw new OrderException(returnCode);
            }
            if(preTradeCard.getCreditCardCycles().intValue()<0)
            {
                returnCode.setDesc("信用卡期数不是有效数字");
                returnCode.setCode(OrderCode.PRETRADE_CREDITCARDCYCLES_INVALID);
                throw new OrderException(returnCode);
            }
            //检查有效期格式
            if(!isValidCreditExpire(preTradeCard.getCreditCardExpire()))
            {
                returnCode.setDesc("信用卡有效期日期格式不正确（yyyy-MM）");
                returnCode.setCode(OrderCode.PRETRADE_CREDITCARDEXPIRE_INVALID);
                throw new OrderException(returnCode);
            }

            //先将身份证、卡插入card表
            checkAndInsertIDCard(order.getContactid(),preTradeCard.getIdCardNumber());
            checkAndInsertCreditCard(preTradeCard,order);

        }
        if(StringUtils.isNotEmpty(preTradeRest.getMailType()))
        {
            order.setMailtype(preTradeRest.getMailType());
        }
    }

    private static DateFormat expireDateFormat=new SimpleDateFormat( "yyyy-MM");
    private static Pattern expireDatePattern = Pattern.compile("[0-9]{4}-[0-9]{2}");
    private synchronized boolean isValidCreditExpire(String expire)
    {
        try
        {
            expireDateFormat.parse(expire);
        } catch (ParseException exp)
        {
            return false;
        }

        if(expireDatePattern.matcher(expire).matches())
        {
            return true;
        }

        return false;
    }

    private void checkAndInsertCreditCard(PreTradeCard preTradeCard,Order order)
    {
        List<Cardtype> cardtypeList=cardtypeDao.findList("from Cardtype where cardtypeid=:cardtypeid",new ParameterString("cardtypeid",preTradeCard.getBankCode()));
        if(cardtypeList==null||cardtypeList.size()<=0)
        {
            OrderReturnCode returnCode = new OrderReturnCode();
            returnCode.setDesc("信用卡未找到对应类型信息");
            returnCode.setCode(OrderCode.PRETRADE_CREDITCARDTYPE_INVALID);
            throw new OrderException(returnCode);
        }
        Cardtype cardtype = cardtypeList.get(0);

        List<Card> cardList=cardDao.findList("from Card where contactId=:contactId and cardNumber=:cardNumber and type=:type",
                new ParameterString("contactId",order.getContactid()),new ParameterString("cardNumber",preTradeCard.getCreditCardNumber()),new ParameterString("type",preTradeCard.getBankCode()));

        Long cardId=null;
        if(cardList==null||cardList.size()<=0)
        {
            //增加信用卡
            Card card=new Card();
            card.setCardNumber(preTradeCard.getCreditCardNumber());
            card.setContactId(order.getContactid());
            card.setType(preTradeCard.getBankCode());
            card.setChecked("Y");
            card.setValidDate(preTradeCard.getCreditCardExpire());
            Card card1 = cardDao.save(card);

            cardId=card1.getCardId();
        }
        else
        {
            cardId=cardList.get(0).getCardId();
        }
        //设置到订单
        order.setCardnumber(cardId.toString());
        order.setLaststatus(preTradeCard.getCreditCardCycles().toString());
        order.setCardtype(cardtype.getCardname());
        order.setCardrightnum(preTradeCard.getAuthCode());
        order.setConfirm("-1");
    }
    private void checkAndInsertIDCard(String contactId,String idCardNumber)
    {
        List<Card> cardList=cardDao.findList("from Card where contactId=:contactId",new ParameterString("contactId",contactId));
        if(cardList!=null)
        {
            for(Card card:cardList)
            {
                if(idCardNumber.equals(card.getCardNumber()))
                {
                    return;
                }
            }
        }

        Card card=new Card();
        card.setCardNumber(idCardNumber);
        card.setContactId(contactId);
        card.setType("001");
        cardDao.save(card);
    }


    private static BigDecimal getSellPriceFromOrderdet(OrderDetail orderdet) {
        if (orderdet.getSprice().compareTo(BigDecimal.ZERO) > 0) {
            return orderdet.getSprice();
        } else {
            return orderdet.getUprice();
        }
    }

    private void setOrderProductInfo(Order order)
    {
        List<String> prodIdList=new ArrayList<String>();
        if(order.getOrderdets()!=null)
        {
            for(OrderDetail orderDetail: order.getOrderdets())
            {
                if(StringUtils.isNotEmpty(orderDetail.getProdid())&&!prodIdList.contains(orderDetail.getProdid()))
                {
                    prodIdList.add(orderDetail.getProdid());
                }
            }
            if(prodIdList.size()>0)
            {
                List<Product> productList=productDao.getProductsFromProdIds(prodIdList);
                if(productList!=null)
                {
                    for(OrderDetail orderDetail: order.getOrderdets())
                    {
                        for(Product product:productList)
                        {
                            if(StringUtils.isNotEmpty(orderDetail.getProdid())&&orderDetail.getProdid().equals(product.getProdid()))
                            {
                                orderDetail.setProdscode(product.getScode());
                                orderDetail.setAccountingcost(product.getDiscount());
                                if(StringUtils.isEmpty(orderDetail.getProdname()))
                                {
                                    orderDetail.setProdname(product.getProdname());
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean isSameString(String left, String right, boolean ignoreEmpty) {
        if (ignoreEmpty == true) {
            if (StringUtils.isEmpty(left) && StringUtils.isEmpty(right)) {
                return true;
            } else if (StringUtils.isNotEmpty(left) && StringUtils.isNotEmpty(right)) {
                return left.equals(right);
            }
        } else {
            if (left == null) {
                if (right == null)
                    return true;
            } else {
                return left.equals(right);
            }
        }

        return false;
    }

    private boolean checkPhoneFromContact(String ph1, String ph2, String ph3, String contactId) {
        List<Phone> phoneList = phoneDao.findPhoneListFromContactAndType(contactId, 1);
        if (phoneList != null && phoneList.size() > 0) {
            for (Phone phone1 : phoneList) {
                if (isSameString(ph1, phone1.getPhn1(), true)
                        && isSameString(ph2, phone1.getPhn2(), true)
                        && isSameString(ph3, phone1.getPhn3(), true)) {
                    return true;
                }
            }
        }
        phoneList = phoneDao.findPhoneListFromContactAndType(contactId, 1);
        if (phoneList != null && phoneList.size() > 0) {
            for (Phone phone1 : phoneList) {
                if (isSameString(ph1, phone1.getPhn1(), true)
                        && isSameString(ph2, phone1.getPhn2(), true)
                        && isSameString(ph3, phone1.getPhn3(), true)) {
                    return true;
                }
            }
        }

        return false;
    }
    /**
     * 产生订单前检查字段数据
     * 有些字段只有在产生时需要检查的，暂时不放在实体类里面
     * @param orderhist
     * @return OrderReturnCode
     */
    private OrderReturnCode checkNewOrderhist(Order orderhist) {
        OrderReturnCode returnCode = new OrderReturnCode();

        if (orderhist.getOrderid() == null) {
            returnCode.setCode(OrderCode.FIELD_INVALID);
            returnCode.setDesc("没有设置订单Id");
        }
        if (orderhist.getCrusr() == null) {
            returnCode.setCode(OrderCode.FIELD_INVALID);
            returnCode.setDesc("没有设置创建人信息");
        }

        if (orderhist.getCrdt() == null)
            orderhist.setCrdt(new Date());

        if (orderhist.getOrderdets() != null && orderhist.getOrderdets().size() > 0) {
            for (OrderDetail orderdet : orderhist.getOrderdets()) {
                if (orderdet.getOrderdetid() == null) {
                    returnCode.setCode(OrderCode.FIELD_INVALID);
                    returnCode.setDesc("没有设置订单明细Id");
                }
            }
        } else {
            returnCode.setCode(OrderCode.NOT_ORDERDET);
            returnCode.setDesc("没有定义订单明细");
        }

        if (orderhist.getAddress() != null && orderhist.getAddress().getAddressId() != null) {
            orderhist.setAddress(addressExtService.getAddressExt(orderhist.getAddress().getAddressId()));
        } else {
            returnCode.setCode(OrderCode.FIELD_INVALID);
            returnCode.setDesc("没有设置订单地址信息");
        }
        //业务版本从1开始
        if (orderhist.getRevision() == null || (orderhist.getRevision() != null && orderhist.getRevision().intValue() <= 0)) {
            orderhist.setRevision(1);
        }
        if (orderhist.getPaytype() == null) {
            returnCode.setCode(OrderCode.FIELD_INVALID);
            returnCode.setDesc("没有设置支付类型");
        }
        if (orderhist.getOrdertype() == null) {
            returnCode.setCode(OrderCode.FIELD_INVALID);
            returnCode.setDesc("没有设置订单类型");
        }

        return returnCode;
    }

    private OrderReturnCode checkUpdateOrderhist(Order orderhist) {
        OrderReturnCode returnCode = new OrderReturnCode();

        //首先检查版本是否给定了
        //目前先不考虑并发的问题，先忽略
        /*if(orderhist.getRevision()==null)
        {
            returnCode.setCode(OrderCode.FIELD_INVALID);
            returnCode.setDesc("未提供版本信息");
            return returnCode;
        }*/

        if (orderhist.getOrderid() == null) {
            returnCode.setCode(OrderCode.FIELD_INVALID);
            returnCode.setDesc("没有设置订单Id");
        }
        if (orderhist.getMdusr() == null) {
            returnCode.setCode(OrderCode.FIELD_INVALID);
            returnCode.setDesc("没有设置修改人信息");
        }

        if (orderhist.getMddt() == null)
            orderhist.setMddt(new Date());

        if (orderhist.getOrderdets() != null) {
            for (OrderDetail orderdet : orderhist.getOrderdets()) {
                if (orderdet.getOrderdetid() == null && orderdet.getId() == null) {
                    returnCode.setCode(OrderCode.FIELD_INVALID);
                    returnCode.setDesc("没有设置订单明细Id");
                }
            }
        }

        if (orderhist.getAddress() != null && orderhist.getAddress().getAddressId() != null) {
            orderhist.setAddress(addressExtService.getAddressExt(orderhist.getAddress().getAddressId()));
        }

        return returnCode;
    }

    @Deprecated
    public boolean isNeedNewRevision(Order orderhist) {
        if(StringUtils.isNotBlank(orderhist.getCustomizestatus()))
        {
            int flag=Integer.parseInt(orderhist.getCustomizestatus());
            if(flag>=2)
            {
                logger.info("update order logistic status is error:"+orderhist.getCustomizestatus());
                return false;
            }
        }
        else
        {
            if(orderhist.getCustomizestatus()!=null)
            {
                logger.error("customizestatus is error:"+orderhist.getCustomizestatus()+"*");
            }
        }

        return true;
        /*if (orderhist.getCustomizestatus() != null && !orderhist.getCustomizestatus().equals("0")
                && !orderhist.getCustomizestatus().equals("1")*//*&&!orderhist.getCustomizestatus().equals("2")*//*) {
            return false;
        } else {
            return true;
        }*/
    }

    /**
     * 更新订单，如果查找不到，那么插入
     *
     * @param orderhist
     * @throws Exception
     */
    private Order addOrUpdateOrderhist(Order orderhist, int warehouseId) {
        Order orgOrderhist = null;
        if (orderhist.getId() != null) {
            orgOrderhist = orderhistDao.get(orderhist.getId());
        } else {

            logger.debug("get orderhist from orderid");

            orgOrderhist = orderhistDao.getOrderHistByOrderid(orderhist.getOrderid());
        }
        //新增订单
        if (orgOrderhist == null) {
            logger.info("add order orderid:"+orderhist.getOrderid());
            return addOrder(orderhist, warehouseId);
        }
        //修改订单
        else {
            logger.info("update order orderid:"+orderhist.getOrderid()+ " status:"+orderhist.getStatus());
            //logger.error("order status:"+orderhist.getStatus());
            resetOrderStatusOnModify(orderhist);
            return updateOrder(orderhist, orgOrderhist, warehouseId);
        }
    }

    private void resetOrderStatusOnModify(Order order)
    {
        if(AccountStatusConstants.ACCOUNT_TRANS.equals(order.getStatus()))
        {
            //信用卡订单且状态为分拣时，不重置订单状态
            if(!"2".equals(order.getPaytype()))
            {
                order.setStatus(AccountStatusConstants.ACCOUNT_NEW);
                order.setIsassign("");
            }
        }
        else
        {
            order.setIsassign("");
        }
    }

    /**
     * 更新订单，如果查找不到，那么插入
     * @param orderhist
     * @throws Exception
     */
    public void updateOrderhist(Order orderhist) throws Exception {

        //1、拆分产品套装：产生单品（12位SKU码）的产品明细。 -ok

        //2、调用承运商规则：分配送货公司，仓库。    -ok
        //（前提条件：承运商规则服务功能需正式提供服务。）

        //3、调用库存服务。                             -ok
        //库存服务功能：判断库存是否满足，如果满足则更新库存数量，不满足则返回错误。
        //（前提条件：库存服务功能需正式提供服务。）

        //4、保存订单信息。

        //5、调用积分、券计算规则服务。(另外服务实现)

        //6、调用结算规则服务。
        //（前提条件：结算规则服务功能需正式提供服务。）

        //7、生成订单快照历史。

        //8、生成发运单。

        //9、调用其他扩展信息（异步）。

    	//Long startStamp = System.currentTimeMillis();
    	
    	Order orgOrderhist=addOrUpdateOrderhist(orderhist,0);
        
        //Long orderStamp = System.currentTimeMillis();
        
        //logger.warn("Order generated costs: "+ (orderStamp-startStamp));
        
        //Long endStamp = System.currentTimeMillis();
        
        //logger.warn("Shipment generated costs : " + (endStamp-orderStamp));
        
        //logger.warn("Order & Shipment ["+ orgOrderhist.getOrderid() +"] generated costs : " + (endStamp-startStamp));
    }
    /**
     * 修改运单相关的订单信息
     * orderhist:订单信息
     * entityId:新发货公司
     * warehouseId:新仓库
     */
    public void updateOrderhist(Order orderhist,String entityId, String wardhouseId)
			throws Exception {
    	logger.debug("update orderhist start");
    	 //首先生成订单快照历史，然后更新
        List<OrderDetail> orgOrderdetList=new ArrayList<OrderDetail>();
        for(OrderDetail orderdet: orderhist.getOrderdets())
        {
            orgOrderdetList.add(orderdet);
        }
        orderHistoryService.insertTcHistory(orderhist, orgOrderdetList);
        orderhistDao.saveOrUpdate(orderhist);
        logger.debug("update orderhist end");

    	 //生成发运单
        shipmentHeaderService.generateShipmentHeader(orderhist,entityId,wardhouseId);
        logger.debug("update generateWaybill end");
	}


    private boolean checkOrderhistInventoryChange(Order orderhist, Order orgOrderhist) {
        //如果明细有变化，那么先删除，后增加
        boolean bInventoryChanged = false;
        if (orderhist.getOrderdets().size() == orgOrderhist.getOrderdets().size()) {
            for (OrderDetail orgOrderdet : orgOrderhist.getOrderdets()) {
                boolean bFind = false;
                for (OrderDetail orderdet : orderhist.getOrderdets()) {
                    if (orgOrderdet.getOrderdetid().equals(orderdet.getOrderdetid())) {
                        //检查产品以及数量
                        Integer orgQuantity = orgOrderdet.getUpnum() + orgOrderdet.getSpnum();
                        Integer quantity = orderdet.getUpnum() + orderdet.getSpnum();
                        if (!orgOrderdet.getProdid().equals(orderdet.getProdid())
                                || !orgQuantity.equals(quantity)) {
                            bInventoryChanged = true;
                            break;
                        }
                    }
                }
                if (bFind == false) {
                    bInventoryChanged = true;
                    break;
                }
            }
        } else {
            bInventoryChanged = true;
        }

        return bInventoryChanged;
    }

    /**
     * 更新单笔订单明细
     *
     * @param orderdet
     * @throws Exception
     */
    public void updateOrderhistOrderdet(OrderDetail orderdet) throws Exception {
        //1、拆分产品套装：产生单品（12位SKU码）的产品明细。

        //2、调用承运商规则：分配送货公司，仓库。
        //（前提条件：承运商规则服务功能需正式提供服务）

        //3、调用库存服务。
        //库存服务功能：判断库存是否满足，如果满足则更新库存数量，不满足则返回错误。
        //（前提条件：库存服务功能需正式提供服务。）

        //4、保存订单明细信息，同时更新订单表头信息。

        //5、调用积分、券计算规则服务。

        //6、调用结算规则服务。
        //（前提条件：结算规则服务功能需正式提供服务。）

        //7、生成订单快照历史。

        //8、生成发运单。

        //9、调用其他扩展信息（异步）。

        OrderReturnCode returnCode = new OrderReturnCode();

        OrderDetail orgOrderdet = getOrderdetAndOrderhistFromOrderdet(orderdet);

        try {
            //更新订单明细
            if (orgOrderdet.getId() != null) {
                if (orderdet.getRevision() == null) {
                    returnCode.setCode(OrderCode.FIELD_INVALID);
                    returnCode.setDesc("未提供订单明细版本");
                    throw new OrderException(returnCode);
                }

                //生成历史快照
                Order orderhist = orgOrderdet.getOrderhist();
                List<OrderDetail> orgOrderdetList = new ArrayList<OrderDetail>();
                for (OrderDetail orderdet1 : orderhist.getOrderdets()) {
                    orgOrderdetList.add(orderdet1);
                }
                orderHistoryService.insertTcHistory(orderhist, orgOrderdetList);

                //更新字段
                orderdetUtil.CopyNotNullValue(orderdet, orgOrderdet);

                orderdetDao.saveOrUpdate(orgOrderdet);
            } else {
                //新增订单明细
                orderdetDao.save(orgOrderdet);
            }
        } catch (ConstraintViolationException constraintExp) {
            //目前先只返回一个错误提示
            throw constraintExp;
            /*returnCode.setCode(OrderCode.FIELD_INVALID);
            returnCode.setDesc(this.getConstraintErrorDesc(constraintExp));
            throw new OrderException(returnCode);*/
        }
    }

    /**
     * 从传递进来的订单明细中获取完整的订单明细信息（包含有效性检查）
     * @param orderdet
     * @return OrderDetail
     */
    private OrderDetail getOrderdetAndOrderhistFromOrderdet(OrderDetail orderdet) throws Exception {
        OrderReturnCode returnCode = new OrderReturnCode();

        //检查订单明细标识
        if (orderdet.getId() == null && orderdet.getOrderdetid() == null) {
            returnCode.setCode(OrderCode.FIELD_INVALID);
            returnCode.setDesc("未提供订单明细Id");
            throw new OrderException(returnCode);
        }

        //检查订单标识
        if (orderdet.getOrderhist() == null && orderdet.getOrderid() == null) {
            returnCode.setCode(OrderCode.FIELD_INVALID);
            returnCode.setDesc("未提供订单Id");
            throw new OrderException(returnCode);
        }

        OrderDetail orgOrderdet = null;
        if (orderdet.getId() != null) {
            orgOrderdet = orderdetDao.get(orderdet.getId());
        } else {
            orgOrderdet = orderdetDao.getOrderdetFromOrderdetId(orderdet.getOrderdetid());
        }

        //不存在，那么新增
        if (orgOrderdet == null) {
            //根据明细提供的订单号或者订单引用Id查找
            Order orderhist = orderdet.getOrderhist();
            if (orderhist != null && orderhist.getId() != null) {
                orderhist = orderhistDao.get(orderhist.getId());
            } else {
                orderhist = this.getOrderHistByOrderid(orderdet.getOrderid());
            }
            if (orderhist == null) {
                returnCode.setCode(OrderCode.ORDER_NOT_FOUND);
                returnCode.setDesc("订单明细：" + orderdet.getOrderdetid() + " 对应的订单不存在");
                throw new OrderException(returnCode);
            } else {
                orgOrderdet = orderdet;
                orgOrderdet.setOrderhist(orderhist);
            }
        } else {
            //需要更新日期和人员
            if (orderdet.getMdusr() == null) {
                returnCode.setCode(OrderCode.FIELD_INVALID);
                returnCode.setDesc("需要提供修改人信息mdusr");
                throw new OrderException(returnCode);
            }
            if (orgOrderdet.getOrderhist() == null) {
                returnCode.setCode(OrderCode.ORDER_NOT_FOUND);
                returnCode.setDesc("订单明细：" + orgOrderdet.getOrderdetid() + " 对应的订单不存在");
                throw new OrderException(returnCode);
            }
        }

        return orgOrderdet;
    }

    private ShipmentHeader generateShipmentHeaderbeforeSave(Order orderhist, String warehouseId, ShipmentHeader shipmentHeaderPre) {
        try {
            ShipmentHeader shipmentHeader = null;

            String usr=orderhist.getMdusr();
            if(StringUtils.isEmpty(usr))
            {
                usr=orderhist.getMdusr();
            }
            if (StringUtils.isNotEmpty(warehouseId))
            {
                shipmentHeader = shipmentHeaderService.generateShipmentHeader(orderhist, orderhist.getEntityid(), warehouseId);
            }
            else
            {
                shipmentHeader = shipmentHeaderService.generateShipmentHeader(orderhist, false);
            }
            inventoryAgentService.reserveInventory(orderhist,shipmentHeaderPre, shipmentHeader.getWarehouseId(),usr);

            //对于需要保持原状态的订单，重置运单状态，保持与订单一致
            if(shipmentHeaderPre!=null)
            {
                shipmentHeader.setAccountStatusId(orderhist.getStatus());
                shipmentHeader.setLogisticsStatusId(shipmentHeaderPre.getLogisticsStatusId());
            }

            if (shipmentHeader.getEntityId() != null && !shipmentHeader.getEntityId().equals(orderhist.getEntityid())) {
                orderhist.setEntityid(shipmentHeader.getEntityId());
                Company company = companyService.findCompany(orderhist.getEntityid());
                orderhist.setMailtype(company.getMailtype());
            }

            return shipmentHeader;
        } catch (Exception exp) {
            logger.error("generateShipmentHeader error:", exp);
            throw new RuntimeException(exp.getMessage());
        }
    }

    private Order addOrder(Order orderhist, int warehouseId) {
        logger.debug("add orderhist");

        OrderReturnCode returnCode = new OrderReturnCode();
        returnCode = checkNewOrderhist(orderhist);
        if (returnCode.getCode() != null) {
            logger.error("create order error:"+returnCode.getCode()+" - dsc:"+returnCode.getDesc());
            throw new OrderException(returnCode);
        }

        ShipmentHeader shipmentHeader = null;
        if (this.isNeedNewRevision(orderhist)) {
            String strWarehouseId = warehouseId > 0 ? String.valueOf(warehouseId) : "";
            shipmentHeader = this.generateShipmentHeaderbeforeSave(orderhist, strWarehouseId, null);
        }

        orderhistDao.saveOrUpdate(orderhist);
        OrderHistory orderHistory = orderHistoryService.insertTcHistory(orderhist);
        if (shipmentHeader != null) {
            //设置运单的关联
            shipmentHeader.setOrderHistory(orderHistory);
            try {
                shipmentHeaderService.addShipmentHeader(shipmentHeader);
            } catch (Exception exp) {
                logger.error("add order addShipmentHeader error:", exp);
                throw new RuntimeException(exp.getMessage());
            }
            //shipmentHeaderService.saveOrUpdate(shipmentHeader);
        }
        return orderhist;
    }

    private ShipmentHeader beforeOrderhistUpdate(Order orgOrderhist, Order newOrderhist) {
        if (newOrderhist.getCustomizestatus() != null)
            orgOrderhist.setCustomizestatus(newOrderhist.getCustomizestatus());
        if (this.isNeedNewRevision(orgOrderhist)) {
            return shipmentHeaderService.cancelShipmentHeader(orgOrderhist, orgOrderhist.getMdusr());
        }
        return null;
    }

    /**
     * 修改订单处理(目前只适用于修改请求过来的修改)
     * 根据不同的字段修改情况，判断是否需要产生新的运单下传
     * 对于已经出库的，直接报错
     * 没有分拣的（status=1），直接修改并进行简单地址匹配(对于修改entityId，如何处理？)
     * 已经分拣的（status=2），需要将status改成1，这样后面可以进行复杂分拣（对于修改entityId，如何处理？）
     * 其他情况（status非1、2时），应该是已经取消（0）或者都已经是出库状态了
     * @param orderhist 修改后的订单，需要将未修改的值合并进来
     * @param orgOrderhist 修改前的订单
     * @param warehouseId  指定的仓库Id（如果指定了承运商，那么直接从Company表中取到对应的仓库Id）
     * @return
     */
    private Order updateOrder(Order orderhist, Order orgOrderhist, int warehouseId) {
        logger.info("update orderhist orderId:" + orderhist.getOrderid());
        OrderReturnCode returnCode = new OrderReturnCode();

        //1.未出库订单
        //2.订单支付方式为“信用卡”时，尚未索权成功。
        //TODO:3.其他情况，直接报错



        returnCode = checkUpdateOrderhist(orderhist);
        if (returnCode.getCode() != null) {
            throw new OrderException(returnCode);
        }

        Order orderhistClone = new Order();
        //BeanUtils.copyProperties(orgOrderhist,orderhistClone);
        OrderhistUtil.copy(orderhistClone, orgOrderhist);
        //TODO:是否需要添加判断的逻辑，来避免相同数据的取消和再分配库存
        //更新前处理-取消原先的库存分配
        ShipmentHeader preShipmentHeader=this.beforeOrderhistUpdate(orderhistClone, orderhist);

        orderhistUtil.CopyNotNullValue(orderhist, orderhistClone);
        //获取更新的订单明细
        this.fetchOrderdetModify(orderhist, orderhistClone);

        resetOrderStatusOnModify(orgOrderhist);
        orderhistUtil.CopyNotNullValue(orderhistClone, orgOrderhist);
        this.fetchOrderdetModify(orderhistClone, orgOrderhist);

        ShipmentHeader shipmentHeader = null;
        if (this.isNeedNewRevision(orderhistClone)) {
            orderhistClone.setRevision(orderhistClone.getRevision() + 1);
            String strWarehouseId = warehouseId > 0 ? String.valueOf(warehouseId) : "";
            if(preShipmentHeader!=null)
            {
                strWarehouseId=preShipmentHeader.getWarehouseId();
                //仓库从订单承运商中获取
                if(StringUtils.isNotEmpty(orderhistClone.getEntityid())&&!orderhistClone.getEntityid().equals(preShipmentHeader.getEntityId()))
                {
                    Company company=companyService.findCompany(orderhistClone.getEntityid());
                    if(company!=null&&company.getWarehouseId()!=null)
                    {
                        strWarehouseId=company.getWarehouseId().toString();
                    }
                }
                shipmentHeader = this.generateShipmentHeaderbeforeSave(orderhistClone, strWarehouseId, preShipmentHeader);
            }
            else
                shipmentHeader = this.generateShipmentHeaderbeforeSave(orderhistClone, strWarehouseId, null);
        }

        orderhistUtil.CopyNotNullValue(orderhistClone, orgOrderhist);
        this.fetchOrderdetModify(orderhistClone, orgOrderhist);
        //OrderhistUtil.copy(orgOrderhist, orderhistClone);
        logger.info("org order id:"+orgOrderhist.getOrderid()+"-status:"+orgOrderhist.getStatus()+"-customizestatus:"+orgOrderhist.getCustomizestatus());

        orderhistDao.saveOrUpdate(orgOrderhist);
        OrderHistory orderHistory = orderHistoryService.insertTcHistory(orgOrderhist);
        if (shipmentHeader != null) {
            //设置运单的关联
            shipmentHeader.setOrderHistory(orderHistory);
            try {
                shipmentHeaderService.addShipmentHeader(shipmentHeader);
            } catch (Exception exp) {
                logger.error("upate order addShipmentHeader error:", exp);
                throw new RuntimeException(exp.getMessage());
            }
        }

        return orgOrderhist;
    }

    private void fetchOrderdetModify(Order orderhist, Order orgOrderhist)
    {
        //1.找到，更新
        //2.没有，删除
        Set<OrderDetail> updateOrderdetSet=new HashSet<OrderDetail>();
        Set<OrderDetail> deleteOrderdetSet=new HashSet<OrderDetail>();

        for(OrderDetail orgOrderdet:orgOrderhist.getOrderdets())
        {
            boolean bFind=false;
            for(OrderDetail orderdet:orderhist.getOrderdets())
            {
                if(orgOrderdet.getId().equals(orderdet.getId())
                        ||(orgOrderdet.getOrderdetid().equals(orderdet.getOrderdetid())))
                {
                    //只赋值需要更新的字段（根据字段是否为null来判断，如果字段为"",那么目前设置成null）
                    orderdetUtil.CopyNotNullValue(orderdet,orgOrderdet);
                    updateOrderdetSet.add(orgOrderdet);
                    bFind=true;
                    break;
                }
            }
            if(bFind==false)
            {
                deleteOrderdetSet.add(orgOrderdet);
            }
        }
        orgOrderhist.getOrderdets().clear();
        for(OrderDetail orderdet:updateOrderdetSet)
        {
            orgOrderhist.getOrderdets().add(orderdet);
        }
        //3.新增
        Set<OrderDetail> addOrderdetSet=new HashSet<OrderDetail>();
        for(OrderDetail orderdet:orderhist.getOrderdets())
        {
            boolean bFind=false;
            for(OrderDetail orgOrderdet:orgOrderhist.getOrderdets())
            {
                if(orgOrderdet.getId().equals(orderdet.getId())
                        ||orgOrderdet.getOrderdetid().equals(orderdet.getOrderdetid()))
                {
                    bFind=true;
                    break;
                }
            }
            if(bFind==false)
            {
                addOrderdetSet.add(orderdet);
            }
        }
        for(OrderDetail orderdet:addOrderdetSet)
        {
            orgOrderhist.getOrderdets().add(orderdet);
            orderdet.setOrderhist(orgOrderhist);
        }
    }

    /**
     * 检查订单修改是否需要重新产生新的订单下传
     * @param newOrderhist
     * @param orgOrderhist
     * @return
     */
    private boolean checkOrderModifyDownload(Order newOrderhist, Order orgOrderhist)
    {
        return false;
    }



	public void deleteOrderhist(Order orderhist) {
		orderhistDao.remove(orderhist.getId());
	}


	public Order getOrderhist(Long id) {
		return this.orderhistDao.get(id);
	}

	public String getOrderId() {
		return this.orderhistDao.getOrderId();
	}

	public void justTestAudit(Long id, String mailId) {
		Order orderhist = this.orderhistDao.get(id);
		if (orderhist != null) {
			if (mailId.equals(orderhist.getMailid())) {
				orderhist.setMailid("");
			} else
				orderhist.setMailid(mailId);
		}
	}

	public int updateOrderhist(String setStr, String treadid) throws Exception {
		return this.orderhistDao.updateOrderhist(setStr, treadid);
	}

	public Order getOrderHistByMailId(String mailId) {
		return this.orderhistDao.getOrderHistByMailId(mailId);
	}

	public Order getOrderHistByNetOrderId(String netOrderId) {
		return this.orderhistDao.find("from Order where netorderid=:netOrderId", new ParameterString("netOrderId",
				netOrderId));
	}


	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.tc.service.OrderhistService#updateOrderhist(java.lang.String, java.lang.String)
	 */
	public int updateOrderhistBySql(String setStr, String treadid) throws Exception {
		return orderhistDao.updateOrderhist(setStr, treadid);
	}

    public Order getOrderHistByOrderid(String orderId) {
        return orderhistDao.getOrderHistByOrderid(orderId);
    }





    /*=======================================================================================*/








	/*
	 * 判断订单是否出库
	 * @param customizeStatus
	 * @return true 已出库， false 未出库
	 */
	public boolean judgeOrderStatus(String customizeStatus) {
		//TODO 业务逻辑待调整
		if(null == customizeStatus){
			return false;
		}
		if("".equals(customizeStatus.trim())){
			return false;
		}
		Integer status = -1;
		try {
			status = Integer.parseInt(customizeStatus.trim());
		} catch (NumberFormatException e) {
			logger.error("判断订单状态失败", e);
			e.printStackTrace();
			return false;
		}

		if(status>=2){
			return true;
		}else{
			return false;
		}
	}


    /*
     * SR3.5_1.4删除订单（更新订单为取消状态）
    * <p>Title: 删除订单 （逻辑删除）</p>
    * <p>Description: </p>
    * @param orderhist
    * @throws Exception
    * @see com.chinadrtv.erp.tc.service.OrderhistService#deleteOrderLogic(com.chinadrtv.erp.model.agent.Order)
     */
    public void deleteOrderLogic(Order orderhist) throws Exception {

    	/**
    	 * 	1、拆分产品套装：产生单品（12位SKU码）的产品明细。
			2、调用承运商规则：分配送货公司，仓库。（前提条件：承运商规则服务功能需正式提供服务。）
			3、调用库存服务。库存服务功能：还原库存。（前提条件：库存服务功能需正式提供服务。）
			4、保存订单信息。
			5、调用积分、券计算规则服务。
			6、调用结算规则服务。（前提条件：结算规则服务功能需正式提供服务。）
			7、生成订单快照历史。
			8、更改运单状态为取消，生成 新的取消发运单
			9、调用其他扩展信息（异步）。
    	 */
    	String orderid = orderhist.getOrderid();
    	String modifyUser = orderhist.getMdusr();
    	String callid = orderhist.getCallid();
        String remark=orderhist.getNote();

    	orderhist = this.getOrderHistByOrderid(orderid);
    	if(null == orderhist){
    		throw new BizException("订单["+orderid+"]不存在");
    	}

    	//Step1 已经分别在step3和step8里操作

    	//Step4 更新订单和详情
        orderhist.setNote(remark);
    	this.updateOrderhistAndDetail(orderhist, callid, modifyUser);

    	//Step7
    	OrderHistory orderHistory = orderHistoryService.insertTcHistory(orderhist);

    	//还原库存、取消旧运单
    	ShipmentHeader shipmentHeader = shipmentHeaderService.cancelShipmentHeader(orderhist, modifyUser);
        inventoryAgentService.unreserveInventory(orderhist, shipmentHeader, modifyUser);

    	//生成取消发运单
    	ShipmentHeader sh = shipmentHeaderService.generateShipmentHeader(orderhist, true);
    	sh.setOrderHistory(orderHistory);
    	shipmentHeaderService.addOrUpdateShipmentHeader(sh);

    	//TODO 后期改造, Step5 和Step6
    }


	/*
	 * 更新订单表和详情表
	 * @param orderhist
	 * @return Order
	 * @throws Exception
	 */
	private Order updateOrderhistAndDetail(Order orderhist, String callid, String mdusr)throws Exception {
    	/**
    	 * 订单反馈结果=0-“取消”：更新订单主表如下字段：订单反馈结果='0'，订单状态='0'，修改人，修改日期，反馈日其，实际结算。
      		更新订单明细表如下字段：产品状态='0'，实际付款='0'，修改人，反馈日期。

      		" update orderhist set status = '0', result = '0', mddt = sysdate, mdusr = :mdusr, callid = :callid, jifen = '0', " & _
               "   ticket = '0', ticketcount = '0', nowmoney = 0, totalprice = 0, clearfee = 0+nvl(mailprice, 0)-nvl(postfee, 0), fbdt = sysdate " & _
               " where orderid = :orderid"

				" update orderdet set status = '0', clearfee = 0, payment = '0', mdusr = :mdusr, fbdt = sysdate " & _
               " where orderid = :orderid"

    	 */

		orderhist.setStatus("0");
		orderhist.setResult("0");
		orderhist.setJifen("0");
		orderhist.setTicket(0);
		orderhist.setTicketcount(0);
		orderhist.setNowmoney(new BigDecimal(0));
		//orderhist.setTotalprice(new BigDecimal(0));
		//orderhist.setMailprice(new BigDecimal(0));
		//orderhist.setProdprice(new BigDecimal(0));
		Double mailPrice = null==orderhist.getMailprice() ? 0 : orderhist.getMailprice().doubleValue();
		Double postfee = null==orderhist.getPostfee() ? 0 : orderhist.getPostfee().doubleValue();
		Double clearfee = mailPrice - postfee;
		orderhist.setClearfee(new BigDecimal(clearfee));
		orderhist.setFbdt(new Date());
		orderhist.setCallid(callid);
		orderhist.setMdusr(mdusr);
		orderhist.setMddt(new Date());

		for(OrderDetail od : orderhist.getOrderdets()){
			od.setOrderhist(orderhist);
			od.setStatus("0");
			od.setClearfee(new BigDecimal(0));
			od.setPayment(new BigDecimal(0));
			//od.setUprice(new BigDecimal(0));
			//od.setSprice(new BigDecimal(0));
			od.setFbdt(new Date());
			od.setMdusr(mdusr);
			//od.setLastUpdateTime(new Date());
			//orderdetDao.saveOrUpdate(od);
		}
		int version = orderhist.getRevision() + 1;
		orderhist.setRevision(version);

		orderhistDao.saveOrUpdate(orderhist);

		return orderhist;
	}


	/*
	 * SR3.5_1.3删除订单
	 * @param orderhist
	 * @throws Exception
	 * @see com.chinadrtv.erp.tc.service.OrderhistService#deleteOrderPhysical(com.chinadrtv.erp.model.agent.Order)
	 */
	public void deleteOrderPhysical(Order orderhist) throws Exception {

		/**
		 *  1、拆分产品套装：产生单品（12位SKU码）的产品明细。
			2、调用承运商规则：分配送货公司，仓库。（前提条件：承运商规则服务功能需正式提供服务。）
			3、调用库存服务。库存服务功能：还原库存。（前提条件：库存服务功能需正式提供服务。）
			4、保存订单信息。
			5、调用积分、券计算规则服务。
			6、调用结算规则服务。（前提条件：结算规则服务功能需正式提供服务。）
			7、生成订单快照历史。
			8、保存现有的IAGENT订单历史表（orderhis_hist）。
			9、更改运单状态为取消
			10、调用其他扩展信息（异步）。
		 */

		String orderId = orderhist.getOrderid();
		String mdusr = orderhist.getMdusr();

		orderhist = this.getOrderHistByOrderid(orderId);
    	List<OrderDetail> orderdetList = orderdetDao.getOrderDetList(orderhist);

    	//Step8
    	this.insertAgentHistory(orderhist, orderdetList);

    	//Step4
    	//物理删除订单
    	this.deleteOrderhist(orderhist);

    	//Step9
    	//还原库存，取消运单
        ShipmentHeader shipmentHeader = shipmentHeaderService.cancelShipmentHeader(orderhist, mdusr);
        inventoryAgentService.unreserveInventory(orderhist,shipmentHeader,mdusr);

    	//TODO 后期改造 Step5、6
	}

	/*
	 * 插入历史表
	* <p>Title: insertTcHistory</p>
	* <p>Description: </p>
	* @param orderhist
	* @see com.chinadrtv.erp.tc.service.OrderhistService#insertTcHistory(com.chinadrtv.erp.model.agent.Order)
	 */
	public OrderHistory insertTcHistory(Order orderhist) {
        List<OrderDetail> orderdetList=new ArrayList<OrderDetail>();
        for(OrderDetail orderdet: orderhist.getOrderdets() )
        {
            orderdetList.add(orderdet);
        }
        return orderHistoryService.insertTcHistory(orderhist,orderdetList);
	}


	/*
	 * 向Agent历史表插入数据
	 */
	private void insertAgentHistory(Order orderhist, List<OrderDetail> orderdetList) throws Exception {
		//插入订单历史
		AddressExt address = orderhist.getAddress();
    	OrderhistHist orderhistHist = new OrderhistHist();
    	BeanUtils.copyProperties(orderhist, orderhistHist, OrderhistHist.class);
    	orderhistHist.setId(null);
    	orderhistHist.setAddressid(address.getAddressId());
        orderhistHist.setHistcrdt(new Date());
    	orderhistHist = orderhistHistDao.save(orderhistHist);

    	//插入订单详细历史
    	for(OrderDetail od : orderdetList){
			OrderdetHist orderdetHist = new OrderdetHist();
			BeanUtils.copyProperties(od, orderdetHist, OrderdetHist.class);
			orderdetHist.setId(null);
			orderdetHistDao.save(orderdetHist);
		}
	}

	/*
	* <p>Title: 更新订单索权号</p>
	* <p>Description: </p>
	* @param orderid
	* @param cardrightnum
	* @throws Exception
	* @see com.chinadrtv.erp.tc.service.OrderhistService#updateOrderRightNum(java.lang.String, java.lang.String)
	*/
	public int updateOrderRightNum(Order orderhist) throws Exception {
		/**
		 * 提供信用卡支付订单更新订单索号功能。
			1、判断订单是否为信用卡订单且是否出库，如果信用卡订单且未出库，则进入步骤2。
			2、更新订单索权号信息。
			3、生成订单快照历史。

			输入：订单ID，索权号
			输出：更新或者失败，失败，返回错误详细信息。
		 */
		String orderId = orderhist.getOrderid();
		String cardrightnum = orderhist.getCardrightnum();
		String modifyUser = orderhist.getMdusr();

		orderhist = this.getOrderHistByOrderid(orderId);
		String payType = orderhist.getPaytype();
		String customizeStatus = orderhist.getCustomizestatus();

		//如果不是信用卡支付
		if(null==payType ||  !payType.trim().equals(OrderCode.PAY_TYPE_CREDITCARD)){
			return -1;
		}
		//如果已出库
		boolean isOut = this.judgeOrderStatus(customizeStatus);
		if(isOut){
			return -2;
		}

		//Step2、更新索权号
		orderhist.setCardrightnum(cardrightnum);
		orderhist.setConfirm("-1");
		orderhist.setMdusr(modifyUser);
		orderhist.setMddt(new Date());

		orderhistDao.saveOrUpdate(orderhist);

		//Step3、插入快照表
		orderHistoryService.insertTcHistory(orderhist);

		return 0;
	}

	/*
	* <p>Title: 更新订单子订单号 </p>
	* <p>Description: </p>
	* @param orderhist
	* @throws Exception
	* @see com.chinadrtv.erp.tc.service.OrderhistService#updateOrderChild(com.chinadrtv.erp.model.agent.Order)
	*/
	public int updateOrderChild(Order orderhist) throws Exception {
		/**
		 * 提供订单更新子订单功能，用于物流配送。
			1、判断订单是是否出库，如果出库，则进入步骤2。
			2、更新订单子订单号。
			3、生成订单快照历史。
			输入：订单ID 子订单ID
			输出：更新或者失败，失败，返回错误详细信息。
		 */

		String childOrderId = orderhist.getChildid();
		String modifyUser = orderhist.getMdusr();

		//父订单
		orderhist = this.getOrderHistByOrderid(orderhist.getOrderid());
		if(null == orderhist){
			return -2;
		}

		//子订单
		Order childOrderhist = this.getOrderHistByOrderid(childOrderId);
		if(null == childOrderhist){
			throw new BizException("不存在单号为["+childOrderId+"]的子订单");
		}

		String customizeStatus = orderhist.getCustomizestatus();

		//如果未出库
		boolean isOut = this.judgeOrderStatus(customizeStatus);
		if(!isOut){
			return -1;
		}

		//保存子订单
		orderhist.setChildid(childOrderId);
		orderhist.setMddt(new Date());
		orderhist.setMdusr(modifyUser);
		orderhistDao.saveOrUpdate(orderhist);

		//保存父订单
		childOrderhist.setParentid(orderhist.getOrderid());
		childOrderhist.setMddt(new Date());
		childOrderhist.setMdusr(modifyUser);
		orderhistDao.saveOrUpdate(childOrderhist);

		//插入父订单快照
		orderHistoryService.insertTcHistory(orderhist);
		//插入子订单快照
		orderHistoryService.insertTcHistory(childOrderhist);

		return 0;
	}

	/*
	* <p>Title: 订单取消分拣状态</p>
	* <p>Description: </p>
	* @param orderhist
	* @return
	* @throws Exception
	* @see com.chinadrtv.erp.tc.service.OrderhistService#cancelOrderSort(com.chinadrtv.erp.model.agent.Order)
	*/
	public int cancelSortStatus(Map<String, Object> params) throws Exception {
		/**
		 * 提供订单取消分拣状态功能（未来可以包括到OMS运单功能）。
			1、判断订单是是否出库，如果未出库，则进入步骤2。
			2、更新订单的送货公司（entityid）为空，状态（status）为1，分拣时间（senddt）为空，送货单号 (parcelnm) 为空，修改人为当前修改人，修改日期为当前时间。
			3、生成订单快照历史。
			4、操作发运单（取消）。

			sSql = "update orderhist set entityid='',status='1', senddt='',parcelnm='',mdusr=:mdusr,mddt=:mddt where orderid=:orderid"

			输入：订单ID 修改人
			输出：更新或者失败，失败，返回错误详细信息。
		 */
		String orderId = params.get("orderid").toString();
		String modifyUser = params.get("mdusr").toString();
		String entityId = params.get("companyid").toString();
		Order orderhist = this.getOrderHistByOrderid(orderId);
		if(null == orderhist){
			return -2;
		}

		String customizeStatus = orderhist.getCustomizestatus();
		//Step1、如果已出库
		boolean isOut = this.judgeOrderStatus(customizeStatus);
		if(isOut){
			return -1;
		}

		//Step2
		orderhist.setIsassign(null);
		orderhist.setEntityid(entityId);
		orderhist.setStatus(AccountStatusConstants.ACCOUNT_NEW);
		orderhist.setSenddt(null);
		orderhist.setParcelnm("");
		orderhist.setMdusr(modifyUser);
		orderhist.setMddt(new Date());
		Integer revision = orderhist.getRevision();
		revision ++;
		orderhist.setRevision(revision);
		orderhistDao.saveOrUpdate(orderhist);

		//Step3
		OrderHistory orderHistory = orderHistoryService.insertTcHistory(orderhist);

		//Step4
		Company company = companyService.findCompany(entityId);
		if(null==company || null==company.getWarehouseId()){
			throw new BizException("无效的送货公司");
		}

		//取消运单
		shipmentHeaderService.cancelShipmentHeader(orderhist, modifyUser);

		//生成运单
		ShipmentHeader sh = shipmentHeaderService.generateShipmentHeader(orderhist, entityId, company.getWarehouseId().toString());
		sh.setOrderHistory(orderHistory);
		shipmentHeaderService.addOrUpdateShipmentHeader(sh);

		return 0;
	}


	/*
	* <p>Title: 修改订单订购类型</p>
	* <p>Description: </p>
	* @param orderhist
	* @return
	* @throws Exception
	* @see com.chinadrtv.erp.tc.service.OrderhistService#updateOrderType(com.chinadrtv.erp.model.agent.Order)
	*/
	public int updateOrderType(Order orderhist) throws Exception {
		/**
		 *	提供订单订购类型修改功能。
			1、判断订单是是否出库，如果未出库，则进入步骤2。
			2、更新订单类型。
			3、生成订单快照历史。
			输入：订单ID  订单类型 修改人
		            输出：更新或者失败，失败，返回错误详细信息。
		 */

		//订购方式
		String mailtype = orderhist.getMailtype();
		String modifyUser = orderhist.getMdusr();

		orderhist = this.getOrderHistByOrderid(orderhist.getOrderid());
		if(null == orderhist){
			return -2;
		}

		String customizeStatus = orderhist.getCustomizestatus();

		//Step1、如果已出库
		boolean isOut = this.judgeOrderStatus(customizeStatus);
		if(isOut){
			return -1;
		}

		//Step4
		orderhist.setMailtype(mailtype);
		orderhist.setMdusr(modifyUser);
		orderhist.setMddt(new Date());
		orderhistDao.saveOrUpdate(orderhist);

		//Step3
		orderHistoryService.insertTcHistory(orderhist);

		return 0;
	}


	/*
	* <p>Title: 更新订购方式和送货公司</p>
	* <p>Description: </p>
	* @param orderhist
	* @return
	* @throws Exception
	* @see com.chinadrtv.erp.tc.service.OrderhistService#updateOrderType(com.chinadrtv.erp.model.agent.Order)
	*/
	public int updateOrderTypeAndDelivery(Order orderhist) throws Exception {
		/**
		 *改送货地址后，更新订购方式和送货公司。（未来OMS处理）
			1、判断订单是否出库，如果未出库，则进入步骤2。
			2、更新订单的订购方式、送货公司。
			3、生成订单快照历史。
			4、生成发运单。
			输入：订单ID  mdusr, companyId, spellId, provinceId, cityId
		            输出：更新或者失败，失败，返回错误详细信息。
		 */

		String companyId = orderhist.getCompanyid();
		Integer spellId = orderhist.getSpellid();
		String provinceId = orderhist.getProvinceid();
		String cityId = orderhist.getCityid();
		String modifyUser = orderhist.getMdusr();

		orderhist = this.getOrderHistByOrderid(orderhist.getOrderid());
		if(null == orderhist){
			return -2;
		}

		String customizeStatus = orderhist.getCustomizestatus();

		//Step1、如果已出库
		boolean isOut = this.judgeOrderStatus(customizeStatus);
		if(isOut){
			return -1;
		}

		//Step2
		orderhist.setCompanyid(companyId);
		orderhist.setSpellid(spellId);
		orderhist.setProvinceid(provinceId);
		orderhist.setCityid(cityId);
		orderhist.setMdusr(modifyUser);
		orderhist.setMddt(new Date());
		Integer revision = orderhist.getRevision() + 1;
		orderhist.setRevision(revision);
		orderhistDao.saveOrUpdate(orderhist);

		//Step3
		OrderHistory orderHistory = orderHistoryService.insertTcHistory(orderhist);

		//Step4
		shipmentHeaderService.cancelShipmentHeader(orderhist, modifyUser);

		ShipmentHeader sh = shipmentHeaderService.generateShipmentHeader(orderhist, false);
		sh.setOrderHistory(orderHistory);
		shipmentHeaderService.addOrUpdateShipmentHeader(sh);

		return 0;
	}


	/*
	 * 修改订单的收货人
	* <p>Title: 修改订单的收货人</p>
	* <p>Description: </p>
	* @param orderhist
	* @return Integer
	* @throws Exception
	* @see com.chinadrtv.erp.tc.service.OrderhistService#updateOrderConsignee(com.chinadrtv.erp.model.agent.Order)
	*/
	public int updateOrderConsignee(Map<String, Object> params) throws Exception {
		/**
		 * 提供订单修改收货人/付款人信息(二期TC处理订单收货人信息)。
			1、判断订单是是否出库，如果未出库，则进入步骤2。
			2、更新订单发货人/收货人信息。
			3、生成订单快照历史。
			4、生成发运单。
			输入：订单ID  收货人/发货人  修改人
		            输出：更新或者失败，失败，返回错误详细信息。
		 */

		String orderId = params.get("orderid").toString();
		//收款人
		String getContactId = params.get("getcontactid").toString();
		String addressId = params.get("addressid").toString();
		String modifyUser = params.get("mdusr").toString();

		Order orderhist = this.getOrderHistByOrderid(orderId);
		if(null==orderhist){
			return -2;
		}
		String customizeStatus = orderhist.getCustomizestatus();

		//Step1、如果已出库
		boolean isOut = this.judgeOrderStatus(customizeStatus);
		if(isOut){
			return -1;
		}

		//Step2
		orderhist.setGetcontactid(getContactId);
		AddressExt addressExt = addressExtService.getAddressExt(addressId);
		if(null==addressExt){
			throw new BizException("无效的联系人信息");
		}
		orderhist.setAddress(addressExt);
		orderhist.setMdusr(modifyUser);
		orderhist.setMddt(new Date());
		Integer revision = orderhist.getRevision();
		orderhist.setRevision(revision + 1);
		orderhistDao.saveOrUpdate(orderhist);

		//Step3
		OrderHistory orderHistory =orderHistoryService.insertTcHistory(orderhist);

		//Step4
		shipmentHeaderService.cancelShipmentHeader(orderhist, modifyUser);

		ShipmentHeader sh = shipmentHeaderService.generateShipmentHeader(orderhist, false);
		sh.setOrderHistory(orderHistory);
		shipmentHeaderService.addOrUpdateShipmentHeader(sh);

		return 0;
	}

	/*
	 * 修改订单的付款人
	* <p>Title: 修改订单的付款人</p>
	* <p>Description: </p>
	* @param orderhist
	* @return Integer
	* @throws Exception
	* @see com.chinadrtv.erp.tc.service.OrderhistService#updateOrderPayer(com.chinadrtv.erp.model.agent.Order)
	*/
	public int updateOrderPayer(Order orderhist) throws Exception {
		/**
		 * 提供订单修改收货人/付款人信息(二期TC处理订单收货人信息)。
			1、判断订单是是否出库，如果未出库，则进入步骤2。
			2、更新订单发货人/收货人信息。
			3、生成订单快照历史。
			4、生成发运单。
			输入：订单ID  收货人/发货人  修改人
		            输出：更新或者失败，失败，返回错误详细信息。
		 */

		String orderId = orderhist.getOrderid();
		//付款人
		String payContactId = orderhist.getPaycontactid();
		String modifyUser = orderhist.getMdusr();

		orderhist = this.getOrderHistByOrderid(orderId);
		if(null==orderhist){
			return -2;
		}
		String customizeStatus = orderhist.getCustomizestatus();

		//Step1、如果已出库
		boolean isOut = this.judgeOrderStatus(customizeStatus);
		if(isOut){
			return -1;
		}

		//Step2
		Integer revision = orderhist.getRevision();
		orderhist.setPaycontactid(payContactId);
		orderhist.setMdusr(modifyUser);
		orderhist.setMddt(new Date());
		orderhist.setRevision(revision+1);
		orderhistDao.saveOrUpdate(orderhist);

		//Step3
		OrderHistory orderHistory = orderHistoryService.insertTcHistory(orderhist);

		//Step4
		shipmentHeaderService.cancelShipmentHeader(orderhist, modifyUser);

		ShipmentHeader sh = shipmentHeaderService.generateShipmentHeader(orderhist, false);
		sh.setOrderHistory(orderHistory);
		shipmentHeaderService.addOrUpdateShipmentHeader(sh);

		return 0;
	}


	/*
	 * 订单回访取消订单
	* <p>Title: 订单回访取消订单</p>
	* <p>Description: </p>
	* @param orderhist
	* @return Integer
	* @throws Exception
	* @see com.chinadrtv.erp.tc.service.OrderhistService#callbackCancleorder(com.chinadrtv.erp.model.agent.Order)
	*/
	public int callbackCancelOrder(Order orderhist) throws Exception {
		/**
		 * 用于订单回访取消订单时，更新订单主表及明细表信息，以及积分返券。
			1、	通过orderid更新订单主表信息(status='0',result='0',fbdt=sysdate,mddt=sysdate,mdusr=:mdusr,nowmoney=0,clearfee=0)
			2、	通过orderid更新明细表信息(status = '0',fbdt=sysdate,mdusr=:mdusr)
			3、	更新积分信息(p_n_conpointfeedback)
			4、	更新返券信息(p_n_conticketfeedback)

			输入：修改人(mdusr) , 订单ID[orderid]
		 	输出：更新或者失败，失败，返回错误详细信息。
		 */

		String orderId = orderhist.getOrderid();
		String modifyUser = orderhist.getMdusr();

		orderhist = this.getOrderHistByOrderid(orderId);
		if(null == orderhist){
			throw new BizException("订单["+orderId+"]不存在");
		}

		//Step1
		orderhist.setStatus("0");
		orderhist.setResult("0");
		orderhist.setFbdt(new Date());
		orderhist.setMddt(new Date());
		orderhist.setMdusr(modifyUser);
		orderhist.setNowmoney(new BigDecimal(0));
		orderhist.setClearfee(new BigDecimal(0));

		for(OrderDetail od : orderhist.getOrderdets()){
			od.setOrderhist(orderhist);
			od.setStatus("0");
			od.setFbdt(new Date());
			od.setMdusr(modifyUser);
			orderdetDao.saveOrUpdate(od);
		}
        orderhistDao.saveOrUpdate(orderhist);

		//插入历史
		orderHistoryService.insertTcHistory(orderhist);

        //此处一定要先FLUSH，要不然存储过程业务逻辑不能正常走下去
        sessionFactory.getCurrentSession().flush();

		//Step3
		Map<String,Object> pvParam=new HashMap<String,Object>();
		pvParam.put("sorderid", orderhist.getOrderid());
		pvParam.put("scrusr", modifyUser);
		pvService.getJifenproc(pvParam);

		//Step4
		Map<String,Object> couponParam=new HashMap<String,Object>();
		couponParam.put("sorderid", orderhist.getOrderid());
		couponParam.put("scrusr", modifyUser);
		disHuifangMesService.couponReviseproc(couponParam);

		return 0;
	}

	/*
	 * 订单回访保存时更新订单信息
	* <p>Title: 订单回访保存时更新订单信息</p>
	* <p>Description: </p>
	* @param orderhist
	* @return Integer
	* @throws Exception
	* @see com.chinadrtv.erp.tc.service.OrderhistService#callbackUpdateOrder(com.chinadrtv.erp.model.agent.Order)
	*/
	public int callbackUpdateOrder(Order orderhist) throws Exception {
		/**
		 * 	1、通过orderid更新订单主表信息
			2、	更新remark，errcode，adusr
			3、保存订单快照历史
			输入：回访结果(remark), 审核错误项(errcode), 核单员(adusr), 核单日期(addt), 订单ID[orderid]
    		输出：更新或者失败，失败，返回错误详细信息。
		 */

		String orderId = orderhist.getOrderid();
		String remark = orderhist.getRemark();
		String errcode = orderhist.getErrcode();
		String adUser = orderhist.getAdusr();
		String modifyUser = orderhist.getMdusr();
		Date adDate = orderhist.getAddt();

		orderhist = this.getOrderHistByOrderid(orderId);
		if(null==orderhist){
			throw new BizException("订单["+orderId+"]不存在");
		}

		//Step2
		orderhist.setRemark(remark);
		orderhist.setErrcode(errcode);
		orderhist.setAdusr(adUser);
		orderhist.setAddt(adDate);
		orderhist.setMddt(new Date());
		orderhist.setMdusr(modifyUser);
		orderhistDao.saveOrUpdate(orderhist);

		//Step3
		orderHistoryService.insertTcHistory(orderhist);

		return 0;
	}

	/*
	 * SR3.5_1.5更新订单明细
	* <p>Title: 更新订单明细</p>
	* <p>Description: </p>
	* @param orderhist
	* @return Integer
	* @throws Exception
	* @see com.chinadrtv.erp.tc.service.OrderhistService#updateOrderDetail(com.chinadrtv.erp.model.agent.Order)
	*/
	public int updateOrderDetail(OrderDetail orderdet) throws Exception {
		/**
		 * 更新orderdet表的，PROVINCEID，STATE，CITY三个字段。
		         传入参数：PROVINCEID，STATE，CITY，ORDERID.
		         插入TC History
		 */
		String orderId = orderdet.getOrderid();
		String provinceId = orderdet.getProvinceid();
		String state = orderdet.getState();
		String city = orderdet.getCity();
		String mdusr = orderdet.getMdusr();

		Order orderhist = this.getOrderHistByOrderid(orderId);
		if(null==orderhist){
			throw new BizException("订单["+orderId+"] 不存在");
		}

		orderhist.setMddt(new Date());
		orderhist.setMdusr(mdusr);
		for(OrderDetail od : orderhist.getOrderdets()){
			od.setOrderhist(orderhist);

			od.setProvinceid(provinceId);
			od.setState(state);
			od.setCity(city);
			od.setMdusr(mdusr);
			od.setLastUpdateTime(new Date());
		}
		orderhistDao.saveOrUpdate(orderhist);

		orderHistoryService.insertTcHistory(orderhist);

		return 0;
	}


	/*
	 * 获取非套装产品信息
	* <p>Title: queryProdNonSuiteInfo</p>
	* <p>Description: </p>
	* @param orderdet
	* @return Map<String, Object>
	* @throws Exception
	* @see com.chinadrtv.erp.tc.service.OrderhistService#queryProdNonSuiteInfo(com.chinadrtv.erp.model.agent.OrderDetail)
	*/
	public Map<String, Object> queryProdNonSuiteInfo(OrderDetail orderdet){
		return orderhistDao.queryProdNonSuiteInfo(orderdet);
	}

    /**
     * 更改订单运费
     * @param params
     * @throws Exception
     */
	public void updateOrderFreight(Map<String, Object> params) throws Exception {

		String orderId = params.get("orderid").toString();
		String mdusr = params.get("mdusr").toString();
		BigDecimal freight = new BigDecimal(Double.parseDouble(params.get("mailprice").toString()));

		Order order = orderhistDao.getOrderHistByOrderid(orderId);
		order.setMailprice(freight);
		BigDecimal totalPrice = order.getProdprice().add(freight);
		order.setTotalprice(totalPrice);
		order.setMdusr(mdusr);
		Integer revision = order.getRevision() + 1;
		order.setRevision(revision);
		orderhistDao.saveOrUpdate(order);
		
		//是否有搭销组合
		boolean hasMainSaleProd = hasMainSaleProd(order);

		//如果有搭销组合，将运费写在主产品上面
		if(hasMainSaleProd){
			for(OrderDetail od : order.getOrderdets()){
				if(od.getSoldwith().equals("1")){
					od.setFreight(freight);
					od.setMdusr(mdusr);
				}else{
					od.setFreight(new BigDecimal(0));
					od.setMdusr(mdusr);
				}
				orderdetDao.saveOrUpdate(od);
			}
		//如果没有搭销组合，将运费写在第一个订单明细上面
		}else{
			OrderDetail[] orderDetails = (OrderDetail[]) order.getOrderdets().toArray();
			for(int i=0; i<orderDetails.length; i++){
				OrderDetail od = orderDetails[i];
				if(i==0){
					od.setFreight(freight);
					od.setMdusr(mdusr);
				}else{
					od.setFreight(new BigDecimal(0));
					od.setMdusr(mdusr);
				}
				orderdetDao.saveOrUpdate(od);
			}
		}

		//插入历史
		OrderHistory oh = this.insertTcHistory(order);

		//取消旧运单
		ShipmentHeader shipmentHeaderPre = shipmentHeaderService.cancelShipmentHeader(order, mdusr);

        //生成新运单
        ShipmentHeader newSh = shipmentHeaderService.generateShipmentHeader(order, false);

		//分配库存
        inventoryAgentService.reserveInventory(order, shipmentHeaderPre, newSh.getWarehouseId(), mdusr);

		newSh.setOrderHistory(oh);
		shipmentHeaderService.addOrUpdateShipmentHeader(newSh);
		
	}

    /**
	 * <p>判断订单是否存在搭销产品</p>
	 * @param order
	 * @return Boolean
	 */
	private boolean hasMainSaleProd(Order order) {
		for(OrderDetail od : order.getOrderdets()){
			if(od.getSoldwith().equals("1")){
				return true;
			}
		}
		return false;
	}














    public void autoChooseOrder(OrderAutoChooseInfo orderAutoChooseInfo)
    {
        //检查状态
        if(orderAutoChooseInfo==null)
        {
            OrderReturnCode orderReturnCode=new OrderReturnCode(OrderCode.FIELD_INVALID,"没有输入参数");
            throw new OrderException(orderReturnCode);
        }

        if(orderAutoChooseInfo.getId()==null&&StringUtils.isEmpty(orderAutoChooseInfo.getOrderId()))
        {
            OrderReturnCode orderReturnCode=new OrderReturnCode(OrderCode.FIELD_INVALID,"没有提供订单号");
            throw new OrderException(orderReturnCode);
        }

        //获取订单
        Order order=null;
        if(orderAutoChooseInfo.getId()!=null)
        {
            order=orderhistDao.get(orderAutoChooseInfo.getId());
        }
        else
        {
            order=orderhistDao.getOrderHistByOrderid(orderAutoChooseInfo.getOrderId());
        }
        if(order==null)
        {
            OrderReturnCode orderReturnCode=new OrderReturnCode(OrderCode.ORDER_NOT_FOUND,"没有对应订单");
            throw new OrderException(orderReturnCode);
        }

        if(!AccountStatusConstants.ACCOUNT_NEW.equals(order.getStatus()))
        {
            OrderReturnCode orderReturnCode=new OrderReturnCode(OrderCode.ORDER_STATUS_NOT_FOR_TRANS,"只有新订单才能分拣");
            throw new OrderException(orderReturnCode);
        }
        if(orderAutoChooseInfo.getVersion()!=null)
        {
            if(!orderAutoChooseInfo.getVersion().equals(order.getVersion()))
            {
                OrderReturnCode orderReturnCode=new OrderReturnCode(OrderCode.ORDER_HAVE_MODIFY,"订单已发生修改");
                throw new OrderException(orderReturnCode);
            }
        }

        //分不同情况处理
        ShipmentHeader shipmentHeader=shipmentHeaderService.getShipmentHeaderFromOrderId(order.getOrderid());
        if(shipmentHeader!=null)
        {
            if(!AccountStatusConstants.ACCOUNT_NEW.equals(shipmentHeader.getAccountStatusId()))
            {
                OrderReturnCode orderReturnCode=new OrderReturnCode(OrderCode.ORDER_SHIPMENT_STATUS_INVALID,"运单状态不正确");
                throw new OrderException(orderReturnCode);
            }
        }
        //1.承运商不变
        Long orderEntityId=-1L;
        if(StringUtils.isNotEmpty(order.getEntityid()))
        {
            orderEntityId=Long.parseLong(order.getEntityid());
        }
        if(orderAutoChooseInfo.getEntityId()==null||orderAutoChooseInfo.getEntityId().equals(orderEntityId))
        {
            order.setSenddt(new Date());
            order.setStatus(AccountStatusConstants.ACCOUNT_TRANS);
            orderhistDao.saveOrUpdate(order);
            OrderHistory orderHistory = orderHistoryService.insertTcHistory(order);
            if(shipmentHeader!=null)
            {
                //直接改变发运单状态
                shipmentHeader.setAccountStatusId(AccountStatusConstants.ACCOUNT_TRANS);
                //new code start
                shipmentHeader.setSenddt(new Date());
                //new code end
                shipmentHeaderService.updateShipmentHeader(shipmentHeader);
            }
        }
        //2.承运商改变
        else
        {
            if(shipmentHeader == null)
            {
                order.setEntityid(orderAutoChooseInfo.getEntityId().toString());
                order.setSenddt(new Date());
                order.setStatus(AccountStatusConstants.ACCOUNT_TRANS);
                orderhistDao.saveOrUpdate(order);
                OrderHistory orderHistory = orderHistoryService.insertTcHistory(order);
            }
            else
            {
                //仓库必须提供
                if(orderAutoChooseInfo.getWarehouseId()==null)
                {
                    OrderReturnCode orderReturnCode=new OrderReturnCode(OrderCode.FIELD_INVALID,"更改承运商时必须提供仓库信息");
                    throw new OrderException(orderReturnCode);
                }

                //取消原先的运单
                ShipmentHeader newShipmentHeader=null;
                ShipmentHeader shipmentHeaderPre = shipmentHeaderService.cancelShipmentHeader(order, order.getMdusr());
                //拷贝数据
                Order orderhistClone = new Order();
                //BeanUtils.copyProperties(orgOrderhist,orderhistClone);
                OrderhistUtil.copy(orderhistClone, order);
                orderhistClone.setRevision(orderhistClone.getRevision()+1);
                orderhistClone.setEntityid(orderAutoChooseInfo.getEntityId().toString());

                try
                {
                    newShipmentHeader=shipmentHeaderService.generateShipmentHeader(orderhistClone,orderAutoChooseInfo.getEntityId().toString(),orderAutoChooseInfo.getWarehouseId().toString());
                    inventoryAgentService.reserveInventory(orderhistClone,shipmentHeaderPre, orderAutoChooseInfo.getWarehouseId().toString(),orderAutoChooseInfo.getMdusr());
                }catch (Exception exp)
                {
                    logger.error("order auto choose generateShipmentHeader error:", exp);
                    throw new RuntimeException(exp.getMessage());
                }
                order.setEntityid(orderAutoChooseInfo.getEntityId().toString());
                order.setSenddt(new Date());
                order.setStatus(AccountStatusConstants.ACCOUNT_TRANS);
                order.setRevision(orderhistClone.getRevision());

                OrderHistory orderHistory = orderHistoryService.insertTcHistory(order);
                if(newShipmentHeader!=null)
                {
                    newShipmentHeader.setOrderHistory(orderHistory);
                    newShipmentHeader.setAccountStatusId(AccountStatusConstants.ACCOUNT_TRANS);
                    //new code start
                    newShipmentHeader.setSenddt(new Date());
                    //new code end
                    try {
                        shipmentHeaderService.addShipmentHeader(newShipmentHeader);
                    } catch (Exception exp) {
                        logger.error("upate order addShipmentHeader error:", exp);
                        throw new RuntimeException(exp.getMessage());
                    }
                }
            }
        }
    }




    /*public OrderDto queryOrder(String orderId)
    {
        Order order = this.orderhistDao.getOrderHistByOrderid(orderId);
        if(order==null)
        {
            throw new OrderException(new OrderReturnCode(OrderCode.ORDER_NOT_FOUND, ""));
        }

        OrderDto orderDto=new OrderDto();
        BeanUtils.copyProperties(order,orderDto);
        ShipmentHeader shipmentHeader = shipmentHeaderService.getShipmentHeaderFromOrderId(orderId);
        if(shipmentHeader!=null)
        {
            orderDto.setShipmentId(shipmentHeader.getShipmentId());
        }
        return orderDto;
    }*/



















    //新增加功能函数(sales TC API)
    @Value("${com.chinadrtv.erp.sales.core.OrderQueryDays}")
    private Long orderQueryDays;

    @Autowired
    private OrderhistTrackTaskDao orderhistTrackTaskDao;

    /**
     * 查询订单
     * @param orderQueryDto
     * @return
     */
    public List<OrderExtDto> queryOrder(OrderQueryDto orderQueryDto) throws ErpException{
        //首先查找订单
        /*if (StringUtils.isNotEmpty(orderQueryDto.getShipmentId())) {
            ShipmentHeader shipmentHeader = shipmentHeaderDao.getByShipmentId(orderQueryDto.getShipmentId());
            if (shipmentHeader == null)
                return new ArrayList<OrderExtDto>();
            if (StringUtils.isNotEmpty(orderQueryDto.getOrderId())) {
                if (!orderQueryDto.getOrderId().equals(orderQueryDto.getShipmentId())) {
                    return new ArrayList<OrderExtDto>();
                }
            } else {
                orderQueryDto.setOrderId(shipmentHeader.getOrderId());
            }
        }*/
        if(StringUtils.isNotEmpty(orderQueryDto.getMailId()))
        {
            orderQueryDto.setMailId(orderQueryDto.getMailId().toUpperCase());
        }

        if(orderQueryDto.isCheckCrDate()==null||orderQueryDto.isCheckCrDate().booleanValue()==true)
        {
            if(orderQueryDto.getBeginCrdt()==null)
            {
                if(StringUtils.isEmpty(orderQueryDto.getOrderId()))
                {
                String errorMsg="查询订单起始日期必须提供";
                logger.error(errorMsg);
                throw new ServiceParameterException(ExceptionConstant.SERVICE_ORDER_QUERY_EXCEPTION,errorMsg);
                }
            }
            else
            {
                Date endDate=orderQueryDto.getEndCrdt();
                if(endDate==null)
                    endDate=new Date();
               if(DateUtil.calcDiffJustDays(endDate,orderQueryDto.getBeginCrdt()).compareTo(orderQueryDays)>0)
               {
                   String errorMsg="查询订单日期应该在";
                   errorMsg+=orderQueryDays;
                   errorMsg+="天内";
                   logger.error(errorMsg);
                   throw new ServiceParameterException(ExceptionConstant.SERVICE_ORDER_QUERY_EXCEPTION,errorMsg);
               }
            }
        }

        try
        {
            if(orderQueryDto.getContactIdList()==null)
                orderQueryDto.setContactIdList(new ArrayList<String>());
            if (orderQueryDto.getContactId() != null) {
                orderQueryDto.getContactIdList().add(orderQueryDto.getContactId());
            } else if (StringUtils.isNotEmpty(orderQueryDto.getContactName())) {
                List<Contact> contactList = queryLastContactsByName(orderQueryDto.getContactName()); //contactDao.findList("from Contact where name=:name", new ParameterString("name", orderQueryDto.getContactName()));
                if (contactList != null && contactList.size() > 0) {
                    for (Contact contact : contactList)
                    {
                        if(!orderQueryDto.getContactIdList().contains(contact.getContactid()))
                            orderQueryDto.getContactIdList().add(contact.getContactid());
                    }
                }
                else
                {
                    return new ArrayList<OrderExtDto>();
                }
            }

            if(orderQueryDto.getGetContactIdList()==null)
                orderQueryDto.setGetContactIdList(new ArrayList<String>());
            if(StringUtils.isNotEmpty(orderQueryDto.getGetContactName())) {
                List<Contact> contactList = queryLastContactsByName(orderQueryDto.getGetContactName());//contactDao.findList("from Contact where name=:name", new ParameterString("name", orderQueryDto.getGetContactName()));
                if (contactList != null && contactList.size() > 0) {
                    for (Contact contact : contactList)
                    {
                        if(!orderQueryDto.getGetContactIdList().contains(contact.getContactid()))
                            orderQueryDto.getGetContactIdList().add(contact.getContactid());
                    }
                }
                else
                {
                    return new ArrayList<OrderExtDto>();
                }
            }

            List<Order> orderList = orderhistDao.queryOrder(orderQueryDto);

            //然后查找相关运单
            List<OrderExtDto> orderExtDtoList = new ArrayList<OrderExtDto>();
            if (orderList != null && orderList.size() > 0) {
                Map<String, Long> mapOrder = new HashMap<String, Long>();
                for (Order order : orderList) {
                    mapOrder.put(order.getOrderid(), new Long(order.getRevision().toString()));
                }
                List<ShipmentHeader> shipmentHeaderList = shipmentHeaderDao.queryShipmentFromOrderIds(mapOrder);
                for (Order order : orderList) {
                    OrderExtDto orderExtDto = new OrderExtDto();
                    orderExtDto.setOrder(order);
                    if (shipmentHeaderList != null) {
                        for (ShipmentHeader shipmentHeader : shipmentHeaderList) {
                            if (order.getOrderid().equals(shipmentHeader.getOrderId())) {
                                orderExtDto.setShipmentHeader(shipmentHeader);
                                break;
                            }
                        }
                    }
                    orderExtDtoList.add(orderExtDto);
                }
            }


            //获取跟单备注
            List<String> trackOrderIdList=new ArrayList<String>();
            for(OrderExtDto orderExtDto:orderExtDtoList)
            {
                if(StringUtils.isNotEmpty(orderExtDto.getOrder().getTrackUsr()))
                {
                    trackOrderIdList.add(orderExtDto.getOrder().getOrderid());
                }
            }
            if(trackOrderIdList.size()>0)
            {
                List<OrderhistTrackTask> orderhistTrackTaskList = orderhistTrackTaskDao.queryByOrderIds(trackOrderIdList);
                if(orderhistTrackTaskList!=null)
                {
                    for(OrderExtDto orderExtDto:orderExtDtoList)
                    {
                        if(StringUtils.isNotEmpty(orderExtDto.getOrder().getTrackUsr()))
                        {
                            for(OrderhistTrackTask orderhistTrackTask:orderhistTrackTaskList)
                            {
                                 if(orderExtDto.getOrder().getOrderid().equals(orderhistTrackTask.getOrderId()))
                                 {
                                     orderExtDto.setOrderhistTrackTask(orderhistTrackTask);
                                     break;
                                 }
                            }
                        }
                    }
                }
            }

            return orderExtDtoList;
        }catch (RuntimeException runtimeExp)
        {
            logger.error("queryOrder error:",runtimeExp);
            throw new ServiceException(ExceptionConstant.SERVICE_ORDER_QUERY_EXCEPTION, ExceptionMsgUtil.getMessage(runtimeExp),runtimeExp);
        }
    }

    @Value("${order_contact_searchbyname_size:100}")
    private Integer contact_pageSize;

    private List<Contact> queryLastContactsByName(String name)
    {
        Map<String, Parameter> parameters=new Hashtable<String, Parameter>();
        parameters.put("name",new ParameterString("name", name));
        ParameterInteger paramStartPos=new ParameterInteger(Page.PARAM_PAGE,0);
        paramStartPos.setForPageQuery(true);
        parameters.put(Page.PARAM_PAGE, paramStartPos);

        ParameterInteger paramPageSize=new ParameterInteger(Page.PARAM_PAGE_SIZE, contact_pageSize); //TODO:
        paramPageSize.setForPageQuery(true);
        parameters.put(Page.PARAM_PAGE_SIZE, paramPageSize);

        Map<String, Criteria> criterias=new Hashtable<String, Criteria>();
        return contactDao.findPageList("from Contact where name=:name order by crdt desc", parameters, criterias);

    }

    public int queryOrderTotalCount(OrderQueryDto orderQueryDto) throws ErpException
    {
        OrderQueryDto orderQueryDto1=new OrderQueryDto();
        BeanUtils.copyProperties(orderQueryDto,orderQueryDto1);
        orderQueryDto1.setSortCrDate(null);
        return orderhistDao.getTotalCount(orderQueryDto1);
    }

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    /**
     * 保存订单修改请求
     * @param order
     * @param address 未改变可传递NULL
     * @param contact 未改变可传递NULL
     * @param phoneList   未改变可传递NULL
     * @param creditCard    信用卡修改
     * @param identityCard  身份证修改
     * @param correlativeOrderIdList 是否处理相关联的订单
     */
    public Map<String,List<String>> saveOrderModifyRequest(Order order, Address address, Contact contact, List<Phone> phoneList, Card creditCard,  Card identityCard, List<String> correlativeOrderIdList, String remark) throws ErpException{
        try
        {
            if(order==null||(order.getId()==null&&order.getOrderid()==null))
            {
                String errorMsg="保存订单修改时未提供订单信息（订单为null或者Id、OrderId为null）。";
                logger.error(errorMsg);
                throw new ServiceParameterException(ExceptionConstant.SERVICE_ORDER_UPDATE_EXCEPTION,errorMsg);
            }
            /*if(address!=null&&StringUtils.isEmpty(address.getAddressid()))
            {
                String errorMsg="保存订单修改时未提供地址Id。";
                logger.error(errorMsg);
                throw new ServiceParameterException(ExceptionConstant.SERVICE_ORDER_UPDATE_EXCEPTION,errorMsg);
            }*/

            if(contact!=null&&StringUtils.isEmpty(contact.getContactid()))
            {
                String errorMsg="保存订单修改时未提供联系人Id。";
                logger.error(errorMsg);
                throw new ServiceParameterException(ExceptionConstant.SERVICE_ORDER_UPDATE_EXCEPTION,errorMsg);
            }

            //电话可以是新建的
            /*if(phone!=null&&phone.getPhoneid()==null)
            {
                String errorMsg="保存订单修改时未提供电话Id。";
                logger.error(errorMsg);
                throw new ServiceParameterException(ExceptionConstant.SERVICE_ORDER_UPDATE_EXCEPTION,errorMsg);
            } */

            OrderChange orderChange = this.getOrderChange(order, address, contact, phoneList, creditCard, identityCard);
            if (orderChange.getId()==null)
            {
                String errorMsg="保存订单修改时未获取到订单号："+order.getOrderid();
                logger.error(errorMsg);
                throw new ServiceParameterException(ExceptionConstant.SERVICE_ORDER_UPDATE_EXCEPTION,errorMsg);
            }
            Order orderOrg = orderhistDao.get(orderChange.getId());
            if (orderOrg == null)
            {
                String errorMsg="保存订单修改时未找到订单Id："+orderChange.getOrderid();
                logger.error(errorMsg);
                throw new ServiceParameterException(ExceptionConstant.SERVICE_ORDER_UPDATE_EXCEPTION,errorMsg);
            }
            if (!this.canModify(orderOrg))
            {
                String errorMsg="已出库、已取消、已完成或者索权成功，不能修改。订单Id："+orderOrg.getOrderid();
                logger.error(errorMsg);
                throw new ServiceException(ExceptionConstant.SERVICE_ORDER_UPDATE_EXCEPTION,errorMsg);
            }

            List<OrderChange> orderChangeList = new ArrayList<OrderChange>();
            orderChangeList.add(orderChange);

            boolean bCorrelative=false;
            if(correlativeOrderIdList!=null&&correlativeOrderIdList.size()>0)
            {
                bCorrelative=true;
            }
            if (bCorrelative == true) {
                boolean bAddressChange = false;
                if (orderChange.getAddress() != null)
                    bAddressChange = true;
                boolean bConcactChange = false;
                if (orderChange.getGetContactChange() != null)
                    bConcactChange = true;
                List<Order> orderList = checkCorrelativeOrder(orderOrg.getOrderid(), bAddressChange, bConcactChange, false);
                //检查传递进来的订单号是否符合
                List<Order> correlativeOrderList=new ArrayList<Order>();
                for(String correlativeOrderId: correlativeOrderIdList)
                {
                    boolean bFind=false;
                    for(Order order1:orderList)
                    {
                        if(correlativeOrderId.equals(order1.getOrderid()))
                        {
                            correlativeOrderList.add(order1);
                            bFind=true;
                        }
                    }
                    if(bFind==false)
                    {
                        String errorMsg="给定的订单并非和修改订单关联。源订单Id："+orderChange.getId()+"-关联订单Id："+correlativeOrderId;
                        logger.error(errorMsg);
                        throw new ServiceException(ExceptionConstant.SERVICE_ORDER_UPDATE_EXCEPTION,errorMsg);
                    }
                }
                //如果修改关联处理，那么对关联订单添加新的修改请求
                for (Order order1 : correlativeOrderList) {
                    OrderChange orderChange1 = new OrderChange();
                    orderChange1.setId(order1.getId());
                    orderChange1.setOrderid(order1.getOrderid());
                    orderChange1.setMddt(orderChange.getMddt());
                    orderChange1.setMdusr(orderChange.getMdusr());

                    if (orderChange.getGetContactChange() != null) {
                        if (orderChange.getGetContactChange().getContactid().equals(order1.getGetcontactid())) {
                            ContactChange contactChange = new ContactChange();
                            BeanUtils.copyProperties(orderChange.getGetContactChange(), contactChange);
                            if (orderChange.getGetContactChange().getPhoneChanges() != null) {
                                for (PhoneChange phoneChange : orderChange.getGetContactChange().getPhoneChanges()) {
                                    PhoneChange phoneChange1 = new PhoneChange();
                                    BeanUtils.copyProperties(phoneChange, phoneChange1);

                                    contactChange.getPhoneChanges().add(phoneChange1);
                                    //设置未联系人关联
                                    phoneChange1.setContactid(contactChange.getContactid());
                                   // phoneChange1.setContactChange(contactChange);
                                }
                            }

                            orderChange1.setGetContactChange(contactChange);
                            //设置为订单关联
                            contactChange.setOrderChange(orderChange1);
                        }
                    }
                    if (orderChange.getAddress() != null) {
                        AddressExtChange addressExtChange = new AddressExtChange();
                        BeanUtils.copyProperties(orderChange.getAddress(), addressExtChange);
                        AddressChange addressChange = new AddressChange();
                        BeanUtils.copyProperties(addressExtChange.getAddressChange(), addressChange);
                        addressExtChange.setAddressChange(addressChange);
                        orderChange1.setAddress(addressExtChange);
                    }

                    orderChangeList.add(orderChange1);
                }
            }

            //保存修改信息
            String createUsrId=this.getCurrentUsr();
            if(StringUtils.isEmpty(createUsrId))
            {
                createUsrId=order.getMdusr();
            }

            return saveOrderModifyRequestAndBeginProcs(orderChangeList, AuditTaskType.ORDERCHANGE, remark, createUsrId,false);
        }catch (RuntimeException runtimeExp)
        {
            logger.error("saveOrderModifyRequest error:",runtimeExp);
            throw new ServiceException(ExceptionConstant.SERVICE_ORDER_UPDATE_EXCEPTION,ExceptionMsgUtil.getMessage(runtimeExp),runtimeExp);
        }
    }

    private String getCurrentUsr()
    {
        AgentUser agentUser = SecurityHelper.getLoginUser();
        if(agentUser==null)
        {
            logger.error("login user is null");
            return null;
        }
        else
        {
            return agentUser.getUserId();
        }
    }

    private String getCurrentDepartment()
    {
        AgentUser agentUser = SecurityHelper.getLoginUser();
        if(agentUser==null)
        {
            return "lizhi7";
        }
        else
        {
            return agentUser.getDepartment();
        }
    }

    @Autowired
    private ChangeRequestService changeRequestService;
    @Autowired
    private UserBpmTaskService userBpmTaskService;

    /*private boolean isOrderChanged(List<OrderChangeType> orderChangeRegionList)
    {
        if(orderChangeRegionList==null||orderChangeRegionList.size()==0)
            return false;
        for(OrderChangeType orderChangeType:orderChangeRegionList)
        {
            if(StringUtils.isNotEmpty(orderChangeType.getOrderChangeType())
                    ||orderChangeType.getOrderChange().getAddress()==null
                    ||orderChangeType.getOrderChange().getContactChange()==null)
            {
                return true;
            }
        }
        return false;
    }*/

    @Autowired
    private OrderModifyCorrelationService orderModifyCorrelationService;

    @Autowired
    private OrderChangeService orderChangeService;

    private Map<String,List<String>> saveOrderModifyRequestAndBeginProcs(List<OrderChange> orderChangeList, AuditTaskType auditTaskType, String remark, String createUsrId, boolean isCreating) throws ErpException{
        Map<String,List<String>> map=new Hashtable<String, List<String>>();
        for (OrderChange orderChange : orderChangeList) {
            Map<String,OrderChange> mapChange = decomposeOrderChange(orderChange);
            //如果是创建时，修改运费，那么修改流程类型
            if(isCreating==true)
            {
                String strMailpriceType=String.valueOf(UserBpmTaskType.ORDER_MAILPRICE_CHANGE.getIndex());
                if(mapChange.size()==1&&mapChange.containsKey(strMailpriceType))
                {
                    OrderChange orderChange1=mapChange.get(strMailpriceType);
                    mapChange.clear();
                    mapChange.put(String.valueOf(UserBpmTaskType.ORDER_MAILPRICE_CHANGE_IN_ORDERADD.getIndex()), orderChange1);
                }
            }
            Map<Long,List<String>> mapSameChange=new HashMap<Long, List<String>>();
            for(Map.Entry<String,OrderChange> entry:mapChange.entrySet())
            {
                List<String> list=null;
                if(mapSameChange.containsKey(entry.getValue().getOrderChangeId()))
                {
                    list=mapSameChange.get(entry.getValue().getOrderChangeId());
                }
                else
                {
                    list=new ArrayList<String>();
                    mapSameChange.put(entry.getValue().getOrderChangeId(),list);
                }
                list.add(entry.getKey());
            }
            //如果没有变化，直接忽略
            if(mapChange==null||mapChange.size()==0)
            {
                continue;
            }
            Order order=orderhistDao.get(orderChange.getId());
            //调用订单修改流程
            UserBpm userBpm = new UserBpm();
            userBpm.setCreateDate(new Date());
            userBpm.setCreateUser(orderChange.getMdusr());
            userBpm.setOrderID(orderChange.getOrderid());
            userBpm.setContactID(order.getContactid());
            userBpm.setBusiType(String.valueOf(auditTaskType.getIndex()));
            try{
                userBpm.setDepartment(userService.getDepartment(order.getCrusr()));//以后添加部门信息
            }catch (NamingException exp)
            {
                logger.error("can't get department info:"+order.getCrusr(),exp);
                throw new ServiceException(ExceptionConstant.SERVICE_ORDER_CANCEL_EXCEPTION,"无法获取订单创建人部门信息:"+exp.getMessage());
            }
            userBpm.setWorkGrp(userService.getUserGroup(order.getCrusr()));
            userBpm.setStatus(String.valueOf(AuditTaskStatus.UNASSIGNED.getIndex()));
            String batchId = changeRequestService.createChangeRequest(userBpm);

            //
            List<UserBpmTaskType> userBpmTaskTypeList=new ArrayList<UserBpmTaskType>();
            for(String str:mapChange.keySet())
            {
                userBpmTaskTypeList.add(UserBpmTaskType.fromNumber(Integer.parseInt(str)));
            }

            List<List<UserBpmTaskType>> listList=orderModifyCorrelationService.getContentCorrelationFromOrder(orderChange,userBpmTaskTypeList);
            List<OrderModifyCorrelation> orderModifyCorrelationList=orderModifyCorrelationService.getCorrelationOrder(userBpmTaskTypeList,listList);

            /*UserBpmTask userBpmTask = new UserBpmTask();
            userBpmTask.setChangeObjID(orderChange.getOrderid());
            userBpmTask.setUpdateDate(userBpm.getCreateDate());
            userBpmTask.setUpdateUser(orderChange.getMdusr());
            userBpmTask.setRemark(remark);
            userBpmTask.setBatchID(batchId);*/
            //对应订单，不同区块启动多个流程
            //首先保存
            for(Map.Entry<String, OrderChange> entry:mapChange.entrySet())
            {
                Long token=entry.getValue().getOrderChangeId();
                if(entry.getValue().getOrderChangeId()==null||entry.getValue().getOrderChangeId().intValue()<0)
                {
                    entry.getValue().setOrderChangeId(null);
                    OrderChange orderChange1=orderChangeService.saveOrderChange(entry.getValue()); //orderChangeDao.save(entry.getValue());
                    mapChange.put(entry.getKey(),orderChange1);
                    //找到后续的相同的orderchange并修改掉
                    if(mapSameChange.containsKey(token))
                    {
                        List<String> keyList=mapSameChange.get(token);
                        for(String key:keyList)
                        {
                            if(mapChange.containsKey(key))
                            {
                                mapChange.put(key,orderChange1);
                            }
                        }
                    }
                }
                else
                {

                }
            }

            saveOrderChangesAndProc(batchId,null,remark,orderModifyCorrelationList,mapChange,createUsrId);
            map.put(orderChange.getOrderid(),getAllProcInstIdList(orderModifyCorrelationList));
            //orderChangeDao.saveOrUpdate(orderChange1);
            if(isCreating==false)
            {
                order.setVersion(order.getVersion()+1);//
                orderhistDao.saveOrUpdate(order);//修改订单版本，防止在读取出来处理的过程中，订单发生修改
                //情况1：订单读取出来后，进行出库扫描
            }
        }

        return map;
    }

    private List<String> getAllProcInstIdList(List<OrderModifyCorrelation> orderModifyCorrelationList)
    {
        List<String> list=new ArrayList<String>();
        for(OrderModifyCorrelation orderModifyCorrelation:orderModifyCorrelationList)
        {
            if(orderModifyCorrelation.getProcInstIdList()!=null&&orderModifyCorrelation.getProcInstIdList().size()>0)
            {
                list.addAll(orderModifyCorrelation.getProcInstIdList());
            }
            if(orderModifyCorrelation.getOrderModifyCorrelationList()!=null&&orderModifyCorrelationList.size()>0)
            {
                List<String> list1=getAllProcInstIdList(orderModifyCorrelation.getOrderModifyCorrelationList());
                if(list1!=null&&list1.size()>0)
                {
                    list.addAll(list1);
                }
            }
        }

        return list;
    }
    private void saveOrderChangesAndProc(String batchId, String parentInstId, String remark, List<OrderModifyCorrelation> orderModifyCorrelationList, Map<String,OrderChange> mapChange,String createUsrId)
    {
        //从上往下进行处理
        for(OrderModifyCorrelation orderModifyCorrelation:orderModifyCorrelationList)
        {
            List<String> procIdList=saveOrderModifyRequestAndBeginProc(mapChange.get(String.valueOf(orderModifyCorrelation.getUserBpmTaskType().getIndex())),
                    orderModifyCorrelation.getUserBpmTaskType(),batchId,parentInstId,remark, new Date(), createUsrId);
            orderModifyCorrelation.setProcInstIdList(procIdList);
            if(procIdList!=null&&procIdList.size()>0
                    &&orderModifyCorrelation.getOrderModifyCorrelationList()!=null&&orderModifyCorrelation.getOrderModifyCorrelationList().size()>0)
            {
                saveOrderChangesAndProc(batchId,procIdList.get(0),remark,orderModifyCorrelation.getOrderModifyCorrelationList(),mapChange,createUsrId);
            }
        }
    }

    private Map<String,OrderChange> decomposeOrderChange(OrderChange orderChange)
    {
        Order orderOrg = orderhistDao.get(orderChange.getId());
        Map<String,OrderChange> map=new HashMap<String, OrderChange>();
        //备注
        if(StringUtils.isNotEmpty(orderChange.getNote()))
        {
            OrderChange orderChangeNew=new OrderChange();
            orderChangeNew.setNote(orderChange.getNote());
            orderChangeNew.setId(orderChange.getId());
            orderChangeNew.setOrderid(orderChange.getOrderid());

            map.put(String.valueOf(UserBpmTaskType.ORDER_REMARK_CHANGE.getIndex()),orderChangeNew);
        }
        //承运商
        if(StringUtils.isNotEmpty(orderChange.getIsreqems()))
        {
            OrderChange orderChangeNew=new OrderChange();
            orderChangeNew.setIsreqems(orderChange.getIsreqems());
            orderChangeNew.setId(orderChange.getId());
            orderChangeNew.setOrderid(orderChange.getOrderid());

            map.put(String.valueOf(UserBpmTaskType.ORDER_EMS_CHANGE.getIndex()),orderChangeNew);
        }

        //购物车
        if(orderChange.getOrderdetChanges()!=null&&orderChange.getOrderdetChanges().size()>0)
        {
            OrderChange orderChangeNew=new OrderChange();
            orderChangeNew.setId(orderChange.getId());
            orderChangeNew.setOrderid(orderChange.getOrderid());

            for(OrderdetChange orderdetChange:orderChange.getOrderdetChanges())
            {
                orderChangeNew.getOrderdetChanges().add(orderdetChange);
                orderdetChange.setOrderChange(orderChangeNew);
            }

            map.put(String.valueOf(UserBpmTaskType.ORDER_CART_CHANGE.getIndex()),orderChangeNew);

            //此时需要重新计算积分等
            if(orderChange.getTotalprice()==null||orderChange.getProdprice()==null)
            {
                 throw new RuntimeException("修改订单购物车必须提供订单总价和商品总价");
            }
            orderChangeNew.setProdprice(orderChange.getProdprice());
            orderChangeNew.setTotalprice(orderChange.getTotalprice());
            //积分计算
            if(StringUtils.isEmpty(orderChangeNew.getJifen()))
            {
                //
                orderChangeNew.setJifen(calcOrderJifenChange(orderChangeNew).toString());
            }
            orderChange.setOrderdetChanges(new HashSet<OrderdetChange>());
        }

        //发票
        if(StringUtils.isNotEmpty(orderChange.getBill())|| StringUtils.isNotEmpty(orderChange.getInvoicetitle()))
        {
            OrderChange orderChangeNew=new OrderChange();
            orderChangeNew.setId(orderChange.getId());
            orderChangeNew.setOrderid(orderChange.getOrderid());

            orderChangeNew.setBill(orderChange.getBill());
            orderChangeNew.setInvoicetitle(orderChange.getInvoicetitle());

            map.put(String.valueOf(UserBpmTaskType.ORDER_BILL_CHANGE.getIndex()),orderChangeNew);
        }

        //运费
        if(orderChange.getMailprice()!=null)
        {
            OrderChange orderChangeNew=new OrderChange();
            orderChangeNew.setId(orderChange.getId());
            orderChangeNew.setOrderid(orderChange.getOrderid());

            orderChangeNew.setMailprice(orderChange.getMailprice());

            map.put(String.valueOf(UserBpmTaskType.ORDER_MAILPRICE_CHANGE.getIndex()),orderChangeNew);
        }

        //订单类型
        if(StringUtils.isNotEmpty(orderChange.getOrdertype()))
        {
            OrderChange orderChangeNew=new OrderChange();
            orderChangeNew.setId(orderChange.getId());
            orderChangeNew.setOrderid(orderChange.getOrderid());

            orderChangeNew.setOrdertype(orderChange.getOrdertype());

            map.put(String.valueOf(UserBpmTaskType.ORDER_TYPE_CHANGE.getIndex()),orderChangeNew);
        }

        //信用卡
        if(StringUtils.isNotEmpty(orderChange.getLaststatus())|| orderChange.getCardChanges().size()>0)
        {
            OrderChange orderChangeNew=new OrderChange();
            orderChangeNew.setId(orderChange.getId());
            orderChangeNew.setOrderid(orderChange.getOrderid());

            orderChangeNew.setLaststatus(orderChange.getLaststatus());

            if(orderChange.getCardChanges().size()>0)
            {
                for(CardChange cardChange:orderChange.getCardChanges())
                {
                    orderChangeNew.getCardChanges().add(cardChange);
                    cardChange.setOrderChange(orderChangeNew);
                }
                orderChange.setCardChanges(new HashSet<CardChange>());
            }
            map.put(String.valueOf(UserBpmTaskType.ORDER_CARD_CHANGE.getIndex()),orderChangeNew);
        }

        OrderChange orderChange1=null;
        if(map.size()>0)
        {
            for(Map.Entry<String,OrderChange> entry:map.entrySet())
            {
                orderChange1=entry.getValue();
                break;
            }
            ContactChange getContactChange = orderChange.getGetContactChange();
            if(getContactChange!=null)
            {
                orderChange1.setGetContactChange(getContactChange);
                getContactChange.setOrderChange(orderChange1);
            }
            AddressExtChange addressExtChange=orderChange.getAddress();
            if(addressExtChange!=null)
            {
                orderChange1.setAddress(addressExtChange);
                addressExtChange.setOrderChange(orderChange1);
            }
            ContactChange contactChange=orderChange.getContactChange();
            if(contactChange!=null)
            {
                orderChange1.setContactChange(contactChange);
                contactChange.setOrderChange(orderChange1);
            }
            /*for(OrderChangeType orderChangeType:orderChangeList)
            {
                orderChangeType.getOrderChange().setMdusr(orderChange.getMdusr());
            }*/
        }
        else
        {
            orderChange1=orderChange;
        }

        /*if(orderChange1.getContactChange()!=null)
        {
            map.put(String.valueOf(UserBpmTaskType.CONTACT_BASE_CHANGE.getIndex()),orderChange1);
        }*/
        if(orderChange1.getAddress()!=null)
        {
            map.put(String.valueOf(UserBpmTaskType.CONTACT_ADDRESS_CHANGE.getIndex()),orderChange1);
        }


        //boolean bContactChange=false;
        if(orderChange1.getGetContactChange()!=null)
        {
            boolean bContactChange=false;
            bContactChange=checkConcactChanged(orderChange1.getGetContactChange());
            if(StringUtils.isEmpty(orderChange1.getGetContactChange().getContactid())||!orderChange1.getGetContactChange().getContactid().equals(orderOrg.getGetcontactid()))
            {
                bContactChange=true;
            }

            boolean bBpmGetContact=false;
            if(bContactChange==true)
            {
                map.put(String.valueOf(UserBpmTaskType.CONTACT_BASE_CHANGE.getIndex()),orderChange1);
                bBpmGetContact=true;
            }

            if(orderChange1.getGetContactChange().getPhoneChanges()!=null&&orderChange1.getGetContactChange().getPhoneChanges().size()>0)
            {
                map.put(String.valueOf(UserBpmTaskType.CONTACT_PHONE_CHANGE.getIndex()),orderChange1);
                bBpmGetContact=true;
            }
            if(bBpmGetContact==false)
            {
                map.put(String.valueOf(UserBpmTaskType.ORDER_RECEIVER_CHANGE.getIndex()),orderChange1);
            }
        }

        if(orderChange1.getContactChange()!=null)
        {
            map.put(String.valueOf(UserBpmTaskType.CONTACT_BASE_CHANGE.getIndex()),orderChange1);
        }

        Long index=-1L;
        for(Map.Entry<String,OrderChange> entry:map.entrySet())
        {
            entry.getValue().setMdusr(orderChange.getMdusr());
            if(entry.getValue().getOrderChangeId()==null)
            {
                index--;
                entry.getValue().setOrderChangeId(index);
            }
        }
        return map;
    }

    private List<String> saveOrderModifyRequestAndBeginProc(OrderChange orderChange, UserBpmTaskType changeType, String batchId, String parentInstId, String remark,Date createDate,String createUsrId)
    {
         List<String> procInstList=new ArrayList<String>();
         Order orderOrg = orderhistDao.get(orderChange.getId());
         if(orderChange.getGetContactChange()!=null)
         {
             String phoneProcInstId="";
             if(changeType==null||changeType.getIndex()==UserBpmTaskType.CONTACT_PHONE_CHANGE.getIndex())
             {
                 if(orderChange.getGetContactChange().getPhoneChanges()!=null&&orderChange.getGetContactChange().getPhoneChanges().size()>0)
                 {
                     for(PhoneChange phoneChange:orderChange.getGetContactChange().getPhoneChanges())
                     {
                         //启动流程
                         UserBpmTask userBpmTask = new UserBpmTask();
                         userBpmTask.setChangeObjID(orderChange.getOrderChangeId().toString());
                         userBpmTask.setUpdateDate(createDate);
                         userBpmTask.setUpdateUser(createUsrId);
                         userBpmTask.setRemark(remark);
                         userBpmTask.setBatchID(batchId);
                         userBpmTask.setStatus(String.valueOf(AuditTaskStatus.UNASSIGNED.getIndex()));
                         userBpmTask.setBusiType(String.valueOf(UserBpmTaskType.CONTACT_PHONE_CHANGE.getIndex()));
                         if(StringUtils.isNotEmpty(parentInstId))
                         {
                             userBpmTask.setParentInsId(parentInstId);
                         }

                         phoneProcInstId = userBpmTaskService.createApprovingTask(userBpmTask);

                         phoneChange.setProcInstId(phoneProcInstId);

                         procInstList.add(phoneProcInstId);
                     }
                 }
             }
             //检查联系人是否变化
             if(changeType==null||changeType.getIndex()==UserBpmTaskType.CONTACT_BASE_CHANGE.getIndex())
             {
                 UserBpmTask userBpmTask = new UserBpmTask();
                 userBpmTask.setChangeObjID(orderChange.getOrderChangeId().toString());
                 userBpmTask.setUpdateDate(createDate);
                 userBpmTask.setUpdateUser(createUsrId);
                 userBpmTask.setRemark(remark);
                 userBpmTask.setBatchID(batchId);
                 userBpmTask.setStatus(String.valueOf(AuditTaskStatus.UNASSIGNED.getIndex()));
                 userBpmTask.setBusiType(String.valueOf(UserBpmTaskType.CONTACT_BASE_CHANGE.getIndex()));

                 if(StringUtils.isNotEmpty(parentInstId))
                 {
                     userBpmTask.setParentInsId(parentInstId);
                 }

                 String procInstId = userBpmTaskService.createApprovingTask(userBpmTask);
                 orderChange.getGetContactChange().setProcInstId(procInstId);

                 procInstList.add(procInstId);
             }
         }

        if(changeType==null||changeType.getIndex()==UserBpmTaskType.CONTACT_ADDRESS_CHANGE.getIndex())
        {
             if(orderChange.getAddress()!=null)
             {
                 UserBpmTask userBpmTask = new UserBpmTask();
                 userBpmTask.setChangeObjID(orderChange.getOrderChangeId().toString());
                 userBpmTask.setUpdateDate(createDate);
                 userBpmTask.setUpdateUser(createUsrId);
                 userBpmTask.setRemark(remark);
                 userBpmTask.setBatchID(batchId);
                 userBpmTask.setStatus(String.valueOf(AuditTaskStatus.UNASSIGNED.getIndex()));
                 userBpmTask.setBusiType(String.valueOf(UserBpmTaskType.CONTACT_ADDRESS_CHANGE.getIndex()));

                 if(StringUtils.isNotEmpty(parentInstId))
                 {
                     userBpmTask.setParentInsId(parentInstId);
                 }

                 String procInstId = userBpmTaskService.createApprovingTask(userBpmTask);
                 orderChange.getAddress().setProcInstId(procInstId);

                 procInstList.add(procInstId);
                 //检查地址是否未启动审批，如果是的，那么设置标记
                 if(StringUtils.isNotEmpty(orderChange.getAddress().getAddressId()))
                 {
                     AddressExt addressExtOrg=addressExtService.getAddressExt(orderChange.getAddress().getAddressId());
                     if(addressExtOrg!=null)
                     {
                         if(this.isNeedAuditFlag(addressExtOrg.getAuditState()))
                         {
                             addressExtOrg.setAuditState(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITING);
                             addressExtService.saveOrUpdate(addressExtOrg);
                         }
                     }
                 }
             }
        }

         //最后启动订单流程
        //boolean bOrderChange = checkOrderChanged(orderChange);
        if(changeType!=null&&changeType.getIndex()!=UserBpmTaskType.CONTACT_ADDRESS_CHANGE.getIndex()
                &&changeType.getIndex()!=UserBpmTaskType.CONTACT_PHONE_CHANGE.getIndex()
                &&changeType.getIndex()!=UserBpmTaskType.CONTACT_BASE_CHANGE.getIndex())
        {
            UserBpmTask userBpmTask = new UserBpmTask();
            userBpmTask.setChangeObjID(orderChange.getOrderChangeId().toString());
            userBpmTask.setUpdateDate(createDate);
            userBpmTask.setUpdateUser(createUsrId);
            userBpmTask.setRemark(remark);
            userBpmTask.setBatchID(batchId);
            userBpmTask.setStatus(String.valueOf(AuditTaskStatus.UNASSIGNED.getIndex()));
            userBpmTask.setBusiType(String.valueOf(changeType.getIndex()));

            if(StringUtils.isNotEmpty(parentInstId))
            {
                userBpmTask.setParentInsId(parentInstId);
            }

            String procInstId = userBpmTaskService.createApprovingTask(userBpmTask);
            orderChange.setProcInstId(procInstId);
            if(orderChange.getOrderdetChanges()!=null&&orderChange.getOrderdetChanges().size()>0)
            {
                for(OrderdetChange orderdetChange:orderChange.getOrderdetChanges())
                {
                    orderdetChange.setProcInstId(procInstId);
                }
            }
            procInstList.add(procInstId);
        }

        //设置创建人和创建时间
        orderChange.setCreateDate(createDate);
        orderChange.setCreateUser(createUsrId);
        if (orderChange.getGetContactChange() != null) {
            orderChange.getGetContactChange().setCreateDate(createDate);
            orderChange.getGetContactChange().setCreateUser(createUsrId);
            if (orderChange.getGetContactChange().getPhoneChanges() != null) {
                for (PhoneChange phoneChange : orderChange.getGetContactChange().getPhoneChanges()) {
                    phoneChange.setCreateDate(createDate);
                    phoneChange.setCreateUser(createUsrId);
                }
            }
        }
        if(orderChange.getAddress()!=null)
        {
            orderChange.getAddress().setCreateDate(createDate);
            orderChange.getAddress().setCreateUser(createUsrId);
        }

        String procId=this.startupBlackContactAudit(orderChange,batchId,remark,createDate,createUsrId);
        if(StringUtils.isNotEmpty(procId))
        {
            procInstList.add(procId);
        }

        return procInstList;
    }

    private boolean checkConcactChanged(ContactChange contactChange)
    {
        ContactChange contactChange1=new ContactChange();
        BeanUtils.copyProperties(contactChange,contactChange1);
        contactChange1.setPhoneChanges(null);
        contactChange1.setContactid(null);
        contactChange1.setProcInstId(null);
        contactChange1.setMddt(null);
        contactChange1.setMdusr(null);
        contactChange1.setCreateDate(null);
        contactChange1.setCreateUser(null);
        contactChange1.setContactChangeId(null);
        contactChange1.setOrderChange(null);
        return  !FieldValueCompareUtil.isValueNull(contactChange1,null);

    }

    private boolean checkAddressChanged(AddressExtChange addressChange)
    {
        AddressExtChange addressChange1=new AddressExtChange();
        BeanUtils.copyProperties(addressChange,addressChange1);
        addressChange1.setAddressId(null);
        addressChange1.setCreateDate(null);
        addressChange1.setCreateUser(null);
        addressChange1.setProcInstId(null);
        addressChange1.setAddressExtChangeId(null);
        addressChange1.setAddressChange(null);
        addressChange1.setOrderChange(null);

        return !FieldValueCompareUtil.isValueNull(addressChange1,null);
    }
    /*private boolean checkOrderChanged(OrderChange orderChange) {
        if(orderChange.getOrderdetChanges()!=null&&orderChange.getOrderdetChanges().size()>0)
            return true;
        OrderChange orderChange1=new OrderChange();
        BeanUtils.copyProperties(orderChange, orderChange1);
        orderChange1.setAddress(null);
        orderChange1.setOrderdetChanges(null);
        orderChange1.setContactChange(null);
        orderChange1.setGetContactChange(null);
        orderChange1.setMddt(null);
        orderChange1.setMdusr(null);
        orderChange1.setId(null);
        orderChange1.setOrderid(null);
        orderChange1.setOrderChangeId(null);
        orderChange1.setCreateDate(null);
        orderChange1.setCreateUser(null);
        orderChange1.setOrderdetChanges(null);
        return !FieldValueCompareUtil.isValueNull(orderChange1, null);
    }*/

    /**
     * 获取订单相关联信息的订单（共享地址、联系人等）
     *
     * @return
     */
    public List<Order> checkCorrelativeOrder(String orderId, boolean bAddressChange, boolean bContactChange, boolean bPhoneChange) throws ErpException {
        //查找时，目前加了7天的时间限制（订单表太大了）
        if(bAddressChange==false&&bPhoneChange==false&&bContactChange==false)
            return null;

        try
        {
            Order order = orderhistDao.getOrderHistByOrderid(orderId);
            if (bPhoneChange == true)
                bContactChange = true;
            return checkOrderCorrelative(bAddressChange, bContactChange, order);
        }catch (RuntimeException runtimeExp)
        {
            logger.error("checkCorrelativeOrder error:",runtimeExp);
            throw new ServiceException(ExceptionConstant.SERVICE_ORDER_CORRELATIVE_QUERY_EXCEPTION,ExceptionMsgUtil.getMessage(runtimeExp),runtimeExp);
        }
    }

    private static final String SELF_ORDER_PAYTYPE="11";
    //private static final String SELF_ORDER_NAME = "上门自提点";
    private boolean checkSelfOrder(Order order)
    {
        //检查地址是否是上门自提点地址
        if(StringUtils.isNotEmpty(order.getPaytype()))
        {
            if(SELF_ORDER_PAYTYPE.equals(order.getPaytype()))
            {
                return true;
            }
        }
        return false;
    }

    private List<Order> checkOrderCorrelative(boolean bAddressChange, boolean bContactChange, Order order) {
        List<Order> orderList = orderhistDao.findCorrelativeOrders(order);
        List<Order> retOrderList = new ArrayList<Order>();
        if (orderList != null) {
            boolean isSelfOrder=false;
            if(bAddressChange==true)
            {
                isSelfOrder=checkSelfOrder(order);
            }

            for (Order order1 : orderList) {
                if (!this.canModify(order1))
                    continue;

                boolean bMatch = false;
                if (bAddressChange) {
                    if(order1.getAddress()!=null&&order1.getAddress().getAddressId()!=null)
                    {
                        if (order.getAddress().getAddressId().equals(order1.getAddress().getAddressId()))
                        {
                            if(isSelfOrder==true)
                            {
                                //上门自提的订单，必须收货人一致
                                if (order.getContactid().equals(order1.getContactid())) {
                                    bMatch = true;
                                }
                            }
                            else
                            {
                                bMatch = true;
                            }
                        }
                    }
                }
                if (bContactChange&&isSelfOrder==false) {
                    if (order.getGetcontactid().equals(order1.getGetcontactid())) {
                        bMatch = true;
                    }
                }

                if (bMatch == true)
                    retOrderList.add(order1);
            }
        }

        return retOrderList;
    }

    /**
     * 获取地址关联的未出库订单
     * @param addressId
     * @return
     */
    public List<Order> checkCorrelativeOrderByContact(String addressId,String contactId) throws ErpException
    {
        if(StringUtils.isEmpty(addressId)&&StringUtils.isEmpty(contactId))
            return null;
        boolean bAddressChange=false;
        boolean bContactChange=false;
        Order order=new Order();
        if(StringUtils.isNotEmpty(addressId))
        {
            bAddressChange=true;
            AddressExt addressExt=new AddressExt();
            addressExt.setAddressId(addressId);
            order.setAddress(addressExt);
        }
        if(StringUtils.isNotEmpty(contactId))
        {
            order.setGetcontactid(contactId);
            bContactChange=true;
        }


        try
        {
            return this.checkOrderCorrelative(bAddressChange,bContactChange,order);
        }catch (RuntimeException runtimeExp)
        {
            logger.error("checkCorrelativeOrderByContact error:",runtimeExp);
            throw new ServiceException(ExceptionConstant.SERVICE_ORDER_CORRELATIVE_QUERY_EXCEPTION,ExceptionMsgUtil.getMessage(runtimeExp),runtimeExp);
        }
    }

    /**
     * 从原保存的订单以及修改请求中获取更新后的订单对象
     *
     * @param orderChange
     * @param order
     * @return
     */
    private Order getModifiedOrderFromChangeRequest(Order orderModify,OrderChange orderChange, Order order, String emsCompanyId) {
        //Order orderModify = new Order();
        OrderhistUtil.copy(orderModify, order);
        //将变更的数据赋值
        return mergeOrderFromOrderChange(orderModify, orderChange, emsCompanyId);
    }

    /**
     * 将修改请求中的数据合并到订单中
     * 算法：非NULL的数据，就是修改的数据，如果是空字符串，那么就认为是改成NULL
     *
     * @param orderChange
     * @param order
     */
    private Order mergeOrderFromOrderChange(Order order, OrderChange orderChange, String emsCompanyId) {
        //订单修改的字段设置
        order = mergeOrderHeadFromOrderChange(order, orderChange, emsCompanyId);

        //订单明细设置(变更历史里面放置了修改后的值，所以只要直接赋值就行了)
        if (orderChange.getOrderdetChanges() != null && orderChange.getOrderdetChanges().size() > 0) {
            order.getOrderdets().clear();
            for (OrderdetChange orderdetChange : orderChange.getOrderdetChanges()) {
                OrderDetail orderDetail = new OrderDetail();
                BeanUtils.copyProperties(orderdetChange, orderDetail);
                orderDetail.setOrderhist(order);
                order.getOrderdets().add(orderDetail);
            }
            //修改购物车
            order.setTotalprice(order.getProdprice().add(order.getMailprice()));
            assignOrderdetMailprice(order);
        }

        //修改运费
        if(orderChange.getMailprice()!=null)
        {
            //重新计算订单金额
            order.setTotalprice(order.getProdprice().add(orderChange.getMailprice()));
            assignOrderdetMailprice(order);
        }

        return order;
    }

    private void assignOrderdetMailprice(Order order) {
        //分摊到明细中
        boolean bSet=false;
        for(OrderDetail orderDetail:order.getOrderdets())
        {
            if(orderDetail.getFreight()!=null&&orderDetail.getFreight().compareTo(BigDecimal.ZERO)>0)
            {
                orderDetail.setFreight(order.getMailprice());
                bSet=true;
                break;
            }
        }
        if(bSet==false)
        {
            for(OrderDetail orderDetail:order.getOrderdets())
            {
                if("1".equals(orderDetail.getSoldwith()))
                {
                    orderDetail.setFreight(order.getMailprice());
                    bSet=true;
                    break;
                }
            }
            if(bSet==false)
            {
                for(OrderDetail orderDetail:order.getOrderdets())
                {
                    orderDetail.setFreight(order.getMailprice());
                    break;
                }
            }
        }
    }

    private Order mergeOrderHeadFromOrderChange(Order order, OrderChange orderChange, String emsCompanyId) {
        //简单属性类型，直接赋值(目前直接赋值操作而不使用属性反射或者工具的原因是：简洁直观而且不同字段值发生改变的业务情况不同)
        String strTemp;
        if (orderChange.getEntityid() != null) {
            strTemp = orderChange.getEntityid().trim();
            if ("".equals(strTemp)) {
                order.setEntityid(null);
            } else {
                order.setEntityid(orderChange.getEntityid());
            }
        }

        if (orderChange.getSpellid() != null)
            order.setSpellid(orderChange.getSpellid());

        order.setMddt(orderChange.getMddt());
        if(StringUtils.isEmpty(orderChange.getMdusr()))
        {
            order.setMdusr(orderChange.getCreateUser());
        }
        else
        {
            order.setMdusr(orderChange.getMdusr());
        }

        if (orderChange.getMailtype() != null) {
            strTemp = orderChange.getMailtype().trim();
            if ("".equals(strTemp)) {
                order.setMailtype(null);
            } else {
                order.setMailtype(orderChange.getMailtype());
            }
        }

        if (orderChange.getPaytype() != null) {
            strTemp = orderChange.getPaytype().trim();
            if ("".equals(strTemp)) {
                order.setPaytype(null);
            } else {
                order.setPaytype(orderChange.getPaytype());
            }
        }

        if (orderChange.getUrgent() != null) {
            strTemp = orderChange.getUrgent().trim();
            if ("".equals(strTemp)) {
                order.setUrgent(null);
            } else {
                order.setUrgent(orderChange.getUrgent());
            }
        }

        //订单总金额和商品金额应该从明细中算出来(在保存修改请求的时候，应该已经算好了)
        if (orderChange.getTotalprice() != null) {
            order.setTotalprice(orderChange.getTotalprice());
        }

        if (orderChange.getProdprice() != null) {
            order.setProdprice(orderChange.getProdprice());
        }

        if (orderChange.getMailprice() != null) {
            order.setMailprice(orderChange.getMailprice());
        }

        if (orderChange.getClearfee() != null) {
            order.setClearfee(orderChange.getClearfee());
        }

        if (orderChange.getBill() != null) {
            strTemp = orderChange.getBill().trim();
            if ("".equals(strTemp)) {
                order.setBill(null);
            } else {
                order.setBill(strTemp);
            }
        }

        if (orderChange.getNote() != null) {
            strTemp = orderChange.getNote().trim();
            if ("".equals(strTemp)) {
                order.setNote(null);
            } else {
                order.setNote(strTemp);
            }
        }

        if (orderChange.getCardtype() != null) {
            strTemp = orderChange.getCardtype().trim();
            if ("".equals(strTemp)) {
                order.setCardtype(null);
            } else {
                order.setCardtype(orderChange.getCardtype());
            }
        }

        if (orderChange.getCardnumber() != null) {
            strTemp = orderChange.getCardnumber().trim();
            if ("".equals(strTemp)) {
                order.setCardnumber(null);
            } else {
                order.setCardnumber(orderChange.getCardnumber());
            }
        }

        //orderChange.getStarttm();orderChange.getEndtm();
        if (orderChange.getJifen() != null) {
            strTemp = orderChange.getJifen().trim();
            if ("".equals(strTemp)) {
                order.setJifen(null);
            } else {
                order.setJifen(orderChange.getJifen());
            }
        }

        if (orderChange.getSpid() != null) {
            strTemp = orderChange.getSpid().trim();
            if ("".equals(strTemp)) {
                order.setSpid(null);
            } else {
                order.setSpid(orderChange.getSpid());
            }
        }

        if (orderChange.getInvoicetitle() != null) {
            strTemp = orderChange.getInvoicetitle().trim();
            if ("".equals(strTemp)) {
                order.setInvoicetitle(null);
            } else {
                order.setInvoicetitle(orderChange.getInvoicetitle());
            }
        }

        if (orderChange.getIsreqems() != null) {
            //如果要求ems送货，那么直接修改成ems公司送货
            strTemp = orderChange.getIsreqems().trim();
            if (!strTemp.equals(order.getReqEMS())) {
                order.setReqEMS(strTemp);
                order.setReqUsr(orderChange.getIsrequsr());

                //如果不一致，那么修改
                if ("Y".equals(strTemp)) {
                    if(StringUtils.isEmpty(emsCompanyId))
                        emsCompanyId=ShipmentHeaderServiceImpl.COMPANY_EMS;
                    order.setEntityid(emsCompanyId);
                    Company company = companyService.findCompany(emsCompanyId);
                    if (company == null)
                        return null;
                    order.setMailtype(company.getMailtype());
                    //设置成手工指定，随后复杂分拣直接使用给定的送货公司
                    order.setIsassign(OrderAssignConstants.HAND_ASSIGN);
                } else {
                    //指定ems改成非ems（目前实际业务情况不会发生）
                    order.setIsassign(null);
                }
            }
        }

        if(orderChange.getCardChanges()!=null&&orderChange.getCardChanges().size()>0)
        {
            //如果身份证等，直接更新，如果银行卡类型
            updateCardFromCardChanges(orderChange,order);
        }
        if(StringUtils.isNotEmpty(orderChange.getLaststatus()))
        {
            order.setLaststatus(orderChange.getLaststatus());
        }

        return order;
    }

    private void updateCardFromCardChanges(OrderChange orderChange,Order order)
    {
        Card cardOrder=null;
        List<Card> cardList=new ArrayList<Card>();
        for(CardChange cardChange:orderChange.getCardChanges())
        {
            Card card=this.mergeFromCardChange(cardChange);
            if(this.isInAuditFlag(card.getState()))
            {
                card.setState(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITED);
                Cardtype cardtype=cardtypeService.getCardType(card.getType());
                if("2".equals(cardtype.getType1()))
                {
                    order.setCardnumber(card.getCardId().toString());
                    order.setCardtype(cardtype.getCardname());
                }
                try
                {
                    cardService.updateCardState(card.getCardId(),CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITED);
                }    catch (Exception exp)
                {
                    logger.error("update order card error:", exp);
                }
            }
            else
            {
                Cardtype cardtype=cardtypeService.getCardType(card.getType());
                if("2".equals(cardtype.getType1()))
                {
                    //判断是否只是替换，而没有发生修改
                    boolean bReplace=false;
                    if(card.getCardId()!=null)
                    {
                        Card cardOrg=cardService.getCard(card.getCardId());
                        if(cardOrg!=null)
                        {
                            List<String> fldsList = FieldValueCompareUtil.compare(cardOrg, card, new ArrayList<String>());
                            if(fldsList==null||fldsList.size()==0)
                                bReplace=true;
                        }
                    }
                    if(bReplace==false)
                    {
                        card.setCardId(null);
                        cardOrder=cardDao.merge(card);
                    }
                    else
                    {
                        cardOrder=card;
                    }
                    order.setCardtype(cardtype.getCardname());

                    order.setCardnumber(cardOrder.getCardId().toString());
                    if(StringUtils.isNotEmpty(cardOrder.getContactId()))
                        order.setPaycontactid(cardOrder.getContactId());
                }
                else
                {
                    //cardList.add(card);
                    cardDao.merge(card);
                }
            }

        }
        /*if(cardList.size()>0)
        {
            for(Card card:cardList)
            {
                cardDao.merge(card);
            }
            //cardService.saveCards(cardList);
        }*/
        /*if(cardOrder!=null)
        {
            order.setCardnumber(cardOrder.getCardId().toString());
            if(StringUtils.isNotEmpty(cardOrder.getContactId()))
                order.setPaycontactid(cardOrder.getContactId());
        }*/
    }

    @Autowired
    private CardService cardService;

    @Autowired
    private CardtypeService cardtypeService;

    private Card mergeFromCardChange(CardChange cardChange)
    {
        Card card=new Card();
        BeanUtils.copyProperties(cardChange,card);
        if(cardChange.getCardId()!=null)
        {
            Card cardOrg=cardService.getCard(cardChange.getCardId());
            FieldValueCompareUtil.copyNullFld(cardOrg,card);
        }

        return card;
    }

    private AddressExt createAddressFromAddressChange(AddressExtChange addressExtChange, String strContactId) {
        AddressExt addressExt = new AddressExt();
        //Address address = new Address();
        //在修改地址的时候，需要同时插入AddressExtChange和AddressChange，所以此时addressExtChange.getAddressChange()必不为NULL
        BeanUtils.copyProperties(addressExtChange, addressExt);
        AddressExt addressExtOrg = addressExtService.getAddressExt(addressExtChange.getAddressId());
        FieldValueCompareUtil.copyNullFld(addressExtOrg, addressExt);

        //address.setAddress(addressExtOrg.getAddressDesc());


        //address.setAddressid(null);
        addressExt.setAddressId(null);
        addressExt.setContactId(strContactId);
        //address.setContactid(strContactId);
        //addressExtService.createAddressExt(addressExt, address);
        Integer spellId=this.getSpellIdFromAddressExt(addressExt);
        Ems ems=null;
        if(spellId!=null)
        {
            ems = emsDao.getEms(spellId);
        }
        addressExtService.createAddressExt(addressExt,ems);

        return addressExt;
    }

    private void createAddressFromContact(AddressExt addressExt, String contactId)
    {
        AddressExt addressExtNew=new AddressExt();
        BeanUtils.copyProperties(addressExt,addressExtNew);
        Address address=addressDao.get(addressExt.getAddressId());
        Address addressNew=new Address();
        BeanUtils.copyProperties(address,addressNew);
        addressExtNew.setContactId(contactId);
        address.setContactid(contactId);

        addressDao.save(addressNew);
        addressExtService.save(addressExtNew);
    }

    /**
     * 根据修改请求来更新订单
     * 使用积分要另外处理（检查积分上限，在contact表中）
     * 地址修改了，变成新增地址
     * 联系人等其他关联的信息修改了，同上
     *
     * @param procInstId
     */
    public void updateOrderFromChangeRequest(String procInstId, AuditTaskType auditTaskType, UserBpmTaskType userBpmTaskType, String orderId, String approvUserId, String remark) throws ErpException
    {
        auditOrder(procInstId,auditTaskType,userBpmTaskType,orderId,approvUserId,remark,null);
    }

    public void auditOrderEms(String procInstId, String emsCompanyId, AuditTaskType auditTaskType,String orderId, String approvUserId, String remark) throws ErpException
    {
        auditOrder(procInstId,auditTaskType,UserBpmTaskType.ORDER_EMS_CHANGE,orderId,approvUserId,remark,emsCompanyId);
    }

    private void auditOrder(String procInstId, AuditTaskType auditTaskType, UserBpmTaskType userBpmTaskType, String orderId, String approvUserId, String remark, String emsCompanyId) throws ErpException {
        //
        //（1）订单商品、规格、价格、数量、销售方式，即购物车中的商品信息
        //（2）收货信息：收货人、收货地址、邮编、联系电话
        //（3）信用卡信息：卡类型、持卡人、卡号、有效期、证件类型、证件号
        //（4）发票信息：产品明细/办公用品/礼品
        //（5）订单备注
        //（6）使用积分
        //（7）运费
        //（8）更换EMS送货
        //AuditTaskType auditTaskType=AuditTaskType.ORDERADD;
        //UserBpmTaskType userBpmTaskType=UserBpmTaskType.ORDER_CART_CHANGE;
        try
        {
            if (StringUtils.isBlank(procInstId))
            {
                String errorMsg="保存订单修改时未提供流程号";
                logger.error(errorMsg);
                throw new ServiceParameterException(ExceptionConstant.SERVICE_ORDER_UPDATE_EXCEPTION,errorMsg);
            }
            //从修改流程中获取修改请求
            Long orderChangeId=null;
            try
            {
                List<Object> list=userBpmTaskService.getChangeObjIdByBatchIdOrInstanceId(null,procInstId);
                if(list==null||list.size()==0)
                {
                    throw new RuntimeException("没有流程对应的订单修改ID");
                }
                if(list.get(0)==null)
                {
                    throw new RuntimeException("没有流程对应的订单修改ID");
                }
                orderChangeId=Long.parseLong(list.get(0).toString());
            }catch (Exception exp)
            {
                throw new ErpException(ExceptionConstant.SERVICE_ORDER_CREATE_EXCEPTION,exp.getMessage(), exp);
            }
            OrderChange orderChange = orderChangeDao.get(orderChangeId);
            if (orderChange == null)
            {
                String errorMsg="保存订单修改时未找到订单修改记录。流程号："+procInstId;
                logger.error(errorMsg);
                throw new ServiceParameterException(ExceptionConstant.SERVICE_ORDER_UPDATE_EXCEPTION,errorMsg);
            }
            Order orgOrder = orderhistDao.get(orderChange.getId());
            if (!this.canModify(orgOrder))
            {
                String errorMsg="已出库、已取消、已完成或者索权成功，不能修改。订单Id："+orgOrder.getOrderid();
                logger.error(errorMsg);
                throw new ServiceException(ExceptionConstant.SERVICE_ORDER_UPDATE_EXCEPTION,errorMsg);
            }

            Integer orgJifen=this.calcOrderJifen(orgOrder);
            Order orderModify = new Order();
            OrderhistUtil.copy(orderModify, orgOrder);

            boolean bOrderChanged=false;
            if(procInstId.equals(orderChange.getProcInstId()))
            {
                orderModify = this.getModifiedOrderFromChangeRequest(orderModify, orderChange, orgOrder, emsCompanyId);
                bOrderChanged=true;
            }

            if(this.blackContactAuditHandle(orderChange,procInstId))
            {
                //客户加黑，不需要处理
            }
            //将订单关联的联系人、联系地址等先保存起来
            bOrderChanged = updateOrderAuditContact(procInstId, orderChange, orderModify, bOrderChanged);

            bOrderChanged = updateOrderAuditAddress(procInstId, orderChange, orgOrder, orderModify, bOrderChanged);

            //检查信信用卡分期数是否有效
            //checkCardOrder(orderModify);

            //检查规则有效性
            if(orderModify.getCheckResult()!=null&& orderModify.getCheckResult().intValue()==CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITING)
            {
                UserBpmTaskType userBpmTaskType1=null;
                if(auditTaskType!=null&&auditTaskType.getIndex()==AuditTaskType.ORDERADD.getIndex())
                {
                    userBpmTaskType1=userBpmTaskType;
                }
                boolean  bPass=orderCheckService.checkOrder(orderModify,userBpmTaskType1,this.getCurrentUsr());
                if(bPass==true)
                {
                    //orderModify.setCheckxResult(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITED);
                }
                else
                    orderModify.setCheckResult(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITING);
            }

            try {
                if(bOrderChanged==true)
                {
                    checkOrderdetChanges(orderModify);
                    if(StringUtils.isNotEmpty(orderChange.getMdusr()))
                        orderModify.setMdusr(orderChange.getMdusr());
                    orderModify.setNowmoney(orderModify.getTotalprice());
                    //如果订单已经分拣了，那么需要重新打成订购状态，来重新跑分拣
                    /*if(orderModify.getStatus().equals(AccountStatusConstants.ACCOUNT_TRANS))
                    {
                        orderModify.setStatus(AccountStatusConstants.ACCOUNT_NEW);
                    }*/
                    this.resetOrderStatusOnModify(orderModify);
                    if(userBpmTaskType!=null&&userBpmTaskType.getIndex()==UserBpmTaskType.ORDER_EMS_CHANGE.getIndex()&& "Y".equals(orderModify.getReqEMS()))
                    {
                        orderModify.setIsassign(OrderAssignConstants.HAND_ASSIGN);
                        Company company=companyService.findCompany(emsCompanyId);
                        updateOrderFromModifyRequest(orderModify,orgOrder,company.getWarehouseId().intValue());
                    }
                    else
                    {
                        //TODO:订单已经指定了ems，此时修改订单，如何处理？？？
                        if(userBpmTaskType!=null&&userBpmTaskType.getIndex()!=UserBpmTaskType.ORDER_EMS_CHANGE.getIndex()&&"Y".equals(orderModify.getReqEMS()))
                        {
                        }
                        orderModify.setIsassign("");
                        updateOrderFromModifyRequest(orderModify,orgOrder,0);
                    }
                    //this.updateOrderhist(orderModify);
                }
            } catch (Exception exp) {
                logger.error("updateOrderFromChangeRequest this.updateOrderhist error:",exp);
                throw new RuntimeException(exp.getMessage(),exp);
            }

            //最后需要处理积分
            if (!orgJifen.equals(this.calcOrderJifen(orderModify))) {
                this.callJifenModify(orderModify);
            }

            //结束流程
            try
            {
                userBpmTaskService.approveChangeRequest(orderChange.getMdusr(),approvUserId,procInstId,remark);
            }catch (Exception exp)
            {
                logger.error("updateOrderFromChangeRequest userBpmTaskService.approveChangeRequest error:",exp);
                throw new ServiceException(ExceptionConstant.SERVICE_ORDER_CORRELATIVE_QUERY_EXCEPTION,ExceptionMsgUtil.getMessage(exp),exp);
            }
        }catch (RuntimeException runtimeExp)
        {
            logger.error("updateOrderFromChangeRequest error:",runtimeExp);
            throw new ServiceException(ExceptionConstant.SERVICE_ORDER_UPDATE_EXCEPTION,ExceptionMsgUtil.getMessage(runtimeExp),runtimeExp);
        }
    }

    private boolean updateOrderAuditAddress(String procInstId, OrderChange orderChange, Order orgOrder, Order orderModify, boolean bOrderChanged) {
        if (orderChange.getAddress() != null&& procInstId.equals(orderChange.getAddress().getProcInstId())) {
            //创建订单时，只是新增地址
            //修改地址每次都新增一条记录
            //如果只是更换了地址，那么直接使用
            boolean bAuditAddress=false;
            if(StringUtils.isNotEmpty(orderChange.getAddress().getAddressId()))
            {
                AddressExt addressExt=addressExtService.getAddressExt(orderChange.getAddress().getAddressId());
                if(this.isInAuditFlag(addressExt.getAuditState()))
                {
                    bAuditAddress=true;
                    addressExt.setAuditState(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITED);
                    addressExtService.saveOrUpdate(addressExt);
                    orderModify.setAddress(addressExt);
                }
            }

            if(bAuditAddress==false)
            {
                if(checkAddressChanged(orderChange.getAddress()))
                {
                    AddressExt addressExt = this.createAddressFromAddressChange(orderChange.getAddress(), orderModify.getGetcontactid());
                    orderModify.setAddress(addressExt);
                }
                else
                {
                    if(!orgOrder.getAddress().getAddressId().equals(orderChange.getAddress().getAddressId()))
                    {
                        AddressExt addressExt=addressExtService.getAddressExt(orderChange.getAddress().getAddressId());
                        orderModify.setAddress(addressExt);
                    }
                }
            }
            bOrderChanged=true;
        }
        return bOrderChanged;
    }

    private boolean updateOrderAuditContact(String procInstId, OrderChange orderChange, Order orderModify, boolean bOrderChanged) throws ServiceTransactionException {
        if (orderChange.getGetContactChange() != null) {
            String strContactId = orderModify.getGetcontactid();
            //修改联系人，目前的做法也是每次都增加新记录
            ContactChange contactChange = orderChange.getGetContactChange();
            if(procInstId.equals(contactChange.getProcInstId()))
            {
                //检查是否只是替换了联系人(注意联系人Id从订单中获取而不是ContactChange表中)

                if("-9".equals(contactChange.getContacttype()))
                {
                    strContactId=contactChange.getContactid();
                }
                else{
                    Contact contactOrg=null;
                    if(StringUtils.isNotEmpty(contactChange.getContactid()))
                    {
                        contactOrg = contactDao.get(contactChange.getContactid());
                    }
                    else
                    {
                        contactOrg = contactDao.get(orderModify.getGetcontactid());
                    }
                    if(this.isInAuditFlag(contactOrg.getState()))
                    {
                        contactOrg.setState(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITED);
                        contactDao.saveOrUpdate(contactOrg);
                        strContactId=contactOrg.getContactid();
                    }
                    else
                    {
                        Contact contact=new Contact();
                        BeanUtils.copyProperties(contactChange, contact);
                        FieldValueCompareUtil.copyNullFld(contactOrg, contact);
                        contact.setContactid(null);
                        contact.setCrdt(new Date());

                        Contact contact1=contactDao.save(contact);
                         //此时需要注意，要拷贝原先联系人的所有电话
                        strContactId = contact1.getContactid();
                        createPhonesFromOrgContact(contactOrg.getContactid(),strContactId);
                        //原先的地址也需要拷贝
                        Order order=orderhistDao.get(orderChange.getId());
                        createAddressFromContact(order.getAddress(),strContactId);
                    }
                }
                orderModify.setGetcontactid(strContactId);
                bOrderChanged=true;
            }
            else
            {
                if (contactChange.getPhoneChanges() != null) {
                    for (PhoneChange phoneChange : contactChange.getPhoneChanges()) {
                        if(procInstId.equals(phoneChange.getProcInstId()))
                        {
                            handlePhoneChange(phoneChange);
                        }
                    }
                }
            }
        }
        return bOrderChanged;
    }

    private void checkCardOrder(Order order) throws ErpException
    {
        if("2".equals(order.getPaytype()))
        {
            if(StringUtils.isNotEmpty(order.getLaststatus())&&StringUtils.isNotEmpty(order.getCardnumber()))
            {
                Integer sum=Integer.parseInt(order.getLaststatus());
                Card card=cardService.getCard(Long.parseLong(order.getCardnumber()));
                if(!checkOrderCardFromType(order,card.getType()))
                {
                    throw new ErpException(ExceptionConstant.SERVICE_ORDER_UPDATE_EXCEPTION,"建行或招行信用卡用户名称必须一致");
                }
                List<Amortisation> amortisationList=this.queryCardAmortisation(card.getType());
                if(amortisationList==null||amortisationList.size()==0)
                {
                    throw new ErpException(ExceptionConstant.SERVICE_ORDER_UPDATE_EXCEPTION,"没有订单信用卡对应的分期数");
                }
                else
                {
                    for(Amortisation amortisation:amortisationList)
                    {
                        if(amortisation.getAmortisation()!=null&&sum.equals(amortisation.getAmortisation()))
                        {
                            if(amortisation.getLprice()!=null)
                            {
                                if(order.getTotalprice().compareTo(new BigDecimal(amortisation.getLprice()))<0)
                                {
                                    throw new ErpException(ExceptionConstant.SERVICE_ORDER_UPDATE_EXCEPTION,"订单信用卡对应的分期数金额范围不正确");
                                }
                            }
                            if(amortisation.getUprice()!=null)
                            {
                                if(order.getTotalprice().compareTo(new BigDecimal(amortisation.getUprice()))>=0)
                                {
                                    throw new ErpException(ExceptionConstant.SERVICE_ORDER_UPDATE_EXCEPTION,"订单信用卡对应的分期数金额范围不正确");
                                }
                            }
                            return;
                        }
                    }
                    //运行到此，表明没有找到对应的分期数
                    throw new ErpException(ExceptionConstant.SERVICE_ORDER_UPDATE_EXCEPTION,"订单信用卡对应的分期数设置不正确");
                }
            }
        }
    }

    private void createPhonesFromOrgContact(String orgContactId,String newContactId)
    {
        List<Phone> phoneList= phoneDao.findList("from Phone where contactid=:contactid", new ParameterString("contactid",orgContactId));
        if(phoneList!=null)
        {
            for(Phone phone:phoneList)
            {
                Phone newPhone=new Phone();
                BeanUtils.copyProperties(phone,newPhone);
                newPhone.setContactid(newContactId);
                newPhone.setPhoneid(null);
                phoneDao.save(newPhone);
            }
        }
    }

    private boolean isExistContactPhone(String contactId,Phone phone)
    {
        List<Phone> phoneList= phoneDao.findList("from Phone where contactid=:contactid", new ParameterString("contactid",contactId));
        if(phoneList!=null)
        {
            for(Phone phone1:phoneList)
            {
                if(isSameString(phone.getPhn1(),phone1.getPhn1())
                        &&isSameString(phone.getPhn2(),phone1.getPhn2())
                    &&isSameString(phone.getPhn3(),phone1.getPhn3())
                        &&isSameString(phone.getPhoneNum(),phone1.getPhoneNum()))
                {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean isSameString(String str1,String str2)
    {
        if(StringUtils.isEmpty(str1))
        {
            if(StringUtils.isEmpty(str2))
                return true;
            else
                return false;
        }
        else
        {
            return str1.equals(str2);
        }
    }

    /**
     * 提交订单取消请求
     *
     * @param orderId
     */
    public boolean saveOrderCancelRequest(String orderId, String remark,String currentUsrId) throws ErpException{
        try{
            //检查订单是否可以取消
            if (orderId == null)
            {
                String errorMsg="取消订单时未提供订单号";
                logger.error(errorMsg);
                throw new ServiceParameterException(ExceptionConstant.SERVICE_ORDER_CANCEL_EXCEPTION,errorMsg);
            }
            Order order = orderhistDao.getOrderHistByOrderid(orderId);
            if (order == null)
            {
                String errorMsg="取消订单时未找到对应订单号："+orderId;
                logger.error(errorMsg);
                throw new ServiceParameterException(ExceptionConstant.SERVICE_ORDER_CANCEL_EXCEPTION,errorMsg);
            }
            if (!this.canDelete(order))
            {
                String errorMsg="已出库、已取消或已完成时不能取消。订单号："+orderId;
                logger.error(errorMsg);
                throw new ServiceException(ExceptionConstant.SERVICE_ORDER_CANCEL_EXCEPTION,errorMsg);
            }

            //现在的需求是：取消订单操作都要走流程
            /*if(currentUsrId.equals(order.getCrusr()))
            {
                this.updateOrderCancel(null,orderId,remark, currentUsrId);
                return false;
            }
            else*/
            {
                //调用订单取消流程
                UserBpm userBpm = new UserBpm();
                userBpm.setCreateDate(new Date());
                userBpm.setCreateUser(currentUsrId);
                userBpm.setBusiType(String.valueOf(AuditTaskType.ORDERCANCEL.getIndex()));
                userBpm.setOrderID(order.getOrderid());
                userBpm.setContactID(order.getContactid());
                try{
                    userBpm.setDepartment(userService.getDepartment(order.getCrusr()));
                }catch (NamingException exp)
                {
                    logger.error("can't get department info:"+order.getCrusr(),exp);
                    throw new ServiceException(ExceptionConstant.SERVICE_ORDER_CANCEL_EXCEPTION,"无法获取订单创建人部门信息:"+exp.getMessage());
                }
                userBpm.setWorkGrp(userService.getUserGroup(order.getCrusr()));
                userBpm.setStatus(String.valueOf(AuditTaskStatus.UNASSIGNED.getIndex()));
                String batchId = changeRequestService.createChangeRequest(userBpm);

                UserBpmTask userBpmTask = new UserBpmTask();
                //userBpmTask.setChangeObjID(order.getOrderid());
                userBpmTask.setUpdateDate(userBpm.getCreateDate());
                userBpmTask.setUpdateUser(currentUsrId);
                userBpmTask.setRemark(remark);
                userBpmTask.setBatchID(batchId);
                userBpmTask.setStatus(String.valueOf(AuditTaskStatus.UNASSIGNED.getIndex()));

                userBpmTaskService.createApprovingTask(userBpmTask);

                return true;
            }
        }catch (RuntimeException runtimeExp)
        {
            logger.error("saveOrderCancelRequest error:",runtimeExp);
            throw new ServiceException(ExceptionConstant.SERVICE_ORDER_CANCEL_EXCEPTION,ExceptionMsgUtil.getMessage(runtimeExp),runtimeExp);
        }
    }

    /**
     * 审核确认订单取消请求
     *
     * @param orderId
     */
    public void updateOrderCancel(String procInstId, String orderId, String remark, String currentUsrId) throws ErpException{
        try{
            //检查订单是否可以取消
            if (orderId == null)
            {
                String errorMsg="取消订单时未提供订单号";
                logger.error(errorMsg);
                throw new ServiceParameterException(ExceptionConstant.SERVICE_ORDER_CANCEL_EXCEPTION,errorMsg);
            }
            Order order = orderhistDao.getOrderHistByOrderid(orderId);
            cancelOrderInner(currentUsrId, order);

            if(StringUtils.isNotEmpty(procInstId))
            {
                //结束流程
                try
                {
                    userBpmTaskService.approveChangeRequest(null,currentUsrId,procInstId,remark);
                }catch (Exception exp)
                {
                    logger.error("updateOrderCancel userBpmTaskService.approveChangeRequest error:",exp);
                    throw new RuntimeException(exp.getMessage(),exp);
                }
            }

            //
            changeRequestService.cancelUnCompletedOrderChangeRequest(orderId);
        }catch (RuntimeException runtimeExp)
        {
            logger.error("updateOrderCancel error:",runtimeExp);
            throw new ServiceException(ExceptionConstant.SERVICE_ORDER_CANCEL_EXCEPTION,ExceptionMsgUtil.getMessage(runtimeExp),runtimeExp);
        }
    }

    private void cancelOrderInner(String currentUsrId, Order order) throws ServiceParameterException, ServiceException {
        String orderId=order.getOrderid();
        /*if (order == null)
        {
            String errorMsg="取消订单时未找到对应订单号："+orderId;
            logger.error(errorMsg);
            throw new ServiceParameterException(ExceptionConstant.SERVICE_ORDER_CANCEL_EXCEPTION,errorMsg);
        }*/
        if (!this.canDelete(order))
        {
            String errorMsg="已出库、已取消或已完成时不能取消。订单号："+orderId;
            logger.error(errorMsg);
            throw new ServiceException(ExceptionConstant.SERVICE_ORDER_CANCEL_EXCEPTION,errorMsg);
        }
        if(AccountStatusConstants.ACCOUNT_CANCEL.equals(order.getStatus()))
        {
            String errorMsg="已取消。订单号："+orderId;
            logger.error(errorMsg);
            throw new ServiceException(ExceptionConstant.SERVICE_ORDER_CANCEL_EXCEPTION,errorMsg);
        }

        String jifen=order.getJifen();
        Order orderFree=new Order();
        BeanUtils.copyProperties(order, orderFree);
        orderFree.setMdusr(currentUsrId);

        try {
            this.deleteOrderLogic(orderFree);
        } catch (Exception exp) {
            logger.error("updateOrderCancel this.deleteOrderLogic error:",exp);
            throw new RuntimeException(exp.getMessage(),exp);
        }

        //调用积分规则
        //首先提交原先的修改
        callJifenCancel(order,jifen,currentUsrId);
    }

    private void callJifenCancel(Order order,String strjifen, String usrId) {

        if(StringUtils.isEmpty(strjifen))
            return;

        try{
            Integer jifen=Integer.parseInt(strjifen);
            if(jifen!=null&&jifen.intValue()>0)
            {
                orderhistDao.flush();
                Map<String, Object> params=new Hashtable<String, Object>();
                params.put("sorderid",order.getOrderid());
                params.put("scontactid",order.getContactid());
                params.put("npoint",jifen);
                params.put("scrusr",usrId);
                params.put("stype","1");
                pvService.addPvByOrder(params);
            }
            //conpointcrService.conpointfeedback(orderId,usrId);
        }catch (Exception exp)
        {
            throw new RuntimeException(exp.getMessage());
        }

    }

    private void callJifenModify(Order order) {
        Map<String, Object> params = new HashMap<String, Object>();

        Integer jifen=Integer.parseInt(order.getJifen())*10;
        params.put("stype", 0);
        params.put("sorderid", order.getOrderid());
        params.put("scontactid", order.getGetcontactid());
        params.put("npoint", jifen);
        params.put("scrusr", order.getMdusr());

        pvService.addPvByOrder(params);
    }

    private OrderChange getOrderChange(Order order, Address address, Contact contact, List<Phone> phoneList, Card creditCard,  Card identityCard) {
        Order orderOrg = orderhistDao.get(order.getId());
        //检查订单是否改变
        OrderChange orderChange = getOrderChangeFromOrder(orderOrg,order);
        //OrderdetChange orderdetChange=null;
        ContactChange contactChange = null;
        AddressChange addressChange = null;
        AddressExtChange addressExtChange = null;

        //检查订单明细是否改变
        if (order.getOrderdets() == null || order.getOrderdets().size() == 0) {
            //表示明细没有变化
        } else {
            if (checkOrderdetChange(order.getOrderdets(), orderOrg.getOrderdets())) {
                for (OrderDetail orderDetail : order.getOrderdets()) {
                    OrderdetChange orderdetChange = new OrderdetChange();
                    BeanUtils.copyProperties(orderDetail, orderdetChange);
                    orderChange.getOrderdetChanges().add(orderdetChange);
                    orderdetChange.setOrderChange(orderChange);
                }
            }
        }

        //检查联系人是否改变(有可能只是更换联系人)
        List<String> changeFldList = null;
        String getContactId=null;
        if (contact != null) {
            getContactId=contact.getContactid();
            if(orderOrg.getGetcontactid().equals(contact.getContactid()))
            {
                Contact contactOrg = contactDao.get(contact.getContactid());
                changeFldList = FieldValueCompareUtil.compare(contactOrg, contact, null);
                if (changeFldList.size() > 0) {
                    //产生新的对象
                    Contact contactModify = new Contact();
                    FieldValueCompareUtil.copyFlds(contact, contactModify, changeFldList);
                    contactChange = new ContactChange();
                    BeanUtils.copyProperties(contactModify, contactChange);
                    contactChange.setContactid(contact.getContactid());
                }
            }
            else
            {
                //只是更改了联系人
                if(this.isNeedAuditFlag(contact.getState()))
                {
                    contactChange=new ContactChange();
                    BeanUtils.copyProperties(contact,contactChange);
                    orderChange.setGetContactChange(contactChange);
                    contactChange.setOrderChange(orderChange);
                    contact.setState(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITING);
                    contactDao.saveOrUpdate(contact);
                }
                else
                {
                    contactChange = new ContactChange();
                    contactChange.setContactid(contact.getContactid());
                    contactChange.setContacttype("-9");
                }
            }
        }

        List<PhoneChange> phoneChangeList=new ArrayList<PhoneChange>();

        //处理原先的存在的电话
        if(StringUtils.isNotEmpty(getContactId))
        {
            List<Phone> orgPhoneList= phoneService.getPhonesByContactId(getContactId);
            if(orgPhoneList!=null)
            {
                for(Phone phone:orgPhoneList)
                {
                    if(this.isNeedAuditFlag(phone.getState()))
                    {
                        if(phoneList==null)
                        {
                            phoneList=new ArrayList<Phone>();
                        }
                        phoneList.add(phone);
                    }
                }
            }
        }

        if (phoneList != null) {
            for(Phone phone:phoneList)
            {
                PhoneChange phoneChange = null;
                if(phone.getPhoneid()!=null)
                {
                    Phone phoneOrg = phoneService.get(phone.getPhoneid());
                    if(this.isNeedAuditFlag(phoneOrg.getState()))
                    {
                        phoneChange=new PhoneChange();
                        BeanUtils.copyProperties(phoneOrg,phoneChange);
                        phoneOrg.setState(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITING);
                        phoneService.updatePhoneDirect(phoneOrg);

                        phoneChangeList.add(phoneChange);
                    }
                    else
                    {
                        changeFldList = FieldValueCompareUtil.compare(phoneOrg, phone, null);
                        if (changeFldList.size() > 0) {
                            //产生新的对象

                            Phone phoneModify = new Phone();
                            FieldValueCompareUtil.copyFlds(phone, phoneModify, changeFldList);
                            phoneChange = new PhoneChange();
                            BeanUtils.copyProperties(phoneModify, phoneChange);
                            phoneChange.setPhoneid(phone.getPhoneid());

                            phoneChangeList.add(phoneChange);
                        }
                    }
                }
                else
                {
                    phoneChange = new PhoneChange();
                    BeanUtils.copyProperties(phone, phoneChange);
                    phoneChangeList.add(phoneChange);
                }
            }
        }
        if(phoneChangeList.size()>0)
        {
            if (contactChange == null) {
                contactChange = new ContactChange();
                contactChange.setContactid(order.getGetcontactid());
            }
            for(PhoneChange phoneChange:phoneChangeList)
            {
                contactChange.getPhoneChanges().add(phoneChange);
                phoneChange.setContactChange(contactChange);
            }
        }

        orderChange.setGetContactChange(contactChange);

        //检查联系地址是否改变
        if(order.getAddress()!=null)
        {
            boolean bAuditFlag=false;
            if(StringUtils.isNotEmpty(order.getAddress().getAddressId()))
            {
                AddressExt addressExtNew=addressExtService.getAddressExt(order.getAddress().getAddressId());
                if(this.isNeedAuditFlag(addressExtNew.getAuditState()))
                {
                    bAuditFlag=true;
                    addressExtChange=new AddressExtChange();
                    BeanUtils.copyProperties(addressExtNew,addressExtChange);
                    orderChange.setAddress(addressExtChange);
                    addressExtChange.setOrderChange(orderChange);
                    addressExtNew.setAuditState(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITING);
                    addressExtService.saveOrUpdate(addressExtNew);
                }
            }
            if(bAuditFlag==false)
            {
                AddressExt addressExtOrg = orderOrg.getAddress();// addressExtService.getAddressExt(order.getAddress().getAddressId());
                if(StringUtils.isNotEmpty(order.getAddress().getAddressId())&&!order.getAddress().getAddressId().equals(addressExtOrg.getAddressId()))
                {
                    //直接替换地址
                    //重选了地址(注意可能替换的地址也修改了)
                    addressExtChange = new AddressExtChange();
                    addressChange = new AddressChange();
                    addressExtChange.setAddressId(order.getAddress().getAddressId());
                    addressChange.setAddressid(order.getAddress().getAddressId());
                    addressExtChange.setAddressChange(addressChange);
                }
                else
                {
                    if(addressExtOrg.getAddressDesc().equals(order.getAddress().getAddressDesc()))
                    {
                        logger.debug("equals");
                    }
                    changeFldList = FieldValueCompareUtil.compare(order.getAddress(), addressExtOrg,  null);
                    if (changeFldList.size() > 0) {
                        AddressExt addressExtModify = new AddressExt();
                        FieldValueCompareUtil.copyFlds(order.getAddress(), addressExtModify, changeFldList);
                        addressExtChange = new AddressExtChange();
                        addressChange = new AddressChange();
                        BeanUtils.copyProperties(addressExtModify, addressExtChange);
                        addressExtChange.setAddressId(order.getAddress().getAddressId());
                        addressChange.setAddressid(order.getAddress().getAddressId());
                        addressExtChange.setAddressChange(addressChange);
                    }
                }
            }
            /*else
            {
                //重选了地址(注意可能替换的地址也修改了)
                addressExtChange = new AddressExtChange();
                addressChange = new AddressChange();
                addressExtChange.setAddressId(order.getAddress().getAddressId());
                addressChange.setAddressid(order.getAddress().getAddressId());
                addressExtChange.setAddressChange(addressChange);
            }*/
        }

        //信用卡修改对象
        getCardChangesFromCards(orderChange,creditCard,identityCard);
        //checkIdentityCardAudit(orderOrg,orderChange,creditCard);
        orderChange.setAddress(addressExtChange);
        orderChange.setMdusr(order.getMdusr());
        return orderChange;
    }

    private void checkIdentityCardAudit(Order order,OrderChange orderChange,Card creditCard)
    {
        if("2".equals(order.getPaytype()))
        {
            String contactId=order.getPaycontactid();
            if(creditCard!=null)
            {
                contactId=creditCard.getContactId();
            }
            else
            {
                if(orderChange.getGetContactChange()!=null&&StringUtils.isNotEmpty(orderChange.getGetContactChange().getContactid()))
                {
                    contactId=orderChange.getGetContactChange().getContactid();
                }
            }
            if(StringUtils.isNotEmpty(contactId))
            {
                List<Card> cardList=cardService.getAllIdentityCardByContact(creditCard.getContactId());
                if(cardList!=null&&cardList.size()>0)
                {
                    //排除掉已经处理的
                    for(Card cardIdentity:cardList)
                    {
                        if(this.isNeedAuditFlag(cardIdentity.getState()))
                        {
                            CardChange cardChangeardIdentity = new CardChange();
                            BeanUtils.copyProperties(cardIdentity, cardChangeardIdentity);
                            cardIdentity.setState(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITING);

                            try{
                                cardService.updateCardState(cardIdentity.getCardId(),CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITING);
                                //cardService.merxge(cardIdentity);
                            }catch(Exception exp)
                            {
                                throw new RuntimeException(exp.getMessage());
                            }

                            orderChange.getCardChanges().add(cardChangeardIdentity);
                        }
                    }
                }
            }
        }
    }

    private void getCardChangesFromCards(OrderChange orderChange, Card creditCard,  Card identityCard)
    {
        Order orderOrg=this.getOrderhist(orderChange.getId());

        CardChange cardChange=getCardChangeFromCard(orderOrg,creditCard,true);
        if(cardChange!=null)
        {
            orderChange.getCardChanges().add(cardChange);
            cardChange.setOrderChange(orderChange);
        }

        cardChange=getCardChangeFromCard(orderOrg,identityCard,false);
        if(cardChange!=null)
        {
            orderChange.getCardChanges().add(cardChange);
            cardChange.setOrderChange(orderChange);
        }
    }

    private CardChange getCardChangeFromCard(Order order,Card card,boolean bRelate)
    {
        if(card==null)
            return null;
        CardChange cardChange=null;
        if(card.getCardId()==null)
        {
            cardChange=new CardChange();
            BeanUtils.copyProperties(card,cardChange);
        }else
        {
            Card cardNew=cardService.getCard(card.getCardId());
            if(this.isNeedAuditFlag(cardNew.getState()))
            {
                cardChange = new CardChange();
                BeanUtils.copyProperties(cardNew, cardChange);
                cardNew.setState(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITING);

                try{
                    cardService.updateCardState(cardNew.getCardId(),CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITING);
                    //cardService.merxge(cardNew);
                }catch(Exception exp)
                {
                    throw new RuntimeException(exp.getMessage());
                }
            }
            else
            {
                cardChange=new CardChange();
                //首先取得卡信息
                Card cardOrg=null;
                if(bRelate==true)
                {
                    if(StringUtils.isNotEmpty(order.getCardnumber()))
                    {
                        cardOrg=cardService.getCard(Long.parseLong(order.getCardnumber()));
                    }
                }
                else
                {
                    cardOrg=cardService.getCard(card.getCardId());
                }
                if(cardOrg!=null)
                {
                    List<String> changeFldList=FieldValueCompareUtil.compare(cardOrg,card,null);
                    if(changeFldList.size()>0)
                    {
                        Card cardModify=new Card();
                        FieldValueCompareUtil.copyFlds(card, cardModify, changeFldList);
                        BeanUtils.copyProperties(cardModify, cardChange);
                        cardChange.setCardId(card.getCardId());
                    }
                    else
                    {
                        return null;
                    }
                }
                else
                {
                    //新增
                    cardChange=new CardChange();
                    BeanUtils.copyProperties(card,cardChange);
                }
            }
        }
        return cardChange;
    }

    private boolean checkOrderdetChange(Set<OrderDetail> orderDetailSetOrg, Set<OrderDetail> orderDetailSetNew) {
        if (orderDetailSetOrg.size() != orderDetailSetNew.size())
            return true;

        List<String> ignoreFldNameList = new ArrayList<String>();
        ignoreFldNameList.add("orderhist");
        for (OrderDetail orderDetailOrg : orderDetailSetOrg) {
            boolean bFound = false;
            for (OrderDetail orderDetailNew : orderDetailSetNew) {
                if (isSameProduct(orderDetailOrg, orderDetailNew)) {
                    bFound = true;
                    List<String> changeFldList = FieldValueCompareUtil.compare(orderDetailNew, orderDetailOrg, ignoreFldNameList);
                    if (changeFldList.size() > 0)
                        return true;
                    break;
                }
            }
            if (bFound == false)
                return true;
        }
        return false;
    }
    
    private boolean isSameProduct(OrderDetail source, OrderDetail target) {
		return StringUtils.equals(source.getProdid(), target.getProdid())
				&& StringUtils.equals(source.getProducttype(), target.getProducttype());
	}

    private OrderChange getOrderChangeFromOrder(Order orderOrg, Order orderNew) {
        OrderChange orderChange = new OrderChange();
        orderChange.setId(orderOrg.getId());
        orderChange.setOrderid(orderOrg.getOrderid());
        orderChange.setMddt(orderNew.getMddt());
        orderChange.setMdusr(orderNew.getMdusr());

        if (!FieldValueCompareUtil.equals(orderOrg.getEntityid(), orderNew.getEntityid())) {
            orderChange.setEntityid(orderNew.getEntityid());
        }
        if (!FieldValueCompareUtil.equals(orderOrg.getSpellid(), orderNew.getSpellid())) {
            orderChange.setSpellid(orderNew.getSpellid());
        }
        if (!FieldValueCompareUtil.equals(orderOrg.getMailtype(), orderNew.getMailtype())) {
            orderChange.setMailtype(orderNew.getMailtype());
        }
        if (!FieldValueCompareUtil.equals(orderOrg.getPaytype(), orderNew.getPaytype())) {
            orderChange.setPaytype(orderNew.getPaytype());
        }
        if (!FieldValueCompareUtil.equals(orderOrg.getUrgent(), orderNew.getUrgent())) {
            orderChange.setUrgent(orderNew.getUrgent());
        }
        if (!FieldValueCompareUtil.equals(orderOrg.getTotalprice(), orderNew.getTotalprice())) {
            orderChange.setTotalprice(orderNew.getTotalprice());
        }
        if (!FieldValueCompareUtil.equals(orderOrg.getMailprice(), orderNew.getMailprice())) {
            orderChange.setMailprice(orderNew.getMailprice());
        }
        if (!FieldValueCompareUtil.equals(orderOrg.getProdprice(), orderNew.getProdprice())) {
            orderChange.setProdprice(orderNew.getProdprice());
        }
        if (!FieldValueCompareUtil.equals(orderOrg.getClearfee(), orderNew.getClearfee())) {
            orderChange.setClearfee(orderNew.getClearfee());
        }
        if (!FieldValueCompareUtil.equals(orderOrg.getBill(), orderNew.getBill())) {
            orderChange.setBill(orderNew.getBill());
        }

        if (!FieldValueCompareUtil.equals(orderOrg.getNote(), orderNew.getNote())) {
            orderChange.setNote(orderNew.getNote());
        }
        if (!FieldValueCompareUtil.equals(orderOrg.getCardtype(), orderNew.getCardtype())) {
            orderChange.setCardtype(orderNew.getCardtype());
        }
        if (!FieldValueCompareUtil.equals(orderOrg.getCardnumber(), orderNew.getCardnumber())) {
            orderChange.setCardnumber(orderNew.getCardnumber());
        }
        if (!FieldValueCompareUtil.equals(orderOrg.getJifen(), orderNew.getJifen())) {
            orderChange.setJifen(orderNew.getJifen());
        }
        if (!FieldValueCompareUtil.equals(orderOrg.getSpid(), orderNew.getSpid())) {
            orderChange.setSpid(orderNew.getSpid());
        }
        if (!FieldValueCompareUtil.equals(orderOrg.getInvoicetitle(), orderNew.getInvoicetitle())) {
            orderChange.setInvoicetitle(orderNew.getInvoicetitle());
        }

        String strOrg=StringUtils.isNotEmpty(orderOrg.getReqEMS())?orderOrg.getReqEMS():"N";
        String strNew=StringUtils.isNotEmpty(orderNew.getReqEMS())?orderNew.getReqEMS():"N";
        if (!strNew.equalsIgnoreCase(strOrg)) {
            orderChange.setIsreqems(strNew);
        }
        if (!FieldValueCompareUtil.equals(orderOrg.getReqUsr(), orderNew.getReqUsr())) {
            orderChange.setIsrequsr(orderNew.getReqUsr());
        }

        if (!FieldValueCompareUtil.equals(orderOrg.getPostfee(), orderNew.getPostfee())) {
            orderChange.setPostFee(orderNew.getPostfee());
        }

        if (!FieldValueCompareUtil.equals(orderOrg.getOrdertype(), orderNew.getOrdertype())) {
            orderChange.setOrdertype(orderNew.getOrdertype());
        }

        if(!FieldValueCompareUtil.equals(orderOrg.getLaststatus(),orderNew.getLaststatus())){
            orderChange.setLaststatus(orderNew.getLaststatus());
        }
        return orderChange;
    }

    @Autowired
    private ShipmentChangeHisDao shipmentChangeHisDao;
    @Autowired
    private OrderUrgentApplicationDao orderUrgentApplicationDao;
    public List<ShipmentChangeHis> queryShipmentFromOrderId(String orderId) throws ErpException
    {
        try
        {
            //首先取得有效的订单版本，然后获取变化历史记录
            ShipmentHeader shipmentHeader=shipmentHeaderService.getShipmentHeaderFromOrderId(orderId);
            if(shipmentHeader==null)
            {
                return new ArrayList<ShipmentChangeHis>();
            }
            else
            {
                List<ShipmentChangeHis> shipmentChangeHisList = shipmentChangeHisDao.findList("from ShipmentChangeHis where shipmentId=:shipmentId",new ParameterString("shipmentId", shipmentHeader.getShipmentId()));
                if(shipmentChangeHisList==null)
                    return getOrderProblemOrUrgentChanges(orderId);
                else
                {
                    //将催送货和问题单处理添加进来
                    shipmentChangeHisList.addAll(getOrderProblemOrUrgentChanges(orderId));
                    Collections.sort(shipmentChangeHisList);
                    return shipmentChangeHisList;
                }
            }
        }catch (RuntimeException runtimeExp)
        {
            logger.error("queryShipmentFromOrderId error:",runtimeExp);
            throw new ServiceException(ExceptionConstant.SERVICE_ORDER_QUERY_EXCEPTION,ExceptionMsgUtil.getMessage(runtimeExp),runtimeExp);
        }
    }

    private List<ShipmentChangeHis> getOrderProblemOrUrgentChanges(String orderId)
    {
        List<ShipmentChangeHis> shipmentChangeHisList=new ArrayList<ShipmentChangeHis>();
        OrderUrgentApplication orderUrgentApplication = orderUrgentApplicationDao.find("from OrderUrgentApplication where orderid=:orderid", new ParameterString("orderid",orderId));
        if(orderUrgentApplication!=null)
        {
            if(orderUrgentApplication.getAppdate()!=null)
            {
                ShipmentChangeHis shipmentChangeHis=new ShipmentChangeHis();
                shipmentChangeHis.setBeforeAccountStatusId("");
                shipmentChangeHis.setAfterAccountStatusId("");
                shipmentChangeHis.setBeforeLogisticsStatusId("");
                shipmentChangeHis.setAfterLogisticsStatusId(LogisticsStatusEnums.LOGISTICS_URGENT_APPLICATION.getCode());
                shipmentChangeHis.setDateTimeStamp(orderUrgentApplication.getAppdate());
                shipmentChangeHis.setUserStamp(orderUrgentApplication.getApppsn());
                shipmentChangeHisList.add(shipmentChangeHis);
            }
            if(orderUrgentApplication.getChkdate()!=null)
            {
                ShipmentChangeHis shipmentChangeHis=new ShipmentChangeHis();
                shipmentChangeHis.setBeforeAccountStatusId("");
                shipmentChangeHis.setAfterAccountStatusId("");
                shipmentChangeHis.setBeforeLogisticsStatusId("");
                shipmentChangeHis.setAfterLogisticsStatusId(LogisticsStatusEnums.LOGISTICS_URGENT_CHECK.getCode());
                shipmentChangeHis.setDateTimeStamp(orderUrgentApplication.getChkdate());
                shipmentChangeHis.setUserStamp(orderUrgentApplication.getChkpsn());
                shipmentChangeHisList.add(shipmentChangeHis);
            }
            if(orderUrgentApplication.getEnddate()!=null)
            {
                ShipmentChangeHis shipmentChangeHis=new ShipmentChangeHis();
                shipmentChangeHis.setBeforeAccountStatusId("");
                shipmentChangeHis.setAfterAccountStatusId("");
                shipmentChangeHis.setBeforeLogisticsStatusId("");
                shipmentChangeHis.setAfterLogisticsStatusId(LogisticsStatusEnums.LOGISTICS_URGENT_END.getCode());
                shipmentChangeHis.setDateTimeStamp(orderUrgentApplication.getEnddate());
                shipmentChangeHis.setUserStamp(orderUrgentApplication.getEndpsn());
                shipmentChangeHisList.add(shipmentChangeHis);
            }
        }

        return shipmentChangeHisList;
    }

    public BigDecimal calcOrderMailPrice(Order order, String departmentId) throws ErpException
    {
        try
        {
            if(order==null||order.getOrderdets()==null||order.getOrderdets().size()==0)
            {
                throw new ServiceException(ExceptionConstant.SERVICE_ORDER_UPDATE_EXCEPTION,"未提供订单或明细");
            }
            if(order.getAddress()==null|| StringUtils.isEmpty(order.getAddress().getAddressId()))
            {
                throw new ServiceException(ExceptionConstant.SERVICE_ORDER_UPDATE_EXCEPTION,"未提供订单地址信息");
            }
            //计算订单运费
            if(order.getOrderdets()!=null)
            {
                StringBuilder stringBuilder = new StringBuilder();
                for(OrderDetail orderDetail:order.getOrderdets())
                {
                    stringBuilder.append("~");
                    stringBuilder.append(orderDetail.getProdid());
                    stringBuilder.append("^");
                    stringBuilder.append(orderDetail.getSoldwith());
                    stringBuilder.append("^");
                    int upNum=orderDetail.getUpnum()!=null?orderDetail.getUpnum():0;
                    int spNum=orderDetail.getSpnum()!=null?orderDetail.getSpnum():0;
                    int totalNum=upNum+spNum;
                    stringBuilder.append(totalNum);
                    stringBuilder.append("^");
                    stringBuilder.append(orderDetail.getUprice()!=null?orderDetail.getUprice().toString():"0");
                    stringBuilder.append("^");
                    stringBuilder.append(upNum);
                    stringBuilder.append("^");
                    stringBuilder.append(orderDetail.getSprice()!=null?orderDetail.getSprice().toString():"0");
                    stringBuilder.append("^");
                    stringBuilder.append(spNum);
                    stringBuilder.append("^");
                    stringBuilder.append(orderDetail.getProdname()) ;
                    stringBuilder.append("^");
                    //stringBuilder.append(orderDetail.getProdname());
                    stringBuilder.append(departmentId);
                    stringBuilder.append("^");
                    //
                    if(SELF_ORDER_PAYTYPE.equals(order.getPaytype()))
                        stringBuilder.append("4");//原先的上门自提使用mailtype=4
                    else
                        stringBuilder.append(order.getMailtype());
                    stringBuilder.append("^");
                    stringBuilder.append(order.getPaytype());
                    stringBuilder.append("^");
                    stringBuilder.append(order.getOrdertype());
                    stringBuilder.append("^");
                    stringBuilder.append(order.getContactid());
                    stringBuilder.append("^");
                    stringBuilder.append(order.getAddress().getAddressId());
                    stringBuilder.append("^");
                    stringBuilder.append(order.getCrusr());//TODO:是否订单创建人

                    stringBuilder.append("^");
                    String amortization = "0";
                    if(StringUtils.isNotEmpty(order.getLaststatus()))
                    {
                        amortization=order.getLaststatus();
                    }
                    stringBuilder.append(amortization);
                    stringBuilder.append("^");
                }

                List<Object> inParams=new ArrayList<Object>();
                inParams.add(stringBuilder.toString());
                try
                {
                    String result= sqlDao.exeFuncProcedure(schemaNames.getAgentSchema()+"fun_getordertransexpense_ex2", inParams);
                    if(StringUtils.isNotEmpty(result))
                    {
                        BigDecimal decimal = new BigDecimal(result);
                        return decimal.setScale(0, BigDecimal.ROUND_HALF_UP);
                    }
                }catch (SQLException sqlExp)
                {
                     throw new RuntimeException(sqlExp.getMessage(),sqlExp);
                }
            }
            return null;
        }catch (RuntimeException runtimeExp)
        {
            logger.error("calcOrderMailPrice error:",runtimeExp);
            throw new ServiceException(ExceptionConstant.SERVICE_ORDER_UPDATE_EXCEPTION,ExceptionMsgUtil.getMessage(runtimeExp),runtimeExp);
        }
    }

    @Autowired
    private NamesDao namesDao;

    @Autowired
    private GrpOrderTypeDao grpOrderTypeDao;

    public List<GrpOrderType> queryGrpOderType(String grpId) throws ErpException
    {
        return grpOrderTypeDao.findList("from GrpOrderType where grpid=:grpid",new ParameterString("grpid",grpId));
        //return "40";
        /*Names names= namesDao.find("from Names where id.tid=:tid and id.id=:grpId", new ParameterString("tid",""),new ParameterString("grpId",grpId));
        if(names!=null)
            return names.getDsc();
        return null;*/
    }

    private boolean checkOrderCardFromType(Order order,String cardType)
    {
        //判断建行或者招行卡类型
        if(!cardTypeCheckList.contains(cardType))
        {
            return true;
        }
        if(order.getPaycontactid()==null||order.getGetcontactid()==null)
            return false;
        if(order.getPaycontactid().equals(order.getGetcontactid()))
            return true;
        else
        {
            Contact payContact=contactDao.get(order.getPaycontactid());
            Contact getContact=contactDao.get(order.getGetcontactid());
            if(payContact!=null&&StringUtils.isNotEmpty(payContact.getName())
                    &&getContact!=null&&StringUtils.isNotEmpty(getContact.getName()))
            {
                if(payContact.getName().equals(getContact.getName()))
                    return true;
            }
        }

        return true;
    }
    public boolean checkOrderCard(Order order) throws ErpException
    {
        //判断建行或者招行卡类型
        if("2".equals(order.getPaytype()))
        {
            if(StringUtils.isNotEmpty(order.getCardnumber()))
            {
                Card card= cardService.getCard(Long.parseLong(order.getCardnumber()));
                return this.checkOrderCardFromType(order,card.getType());
            }
        }
        return true;
    }

    @Autowired
    private AmortisationDao amortisationDao;

    public List<Amortisation> queryCardAmortisation(String cardType) throws ErpException
    {
        List<Amortisation> amortisationList=amortisationDao.queryCardTypeAmortisations(cardType);
        if(amortisationList==null)
            return new ArrayList<Amortisation>();
        //然后排序
        Collections.sort(amortisationList);
        return amortisationList;
    }


    /*public boolean isNeedApprovalProcedure(Order order, Address address, Contact contact, List<Phone> phoneList) throws ErpException
    {
        OrderChange orderChange = this.getOrderChange(order, address, contact, phoneList,null,null);
        if(orderChange==null)
            return false;
        if(orderChange.getGetContactChange()!=null)
            return true;
        if(orderChange.getAddress()!=null)
            return true;
        //检查订单里面的字段，如果有需要记录的修改，那么就需要审批
        if(orderChange!=null)
        {
             return true;
        }

        return false;
    }*/

    public Map<Order,List<String>> checkOrderRights(List<Order> orderList,String currentUsrId)
    {
        //TODO:目前只假设检查当前用户，后期添加主管的权限
        if(orderList==null)
            return null;
        Map<Order,List<String>> map=new Hashtable<Order, List<String>>();
        for(Order order:orderList)
        {
            if(this.canDelete(order)&&this.canModify(order))
            {
                List<String> list=new ArrayList<String>();
                list.add(OrderRight.Delete);
                list.add(OrderRight.Modify);
                map.put(order, list);
            }
            else if(this.canDelete(order))
            {
                List<String> list=new ArrayList<String>();
                list.add(OrderRight.Delete);
                list.add(OrderRight.View);
                map.put(order, list);
            }
            else if(this.canModify(order))
            {
                List<String> list=new ArrayList<String>();
                list.add(OrderRight.Modify);
                list.add(OrderRight.View);
                map.put(order, list);
            }
            else
            {
                List<String> list=new ArrayList<String>();
                list.add(OrderRight.View);
                map.put(order, list);
            }

            if(StringUtils.isEmpty(this.canConsult(order)))
            {
                List<String> list=map.get(order);
                list.add(OrderRight.Consult);
            }
        }
        return map;
    }

    private boolean canDelete(Order order)
    {
        if (AccountStatusConstants.ACCOUNT_CANCEL.equals(order.getStatus())
                || AccountStatusConstants.ACCOUNT_FINI.equals(order.getStatus())
                || AccountStatusConstants.ACCOUNT_REJECTION.equals(order.getStatus())
                || AccountStatusConstants.ACCOUNT_WMSCANCEL.equals(order.getStatus())
                || this.judgeOrderStatus(order.getCustomizestatus()))
            return false;
        //索权成功也可以取消订单
        /*if("-1".equals(order.getConfirm()))
            return false; */
        return true;
    }

    private boolean canModify(Order order)
    {
        if (AccountStatusConstants.ACCOUNT_CANCEL.equals(order.getStatus())
                || AccountStatusConstants.ACCOUNT_FINI.equals(order.getStatus())
                || AccountStatusConstants.ACCOUNT_REJECTION.equals(order.getStatus())
                || AccountStatusConstants.ACCOUNT_WMSCANCEL.equals(order.getStatus())
                || this.judgeOrderStatus(order.getCustomizestatus()))
            return false;
        //信用卡索权成功，也可以修改，只是金额不能改变
        //if("-1".equals(order.getConfirm()))
        //    return false;
        return true;
    }

    public boolean isOrderModifiable(String orderId)
    {
        Order order=this.getOrderHistByOrderid(orderId);
        if(order==null)
            return false;
        return this.canModify(order);
    }

    public boolean isOrderDeletable(String orderId)
    {
        Order order=this.getOrderHistByOrderid(orderId);
        if(order==null)
            return false;
        return this.canDelete(order);
    }


    public List<OrderAuditExtDto> queryAuditOrder(OrderAuditQueryDto orderAuditQueryDto) throws ErpException
    {
        try
        {
            if(orderAuditQueryDto.getBeginCrdt()==null)
            {
                if(StringUtils.isEmpty(orderAuditQueryDto.getOrderId()))
                {
                    String errorMsg="查询订单起始日期必须提供";
                    logger.error(errorMsg);
                    throw new ServiceParameterException(ExceptionConstant.SERVICE_ORDER_QUERY_EXCEPTION,errorMsg);
                }
            }
            else
            {
                Date endDate=orderAuditQueryDto.getEndCrdt();
                if(endDate==null)
                    endDate=new Date();
                if(DateUtil.calcDiffJustDays(endDate,orderAuditQueryDto.getBeginCrdt()).compareTo(orderQueryDays)>0)
                {
                    String errorMsg="查询订单起始日期应该在";
                    errorMsg+=orderQueryDays;
                    errorMsg+="天内";
                    logger.error(errorMsg);
                    throw new ServiceParameterException(ExceptionConstant.SERVICE_ORDER_QUERY_EXCEPTION,errorMsg);
                }
            }

            if(orderAuditQueryDto.getContactIdList()==null)
                orderAuditQueryDto.setContactIdList(new ArrayList<String>());
            if (orderAuditQueryDto.getContactId() != null) {
                orderAuditQueryDto.getContactIdList().add(orderAuditQueryDto.getContactId());
            } else if (StringUtils.isNotEmpty(orderAuditQueryDto.getContactName())) {
                List<Contact> contactList = contactDao.findList("from Contact where name=:name", new ParameterString("name", orderAuditQueryDto.getContactName()));
                if (contactList != null && contactList.size() > 0) {
                    for (Contact contact : contactList)
                    {
                        if(!orderAuditQueryDto.getContactIdList().contains(contact.getContactid()))
                            orderAuditQueryDto.getContactIdList().add(contact.getContactid());
                    }
                }
                else
                {
                    return new ArrayList<OrderAuditExtDto>();
                }
            }

            return orderhistDao.queryAuditOrder(orderAuditQueryDto);

        }catch (RuntimeException runtimeExp)
        {
            logger.error("queryAuditOrder error:",runtimeExp);
            throw new ServiceException(ExceptionConstant.SERVICE_ORDER_QUERY_EXCEPTION,ExceptionMsgUtil.getMessage(runtimeExp),runtimeExp);
        }
    }

    public int queryOrderAuditTotalCount(OrderAuditQueryDto orderAuditQueryDto) throws ErpException
    {
       return orderhistDao.getAuditTotalCount(orderAuditQueryDto);
    }

    @Autowired
    private AddressExtChangeDao addressExtChangeDao;

    @Autowired
    private ContactChangeDao contactChangeDao;

    public boolean checkCorrelativeInBmp(String addressId, String contactId) throws ErpException
    {
        //首先检查地址变化表和联系人变化表，获取流程实例号，最后判断是否在走流程
        Date beginDate=this.getQueryBeginDate();
        List<String> procInstList=null;
        if(StringUtils.isNotEmpty(addressId))
        {
            procInstList=addressExtChangeDao.findProcInstFromAddressId(addressId,beginDate);
        }
        if(StringUtils.isNotEmpty(contactId))
        {
            if(procInstList==null)
            {
                procInstList=contactChangeDao.findProcInstFromContactId(contactId,beginDate);
            }
            else
            {
                List<String> list=contactChangeDao.findProcInstFromContactId(contactId,beginDate);
                if(list!=null)
                {
                    for(String procInstId:list)
                    {
                        if(!procInstList.contains(procInstId))
                        {
                            procInstList.add(procInstId);
                        }
                    }
                }
            }
        }
        if(procInstList!=null&&procInstList.size()>0)
        {
            List<Object> list=userBpmTaskService.filterPendingInstances(procInstList);
            if(list!=null&&list.size()>0)
                return true;
        }
        return false;
    }

    private SimpleDateFormat queryBeignDateFormat=new SimpleDateFormat("yyyy-MM-dd");
    private synchronized Date getQueryBeginDate()
    {
        Date dtNow=new Date();
        try{
            dtNow=queryBeignDateFormat.parse(queryBeignDateFormat.format(dtNow));
        }catch (Exception exp)
        {

        }
        return new Date(dtNow.getTime()-orderQueryDays*86400000L);
    }
    /*public List<OrderAuditQueryDto> queryAuditOrder(OrderAuditQueryDto orderAuditQueryDto)
    {
        //从流程表和订单表联合查询
        StringBuilder stringBuilder=new StringBuilder("from Order a,UserBpm b where ");
        //orderhistDao.findList();
        Map<String,Parameter> mapParms=new Hashtable<String, Parameter>();
        if(orderAuditQueryDto.getBeginCrdt()!=null)
        {
            stringBuilder.append(" a.crdt>=:beginCrdt");
            mapParms.put("",new ParameterString());
        }
        return null;
    }*/

    @Autowired
    private ConpointcrService conpointcrService;
    @Autowired
    private ConpointuseService conpointuseService;

    private boolean isNeedPhoneAudit(Order order)
    {
        List<Phone> phoneList= phoneDao.findList("from Phone where contactid=:contactid", new ParameterString("contactid",order.getGetcontactid()));
        if(phoneList!=null&&phoneList.size()>0)
        {
            for(Phone phone:phoneList)
            {
                if(this.isNeedAuditFlag(phone.getState()))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean createOrder(Order order, BigDecimal newMailPrice) throws ErpException
    {
        checkOrderOnCreate(order);
        //同时判断电话
        boolean isPhoneAudit=this.isNeedPhoneAudit(order);
        boolean bMailpriceChanged=(newMailPrice!=null&&newMailPrice.compareTo(order.getMailprice())!=0)?true:false;
        List<OrderRuleCheckService> orderRuleCheckServiceList = orderCheckService.attachOrderRules(order);
        if((orderRuleCheckServiceList!=null&&orderRuleCheckServiceList.size()>0)||isPhoneAudit==true || bMailpriceChanged==true )
        {
            order.setCheckResult(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITING);
        }
        else
        {
            order.setCheckResult(null);
        }

        try
        {
            //
            this.updateOrderhist(order);

            //最后处理积分
            if(StringUtils.isNotEmpty(order.getJifen()))
            {
                Integer jifen=Integer.parseInt(order.getJifen());
                if(jifen!=null&&jifen.intValue()>0)
                {
                    orderhistDao.flush();
                    Integer addJifen=jifen*10;
                    Map<String, Object> params=new Hashtable<String, Object>();
                    params.put("sorderid",order.getOrderid());
                    params.put("scontactid",order.getContactid());
                    params.put("npoint",addJifen);
                    params.put("scrusr",order.getCrusr());
                    params.put("stype","0");
                    pvService.addPvByOrder(params);
                }
            }

            if((orderRuleCheckServiceList!=null&&orderRuleCheckServiceList.size()>0) || isPhoneAudit==true)
            {
                return startupProcs(order, orderRuleCheckServiceList, newMailPrice);
            }
            else
            {
                //最后如果运费修改了，那么启动修改流程
                if(bMailpriceChanged==true)
                {
                    OrderChange orderChange=new OrderChange();
                    orderChange.setOrderid(order.getOrderid());
                    String usrId=this.getCurrentUsr();
                    if(StringUtils.isEmpty(usrId))
                    {
                        usrId=order.getCrusr();
                    }
                    orderChange.setMdusr(usrId);
                    orderChange.setMddt(new Date());
                    orderChange.setMailprice(newMailPrice);
                    orderChange.setId(order.getId());
                    List<OrderChange> orderChangeList=new ArrayList<OrderChange>();
                    orderChangeList.add(orderChange);
                    saveOrderModifyRequestAndBeginProcs(orderChangeList, AuditTaskType.ORDERCHANGE,  "", usrId, true);
                    return false;
                }
                else
                {
                    return true;
                }
            }
        }catch (Exception exp)
        {
            throw new ErpException(ExceptionConstant.SERVICE_ORDER_CREATE_EXCEPTION,exp.getMessage(),exp);
        }
    }

    public boolean startupProcs(Order order, List<OrderRuleCheckService> orderRuleCheckServiceList,BigDecimal newMailPrice) throws ErpException
    {
        List<UserBpmTaskType> userBpmTaskTypeList = getUserBpmTaskTypeFromRules(orderRuleCheckServiceList);
        OrderChange orderChange = this.cloneOrderChangeFromOrder(order,userBpmTaskTypeList);
        if(newMailPrice!=null&&newMailPrice.compareTo(order.getMailprice())!=0)
        {
            orderChange.setMailprice(newMailPrice);
        }
        List<OrderChange> orderChangeList=new ArrayList<OrderChange>();
        orderChangeList.add(orderChange);
        Map<String,List<String>> map=saveOrderModifyRequestAndBeginProcs(orderChangeList,AuditTaskType.ORDERADD,  "", order.getCrusr(),false);
        if(!map.isEmpty())
            return false;
        return true;
    }

    private List<UserBpmTaskType> getUserBpmTaskTypeFromRules(List<OrderRuleCheckService> orderRuleCheckServiceList) {
        List<UserBpmTaskType> userBpmTaskTypeList=new ArrayList<UserBpmTaskType>();
        if(orderRuleCheckServiceList==null)
            return userBpmTaskTypeList;
        for(OrderRuleCheckService orderRuleCheckService:orderRuleCheckServiceList)
        {
            if(StringUtils.isNotEmpty(orderRuleCheckService.getBPMType()))
            {
                int type=Integer.parseInt(orderRuleCheckService.getBPMType());
                for(UserBpmTaskType userBpmTaskType:UserBpmTaskType.values())
                {
                    if(type==userBpmTaskType.getIndex())
                    {
                        userBpmTaskTypeList.add(userBpmTaskType);
                        break;
                    }
                }
            }
        }
        return userBpmTaskTypeList;
    }

    @Autowired
    private PhoneService phoneService;

    @Autowired
    private CardChangeService cardChangeService;

    private OrderChange cloneOrderChangeFromOrder(Order order, List<UserBpmTaskType> userBpmTaskTypeList)
    {
        //
        //1.订单主体
        OrderChange orderChange=new OrderChange();
        orderChange.setCreateUser(order.getCrusr());
        orderChange.setMdusr(order.getCrusr());
        orderChange.setMddt(new Date());
        orderChange.setCreateDate(new Date());
        orderChange.setProdprice(order.getProdprice());
        orderChange.setTotalprice(order.getTotalprice());
        orderChange.setId(order.getId());
        orderChange.setOrderid(order.getOrderid());
        //BeanUtils.copyProperties(order,orderChange);
        //2.订单明细
        boolean bDetailChange=false;
        for(UserBpmTaskType userBpmTaskType:userBpmTaskTypeList)
        {
            if(userBpmTaskType.getIndex()==UserBpmTaskType.ORDER_CART_CHANGE.getIndex())
            {
                bDetailChange=true;
                break;
            }
            else if(userBpmTaskType.getIndex()==UserBpmTaskType.CONTACT_BASE_CHANGE.getIndex())
            {
                ContactChange contactChange=new ContactChange();
                contactChange.setContactid(order.getGetcontactid());
                //contactChange.setContacttype("-10");

                orderChange.setContactChange(contactChange);
                contactChange.setOrderChange(orderChange);
            }
        }
        if(bDetailChange==true)
        {
            for (OrderDetail orderDetail : order.getOrderdets()) {
                OrderdetChange orderdetChange = new OrderdetChange();
                BeanUtils.copyProperties(orderDetail, orderdetChange);
                orderChange.getOrderdetChanges().add(orderdetChange);
                orderdetChange.setOrderChange(orderChange);
            }
        }
        //3.各个关联对象
        Contact contact=contactDao.get(order.getGetcontactid());
        if(isNeedAuditFlag(contact.getState()))
        {
            ContactChange contactChange=new ContactChange();
            BeanUtils.copyProperties(contact,contactChange);
            orderChange.setGetContactChange(contactChange);
            contactChange.setOrderChange(orderChange);
            contact.setState(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITING);
            contactDao.saveOrUpdate(contact);
        }
        if(isNeedAuditFlag(order.getAddress().getAuditState()))
        {
            AddressExtChange addressExtChange=new AddressExtChange();
            BeanUtils.copyProperties(order.getAddress(),addressExtChange);
            orderChange.setAddress(addressExtChange);
            addressExtChange.setOrderChange(orderChange);
            order.getAddress().setAuditState(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITING);
            addressExtService.saveOrUpdate(order.getAddress());
        }
        //电话
        List<Phone> phoneList= phoneDao.findList("from Phone where contactid=:contactid", new ParameterString("contactid",order.getGetcontactid()));
        //检查电话是否有效
        if(phoneList!=null&&phoneList.size()>0)
        {
            for(Phone phone:phoneList)
            {
                if(isNeedAuditFlag(phone.getState()))
                {
                    PhoneChange phoneChange=new PhoneChange();
                    BeanUtils.copyProperties(phone,phoneChange);
                    phone.setState(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITING);
                    phoneService.updatePhoneDirect(phone);
                    if(orderChange.getGetContactChange()==null)
                    {
                        ContactChange getContactChange=new ContactChange();
                        getContactChange.setContactid(contact.getContactid());
                        getContactChange.setCreateUser(order.getCrusr());
                        getContactChange.setCreateDate(new Date());
                        orderChange.setGetContactChange(getContactChange);
                        getContactChange.setOrderChange(orderChange);
                    }
                    orderChange.getGetContactChange().getPhoneChanges().add(phoneChange);
                    phoneChange.setContactChange(orderChange.getGetContactChange());
                }
            }
        }

        //phoneService.findByContactId();

        List<CardChange> cardChangeList=cardChangeService.getCardChangesFromOrder(order);
        if(cardChangeList!=null&&cardChangeList.size()>0)
        {
            for(CardChange cardChange:cardChangeList)
            {
                orderChange.getCardChanges().add(cardChange);
                cardChange.setOrderChange(orderChange);
            }
        }

        for(UserBpmTaskType userBpmTaskType:userBpmTaskTypeList)
        {
            if(userBpmTaskType.getIndex()==UserBpmTaskType.ORDER_RECEIVER_CHANGE.getIndex())
            {
                if(orderChange.getGetContactChange()==null)
                {
                    ContactChange getContactChange=new ContactChange();
                    getContactChange.setContactid(order.getGetcontactid());
                    orderChange.setGetContactChange(getContactChange);
                    getContactChange.setOrderChange(orderChange);
                }
                break;
            }
        }
        return orderChange;
    }

    private boolean isNeedAuditFlag(Integer state)
    {
        //TODO:如何防止并发问题
        if(state!=null&&(state.intValue()== CustomerConstant.CUSTOMER_AUDIT_STATUS_UNAUDITED))
        {
            return true;
        }

        return false;
    }

    private boolean isInAuditFlag(Integer state)
    {
        if(state!=null&&state.intValue()==CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITING)
            return true;
        return false;
    }

    private void checkOrderdetChanges(Order order)
    {
        boolean bSet=false;
        for(OrderDetail orderDetail:order.getOrderdets())
        {
            orderDetail.setOrderid(order.getOrderid());
            orderDetail.setOrderhist(order);
            orderDetail.setState(order.getAddress().getProvince().getChinese());
            orderDetail.setCity(order.getAddress().getCity().getCityname());
            orderDetail.setProvinceid(order.getAddress().getProvince().getProvinceid());
            if(StringUtils.isEmpty(orderDetail.getOrderdetid()))
            {
                orderDetail.setOrderdetid(orderdetDao.getOrderdetId());
            }
            if(StringUtils.isEmpty(orderDetail.getStatus()))
            {
                orderDetail.setStatus(AccountStatusConstants.ACCOUNT_NEW);
            }
            orderDetail.setFreight(BigDecimal.ZERO);

            if("1".equals(orderDetail.getSoldwith()))
            {
                bSet=true;
                orderDetail.setFreight(order.getMailprice());
            }
        }
        //重设订单明细运费
        if(bSet==false)
        {
            for(OrderDetail orderDetail:order.getOrderdets())
            {
                if("3".equals(orderDetail.getSoldwith()))
                {
                    orderDetail.setFreight(order.getMailprice());
                    break;
                }
            }
        }

    }

    @Autowired
    private OrderCheckService orderCheckService;

    private void checkOrderOnCreate(Order order) throws ErpException
    {
        //首先检查订单类型（grpid）
        /*List<GrpOrderType> grpOrderTypeList =this.queryGrpOderType(order.getGrpid());
        boolean  bFind=false;
        if(grpOrderTypeList!=null)
        {
            for(GrpOrderType grpOrderType:grpOrderTypeList)
            {
                if(StringUtils.isNotEmpty(grpOrderType.getOrderType())&&grpOrderType.getOrderType().equals(order.getOrdertype()))
                {
                    bFind=true;
                    break;
                }
            }
        }
        if(!bFind)
        {
            throw new RuntimeException("订单类型不正确");
        }*/
        if(StringUtils.isEmpty(order.getOrdertype()))
        {
            throw new RuntimeException("订单类型不正确");
        }

        //为订单产生编号
        if(StringUtils.isEmpty(order.getOrderid()))
        {
            String orderId = orderhistDao.getOrderId();
            order.setOrderid(orderId);
        }
        if(StringUtils.isEmpty(order.getStatus()))
        {
            order.setStatus(AccountStatusConstants.ACCOUNT_NEW);
        }

        //处理积分
        if(order.getOrderdets()!=null)
        {
            for(OrderDetail orderDetail:order.getOrderdets())
            {
                orderDetail.setOrderid(order.getOrderid());
                orderDetail.setOrderhist(order);
                if(StringUtils.isEmpty(orderDetail.getOrderdetid()))
                {
                    orderDetail.setOrderdetid(orderdetDao.getOrderdetId());
                }
                if(StringUtils.isEmpty(orderDetail.getStatus()))
                {
                    orderDetail.setStatus(AccountStatusConstants.ACCOUNT_NEW);
                }

                if(StringUtils.isEmpty(orderDetail.getProdid())||StringUtils.isEmpty(orderDetail.getProducttype()))
                {
                    throw new RuntimeException("明细中商品信息不正确");
                }
                //商品数量
                if(orderDetail.getUpnum()==null)
                {
                    orderDetail.setUpnum(0);
                }
                if(orderDetail.getSpnum()==null)
                {
                    orderDetail.setSpnum(0);
                }

                if(orderDetail.getUpnum().compareTo(0)<0 || orderDetail.getSpnum().compareTo(0)<0)
                {
                    throw new RuntimeException("明细中商品数量不能为负数");
                }

                int totalNum=orderDetail.getUpnum()+orderDetail.getSpnum();
                if(totalNum<=0)
                {
                    throw new RuntimeException("明细中商品数量不能为0");
                }

                if(orderDetail.getSprice()==null&&orderDetail.getUprice()==null)
                {
                    throw new RuntimeException("明细中商品未设置价格");
                }
                if((orderDetail.getSprice()!=null&&orderDetail.getSprice().compareTo(BigDecimal.ZERO)<0)
                    ||(orderDetail.getUprice()!=null&&orderDetail.getUprice().compareTo(BigDecimal.ZERO)<0))
                {
                    throw new RuntimeException("明细中商品价格不能为负数");
                }
            }
        }
        //检查价格
        if(order.getTotalprice()==null||order.getTotalprice().compareTo(BigDecimal.ZERO)<0)
        {
            throw new RuntimeException("订单总价不能为空或者负数");
        }
        if(order.getProdprice()==null||order.getProdprice().compareTo(BigDecimal.ZERO)<0)
        {
            throw new RuntimeException("商品总价不能为空或者负数");
        }

        if(order.getMailprice()==null)
        {
            order.setMailprice(BigDecimal.ZERO);
        }
        else
        {
            if(order.getMailprice().compareTo(BigDecimal.ZERO)<0)
            {
                throw new RuntimeException("订单运费不能为负数");
            }
        }


        //处理积分
        checkOrderJifen(order);

        order.setCrdt(new Date());
    }

    private void checkOrderJifen(Order order)
    {
        Integer jifen=calcOrderJifen(order);

        Integer jifenOrder=0;
        if(StringUtils.isNotEmpty(order.getJifen()))
        {
            jifenOrder=Integer.parseInt(order.getJifen());
            if(!jifenOrder.equals(jifen))
            {
                throw new RuntimeException("明细中积分与订单中不符合");
            }
        }
        else
        {
            if(jifen>0)
            {
                order.setJifen(jifen.toString());
            }
        }
    }

    private Integer calcOrderJifen(Order order) {
        Integer jifen=0;
        if(order.getOrderdets()!=null)
        {
            for(OrderDetail orderDetail:order.getOrderdets())
            {
                if(StringUtils.isNotEmpty(orderDetail.getJifen()))
                {
                    Integer jifenDetail= Integer.parseInt(orderDetail.getJifen());
                    if(jifenDetail<0)
                    {
                        throw new RuntimeException("使用积分不能是负数");
                    }
                    jifen+=jifenDetail;
                }
            }
        }
        return jifen;
    }

    private Integer calcOrderJifenChange(OrderChange orderChange) {
        Integer jifen=0;
        if(orderChange.getOrderdetChanges()!=null)
        {
            for(OrderdetChange orderdetChange:orderChange.getOrderdetChanges())
            {
                if(StringUtils.isNotEmpty(orderdetChange.getJifen()))
                {
                    Integer jifenDetail= Integer.parseInt(orderdetChange.getJifen());
                    if(jifenDetail<0)
                    {
                        throw new RuntimeException("使用积分不能是负数");
                    }
                    jifen+=jifenDetail;
                }
            }
        }
        return jifen;
    }


    private Order updateOrderFromModifyRequest(Order orderhist, Order orgOrderhist, int warehouseId) {
        logger.debug("update order orderId:" + orderhist.getOrderid());
        OrderReturnCode returnCode = new OrderReturnCode();

        returnCode = checkUpdateOrderhist(orderhist);
        if (returnCode.getCode() != null) {
            logger.error("create order error:"+returnCode.getCode()+" - dsc:"+returnCode.getDesc());
            throw new OrderException(returnCode);
        }

        //TODO:是否需要添加判断的逻辑，来避免相同数据的取消和再分配库存
        //更新前处理-取消原先的库存分配
        ShipmentHeader shipmentHeaderPre= shipmentHeaderService.cancelShipmentHeader(orgOrderhist, orgOrderhist.getMdusr());

        orderhist.setRevision(orderhist.getRevision() + 1);
        String strWarehouseId = warehouseId > 0 ? String.valueOf(warehouseId) : "";
        ShipmentHeader shipmentHeader = this.generateShipmentHeaderbeforeSave(orderhist, strWarehouseId,shipmentHeaderPre);

        orderhistUtil.CopyNotNullValue(orderhist, orgOrderhist);
        this.fetchOrderdetModify(orderhist, orgOrderhist);

        orderhistDao.saveOrUpdate(orgOrderhist);
        OrderHistory orderHistory = orderHistoryService.insertTcHistory(orgOrderhist);
        if (shipmentHeader != null) {
            //设置运单的关联
            shipmentHeader.setOrderHistory(orderHistory);
            try {
                shipmentHeaderService.addShipmentHeader(shipmentHeader);
            } catch (Exception exp) {
                logger.error("upate order addShipmentHeader error:", exp);
                throw new RuntimeException(exp.getMessage());
            }
        }

        return orgOrderhist;
    }

    /**
     * 判断购物车是否发生了变化
     * 产品编码、规格、数量都一致，那么认为没有变化
     * @param orderModify
     * @param orgOrder
     * @return
     */
    private boolean isShoppingCartChanged(Order orderModify, Order orgOrder)
    {
        return false;
    }

    /**
     * 如果订单产品没有变化，并且仓库也没变化，那么不需要重新分配库存
     * @param orderModify
     * @param orgOrder
     * @return
     */
    private boolean isNeedReassignInventory(Order orderModify, Order orgOrder)
    {
         return true;
    }

    private String canConsult(Order order)
    {
        if(!this.getCurrentUsr().equals(order.getCrusr()))
        {
            return "非创建人员的订单，不能转咨询.";
        }
        if(!AccountStatusConstants.ACCOUNT_NEW.equals(order.getStatus()))
        {
            return "订单非订购状态时，不能转咨询.";
        }
        if(StringUtils.isNotEmpty(order.getCustomizestatus()))
        {
            int logisticsStatus=Integer.parseInt(order.getCustomizestatus());
            if(logisticsStatus>0)
            {
                return "订单非订购状态时，不能转咨询.";
            }
        }
        if(!DateUtil.isSameDay(order.getCrdt(),new Date()))
        {
            return "非当天订购的订单，不能转咨询.";
        }
        if("2".equals(order.getPaytype()))
        {
            return "信用卡订单，不能转咨询";
        }
        return null;
    }

    public  boolean isOrderConsultable(String orderId)
    {
        Order order=this.getOrderHistByOrderid(orderId);
        if(StringUtils.isEmpty(canConsult(order)))
            return true;
        return false;
    }

    @Autowired
    private UrgentorderhistDao urgentorderhistDao;

    public boolean consultOrder(String orderId) throws ErpException
    {
        Order order=this.getOrderHistByOrderid(orderId);
        String strMsg=this.canConsult(order);
        if(StringUtils.isNotEmpty(strMsg))
        {
            throw new ServiceParameterException(ExceptionConstant.SERVICE_ORDER_CONSULT_EXCEPTION,strMsg);
        }
        List<UserBpm> userBpmList=changeRequestService.cancelUnCompletedOrderChangeRequest(orderId);

        //插入咨询历史表
        List<OrderDetail> orderDetailList=new ArrayList<OrderDetail>();
        for(OrderDetail orderDetail:order.getOrderdets())
        {
            orderDetailList.add(orderDetail);
        }

        try{
            insertAgentHistory(order,orderDetailList);
        }catch (Exception exp)
        {
            throw new ServiceParameterException(ExceptionConstant.SERVICE_ORDER_CONSULT_EXCEPTION,exp.getMessage());
        }

        String currentUsrId=this.getCurrentUsr();
        String jifen=order.getJifen();

        //this.deleteOrderPhysical(null);
        //删除
        ShipmentHeader shipmentHeader =  shipmentHeaderService.cancelShipmentHeader(order,currentUsrId);
        inventoryAgentService.unreserveInventory(order,shipmentHeader,currentUsrId);
        //调用积分规则
        //首先提交原先的修改
        callJifenCancel(order,jifen,currentUsrId);

        if("1".equals(order.getUrgent()))
        {
            //删除紧急订单
            urgentorderhistDao.deleteUrgentOrder(order.getOrderid());
        }
        this.deleteOrderhist(order);

        if(userBpmList!=null&&userBpmList.size()>0)
            return true;

        return false;
    }


    public boolean checkConfirmOrderModify(Order orderModify, Order orderOrg)
    {
        //TODO:校验信用卡订单总金额是否有效（积分也必须一样）
        return true;
    }

    @Autowired
    private OrderCheckDao orderCheckDao;

    private void rejectOrderModifyOnCreate(String orderId, String usrId, AuditTaskType auditTaskType, UserBpmTaskType userBpmTaskType) throws Exception
    {
        //规则检查
        String ruleName = orderCheckService.getCheckRuleName(String.valueOf(userBpmTaskType.getIndex()));
        if (StringUtils.isNotEmpty(ruleName)) {
            List<OrderCheck> orderCheckList = orderCheckDao.getOrderChecks(orderId);
            if (orderCheckList != null && orderCheckList.size() > 0) {
                for (OrderCheck orderCheck : orderCheckList) {
                    if (ruleName.equals(orderCheck.getCheckRule())) {
                        orderCheck.setManagerCheck(false);
                        orderCheck.setManagerId(usrId);
                        orderCheckDao.saveOrUpdate(orderCheck);
                        break;
                    }
                }
            }
        }

        Order order = null;
        switch (userBpmTaskType) {
            case ORDER_CARD_CHANGE:
                order = this.getOrderHistByOrderid(orderId);
                if (StringUtils.isNotEmpty(order.getCardnumber())) {
                    Card card = cardService.getCard(Long.parseLong(order.getCardnumber()));
                    if (this.isInAuditFlag(card.getState())) {
                        card.setState(CustomerConstant.CUSTOMER_AUDIT_STATUS_REJECTED);
                        cardService.updateCardState(card.getCardId(),CustomerConstant.CUSTOMER_AUDIT_STATUS_REJECTED);
                        //cardService.merxge(card);
                    }
                }
                break;
            case CONTACT_ADDRESS_CHANGE:
                order = this.getOrderHistByOrderid(orderId);
                AddressExt addressExt = order.getAddress();
                if (this.isInAuditFlag(addressExt.getAuditState())) {
                    addressExt.setAuditState(CustomerConstant.CUSTOMER_AUDIT_STATUS_REJECTED);
                    addressExtService.saveOrUpdate(addressExt);
                }
                break;
            case CONTACT_PHONE_CHANGE:
                order = this.getOrderHistByOrderid(orderId);
                List<Phone> phoneList = phoneService.getPhonesByContactId(order.getGetcontactid());
                if (phoneList != null && phoneList.size() > 0) {
                    for (Phone phone : phoneList) {
                        if (this.isInAuditFlag(phone.getState())) {
                            phone.setState(CustomerConstant.CUSTOMER_AUDIT_STATUS_REJECTED);
                            phoneService.saveOrUpdate(phone);
                        }
                    }
                }
                break;
            case CONTACT_BASE_CHANGE:
                order = this.getOrderHistByOrderid(orderId);
                Contact getContact=contactDao.get(order.getGetcontactid());
                if(this.isInAuditFlag(getContact.getState()))
                {
                    getContact.setState(CustomerConstant.CUSTOMER_AUDIT_STATUS_REJECTED);
                    contactDao.saveOrUpdate(getContact);
                }
            default:
                break;
        }
    }

    private void rejectOrderModifyOnModify(String orderId, Long orderChangeId, String usrId, AuditTaskType auditTaskType, UserBpmTaskType userBpmTaskType) throws Exception
    {
        OrderChange orderChange=orderChangeDao.get(orderChangeId);
        switch (userBpmTaskType)
        {
            case ORDER_CARD_CHANGE:
                if(orderChange.getCardChanges()!=null)
                {
                    for(CardChange cardChange:orderChange.getCardChanges())
                    {
                        if(cardChange.getCardId()!=null)
                        {
                            Card card=cardService.getCard(cardChange.getCardId());
                            if(this.isInAuditFlag(card.getState()))
                            {
                                //card.setState(CustomerConstant.CUSTOMER_AUDIT_STATUS_REJECTED);
                                cardService.updateCardState(card.getCardId(),CustomerConstant.CUSTOMER_AUDIT_STATUS_REJECTED);
                            }
                        }
                    }
                }
                break;
            case CONTACT_ADDRESS_CHANGE:
                if(orderChange.getAddress()!=null&&StringUtils.isNotEmpty(orderChange.getAddress().getAddressId()))
                {
                    AddressExt addressExt=addressExtService.getAddressExt(orderChange.getAddress().getAddressId());
                    if(this.isInAuditFlag(addressExt.getAuditState()))
                    {
                        addressExt.setAuditState(CustomerConstant.CUSTOMER_AUDIT_STATUS_REJECTED);
                        addressExtService.saveOrUpdate(addressExt);
                    }
                }
                break;
            case CONTACT_PHONE_CHANGE:
                if(orderChange.getGetContactChange()!=null&&orderChange.getGetContactChange().getPhoneChanges()!=null)
                {
                    for(PhoneChange phoneChange:orderChange.getGetContactChange().getPhoneChanges())
                    {
                        if(phoneChange.getPhoneid()!=null)
                        {
                            Phone phone=phoneService.get(phoneChange.getPhoneid());
                            if(this.isInAuditFlag(phone.getState()))
                            {
                                phone.setState(CustomerConstant.CUSTOMER_AUDIT_STATUS_REJECTED);
                                phoneService.saveOrUpdate(phone);
                            }
                        }
                    }
                }
                break;
            case CONTACT_BASE_CHANGE:
                if(orderChange.getGetContactChange()!=null)
                {
                    if(StringUtils.isNotEmpty(orderChange.getGetContactChange().getContactid()))
                    {
                        Contact contact=contactDao.get(orderChange.getGetContactChange().getContactid());
                        if(this.isInAuditFlag(contact.getState()))
                        {
                            contact.setState(CustomerConstant.CUSTOMER_AUDIT_STATUS_REJECTED);
                            contactDao.saveOrUpdate(contact);
                        }
                    }
                }
            default:break;
        }
    }

    public void rejectOrderModify(String orderId, Long orderChangeId, String usrId, AuditTaskType auditTaskType, UserBpmTaskType userBpmTaskType) throws Exception
    {
        if(auditTaskType.getIndex()==AuditTaskType.ORDERADD.getIndex())
        {
            this.rejectOrderModifyOnCreate(orderId,usrId,auditTaskType,userBpmTaskType);
        }
        else if(auditTaskType.getIndex()==AuditTaskType.ORDERCHANGE.getIndex())
        {
            this.rejectOrderModifyOnModify(orderId,orderChangeId,usrId,auditTaskType,userBpmTaskType);
        }
    }


    public void checkOrderByContactAudit(String contactId,String usrId) throws ErpException
    {
        OrderQueryDto orderQueryDto=new OrderQueryDto();
        orderQueryDto.setGetContactIdList(new ArrayList<String>());
        orderQueryDto.getGetContactIdList().add(contactId);
        //目前就查一个月的
        Date dtNow=new Date();
        Date dtBeginDate=new Date(dtNow.getTime()-this.orderQueryDays*24*3600000);
        orderQueryDto.setBeginCrdt(dtBeginDate);
        List<Order> orderList=orderhistDao.queryOrder(orderQueryDto);
        if(orderList!=null&&orderList.size()>0)
        {
            Boolean bPass=true;
            for(Order order:orderList)
            {
                if(!bPass.equals(order.getCheckResult()))
                {
                    if(orderCheckService.checkOrder(order,null,usrId))
                    {
                        if(order.getCheckResult()!=null&&order.getCheckResult().intValue()==CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITING)
                        {
                            order.setCheckResult(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITED);
                            orderhistDao.saveOrUpdate(order);
                        }
                    }
                }
            }
        }
    }

    private boolean blackContactAuditHandle(OrderChange orderChange, String procInstId)
    {
        if(orderChange.getContactChange()!=null&&StringUtils.isNotEmpty(orderChange.getContactChange().getProcInstId()))
        {
            if(procInstId.equals(orderChange.getContactChange().getProcInstId()))
            {
                //目前不需要什么处理
                return true;
            }
        }
        return false;
    }

    private String startupBlackContactAudit(OrderChange orderChange,String batchId, String remark,Date createDate,String createUsrId)
    {
        if(orderChange.getContactChange()!=null)
        {
            UserBpmTask userBpmTask = new UserBpmTask();
            userBpmTask.setChangeObjID(orderChange.getOrderChangeId().toString());
            userBpmTask.setUpdateDate(createDate);
            userBpmTask.setUpdateUser(createUsrId);
            userBpmTask.setRemark(remark);
            userBpmTask.setBatchID(batchId);
            userBpmTask.setStatus(String.valueOf(AuditTaskStatus.UNASSIGNED.getIndex()));
            userBpmTask.setBusiType(String.valueOf(UserBpmTaskType.CONTACT_BASE_CHANGE.getIndex()));

            String procInstId = userBpmTaskService.createApprovingTask(userBpmTask);
            orderChange.setProcInstId(procInstId);
            return procInstId;
        }
        return null;
    }

    public void auditResultOnOrderCreate(String orderId, boolean bPass, String usrId) throws ErpException
    {
        Order order=this.getOrderHistByOrderid(orderId);
        if(order.getCheckResult()==null||order.getCheckResult().intValue()!=CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITING)
        {
            throw new ServiceException(ExceptionConstant.SERVICE_ORDER_UPDATE_EXCEPTION, "订单状态不正确");
        }
        if(bPass==true)
        {
            order.setCheckResult(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITED);
            this.saveOrUpdate(order);
        }
        else
        {
            order.setCheckResult(CustomerConstant.CUSTOMER_AUDIT_STATUS_REJECTED);
            this.cancelOrderInner(usrId,order);
        }
    }

    public void auditFinishOnOrderModify(String orderId, boolean bPass, String usrId) throws ErpException
    {
        //修改订单，流程结束，调用接口
        Order order=this.getOrderHistByOrderid(orderId);
        if(order.getCheckResult()!=null&&order.getCheckResult().intValue()==CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITING)
        {
            order.setCheckResult(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITED);
            this.saveOrUpdate(order);
        }
    }

    @Autowired
    private OrderTrackRemarkDao orderTrackRemarkDao;

    @Override
    public boolean canAddNote(String orderId) {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addOrderNote(String orderId, String note, Date dtCreate,String usrId, String usrName) {
        if(!canAddNote(orderId))
        {
            OrderReturnCode orderReturnCode=new OrderReturnCode("1","");
            throw new OrderException(orderReturnCode);
        }
        Order order=this.getOrderHistByOrderid(orderId);
        if(order!=null)
        {
            order.setTrackRemark(note);
            this.saveOrUpdate(order);

            OrderTrackRemark orderTrackRemark =new OrderTrackRemark();
            orderTrackRemark.setAgentName(usrName);
            orderTrackRemark.setCreateUser(usrId);
            orderTrackRemark.setCreateDate(dtCreate);
            orderTrackRemark.setOrderId(orderId);
            orderTrackRemark.setRemark(note);
            orderTrackRemarkDao.save(orderTrackRemark);
        }

    }

    @Override
    public List<String> queryN400ByDnis(String dnis) {
        List<String> n400List = orderhistDao.queryN400ByDnis(dnis);
        return n400List;
    }

    @Override
    public List<GrpOrderType> queryOrderType(List<String> n400List, String area) {
        List<MediaDnis> mediaDniseList =   orderhistDao.queryOrderType(n400List, area) ;
        if(mediaDniseList !=null && !mediaDniseList.isEmpty()){
            List<GrpOrderType> grpOrderTypeList = new ArrayList<GrpOrderType>();
            List<String> checkList = new ArrayList<String>();
            for(MediaDnis mediaDnis:mediaDniseList){
                if(StringUtils.isNotBlank(mediaDnis.getOrdertype())){
                    if(checkList.contains(mediaDnis.getOrdertype())){
                        continue;
                    }else{
                        checkList.add(mediaDnis.getOrdertype());
                    }

                    GrpOrderType grpOrderType =new GrpOrderType();
                    grpOrderType.setOrderType(mediaDnis.getOrdertype());
                    grpOrderTypeList.add(grpOrderType);
                }
            }
            return grpOrderTypeList;
        }

        return null;
    }

    @Override
    public void cancelOrderAudit(String batchId, String orderId, String remark, String userId) throws Exception {
        //1.取消流程
        changeRequestService.cancelChangeRequestByBatchID(batchId, remark);
        //2.取消订单关联的对象流程标识
        List<UserBpmTask> userBpmTasks = userBpmTaskService.queryApprovingTaskByBatchID(batchId);
        UserBpm userBpm=changeRequestService.queryApprovingTaskById(batchId);

        int auditType=Integer.parseInt(userBpm.getBusiType());
        AuditTaskType auditTaskType=null;
        for(AuditTaskType auditTaskTypeItem:AuditTaskType.values())
        {
            if(auditTaskTypeItem.getIndex()==auditType)
            {
                auditTaskType=auditTaskTypeItem;
                break;
            }
        }
        for(UserBpmTask userBpmTask : userBpmTasks)
        {
            if(StringUtils.isNotBlank(userBpmTask.getBusiType()))
            {
                UserBpmTaskType userBpmTaskType=UserBpmTaskType.fromNumber(Integer.parseInt(userBpmTask.getBusiType()));
                this.rejectOrderModify(orderId, Long.valueOf(userBpmTask.getChangeObjID()),userId,auditTaskType,userBpmTaskType);
            }
        }

        if(auditTaskType.getIndex()==AuditTaskType.ORDERADD.getIndex())
            auditResultOnOrderCreate(orderId,false,userId);
    }

    @Override
    public OrderCreateResult createOrderExt(Order order, BigDecimal newMailPrice) throws ErpException {
        OrderCreateResult orderCreateResult=new OrderCreateResult();
        checkOrderOnCreate(order);
        //同时判断电话
        boolean isPhoneAudit=this.isNeedPhoneAudit(order);
        boolean bMailpriceChanged=(newMailPrice!=null&&newMailPrice.compareTo(order.getMailprice())!=0)?true:false;
        List<OrderRuleCheckService> orderRuleCheckServiceList = orderCheckService.attachOrderRules(order);
        if((orderRuleCheckServiceList!=null&&orderRuleCheckServiceList.size()>0)||isPhoneAudit==true || bMailpriceChanged==true )
        {
            order.setCheckResult(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITING);
        }
        else
        {
            order.setCheckResult(null);
        }

        try
        {
            //
            this.updateOrderhist(order);

            //最后处理积分
            if(StringUtils.isNotEmpty(order.getJifen()))
            {
                Integer jifen=Integer.parseInt(order.getJifen());
                if(jifen!=null&&jifen.intValue()>0)
                {
                    orderhistDao.flush();
                    Integer addJifen=jifen*10;
                    Map<String, Object> params=new Hashtable<String, Object>();
                    params.put("sorderid",order.getOrderid());
                    params.put("scontactid",order.getContactid());
                    params.put("npoint",addJifen);
                    params.put("scrusr",order.getCrusr());
                    params.put("stype","0");
                    pvService.addPvByOrder(params);
                }
            }

            if((orderRuleCheckServiceList!=null&&orderRuleCheckServiceList.size()>0) || isPhoneAudit==true)
            {
                orderCreateResult.setAudit(!startupProcs(order, orderRuleCheckServiceList, newMailPrice));
                if(orderCreateResult.isAudit()==true)
                {
                    orderCreateResult.setAuditTaskType(AuditTaskType.ORDERADD);
                }
            }
            else
            {
                //最后如果运费修改了，那么启动修改流程
                if(bMailpriceChanged==true)
                {
                    OrderChange orderChange=new OrderChange();
                    orderChange.setOrderid(order.getOrderid());
                    String usrId=this.getCurrentUsr();
                    if(StringUtils.isEmpty(usrId))
                    {
                        usrId=order.getCrusr();
                    }
                    orderChange.setMdusr(usrId);
                    orderChange.setMddt(new Date());
                    orderChange.setMailprice(newMailPrice);
                    orderChange.setId(order.getId());
                    List<OrderChange> orderChangeList=new ArrayList<OrderChange>();
                    orderChangeList.add(orderChange);
                    saveOrderModifyRequestAndBeginProcs(orderChangeList, AuditTaskType.ORDERCHANGE,  "", usrId, true);

                    orderCreateResult.setAudit(true);
                    orderCreateResult.setAuditTaskType(AuditTaskType.ORDERCHANGE);
                }
                else
                {
                }
            }
        }catch (Exception exp)
        {
            throw new ErpException(ExceptionConstant.SERVICE_ORDER_CREATE_EXCEPTION,exp.getMessage(),exp);
        }
        return orderCreateResult;
    }

    public void checkAuditedOrder(Order order) throws ErpException
    {
        this.checkCardOrder(order);
    }

    private void handlePhoneChange(PhoneChange phoneChange) throws ServiceTransactionException
    {
        //情况1：新增电话号码-从change表中获取新电话号码
        //情况2：修改电话号码-从change表以及phone表合并新的电话(目前不存在此情况)

        Phone phoneOrg=null;
        if(phoneChange.getPhoneid()!=null)
        {
            phoneOrg=phoneService.get(phoneChange.getPhoneid());
            if(this.isInAuditFlag(phoneOrg.getState()))
            {
                //检查是否重复
                if(!phoneService.checkExistPhone(phoneOrg))
                {
                    phoneOrg.setState(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITED);
                    //phoneService.updatePhoneDirect(phoneOrg);
                    phoneService.updatePhone(phoneOrg);
                }
                else
                {
                    phoneOrg.setState(CustomerConstant.CUSTOMER_AUDIT_STATUS_REJECTED);
                    phoneService.updatePhoneDirect(phoneOrg);
                }
            }
        }
        else
        {
            //处理电话，需要去重
            // （重复情况：
            // 1.存在并且状态是空
            // 2.存在并且状态是审核通过
            // ）
            //实现方法：为当前订单的联系人增加电话(检查一下电话是否已经存在了)
            Phone phone = new Phone();
            BeanUtils.copyProperties(phoneChange, phone);
            //存在相同的电话，就直接忽略
            if(!phoneService.checkExistPhone(phone))
            {
                if(phoneService.checkExistSameNameAndPhone(phone))
                {
                    throw new ServiceTransactionException("电话号码已绑定在相同客户名的不同客户编号身上，请确认是否将一个客户录了两次");
                }
                else
                {
                    phoneService.addPhoneWithProcessPrm(phone);// .addOrUpdateNewPhone(phone);
                }
            }
        }
    }
}
