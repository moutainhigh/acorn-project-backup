package com.chinadrtv.erp.tc.core.service.impl;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.tc.core.dao.CouponReviseDao;
import com.chinadrtv.erp.tc.core.model.ConTicket;
import com.chinadrtv.erp.tc.core.service.CouponReviseService;

@Service("couponReviseService")
public class CouponReviseServiceImpl extends GenericServiceImpl<ConTicket, String>
        implements CouponReviseService
{
    private static final Logger logger = LoggerFactory.getLogger(CouponReviseServiceImpl.class);
    @Autowired
    private CouponReviseDao couponReviseDao;

    protected GenericDao<ConTicket, String> getGenericDao()
    {
        return this.couponReviseDao;
    }

    /**
     * 校正赠券
     */
    public void getCouponReviseproc(ConTicket conTicket) throws SQLException
    {
    	logger.debug("begin getCouponReviseproc");
        this.couponReviseDao.getCouponReviseproc(conTicket);
        logger.debug("end getCouponReviseproc");
    }
}