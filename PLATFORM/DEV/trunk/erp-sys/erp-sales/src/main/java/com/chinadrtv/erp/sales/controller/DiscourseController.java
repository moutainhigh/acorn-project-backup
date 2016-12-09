package com.chinadrtv.erp.sales.controller;

import com.chinadrtv.erp.marketing.core.service.DiscourseApiService;
import com.chinadrtv.erp.model.marketing.Discourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品话术
 * User: Administrator
 * Date: 13-5-31
 * Time: 下午2:58
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class DiscourseController extends BaseController {

    @Autowired
    private DiscourseApiService discourseApiService;

    @ResponseBody
    @RequestMapping(value = "/product/discourse",method= RequestMethod.POST)
    public Discourse getDiscourses(@RequestParam(required = true) String nccode)  throws Exception {
        return discourseApiService.getDiscourseByProductCode(nccode);
    }
}
