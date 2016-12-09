/*
 * @(#)NamesDaoImpl.java 1.0 2013-1-22下午2:24:24
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.dao.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterLong;
import com.chinadrtv.erp.marketing.core.dto.SmssnedDto;
import com.chinadrtv.erp.marketing.dao.NamesMarketingDao;
import com.chinadrtv.erp.model.Names;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.util.DateTimeUtil;

/**
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author andrew
 * @version 1.0
 * @since 2013-1-22 下午2:24:24
 * 
 */
@Repository
public class NamesMarketingDaoImpl extends
		GenericDaoHibernate<Names, java.lang.String> implements
		NamesMarketingDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param persistentClass
	 */
	public NamesMarketingDaoImpl() {
		super(Names.class);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (非 Javadoc) <p>Title: queryNamesList</p> <p>Description: </p>
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.dao.NamesDao#queryNamesList()
	 */
	public List<Names> queryNamesList() {
		String hql = "select t from Names t where t.id.tid='GRPDEPT' order by t.id.id";
		Map<String, Parameter> params = new HashMap<String, Parameter>();
		List<Names> namesList = this.findList(hql, params);
		return namesList;
	}

	/*
	 * (非 Javadoc) <p>Title: queryContactidByPhone</p> <p>Description: </p>
	 * 
	 * @param phone
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.dao.SmsAnswersDao#queryContactidByPhone(java
	 * .lang.String)
	 */
	public List<Map<String, Object>> queryContactidByPhone(String phone) {
		// TODO Auto-generated method stub
		String sql = "select contactid from iagent.phone where phn2 = ?";
		return jdbcTemplate.queryForList(sql, new Object[] { phone });

	}

	/**
	 * <p>
	 * Title: 根据营销计划id查询生成的批次
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param smsSend
	 * 
	 * @param dataModel
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.dao.SmsAnswersDao#query(com.chinadrtv.erp
	 *      .smsapi.module.SmsSend,
	 *      com.chinadrtv.erp.marketing.common.DataGridModel)
	 */
	public List<SmssnedDto> queryByCampaign(Long campaignId,
			DataGridModel dataModel) {
		StringBuffer sql = new StringBuffer(
				"select o.batchId ,o.department,o.groupCode,o.groupName,o.smsName,o.createtime,o.creator,o.smsstopStatus,o.sendStatus,o.receiveCount,o.recordcount "
						+ "from SmsSend o,CampaignBatch c "
						+ "where o.type=2 and o.batchId = c.batchId and c.campaignId = :campaignId");
		Map<String, Parameter> params = new HashMap<String, Parameter>();

		Parameter campaignIdParam = new ParameterLong("campaignId", campaignId);

		Parameter page = new ParameterInteger("page", dataModel.getStartRow());
		page.setForPageQuery(true);

		Parameter rows = new ParameterInteger("rows", dataModel.getRows());
		rows.setForPageQuery(true);

		params.put("campaignId", campaignIdParam);
		params.put("page", page);
		params.put("rows", rows);
		sql.append(" order by o.createtime desc");
		List objList = findPageList(sql.toString(), params);
		List<SmssnedDto> result = new ArrayList<SmssnedDto>();
		Object[] obj = null;
		SmssnedDto smsSends = null;
		for (int i = 0; i < objList.size(); i++) {
			obj = (Object[]) objList.get(i);
			smsSends = new SmssnedDto();
			smsSends.setBatchId(obj[0].toString());
			if (obj[1] != null) {
				smsSends.setDepartment(obj[1].toString());
			}
			if (obj[2] != null) {
				smsSends.setGroupCode(obj[2].toString());
			}
			if (obj[3] != null) {
				smsSends.setGroupName(obj[3].toString());
			}
			if (obj[4] != null) {
				smsSends.setSmsName(obj[4].toString());
			}
			if (obj[5] != null) {
				smsSends.setCreateTime(DateTimeUtil.sim3
						.format((java.util.Date) obj[5]));
			}
			if (obj[6] != null) {
				smsSends.setCreator(obj[6].toString());
			}
			if (obj[7] != null) {
				smsSends.setSmsstopStatus(obj[7].toString());
			}
			if (obj[8] != null) {
				smsSends.setSendStatus(obj[8].toString());
			}
			if (obj[9] != null) {
				smsSends.setReceiveCount(Long.valueOf(obj[9].toString()));
			}
			if (obj[10] != null) {
				smsSends.setRecordcount(Long.valueOf(obj[10].toString()));
			}
			result.add(smsSends);
		}
		return result;
	}

	/**
	 * <p>
	 * Title: queryCounts
	 * </p>
	 * <p>
	 * Description:根据营销计划查询批次总数
	 * </p>
	 * 
	 * @param smsSend
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.dao.SmsAnswersDao#queryCounts(com.chinadrtv
	 *      .erp.marketing.dto.SmssnedDto)
	 */
	public Integer queryCountsByCampaign(Long campaignId) {
		StringBuffer sql = new StringBuffer(
				"select count(o.id) "
						+ "from SmsSend o ,CampaignBatch c "
						+ "where o.type=2 and o.batchId = c.batchId and c.campaignId = :campaignId");

		Map<String, Parameter> params = new HashMap<String, Parameter>();

		Parameter campaignIdParam = new ParameterLong("campaignId", campaignId);

		params.put("campaignId", campaignIdParam);
		Integer result = findPageCount(sql.toString(), params);

		return result;
	}

}
