/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.sales.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.user.service.UserService;

/**
 * 2013-12-30 下午12:45:37
 * @version 1.0.0
 * @author yangfei
 *
 */
@Controller
@RequestMapping(value="/security")

public class SecurityController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(SecurityController.class);
	
	@Autowired
	private UserService userService;
	
    @RequestMapping(value = "/modifyPwd4Iagent",method = RequestMethod.POST)
    @ResponseBody
    public void modifyPwd4Iagent(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	
    	JSONObject jsonObj = new JSONObject();
    	String userId = request.getParameter("userId");
    	String newPassword = request.getParameter("newPassword");
    	String oldPassword = request.getParameter("oldPassword");
    	if(StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(newPassword)) {
			try {
				boolean isAllowModify = true;
				if(StringUtils.isNotBlank(oldPassword)) {
					isAllowModify = userService.checkPassword(userId, oldPassword);
				}
				if (isAllowModify) {
					Map<String, Object> result = userService.resetUserPassword(userId, newPassword);
					if("success".equalsIgnoreCase((String)result.get("result"))){
						jsonObj.put("status", "1");
						jsonObj.put("msg", "修改成功");
					} else if("failure".equalsIgnoreCase((String)result.get("result"))){
						jsonObj.put("status", "-1");
						jsonObj.put("msg", (String)result.get("message"));
					}

				} else {
					//
					jsonObj.put("status", "-2");
					jsonObj.put("msg", "旧密码不正确，修改失败");
				}
			} catch (ServiceException e) {
				logger.info("修改密码失败,错误信息=" + e.getMessage());
				logger.error("",e);
				jsonObj.put("status", e.getErrorCode());
				jsonObj.put("msg", e.getMessage());
			}
    	} else {
			jsonObj.put("status", "0");
			jsonObj.put("msg", "输入错误");
    	}
		response.setContentType("text/json;charset=UTF-8");
		response.getWriter().print(jsonObj.toString());
    }
    
    @RequestMapping(value = "/releaseLock")
    @ResponseBody
    public void releaseLock(@RequestParam(required = true, defaultValue = "") String userId,
    		HttpServletRequest request, HttpServletResponse response) throws IOException{
    	
    	JSONObject jsonObj = new JSONObject();
    	if(StringUtils.isNotBlank(userId)) {
			try {
				userService.releaseLock(userId);
                jsonObj.put("status", "1");
                jsonObj.put("msg", "解锁成功");
			} catch (ServiceException e) {
				logger.info("解锁失败：" + e.getMessage());
                logger.error("",e);
				jsonObj.put("status", "0");
				jsonObj.put("msg", e.getMessage());
			}
    	} else {
			jsonObj.put("status", "0");
			jsonObj.put("msg", "输入错误");
    	}
		response.setContentType("text/json;charset=UTF-8");
		response.getWriter().print(jsonObj.toString());
    }
}
