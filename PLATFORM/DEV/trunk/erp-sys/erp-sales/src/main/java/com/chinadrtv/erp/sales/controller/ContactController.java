package com.chinadrtv.erp.sales.controller;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.constant.SecurityConstants;
import com.chinadrtv.erp.core.model.ScmPromotion;
import com.chinadrtv.erp.core.service.NamesService;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.ic.service.PlubasInfoService;
import com.chinadrtv.erp.marketing.core.common.AuditTaskStatus;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskStatus;
import com.chinadrtv.erp.marketing.core.common.UserBpmTaskType;
import com.chinadrtv.erp.marketing.core.dto.CampaignDto;
import com.chinadrtv.erp.marketing.core.dto.CampaignTaskDto;
import com.chinadrtv.erp.marketing.core.dto.CampaignTaskVO;
import com.chinadrtv.erp.marketing.core.exception.MarketingException;
import com.chinadrtv.erp.marketing.core.service.*;
import com.chinadrtv.erp.marketing.core.util.HttpUtil;
import com.chinadrtv.erp.model.*;
import com.chinadrtv.erp.model.agent.*;
import com.chinadrtv.erp.model.marketing.UserBpm;
import com.chinadrtv.erp.model.marketing.UserBpmTask;
import com.chinadrtv.erp.sales.core.service.OrderhistService;
import com.chinadrtv.erp.sales.dto.*;
import com.chinadrtv.erp.sales.service.ContactEditService;
import com.chinadrtv.erp.smsapi.model.SmsSendDetail;
import com.chinadrtv.erp.uc.dto.AddressDto;
import com.chinadrtv.erp.uc.dto.CardDto;
import com.chinadrtv.erp.uc.dto.CustomerDto;
import com.chinadrtv.erp.uc.dto.PhoneAddressDto;
import com.chinadrtv.erp.uc.service.*;
import com.chinadrtv.erp.user.aop.Mask;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.chinadrtv.erp.util.DateUtil;
import com.chinadrtv.erp.util.JsonBinder;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Title: ContactController
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
@Controller
@RequestMapping(value = "contact")
public class ContactController extends BaseController {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;
    @Autowired
    private ContactEditService contactEditService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PotentialContactService potentialContactService;

    @Autowired
    private PhoneService phoneService;

    @Autowired
    private PotentialContactPhoneService potentialContactPhoneService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private CardService cardService;

    @Autowired
    private NamesService namesService;


    @Autowired
    private UserBpmService userBpmService;

    @Autowired
    private UserBpmTaskService userBpmTaskService;

    @Autowired
    private PhoneChangeService phoneChangeService;

    @Autowired
    private AddressChangeService addressChangeService;

    @Autowired
    private AddressExtChangeService addressExtChangeService;

    @Autowired
    private ContactChangeService contactChangeService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private PlubasInfoService plubasInfoService;

    @Autowired
    private OrderhistService orderhistService;

    @Autowired
    private CampaignBPMTaskService campaignBPMTaskService;

    @Autowired
    private OldContactexService oldContactexService;

    @Autowired
    private CampaignApiService campaignApiService;

    @Autowired
    private CasesService casesService;

    @Autowired
    private  CallHistService callHistService;

    @Autowired
    private SmsApiService smsApiService;

    @Autowired
    private CardtypeService cardtypeService;

    @Autowired
    private UserService userService;

    @Autowired
    private CusnoteService cusnoteService;

    @Autowired
    private ContactinsureService contactinsureService;

    private static JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();

    @RequestMapping(value = "/{customerType}/{customerId}", method = RequestMethod.GET)
    public ModelAndView get(@PathVariable("customerType") String customerType,
                            @PathVariable("customerId") String customerId,
                            @RequestParam(value = "orderId",required = false)String orderId,
                            @RequestParam(value = "selectedTab",required = false)String selectedTab,
                            @RequestParam(value = "batchId",required = false)String batchId,
                            @RequestParam(value = "instId",required = false)String instId,
                            @RequestParam(value = "provid",required = false)String provid,
                            @RequestParam(value = "cityid",required = false)String cityid,
                            @RequestParam(value = "campaignId",required = false)String campaignId) {
        ModelAndView mav = new ModelAndView("inbound/inbound");
        AgentUser user = SecurityHelper.getLoginUser();
        CustomerFrontDto customerFrontDto;
        double points = 0;
        String level = "";
        BigDecimal totalAmount = new BigDecimal(0);
        if (CustomerFrontDto.POTENTIAL_CONTACT.equals(customerType)) {
            customerFrontDto = queryPotentialContactConvertToDto(customerId);
        } else {
            customerFrontDto = queryContactConvertToDto(customerId);
            try {
                points = contactService.findJiFenByContactId(customerId);
                level = contactService.findLevelByContactId(customerId);
                totalAmount = orderService.queryTotalAmountByContactId(customerId);
            } catch (ServiceException e) {
                logger.error( "获取积分或会员级别失败", e);
            }
        }
        mav.addObject("customer", customerFrontDto);
        mav.addObject("insureList",JSONSerializer.toJSON(customerFrontDto.getInsureProdList()));
        mav.addObject("insureToList",JSONSerializer.toJSON(customerFrontDto.getInsureProdToList()));
        try {
            mav.addObject("agentType", userService.getGroupType(user.getUserId()));
        } catch (ServiceException e) {
            mav.addObject("agentType", "OUT");
            logger.error("获取会员的IN OUT组类型失败", e);
        }
        mav.addObject("selectedTab", selectedTab);
        mav.addObject("points", points);
        mav.addObject("level", level);
        mav.addObject("totalAmount", totalAmount);
        mav.addObject("instanceId", instId);
        mav.addObject("provid", provid);
        mav.addObject("cityid", cityid);
        mav.addObject("campaignId", campaignId);
        mav.addObject("educations", namesService.getAllNames("EDUCATION"));
        mav.addObject("marriages", namesService.getAllNames("MARRIAGE"));
        mav.addObject("occupationStatuss", namesService.getAllNames("OCCUPATIONSTATUS"));
        mav.addObject("income", namesService.getAllNames("INCOME"));
        if(StringUtils.isNotBlank(batchId)) {
        	mav.addObject("batchId",batchId);
        }
        CampaignTaskDto campaignTaskDto = new CampaignTaskDto();
        campaignTaskDto.setCustomerID(customerId);
        campaignTaskDto.setUserID(user.getUserId());
        mav.addObject("taskCount", campaignBPMTaskService.queryUnStartedCampaignTaskCount(campaignTaskDto));
        mav.addObject("bindAgentUser", oldContactexService.queryBindAgentUser(customerId));

        mav.addObject("orderPayTypes", JSONSerializer.toJSON(namesService.getAllNames("PAYTYPE")));

        //购物车产品及促销
        try {
            List<ScmPromotion> scmPromotions;
            ShoppingCart shoppingCart;
            if (orderId != null) {
                Order orderhist = orderhistService.getOrderHistByOrderid(orderId);
                shoppingCart = shoppingCartService.createShoppingCartbyOrder(orderhist);
                double allPoint = getAllPoint(orderhist, points);
                mav.addObject("allPoint",  (new BigDecimal(allPoint)).setScale(0,RoundingMode.DOWN));
                mav.addObject("cart_cartType", ShoppingCart.ORDER);
                mav.addObject("selectedTab",5);
            } else {
                shoppingCart = shoppingCartService.findShoppingCartByContactId(Long.parseLong(customerFrontDto.getCustomerId()),ShoppingCart.CART);
                mav.addObject("allPoint",  (new BigDecimal(points)).setScale(0,RoundingMode.DOWN));
                mav.addObject("cart_cartType", ShoppingCart.CART);
            }
            if (shoppingCart != null) {
                scmPromotions = shoppingCartService.getScmPromotions(shoppingCart, user.getUserId());
                ShoppingCartDto shoppingCartDto = this.getShoppingCartDto(shoppingCart, scmPromotions);
                mav.addObject("shoppingCartDto", shoppingCartDto);
                mav.addObject("orderId", orderId);
                List<Cardtype> cardtypes = cardtypeService.queryUsefulCardtypes();
                if(cardtypes != null){
                    //001=身份证 002=护照 011=军官证 014=台胞证
                    for(Object ct : cardtypes.toArray()){
                        Cardtype cardtype = (Cardtype)ct;
                        if(cardtype != null && ("014".equalsIgnoreCase(cardtype.getCardtypeid()))){
                            cardtypes.remove(cardtype);
                        }
                    }
                    mav.addObject("cardTypes", cardtypes);
                    mav.addObject("cardBanks", getUsefulBanks(cardtypes));
                    mav.addObject("contactName", customerFrontDto.getName());
                }
                //修改地址修改电话权限
                boolean approveManager =false;
                try {
                    String groupType = userService.getGroupType(user.getUserId());
                    approveManager ="in".equalsIgnoreCase(groupType)  ||  user.hasRole(SecurityConstants.ROLE_APPROVE_MANAGER);
                } catch (ServiceException e) {
                    logger.error("新增或修改客户主地址获取权限", e);
                }
                mav.addObject("approveManager", approveManager);
            }
        } catch (Exception e1) {
            logger.error(e1.getMessage());
            mav.addObject("errorMessage", "查询购物车失败");
        }
        return mav;
    }

    @RequestMapping(value = "/getPotentialContactInfo/{customerId}", method = RequestMethod.GET)
    @ResponseBody
    public Map getPotentialContactInfo(@PathVariable("customerId") String customerId) {
        Map result = new HashMap();
        CustomerFrontDto customerFrontDto = queryPotentialContactConvertToDto(customerId);
        result.put("customer", customerFrontDto);
        return result;
    }

    @RequestMapping(value = "/getContactInfo/{customerId}", method = RequestMethod.GET)
    @ResponseBody
    public Map getContactInfo(@PathVariable("customerId") String customerId) {
        Map result = new HashMap();
        CustomerFrontDto customerFrontDto = queryContactConvertToDto(customerId);
        result.put("customer", customerFrontDto);
        result.put("insureList",JSONSerializer.toJSON(customerFrontDto.getInsureProdList()));
        result.put("insureToList",JSONSerializer.toJSON(customerFrontDto.getInsureProdToList()));
        return result;
    }

    private double getAllPoint(Order orderhist, double points) {
        BigDecimal bigDecimal = new BigDecimal(points);
        if (orderhist != null) {
            for (OrderDetail det : orderhist.getOrderdets()) {
                if (StringUtils.isNotBlank(det.getJifen())) {
                    bigDecimal = bigDecimal.add(new BigDecimal(det.getJifen()));
                }
            }
        }
        return bigDecimal.doubleValue();
    }

    private ShoppingCartDto getShoppingCartDto(ShoppingCart shoppingCart, List<ScmPromotion> scmPromotions) {
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto(shoppingCart);

        Set<ShoppingCartProductDto> set = new HashSet<ShoppingCartProductDto>();
        for (ShoppingCartProduct scp : shoppingCart.getShoppingCartProducts()) {
            ShoppingCartProductDto shoppingCartProductDto = new ShoppingCartProductDto(scp);
            //设置规格
            if(3 ==scp.getIsGift()){
                shoppingCartProductDto.setProductTypes(plubasInfoService.getNcAllAttributes(scp.getSkuCode()));
            }else {
                shoppingCartProductDto.setProductTypes(plubasInfoService.getNcAttributes(scp.getSkuCode()));
            }
            //判断是否赠品
            boolean isScmGift = getScmGift(scp, scmPromotions);
            shoppingCartProductDto.setIsScmGift(isScmGift);
            set.add(shoppingCartProductDto);
        }
        shoppingCartDto.setShoppingCartProductDtos(set);
        return shoppingCartDto;
    }
    //判断是否促销规则产品
    private boolean getScmGift(ShoppingCartProduct scp, List<ScmPromotion> scmPromotions) {
        if(scp.getIsGift()==3){
            for (ScmPromotion scmPromotion :scmPromotions) {
                if(scp.getSkuCode().equals(scmPromotion.getPluid())){
                    return true;
                }
            }
        }
        return false;
    }

    private CustomerFrontDto queryPotentialContactConvertToDto(String customerId) {
        CustomerFrontDto customerFrontDto = new CustomerFrontDto();
        PotentialContact potentialContact = null;
        try {
            potentialContact = potentialContactService.queryById(Long.valueOf(customerId));
        } catch (Exception e) {
            potentialContact = new PotentialContact();
        }
        customerFrontDto.setCustomerId(customerId);
        customerFrontDto.setName(potentialContact.getName());
        customerFrontDto.setSex(potentialContact.getGender());
        customerFrontDto.setBirthday(potentialContact.getBirthday());
        customerFrontDto.setCustomerType(CustomerFrontDto.POTENTIAL_CONTACT);
        return customerFrontDto;
    }

    private CustomerFrontDto queryContactConvertToDto(String customerId) {
        CustomerFrontDto customerFrontDto = new CustomerFrontDto();
        Contact contact;
        try {
            contact = contactService.get(customerId);
        } catch (Exception e) {
            contact = new Contact();
        }
        BeanUtils.copyProperties(contact, customerFrontDto);
        customerFrontDto.setCustomerId(customerId);
        customerFrontDto.setCustomerType(CustomerFrontDto.CONTACT);

        fetchContactInsure(customerFrontDto);

        return customerFrontDto;
    }

    private void fetchContactInsure(CustomerFrontDto customerFrontDto)
    {
        Contactinsure contactinsure=contactinsureService.findContactinsure(customerFrontDto.getCustomerId());
        if(contactinsure!=null)
        {
            customerFrontDto.setInsureNote(contactinsure.getDsc());
            customerFrontDto.setInsureAge(contactinsure.getIsok());
            customerFrontDto.setInsureStatus(contactinsure.getStatus());
            AgentUser user  = SecurityHelper.getLoginUser();
            customerFrontDto.setHasinsure((shoppingCartService.getUserdInsureProduct(user.getUserId()).size()>0) ? "1" : "0");
            if(DateUtil.getDateDeviations(contactinsure.getRefuseDateTime(), new Date())>90 || DateUtil.getDateDeviations(contactinsure.getRefuseDateTime(), new Date())<0){
                customerFrontDto.setRefuse("0");
                customerFrontDto.setRefuseDateTime(new Date());
            }else{
                customerFrontDto.setRefuse("1");
                customerFrontDto.setRefuseDateTime(contactinsure.getRefuseDateTime());
            }

            if(DateUtil.getDateDeviations(contactinsure.getInsureSeccDate(), new Date())>90){
                customerFrontDto.setInsureExpire("1");
            }else{
                customerFrontDto.setInsureExpire("0");
            }
        }
        List<Product> productList=shoppingCartService.getUserdInsureProduct(customerFrontDto.getCustomerId());
        List<Product> productToList=shoppingCartService.getInsureList(customerFrontDto.getCustomerId(),SecurityHelper.getLoginUser().getDefGrp(), null);

        if(productList!=null&&productList.size()>0)
        {
            List<Map<String,String>> list=new ArrayList<Map<String,String>>();
            for(Product product:productList)
            {
                Map<String,String> map=new HashMap<String, String>();
                map.put("prodId",product.getProdid());
                map.put("prodName",product.getProdname());
                list.add(map);
            }
            customerFrontDto.setInsureProdList(list);
        }

        if(productToList!=null&&productToList.size()>0)
        {
            List<Map<String,String>> list=new ArrayList<Map<String,String>>();
            for(Product product:productToList)
            {
                Map<String,String> map=new HashMap<String, String>();
                map.put("prodId",product.getProdid());
                map.put("prodName",product.getProdname());
                list.add(map);
            }
            customerFrontDto.setInsureProdToList(list);
        }
    }

    private List<String> getUsefulBanks(List<Cardtype> cardtypes) {
        List<String> banks = new ArrayList<String>();
        if(cardtypes != null) {
            for(Cardtype type : cardtypes){
                if(StringUtils.isNotBlank(type.getBankName())) {
                    if(!banks.contains(type.getBankName())){
                        banks.add(type.getBankName());
                    }
                }
            }
        }
        return banks;
    }

    @RequestMapping(value = "/phoneList/{customerType}/{customerId}", method = RequestMethod.GET)
    @ResponseBody
    @Mask
    public Map phoneList(@PathVariable("customerType") String customerType,
                         @PathVariable("customerId") String customerId) {
        DataGridModel dataGridModel = new DataGridModel();
        dataGridModel.setPage(1);
        dataGridModel.setRows(100);
        List<CustomerPhoneFrontDto> phones = getPhoneDtos(customerType, customerId, dataGridModel);
        Map result = new HashMap();
        result.put("rows", phones);
        return result;
    }

    private List<CustomerPhoneFrontDto> getPhoneDtos(String customerType, String customerId, DataGridModel dataGridModel) {
        List<CustomerPhoneFrontDto> phones = new ArrayList<CustomerPhoneFrontDto>();
        if (CustomerFrontDto.POTENTIAL_CONTACT.equals(customerType)) {
            List<PotentialContactPhone> result = (List<PotentialContactPhone>) potentialContactPhoneService.findByPotentialContactId(customerId, dataGridModel).get("rows");
            for (PotentialContactPhone phone : result) {
                CustomerPhoneFrontDto customerPhoneFrontDto = new CustomerPhoneFrontDto();
                customerPhoneFrontDto.setCustomerType(CustomerFrontDto.POTENTIAL_CONTACT);
                customerPhoneFrontDto.setCustomerId(customerId);
                customerPhoneFrontDto.setCustomerPhoneId(phone.getId().toString());
                customerPhoneFrontDto.setPhn1(phone.getPhone1());
                customerPhoneFrontDto.setPhn2(phone.getPhone2());
                customerPhoneFrontDto.setPhn3(phone.getPhone3());
                customerPhoneFrontDto.setPrmphn(phone.getPrmphn());
                customerPhoneFrontDto.setLastCallDate(phone.getLastCallDate());
                customerPhoneFrontDto.setCallCount(phone.getCallCount());
                customerPhoneFrontDto.setPhonetypid(phone.getPhoneTypeId());
                customerPhoneFrontDto.setNoAreaCodePhone(phone.getPhone2());
                String noEncryptionPhone;
                if (StringUtils.isBlank(phone.getPhoneTypeId()) || "4".equals(phone.getPhoneTypeId())){
                    noEncryptionPhone = phone.getPhone2();
                } else {
                    if (phone.getPhone1()!=null) noEncryptionPhone = phone.getPhone1() + phone.getPhone2();
                    else noEncryptionPhone = phone.getPhone2();
                }
                customerPhoneFrontDto.setNoEncryptionPhone(noEncryptionPhone);
                PhoneAddressDto phoneAddressDto = phoneService.splicePhone(noEncryptionPhone);
                String belongAddress = StringUtils.isBlank(phoneAddressDto.getCityName()) ? "" : "(" + phoneAddressDto.getProvName() + phoneAddressDto.getCityName() + ")";
                customerPhoneFrontDto.setBelongAddress(belongAddress);
                phones.add(customerPhoneFrontDto);
            }
        } else {
            List<Phone> result = (List<Phone>) phoneService.findByContactId(customerId, dataGridModel).get("rows");
            for (Phone phone : result) {
                CustomerPhoneFrontDto customerPhoneFrontDto = new CustomerPhoneFrontDto();
                BeanUtils.copyProperties(phone, customerPhoneFrontDto);
                customerPhoneFrontDto.setCustomerType(CustomerFrontDto.CONTACT);
                customerPhoneFrontDto.setCustomerId(customerId);
                customerPhoneFrontDto.setCustomerPhoneId(phone.getPhoneid().toString());
                customerPhoneFrontDto.setNoAreaCodePhone(phone.getPhn2());
                String noEncryptionPhone;
                if (StringUtils.isBlank(phone.getPhonetypid()) || "4".equals(phone.getPhonetypid())){
                    noEncryptionPhone = phone.getPhn2();
                } else {
                    if (phone.getPhn1()!=null) noEncryptionPhone = phone.getPhn1() + phone.getPhn2();
                    else noEncryptionPhone = phone.getPhn2();
                }
                customerPhoneFrontDto.setNoEncryptionPhone(noEncryptionPhone);
                PhoneAddressDto phoneAddressDto = phoneService.splicePhone(noEncryptionPhone);
                String belongAddress = StringUtils.isBlank(phoneAddressDto.getCityName()) ? "" : "(" + phoneAddressDto.getProvName() + phoneAddressDto.getCityName() + ")";
                customerPhoneFrontDto.setBelongAddress(belongAddress);
                phones.add(customerPhoneFrontDto);
            }
        }
        return phones;
    }

    @RequestMapping(value = "/addressList/{customerType}/{customerId}", method = RequestMethod.GET)
    public void addressList(HttpServletResponse response,
                            @PathVariable("customerType") String customerType,
                            @PathVariable("customerId") String customerId) {
        List<AddressDto> addressDtos = new ArrayList<AddressDto>();
        if (CustomerFrontDto.CONTACT.equals(customerType)) {
            DataGridModel dataGridModel = new DataGridModel();
            dataGridModel.setPage(1);
            dataGridModel.setRows(100);
            addressDtos = (List<AddressDto>) addressService.queryAddressPageList(dataGridModel, customerId).get("rows");

        }

        String jsonData = "{\"rows\":" + jsonBinder.toJson(addressDtos) + ",\"total\":" + 100
                + " }";
        response.setContentType("text/json;charset=UTF-8");
        PrintWriter pt;
        try {
            pt = response.getWriter();
            pt.write(jsonData);
            pt.flush();
            pt.close();
        } catch (IOException e) {
            logger.error(e.getMessage(),e); //e.printStackTrace();
        }
    }

    @RequestMapping(value = "/cardList/{customerType}/{customerId}", method = RequestMethod.GET)
    @ResponseBody
    @Mask
    public Map cardList(@PathVariable("customerType") String customerType,
                        @PathVariable("customerId") String customerId) {
        List<CardDto> cards = new ArrayList<CardDto>();
        if (CustomerFrontDto.CONTACT.equals(customerType)) {
            cards = cardService.getCards(customerId);
        }
        Map result = new HashMap();
        result.put("rows", cards);
        return result;
    }

    @RequestMapping(value = "/getReceiveAddress/{provinceId}/{cityId}/{countyId}/{areaId}", method = RequestMethod.GET)
    public void cardList(HttpServletResponse response,
                         @PathVariable("provinceId") String provinceId,
                         @PathVariable("cityId") Integer cityId,
                         @PathVariable("countyId") Integer countyId,
                         @PathVariable("areaId") Integer areaId) {
        AddressDto addressDto = new AddressDto();
        addressDto.setState(provinceId);
        addressDto.setCityId(cityId);
        addressDto.setCountyId(countyId);
        addressDto.setAreaid(areaId);
        String receiveAddress = "error";
        if (addressService.checkEffectAddress(addressDto)) receiveAddress = addressService.addressConcordancyNoAddressInfo(addressDto);
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(receiveAddress), "application/json");
    }

    /**
     * 比对客户姓名 性别
     *
     * @param name         姓名
     * @param customerId   客户ID 正式客户or潜客
     * @param response
     * @return
     */
    @RequestMapping(value = "/compareNameAndSex")
    public void compareNameAndSex(
            @RequestParam(required = true, defaultValue = "") String name,
            @RequestParam(required = true, defaultValue = "") String sex,
            @RequestParam(required = true) String customerId,
            HttpServletResponse response) {
        Map map = new HashMap();
        Contact contact = null;
        try {
            contact = contactService.get(customerId);
        } catch (Exception e) {
            map.put("error", "此用户不存在，无法进行修改操作。");
        }
        if (!contact.getName().equals(name) || !sex.equals(contact.getSex())) {
            if (!contact.getName().equals(name) && orderService.haveExWarehouseOrder(customerId)) {
                map.put("error", "此用户关联未出库订单，不能修改名字。");
            }
            map.put("oldName", contact.getName());
            map.put("newName", name);
            map.put("oldSex", contact.getSex());
            map.put("newSex", sex);
            map.put("nameEdit", contact.getName().equals(name) ? false : true);
            map.put("sexEdit", sex.equals(contact.getSex()) ? false : true);
        } else {
            map.put("error", "没有进行修改操作，无需保存。");
        }
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
    }


    /**
     * 确认修改客户姓名 性别
     * @param name         姓名
     * @param customerId   客户ID 正式客户or潜客
     * @param customerType 客户类型
     * @param response
     * @return
     */
    @RequestMapping(value = "/submitEditNameAndSex")
    public void submitEditNameAndSex(
            @RequestParam(required = true, defaultValue = "") String name,
            @RequestParam(required = true, defaultValue = "") String sex,
            @RequestParam(required = true) String customerId,
            @RequestParam(required = true) String customerType,
            @RequestParam(value = "batchId",required = false)String lastBatchId,
            HttpServletResponse response) {
        Map map = new HashMap();
        if (CustomerFrontDto.CONTACT.equals(customerType)) {
            try {
                contactEditService.createEditNameAndSexBatchAndFlow(name, sex, customerId, lastBatchId);
                map.put("msg", "修改客户基本信息流程申请成功。");
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                map.put("msg", "系统后台出错，无法进行正式客户修改操作。");
            }
        } else {
            try {
                PotentialContact potentialContact = potentialContactService.queryById(Long.valueOf(customerId));
                potentialContact.setName(name);
                potentialContact.setGender(sex);
                potentialContactService.saveOrUpdate(potentialContact);
                map.put("msg", "修改潜客基本信息成功。");
            } catch (Exception e) {
                map.put("msg", "系统后台出错，无法进行潜客修改操作。");
            }
        }
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
    }

    /**
     * 比对客户电话 地址
     * @param customerId   客户ID 正式客户or潜客
     * @param customerType 客户类型
     * @return
     */
    @RequestMapping(value = "/comparePhoneAndAddress")
    @ResponseBody
    @Mask
    public Map comparePhoneAndAddress(
            @RequestParam(required = true) String phones,
            @RequestParam(required = true) String addresss,
            @RequestParam(required = true) String customerId,
            @RequestParam(required = true) String customerType) {
        Map map = new HashMap();
        DataGridModel dataGridModel = new DataGridModel();
        dataGridModel.setPage(1);
        dataGridModel.setRows(100);
        List<CustomerPhoneFrontDto> newPhones = new ArrayList<CustomerPhoneFrontDto>();
        List<CustomerPhoneFrontDto> oldPhones = getPhoneDtos(customerType, customerId, dataGridModel);
        List<AddressDto> newAddresss = new ArrayList<AddressDto>();
        try {
            newPhones = jsonBinder.getMapper().readValue(phones, new TypeReference<List<CustomerPhoneFrontDto>>() {
            });
            newAddresss = jsonBinder.getMapper().readValue(addresss, new TypeReference<List<AddressDto>>() {
            });
        } catch (IOException e) {
            logger.error( "json转换对象异常", e);
        }
        List<AddressDto> oldAddresss = (List<AddressDto>) addressService.queryAddressPageList(dataGridModel, customerId).get("rows");
        Map addressEditIndexs;
        List phoneEditIndexs;
        try {
            addressEditIndexs = editAddressIndexs(newAddresss, oldAddresss);
            phoneEditIndexs = editPhoneIndexs(newPhones, customerId);
        } catch (Exception e) {
            map.put("error", e.getMessage());
            return map;
        }
        List editAddressIndexs = (List)addressEditIndexs.get("editAddressIndexs");
        List addAddressIndexs = (List)addressEditIndexs.get("addAddressIndexs");
        if (editAddressIndexs.size() == 0 && addAddressIndexs.size() == 0 && phoneEditIndexs.size() == 0) {
            map.put("error", "没有进行修改操作，无需保存。");
        }
        map.put("newPhones", newPhones);
        map.put("oldPhones", oldPhones);
        map.put("newAddresss", newAddresss);
        map.put("oldAddresss", oldAddresss);
        map.put("phoneEditIndexs", phoneEditIndexs);
        map.put("addressEditIndexs", addressEditIndexs);
        map.put("editAddressIndexColumns", addressEditIndexs.get("editAddressIndexColumns"));
        return map;
    }

    private List editPhoneIndexs(List<CustomerPhoneFrontDto> newPhones, String customerId) throws Exception {
        List result = new ArrayList();
        Map<String, CustomerPhoneFrontDto> duplicatePhones = new HashMap();
        for (int i = 0; i < newPhones.size(); i++) {
            CustomerPhoneFrontDto newPhoneFrontDto = newPhones.get(i);
            if (StringUtils.isBlank(newPhoneFrontDto.getCustomerPhoneId())) {
                CustomerPhoneFrontDto phone = duplicatePhones.get(newPhoneFrontDto.getPhn2());
                if (phone != null&&StringUtils.equals(phone.getPhn1(), newPhoneFrontDto.getPhn1())&&StringUtils.equals(phone.getPhn3(), newPhoneFrontDto.getPhn3())&&StringUtils.equals(phone.getPhonetypid(), newPhoneFrontDto.getPhonetypid())) {
                    throw new Exception("电话\"" + newPhoneFrontDto.getPhn2() + "\"是重复添加号码。");
                } else duplicatePhones.put(newPhoneFrontDto.getPhn2(), newPhoneFrontDto);
                if (checkExistPhone(customerId, newPhoneFrontDto)) {
                    throw new Exception("电话号码\"" + newPhoneFrontDto.getPhn2() + "\"已绑定在相同客户名的不同客户编号身上。");
                }
                result.add(i);
                continue;
            }
        }
        return result;
    }

    private boolean checkExistPhone(String customerId, CustomerPhoneFrontDto newPhoneFrontDto) {
        Phone phone = new Phone();
        phone.setContactid(customerId);
        phone.setPhonetypid(newPhoneFrontDto.getPhonetypid());
        phone.setPhn1(newPhoneFrontDto.getPhn1());
        phone.setPhn3(newPhoneFrontDto.getPhn3());
        phone.setPhn2(newPhoneFrontDto.getPhn2());
        return phoneService.checkExistSameNameAndPhone(phone);
    }

    private Map editAddressIndexs(List<AddressDto> newAddresss, List<AddressDto> oldAddresss) throws Exception {
        List addAddressIndexs = new ArrayList();
        List editAddressIndexs = new ArrayList();
        Map editAddressIndexColumns = new HashMap();
        for (int i = 0; i < newAddresss.size(); i++) {
            AddressDto newAddressDto = newAddresss.get(i);
            if (StringUtils.isBlank(newAddressDto.getAddressid())) {
                addAddressIndexs.add(i);
                continue;
            }
            for (AddressDto oldAddressDto : oldAddresss) {
                if (newAddressDto.getAddressid().equals(oldAddressDto.getAddressid()) &&
                        !compareAddress(newAddressDto, oldAddressDto)) {
                    if (orderService.haveExWarehouseOrderByAddress(oldAddressDto.getAddressid())) {
                        throw new Exception("地址\""+newAddressDto.getAddress()+"\"关联有未出库订单，不能修改。");
                    }
                    editAddressIndexs.add(i);
                    List editColumns = new ArrayList();
                    if (!compareAddressFourLevelAddress(newAddressDto, oldAddressDto)) editColumns.add(1);
                    if (!compareAddressDetail(newAddressDto, oldAddressDto)) editColumns.add(2);
                    if (!compareAddressZip(newAddressDto, oldAddressDto)) editColumns.add(3);
                    editAddressIndexColumns.put(i, editColumns);
                    break;
                }
            }
        }
        Map result = new HashMap();
        result.put("addAddressIndexs", addAddressIndexs);
        result.put("editAddressIndexs", editAddressIndexs);
        result.put("editAddressIndexColumns", editAddressIndexColumns);
        return result;
    }

    @RequestMapping(value = "/haveExWarehouseOrder/{addressId}")
    @ResponseBody
    public boolean haveExWarehouseOrder(@PathVariable("addressId")String addressId) {
        return orderService.haveExWarehouseOrderByAddress(addressId);
    }

    @RequestMapping(value = "/checkDuplicatePhone/{contactId}/{phoneType}/{phoneNum}")
    @ResponseBody
    public Map checkDuplicatePhone(@PathVariable("contactId") String contactId, @PathVariable("phoneType") int phoneType, @PathVariable("phoneNum") String phoneNum) {
        DataGridModel dataGridModel = new DataGridModel();
        dataGridModel.setPage(1);
        dataGridModel.setRows(100);
        Map map = new HashMap();
        List<Phone> phones = new ArrayList<Phone>();
        if (!StringUtils.equals("newCustomer", contactId)) {
            phones = (List<Phone>) phoneService.findByContactId(contactId, dataGridModel).get("rows");
        }
        for (Phone phone : phones) {
            if (phoneType == 4 && StringUtils.equals(phone.getPhn2(), phoneNum)) {
                map.put("code", 0); //code = 0 代表自己已经包含此电话
                return map;
            }
            if (phoneType == 1 && StringUtils.equals(phone.getPhn1() + "-" + phone.getPhn2() + "-" + (phone.getPhn3() == null ? "" : phone.getPhn3()), phoneNum)) {
                map.put("code", 0); //code = 0 代表自己已经包含此电话
                return map;
            }
        }

        List<PotentialContactPhone> potentialContactPhones = new ArrayList<PotentialContactPhone>();
        if (!StringUtils.equals("newCustomer", contactId)) {
            potentialContactPhones = (List<PotentialContactPhone>) potentialContactPhoneService.findByPotentialContactId(contactId, dataGridModel).get("rows");
        }
        for (PotentialContactPhone phone : potentialContactPhones) {
            if (phoneType == 4 && StringUtils.equals(phone.getPhone2(), phoneNum)) {
                map.put("code", 0); //code = 0 代表自己已经包含此电话
                return map;
            }
            if (phoneType == 1 && StringUtils.equals(phone.getPhone1() + "-" + phone.getPhone2() + "-" + phone.getPhone3(), phoneNum)) {
                map.put("code", 0); //code = 0 代表自己已经包含此电话
                return map;
            }
        }
        int contactCount = 0;
        if (phoneType == 4) contactCount = contactService.findContactCountByPhone(phoneNum);
        if (phoneType == 1) contactCount = contactService.findContactCountByFixPhone(phoneNum);
        if (contactCount == 0) {
            map.put("code", 1); //code = 1 代表正式客户表中不存在该电话
            return map;
        } else if (contactCount == 1) {
            map.put("code", 2); //code = 2 代表正式客户表中存在一个客户绑定该电话
            List<CustomerDto> customerDtos = new ArrayList<CustomerDto>();
            if (phoneType == 4) customerDtos = (List<CustomerDto>) contactService.findContactByPhone(phoneNum, dataGridModel).get("rows");
            if (phoneType == 1) customerDtos = (List<CustomerDto>) contactService.findContactByFixPhone(phoneNum, dataGridModel).get("rows");
            map.put("bindContactId", customerDtos.get(0).getContactId());
            return map;
        } else {
            map.put("code", 3); //code = 3 代表正式客户表中存在多个客户绑定该电话
            return map;
        }
    }

    private boolean compareAddress(AddressDto newAddressDto, AddressDto oldAddressDto) {
        return compareAddressDetail(newAddressDto, oldAddressDto) &&
                compareAddressZip(newAddressDto, oldAddressDto) &&
                compareAddressFourLevelAddress(newAddressDto, oldAddressDto);
    }

    private boolean compareAddressDetail(AddressDto newAddressDto, AddressDto oldAddressDto) {
        return StringUtils.equals(newAddressDto.getAddress(), oldAddressDto.getAddress());
    }

    private boolean compareAddressZip(AddressDto newAddressDto, AddressDto oldAddressDto) {
        return StringUtils.equals(newAddressDto.getZip(), oldAddressDto.getZip());
    }

    private boolean compareAddressFourLevelAddress(AddressDto newAddressDto, AddressDto oldAddressDto) {
        return StringUtils.equals(newAddressDto.getState(), oldAddressDto.getState()) &&
                StringUtils.equals(newAddressDto.getCityId() == null ? "" : newAddressDto.getCityId().toString(), oldAddressDto.getCityId() == null ? "" : oldAddressDto.getCityId().toString()) &&
                StringUtils.equals(newAddressDto.getCountyId() == null ? "" : newAddressDto.getCountyId().toString(), oldAddressDto.getCountyId() == null ? "" : oldAddressDto.getCountyId().toString()) &&
                StringUtils.equals(newAddressDto.getAreaid() == null ? "" : newAddressDto.getAreaid().toString(), oldAddressDto.getAreaid() == null ? "" : oldAddressDto.getAreaid().toString());
    }

    /**
     * 提交修改客户电话 地址
     * @param customerId   客户ID 正式客户or潜客
     * @param response
     * @return
     */
    @RequestMapping(value = "/submitEditPhoneAndAddress")
    public void submitEditPhoneAndAddress(
            @RequestParam(required = true) String phones,
            @RequestParam(required = true) String addresss,
            @RequestParam(required = true) String customerId,
            @RequestParam(value = "batchId", required = false) String lastBatchId,
            HttpServletResponse response) {
        Map map = new HashMap();
        List<CustomerPhoneFrontDto> newPhones;
        List<AddressDto> newAddresss;
        try {
            newPhones = jsonBinder.getMapper().readValue(phones, new TypeReference<List<CustomerPhoneFrontDto>>() {
            });
            newAddresss = jsonBinder.getMapper().readValue(addresss, new TypeReference<List<AddressDto>>() {
            });
        } catch (IOException e) {
            logger.error("json转换对象异常", e);
            map.put("msg", "后台系统出错，无法提交修改申请。");
            HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
            return;
        }
        try {
            map = contactEditService.createEditPhoneAndAddressBatchAndFlow(customerId, lastBatchId, newPhones, newAddresss);
        } catch (MarketingException e) {
            map.put("msg", e.getMessage());
        }
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
    }

    @RequestMapping(value = "/updateBpm/view/{bpmId}", method = RequestMethod.GET)
    public ModelAndView bpmView(@PathVariable("bpmId") String bpmId) {
        ModelAndView mav = new ModelAndView("inbound/bpmView");
        mav.addObject("batchId", bpmId);
        return mav;
    }

    @RequestMapping(value = "/updateBpm/get/{bpmId}", method = RequestMethod.GET)
    @ResponseBody
    @Mask
    public Map getBpm(@PathVariable("bpmId") String bpmId) {
        Map map = new HashMap();
        UserBpm userBpm = userBpmService.get(Long.valueOf(bpmId));
        DataGridModel dataGridModel = new DataGridModel();
        dataGridModel.setPage(1);
        dataGridModel.setRows(100);
        Contact contact = contactService.get(userBpm.getContactID());
        List<CustomerPhoneFrontDto> oldPhones = getPhoneDtos(CustomerFrontDto.CONTACT, userBpm.getContactID(), dataGridModel);
        List<CustomerPhoneFrontDto> newPhones = copyPhoneList(oldPhones);
        List<AddressDto> oldAddresss = (List<AddressDto>) addressService.queryAddressPageList(dataGridModel, userBpm.getContactID()).get("rows");
        List<AddressDto> newAddresss = copyAddressList(oldAddresss);
        List phoneAddIndexs = new ArrayList();
        List addressEditIndexs = new ArrayList();
        List<UserBpmTask> userBpmTaskList = userBpmTaskService.queryApprovingTaskByBatchID(bpmId);
        map.put("isBaseRejected", 0);
        map.put("isPhoneRejected", 0);
        map.put("isAddressRejected", 0);
        map.put("addressComment","");
        map.put("phoneComment", "");
        map.put("basecomment","");
        for (UserBpmTask userBpmTask : userBpmTaskList) {
            processBaseInfoChange(map, userBpmTask, contact);
            processPhoneAdd(map,phoneAddIndexs, userBpmTask, newPhones);
            processAddressEdit(map,addressEditIndexs, userBpmTask, newAddresss, oldAddresss);
        }
        map.put("oldPhones", oldPhones);
        map.put("newPhones", newPhones);
        map.put("oldAddresss", oldAddresss);
        map.put("newAddresss", newAddresss);
        map.put("phoneAddIndexs", phoneAddIndexs);
        map.put("addressEditIndexs", addressEditIndexs);
        return map;
    }

    private void processAddressEdit(Map map, List addressEditIndexs, UserBpmTask userBpmTask, List<AddressDto> newAddresss, List<AddressDto> oldAddresss) {
        if (UserBpmTaskType.CONTACT_ADDRESS_CHANGE.getIndex() == Integer.valueOf(userBpmTask.getBusiType())) {
            AddressChange addressChange = addressChangeService.queryByBpmInstID(userBpmTask.getBpmInstID());
            AddressExtChange addressExtChange = addressExtChangeService.queryByBpmInstID(userBpmTask.getBpmInstID());
            if(oldAddresss == null || oldAddresss.size() == 0) {
                AddressDto newAddress = new AddressDto();
                newAddress.setInstId(userBpmTask.getBpmInstID());
                newAddress.setStatus(userBpmTask.getStatus());
                if(String.valueOf(AuditTaskStatus.REJECTED.getIndex()).equals(userBpmTask.getStatus())) {
                	map.put("isAddressRejected", 1);
                }
                if(StringUtils.isNotBlank(userBpmTask.getApproverRemark())) {
                	map.put("addressComment", userBpmTask.getApproverRemark());
                }
                newAddress.setReceiveAddress(getReceiveAddress(addressExtChange.getProvince().getProvinceid(),
                        addressExtChange.getCity().getCityid(), addressExtChange.getCounty().getCountyid(),
                        addressExtChange.getArea().getAreaid()));
                BeanUtils.copyProperties(addressChange, newAddress);
                newAddresss.add(newAddress);
                addressEditIndexs.add(newAddresss.size() - 1);
            }
            for (int i = 0; i < oldAddresss.size(); i++) {
                AddressDto oldAddress = oldAddresss.get(i);
                if (StringUtils.equals(oldAddress.getAddressid(), addressChange.getAddressid())) {
                    AddressDto newAddress = newAddresss.get(i);
                    if (StringUtils.isNotBlank(addressChange.getAddress()))
                        newAddress.setAddress(addressChange.getAddress());
                    if (StringUtils.isNotBlank(addressChange.getZip())) newAddress.setZip(addressChange.getZip());
                    if (StringUtils.isNotBlank(addressChange.getIsdefault()))
                        newAddress.setIsdefault(addressChange.getIsdefault());
                    addressEditIndexs.add(i);
                    newAddress.setInstId(userBpmTask.getBpmInstID());
                    newAddress.setStatus(userBpmTask.getStatus());
                    if(String.valueOf(AuditTaskStatus.REJECTED.getIndex()).equals(userBpmTask.getStatus())) {
                    	map.put("isAddressRejected", 1);
                    }
                    if(StringUtils.isNotBlank(userBpmTask.getApproverRemark())) {
                    	map.put("addressComment", userBpmTask.getApproverRemark());
                    }
                    newAddress.setReceiveAddress(getReceiveAddress(addressExtChange.getProvince()==null?oldAddress.getState():addressExtChange.getProvince().getProvinceid(),
                            addressExtChange.getCity()==null?oldAddress.getCityId():addressExtChange.getCity().getCityid(),
                            addressExtChange.getCounty()==null?oldAddress.getCountyId():addressExtChange.getCounty().getCountyid(),
                            addressExtChange.getArea()==null?oldAddress.getAreaid():addressExtChange.getArea().getAreaid()));
                    return;
                }
                if (i == oldAddresss.size() - 1) {
                    AddressDto newAddress = new AddressDto();
                    newAddress.setInstId(userBpmTask.getBpmInstID());
                    newAddress.setStatus(userBpmTask.getStatus());
                    if(String.valueOf(AuditTaskStatus.REJECTED.getIndex()).equals(userBpmTask.getStatus())) {
                    	map.put("isAddressRejected", 1);
                    }
                    if(StringUtils.isNotBlank(userBpmTask.getApproverRemark())) {
                    	map.put("addressComment", userBpmTask.getApproverRemark());
                    }
                    newAddress.setReceiveAddress(getReceiveAddress(addressExtChange.getProvince().getProvinceid(),
                            addressExtChange.getCity().getCityid(), addressExtChange.getCounty().getCountyid(),
                            addressExtChange.getArea().getAreaid()));
                    BeanUtils.copyProperties(addressChange, newAddress);
                    newAddresss.add(newAddress);
                    addressEditIndexs.add(newAddresss.size() - 1);
                }
            }
        }
    }

    private String getReceiveAddress(String pId, Integer cityId, Integer countyId, Integer areaId) {
        AddressDto queryAddressDto = new AddressDto();
        queryAddressDto.setState(pId);
        queryAddressDto.setCityId(cityId);
        queryAddressDto.setCountyId(countyId);
        queryAddressDto.setAreaid(areaId);
        return addressService.addressConcordancyNoAddressInfo(queryAddressDto);
    }

    private List<AddressDto> copyAddressList(List<AddressDto> oldAddresss) {
        List<AddressDto> newAddresss = new ArrayList<AddressDto>();
        for (AddressDto oldAddress : oldAddresss) {
            AddressDto newAddress = new AddressDto();
            BeanUtils.copyProperties(oldAddress, newAddress);
            newAddresss.add(newAddress);
        }
        return newAddresss;
    }

    private void processPhoneAdd(Map map, List addPhoneIndex, UserBpmTask userBpmTask, List<CustomerPhoneFrontDto> newPhones) {
        if (UserBpmTaskType.CONTACT_PHONE_CHANGE.getIndex() == Integer.valueOf(userBpmTask.getBusiType())) {
            PhoneChange phoneChange = phoneChangeService.queryByBpmInstID(userBpmTask.getBpmInstID());
            CustomerPhoneFrontDto customerPhoneFrontDto = new CustomerPhoneFrontDto();
            BeanUtils.copyProperties(phoneChange, customerPhoneFrontDto);
            customerPhoneFrontDto.setInstId(userBpmTask.getBpmInstID());
            customerPhoneFrontDto.setStatus(userBpmTask.getStatus());
            if(String.valueOf(AuditTaskStatus.REJECTED.getIndex()).equals(userBpmTask.getStatus())) {
            	map.put("isPhoneRejected", 1);
            }
            if(StringUtils.isNotBlank(userBpmTask.getApproverRemark())) {
            	map.put("phoneComment", userBpmTask.getApproverRemark());
            }
            newPhones.add(customerPhoneFrontDto);
            addPhoneIndex.add(newPhones.size() - 1);
        }
    }

    private List copyPhoneList(List<CustomerPhoneFrontDto> oldPhones) {
        List<CustomerPhoneFrontDto> newPhones = new ArrayList<CustomerPhoneFrontDto>();
        for (CustomerPhoneFrontDto oldPhone : oldPhones) {
            CustomerPhoneFrontDto newPhone = new CustomerPhoneFrontDto();
            BeanUtils.copyProperties(oldPhone, newPhone);
            newPhones.add(newPhone);
        }
        return newPhones;
    }

    private void processBaseInfoChange(Map map, UserBpmTask userBpmTask, Contact contact) {
        map.put("oldName", contact.getName());
        map.put("newName", contact.getName());
        map.put("oldSex", contact.getSex());
        map.put("newSex", contact.getSex());
        if (UserBpmTaskType.CONTACT_BASE_CHANGE.getIndex() == Integer.valueOf(userBpmTask.getBusiType())) {
            ContactChange contactChange = contactChangeService.queryByBpmInstID(userBpmTask.getBpmInstID());
            map.put("newName", StringUtils.isBlank(contactChange.getName()) ? contact.getName() : contactChange.getName());
            map.put("newSex", StringUtils.isBlank(contactChange.getSex()) ? contact.getSex() : contactChange.getSex());
            map.put("nameEdit", StringUtils.isNotBlank(contactChange.getName()));
            map.put("sexEdit", StringUtils.isNotBlank(contactChange.getSex()));
            map.put("baseInfoInstId", userBpmTask.getBpmInstID());
            map.put("baseInfoStatus", userBpmTask.getStatus());
            if(String.valueOf(AuditTaskStatus.REJECTED.getIndex()).equals(userBpmTask.getStatus())) {
            	map.put("isBaseRejected", 1);
            }
            map.put("basecomment", userBpmTask.getApproverRemark());
        }
    }

    @RequestMapping(value = "/setPrimePhone",method= RequestMethod.POST)
    public void setPrimePhone(HttpServletResponse response,
            @RequestParam(required = true) String customerId,
            @RequestParam(required = true) String phoneId,
            @RequestParam(required = true) String customerType)  {
        if (CustomerFrontDto.CONTACT.equals(customerType)) {
            phoneService.setPrimePhone(customerId, phoneId);
        } else {
            potentialContactPhoneService.setPrimePotentialContactPhone(customerId, phoneId);
        }
        Map map = new HashMap();
        map.put("msg", "重置客户主电话成功");
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
    }

    @RequestMapping(value = "/updateAddressArea",method= RequestMethod.POST)
    public void updateAddressArea(HttpServletResponse response,
                              @RequestParam(required = true) String addressId,
                              @RequestParam(required = true) Integer areaId) {
        addressService.updateAddressArea(addressId, areaId);
        Map map = new HashMap();
        map.put("result", true);
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
    }

    @RequestMapping(value = "/setBackupPhone",method= RequestMethod.POST)
    public void setBackupPhone(HttpServletResponse response,
                              @RequestParam(required = true) String customerId,
                              @RequestParam(required = true) String phoneId,
                              @RequestParam(required = true) String customerType,
                              @RequestParam(required = true) Boolean isBackup)  {
        if (CustomerFrontDto.CONTACT.equals(customerType)) {
            if (isBackup) phoneService.setBackupPhone(customerId, phoneId);
            else phoneService.unsetBackupPhone(phoneId);
        } else {
            if (isBackup) potentialContactPhoneService.setBackupPotentialContactPhone(customerId, phoneId);
            else potentialContactPhoneService.unsetBackupPotentialContactPhone(phoneId);
        }
        Map map = new HashMap();
        map.put("msg", "设置客户备选电话成功");
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
    }

    @RequestMapping(value = "/setMainAddress", method = RequestMethod.POST)
    public void setMainAddress(HttpServletResponse response,
                               @RequestParam(required = true) String customerId,
                               @RequestParam(required = true) String addressId) {
        addressService.updateContactMainAddress(customerId, addressId);
        Map map = new HashMap();
        map.put("msg", "重置客户主地址成功");
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
    }

    /*=============================================================================*/

    @RequestMapping(value="/queryOrderHistory")
    @ResponseBody
    public Map<String, Object> queryOrderHistory(String contactId, DataGridModel dataGrid){
    	Map<String, Object> rsMap = orderService.queryOrderListByContactId(dataGrid, contactId);
    	return rsMap;
    }

    @RequestMapping(value="/queryContactTasks/{customerType}/{customerId}")
    @ResponseBody
    public Map<String, Object> queryContactTasks(@PathVariable("customerType") String customerType,
                                                 @PathVariable("customerId") String customerId) {
        //result 中 code 的说明： 0前台直接弹出msg，1前台弹出新建任务列表框 2前台直接外呼电话 3前台弹出任务列表框
        CampaignTaskDto campaignTaskDto = new CampaignTaskDto();
        campaignTaskDto.setCustomerID(customerId);
        campaignTaskDto.setUserID(SecurityHelper.getLoginUser().getUserId());
        campaignTaskDto.setStartDate(DateUtil.dateToString(DateUtil.addTime(new Date(), Calendar.DAY_OF_YEAR, -179)));
        DataGridModel dataGridModel = new DataGridModel();
        dataGridModel.setPage(1);
        dataGridModel.setRows(100);
        Map result = new HashMap();
        List<CampaignTaskVO> campaignTaskVOList;
        try {
            List<CampaignTaskVO> campaignTaskVOs = (List<CampaignTaskVO>) campaignBPMTaskService.queryMarketingTask(campaignTaskDto, dataGridModel).get("rows");
            campaignTaskVOList = removeOutDateTasks(campaignTaskVOs);
        } catch (MarketingException e) {
            logger.error("查询客户任务出错。" + e.getMessage(), e);
            result.put("code", "0");
            result.put("msg", "此客户不能提供外拨服务。");
            return result;
        }
        if (campaignTaskVOList.size() == 0) {
            if (CustomerFrontDto.POTENTIAL_CONTACT.equals(customerType)) {
                result.put("code", "0");
                result.put("msg", "此客户为潜在客户，无法新建任务外拨。");
                return result;
            }
            if (contactService.havePermissionCreateTask(SecurityHelper.getLoginUser().getUserId(), customerId)
                    ||cusnoteService.haveNoRepalyNotice(SecurityHelper.getLoginUser().getUserId(), customerId)
                    ||SecurityHelper.getLoginUser().hasRole(SecurityConstants.ROLE_CREATE_TASK)) {
                List<CampaignDto> campaignList;
                try {
                    campaignList = campaignApiService.queryOldCustomerCampaign(SecurityHelper.getLoginUser().getDepartment());
                } catch (ServiceException e) {
                    logger.error("查询任务列表出错。" + e.getMessage(), e);
                    result.put("code", "0");
                    result.put("msg", "此客户不能提供外拨服务。");
                    return result;
                }
                if (campaignList.size() == 0) {
                    result.put("code", "0");
                    result.put("msg", "此客户不能提供外拨服务。");
                    return result;
                } else {
                    result.put("code", "1");
                    result.put("campaignList", campaignList);
                    return result;
                }
            } else {
                result.put("code", "0");
                result.put("msg", "您没有权限为该客户新建任务外拨。");
                return result;
            }
        } else if (campaignTaskVOList.size() == 1) {
            result.put("code", "2");
            result.put("campaignTask", campaignTaskVOList.get(0));
            return result;
        } else {
            result.put("code", "3");
            result.put("myCampaignList", campaignTaskVOList);
            return result;
        }
    }

    private List<CampaignTaskVO> removeOutDateTasks(List<CampaignTaskVO> campaignTaskVOs) {
        List result = new ArrayList();
        for (CampaignTaskVO campaignTaskVO : campaignTaskVOs) {
            if (CampaignTaskStatus.INITIALIZED.getName().equals(campaignTaskVO.getLtStatus()) ||
                    CampaignTaskStatus.ACTIVE.getName().equals(campaignTaskVO.getLtStatus())) {
                result.add(campaignTaskVO);
            }
        }
        return result;
    }

    @RequestMapping(value = "/createTask")
    @ResponseBody
    public Map<String, Object> createTask(String campaignDtoStr, String contactId, String dnsi){
        Map<String, Object> rsMap = new HashMap<String, Object>();
        JSONObject jsonObj = JSONObject.fromObject(campaignDtoStr);
        CampaignDto campaignDto = (CampaignDto) JSONObject.toBean(jsonObj, CampaignDto.class);
        campaignDto.setDnis(dnsi);
        String id = "";
        try {
            id = contactService.createTask(contactId, campaignDto);
        } catch (ServiceException e) {
            logger.error("新建任务出错。" + e.getMessage(), e);
            rsMap.put("error", "无法新建任务。");
            return rsMap;
        }
        CampaignTaskDto campaignTaskDto = new CampaignTaskDto();
        campaignTaskDto.setInstId(id);
        campaignTaskDto.setUserID(SecurityHelper.getLoginUser().getUserId());
        DataGridModel dataGridModel = new DataGridModel();
        dataGridModel.setPage(1);
        dataGridModel.setRows(1);
        List<CampaignTaskVO> campaignTaskVOList;
        try {
            campaignTaskVOList = (List<CampaignTaskVO>) campaignBPMTaskService.queryMarketingTask(campaignTaskDto, dataGridModel).get("rows");
        } catch (MarketingException e) {
            logger.error("查询客户任务出错。" + e.getMessage(), e);
            rsMap.put("error", "新建任务后处理失败。");
            return rsMap;
        }
        if (campaignTaskVOList.size() == 0) {
            rsMap.put("error", "新建任务后无法获取任务内容。");
            return rsMap;
        } else {
            rsMap.put("campaignTask", campaignTaskVOList.get(0));
            return rsMap;
        }
    }
    @RequestMapping(value = "/queryCase/{contactId}",method = RequestMethod.POST)
    public  void  queryCase(@PathVariable("contactId")String contactId,DataGridModel dataGridModel, HttpServletResponse response){
        int value = casesService.getAllCasesByContactIdCount(contactId);
        List<Cases> result = casesService.getAllCasesByContactId(contactId, dataGridModel.getStartRow(), dataGridModel.getRows());
        List<Names> names = namesService.getAllNames("DELIVERED");
        List<CasesDto> casesDtoList = new ArrayList<CasesDto>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(result !=null && !result.isEmpty()){
           for (Cases cases :result){
               CasesDto casesDto =new CasesDto();

               if(cases.getCasetype()!=null){
                   casesDto.setCasetypeName(cases.getCasetype().getWfname());
               }
               casesDto.setCrdt(simpleDateFormat.format(cases.getCrdt()));
               casesDto.setCrusr(cases.getCrusr());
               casesDto.setExternal(cases.getExternal());
               casesDto.setProbdsc(cases.getProbdsc());
               casesDto.setDelivered(getDelivered(names, cases.getDelivered()));
               Cmpfback cmpfBack = casesService.getCmpfbackByCaseId(cases.getCaseid());
               if(cmpfBack!=null){
                   casesDto.setCmpfBack("协办");
               }else {
                   casesDto.setCmpfBack("非协办");
               }
               casesDtoList.add(casesDto) ;
           }
       }
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("total",value);
        map.put("rows",casesDtoList);
        HttpUtil.ajaxReturn(response,jsonBinder.toJson(map), "application/json");
    }

    private String getDelivered(List<Names> namesList, String delivered) {
        if(namesList==null || namesList.isEmpty() || delivered==null){
            return delivered;
        }
        for (Names names:namesList) {
              if(delivered.equalsIgnoreCase(names.getId().getId())){
                  return  names.getDsc();
              }
        }
        return delivered;
    }

    @RequestMapping(value = "/getLeadIntegration/{contactId}",method = RequestMethod.POST)
    public void getLeadIntegration(@PathVariable("contactId")String contactId,DataGridModel dataGridModel, HttpServletResponse response) {
        try {
            Map<String, Object> map = callHistService.getCallHistLeadInteraction(contactId, dataGridModel);
            HttpUtil.ajaxReturn(response,jsonBinder.toJson(map), "application/json");
        } catch (Exception e) {
            logger.error(e.getMessage(),e); //e.printStackTrace();
        }

    }
    @RequestMapping(value = "/getMessageHistory/{contactId}",method = RequestMethod.POST)
    public void getMessageHistory(@PathVariable("contactId")String contactId,com.chinadrtv.erp.smsapi.constant.DataGridModel dataGridModel, HttpServletResponse response) {
        try {
            Map<String, Object> map = smsApiService.findSmsSendDetailPageList(contactId,dataGridModel);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(map !=null && map.get("rows") !=null){
                List<SmsSendDetail>  smsSendDetailList = (List<SmsSendDetail>) map.get("rows");
                List<SmsSendDetailDto> smsSendDetailDtoList = new ArrayList<SmsSendDetailDto>();
                for (SmsSendDetail smsSendDetail :smsSendDetailList){
                    SmsSendDetailDto smsSendDetailDto = new SmsSendDetailDto();
                    smsSendDetailDto.setSmsType("短信发送");
                    smsSendDetailDto.setContent(smsSendDetail.getContent());
                    smsSendDetailDto.setCreateTime(simpleDateFormat.format(smsSendDetail.getCreatetime()));
                    smsSendDetailDto.setMobile(smsSendDetail.getMobile().substring(0,7)+"****");
                    smsSendDetailDto.setReceiveStatusDes(smsSendDetail.getReceiveStatusDes());
                    smsSendDetailDtoList.add(smsSendDetailDto);
                }
                map.put("rows",smsSendDetailDtoList);
            }
            HttpUtil.ajaxReturn(response,jsonBinder.toJson(map), "application/json");
        } catch (Exception e) {
            logger.error(e.getMessage(),e); //e.printStackTrace();
        }
    }
    
    /**
     * <p>查询分配历史</p>
     * @param contactId
     * @return Object list
     * @throws ServiceException 
     */
    @RequestMapping(value = "/queryAssignHistory")
    @ResponseBody
    public List<Map<String, Object>> queryAssignHistory(String contactId) {
    	List<Map<String, Object>> assignList = campaignBPMTaskService.queryContactTaskHistory(contactId);
    	
    	for(Map<String, Object> rsMap : assignList){
    		String _contactId = (String) rsMap.get("CONTACT_ID");
    		String _agentId = (String) rsMap.get("USERID");
    		rsMap.put("contactId", _contactId);
    		rsMap.put("agentId", _agentId);
    		rsMap.put("endDate", rsMap.get("END_DATE"));
    		rsMap.put("createDate", rsMap.get("CREATE_DATE"));
    		rsMap.put("sourceType", rsMap.get("SOURCE_TYPE"));
    		String userGrp = "";
    		try {
    			userGrp = userService.getUserGroup(_agentId);
			} catch (Exception e) {
                logger.error(e.getMessage(),e); //e.printStackTrace();
			}
    		rsMap.put("agentGrp", userGrp);
    	}
    	return assignList;
    }

    @RequestMapping(value = "/saveInsure")
    @ResponseBody
    public String saveInsure(Contactinsure contactinsure)
    {
        String strRet="ok";
        try{
            AgentUser user  = SecurityHelper.getLoginUser();
            contactinsure.setHasinsure(shoppingCartService.getUserdInsureProduct(user.getUserId()).size());
        contactinsure.setMdusr(SecurityHelper.getLoginUser().getUserId());
        contactinsure.setMddt(new Date());

                if(contactinsure.getRefuse() != null || contactinsure.getRefuse().equals("1")){
                   if(contactinsure.getRefuseDateTime()==null){
                       contactinsure.setRefuseDateTime(new Date());
                   }
                }


         int r = contactinsureService.saveContactinsure(contactinsure);
         if(r==1){
             strRet="已赠险";
         }

        }
        catch (Exception exp)
        {
            logger.error("save contact insure error:"+exp.getMessage(),exp);
            strRet=exp.getMessage();
        }
        return strRet;
    }

    @RequestMapping(value = "/getInsure" ,method = RequestMethod.GET)
    @ResponseBody
    public String getInsure(Contactinsure contactinsure)
    {
        String result="0";//不在拒绝有效期内,1:在拒绝有效期内
        try{
            Contactinsure getcontactinsure = contactinsureService.findContactinsure(contactinsure.getContactid());
            if(DateUtil.getDateDeviations(getcontactinsure.getRefuseDateTime(), new Date())>90 || DateUtil.getDateDeviations(getcontactinsure.getRefuseDateTime(), new Date())<0){
                result="0";
            }else{
                result ="1";
            }
        }catch (Exception e){
                 logger.warn(e.getMessage());
        }

        return result;

    }


    @RequestMapping(value = "/getInsureList" ,method = RequestMethod.GET)
    @ResponseBody
    public Map getInsureList(Contactinsure contactinsure)
    {
        Map mapResult = new HashMap();
        List<Product> productList=shoppingCartService.getUserdInsureProduct(contactinsure.getContactid());
        List<Product> productToList=shoppingCartService.getInsureList(contactinsure.getContactid(),SecurityHelper.getLoginUser().getDefGrp(), null);

        if(productList!=null&&productList.size()>0)
        {
            List<Map<String,String>> list=new ArrayList<Map<String,String>>();
            for(Product product:productList)
            {
                Map<String,String> map =new HashMap<String, String>();
                map.put("prodId",product.getProdid());
                map.put("prodName",product.getProdname());
                list.add(map);
            }
            mapResult.put("product",list);
        }

        if(productToList!=null&&productToList.size()>0)
        {
            List<Map<String,String>> list=new ArrayList<Map<String,String>>();
            for(Product product:productToList)
            {
                Map<String,String> map=new HashMap<String, String>();
                map.put("prodId",product.getProdid());
                map.put("prodName",product.getProdname());
                list.add(map);
            }
            mapResult.put("productTo",list);
        }
        return mapResult;

    }
}