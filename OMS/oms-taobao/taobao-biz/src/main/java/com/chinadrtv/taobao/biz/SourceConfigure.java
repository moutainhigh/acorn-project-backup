package com.chinadrtv.taobao.biz;

import com.chinadrtv.taobao.model.TaobaoOrderConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-18
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class SourceConfigure {
    public List<TaobaoOrderConfig> getTaobaoOrderConfigList() {
        return taobaoOrderConfigList;
    }

    public void setTaobaoOrderConfigList(@Value("cfgList") List<TaobaoOrderConfig> taobaoOrderConfigList) {
        this.taobaoOrderConfigList = taobaoOrderConfigList;
    }

    private List<TaobaoOrderConfig> taobaoOrderConfigList;
}
