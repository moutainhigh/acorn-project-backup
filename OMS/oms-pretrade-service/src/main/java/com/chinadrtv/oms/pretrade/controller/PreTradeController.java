package com.chinadrtv.oms.pretrade.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.model.oms.PreTradeDetail;
import com.chinadrtv.model.oms.dto.PreTradeDto;
import com.chinadrtv.model.oms.dto.PreTradeLotDto;
import com.chinadrtv.service.oms.PlubasInfoService;
import com.chinadrtv.service.oms.PreTradeImportService;



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
 * @since 2014-9-24 下午5:20:58 
 *
 */
@Controller
@RequestMapping("/preTrade")
public class PreTradeController {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PreTradeController.class);
	
    @Autowired
    private PreTradeImportService preTradeImportService;
    @Autowired
    private PlubasInfoService plubasInfoService;
    
    
    @RequestMapping(value = "/import",method= RequestMethod.POST, headers="Content-Type=application/json")
    @ResponseBody
    public Map<String, Object> importPreTrade(@RequestBody PreTradeLotDto preTradeLotDto){
    	
    	Map<String, Object> rsMap = new HashMap<String, Object>();
    	
    	boolean success = false;
    	String message = "";
    	
		try {
			//validate SKU code
			List<PreTradeDto> preTradeList = preTradeLotDto.getPreTrades();
			for(PreTradeDto preTradeDto : preTradeList) {
	        	for(PreTradeDetail preTradeDetail : preTradeDto.getPreTradeDetails()) {
	        		Boolean valid = plubasInfoService.checkSkuCode(String.valueOf(preTradeDetail.getSkuId()));
	        		if(null ==valid || !valid) {
	        			throw new RuntimeException("无效的SKU");
	        		}
	        	}
			}
			
			preTradeImportService.importPretrades(preTradeLotDto);
			success = true;
		} catch (Exception e) {
			logger.error("import failed", e);
			message = e.getMessage();
		}
    	
    	rsMap.put("success", success);
    	rsMap.put("message", message);
    	
    	return rsMap;
    }

}
