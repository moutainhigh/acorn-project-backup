package com.chinadrtv.erp.admin.service.impl;
import com.chinadrtv.erp.model.PreTradeDetail;
import com.chinadrtv.erp.admin.service.PreTradeDetailService;
import com.chinadrtv.erp.admin.dao.PreTradeDetailDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service("preTradeDetailService")
public class PreTradeDetailServiceImpl implements PreTradeDetailService{

    @Autowired
    private PreTradeDetailDao preTradeDetailDao;


    public void addPreTradeDetail(PreTradeDetail preTradeDetail) {
    	PreTradeDetail pd=   preTradeDetailDao.save(preTradeDetail);
    	System.out.println("pd::"+pd.getId());
   
    }



    public void savePreTradeDetail(PreTradeDetail preTradeDetail) {
        preTradeDetailDao.saveOrUpdate(preTradeDetail);
    }

    public void delPreTradeDetail(Long id) {
        preTradeDetailDao.remove(id);
    }



	public PreTradeDetail getCompanyById(String preTradeDetailId) {
		// TODO Auto-generated method stub
		return null;
	}



	public List<PreTradeDetail> getAllPreTradeDetail() {
		// TODO Auto-generated method stub
		return null;
	}



	public List<PreTradeDetail> getAllPreTradeDetail(int index, int size) {
		// TODO Auto-generated method stub
		return null;
	}



	public int getPreTradeDetailCount() {
		// TODO Auto-generated method stub
		return 0;
	}



	public void removePreTradeDetail(PreTradeDetail preTradeDetail) {
		// TODO Auto-generated method stub
		
	}



	public List<PreTradeDetail> getAllPreTradeDetailByPerTradeID(
			String preTradeID) {
		// TODO Auto-generated method stub
		return preTradeDetailDao.getPreTradeDetailByPreTradeId(preTradeID);
	}
}
