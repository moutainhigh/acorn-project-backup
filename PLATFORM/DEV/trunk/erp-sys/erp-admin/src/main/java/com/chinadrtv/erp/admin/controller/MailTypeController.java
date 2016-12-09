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
 * 订购方式处理
 */
@Controller
public class MailTypeController extends BaseController {

    @Autowired
    private MailTypeService mailTypeService;

    @RequestMapping(value = "/admin/mailType/load", method = RequestMethod.POST)
    public String load(HttpServletRequest request,
                        HttpServletResponse response) throws Exception {
        /*
        TODO:提供DataGrid绑定数据
        */
        JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
        List<MailType> list = mailTypeService.getAllMailTypes();
        String jsonData = "{\"rows\":" +jsonBinder.toJson(list) + ",\"total\":" +  list.size() + " }";

        response.setContentType("text/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().print(jsonData);
        return null;
    }


    @RequestMapping(value = "/admin/mailType/lookup", method = RequestMethod.POST)
    public String lookup(HttpServletRequest request,
                       HttpServletResponse response) throws Exception {
        /*
        TODO:提供ComboBox绑定数据
        */
        JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
        List<MailType> list = mailTypeService.getAllMailTypes();
        response.setContentType("text/json; charset=UTF-8");
        response.getWriter().print(jsonBinder.toJson(list));
        return null;
    }
}
