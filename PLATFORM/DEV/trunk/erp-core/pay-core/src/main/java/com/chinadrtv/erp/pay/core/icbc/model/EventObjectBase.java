package com.chinadrtv.erp.pay.core.icbc.model;

import java.util.EventObject;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-8
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class EventObjectBase extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public EventObjectBase(Object source) {
        super(source);
        bSucc=true;
    }

    public boolean isbSucc() {
        return bSucc;
    }

    public void setbSucc(boolean bSucc) {
        this.bSucc = bSucc;
    }

    private boolean bSucc;
}
