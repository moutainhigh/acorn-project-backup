package com.chinadrtv.erp.admin.controller;

import com.chinadrtv.erp.admin.model.*;
import com.chinadrtv.erp.util.JsonBinder;
import com.chinadrtv.erp.admin.service.*;
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
public class OrderTypeController extends BaseController {

    @Autowired
    private OrderTypeService orderTypeService;
    @Autowired
    private CompanyService companyService;

    @RequestMapping(value = "/admin/QueryOrderType", method = RequestMethod.POST)
    public String load(HttpServletRequest request,
                        HttpServletResponse response) throws Exception {
        /*
        TODO:提供DataGrid绑定数据
        */
        JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
        List<OrderType> companyList = orderTypeService.getAllOrderTypes();
        int totalRecords = companyList.size();
        String jsonData = "{\"rows\":" +jsonBinder.toJson(companyList) + ",\"total\":" + totalRecords + " }";

        response.setContentType("text/json; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().print(jsonData);
        return null;
    }
    @RequestMapping(value = "/admin/OrderTypeLog", method = RequestMethod.POST)
    public String lookup(HttpServletRequest request,
                       HttpServletResponse response) throws Exception {
        /*
        TODO:提供订单类型ComboBox绑定数据
        */
        JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
        List<OrderType> orderTypeList = orderTypeService.getAllOrderTypes();
       response.setContentType("text/json;charset=UTF-8");
       response.getWriter().print(jsonBinder.toJson(orderTypeList));
       // response.setContentType("text/json; charset=UTF-8");
      //  String jsonData = "{\"rows\":" +jsonBinder.toJson(orderTypeList) + "}";
      //  response.getWriter().print(jsonData);
       // response.setHeader("Cache-Control", "no-cache");
        return null;
    }

}
