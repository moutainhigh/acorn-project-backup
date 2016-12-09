package com.chinadrtv.jingdong.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.jingdong.model.JingdongOrderConfig;
import com.chinadrtv.jingdong.service.OrderImportJDService;

/**
 * 
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
 * @since 2014-12-1 上午10:51:06 
 *
 */
@Controller
@RequestMapping({ "/api" })
public class ImportController {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ImportController.class);

	private AtomicBoolean isRun = new AtomicBoolean(false);

	@Autowired
	private OrderImportJDService orderImportJDService;
	
	private List<JingdongOrderConfig> cfgList;
	
	private List<JingdongOrderConfig> fbpList;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		cfgList = (List<JingdongOrderConfig>) applicationContext.getBean("cfgList");
		fbpList = (List<JingdongOrderConfig>) applicationContext.getBean("fbpList");
	}

	@RequestMapping(value = "/import", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> input(Date beginDate, Date endDate) {
		Map<String, Object> rsMap = new HashMap<String, Object>();
		boolean success = false;
		String message = "";
		
		if (isRun.compareAndSet(false, true)) {
			logger.info("begin order import");

			try {
				orderImportJDService.importPreOrdersInfo(cfgList, beginDate, endDate);

				// fetch FBP orders
				orderImportJDService.importFBPOrder(fbpList, beginDate, endDate);

				success = true; 
			} catch (Exception exp) {
				logger.error("import jingdong order error:", exp);
				message = exp.getMessage();
			} finally {
				isRun.set(false);
				logger.info("end import");
			}
		} else {
			logger.error("import is running!!!");
		}
		
		rsMap.put("success", success);
		rsMap.put("message", message);

		return rsMap;
	}
}
