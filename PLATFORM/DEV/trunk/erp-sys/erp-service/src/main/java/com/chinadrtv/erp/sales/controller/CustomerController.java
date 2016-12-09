/*
 * @(#)CustomerController.java 1.0 2013-5-22下午2:20:53
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.sales.controller;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.constant.SecurityConstants;
import com.chinadrtv.erp.core.service.NamesService;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.exception.service.ServiceTransactionException;
import com.chinadrtv.erp.marketing.core.common.AuditTaskStatus;
import com.chinadrtv.erp.marketing.core.dto.CampaignDto;
import com.chinadrtv.erp.marketing.core.exception.MarketingException;
import com.chinadrtv.erp.marketing.core.service.CampaignApiService;
import com.chinadrtv.erp.marketing.core.service.ChangeRequestService;
import com.chinadrtv.erp.marketing.core.util.HttpUtil;
import com.chinadrtv.erp.marketing.core.util.StringUtil;
import com.chinadrtv.erp.model.Address;
import com.chinadrtv.erp.model.Contact;
import com.chinadrtv.erp.model.Phone;
import com.chinadrtv.erp.model.PotentialContact;
import com.chinadrtv.erp.model.PotentialContactPhone;
import com.chinadrtv.erp.model.SysMessage;
import com.chinadrtv.erp.model.agent.MemberLevel;
import com.chinadrtv.erp.model.agent.MemberType;
import com.chinadrtv.erp.sales.core.constant.MessageType;
import com.chinadrtv.erp.sales.core.service.SysMessageService;
import com.chinadrtv.erp.sales.dto.CustomerFrontDto;
import com.chinadrtv.erp.sales.dto.CustomerPhoneFrontDto;
import com.chinadrtv.erp.sales.service.MemberLevelService;
import com.chinadrtv.erp.sales.service.MemberTypeService;
import com.chinadrtv.erp.uc.constants.CustomerConstant;
import com.chinadrtv.erp.uc.dto.AddressDto;
import com.chinadrtv.erp.uc.dto.CustomerBaseSearchDto;
import com.chinadrtv.erp.uc.dto.CustomerDto;
import com.chinadrtv.erp.uc.dto.ObCustomerDto;
import com.chinadrtv.erp.uc.service.AddressService;
import com.chinadrtv.erp.uc.service.ContactService;
import com.chinadrtv.erp.uc.service.ObCustomerService;
import com.chinadrtv.erp.uc.service.PhoneService;
import com.chinadrtv.erp.uc.service.PotentialContactPhoneService;
import com.chinadrtv.erp.uc.service.PotentialContactService;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.chinadrtv.erp.util.DateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013-5-22 下午2:20:53 
 * 
 */
@Controller
@RequestMapping(value="/customer")
public class CustomerController extends BaseController {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	private ContactService  contactService;
	@Autowired
	private MemberTypeService memberTypeService;
	@Autowired
	private MemberLevelService memberLevelService;
	@Autowired
	private ObCustomerService obCustomerService;
	@Autowired
	private CampaignApiService campaignApiService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private PotentialContactService potentialContactService;
	@Autowired
	private PhoneService phoneService;
	@Autowired
	private PotentialContactPhoneService potentialContactPhoneService;
    @Autowired
    private NamesService namesService;
    @Autowired
    private UserService userService;
    @Autowired
    private SysMessageService sysMessageService;

    @InitBinder
    public void InitBinder(ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
    }
	
	@RequestMapping(value="/mycustomer")
	public ModelAndView mycustomer(){
		AgentUser user = SecurityHelper.getLoginUser();
		String deptId = user.getDepartment();
		
		ModelAndView mav = new ModelAndView("customer/mycustomer");
		List<MemberLevel> memberLevelList = memberLevelService.queryAll();
		List<MemberType> memberTypeList = memberTypeService.queryAll();
		try {
			List<CampaignDto> campaignList = campaignApiService.queryOldCustomerCampaign(deptId);
			mav.addObject("campaignList", campaignList);
		} catch (Exception e) {
            logger.error(e.getMessage(),e); //e.printStackTrace();
		}
		mav.addObject("memberLevelList", memberLevelList);
		mav.addObject("memberTypeList", memberTypeList);
		mav.addObject("auditTaskStatus", AuditTaskStatus.toList());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		String endDate = sdf.format(calendar.getTime());
		calendar.add(Calendar.MONTH, -3);	
		calendar.add(Calendar.DATE, 2);
		String beginDate = sdf.format(calendar.getTime());
		mav.addObject("beginDate", beginDate);
		mav.addObject("endDate", endDate);
		mav.addObject("agentId", user.getUserId());
		
		//主管权限
        boolean isSupervisor = false;
        isSupervisor |= user.hasRole(SecurityConstants.ROLE_OUTBOUND_GROUP_MANAGER);
		isSupervisor |= user.hasRole(SecurityConstants.ROLE_INBOUND_GROUP_MANAGER);
		isSupervisor |= user.hasRole(SecurityConstants.ROLE_OUTBOUND_MANAGER);
		isSupervisor |= user.hasRole(SecurityConstants.ROLE_INBOUND_MANAGER);
		mav.addObject("isSupervisor", isSupervisor);
		
		//仅仅是inbound坐席
		boolean isINB = false;
		isINB |= user.hasRole(SecurityConstants.ROLE_INBOUND_AGENT);
		mav.addObject("isINB", isINB);
		
		return mav;
	}
	
	/**
	 * <p>查询我的客户</p>
	 * @param dataGrid
	 * @param obCustomerDto
	 * @return Map<String, Object>
	 */
	@RequestMapping(value = "/queryAllCustomer")
	@ResponseBody
	public Map<String, Object> queryAllCustomer(DataGridModel dataGrid, ObCustomerDto obCustomerDto){
		Map<String, Object> pageMap = obCustomerService.queryObCustomer(dataGrid, obCustomerDto);
		return pageMap;
	}

	/**
	 * <p>查询客户对应的Campaign</p>
	 * @return Map<String, Object>
	 */
	@RequestMapping(value = "/queryCampaignList")
	@ResponseBody
	public List<CampaignDto> queryCampaignList(){
		List<CampaignDto> campaignList = new ArrayList<CampaignDto>();
		AgentUser user = SecurityHelper.getLoginUser();
		String deptId = user.getDepartment();
		try {
			campaignList = campaignApiService.queryOldCustomerCampaign(deptId);
		} catch (Exception e) {
            logger.error(e.getMessage(),e); //e.printStackTrace();
			campaignList = new ArrayList<CampaignDto>();
		}
		return campaignList;
	}
	
	/**
	 * <p>查询修改客户申请</p>
	 * @param dataGrid
	 * @return Map<String, Object>
	 * @throws MarketingException 
	 */
	@RequestMapping(value = "/queryAuditCustomer")
	@ResponseBody
	public Map<String, Object> queryAuditCustomer(DataGridModel dataGrid, ObCustomerDto obCustomerDto) throws MarketingException{
		Map<String, Object> pageMap = contactService.queryAuditPageList(dataGrid, obCustomerDto);
		return pageMap;
	}

	@RequestMapping(value = "/createTask")
	@ResponseBody
	public Map<String, Object> createTask(String campaignDtoStr, String contactId){
		Map<String, Object> rsMap = new HashMap<String, Object>();
		boolean success = false;
		String message = "";
		JSONObject jsonObj = JSONObject.fromObject(campaignDtoStr);
		CampaignDto campaignDto = (CampaignDto) JSONObject.toBean(jsonObj, CampaignDto.class);
		String id = "";
		try {
			id = contactService.createTask(contactId, campaignDto);
			success = true;
		} catch (Exception e) {
			message = e.getMessage();
            logger.error(e.getMessage(),e); //e.printStackTrace();
		}
		rsMap.put("id", id);
		rsMap.put("success", success);
		rsMap.put("message", message);
		return rsMap;
	}

    /*===================================Separator==============================================*/

    @RequestMapping(value = "/inbound", method = RequestMethod.GET)
    public ModelAndView inbound() throws Exception {
        ModelAndView mav = new ModelAndView("inbound/inbound");
        
        mav.addObject("educations", namesService.getAllNames("EDUCATION"));
        mav.addObject("marriages", namesService.getAllNames("MARRIAGE"));
        mav.addObject("occupationStatuss", namesService.getAllNames("OCCUPATIONSTATUS"));
        return mav;
    }

	/**
	 * 新建客户基本信息
	 *
     * @param name 姓名
     * @param sex  性别
     * @param response
     * @return
	 */
    @RequestMapping(value = "/mycustomer/add/formal")
    public void mycustomer_formal_add(
            @RequestParam(required = false, defaultValue = "") String customerId,
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "") String sex,
            @RequestParam(required = false, defaultValue = "") String phones,
            @RequestParam(required = false, defaultValue = "") String addresses,
            @RequestParam(required = false, defaultValue = "") String source,
            HttpServletResponse response) {
        String message = "";
        Map map = new HashMap();
        Boolean result = false;
        List<CustomerPhoneFrontDto> listPhones = new ArrayList<CustomerPhoneFrontDto>();
        List<Phone> listphone = new ArrayList<Phone>();
        List<AddressDto> listAddresses = new ArrayList<AddressDto>();

        try {
            if (StringUtils.isNotBlank(phones)) {
                listPhones = jsonBinder.getMapper().readValue(phones, new TypeReference<List<CustomerPhoneFrontDto>>() {
                });
            }
            listAddresses = jsonBinder.getMapper().readValue(addresses, new TypeReference<List<AddressDto>>() {
            });
        } catch (IOException e) {
            logger.error( "json转换对象异常", e);
            map.put("msg", "后台系统出错，无法添加客户。");
            result = false;
            map.put("result", result);
            HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
            return;
        }
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName(name);
        customerDto.setSex(sex);
        AgentUser user = SecurityHelper.getLoginUser();
        customerDto.setCrusr(user.getUserId());

        for (CustomerPhoneFrontDto cPhone : listPhones) {
            Phone phone = new Phone();
            phone.setPhonetypid(cPhone.getPhonetypid());
            if (cPhone.getPhonetypid().equals("4")) {
                phone.setPhn2(cPhone.getPhn2());
            } else {
                phone.setPhn1(cPhone.getPhn1());
                phone.setPhn2(cPhone.getPhn2());
                phone.setPhn3(cPhone.getPhn3());
            }

            phone.setPrmphn(cPhone.getPrmphn());
            listphone.add(phone);
        }
        String contactId = "";
        //潜客转正式客户
        if (StringUtils.isNotBlank(customerId)) {
            updatePotentialContact(customerId, name, sex);
            try {
                if (userService.getGroupType(user.getUserId()).equals(CustomerConstant.OUTBOUNR_TYPE) && !user.hasRole(SecurityConstants.ROLE_APPROVE_MANAGER)) {
                    potentialContactService.upgradeToContactWithFlow(Long.valueOf(customerId), listAddresses);
                    createAndSaveMessage(customerId, user, "ADD_CONTACT");
                } else potentialContactService.upgradeToContact2(Long.valueOf(customerId), listAddresses);
            } catch (Exception e) {
                logger.error("潜客转正式客户出错", e);
                map.put("msg", "后台系统出错，无法把该客户转为正式客户。" + e.getMessage());
                map.put("result", false);
                HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
                return;
            }
            PotentialContact pc = potentialContactService.queryById(Long.valueOf(customerId));
            contactId = pc.getContactId().toString();
        } else {
            Map resultMap;
            try {
                resultMap = contactService.addFormalCustomerWithFlow(customerDto, listAddresses, listphone,user, source);
                if (!StringUtils.equals(source, "shoppingCart") && !StringUtils.equals(source, "editOrder") && userService.getGroupType(user.getUserId()).equals(CustomerConstant.OUTBOUNR_TYPE) && !user.hasRole(SecurityConstants.ROLE_APPROVE_MANAGER)) {
                    createAndSaveMessage((String) resultMap.get("contactId"), user, "ADD_CONTACT");
                }
            } catch (ServiceException se) {
                logger.error( "新增客户出错", se);
                map.put("msg", se.getMessage());
                map.put("result", false);
                HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
                return;
            } catch (Exception e) {
                logger.error( "新增客户出错", e);
                map.put("msg", "后台系统出错，无法新增客户。" + e.getMessage());
                map.put("result", false);
                HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
                return;
            }
            contactId = (String) resultMap.get("contactId");
        }

        if(! StringUtil.isNullOrBank(contactId)){
            map.put("customerType", "1");
            map.put("customerId", contactId);
            message = "添加成功";
            result = true;
        }
        logger.info(message);
        map.put("msg", message);
        map.put("result", result);
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
    }



    private void createAndSaveMessage(String customerId, AgentUser user, String type) {
        try {
            SysMessage sysMessage = new SysMessage();
            sysMessage.setSourceTypeId(MessageType.valueOf(type).getIndex() + "");
            sysMessage.setContent(customerId);
            sysMessage.setCreateUser(user.getUserId());
            sysMessage.setCreateDate(new Date());
            sysMessage.setRecivierGroup(user.getWorkGrp());
            sysMessage.setDepartmentId(user.getDepartment());
            sysMessageService.addMessage(sysMessage);
        } catch (Exception e) {
            logger.error("潜客转正审批流程消息创建失败，"+e.getMessage());
        }
    }

    /**
	 * 新建客户基本信息
	 *
     *
     * @param name 姓名
     * @param sex  性别
     * @param response
     * @return
	 */
	@RequestMapping(value="/mycustomer/add")
	public void mycustomer_add(
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "") String sex,
            @RequestParam(required = false, defaultValue = "") String customerId,
            HttpServletResponse response){
		String message ="";
		 Map map = new HashMap();
		if( ! customerId.trim().equals("")){
			map.put("customerId", customerId.trim());
			message ="用户已存在！";
		}else{
		CustomerDto customerDto = new CustomerDto();
		customerDto.setName(name);
		customerDto.setSex(sex);
        try {
            map = contactService.saveCustomer(customerDto);
        } catch (ServiceTransactionException e) {
            logger.error( e.getMessage(), e);
        }
	     
	    if(map != null){
	    	if(map.get("customerId") == null){
	    		map.put("customerType", "2");
	    		map.put("customerId", map.get("potentialContactId"));
	    	}
	    
	    	message = "添加成功";
	    }else{
	    	message = "添加失败";
	    }
		}
		logger.info(message);
		map.put("result", message);
	    HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
	}

    /**
     * 电话
     * @param phn1
     * @param phn2
     * @param phn3
     * @param phonetype
     * @param response
     */
	@RequestMapping(value="/mycustomer/potentialContact/add")
	public void mycustomer_potentialContact_add(
            @RequestParam(required = false, defaultValue = "") String phn1,
            @RequestParam(required = false, defaultValue = "") String phn2,
            @RequestParam(required = false, defaultValue = "") String phn3,
            @RequestParam(required = false, defaultValue = "") String phonetype,
            HttpServletResponse response){
		String message ="";
		Boolean result = false;
		Map map = new HashMap();
		CustomerDto customerDto = new CustomerDto();
		AgentUser user = SecurityHelper.getLoginUser();
	    customerDto.setCrusr(user.getUserId());
		
		customerDto.setCrdt(DateUtil.getCurDT());
		customerDto.setCrtm(DateUtil.getCurTM());
		
		if(phonetype.equals("4")){
		customerDto.setPhone2(phn2);
		customerDto.setPhoneNum("Y");
		customerDto.setPhoneType("4");
		}else{
			customerDto.setPhone1(phn1);
			customerDto.setPhone2(phn2);
			customerDto.setPhone3(phn3);
			customerDto.setPhoneNum("Y");
			customerDto.setPhoneType(phonetype == null ? "1" : phonetype);
			
		}
		if(customerDto.getPhone2() != null &&  !customerDto.getPhone2().equals("") ){
            try {
                map = contactService.saveCustomer(customerDto);
                message = "添加潜客成功";
                result = true;
            } catch (ServiceTransactionException e) {
                logger.error( e.getMessage(), e);
                message = "添加潜客失败!"+e.getMessage();
            }
        }else{
            message="客户电话号码为空";
        }

		
		map.put("result", result);
		map.put("msg", message);
	    HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
	}

	
	/**
	 * 新建客户收货信息
	 * 
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/mycustomer/address/add")
	public void mycustomer_address_add(
			@RequestParam(required = false, defaultValue = "") String customerType,
			@RequestParam(required = false, defaultValue = "") String customerId,
			@RequestParam(required = false, defaultValue = "") String province,
			@RequestParam(required = false, defaultValue = "")  Integer cityId,
			@RequestParam(required = false, defaultValue = "")  Integer countyId,
			@RequestParam(required = false, defaultValue = "")  Integer areaId,
			@RequestParam(required = false, defaultValue = "")   String address,
			@RequestParam(required = false, defaultValue = "")   String isDefault,
			@RequestParam(required = false, defaultValue = "")   String zip,
			@RequestParam(required = false, defaultValue = "")   String phoneStr,
			@RequestParam(required = false, defaultValue = "")   String name,
			@RequestParam(required = false, defaultValue = "")   String sex,
			 HttpServletResponse response){
		String message ="";
		boolean isUpgrade = false;
		 Map map = new HashMap();
		 AddressDto addressDto = new AddressDto();
			addressDto.setState(province);
			addressDto.setCityId(cityId);
			addressDto.setCountyId(countyId);
			addressDto.setAreaid(areaId);
			addressDto.setAddress(address);
			addressDto.setIsdefault(isDefault);
			addressDto.setAddrtypid("2");
		if(customerId.equals("")){
			message ="用户不存在，请先添加用户！";
		}else{
            List<Phone> phoneList = this.convertStrToPhone(phoneStr);
            if(customerType.equals("2")){
                if(!phoneList.isEmpty()){
                    try {
                        List<PotentialContactPhone> updatePotentialContactPhones = new ArrayList<PotentialContactPhone>();
                        List<PotentialContactPhone> addPotentialContactPhones = new ArrayList<PotentialContactPhone>();
                        for (Phone phone :phoneList){
                            if("Y".equalsIgnoreCase(phone.getPrmphn())){
                                if(phone.getPhoneid()!=null){
                                    potentialContactPhoneService.setPrimePotentialContactPhone(customerId,String.valueOf(phone.getPhoneid()));
                                }else {
                                    PotentialContactPhone potentialContactPhone = convertPhoneToPotentialContactPhone(customerId, phone);
                                    PotentialContactPhone pcPhone = potentialContactPhoneService.addPotentialContactPhone(potentialContactPhone);
                                    potentialContactPhoneService.setPrimePotentialContactPhone(customerId,String.valueOf(pcPhone.getId()));
                                }
                                continue;
                            }
                            PotentialContactPhone potentialContactPhone = convertPhoneToPotentialContactPhone(customerId, phone);
                            if(phone.getPhoneid() !=null){
                                updatePotentialContactPhones.add(potentialContactPhone);
                            }else {
                                addPotentialContactPhones.add(potentialContactPhone);
                            }
                        }
                        if (!updatePotentialContactPhones.isEmpty()) {
                            potentialContactPhoneService.updatePotentialContactPhoneList(updatePotentialContactPhones);
                        }
                        if (!addPotentialContactPhones.isEmpty()) {
                            potentialContactPhoneService.addPotentialContactPhoneList(addPotentialContactPhones);
                        }
                    } catch (ServiceTransactionException e) {
                        logger.error(e.getMessage());
                        map.put("customerId", customerId);
                        map.put("customerType", 2);
                        map.put("result", e.getMessage());
                        HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
                        return;
                    }
                }
                updatePotentialContact(customerId, name, sex);
                isUpgrade=potentialContactService.upgradeToContact(Long.valueOf(customerId), province, cityId, countyId, areaId, address,zip);

			}else{
				addressDto.setContactid(customerId.toString());
				addressDto.setZip(zip);
				Address addressObj = addressService.addAddress(addressDto);
				if(isDefault.equals("-1")) addressService.updateContactMainAddress(customerId, addressObj.getAddressid());
				if(addressObj != null){
					isUpgrade =true;
				}
			}
			    if(isUpgrade){
			    	if(customerType.equals("2")){
			    		PotentialContact pc = potentialContactService.queryById(Long.valueOf(customerId)); 
			    	 	map.put("customerId", pc.getContactId());
			    	}else{
			    		map.put("customerId", customerId);
			    	}
			    	
			    	message = "添加成功";
			    	map.put("customerType", "1");
			   
			    }else{
			      	map.put("customerType", customerType);
			    	map.put("customerId", customerId);
			    	message = "添加失败";
			    }
		}
		logger.info(message);
		map.put("result", message);
	    HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
	}

    private PotentialContactPhone convertPhoneToPotentialContactPhone(String customerId, Phone phone) {
        PotentialContactPhone potentialContactPhone = new PotentialContactPhone();
        potentialContactPhone.setId(phone.getPhoneid());
        potentialContactPhone.setPhone1(phone.getPhn1());
        potentialContactPhone.setPhone2(phone.getPhn2());
        potentialContactPhone.setPhone3(phone.getPhn3());
        potentialContactPhone.setPhoneTypeId(phone.getPhonetypid());
        potentialContactPhone.setPotentialContactId(customerId);
        potentialContactPhone.setPrmphn(phone.getPrmphn());
        return potentialContactPhone;
    }


    private void updatePotentialContact(String customerId, String name, String sex) {
        if(StringUtils.isNotBlank(name) || StringUtils.isNotBlank(sex)){
            PotentialContact value = potentialContactService.queryById(Long.valueOf(customerId));
            if(StringUtils.isNotBlank(name)){
                value.setName(name);
            }
            if(StringUtils.isNotBlank(sex)){
                value.setGender(sex);
            }
            potentialContactService.updatePotentialContact(value);
        }
    }


    private List<Phone> convertStrToPhone(String phoneStr) {
        List<Phone> list = new ArrayList<Phone>();
        if(StringUtils.isBlank(phoneStr)){
            return  list;
        }
        JSONArray array = JSONArray.fromObject(phoneStr);
        for (int i=0;i<array.size();i++){
            JSONObject obj = (JSONObject) array.get(i);
            Phone phone = (Phone) JSONObject.toBean(obj, Phone.class);
            if(phone!=null && StringUtils.isNotBlank(phone.getPhn2())){
                list.add(phone);
            }
        }
        return list;
    }
	/**
	 * 新建用户电话信息
	 * @return
	 */
	@RequestMapping(value="/mycustomer/phone/add")
	public void mycustomer_phone_add(
            @RequestParam(required = false, defaultValue = "") String phonetypid,
            @RequestParam(required = false, defaultValue = "") Long customerId,
            @RequestParam(required = false, defaultValue = "") String phn1,
            @RequestParam(required = false, defaultValue = "") String phn2,
            @RequestParam(required = false, defaultValue = "") String phn3,
            @RequestParam(required = false, defaultValue = "") String phn4,
            @RequestParam(required = false, defaultValue = "") String isDefault,
            @RequestParam(required = false, defaultValue = "") String customerType,
            @RequestParam(required = false, defaultValue = "") String potentialPhoneId,
            HttpServletResponse response){
		 String message ="";
		 Boolean result = false;
		 Map map = new HashMap();
		 if(customerId==null){
		    	 message="请先添加客户基本信息";
			     map.put("message", message);
			     HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
		}else{
	
		 isDefault=isDefault.equals("checked")?"Y":"N";
		 if(customerType.equals("2")){
			 PotentialContactPhone potentialContactPhone = null;
			 if(StringUtil.isNullOrBank(potentialPhoneId)){
				  potentialContactPhone = new PotentialContactPhone();	 
			 }else{
				  potentialContactPhone = potentialContactPhoneService.findByPotentialPhoneId(Long.valueOf(potentialPhoneId));
			 }
			 
			 if(!phonetypid.equals("") ){
				 potentialContactPhone.setPhoneTypeId("1");
				 potentialContactPhone.setPhone1(phn1);
				 potentialContactPhone.setPhone2(phn2);
				 potentialContactPhone.setPhone3(phn3);
			 }else{
				 potentialContactPhone.setPhoneTypeId("4");
				 potentialContactPhone.setPhone2(phn4);
			 }
			 potentialContactPhone.setPotentialContactId(customerId.toString());
			 
			 potentialContactPhone.setPrmphn(isDefault);
			 try {
				 if(StringUtil.isNullOrBank(potentialPhoneId)){
				 PotentialContactPhone potentialContactPhone2 = potentialContactPhoneService.addPotentialContactPhone(potentialContactPhone);
				    if(isDefault.equals("Y")) potentialContactPhoneService.setPrimePotentialContactPhone(potentialContactPhone.getPotentialContactId(), potentialContactPhone2.getId().toString());
					message="添加潜客电话成功!";
					result=true;
				 }else{
					 potentialContactPhoneService.updatePotentialContactPhone(potentialContactPhone);
					 if(isDefault.equals("Y")) potentialContactPhoneService.setPrimePotentialContactPhone(potentialContactPhone.getPotentialContactId(), potentialPhoneId);
					 message="修改潜客电话成功!";
					 result=true;
				 }

			} catch (ServiceTransactionException e) {
				message=e.getMessage();
				logger.info(message+e.getMessage());
			}
			 
		 }else{
			 Phone phone = new Phone();
			 if(! phonetypid.equals("")){
				 phone.setPhonetypid("1");
				 phone.setPhn1(phn1);
				 phone.setPhn2(phn2);
				 phone.setPhn3(phn3);
			 }else{
				 phone.setPhonetypid("4");
				 phone.setPhn2(phn4);
			 }
			 phone.setPrmphn(isDefault);
			 phone.setContactid(customerId.toString());
		
		 try {
			phoneService.addPhone(phone);
			message="添加电话成功!";
			result=true;
		} catch (ServiceTransactionException e) {
			message=e.getMessage();
			logger.info(message+e.getMessage());

		}
		 
		 }
		
		map.put("result", result);
		map.put("msg", message);
		
	    HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
		}
	}
	
	
	
	/**
	 * 修改潜客电话信息
	 * @return
	 */
	@RequestMapping(value="/mycustomer/phone/delete")
	public void mycustomer_phone_delete(
            @RequestParam(required = false, defaultValue = "") String customerType,
            @RequestParam(required = false, defaultValue = "") String potentialPhoneId,
            HttpServletResponse response){
		 String message ="";
		 Boolean result=false;
		 Map map = new HashMap();
		 if(customerType.equals("2")){
			 PotentialContactPhone potentialContactPhone =  potentialContactPhoneService.findByPotentialPhoneId(Long.valueOf(potentialPhoneId));
			 try {
				potentialContactPhoneService.deletePhone(potentialContactPhone);
				message="删除潜客电话成功!";
				result=true;
			} catch (ServiceTransactionException e) {
                 logger.error(e.getMessage(),e); //e.printStackTrace();
				result=false;
				message="删除潜客电话失败!"+e.getMessage();
				map.put("msg", message);
				map.put("result", result);
				 HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
			}
		map.put("msg", message);
		map.put("result", result);
	    HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
		}
	}
	
	/**
	 * 新建用户扩展信息
	 * @return
	 */
    @RequestMapping(value = "/mycustomer/ext/add", method = RequestMethod.POST)
    public void mycustomer_ext_add(
            @RequestParam(required = false, defaultValue = "") String customerType,
            @RequestParam(required = false, defaultValue = "") String customerId,
            @RequestParam(required = false, defaultValue = "") Date birthday,
            @RequestParam(required = false, defaultValue = "") String marriage,
            @RequestParam(required = false, defaultValue = "") String income,
            @RequestParam(required = false, defaultValue = "") String occupation,
            @RequestParam(required = false, defaultValue = "") String education,
            @RequestParam(required = false, defaultValue = "") Integer carValue,
            @RequestParam(required = false, defaultValue = "") String isCar,
            @RequestParam(required = false, defaultValue = "") Date childAge,
            @RequestParam(required = false, defaultValue = "") String isChild,
            @RequestParam(required = false, defaultValue = "") Date parentAge,
            @RequestParam(required = false, defaultValue = "") String isParent,
            @RequestParam(required = false, defaultValue = "") String email,
            @RequestParam(required = false, defaultValue = "") String weixin,
            HttpServletResponse response) {
        String message = "";
        Map map = new HashMap();
        if (customerId.equals("")) {
            message = "请先添加客户基本信息";
            map.put("result", message);
            HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
        } else {
            try {
                if (customerType.equals("2")) {
                    message = "潜客暂时不支持添加扩展信息";
                } else {
                    Contact contact = contactService.get(customerId);
                    contact.setBirthday(birthday);
                    contact.setEmail(email);
                    contact.setWeixin(weixin);
                    contact.setMarriage(marriage);
                    contact.setIncome(income);
                    contact.setOccupationStatus(occupation);
                    contact.setEducation(education);
                    contact.setCar(isCar);
                    if (isCar.equals("-1")) {
                        contact.setCarmoney1(0);
                    }
                    contact.setCarmoney2(carValue);
                    contact.setChildren(isChild);
                    contact.setChildrenage(childAge);
                    contact.setHasElder(isParent);
                    contact.setElderBirthday(parentAge);
                    contactService.saveOrUpdate(contact);
                    message = "添加客户扩展信息成功!";
                }
            } catch (Exception e) {
                logger.error(e.getMessage(),e); //e.printStackTrace();
                message = "添加客户扩展信息失败!";
                logger.info(message + e.getMessage());
            } finally {
                map.put("result", message);
                HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
            }
        }
    }


    /**
	 * 查询客户
	 * @return
	 */
	@RequestMapping(value="/mycustomer/find",method = RequestMethod.POST)
	public void mycustomer_find(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam Integer rows,
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "") String phone,
            @RequestParam(required = false, defaultValue = "") String province,
            @RequestParam(required = false, defaultValue = "") String cityId,
            @RequestParam(required = false, defaultValue = "") String countyId,
            @RequestParam(required = false, defaultValue = "") String areaId,
            HttpServletResponse response){
		 Map map = new HashMap();
		 CustomerBaseSearchDto customerBaseSearchDto = new CustomerBaseSearchDto();
		 customerBaseSearchDto.setName(name);
		 customerBaseSearchDto.setPhone(phone);
		 
		 customerBaseSearchDto.setAreaId(areaId.toString());
		 customerBaseSearchDto.setCityId(cityId.toString());
		 customerBaseSearchDto.setCountyId(countyId.toString());
		 customerBaseSearchDto.setProvinceId(province);
		 DataGridModel dataGridModel = new DataGridModel();
		 dataGridModel.setPage(page);
		 dataGridModel.setRows(rows);
	   map = contactService.findByBaseCondition(customerBaseSearchDto, dataGridModel);
		logger.info("map:"+map);
		jsonBinder.setDateFormat("yyyy-MM-dd HH:mm:ss");
		 HttpUtil.ajaxReturn(response, jsonBinder.toJson(po2vo(map)), "application/json");
	}

    /**
     * 根据电话号码查询正式客户
     * @return
     */
    @RequestMapping(value="/contact/phone/find",method = RequestMethod.POST)
    public void findContactByPhone(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam Integer rows,
            @RequestParam(required = false, defaultValue = "") String phone,
            @RequestParam(required = false, defaultValue = "4") int phoneType,
            HttpServletResponse response){
        Map map = new HashMap();
        DataGridModel dataGridModel = new DataGridModel();
        dataGridModel.setPage(page);
        dataGridModel.setRows(rows);
        if (phoneType == 4) map = contactService.findContactByPhone(phone, dataGridModel);
        if (phoneType == 1) map = contactService.findContactByFixPhone(phone, dataGridModel);
        jsonBinder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(po2vo(map)), "application/json");
    }

	/**
	 * 查询客户
	 * @return
	 */
	@RequestMapping(value="/mycustomer/phone/find",method = RequestMethod.POST)
	public void mycustomer_findByPhone(
            @RequestParam(required = false, defaultValue = "") String phone,
            HttpServletResponse response){
		 Map map = new HashMap();
		 Boolean result=false;
		 CustomerBaseSearchDto customerBaseSearchDto = new CustomerBaseSearchDto();
		 customerBaseSearchDto.setPhone(phone);
		 DataGridModel dataGridModel = new DataGridModel();
		 dataGridModel.setPage(1);
		 dataGridModel.setRows(5);
	    map =contactService.findByBaseCondition(customerBaseSearchDto, dataGridModel);
		logger.info("map:"+map);
	    if(map != null){
	    	if(Integer.valueOf(map.get("total").toString()) > 0) result=true;
	    }
	    
        map.put("result", result);
		 HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
	}

	/**
	 * 保存查询客户到Session
	 * @return
	 */
	@RequestMapping(value="/mycustomer/save/find",method = RequestMethod.POST)
	public void mycustomer_save_find(
			@RequestParam(required = false, defaultValue = "") String customerId, //客户编号
			HttpServletRequest request, HttpServletResponse response){
		Map map = new HashMap();
		String message="";
		Contact contact = null;
		try {
			contact = contactService.get(customerId);
		} catch (Exception e) {
            logger.error(e.getMessage(),e); //e.printStackTrace();
		}
		CustomerDto dto = new CustomerDto();
		
		if(contact == null){
			PotentialContact pcontact  = potentialContactService.queryById(Long.valueOf(customerId));
			dto.setPotentialContactId(pcontact.getContactId().toString());
			dto.setName(pcontact.getName());
			dto.setCrdt(DateUtil.dateToString(pcontact.getCrdt()));
			dto.setCrusr(pcontact.getCrusr());
		}else{
			   dto.setContactId(contact.getContactid());
			   AddressDto addressdto = addressService.getContactMainAddress(contact.getContactid());
			   dto.setAddressDto(addressdto);
			   dto.setName(contact.getName());
			   dto.setLevel(contact.getMemberlevel());
			   dto.setCrdt(contact.getCrdtString());
			   dto.setCrusr(contact.getCrusr());
		}
		if(request.getSession().getAttribute("findCustomerResult") != null ){
		  request.getSession().removeAttribute("findCustomerResult");
		}
		request.getSession().setAttribute("findCustomerResult",dto);
		map.put("result", message);
		 HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
	}
	
	/**
	 * 根据客户编号,运单号,订单号 ,查询客户
	 * @return
	 */
	@RequestMapping(value="/mycustomer/more/find",method = RequestMethod.POST)
	public void mycustomer_more_find(
            @RequestParam(required = false, defaultValue = "") String typeValue, //客户编号
            @RequestParam(required = false, defaultValue = "") String findValue,//运单号
            HttpServletResponse response){
		 Map map = new HashMap();
        Contact contact = null;
	     CustomerDto dto = null;
	     String customerId="",shipmentId="",orderId="";
	     if(typeValue.equals("customerId")) customerId=findValue;
	     if(typeValue.equals("shipmentId")) shipmentId=findValue;
	     if(typeValue.equals("orderId")) orderId=findValue;
	     if(!customerId.equals("")){
	    	  //正式客户
		     try {
		    	 dto= new CustomerDto();
		    	 contact = contactService.get(customerId);
				if(contact == null){
					PotentialContact pcontact  = potentialContactService.queryById(Long.valueOf(customerId));
					if(pcontact != null){
					dto.setPotentialContactId(pcontact.getContactId().toString());
					dto.setName(pcontact.getName());
					dto.setCrdt(DateUtil.dateToString(pcontact.getCrdt()));
					dto.setCrusr(pcontact.getCrusr());
                    dto.setContactType(CustomerFrontDto.POTENTIAL_CONTACT);
					}
					
				}else{
					   dto.setContactId(contact.getContactid());
					   AddressDto addressdto = addressService.getContactMainAddress(contact.getContactid());
					   dto.setAddressDto(addressdto);
					   dto.setName(contact.getName());
					   dto.setLevel(contactService.findLevelByContactId(customerId));
					   dto.setCrdt(contact.getCrdtString());
					   dto.setCrusr(contact.getCrusr());
                       dto.setContactType(CustomerFrontDto.CONTACT);
				}
			} catch (Exception e) {
			     logger.info("查询客户信息失败,"+e.getMessage());
			}
	     }else if(! shipmentId.equals("")){
	    	 contact = contactService.findByMailId(shipmentId);
	       	 dto= new CustomerDto();
	    	 if(contact != null){
			   AddressDto addressdto = addressService.getContactMainAddress(contact.getContactid());
			   dto.setContactId(contact.getContactid());
			   dto.setAddressDto(addressdto);
			   dto.setName(contact.getName());
			   dto.setLevel(contactService.findLevelByContactId(customerId));
			   dto.setCrdt(contact.getCrdtString());
			   dto.setCrusr(contact.getCrusr());
               dto.setContactType(CustomerFrontDto.CONTACT);
	    	 }
	     }else if(! orderId.equals("")){
	       	 dto= new CustomerDto();
		     contact = contactService.findByOrderId(orderId);
		      if(contact != null){
			   AddressDto addressdto = addressService.getContactMainAddress(contact.getContactid());
			   dto.setContactId(contact.getContactid());
			   dto.setAddressDto(addressdto);
			   dto.setName(contact.getName());
			   dto.setLevel(contactService.findLevelByContactId(customerId));
			   dto.setCrdt(contact.getCrdtString());
			   dto.setCrusr(contact.getCrusr());
               dto.setContactType(CustomerFrontDto.CONTACT);
		      }
	     }else{
             dto= new CustomerDto();
         }
	     
		 //潜客

        List<CustomerDto> list = new ArrayList();
        if (StringUtils.isNotBlank(dto.getContactId()) || StringUtils.isNotBlank(dto.getPotentialContactId())) {
            list.add(dto);
            map.put("total", 1);
            map.put("rows", list);
        } else {
            map.put("total", 0);
            map.put("rows", list);
        }

        logger.info("map:"+map);
        jsonBinder.setDateFormat("yyyy-MM-dd HH:mm:ss");
         HttpUtil.ajaxReturn(response, jsonBinder.toJson(po2vo(map)), "application/json");
	}

	public Map po2vo(Map map){
	    List<CustomerDto> list = (List<CustomerDto>) map.get("rows");
	    List<CustomerFrontDto> result = new ArrayList();
	    Map<String,MemberLevel> mapLevel= memberLevelService.queryAlltoMap();
	
		//System.out.println(mapLevel);
		for(CustomerDto po : list  ){
			CustomerFrontDto vo = new  CustomerFrontDto();
			vo.setCustomerType(po.getContactType());
			if(po.getContactId() == null){
				vo.setCustomerId(po.getPotentialContactId());
			}else{
				vo.setCustomerId(po.getContactId());
				AddressDto addressDto= addressService.getContactMainAddress(po.getContactId());
                vo.setDetailedAddress(addressDto==null ? "":addressDto.getAddressDesc());
				vo.setAddress(addressDto==null ? "":addressService.addressConcordancyNoAddressInfo(addressDto));
				vo.setAddressid(addressDto==null ? null:addressDto.getAddressid());
			}
			vo.setName(po.getName());
			vo.setLevel(po.getLevel());
			vo.setCrusr(po.getCrusr());
			vo.setCrdt(po.getCrdtDate());
            vo.setSex(po.getSex());
			result.add(vo);
		}
		map.put("rows", result);
		
		return map; 
	}


    @RequestMapping(value="/mycustomer/getByTypeAndId",method = RequestMethod.POST)
    public void  getByTypeAndId(@RequestParam(required = false, defaultValue = "") String customerId, //客户编号
                                @RequestParam(required = false, defaultValue = "") String customerType,//客户类型
                                HttpServletResponse response){
        CustomerDto dto = new CustomerDto();
        if(Integer.valueOf(customerType) == 1){
         Contact contact =   contactService.get(customerId);
          dto.setContactId(contact.getContactid());
          dto.setName(contact.getName());
            if(StringUtil.isNullOrBank(contact.getSex())){
                dto.setSex(StringUtil.nullToBlank(contact.getSex()));
            }else{
                dto.setSex(contact.getSex().equals("1")?"男":"女");
            }
             AddressDto addressDto= addressService.getContactMainAddress(contact.getContactid());
             dto.setComments(addressDto.getAddress());
        }else{
            PotentialContact pcontact= potentialContactService.queryById(Long.valueOf(customerId));
            dto.setContactId(pcontact.getId().toString());
            if(StringUtil.isNullOrBank(pcontact.getGender())){
                dto.setSex(StringUtil.nullToBlank(pcontact.getGender()));
            }else{
                dto.setSex(pcontact.getGender().equals("1")?"男":"女");
            }
            dto.setComments("");
        }
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(dto), "application/json");
    }
}
