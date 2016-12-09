package com.chinadrtv.erp.marketing.core.dao.hibernate;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterLong;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.core.dao.query.ParameterTimestamp;
import com.chinadrtv.erp.marketing.core.common.Constants;
import com.chinadrtv.erp.marketing.core.dao.CampaignDao;
import com.chinadrtv.erp.marketing.core.dto.CampaignDto;
import com.chinadrtv.erp.marketing.core.util.StringUtil;
import com.chinadrtv.erp.model.marketing.Campaign;
import com.chinadrtv.erp.model.marketing.CampaignType;
import com.chinadrtv.erp.model.marketing.LeadType;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.util.DateTimeUtil;
import com.chinadrtv.erp.user.util.PermissionHelper;
import com.chinadrtv.erp.util.DateUtil;

/**
 * 
 * <dl>
 * <dt><b>Title:营销计划管理实现</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:营销计划管理实现</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午3:19:43
 * 
 */
@Repository
public class CampaignDaoImpl extends
		GenericDaoHibernate<Campaign, java.lang.Long> implements Serializable,
		CampaignDao {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CampaignDaoImpl.class);
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	public CampaignDaoImpl() {
		super(Campaign.class);
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 分页查询符合条件，
	 */
	public List<Campaign> query(CampaignDto Campaign, DataGridModel dataModel) {
		StringBuffer sql = new StringBuffer("from Campaign o where 1=1 ");

		Map<String, Parameter> params = genSql(sql, Campaign);

		Parameter page = new ParameterInteger("page", dataModel.getStartRow());
		page.setForPageQuery(true);

		Parameter rows = new ParameterInteger("rows", dataModel.getRows());
		rows.setForPageQuery(true);

		//行级权限过滤
		String whereCondition = PermissionHelper.getFilterCondition();
		System.out.println("权限过滤" + whereCondition + "==");
		if (!StringUtils.isEmpty(whereCondition)) {
			sql.append(" and ").append(whereCondition);
			Map<String, Parameter> configParams = PermissionHelper
					.getFilterParameter();
			params.putAll(configParams);
		}
		params.put("page", page);
		params.put("rows", rows);
		sql.append(" order by o.id desc");
		List<Campaign> objList = findPageList(sql.toString(), params);
		return objList;
	}

	/**
	 * 查询总共记录
	 */
	public Integer queryCount(CampaignDto Campaign) {
		StringBuffer sql = new StringBuffer(
				"select count(o.id) from Campaign o where 1=1 ");

		Map<String, Parameter> params = genSql(sql, Campaign);

		// 行级权限过滤
		String whereCondition = PermissionHelper.getFilterCondition();
		if (!StringUtils.isEmpty(whereCondition)) {
			sql.append(" and ").append(whereCondition);
			Map<String, Parameter> configParams = PermissionHelper
					.getFilterParameter();
			params.putAll(configParams);
		}

		Integer result = findPageCount(sql.toString(), params);

		return result;
	}

	/**
	 * 生成查询条件
	 * 
	 * @Description: TODO
	 * @param sql
	 * @param Campaign
	 * @return
	 * @return Map<String,Parameter>
	 * @throws
	 */
	public Map<String, Parameter> genSql(StringBuffer sql, CampaignDto Campaign) {

		Map<String, Parameter> paras = new HashMap<String, Parameter>();

		if (Campaign.getType() != null) {
			sql.append(" AND o.campaignTypeId=:campaignTypeId");
			Parameter campaignTypeId = new ParameterLong("campaignTypeId",
					Campaign.getType());
			paras.put("campaignTypeId", campaignTypeId);
		}

		if (!StringUtils.isEmpty(Campaign.getStatus())) {
			sql.append(" AND o.status=:status");
			Parameter status = new ParameterString("status",
					Campaign.getStatus());
			paras.put("status", status);
		}

		if (!StringUtils.isEmpty(Campaign.getStartDate())) {
			sql.append(" AND o.startDate >= :startDate");
			Parameter startDate = null;
			try {
				startDate = new ParameterTimestamp("startDate",
						DateUtil.string2Date(Campaign.getStartDate(),
								"yyyy-MM-dd HH:mm:ss"));
			} catch (SQLException e) {
                logger.error("",e);
			}
			paras.put("startDate", startDate);
		}

		if (!StringUtils.isEmpty(Campaign.getEndDate())) {
			sql.append(" AND o.endDate <= :endDate");
			Parameter endDate = null;
			try {
				endDate = new ParameterTimestamp("endDate",
						DateUtil.string2Date(Campaign.getEndDate(),
								"yyyy-MM-dd HH:mm:ss"));
			} catch (SQLException e) {
				logger.error("",e);
			}
			paras.put("endDate", endDate);
		}

		if (!StringUtils.isEmpty(Campaign.getUser())) {
			sql.append(" AND o.owner = :owner");
			Parameter crtUser = new ParameterString("owner", Campaign.getUser());
			paras.put("owner", crtUser);
		}

		if (!StringUtils.isEmpty(Campaign.getName())) {
			sql.append(" AND o.name like :name");
			Parameter groupName = new ParameterString("name", "%"
					+ Campaign.getName() + "%");
			paras.put("name", groupName);
		}

		return paras;

	}

	/**
	 * 查询所有营销计划
	 */
	public List<Campaign> queryList() {
		String hql = "from Campaign o where 1=1";
		List<Campaign> list = getHibernateTemplate().find(hql);
		return list;
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
	public List<CampaignDto> queryInboundCampaign(String tollFreeNums,
			String callTime) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(
				"select  o.id,o.name,o.status,o.startDate,o.endDate,o.owner,o.description,"
						+ "o.createUser,o.department,o.tollFreeNum,o.dnis,o.campaignType,o.leadType,o.execDepartment from   Campaign o  where 1=1 ");
		Map<String, Parameter> paras = new HashMap<String, Parameter>();
		if (!StringUtil.isNullOrBank(tollFreeNums)) {
			sql.append("AND o.tollFreeNum = :tollFreeNum");
			Parameter tollFreeNum = new ParameterString("tollFreeNum",
					tollFreeNums);
			paras.put("tollFreeNum", tollFreeNum);
		}
		if (callTime == null || callTime.equals("")) {
			callTime = DateTimeUtil.sim3.format(new Date());
		}
		sql.append(" AND o.startDate <=:startDate AND o.endDate >= :endDate ");
		Parameter startDate = null;
		Parameter endDate = null;
		try {
			startDate = new ParameterTimestamp("startDate",
					DateUtil.string2Date(callTime, "yyyy-MM-dd HH:mm:ss"));

			endDate = new ParameterTimestamp("endDate", DateUtil.string2Date(
					callTime, "yyyy-MM-dd HH:mm:ss"));
		} catch (Exception e) {
			logger.error("",e);
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
						+ "o.createUser,o.department,o.tollFreeNum,o.dnis,o.campaignType,o.leadType,o.execDepartment"
						+ " from Campaign o  where o.audience = :audience ");
		Map<String, Parameter> paras = new HashMap<String, Parameter>();
		if (StringUtil.isNullOrBank(execDepartments)) {
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

	/**
	 * TODOX: psql
	 */
	public List<Object> queryCampaignParas(long camID) {
		StringBuilder sql = new StringBuilder(
				"select ltp.id, ltp.name, ltp.type, cam.name as camName from "
						+ Constants.MARKETING_SCHEMA
						+ ".campaign cam "
						+ "left join "
						+ Constants.MARKETING_SCHEMA
						+ ".lead_type leadType on cam.lead_type_id=leadType.Id "
						+ "left join "
						+ Constants.MARKETING_SCHEMA
						+ ".lead_type_params ltp on ltp.lead_type_id=leadType.Id "
						+ "where cam.id=:camID");
		Query sqlQuery = this.getSessionFactory().getCurrentSession()
				.createSQLQuery(sql.toString());
		sqlQuery.setLong("camID", camID);
		List<Object> objList = sqlQuery.list();
		return objList;
	}

	/**
	 * TODOX: psql
	 */
	public List<Object> queryCampaignParaValue(long camID, long camParaID) {
		StringBuilder sql = new StringBuilder();
		sql.append("select ltv.value from " + Constants.MARKETING_SCHEMA
				+ ".lead_type_value ltv where ltv.campaign_id=:camID");
		sql.append(" and ltv.lead_type_params_id=:camParaID");
		Query sqlQuery = this.getSessionFactory().getCurrentSession()
				.createSQLQuery(sql.toString());
		sqlQuery.setLong("camID", camID);
		sqlQuery.setLong("camParaID", camParaID);
		List<Object> objList = sqlQuery.list();
		return objList;
	}

	/**
	 * 根据jobId查询所有营销计划
	 */
	public List<Campaign> queryList(String jobId, Integer campaignType,
			Date date) {
		String hql = "from Campaign o " + "where o.group.jobId=:jobId "
				+ "and campaignTypeId=:campaignType "
				+ "and startDate<=:startDate and endDate>=:endDate";
		Map<String, Parameter> paras = new HashMap<String, Parameter>();
		Parameter jobIdParam = new ParameterString("jobId", jobId);
		Parameter campaignTypeParam = new ParameterInteger("campaignType",
				campaignType);
		Parameter startDateParam = new ParameterTimestamp("startDate", date);
		Parameter endDateParam = new ParameterTimestamp("endDate", date);
		paras.put("jobId", jobIdParam);
		paras.put("campaignType", campaignTypeParam);
		paras.put("startDate", startDateParam);
		paras.put("endDate", endDateParam);

		List<Campaign> list = this.findList(hql, paras);
		return list;
	}

	public Integer getUserCountForCampaigns(String groupCode, Long campaignId,
			Date now) {
		Campaign campaign = get(campaignId);
		Date customerCrDate = campaign.getGroup().getCrtDate();
		StringBuilder sql = new StringBuilder("SELECT  count(*)  FROM (");
		sql.append("SELECT /*+ first_rows*/ PHN2, CONTACTID, ROWNUM AS NO FROM  iagent.phone p WHERE p.contactid in  ");
		sql.append("( select tmp.connectid   from (select t3.customer_id connectid   from Customer_Detail t3  "
				+ " where group_code = ?"
				+ " and t3.crt_date >= ?  "
				+ " and t3.crt_date <= ? ) tmp");
		sql.append(") ) ");
		return jdbcTemplate.queryForInt(sql.toString(), new Object[] {
				groupCode, customerCrDate, now });
	}

	public List<Map<String, Object>> queryPhoneListForCampaign(
			String groupCode, Integer begin, Integer end, Long campaignId,
			Date now) {
		Campaign campaign = get(campaignId);
		Date customerCrDate = campaign.getGroup().getCrtDate();
		StringBuilder sql = new StringBuilder(
				"SELECT  PHN2,PHN1,PHN3, CONTACTID,CHAIN_N,PRMPHN from (");
		sql.append("SELECT /*+ first_rows*/ PHN2,PHN1,PHN3,PRMPHN, CONTACTID,ROWNUM AS NO , rank()over( partition  by CONTACTID order by PRMPHN desc, PHN2  ) CHAIN_N FROM  iagent.phone  p , Customer_Detail t3 "
				+ " WHERE t3.customer_id = p.contactid  and t3.group_code = ? and t3.crt_date >= ?  and t3.crt_date <= ?   )");
		sql.append(" where no> ? and no <= ?");
		List<Map<String, Object>> phoneList = jdbcTemplate.queryForList(
				sql.toString(), new Object[] { groupCode, customerCrDate, now,
						begin, end });
		return phoneList;
	}
}
