package com.chinadrtv.jingdong.biz.jms;

import com.chinadrtv.jingdong.common.dal.model.TradeFeedback;
import com.chinadrtv.jingdong.model.JingdongOrderConfig;
import com.chinadrtv.jingdong.service.OrderFeedbackJDService;
import com.chinadrtv.model.oms.PreTrade;
import com.chinadrtv.runtime.jms.receive.JmsListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-11-11
 * Time: 下午1:45
 * To change this template use File | Settings | File Templates.
 */
public class JDOrderFeedbackTopicListener extends JmsListener {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(JDOrderFeedbackTopicListener.class);
    public JDOrderFeedbackTopicListener()
    {
        logger.info("JDOrderFeedbackTopicListener is created.");
    }
    private AtomicBoolean isRun=new AtomicBoolean(false);

    @Autowired
    private OrderFeedbackJDService orderFeedbackJDService;

    private List<JingdongOrderConfig> jingdongOrderConfigList;

    public List<JingdongOrderConfig> getJingdongOrderConfigList() {
        return jingdongOrderConfigList;
    }

    public void setJingdongOrderConfigList(List<JingdongOrderConfig> jingdongOrderConfigList) {
        this.jingdongOrderConfigList = jingdongOrderConfigList;
    }

    @Override
    public void messageHandler(Object msg) throws Exception {
        if(isRun.compareAndSet(false,true))
        {
            List<TradeFeedback> tradeList;
            boolean b = false;
            String errMsg = "";
            PreTrade preTrade = null;
            logger.info("com.chinadrtv.jingdong.biz.jms.JDOrderFeedbackTopicListener begin feed back");
            try
            {
                if(this.jingdongOrderConfigList==null)
                {
                    logger.error("no jingdong config");
                }
                else
                {
                    for(JingdongOrderConfig jdConfig:jingdongOrderConfigList) {
                         //获取反馈信息
                        tradeList = orderFeedbackJDService.searchOrderByType(jdConfig.getTradeType());
                        if(tradeList != null && tradeList.size() > 0){
                            for(TradeFeedback tradeFeedback : tradeList){
                                b = false;
                                errMsg = null;
                                try{
                                    b = orderFeedbackJDService.updateTradeStatus(tradeFeedback,jdConfig);
                                }catch (Exception e){
                                    errMsg = e.getMessage();
                                    logger.error("exception:"+e);
                                }
                                //回写数据库
                                preTrade = new PreTrade();
                                preTrade.setTradeId(tradeFeedback.getTradeId());
                                preTrade.setOpsTradeId(tradeFeedback.getTradeId());
                                preTrade.setFeedbackStatus(b ? "2" : "4");
                                if (b) errMsg = "反馈成功";
                                else errMsg = "反馈失败，原因：" + ((errMsg == null) ? "未知错误" : errMsg);
                                preTrade.setFeedbackStatusRemark(errMsg);
                                preTrade.setFeedbackUser("order-feedback-jingdong");
                                preTrade.setFeedbackDate(new Date());
                                orderFeedbackJDService.updateOrderFeedbackStatus(preTrade);
                            }
                        }
                    }
                }
            } catch (Exception exp)
            {
                logger.error("com.chinadrtv.jingdong.biz.jms.JDOrderFeedbackTopicListener feed back error:",exp);
            }
            finally {
                isRun.set(false);
                logger.info("com.chinadrtv.jingdong.biz.jms.JDOrderFeedbackTopicListener end feed back");
            }
        }
        else
        {
            logger.error("feed back is running!!!");
        }
    }
}
