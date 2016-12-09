package com.chinadrtv.taobao.biz.impl;

import com.chinadrtv.model.oms.PreTrade;
import com.chinadrtv.taobao.biz.OrderBizHandler;
import com.chinadrtv.taobao.common.dal.model.TradeFeedback;
import com.chinadrtv.taobao.model.TaobaoOrderConfig;
import com.chinadrtv.taobao.service.OrderFeedbackFetchService;
import com.chinadrtv.taobao.service.OrderFeedbackService;
import com.chinadrtv.taobao.service.OrderImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-18
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class OrderBizHandlerImpl implements OrderBizHandler {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderBizHandlerImpl.class);

    private AtomicBoolean isRun=new AtomicBoolean(false);
    private AtomicBoolean isRun_feedback=new AtomicBoolean(false);
    
    @Autowired
    private OrderFeedbackFetchService orderFeedbackFetchService;
    @Autowired
    private OrderFeedbackService orderFeedbackService;
    @Autowired
    private OrderImportService orderImportService;

	public OrderBizHandlerImpl() {
		logger.info("OrderImportHandlerImpl is created!");
	}
    
    @Override
    public boolean input(List<TaobaoOrderConfig> taobaoOrderConfigList, Date startDate, Date endDate) {
        //如果有在处理，那么直接忽略此消息
        if(isRun.compareAndSet(false,true))
        {
            logger.info("begin order import");
            //定时消息处理
            try
            {
                orderImportService.importOrders(taobaoOrderConfigList, startDate, endDate);
            }catch (Exception exp)
            {
                logger.error("import taobao order error:", exp);
                throw new RuntimeException(exp.getMessage());
            }
            finally {
                isRun.set(false);
                logger.info("end import");
            }
            return true;
        }
        else
        {
            logger.error("import is running!!!");
            return false;
        }
    }


	@Override
	public boolean feedback(List<TaobaoOrderConfig> taobaoOrderConfigList) {
		if (isRun_feedback.compareAndSet(false, true)) {
			logger.info("begin feed back");
			try {
				if (taobaoOrderConfigList == null) {
					logger.error("no taobao config");
				} else {
					for (TaobaoOrderConfig taobaoOrderConfig : taobaoOrderConfigList) {
						
						List<TradeFeedback> tradeFeedbackList = orderFeedbackFetchService.searchOrderByType(taobaoOrderConfig.getTradeType());
						if (tradeFeedbackList != null && tradeFeedbackList.size() > 0) {
							this.feedbackList(taobaoOrderConfig, tradeFeedbackList);
						}
						
						//处理冷链订单265类型订单
						if(taobaoOrderConfig.getTradeType().equals("259")) {
							List<TradeFeedback> rawFoodtradeFeedbackList = orderFeedbackFetchService.searchOrderByType("265");
							if (rawFoodtradeFeedbackList != null && rawFoodtradeFeedbackList.size() > 0) {
								this.feedbackList(taobaoOrderConfig, rawFoodtradeFeedbackList);
							}
						}
					}
				}
			} catch (Exception exp) {
				logger.error("feed back error:", exp);
				throw new RuntimeException(exp.getMessage());
			} finally {
				isRun_feedback.set(false);
				logger.info("end feed back");
			}

			return true;
		} else {
			logger.error("feed back is running!!!");
			return false;
		}
	}

	private void feedbackList(TaobaoOrderConfig taobaoOrderConfig, List<TradeFeedback> tradeFeedbackList) {
		for (TradeFeedback tradeFeedback : tradeFeedbackList) {
			boolean b = false;
			String errMsg = "";
			try {
				errMsg = orderFeedbackService.updateTradeStatus(taobaoOrderConfig, tradeFeedback);
				if (StringUtils.isEmpty(errMsg)) {
					b = true;
				}
			} catch (Exception expInner) {
				errMsg = expInner.getMessage();
				logger.error("feed back error trade id:" + tradeFeedback.getTradeId(), expInner);
			}

			PreTrade preTrade = new PreTrade();
			preTrade.setTradeId(tradeFeedback.getTradeId());
			preTrade.setOpsTradeId(tradeFeedback.getTradeId());
			preTrade.setFeedbackStatus(b ? "2" : "4");
			if (b)
				errMsg = "反馈成功";
			else
				errMsg = "反馈失败，原因：" + ((errMsg == null) ? "未知错误" : errMsg);
			preTrade.setFeedbackStatusRemark(errMsg);
			preTrade.setFeedbackUser("esb-order-feedback-taobao");
			preTrade.setFeedbackDate(new Date());

			orderFeedbackFetchService.updateOrderFeedbackStatus(preTrade);
		}
	}
}
