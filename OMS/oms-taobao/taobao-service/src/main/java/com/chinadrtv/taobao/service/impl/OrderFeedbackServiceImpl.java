package com.chinadrtv.taobao.service.impl;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.chinadrtv.dal.oms.dao.PreTradeDetailDao;
import com.chinadrtv.model.oms.PreTradeDetail;
import com.chinadrtv.taobao.common.dal.dao.TradeFeedbackDao;
import com.chinadrtv.taobao.common.dal.model.TradeFeedback;
import com.chinadrtv.taobao.model.TaobaoOrderConfig;
import com.chinadrtv.taobao.model.TradeStatusModel;
import com.chinadrtv.taobao.service.OrderFeedbackService;

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
    private RestTemplate restTemplate;
    
    @Autowired
    private TradeFeedbackDao tradeFeedbackDao;
    
    @Autowired
    private PreTradeDetailDao preTradeDetailDao;
    
    @Value("${taobao_cloud_url}")
    private  String cloudUrl;

    @SuppressWarnings("rawtypes")
	@Override
    public String updateTradeStatus(TaobaoOrderConfig taobaoOrderConfig, TradeFeedback tradefeedback) {
        Long parentTradeId = null;
        //拼接子订单数据
        if(tradefeedback.getTradeId().contains("-")) {
        	String childTradeId = tradefeedback.getTradeId();
        	parentTradeId = Long.parseLong(childTradeId.substring(0, childTradeId.indexOf("-")));
        	
        	List<PreTradeDetail> detailList = preTradeDetailDao.queryDetailByTradeId(childTradeId);
        	StringBuffer sb = new StringBuffer();
        	for(PreTradeDetail ptd : detailList) {
        		sb.append(ptd.getOid() + ",");
        	}
        	String oids = sb.toString();
        	if(oids.endsWith(",")) {
        		oids = oids.substring(0, oids.length());
        	}
        	tradefeedback.setOids(oids);
        }
        TradeStatusModel tradeStatusModel = new TradeStatusModel();
        tradeStatusModel.setTaobaoOrderConfig(taobaoOrderConfig);
        tradeStatusModel.setTradefeedback(tradefeedback);
        
        HttpEntity request = getHttpEntity(tradeStatusModel);
        logger.info("更新交易状态 updateTradeStatus");

        String response = null;
        //如果是 心享旗舰店 (生鲜店铺)，则走生鲜发货
        if(taobaoOrderConfig.getTradeType().equals("261")) {
        	response = restTemplate.postForObject(cloudUrl + "/rawTradeFeedback", request, String.class);
        }else{
        	if(null == parentTradeId) {
            	response = restTemplate.postForObject(cloudUrl + "/updateTradeStatus", request, String.class);
            }else{
            	//子订单发货
            	response = restTemplate.postForObject(cloudUrl + "/childFeedback", request, String.class);
            }	
        }
        
        logger.info("更新交易状态结果：",response);

        return response == null ? "" : response;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	private HttpEntity getHttpEntity(Object obj) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String JSONInput = JSONObject.fromObject(obj).toString();

        return new HttpEntity(JSONInput, headers);
    }

    /*@Override
    public String updateTradeStatus(TaobaoOrderConfig taobaoOrderConfig, TradeFeedback tradefeedback) {
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
    }*/
}
