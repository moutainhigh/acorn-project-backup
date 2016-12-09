package com.chinadrtv.erp.tc.core.service;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.RfError;
import com.chinadrtv.erp.tc.core.constant.model.shipment.RequestScanOutInfo;
import com.chinadrtv.erp.tc.core.constant.model.shipment.ShipmentReturnCode;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-2-21
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface RfErrorService extends GenericService<RfError, Long> {
    void saveScanOutErrorInfo(ShipmentReturnCode shipmentReturnCode, RequestScanOutInfo requestScanOutInfo);
}
