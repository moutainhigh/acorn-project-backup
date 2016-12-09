package com.chinadrtv.erp.sales.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;

/**
 * 
 * <dl>
 *    <dt><b>Title:用户操作相关类</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-7-26 下午2:05:33 
 *
 */
@Controller
@RequestMapping(value = "user")
public class UserController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/modifyPwdInit")
    public ModelAndView get() {
        ModelAndView mav = new ModelAndView("user/modifyPwd");
        
        return mav;
    }
    
    @RequestMapping(value = "/modifyPwd")
    @ResponseBody
    public Map<String,String> modifyPwd(HttpServletRequest request,String oldPassword,String newPassword){
    	
    	Map<String,String> result = new HashMap<String, String>();
    	
    	AgentUser user = SecurityHelper.getLoginUser();
    	if(user!=null){
    		try {
				//boolean isAllowModify = userService.checkPassword(user.getUserId(), oldPassword);
				//if(isAllowModify){
					userService.changePassword(user.getUserId(), newPassword,oldPassword);
					result.put("status", "1");
				//}else{
					//
					//result.put("status", "-2");
				//}
			} catch (Exception e) {
				logger.error("修改密码失败:", e.getMessage());
				result.put("status", "0");
				result.put("msg",e.getMessage());
			}
    	}else{
    		result.put("status", "-1");
    	}
    	
    	return result;
    }

}

