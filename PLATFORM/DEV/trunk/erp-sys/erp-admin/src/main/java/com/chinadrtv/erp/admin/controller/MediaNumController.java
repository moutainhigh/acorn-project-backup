package com.chinadrtv.erp.admin.controller;


import com.chinadrtv.erp.admin.service.MediaNumService;
import com.chinadrtv.erp.util.JsonBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: liyu
 * Date: 12-11-13
 * Time: 下午12:42
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class MediaNumController extends BaseController  {
    @Autowired
    private MediaNumService mediaNumService;
    private static JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();

    @RequestMapping(value = "/admin/queryMediaNum", method = RequestMethod.POST)
    public String queryPromotionsJSON(@RequestParam(required = false, defaultValue = "") String mediaName,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        //String dateFormatStr =  "yyyy-MM-dd";
        //SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
        //jsonBinder.setDateFormat(dateFormatStr);
        //dateFormat.setLenient(false);
        //String sBeginDate =  request.getParameter("beginDate");
        //Date beginDate = null;
        //if (sBeginDate != null &&  !sBeginDate.isEmpty()){
        //    beginDate = dateFormat.parse(sBeginDate);
        //}
        //String sEndDate =  request.getParameter("endDate");
        //Date endDate =  null;
        //if (sEndDate != null &&  !sEndDate.isEmpty()){
        //    endDate = dateFormat.parse(sEndDate);
        //}

        //List<MediaNum> mediaNumList = mediaNumService.searchPaginatedAuditLogByAppDate(appName,funcName,beginDate,endDate,page-1,rows);
        int totalRecords = mediaNumService.getMediaNumCountByNum(mediaName);
        response.setContentType("text/json;charset=UTF-8");
        String jsonData = "{\"total\":" + totalRecords + " }";
        response.getWriter().print(jsonData);
        response.setHeader("Cache-Control", "no-cache");

        return null;
    }


}
