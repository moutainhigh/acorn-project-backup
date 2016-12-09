package com.chinadrtv.erp.knowledge.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.knowledge.dao.CategoryDao;
import com.chinadrtv.erp.knowledge.model.KnowledgeCategory;

@SuppressWarnings("unchecked")
@Repository
public class CategoryDaoImpl extends
		GenericDaoHibernate<KnowledgeCategory, Long> implements CategoryDao {

	private static final String RECURSION_SQL = "SELECT T.* FROM ACOAPP_MARKETING.KNOWLEDGE_CATEGORY T "
			+ "WHERE T.DEPART_TYPE LIKE 'ALL' OR T.DEPART_TYPE LIKE ? "
			+ "START WITH T.PARENT_ID = -1 CONNECT BY PRIOR T.ID = T.PARENT_ID";

	/**
	 * 
	 */
	public CategoryDaoImpl() {
		super(KnowledgeCategory.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinadrtv.erp.knowledge.dao.CategoryDao#recursion()
	 */
	@Override
	public List<KnowledgeCategory> recursion(final String departType) {
		return getHibernateTemplate().executeFind(
				new HibernateCallback<List<KnowledgeCategory>>() {
					@Override
					public List<KnowledgeCategory> doInHibernate(Session session)
							throws HibernateException, SQLException {
						SQLQuery query = session.createSQLQuery(RECURSION_SQL);
						query.setString(0, departType);
						return query.addEntity(KnowledgeCategory.class).list();
					}
				});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinadrtv.erp.knowledge.dao.CategoryDao#hasChildren(java.lang.Long)
	 */
	@Override
	public boolean hasChildren(final Long id) {
		return getHibernateTemplate().execute(new HibernateCallback<Boolean>() {
			@Override
			public Boolean doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria criteria = session
						.createCriteria(KnowledgeCategory.class);
				criteria.add(Restrictions.eq("parentId", id));
				criteria.setProjection(Projections.rowCount());
				Long result = (Long) criteria.uniqueResult();
				return result != null && result > 0;
			}
		});
	}

}
