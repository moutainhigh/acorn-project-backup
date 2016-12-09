package com.chinadrtv.erp.oms.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * 资源文件映射类
 *  
 * @author haoleitao
 * @date 2013-1-30 上午11:20:07
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */

@Component
public class SourceConfigure {

    public List<String> getNetOrderTypeList() {
        return netOrderTypeList;
    }

    public void setNetOrderTypeList(List<String> netOrderTypeList) {
        this.netOrderTypeList = netOrderTypeList;
    }

    @Value("${NETORDER_CANCEL_TYPES}")
    public void initOrderTypes(String orderTypes)
    {
        if(StringUtils.isNotBlank(orderTypes))
        {
            String[] values=orderTypes.split(",");
            netOrderTypeList=new ArrayList<String>();
            for(String item:values)
            {
                if(StringUtils.isNotBlank(item))
                    netOrderTypeList.add(item);
            }
        }
    }

    private List<String> netOrderTypeList;
}
