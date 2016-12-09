package com.chinadrtv.taobao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 14-3-3
 * Time: 上午10:50
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class TaobaoCloudController {

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    @ResponseBody
    public String getTradeList2() {
        return "this is test url  date:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

}
