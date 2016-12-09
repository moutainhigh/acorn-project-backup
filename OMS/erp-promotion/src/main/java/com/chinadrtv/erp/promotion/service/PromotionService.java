package com.chinadrtv.erp.promotion.service;

import com.chinadrtv.erp.promotion.dto.PretradeDto;
import com.chinadrtv.erp.promotion.dto.PromotionResultDto;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-8
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface PromotionService {
    PromotionResultDto promotionPretrade(List<PretradeDto> pretradeDtoList);
}
