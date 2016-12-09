package com.chinadrtv.erp.oms.controller;


import com.chinadrtv.erp.model.agent.Province;
import com.chinadrtv.erp.oms.service.ProvinceService;
import com.chinadrtv.erp.util.JsonBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: gaodejian
 * Date: 12-11-13
 * Time: 下午1:54
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class ProvinceController extends BaseController {

    @Autowired
    private ProvinceService provinceService;

    @RequestMapping(value = "/order/province/load", method = RequestMethod.POST)
    public String load(HttpServletRequest request,
                        HttpServletResponse response) throws Exception {
        /*
        TODO:提供DataGrid绑定数据
        */
        JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
        List<Province> companyList = provinceService.getAllProvinces();
        int totalRecords = companyList.size();
        String jsonData = "{\"rows\":" +jsonBinder.toJson(companyList) + ",\"total\":" + totalRecords + " }";
        response.setContentType("text/json; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().print(jsonData);
        return null;
    }


    @RequestMapping(value = "/order/province/lookup", method = RequestMethod.POST)
    public String lookup(HttpServletRequest request,
                       HttpServletResponse response) throws Exception {
        /*
        TODO:提供ComboBox绑定数据
        "[{\"id\":\"1\",\"name\":\"1234567678\"}]"
        */
        JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
        List<Province> companyList = provinceService.getAllProvinces();
        response.setContentType("text/json; charset=UTF-8");
        response.getWriter().print(jsonBinder.toJson(companyList));
        return null;
    }
}
