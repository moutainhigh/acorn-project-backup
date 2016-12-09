package com.chinadrtv.erp.task.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.task.core.repository.jpa.BaseRepository;
import com.chinadrtv.erp.task.core.service.BaseServiceImpl;
import com.chinadrtv.erp.task.dao.oms.PreTradeDetailDao;
import com.chinadrtv.erp.task.entity.PreTradeDetail;
import com.chinadrtv.erp.task.service.PreTradeDetailService;

@Service
public class PreTradeDetailServiceImpl extends BaseServiceImpl<PreTradeDetail, Long> implements PreTradeDetailService{

	@Autowired
	private PreTradeDetailDao preTradeDetailDao;
	
	@Override
	public BaseRepository<PreTradeDetail, Long> getDao() {
		return preTradeDetailDao;
	}

	@Override
	public List<PreTradeDetail> getAllPreTradeDetailByPerTradeID(String preTradeID) {
		return preTradeDetailDao.getPreTradeDetailByPreTradeId(preTradeID,true,true);
	}

}
