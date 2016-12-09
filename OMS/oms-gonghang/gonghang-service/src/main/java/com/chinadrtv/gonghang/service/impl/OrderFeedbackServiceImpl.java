package com.chinadrtv.gonghang.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.transform.stream.StreamSource;

import org.milyn.Smooks;
import org.milyn.container.ExecutionContext;
import org.milyn.payload.JavaResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;

import com.chinadrtv.gonghang.dal.dao.TradeFeedbackDao;
import com.chinadrtv.gonghang.dal.dto.ResponseHeaderDto;
import com.chinadrtv.gonghang.dal.model.OrderConfig;
import com.chinadrtv.gonghang.dal.model.TradeFeedback;
import com.chinadrtv.gonghang.service.OrderFeedbackService;
import com.chinadrtv.gonghang.util.EncryptUtil;
import com.chinadrtv.model.oms.PreTrade;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-5
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class OrderFeedbackServiceImpl implements OrderFeedbackService {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderFeedbackServiceImpl.class);
    
    @Autowired
    private TradeFeedbackDao tradeFeedbackDao;

	private Smooks smooks = null;
	@Autowired
    private RestTemplate restTemplate;
	
	private SimpleDateFormat sdfTimestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public OrderFeedbackServiceImpl() throws IOException, SAXException {
		super();
		smooks = new Smooks("smooks/smooks-config.xml");
	}
    

	@Override
	public void feedback(List<OrderConfig> configList) throws Exception {
		for (OrderConfig config : configList) {
			List<TradeFeedback> tradeFeedbackList = tradeFeedbackDao.findFeedbacks(config.getTradeType());
			if (tradeFeedbackList != null && tradeFeedbackList.size() > 0) {
				this.feedbackList(config, tradeFeedbackList);
			}
		}
	}

	private void feedbackList(OrderConfig config, List<TradeFeedback> tradeFeedbackList) throws Exception {
		
		for (TradeFeedback tradeFeedback : tradeFeedbackList) {
			boolean b = false;
			String errMsg = "";

			b = this.postFeedback(config, tradeFeedback);

			PreTrade preTrade = new PreTrade();
			preTrade.setTradeId(tradeFeedback.getTradeId());
			preTrade.setOpsTradeId(tradeFeedback.getTradeId());
			preTrade.setFeedbackStatus(b ? "2" : "4");
			if (b) {
				errMsg = "反馈成功";
			} else {
				errMsg = "反馈失败，原因：" + ((errMsg == null) ? "未知错误" : errMsg);
			}

			preTrade.setFeedbackStatusRemark(errMsg);
			preTrade.setFeedbackUser("order-feedback-gonghang");
			preTrade.setFeedbackDate(new Date());

			tradeFeedbackDao.updateOrderFeedbackStatus(preTrade);
		}
	}

    /**
	 * <p></p>
	 * @param tradeFeedback
	 * @return boolean
     * @throws Exception 
	 */
	private boolean postFeedback(OrderConfig config, TradeFeedback tradeFeedback) throws Exception {
		
		String method = "icbcb2c.order.sendmess";
		String reqData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
						 "<body>" +
						 "<order_id>" + tradeFeedback.getTradeId() + "</order_id>" +
						 "<logistics_company>" + tradeFeedback.getCompanyCode() + "</logistics_company>" +
						 "<shipping_code>" + tradeFeedback.getMailId() + "</shipping_code>" +
						 "<shipping_time>" + sdfTimestamp.format(new Date()) + "</shipping_time>" +
						 "<shipping_user>ACORN</shipping_user>" +
						 "</body>";
		
		String requestUrl = EncryptUtil.generateRequestUrl(config, method, reqData);
		
		logger.debug("post url : " + requestUrl);
		
		ResponseEntity<String> response = null;
		try {
			response = restTemplate.postForEntity(requestUrl, new Object(), String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(null ==response || response.getStatusCode().value() != 200) {
    		throw new Exception("post ICBC web service error!");
    	}
		
		String content = new String(response.getBody().getBytes("ISO-8859-1"), "UTF-8");
		
		logger.debug("post content: " + content);
		
		ExecutionContext executionContext = smooks.createExecutionContext();
		JavaResult result = new JavaResult();

		ByteArrayInputStream baInputStream = new ByteArrayInputStream(content.getBytes());
		smooks.filterSource(executionContext, new StreamSource(baInputStream), result);

		ResponseHeaderDto responseHeader = (ResponseHeaderDto) result.getBean("responseHeader");
		if(null == responseHeader || null == responseHeader.getRetMsg() || !"OK".equalsIgnoreCase(responseHeader.getRetMsg())) {
			throw new Exception("更新失败");
		}
		
		return true;
	}
}
