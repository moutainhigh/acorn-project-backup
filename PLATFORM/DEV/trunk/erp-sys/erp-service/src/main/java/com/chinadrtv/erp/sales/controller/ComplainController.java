package com.chinadrtv.erp.sales.controller;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.model.Contact;
import com.chinadrtv.erp.model.ToService;
import com.chinadrtv.erp.uc.dto.ToServiceDto;
import com.chinadrtv.erp.uc.service.ContactService;
import com.chinadrtv.erp.uc.service.PriorityService;
import com.chinadrtv.erp.uc.service.ToServiceService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.chinadrtv.erp.util.StringUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.tools.ant.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
        model.addObject("priorityList",  priorityService.findAll());
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


    @RequestMapping(value = "/complain/complainAll", method = RequestMethod.POST)
    @ResponseBody
    public Map toServiceAll(ToServiceDto toServiceDto,DataGridModel dataGridModel) {

        return toServiceService.queryComplainPageList(toServiceDto,dataGridModel);
    }


    @RequestMapping(value = "/complain/process", method = RequestMethod.POST)
    @ResponseBody
    public Map process(@RequestParam(required = true, defaultValue = "") String contactId,
                       @RequestParam(required = true, defaultValue = "") String crdt,
                       @RequestParam(required = true, defaultValue = "") String crtm
                       ){
      Map<String,Object> map = new HashMap<String,Object>();


       String msg = "";
       Boolean result = false; //没有被锁定



       String currentUser = SecurityHelper.getLoginUser().getUserId();

       //判断此记录是否被其他人处理
       ToService toService= toServiceService.getByToServiceId(contactId, crdt,crtm);

       if(toService.getStatus().equals("1")){
           result=true;
           msg ="此条记录已被处理";
       }else if(StringUtil.isNullOrBank(toService.getOpusr())){
           //解锁其他记录
           toServiceService.unclock(currentUser);
           //锁定当前记录
           toService.setOpusr(currentUser);
           toServiceService.saveOrUpdate(toService);
       }else if(toService.getOpusr().equals(currentUser)){
           //解锁其他记录
           //toServiceService.unclock(currentUser);
           //锁定当前记录
           //toService.setOpusr(currentUser);
           //toServiceService.saveOrUpdate(toService);
       }else{
           result=true;
           msg ="此条记录已被锁定";
       }
        //如果被别人锁定返回提示"此记录,已被别人处理"
        //锁定此记录,返回事件添加界面
        map.put("result",result);
        map.put("msg",msg);
        return map;
    }

    @RequestMapping(value = "/complain/callback", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> callback(@RequestParam(required = true, defaultValue = "") String contactId,
                                       @RequestParam(required = true, defaultValue = "") String crdt,
                                       @RequestParam(required = true, defaultValue = "") String crtm)
    {
        Map map =new HashMap<String,Object>();
        logger.info("回调成功>>.");
        ToService toService= toServiceService.getByToServiceId(contactId, crdt,crtm);
        map.put("scode",toService.getProd());
        map.put("casetypeid","121");
        map.put("probdsc",toService.getContent());
        map.put("orderid",StringUtil.nullToBlank(toService.getOrderId()));
        return map;
    }
}
