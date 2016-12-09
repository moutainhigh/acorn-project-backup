package com.chinadrtv.oms.suning.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.oms.suning.dto.OpsTradeRequestDto;
import com.chinadrtv.oms.suning.dto.OpsTradeResponseDto;
import com.chinadrtv.oms.suning.service.InternetCheckService;
import com.chinadrtv.oms.suning.service.InternetService;
import com.chinadrtv.service.oms.PreTradeImportService;

/**
 * Created with IntelliJ IDEA. User: liukuan Date: 13-10-24 Time: 下午5:42 To
 * change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping({ "/order" })
public class SuningOrderInputController {

	private static final Logger logger = LoggerFactory.getLogger(SuningOrderInputController.class);

	@Autowired
	private InternetCheckService internetCheckService;
	@Autowired
	private PreTradeImportService preTradeLotService;
	@Autowired
	private InternetService internetService;
	
    @InitBinder
    public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
    }

	@RequestMapping(value = "/input", method = { RequestMethod.GET, RequestMethod.POST }, headers = "Content-Type=application/json")
	@ResponseBody
	public OpsTradeResponseDto importOrderList(@RequestBody OpsTradeRequestDto opsTradeRequest){
		
		//StringBuffer response = new StringBuffer();
		boolean requestSuccess = false;
		
		OpsTradeResponseDto responseDto = null;
		
		try {
			logger.info("get post params:" + opsTradeRequest);
			
			responseDto = internetService.importOrderList(opsTradeRequest);
			
			requestSuccess = true;
			responseDto.setResult(true);
			responseDto.setAgent_trade_id("");
			
		} catch (Exception e) {
			logger.error("controller error", e);
		}
		
		if(!requestSuccess) {
			responseDto = new OpsTradeResponseDto();
			responseDto.setResult(false);
			responseDto.setAgent_trade_id("");
			responseDto.setMessage_code("012");
			responseDto.setMessage("系统异常, 请检查输入参数是否正确");
		}
		
		logger.info("http finished!");
		
		return responseDto;
	}

	@ExceptionHandler
	@ResponseBody
	public String handleException(Exception ex, HttpServletRequest request) {
		return ex.getMessage();
	}
}
