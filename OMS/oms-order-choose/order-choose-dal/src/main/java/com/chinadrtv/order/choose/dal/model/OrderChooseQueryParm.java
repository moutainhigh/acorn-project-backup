package com.chinadrtv.order.choose.dal.model;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-4-23
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class OrderChooseQueryParm {
    private Integer nDays;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    private Integer limit;

    public Integer getnDays() {
        return nDays;
    }

    public void setnDays(Integer nDays) {
        this.nDays = nDays;
    }
}
