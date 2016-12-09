package com.chinadrtv.erp.sales.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chinadrtv.erp.core.service.NamesService;
import com.chinadrtv.erp.marketing.core.util.HttpUtil;
import com.chinadrtv.erp.model.Names;
import com.chinadrtv.erp.model.agent.Conpointcr;
import com.chinadrtv.erp.model.agent.Conpointuse;
import com.chinadrtv.erp.model.agent.CountyAll;
import com.chinadrtv.erp.uc.dto.CustomerBaseSearchDto;
import com.chinadrtv.erp.uc.service.ConpointcrService;
import com.chinadrtv.erp.uc.service.ConpointuseService;
import com.chinadrtv.erp.uc.service.ContactService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 用户积分 
 * @author haoleitao
 *
 */
@Controller
@RequestMapping(value="/integral")
public class IntegralController extends BaseController {
	
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(IntegralController.class);
    @Autowired
    private ConpointcrService conpointcrService;
    @Autowired
    private ConpointuseService conpointuseService;
    @Autowired
    private ContactService contactService;
    @Autowired
    private NamesService namesService;
    
//    @RequestMapping(value="/integral", method = RequestMethod.GET)
//    public String integral() throws Exception {
//    	
//        return "integral/integral";
//    }
	
   //查询用户获取积分
    
    @RequestMapping(value="/getIntegral", method = RequestMethod.POST)
    public void getIntegral(@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam Integer rows,
			@RequestParam(required = false, defaultValue = "") String name, //客户姓名
			@RequestParam(required = false, defaultValue = "") String customerId, //客户编号
			HttpServletRequest request, HttpServletResponse response
			) throws Exception {
    	List<Conpointcr> list=conpointcrService.getAllConpointcrByContactId(customerId, page-1, rows);
    	int count=conpointcrService.getAllConpointcrByContactIdCount(customerId);
    	 Map<String, Object> map = new HashMap<String, Object>();
    	map.put("total", count);
    	map.put("rows", list);
    	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    	HttpUtil.ajaxReturn(response, gson.toJson(map), "application/json");
    }
    
    
	/**
	 * 获取用户可用积分
	 * @param name
	 * @param customerId
	 * @param request
	 * @param response
	 * @throws Exception
	 */
    @RequestMapping(value="/getAvailableIntegral", method = RequestMethod.POST)
    public void getAvailableIntegral(
			@RequestParam(required = false, defaultValue = "") String name, //客户姓名
			@RequestParam(required = false, defaultValue = "") String customerId, //客户编号
			HttpServletRequest request, HttpServletResponse response
			) throws Exception {
    	 Map<String, Object> map = new HashMap<String, Object>();
        Double integral=contactService.findJiFenByContactId(customerId);
        map.put("integral", integral);    	

    	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    	HttpUtil.ajaxReturn(response, gson.toJson(map), "application/json");
    }
    
    
    
	/**
	 * 获取用户可用积分
	 * @param name
	 * @param customerId
	 * @param request
	 * @param response
	 * @throws Exception
	 */
    @RequestMapping(value="/getSubtractionIntegral", method = RequestMethod.POST)
    public void getSubtractionIntegral(
			@RequestParam(required = false, defaultValue = "") String name, //客户姓名
			@RequestParam(required = false, defaultValue = "") String customerId, //客户编号
			HttpServletRequest request, HttpServletResponse response
			) throws Exception {
    	 Map<String, Object> map = new HashMap<String, Object>();
        Double integral=conpointuseService.getUseIntegralByContactId(customerId);
        map.put("integral", integral);    	

    	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    	HttpUtil.ajaxReturn(response, gson.toJson(map), "application/json");
    }
    
    
	
    //查询用户使用积分
    @RequestMapping(value="/useIntegral", method = RequestMethod.POST)
    public void useIntegral(@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam Integer rows,
			@RequestParam(required = false, defaultValue = "") String name, //客户姓名
			@RequestParam(required = false, defaultValue = "") String customerId, //客户编号
			HttpServletRequest request, HttpServletResponse response) throws Exception {
   	 Map<String, Object> map = new HashMap<String, Object>();
    	List<Conpointuse> list=conpointuseService.getAllConpointuseByContactId(customerId, page-1, rows);
    	int count=conpointuseService.getAllConpointuseByContactIdCount(customerId);
    	List<Names> listNames=namesService.getAllNames("ORDERFEEDBACK");
    	
    	map.put("total", count);
    	map.put("rows", list);
    	map.put("names", listNames);
    	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    	HttpUtil.ajaxReturn(response, gson.toJson(map), "application/json");
    }
    
    
	

}
