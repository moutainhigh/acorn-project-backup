/*
 * @(#)CampaignMonitorDaoImpl.java 1.0 2013-7-8下午2:49:04
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.dao.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterLong;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.dao.CampaignMonitorDao;
import com.chinadrtv.erp.smsapi.dao.SmsSendDao;
import com.chinadrtv.erp.smsapi.dto.CampaignMonitorDto;
import com.chinadrtv.erp.smsapi.model.CampaignMonitor;
import com.chinadrtv.erp.smsapi.model.SmsSend;
import com.chinadrtv.erp.smsapi.util.StringUtil;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-7-8 下午2:49:04
 * 
 */
@Repository
public class CampaignMonitorDaoImpl extends
		GenericDaoHibernate<CampaignMonitor, java.lang.Long> implements
		Serializable, CampaignMonitorDao {

	public CampaignMonitorDaoImpl() {
		super(CampaignMonitor.class);
	}

	@Autowired
	private SmsSendDao smsSendDao;

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: getCampaignMonitorPageList
	 * </p>
	 * <p>
	 * Description:分页
	 * </p>
	 * 
	 * @param campaignMonitor
	 * 
	 * @param dataGridModel
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.smsapi.dao.CampaignMonitorDao#getCampaignMonitorPageList
	 *      (com.chinadrtv.erp.smsapi.model.CampaignMonitor,
	 *      com.chinadrtv.erp.smsapi.constant.DataGridModel)
	 */
	@Override
	public List<CampaignMonitorDto> getCampaignMonitorPageList(
			CampaignMonitorDto campaignMonitorDto, DataGridModel dataGridModel) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer(
				" select   c.id, c.campaignId,c.deparment, c.type, c.minuCount,c.errorCode ,"
						+ " c.errorMessage,c.status,c.createTime,c.createUser,c.updateTime,c.updateUser,c.smsCount, c.smsContent"
						+ "  from  CampaignMonitor c where 1=1");
		Map<String, Parameter> parameters = gensql(campaignMonitorDto, hql);
		Parameter page = new ParameterInteger("page",
				dataGridModel.getStartRow());
		page.setForPageQuery(true);

		Parameter rows = new ParameterInteger("rows", dataGridModel.getRows());
		rows.setForPageQuery(true);

		parameters.put("page", page);
		parameters.put("rows", rows);
		hql.append(" order by c.createTime desc");
		List list = findPageList(hql.toString(), parameters);
		Object[] obj = null;
		CampaignMonitorDto campaignDto = null;
		List<CampaignMonitorDto> resultList = new ArrayList<CampaignMonitorDto>();
		List<SmsSend> sendList = new ArrayList<SmsSend>();
		for (int i = 0; i < list.size(); i++) {
			obj = (Object[]) list.get(i);
			Long receiveCount = 0l;
			Long recordCountLong = 0l;
			campaignDto = new CampaignMonitorDto();
			if (obj[0] != null) {
				campaignDto.setId(Long.valueOf(obj[0].toString()));
			}
			if (obj[1] != null) {
				campaignDto.setCampaignId(Long.valueOf(obj[1].toString()));
			}
			if (obj[2] != null) {
				campaignDto.setDeparment(obj[2].toString());
			}
			if (obj[3] != null) {
				campaignDto.setType(obj[3].toString());
			}
			if (obj[4] != null) {
				campaignDto.setMinuCount(obj[4].toString());
			}
			if (obj[5] != null) {
				campaignDto.setErrorCode(obj[5].toString());
			}
			if (obj[6] != null) {
				campaignDto.setErrorMessage(obj[6].toString());
			}
			if (obj[7] != null) {
				campaignDto.setStatus(obj[7].toString());
			}
			if (obj[8] != null) {
				campaignDto.setCreateTime((Date) obj[8]);
			}
			if (obj[9] != null) {
				campaignDto.setCreateUser(obj[9].toString());
			}
			if (obj[10] != null) {
				campaignDto.setUpdateTime((Date) obj[10]);
			}
			if (obj[11] != null) {
				campaignDto.setUpdateUser(obj[11].toString());
			}
			sendList = smsSendDao.getbyCampaign(campaignDto.getCampaignId());
			for (SmsSend smsSend : sendList) {
				if (smsSend.getRecordcount() == null) {
					recordCountLong = recordCountLong + 0l;
				} else {
					recordCountLong = recordCountLong
							+ smsSend.getRecordcount();
				}
			}
			campaignDto.setSmsCount(recordCountLong);
			if (obj[13] != null) {
				campaignDto.setSmsContent(obj[13].toString());
			}
			for (SmsSend smsSend : sendList) {
				if (smsSend.getReceiveCount() == null) {
					receiveCount = receiveCount + 0l;
				} else {
					receiveCount = receiveCount + smsSend.getReceiveCount();
				}
			}
			campaignDto.setReceiveCount(receiveCount);
			resultList.add(campaignDto);
		}
		return resultList;
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: getCampaignMonitorCount
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param campaignMonitor
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.smsapi.dao.CampaignMonitorDao#getCampaignMonitorCount
	 *      (com.chinadrtv.erp.smsapi.model.CampaignMonitor)
	 */
	@Override
	public Integer getCampaignMonitorCount(CampaignMonitorDto campaignMonitor) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer(
				"select count (c.id) from CampaignMonitor c where 1=1 ");
		Map<String, Parameter> parameters = gensql(campaignMonitor, hql);

		return this.findPageCount(hql.toString(), parameters);
	}

	private Map<String, Parameter> gensql(
			CampaignMonitorDto campaignMonitorDto, StringBuffer hql) {
		Map<String, Parameter> parameters = new HashMap<String, Parameter>();
		if (campaignMonitorDto.getCampaignId() != null) {
			hql.append(" and c.campaignId=:campaignId");
			Parameter campaign = new ParameterLong("campaignId",
					campaignMonitorDto.getCampaignId());
			parameters.put("campaignId", campaign);
		}
		if (!StringUtil.isNullOrBank(campaignMonitorDto.getStatus())) {
			hql.append(" and c.status=:status");
			Parameter status = new ParameterString("status",
					campaignMonitorDto.getStatus());
			parameters.put("status", status);
		}
		if (!StringUtils.isEmpty(campaignMonitorDto.getStartTimes())) {
			hql.append(" AND c.createTime >= TO_DATE(:starttime, 'YYYY-MM-DD HH24:MI:SS')");
			Parameter starttime = null;
			try {
				starttime = new ParameterString("starttime",
						campaignMonitorDto.getStartTimes());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			parameters.put("starttime", starttime);
		}
		if (!StringUtils.isEmpty(campaignMonitorDto.getEndTimes())) {
			hql.append(" AND c.createTime <= TO_DATE(:endtime, 'YYYY-MM-DD HH24:MI:SS')");
			Parameter endtime = null;
			try {
				endtime = new ParameterString("endtime",
						campaignMonitorDto.getEndTimes());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			parameters.put("endtime", endtime);
		}
		if (!StringUtils.isEmpty(campaignMonitorDto.getCreateUser())) {
			hql.append(" AND c.createUser =:createUser");
			Parameter createUser = new ParameterString("createUser",
					campaignMonitorDto.getCreateUser());
			parameters.put("createUser", createUser);
		}

		return parameters;
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: getByCampaignId
	 * </p>
	 * <p>
	 * Description:根据campaignid 查询
	 * </p>
	 * 
	 * @param campaignId
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.smsapi.dao.CampaignMonitorDao#getByCampaignId(java.
	 *      lang.String)
	 */
	@Override
	public CampaignMonitor getByCampaignId(Long campaignId) {
		// TODO Auto-generated method stub
		String sql = "from  CampaignMonitor t where t.campaignId = ? ";
		List<CampaignMonitor> list = (List<CampaignMonitor>) getHibernateTemplate()
				.find(sql, new Object[] { campaignId });
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

}
