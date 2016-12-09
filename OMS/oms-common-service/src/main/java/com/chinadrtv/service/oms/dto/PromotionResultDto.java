package com.chinadrtv.service.oms.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-8
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class PromotionResultDto implements java.io.Serializable{
    public PromotionResultDto()
    {
        promotionList =new ArrayList<PretradePromotionDto>();
        succ=true;
    }
    private List<PretradePromotionDto> promotionList;

    public List<PretradePromotionDto> getPromotionList() {
        return promotionList;
    }

    public void setPromotionList(List<PretradePromotionDto> promotionList) {
        this.promotionList = promotionList;
    }

    public boolean isSucc() {
        return succ;
    }

    public void setSucc(boolean succ) {
        this.succ = succ;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    private boolean succ;
    private String errorMsg;
}
