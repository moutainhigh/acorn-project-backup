package com.chinadrtv.erp.tc.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.tc.core.constant.SchemaNames;
import com.chinadrtv.erp.tc.core.dao.DisHuifangMesDao;
import com.chinadrtv.erp.tc.core.dao.JifenReviseDao;
import com.chinadrtv.erp.tc.core.dao.SqlDao;
import com.chinadrtv.erp.tc.core.service.DisHuifangMesService;

@Service("disHuifangMesService")
public class DisHuifangMesServiceImpl extends GenericServiceImpl<Order, String> implements DisHuifangMesService {
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DisHuifangMesServiceImpl.class);

	@Autowired
	private DisHuifangMesDao disHuifangMesDao;
	@Autowired
	private SchemaNames schemaNames;
	@Autowired
	private JifenReviseDao jifenReviseDao;
	@Autowired
	private SqlDao sqlDao;

	protected GenericDao<Order, String> getGenericDao() {
		return this.disHuifangMesDao;
	}

	/**
	 * 处理回访信息
	 */
	public boolean upOrderHistByfive(Map<String, Object> map) throws Exception {
		logger.debug("begin upOrderHistByfive");
		boolean upid = false;
		upid = disHuifangMesDao.upOrderHistByfive(map);
		logger.debug("end upOrderHistByfive");
		return upid;
	}

	/*
	 * 处理回访信息的赠券校正 
	 * Updated by WangJian 
	 * <p>Title: couponReviseproc</p>
	 * <p>Description: </p>
	 * @param params
	 * @throws Exception
	 * @see com.chinadrtv.erp.tc.service.DisHuifangMesService#couponReviseproc(java.util.Map)
	 */
	public void couponReviseproc(Map<String, Object> params) throws Exception {
		
		/*List<ParameterString> parmList = new ArrayList<ParameterString>();
		parmList.add(new ParameterString("sorderid", mapval.get("sorderid").toString()));
		parmList.add(new ParameterString("scrusr", mapval.get("scrusr").toString()));
		
		jifenReviseDao.callProcedure("call " + schemaNames.getAgentSchema() + "p_n_conticketfeedback(:sorderid,:scrusr)", parmList);
		
		logger.debug("call p_n_conticketfeedback(:sorderid, :scrusr) " + mapval.get("sorderid").toString());*/
		
		List<Object> inParams = new ArrayList<Object>();
		inParams.add(params.get("sorderid"));
		inParams.add(params.get("scrusr"));
		
		sqlDao.exeStoredProcedure(schemaNames.getAgentSchema() + "p_n_conticketfeedback", inParams, 0);
	}

}