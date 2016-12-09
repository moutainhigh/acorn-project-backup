package com.chinadrtv.amazon.biz.impl;

import com.chinadrtv.amazon.biz.OrderBizHandler;
import com.chinadrtv.amazon.common.dal.model.TradeFeedback;
import com.chinadrtv.amazon.model.AmazonOrderConfig;
import com.chinadrtv.amazon.model.TradeResultInfo;
import com.chinadrtv.amazon.model.TradeResultList;
import com.chinadrtv.amazon.service.OrderFeedbackFetchService;
import com.chinadrtv.amazon.service.OrderFeedbackService;
import com.chinadrtv.amazon.service.OrderImportService;
import com.chinadrtv.model.oms.PreTrade;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

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
public class OrderBizHandlerImpl implements OrderBizHandler {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderBizHandlerImpl.class);

    public OrderBizHandlerImpl()
    {
        logger.info("OrderBizHandlerImpl is created!");
    }

    private AtomicBoolean isRun_import=new AtomicBoolean(false);
    private AtomicBoolean isRun_feedback=new AtomicBoolean(false);

    @Autowired
    private OrderImportService orderImportService;

    @Override
    public boolean input(List<AmazonOrderConfig> amazonOrderConfigList, Date startDate, Date endDate) {
        //如果有在处理，那么直接忽略此消息
        if(isRun_import.compareAndSet(false,true))
        {
            logger.info("begin order import");
            //定时消息处理
            try
            {
                orderImportService.importOrders(amazonOrderConfigList, startDate, endDate);
            }catch (Exception exp)
            {
                logger.error("import amazon order error:", exp);
                throw new RuntimeException(exp.getMessage());
            }
            finally {
                isRun_import.set(false);
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

    @Autowired
    private OrderFeedbackFetchService orderFeedbackFetchService;

    @Autowired
    private OrderFeedbackService orderFeedbackService;

    @Override
    public boolean feedback(List<AmazonOrderConfig> amazonOrderConfigList) {
        if(isRun_feedback.compareAndSet(false,true))
        {
            logger.info("begin feed back");

            try
            {
                if(amazonOrderConfigList==null)
                {
                    logger.error("no amazon config");
                }
                else
                {
                    for(AmazonOrderConfig amazonOrderConfig:amazonOrderConfigList)
                    {
                        if(!StringUtils.isBlank(amazonOrderConfig.getFeedbackUrl()))
                        {
                            logger.info("begin feedback ordertype:"+amazonOrderConfig.getTradeType());
                            List<TradeFeedback> tradeFeedbackList = orderFeedbackFetchService.searchOrderByType(amazonOrderConfig.getTradeType());
                            if(tradeFeedbackList!=null&&tradeFeedbackList.size()>0)
                            {
                                this.feedbackList(amazonOrderConfig,tradeFeedbackList);
                            }
                            logger.info("end feedback ordertype:"+amazonOrderConfig.getTradeType());

                            logger.info("begin update feedback result");
                            List<String> list=orderFeedbackFetchService.seachResultByType(amazonOrderConfig.getTradeType());
                            if(list!=null&&list.size()>0)
                            {
                                this.feedbackResult(amazonOrderConfig,list);
                            }
                            logger.info("end update feedback result");
                        }
                    }
                }
            } catch (Exception exp)
            {
                logger.error("feed back error:",exp);
                throw new RuntimeException(exp.getMessage());
            }
            finally {
                isRun_feedback.set(false);
                logger.info("end feed back");
            }

            return true;
        }
        else
        {
            logger.error("feed back is running!!!");
            return false;
        }
    }

    private void feedbackList(AmazonOrderConfig amazonOrderConfig, List<TradeFeedback> tradeFeedbackList)
    {
        boolean b=false;
        String errMsg="";
        StringBuffer feedSubmissionId = new StringBuffer();
        try{
            b=orderFeedbackService.updateTradeStatus(amazonOrderConfig,tradeFeedbackList,feedSubmissionId);
        }catch (Exception exp)
        {
            errMsg=exp.getMessage();
            logger.error("feed back error", exp);
        }

        int i = 1;
        for (TradeFeedback tradeFeedback:tradeFeedbackList)
        {
            PreTrade preTrade = new PreTrade();
            preTrade.setTradeId(tradeFeedback.getTradeId());
            preTrade.setOpsTradeId(tradeFeedback.getTradeId());
            preTrade.setFeedbackStatus(b ? "3" : "4");
            if (b) errMsg = "提交成功";
            else errMsg = "提交失败，原因：" + ((errMsg == null) ? "未知错误" : errMsg);
            preTrade.setFeedbackStatusRemark(errMsg);
            preTrade.setFeedbackUser("esb-order-feedback-amazon");
            preTrade.setFeedbackDate(new Date());
            preTrade.setFeedbackSubmissionId(feedSubmissionId.toString());
            preTrade.setFeedbackMessageId(Integer.toString(i));
            i++;

            orderFeedbackFetchService.updateOrderFeedbackStatus(preTrade);
        }
    }

    private void feedbackResult(AmazonOrderConfig amazonOrderConfig, List<String> feedbackList)
    {
        for (String feedSubmissionId : feedbackList)
        {
            logger.info("begin feed back result:"+feedSubmissionId);
            TradeResultList tradeResultList=orderFeedbackService.getTradeResultInfo(amazonOrderConfig,feedSubmissionId);
            logger.info("fetch feed back result:"+feedSubmissionId);
            if (tradeResultList != null && "Complete".equals(tradeResultList.getProcessStatus())) {
                if (tradeResultList.getTradeResultInfos() != null && !tradeResultList.getTradeResultInfos().isEmpty()) {
                    for (TradeResultInfo tradeResultInfo : tradeResultList.getTradeResultInfos()) {
                        //记录反馈状态
                        PreTrade preTrade = new PreTrade();
                        preTrade.setFeedbackStatus("4");
                        String errMsg = "提交失败，原因：" + ((tradeResultInfo.getErrorDescription() == null) ? "未知错误" : tradeResultInfo.getErrorDescription());
                        preTrade.setFeedbackStatusRemark(errMsg);
                        preTrade.setFeedbackUser("esb-order-feedback-amazon");
                        preTrade.setFeedbackDate(new Date());
                        preTrade.setFeedbackSubmissionId(feedSubmissionId);
                        preTrade.setFeedbackMessageId(tradeResultInfo.getMessageId());

                        orderFeedbackFetchService.updateOrderFeedbackResultStatus(preTrade);
                        //amazonTradeFeedbackService.updateOrderFeedbackResultStatus(preTrade);
                    }
                }
                //更新成功订单列表
                PreTrade preTrade = new PreTrade();
                preTrade.setFeedbackStatus("2");
                preTrade.setFeedbackStatusRemark("反馈成功");
                preTrade.setFeedbackUser("esb-order-feedback-amazon");
                preTrade.setFeedbackDate(new Date());
                preTrade.setFeedbackSubmissionId(feedSubmissionId);
                orderFeedbackFetchService.updateOrderFeedbackResultSuccess(preTrade);
            }
        }
    }
}
