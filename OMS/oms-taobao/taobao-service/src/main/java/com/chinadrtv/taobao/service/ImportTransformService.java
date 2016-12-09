package com.chinadrtv.taobao.service;

import com.chinadrtv.model.oms.PreTrade;
import com.chinadrtv.model.oms.dto.PreTradeDto;
import com.chinadrtv.taobao.model.TaobaoOrderConfig;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-10-29
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface ImportTransformService {
    List<PreTradeDto> transform(TaobaoOrderConfig taobaoOrderConfig, List<String> tradeList);
}
