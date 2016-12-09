package com.chinadrtv.erp.sales.dto;

import java.io.Serializable;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-12-17
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class OrderNoteDto implements Serializable {
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getNewNote() {
        return newNote;
    }

    public void setNewNote(String newNote) {
        this.newNote = newNote;
    }

    private String orderId;
    private String newNote;
}
