package com.chinadrtv.erp.pay.core.icbc.model;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-8
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface Marshal {
    String marshal();
    String unmarshal(String data);
}
