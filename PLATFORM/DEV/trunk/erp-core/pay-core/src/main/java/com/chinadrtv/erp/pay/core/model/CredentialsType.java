package com.chinadrtv.erp.pay.core.model;

import org.codehaus.jackson.annotate.JsonCreator;

/**
 * 证件类型.
 * User: 徐志凯
 * Date: 13-8-6
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public enum CredentialsType {
    IDENTITYCARD("身份证", 1),
    PASSORT("护照", 2);

    private String name;
    private int index;

    CredentialsType(String name,int index)
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
    public static CredentialsType fromIndex(Integer index) {
        if(index != null) {
            for(CredentialsType credentialsType : CredentialsType.values()) {
                if(credentialsType.getIndex()==index.intValue()) {
                    return credentialsType;
                }
            }

            throw new IllegalArgumentException(index + " is an invalid index.");
        }

        throw new IllegalArgumentException("A value was not provided.");
    }
}
