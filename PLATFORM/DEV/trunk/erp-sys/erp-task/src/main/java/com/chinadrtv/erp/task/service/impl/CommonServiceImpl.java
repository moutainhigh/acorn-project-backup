package com.chinadrtv.erp.task.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.task.core.orm.entity.BaseEntity;
import com.chinadrtv.erp.task.core.repository.jpa.BaseRepository;
import com.chinadrtv.erp.task.core.service.BaseServiceImpl;
import com.chinadrtv.erp.task.dao.oms.CommonDao;
import com.chinadrtv.erp.task.service.CommonService;

@Service
public class CommonServiceImpl extends BaseServiceImpl<BaseEntity, Serializable> implements CommonService{

	@Autowired
	private CommonDao commonDao;
	
	@Override
	public BaseRepository<BaseEntity, Serializable> getDao() {
		return commonDao;
	}

	/**
	 * 查询保证额度超标的承运商
	 * @return
	 */
	@Override
	public List<Object[]> queryMarginExceededCompanyPayment() {
		
//	SELECT 
//		( NVL(CC.PERFORMANCE_BOND_AMOUNT, 0)*NVL(CC.CREDIT_MARGIN_AMOUNT, 0) ) + NVL(CC.DAILY_AMOUNT, 0),
//		( NVL(CC.PERFORMANCE_BOND_AMOUNT, 0)*NVL(CC.ACTUAL_RISK_FACTOR, 0) ),
//		CC.RISK_EMAIL,
//		CC.RISK_OPTS_EMAIL,
//		NVL(CC.PERFORMANCE_BOND_AMOUNT, 0) AS 保证金, 
//		NVL(CC.CREDIT_MARGIN_AMOUNT, 0) AS 风险控制设定值, 
//		NVL(CC.ACTUAL_RISK_FACTOR, 0) AS 实际风险系数, 
//		NVL(CC.DAILY_AMOUNT, 0) AS 发包金额
//	FROM COMPANY_CONTRACT CC
//	WHERE 
//		CC.STATUS=1 AND
//		CC.CREDIT_MARGIN_AMOUNT IS NOT NULL AND CC.CREDIT_MARGIN_AMOUNT != 0 AND
//		( NVL(CC.PERFORMANCE_BOND_AMOUNT, 0)*NVL(CC.ACTUAL_RISK_FACTOR, 0) ) + NVL(CC.DAILY_AMOUNT, 0) > 
//		( NVL(CC.PERFORMANCE_BOND_AMOUNT, 0)*NVL(CC.CREDIT_MARGIN_AMOUNT, 0) )
		
		Object[] values = {};
		String sql = 
				"SELECT CC.NAME,NVL(CC.ACTUAL_RISK_FACTOR, 0),CC.RISK_EMAIL,CC.RISK_OPTS_EMAIL FROM COMPANY_CONTRACT CC " +
				"WHERE CC.STATUS=1 AND CC.CREDIT_MARGIN_AMOUNT IS NOT NULL AND CC.CREDIT_MARGIN_AMOUNT != 0 AND ( CC.PERFORMANCE_BOND_AMOUNT*NVL(CC.ACTUAL_RISK_FACTOR, 0) ) + NVL(CC.DAILY_AMOUNT, 0) > ( CC.PERFORMANCE_BOND_AMOUNT*NVL(CC.CREDIT_MARGIN_AMOUNT, 0) ) ";
		List<Object[]> items = commonDao.nativeQuery(sql, values, null, null);
		return items;
	}

	@Override
	public List<Object[]> queryLosePickOrder(int pageNo, int pageSize) {
		
//		select /*+index(a, IDX_ORDERHIST_CRDT)*/ a.orderid, a.crdt, c.dsc, a.crusr, d.grpname 
//		from iagent.orderhist a 
//		left join iagent.usr b on a.crusr = b.usrid  
//		left join iagent.names c on c.tid = 'ORDERSTATUS' and c.id = a.status
//		left join iagent.grp d on b.defgrp = d.grpid
//		where a.paytype = '1' and a.status = '1' and a.crdt <= sysdate - 1 and a.crdt >= sysdate - 90;
		
		Object[] values = {};
		String sql = 
				"select /*+index(a, IDX_ORDERHIST_CRDT)*/ a.orderid, a.crdt, c.dsc, a.crusr, d.grpname " +
				"from iagent.orderhist a " +
				"left join iagent.usr b on a.crusr = b.usrid " +
				"left join iagent.names c on c.tid = 'ORDERSTATUS' and c.id = a.status " +
				"left join iagent.grp d on b.defgrp = d.grpid " +
				"where a.paytype = '1' and a.status = '1' and a.crdt <= sysdate - 1 and a.crdt >= sysdate - 90";
		List<Object[]> items = commonDao.nativeQuery(sql, values, pageNo, pageSize);
		return items;
	}

}
