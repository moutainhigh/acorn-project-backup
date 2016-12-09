package com.chinadrtv.erp.distribution.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.chinadrtv.erp.constant.SecurityConstants;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.SecurityHelper;

/**
 * Created with IntelliJ IDEA. User: liuhaidong Date: 13-5-14 Time: 下午4:41 To
 * change this template use File | Settings | File Templates.
 */
@Controller
public class HomeController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);


	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView("/index");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date curDate = new Date();
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(curDate);
		calendar.add(Calendar.DATE, -2);
		
		modelAndView.addObject("curDate", sdf.format(curDate));
		modelAndView.addObject("incomingLowDate", sdf.format(calendar.getTime()) + " 00:00:00");
		modelAndView.addObject("incomingHighDate", sdf.format(curDate) + " 23:59:59");
		
		AgentUser user = SecurityHelper.getLoginUser();
		boolean isIb = false;
		isIb |= user.hasRole(SecurityConstants.ROLE_INBOUND_AGENT);
		isIb |= user.hasRole(SecurityConstants.ROLE_INBOUND_GROUP_MANAGER);
		isIb |= user.hasRole(SecurityConstants.ROLE_INBOUND_MANAGER);
		
		modelAndView.addObject("isIb", isIb);

        modelAndView.addObject("currentUser", user);
		
		//主管权限
		boolean isSupervisor = false;
		isSupervisor |= user.hasRole(SecurityConstants.ROLE_ASSIGN_TO_AGENT);
		modelAndView.addObject("isSupervisor", isSupervisor);
		
		//部门经理权限
		boolean isDepartmentManager = false;
		isDepartmentManager |= user.hasRole(SecurityConstants.ROLE_INBOUND_MANAGER);
		isDepartmentManager |= user.hasRole(SecurityConstants.ROLE_OUTBOUND_MANAGER);
		if(isSupervisor) {
			isDepartmentManager = false;
		}
		modelAndView.addObject("isDepartmentManager", isDepartmentManager);
		
		//分配到部门的权限
		boolean deptAssignRight = false;
		deptAssignRight |= user.hasRole(SecurityConstants.ROLE_ASSIGN_TO_DEPARTMENT);
		modelAndView.addObject("deptAssignRight", deptAssignRight);
		
		return modelAndView;
	}

	@RequestMapping("/welcome")
	public ModelAndView welcome() throws Exception {
		ModelAndView modelAndView = new ModelAndView("/welcome");
        AgentUser user = SecurityHelper.getLoginUser();
        if(user != null){
            modelAndView.addObject("currentUser", user);
        }
		return modelAndView;
	}
}