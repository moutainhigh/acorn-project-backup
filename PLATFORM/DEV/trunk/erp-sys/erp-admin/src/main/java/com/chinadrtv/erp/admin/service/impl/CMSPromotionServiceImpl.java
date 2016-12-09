package com.chinadrtv.erp.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.admin.dao.CMSPromotionDao;
import com.chinadrtv.erp.admin.dao.CompanyDao;
import com.chinadrtv.erp.admin.service.CMSPromotionService;
@Service("cMSPromotionService")
public class CMSPromotionServiceImpl implements CMSPromotionService {
    @Autowired
    private CMSPromotionDao cMSPromotionDao;
	public List getPromotion(String prodids, String moneys, String surid,
			String orderid) {
		
		return cMSPromotionDao.getPromotion(prodids, moneys, surid, orderid);
	}
	
	

}
