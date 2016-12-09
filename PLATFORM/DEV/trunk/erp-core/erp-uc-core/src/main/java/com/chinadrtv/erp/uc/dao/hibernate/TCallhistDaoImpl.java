/*
 * @(#)TCallhistDaoImpl.java 1.0 2014年1月14日上午11:15:31
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.dao.hibernate;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.marketing.core.dto.LeadInteractionSearchDto;
import com.chinadrtv.erp.model.agent.TCallhist;
import com.chinadrtv.erp.uc.dao.TCallhistDao;
import com.chinadrtv.erp.uc.dto.TCallHistDto;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2014年1月14日 上午11:15:31 
 * 
 */
@Repository("tCallhistDao")
public class TCallhistDaoImpl extends GenericDaoHibernate<TCallhist, String> implements TCallhistDao {

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param persistentClass
	 */ 
	public TCallhistDaoImpl() {
		super(TCallhist.class);
	}


	/** 
	 * <p>Title: queryIvrConnectedAvaliableQty</p>
	 * <p>Description: </p>
	 * @param searchDto
	 * @return Integer
	 * @see com.chinadrtv.erp.uc.dao.CallHistDao#queryIvrConnectedAvaliableQty(com.chinadrtv.erp.marketing.core.dto.LeadInteractionSearchDto)
	 */ 
	@Override
	public Integer queryIvrConnectedAvaliableQty(LeadInteractionSearchDto searchDto) {
		
		StringBuffer sb = new StringBuffer();
		sb.append(" select count(*) from ( ");
		sb.append(this.generateSql(searchDto));
		sb.append(" )");
		
		Query query = this.getSessionFactory().getCurrentSession().createSQLQuery(sb.toString());
		
		this.parameterlizedSql(query, searchDto);
		
		BigDecimal result =  (BigDecimal) query.list().get(0);
		
		return result.intValue();
	}

	/**
	 * <p>参数化SQL</p>
	 * @param query
	 * @param searchDto
	 */
	private void parameterlizedSql(Query query, LeadInteractionSearchDto searchDto) {
		if(null != searchDto.getIncomingLowDate() && null != searchDto.getIncomingHighDate()) {
			query.setParameter("incomingLowDate", searchDto.getIncomingLowDate());
			query.setParameter("incomingHighDate", searchDto.getIncomingHighDate());
		}
		if(null != searchDto.getDnis() && !"".equals(searchDto.getDnis())) {
			query.setParameter("dnis", searchDto.getDnis());
		}
		if(null != searchDto.getAcdGroup() && !"".equals(searchDto.getAcdGroup())) {
			query.setParameter("acdGroup", searchDto.getAcdGroup());
		}
	}

	/**
	 * <p>拼SQL</p>
	 * @param searchDto
	 * @return String
	 */
	private String generateSql(LeadInteractionSearchDto searchDto) {
		StringBuffer sb = new StringBuffer();
		
		String search =  " select max(a.caseid) as caseid, min(a.createtime) as createtime, a.ani,max(a.calllen) as calllen, " + 
						 " max(a.dnis) as dnis,  max(a.scallgrp) as callgrp, max(a.agentdropspan) as agentdropspan, " +
						 " max(a.agentcalltype) as agentcalltype, max(a.agent) as agent " +
						 " from iagent.t_callhist a " + 
						 " inner join (select a.caseid " +
						 " from iagent.t_callhist a, acoapp_cntrpbank.acdinfo b " +
						 " where a.scallgrp = b.acdid " +
						 " and a.bpm_inst_id is null " +
						 " and b.ivr = 0 " +
						 " and a.agentcalltype = 'IN' ";
		
		sb.append(search);
		
		if(0 == searchDto.getLowCallDuration()) {
			sb.append(" and a.calllen <= 20 ");
		}else if(20 == searchDto.getLowCallDuration()) {
			sb.append(" and a.calllen < 180 and a.calllen > 20");
		}else if(180 == searchDto.getLowCallDuration()) {
			sb.append(" and a.calllen >= 180 ");
		}
		if(null != searchDto.getIncomingLowDate() && null != searchDto.getIncomingHighDate()) {
			sb.append(" and a.createtime between to_date(:incomingLowDate, 'yyyy-mm-dd hh24:mi:ss') "
					+ "and to_date(:incomingHighDate, 'yyyy-mm-dd hh24:mi:ss') ");
		}
		if(null != searchDto.getDnis() && !"".equals(searchDto.getDnis())) {
			sb.append(" and a.dnis = :dnis ");
		}
		if(null != searchDto.getAcdGroup() && !"".equals(searchDto.getAcdGroup())) {
			sb.append(" and a.scallgrp = :acdGroup ");
		}
		
		String cause = " )b on a.caseid = b.caseid  " +
				 	   " group by a.ani";
		sb.append(cause);
		
		return sb.toString();
	}

	/** 
	 * <p>Title: queryIvrConnectedAvaliableList</p>
	 * <p>Description: </p>
	 * @param liDto
	 * @return List<CallHist>
	 * @see com.chinadrtv.erp.uc.dao.CallHistDao#queryIvrConnectedAvaliableList(com.chinadrtv.erp.marketing.core.dto.LeadInteractionSearchDto)
	 */ 
	@Override
	public List<TCallHistDto> queryIvrConnectedAvaliableList(LeadInteractionSearchDto liDto) {
		String sql = this.generateSql(liDto);
		
		Query query = this.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		
		this.parameterlizedSql(query, liDto);
		
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rsMapList = query.list();
		
		return this.mapAdapter(rsMapList);
	}

	/**
	 * <p></p>
	 * @param rsMapList
	 * @return
	 */
	private List<TCallHistDto> mapAdapter(List<Map<String, Object>> rsMapList) {
		if(null == rsMapList) {
			return new ArrayList<TCallHistDto>();
		}
		
		List<TCallHistDto> callHistList = new ArrayList<TCallHistDto>();
		
		for(Map<String, Object> rsMap : rsMapList) {
			callHistList.add(this.mapAdapter(rsMap));
		}
		
		return callHistList;
	}

	/**
	 * <p></p>
	 * @param rsMap
	 * @return
	 */
	private TCallHistDto mapAdapter(Map<String, Object> rsMap) {
		
		TCallHistDto callHist = new TCallHistDto();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		Date createTime = null;
		Date transDate = null;
		try {
			createTime = null == rsMap.get("CREATETIME") ? null : sdf.parse(rsMap.get("CREATETIME").toString());
			transDate = null == rsMap.get("TRANSDATE") ? null : sdf.parse(rsMap.get("TRANSDATE").toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		String caseId = null == rsMap.get("CASEID") ? "" : rsMap.get("CASEID").toString();
		String callId = null == rsMap.get("CALLID") ? "" : rsMap.get("CALLID").toString();
		String ani = null == rsMap.get("ANI") ? "" : rsMap.get("ANI").toString();
		String dnis = null == rsMap.get("DNIS") ? "" : rsMap.get("DNIS").toString();
		String scallType = null == rsMap.get("SCALLTYPE") ? "" : rsMap.get("SCALLTYPE").toString();
		String scallGrp = null == rsMap.get("ECALLTYPE") ? "" : rsMap.get("ECALLTYPE").toString();
		String ecallType = null == rsMap.get("ECALLGRP") ? "" : rsMap.get("ECALLGRP").toString();
		String ecallGrp = null == rsMap.get("ECALLGRP") ? "" : rsMap.get("ECALLGRP").toString();
		
		String callLen = null == rsMap.get("CALLLEN") ? "" : rsMap.get("CALLLEN").toString();
		String agentTn = null == rsMap.get("AGENTTN") ? "" : rsMap.get("AGENTTN").toString();
		String agentDn = null == rsMap.get("AGENTDN") ? "" : rsMap.get("AGENTDN").toString();
		String agent = null == rsMap.get("AGENT") ? "" : rsMap.get("AGENT").toString();
		String agentCallType = null == rsMap.get("AGENTCALLTYPE") ? "" : rsMap.get("AGENTCALLTYPE").toString();
		String agentCallResult = null == rsMap.get("AGENTCALLRESULT") ? "" : rsMap.get("AGENTCALLRESULT").toString();
		
		String agentStartSpan = null == rsMap.get("AGENTSTARTSPAN") ? "" : rsMap.get("AGENTSTARTSPAN").toString();
		String agentDropSpan = null == rsMap.get("AGENTDROPSPAN") ? "" : rsMap.get("AGENTDROPSPAN").toString();
		String callData = null == rsMap.get("CALLDATA") ? "" : rsMap.get("CALLDATA").toString();
		String dnCount = null == rsMap.get("DNCOUNT") ? "" : rsMap.get("DNCOUNT").toString();
		Integer isStat = null == rsMap.get("ISSTAT") ? null : Integer.parseInt(rsMap.get("ISSTAT").toString());
		String agentActiveSpan = null == rsMap.get("AGENTACTIVESPAN") ? "" : rsMap.get("AGENTACTIVESPAN").toString();
		String isTrans = null == rsMap.get("ISTRANS") ? "" : rsMap.get("ISTRANS").toString();
		String transversion = null == rsMap.get("TRANSVERSION") ? "" : rsMap.get("TRANSVERSION").toString();
		
		callHist.setCreateTime(createTime);
		callHist.setTransDate(transDate);
		callHist.setCaseId(caseId);
		callHist.setCallId(callId);
		callHist.setAni(ani);
		callHist.setDnis(dnis);
		callHist.setScallType(scallType);
		callHist.setScallGrp(scallGrp);
		callHist.setEcallType(ecallType);
		callHist.setEcallGrp(ecallGrp);
		
		callHist.setCallLen(callLen);
		callHist.setAgentTn(agentTn);
		callHist.setAgentDn(agentDn);
		callHist.setAgent(agent);
		callHist.setAgentCallType(agentCallType);
		callHist.setAgentCallResult(agentCallResult);
		callHist.setAgentStartSpan(agentStartSpan);
		callHist.setAgentDropSpan(agentDropSpan);
		callHist.setCallData(callData);
		callHist.setDnCount(dnCount);
		callHist.setIsStat(isStat);
		callHist.setAgentActiveSpan(agentActiveSpan);
		callHist.setIsTrans(isTrans);
		callHist.setTransversion(transversion);
		
		return callHist;
	}

}
