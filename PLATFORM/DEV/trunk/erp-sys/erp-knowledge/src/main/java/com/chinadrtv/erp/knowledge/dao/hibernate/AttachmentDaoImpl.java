package com.chinadrtv.erp.knowledge.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.knowledge.dao.AttachmentDao;
import com.chinadrtv.erp.knowledge.model.KnowledgeAttachment;

@Repository
public class AttachmentDaoImpl extends
		GenericDaoHibernate<KnowledgeAttachment, Long> implements AttachmentDao {

	public AttachmentDaoImpl() {
		super(KnowledgeAttachment.class);
	}

	@Override
	public KnowledgeAttachment get(Long id) {
		return getHibernateTemplate().get(KnowledgeAttachment.class, id);
	}

	@Override
	public Integer countByArticleId(final Long articleId) {
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria criteria = session
						.createCriteria(KnowledgeAttachment.class);
				criteria.setProjection(Projections.rowCount());
				criteria.add(Restrictions.eq("articleId", articleId));
				criteria.add(Restrictions.eq("status", "Y"));
				return ((Long) criteria.uniqueResult()).intValue();
			}
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<KnowledgeAttachment> listByArticleId(final Long articleId) {
		return getHibernateTemplate().executeFind(
				new HibernateCallback<List<KnowledgeAttachment>>() {
					@Override
					public List<KnowledgeAttachment> doInHibernate(
							Session session) throws HibernateException,
							SQLException {
						Criteria criteria = session
								.createCriteria(KnowledgeAttachment.class);
						criteria.add(Restrictions.eq("articleId", articleId));
						criteria.add(Restrictions.eq("status", "Y"));
						return criteria.list();
					}
				});
	}
}
