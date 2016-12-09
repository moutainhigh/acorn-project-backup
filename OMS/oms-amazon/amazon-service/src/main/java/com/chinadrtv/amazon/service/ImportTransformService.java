package com.chinadrtv.amazon.service;

import com.chinadrtv.model.oms.dto.PreTradeDto;
import com.chinadrtv.amazon.model.AmazonOrderConfig;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-10-29
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface ImportTransformService {
    List<PreTradeDto> transform(AmazonOrderConfig amazonOrderConfig, String jsonval);
}
