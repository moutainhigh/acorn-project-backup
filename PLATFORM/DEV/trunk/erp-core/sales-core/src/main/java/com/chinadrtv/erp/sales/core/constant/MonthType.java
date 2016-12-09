package com.chinadrtv.erp.sales.core.constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.codehaus.jackson.annotate.JsonCreator;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-7-8
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
//@JsonFormat(shape= JsonFormat.Shape.OBJECT)
public enum MonthType {
    THISMONTH("本月",0),
    LASTMONTH("上个月",1),
    EARLIERMONTH("更早",2);

    private String name;
    private int index;

    MonthType(String name,int index)
    {
        this.name=name;
        this.index=index;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }

    @JsonCreator
    public static MonthType fromIndex(Integer index) {
        if(index != null) {
            for(MonthType monthType : MonthType.values()) {
                if(monthType.getIndex()==index.intValue()) {
                    return monthType;
                }
            }

            throw new IllegalArgumentException(index + " is an invalid index.");
        }

        throw new IllegalArgumentException("A value was not provided.");
    }

}
