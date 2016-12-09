package com.chinadrtv.erp.uc.service.impl;
import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.dao.query.Criteria;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.model.client.EntityView;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.agent.Cmpfback;
import com.chinadrtv.erp.model.marketing.LeadInteraction;
import com.chinadrtv.erp.model.agent.Cases;
import com.chinadrtv.erp.uc.service.CasesService;
import com.chinadrtv.erp.uc.dao.CasesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
/**
 * 
 *  API-UC-32.	查询服务历史
 * @author haoleitao
 * @date 2013-5-14 下午4:18:36
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service("casesService")
public class CasesServiceImpl extends GenericServiceImpl<Cases, String> implements CasesService{

    @Autowired
    private CasesDao casesDao;
    

	@Override
	protected GenericDao getGenericDao() {
		// TODO Auto-generated method stub
		return casesDao;
	}


    public void addCases(Cases cases) {
        casesDao.save(cases);
    }



    public void saveCases(Cases cases) {
        casesDao.saveOrUpdate(cases);
    }

    public void delCases(Long id) {
        casesDao.remove(String.valueOf(id));
    }
	
	public Cases getCasesById(String casesId){
		return casesDao.get(casesId);
	}



	public List<Cases> getAllCasesByContactId(String contactId,int index, int size) {
		// TODO Auto-generated method stub
		return casesDao.getAllCasesByContactId(contactId, index, size);
	}



	public int getAllCasesByContactIdCount(String contactId) {
		// TODO Auto-generated method stub
		return casesDao.getAllCasesByContactIdCount(contactId);
	}


    public Cmpfback getCmpfbackByCaseId(String caseId) {
        return  casesDao.getCmpfbackByCaseId(caseId);

    }


    public void removeCases(Cases cases) {
		// TODO Auto-generated method stub
		
	}






}
