package com.chinadrtv.oms.internet.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.model.constant.TradeSourceConstants;
import com.chinadrtv.model.oms.PreTradeDetail;
import com.chinadrtv.model.oms.dto.PreTradeDto;
import com.chinadrtv.model.oms.dto.PreTradeLotDto;
import com.chinadrtv.oms.internet.dto.OpsTradeDto;
import com.chinadrtv.oms.internet.dto.OpsTradeResponseDto;
import com.chinadrtv.oms.internet.dto.SkuDto;
import com.chinadrtv.oms.internet.service.InternetCheckService;
import com.chinadrtv.oms.internet.service.InternetService;

/**
 * Created with IntelliJ IDEA. User: liukuan Date: 13-11-1 Time: 下午5:23 To
 * change this template use File | Settings | File Templates.
 */
@Service
public class InternetCheckServiceImpl implements InternetCheckService {
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(InternetCheckServiceImpl.class);

	@Autowired
	private InternetService internetService;


	/**
	 * 转换listmap的值到ListPretare
	 * 
	 * @param trademapList
	 * @return
	 */
	public List<PreTradeDto> getPretrademapto(List<OpsTradeDto> opsTradeList) {

		List<PreTradeDto> tradeList = new LinkedList<PreTradeDto>();
		// 转换本地mode到erp.mode
		for (OpsTradeDto dto : opsTradeList) {
			PreTradeDto pretrade = new PreTradeDto();
			pretrade.setTradeId(dto.getOps_trade_id());
			pretrade.setOpsTradeId(dto.getOps_trade_id());
			pretrade.setAlipayTradeId(dto.getAlipay_trade_id());
			pretrade.setCrdt(dto.getCreated());
			pretrade.setOutCrdt(dto.getCreated());
			String tradeFrom = dto.getTrade_from();
			pretrade.setTradeFrom(tradeFrom);
			String tradeType = tradeFrom.substring(tradeFrom.indexOf("_") + 1, tradeFrom.length());
			pretrade.setTradeType(tradeType);
			pretrade.setBuyerNick(dto.getCustomer_name());
			pretrade.setReceiverName(dto.getReceiver_name());
			pretrade.setReceiverMobile(dto.getReceiver_mobile());
			pretrade.setReceiverPhone(dto.getReceiver_phone());
			pretrade.setReceiverProvince(dto.getReceiver_province() == null ? "" : dto.getReceiver_province());
			pretrade.setReceiverCity(dto.getReceiver_city() == null ? "" : dto.getReceiver_city());
			pretrade.setReceiverCounty(dto.getReceiver_district() == null ? "" : dto.getReceiver_district());
			pretrade.setReceiverAddress(dto.getReceiver_address() == null ? "" : dto.getReceiver_address());
			pretrade.setReceiverPostCode(dto.getReceiver_post_code() == null ? "" : dto.getReceiver_post_code());
			pretrade.setInvoiceComment(dto.getInvoiceComment());
			pretrade.setInvoiceTitle(dto.getInvoiceTitle());
			pretrade.setShippingFee(null == dto.getPostfee() ? 0 : dto.getPostfee().doubleValue());
			pretrade.setPaytype("4");
			pretrade.setSourceId(2L);
			pretrade.setTmsCode(dto.getTms_code());
			
			pretrade.setPayment(Double.parseDouble(dto.getGoodsValue().toEngineeringString()));

			List<PreTradeDetail> preTradeDetailHashSet = new ArrayList<PreTradeDetail>();
			for (SkuDto skuDto : dto.getSkus()) {
				PreTradeDetail preTradeDetail = new PreTradeDetail();
				preTradeDetail.setOutSkuId(skuDto.getSku_code());
				preTradeDetail.setQty(skuDto.getNumber());
				preTradeDetail.setIsActive(true);
				preTradeDetail.setUpPrice(skuDto.getPrice().doubleValue());
				preTradeDetail.setPayment(skuDto.getPrice().doubleValue() * skuDto.getNumber());
				preTradeDetailHashSet.add(preTradeDetail);
			}

			pretrade.setPreTradeDetails(preTradeDetailHashSet);
			tradeList.add(pretrade);
		}
		return tradeList;
	}

	/**
	 * 消息转换记录数据
	 * 
	 * @param customerId
	 * @param trademapList
	 * @return
	 */
	public PreTradeLotDto routeTradeInfo(String customerId, List<OpsTradeDto> trademapList) {

		List<PreTradeDto> tradeList = this.getPretrademapto(trademapList);

		PreTradeLotDto lot = new PreTradeLotDto();
		lot.setCrdt(new Date());
		lot.setValidCount(Long.parseLong(String.valueOf(tradeList.size())));
		lot.setErrCount(Long.parseLong("0"));
		lot.setTotalCount(Long.parseLong(String.valueOf(tradeList.size())));
		lot.setStatus(Long.parseLong("0")); // 未处理
		for (PreTradeDto trade : tradeList) {
			trade.setSourceId(TradeSourceConstants.WEBSITE_SOURCE_ID);
			trade.setCustomerId(customerId);
			for (PreTradeDetail tradeDetail : trade.getPreTradeDetails()) {
				tradeDetail.setTradeId(trade.getTradeId());
				// 特殊处理：如果有自由项商品，取自由项商品ID，否则取商品ID
				if (tradeDetail.getOutSkuId() == null) {
					tradeDetail.setOutSkuId(tradeDetail.getOutItemId());
				}
				if (tradeDetail.getSkuId() == null) {
					tradeDetail.setSkuId(tradeDetail.getItemId());
				}
			}
			lot.getPreTrades().add(trade);
		}
		return lot;

	}

	/**
	 * 检测当前导入的订单信息是否正确
	 * @param tradeList
	 * @return OpsTradeResponseDto
	 */
	public OpsTradeResponseDto checkError(List<OpsTradeDto> tradeList) {
	
		OpsTradeResponseDto responseDto = this.validate(tradeList);
		
		return responseDto;
	}

	/**
	 * 检查茶马订单的信息
	 * 
	 * @param tradeList
	 * @return
	 */
	public OpsTradeResponseDto validate(List<OpsTradeDto> tradeList) {
		OpsTradeResponseDto responseDto = new OpsTradeResponseDto();
		String opsTradeId = "";
		String messageCode = "013";
		String message = "订单导入成功";
		
		for (OpsTradeDto preTrade : tradeList) {
			opsTradeId = preTrade.getOps_trade_id();
			Double goodsValue = 0.00;
			Double totalItemValue = 0.00;
			
			if (preTrade.getTrade_from()== null || !preTrade.getTrade_from().startsWith("ORD_")) {
				messageCode =  "013";
				message =  "无效的订单来源";
				break;
			}
			
			// 客户名称长度
			if (preTrade.getReceiver_name() == null || preTrade.getReceiver_name().length() < 2) {
				messageCode =  "001";
				message =  "客户名称无效(长度小于2)";
				break;
			}
			
			//地址长度无效
			if(preTrade.getReceiver_address() == null || "".equals(preTrade.getReceiver_address().trim()) 
					|| preTrade.getReceiver_address().length() < 5) {
				messageCode =  "003";
				message = "地址长度无效(应该大于5，小于128)";
				break;
			}
			
			// 无效省份地址
			if (preTrade.getReceiver_province() == null || "".equals(preTrade.getReceiver_province())) {
				messageCode =  "009";
				message = "无效省份地址";
				break;
			}
			
			// 无效城市地址
			if (preTrade.getReceiver_city() == null || "".equals(preTrade.getReceiver_city())) {
				messageCode = "010";
				message = "无效城市地址";
				break;
			}
			
			if (null == preTrade.getOps_trade_id() || "".equals(preTrade.getOps_trade_id().trim())) {
				messageCode = "115";
				message = "官网子网站订单Id为空";
				break;
			}
			
			if (preTrade.getReceiver_mobile() == null || "".equals(preTrade.getReceiver_mobile())
					&& (preTrade.getReceiver_phone() == null || "".equals(preTrade.getReceiver_phone()))) {
				messageCode = "117";
				message = "客户手机或电话都为空";
				break;
			}
			
			if (preTrade.getReceiver_post_code() == null || preTrade.getReceiver_post_code().length() < 6) {
				messageCode = "118";
				message = "客户邮编为空";
				break;
			}
			
			if (preTrade.getCreated() == null) {
				messageCode = "119";
				message = "订单付款时间为空";
				break;
			}

			// 订单重复
			int rowid = internetService.getPreTradeByTradeId(preTrade.getOps_trade_id(), preTrade.getOps_trade_id());
			
			if (rowid > 0) {
				messageCode = "002";
				message = "订单重复, 该订单已经被导入过";
				break;
			}

			// 判断指定送货公司是否存在
			if(null != preTrade.getTms_code() && !"".equals(preTrade.getTms_code())) {
				if (!internetService.checkCompanyCode(preTrade.getTms_code())) {
					messageCode = "114";
					message = "指定送货公司不存在";
					break;
				}
			} else {
				preTrade.setTms_code("12345");
			}

			for (SkuDto sku : preTrade.getSkus()) {
				// 商品明细数量不能为0
				if (null ==sku.getNumber() || sku.getNumber() == 0) {
					messageCode = "004";
					message = sku.getSku_code() + "商品明细数量不能为0";
					break;
				}
				
				try {
					BigDecimal pr = sku.getPrice();
					BigDecimal qt = new BigDecimal(sku.getNumber());
					totalItemValue += Double.parseDouble(pr.multiply(qt).toEngineeringString());
				} catch (Exception e) {
					messageCode = "005";
					message = "无效商品单价或数量";
					logger.error("无效商品单价或数量", e);
					break;
				}
			}

			//商品总价
			try {
				goodsValue = preTrade.getGoodsValue().doubleValue();
			} catch (Exception e) {
				messageCode = "006";
				message = "无效商品总价";
				break;
			}
			
			Double freight = 0d;
			if(null != preTrade.getPostfee() && 0 != preTrade.getPostfee().doubleValue()) {
				freight = preTrade.getPostfee().doubleValue();
			}
			Double totalValue = totalItemValue + freight;

			//订单总价和商品明细价格合计不相等
			if (!totalValue.toString().equals(goodsValue.toString())) {
				messageCode = "007";
				message = "订单总价和商品明细价格合计不相等";
				break;
			}

			// 产品或套件编号不存在
			for (SkuDto sku : preTrade.getSkus()) {
				if (!internetService.checkSkuCode(sku.getSku_code())) {
					messageCode = "008";
					message = "产品或套件编号不存在";
					break;
				}
			}
		}
		
		responseDto.setOps_trade_id(opsTradeId);
		responseDto.setMessage(message);
		responseDto.setMessage_code(messageCode);
		
		return responseDto;
	}
}
