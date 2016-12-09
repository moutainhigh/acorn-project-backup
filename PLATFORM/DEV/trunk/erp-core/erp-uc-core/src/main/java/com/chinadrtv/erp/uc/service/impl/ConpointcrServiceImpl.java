package com.chinadrtv.erp.uc.service.impl;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.model.agent.Conpointcr;
import com.chinadrtv.erp.uc.dao.ConpointcrDao;
import com.chinadrtv.erp.uc.dao.SqlDao;
import com.chinadrtv.erp.uc.service.ConpointcrService;

@Service("conpointcrService")
public class ConpointcrServiceImpl extends GenericServiceImpl<Conpointcr,String>  implements ConpointcrService{
 
	public static final int aaa = 1;
	
    @Autowired
    private ConpointcrDao conpointcrDao;
    
    @Autowired
    private SqlDao sqlDao;

	@Override
	protected GenericDao<Conpointcr, String> getGenericDao() {
		return conpointcrDao;
	}

    public void addConpointcr(Conpointcr conpointcr) throws ServiceException {
    	try{
        conpointcrDao.save(conpointcr);
    	}catch(Exception e){
          	throw new ServiceException("添加 创建积分失败:"+e.getMessage());
    	}
    }



    public void saveConpointcr(Conpointcr conpointcr) throws ServiceException {
    	try{
        conpointcrDao.saveOrUpdate(conpointcr);
    	}catch(Exception e){
          	throw new ServiceException("添加或修改 创建积分失败:"+e.getMessage());
    	}
    }

    public void delConpointcr(Long id) 	throws ServiceException{
        try{
        	  conpointcrDao.remove(String.valueOf(id));
        }catch(Exception e){
        	throw new ServiceException("删除创建积分失败:"+e.getMessage());
        }
      
    }
	
	public Conpointcr getConpointcrById(String conpointcrId){
		return conpointcrDao.get(conpointcrId);
	}



	public List<Conpointcr> getAllConpointcr() {

		return null;
	}



	public List<Conpointcr> getAllConpointcrByContactId(String contactId,int index, int size) {
		List<Conpointcr> list =conpointcrDao.getAllConpointcrByContactId(contactId, index, size); 
		return list;
	}


	public int getAllConpointcrByContactIdCount(String contactId) {

		return conpointcrDao.getAllConpointcrByContactIdCount(contactId);
	}
	


	public void removeConpointcr(Conpointcr conpointcr) {
     //to do 
		
	}

	public void conpointfeedback(String sorderid, String scrusr) throws ServiceException {

		List inParams = new ArrayList();
		inParams.add(sorderid);
		inParams.add(scrusr);
		  try{
		     sqlDao.exeStoredProcedure("iagent.p_n_conpointfeedback", inParams, 0);
	        }catch(Exception e){
	        	throw new ServiceException("校验积分失败:"+e.getMessage());
	        }
	
	}

}
