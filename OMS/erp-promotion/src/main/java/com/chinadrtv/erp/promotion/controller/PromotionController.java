package com.chinadrtv.erp.promotion.controller;

import com.chinadrtv.erp.promotion.dto.*;
import com.chinadrtv.erp.promotion.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-8
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Controller
@RequestMapping({ "/promotion" })
public class PromotionController {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PromotionController.class);

    @Autowired
    private PromotionService promotionService;

    @RequestMapping(value = "/evaluate",method= RequestMethod.POST, headers="Content-Type=application/json")
    @ResponseBody
    public PromotionResultDto promotionPretrade(@RequestBody PretradeEvalDto pretradeEvalDto)
    {
        List<PretradeDto> pretradeDtoList=pretradeEvalDto;
        //使用订单类型过滤促销
        logger.debug("begin promotion");
        Long startStamp = System.currentTimeMillis();

        PromotionResultDto promotionResultDto=promotionService.promotionPretrade(pretradeDtoList);
        logger.debug("end promotion");
        Long endStamp = System.currentTimeMillis();
        logger.debug("promotion costs: " + (endStamp - startStamp));

        return promotionResultDto;
    }

    @ExceptionHandler
    @ResponseBody
    public PromotionResultDto handleException(Exception ex, HttpServletRequest request) {
        logger.error("system error:",ex);
        logger.error("request info:"+request.getRequestURI());

        PromotionResultDto promotionResultDto=new PromotionResultDto();
        promotionResultDto.setErrorMsg(ex.getMessage());
        promotionResultDto.setSucc(false);
        return promotionResultDto;
    }
}
