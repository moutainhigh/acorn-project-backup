package com.chinadrtv.erp.marketing.core.dao.hibernate;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.marketing.core.dao.CampaignTypeDao;
import com.chinadrtv.erp.marketing.core.dto.CampaignDto;
import com.chinadrtv.erp.model.marketing.CampaignType;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

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
public class CampaignTypeDaoImpl extends
		GenericDaoHibernate<CampaignType, java.lang.Long> implements
		Serializable, CampaignTypeDao {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	public CampaignTypeDaoImpl() {
		super(CampaignType.class);
	}

	/**
	 * 分页查询符合条件，
	 */
	public List<CampaignType> query(CampaignDto Campaign,
			DataGridModel dataModel) {
		StringBuffer sql = new StringBuffer("from CampaignType o where 1=1 ");

		Map<String, Parameter> params = genSql(sql, Campaign);

		Parameter page = new ParameterInteger("page", dataModel.getStartRow());
		page.setForPageQuery(true);

		Parameter rows = new ParameterInteger("rows", dataModel.getRows());
		rows.setForPageQuery(true);

		params.put("page", page);
		params.put("rows", rows);
		sql.append(" order by o.id desc");
		List<CampaignType> objList = findPageList(sql.toString(), params);
		return objList;
	}

	/**
	 * 查询总共记录
	 */
	public Integer queryCount(CampaignDto Campaign) {
		StringBuffer sql = new StringBuffer(
				"select count(o.id) from CampaignType o where 1=1 ");

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
	 * 查询所有营销计划类型
	 */
	public List<CampaignType> queryList() {
		String hql = "from CampaignType where isVisible='1'";
		List<CampaignType> list = getHibernateTemplate().find(hql);
		return list;
	}

}
