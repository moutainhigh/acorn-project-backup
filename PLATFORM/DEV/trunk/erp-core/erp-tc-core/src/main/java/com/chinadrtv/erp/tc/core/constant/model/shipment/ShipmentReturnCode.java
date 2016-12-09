package com.chinadrtv.erp.tc.core.constant.model.shipment;

import com.chinadrtv.erp.tc.core.model.ReturnInfo;

/**
 * 运单服务返回值 (TC).
 * User: 徐志凯
 * Date: 13-2-19
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class ShipmentReturnCode extends ReturnInfo {
    public ShipmentReturnCode(String code,String desc)
    {
        this.setCode(code);
        this.setDesc(desc);
    }

    public ShipmentReturnCode()
    {

    }
}
