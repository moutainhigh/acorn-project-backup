package com.chinadrtv.erp.sales.controller;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.service.NamesService;
import com.chinadrtv.erp.marketing.core.util.StringUtil;
import com.chinadrtv.erp.model.Contact;
import com.chinadrtv.erp.model.Names;
import com.chinadrtv.erp.model.service.CaseRecall;
import com.chinadrtv.erp.service.core.constant.Common;
import com.chinadrtv.erp.service.core.service.CaseRecallService;
import com.chinadrtv.erp.uc.service.ContactService;
import com.chinadrtv.erp.user.ldap.AcornRole;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.model.GroupInfo;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: cuiming
 * Date: 14-5-4
 * Time: 下午1:51
 * To change this template use File | Settings | File Templates.
 */
@Controller("/caseRecall")
public class CaseRecallController extends BaseController {

    @Autowired
    private CaseRecallService caseRecallService;
    @Autowired
    private NamesService namesService;

    @Autowired
    private ContactService contactService;
    @Autowired
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(CaseRecallController.class);

    /**
     *   初始化页面
     * @return
     */
    @RequestMapping(value = "/caseRecall/openwindow",method = RequestMethod.GET)
    public ModelAndView  init  (CaseRecall caseRecall ) {
        ModelAndView modelAndView = new ModelAndView("/event/openCaseRecallwindow");
        modelAndView.addObject("caseid",caseRecall.getCaseid());
        modelAndView.addObject("contactid",caseRecall.getContactid());
        if (StringUtil.isNullOrBank(caseRecall.getOrderid())||caseRecall.getOrderid().equals("null")) {
            modelAndView.addObject("orderid","");
        }else {
            modelAndView.addObject("orderid",caseRecall.getOrderid());
        }
        modelAndView.addObject("recid",caseRecall.getRecid());
        if (!StringUtil.isNullOrBank(caseRecall.getRecid())) {
            modelAndView.addObject("caseRecall",caseRecallService.viewCaseReall(caseRecall));
        }
        Contact contact = contactService.findById(caseRecall.getContactid());
        modelAndView.addObject("name", contact.getName()) ;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        modelAndView.addObject("nowDate",dateFormat.format(new Date()));
//        namesService.getAllNames();
        return   modelAndView;
    }

    /**
     * 获得回访类型
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/caseRecall/caseRecallType", method = RequestMethod.POST)
    public List<Names> getCaseRecallType(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return namesService.getAllNames("CASERECALLTYPE");
    }

    /**
     * 回访状态类型
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/caseRecall/caseRecallstat", method = RequestMethod.POST)
    public List<Names> getCaseRecallstat(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return namesService.getAllNames("CASERECALLSTAT");
    }

    /**
     * 新增回访记录
     * @param caseRecall
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/caseRecall/saveRecall", method = RequestMethod.POST)
    public Map<String,Object> save (CaseRecall caseRecall,String recalldts) {

        caseRecall.setUsrid(SecurityHelper.getLoginUser().getUserId());
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(recalldts);  // Thu Jan 18 00:00:00 CST 2007
        } catch (Exception e) {
            e.printStackTrace();
        }
        caseRecall.setRecalldt(date);
        if (!StringUtil.isNullOrBank(caseRecall.getRecid())) {
            if(caseRecall.getCrdt() == null){
                caseRecall.setCrdt(caseRecall.getMddt());
            }
            caseRecall.setMddt(new Date());
            caseRecall.setMdusr(SecurityHelper.getLoginUser().getUserId());

        } else {
            caseRecall.setMddt(new Date());
            caseRecall.setCrdt(new Date());
        }
        return  caseRecallService.SaveCaseRecall(caseRecall);
    }

    @ResponseBody
    @RequestMapping(value = "/caseRecall/queryList", method = RequestMethod.POST)
    public Map<String,Object> queryList (
            @RequestParam(required = true) Date startDate,
            @RequestParam(required = true) Date endDate,
            DataGridModel dataGridModel) {
        List useridList = new ArrayList();
        try
        {
            AgentUser user =  userService.getUserById(SecurityHelper.getLoginUser().getUserId()) ;
            List<AcornRole> list  =   userService.getRoleList(SecurityHelper.getLoginUser().getUserId());
            List <AgentUser> userList =  null;
            List<GroupInfo>  groupInfoList =  null;
            List   <AgentUser> listbak = null;
            //判断是否有客服主管权限
            if (!user.hasRole(Common.CL_MANAGER)){
                useridList.add(SecurityHelper.getLoginUser().getUserId());
            }else{
                List<String> usrs = userService.getUserIdListByDept(user.getDepartment());
                if(usrs != null && usrs.size() > 0){
                    useridList.addAll(usrs);
                }
                /*
                groupInfoList = userService.getGroupList(user.getDepartment());
                for (int i=0;i<groupInfoList.size();i++) {
                    listbak =  userService.getUserList(groupInfoList.get(i).getId());
                    for (int j=0;j<listbak.size();j++){
                        useridList.add(listbak.get(j).getUserId());
                    }
                }
                */
            }
        }  catch (Exception e){
            logger.error("查询异常",e);
        }
        return caseRecallService.query(startDate, endDate, useridList,dataGridModel);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {

        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (!StringUtils.hasText(text)) {
                    return;
                } else {
                    try
                    {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        setValue(sdf.parse(text));
                    }
                    catch(Exception ex) { /* do nothing */}
                }
            }
        });
    }
}
