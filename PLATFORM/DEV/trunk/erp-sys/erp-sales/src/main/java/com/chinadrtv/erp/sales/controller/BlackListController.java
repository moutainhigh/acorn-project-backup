package com.chinadrtv.erp.sales.controller;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.service.LeadInterActionService;
import com.chinadrtv.erp.model.marketing.LeadInteraction;
import com.chinadrtv.erp.sales.dto.CustomerFrontDto;
import com.chinadrtv.erp.uc.dto.PhoneAddressDto;
import com.chinadrtv.erp.uc.service.BlackListService;
import com.chinadrtv.erp.uc.service.PhoneService;
import com.chinadrtv.erp.uc.service.PotentialContactPhoneService;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Title: BlackListController
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
@Controller
@RequestMapping(value = "blackList")
public class BlackListController extends BaseController {
    private static final Logger logger = LoggerFactory
            .getLogger(BlackListController.class);
    @Autowired
    private BlackListService blackListService;

    @Autowired
    private PhoneService phoneService;

    @Autowired
    private PotentialContactPhoneService potentialContactPhoneService;

    @Autowired
    private LeadInterActionService leadInterActionService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/addPhoneBlackList")
    @ResponseBody
    public void addPhoneBlackList(String customerId,
                                  String customerType,
                                  String phoneNum,
                                  String instId) {
        try {
            if (!"in".equalsIgnoreCase(userService.getGroupType(SecurityHelper.getLoginUser().getUserId()))) {
                logger.error("暂时只支持inbound部门使用加黑功能");
                return;
            }
        } catch (ServiceException e) {
            logger.error("获取登录坐席的groupType出错", e);
        }
        LeadInteraction leadInteraction = leadInterActionService.getLatestPhoneInterationByInstId(instId);
        if (leadInteraction == null) {
            logger.error("没有找到instId最近通话的LeadInteraction，加黑记录不能绑定到LeadInteraction. instId=" + instId);
            leadInteraction = new LeadInteraction();
        }
        Long phoneId = getPhoneId(customerId, customerType, phoneNum);
        if (phoneId == null) {
            logger.error("没有查找到对应的电话ID. customerId=" + customerId + ",customerType=" + customerType + ",phone=" + phoneNum);
            return;
        }
        blackListService.addBlackPhone(customerId, customerType, leadInteraction, phoneId, phoneNum);
        if (leadInteraction != null && leadInteraction.getLeadId() != null) leadInterActionService.updateLeadInteraction(leadInteraction.getLeadId(), "0");
    }

    private Long getPhoneId(String customerId, String customerType, String phoneNum) {
        PhoneAddressDto phoneAddressDto = phoneService.splicePhone(phoneNum);
        if (CustomerFrontDto.POTENTIAL_CONTACT.equals(customerType)) {
            return potentialContactPhoneService.findByPhoneFullNumAndPotentialContactId(phoneAddressDto.getPhn1(), phoneAddressDto.getPhn2(), phoneAddressDto.getPhn3(), customerId).getId();
        } else {
            return phoneService.findByPhoneFullNumAndContactId(phoneAddressDto.getPhn1(), phoneAddressDto.getPhn2(), phoneAddressDto.getPhn3(), customerId).getPhoneid();
        }
    }

    @RequestMapping("/blacklist")
    public String blacklist() throws Exception {
        return "blacklist/blacklist";
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryMyOrdersByContact(String phoneNum, Integer addTimes, Integer status, DataGridModel dataGridModel) {
        return blackListService.queryWithDetails(phoneNum, addTimes, status, dataGridModel);
    }

    @RequestMapping("/addToBlackPhone/{blackPhoneId}")
    @ResponseBody
    public Boolean addToBlackPhone(@PathVariable("blackPhoneId") Long blackPhoneId) {
        try {
            blackListService.addToBlackPhone(blackPhoneId);
            return true;
        } catch (Exception e) {
            logger.error("主管设置黑名单电话出错，ID=" + blackPhoneId, e);
            return false;
        }
    }

    @RequestMapping("/removeFromBlackPhone/{blackPhoneId}")
    @ResponseBody
    public Boolean removeFromBlackPhone(@PathVariable("blackPhoneId") Long blackPhoneId) {
        try {
            blackListService.removeFromBlackPhone(blackPhoneId);
            return true;
        } catch (Exception e) {
            logger.error("主管移除黑名单电话出错，ID=" + blackPhoneId, e);
            return false;
        }
    }
}
