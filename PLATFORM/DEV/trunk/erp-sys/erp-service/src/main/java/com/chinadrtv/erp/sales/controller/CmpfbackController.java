package com.chinadrtv.erp.sales.controller;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.model.Contact;
import com.chinadrtv.erp.model.Phone;
import com.chinadrtv.erp.model.agent.Cases;
import com.chinadrtv.erp.model.agent.Cmpfback;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.sales.dto.CmpfbackEventDto;
import com.chinadrtv.erp.service.core.dto.CmpCateDto;
import com.chinadrtv.erp.service.core.dto.CmpfbackDto;
import com.chinadrtv.erp.service.core.service.CaseService;
import com.chinadrtv.erp.service.core.service.CmpfbackService;
import com.chinadrtv.erp.uc.constants.AddressConstant;
import com.chinadrtv.erp.uc.dto.AddressDto;
import com.chinadrtv.erp.uc.service.AddressService;
import com.chinadrtv.erp.uc.service.ContactService;
import com.chinadrtv.erp.uc.service.PhoneService;
import com.chinadrtv.erp.user.aop.Mask;
import com.chinadrtv.erp.user.ldap.AcornRole;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 14-4-23
 * Time: 下午1:36
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "cmpfback")
public class CmpfbackController {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CmpfbackController.class);
    @Autowired
    private CmpfbackService cmpfbackService;
    @Autowired
    private CaseService caseService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private PhoneService phoneService;
    @Autowired
    private ContactService contactService;
    @Autowired
    private UserService userService;

    @InitBinder()
    public void orderDateBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> list(CmpfbackDto cmpfbackDto, DataGridModel dataGridModel) {
        try {
            AgentUser user = SecurityHelper.getLoginUser();
            List<AcornRole> list = userService.getRoleList(user.getUserId());
            for(AcornRole acornRole:list){
                //协办部门：   and a.result <> '1'  非协办部门： 不做result限制
                if("cmpfback_department".equalsIgnoreCase(acornRole.getName())){
                    cmpfbackDto.setCmpfbackDepartment(true);
                }else {
                    cmpfbackDto.setCmpfbackDepartment(false);
                }
            }
        }catch(Exception e1){
            logger.error("协办客服角色获取异常",e1);
        }

        Map<String, Object> cmpfbackMap = cmpfbackService.getCmpfbackList(cmpfbackDto, dataGridModel);

        return cmpfbackMap;
    }

    @RequestMapping(value = "case/{caseId}", method = RequestMethod.GET)
    @ResponseBody
    public CmpfbackEventDto getCase(@PathVariable("caseId") String caseId) {
        CmpfbackEventDto cmpfbackEventDto = new CmpfbackEventDto();
        try {

            Cmpfback cmpfback = cmpfbackService.get(caseId);
            Cases cases = caseService.findCase(caseId);

            AgentUser user = SecurityHelper.getLoginUser();
            if (cmpfback != null) {
                cmpfbackEventDto.setCmpfback(cmpfback);
            } else {
                cmpfbackEventDto.setCaseid(cases.getCaseid());

                if (StringUtils.isNotBlank(cases.getOrderid())) {
                    Order order = cmpfbackService.getOrder(cases.getOrderid());
                    if (order != null) {
                        String productCodes = this.getProductCodes(order);
                        cmpfbackEventDto.setProductdsc(productCodes);
                        cmpfbackEventDto.setOrdercrdt(order.getCrdt());
                    }
                }

                cmpfbackEventDto.setOrderid(cases.getOrderid());
                if (StringUtils.isNotBlank(cases.getScode())) {
                    cmpfbackEventDto.setProductdsc(cases.getScode());
                }

                cmpfbackEventDto.setContactid(cases.getContactid());
                cmpfbackEventDto.setProbdsc(cases.getProbdsc());
                cmpfbackEventDto.setCrusr(user.getName());
                cmpfbackEventDto.setCrdt(new Date());
                cmpfbackEventDto.setChuliusr(user.getName());
                cmpfbackEventDto.setChulidt(new Date());
                cmpfbackEventDto.setFbusr(user.getName());
                cmpfbackEventDto.setFbdt(new Date());

            }

            AddressDto addressDto = getMainAddress(cases.getContactid());
            cmpfbackEventDto.setAddress((addressDto.getReceiveAddress() == null ? "" : addressDto.getReceiveAddress())
                    + (addressDto.getAddress() == null ? "" : addressDto.getAddress()));
            cmpfbackEventDto.setZip(addressDto.getZip());
            Phone phone = getMainPhone(cases.getContactid());
            String phoneNum = getPhoneNum(phone);
            cmpfbackEventDto.setPhone(phoneNum);
            cmpfbackEventDto.setPhoneMask(phone.getPhoneMask() == null ? phoneNum : phone.getPhoneMask());
            Contact contact = contactService.get(cases.getContactid());
            cmpfbackEventDto.setContactName(contact.getName());


            List<AcornRole> list = userService.getRoleList(user.getUserId());
            for (AcornRole acornRole : list) {
                //客服主管
                if ("complain_manager".equalsIgnoreCase(acornRole.getName())) {
                    cmpfbackEventDto.setComplainManager(true);
                }
                //协办部门
                if ("cmpfback_department".equalsIgnoreCase(acornRole.getName())) {
                    cmpfbackEventDto.setCmpfbackDepartment(true);
                }
            }
        } catch (ServiceException e) {
            logger.error("协办客服角色获取异常", e);
            cmpfbackEventDto.setResultMessage("客服角色获取失败");
        } catch (Exception e1) {
            logger.error("查询协办反馈失败", e1);
            cmpfbackEventDto.setResultMessage("查询协办反馈失败");
        }
        return cmpfbackEventDto;
    }

    private String getPhoneNum(Phone phone) {
        StringBuffer stringBuffer =new StringBuffer();
        if(StringUtils.isNotBlank(phone.getPhn1())){
            stringBuffer.append(phone.getPhn1()).append("-");
        }
        if(StringUtils.isNotBlank(phone.getPhn2())){
            stringBuffer.append(phone.getPhn2());
        }
        if(StringUtils.isNotBlank(phone.getPhn3())){
            stringBuffer.append("-").append(phone.getPhn3());
        }
        return stringBuffer.toString();
    }

    private String getProductCodes(Order order) {
        StringBuffer productCodes = new StringBuffer();
        for (OrderDetail orderDet : order.getOrderdets()) {
            if (productCodes.length() > 1) {
                productCodes.append(",").append(orderDet.getProdscode());
            } else {
                productCodes.append(orderDet.getProdscode());
            }
        }
        return productCodes.toString();
    }

    //返回主地址，当没有主地址时，返回一个可用地址
    private AddressDto getMainAddress(String contactId) {
        logger.warn("协办反馈对话框查询手机号码"+contactId);
        DataGridModel dataGridModel = new DataGridModel();
        dataGridModel.setRows(999);
        Map<String, Object> addressMap = addressService.queryAddressPageList(dataGridModel, contactId);
        if (addressMap == null || addressMap.get("rows") == null) {
            return new AddressDto();
        }
        List<AddressDto> addressDtoList = (List<AddressDto>) addressMap.get("rows");

        for (AddressDto addressDto : addressDtoList) {
            if (AddressConstant.CONTACT_MAIN_ADDRESS.equalsIgnoreCase(addressDto.getIsdefault())) {
                return addressDto;
            }
        }
        for (AddressDto addressDto : addressDtoList) {
            //客户审批状态( 0:待审批,1:审批中,2:通过审批,3:未通过审批)
            if (addressDto.getStatus() == null || addressDto.getStatus().equalsIgnoreCase("2")) {
                return addressDto;
            }
        }
        return addressDtoList.get(0);
    }

    @Mask(depth = 1)
    private Phone getMainPhone(String contactId) {
        DataGridModel dataGridModel = new DataGridModel();
        dataGridModel.setRows(999);

        Map<String, Object> phoneMap = phoneService.findByContactId(contactId, dataGridModel);

        List<Phone> phoneList   = (List<Phone>) phoneMap.get("rows");

        if (phoneList == null || phoneList.isEmpty()) {
            return new Phone();
        }
        for (Phone phone : phoneList) {
            if ("Y".equals(phone.getPrmphn())) {
                return phone;
            }
        }
        for (Phone phone : phoneList) {
            //客户审批状态( 0:待审批,1:审批中,2:通过审批,3:未通过审批,4为老电话)
            if (phone.getState() == null || phone.getState().equals("2") || phone.getState().equals("4")) {
                return phone;
            }
        }
        return phoneList.get(0);
    }

    /**
     * @return
     */
    @RequestMapping(value = "cmpCate1", method = RequestMethod.POST)
    @ResponseBody
    public List<CmpCateDto> getCmpCate1() {
        List<CmpCateDto> cmpCate1 = cmpfbackService.getCmpCate1();
        return cmpCate1;
    }

    @RequestMapping(value = "cmpCate2/{cate1}", method = RequestMethod.POST)
    @ResponseBody
    public List<CmpCateDto> getCmpCate2(@PathVariable String cate1) {
        List<CmpCateDto> cmpCate2 = cmpfbackService.getCmpCate2(cate1);
        return cmpCate2;
    }



    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public String save(Cmpfback cmpfback) {
        Cmpfback oldCmpfback = cmpfbackService.get(cmpfback.getCaseid());
        AgentUser user = SecurityHelper.getLoginUser();

        if (oldCmpfback != null) {
            oldCmpfback.setProbdsc(cmpfback.getProbdsc());
            oldCmpfback.setYijian(cmpfback.getYijian());
            oldCmpfback.setYijian2(cmpfback.getYijian2());
            oldCmpfback.setReason(cmpfback.getReason());
            oldCmpfback.setResult(cmpfback.getResult());
            if(!StringUtils.equalsIgnoreCase(oldCmpfback.getChuliqk(),cmpfback.getChuliqk())){
                oldCmpfback.setChuliusr(user.getName());
                oldCmpfback.setChulidt(new Date());
            }
            oldCmpfback.setChuliqk(cmpfback.getChuliqk());

            if(!StringUtils.equalsIgnoreCase(oldCmpfback.getFeedbackdsc(),cmpfback.getFeedbackdsc())){
                oldCmpfback.setFbusr(user.getName());
                oldCmpfback.setFbdt(new Date());
            }
            oldCmpfback.setFeedbackdsc(cmpfback.getFeedbackdsc());
            oldCmpfback.setPhone(cmpfback.getPhone());
        } else {
            oldCmpfback = cmpfback;
        }
        //form date type不同，额外处理
        if (StringUtils.isNotBlank(oldCmpfback.getOrderid())) {
            Order order = cmpfbackService.getOrder(oldCmpfback.getOrderid());
            if (order != null) {
                oldCmpfback.setOrdercrdt(order.getCrdt());
            }
        }

        cmpfbackService.saveOrUpdate(oldCmpfback);
        return "success";
    }

}
