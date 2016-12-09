package com.chinadrtv.yhd.biz.jms;

import com.chinadrtv.runtime.jms.receive.JmsListener;
import com.chinadrtv.yhd.model.YhdOrderConfig;
import com.chinadrtv.yhd.service.YhdOrderInputService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
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
public class OrderImportTopicListener extends JmsListener {
    public OrderImportTopicListener()
    {
        logger.info("OrderImportTopicListener is created.");
    }
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderImportTopicListener.class);
    private AtomicBoolean isRun=new AtomicBoolean(false);

    @Autowired
    private YhdOrderInputService yhdOrderInputService;

    private List<YhdOrderConfig> yhdOrderConfigList;

    public List<YhdOrderConfig> getYhdOrderConfigList() {
        return yhdOrderConfigList;
    }

    public void setYhdOrderConfigList(List<YhdOrderConfig> yhdOrderConfigList) {
        this.yhdOrderConfigList = yhdOrderConfigList;
    }

    @Override
    public void messageHandler(Object msg) throws Exception {
        //如果有在处理，那么直接忽略此消息
        if(isRun.compareAndSet(false,true))
        {
            try
            {
                logger.info("timing begin import");
                //获取yihaodian的配置信息
                Date startDate,endDate;
                endDate = new Date();
                startDate = getAddDay(endDate,Calendar.DATE,-1);

                yhdOrderInputService.input(yhdOrderConfigList,startDate,endDate);
            }catch (Exception exp)
            {
                logger.error("timing import yihaodian order error:", exp);
            }
            finally {
                isRun.set(false);
                logger.info("timing end import");
            }
        }else {
            logger.error("import is running!!!");
        }

    }
    /**
     * 转换当前日期的方法
     * @param d      当前日期
     * @param field  参数类型
     * @param amount 参数区间
     * @return
     */
    public static Date getAddDay(Date d, int field, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(field, amount);
        return cal.getTime();
    }
}
