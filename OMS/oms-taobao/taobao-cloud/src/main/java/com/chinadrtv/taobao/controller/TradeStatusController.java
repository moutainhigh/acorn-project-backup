package com.chinadrtv.taobao.controller;

import com.chinadrtv.taobao.common.dal.model.TradeFeedback;
import com.chinadrtv.taobao.model.TaobaoOrderConfig;
import com.chinadrtv.taobao.model.TradeStatusModel;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.LogisticsOfflineSendRequest;
import com.taobao.api.request.TradeFullinfoGetRequest;
import com.taobao.api.response.LogisticsOfflineSendResponse;
import com.taobao.api.response.TradeFullinfoGetResponse;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 14-3-7
 * Time: 下午1:14
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class TradeStatusController {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(TradeStatusController.class);

    @RequestMapping(value = "/updateTradeStatus", method = RequestMethod.POST, consumes="application/json",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updateTradeStatus(@RequestBody TradeStatusModel tradeStatusModel) {
        logger.info("更新交易状态 ,updateTradeStatus");

        TaobaoOrderConfig taobaoOrderConfig = tradeStatusModel.getTaobaoOrderConfig();
        TradeFeedback tradefeedback = tradeStatusModel.getTradefeedback();

        long tid = Long.parseLong(tradefeedback.getTradeId());
        TaobaoClient client = new DefaultTaobaoClient(taobaoOrderConfig.getUrl(), taobaoOrderConfig.getAppkey(), taobaoOrderConfig.getAppSecret());

        TradeFullinfoGetRequest req = new TradeFullinfoGetRequest();
        req.setFields("status");
        req.setTid(tid);
        try{
            TradeFullinfoGetResponse response = client.execute(req, taobaoOrderConfig.getSessionKey());
            if (!response.isSuccess()) {
                logger.error("session key:"+taobaoOrderConfig.getSessionKey());
                logger.error("get status error:"+response.getErrorCode()+" - "+ response.getMsg());
                logger.error("sub msg:"+response.getSubCode()+" - "+response.getSubMsg());
                return response.getErrorCode() + "-" + response.getMsg();
            } else {
                String status = response.getTrade().getStatus();
                if ("WAIT_BUYER_CONFIRM_GOODS".equals(status) || "TRADE_BUYER_SIGNED".equals(status)
                        || "TRADE_FINISHED".equals(status) || "TRADE_CLOSED".equals(status)
                        || "TRADE_CLOSED_BY_TAOBAO".equals(status) || "ALL_WAIT_PAY".equals(status)
                        || "ALL_CLOSED".equals(status)) {
                    //b = true;
                } else {
                    LogisticsOfflineSendRequest req2 = new LogisticsOfflineSendRequest();
                    req2.setTid(tid);
                    req2.setOutSid(tradefeedback.getMailId());
                    req2.setCompanyCode(tradefeedback.getCompanyCode());
                    LogisticsOfflineSendResponse response2 = client.execute(req2, taobaoOrderConfig.getSessionKey());
                    if (response2.isSuccess()) {
                        //b = true;
                    } else {
                        logger.error("feed back orderid:"+tradefeedback.getTradeId());
                        String strError= response2.getErrorCode() + "/" + response2.getMsg()
                                + "," + response2.getSubCode() + "/" + response2.getSubMsg();
                        logger.error("feed back error:"+ strError);
                        return strError;
                        //throw new RuntimeException(strError);
                    }
                }
            }
        }catch (Exception exp)
        {
            logger.error("feed back system error:",exp);
            return exp.getMessage();
            //throw new RuntimeException(exp.getMessage());
        }
        return "";
    }
}
