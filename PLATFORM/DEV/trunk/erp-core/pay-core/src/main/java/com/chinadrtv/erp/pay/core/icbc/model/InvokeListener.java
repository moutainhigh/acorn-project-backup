package com.chinadrtv.erp.pay.core.icbc.model;

import com.chinadrtv.erp.pay.core.icbc.model.*;

import java.util.EventListener;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-8
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface InvokeListener extends EventListener {
    void marshaling(MarshalEvent marshalEvent);
    void marshaled(TransformEvent transformEvent);
    void transforming(TransformEvent transformEvent);
    void transformed(InvokeEvent invokeEvent);
    void invoking(InvokeEvent invokeEvent);
    void invoked(InvokeEvent invokeEvent);
    void reverseTransforming(InvokeEvent invokeEvent);
    void reverseTransformed(TransformEvent transformEvent);
    void unmarshaling(TransformEvent transformEvent);
    void unmarshaled(MarshalEvent marshalEvent);
}
