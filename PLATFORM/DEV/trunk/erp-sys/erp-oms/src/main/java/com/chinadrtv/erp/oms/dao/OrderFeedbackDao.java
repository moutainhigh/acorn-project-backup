package com.chinadrtv.erp.oms.dao;
import java.util.Date;
import java.util.List;

import com.chinadrtv.erp.model.EdiClear;
import com.chinadrtv.erp.oms.dto.EdiClearDto;
import com.chinadrtv.erp.core.dao.GenericDao;


public interface OrderFeedbackDao extends GenericDao<EdiClear,java.lang.Long>{
	public List<EdiClearDto> getAllOrderFeedback(String companyid,int state,int settleType,Date beginDate,Date endDate,int index, int size,Boolean remark);
	public int getOrderFeedbackCount(String companyid,int state,int settleType,Date beginDate,Date endDate,Boolean remark);
}
