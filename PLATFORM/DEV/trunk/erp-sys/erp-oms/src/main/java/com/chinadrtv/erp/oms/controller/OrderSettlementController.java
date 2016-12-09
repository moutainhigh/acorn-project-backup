package com.chinadrtv.erp.oms.controller;

import com.chinadrtv.erp.constant.TradeSourceConstants;
import com.chinadrtv.erp.model.OrderSettlement;
import com.chinadrtv.erp.model.OrderSettlementType;
import com.chinadrtv.erp.oms.service.OrderSettlementService;
import com.chinadrtv.erp.util.JsonBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单结算单导入显示
 * User: gaodejian
 * Date: 12-12-5
 * Time: 下午12:55
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class OrderSettlementController {

    @Autowired
    private OrderSettlementService orderSettlementService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {

        binder.registerCustomEditor(OrderSettlementType.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (!StringUtils.hasText(text)) {
                    return;
                } else {
                    try
                    {
                        //OrderSettlementType settlementType  = orderSettlementService.findById(text);
                        //setValue(settlementType);
                    }
                    catch(Exception ex) { /* do nothing */}
                }
            }
        });
    }

    @RequestMapping("/taobao/settle/{sourceId}")
    public ModelAndView taobao(@PathVariable String sourceId) throws Exception {
        ModelAndView modelAndView = new ModelAndView("orderSettlement/index");
        modelAndView.addObject("url", "/taobao/settle/load/"+sourceId);
        modelAndView.addObject("saveUrl", "/taobao/settle/save/"+sourceId);
        modelAndView.addObject("updateUrl", "/taobao/settle/update/"+sourceId);
        modelAndView.addObject("destroyUrl", "/taobao/settle/delete/"+sourceId);
        modelAndView.addObject("approveUrl", "/taobao/settle/approve/"+sourceId);
        return modelAndView;
    }



    @RequestMapping("/jingdong/settle/{sourceId}")
    public ModelAndView jingdong(@PathVariable String sourceId) throws Exception {
        ModelAndView modelAndView = new ModelAndView("orderSettlement/index");
        modelAndView.addObject("url", "/jingdong/settle/load/"+sourceId);
        modelAndView.addObject("saveUrl", "/jingdong/settle/save/"+sourceId);
        modelAndView.addObject("updateUrl", "/jingdong/settle/update/"+sourceId);
        modelAndView.addObject("destroyUrl", "/jingdong/settle/delete/"+sourceId);
        modelAndView.addObject("approveUrl", "/jingdong/settle/approve/"+sourceId);
        return modelAndView;
    }

    @RequestMapping("/amazon/settle/{sourceId}")
    public ModelAndView amazon(@PathVariable String sourceId) throws Exception {
        ModelAndView modelAndView = new ModelAndView("orderSettlement/index");
        modelAndView.addObject("url", "/amazon/settle/load/"+sourceId);
        modelAndView.addObject("saveUrl", "/amazon/settle/save/"+sourceId);
        modelAndView.addObject("updateUrl", "/amazon/settle/update/"+sourceId);
        modelAndView.addObject("destroyUrl", "/amazon/settle/delete/"+sourceId);
        modelAndView.addObject("approveUrl", "/amazon/settle/approve/"+sourceId);
        return modelAndView;
    }


    @RequestMapping(value = "/taobao/settle/approve/{sourceId}", method = RequestMethod.POST)
    public ModelAndView taobao_approve(@PathVariable String sourceId, @RequestParam(required = true) Long ruId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/plain; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        try
        {
            orderSettlementService.approveSettlement(ruId);
            response.getWriter().print("{\"success\":\"true\"}");
        }
        catch (Exception ex){
            response.getWriter().print("{\"errorMsg\":"+ ex.getMessage() +"}");
        }
        return null;
    }

    @RequestMapping(value = "/taobao/settle/delete/{sourceId}", method = RequestMethod.POST)
    public ModelAndView taobao_delete(@PathVariable String sourceId, @RequestParam(required = true) Long ruId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/plain; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        try
        {
            orderSettlementService.removeSettlement(ruId);
            response.getWriter().print("{\"success\":\"true\"}");
        }
        catch (Exception ex){
            response.getWriter().print("{\"errorMsg\":"+ ex.getMessage() +"}");
        }
        return null;
    }

    @RequestMapping(value = "/jingdong/settle/approve/{sourceId}", method = RequestMethod.POST)
    public ModelAndView jingdong_approve(@PathVariable String sourceId, @RequestParam(required = true) Long ruId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/plain; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        try
        {
            orderSettlementService.approveSettlement(ruId);
            response.getWriter().print("{\"success\":\"true\"}");
        }
        catch (Exception ex){
            response.getWriter().print("{\"errorMsg\":"+ ex.getMessage() +"}");
        }
        return null;
    }

    @RequestMapping(value = "/jingdong/settle/delete/{sourceId}", method = RequestMethod.POST)
    public ModelAndView jingdong_delete(@PathVariable String sourceId, @RequestParam(required = true) Long ruId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/plain; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        try
        {
            orderSettlementService.removeSettlement(ruId);
            response.getWriter().print("{\"success\":\"true\"}");
        }
        catch (Exception ex){
            response.getWriter().print("{\"errorMsg\":"+ ex.getMessage() +"}");
        }
        return null;
    }

    @RequestMapping(value = "/amazon/settle/approve/{sourceId}", method = RequestMethod.POST)
    public ModelAndView amazon_approve(@PathVariable String sourceId, @RequestParam(required = true) Long ruId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/plain; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        try
        {
            orderSettlementService.approveSettlement(ruId);
            response.getWriter().print("{\"success\":\"true\"}");
        }
        catch (Exception ex){
            response.getWriter().print("{\"errorMsg\":"+ ex.getMessage() +"}");
        }
        return null;
    }

    @RequestMapping(value = "/amazon/settle/delete/{sourceId}", method = RequestMethod.POST)
    public ModelAndView amazon_delete(@PathVariable String sourceId, @RequestParam(required = true) Long ruId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/plain; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        try
        {
            orderSettlementService.removeSettlement(ruId);
            response.getWriter().print("{\"success\":\"true\"}");
        }
        catch (Exception ex){
            response.getWriter().print("{\"errorMsg\":"+ ex.getMessage() +"}");
        }
        return null;
    }

    @RequestMapping(value = "/taobao/settle/load/{sourceId}", method = RequestMethod.POST)
    public String taobaoLoad(
            @PathVariable String sourceId,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam Integer rows,
            @RequestParam(required = false, defaultValue = "") String tradeId,
            @RequestParam(required = false, defaultValue = "") String shipmentId,
            @RequestParam(required = false, defaultValue = "") String startDate,
            @RequestParam(required = false, defaultValue = "") String endDate,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        //String sourceId = String.valueOf(TradeSourceConstants.TAOBAO_SOURCE_ID) + "," +
        //                  String.valueOf(TradeSourceConstants.TAOBAOC_SOURCE_ID) + "," +
        //                  String.valueOf(TradeSourceConstants.TAOBAOZ_SOURCE_ID);
         return Load(page, rows, sourceId, tradeId, shipmentId, startDate, endDate, request, response);
    }

    @RequestMapping(value = "/jingdong/settle/load/{sourceId}", method = RequestMethod.POST)
    public String jingdongLoad(
            @PathVariable String sourceId,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam Integer rows,
            @RequestParam(required = false, defaultValue = "") String tradeId,
            @RequestParam(required = false, defaultValue = "") String shipmentId,
            @RequestParam(required = false, defaultValue = "") String startDate,
            @RequestParam(required = false, defaultValue = "") String endDate,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        //String sourceId = String.valueOf(TradeSourceConstants.BUY360_SOURCE_ID)+","+String.valueOf(TradeSourceConstants.BUY360FBP_SOURCE_ID);
        return Load(page, rows, sourceId, tradeId, shipmentId, startDate, endDate, request, response);
    }

    @RequestMapping(value = "/amazon/settle/load/{sourceId}", method = RequestMethod.POST)
    public String amazonLoad(
            @PathVariable String sourceId,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam Integer rows,
            @RequestParam(required = false, defaultValue = "") String tradeId,
            @RequestParam(required = false, defaultValue = "") String shipmentId,
            @RequestParam(required = false, defaultValue = "") String startDate,
            @RequestParam(required = false, defaultValue = "") String endDate,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        //String sourceId = String.valueOf(TradeSourceConstants.AMAZON_SOURCE_ID)+","+String.valueOf(TradeSourceConstants.AMAZONFBP_SOURCE_ID);
        return Load(page, rows, sourceId, tradeId, shipmentId, startDate, endDate, request, response);
    }




    public String Load(
           int page,Integer rows,
           String sourceId,
           String tradeId,
           String shipmentId,
           String startDate,
           String endDate,
           HttpServletRequest request,
           HttpServletResponse response) throws Exception
    {
        JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();

        List<OrderSettlement> list = orderSettlementService.getAllSettlements(sourceId, tradeId, shipmentId, startDate, endDate, (page -1) * rows, rows);
        Long totalRecords = orderSettlementService.getSettlementCount(sourceId, tradeId, shipmentId, startDate, endDate);
        String jsonData = "{\"rows\":" +jsonBinder.toJson(list) + ",\"total\":" + totalRecords + " }";
        response.setContentType("text/json; charset=UTF-8");
        response.setContentType("text/json; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().print(jsonData);
        return null;
    }
}
