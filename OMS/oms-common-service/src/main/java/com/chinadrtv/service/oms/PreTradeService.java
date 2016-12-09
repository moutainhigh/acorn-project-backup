package com.chinadrtv.service.oms;

import java.util.List;

import com.chinadrtv.model.oms.PreTrade;
import com.chinadrtv.model.oms.PreTradeDetail;

/**
 * User: liuhaidong
 * Date: 12-12-10
 */
public interface PreTradeService{
    
	PreTrade findByOpsId(String opsTradeId,Long sourceId);
    
//    String validatePreTrade(PreTrade preTrade);
    
    boolean checkSkuCode(String skuCode);

    void updateSkuTitle(PreTrade preTrade,List<PreTradeDetail> preTradeDetailList);
    
    String checkReceiverAddress(PreTrade preTrade);

    void updateTmsCodeAndPayType(PreTrade preTrade) ;

    List<String> getInsuranceSkus() ;

    void updateInsuranceSku(PreTrade preTrade, List<String> skus);

    PreTrade queryPreTradeByTradeId(String tradeId);

    void insert(PreTrade preTrade);

    void updatePreTrade(PreTrade preTrade);
}
