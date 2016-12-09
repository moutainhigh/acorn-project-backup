package com.chinadrtv.erp.tc.core.dao;

import java.sql.SQLException;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.tc.core.model.ConTicket;

public interface CouponReviseDao extends GenericDao<ConTicket, String>
{
    public abstract void getCouponReviseproc(ConTicket paramConTicket)
            throws SQLException;
}