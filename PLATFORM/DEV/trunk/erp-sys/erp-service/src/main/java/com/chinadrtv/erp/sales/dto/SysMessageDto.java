package com.chinadrtv.erp.sales.dto;

import com.chinadrtv.erp.model.SysMessage;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-7-7
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class SysMessageDto extends SysMessage implements Serializable,Comparable {
    public String getOrgSourceType() {
        return orgSourceType;
    }

    public void setOrgSourceType(String orgSourceType) {
        this.orgSourceType = orgSourceType;
    }

    private String orgSourceType;

    public int compareTo(Object o) {
        //首先按照类型排序
        if(o instanceof SysMessageDto)
        {
            SysMessageDto other=(SysMessageDto)o;
            int i1=Integer.parseInt(this.getSourceTypeId());
            int i2=Integer.parseInt(other.getSourceTypeId());
            return i1-i2;
        }
        else
        {
            return 1;
        }
    }
}
