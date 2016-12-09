package com.chinadrtv.erp.oms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.model.PreTradeCompany;
import com.chinadrtv.erp.oms.dao.AvoidFreightDao;
import com.chinadrtv.erp.oms.dao.PreTradeCompanyDao;
import com.chinadrtv.erp.oms.service.PreTradeCompanyService;

@Service("preTradeCompanyService")
public class PreTradeCompanyServiceImpl implements PreTradeCompanyService {
    @Autowired
    private PreTradeCompanyDao preTradeCompanyDao;
	public List<PreTradeCompany> getPreTradeCompanyBySourceid(Long sourceId) {
		// TODO Auto-generated method stub
		return preTradeCompanyDao.getPreTradeCompanyBySourceid(sourceId);
	}

}
