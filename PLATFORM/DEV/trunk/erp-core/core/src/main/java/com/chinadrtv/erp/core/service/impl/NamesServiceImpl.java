package com.chinadrtv.erp.core.service.impl;
import com.chinadrtv.erp.model.Names;
import com.chinadrtv.erp.model.NamesId;
import com.chinadrtv.erp.core.service.NamesService;
import com.chinadrtv.erp.core.dao.NamesDao;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service("namesService")
public class NamesServiceImpl implements NamesService{

    @Autowired
    private NamesDao namesDao;


    public void addNames(Names names) {
        namesDao.save(names);
    }


   public void saveNames(Names names) {
        namesDao.saveOrUpdate(names);
    }

    public void delNames(Long id) {
    	namesDao.remove(id.toString());
    }
	
	public Names getNamesById(String namesId){
		return namesDao.get(namesId);
	}



	@ReadThroughSingleCache(namespace = "com.chinadrtv.erp.oms.model.Names",expiration=3600)
	public List<Names> getAllNames(@ParameterValueKeyProvider String tid) {
		// TODO Auto-generated method stub
		return namesDao.getAllNames(tid);
	}



	public List<Names> getAllNames(int index, int size) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Names getNamesByID(NamesId namesId){
		return namesDao.getNamesByID(namesId);
	}





	public int getNamesCount() {
		// TODO Auto-generated method stub
		return 0;
	}




	public void removeNames(Names names) {
		// TODO Auto-generated method stub
		
	}


	public List<Names> getAllNames() {
		// TODO Auto-generated method stub
		return null;
	}

    public List<Names> getAllNames(String tid, String tdsc) {
        return namesDao.getAllNames(tid, tdsc);
    }
}
