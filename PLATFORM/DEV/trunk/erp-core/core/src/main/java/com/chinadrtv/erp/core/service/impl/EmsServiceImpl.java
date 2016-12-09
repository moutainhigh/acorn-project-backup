package com.chinadrtv.erp.core.service.impl;
import com.chinadrtv.erp.model.Ems;
import com.chinadrtv.erp.core.service.EmsService;
import com.chinadrtv.erp.core.dao.EmsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * EmsServiceImpl
 *  
 * @author haoleitao
 * @date 2013-3-4 下午2:47:19
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service("emsService")
public class EmsServiceImpl implements EmsService{

    @Autowired
    private EmsDao emsDao;


    public void addEms(Ems ems) {
        emsDao.save(ems);
    }



    public void saveEms(Ems ems) {
        emsDao.saveOrUpdate(ems);
    }

    public void delEms(Long id) {
       // emsDao.remove(id);
    }
	
	public Ems getEmsById(String emsId){
		return emsDao.get(Integer.valueOf(emsId));
	}



	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.oms.service.EmsService#getAllEms()
	 */
	public List<Ems> getAllEms() {
		// TODO Auto-generated method stub
		return emsDao.getAll();
	}



	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.oms.service.EmsService#getAllEms(int, int)
	 */
	public List<Ems> getAllEms(int index, int size) {
		// TODO Auto-generated method stub
		return null;
	}



	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.oms.service.EmsService#getEmsCount()
	 */
	public int getEmsCount() {
		// TODO Auto-generated method stub
		return 0;
	}



	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.oms.service.EmsService#removeEms(com.chinadrtv.erp.model.Ems)
	 */
	public void removeEms(Ems ems) {
		// TODO Auto-generated method stub
		
	}


    public Ems getEmsBySpellid(Integer spellid){
        Ems ems = emsDao.getEmsBySpellid(spellid);
        return ems;
    }


    /* (non-Javadoc)
     * @see com.chinadrtv.erp.oms.service.EmsService#getCityNameById(java.lang.String)
     */
	public String getCityNameById(String cityId) {
		// TODO Auto-generated method stub
		return null;
	}
}
