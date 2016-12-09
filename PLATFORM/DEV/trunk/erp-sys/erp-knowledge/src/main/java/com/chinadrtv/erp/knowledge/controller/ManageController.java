package com.chinadrtv.erp.knowledge.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created with IntelliJ IDEA.
 * User: liuhaidong
 * Date: 13-5-14
 * Time: 下午4:41
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/manage")
public class ManageController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ManageController.class);
	
    
    @RequestMapping("/index")
    public String index() throws Exception {
        return "/manage/index";
    }
    
}