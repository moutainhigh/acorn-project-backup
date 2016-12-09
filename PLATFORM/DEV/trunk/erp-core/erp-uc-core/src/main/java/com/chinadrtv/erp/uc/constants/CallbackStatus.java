package com.chinadrtv.erp.uc.constants;

import org.codehaus.jackson.annotate.JsonCreator;

/**
 * Callback处理标志枚举.
 * User: 徐志凯
 * Date: 13-7-31
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public enum CallbackStatus {
    NOT_HANDLE("未处理", 0),
    IN_HANDLING("正在处理", 1),
    HANDLED("处理完毕", 2);

    private String name;
    private int index;

    CallbackStatus(String name,int index)
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
    public static CallbackStatus fromIndex(Integer index) {
        if(index != null) {
            for(CallbackStatus callbackStatus : CallbackStatus.values()) {
                if(callbackStatus.getIndex()==index.intValue()) {
                    return callbackStatus;
                }
            }

            throw new IllegalArgumentException(index + " is an invalid index.");
        }

        throw new IllegalArgumentException("A value was not provided.");
    }
}
