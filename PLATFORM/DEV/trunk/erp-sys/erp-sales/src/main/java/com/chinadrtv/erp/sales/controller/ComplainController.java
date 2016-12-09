package com.chinadrtv.erp.sales.controller;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.model.Contact;
import com.chinadrtv.erp.model.ToService;
import com.chinadrtv.erp.uc.service.ContactService;
import com.chinadrtv.erp.uc.service.PriorityService;
import com.chinadrtv.erp.uc.service.ToServiceService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * Title: ComplainController
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
@Controller
public class ComplainController extends BaseController {

    private static final Logger logger = Logger.getLogger(ContactController.class.getName());

    @Autowired
    private PriorityService priorityService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private ToServiceService toServiceService;

    @RequestMapping("/complain/complain")
    public ModelAndView complain() {
        ModelAndView model = new ModelAndView("/complain/complain");
        model.addObject("priorityList", priorityService.findAll());
        return model;
    }

    @RequestMapping(value = "/complain/getContactInfo/{contactId}", method = RequestMethod.GET)
    @ResponseBody
    public Contact getContactInfo(@PathVariable("contactId") String contactId) {
        return contactService.get(contactId);
    }

    @RequestMapping(value = "/complain/submitComplain", method = RequestMethod.POST)
    @ResponseBody
    public boolean submitComplain(ToService toService) {
        toService.setCrtm(new Date());
        toService.setStatus("0");
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            toService.setCrdt(simpleDateFormat.parse(simpleDateFormat.format(new Date())));
            toService.setCrusr(SecurityHelper.getLoginUser().getUserId());
            toServiceService.save(toService);
            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "生成投诉单出错。", e);
            return false;
        }
    }

    @RequestMapping(value = "/complain/complainList/{contactId}", method = RequestMethod.GET)
    @ResponseBody
    public Map toServiceList(@PathVariable("contactId") String contactId,
                             DataGridModel dataGridModel) {
        return toServiceService.queryComplainPageList(contactId, dataGridModel);
    }
}
