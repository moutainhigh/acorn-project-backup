package com.chinadrtv.erp.sales.controller;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.constant.SecurityConstants;
import com.chinadrtv.erp.core.service.NamesService;
import com.chinadrtv.erp.exception.ErpException;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.exception.service.ServiceTransactionException;
import com.chinadrtv.erp.marketing.core.dto.CampaignTaskVO;
import com.chinadrtv.erp.marketing.core.service.CampaignBPMTaskService;
import com.chinadrtv.erp.marketing.core.service.LeadService;
import com.chinadrtv.erp.marketing.core.service.UserBpmTaskService;
import com.chinadrtv.erp.marketing.core.util.HttpUtil;
import com.chinadrtv.erp.model.*;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.marketing.Lead;
import com.chinadrtv.erp.model.marketing.UserBpmTask;
import com.chinadrtv.erp.sales.core.service.OrderhistService;
import com.chinadrtv.erp.sales.core.util.DateUtil;
import com.chinadrtv.erp.sales.dto.ContactAddressPhoneDto;
import com.chinadrtv.erp.sales.dto.CustomerFrontDto;
import com.chinadrtv.erp.sales.dto.OrderTypeDto;
import com.chinadrtv.erp.sales.service.OrderInfoService;
import com.chinadrtv.erp.uc.constants.AddressConstant;
import com.chinadrtv.erp.uc.dto.AddressDto;
import com.chinadrtv.erp.uc.dto.CustomerBaseSearchDto;
import com.chinadrtv.erp.uc.dto.CustomerDto;
import com.chinadrtv.erp.uc.service.AddressService;
import com.chinadrtv.erp.uc.service.ContactService;
import com.chinadrtv.erp.uc.service.PhoneService;
import com.chinadrtv.erp.uc.service.PotentialContactPhoneService;
import com.chinadrtv.erp.user.aop.Mask;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 13-6-7
 * Time: 下午5:31
 * 购物车地址急电话处理逻辑太多，抽取单独controller
 */
@Controller
@RequestMapping(value = "cart")
public class ShoppingCartAddressController extends BaseController {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ShoppingCartAddressController.class);
    @Autowired
    private AddressService addressService;
    @Autowired
    private ContactService contactService;
    @Autowired
    private PhoneService phoneService;
    @Autowired
    private OrderhistService orderhistService;
    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private NamesService namesService;
    @Autowired
    private UserBpmTaskService userBpmTaskService;
    @Autowired
    private PotentialContactPhoneService potentialContactPhoneService;
    @Autowired
    private UserService userService;
    @Autowired
    private CampaignBPMTaskService campaignBPMTaskService;
    @Autowired
    private LeadService leadService;

    /**
     * 获取订单类型
     *
     * @param instId
     * @param dnsi
     * @return
     */
    @RequestMapping(value = "getOrderType", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> getOrderType(@RequestParam(value = "instId", required = false) String instId,
                                            @RequestParam(value = "dnsi", required = false) String dnsi, @RequestParam(value = "parentId", required = false) String parentId) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            AgentUser user = SecurityHelper.getLoginUser();

            List<GrpOrderType> mediaDnisOrderTypeList = this.getMediaDnisOrderType(dnsi, user);
            List<GrpOrderType> grpOrderTypeList = orderhistService.queryGrpOderType(user.getDefGrp());

            List<GrpOrderType> orderTypeList = this.getOrderTypeList(mediaDnisOrderTypeList, grpOrderTypeList);

            if(grpOrderTypeList==null)
            {
                grpOrderTypeList=new ArrayList<GrpOrderType>();
            }
            if(StringUtils.isNotBlank(parentId))
            {
                //
                Order parentOrder=orderhistService.getOrderHistByOrderid(parentId);
                if(parentOrder!=null)
                {
                    boolean bHave=false;
                    for (GrpOrderType grpOrderType:orderTypeList)
                    {
                        if(parentOrder.getOrdertype().equals(grpOrderType.getOrderType()))
                        {
                            bHave=true;
                            break;
                        }
                    }
                    if(bHave==false)
                    {
                        GrpOrderType grpOrderType=new GrpOrderType();
                        grpOrderType.setId(-1L);
                        grpOrderType.setGrpid("");
                        grpOrderType.setDefault(false);
                        grpOrderType.setOrderType(parentOrder.getOrdertype());
                        grpOrderType.setGrpname("");
                        orderTypeList.add(grpOrderType);
                    }
                }
            }

            List<OrderTypeDto> orderTypeDtoList = new ArrayList<OrderTypeDto>();
            if (orderTypeList != null && !orderTypeList.isEmpty()) {
                List<Names> names = namesService.getAllNames("ORDERTYPE");
                for (GrpOrderType grpOrderType : orderTypeList) {
                    OrderTypeDto orderTypeDto = new OrderTypeDto(grpOrderType);
                    orderTypeDto.setOrderTypeName(getOrderTypeName(names, grpOrderType.getOrderType()));
                    orderTypeDtoList.add(orderTypeDto);
                }
            }
            map.put("result", JSONArray.fromObject(orderTypeDtoList).toString());
        } catch (ErpException e) {
            logger.error("获取订单类型失败", e);
            map.put("message", e.getMessage());
        }
        return map;
    }

    private List<GrpOrderType> getMediaDnisOrderType(String dnsi, AgentUser user) {
        List<GrpOrderType> mediaDnisOrderTypeList = new ArrayList<GrpOrderType>();
        try {
            List<String> n400List = null;
            if (StringUtils.isNotBlank(dnsi)) {
                n400List = orderhistService.queryN400ByDnis(dnsi);
            }/*else if(StringUtils.isNotBlank(instId)){
                 n400 = getLeadN400(instId);      //目前只有进线进行获取
            }*/

            if (n400List!=null && !n400List.isEmpty()) {
                String areaCode = userService.getGroupAreaCode(user.getUserId());
                mediaDnisOrderTypeList = orderhistService.queryOrderType(n400List, areaCode);
            }
        } catch (Exception e) {
            logger.error("获取没提计划对应订单类型失败", e);
        }
        return mediaDnisOrderTypeList;
    }

    private List<GrpOrderType> getOrderTypeList(List<GrpOrderType> mediaDnisOrderTypeList, List<GrpOrderType> grpOrderTypeList) {
        List<GrpOrderType> orderTypeList = new ArrayList<GrpOrderType>();
        if (mediaDnisOrderTypeList != null) {
            for (GrpOrderType grp : mediaDnisOrderTypeList) {
                grp.setDefault(true);
                orderTypeList.add(grp);
            }
        }

        if (grpOrderTypeList != null) {
            for (GrpOrderType grp : grpOrderTypeList) {
                if (mediaDnisOrderTypeList != null && !mediaDnisOrderTypeList.isEmpty()) {
                    grp.setDefault(false);
                }
                orderTypeList.add(grp);
            }
        }
        return orderTypeList;
    }

    /**
     * 查询leadid
     *
     * @param instId
     * @param contactId
     */
    @RequestMapping(value = "queryMarketingTask/{contactId}", method = RequestMethod.POST)
    @ResponseBody
    public Long queryMarketingTask(@RequestParam(value = "instId", required = false) String instId,
                                   @PathVariable("contactId") String contactId) {
        Long leadId = null;
        try {
            if (StringUtils.isNotBlank(instId)) {
                CampaignTaskVO campaignTaskVO = campaignBPMTaskService.queryMarketingTask(instId);
                if (campaignTaskVO != null && campaignTaskVO.getConid().equals(contactId)) {
                    leadId = campaignTaskVO.getLeadId();
                }
            }
            if (leadId == null) {
                AgentUser user = SecurityHelper.getLoginUser();
                Lead lead = leadService.getLastestAliveLead(user.getUserId(), contactId);
                if (lead != null) {
                    leadId = lead.getId();
                }
            }
        } catch (Exception e) {
            logger.error("获取销售线索失败", e);
        }

        return leadId;
    }

    private String getOrderTypeName(List<Names> names, String orderType) {
        for (Names name : names) {
            if (name.getId() != null && name.getId().getId().equals(orderType)) {
                return name.getDsc();
            }
        }
        return orderType;
    }

    /**
     * 获取地址列表
     *
     * @param contactId
     * @param type      single:获取单条地址  list:获取多条
     * @param response
     */
    @RequestMapping(value = "getAddressList/{contactId}/{type}", method = RequestMethod.POST)
    public void getAddressList(@PathVariable("contactId") String contactId,
                               @PathVariable("type") String type, HttpServletResponse response) {
        List<AddressDto> addressDtoList = getAddressDtoList(contactId);
        List<ContactAddressPhoneDto> contactAddressPhoneDtoList = new ArrayList<ContactAddressPhoneDto>();
        if (addressDtoList.isEmpty()) {
            HttpUtil.ajaxReturn(response, JSONArray.fromObject(contactAddressPhoneDtoList).toString(), "application/json");
            return;
        }
        String name = getContactName(contactId);
        List<Phone> phoneList = getPhones(contactId);

        if ("single".equalsIgnoreCase(type)) {
            AddressDto addressDto = getMainAddress(addressDtoList);

            ContactAddressPhoneDto contactAddressPhoneDto = new ContactAddressPhoneDto(addressDto, phoneList);
            contactAddressPhoneDto.setContactName(name);
            contactAddressPhoneDto.setContactid(contactId);
            contactAddressPhoneDtoList.add(contactAddressPhoneDto);
        } else {
            for (AddressDto addressDto : addressDtoList) {
                ContactAddressPhoneDto contactAddressPhoneDto = new ContactAddressPhoneDto(addressDto, phoneList);
                contactAddressPhoneDto.setContactName(name);
                contactAddressPhoneDto.setContactid(contactId);
                contactAddressPhoneDtoList.add(contactAddressPhoneDto);
            }
        }
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(contactAddressPhoneDtoList), "application/json");
    }

    private String getContactName(String contactId) {
        String name = "";
        try {
            Contact contact = contactService.get(contactId);
            return contact.getName();
        } catch (Exception e) {
            logger.error("客户没有姓名", e);
        }
        return name;
    }

    private List<AddressDto> getAddressDtoList(String contactId) {
        DataGridModel dataGridModel = new DataGridModel();
        dataGridModel.setRows(999);
        Map<String, Object> addressMap = addressService.queryAddressPageList(dataGridModel, contactId);
        if (addressMap == null || addressMap.get("rows") == null) {
            return new ArrayList<AddressDto>();
        }
        return (List<AddressDto>) addressMap.get("rows");
    }

    /**
     * 根据AddressId获取地址列表
     *
     * @param contactId
     * @param addressId
     * @param response
     */
    @RequestMapping(value = "getAddressByAddressId/{contactId}/{addressId}", method = RequestMethod.POST)
    public void getAddressByAddressId(@PathVariable("contactId") String contactId, @PathVariable("addressId") String addressId, HttpServletResponse response) {
        String name = getContactName(contactId);
        AddressDto addressDto = addressService.queryAddress(addressId);

        List<Phone> phoneList = getPhones(contactId);

        List<ContactAddressPhoneDto> list = new ArrayList<ContactAddressPhoneDto>();
        ContactAddressPhoneDto contactAddressPhoneDto = new ContactAddressPhoneDto(addressDto, phoneList);
        contactAddressPhoneDto.setContactName(name);
        contactAddressPhoneDto.setContactid(contactId);
        list.add(contactAddressPhoneDto);
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(list), "application/json");
    }

    private AddressDto getMainAddress(List<AddressDto> addressDtoList) {
        if (addressDtoList == null || addressDtoList.isEmpty()) {
            return new AddressDto();
        }
        for (AddressDto addressDto : addressDtoList) {
            if (AddressConstant.CONTACT_MAIN_ADDRESS.equalsIgnoreCase(addressDto.getIsdefault())) {
                return addressDto;
            }
        }
        return addressDtoList.get(0);
    }

    /**
     * 更新客户主地址
     *
     * @param contactId
     * @param addressId
     * @param response
     */
    @RequestMapping(value = "updateContactMainAddress/{contactId}/{addressId}", method = RequestMethod.GET)
    public void updateContactMainAddress(@PathVariable("contactId") Long contactId, @PathVariable("addressId") String addressId, HttpServletResponse response) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            addressService.updateContactMainAddress(String.valueOf(contactId), addressId);
        } catch (Exception e) {
            map.put("message", "更新客户主地址失败");
        }
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
    }

    /**
     * 关联未出库订单判断
     *
     * @param addressid
     * @param response
     */
    @RequestMapping(value = "validateContactAddressAndPhone", method = RequestMethod.POST)
    public void validateContactAddress(String addressid, HttpServletResponse response) {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        try {
            if (StringUtils.isNotBlank(addressid)) {
                Map<String, String> orderStatusMap = new HashMap<String, String>();
                for (Names names : namesService.getAllNames("ORDERSTATUS")) {
                    orderStatusMap.put(names.getId().getId(), names.getDsc());
                }
                List<Order> orderList = orderhistService.checkCorrelativeOrderByContact(addressid, null);
                if (orderList != null)
                    for (Order order : orderList) {
                        Map<String, Object> map = new HashMap<String, Object>(9);
                        map.put("orderId", order.getOrderid());
                        map.put("createTime", DateUtil.formatDate(order.getCrdt(), DateUtil.FORMAT_DATETIME_FULL));
                        map.put("crusr", order.getCrusr());
                        map.put("grpid", order.getGrpid());
                        map.put("contactName", orderInfoService.getContactNameByContactId(order.getContactid()));
                        map.put("status", orderStatusMap.get(order.getStatus()));
                        map.put("productPrice", order.getProdprice().setScale(2).toString());
                        map.put("mailPrice", order.getMailprice().setScale(2).toString());
                        map.put("totalPrice", order.getTotalprice().setScale(2).toString());
                        result.add(map);
                    }
            }
        } catch (ErpException e) {
            logger.error("检验客户地址失败", e);
        }
        HttpUtil.ajaxReturn(response, JSONArray.fromObject(result).toString(), "application/json");
    }

    //新增或修改客户主地址
    @RequestMapping(value = "saveOrUpdateContactAddressAndPhone", method = RequestMethod.POST)
    public void addContactAddress(AddressDto addressDto, boolean modify, String phoneStr, HttpServletResponse response) {

        Map<String, String> map = new HashMap<String, String>();
        Address addressResult = new Address();
        try {

            this.saveContactPhone(phoneStr);

            if (modify) {
                addressDto.setAuditState(0);
                addressDto.setIsdefault(AddressConstant.CONTACT_EXTRA_ADDRESS);
                addressDto.setAddressid(null);
                addressDto.setAddrtypid("2");
                addressResult = addressService.addAddress(addressDto);
            } else {
                AddressDto oldAddressDto = addressService.queryAddress(addressDto.getAddressid());
                if (StringUtils.equals(oldAddressDto.getZip(), addressDto.getZip())
                        && oldAddressDto.getState().equals(addressDto.getState())
                        && StringUtils.equals(oldAddressDto.getCityId() == null ? "" : oldAddressDto.getCityId().toString(), addressDto.getCityId() == null ? "" : addressDto.getCityId().toString())
                        && StringUtils.equals(oldAddressDto.getCountyId() == null ? "" : oldAddressDto.getCountyId().toString(), addressDto.getCountyId() == null ? "" : addressDto.getCountyId().toString())
                        && StringUtils.equals(oldAddressDto.getAreaid() == null ? "" : oldAddressDto.getAreaid().toString(), addressDto.getAreaid() == null ? "" : addressDto.getAreaid().toString())) {

                    addressResult.setAddressid(oldAddressDto.getAddressid());
                } else {
                    addressDto.setAuditState(0);
                    addressDto.setIsdefault(AddressConstant.CONTACT_EXTRA_ADDRESS);
                    addressDto.setAddressid(null);
                    addressDto.setAddrtypid("2");
                    addressDto.setAddress(oldAddressDto.getAddressDesc());
                    addressResult = addressService.addAddress(addressDto);
                }

            }

        } catch (ServiceTransactionException e) {
            logger.error("修改或新增客户手机", e);
            map.put("message", e.getMessage());
        } catch (Exception e) {
            logger.error("修改或新增客户地址", e);
            map.put("message", "修改或新增客户地址失败");
        }
        map.put("result", addressResult.getAddressid());
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
    }

    private boolean getApproveManager() {
        boolean approveManager = false;
        try {
            AgentUser user = SecurityHelper.getLoginUser();
            String groupType = userService.getGroupType(user.getUserId());
            approveManager = "in".equalsIgnoreCase(groupType) || user.hasRole(SecurityConstants.ROLE_APPROVE_MANAGER);
        } catch (ServiceException e) {
            logger.error("新增或修改客户主地址获取权限", e);
        }
        return approveManager;
    }

    private void saveContactPhone(String phoneStr) throws ServiceTransactionException {
        List<Phone> phones = this.convertStrToPhone(phoneStr, false);
        List<Phone> phoneList = new ArrayList<Phone>();
        for (Phone phone : phones) {
            if ("Y".equalsIgnoreCase(phone.getPrmphn())) {
                if (phone.getPhoneid() != null) {
                    phoneService.setPrimePhone(phone.getContactid(), String.valueOf(phone.getPhoneid()));
                } else {
                    Phone p = phoneService.addPhone(phone);
                    phoneService.setPrimePhone(phone.getContactid(), String.valueOf(p.getPhoneid()));
                }
                continue;
            }
            phone.setState(0);
            if (!checkRepeatPhone(phoneList, phone)) {
                phoneList.add(phone);
            }
        }
        if (!phoneList.isEmpty()) {
            phoneService.addPhoneList(phoneList);
        }
    }

    private boolean checkRepeatPhone(List<Phone> addList, Phone phone) {
        for (Phone p : addList) {
            if (p.getPhn2().equalsIgnoreCase(phone.getPhn2())) {
                return true;
            }
        }
        return false;
    }

    @RequestMapping(value = "getTakeAddressList/{contactId}", method = RequestMethod.POST)
    public void getTakeAddressList(@PathVariable("contactId") String contactId, HttpSession session, HttpServletResponse response) {
        List<ContactAddressPhoneDto> contactAddressPhoneDtos = new ArrayList<ContactAddressPhoneDto>();
        CustomerBaseSearchDto customerBaseSearchDto = new CustomerBaseSearchDto();
        customerBaseSearchDto.setName("上门自提点");
        DataGridModel dataGridModel = new DataGridModel();
        dataGridModel.setRows(999);
        Map<String, Object> customerMap = contactService.findByBaseCondition(customerBaseSearchDto, dataGridModel);
        List<CustomerDto> customerDtoList = (List<CustomerDto>) customerMap.get("rows");
        List<AddressDto> addressDtoList = new ArrayList<AddressDto>();
        for (CustomerDto customerDto : customerDtoList) {
            Map<String, Object> addresssMap = addressService.queryAddressPageList(dataGridModel, customerDto.getContactId());
            List<AddressDto> subAddressDtoList = (List<AddressDto>) addresssMap.get("rows");
            addressDtoList.addAll(subAddressDtoList);
        }

        String name = getContactName(contactId);
        List<Phone> phoneList = getPhones(contactId);
        for (AddressDto addressDto : addressDtoList) {
            ContactAddressPhoneDto contactAddressPhoneDto = new ContactAddressPhoneDto(addressDto, phoneList);
            contactAddressPhoneDto.setContactName(name);
            contactAddressPhoneDto.setContactid(contactId);
            contactAddressPhoneDto.setType("上门自提");
            contactAddressPhoneDtos.add(contactAddressPhoneDto);
        }

        HttpUtil.ajaxReturn(response, JSONArray.fromObject(contactAddressPhoneDtos).toString(), "application/json");
    }

    @Mask(depth = 1)
    private List<Phone> getPhones(String contactId) {
        DataGridModel dataGridModel = new DataGridModel();
        dataGridModel.setRows(999);

        Map<String, Object> phoneMap = phoneService.findByContactId(contactId, dataGridModel);
        if (phoneMap == null || phoneMap.get("rows") == null) {
            return new ArrayList<Phone>();
        }

        return (List<Phone>) phoneMap.get("rows");
    }

    @RequestMapping(value = "getCellPhones/{contactId}", method = RequestMethod.GET)
    public void getCellPhones(@PathVariable("contactId") String contactId, HttpServletResponse response) {
        Map<String, String> map = new HashMap<String, String>();
        List<Phone> phoneList = this.getPhones(contactId);
        if (phoneList == null || phoneList.isEmpty()) {
            map.put("message", "查询手机号码为空");
        } else {
            JSONArray jsonArray = new JSONArray();
            int index = 0;
            for (Phone phone : phoneList) {
                JSONObject json = new JSONObject();
                Map<String, String> phoneStrMap = new HashMap<String, String>();
                if (StringUtils.isNotBlank(phone.getPhn2()) && phone.getPhn2().length() == 11) {
                    phoneStrMap.put("prmphn",phone.getPrmphn());
                    phoneStrMap.put("phone", phone.getPhn2());
                    phoneStrMap.put("phoneStr", phone.getPhoneMask() != null ? phone.getPhoneMask() : phone.getPhn2());
                    json.accumulateAll(phoneStrMap);
                    jsonArray.add(index, json);
                    index++;
                }
            }
            map.put("result", jsonArray.toString());
        }
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");

    }

    /**
     * 获取潜客客户电话号码，url勿修改，牵扯权限
     *
     * @param contactId
     * @param response
     */
    @RequestMapping(value = "getPhones/{contactId}/{contactType}", method = RequestMethod.GET)
    public void getPhones(@PathVariable("contactId") String contactId, @PathVariable("contactType") String contactType, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        if ("1".equals(contactType)) {
            List<Phone> phoneList = this.getPhones(contactId);
            if (phoneList == null || phoneList.isEmpty()) {
                map.put("message", "查询电话号码为空");
            } else {
                map.put("result", phoneList);
            }

        } else {
            List<PotentialContactPhone> potentialContactPhoneList = potentialContactPhoneService.getPotentialContactPhones(Long.parseLong(contactId));
            if (potentialContactPhoneList == null || potentialContactPhoneList.isEmpty()) {
                map.put("message", "查询电话号码为空");
            } else {
                List<Phone> phoneList = new ArrayList<Phone>();
                for (PotentialContactPhone potentialContactPhone : potentialContactPhoneList) {
                    Phone newPhone = convertPotentialContactPhoneToPhone(potentialContactPhone);
                    phoneList.add(newPhone);
                    map.put("result", phoneList);
                }
            }
        }
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
    }

    private Phone convertPotentialContactPhoneToPhone(PotentialContactPhone potentialContactPhone) {
        Phone newPhone = new Phone();
        newPhone.setPhn1(potentialContactPhone.getPhone1());
        newPhone.setPhn2(potentialContactPhone.getPhone2());
        newPhone.setPhn3(potentialContactPhone.getPhone3());
        newPhone.setPhoneNum(potentialContactPhone.getPhoneNum());
        newPhone.setPhonetypid(potentialContactPhone.getPhoneTypeId());
        newPhone.setPrmphn(potentialContactPhone.getPrmphn());
        newPhone.setContactid(potentialContactPhone.getPotentialContactId());
        newPhone.setBlack(potentialContactPhone.getBlack());
        newPhone.setPhone_mask(potentialContactPhone.getPhoneMask());
        return newPhone;
    }

    @ResponseBody
    @RequestMapping(value = "/check/contactName", method = RequestMethod.POST)
    public String checkContactNameBeingModified(String contactId) {
        //潜客：oub审批，inb不需要审批    是否审批判断
        if (StringUtils.isNotBlank(contactId)) {
            try {
                Contact contact = contactService.get(contactId);
                if (contact.getState() != null && (1 == contact.getState() || 3 == contact.getState())) {
                    return "auditContact";
                }
            } catch (Exception e) {
                logger.error("验证联系人", e);
            }
            try {
                Collection<UserBpmTask> results = userBpmTaskService.queryUnProcessedContactBaseChangeTask(contactId);
                return String.valueOf(!CollectionUtils.isEmpty(results));
            } catch (Exception e) {
                logger.error("查询任务出错", e);
            }
            return "false";
        } else {
            return "false";
        }
    }

    //设置主电话
    @ResponseBody
    @RequestMapping(value = "/updateContactMainPhone/{contactId}/{phoneId}/{contactType}", method = RequestMethod.GET)
    public String updateContactPhone(@PathVariable("contactId") String contactId,
                                     @PathVariable("phoneId") String phoneId,
                                     @PathVariable("contactType") String contactType) {
        try {
            if ("1".equals(contactType)) {
                Phone phone = phoneService.get(Long.parseLong(phoneId));
                if (phone.getState() != null && (phone.getState() == 0 || phone.getState() == 1 || phone.getState() == 3)) {
                    return "false";
                }
                phoneService.setPrimePhone(contactId, phoneId);
            } else {
                potentialContactPhoneService.setPrimePotentialContactPhone(contactId, phoneId);
            }
            return "true";
        } catch (Exception e) {
            return "false";
        }
    }

    //设置备选电话
    @ResponseBody
    @RequestMapping(value = "/setBackupPhone/{contactId}/{phoneId}/{contactType}", method = RequestMethod.GET)
    public String setBackupPhone(@PathVariable("contactId") String contactId, @PathVariable("phoneId") String phoneId,
                                 @PathVariable("contactType") String contactType) {
        try {
            if ("1".equals(contactType)) {
                Phone phone = phoneService.get(Long.parseLong(phoneId));
                if (phone.getState() != null && (phone.getState() == 0 || phone.getState() == 1 || phone.getState() == 3)) {
                    return "false";
                }
                phoneService.setBackupPhone(contactId, phoneId);
            } else {
                potentialContactPhoneService.setBackupPotentialContactPhone(contactId, phoneId);
            }
            return "true";
        } catch (Exception e) {
            return "false";
        }
    }

    //取消备选电话
    @ResponseBody
    @RequestMapping(value = "/unsetBackupPhone/{phoneId}/{contactType}", method = RequestMethod.GET)
    public String unsetBackupPhone(@PathVariable("phoneId") String phoneId, @PathVariable("contactType") String contactType) {
        try {
            if ("1".equals(contactType)) {
                boolean approveManager = getApproveManager();
                if (!approveManager) {
                    Phone phone = phoneService.get(Long.parseLong(phoneId));
                    if (phone.getState() != null && (phone.getState() == 0 || phone.getState() == 1 || phone.getState() == 3)) {
                        return "false";
                    }
                }
                phoneService.unsetBackupPhone(phoneId);
            } else {
                potentialContactPhoneService.unsetBackupPotentialContactPhone(phoneId);
            }
            return "true";
        } catch (Exception e) {
            return "false";
        }
    }

    /**
     * 检查重复手机号码
     * 返回值 ：0：无需处理，1：自己号码重复，2：他人号码重复，3：多人号码重复
     *
     * @param contactId
     * @param phoneStr
     * @param response
     */
    @RequestMapping(value = "checkRepeatPhone", method = RequestMethod.POST)
    public void checkRepeatPhone(String contactId, String phoneStr, HttpServletResponse response) {
        List<Phone> cellPhones = this.convertStrToPhone(phoneStr, true);
        Map<String, Object> map = new HashMap<String, Object>();
        if (cellPhones.isEmpty()) {
            map.put("result", 0);
        } else {
            List<String> list = new ArrayList<String>();
            for (Phone phone : cellPhones) {
                list.add(phone.getPhn2());
            }
            Integer count = contactService.findContactCountByPhoneList(list);
            if (count == 0) {
                map.put("result", 0);
            } else {
                DataGridModel dataGridModel = new DataGridModel();
                dataGridModel.setRows(999);
                Map<String, Object> contactMap = contactService.findContactByPhoneList(list, dataGridModel);
                List<Contact> contactList = (List<Contact>) contactMap.get("rows");
                if (contactList.size() == 1) {
                    if (contactList.get(0).getContactid().equalsIgnoreCase(contactId)) {
                        map.put("result", 1);
                    } else {
                        map.put("data", contactList.get(0).getContactid());
                        map.put("result", 2);
                    }
                } else {
                    map.put("result", 3);
                }
            }
        }

        //固定电话号码验证
        List<Phone> telephone = this.convertStrTotelePhone(phoneStr);
        List<Phone> contactPhoneList = phoneService.getPhonesByContactId(contactId);
        for (Phone contactPhone : contactPhoneList) {
            String phoneNum = (contactPhone.getPhn1() == null ? "" : contactPhone.getPhn1()) + contactPhone.getPhn2() + (contactPhone.getPhn3() == null ? "" : contactPhone.getPhn3());
            for (Phone phone : telephone) {
                String newPhoneNum = (phone.getPhn1() == null ? "" : phone.getPhn1()) + phone.getPhn2() + (phone.getPhn3() == null ? "" : phone.getPhn3());
                if (phoneNum.equals(newPhoneNum)) {
                    map.put("result", 1);
                }
            }
        }


        HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
    }

    @RequestMapping(value = "findContactByPhone", method = RequestMethod.POST)
    public void findContactByPhone(String phoneStr, DataGridModel dataGridModel, HttpServletResponse response) {
        if (StringUtils.isBlank(phoneStr)) {
            return;
        }
        List<Phone> cellPhones = this.convertStrToPhone(phoneStr, true);
        List<String> list = new ArrayList<String>();
        for (Phone phone : cellPhones) {
            list.add(phone.getPhn2());
        }
        Map<String, Object> contactMap = contactService.findContactByPhoneList(list, dataGridModel);
        List<Contact> contactList = (List<Contact>) contactMap.get("rows");
        List<CustomerFrontDto> customerDtoList = new ArrayList<CustomerFrontDto>();
        for (Contact contact : contactList) {
            CustomerFrontDto customerDto = new CustomerFrontDto();
            AddressDto address = addressService.getContactMainAddress(contact.getContactid());
            if (address != null) {
                customerDto.setAddress(address.getAddress());
            } else {
                customerDto.setAddress("");
            }
            String level = contactService.findLevelByContactId(contact.getContactid());
            customerDto.setLevel(level);

            customerDto.setCustomerId(contact.getContactid());
            customerDto.setName(contact.getName());
            customerDto.setCrusr(contact.getCrusr());
            customerDto.setCrdt(contact.getCrdt());
            customerDtoList.add(customerDto);
        }
        contactMap.put("rows", customerDtoList);
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(contactMap), "application/json");
    }

    private List<Phone> convertStrToPhone(String phoneStr, boolean cellPhoneOnly) {
        List<Phone> list = new ArrayList<Phone>();
        if (StringUtils.isBlank(phoneStr)) {
            return list;
        }
        JSONArray array = JSONArray.fromObject(phoneStr);
        for (Object object : array) {
            JSONObject jsonObject = (JSONObject) object;
            Phone phone = (Phone) JSONObject.toBean(jsonObject, Phone.class);
            if (phone != null && StringUtils.isNotBlank(phone.getPhn2())) {
                if (cellPhoneOnly) {
                    if (phone.getPhn2().length() == 11) {
                        list.add(phone);
                    }
                } else {
                    list.add(phone);
                }
            }
        }
        return list;
    }

    private List<Phone> convertStrTotelePhone(String phoneStr) {
        List<Phone> list = new ArrayList<Phone>();
        if (StringUtils.isBlank(phoneStr)) {
            return list;
        }
        JSONArray array = JSONArray.fromObject(phoneStr);
        for (Object object : array) {
            JSONObject jsonObject = (JSONObject) object;
            Phone phone = (Phone) JSONObject.toBean(jsonObject, Phone.class);
            if (phone != null && StringUtils.isNotBlank(phone.getPhn2())) {
                if (phone.getPhn2().length() != 11 && !"4".equals(phone.getPhonetypid())) {
                    list.add(phone);
                }
            }
        }
        return list;
    }

}
