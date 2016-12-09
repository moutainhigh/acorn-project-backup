package com.chinadrtv.erp.tc.core.service;

import java.sql.SQLException;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.tc.core.model.ConTicket;

public interface CouponService extends GenericService<ConTicket, String>
{
    public abstract String getCouponproc(ConTicket conTicket)
            throws SQLException;
}