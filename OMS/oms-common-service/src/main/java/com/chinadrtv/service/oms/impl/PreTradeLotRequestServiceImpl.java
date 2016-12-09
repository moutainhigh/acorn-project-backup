package com.chinadrtv.service.oms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.dal.oms.dao.PreTradeLotRequestDao;
import com.chinadrtv.model.oms.PreTradeLotRequest;
import com.chinadrtv.service.oms.PreTradeLotRequestService;

/**
 * User: liuhaidong
 * Date: 12-12-14
 */
@Service("preTradeLotRequestService")
public class PreTradeLotRequestServiceImpl implements PreTradeLotRequestService {
    @Autowired
    private PreTradeLotRequestDao preTradeLotRequestDao;

    public PreTradeLotRequest findLastSuccess(Integer sourceId) {
        return preTradeLotRequestDao.findLastSuccess(sourceId);
    }

	/** 
	 * <p>Title: insert</p>
	 * <p>Description: </p>
	 * @param preTradeLotRequest
	 * @see com.chinadrtv.service.oms.PreTradeLotRequestService#insert(com.chinadrtv.model.oms.PreTradeLotRequest)
	 */ 
	@Override
	public void insert(PreTradeLotRequest preTradeLotRequest) {
		preTradeLotRequestDao.insertData(preTradeLotRequest);
	}
}
