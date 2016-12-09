package com.chinadrtv.amazon.biz;

import com.chinadrtv.amazon.model.AmazonOrderConfig;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-18
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class SourceConfigure {
    public List<AmazonOrderConfig> getAmazonOrderConfigList() {
        return amazonOrderConfigList;
    }

    public void setAmazonOrderConfigList(List<AmazonOrderConfig> amazonOrderConfigList) {
        this.amazonOrderConfigList = amazonOrderConfigList;
    }

    private List<AmazonOrderConfig> amazonOrderConfigList;
}
