package com.chinadrtv.service.oms.impl;

import com.chinadrtv.model.oms.PreTradeDetail;
import com.chinadrtv.model.oms.dto.PreTradeDto;
import com.chinadrtv.service.oms.PromotionService;
import com.chinadrtv.service.oms.dto.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-11
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class PromotionServiceImpl implements PromotionService {

    private RestTemplate restTemplate;

    private final static String mokUrl="moktest";

    @Value("${env_promotion_restful_url}")
    private String promotionUrl;

    @PostConstruct
    private void init()
    {
        restTemplate=new RestTemplate();
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter=new MappingJackson2HttpMessageConverter();

        List<MediaType> mediaTypeList=new ArrayList<MediaType>();
        mediaTypeList.add(MediaType.APPLICATION_JSON);
        //jackson2HttpMessageConverter.setSupportedMediaTypes();
        //jackson2HttpMessageConverter.getSupportedMediaTypes().add(MediaType.APPLICATION_JSON);
        //JsonProcessingException
        jackson2HttpMessageConverter.setSupportedMediaTypes(mediaTypeList);
        restTemplate.getMessageConverters().add(jackson2HttpMessageConverter);
    }

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(PromotionServiceImpl.class);

    @Override
    public boolean promotion(List<PreTradeDto> preTradeDtoList) {
        if(StringUtils.isEmpty(promotionUrl) || mokUrl.endsWith(promotionUrl))
        {
            logger.warn("mok promotion");
            return true;
        }
        List<PretradeDto> pretradeDtoList =new ArrayList<PretradeDto>();
        for(PreTradeDto preTradeDto:preTradeDtoList)
        {
            PretradeDto pretradeDto=new PretradeDto();
            pretradeDto.setOutCrdt(preTradeDto.getOutCrdt());
            pretradeDto.setPayment(new BigDecimal(preTradeDto.getPayment()));
            pretradeDto.setTradeId(preTradeDto.getTradeId());
            pretradeDto.setTradeType(preTradeDto.getTradeType());

            for(PreTradeDetail preTradeDetail:preTradeDto.getPreTradeDetails())
            {
                PretradeDetailDto pretradeDetailDto=new PretradeDetailDto();
                pretradeDetailDto.setOutSkuId(preTradeDetail.getOutSkuId());

                pretradeDto.getDetails().add(pretradeDetailDto);
            }

            pretradeDtoList.add(pretradeDto);
        }

        if(preTradeDtoList==null||preTradeDtoList.size()==0)
            return true;

       /* PretradeEvalDto pretradeEvalDto=new PretradeEvalDto();
        for(PretradeDto pretradeDto:)*/
        logger.debug("begin call promotion");
        PromotionResultDto promotionResultDto=restTemplate.postForObject(promotionUrl,pretradeDtoList,PromotionResultDto.class);
        logger.debug("end call promotion");
        if(promotionResultDto.isSucc())
        {
            //处理结果
            logger.debug("call promotion succ");
            for(PretradePromotionDto pretradePromotionDto:promotionResultDto.getPromotionList())
            {
                for(PreTradeDto preTradeDto:preTradeDtoList)
                {
                    if(preTradeDto.getTradeId().equals(pretradePromotionDto.getTradeId()))
                    {
                        PreTradeDetail preTradeDetail = new PreTradeDetail();
                        //preTradeDetail.setPreTradeId(preTradeDto.getTradeId());
                        preTradeDetail.setQty(pretradePromotionDto.getQty());
                        preTradeDetail.setPrice(0d);
                        preTradeDetail.setUpPrice(0d);
                        preTradeDetail.setTradeId(preTradeDto.getTradeId());
                        preTradeDetail.setPromotionResultId(pretradePromotionDto.getResultId());
                        preTradeDetail.setIsValid(true);
                        preTradeDetail.setIsActive(true);
                        preTradeDetail.setOutSkuId(pretradePromotionDto.getOutSkuId());
                        preTradeDto.getPreTradeDetails().add(preTradeDetail);
                        break;
                    }
                }
            }
        }
        else
        {
            logger.error("promotion call error:"+promotionResultDto.getErrorMsg());
        }

        return promotionResultDto.isSucc();
    }
}
