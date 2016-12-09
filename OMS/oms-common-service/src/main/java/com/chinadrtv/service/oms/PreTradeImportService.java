package com.chinadrtv.service.oms;

import com.chinadrtv.model.oms.dto.PreTradeLotDto;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-11
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface PreTradeImportService {
    void importPretrades(PreTradeLotDto preTradeLotDto);

    void splitPreTrades(String tradeId);
}
