package com.chinadrtv.erp.oms.controller;

import com.chinadrtv.erp.model.*;
import com.chinadrtv.erp.model.Warehouse;
import com.chinadrtv.erp.oms.service.*;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.chinadrtv.erp.util.JsonBinder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: gaodejian
 * Date: 12-11-13
 * Time: 下午1:54
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class LogisticsFeeRuleController extends BaseController {

    @Autowired
    private LogisticsFeeRuleService logisticsFeeRuleService;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyContractService contractService;



    @RequestMapping("/company/priceRule")
    public ModelAndView price() throws Exception {
        return new ModelAndView("company/logisticsPriceRule");
    }

    @RequestMapping("/company/weightRule")
    public ModelAndView weight() throws Exception {
        return new ModelAndView("company/logisticsWeightRule");
    }

    @RequestMapping(value = "/company/logisticsFeeRule/load", method = RequestMethod.POST)
    public String load(@RequestParam(required = false, defaultValue = "1") int page,
                        @RequestParam Integer rows,
                        @RequestParam(required = false, defaultValue = "") String companyId,
                        @RequestParam(required = false, defaultValue = "") Long warehouseId,
                        @RequestParam(required = false, defaultValue = "")  Integer freightMethod,
                        HttpServletRequest request,
                        HttpServletResponse response) throws Exception {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        Map<String, Object> params = new HashMap<String, Object>();

        if(companyId != null && !companyId.equals("")){
            try
            {
                //二级ID,整型
                params.put("companyId", Integer.parseInt(companyId));
            }
            catch (NumberFormatException ex) {
                /* do nothing */
            }
        }

        if(warehouseId != null && !warehouseId.equals("")){
            params.put("warehouseId", warehouseId);
        }

        if(freightMethod != null && !freightMethod.equals("")){
            params.put("freightMethod", freightMethod);
        }

        Long totalRecords = logisticsFeeRuleService.getLogisticsFeeRuleCount(params);
        List<LogisticsFeeRule> list = logisticsFeeRuleService.getAllLogisticsFeeRules(params, (page -1) * rows, rows);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .setExclusionStrategies(
                        new ExcludeUnionResource(
                                new String[]{"parent", "orderPayTypes"}))
                .create();

        //JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
        String jsonData = "{\"rows\":" +gson.toJson(list) + ",\"total\":" + totalRecords + " }";
        response.setContentType("text/json; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().print(jsonData);
        return null;
    }

    @RequestMapping(value = "/company/logisticsFeeRule/add", method = RequestMethod.POST)
    public ModelAndView add(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/plain; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        try
        {
            LogisticsFeeRule logisticsFeeRule = new LogisticsFeeRule();
            updateLogisticsFeeRule(logisticsFeeRule, request.getParameterMap());
            logisticsFeeRule.setStatus(0);  //禁用
            logisticsFeeRule.setCreateDate(new Date());
            AgentUser agentUser = SecurityHelper.getLoginUser();
            if(agentUser != null){
                logisticsFeeRule.setCreateUser(agentUser.getUserId());
            }
            logisticsFeeRuleService.saveLogisticsFeeRule(logisticsFeeRule);
            response.getWriter().print("{\"success\":\"true\"}");
        }
        catch (Exception ex){
            response.getWriter().print("{\"errorMsg\":\""+ ex.getMessage() +"\"}");
        }
        return null;
    }

    @RequestMapping(value = "/company/logisticsFeeRule/save", method = RequestMethod.POST)
    public ModelAndView save(
            @RequestParam(required = true, defaultValue = "") Integer id,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        response.setContentType("text/plain; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        try
        {
            LogisticsFeeRule logisticsFeeRule = logisticsFeeRuleService.getLogisticsFeeRuleById(id);
            if(logisticsFeeRule != null) {
                Integer oldstatus = logisticsFeeRule.getStatus();
                updateLogisticsFeeRule(logisticsFeeRule, request.getParameterMap());
                Integer newstatus = logisticsFeeRule.getStatus();
                if(oldstatus == 0 && newstatus == 1) //启用
                {
                    CompanyContract contract = logisticsFeeRule.getContract();
                    if(contract != null){
                        List<LogisticsFeeRule> list = logisticsFeeRuleService.getActiveLogisticsFeeRules(contract.getId());
                        for(LogisticsFeeRule rule : list){
                            rule.setStatus(0);
                            logisticsFeeRuleService.saveLogisticsFeeRule(rule);
                        }
                    }
                }
                logisticsFeeRuleService.saveLogisticsFeeRule(logisticsFeeRule);
            }
            response.getWriter().print("{\"success\":\"true\"}");
        }
        catch (Exception ex){
            response.getWriter().print("{\"errorMsg\":\""+ ex.getMessage() +"\"}");
        }
        return null;
    }

    @RequestMapping(value = "/company/logisticsFeeRule/delete", method = RequestMethod.POST)
    public ModelAndView delete(@RequestParam(required = true) Integer id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/plain; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        try
        {
            logisticsFeeRuleService.removeLogisticsFeeRule(id);
            response.getWriter().print("{\"success\":\"true\"}");
        }
        catch (Exception ex){
            response.getWriter().print("{\"errorMsg\":"+ ex.getMessage() +"}");
        }
        return null;
    }

    @RequestMapping(value = "/company/logisticsFeeRule/contracts/{warehosueId}", method = RequestMethod.POST)
    public String lookupContracts(
            @PathVariable Long warehosueId,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .setExclusionStrategies(
                        new ExcludeUnionResource(
                                new String[]{"parent", "orderPayTypes"}))
                .create();

        List<CompanyContract> contracts;
        if(warehosueId != null && warehosueId > 0){
            contracts = contractService.getAllContracts(warehosueId);
        } else {
            contracts = contractService.getAllContracts();
        }
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().print(gson.toJson(contracts));
        return null;
    }

    @RequestMapping(value = "/company/logisticsFeeRule/companies/{warehouseId}", method = RequestMethod.POST)
    public String lookupCompanies(
            @PathVariable Long warehouseId,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        /*
        TODO:提供ComboBox绑定数据
        */
        List<Company> companies;
        if(warehouseId != null && warehouseId > 0){
            companies = companyService.getWarehouseCompanies(warehouseId);
        } else {
            companies = companyService.getAllCompanies();
        }
        JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();

        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().print(jsonBinder.toJson(companies));
        return null;
    }

    @RequestMapping(value = "/company/logisticsFeeRule/warehouses", method = RequestMethod.POST)
    public String lookupWarehouses(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        /*
        TODO:提供ComboBox绑定数据
        */
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        List<Warehouse> warehouses = warehouseService.getAllWarehouses();
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().print(gson.toJson(warehouses));
        return null;
    }




    private void updateLogisticsFeeRule(LogisticsFeeRule logisticsFeeRule, Map map){

        WebDataBinder binder = new WebDataBinder(logisticsFeeRule);
        binder.registerCustomEditor(CompanyContract.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (!StringUtils.hasText(text)) {
                    return;
                } else {
                    try
                    {
                        setValue(contractService.getContractById(Integer.parseInt(text)));
                    }
                    catch(Exception ex) { /* do nothing */}
                }
            }
        });

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.bind(new MutablePropertyValues(map));

        logisticsFeeRule.setUpdateDate(new Date());

        CompanyContract contract = logisticsFeeRule.getContract();
        if(contract != null){
            logisticsFeeRule.setBeginDate(contract.getBeginDate());
            logisticsFeeRule.setEndDate(contract.getEndDate());
        }

        AgentUser agentUser = SecurityHelper.getLoginUser();
        if(agentUser != null){
            logisticsFeeRule.setUpdateUser(agentUser.getUserId());
        }
    }
}
