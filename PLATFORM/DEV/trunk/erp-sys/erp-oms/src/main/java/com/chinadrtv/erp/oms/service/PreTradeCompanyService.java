package com.chinadrtv.erp.oms.service;

import java.util.List;

import com.chinadrtv.erp.model.PreTradeCompany;

public interface PreTradeCompanyService {
		public List<PreTradeCompany> getPreTradeCompanyBySourceid(Long sourceId);
}
