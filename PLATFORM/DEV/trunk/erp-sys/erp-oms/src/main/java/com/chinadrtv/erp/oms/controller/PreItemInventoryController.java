package com.chinadrtv.erp.oms.controller;

import com.chinadrtv.erp.constant.TradeSourceConstants;
import com.chinadrtv.erp.model.PreItemInventory;
import com.chinadrtv.erp.oms.service.PreItemInventoryService;
import com.chinadrtv.erp.oms.service.PreItemInventoryService;
import com.chinadrtv.erp.util.JsonBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 库存同步导入显示
 * User: gaodejian
 * Date: 12-12-5
 * Time: 下午12:55
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class PreItemInventoryController {

    @Autowired
    private PreItemInventoryService preItemInventoryService;

    @RequestMapping("/taobao/inventory/{sourceId}")
    public ModelAndView taobao(@PathVariable String sourceId) throws Exception {
        ModelAndView modelAndView = new ModelAndView("preItemInventory/index");
        modelAndView.addObject("url", "/taobao/inventory/load/"+sourceId);
        modelAndView.addObject("saveUrl", "#");
        modelAndView.addObject("updateUrl", "#");
        modelAndView.addObject("destroyUrl", "#");
        return modelAndView;
    }

    @RequestMapping("/jingdong/inventory/{sourceId}")
    public ModelAndView jingdong(@PathVariable String sourceId) throws Exception {
        ModelAndView modelAndView = new ModelAndView("preItemInventory/index");
        modelAndView.addObject("url", "/jingdong/inventory/load/"+sourceId);
        modelAndView.addObject("saveUrl", "#");
        modelAndView.addObject("updateUrl", "#");
        modelAndView.addObject("destroyUrl", "#");
        return modelAndView;
    }

    @RequestMapping("/amazon/inventory/{sourceId}")
    public ModelAndView amazon(@PathVariable String sourceId) throws Exception {
        ModelAndView modelAndView = new ModelAndView("preItemInventory/index");
        modelAndView.addObject("url", "/amazon/inventory/load/"+sourceId);
        modelAndView.addObject("saveUrl", "#");
        modelAndView.addObject("updateUrl", "#");
        modelAndView.addObject("destroyUrl", "#");
        return modelAndView;
    }

    @RequestMapping(value = "/taobao/inventory/load/{sourceId}", method = RequestMethod.POST)
    public String taobaoLoad(
            @PathVariable String sourceId,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam Integer rows,
            @RequestParam(required = false, defaultValue = "") String skuId,
            @RequestParam(required = false, defaultValue = "") String outSkuId,
            @RequestParam(required = false, defaultValue = "") String startDate,
            @RequestParam(required = false, defaultValue = "") String endDate,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        //String sourceId = String.valueOf(TradeSourceConstants.TAOBAO_SOURCE_ID) + "," +
        //        String.valueOf(TradeSourceConstants.TAOBAOC_SOURCE_ID) + "," +
        //        String.valueOf(TradeSourceConstants.TAOBAOZ_SOURCE_ID);
        return Load(page, rows, sourceId, skuId, outSkuId, startDate, endDate, request, response);
    }

    @RequestMapping(value = "/jingdong/inventory/load/{sourceId}", method = RequestMethod.POST)
    public String jingdongLoad(
            @PathVariable String sourceId,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam Integer rows,
            @RequestParam(required = false, defaultValue = "") String skuId,
            @RequestParam(required = false, defaultValue = "") String outSkuId,
            @RequestParam(required = false, defaultValue = "") String startDate,
            @RequestParam(required = false, defaultValue = "") String endDate,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        //String sourceId = String.valueOf(TradeSourceConstants.BUY360_SOURCE_ID)+","+String.valueOf(TradeSourceConstants.BUY360FBP_SOURCE_ID);
        return Load(page, rows, sourceId, skuId, outSkuId, startDate, endDate, request, response);
    }

    @RequestMapping(value = "/amazon/inventory/load/{sourceId}", method = RequestMethod.POST)
    public String amazonLoad(
            @PathVariable String sourceId,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam Integer rows,
            @RequestParam(required = false, defaultValue = "") String skuId,
            @RequestParam(required = false, defaultValue = "") String outSkuId,
            @RequestParam(required = false, defaultValue = "") String startDate,
            @RequestParam(required = false, defaultValue = "") String endDate,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        //String sourceId = String.valueOf(TradeSourceConstants.AMAZON_SOURCE_ID)+","+String.valueOf(TradeSourceConstants.AMAZONFBP_SOURCE_ID);
        return Load(page, rows, sourceId, skuId, outSkuId, startDate, endDate, request, response);
    }

    public String Load(
            int page,Integer rows,
            String sourceId,
            String skuId,
            String outSkuId,
            String startDate,
            String endDate,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();

        List<PreItemInventory> list = preItemInventoryService.getAllInventoryItems(sourceId, skuId, outSkuId, startDate, endDate, (page -1) * rows, rows);
        Long totalRecords = preItemInventoryService.getInventoryItemCount(sourceId, skuId, outSkuId, startDate, endDate);
        String jsonData = "{\"rows\":" +jsonBinder.toJson(list) + ",\"total\":" + totalRecords + " }";
        response.setContentType("text/json; charset=UTF-8");
        response.setContentType("text/json; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().print(jsonData);
        return null;
    }
}
