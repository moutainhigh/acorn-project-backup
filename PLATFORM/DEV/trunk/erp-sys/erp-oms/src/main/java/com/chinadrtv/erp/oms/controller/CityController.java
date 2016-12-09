package com.chinadrtv.erp.oms.controller;

import com.chinadrtv.erp.model.agent.CityAll;
import com.chinadrtv.erp.oms.service.CityService;
import com.chinadrtv.erp.util.JsonBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 城市控制类
 * User: gaodejian
 * Date: 12-11-13
 * Time: 下午1:54
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class CityController extends BaseController {

    @Autowired
    private CityService cityService;

    @RequestMapping(value = "/order/city/load", method = RequestMethod.POST)
    public String load(HttpServletRequest request,
                        HttpServletResponse response) throws Exception {
        /*
        TODO:提供DataGrid绑定数据
        */
        JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
        List<CityAll> list = cityService.getAllCities();
        int totalRecords = list.size();
        String jsonData = "{\"rows\":" +jsonBinder.toJson(list) + ",\"total\":" + totalRecords + " }";
        response.setContentType("text/json; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().print(jsonData);
        return null;
    }

    @RequestMapping(value = "/order/city/lookup", method = RequestMethod.POST)
    public String lookup(
          @RequestParam(required = false, defaultValue = "") String provinceId,
          HttpServletRequest request,
          HttpServletResponse response) throws Exception {
        /*
        TODO:提供ComboBox绑定数据
        */
        JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
        List<CityAll> list = cityService.getCities(provinceId);
        response.setContentType("text/json; charset=UTF-8");
        response.getWriter().print(jsonBinder.toJson(list));
        return null;
    }
}
