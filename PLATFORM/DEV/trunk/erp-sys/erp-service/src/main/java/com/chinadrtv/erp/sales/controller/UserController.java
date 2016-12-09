package com.chinadrtv.erp.sales.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.model.ScmPromotion;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.common.AuditTaskStatus;
import com.chinadrtv.erp.marketing.core.common.AuditTaskType;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskStatus;
import com.chinadrtv.erp.marketing.core.common.UserBpmTaskType;
import com.chinadrtv.erp.marketing.core.dto.CampaignDto;
import com.chinadrtv.erp.marketing.core.dto.CampaignTaskDto;
import com.chinadrtv.erp.marketing.core.dto.CampaignTaskVO;
import com.chinadrtv.erp.marketing.core.exception.MarketingException;
import com.chinadrtv.erp.marketing.core.util.HttpUtil;
import com.chinadrtv.erp.model.Address;
import com.chinadrtv.erp.model.AddressChange;
import com.chinadrtv.erp.model.AddressExt;
import com.chinadrtv.erp.model.AddressExtChange;
import com.chinadrtv.erp.model.Contact;
import com.chinadrtv.erp.model.ContactChange;
import com.chinadrtv.erp.model.Names;
import com.chinadrtv.erp.model.Phone;
import com.chinadrtv.erp.model.PhoneChange;
import com.chinadrtv.erp.model.PotentialContact;
import com.chinadrtv.erp.model.PotentialContactPhone;
import com.chinadrtv.erp.model.ShoppingCart;
import com.chinadrtv.erp.model.ShoppingCartProduct;
import com.chinadrtv.erp.model.SysMessage;
import com.chinadrtv.erp.model.agent.AreaAll;
import com.chinadrtv.erp.model.agent.Cases;
import com.chinadrtv.erp.model.agent.CityAll;
import com.chinadrtv.erp.model.agent.Cmpfback;
import com.chinadrtv.erp.model.agent.CountyAll;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.model.agent.Province;
import com.chinadrtv.erp.model.marketing.UserBpm;
import com.chinadrtv.erp.model.marketing.UserBpmTask;
import com.chinadrtv.erp.sales.core.constant.MessageType;
import com.chinadrtv.erp.sales.dto.CasesDto;
import com.chinadrtv.erp.sales.dto.CustomerFrontDto;
import com.chinadrtv.erp.sales.dto.CustomerPhoneFrontDto;
import com.chinadrtv.erp.sales.dto.ShoppingCartDto;
import com.chinadrtv.erp.sales.dto.ShoppingCartProductDto;
import com.chinadrtv.erp.sales.dto.SmsSendDetailDto;
import com.chinadrtv.erp.smsapi.model.SmsSendDetail;
import com.chinadrtv.erp.uc.dto.AddressDto;
import com.chinadrtv.erp.uc.dto.CardDto;
import com.chinadrtv.erp.uc.dto.ContactDto;
import com.chinadrtv.erp.uc.dto.PhoneAddressDto;
import com.chinadrtv.erp.user.aop.Mask;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.chinadrtv.erp.util.DateUtil;
import com.chinadrtv.erp.util.PojoUtils;

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

