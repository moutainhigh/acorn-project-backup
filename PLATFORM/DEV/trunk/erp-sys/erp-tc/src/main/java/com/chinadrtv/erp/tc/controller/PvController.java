package com.chinadrtv.erp.tc.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.erp.tc.core.model.OrderReturnCode;
import com.chinadrtv.erp.tc.core.service.PvService;

/**
 * Author :taoyawei 
 * Updated By WangJian 
 * Updated On March 29, 2013
 */
@Controller
@RequestMapping({ "/jifen" })
public class PvController extends TCBaseController {
	private static final Logger logger = LoggerFactory.getLogger(PvController.class);

	@Autowired
	private PvService pvService;

	@RequestMapping(value = "/revise", method = RequestMethod.POST, headers = "Content-Type=application/json")
	@ResponseBody
	public OrderReturnCode getJifenRevisebyProc(@RequestBody Map<String, Object> mapval) {
		try {
			logger.debug("start get JifenEnd");
			if (mapval.size() == 2) {
				pvService.getJifenproc(mapval);
				logger.debug("call order getJifenproc service");
				return new OrderReturnCode("000", "积分校正成功");
			} else {
				return new OrderReturnCode("100", "积分校正参数不对");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new OrderReturnCode("100", "积分校正错误:" + e.getMessage());
		}
	}

	/**
	 * 积分新增
	 * 
	 * @Description:
	 * @param mapval
	 * @return
	 * @return OrderReturnCode
	 * @throws
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST, headers = "Content-Type=application/json")
	@ResponseBody
	public OrderReturnCode getJifenbyProc(@RequestBody Map<String, Object> params) {
		try {
			if (params.size() == 5) {
				
				//String str = orderIntegarlService.getIntegarlproc(params);
				String str = pvService.addPvByOrder(params);
				logger.debug("Call order PointValue service");
				
				if (!str.equals("")) {
					return new OrderReturnCode("000", "积分新增成功");
				} else {
					return new OrderReturnCode("100", "积分新增失败");
				}
			} else {
				return new OrderReturnCode("100", "积分新增参数不对");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("新增积分失败", e);
			return new OrderReturnCode("100", "积分新增失败");
		}

	}
}