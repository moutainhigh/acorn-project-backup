package com.chinadrtv.erp.ic.controller;

import com.chinadrtv.erp.ic.model.JsonData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * User: xuzk
 * Date: 13-1-24
 */
@Controller
@RequestMapping("/test")
public class TestController {


    @RequestMapping(value = "/add",method= RequestMethod.POST, headers="Content-Type=application/json")
    @ResponseBody
    public Integer addOrderhist(@RequestBody JsonData jsonData){
        System.out.println(jsonData.getId()+" - "+ jsonData.getData());
        return 1;
    }
}
