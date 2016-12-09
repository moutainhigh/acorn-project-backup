package com.chinadrtv.erp.pay.core.icbc.model;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-8
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class MarshalEvent extends EventObjectBase {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public MarshalEvent(Object source, Marshal input) {
        super(source);
        this.input=input;
    }


    public Marshal getInput() {
        return input;
    }

    public void setInput(Marshal input) {
        this.input = input;
    }

    private Marshal input;

}
