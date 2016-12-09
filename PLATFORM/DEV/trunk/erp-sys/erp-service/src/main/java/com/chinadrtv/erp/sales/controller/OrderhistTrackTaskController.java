/*
 * @(#)OrderhistTrackTaskController.java 1.0 2013年12月25日下午2:35:45
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.sales.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.sales.core.dto.OrderhistTrackTaskDto;
import com.chinadrtv.erp.sales.core.service.OrderhistTrackTaskService;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013年12月25日 下午2:35:45 
 * 
 */
@Controller
@RequestMapping("/myorder/trackorder")
public class OrderhistTrackTaskController extends BaseController {
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderhistTrackTaskController.class);

	@Autowired
	private OrderhistTrackTaskService orderhistTrackTaskService;
	@Autowired
	private UserService userService;
	
    @InitBinder
    public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
    }
    
	@RequestMapping("/queryPageList")
	@ResponseBody
	public Map<String, Object> queryPageList(DataGridModel dataGridModel, OrderhistTrackTaskDto orderhistTrackTaskDto) {
		Map<String, Object> rsMap = orderhistTrackTaskService.queryPageList(dataGridModel, orderhistTrackTaskDto);
		return rsMap;
	}
	
	@RequestMapping("/queryUserList")
	@ResponseBody
	public Map<String, Object> queryUserList(){
		Map<String, Object> rsMap = new HashMap<String, Object>();
		boolean success = false;
		String message = "";
		
		AgentUser user = SecurityHelper.getLoginUser();
		List<AgentUser> userList = null;
       
        try {
        	String userGroup = userService.getUserGroup(user.getUserId());
        	userList = userService.getUserList(userGroup);
			success = true;
		} catch (ServiceException e) {
			logger.error("获取用户列表失败", e);
			message = e.getMessage();
			e.printStackTrace();
		}
        
        rsMap.put("success", success);
        rsMap.put("message", message);
        rsMap.put("userList", userList);
            
        return rsMap;
	}
}
