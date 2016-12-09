package com.chinadrtv.erp.oms.controller;

import com.chinadrtv.erp.core.service.NamesService;
import com.chinadrtv.erp.model.*;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.OrderHistory;
import com.chinadrtv.erp.model.trade.ShipmentDetail;
import com.chinadrtv.erp.model.trade.ShipmentHeader;
import com.chinadrtv.erp.oms.dto.LogisticsDto;
import com.chinadrtv.erp.oms.service.*;
import com.chinadrtv.erp.oms.util.StringUtil;
import com.chinadrtv.erp.util.JsonBinder;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 发运单-变更送货公司
 * User: gaodejian
 * Date: 13-3-7
 * Time: 下午1:07
 * To change this template use File | Settings | File Templates.
 * 检查订单状态及库存状态,作废原发运单,新增发运单
 */
@Controller
public class ShipmentAlternateController {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ShipmentAlternateController.class);



    @Autowired
    private CompanyService companyService;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private ShipmentHeaderService shipmentHeaderService;

    @Autowired
    private NamesService namesService ;

    @Autowired
    private AddressService addressService;
    @Autowired
    private CardtypeService cardtypeService;
    @Autowired
    private OrderhistService orderhistService;

    @RequestMapping("/shipment/stock")
    public ModelAndView stock() throws Exception {
        ModelAndView modelAndView = new ModelAndView("shipment/stock");
        modelAndView.addObject("url", "/shipment/stock/load");
        modelAndView.addObject("saveUrl", "#");
        modelAndView.addObject("updateUrl", "#");
        modelAndView.addObject("destroyUrl", "#");
        return modelAndView;
    }

    @RequestMapping("/shipment/logistics")
    public ModelAndView logistics() throws Exception {
        ModelAndView modelAndView = new ModelAndView("shipment/logistics");
        modelAndView.addObject("url", "/shipment/logistics/load");
        modelAndView.addObject("saveUrl", "#");
        modelAndView.addObject("updateUrl", "#");
        modelAndView.addObject("destroyUrl", "#");
        return modelAndView;
    }

    @RequestMapping(value = "/shipment/stock/load", method = RequestMethod.POST)
    public String stock_load(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam Integer rows,
            @RequestParam(required = false, defaultValue="") String orderId,
            @RequestParam(required = false, defaultValue="") String shipmentId,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .setExclusionStrategies(
                        new ExcludeUnionResource(
                                new String[] { "orderHistory", "shipmentDetails"}))
                .create();

        Long totalRecords = shipmentHeaderService.getShipmentCount(shipmentId, orderId);
        List<ShipmentHeader> shipments = shipmentHeaderService.getShipments(shipmentId, orderId, (page - 1) * rows, rows);
        if(shipments != null){
            String jsonData = "{\"rows\":" +gson.toJson(shipments) + ",\"total\":" + totalRecords + " }";
            response.setContentType("text/json; charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().print(jsonData);
        }
        return null;
    }

    @RequestMapping(value = "/shipment/stock/alternate", method = RequestMethod.POST)
    public String stockAlternate(
            @RequestParam(required = true) Long id,
            @RequestParam(required = true, defaultValue="") String warehouseId,
            @RequestParam(required = true, defaultValue="") String entityId,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        try
        {
            shipmentHeaderService.alternate(id, warehouseId, entityId);
            response.getWriter().print("{\"success\":\"true\"}");
        } catch (Exception ex) {
            logger.error("变更承运商失败!", ex);
            response.setContentType("text/plain; charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().print("{\"errorMsg\":\""+ ex.getMessage() +"\"}");
        }
        return null;
    }

    @RequestMapping(value = "/shipment/logistics/load", method = RequestMethod.POST)
    public String logisticsLoad(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam Integer rows,
            @RequestParam(required = false, defaultValue="") String orderId,
            @RequestParam(required = false, defaultValue="") String shipmentId,
            @RequestParam(required = false, defaultValue="") String provinceId,
            @RequestParam(required = false, defaultValue="") String cityId,
            @RequestParam(required = false, defaultValue="") String countyId,
            @RequestParam(required = false, defaultValue="") String productId,
            @RequestParam(required = false, defaultValue="") String accountStatusId,
            @RequestParam(required = false, defaultValue="") String warehouseId,
            @RequestParam(required = false, defaultValue="") String entityId,
            @RequestParam(required = false, defaultValue="") String paytypeId,
            @RequestParam(required = false) Double minAmount,
            @RequestParam(required = false) Double maxAmount,
            @RequestParam(required = false, defaultValue="") Boolean capture,
            @RequestParam(required = false, defaultValue="") String crdtStart,
            @RequestParam(required = false, defaultValue="") String crdtEnd,
            @RequestParam(required = false, defaultValue="") String senddtStart,
            @RequestParam(required = false, defaultValue="") String senddtEnd,
            @RequestParam(required = false, defaultValue="") String cardtype,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {


        Map<String, Object> params = new HashMap<String, Object>();

        if(orderId != null && !orderId.equals("")){
            params.put("orderId", orderId);
        }
        if(orderId != null && !orderId.equals("")){
            params.put("orderId", orderId);
        }
        if(shipmentId != null && !shipmentId.equals("")){
            params.put("shipmentId", shipmentId);
        }
        if(provinceId != null && !provinceId.equals("")){
            params.put("provinceId", provinceId);
        }
        if(cityId != null && !cityId.equals("")){
            params.put("cityId", cityId);
        }
        if(accountStatusId != null && !accountStatusId.equals("")){
            params.put("accountStatusId", accountStatusId);
        }
        if(warehouseId != null && !warehouseId.equals("")){
            params.put("warehouseId", warehouseId);
        }
        if(entityId != null && !entityId.equals("")){
            params.put("entityId", entityId);
        }
        if(paytypeId != null && !paytypeId.equals("")){
            params.put("paytypeId", paytypeId);
        }
        if(countyId != null && !countyId.equals("")){
            params.put("countyId", countyId);
        }
        if(productId != null && !productId.equals("")){
            params.put("productId", productId);
        }
        if(minAmount != null && !minAmount.equals("")){
            params.put("minAmount", minAmount);
        }
        if(maxAmount != null && !maxAmount.equals("")){
            params.put("maxAmount", maxAmount);
        }
        if(capture != null && !capture.equals("")){
            if(capture) {
               params.put("capture", "-1");
            }
        }
        if(crdtStart != null && !crdtStart.equals("")){
            params.put("crdtStart",crdtStart);
        }
        if(crdtEnd != null && !crdtEnd.equals("")){
            params.put("crdtEnd",crdtEnd);
        }
        if(senddtStart != null && !senddtStart.equals("")){
            params.put("senddtStart",senddtStart);
        }
        if(senddtEnd != null && !senddtEnd.equals("")){
            params.put("senddtEnd",senddtEnd);
        }
        if(cardtype != null && !cardtype.equals("")){
            params.put("cardtype",cardtype);
        }

        Long totalRecords = shipmentHeaderService.getLogisticsShipmentCount(params);
        List<LogisticsDto> shipments = shipmentHeaderService.getLogisticsShipments(params, (page - 1) * rows, rows);
        if(shipments != null){
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create();

            String jsonData = "{\"rows\":" +gson.toJson(shipments) + ",\"total\":" + totalRecords + " }";
            response.setContentType("text/json; charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().print(jsonData);
        }
        return null;
    }

    @RequestMapping(value = "/shipment/logistics/alternate", method = RequestMethod.POST)
    public String logisticsAlternate(
            @RequestParam(required = true, defaultValue = "0") Long id,
            @RequestParam(required = true, defaultValue = "0") String ids,
            @RequestParam(required = true, defaultValue="") String orderId,
            @RequestParam(required = true, defaultValue="") String warehouseId,
            @RequestParam(required = true, defaultValue="") String entityId,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        try
        {
            String sid[] = ids.split(",");
            for(int i=0;i<sid.length;i++){
                id = Long.parseLong(sid[i]);
                if(id > 0){
                    shipmentHeaderService.alternate(id, warehouseId, entityId);
                } else {
                    shipmentHeaderService.alternate(orderId, warehouseId, entityId);
                }
            }

            response.getWriter().print("{\"success\":\"true\"}");
        } catch (Exception ex) {
            logger.error("变更承运商失败!", ex);
            response.setContentType("text/plain; charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().print("{\"errorMsg\":\""+ ex.getMessage() +"\"}");
        }
        return null;
    }

    @RequestMapping(value = "/shipment/cardtype/lookup", method = RequestMethod.POST)
    public String lookupCardtypes(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        /*
        TODO:提供ComboBox绑定数据
        */
        Gson gson = new GsonBuilder().create();
        List<String> cardNames = cardtypeService.getCreditCardNames();
        List<Map<String, String>> maps = new ArrayList<Map<java.lang.String, java.lang.String>>();
        for(String name : cardNames){
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", name);
            map.put("name", name);
            maps.add(map);
        }
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().print(gson.toJson(maps)); //replaceAll("\"id\"", "id")
        return null;
    }
    @RequestMapping(value = "/shipment/company/lookup/{warehouseId}", method = RequestMethod.POST)
    public String lookupCompanies(
            @PathVariable Long warehouseId,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        /*
        TODO:提供ComboBox绑定数据
        */
        JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
        List<Company> companies;
        if(warehouseId != null && warehouseId > 0){
            companies = companyService.getWarehouseCompanies(warehouseId);
        } else {
            companies = companyService.getAllCompanies();
        }
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().print(jsonBinder.toJson(companies));
        return null;
    }

    @RequestMapping(value = "/shipment/warehouse/lookup", method = RequestMethod.POST)
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

    @RequestMapping(value = "/shipment/paytype/lookup", method = RequestMethod.POST)
    public String lookupPayTypes(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        /*
        TODO:提供ComboBox绑定数据
        */
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

        List<Names> paytypes = namesService.getAllNames("PAYTYPE");
        List<Map<String, String>> maps = new ArrayList<Map<java.lang.String, java.lang.String>>();
        for(Names name : paytypes){
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", name.getId().getId());
            map.put("dsc", name.getDsc());
            maps.add(map);
        }
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().print(gson.toJson(maps));
        return null;
    }

    @RequestMapping(value = "/shipment/buytype/lookup", method = RequestMethod.POST)
    public String lookupBuyTypes(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        /*
        TODO:提供ComboBox绑定数据
        */
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

        List<Names> buytypes = namesService.getAllNames("BUYTYPE");
        List<Map<String, String>> maps = new ArrayList<Map<java.lang.String, java.lang.String>>();
        for(Names name : buytypes){
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", name.getId().getId());
            map.put("dsc", name.getDsc());
            maps.add(map);
        }
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().print(gson.toJson(maps));
        return null;
    }

    @RequestMapping(value = "/shipment/accountStatus/lookup", method = RequestMethod.POST)
    public String lookupAccountStatuse(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        /*
        TODO:提供ComboBox绑定数据
        */
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

        List<Names> statuses = namesService.getAllNames("ORDERSTATUS");

        List<Map<String, String>> maps = new ArrayList<Map<java.lang.String, java.lang.String>>();
        for(Names name : statuses){
            if("1".equals(name.getId().getId()) || "2".equals(name.getId().getId())) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id", name.getId().getId());
                map.put("dsc", name.getDsc());
                maps.add(map);
            }
        }

        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().print(gson.toJson(maps));
        return null;
    }

    @RequestMapping(value = "/shipment/logisticsStatus/lookup", method = RequestMethod.POST)
    public String lookupLogisticsStatuses(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        /*
        TODO:提供ComboBox绑定数据
        */
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        List<Names> statuses = namesService.getAllNames("CUSTOMIZESTATUS");
        List<Map<String, String>> maps = new ArrayList<Map<java.lang.String, java.lang.String>>();
        for(Names name : statuses){
                Map<String, String> map = new HashMap<String, String>();
                map.put("id", name.getId().getId());
                map.put("dsc", name.getDsc());
                maps.add(map);
        }
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().print(gson.toJson(maps));
        return null;
    }

}
