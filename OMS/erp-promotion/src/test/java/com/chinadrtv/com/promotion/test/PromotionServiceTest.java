package com.chinadrtv.com.promotion.test;

import com.chinadrtv.erp.promotion.dto.PretradeDetailDto;
import com.chinadrtv.erp.promotion.dto.PretradeDto;
import com.chinadrtv.erp.promotion.dto.PromotionResultDto;
import com.chinadrtv.erp.promotion.service.PromotionService;
import com.chinadrtv.erp.test.SpringTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-8
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class PromotionServiceTest extends SpringTest {
    @Autowired
    private PromotionService promotionService;

    @Test
    public void testPromotionService() throws Exception
    {
        List<PretradeDto> pretradeDtoList=new ArrayList<PretradeDto>();
        PretradeDto pretradeDto=new PretradeDto();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");

        pretradeDto.setOutCrdt(simpleDateFormat.parse("2013-11-25"));
        pretradeDto.setPayment(new BigDecimal("158"));
        pretradeDto.setTradeId("463917927418593");
        pretradeDto.setTradeType("221");

        PretradeDetailDto pretradeDetailDto=new PretradeDetailDto();
        pretradeDetailDto.setOutSkuId("106072710004");

        pretradeDto.getDetails().add(pretradeDetailDto);

        pretradeDtoList.add(pretradeDto);


        PromotionResultDto promotionResultDto=promotionService.promotionPretrade(pretradeDtoList);

        System.out.println("result is :"+promotionResultDto.isSucc());
    }
}
