package com.chinadrtv.service.oms;

import java.math.BigDecimal;
import java.util.Set;

import com.chinadrtv.model.oms.PreTrade;
import com.chinadrtv.model.oms.PreTradeDetail;
import com.chinadrtv.model.oms.dto.PreTradeDto;

/**
 * User: liuhaidong
 * Date: 12-12-20
 */
public interface PriceReconService {
    void reconPrice(PreTradeDto preTrade);

    BigDecimal checkSumPrice(PreTradeDto preTrade);
    void assignDiff1(BigDecimal diff,PreTradeDto preTrade);
    Set<PreTradeDetail> assignDiff2(BigDecimal diff, PreTradeDetail preTradeDetail);
}
