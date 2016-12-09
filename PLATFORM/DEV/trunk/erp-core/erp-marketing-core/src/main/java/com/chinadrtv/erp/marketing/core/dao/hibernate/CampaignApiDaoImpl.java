/*
 * @(#)CampaignApiDaoImpl.java 1.0 2013-5-10上午10:50:47
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.dao.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterLong;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.core.dao.query.ParameterTimestamp;
import com.chinadrtv.erp.marketing.core.common.Constants;
import com.chinadrtv.erp.marketing.core.dao.CampaignApiDao;
import com.chinadrtv.erp.marketing.core.dto.CampaignDto;
import com.chinadrtv.erp.marketing.core.util.StringUtil;
import com.chinadrtv.erp.model.marketing.Campaign;
import com.chinadrtv.erp.model.marketing.CampaignType;
import com.chinadrtv.erp.model.marketing.LeadType;
import com.chinadrtv.erp.smsapi.util.DateTimeUtil;
import com.chinadrtv.erp.util.DateUtil;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-5-10 上午10:50:47
 * 
 */
@Repository
public class CampaignApiDaoImpl extends
		GenericDaoHibernate<Campaign, java.lang.Long> implements Serializable,
		CampaignApiDao {

	public CampaignApiDaoImpl() {
		super(Campaign.class);
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 
	 * 根据400 号码 呼入时间查询 营销计划
	 * 
	 * @param tollFreeNum
	 * 
	 * @param callTime
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.core.dao.CampaignDao#queryInboundCampaign(java
	 *      .lang.String, java.util.Date)
	 */
	public List<CampaignDto> queryInboundCampaign(String dnis, String callTime) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(
				"select  o.id,o.name,o.status,o.startDate,o.endDate,o.owner,o.description,"
						+ "o.createUser,o.department,o.tollFreeNum,o.dnis,o.campaignType,o.leadType,o.execDepartment from   Campaign o  where 1=1 ");
		Map<String, Parameter> paras = new HashMap<String, Parameter>();
		if (!StringUtil.isNullOrBank(dnis)) {
			sql.append("AND o.dnis like :dnis");
			Parameter dniss = new ParameterString("dnis", "%"+dnis+"%");
			paras.put("dnis", dniss);
		}
		if (callTime == null || callTime.equals("")) {
			callTime = DateTimeUtil.sim3.format(new Date());
		}
		sql.append(" AND o.startDate <=:startDate AND o.endDate >= :endDate ");
        sql.append(" AND o.campaignTypeId = '2'");
		Parameter startDate = null;
		Parameter endDate = null;
		try {
			startDate = new ParameterTimestamp("startDate",
					DateUtil.string2Date(callTime, "yyyy-MM-dd HH:mm:ss"));

			endDate = new ParameterTimestamp("endDate", DateUtil.string2Date(
					callTime, "yyyy-MM-dd HH:mm:ss"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		paras.put("startDate", startDate);
		paras.put("endDate", endDate);
		sql.append(" order by o.startDate desc");
		List objList = findList(sql.toString(), paras);
		Object[] obj = null;
		List<CampaignDto> list = new ArrayList<CampaignDto>();
		CampaignDto cd = null;
		for (int i = 0; i < objList.size(); i++) {
			cd = new CampaignDto();
			obj = (Object[]) objList.get(i);
			if (obj[0] != null) {
				cd.setId((Long) obj[0]);
			}
			if (obj[1] != null) {
				cd.setName(obj[1].toString());
			}
			if (obj[2] != null) {
				cd.setStatus(obj[2].toString());
			}
			if (obj[3] != null) {
				cd.setStartDate(obj[3].toString());
			}
			if (obj[4] != null) {
				cd.setEndDate(obj[4].toString());
			}
			if (obj[5] != null) {
				cd.setOwner(obj[5].toString());
			}
			if (obj[6] != null) {
				cd.setDescription(obj[6].toString());
			}
			if (obj[7] != null) {
				cd.setCreateUser(obj[7].toString());
			}
			if (obj[8] != null) {
				cd.setDepartment(obj[8].toString());
			}
			if (obj[9] != null) {
				cd.setTollFreeNum(obj[9].toString());
			}
			if (obj[10] != null) {
				cd.setDnis(obj[10].toString());
			}
			if (obj[11] != null) {
				cd.setCampaignType((CampaignType) obj[11]);
			}
			if (obj[12] != null) {
				cd.setLeadType((LeadType) obj[12]);
			}
			if (obj[13] != null) {
				cd.setExecDepartment(obj[13].toString());
			}
			list.add(cd);
		}
		return list;
	}

	/*
	 * 查询老客户营销活动
	 * 
	 * @param execDepartment
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.dao.CampaignDao#queryOldCustomerCampaign(
	 * java.lang.String)
	 */
	public List<CampaignDto> queryOldCustomerCampaign(String execDepartments) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(
				"select  o.id,o.name,o.status,o.startDate,o.endDate,o.owner,o.description,"
						+ "o.createUser,o.department,o.dnis,o.campaignType,o.leadType,o.execDepartment"
						+ " from Campaign o  where o.audience = :audience ");
		Map<String, Parameter> paras = new HashMap<String, Parameter>();
		if (!StringUtil.isNullOrBank(execDepartments)) {
			sql.append(" AND o.execDepartment = :execDepartment");
			Parameter execDepartment = new ParameterString("execDepartment",
					execDepartments);
			paras.put("execDepartment", execDepartment);
		}
		Parameter audience = new ParameterString("audience",
				Constants.CAMPAIGN_OLD_GROUP);
		paras.put("audience", audience);
		sql.append(" order by o.startDate desc");
		List objList = findList(sql.toString(), paras);
		Object[] obj = null;
		List<CampaignDto> list = new ArrayList<CampaignDto>();
		CampaignDto cd = null;
		for (int i = 0; i < objList.size(); i++) {
			cd = new CampaignDto();
			obj = (Object[]) objList.get(i);
			if (obj[0] != null) {
				cd.setId((Long) obj[0]);
			}
			if (obj[1] != null) {
				cd.setName(obj[1].toString());
			}
			if (obj[2] != null) {
				cd.setStatus(obj[2].toString());
			}
			if (obj[3] != null) {
				cd.setStartDate(obj[3].toString());
			}
			if (obj[4] != null) {
				cd.setEndDate(obj[4].toString());
			}
			if (obj[5] != null) {
				cd.setOwner(obj[5].toString());
			}
			if (obj[6] != null) {
				cd.setDescription(obj[6].toString());
			}
			if (obj[7] != null) {
				cd.setCreateUser(obj[7].toString());
			}
			if (obj[8] != null) {
				cd.setDepartment(obj[8].toString());
			}
			if (obj[9] != null) {
				cd.setDnis(obj[9].toString());
			}
			if (obj[10] != null) {
				cd.setCampaignType((CampaignType) obj[10]);
			}
			if (obj[11] != null) {
				cd.setLeadType((LeadType) obj[11]);
			}
			if (obj[12] != null) {
				cd.setExecDepartment(obj[12].toString());
			}
			list.add(cd);
		}
		return list;
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: queryMedia
	 * </p>
	 * <p>
	 * Description:根据落地号查询媒体产品和400号
	 * </p>
	 * 
	 * @param dnis
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.core.dao.CampaignApiDao#queryMedia(java.lang
	 *      .String)
	 */
	@Override
	public Map<String, Object> queryMedia(String dnis) {
		// TODO Auto-generated method stub
		String sql = "select (select name from iagent.mediacompany where companyid = c.company) as company ,"
				+ " (select name from iagent.mediaproduct where id = b.mediaproductid) as MEDIA_NAME ,d.n400 as TOLL_FREE_NUM "
				+ " from iagent.promotion a, iagent.mpversion b, iagent.mediaplan c, iagent.mediadnis d"
				+ " where a.mpversionid = b.mpversionid  "
				+ " and a.promotionid = c.promotionid "
				+ " and sysdate >= c.startdt "
				+ " and sysdate <= c.enddt "
				+ " and a.dnis = d.n400 " + " and d.dnis = ?";
		return jdbcTemplate.queryForMap(sql, new Object[] { dnis });
	}
	
	
	/**
	 * 
	 * 根据400 号码 呼入时间查询 营销计划
	 * 
	 * @param tollFreeNum
	 * 
	 * @param callTime
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.core.dao.CampaignDao#queryInboundCampaign(java
	 *      .lang.String, java.util.Date)
	 */
	public List<CampaignDto> queryCampaignForTask() {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(
				"select  o.id,o.name,o.status,o.startDate,o.endDate,o.owner,o.description,"
						+ "o.createUser,o.department,o.tollFreeNum,o.dnis,o.campaignType,o.leadType,o.execDepartment from   Campaign o  where 1=1 ");
		Map<String, Parameter> paras = new HashMap<String, Parameter>();
		
		sql.append("AND o.campaignTypeId = :campaignTypeId");
		Parameter campaignType = new ParameterLong("campaignTypeId", Constants.CAMPAIGN_TYPE_CUSTOME_TASK);
		paras.put("campaignTypeId", campaignType);
		
		sql.append(" AND o.startDate <=:startDate AND o.endDate >= :endDate ");
		Parameter startDate = new ParameterTimestamp("startDate",new Date());
		Parameter endDate = new ParameterTimestamp("endDate", new Date());
		paras.put("startDate", startDate);
		paras.put("endDate", endDate);
		sql.append(" order by o.startDate desc");
		List objList = findList(sql.toString(), paras);
		Object[] obj = null;
		List<CampaignDto> list = new ArrayList<CampaignDto>();
		CampaignDto cd = null;
		for (int i = 0; i < objList.size(); i++) {
			cd = new CampaignDto();
			obj = (Object[]) objList.get(i);
			if (obj[0] != null) {
				cd.setId((Long) obj[0]);
			}
			if (obj[1] != null) {
				cd.setName(obj[1].toString());
			}
			if (obj[2] != null) {
				cd.setStatus(obj[2].toString());
			}
			if (obj[3] != null) {
				cd.setStartDate(obj[3].toString());
			}
			if (obj[4] != null) {
				cd.setEndDate(obj[4].toString());
			}
			if (obj[5] != null) {
				cd.setOwner(obj[5].toString());
			}
			if (obj[6] != null) {
				cd.setDescription(obj[6].toString());
			}
			if (obj[7] != null) {
				cd.setCreateUser(obj[7].toString());
			}
			if (obj[8] != null) {
				cd.setDepartment(obj[8].toString());
			}
			if (obj[9] != null) {
				cd.setTollFreeNum(obj[9].toString());
			}
			if (obj[10] != null) {
				cd.setDnis(obj[10].toString());
			}
			if (obj[11] != null) {
				cd.setCampaignType((CampaignType) obj[11]);
			}
			if (obj[12] != null) {
				cd.setLeadType((LeadType) obj[12]);
			}
			if (obj[13] != null) {
				cd.setExecDepartment(obj[13].toString());
			}
			list.add(cd);
		}
		return list;
	}


}
