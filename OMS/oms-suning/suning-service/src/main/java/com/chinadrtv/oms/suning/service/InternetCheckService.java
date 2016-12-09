package com.chinadrtv.oms.suning.service;

import java.util.List;

import com.chinadrtv.model.oms.dto.PreTradeDto;
import com.chinadrtv.model.oms.dto.PreTradeLotDto;
import com.chinadrtv.oms.suning.dto.OpsTradeDto;
import com.chinadrtv.oms.suning.dto.OpsTradeResponseDto;

/**
 * Created with IntelliJ IDEA. User: liukuan Date: 13-11-1 Time: 下午5:07 To
 * change this template use File | Settings | File Templates.
 */
public interface InternetCheckService {

	// 转换listmap的值到ListPretare
	List<PreTradeDto> getPretrademapto(List<OpsTradeDto> trademapList);

	// 数据转换保存到数据库
	PreTradeLotDto routeTradeInfo(String customerId, List<OpsTradeDto> trademapList);

	// 检测当前导入的订单信息是否正确
	OpsTradeResponseDto checkError(List<OpsTradeDto> tradeList);

	// 检查订单的信息
	OpsTradeResponseDto validate(List<OpsTradeDto> tradeList);

}
