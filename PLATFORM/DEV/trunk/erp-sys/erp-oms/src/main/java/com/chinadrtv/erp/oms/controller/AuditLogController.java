package com.chinadrtv.erp.oms.controller;


import com.chinadrtv.erp.core.model.AuditLog;
import com.chinadrtv.erp.core.service.AuditLogService;
import com.chinadrtv.erp.model.EsbAuditLog;
import com.chinadrtv.erp.oms.service.CompanyService;
import com.chinadrtv.erp.oms.service.EsbAuditLogService;
import com.chinadrtv.erp.util.JsonBinder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 操作日志
 * User: liuhaidong
 * Date: 12-8-10
 */
@Controller
public class AuditLogController extends BaseController {

    @Autowired
    private AuditLogService auditLogService;
    
    @Autowired
    private EsbAuditLogService esbAuditLogService;
    @Autowired
    private CompanyService companyService;

    private static JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();

    @RequestMapping("/admin/auditLogs")
    public String auditLogs() throws Exception {
        return "auditLog/auditList";
    }

    @RequestMapping(value = "/admin/queryAuditLog", method = RequestMethod.POST)
    public String queryPromotionsJSON(@RequestParam(required = false, defaultValue = "0") int page,
                                    @RequestParam Integer rows,
                                    @RequestParam(required = false, defaultValue = "") String appName,
                                    @RequestParam(required = false, defaultValue = "") String funcName,
                                    @RequestParam(required = false, defaultValue = "") String treadid,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        String dateFormatStr =  "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
        jsonBinder.setDateFormat(dateFormatStr);
        dateFormat.setLenient(false);
        String sBeginDate =  request.getParameter("beginDate");
        Date beginDate = null;
        if (sBeginDate != null &&  !sBeginDate.isEmpty()){
            beginDate = dateFormat.parse(sBeginDate+" 00:00:00");
        }
        String sEndDate =  request.getParameter("endDate");
        Date endDate =  null;
        if (sEndDate != null &&  !sEndDate.isEmpty()){
            endDate = dateFormat.parse(sEndDate+" 23:59:59");
        }
        appName="OMS";
        List<AuditLog> auditLogList = auditLogService.searchPaginatedAuditLogByAppDate(appName,funcName,treadid,beginDate,endDate,page-1,rows);
       int totalRecords = auditLogService.getAuditLogCountByAppDate(appName,funcName,treadid,beginDate,endDate);
        response.setContentType("text/json");
        String jsonData = "{\"rows\":" +jsonBinder.toJson(auditLogList) + ",\"total\":" + totalRecords + " }";
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(jsonData);
        response.setHeader("Cache-Control", "no-cache");

        return null;
    }
    
    @RequestMapping("/admin/esbAuditLogs")
    public String esbAuditLogs( HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	initRequest(request);
        return "auditLog/esbAuditList";
    }

    @RequestMapping(value = "/admin/esbQueryAuditLog", method = RequestMethod.POST)
    public String EsbQueryPromotionsJSON(@RequestParam(required = false, defaultValue = "0") int page,
                                    @RequestParam Integer rows,
                                    @RequestParam(required = false, defaultValue = "") String esbName,
                                    @RequestParam(required = false, defaultValue = "") String errorCode,
                                    @RequestParam(required = false, defaultValue = "") String companyId,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        String dateFormatStr =  "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
        jsonBinder.setDateFormat(dateFormatStr);
        dateFormat.setLenient(false);
        String sBeginDate =  request.getParameter("beginDate");
        Date beginDate = null;
        if (sBeginDate != null &&  !sBeginDate.isEmpty()){
            beginDate = dateFormat.parse(sBeginDate+" 00:00:00");
        }
        String sEndDate =  request.getParameter("endDate");
        Date endDate =  null;
        if (sEndDate != null &&  !sEndDate.isEmpty()){
            endDate = dateFormat.parse(sEndDate+" 23:59:59");
        }
        List<EsbAuditLog> esbAuditLogList = esbAuditLogService.list(esbName,null,errorCode,null, companyId, null, beginDate,endDate,page-1,rows);
       int totalRecords = esbAuditLogService.listCount(esbName,null,errorCode,null, companyId, null,beginDate,endDate);
        response.setContentType("text/json");
        String jsonData = "{\"rows\":" +jsonBinder.toJson(esbAuditLogList) + ",\"total\":" + totalRecords + " }";
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(jsonData);
        response.setHeader("Cache-Control", "no-cache");

        return null;
    }

    @RequestMapping(value = "/admin/saveAuditLog", method = RequestMethod.POST)
    public ModelAndView saveNewProductRequest(
                                              AuditLog auditLog, HttpServletRequest request) throws JSONException {
        auditLogService.saveAuditLog(auditLog);
        return null;
    }

    @RequestMapping(value = "/admin/delAuditLog", method = RequestMethod.POST)
    public ModelAndView delProductRequest(@RequestParam(required = true) Long id,
                                              HttpServletRequest request) throws JSONException {
        auditLogService.delAuditLog(id);
        return null;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    
    public void initRequest(HttpServletRequest request){
		 
		 Gson gson = new GsonBuilder()
			.setDateFormat("yyyy-MM-dd")			
					.create();
		 //初始化付款方式
		 request.setAttribute("company_req",companyService.getAllCompanies());
		 request.setAttribute("company_json","{\"rows\":"+gson.toJson(companyService.getAllCompanies())+" }");
    }
}
