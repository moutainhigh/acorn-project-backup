package com.chinadrtv.erp.task.jobs.chama;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.task.entity.AuditLog;
import com.chinadrtv.erp.task.entity.PreTrade;
import com.chinadrtv.erp.task.entity.PreTradeDetail;
import com.chinadrtv.erp.task.service.AuditLogService;
import com.chinadrtv.erp.task.service.PreTradeDetailService;
import com.chinadrtv.erp.task.service.PreTradeService;
import com.chinadrtv.erp.task.service.SourceConfigure;
import com.chinadrtv.erp.task.util.HttpUtil;
import com.chinadrtv.erp.task.util.SaxParseXmlUtil;
import com.chinadrtv.erp.task.util.StringUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 茶马订单自动审核操作
 * 
 * @author haoleitao
 * @date 2013-1-31 下午3:10:05
 * 
 */
@Repository
public class AutoOperator {
	
	private static final Log log = LogFactory.getLog(AutoOperator.class);
	
	@Autowired
	private PreTradeService preTradeService;

	@Autowired
	private PreTradeDetailService preTradeDetailService;

	@Autowired
	private SourceConfigure sourceConfigure;

	@Autowired
	private AuditLogService auditLogService;

	public void singleApproval(PreTrade preTrade) {

		List<PreTradeDetail> list = preTradeDetailService.getAllPreTradeDetailByPerTradeID(preTrade.getTradeId());
		// 格式化省份地址
		if (preTrade.getReceiverProvince() != null && !preTrade.getReceiverProvince().equals("")){
			preTrade.setReceiverProvince(preTrade.getReceiverProvince().replace("省", ""));
		}
		// 单审逻辑
//		String content = XmlBeanConvertUtil.beanToXml(preTrade);
		String mcode;
		String message;
		String url = "";
		url = getPostUrl(preTrade, url);
		if (!url.isEmpty()) {
			String result = "";
			result = postTrade(preTrade, list, url, result);
			// 保存反馈信息
			// 反馈成功
			mcode = SaxParseXmlUtil._readXml(result, "message_code").get(0).get("message_code");
			message = SaxParseXmlUtil._readXml(result, "message").get(0).get("message");
			String igentId = SaxParseXmlUtil._readXml(result, "agent_trade_id").get(0).get("agent_trade_id");
			boolean igent_result = Boolean.valueOf(SaxParseXmlUtil._readXml(result, "result").get(0).get("result"));
			if (igent_result && igentId != null && !igentId.equals("")) {
				preTrade.setShipmentId(igentId);
			}
			preTrade.setImpStatus(mcode);
			preTrade.setImpStatusRemark(message);
			preTradeService.savePreTrade(preTrade);
			wirteLog("前置订单处理", "自动审核", preTrade.getTradeId(), null);
		} else {
			message = "订单来源不可识别";
			preTrade.setImpStatusRemark(message);
		}

	}

	private String getPostUrl(PreTrade preTrade, String url) {
		// 淘宝
		if (preTrade.getSourceId() == TradeSourceConstants.TAOBAO_SOURCE_ID)
			url = sourceConfigure.getTaobaoUrl();
		// url = "http://192.168.75.220:8888/sync/taobao/order";
		if (preTrade.getSourceId() == TradeSourceConstants.TAOBAOC_SOURCE_ID)
			// url = "http://192.168.75.220:8888/sync/taobao/order";
			url = sourceConfigure.getTaobaocUrl();
		if (preTrade.getSourceId() == TradeSourceConstants.TAOBAOZ_SOURCE_ID)
			url = sourceConfigure.getTaobaozUrl();
		// url = "http://192.168.75.220:8888/sync/taobao/order";
		// 京东
		if (preTrade.getSourceId() == TradeSourceConstants.BUY360_SOURCE_ID)
			url = sourceConfigure.getBuy360Url();
		// url = "http://192.168.75.220:8888/sync/360buy/order";

		if (preTrade.getSourceId() == TradeSourceConstants.BUY360FBP_SOURCE_ID)
			url = sourceConfigure.getBuy360fbpUrl();
		// url = "http://192.168.75.220:8888/sync/account/order";
		// 卓越
		if (preTrade.getSourceId() == TradeSourceConstants.AMAZON_SOURCE_ID)
			url = sourceConfigure.getAmazonUrl();
		// url = "http://192.168.75.220:8888/sync/amazon/order";
		if (preTrade.getSourceId() == TradeSourceConstants.AMAZONFBP_SOURCE_ID)
			url = sourceConfigure.getAmazonfbpUrl();
		;
		// VSC
		if (preTrade.getSourceId() == TradeSourceConstants.CSV_SOURCE_ID)
			url = sourceConfigure.getCsvUrl();
		// url="http://192.168.75.220:8888/sync/account/order";
		// 茶马
		if (preTrade.getSourceId() == TradeSourceConstants.CHAMA_SOURCE_ID)
			url = sourceConfigure.getChamaurl();
		// url = "http://192.168.75.220:8888/sync/chamago/order";
		return url;
	}

	private String postTrade(PreTrade preTrade, List<PreTradeDetail> list,
			String url, String result) {
		try {

			String r2 = "<ops_trade>" + "<ops_trade_id>"
					+ preTrade.getTradeId() + "</ops_trade_id>" + "<tid>"
					+ preTrade.getAlipayTradeId() + "</tid>"
					+ "<retailer_id>ORD_" + preTrade.getTradeType()
					+ "</retailer_id>" + "<customer_id>"
					+ preTrade.getCustomerId() + "</customer_id>"
					+ "<trade_from>" + preTrade.getTradeFrom()
					+ "</trade_from>" + "<receiver_name>"
					+ preTrade.getReceiverName() + "</receiver_name>"
					+ "<receiver_mobile>" + preTrade.getReceiverMobile()
					+ "</receiver_mobile>" + "<receiver_phone>"
					+ preTrade.getReceiverPhone() + "</receiver_phone>"
					+ "<receiver_province>" + preTrade.getReceiverProvince()
					+ "</receiver_province>" + "<receiver_city>"
					+ preTrade.getReceiverCity() + "</receiver_city>"
					+ "<receiver_district>" + preTrade.getReceiverCounty()
					+ "</receiver_district>" + "<receiver_street>"
					+ preTrade.getReceiverArea() + "</receiver_street>"
					+ "<receiver_address><![CDATA["
					+ preTrade.getReceiverAddress() + "]]></receiver_address>"
					+ "<receiver_post_code>" + preTrade.getReceiverPostCode()
					+ "</receiver_post_code>" + "<tms_code>"
					+ preTrade.getTmsCode() + "</tms_code>" + "<payment>"
					+ preTrade.getPayment() + "</payment>" + "<commission_fee>"
					+ preTrade.getCreditFee() + "</commission_fee>"
					+ "<retailer_commission_fee>"
					+ preTrade.getRetailerCommissionFee()
					+ "</retailer_commission_fee>"
					+ "<platform_commission_fee>"
					+ preTrade.getPlatformCommissionFee()
					+ "</platform_commission_fee>" + "<credit_fee>"
					+ preTrade.getCreditFee() + "</credit_fee>" + "<adv_fee>"
					+ preTrade.getAdvFee() + "</adv_fee>" + "<jhs_fee>"
					+ preTrade.getJhsFee() + "</jhs_fee>" + "<revenue>"
					+ preTrade.getRevenue() + "</revenue>" + "<created>"
					+ preTrade.getOutCrdt() + "</created>" + "<post_fee>"
					+ preTrade.getShippingFee() + "</post_fee>"
					+ "<invoice_comment> "
					+ StringUtil.nullToBlank(preTrade.getInvoiceComment())
					+ "</invoice_comment>" + "<invoice_title><![CDATA["
					+ StringUtil.nullToBlank(preTrade.getInvoiceTitle())
					+ "]]></invoice_title>" + "<skus>";

			for (PreTradeDetail pre : list) {
				if (pre.getOutSkuId() == null) {
					r2 += "<sku><sku_code>" + pre.getSkuId();
				} else {
					r2 += "<sku><sku_code>" + pre.getOutSkuId();
				}
				r2 += "</sku_code><number>" + pre.getQty() + "</number><price>"
						+ pre.getUpPrice() + "</price></sku>";
			}
			r2 += "</skus>" + "</ops_trade>";
			log.info("xml:" + r2);

			result = HttpUtil.postUrl(url, r2, "UTF-8");
			log.info("result:" + result);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public void singleApproval_json(PreTrade preTrade) {
		// List<String> combineList = preTradeService.getCombinePreTradeId(preTrade,preTrade.getSourceId());
		List<PreTradeDetail> list = preTradeDetailService.getAllPreTradeDetailByPerTradeID(preTrade.getTradeId());
		// 格式化省份地址
		if (preTrade.getReceiverProvince() != null && !preTrade.getReceiverProvince().equals("")){
			preTrade.setReceiverProvince(preTrade.getReceiverProvince().replace("省", ""));
		}
		// 单审逻辑
		// String content = XmlBeanConvertUtil.beanToXml(preTrade);
		String mcode;
		String message = "";
		String url = "";
		String losStr = "";
//		String sjson = "";
		url = getPostJsonUrl(url);
		boolean igent_result = false;
		// 判断订单是否已经审核
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setExclusionStrategies(new ExcludeUnionResource(new String[] { "preTradeLot","preTradeDetails" })).create();
		String result = null;
		try {
			if (!url.isEmpty()) {
				// 判断是否合单数据
				// if(combineList != null && combineList.size() >0 ){
				// 		message = "可以合单的数据:"+combineList;
				// }else{
				if (!StringUtil.nullToFalse(preTrade.getIsApproval())) {
					// 判断是否为退款订单
					if ((preTrade.getRefundStatus() != null && StringUtil.nullTo0(preTrade.getRefundStatusConfirm()) != 1)) {
						if (preTrade.getRefundStatusConfirm() == 2) {
							message = "取消发货的订单";
						} else {
							message = "申请退款的订单";
						}
						preTrade.setImpStatusRemark(message);
					} else {
						preTrade.setIsApproval(false);
						preTradeService.savePreTrade(preTrade);
						Long sh = System.currentTimeMillis();
						result = postTradeJson(preTrade, list, url, "chama-ord");
						Long eh = System.currentTimeMillis();
						log.info("result(" + (eh - sh) + "ms):" + result);
						// 保存反馈信息
						// 反馈成功
						IgentResult p = gson.fromJson(result, IgentResult.class);
						mcode = p.getImpState();
						message = p.getDesc();
						String igentId = p.getOrderId();
						igent_result = p.isSuccess();
						if (igent_result) {
							igentId = p.getOrderId();
							preTrade.setShipmentId(igentId);
							preTrade.setIsApproval(true);
						} else if (igentId != null && !igentId.equals("")) {
							preTrade.setShipmentId(igentId);
						}
						preTrade.setImpStatus(mcode);
						if (igent_result){
							message = "导入订单成功";
						}
						preTrade.setImpStatusRemark(message);
						preTradeService.savePreTrade(preTrade);
					}
				} else {
					message = "重复订单";
					// preTrade.setImpStatusRemark(message);
				}
				// }
			} else {
				message = "订单来源不可识别";
				// preTrade.setImpStatusRemark(message);
			}

//			String json = gson.toJson(preTrade);
//			sjson = "{\"tradeId\":\"" + preTrade.getTradeId()
//					+ "\",\"impStatusRemark\":\"" + message
//					+ "\",\"impStatus\":\""
//					+ StringUtil.nullToBlank(preTrade.getImpStatus())
//					+ "\",\"\":\"\"}";

			losStr = igent_result ? "审核成功," + preTrade.getImpStatusRemark() : "审核失败," + preTrade.getImpStatusRemark() == null ? message : preTrade.getImpStatusRemark();

		} catch (HibernateOptimisticLockingFailureException e) {
			message = "重复订单";
		} catch (Exception e) {
			message = "系统异常";
			if (result == null){
				message = "TC服务调用失败!";
			}
			log.info("系统异常" + e.getMessage());
			preTrade.setIsApproval(false);
			preTradeService.savePreTrade(preTrade);
			// preTrade.setImpStatusRemark(message);
		} finally {
//			sjson = "{\"tradeId\":\"" + preTrade.getTradeId()
//					+ "\",\"impStatusRemark\":\"" + message
//					+ "\",\"impStatus\":\""
//					+ StringUtil.nullToBlank(preTrade.getImpStatus())
//					+ "\",\"\":\"\"}";
			// HttpUtil.ajaxReturn(response, sjson, "application/json");
			wirteLog("前置订单处理", "自动审核:" + losStr, preTrade.getTradeId(), null);
		}

	}

	private String getPostJsonUrl(String url) {
		url = sourceConfigure.getJsonSourceUrl();
		return url;
	}

	private String postTradeJson(PreTrade preTrade, List<PreTradeDetail> list, String url, String crusr) {

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setExclusionStrategies( new ExcludeUnionResource(new String[] { "preTradeLot", "preTradeDetails", "preTrade" })).create();
		preTrade.setReceiverAddress(StringUtil.stringToJson(preTrade.getReceiverAddress()));
		preTrade.setBuyerMessage(StringUtil.stringToJson(preTrade.getBuyerMessage()));
		preTrade.setSellerMessage(StringUtil.stringToJson(preTrade.getSellerMessage()));
		String r = gson.toJson(preTrade);
		String detail = "";
		if (list != null) {
			detail = gson.toJson(list);
		} else {
			detail = "[]";
		}

		r = r.replace("}", ",\"crusr\":\"" + crusr + "\",\"preTradeDetails\": " + detail + "}");
		log.info("send json:" + r);
		try {
			r = HttpUtil.postjsonUrl(url, r, "UTF-8");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		log.info("receive json:" + r);
		return r;
	}

	public void wirteLog(String title, String txt, String treadid, HttpServletRequest request) {
		AuditLog auditLog = new AuditLog();
		auditLog.setAppName("OMS");
		auditLog.setFuncName(title);
		auditLog.setTreadid(treadid);
		auditLog.setLogDate(new Date());
		auditLog.setLogValue(txt);
		// auditLog.setSessionId(request.getSession().getId());
		// UserDetails userDetails = (UserDetails)
		// SecurityContextHolder.getContext()
		// .getAuthentication()
		// .getPrincipal();
		auditLog.setUserId("OMS");
		auditLogService.addAuditLog(auditLog);
	}

	/**
	 * @param args
	 * @author haoleitao
	 * @date 2013-1-31 下午3:10:05
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
