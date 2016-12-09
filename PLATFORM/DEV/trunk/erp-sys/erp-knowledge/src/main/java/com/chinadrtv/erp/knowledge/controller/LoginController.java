package com.chinadrtv.erp.knowledge.controller;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.chinadrtv.erp.user.util.SecurityHelper;

/**
 * User: liuhaidong
 * Date: 12-11-20
 */
@Controller(value = "loginControl")
public class LoginController extends BaseController {
     private static final Logger logger = Logger.getLogger(BaseController.class.getName());

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String toLogin(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "login/login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String toLogout(HttpServletRequest request, HttpServletResponse response,@RequestParam(required = true, defaultValue = "") String name ,Model model) {
        if(SecurityHelper.getLoginUser() != null){
                logger.info("用户"+SecurityHelper.getLoginUser().getUserId()+"退出");
        }


        return "login/logout";
    }



}
