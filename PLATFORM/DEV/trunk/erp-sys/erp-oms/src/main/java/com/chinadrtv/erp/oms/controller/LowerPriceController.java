package com.chinadrtv.erp.oms.controller;

import com.chinadrtv.erp.oms.dto.LowerPriceInfoDto;
import com.chinadrtv.erp.oms.dto.OrderDetailsDto;
import com.chinadrtv.erp.oms.service.LowerPriceInfoService;
import com.chinadrtv.erp.oms.service.OrderdetService;
import com.chinadrtv.erp.oms.service.ShipmentHeaderService;
import com.chinadrtv.erp.util.JsonBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-12-18
 * Time: 上午10:56
 * To change this template use File | Settings | File Templates.
 * 订单金额折扣处理
 */
@Controller
public class LowerPriceController {
    @Autowired
    private LowerPriceInfoService lowerPriceInfoService;

    @RequestMapping("/lower/price")
    public ModelAndView index() throws Exception {
        ModelAndView modelAndView = new ModelAndView("lowerPrice/lprice");
        /*modelAndView.addObject("url", "/lower/price/load");
        modelAndView.addObject("saveUrl", "#");
        modelAndView.addObject("updateUrl", "#");
        modelAndView.addObject("destroyUrl", "#");*/
        return modelAndView;
    }

    @RequestMapping(value = "/lower/price/load", method = RequestMethod.POST)
    public String load(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam Integer rows,
            @RequestParam(required = false, defaultValue = "") String shipmentId,
            @RequestParam(required = false, defaultValue = "") String mailId,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
        int index=shipmentId.indexOf("V");
        if(index>0)
        {
          String orderid = shipmentId.substring(0,index);
            List<LowerPriceInfoDto> list = lowerPriceInfoService.getLowerPriceInfoDto(orderid, mailId, (page - 1) * rows, rows);
            Long totalRecords = lowerPriceInfoService.getCountLowerPriceDto(orderid,mailId);
            String jsonData = "{\"rows\":" +jsonBinder.toJson(list) + ",\"total\":" + totalRecords + " }";
            response.setContentType("text/json; charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().print(jsonData);
        }else{
            List<LowerPriceInfoDto> list = lowerPriceInfoService.getLowerPriceInfoDto(shipmentId, mailId, (page - 1) * rows, rows);
            Long totalRecords = lowerPriceInfoService.getCountLowerPriceDto(shipmentId,mailId);
            String jsonData = "{\"rows\":" +jsonBinder.toJson(list) + ",\"total\":" + totalRecords + " }";
            response.setContentType("text/json; charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().print(jsonData);
        }
        return null;
    }


    @RequestMapping(value = "/lower/price/orderDetVim", method = RequestMethod.POST)
    public String showOrderDetList(HttpServletRequest request,String orderId,
            HttpServletResponse response) throws Exception
    {
        JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
        List<OrderDetailsDto> orderDetailsDtos = lowerPriceInfoService.getOrderDetailsDto(orderId);

        String jsonData = "{\"rows\":" +jsonBinder.toJson(orderDetailsDtos) + ",\"total\":" + orderDetailsDtos.size() + "}";
        response.setContentType("text/json; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().print(jsonData);

        return null;
    }
    //商品价格调整
    @RequestMapping(value = "/lower/price/save", method = RequestMethod.POST)
    public String saveLowerPrice(
            @RequestParam(required = true, defaultValue="") String editPrice,
            @RequestParam(required = true, defaultValue="") String orderId,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception{

        try{
            lowerPriceInfoService.saveLowerPrice(orderId,editPrice);

            response.getWriter().print("{\"success\":\"true\"}");
        } catch (Exception ex) {

            response.setContentType("text/plain; charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().print("{\"errorMsg\":\""+ ex.getMessage() +"\"}");
        }
        return null;
    }
}
