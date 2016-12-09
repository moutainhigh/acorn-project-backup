package com.chinadrtv.oms.internet.biz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.chinadrtv.model.oms.PreTrade;
import com.chinadrtv.oms.internet.dal.model.TradeFeedback;
import com.chinadrtv.oms.internet.service.FeedbackService;
import com.chinadrtv.runtime.jms.receive.JmsListener;

/**
 * Created with IntelliJ IDEA. User: liukuan Date: 13-11-6 Time: 下午1:38 To
 * change this template use File | Settings | File Templates.
 */
public class FeedbackTopicListener extends JmsListener<Object> {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(FeedbackTopicListener.class);

	private AtomicBoolean isRun = new AtomicBoolean(false);

	@Value("${url}")
	private String url;
	
	@Value("${orderType}")
	private String orderType;
	
	@Autowired
	private FeedbackService feedbackService;

	public FeedbackTopicListener() {
		logger.info("Internet FeedbackTopicListener created");
	}
	
	
	@Override
	public void messageHandler(Object msg) throws Exception {
		List<TradeFeedback> tradeFeedbackList = null;
		boolean flag = false;
		String errMsg = "";
		PreTrade preTrade = null;

		if (isRun.compareAndSet(false, true)) {
			
			logger.info("url: " + url + "\t orderType:" + orderType);
			
			try {
				List<String> orderTypeList = this.getOrderType(orderType);
				
				if (orderTypeList.size() > 0) {
					tradeFeedbackList = feedbackService.searchOrderByType(orderTypeList);
				}
				
				// 反馈
				if (tradeFeedbackList != null && tradeFeedbackList.size() > 0) {
					for (TradeFeedback tradeFeedback : tradeFeedbackList) {
						flag = false;
						errMsg = "";
						
						logger.info("ops_trade_id: " + tradeFeedback.getOpsTradeId());
						
						try {
							flag = feedbackService.updateTradeStatus(url, tradeFeedback);
						} catch (Exception exp) {
							errMsg = exp.getMessage();
							logger.error("feedback exception: " + exp.getMessage());
						}
						
						logger.info("feedback result:" + flag);
						
						// 回写数据库
						preTrade = new PreTrade();
						preTrade.setTradeId(tradeFeedback.getOpsTradeId());
						preTrade.setOpsTradeId(tradeFeedback.getOpsTradeId());
						preTrade.setFeedbackStatus(flag ? "2" : "4");
						if (flag) {
							errMsg = "反馈成功";
						} else {
							errMsg = "反馈失败: " + errMsg;
						}
						preTrade.setFeedbackStatusRemark(errMsg);
						preTrade.setFeedbackUser("internet-order-feedback");
						preTrade.setFeedbackDate(new Date());
						
						boolean updateResult = feedbackService.updateOrderFeedbackStatus(preTrade);
						logger.info("update Pretrade result:" + updateResult);
					}
				}
			} catch (Exception e) {
				logger.error("feed back error:", e);
			} finally {
				isRun.set(false);
				logger.info("end feed back!");
			}
		} else {
			logger.error("feed back is running!");
		}
	}

	/**
	 * <p>获取订单类型</p>
	 * @param str
	 * @return
	 */
	private List<String> getOrderType(String str) {
		if (!("").equals(str) && str != null) {
			String[] arrayStr = str.split(",");
			List<String> orderType = new ArrayList<String>();
			for (String s : arrayStr) {
				orderType.add(s);
			}
			return orderType;
		}
		return null;
	}
}
