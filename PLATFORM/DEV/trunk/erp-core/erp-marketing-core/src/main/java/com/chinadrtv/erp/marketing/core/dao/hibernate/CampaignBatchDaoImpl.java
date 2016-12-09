package com.chinadrtv.erp.marketing.core.dao.hibernate;

import java.io.Serializable;
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
import com.chinadrtv.erp.marketing.core.dao.CampaignBatchDao;
import com.chinadrtv.erp.marketing.core.dto.CampaignDto;
import com.chinadrtv.erp.marketing.core.dto.SmsSendDto;
import com.chinadrtv.erp.model.marketing.CampaignBatch;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.util.StringUtil;

/**
 * 
 * <dl>
 * <dt><b>Title:营销计划类型DAO实现</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:营销计划类型DAO实现</b></dt>
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
public class CampaignBatchDaoImpl extends
		GenericDaoHibernate<CampaignBatch, java.lang.Long> implements
		Serializable, CampaignBatchDao {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	public CampaignBatchDaoImpl() {
		super(CampaignBatch.class);
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 分页查询符合条件，
	 */
	public List<CampaignBatch> query(CampaignDto Campaign,
			DataGridModel dataModel) {
		StringBuffer sql = new StringBuffer("from CampaignBatch o where 1=1 ");

		Map<String, Parameter> params = genSql(sql, Campaign);

		Parameter page = new ParameterInteger("page", dataModel.getStartRow());
		page.setForPageQuery(true);

		Parameter rows = new ParameterInteger("rows", dataModel.getRows());
		rows.setForPageQuery(true);

		params.put("page", page);
		params.put("rows", rows);
		sql.append(" order by o.id desc");
		List<CampaignBatch> objList = findPageList(sql.toString(), params);
		return objList;
	}

	/**
	 * 查询总共记录
	 */
	public Integer queryCount(CampaignDto Campaign) {
		StringBuffer sql = new StringBuffer(
				"select count(o.id) from CampaignBatch o where 1=1 ");

		Map<String, Parameter> params = genSql(sql, Campaign);

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

		// if (!StringUtils.isEmpty(Campaign.getType())) {
		// sql.append(" AND o.type=:type");
		// Parameter groupCode = new ParameterString("type",
		// Campaign.getType());
		// paras.put("type", groupCode);
		// }
		//
		// if (!StringUtils.isEmpty(Campaign.getStatus())) {
		// sql.append(" AND o.status=:status");
		// Parameter status = new ParameterString("status",
		// Campaign.getStatus());
		// paras.put("status", status);
		// }
		//
		// if (!StringUtils.isEmpty(Campaign.getStartDate())) {
		// sql.append(" AND o.startDate >= :startDate");
		// Parameter startDate = null;
		// try {
		// startDate = new ParameterTimestamp("startDate", DateUtil.string2Date(
		// Campaign.getStartDate(), "yyyy-MM-dd HH:mm:ss"));
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// paras.put("startDate", startDate);
		// }
		//
		// if (!StringUtils.isEmpty(Campaign.getEndDate())) {
		// sql.append(" AND o.endDate <= :endDate");
		// Parameter endDate = null;
		// try {
		// endDate = new ParameterTimestamp("endDate", DateUtil.string2Date(
		// Campaign.getEndDate(), "yyyy-MM-dd HH:mm:ss"));
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// paras.put("endDate", endDate);
		// }
		//
		// if (!StringUtils.isEmpty(Campaign.getUser())) {
		// sql.append(" AND o.owner = :owner");
		// Parameter crtUser = new ParameterString("owner", Campaign.getUser());
		// paras.put("owner", crtUser);
		// }
		//
		// if (!StringUtils.isEmpty(Campaign.getName())) {
		// sql.append(" AND o.name like :name");
		// Parameter groupName = new ParameterString("name", "%" +
		// Campaign.getName() + "%");
		// paras.put("name", groupName);
		// }

		return paras;

	}

	/**
	 * 查询所有营销计划批次
	 */
	public List<CampaignBatch> queryList(Long campaignId, String status) {
		StringBuffer hql = new StringBuffer(
				"from CampaignBatch t where t.campaignId=:campaignId ");

		Map<String, Parameter> params = new HashMap<String, Parameter>();

		Parameter campaignIdParam = new ParameterLong("campaignId", campaignId);
		if (!StringUtil.isNullOrBank(status)) {
			Parameter statusParam = new ParameterString("status", status);
			params.put("status", statusParam);
			hql.append("and t.status=:status");
		}
		params.put("campaignId", campaignIdParam);
		List<CampaignBatch> objList = findList(hql.toString(), params);
		return objList;
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: getCampaignBatchByBatchId
	 * </p>
	 * <p>
	 * Description: 根据批次id
	 * </p>
	 * 
	 * @param batchId
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.core.dao.CampaignBatchDao#
	 *      getCampaignBatchByBatchId(java.lang.String)
	 */
	public CampaignBatch getCampaignBatchByCampaignId(Long campaignId) {
		// TODO Auto-generated method stub
		String hql = "from CampaignBatch t where t.campaignId=:campaignId ";
		Map<String, Parameter> params = new HashMap<String, Parameter>();
		Parameter campaignIdParam = new ParameterLong("campaignId", campaignId);
		params.put("campaignId", campaignIdParam);
		List<CampaignBatch> objList = findList(hql, params);
		return objList.size() > 0 ? objList.get(0) : null;
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: getSmsDetailByCampaingId
	 * </p>
	 * <p>
	 * Description: 根据campaignId 和短信号码查询短信详情
	 * </p>
	 * 
	 * @param campaignId
	 * 
	 * @param mobile
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.core.dao.CampaignBatchDao#
	 *      getSmsDetailByCampaingId(java.lang.Long, java.lang.String)
	 */
	public SmsSendDto getSmsDetailByCampaingId(Long campaignId, String mobile) {
		// TODO Auto-generated method stub
		String sql = "select s.content,s.uuid,s.receive_status,s.feedback_status  "
				+ " from ACOAPP_MARKETING.Campaign_Batch c   left join ACOAPP_MARKETING.sms_send_detail s   "
				+ "  on c.batch_id = s.batchid"
				+ "   where c.campaign_id = "
				+ campaignId
				+ "    and s.mobile = '"
				+ mobile
				+ "' and s.smsstop_status='10'  order by s.id desc";
		// Session session = getSession();
		// Query query = session.createSQLQuery(sql).setResultTransformer(
		// Transformers.ALIAS_TO_ENTITY_MAP);

		List<Map<String, Object>> listmap = jdbcTemplate.queryForList(sql);
		if (listmap != null && !listmap.isEmpty()) {
			SmsSendDto smsSendDto = new SmsSendDto();
			smsSendDto.setUuid(listmap.get(0).get("UUID").toString());
			smsSendDto.setSmsContent(listmap.get(0).get("CONTENT").toString());
			String smsStatus = "";
			if (listmap.get(0).get("RECEIVE_STATUS").toString().equals("1")) {
				smsStatus = "提交成功";
			} else if (listmap.get(0).get("RECEIVE_STATUS").toString()
					.equals("0")) {
				smsStatus = "提交失败";
			} else if (listmap.get(0).get("FEEDBACK_STATUS").toString()
					.equals("1")) {
				smsStatus = "发送成功";
			} else if (listmap.get(0).get("FEEDBACK_STATUS").toString()
					.equals("1")) {
				smsStatus = "发送失败";
			} else {
				smsStatus = "无结果";
			}
			smsSendDto.setSmsStatus(smsStatus);
			return smsSendDto;
		} else {
			return null;
		}
	}
}
