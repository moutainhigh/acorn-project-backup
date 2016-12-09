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
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: gaodejian
 * Date: 12-11-13
 * Time: 下午1:54
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class OnlieShopController extends BaseController {

    @Autowired
    private OnlieShopService onlieShopService;
    @Autowired
    private CompanyService companyService;

    @RequestMapping(value = "/admin/OnlieShop/lookup", method = RequestMethod.POST)
    public String lookup(HttpServletRequest request,
                       HttpServletResponse response) throws Exception {
        /*
        TODO:提供ComboBox绑定数据
        */
        JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
        List<OnlieShop> companyList = onlieShopService.getAllOnliShop();
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().print(jsonBinder.toJson(companyList));
        return null;
    }
    @RequestMapping(value = "/admin/Company/lookup", method = RequestMethod.POST)
    public String CompanyName(HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        /*
        TODO:提供订单类型ComboBox绑定数据
        */
        JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
        List<Company> companyList = companyService.getAllCompanies();
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().print(jsonBinder.toJson(companyList));
        return null;
    }

}
