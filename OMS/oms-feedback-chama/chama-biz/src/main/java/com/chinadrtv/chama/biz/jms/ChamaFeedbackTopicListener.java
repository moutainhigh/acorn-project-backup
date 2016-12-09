package com.chinadrtv.chama.biz.jms;

import com.chinadrtv.chama.dal.model.TradeFeedback;
import com.chinadrtv.chama.service.ChamaFeedbackService;
import com.chinadrtv.model.oms.PreTrade;
import com.chinadrtv.runtime.jms.receive.JmsListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-11-6
 * Time: 下午1:38
 * To change this template use File | Settings | File Templates.
 */
public class ChamaFeedbackTopicListener extends JmsListener {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ChamaFeedbackTopicListener.class);
    public ChamaFeedbackTopicListener()
    {
        logger.info("ChamaFeedbackTopicListener is created");
    }
    private AtomicBoolean isRun=new AtomicBoolean(false);

    @Value("${chama_url}")
    private String url;
    @Value("${chama_orderType}")
    private String orderType;

    @Autowired
    private ChamaFeedbackService chamaFeedbackService;

    @Override
    public void messageHandler(Object msg) throws Exception {
        List<TradeFeedback> tradeFeedbackList = null;
        boolean flag = false;
        String errMsg = "";
        PreTrade preTrade = null;

        if(isRun.compareAndSet(false,true)){
            logger.info("begin get search param....");
            logger.info("com.chinadrtv.chama.biz.jms.ChamaFeedbackTopicListener get param url="+url+",orderType="+orderType);
            try{
                //获取茶马订单类型
                List<String> orderTypeList = this.getOrderType(orderType);
                logger.info("orderTypeList size:"+orderTypeList.size());
                if(orderTypeList.size() > 0){
                    tradeFeedbackList = chamaFeedbackService.searchOrderByType(orderTypeList);
                }
                logger.info("get tradeFeedbackList size:"+tradeFeedbackList.size());
                //反馈
                if (tradeFeedbackList != null && tradeFeedbackList.size() > 0) {
                    for (TradeFeedback tradeFeedback : tradeFeedbackList) {
                        flag = false;
                        errMsg = "";
                        try {
                            logger.info("ops_trade_id: " + tradeFeedback.getOpsTradeId());
                            flag = chamaFeedbackService.updateTradeStatus(url, tradeFeedback);
                        } catch (Exception exp) {
                            errMsg = exp.getMessage() ;
                            logger.error("feedback exception: " + exp.getMessage());
                        }
                        logger.info("feedback result:"+flag);
                        //回写数据库
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
                        preTrade.setFeedbackUser("esb-order-feedback-chama");
                        preTrade.setFeedbackDate(new Date());
                        logger.info("update Pretrade begin.....");
                        boolean updateResult = chamaFeedbackService.updateOrderFeedbackStatus(preTrade);
                        logger.info("update Pretrade result:"+updateResult);
                    }
                }
            }catch (Exception e){
                 logger.error("feed back error:",e);
            }finally {
                isRun.set(false);
                logger.info("end feed back!");
            }
        }else {
            logger.error("feed back is running!");
        }
    }
    //获取茶马订单类型
    private List<String> getOrderType(String str){
        if(!("").equals(str) && str != null){
            String[] arrayStr = str.split(",");
            List<String> orderType = new ArrayList<String>();
            for(String s:arrayStr){
                orderType.add(s);
            }
            return orderType;
        }
        return null;
    }
}
