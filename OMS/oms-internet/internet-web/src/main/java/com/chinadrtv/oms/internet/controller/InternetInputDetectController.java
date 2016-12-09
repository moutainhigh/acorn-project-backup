package com.chinadrtv.oms.internet.controller;

import com.chinadrtv.service.iagent.DetectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created with IntelliJ IDEA. User: liukuan Date: 13-12-27 Time: 下午5:02 To
 * change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping({ "/internet" })
public class InternetInputDetectController {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(InternetInputDetectController.class);
	@Autowired
	private DetectService detectService;

	@RequestMapping(value = "/input/detect", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String testFeedback() throws Exception {
		logger.info("begin test connect.......");
		try {
			int temp = detectService.omsTestConnectDate();
			logger.info("search result:" + temp);
		} catch (Exception exp) {
			logger.error("test error:", exp);
			throw exp;
		}
		logger.info("end test connect....");
		return "ok";
	}
}
