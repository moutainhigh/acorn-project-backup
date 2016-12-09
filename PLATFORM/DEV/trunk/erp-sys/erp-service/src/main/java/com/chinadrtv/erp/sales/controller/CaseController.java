package com.chinadrtv.erp.sales.controller;

import com.chinadrtv.erp.constant.SecurityConstants;
import com.chinadrtv.erp.core.service.NamesService;
import com.chinadrtv.erp.model.Names;
import com.chinadrtv.erp.model.agent.*;
import com.chinadrtv.erp.service.core.constant.Common;
import com.chinadrtv.erp.service.core.dto.CaseDto;
import com.chinadrtv.erp.service.core.service.*;
import com.chinadrtv.erp.tc.core.service.ProductService;
import com.chinadrtv.erp.user.aop.Mask;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.SecurityHelper;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 客户事件管理
 * User: gaudi.gao
 * Date: 14-4-22
 * Time: 下午4:29
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class CaseController extends BaseController {
    @Autowired
    private CaseService caseService;
    @Autowired
    private CasetypeService casetypeService;
    @Autowired
    private CaseCategoryService caseCategoryService;
    @Autowired
    private CasePriorityService casePriorityService;
    @Autowired
    private CasestatService casestatService;
    @Autowired
    private NamesService namesService;
    @Autowired
    private ProductService productService;

    @RequestMapping("/case/index")
    public ModelAndView index(HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView("event/eventmanage");
        modelAndView.addObject("urlString", "/case/add");

        modelAndView.addObject("module", request.getParameter("module"));
        modelAndView.addObject("caseId", request.getParameter("caseId"));
        modelAndView.addObject("contactId", request.getParameter("contactId"));
        modelAndView.addObject("contactName", request.getParameter("contactName"));
        modelAndView.addObject("orderId", request.getParameter("orderId"));
        String callback =  request.getParameter("callback");
        String queryString = request.getQueryString();
        if(StringUtils.hasText(callback)) {
            queryString = queryString.replace("&callback="+java.net.URLEncoder.encode(callback), "");
        }
        modelAndView.addObject("callback", callback);
        modelAndView.addObject("queryString",  queryString);

        AgentUser agentUser = SecurityHelper.getLoginUser();
        if(agentUser != null && agentUser.hasRole(SecurityConstants.CASE_STATUS_REQUIRED)){
            modelAndView.addObject("status_required", "true");
        } else {
            modelAndView.addObject("status_required", "false");
        }
        if(agentUser != null && agentUser.hasRole(SecurityConstants.CASE_RECORD_DELETED)){
            modelAndView.addObject("record_deleted", "true");
        } else {
            modelAndView.addObject("record_deleted", "false");
        }
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/case/solution/lookup", method = RequestMethod.POST)
    public List<Names> lookupSolutions(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return namesService.getAllNames("DELIVERED");
    }

    @ResponseBody
    @RequestMapping(value = "/case/processtime/lookup", method = RequestMethod.POST)
    public List<Names> lookupProcesstimes(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return namesService.getAllNames("PROCESSTIME");
    }

    @ResponseBody
    @RequestMapping(value = "/case/source/lookup", method = RequestMethod.POST)
    public List<Names> lookupSources(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return namesService.getAllNames("CASESOURCE");
    }

    @ResponseBody
    @RequestMapping(value = "/case/channel/lookup", method = RequestMethod.POST)
    public List<Names> lookupChannels(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return namesService.getAllNames("BUYCHANNEL");
    }

    @ResponseBody
    @RequestMapping(value = "/case/satisfaction/lookup", method = RequestMethod.POST)
    public List<Names> lookupSatisfactions(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return namesService.getAllNames("SATISFACTION");
    }

    @ResponseBody
    @RequestMapping(value = "/case/reason/lookup", method = RequestMethod.POST)
    public List<Names> lookupReasons(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return namesService.getAllNames("CASEREASON");
    }

    @ResponseBody
    @RequestMapping(value = "/case/type/lookup", method = RequestMethod.POST)
    public List<Casetype> lookupCaseTypes(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return casetypeService.getCacheCaseTypes(0L);
    }

    @ResponseBody
    @RequestMapping(value = "/case/category/lookup", method = RequestMethod.POST)
    public List<CaseCategory> lookupCaseCategories(
            @RequestParam(required = true) String category,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return caseCategoryService.getCaseCategories(category);
    }

    @ResponseBody
    @RequestMapping(value = "/case/priority/lookup", method = RequestMethod.POST)
    public List<CasePriority> lookupCasePriorities(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return casePriorityService.getCacheCasePriorities(0L);
    }



    @ResponseBody
    @RequestMapping(value = "/case/product/lookup", method = RequestMethod.POST)
    public List<Product> lookupProducts(
            @RequestParam(required = false) String key,
            @RequestParam(value = "scodes[]", required = false) String[] scodes,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        List<Product> products = new ArrayList<Product>();
        if(StringUtils.hasText(key))
        {
            if(scodes != null && scodes.length > 0){
                products.addAll(productService.getProductsByScodes(scodes));
            }
            products.addAll(productService.getProductsStartWith(key));
        }
        return products;
    }

    @ResponseBody
    @RequestMapping(value = "/case/state/lookup", method = RequestMethod.POST)
    public List<Casestat> lookupCaseStates(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return casestatService.getCacheCaseStatuses(0L);
    }

    @ResponseBody
    @RequestMapping(value = "/case/detail/lookup", method = RequestMethod.POST)
    public List<CaseDetail> lookupCaseDetails(
            @RequestParam(required = true) String caseId,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return caseService.findCaseDetails(caseId);
    }

    @ResponseBody
    @RequestMapping(value = "/case/search", method = RequestMethod.POST)
    public Map<String, Object> searchCases(
            CaseSpecification spec,
            @RequestParam(required = true, defaultValue = "1") int page,
            @RequestParam(required = true, defaultValue = "500") int rows,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        Long totalNum = caseService.getSpecifiedCaseCount(spec);
        if(totalNum > 0)
        {
            List<CaseDto> list = caseService.getSpecifiedCases(spec, (page -1) * rows, rows);
            map.put("total", totalNum);
            map.put("rows", list);
        }
        else
        {
            map.put("total", 0);
            map.put("rows", new ArrayList<CaseDto>());
        }
        return map;
    }

    @RequestMapping(value = "/case/export", method = RequestMethod.GET)
    public void exportCases(
            CaseSpecification spec,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        try
        {
            HSSFWorkbook wb = caseService.exportSpecifiedCases(spec);
            if (wb != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                String fileName = "Case" +sdf.format(new Date()) + ".xls";
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + fileName);
                OutputStream output = response.getOutputStream();
                wb.write(output);
                output.flush();
                output.close();
            }
        }
        catch (IOException ex){
            /* do nothing */
        }
    }


    @ResponseBody
    @RequestMapping(value = "/case/retrieve", method = RequestMethod.POST)
    public CaseDto retrieveCases(
            @RequestParam(required = true) String caseId,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return caseService.getCaseById(caseId);
    }


    @ResponseBody
    @RequestMapping(value = "/case/contact/load", method = RequestMethod.POST)
    public Map<String, Object> loadCases(
            @RequestParam(required = true) String contactId,
            @RequestParam(required = true, defaultValue = "1") int page,
            @RequestParam(required = true, defaultValue = "12") int rows,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        Long totalNum = caseService.getRecentCaseCount(contactId);
        if(totalNum > 0)
        {
            List<CaseDto> list = caseService.getRecentCases(contactId, (page -1) * rows, rows);
            map.put("total", totalNum);
            map.put("rows", list);
        }
        else
        {
            map.put("total", 0);
            map.put("rows", new ArrayList<CaseDto>());
        }
        return map;
    }

    @Mask (depth = 3)
    @ResponseBody
    @RequestMapping(value = "/case/today/load", method = RequestMethod.POST)
    public Map<String, Object> todayCases(
            @RequestParam(required = true, defaultValue = "1") int page,
            @RequestParam(required = true, defaultValue = "12") int rows,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        AgentUser agentUser = SecurityHelper.getLoginUser();
        if(agentUser != null){
            Long totalNum = caseService.getCurrentCaseCount(new Date(), agentUser.getUserId());
            if(totalNum > 0)
            {
                List<CaseDto> list = caseService.getCurrentCases(new Date(), agentUser.getUserId(), (page -1) * rows, rows);
                map.put("total", totalNum);
                map.put("rows", list);
            }
            else
            {
                map.put("total", 0);
                map.put("rows", new ArrayList<CaseDto>());
            }
        }
        else{
            map.put("total", 0);
            map.put("rows", new ArrayList<CaseDto>());
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/case/group/load", method = RequestMethod.POST)
    public Map<String, Object> groupCases(
            @RequestParam(required = true) Date startDate,
            @RequestParam(required = true) Date endDate,
            @RequestParam(required = true, defaultValue = "1") int page,
            @RequestParam(required = true, defaultValue = "12") int rows,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        AgentUser agentUser = SecurityHelper.getLoginUser();
        if(agentUser != null && agentUser.hasRole(Common.CL_MANAGER)){
            Long totalNum = caseService.getDeptCaseCount(agentUser.getDepartment(), startDate, endDate);
            if(totalNum > 0)
            {
                List<CaseDto> list = caseService.getDeptCases(agentUser.getDepartment(), startDate, endDate, (page -1) * rows, rows);
                map.put("total", totalNum);
                map.put("rows", list);
            }
            else
            {
                map.put("total", 0);
                map.put("rows", new ArrayList<CaseDto>());
            }
        }
        else{
            map.put("total", 0);
            map.put("rows", new ArrayList<CaseDto>());
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/case/group/status", method = RequestMethod.POST)
    public Map<String, Object> groupStatusCases(
            @RequestParam(required = true) String caseId,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        AgentUser agentUser = SecurityHelper.getLoginUser();
        if(agentUser != null && agentUser.hasRole(Common.CL_MANAGER)){
            int update = caseService.updateCaseStatus(caseId, "221");
            if(update > 0){
                map.put("success", true);
                map.put("caseid", caseId);
            } else {
                map.put("success", false);
                map.put("errorMsg", "此条记录已经处理过，请刷新列表！");
            }
        } else{
            map.put("success", false);
            map.put("errorMsg", "没有权限处理此条记录！");
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/case/add", method = RequestMethod.POST)
    public Map<String,Object> addCases(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        try
        {
            AgentUser agentUser = SecurityHelper.getLoginUser();
            if(agentUser != null){
                Cases cases = new Cases();
                updateCase(cases, request.getParameterMap());
                cases.setCrdt(new Date());
                cases.setMddt(new Date());
                cases.setCrusr(agentUser.getUserId());
                cases.setMdusr(agentUser.getUserId());
                caseService.addCase(cases, request.getParameterMap());
                map.put("success", true);
                map.put("caseid", cases.getCaseid());
            } else {
                map.put("success", false);
                map.put("errorMsg", "请先登陆系统！");
            }
        }
        catch (Exception ex){
            map.put("success", false);
            map.put("errorMsg", ex.getMessage());
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/case/save", method = RequestMethod.POST)
    public Map<String,Object> saveCases(
            @RequestParam(required = true) String caseid,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        try
        {
            AgentUser agentUser = SecurityHelper.getLoginUser();
            if(agentUser != null){
                Cases cases = caseService.findCase(caseid);
                updateCase(cases, request.getParameterMap());
                cases.setMddt(new Date());
                cases.setMdusr(agentUser.getUserId());
                caseService.saveCase(cases, request.getParameterMap());
                map.put("success", true);
                map.put("caseid", cases.getCaseid());
            } else {
                map.put("success", false);
                map.put("errorMsg", "请先登陆系统！");
            }
        }
        catch (Exception ex){
            map.put("success", false);
            map.put("errorMsg", ex.getMessage());
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/case/delete", method = RequestMethod.POST)
    public Map<String,Object> removeCases(
            @RequestParam(required = true) String caseid,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        try
        {
            AgentUser agentUser = SecurityHelper.getLoginUser();
            if(agentUser != null && agentUser.hasRole(SecurityConstants.CASE_RECORD_DELETED)){
                Cases cases = caseService.findCase(caseid);
                caseService.removeCase(cases);
                map.put("success", true);
            } else {
                map.put("success", false);
                map.put("errorMsg", "用户没有删除事件权限");
            }
        }
        catch (Exception ex){
            map.put("success", false);
            map.put("errorMsg", ex.getMessage());
        }
        return map;
    }

    private void updateCase(Cases cases, Map map){
        WebDataBinder binder = new WebDataBinder(cases);
        binder.bind(new MutablePropertyValues(map));
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
