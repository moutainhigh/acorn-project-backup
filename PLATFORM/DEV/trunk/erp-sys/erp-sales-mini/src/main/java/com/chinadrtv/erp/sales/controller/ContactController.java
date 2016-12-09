package com.chinadrtv.erp.sales.controller;

import com.chinadrtv.erp.core.service.NamesService;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.dto.CampaignTaskDto;
import com.chinadrtv.erp.marketing.core.service.CampaignBPMTaskService;
import com.chinadrtv.erp.model.Contact;
import com.chinadrtv.erp.model.PotentialContact;
import com.chinadrtv.erp.sales.dto.CustomerFrontDto;
import com.chinadrtv.erp.uc.service.ContactService;
import com.chinadrtv.erp.uc.service.OldContactexService;
import com.chinadrtv.erp.uc.service.OrderService;
import com.chinadrtv.erp.uc.service.PotentialContactService;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.SecurityHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    private static final Logger logger = Logger.getLogger(ContactController.class.getName());

    @Autowired
    private ContactService contactService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PotentialContactService potentialContactService;

    @Autowired
    private NamesService namesService;

    @Autowired
    private CampaignBPMTaskService campaignBPMTaskService;

    @Autowired
    private OldContactexService oldContactexService;

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
                logger.log(Level.SEVERE, "获取积分或会员级别失败", e);
            }
        }
        mav.addObject("customer", customerFrontDto);
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
        return mav;
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
        return customerFrontDto;
    }
}