/*
 * @(#)SmsTemplatesDao.java 1.0 2013-5-15下午4:28:06
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.dao.SmsTemplatesDao;
import com.chinadrtv.erp.smsapi.model.SmsTemplate;
import com.chinadrtv.erp.smsapi.util.StringUtil;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-5-15 下午4:28:06
 * 
 */
@Repository
public class SmsTemplatesDaoImpl extends
		GenericDaoHibernate<SmsTemplate, java.lang.Long> implements
		SmsTemplatesDao {

	public SmsTemplatesDaoImpl() {
		super(SmsTemplate.class);
	}

	/*
	 * (非 Javadoc) <p>Title: setSessionFactory</p> <p>Description: </p>
	 * 
	 * @param sessionFactory
	 * 
	 * @see
	 * com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate#setSessionFactory
	 * (org.hibernate.SessionFactory)
	 */
	@Override
	@Autowired
	@Qualifier("sessionFactorySms")
	public void setSessionFactory(SessionFactory sessionFactory) {
		// TODO Auto-generated method stub
		super.setSessionFactory(sessionFactory);
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: getByDepartment
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param department
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.sms.dao.SmsTemplateDao#getByDepartment(java.lang.String
	 *      )
	 */
	public List<SmsTemplate> getByDepartment(String department, String theme) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("from SmsTemplate s where 1=1 ");
		if (!StringUtil.isNullOrBank(department)) {
			sql.append(" and s.exeDepartment = '" + department + "'");
		}
		if (!StringUtil.isNullOrBank(theme)) {
			sql.append(" and  s.themeTemplate = '" + theme + "'");
		}
		List<SmsTemplate> list = getHibernateTemplate().find(sql.toString());
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.dao.SmsTemplateDao#query(com.chinadrtv.erp
	 * .marketing.dto.SmsTemplateDto,
	 * com.chinadrtv.erp.marketing.common.DataGridModel)
	 */
	public List<SmsTemplate> query(String department, String theme,
			DataGridModel dataModel) {
		StringBuffer hql = new StringBuffer();
		hql.append("select s from SmsTemplate s where 1=1 ");
		if (!StringUtil.isNullOrBank(department)) {
			hql.append(" and s.exeDepartment = '" + department + "'");
		}
		if (!StringUtil.isNullOrBank(theme)) {
			hql.append(" and s.themeTemplate = '" + theme + "'");
		}
		hql.append(" order by s.crttime desc");

		Map<String, Parameter> params = new HashMap<String, Parameter>();

		Parameter page = new ParameterInteger("page", dataModel.getStartRow());
		page.setForPageQuery(true);

		Parameter rows = new ParameterInteger("rows", dataModel.getRows());
		rows.setForPageQuery(true);

		params.put("page", page);
		params.put("rows", rows);

		List stDtoList = this.findPageList(hql.toString(), params);

		return stDtoList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.dao.SmsTemplateDao#queryCount(com.chinadrtv
	 * .erp.marketing.dto.SmsTemplateDto)
	 */
	public int queryCount(String department, String theme) {
		StringBuffer hql = new StringBuffer();
		hql.append("select count(*) from SmsTemplate s where 1=1 ");
		if (!StringUtil.isNullOrBank(department)) {
			hql.append(" and s.deptid = '" + department + "'");
		}
		if (!StringUtil.isNullOrBank(theme)) {
			hql.append(" and s.themeTemplate = '" + theme + "'");
		}
		hql.append(" order by s.crttime desc");

		Map<String, Parameter> params = new HashMap<String, Parameter>();

		Integer count = this.findPageCount(hql.toString(), params);

		return count;
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: queryByTemplateId
	 * </p>
	 * <p>
	 * Description: 根据id 查询模板
	 * </p>
	 * 
	 * @param templateId
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.smsapi.dao.SmsTemplatesDao#queryByTemplateId(java.lang
	 *      .String)
	 */
	@Override
	public SmsTemplate queryByTemplateId(String id) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().load(SmsTemplate.class, id);
	}

}
