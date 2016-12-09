package com.chinadrtv.erp.oms.controller;

import com.chinadrtv.erp.model.OmsBackorder;
import com.chinadrtv.erp.oms.service.OmsBackorderService;
import com.chinadrtv.erp.oms.service.SystemConfigure;
import com.chinadrtv.erp.util.JsonBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 缺货压单取消
 * User: gaodejian
 * Date: 12-12-5
 * Time: 下午12:55
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class BackorderController {

    @Autowired
    private OmsBackorderService omsBackorderService;

    @Autowired
    private SystemConfigure systemConfigure;

    @RequestMapping("/order/backorder")
    public ModelAndView index() throws Exception {
        ModelAndView modelAndView = new ModelAndView("backorder/index");
        modelAndView.addObject("url", "/order/backorder/load");
        modelAndView.addObject("saveUrl", "#");
        modelAndView.addObject("updateUrl", "#");
        modelAndView.addObject("destroyUrl", "#");
        return modelAndView;
    }

    @RequestMapping(value = "/order/backorder/defer", method = RequestMethod.POST)
    public ModelAndView defer(
            @RequestParam(required = true) Long id,
            @RequestParam(required = true, defaultValue = "7") Double days,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        response.setContentType("text/plain; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        try
        {
            omsBackorderService.deferBackorder(id, days);
            response.getWriter().print("{\"success\":\"true\"}");
        }
        catch (Exception ex){
            response.getWriter().print("{\"errorMsg\":"+ ex.getMessage() +"}");
        }
        return null;
    }

    @RequestMapping(value = "/order/backorder/delete", method = RequestMethod.POST)
    public ModelAndView delete(@RequestParam(required = true) Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/plain; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        try
        {
            omsBackorderService.removeBackorder(id);
            response.getWriter().print("{\"success\":\"true\"}");
        }
        catch (Exception ex){
            response.getWriter().print("{\"errorMsg\":"+ ex.getMessage() +"}");
        }
        return null;
    }


    @RequestMapping(value = "/order/backorder/load", method = RequestMethod.POST)
    public String load(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam Integer rows,
            @RequestParam(required = false, defaultValue = "") String orderId,
            @RequestParam(required = false, defaultValue = "") String productId,
            @RequestParam(required = false, defaultValue = "") String status,
            @RequestParam(required = false, defaultValue = "") String startDate,
            @RequestParam(required = false, defaultValue = "") String endDate,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();

        List<OmsBackorder> list = omsBackorderService.getAllBackorders(orderId, productId, status, startDate, endDate, (page -1) * rows, rows);
        Long totalRecords = omsBackorderService.getBackorderCount(orderId, productId, status, startDate, endDate);
        String jsonData = "{\"rows\":" +jsonBinder.toJson(list) + ",\"total\":" + totalRecords + " }";
        response.setContentType("text/json; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().print(jsonData);

        return null;
    }

}
