package com.chinadrtv.erp.oms.service.impl;
import com.chinadrtv.erp.model.PreTradeLot;
import com.chinadrtv.erp.oms.service.PreTradeLotService;
import com.chinadrtv.erp.oms.dao.PreTradeLotDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * 前置订单批次服务
 * @author haoleitao
 *
 */
@Service("preTradeLotService")
public class PreTradeLotServiceImpl implements PreTradeLotService{

    @Autowired
    private PreTradeLotDao preTradeLotDao;


    public void addPreTradeLot(PreTradeLot preTradeLot) {
        preTradeLotDao.save(preTradeLot);
    }



    public void savePreTradeLot(PreTradeLot preTradeLot) {
        preTradeLotDao.saveOrUpdate(preTradeLot);
    }

    public void delPreTradeLot(Long id) {
        preTradeLotDao.remove(id);
    }



	public PreTradeLot getCompanyById(String preTradeLotId) {
		// TODO Auto-generated method stub
		return null;
	}



	public List<PreTradeLot> getAllPreTradeLot() {
		// TODO Auto-generated method stub
		return null;
	}



	public List<PreTradeLot> getAllPreTradeLot(int index, int size) {
		// TODO Auto-generated method stub
		return null;
	}



	public int getPreTradeLotCount() {
		// TODO Auto-generated method stub
		return 0;
	}



	public void removePreTradeLot(PreTradeLot preTradeLot) {
		// TODO Auto-generated method stub
		
	}
}
