package com.chinadrtv.erp.uc.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.NamesService;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.model.Names;
import com.chinadrtv.erp.model.agent.Conpointcr;
import com.chinadrtv.erp.model.agent.Conpointuse;
import com.chinadrtv.erp.uc.dao.ConpointuseDao;
import com.chinadrtv.erp.uc.service.ConpointuseService;

@Service("conpointuseService")
public class ConpointuseServiceImpl extends GenericServiceImpl<Conpointuse,String> implements ConpointuseService{

    @Autowired
    private ConpointuseDao conpointuseDao;
    
    @Autowired
    private NamesService namesService;
	@Override
	protected GenericDao<Conpointuse, String> getGenericDao() {
		return conpointuseDao;
	}


    public void addConpointuse(Conpointuse conpointuse) throws ServiceException {
    	try{
    		conpointuseDao.save(conpointuse);
    	}catch(Exception e){
    		throw new ServiceException("添加使用积分失败");
    	}
    	
    }



    public void saveConpointuse(Conpointuse conpointuse) throws ServiceException {
    	try{
    	    conpointuseDao.saveOrUpdate(conpointuse);
    	}catch(Exception e){
    		throw new ServiceException("添加或更新使用积分失败");
    	}
    
    }

    public void delConpointuse(Long id) {
        conpointuseDao.remove(String.valueOf(id));
    }
	
	public Conpointuse getConpointuseById(String conpointuseId){
		return conpointuseDao.get(conpointuseId);
	}



	public List<Conpointuse> getAllConpointuseByContactId(String contactId,int index, int size) {
		List<Conpointuse> list = conpointuseDao.getAllConpointuseByContactId(contactId, index, size);
		List<Conpointuse> result= new ArrayList();
		List<Names> listNames=namesService.getAllNames("ORDERFEEDBACK");
		Map map = new HashMap();
		for (Names names:listNames){
			map.put(names.getId().getId(), names.getDsc());
		}
		map.put("99", "正常订单");
		for(Conpointuse conpointuse:list){
			conpointuse.setType(map.get(conpointuse.getType()).toString());
			result.add(conpointuse);
		}
		return result;
	}





	public void removeConpointuse(Conpointuse conpointuse) {
		// TODO Auto-generated method stub
		
	}



	public int getAllConpointuseByContactIdCount(String contactId) {
		return conpointuseDao.getAllConpointuseByContactIdCount(contactId);
	}


	@Override
	public Double getUseIntegralByContactId(String contactId) {
		return conpointuseDao.getUseIntegralByContactId(contactId);
	}

}
