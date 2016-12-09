package com.chinadrtv.erp.uc.constants;

import org.codehaus.jackson.annotate.JsonCreator;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-1
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public enum CallbackPriority {
    NORMAL("普通", 100),
    URGENT("紧急", 150);

    private String name;
    private int index;

    CallbackPriority(String name,int index)
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
    public static CallbackPriority fromIndex(Integer index) {
        if(index != null) {
            for(CallbackPriority callbackPriority : CallbackPriority.values()) {
                if(callbackPriority.getIndex()==index.intValue()) {
                    return callbackPriority;
                }
            }

            throw new IllegalArgumentException(index + " is an invalid index.");
        }

        throw new IllegalArgumentException("A value was not provided.");
    }
}
