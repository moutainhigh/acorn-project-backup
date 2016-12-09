package com.chinadrtv.erp.tc.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;

import com.chinadrtv.erp.model.*;
import com.chinadrtv.erp.model.agent.*;
import com.chinadrtv.erp.model.inventory.PlubasInfo;
import com.chinadrtv.erp.tc.core.constant.*;
import com.chinadrtv.erp.tc.core.dao.*;
import com.chinadrtv.erp.tc.core.model.OrderAutoChooseInfo;
import com.chinadrtv.erp.tc.service.InventoryAgentService;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.core.exception.BizException;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.ic.service.ItemInventoryAllocateService;
import com.chinadrtv.erp.model.trade.ShipmentHeader;
import com.chinadrtv.erp.tc.core.model.OrderReturnCode;
import com.chinadrtv.erp.tc.core.model.PreTradeRest;
import com.chinadrtv.erp.tc.core.service.AddressExtService;
import com.chinadrtv.erp.tc.core.service.CompanyService;
import com.chinadrtv.erp.tc.core.service.CouponService;
import com.chinadrtv.erp.tc.core.service.DisHuifangMesService;
import com.chinadrtv.erp.tc.core.service.OrderHistoryService;
import com.chinadrtv.erp.tc.core.service.OrderSkuSplitService;
import com.chinadrtv.erp.tc.core.service.OrderhistCompanyAssignService;
import com.chinadrtv.erp.tc.core.service.PvService;
import com.chinadrtv.erp.tc.core.utils.OrderException;
import com.chinadrtv.erp.tc.core.utils.OrderdetUtil;
import com.chinadrtv.erp.tc.core.utils.OrderhistUtil;
import com.chinadrtv.erp.tc.service.OrderhistService;
import com.chinadrtv.erp.tc.service.ShipmentHeaderService;

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
    private ItemInventoryAllocateService itemInventoryAllocateService;
    @Autowired
    private OrderhistCompanyAssignService orderhistCompanyAssignService;
    @Autowired
    private ShipmentHeaderService shipmentHeaderService;
    @Autowired
    private PvService pvService;
    @Autowired
    private CouponService couponService;
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
            List<Phone> phoneList=phoneDao.findList("from Phone where phn2 =:phn", new ParameterString("phn",preTrade.getReceiverMobile()));
            if(phoneList!=null)
            {
                for(Phone phone1:phoneList)
                {
                    Contact contact1=contactDao.get(phone1.getContactid());
                    if(preTrade.getReceiverName().equals(contact1.getName()))
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
            String contactId = contactDao.GetContactId();

            contact = new Contact();
            contact.setContactid(contactId);
            contact.setName(preTrade.getReceiverName());
            contact.setSex("2");
            contact.setCrdt(new Date());
            contact.setCrusr(preTrade.getCrusr());
            contact.setCrtm(new Date());
            contact.setMembertype("4");
            contact.setTotal(0L);
            contact.setAreacode(preTrade.getReceiverPostCode());

            contactDao.save(contact);

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
        System.out.println("前置订单id：" + orderId);

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
            System.out.println("前置订单明细id：" + orderdet.getOrderdetid());

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
    private boolean isValidCreditExpire(String expire)
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
                order.setIsassign(null);
            }
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
        /*if(AccountStatusConstants.ACCOUNT_NEW.equals(orgOrderhist.getStatus()))
        {
            preShipmentHeader=null;
        }*/
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
        resetOrderStatusOnModify(orgOrderhist);

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



	public void deleteOrderhist(Order orderhist) {
		/*Set<OrderDetail> odSet = orderhist.getOrderdets();
		for(OrderDetail od : odSet){
			od.setOrderhist(null);
			orderdetDao.saveOrUpdate(od);
			orderdetDao.remove(od.getId());
		}*/
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
	* @Description: true 已出库， false 未出库
	* @param customizeStatus
	* @return
	* @return boolean
	* @throws
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

    	orderhist = this.getOrderHistByOrderid(orderid);
    	if(null == orderhist){
    		throw new BizException("订单["+orderid+"]不存在");
    	}

    	//Step1 已经分别在step3和step8里操作

    	//Step4 更新订单和详情
    	this.updateOrderhistAndDetail(orderhist, callid, modifyUser);

    	//Step7
    	//List<OrderDetail> orderdetList = orderdetDao.getOrderDetList(orderhist);
    	OrderHistory orderHistory = orderHistoryService.insertTcHistory(orderhist);

    	//还原库存、取消旧运单
    	ShipmentHeader shipmentHeader = shipmentHeaderService.cancelShipmentHeader(orderhist, modifyUser);
        inventoryAgentService.unreserveInventory(orderhist, shipmentHeader, modifyUser);

    	//生成取消发运单
    	ShipmentHeader sh = shipmentHeaderService.generateShipmentHeader(orderhist, true);
    	sh.setOrderHistory(orderHistory);
    	shipmentHeaderService.addOrUpdateShipmentHeader(sh);
    	//FIXME Step9

    	//Step5 和Step6 暂时不用调
    }


	/*
	 * 更新订单表和详情表
	* @Description:
	* @param orderhist
	* @return void
	* @throws
	*/
	private Order updateOrderhistAndDetail(Order orderhist, String callid, String mdusr)throws Exception {

		//List<OrderDetail> orderdetList = orderdetDao.getOrderDetList(orderhist);

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
	* <p>Title: 删除订单（物理删除）</p>
	* <p>Description: </p>
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
    	//orderdetDao.deleteList(orderdetList);


    	//Step9
    	//shipmentHeaderService.cancelWaybill(orderId, mdusr);
    	//还原库存，取消运单
    	ShipmentHeader shipmentHeader = shipmentHeaderService.cancelShipmentHeader(orderhist, mdusr);
        inventoryAgentService.unreserveInventory(orderhist,shipmentHeader,mdusr);

    	//Step10 FIXME
    	//Step5、6 暂不调用
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

		//List<OrderDetail> orderdetList = orderdetDao.getOrderDetList(orderhist);
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

		//List<OrderDetail> childOrderdetList = orderdetDao.getOrderDetList(childOrderhist);
		//List<OrderDetail> orderdetList = orderdetDao.getOrderDetList(orderhist);
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
		//List<OrderDetail> orderdetList = orderdetDao.getOrderDetList(orderhist);
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
		//List<OrderDetail> orderdetList = orderdetDao.getOrderDetList(orderhist);
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
		//List<OrderDetail> orderdetList = orderdetDao.getOrderDetList(orderhist);
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
	* @return
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
		//List<OrderDetail> orderdetList = orderdetDao.getOrderDetList(orderhist);
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
	* @return
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
		//List<OrderDetail> orderdetList = orderdetDao.getOrderDetList(orderhist);
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
	* @return
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

		//List<OrderDetail> orderdetList = orderdetDao.getOrderDetList(orderhist);

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
	* @return
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
	* @return
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
	 * <p>Title: 更改订单运费</p>
	 * <p>Description: </p>
	 * @throws Exception
	 * @see com.chinadrtv.erp.tc.service.OrderhistService
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
}
