package com.chinadrtv.erp.uc.dao.hibernate;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.marketing.LeadInteraction;
import com.chinadrtv.erp.uc.dao.LeadInteractionDao;
/**
 * 
 *  
 * @author haoleitao
 * @date 2013-5-14 下午1:46:06
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Repository
public class LeadInteractionDaoImpl extends GenericDaoHibernate<LeadInteraction, String> implements LeadInteractionDao {

	public LeadInteractionDaoImpl() {
		super(LeadInteraction.class);
	}

	public List<LeadInteraction> getAllLeadInteractionByContactId(String contactId,int index, int size) {
    	StringBuffer sql = new StringBuffer("from LeadInteraction a where a.contactId=:contactid ");
		Map<String, Parameter> params = new HashMap<String, Parameter>();
		Parameter contactIdParams = new ParameterString("contactid", contactId);
		Parameter page = new ParameterInteger("page", index);
		page.setForPageQuery(true);
		Parameter rows = new ParameterInteger("rows", size);
		rows.setForPageQuery(true);
		params.put("contactIdParams", contactIdParams);
		params.put("page", page);
		params.put("rows", rows);
		sql.append(" order by createDate desc");
		List<LeadInteraction> objList = findPageList(sql.toString(), params);
		return objList;
	}


	public int getAllLeadInteractionByContactIdCount(String contactId) {
		StringBuffer sql = new StringBuffer("select count(id) from LeadInteraction a  where  a.contactId=:contactid ");
		Map<String, Parameter> params = new HashMap<String, Parameter>();
		Parameter contactIdParams = new ParameterString("contactid", contactId);
		params.put("contactIdParams", contactIdParams);
		Integer count = findPageCount(sql.toString(), params);
		return count;
	}

	@Override
	public int countLeadInteractionByAgent(final String agentId, final Date begin, final Date end) {
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(LeadInteraction.class);
				criteria.add(Restrictions.eq("createUser", agentId));
				criteria.add(Restrictions.ge("createDate", begin));
				criteria.add(Restrictions.lt("createDate", end));
				criteria.setProjection(Projections.count("id"));
				return ((Long) criteria.uniqueResult()).intValue();
			}
		});
	}

}
