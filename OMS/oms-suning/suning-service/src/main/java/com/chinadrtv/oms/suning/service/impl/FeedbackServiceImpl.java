package com.chinadrtv.oms.suning.service.impl;

import java.io.StringReader;
import java.text.SimpleDateFormat;
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
import com.chinadrtv.oms.suning.dal.dao.TradeFeedbackDao;
import com.chinadrtv.oms.suning.dal.model.TradeFeedback;
import com.chinadrtv.oms.suning.dto.FeedbackResponseDto;
import com.chinadrtv.oms.suning.dto.OrderConfig;
import com.chinadrtv.oms.suning.service.FeedbackService;
import com.chinadrtv.service.oms.PreTradeService;
import com.suning.api.DefaultSuningClient;
import com.suning.api.entity.transaction.OrderdeliveryAddRequest;
import com.suning.api.entity.transaction.OrderdeliveryAddResponse;
import com.suning.api.exception.SuningApiException;

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
    
    @Autowired
    private TradeFeedbackDao tradeFeedbackDao;
    
    @Autowired
    private PreTradeService preTradeService;
    
    private Smooks smooks;
    
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
	@PostConstruct
	public void init() {
		try {
			smooks = new Smooks("/smooks/smooksSuning.xml");
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
	 * @see com.chinadrtv.oms.suning.service.FeedbackService#feedback(java.util.List)
	 */ 
	@Override
	public void feedback(List<OrderConfig> configList) {
		
		for(OrderConfig oc : configList) {
			List<TradeFeedback> tradeFeedbackList = tradeFeedbackDao.findFeedbackList(oc.getTradeType());
			
			if (tradeFeedbackList != null && tradeFeedbackList.size() > 0) {
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
					preTrade.setFeedbackUser("suning-order-feedback");
					preTrade.setFeedbackDate(new Date());
					
					boolean updateResult = this.updateOrderFeedbackStatus(preTrade);
					logger.info("update Pretrade result:" + updateResult);
				}
			}
		}
	}

	/**
	 * <p></p>
	 * @param oc
	 * @param tradeFeedback
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> executeFeedback(OrderConfig oc, TradeFeedback tradeFeedback) {
		Map<String, Object> rsMap = new HashMap<String, Object>();
		boolean result = true;
		String message = "反馈成功";
		
		OrderdeliveryAddRequest request = new OrderdeliveryAddRequest(); 
		request.setOrderCode(tradeFeedback.getOpsTradeId());
		request.setExpressNo(tradeFeedback.getTmsLogisiticsIds());
		request.setExpressCompanyCode(tradeFeedback.getTmsCode());
		request.setDeliveryTime(sdf.format(tradeFeedback.getCreateDat()));
		
		//api入参校验逻辑开关，当测试稳定之后建议设置为 false 或者删除该行
		request.setCheckParam(true);
		
		DefaultSuningClient client = new DefaultSuningClient(oc.getUrl(), oc.getAppkey(), oc.getAppSecret(), "xml");
		OrderdeliveryAddResponse response = null;
		
		try {
		    response = client.excute(request);
		} catch (SuningApiException e) {
		    logger.error("feedback error ", e);
		    message = e.getMessage();
		}
		
		Source source = new StreamSource(new StringReader(response.getBody()));
		JavaResult javaResult = new JavaResult();
		smooks.filterSource(source, javaResult);

		List<FeedbackResponseDto> feedbackResultList = (List<FeedbackResponseDto>) javaResult.getBean("feedbackResultList");
		
		for(FeedbackResponseDto dto : feedbackResultList) {
			if("Y".equals(dto.getSendresult())){
				result |= true;
			}else{
				result |= false;
			}
		}
		
		rsMap.put("success", result);
		rsMap.put("message", message);
		
		return rsMap;
	}
}
