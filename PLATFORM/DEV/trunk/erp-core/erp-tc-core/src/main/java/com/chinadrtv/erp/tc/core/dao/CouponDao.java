package com.chinadrtv.erp.tc.core.dao;

import java.sql.SQLException;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.tc.core.model.ConTicket;

@Deprecated
public interface CouponDao extends GenericDao<ConTicket, String>
{
    public abstract String getCouponproc(ConTicket paramConTicket)throws SQLException;
}