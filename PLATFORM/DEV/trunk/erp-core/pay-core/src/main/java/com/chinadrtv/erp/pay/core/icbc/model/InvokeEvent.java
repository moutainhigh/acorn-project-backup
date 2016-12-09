package com.chinadrtv.erp.pay.core.icbc.model;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-8
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class InvokeEvent extends EventObjectBase {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public InvokeEvent(Object source, byte[] data) {
        super(source);
        this.data=data;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    private byte[] data;
}
