package com.chinadrtv.yhd.biz.jms;

import com.chinadrtv.runtime.jms.receive.JmsListener;
import com.chinadrtv.yhd.model.YhdOrderConfig;
import com.chinadrtv.yhd.service.YhdFeedbackService;
import com.chinadrtv.yhd.service.YhdOrderInputService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created with (oms).
 * User: liukuan
 * Date: 14-03-20
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class OrderFeedbackTopicListener extends JmsListener {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderFeedbackTopicListener.class);

    public OrderFeedbackTopicListener()
    {
        logger.info("OrderFeedbackTopicListener is created.");
    }
    private AtomicBoolean isRun=new AtomicBoolean(false);

    @Autowired
    private YhdFeedbackService yhdFeedbackService;

    private List<YhdOrderConfig> yhdOrderConfigList;

    public List<YhdOrderConfig> getYhdOrderConfigList() {
        return yhdOrderConfigList;
    }

    public void setYhdOrderConfigList(List<YhdOrderConfig> yhdOrderConfigList) {
        this.yhdOrderConfigList = yhdOrderConfigList;
    }

    @Override
    public void messageHandler(Object msg) throws Exception {
        if(isRun.compareAndSet(false,true)){
            try
            {
                logger.info("timing begin feedback");
                if(this.yhdOrderConfigList == null){
                    logger.error("no yihaodian config");
                }else {
                    yhdFeedbackService.orderFeedback(yhdOrderConfigList);
                }

            }catch (Exception exp)
            {
                logger.error("timing feedback yihaodian order error:", exp);
            }
            finally {
                isRun.set(false);
                logger.info("OrderFeedbackTopicListener end feed back");
            }
        }else
        {
            logger.error("feed back is running!!!");
        }

    }
}
