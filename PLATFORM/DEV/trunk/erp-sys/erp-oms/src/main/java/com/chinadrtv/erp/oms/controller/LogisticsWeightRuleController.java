package com.chinadrtv.erp.oms.controller;

import com.chinadrtv.erp.model.LogisticsFeeRule;
import com.chinadrtv.erp.model.LogisticsWeightRuleDetail;
import com.chinadrtv.erp.model.agent.CityAll;
import com.chinadrtv.erp.model.agent.CountyAll;
import com.chinadrtv.erp.model.agent.Province;
import com.chinadrtv.erp.oms.service.*;
import com.chinadrtv.erp.util.JsonBinder;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: gaodejian
 * Date: 12-11-13
 * Time: 下午1:54
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class LogisticsWeightRuleController extends BaseController {

    @Autowired
    private LogisticsFeeRuleService headerService;
    @Autowired
    private LogisticsWeightRuleDetailService detailService;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private CityService cityService;

    @Autowired
    private CountyService countyService;

    @RequestMapping(value = "/company/logisticsWeightRuleDetail/load", method = RequestMethod.POST)
    public String load(@RequestParam(required = false, defaultValue = "1") int page,
                       @RequestParam Integer rows,
                       @RequestParam(required = false) Integer ruleId,
                       HttpServletRequest request,
                       HttpServletResponse response) throws Exception {

        if(ruleId != null && ruleId > 0){
            Long totalRecords = detailService.getWeightRuleDetailCount(ruleId);
            List<LogisticsWeightRuleDetail> list = detailService.getWeightRuleDetailsByRuleId(ruleId, (page - 1) * rows, rows);

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .setExclusionStrategies(
                            new ExcludeUnionResource(
                                    new String[]{"parent", "orderPayTypes"}))
                    .create();

            JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
            String jsonData = "{\"rows\":" +gson.toJson(list) + ",\"total\":" + totalRecords + " }";
            response.setContentType("text/json; charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().print(jsonData);
        }
        return null;
    }

    @RequestMapping(value = "/company/logisticsWeightRuleDetail/add", method = RequestMethod.POST)
    public ModelAndView add(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/plain; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        try
        {
            LogisticsWeightRuleDetail detail = new LogisticsWeightRuleDetail();
            updateLogisticsWeightRuleDetail(detail, request.getParameterMap());
            detailService.add(detail);
            response.getWriter().print("{\"success\":\"true\"}");
        }
        catch (Exception ex){
            response.getWriter().print("{\"errorMsg\":\""+ ex.getMessage() +"\"}");
        }
        return null;
    }

    @RequestMapping(value = "/company/logisticsWeightRuleDetail/save", method = RequestMethod.POST)
    public ModelAndView save(
            @RequestParam(required = true, defaultValue = "") Integer id,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        response.setContentType("text/plain; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        try
        {
            LogisticsWeightRuleDetail detail = detailService.getWeightRuleDetailById(id);
            if(detail != null) {
                updateLogisticsWeightRuleDetail(detail, request.getParameterMap());

                detailService.save(detail);
            }
            response.getWriter().print("{\"success\":\"true\"}");
        }
        catch (Exception ex){
            response.getWriter().print("{\"errorMsg\":\""+ ex.getMessage() +"\"}");
        }
        return null;
    }

    @RequestMapping(value = "/company/logisticsWeightRuleDetail/delete", method = RequestMethod.POST)
    public ModelAndView delete(@RequestParam(required = true) Integer id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/plain; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        try
        {
            detailService.remove(id);
            response.getWriter().print("{\"success\":\"true\"}");
        }
        catch (Exception ex){
            response.getWriter().print("{\"errorMsg\":"+ ex.getMessage() +"}");
        }
        return null;
    }

    private void updateLogisticsWeightRuleDetail(LogisticsWeightRuleDetail detail, Map map){

        WebDataBinder binder = new WebDataBinder(detail);
        binder.registerCustomEditor(LogisticsFeeRule.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (!StringUtils.hasText(text)) {
                    return;
                } else {
                    try
                    {
                        setValue(headerService.getLogisticsFeeRuleById(Integer.parseInt(text)));
                    }
                    catch(Exception ex) { /* do nothing */}
                }
            }
        });
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.bind(new MutablePropertyValues(map));
        if(detail.getRule() != null &&
           detail.getRule().getContract() != null){
            detail.setWarehouse(detail.getRule().getContract().getWarehouse());
        }
    }

    @RequestMapping(value = "/company/logisticsWeightRuleDetail/provinces", method = RequestMethod.POST)
    public String lookupProvinces(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        /*
        TODO:提供ComboBox绑定数据
        */
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        List<Province> provinces = provinceService.getAllProvinces();
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().print(gson.toJson(provinces));
        return null;
    }

    @RequestMapping(value = "/company/logisticsWeightRuleDetail/cities", method = RequestMethod.POST)
    public String lookupCities(
            @RequestParam(required = false, defaultValue = "") String provinceId,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        /*
        TODO:提供ComboBox绑定数据
        */
        List<CityAll> cites;
        if(!"".equals(provinceId)){
            cites = cityService.getCities(provinceId);
        } else  {
            cites=cityService.getAllCities();
        }

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().print(gson.toJson(cites));
        return null;
    }

    @RequestMapping(value = "/company/logisticsWeightRuleDetail/counties", method = RequestMethod.POST)
    public String lookupCounties(
            @RequestParam(required = false, defaultValue = "") Long cityId,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        /*
        TODO:提供ComboBox绑定数据
        */
        List<CountyAll> counties;
        if(cityId != null && cityId > 0){
            counties = countyService.getCounties(cityId);
        } else {
            counties = countyService.getAllCounties();
        }
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().print(gson.toJson(counties));
        return null;
    }
}
