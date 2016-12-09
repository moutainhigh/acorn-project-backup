package com.chinadrtv.erp.oms.dao;
import java.util.List;

import com.chinadrtv.erp.model.PreTradeDetail;
import com.chinadrtv.erp.core.dao.GenericDao;

/**
 * PreTradeDetailDao
 * 
 * @author haoleitao
 *
 */
public interface PreTradeDetailDao extends GenericDao<PreTradeDetail,Long>{
	public List<PreTradeDetail> getPreTradeDetailByPreTradeId(String preTradeId);
}
