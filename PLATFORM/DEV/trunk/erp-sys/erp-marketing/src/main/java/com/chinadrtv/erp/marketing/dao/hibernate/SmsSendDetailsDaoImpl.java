/*
 * @(#)SmsSendDetailDaoImpl.java 1.0 2013-4-3上午10:09:28
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.dao.hibernate;

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
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterLong;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.marketing.dao.SmsSendDetailsDao;
import com.chinadrtv.erp.marketing.dto.SmsSendDetailDto;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.model.SmsSendDetail;
import com.chinadrtv.erp.smsapi.util.DateTimeUtil;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-4-3 上午10:09:28
 * 
 */
@Repository
public class SmsSendDetailsDaoImpl extends
		GenericDaoHibernate<SmsSendDetail, java.lang.String> implements
		SmsSendDetailsDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

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
	public SmsSendDetailsDaoImpl(Class<SmsSendDetail> persistentClass) {
		super(persistentClass);
		// TODO Auto-generated constructor stub
	}

	public SmsSendDetailsDaoImpl() {
		super(SmsSendDetail.class);
	}

	/**
	 * 分页查询
	 * 
	 * @param smsSendDetail
	 * 
	 * @param dataModel
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.dao.SmsSendDetailsDao#query(com.chinadrtv.erp.smsapi.model.SmsSendDetail,
	 *      com.chinadrtv.erp.marketing.common.DataGridModel)
	 */
	public List<SmsSendDetailDto> query(SmsSendDetailDto smsSendDetailDto,
			DataGridModel dataModel) {
		StringBuffer sql = new StringBuffer(
				"select o.id ,o.mobile,o.connectId,o.uuid,o.batchId,o.receiveStatus,feedbackStatus, o.receiveStatusTime,o.feedbackStatusTime"
						+ " , o.createtime,o.creator,o.source,o.channel,o.receiveStatusDes,o.feedbackStatusDes,o.content from SmsSendDetail o where o.smsType='2' ");
		Map<String, Parameter> params = genSql(sql, smsSendDetailDto);

		// sql.append(" AND o.addressid = a.addressId ");
		Parameter page = new ParameterInteger("page", dataModel.getStartRow());
		page.setForPageQuery(true);

		Parameter rows = new ParameterInteger("rows", dataModel.getRows());
		rows.setForPageQuery(true);
		params.put("page", page);
		params.put("rows", rows);
		sql.append(" order by o.createtime desc ");
		List objList = findPageList(sql.toString(), params);
		List<SmsSendDetailDto> result = new ArrayList<SmsSendDetailDto>();
		Object[] obj = null;
		SmsSendDetailDto smsSendDetails = null;
		for (int i = 0; i < objList.size(); i++) {
			obj = (Object[]) objList.get(i);
			smsSendDetails = new SmsSendDetailDto();
			smsSendDetails.setId((Long) obj[0]);
			if (obj[1] != null) {
				smsSendDetails.setMobile(obj[1].toString());
			}
			if (obj[2] != null) {
				smsSendDetails.setConnectId(obj[2].toString());
			}
			if (obj[3] != null) {
				smsSendDetails.setUuid(obj[3].toString());
			}
			if (obj[4] != null) {
				smsSendDetails.setBatchId(obj[4].toString());
			}
			if (obj[5] != null) {
				smsSendDetails.setReceiveStatus(obj[5].toString());
			}
			if (obj[6] != null) {
				smsSendDetails.setFeedbackStatus(obj[6].toString());
			}
			if (obj[7] != null) {
				smsSendDetails.setReceiveStatusTime(DateTimeUtil.sim3
						.format((Date) obj[7]));
			}
			if (obj[8] != null) {
				smsSendDetails.setFeedBackStatusTime(DateTimeUtil.sim3
						.format((Date) obj[8]));
			}
			if (obj[9] != null) {
				smsSendDetails.setCreatetime(DateTimeUtil.sim3
						.format((Date) obj[9]));
			}
			if (obj[10] != null) {
				smsSendDetails.setCreator(obj[10].toString());
			}
			if (obj[11] != null) {
				smsSendDetails.setSource(obj[11].toString());
			}
			if (obj[12] != null) {
				smsSendDetails.setChannel(obj[12].toString());
			}
			if (obj[13] != null) {
				smsSendDetails.setReceiveStatusDes(obj[13].toString());
			}
			if (obj[14] != null) {
				smsSendDetails.setFeedbackStatusDes(obj[14].toString());
			}
			if (obj[15] != null) {
				smsSendDetails.setContent(obj[15].toString());
			}
			result.add(smsSendDetails);
		}
		return result;
	}

	/**
	 * 
	 * 查询 总数
	 * 
	 * @param smsSendDetail
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.dao.SmsSendDetailsDao#queryCounts(com.chinadrtv.erp.smsapi.model.SmsSendDetail)
	 */
	public Integer queryCounts(SmsSendDetailDto SmsSendDetailDto) {
		StringBuffer sql = new StringBuffer(
				"select count(o.id) from SmsSendDetail o  where o.smsType='2' ");

		Map<String, Parameter> params = genSql(sql, SmsSendDetailDto);

		Integer result = findPageCount(sql.toString(), params);

		return result;
	}

	public Map<String, Parameter> genSql(StringBuffer sql,
			SmsSendDetailDto smsSendDetailDto) {
		Map<String, Parameter> paras = new HashMap<String, Parameter>();
		if (smsSendDetailDto.getId() != null) {
			sql.append(" AND o.id=:id");
			Parameter id = new ParameterString("id", smsSendDetailDto.getId()
					.toString());
			paras.put("id", id);
		}
		if (smsSendDetailDto.getMobile() != null
				&& !("").equals(smsSendDetailDto.getMobile())) {
			sql.append(" AND o.mobile=:mobile");
			Parameter mobile = new ParameterString("mobile", smsSendDetailDto
					.getMobile().toString());
			paras.put("mobile", mobile);
		}
		if (smsSendDetailDto.getChannel() != null
				&& !("").equals(smsSendDetailDto.getChannel())) {
			sql.append(" AND o.channel=:channel");
			Parameter channel = new ParameterString("channel",
					smsSendDetailDto.getChannel());
			paras.put("channel", channel);
		}
		if (smsSendDetailDto.getBatchId() != null
				&& !("").equals(smsSendDetailDto.getBatchId())) {
			sql.append(" AND o.batchId=:batchId");
			Parameter batchid = new ParameterString("batchId", smsSendDetailDto
					.getBatchId().toString());
			paras.put("batchId", batchid);
		}
		if (smsSendDetailDto.getUuid() != null
				&& !("").equals(smsSendDetailDto.getUuid())) {
			sql.append(" AND o.uuid=:uuid");
			Parameter uuid = new ParameterString("uuid", smsSendDetailDto
					.getUuid().toString());
			paras.put("uuid", uuid);
		}
		if (smsSendDetailDto.getConnectId() != null
				&& !("").equals(smsSendDetailDto.getConnectId())) {
			sql.append(" AND o.connectId=:connectId");
			Parameter connectid = new ParameterString("connectId",
					smsSendDetailDto.getConnectId());
			paras.put("connectId", connectid);
		}
		if (smsSendDetailDto.getStartfeedBackStatusTime() != null
				&& !("").equals(smsSendDetailDto.getStartfeedBackStatusTime())) {
			sql.append(" AND o.feedbackStatusTime >=TO_DATE(:startfeedBackStatusTime, 'YYYY-MM-DD HH24:MI:SS')");
			Parameter startfeedBackStatusTime = null;
			try {
				startfeedBackStatusTime = new ParameterString(
						"startfeedBackStatusTime",
						smsSendDetailDto.getStartfeedBackStatusTime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			paras.put("startfeedBackStatusTime", startfeedBackStatusTime);
		}
		if (smsSendDetailDto.getEndfeedBackStatusTime() != null
				&& !("").equals(smsSendDetailDto.getEndfeedBackStatusTime())) {
			sql.append(" AND o.feedbackStatusTime <=TO_DATE(:endfeedBackStatusTime, 'YYYY-MM-DD HH24:MI:SS')");
			Parameter endfeedBackStatusTime = null;
			try {
				endfeedBackStatusTime = new ParameterString(
						"endfeedBackStatusTime",
						smsSendDetailDto.getEndfeedBackStatusTime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			paras.put("endfeedBackStatusTime", endfeedBackStatusTime);
		}
		if (smsSendDetailDto.getCreatetime() != null
				&& !("").equals(smsSendDetailDto.getCreatetime())) {
			sql.append(" AND o.createtime = TO_DATE(:createtime, 'YYYY-MM-DD HH24:MI:SS')");
			Parameter createtime = null;
			try {
				createtime = new ParameterString("createtime",
						smsSendDetailDto.getCreatetime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			paras.put("createtime", createtime);
		}
		if (smsSendDetailDto.getStartreceiveStatusTime() != null
				&& !("").equals(smsSendDetailDto.getStartreceiveStatusTime())) {
			sql.append(" AND o.receiveStatusTime >= TO_DATE(:startreceiveStatusTime, 'YYYY-MM-DD HH24:MI:SS')");
			Parameter startreceiveStatusTime = null;
			try {
				startreceiveStatusTime = new ParameterString(
						"startreceiveStatusTime",
						smsSendDetailDto.getStartreceiveStatusTime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			paras.put("startreceiveStatusTime", startreceiveStatusTime);
		}
		if (smsSendDetailDto.getEndreceiveStatusTime() != null
				&& !("").equals(smsSendDetailDto.getEndreceiveStatusTime())) {
			sql.append(" AND o.receiveStatusTime <= TO_DATE(:endreceiveStatusTime, 'YYYY-MM-DD HH24:MI:SS')");
			Parameter endreceiveStatusTime = null;
			try {
				endreceiveStatusTime = new ParameterString(
						"endreceiveStatusTime",
						smsSendDetailDto.getEndreceiveStatusTime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			paras.put("endreceiveStatusTime", endreceiveStatusTime);
		}
		if (smsSendDetailDto.getCreator() != null
				&& !("").equals(smsSendDetailDto.getCreator())) {
			sql.append(" AND o.creator=:creator");
			Parameter creator = new ParameterString("creator",
					smsSendDetailDto.getCreator());
			paras.put("creator", creator);
		}
		if (smsSendDetailDto.getFeedbackStatus() != null
				&& !("").equals(smsSendDetailDto.getFeedbackStatus())) {
			sql.append(" AND o.feedbackStatus=:feedbackStatus");
			Parameter feedbackStatus = new ParameterString("feedbackStatus",
					smsSendDetailDto.getFeedbackStatus());
			paras.put("feedbackStatus", feedbackStatus);
		}
		if (smsSendDetailDto.getReceiveStatus() != null
				&& !("").equals(smsSendDetailDto.getReceiveStatus())) {
			sql.append(" AND o.receiveStatus=:receiveStatus");
			Parameter receiveStatus = new ParameterString("receiveStatus",
					smsSendDetailDto.getReceiveStatus());
			paras.put("receiveStatus", receiveStatus);
		}
		if (smsSendDetailDto.getFeedbackStatusDes() != null
				&& !("").equals(smsSendDetailDto.getFeedbackStatusDes())) {
			sql.append(" AND o.feedbackStatusDes=:feedbackStatusDes");
			Parameter feedbackStatusDes = new ParameterString(
					"feedbackStatusDes",
					smsSendDetailDto.getFeedbackStatusDes());
			paras.put("feedbackStatusDes", feedbackStatusDes);
		}
		if (smsSendDetailDto.getReceiveStatusDes() != null
				&& !("").equals(smsSendDetailDto.getReceiveStatusDes())) {
			sql.append(" AND o.receiveStatusDes=:receiveStatusDes");
			Parameter receiveStatusDes = new ParameterString(
					"receiveStatusDes", smsSendDetailDto.getReceiveStatusDes());
			paras.put("receiveStatusDes", receiveStatusDes);
		}
		if (smsSendDetailDto.getSource() != null
				&& !("").equals(smsSendDetailDto.getSource())) {
			sql.append(" AND o.source=:source");
			Parameter source = new ParameterString("source",
					smsSendDetailDto.getSource());
			paras.put("source", source);
		}

		return paras;
	}

	/**
	 * 根据batchid查询总数
	 * 
	 * @Description: TODO
	 * @param batchid
	 * @return
	 * @return Integer
	 * @throws
	 */
	public Long queryCountByBatchid(String batchid) {
		String sql = "select count(*)   from SmsSendDetail where batchId = ?  ";
		return (Long) getHibernateTemplate()
				.find(sql, new Object[] { batchid }).get(0);

	}

	/**
	 * 分页查询
	 * 
	 * @param smsSendDetail
	 * 
	 * @param dataModel
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.dao.SmsSendDetailsDao#query(com.chinadrtv.erp.smsapi.model.SmsSendDetail,
	 *      com.chinadrtv.erp.marketing.common.DataGridModel)
	 */
	public List<SmsSendDetailDto> queryForcampaign(
			SmsSendDetailDto smsSendDetailDto, DataGridModel dataModel) {
		StringBuffer sql = new StringBuffer(
				"select o.id ,o.mobile,o.connectId,o.uuid,o.batchId,o.receiveStatus,o.feedbackStatus, o.receiveStatusTime,o.feedbackStatusTime"
						+ " , o.createtime,o.creator,o.source,o.channel,o.receiveStatusDes,o.feedbackStatusDes,o.content "
						+ "from SmsSendDetail o,CampaignBatch c "
						+ "where o.smsType='2' and o.batchId=c.batchId ");
		Map<String, Parameter> params = genSqlForcampaign(sql, smsSendDetailDto);

		// sql.append(" AND o.addressid = a.addressId ");
		Parameter page = new ParameterInteger("page", dataModel.getStartRow());
		page.setForPageQuery(true);

		Parameter rows = new ParameterInteger("rows", dataModel.getRows());
		rows.setForPageQuery(true);
		params.put("page", page);
		params.put("rows", rows);
		sql.append(" order by o.createtime desc ");
		List objList = findPageList(sql.toString(), params);
		List<SmsSendDetailDto> result = new ArrayList<SmsSendDetailDto>();
		Object[] obj = null;
		SmsSendDetailDto smsSendDetails = null;
		for (int i = 0; i < objList.size(); i++) {
			obj = (Object[]) objList.get(i);
			smsSendDetails = new SmsSendDetailDto();
			smsSendDetails.setId((Long) obj[0]);
			if (obj[1] != null) {
				smsSendDetails.setMobile(obj[1].toString());
			}
			if (obj[2] != null) {
				smsSendDetails.setConnectId(obj[2].toString());
			}
			if (obj[3] != null) {
				smsSendDetails.setUuid(obj[3].toString());
			}
			if (obj[4] != null) {
				smsSendDetails.setBatchId(obj[4].toString());
			}
			if (obj[5] != null) {
				smsSendDetails.setReceiveStatus(obj[5].toString());
			}
			if (obj[6] != null) {
				smsSendDetails.setFeedbackStatus(obj[6].toString());
			}
			if (obj[7] != null) {
				smsSendDetails.setReceiveStatusTime(DateTimeUtil.sim3
						.format((Date) obj[7]));
			}
			if (obj[8] != null) {
				smsSendDetails.setFeedBackStatusTime(DateTimeUtil.sim3
						.format((Date) obj[8]));
			}
			if (obj[9] != null) {
				smsSendDetails.setCreatetime(DateTimeUtil.sim3
						.format((Date) obj[9]));
			}
			if (obj[10] != null) {
				smsSendDetails.setCreator(obj[10].toString());
			}
			if (obj[11] != null) {
				smsSendDetails.setSource(obj[11].toString());
			}
			if (obj[12] != null) {
				smsSendDetails.setChannel(obj[12].toString());
			}
			if (obj[13] != null) {
				smsSendDetails.setReceiveStatusDes(obj[13].toString());
			}
			if (obj[14] != null) {
				smsSendDetails.setFeedbackStatusDes(obj[14].toString());
			}
			if (obj[15] != null) {
				smsSendDetails.setContent(obj[15].toString());
			}
			result.add(smsSendDetails);
		}
		return result;
	}

	/**
	 * 
	 * 查询 总数
	 * 
	 * @param smsSendDetail
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.dao.SmsSendDetailsDao#queryCounts(com.chinadrtv.erp.smsapi.model.SmsSendDetail)
	 */
	public Integer queryCountsForcampaign(SmsSendDetailDto SmsSendDetailDto) {
		StringBuffer sql = new StringBuffer(
				"select count(o.id) from SmsSendDetail o  ,CampaignBatch c "
						+ "where o.smsType='2' and o.batchId=c.batchId ");

		Map<String, Parameter> params = genSqlForcampaign(sql, SmsSendDetailDto);

		Integer result = findPageCount(sql.toString(), params);

		return result;
	}

	public Map<String, Parameter> genSqlForcampaign(StringBuffer sql,
			SmsSendDetailDto smsSendDetailDto) {
		Map<String, Parameter> paras = new HashMap<String, Parameter>();
		if (smsSendDetailDto.getCampaignId() != null) {
			sql.append(" AND c.campaignId=:campaignId");
			Parameter campaignId = new ParameterLong("campaignId",
					smsSendDetailDto.getCampaignId());
			paras.put("campaignId", campaignId);
		}

		if (smsSendDetailDto.getId() != null) {
			sql.append(" AND o.id=:id");
			Parameter id = new ParameterString("id", smsSendDetailDto.getId()
					.toString());
			paras.put("id", id);
		}
		if (smsSendDetailDto.getMobile() != null
				&& !("").equals(smsSendDetailDto.getMobile())) {
			sql.append(" AND o.mobile=:mobile");
			Parameter mobile = new ParameterString("mobile", smsSendDetailDto
					.getMobile().toString());
			paras.put("mobile", mobile);
		}
		if (smsSendDetailDto.getChannel() != null
				&& !("").equals(smsSendDetailDto.getChannel())) {
			sql.append(" AND o.channel=:channel");
			Parameter channel = new ParameterString("channel",
					smsSendDetailDto.getChannel());
			paras.put("channel", channel);
		}
		if (smsSendDetailDto.getBatchId() != null
				&& !("").equals(smsSendDetailDto.getBatchId())) {
			sql.append(" AND o.batchId=:batchId");
			Parameter batchid = new ParameterString("batchId", smsSendDetailDto
					.getBatchId().toString());
			paras.put("batchId", batchid);
		}
		if (smsSendDetailDto.getUuid() != null
				&& !("").equals(smsSendDetailDto.getUuid())) {
			sql.append(" AND o.uuid=:uuid");
			Parameter uuid = new ParameterString("uuid", smsSendDetailDto
					.getUuid().toString());
			paras.put("uuid", uuid);
		}
		if (smsSendDetailDto.getConnectId() != null
				&& !("").equals(smsSendDetailDto.getConnectId())) {
			sql.append(" AND o.connectId=:connectId");
			Parameter connectid = new ParameterString("connectId",
					smsSendDetailDto.getConnectId());
			paras.put("connectId", connectid);
		}
		if (smsSendDetailDto.getStartfeedBackStatusTime() != null
				&& !("").equals(smsSendDetailDto.getStartfeedBackStatusTime())) {
			sql.append(" AND o.feedbackStatusTime >=TO_DATE(:startfeedBackStatusTime, 'YYYY-MM-DD HH24:MI:SS')");
			Parameter startfeedBackStatusTime = null;
			try {
				startfeedBackStatusTime = new ParameterString(
						"startfeedBackStatusTime",
						smsSendDetailDto.getStartfeedBackStatusTime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			paras.put("startfeedBackStatusTime", startfeedBackStatusTime);
		}
		if (smsSendDetailDto.getEndfeedBackStatusTime() != null
				&& !("").equals(smsSendDetailDto.getEndfeedBackStatusTime())) {
			sql.append(" AND o.feedbackStatusTime <=TO_DATE(:endfeedBackStatusTime, 'YYYY-MM-DD HH24:MI:SS')");
			Parameter endfeedBackStatusTime = null;
			try {
				endfeedBackStatusTime = new ParameterString(
						"endfeedBackStatusTime",
						smsSendDetailDto.getEndfeedBackStatusTime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			paras.put("endfeedBackStatusTime", endfeedBackStatusTime);
		}
		if (smsSendDetailDto.getCreatetime() != null
				&& !("").equals(smsSendDetailDto.getCreatetime())) {
			sql.append(" AND o.createtime = TO_DATE(:createtime, 'YYYY-MM-DD HH24:MI:SS')");
			Parameter createtime = null;
			try {
				createtime = new ParameterString("createtime",
						smsSendDetailDto.getCreatetime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			paras.put("createtime", createtime);
		}
		if (smsSendDetailDto.getStartreceiveStatusTime() != null
				&& !("").equals(smsSendDetailDto.getStartreceiveStatusTime())) {
			sql.append(" AND o.receiveStatusTime >= TO_DATE(:startreceiveStatusTime, 'YYYY-MM-DD HH24:MI:SS')");
			Parameter startreceiveStatusTime = null;
			try {
				startreceiveStatusTime = new ParameterString(
						"startreceiveStatusTime",
						smsSendDetailDto.getStartreceiveStatusTime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			paras.put("startreceiveStatusTime", startreceiveStatusTime);
		}
		if (smsSendDetailDto.getEndreceiveStatusTime() != null
				&& !("").equals(smsSendDetailDto.getEndreceiveStatusTime())) {
			sql.append(" AND o.receiveStatusTime <= TO_DATE(:endreceiveStatusTime, 'YYYY-MM-DD HH24:MI:SS')");
			Parameter endreceiveStatusTime = null;
			try {
				endreceiveStatusTime = new ParameterString(
						"endreceiveStatusTime",
						smsSendDetailDto.getEndreceiveStatusTime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			paras.put("endreceiveStatusTime", endreceiveStatusTime);
		}
		if (smsSendDetailDto.getCreator() != null
				&& !("").equals(smsSendDetailDto.getCreator())) {
			sql.append(" AND o.creator=:creator");
			Parameter creator = new ParameterString("creator",
					smsSendDetailDto.getCreator());
			paras.put("creator", creator);
		}
		if (smsSendDetailDto.getFeedbackStatus() != null
				&& !("").equals(smsSendDetailDto.getFeedbackStatus())) {
			sql.append(" AND o.feedbackStatus=:feedbackStatus");
			Parameter feedbackStatus = new ParameterString("feedbackStatus",
					smsSendDetailDto.getFeedbackStatus());
			paras.put("feedbackStatus", feedbackStatus);
		}
		if (smsSendDetailDto.getReceiveStatus() != null
				&& !("").equals(smsSendDetailDto.getReceiveStatus())) {
			sql.append(" AND o.receiveStatus=:receiveStatus");
			Parameter receiveStatus = new ParameterString("receiveStatus",
					smsSendDetailDto.getReceiveStatus());
			paras.put("receiveStatus", receiveStatus);
		}
		if (smsSendDetailDto.getFeedbackStatusDes() != null
				&& !("").equals(smsSendDetailDto.getFeedbackStatusDes())) {
			sql.append(" AND o.feedbackStatusDes=:feedbackStatusDes");
			Parameter feedbackStatusDes = new ParameterString(
					"feedbackStatusDes",
					smsSendDetailDto.getFeedbackStatusDes());
			paras.put("feedbackStatusDes", feedbackStatusDes);
		}
		if (smsSendDetailDto.getReceiveStatusDes() != null
				&& !("").equals(smsSendDetailDto.getReceiveStatusDes())) {
			sql.append(" AND o.receiveStatusDes=:receiveStatusDes");
			Parameter receiveStatusDes = new ParameterString(
					"receiveStatusDes", smsSendDetailDto.getReceiveStatusDes());
			paras.put("receiveStatusDes", receiveStatusDes);
		}
		if (smsSendDetailDto.getSource() != null
				&& !("").equals(smsSendDetailDto.getSource())) {
			sql.append(" AND o.source=:source");
			Parameter source = new ParameterString("source",
					smsSendDetailDto.getSource());
			paras.put("source", source);
		}

		return paras;
	}

	/**
	 * 
	 * 查询 总数
	 * 
	 * @param smsSendDetail
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.dao.SmsSendDetailsDao#queryCounts(com.chinadrtv.erp.smsapi.model.SmsSendDetail)
	 */
	public Integer updateForcampaign(String smsStopStatus,
			String oldSmsStopStatus, Long campaignId) {
		StringBuffer sql = new StringBuffer(
				"UPDATE SMS_SEND_DETAIL o SET o.SMSSTOP_STATUS=? WHERE  o.SMSSTOP_STATUS=?"
						+ " AND EXISTS (SELECT 1 FROM campaign_batch B WHERE B.BATCH_ID=o.BATCHID AND B.CAMPAIGN_ID=?)");

		return jdbcTemplate.update(sql.toString(), new Object[] {
				smsStopStatus, oldSmsStopStatus, campaignId });
	}

}
