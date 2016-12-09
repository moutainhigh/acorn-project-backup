package com.chinadrtv.service.oms.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.chinadrtv.dal.iagent.dao.PlubasInfoDao;
import com.chinadrtv.model.oms.PreTrade;
import com.chinadrtv.model.oms.PreTradeDetail;
import com.chinadrtv.model.oms.dto.PreTradeDto;
import com.chinadrtv.model.oms.dto.PreTradeLotDto;
import com.chinadrtv.service.oms.PreTradeDetailService;
import com.chinadrtv.service.oms.PreTradeImportService;
import com.chinadrtv.service.oms.PreTradeLotService;
import com.chinadrtv.service.oms.PreTradeService;
import com.chinadrtv.service.oms.PriceReconService;
import com.chinadrtv.service.oms.PromotionService;
import com.chinadrtv.service.util.AESEncryptor;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-11
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class PreTradeImportServiceImpl implements PreTradeImportService {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(PreTradeImportServiceImpl.class);

    @Autowired
    private PreTradeService preTradeService;
    @Autowired
    private PreTradeDetailService preTradeDetailService;
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private PreTradeLotService preTradeLotService;
    @Autowired
    private PriceReconService priceReconService;
    @Autowired
    private PlubasInfoDao plubasInfoDao;

    private static String impStatusRemark = "拆分订单成功";
    private static String sellerMessage_prefix = "子订单 ";
    private static String impStatus_split = "020";
    @Override
    public void importPretrades(PreTradeLotDto preTradeLotDto) {
    	
        List<String> skus = preTradeService.getInsuranceSkus() ;
        for (Iterator it = preTradeLotDto.getPreTrades().iterator(); it.hasNext(); ) {
            PreTradeDto preTrade = (PreTradeDto) it.next();
            
            //Replacing illegal character which contained in receiver address.
            String receiverAddress = preTrade.getReceiverAddress();
            receiverAddress = this.eraseIllgealCharacter(receiverAddress);
            preTrade.setReceiverAddress(receiverAddress);
            
            String name = preTrade.getReceiverName();
            name = this.eraseIllgealCharacter(name);
            preTrade.setReceiverName(name);
            
            String invoiceTitle = preTrade.getInvoiceTitle();
            invoiceTitle = this.eraseIllgealCharacter(invoiceTitle);
            preTrade.setInvoiceTitle(invoiceTitle);
            
            try {
				if (null != preTrade.getReceiverMobile() && !"".equals(preTrade.getReceiverMobile().trim())) {
					String cipherMobile = AESEncryptor.encrypt(preTrade.getReceiverMobile());
					
					logger.info("Encrypted preTrade[" + preTrade.getTradeId() + "] mobile No. " + preTrade.getReceiverMobile() + "\t" + cipherMobile);
					
					preTrade.setReceiverMobile(cipherMobile);
				}
				
				if(null != preTrade.getReceiverPhone() && !"".equals(preTrade.getReceiverPhone().trim())) {
					String cipherTelPhone = AESEncryptor.encrypt(preTrade.getReceiverPhone());
					
					logger.info("Encrypted preTrade[" + preTrade.getTradeId() + "] Telphone No. " + preTrade.getReceiverPhone() + "\t" + cipherTelPhone);
					
					preTrade.setReceiverPhone(cipherTelPhone);
				}
			} catch (Exception e) {
				logger.error("encrypt error", e);
			}
            
            preTrade.setCrdt(new Date());
            if ((preTrade.getReceiverMobile() == null || "".equals(preTrade.getReceiverMobile()))
                    && preTrade.getReceiverPhone() != null) {
                preTrade.setBuyerMessage((preTrade.getBuyerMessage() == null ? "" : preTrade.getBuyerMessage())/* + preTrade.getReceiverPhone()*/);
            }
            PreTrade p = preTradeService.findByOpsId(preTrade.getOpsTradeId(), preTrade.getSourceId());
            if (p != null) {
                it.remove();
            }
        }
        for (Iterator it = preTradeLotDto.getPreTrades().iterator(); it.hasNext(); ) {
            PreTradeDto preTradeDto = (PreTradeDto) it.next();
            preTradeDto.setFeedbackStatus("0");

            validatePreTrade(it, preTradeDto, true);
            //处理保险商品
            //PreTrade preTrade = (PreTrade) PojoUtils.convertDto2Pojo(preTradeDto, PreTrade.class);
            preTradeService.updateInsuranceSku(preTradeDto, skus);
            /* 重算价格 平衡表头payment - shippingFee = sum(preTradeDetail.upPrice * qty - discount_fee) */
            priceReconService.reconPrice(preTradeDto);

            /* 订单类型对应默认送货公司 */
            preTradeService.updateTmsCodeAndPayType(preTradeDto);

            //把前3级地址根据映射关系对应到橡果地址
            matchAddress(preTradeDto);

            //调度促销服务
            //getPromotionResult(context, preTrade);
        }

        promotionService.promotion(preTradeLotDto.getPreTrades());
        for(PreTradeDto preTradeDto:preTradeLotDto.getPreTrades()) {
            /* 更新商品名称 */
            preTradeService.updateSkuTitle(preTradeDto,preTradeDto.getPreTradeDetails());
        }

        preTradeLotService.insert(preTradeLotDto);
    }

    /**
	 * <p></p>
	 * @param receiverAddress
	 * @return
	 */
	private String eraseIllgealCharacter(String receiverAddress) {
		if(null == receiverAddress || "".equals(receiverAddress.trim())) {
			return "";
		}
		String regExp = "[\"`~!@$%^&*\\]\\[\\-_\\\\+=|{}':;',//[//].<>/?~！@￥%……&*——+|{}【】‘；：”“’。，、？]";
		
		Pattern pattern = Pattern.compile(regExp);
		Matcher matcher = pattern.matcher(receiverAddress);
		receiverAddress = matcher.replaceAll(" ");
		
		return receiverAddress;
	}

	@Override
    public void splitPreTrades(String tradeId) {

        PreTrade preTrade = preTradeService.queryPreTradeByTradeId(tradeId);
        if (preTrade == null || preTrade.getSplitFlag() == null
                || preTrade.getSplitFlag() <= 1 || impStatus_split.equalsIgnoreCase(preTrade.getImpStatus())) {
            return;
        }
        logger.info("订单拆分 tradeId:"+tradeId);
        List<PreTradeDetail> preTradeDetailList = preTradeDetailService.queryDetailByTradeId(tradeId);
        if (preTradeDetailList == null || preTradeDetailList.isEmpty() || preTradeDetailList.size() == 1) {
            return;
        }

        Map<Integer, List<PreTradeDetail>> detailMap = new HashMap<Integer, List<PreTradeDetail>>();
        Integer shippingFeeIndex = splitPreTradeDetail(preTradeDetailList, detailMap);
        //邮费
        Double shippingFee = preTrade.getShippingFee();

        //根据分类，重新入库
        for (Integer key : detailMap.keySet()) {
            List<PreTradeDetail> newPreTradeDetailList = detailMap.get(key);
            PreTradeDetail newPreTrade = newPreTradeDetailList.get(0);
            String oldTradeId = newPreTrade.getTradeId();
            String newTradeId = oldTradeId + "-" + key;
            double payment = 0d;
            for (PreTradeDetail preTradeDetail : newPreTradeDetailList) {
                preTradeDetail.setTradeId(newTradeId);
                payment += preTradeDetail.getPayment();
                preTradeDetailService.insert(preTradeDetail);
            }

            if (key.intValue() == shippingFeeIndex.intValue()) {
                preTrade.setShippingFee(shippingFee);
            } else {
                preTrade.setShippingFee(0d);
            }
            preTrade.setPartTradeId(oldTradeId);
            preTrade.setTradeId(newTradeId);
            preTrade.setOpsTradeId(newTradeId);
            preTrade.setPayment(payment + preTrade.getShippingFee());
            preTrade.setTradeType(newPreTrade.getItemTradeType());
            preTrade.setTmsCode(newPreTrade.getItemTmsCode());
            preTrade.setSplitFlag(-1);

            preTradeService.insert(preTrade);
        }
        logger.info("订单拆分完成 tradeId:"+tradeId);
        PreTrade oldPreTrade = preTradeService.queryPreTradeByTradeId(tradeId);

        oldPreTrade.setImpStatus(impStatus_split);
        oldPreTrade.setImpStatusRemark(impStatusRemark);
        oldPreTrade.setSellerMessage(sellerMessage_prefix + preTrade.getSellerMessage() == null ? "" : preTrade.getSellerMessage());
        preTradeService.updatePreTrade(oldPreTrade);
        logger.info("订单拆分结束 原始订单更新");
    }

    //明细分类
    private Integer splitPreTradeDetail(List<PreTradeDetail> preTradeDetailList, Map<Integer, List<PreTradeDetail>> detailMap) {
        Integer shippingFeeIndex = -1;
        for(PreTradeDetail preTradeDetail:preTradeDetailList){
            if(shippingFeeIndex==-1 || shippingFeeIndex>preTradeDetail.getWarehouseType()){
                shippingFeeIndex = preTradeDetail.getWarehouseType();
            }
            Integer warehouseType = preTradeDetail.getWarehouseType();
            List<PreTradeDetail> newList =  detailMap.get(warehouseType);
            if(newList==null){
                newList = new ArrayList<PreTradeDetail>();
                newList.add(preTradeDetail);
                detailMap.put(warehouseType,newList);
            }else {
                detailMap.get(warehouseType).add(preTradeDetail);
            }
        }
        return shippingFeeIndex;
    }


    private void matchAddress(PreTrade preTrade) {
        String errmsg = preTradeService.checkReceiverAddress(preTrade);
        if (!errmsg.isEmpty()) {
            preTrade.setIsValid(false);
            preTrade.setErrMsg(errmsg);
        }
    }

    /**
     * 验证数据
     *
     * @param it
     * @param preTrade
     * @param isImport true：是导入文件，验证价格是否为0，false:促销生成后的验证，不检查价格
     */
    private void validatePreTrade(Iterator it, PreTradeDto preTrade, boolean isImport) {
        /*String val = preTradeService.validatePreTrade(preTrade);
        if (!val.isEmpty()) {
            preTrade.setIsVaid(false);
            preTrade.setErrMsg(val);

        }
        */
        preTrade.setIsValid(true);
        StringBuffer s = new StringBuffer("");
        boolean detailValid = true;
        for (PreTradeDetail preTradeDetail : preTrade.getPreTradeDetails()) {
            preTradeDetail.setErrMsg("");
            StringBuffer errs = new StringBuffer();
            logger.debug("out sku id:"+preTradeDetail.getOutSkuId());
            if(!StringUtils.isEmpty(preTradeDetail.getOutSkuId()))
            {
                if (!preTradeService.checkSkuCode(preTradeDetail.getOutSkuId())) {
                    logger.error("not found sku id:"+preTradeDetail.getOutSkuId());
                    s.append(preTradeDetail.getOutSkuId());
                    s.append(" ,");
                    errs.append("sku 找不到:" + preTradeDetail.getOutSkuId());
                    errs.append(" ,");
                }
            }
            else
            {
                s.append(preTradeDetail.getOutSkuId());
                s.append(" ,");
                errs.append("sku is null:" + preTradeDetail.getOutSkuId());
                errs.append(" ,");
            }

            /* 2013-06-13 去掉对单价的判断
            if ((isImport) && (preTradeDetail.getPrice() == null || preTradeDetail.getPrice() == 0)) {
                errs.append("price 为空");
                errs.append(" ,");
            }
            */

            if (preTradeDetail.getQty() == null || preTradeDetail.getQty() == 0) {
                errs.append("qty 为空");
                errs.append(" ,");
            }
            if (errs.length() > 0) {
                detailValid = false;
                preTradeDetail.setIsValid(false);
                preTradeDetail.setErrMsg(errs.toString());
            } else {
                preTradeDetail.setIsValid(true);
                preTradeDetail.setErrMsg(null);
            }
        }
        String errMsg = preTrade.getErrMsg() == null ? "" : preTrade.getErrMsg();
        if (!s.toString().isEmpty()) {
            s.append(" sku code 找不到");

            errMsg += s.toString();
            preTrade.setIsValid(false);
            preTrade.setErrMsg(errMsg);
        }
        try {
            errMsg = preTrade.getErrMsg() == null ? "" : preTrade.getErrMsg();
            StringBuffer errs = new StringBuffer(errMsg);
            if (!detailValid) {
                errs.append(" 订单明细错误");
            }
            if (preTrade.getPayment() == null) {
                errs.append(" 支付金额为空");
            }
            if (errs.length() > 0) {
                preTrade.setIsValid(false);
                preTrade.setErrMsg(errs.toString());
            }
        } catch (Exception e) {
            logger.error("validate PreTrade error:", e.getMessage());
        }

    }
}
