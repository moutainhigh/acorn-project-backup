package com.chinadrtv.oms.paipai.service.impl;

import java.io.StringReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.milyn.Smooks;
import org.milyn.payload.JavaResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.model.oms.PreTrade;
import com.chinadrtv.oms.paipai.dal.dao.TradeFeedbackDao;
import com.chinadrtv.oms.paipai.dal.model.TradeFeedback;
import com.chinadrtv.oms.paipai.dto.FeedbackResponseDto;
import com.chinadrtv.oms.paipai.dto.OrderConfig;
import com.chinadrtv.oms.paipai.oauth.ApiUtil;
import com.chinadrtv.oms.paipai.oauth.PaiPaiOpenApiOauth;
import com.chinadrtv.oms.paipai.service.FeedbackService;
import com.chinadrtv.service.oms.PreTradeService;

/**
 * 
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2014-11-13 下午2:30:30 
 *
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {
	
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(FeedbackServiceImpl.class);
    
    private static final String FEEDBACK_URL = "/deal/sellerConsignDealItem.xhtml";
    
    @Autowired
    private TradeFeedbackDao tradeFeedbackDao;
    
    @Autowired
    private PreTradeService preTradeService;
    
    private Smooks smooks;
    
	@PostConstruct
	public void init() {
		try {
			smooks = new Smooks("/smooks/smooks.xml");
		} catch (Exception e) {
			logger.error("initation smooks error", e);
		}
	}

    /**
     * 更改状态
     * @param preTrade
     * @return
     */
    public Boolean updateOrderFeedbackStatus(PreTrade preTrade) {
        boolean b = false;
        try{
            int result = tradeFeedbackDao.updateOrderFeedbackStatus(preTrade);
            if(result > 0){
                b = true;
            }
        }catch (Exception e){
            b = false;
            logger.error("", e);
        }
        return b;
    }


	/** 
	 * <p>Title: feedback</p>
	 * <p>Description: </p>
	 * @param configList
	 * @see com.chinadrtv.oms.paipai.service.FeedbackService#feedback(java.util.List)
	 */ 
	@Override
	public void feedback(List<OrderConfig> configList) {
		
		for(OrderConfig oc : configList) {
			List<TradeFeedback> tradeFeedbackList = tradeFeedbackDao.findFeedbackList(oc.getTradeType());
			
			if (null == tradeFeedbackList || tradeFeedbackList.size() == 0) {
				continue;
			}
			
			for (TradeFeedback tradeFeedback : tradeFeedbackList) {
				
				String tradeId = tradeFeedback.getOpsTradeId();
				PreTrade  preTrade = preTradeService.findByOpsId(tradeId, new Long(oc.getSourceId()));
				
				Map<String, Object> rsMap = this.executeFeedback(oc, tradeFeedback);
				
				boolean success = Boolean.parseBoolean(rsMap.get("success").toString());
				String message = String.valueOf(rsMap.get("message"));
				
				// 回写数据库
				preTrade = new PreTrade();
				preTrade.setTradeId(tradeFeedback.getOpsTradeId());
				preTrade.setOpsTradeId(tradeFeedback.getOpsTradeId());
				preTrade.setFeedbackStatus(success ? "2" : "4");
				if (success) {
					message = "反馈成功";
				} else {
					message = "反馈失败: " + message;
				}
				preTrade.setFeedbackStatusRemark(message);
				preTrade.setFeedbackUser("paipai-order-feedback");
				preTrade.setFeedbackDate(new Date());
				
				boolean updateResult = this.updateOrderFeedbackStatus(preTrade);
				logger.info("update Pretrade result:" + updateResult);
			}
		}
	}

	/**
	 * <p>Execute feedback action</p>
	 * @param oc
	 * @param tradeFeedback
	 * @return
	 */
	private Map<String, Object> executeFeedback(OrderConfig oc, TradeFeedback tradeFeedback) {
		
		PaiPaiOpenApiOauth api = ApiUtil.getAPI(oc, FEEDBACK_URL);
		api.addParam("sellerUin", String.valueOf(oc.getUin()));
		api.addParam("dealCode", tradeFeedback.getOpsTradeId());
		api.addParam("logisticsName", tradeFeedback.getTmsCode());
		api.addParam("logisticsCode", tradeFeedback.getTmsLogisiticsIds());
		api.addParam("arriveDays", "5");
		
		String responseXml = null;
		try {
			responseXml = PaiPaiOpenApiOauth.invoke(api);
			logger.info("responseXml: " + responseXml);
		} catch (Exception e) {
		    logger.error("Call paipai interface error ", e);
		}
		
		FeedbackResponseDto responseDto = null;
		if(null != responseXml && !"".equals(responseXml.trim())) {
			responseDto = this.parseResult(oc, responseXml);
		}
		
		Map<String, Object> rsMap = new HashMap<String, Object>();
		boolean result = false;
		String message = "";
		
		if(null ==responseDto || null == responseDto.getDealCode() || !"0".equals(responseDto.getErrorCode())){
			result = false;
			message = responseDto.getErrorMessage();
		}else{
			result = true;
		}
	
		rsMap.put("success", result);
		rsMap.put("message", message);
		
		return rsMap;
	}

	/**
	 * <p></p>
	 * @param oc
	 * @param responseXml
	 * @return
	 */
	private FeedbackResponseDto parseResult(OrderConfig oc, String responseXml) {
		Source source = new StreamSource(new StringReader(responseXml));
		JavaResult javaResult = new JavaResult();
		smooks.filterSource(source, javaResult);

		FeedbackResponseDto dto = (FeedbackResponseDto) javaResult.getBean("feedback");
		
		return dto;
	}
}
