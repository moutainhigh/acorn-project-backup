package com.chinadrtv.erp.tc.core.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.RfError;
import com.chinadrtv.erp.tc.core.constant.model.shipment.RequestScanOutInfo;
import com.chinadrtv.erp.tc.core.constant.model.shipment.ShipmentReturnCode;
import com.chinadrtv.erp.tc.core.dao.RfErrorDao;
import com.chinadrtv.erp.tc.core.service.RfErrorService;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-2-21
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class RfErrorServiceImpl extends GenericServiceImpl<RfError, Long> implements RfErrorService {

    @Autowired
    private RfErrorDao rfErrorDao;

    @Override
    protected GenericDao<RfError, Long> getGenericDao() {
        return this.rfErrorDao;
    }

    public void saveScanOutErrorInfo(ShipmentReturnCode shipmentReturnCode, RequestScanOutInfo requestScanOutInfo)
    {
        RfError rfError=new RfError();
        rfError.setLogRunid(99);
        rfError.setLogType(99);
        rfError.setLogAppend(requestScanOutInfo.getOrderId()+"-"+requestScanOutInfo.getUser()+'-'+requestScanOutInfo.getMailId()+"--"+shipmentReturnCode.getDesc());
        rfError.setOrderid(requestScanOutInfo.getOrderId());
        rfError.setOutpsn(requestScanOutInfo.getUser());
        rfError.setLogDate(new Date());
        rfError.setErrtype(Integer.parseInt(shipmentReturnCode.getCode()));
        rfError.setMailid(requestScanOutInfo.getMailId());

        rfErrorDao.save(rfError);
    }
}
