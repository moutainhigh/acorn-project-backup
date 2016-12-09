package com.chinadrtv.erp.oms.service.impl;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.PreTrade;
import com.chinadrtv.erp.model.PreTradeDetail;
import com.chinadrtv.erp.oms.service.PreTradeService;
import com.chinadrtv.erp.oms.util.HttpUtil;
import com.chinadrtv.erp.oms.util.SaxParseXmlUtil;
import com.chinadrtv.erp.oms.util.StringUtil;
import com.chinadrtv.erp.oms.controller.ExcludeUnionResource;
import com.chinadrtv.erp.oms.controller.OrderPreprocessingController;
import com.chinadrtv.erp.oms.dao.PreTradeDao;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * 前置订单服务
 * @author haoleitao
 *
 */
@Service("preTradeService")
public class PreTradeServiceImpl implements PreTradeService{

	private static final Logger log = LoggerFactory
			.getLogger(PreTradeServiceImpl.class);
    @Autowired
    private PreTradeDao preTradeDao;


    public void addPreTrade(PreTrade preTrade) {
        preTradeDao.save(preTrade);
    }



    public void savePreTrade(PreTrade preTrade) {
        preTradeDao.saveOrUpdate(preTrade);
    }

    public void delPreTrade(Long id) {
        preTradeDao.remove(id);
    }



	public PreTrade getById(Long id) {
		// TODO Auto-generated method stub
		return preTradeDao.get(id);
	}




	public List<PreTrade> getAllPreTrade(String treadType,Long sourceId,String tradeFrom,String alipayTradeId,Date beginDate,
			Date endDate, Double min, Double max,String receiverMobile,String buyerMessage,String sellerMessage,int state,int refundStatus,int refundStatusConfirm,String id,String tradeId,int index, int size) {
		// TODO Auto-generated method stub
		return preTradeDao.searchPaginatedPreTradeByAppDate(treadType,sourceId,tradeFrom,alipayTradeId,beginDate, endDate,min, max,receiverMobile,buyerMessage,sellerMessage,state,refundStatus,refundStatusConfirm,id,tradeId,index,size);
	}



	public int getCountAllPreTrade(String treadType,Long sourceId,String tradeFrom,String alipayTradeId,Date beginDate,
			Date endDate, Double min, Double max, String receiverMobile,String buyerMessage,String sellerMessage,int state,int refundStatus,int refundStatusConfirm,String id, String tradeId) {
		return preTradeDao.searchPaginatedPreTradeByAppDate(treadType,sourceId,tradeFrom,alipayTradeId,beginDate, endDate,min, max,receiverMobile,buyerMessage,sellerMessage,state,refundStatus,refundStatusConfirm,id,tradeId);
	}

	public List<PreTrade> getAllIsCombinePreTrade(String treadType,Long sourceId,String tradeFrom,String alipayTradeId,Date beginDate,
			Date endDate, Double min, Double max,String receiverMobile,String buyerMessage,String sellerMessage,int state,int refundStatus,int refundStatusConfirm,String id,String tradeId,int index, int size) {
		// TODO Auto-generated method stub
		return preTradeDao.searchPaginatedIsCombinePreTradeByAppDate(treadType,sourceId,tradeFrom,alipayTradeId,beginDate, endDate,min, max,receiverMobile,buyerMessage,sellerMessage,state,refundStatus,refundStatusConfirm,id,tradeId,index,size);
	}

	public int getCountAllIsCombinePreTrade(String treadType,Long sourceId,String tradeFrom,String alipayTradeId,Date beginDate,
			Date endDate, Double min, Double max, String receiverMobile,String buyerMessage,String sellerMessage,int state,int refundStatus,int refundStatusConfirm,String id, String tradeId) {
		return preTradeDao.searchPaginatedIsCombinePreTradeByAppDate(treadType,sourceId,tradeFrom,alipayTradeId,beginDate, endDate,min, max,receiverMobile,buyerMessage,sellerMessage,state,refundStatus,refundStatusConfirm,id,tradeId);
	}


	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.oms.service.PreTradeService#savePreTrade(java.lang.String)
	 */
	public void savePreTrade(String ids) {
		// TODO Auto-generated method stub
		preTradeDao.savePreTrade(ids);
	}



	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.oms.service.PreTradeService#approvalPreTrade()
	 */
	public boolean updateApproval(PreTrade preTrade,String url,List<PreTradeDetail> list) {

		String mcode;
		String message = "";
		String losStr = "";
		String sjson ="";
		boolean igent_result = false;
		try {
			if (!url.isEmpty()) {
				if (!StringUtil.nullToFalse(preTrade.getIsApproval())) {
					// 判断是否为退款订单
					if ((StringUtil.nullTo0(preTrade.getRefundStatus()) == 1 && StringUtil
							.nullTo0(preTrade.getRefundStatusConfirm()) == 0)
							|| StringUtil.nullTo0(preTrade.getRefundStatus()) == 2) {

						if (preTrade.getRefundStatus() == 1)
							message = "申请退款的订单";
						if (preTrade.getRefundStatus() == 2)
							message = "同意退款的订单";
						preTrade.setImpStatusRemark(message);
					} else {

						String result = "";
						preTrade.setIsApproval(true);
						savePreTrade(preTrade);
	                    Long sh = System.currentTimeMillis();
						result = postTrade(preTrade, list, url, result);
						Long eh = System.currentTimeMillis();
						
						log.info("result("+(eh-sh)+"ms):" + result);
						// 保存反馈信息
						// 反馈成功
						mcode = SaxParseXmlUtil
								._readXml(result, "message_code").get(0)
								.get("message_code");
						message = SaxParseXmlUtil._readXml(result, "message")
								.get(0).get("message");
						String igentId = SaxParseXmlUtil
								._readXml(result, "agent_trade_id").get(0)
								.get("agent_trade_id");
						igent_result = Boolean.valueOf(SaxParseXmlUtil
								._readXml(result, "result").get(0)
								.get("result"));
						if (igent_result && igentId != null && !igentId.equals("")) {
							preTrade.setShipmentId(igentId);
						} else {
							preTrade.setIsApproval(false);
						}
						preTrade.setImpStatus(mcode);
						if(igent_result) message="导入成功";
						preTrade.setImpStatusRemark(message);
						savePreTrade(preTrade);
					}
				} else {
					message = "重复订单";
					preTrade.setImpStatusRemark(message);
				}
			} else {
				message = "订单来源不可识别";
				preTrade.setImpStatusRemark(message);
			}
			

		} catch (Exception e) {
			message = "系统异常"+e;
			log.info(e.getMessage());
			preTrade.setImpStatusRemark(message);
		}
		
		return igent_result;
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
					+ "<receiver_address>" + preTrade.getReceiverAddress()
					+ "</receiver_address>" + "<receiver_post_code>"
					+ preTrade.getReceiverPostCode() + "</receiver_post_code>"
					+ "<tms_code>" + preTrade.getTmsCode() + "</tms_code>"
					+ "<payment>" + preTrade.getPayment() + "</payment>"
					+ "<commission_fee>" + preTrade.getCreditFee()
					+ "</commission_fee>" + "<retailer_commission_fee>"
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
					+ "<invoice_comment>"
					+ StringUtil.nullToBlank(preTrade.getInvoiceComment())
					+ "</invoice_comment>" + "<invoice_title>"
					+ StringUtil.nullToBlank(preTrade.getInvoiceTitle())
					+ "</invoice_title>" + "<skus>";

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public int batchupdateConfrimState(Long[] ids,int value){
		return preTradeDao.batchupdateConfrimState(ids, value);
	}



	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.oms.service.PreTradeService#updatePreTrade(com.chinadrtv.erp.model.PreTrade)
	 */
	public PreTrade updatePreTrade(PreTrade preTrade) {
		// TODO Auto-generated method stub
		return preTradeDao.save(preTrade);
	}



	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.oms.service.PreTradeService#getCombinePreTradeId(com.chinadrtv.erp.model.PreTrade)
	 */
	public List<String> getCombinePreTradeId(PreTrade preTrade,Long sourceId) {
		// TODO Auto-generated method stub
		return preTradeDao.getCombinePreTradeId(preTrade,sourceId);
	}

    public List<PreTrade> findPretrades(String tradeId)
    {
        return preTradeDao.findList("from PreTrade where tradeId=:tradeId and shipmentId is not null",new ParameterString("tradeId",tradeId));
    }
}
