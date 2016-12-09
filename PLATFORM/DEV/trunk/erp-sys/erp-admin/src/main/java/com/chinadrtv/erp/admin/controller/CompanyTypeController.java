package com.chinadrtv.erp.admin.controller;

import com.chinadrtv.erp.admin.model.*;
import com.chinadrtv.erp.admin.service.*;
import com.chinadrtv.erp.util.JsonBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


/**
 * Created with IntelliJ IDEA.
 * User: gaodejian
 * Date: 12-11-13
 * Time: 下午1:54
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class CompanyTypeController extends BaseController {

    @Autowired
    private CompanyTypeService companyTypeService;

    @RequestMapping(value = "/admin/companyType/load", method = RequestMethod.POST)
    public String load(HttpServletRequest request,
                        HttpServletResponse response) throws Exception {
        /*
        TODO:提供DataGrid绑定数据
        */
        JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
        List<CompanyType> companyList = companyTypeService.getAllCompanyTypes();
        int totalRecords = companyList.size();
        String jsonData = "{\"rows\":" +jsonBinder.toJson(companyList) + ",\"total\":" + totalRecords + " }";

        response.setContentType("text/json; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().print(jsonData);
        return null;
    }


    @RequestMapping(value = "/admin/companyType/lookup", method = RequestMethod.POST)
    public String lookup(HttpServletRequest request,
                       HttpServletResponse response) throws Exception {
        /*
        TODO:提供ComboBox绑定数据
        */
        JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
        List<CompanyType> companyList = companyTypeService.getAllCompanyTypes();
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().print(jsonBinder.toJson(companyList));
        return null;
    }
}
