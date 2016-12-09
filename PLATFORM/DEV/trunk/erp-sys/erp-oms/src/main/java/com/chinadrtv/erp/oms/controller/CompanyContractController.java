package com.chinadrtv.erp.oms.controller;

import com.chinadrtv.erp.core.service.NamesService;
import com.chinadrtv.erp.model.CompanyContract;
import com.chinadrtv.erp.model.Names;
import com.chinadrtv.erp.model.OrderChannel;
import com.chinadrtv.erp.model.Warehouse;
import com.chinadrtv.erp.oms.service.CompanyContractService;
import com.chinadrtv.erp.oms.service.OrderChannelService;
import com.chinadrtv.erp.oms.service.WarehouseService;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditorSupport;
import java.text.ParseException;
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
public class CompanyContractController extends BaseController {

    @Autowired
    private CompanyContractService contractService;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private OrderChannelService orderChannelService;

    @Autowired
    private NamesService namesService ;

    @RequestMapping("/company/contract")
    public ModelAndView index() throws Exception {
        return new ModelAndView("company/contract");
    }

    @RequestMapping(value = "/company/contract/load", method = RequestMethod.POST)
    public String load(@RequestParam(required = false, defaultValue = "1") int page,
                        @RequestParam Integer rows,
                        @RequestParam(required = false, defaultValue = "") String companyId,
                        @RequestParam(required = false, defaultValue = "") String nccompanyId,
                        @RequestParam(required = false, defaultValue = "") Long warehouseId,
                        @RequestParam(required = false, defaultValue = "") Long channelId,
                        @RequestParam(required = false, defaultValue = "") String startDate,
                        @RequestParam(required = false, defaultValue = "") String endDate,
                        @RequestParam(required = false, defaultValue="") Boolean status,
                        @RequestParam(required = false, defaultValue = "") String quYuId,
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

        if(nccompanyId != null && !nccompanyId.equals("")){
            params.put("nccompanyId", nccompanyId);
        }

        if(warehouseId != null && !warehouseId.equals("")){
            params.put("warehouseId", warehouseId);
        }

        if(channelId != null && !channelId.equals("")){
            params.put("channelId", channelId);
        }

        if(startDate != null && !startDate.equals("")){
            try
            {
                params.put("startDate", dateFormat.parse(startDate));
            }
            catch (ParseException ex) {
                /* do nothing */
            }
        }

        if(endDate != null && !endDate.equals("")){
            try
            {
                params.put("endDate", dateFormat.parse(endDate));
            }
            catch (ParseException ex) {
                /* do nothing */
            }
        }
        if(status != null && !status.equals("")){
            if(status) {
                params.put("status", 1);
            }
        }
        if(quYuId != null && !quYuId.equals("")){
            params.put("quYuId", quYuId);
        }

        Long totalRecords = contractService.getContractCount(params);
        List<CompanyContract> list = contractService.getAllContracts(params, (page -1) * rows, rows);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
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

    @RequestMapping(value = "/company/contract/add", method = RequestMethod.POST)
    public ModelAndView add(CompanyContract contract, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/plain; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        try
        {
            if(contract.getId() == null || contract.getId() == 0){
                contractService.addContract(contract);
                response.getWriter().print("{\"success\":\"true\"}");
            }
            //response.getWriter().print("{\"errorMsg\":\"添加失败:公司编号"+ contract.getContractId() +"已经存在!\"}");
        }
        catch (Exception ex){
            response.getWriter().print("{\"errorMsg\":\""+ ex.getMessage() +"\"}");
        }
        return null;
    }

    @RequestMapping(value = "/company/contract/save", method = RequestMethod.POST)
    public ModelAndView save(
            @RequestParam(required = true, defaultValue = "") Integer id,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        response.setContentType("text/plain; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        try
        {
            CompanyContract contract = contractService.getContractById(id);
            if(contract != null) {
                //contract.setName(contract.getName()+"-test");
                updateContract(contract, request.getParameterMap());
                contractService.saveContract(contract);
            }
            response.getWriter().print("{\"success\":\"true\"}");
        }
        catch (Exception ex){
            response.getWriter().print("{\"errorMsg\":\""+ ex.getMessage() +"\"}");
        }
        return null;
    }

    @RequestMapping(value = "/company/contract/delete", method = RequestMethod.POST)
    public ModelAndView delete(@RequestParam(required = true) Integer contractId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/plain; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        try
        {
            contractService.removeContract(contractId);
            response.getWriter().print("{\"success\":\"true\"}");
        }
        catch (Exception ex){
            response.getWriter().print("{\"errorMsg\":"+ ex.getMessage() +"}");
        }
        return null;
    }

    @RequestMapping(value = "/company/warehouse/lookup", method = RequestMethod.POST)
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

    @RequestMapping(value = "/company/channel/lookup", method = RequestMethod.POST)
    public String lookupChannels(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        /*
        TODO:提供ComboBox绑定数据
        */
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .setExclusionStrategies(
                        new ExcludeUnionResource(
                                new String[]{"parent", "orderPayTypes"}))
                .create();
        List<OrderChannel> channels = orderChannelService.getAllOrderChannel();
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().print(gson.toJson(channels));
        return null;
    }

    @RequestMapping(value = "/company/companyType/lookup", method = RequestMethod.POST)
    public String lookupBuyTypes(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        /*
        TODO:提供ComboBox绑定数据
        */
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

        List<Names> names = namesService.getAllNames("COMPANYTYPE");
        List<Map<String, String>> maps = new ArrayList<Map<String, String>>();
        for(Names name : names){
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", name.getId().getId());
            map.put("dsc", name.getDsc());
            maps.add(map);
        }
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().print(gson.toJson(maps));
        return null;
    }
    //担保形式
    @RequestMapping(value = "/company/suretyType/lookup", method = RequestMethod.POST)
    public String suretyType(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        /*
        TODO:提供ComboBox绑定数据
        */
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

        List<Names> names = namesService.getAllNames("Surety_Type");
        List<Map<String, String>> maps = new ArrayList<Map<String, String>>();
        for(Names name : names){
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", name.getId().getId());
            map.put("dsc", name.getDsc());
            maps.add(map);
        }
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().print(gson.toJson(maps));
        return null;
    }
    //配送区域
    @RequestMapping(value = "/company/distributionRegion/lookup", method = RequestMethod.POST)
    public String distributionRegion(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        /*
        TODO:提供ComboBox绑定数据
        */
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

        List<Names> names = namesService.getAllNames("Distribution_Region");
        List<Map<String, String>> maps = new ArrayList<Map<String, String>>();
        for(Names name : names){
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", name.getId().getId());
            map.put("dsc", name.getDsc());
            maps.add(map);
        }
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().print(gson.toJson(maps));
        return null;
    }

    private void updateContract(CompanyContract contract, Map map){

        WebDataBinder binder = new WebDataBinder(contract);

        /*
        if(map.containsKey("warehouse"))
        {
            String[] wareshouses = (String[])map.get("warehouse");
            if(wareshouses != null && wareshouses.length > 0 &&
                    StringUtils.hasText(wareshouses[0])){
                Warehouse warehouse = warehouseService.findById(Long.parseLong(wareshouses[0]));
                if(warehouse != null){
                    contract.setWarehouse(warehouse);
                }
            }
        }
        if(map.containsKey("channel"))
        {
            String[] channels = (String[])map.get("channel");
            if(channels != null && channels.length > 0 &&
                    StringUtils.hasText(channels[0])){
                OrderChannel channel = orderChannelService.findById(Long.parseLong(channels[0]));
                if(channel != null){
                    contract.setChannel(channel);
                }
            }
        }
        */
        binder.registerCustomEditor(Warehouse.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (!StringUtils.hasText(text)) {
                    return;
                } else {

                    try
                    {
                        Warehouse warehouse = warehouseService.findById(Long.parseLong(text));
                        if(warehouse != null){
                            setValue(warehouse);
                        }
                    }
                    catch(Exception ex) { /* do nothing */}
                }
            }
        });
        binder.registerCustomEditor(OrderChannel.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (!StringUtils.hasText(text)) {
                    return;
                } else {
                    try
                    {
                        OrderChannel channel = orderChannelService.findById(Long.parseLong(text));
                        if(channel != null){
                            setValue(channel);
                        }
                    }
                    catch(Exception ex)  { /* do nothing */}
                }
            }
        });
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));

        CompanyContract parent = contract.getParent();
        if(parent != contract){

            binder.bind(new MutablePropertyValues(map));
            contract.setUpdateDate(new Date());
            AgentUser agentUser = SecurityHelper.getLoginUser();
            if(agentUser != null){
                contract.setUpdateUser(agentUser.getUserId());
            }
        }
    }
}
