package com.chinadrtv.erp.oms.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chinadrtv.erp.core.model.AuditLog;
import com.chinadrtv.erp.core.service.AuditLogService;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.SecurityHelper;

/**
 * 系统登陆
 * User: liuhaidong
 * Date: 12-11-20
 */
@Controller(value = "loginControl")
public class LoginController {
	Logger log = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private AuditLogService auditLogService;
	
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String toLogin(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "login/login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String toLogout(HttpServletRequest request, HttpServletResponse response, Model model) {
    	//wirteLog("退出","退出系统",request);
    	log.info("退出系统");
        return "login/logout";
    }
    
    
    @RequestMapping(value = "/wel", method = RequestMethod.GET)
    public String wel(HttpServletRequest request, HttpServletResponse response, Model model) {
    	//log.info("OMSsession:" + request.getSession().getAttribute("OMSsessiongId"));
		if (request.getSession().getAttribute("OMSsessiongId") == null) {
			String ip = this.getIpAddr(request);
			wirteLog("登陆", "系统登陆 " + ip, "", request);
		}
		request.getSession().setAttribute("OMSsessiongId",
				request.getSession().getId());
		request.getSession().setAttribute("omsusername",SecurityHelper.getLoginUser().getUsername());
        return "login/welcome";
    }
    
    public void wirteLog (String title , String txt,String treadId,HttpServletRequest request){
   	 AuditLog auditLog = new AuditLog();
        auditLog.setAppName("OMS");
        auditLog.setFuncName(title);
        auditLog.setLogDate(new Date());
        auditLog.setLogValue(txt);
        auditLog.setSessionId(request.getSession().getId());
        AgentUser user = SecurityHelper.getLoginUser();
        auditLog.setUserId(user.getUserId() + "(" + user.getUsername() + ")");
        auditLogService.addAuditLog(auditLog);
    
   }
    
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
