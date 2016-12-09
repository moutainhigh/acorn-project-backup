package com.chinadrtv.oms.internet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.chinadrtv.model.oms.PreTrade;
import com.chinadrtv.oms.internet.dal.dao.TradeFeedbackDao;
import com.chinadrtv.oms.internet.dal.model.TradeFeedback;
import com.chinadrtv.oms.internet.dto.OpsTradeResponseDto;
import com.chinadrtv.oms.internet.dto.OpsUpdateRequestDto;
import com.chinadrtv.oms.internet.service.FeedbackService;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-11-6
 * Time: 下午1:21
 * To change this template use File | Settings | File Templates.
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {
	
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(FeedbackServiceImpl.class);
    
    @Autowired
    private TradeFeedbackDao tradeFeedbackDao;
    
    @Autowired
    private RestTemplate restTemplate;

    /**
     *获取反馈信息
     * @param orderType
     * @return
     */
    public List<TradeFeedback> searchOrderByType(List<String> orderType) {
        return tradeFeedbackDao.findByOpsId(orderType);
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
     * 反馈信息
     * @param url
     * @param tradefeedback
     * @return
     * @throws Exception 
     */
    public Boolean updateTradeStatus(String url, TradeFeedback tradefeedback) throws Exception {

        boolean success = false;

        String opsTradeId = tradefeedback.getOpsTradeId();
        String tmsCode = tradefeedback.getTmsCode();
        String tmsLogisiticsIds = tradefeedback.getTmsLogisiticsIds();
        
        OpsUpdateRequestDto updateDto = new OpsUpdateRequestDto();
        updateDto.setOps_trade_id(opsTradeId);
        updateDto.setTms_code(tmsCode);
        updateDto.setTms_logisitics_ids(tmsLogisiticsIds);
        updateDto.setWeight("0");
        updateDto.setCreated(tradefeedback.getCreateDat());

    	ResponseEntity<OpsTradeResponseDto> response = null;
    	
    	logger.info("post data: " + updateDto);
    	
    	logger.info("post url: " + url + "/orderapi.ashx?action=Ship");
    	
    	try {
			response = restTemplate.postForEntity(url + "/orderapi.ashx?action=Ship", updateDto, OpsTradeResponseDto.class);
		} catch (Exception e) {
			logger.error("post error: ", e);
		}
    	
    	if(null == response || response.getStatusCode().value() != 200) {
    		throw new Exception("post url failed.");
    	}
    	
    	OpsTradeResponseDto responseDto = response.getBody();
    	
    	if(null != responseDto && responseDto.getResult()){
    		success = true;
    	}
        	
        return success;
    }
}
