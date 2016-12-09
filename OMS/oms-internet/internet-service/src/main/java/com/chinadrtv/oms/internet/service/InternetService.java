package com.chinadrtv.oms.internet.service;

import com.chinadrtv.oms.internet.dto.OpsTradeRequestDto;
import com.chinadrtv.oms.internet.dto.OpsTradeResponseDto;


/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-11-4
 * Time: 上午11:04
 * To change this template use File | Settings | File Templates.
 */
public interface InternetService {
	
	int getPreTradeByTradeId(String tradeId, String opsTradeId);

	boolean checkCompanyCode(String tmsCode);

	boolean checkSkuCode(String skuCode);

	/**
	 * <p>导入订单</p>
	 * @param request
	 * @return String
	 */
	OpsTradeResponseDto importOrderList(OpsTradeRequestDto opsTradeRequestDto) throws Exception;
}
