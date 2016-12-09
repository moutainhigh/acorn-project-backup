package com.chinadrtv.jingdong.service.impl;

import com.chinadrtv.jingdong.common.dal.dao.TradeFeedbackDao;
import com.chinadrtv.jingdong.common.dal.model.TradeFeedback;
import com.chinadrtv.jingdong.model.JingdongOrderConfig;
import com.chinadrtv.jingdong.service.OrderFeedbackJDService;
import com.chinadrtv.model.oms.PreTrade;
import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.request.order.OrderSopOutstorageRequest;
import com.jd.open.api.sdk.response.order.OrderSopOutstorageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-11-11
 * Time: 下午1:28
 * To change this template use File | Settings | File Templates.
 */
@Service("OrderFeedbackJDService")
public class OrderFeedbackJDServiceImpl implements OrderFeedbackJDService {
    @Autowired
    private TradeFeedbackDao tradeFeedbackDao;

    /**
     * 获取指定订单类型信息
     * @param orderType
     * @return
     */
    public List<TradeFeedback> searchOrderByType(String orderType) {
        return tradeFeedbackDao.findFeedbacks(orderType);
    }

    /**
     * 回写数据状态
     * @param preTrade
     * @return
     */
    public boolean updateOrderFeedbackStatus(PreTrade preTrade) {
        boolean b = false;
        int result = tradeFeedbackDao.updateOrderFeedbackStatus(preTrade);
        if(result > 0){
            b = true;
        }
        return b;
    }

    /**
     * 反馈发货信息到JD
     * @param tradefeedback
     * @param jdorderconfig
     * @return
     * @throws Exception
     */
    public boolean updateTradeStatus(TradeFeedback tradefeedback,JingdongOrderConfig jdorderconfig) throws Exception {
        boolean b = false;

            JdClient client = new DefaultJdClient(jdorderconfig.getUrl(), jdorderconfig.getSessionKey(), jdorderconfig.getAppkey(), jdorderconfig.getAppSecret());
            OrderSopOutstorageRequest request = new OrderSopOutstorageRequest();
            request.setOrderId(tradefeedback.getTradeId());
            request.setLogisticsId(tradefeedback.getCompanyCode());
            request.setWaybill(tradefeedback.getMailId());
            request.setTradeNo(tradefeedback.getOrderId());
            OrderSopOutstorageResponse response = client.execute(request);
            if (!"0".equals(response.getCode())) {
                String errMsg = response.getZhDesc();
                if (errMsg.endsWith("订单已出库")) b = true;
                else
                    throw new Exception(response.getCode() + "/"
                            + response.getZhDesc());
            } else {
                b = true;
            }
        return b;
    }
}
