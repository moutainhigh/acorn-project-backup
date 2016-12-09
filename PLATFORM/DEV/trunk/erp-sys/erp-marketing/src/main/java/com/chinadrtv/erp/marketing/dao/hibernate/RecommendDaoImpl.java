/*
 * @(#)NamesDaoImpl.java 1.0 2013-1-22下午2:24:24
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.marketing.dao.RecommendDao;
import com.chinadrtv.erp.marketing.dto.RecommendConfDto;
import com.chinadrtv.erp.model.marketing.RecommendConf;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

/**
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * 推荐产品规则配置</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author zhaizy
 * @version 1.0
 * @since 2013-1-22 下午2:24:24
 * 
 */
@Repository
public class RecommendDaoImpl extends
		GenericDaoHibernate<RecommendConf, java.lang.Long> implements
		RecommendDao {

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
	public RecommendDaoImpl() {
		super(RecommendConf.class);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (非 Javadoc) <p>Title: queryNamesList</p> <p>Description: </p>
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.dao.NamesDao#queryNamesList()
	 */
	public List<RecommendConf> queryList(DataGridModel dataModel) {
		String hql = "select t from RecommendConf t" + " left join t.group"
				+ " left join t.prod1" + " left join t.prod2"
				+ " left join t.prod3";
		Map<String, Parameter> params = new HashMap<String, Parameter>();
		Parameter page = new ParameterInteger("page", dataModel.getStartRow());
		page.setForPageQuery(true);

		Parameter rows = new ParameterInteger("rows", dataModel.getRows());
		rows.setForPageQuery(true);

		params.put("page", page);
		params.put("rows", rows);
		List<RecommendConf> list = this.findPageList(hql, params);
		return list;
	}

	public Integer count() {
		String hql = "select count(*) from RecommendConf t"
				+ " left join t.group" + " left join t.prod1"
				+ " left join t.prod2" + " left join t.prod3";
		Query q = getSession().createQuery(hql);
		List list = q.list();
		return Integer.valueOf(list.get(0).toString());
	}

	/*
	 * (非 Javadoc) <p>Title: querRecommendConf</p> <p>Description: </p>
	 * 
	 * @param recommendConfDto
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.dao.RecommendDao#querRecommendConf(com.chinadrtv
	 * .erp.marketing.dto.RecommendConfDto)
	 */
	public RecommendConf getRecommendConf(RecommendConfDto recommendConfDto) {
		StringBuffer hql = new StringBuffer(
				"select t from RecommendConf t where 1=1 ");
		Map<String, Parameter> params = new HashMap<String, Parameter>();

		// hql.append(" and t.valid_start<=:validStartDate");
		// Parameter validStartDate = new ParameterDate("validStartDate", new
		// Date());
		// params.put("validStartDate", validStartDate);
		//
		// hql.append(" and t.valid_end>=:validEndDate");
		// Parameter validEndDate = new ParameterDate("validEndDate", new
		// Date());
		// params.put("validEndDate", validEndDate);

		// if(!StringUtils.isEmpty(recommendConfDto.getDepartment())){
		// hql.append(" and t.department=:department");
		// Parameter department = new ParameterString("department",
		// recommendConfDto.getDepartment());
		// params.put("department", department);
		// }
		if (!StringUtils.isEmpty(recommendConfDto.getAgentLevel())) {
			hql.append(" and t.agent_level=:agentLevel");
			Parameter agentLevel = new ParameterString("agentLevel",
					recommendConfDto.getAgentLevel());
			params.put("agent_level", agentLevel);
		}
		if (!StringUtils.isEmpty(recommendConfDto.getCustomerLevel())) {
			hql.append(" and t.customer_level=:customerLevel");
			Parameter customerLevel = new ParameterString("customerLevel",
					recommendConfDto.getCustomerLevel());
			params.put("customerLevel", customerLevel);
		}

		if (!StringUtils.isEmpty(recommendConfDto.getGroupId())) {
			hql.append(" and t.groupid=:groupId");
			Parameter groupId = new ParameterString("groupId",
					recommendConfDto.getGroupId());
			params.put("groupId", groupId);
		}

		List<RecommendConf> list = this.findList(hql.toString(), params);
		if (list.size() > 0)
			return list.get(0);
		return null;
	}

	/**
	 * 根据客户群id查询推荐规则
	 * 
	 * @Description: TODO
	 * @param groupId
	 * @return
	 * @return RecommendConf
	 * @throws
	 */
	public RecommendConf getRecommendConf(String groupId) {
		StringBuffer hql = new StringBuffer(
				"select t from RecommendConf t where 1=1 ");
		Map<String, Parameter> params = new HashMap<String, Parameter>();

		// hql.append(" and t.valid_start<=:validStartDate");
		// Parameter validStartDate = new ParameterDate("validStartDate", new
		// Date());
		// params.put("validStartDate", validStartDate);
		//
		// hql.append(" and t.valid_end>=:validEndDate");
		// Parameter validEndDate = new ParameterDate("validEndDate", new
		// Date());
		// params.put("validEndDate", validEndDate);

		if (!StringUtils.isEmpty(groupId)) {
			hql.append(" and t.groupid=:groupId");
			Parameter groupIdPara = new ParameterString("groupId", groupId);
			params.put("groupId", groupIdPara);
		}

		List<RecommendConf> list = this.findList(hql.toString(), params);
		if (list.size() > 0)
			return list.get(0);
		return null;
	}

}
