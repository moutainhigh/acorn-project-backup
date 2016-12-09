/*
 * Copyright (c) 2012 Acorn International.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http:www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or  implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.chinadrtv.erp.knowledge.controller;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.chinadrtv.erp.util.JsonBinder;

/**
 * User: liuhaidong Date: 12-7-24
 */
@Controller
public abstract class BaseController {
	private static final Logger logger = Logger.getLogger(BaseController.class
			.getName());
	public JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
	@Autowired
	private UserService userService;

	private ModelAndView errorModelAndView(Exception ex) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("exception/handling");
		modelAndView.addObject("exception", ex);
		return modelAndView;
	}

	// @ExceptionHandler({PageException.class})
	public ModelAndView handleExceptionArray(Exception ex,
			HttpServletRequest request) {
		logger.info("Uncaught exception: " + ex.getClass().getSimpleName());
		logger.info("exception: " + ex.getMessage());
		return errorModelAndView(ex);
	}

	protected String getUserId(HttpServletRequest request) {
		return SecurityHelper.getLoginUser().getUserId();
	}

	protected String getGoupType(HttpServletRequest request) {
		try {
			String userType = userService.getGroupType(
					SecurityHelper.getLoginUser().getUserId()).toUpperCase();
			return userType;
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	// protected String getUserName(HttpServletRequest request){
	// return SecurityHelper.getLoginUser().getUserId();
	// }

	protected String getDepartmentId(HttpServletRequest request) {
		return SecurityHelper.getLoginUser().getDepartment();
	}

	public Integer getCompanyId(HttpServletRequest request) {
		return 1;
	}

}
