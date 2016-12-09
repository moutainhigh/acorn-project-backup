package com.chinadrtv.erp.sales.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.model.Address;
import com.chinadrtv.erp.model.Contact;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.model.service.EmmaTemplate;
import com.chinadrtv.erp.sales.core.service.OrderhistService;
import com.chinadrtv.erp.service.core.service.SmsEmmaService;
import com.chinadrtv.erp.uc.dto.AddressDto;
import com.chinadrtv.erp.uc.service.AddressService;
import com.chinadrtv.erp.uc.service.ContactService;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.SecurityHelper;

/**
 * 
 * @author dengqianyong
 *
 */
@Controller
@RequestMapping("/sms")
public class SmsEmmaController extends BaseController {
	
	private static final String KW_NAME 		= "[姓名]";
	private static final String KW_MOBILE 		= "[电话]";
	private static final String KW_ADDRESS 		= "[地址]";
	private static final String KW_ORDER 		= "[订单号]";
	private static final String KW_PRODUCT 		= "[产品]";
	private static final String KW_PRICE 		= "[订单金额]";
	private static final String KW_MAIL 		= "[包裹单]";
	private static final String KW_MAIL_ADDRESS = "[配送地址]";
	
	private static final String COMPLAIN_TYPE = "Complain";
	private static final String KFWL_TYPE = "KFWL";
	
	private static final String[] ORDER_KEYWORDS = new String[] { KW_ORDER,
			KW_PRODUCT, KW_PRICE, KW_MAIL };

	@Autowired
	private SmsEmmaService smsEmmaService;
	
	@Autowired
	private ContactService contactService;
	
	@Autowired
	private OrderhistService orderService;
	
	@Autowired
	private AddressService addressService;
	
	@ResponseBody
	@RequestMapping(value = "/template/list", method = RequestMethod.POST)
	public Map<String, Object> list(DataGridModel model) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", smsEmmaService.count());
		result.put("rows", smsEmmaService.pageTemplates(model));
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/template/list/activated", method = RequestMethod.POST)
	public Map<String, Object> listActivated(DataGridModel model) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", smsEmmaService.count(
				"1", isKFWL() ? KFWL_TYPE : COMPLAIN_TYPE));
		result.put("rows", smsEmmaService.pageTemplates(
				model, "1", isKFWL() ? KFWL_TYPE : COMPLAIN_TYPE));
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/template/save", method = RequestMethod.POST)
	public String save(@RequestBody EmmaTemplate emma) {
		AgentUser user = SecurityHelper.getLoginUser();
		if (user != null) {
			emma.setCreateUser(user.getUserId());
			emma.setModifyUser(user.getUserId());
		}
		Date now = new Date();
		emma.setCreateDate(now);
		emma.setModifyDate(now);
		emma.setGrpType(isKFWL() ? KFWL_TYPE : COMPLAIN_TYPE);
		smsEmmaService.save(emma);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "/template/remove", method = RequestMethod.POST)
	public String remove(@RequestBody String type) {
		smsEmmaService.remove(type);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "/merge", method = RequestMethod.POST)
	public Map<String, String> mergeSmsContent(@RequestBody SmsMergeDto model) {
		EmmaTemplate template = smsEmmaService.get(model.getType());
		String templateContent = template.getDesc();
		if (needOrderId(templateContent) && StringUtils.isEmpty(model.getOrderId())) {
			return result(false, "该模板需要输入订单号");
		}
		
		// 替换用户姓名
		if (hasKeyword(templateContent, KW_NAME)) {
			try {
				Contact contact = contactService.get(model.getContactId());
				templateContent = replaceContent(templateContent, KW_NAME, contact.getName());
			} catch (Exception e) {
				return result(false, "找不到该客户");
			}
		}
		
		// 替换手机
		if (hasKeyword(templateContent, KW_MOBILE)) {
			templateContent = replaceContent(templateContent, KW_MOBILE, model.getMobile());
		}
		
		// 替换地址
		if (hasKeyword(templateContent, KW_ADDRESS)) {
			AddressDto addressDto = addressService.getContactMainAddress(model.getContactId());
			if (addressDto == null) {
				return result(false, "未找到主地址");
			}
			templateContent = replaceContent(templateContent, KW_ADDRESS, addressDto.getAddress());
		}
		
		Order order = null;
		if (model.getOrderId() != null) {
			try {
				order = orderService.getOrderHistByOrderid(model.getOrderId());
			} catch (Exception e) {
				return result(false, "未找到该订单");
			}
		}
		
		// 订单号
		if (hasKeyword(templateContent, KW_ORDER)) {
			templateContent = replaceContent(templateContent, KW_ORDER, 
					model.getOrderId().toString());
		}
		
		// 产品
		if (hasKeyword(templateContent, KW_PRODUCT)) {
			String defaultProductName = "";
			int index = 0;
			String productName = "";
			Set<OrderDetail> details = order.getOrderdets();
			for (Iterator<OrderDetail> it = details.iterator(); it.hasNext();) {
				OrderDetail det = it.next();
				if (index == 0) {
					defaultProductName = det.getProdname();
				}
				if ("1".equals(det.getSoldwith())) {
					productName = det.getProdname();
					break;
				}
			}
			if ("".equals(productName)) {
				productName = defaultProductName;
			}
			templateContent = replaceContent(templateContent, KW_PRODUCT, productName);
		}
		
		// 订单金额
		if (hasKeyword(templateContent, KW_PRICE)) {
			templateContent = replaceContent(templateContent, KW_PRICE, 
					order.getTotalprice().toString());
		}
		
		// 包裹单
		if (hasKeyword(templateContent, KW_MAIL)) {
			templateContent = replaceContent(templateContent, KW_MAIL, 
					order.getMailid() != null ? order.getMailid() : "");
		}
		
		// 配送地址
		if (hasKeyword(templateContent, KW_MAIL_ADDRESS)) {
			if (model.getOrderId() == null) {
				AddressDto addressDto = addressService.getContactMainAddress(model.getContactId());
				if (addressDto == null) {
					return result(false, "未找到主地址");
				}
				templateContent = replaceContent(templateContent, KW_MAIL_ADDRESS, addressDto.getAddress());
			} else {
				String addressId = order.getAddress().getAddressId();
				try {
					Address address = addressService.get(addressId);
					templateContent = replaceContent(templateContent, KW_MAIL_ADDRESS, address.getAddress());
				} catch (Exception e) {
					return result(false, "未找到配送地址");
				}
			}
		}
		
		return result(true, templateContent);
	}
	
	private Map<String, String> result(boolean isSuccess, String msg) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("code", isSuccess ? "1" : "0");
		result.put("msg", msg);
		return result;
	}
	
	/**
	 * 
	 * @param template
	 * @param keyword
	 * @return
	 */
	private boolean hasKeyword(String template, String keyword) {
		return StringUtils.isNotEmpty(template)
				&& StringUtils.indexOf(template, keyword) >= 0;
	}
	
	/**
	 * 
	 * @param template
	 * @param keyword
	 * @param content
	 * @return
	 */
	private String replaceContent(String template, String keyword,
			String content) {
		return StringUtils.replace(template, keyword, content);
	}
	
	/**
	 * 
	 * @param template
	 * @return
	 */
	private boolean needOrderId(String template) {
		return StringUtils.isNotEmpty(template)
				&& StringUtils.indexOfAny(template, ORDER_KEYWORDS) >= 0;
	}
	
	private boolean isKFWL() {
		return SecurityHelper.getLoginUser().hasRole("KFWL");
	}
	
}
