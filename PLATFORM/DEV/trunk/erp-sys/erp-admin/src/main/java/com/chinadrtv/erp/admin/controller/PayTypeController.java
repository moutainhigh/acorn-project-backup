package com.chinadrtv.erp.admin.controller;

import com.chinadrtv.erp.admin.model.PayType;
import com.chinadrtv.erp.admin.service.PayTypeService;
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
public class PayTypeController extends BaseController {

    @Autowired
    private PayTypeService payTypeService;
    @RequestMapping(value = "/admin/PayType/lookup", method = RequestMethod.POST)
    public String lookup(HttpServletRequest request,
                         HttpServletResponse response) throws Exception {
        /*
        TODO:提供付款方式ComboBox绑定数据
        */
        JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
        List<PayType> payTypeList = payTypeService.getAllPayTypes();
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().print(jsonBinder.toJson(payTypeList));
        return null;
    }

}
