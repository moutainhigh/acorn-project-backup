package com.chinadrtv.erp.promotion.service.impl;

import com.chinadrtv.erp.core.service.PromotionEngineService;
import com.chinadrtv.erp.promotion.dto.PretradeDetailDto;
import com.chinadrtv.erp.promotion.dto.PretradeDto;
import com.chinadrtv.erp.promotion.dto.PretradePromotionDto;
import com.chinadrtv.erp.promotion.dto.PromotionResultDto;
import com.chinadrtv.erp.model.PreTrade;
import com.chinadrtv.erp.model.PreTradeDetail;
import com.chinadrtv.erp.model.PromotionRuleResult;
import com.chinadrtv.erp.model.PromotionRuleResult_FreeProduct;
import com.chinadrtv.erp.promotion.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-8
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class PromotionServiceImpl implements PromotionService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PromotionServiceImpl.class);

    public PromotionServiceImpl()
    {
        logger.debug("PromotionServiceImpl is created");
    }
    @Autowired
    private PromotionEngineService promotionEngineService;

    public PromotionResultDto promotionPretrade(List<PretradeDto> pretradeDtoList) {
        PromotionResultDto promotionResultDto=new PromotionResultDto();
        try{
            List<PreTrade> preTradeList=this.convertFromDto(pretradeDtoList);
            if(preTradeList==null)
                return promotionResultDto;

            Map<String, Object> context = new HashMap<String, Object>();
            for(PreTrade preTrade:preTradeList)
            {
                logger.debug("begin promotion trade id:"+preTrade.getTradeId());
                logger.debug("data: payment-"+preTrade.getPayment()+" type:"+preTrade.getTradeType()+" outDate:"+preTrade.getOutCrdt());
                for(PreTradeDetail preTradeDetail:preTrade.getPreTradeDetails())
                {
                    logger.debug("sku id:"+preTradeDetail.getOutSkuId());
                }
                context.clear();
                context.put("SUM_AMOUNT", preTrade.getPayment());
                List<PromotionRuleResult> promotionRuleResults = promotionEngineService.getPromotionResult(preTrade, context, 0);

                logger.debug("promotion rule results:"+promotionRuleResults.size());

                promotionRuleResults= promotionEngineService.savePromotionResultt(promotionRuleResults);

                logger.debug("save promotion results");

                List<PretradePromotionDto> pretradePromotionDtoList=this.getPromotionFromResult(preTrade,promotionRuleResults);
                if(pretradePromotionDtoList!=null&&pretradePromotionDtoList.size()>0)
                {
                    promotionResultDto.getPromotionList().addAll(pretradePromotionDtoList);
                }

                logger.debug("end promotion trade id:"+preTrade.getTradeId());
            }
        }catch (Exception exp)
        {
            promotionResultDto.setSucc(false);
            promotionResultDto.setErrorMsg(exp.getMessage());
            promotionResultDto.setPromotionList(null);
            logger.error("promotion error:", exp);
        }
        return promotionResultDto;
    }

    private List<PretradePromotionDto> getPromotionFromResult(PreTrade preTrade, List<PromotionRuleResult> promotionRuleResults)
    {
        List<PretradePromotionDto> pretradePromotionDtoList=new ArrayList<PretradePromotionDto>();
        for (PromotionRuleResult promotionRuleResult : promotionRuleResults) {
            if (promotionRuleResult instanceof PromotionRuleResult_FreeProduct) {
                for (PromotionRuleResult promotionRuleResultc : promotionRuleResult.getChildResult()) {
                    PromotionRuleResult_FreeProduct freeProduct = (PromotionRuleResult_FreeProduct) promotionRuleResultc;
                    if (freeProduct.getProductId() != null) {
                        String skucodes[] = freeProduct.getProductId().split(",");
                        for (String skuCode : skucodes) {
                            if (!"".equals(skuCode)) {
                                PretradePromotionDto pretradePromotionDto=new PretradePromotionDto();

                                //preTradeDetail.setPreTrade(preTrade);
                                pretradePromotionDto.setQty(freeProduct.getUnitNumber());
                                //preTradeDetail.setPrice(0d);
                                //preTradeDetail.setUpPrice(0d);
                                pretradePromotionDto.setTradeId(preTrade.getTradeId());
                                pretradePromotionDto.setResultId(freeProduct.getId());
                                //preTradeDetail.setIsVaid(true);
                                pretradePromotionDto.setOutSkuId(skuCode);
                                //preTrade.getPreTradeDetails().add(preTradeDetail);
                                pretradePromotionDtoList.add(pretradePromotionDto);
                            }
                        }
                    }
                }
            }
        }

        return pretradePromotionDtoList;
    }

    private List<PreTrade> convertFromDto(List<PretradeDto> pretradeDtoList)
    {
        if(pretradeDtoList!=null&&pretradeDtoList.size()>0)
        {
            List<PreTrade> preTradeList=new ArrayList<PreTrade>();
            for(PretradeDto pretradeDto:pretradeDtoList)
            {
                PreTrade preTrade=new PreTrade();
                preTrade.setTradeType(pretradeDto.getTradeType());
                preTrade.setTradeId(pretradeDto.getTradeId());
                preTrade.setPayment(pretradeDto.getPayment().doubleValue());
                preTrade.setOutCrdt(pretradeDto.getOutCrdt());


                for(PretradeDetailDto pretradeDetailDto:pretradeDto.getDetails())
                {
                    PreTradeDetail preTradeDetail=new PreTradeDetail();
                    preTradeDetail.setOutSkuId(pretradeDetailDto.getOutSkuId());
                    preTradeDetail.setPreTrade(preTrade);

                    preTrade.getPreTradeDetails().add(preTradeDetail);
                }

                preTradeList.add(preTrade);
            }


            return preTradeList;
        }
        return null;
    }
}
