package com.chinadrtv.erp.admin.controller;


import com.chinadrtv.erp.core.model.AuditLog;
import com.chinadrtv.erp.core.service.AuditLogService;
import com.chinadrtv.erp.util.JsonBinder;
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
 * User: liuhaidong
 * Date: 12-8-10
 */
@Controller
public class AuditLogController extends BaseController {

    @Autowired
    private AuditLogService auditLogService;

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
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        String dateFormatStr =  "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
        jsonBinder.setDateFormat(dateFormatStr);
        dateFormat.setLenient(false);
        String sBeginDate =  request.getParameter("beginDate");
        Date beginDate = null;
        if (sBeginDate != null &&  !sBeginDate.isEmpty()){
            beginDate = dateFormat.parse(sBeginDate);
        }
        String sEndDate =  request.getParameter("endDate");
        Date endDate =  null;
        if (sEndDate != null &&  !sEndDate.isEmpty()){
            endDate = dateFormat.parse(sEndDate);
        }
         appName= "ADMIN";
        //TODO get value of :threadid
        List<AuditLog> auditLogList = auditLogService.searchPaginatedAuditLogByAppDate(appName,funcName,"",beginDate,endDate,page-1,rows);
       int totalRecords = auditLogService.getAuditLogCountByAppDate(appName,funcName,"",beginDate,endDate);
        response.setContentType("text/json;charset=UTF-8");
        String jsonData = "{\"rows\":" +jsonBinder.toJson(auditLogList) + ",\"total\":" + totalRecords + " }";
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
}
