package com.chinadrtv.erp.distribution.controller;

import com.chinadrtv.erp.marketing.core.service.BaseAcdInfoService;
import com.chinadrtv.erp.model.cntrpbank.AcdGroup;
import com.chinadrtv.erp.model.marketing.BaseAcdInfo;
import com.chinadrtv.erp.uc.service.AcdGroupService;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.chinadrtv.erp.util.JsonBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 * User: gaodejian
 * Date: 13-12-13
 * Time: 下午1:54
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class AcdGroupController extends BaseController {

    @Autowired
    @Deprecated
    private AcdGroupService acdGroupService;

    @Autowired
    private BaseAcdInfoService baseAcdInfoService;

    @Deprecated
    @ResponseBody
    @RequestMapping(value = "/acdgroup/load", method = RequestMethod.POST)
    public Map<String, Object> load(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<AcdGroup> list = acdGroupService.getAllAcdGroups();
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("total", list.size());
        result.put("rows", list);
        return result;
    }

    @Deprecated
    @ResponseBody
    @RequestMapping(value = "/acdgroup/lookup", method = RequestMethod.POST)
    public List<AcdGroup> lookup(
          @RequestParam(required = true) Boolean ivr,
          HttpServletRequest request, HttpServletResponse response) {
        AgentUser user = SecurityHelper.getLoginUser();
        if(user != null){
            return acdGroupService.getAcdGroupsByDeptNo(user.getDepartment(), ivr);
        } else {
            return  new ArrayList<AcdGroup>();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/acdinfo/lookup", method = RequestMethod.POST)
    public List<BaseAcdInfo> lookupAcdInfos(
            HttpServletRequest request, HttpServletResponse response) {
        AgentUser user = SecurityHelper.getLoginUser();
        if(user != null){
            return baseAcdInfoService.getBaseAcdsByDeptNo(user.getDepartment());
        } else {
            return  new ArrayList<BaseAcdInfo>();
        }
    }
}
