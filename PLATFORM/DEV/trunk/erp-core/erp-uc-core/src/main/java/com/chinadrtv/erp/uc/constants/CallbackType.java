package com.chinadrtv.erp.uc.constants;

import org.codehaus.jackson.annotate.JsonCreator;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-1
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public enum CallbackType {
	IVR("IVR",1),
    IVR_WILCOM("IVR(井星)",11),
	GIVEUP("放弃",2),
    GIVEUP_WILCOM("放弃(井星)",12),
	CONNECTED("接通",3),
    CONNECTED_WILCOM("接通(井星)",13),
	VIRTUAL_PHONECALL("虚拟进线", 4),
	CALLBACK("Callback",5),
    CALLBACK_WILCOM("Callback(井星)",15);
    
    private String name;
    private int index;

    CallbackType(String name,int index)
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
    public String getStringIndex() {
    	return String.valueOf(index);
    }
    public void setIndex(int index) {
        this.index = index;
    }

    @JsonCreator
    public static CallbackType fromIndex(Integer index) {
        if(index != null) {
            for(CallbackType callbackType: CallbackType.values()) {
                if(callbackType.getIndex()==index.intValue()) {
                    return callbackType;
                }
            }

            throw new IllegalArgumentException(index + " is an invalid index.");
        }

        throw new IllegalArgumentException("A value was not provided.");
    }
}
