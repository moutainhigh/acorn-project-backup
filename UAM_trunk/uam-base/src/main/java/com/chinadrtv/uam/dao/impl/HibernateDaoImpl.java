package com.chinadrtv.uam.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.ResultTransformer;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.chinadrtv.uam.dao.HibernateDao;
import com.chinadrtv.uam.dao.wrapper.AliasWrapper;
import com.chinadrtv.uam.dao.wrapper.CriteriaWrapper;
import com.chinadrtv.uam.dao.wrapper.FetchModeWrapper;
import com.chinadrtv.uam.dao.wrapper.RowBounds;
import com.chinadrtv.uam.model.BaseEntity;
import com.chinadrtv.uam.utils.CollectionUtils;

/**
 * Base Hibernate Dao Implement.
 * 
 * @author Qianyong,Deng
 * @since Oct 2, 2012
 *
 */
@Repository("hibernateDao")
@SuppressWarnings({"unchecked", "deprecation"})
public class HibernateDaoImpl extends HibernateDaoSupport implements HibernateDao {
	
	/**
	 * Used for null parameter in sql query
	 */
	private static final Object[] NULL_OBJECT_ARRAY = null;
	
	private static final String HQL_MAP_KEYWORDS_REGEX = "new\\s*map{1}\\s*\\([\\w|\\W|\\s|\\S]*\\)";
	
	private static final String HQL_COUNT_KEYWORDS_REGEX = "count{1}\\s*\\([\\w|\\W|\\s|\\S]*\\)";
	
	/******************************************************************/
	/**********      Inject Hibernate Session Factory      ************/
	/******************************************************************/
	
	/**
	 * This method will be call by spring container and inject session factory
	 * on startup automatically.
	 * 
	 * @param sessionFactory
	 *            session factory for hibernate
	 */
	@Resource
	public void setInjectSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	
	/******************************************************************/
	/**********          Hibernate Base Operation          ************/
	/******************************************************************/
	
	/*
	 * (non-Javadoc)
	 * @see com.arvato.npass.common.dao.HibernateDao#get(java.lang.Class, java.io.Serializable)
	 */
	@Override
	public <E extends BaseEntity> E get(Class<E> entityClass, Serializable id) {
		return getHibernateTemplate().get(entityClass, id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.arvato.npass.common.dao.HibernateDao#load(java.lang.Class, java.io.Serializable)
	 */
	@Override
	public <E extends BaseEntity> E load(Class<E> entityClass, Serializable id) {
		return getHibernateTemplate().load(entityClass, id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.arvato.npass.common.dao.HibernateDao#getAll(java.lang.Class)
	 */
	@Override
	public <E extends BaseEntity> List<E> getAll(Class<E> entityClass) {
		return getHibernateTemplate().loadAll(entityClass);
	}

	/*
	 * (non-Javadoc)
	 * @see com.arvato.npass.common.dao.HibernateDao#save(com.arvato.npass.common.entity.BaseEntity)
	 */
	@Override
	public <K extends Serializable, E extends BaseEntity> K save(final E e) {
		return (K) getHibernateTemplate().save(e);
	}

	/*
	 * (non-Javadoc)
	 * @see com.arvato.npass.common.dao.HibernateDao#merge(com.arvato.npass.common.entity.BaseEntity)
	 */
	@Override
	public <E extends BaseEntity> E merge(final E e) {
		return getHibernateTemplate().merge(e);
	}

	/*
	 * (non-Javadoc)
	 * @see com.arvato.npass.common.dao.HibernateDao#saveOrUpdate(com.arvato.npass.common.entity.BaseEntity)
	 */
	@Override
	public <E extends BaseEntity> void saveOrUpdate(final E e) {
		getHibernateTemplate().saveOrUpdate(e);
	}

	/*
	 * (non-Javadoc)
	 * @see com.arvato.npass.common.dao.HibernateDao#update(com.arvato.npass.common.entity.BaseEntity)
	 */
	@Override
	public <E extends BaseEntity> void update(final E e) {
		getHibernateTemplate().update(e);
	}

	/*
	 * (non-Javadoc)
	 * @see com.arvato.npass.common.dao.HibernateDao#delete(com.arvato.npass.common.entity.BaseEntity)
	 */
	@Override
	public <E extends BaseEntity> void delete(final E e) {
		getHibernateTemplate().delete(e);
	}

	/*
	 * (non-Javadoc)
	 * @see com.arvato.npass.common.dao.HibernateDao#delete(java.lang.Class, java.io.Serializable)
	 */
	@Override
	public <K extends Serializable, E extends BaseEntity> void delete(final Class<E> entityClass, final K id) {
		E e = get(entityClass, id);
		if (e == null) return;
		delete(e);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.arvato.npass.common.dao.HibernateDao#evict(com.arvato.npass.common.entity.BaseEntity)
	 */
	@Override
	public <E extends BaseEntity> void evict(final E entity) {
		getHibernateTemplate().evict(entity);
	}
	
	
	/******************************************************************/
	/**********          Hibernate SQL Operation           ************/
	/******************************************************************/
	
	/*
	 * (non-Javadoc)
	 * @see com.arvato.npass.common.dao.HibernateDao#executeBySql(java.lang.String)
	 */
	@Override
	public int executeBySql(final String sql) {
		return getHibernateTemplate().bulkUpdate(sql);
	}

	/*
	 * (non-Javadoc)
	 * @see com.arvato.npass.common.dao.HibernateDao#executeBySql(java.lang.String, java.lang.Object[])
	 */
	@Override
	public int executeBySql(final String sql, final Object... params) {
		return getHibernateTemplate().bulkUpdate(sql, params);
	}

	/*
	 * (non-Javadoc)
	 * @see com.arvato.npass.common.dao.HibernateDao#findBySql(java.lang.String)
	 */
	@Override
	public Object[] findBySql(final String sql) {
		return findBySql(sql, RowBounds.DEFAULT, NULL_OBJECT_ARRAY);
	}

	/*
	 * (non-Javadoc)
	 * @see com.arvato.npass.common.dao.HibernateDao#findBySql(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Object[] findBySql(final String sql, final Object... params) {
		return getHibernateTemplate().execute(new HibernateCallback<Object[]>() {
			@Override
			public Object[] doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				if (params != null) {
					for (int pos = 0; pos < params.length; pos++) {
						query.setParameter(pos, params[pos]);
					}
				}
				return (Object[]) query.uniqueResult();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * @see com.arvato.npass.common.dao.HibernateDao#listBySql(java.lang.String)
	 */
	@Override
	public List<Object[]> listBySql(final String sql) {
		return listBySql(sql, RowBounds.DEFAULT, NULL_OBJECT_ARRAY);
	}

	/*
	 * (non-Javadoc)
	 * @see com.arvato.npass.common.dao.HibernateDao#listBySql(java.lang.String, java.lang.Object[])
	 */
	@Override
	public List<Object[]> listBySql(final String sql, final Object... params) {
		return listBySql(sql, RowBounds.DEFAULT, params);
	}

	/*
	 * (non-Javadoc)
	 * @see com.arvato.npass.common.dao.HibernateDao#listBySql(java.lang.String, com.arvato.npass.common.dao.wrapper.RowBounds, java.lang.Object[])
	 */
	@Override
	public List<Object[]> listBySql(final String sql, final RowBounds rowBounds, final Object... params) {
		return getHibernateTemplate().executeFind(new HibernateCallback<List<Object[]>>() {
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				if (params != null) {
					for (int pos = 0; pos < params.length; pos++) {
						query.setParameter(pos, params[pos]);
					}
				}
				setPagination(query, rowBounds);
				return query.list();
			}
		});
	}


	/******************************************************************/
	/**********          Hibernate HQL Operation           ************/
	/******************************************************************/
	
	/*
	 * (non-Javadoc)
	 * @see com.arvato.npass.common.dao.HibernateDao#executeByHql(java.lang.String)
	 */
	@Override
	public int executeByHql(final String hql) {
		return executeByHql(hql, null);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.arvato.npass.common.dao.HibernateDao#executeByHql(java.lang.String, java.util.Map)
	 */
	@Override
	public int executeByHql(final String hql, final Map<String, ?> params) {
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				setParameters(query, params);
				return query.executeUpdate();
			}
		});
	}
	/*
	 * (non-Javadoc)
	 * @see com.arvato.npass.common.dao.HibernateDao#countByHql(java.lang.String)
	 */
	@Override
	public int countByHql(final String hql) {
		return countByHql(hql, null);
	}

	/*
	 * (non-Javadoc)
	 * @see com.arvato.npass.common.dao.HibernateDao#countByHql(java.lang.String, java.util.Map)
	 */
	@Override
	public int countByHql(final String hql, final Map<String, ?> params) {
		if (!checkHasKeyWords(hql, HQL_COUNT_KEYWORDS_REGEX))
			throw new HibernateException("Count key words not found in HQL[" + hql + "].");
		return ((Long)findByHql(hql, params)).intValue();
	}

	/*
	 * (non-Javadoc)
	 * @see com.arvato.npass.common.dao.HibernateDao#findByHql(java.lang.String)
	 */
	@Override
	public <T extends Object> T findByHql(final String hql) {
		return findByHql(hql, null);
	}

	/*
	 * (non-Javadoc)
	 * @see com.arvato.npass.common.dao.HibernateDao#findByHql(java.lang.String, java.util.Map)
	 */
	@Override
	public <T extends Object> T findByHql(final String hql, final Map<String, ?> params) {
		return getHibernateTemplate().execute(new HibernateCallback<T>() {
			@Override
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				setParameters(query, params);
				return (T) query.uniqueResult();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * @see com.arvato.npass.common.dao.HibernateDao#mapByHql(java.lang.String)
	 */
	@Override
	public List<Map<String, ?>> mapByHql(final String hql) {
		return mapByHql(hql, null);
	}

	/*
	 * (non-Javadoc)
	 * @see com.arvato.npass.common.dao.HibernateDao#mapByHql(java.lang.String, java.util.Map)
	 */
	@Override
	public List<Map<String, ?>> mapByHql(final String hql, final Map<String, ?> params) {
		return mapByHql(hql, RowBounds.DEFAULT, params);
	}

	/*
	 * (non-Javadoc)
	 * @see com.arvato.npass.common.dao.HibernateDao#mapByHql(java.lang.String, com.arvato.npass.common.dao.wrapper.RowBounds, java.util.Map)
	 */
	@Override
	public List<Map<String, ?>> mapByHql(final String hql, final RowBounds rowBounds, final Map<String, ?> params) {
		return getHibernateTemplate().executeFind(new HibernateCallback<List<Map<String, ?>>>() {
			@Override
			public List<Map<String, ?>> doInHibernate(Session session) throws HibernateException, SQLException {
				if (!checkHasKeyWords(hql, HQL_MAP_KEYWORDS_REGEX)) 
					throw new HibernateException("Map key words not found in HQL[" + hql + "].");
				Query query = session.createQuery(hql);
				setParameters(query, params);
				setPagination(query, rowBounds);
				return query.list();
			}
		});
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.arvato.npass.common.dao.HibernateDao#listByHql(java.lang.String)
	 */
	@Override
	public <T extends Object> List<T> listByHql(final String hql) {
		return listByHql(hql, null);
	}

	/*
	 * (non-Javadoc)
	 * @see com.arvato.npass.common.dao.HibernateDao#listByHql(java.lang.String, java.util.Map)
	 */
	@Override
	public <T extends Object> List<T> listByHql(final String hql, final Map<String, ?> params) {
		return listByHql(hql, RowBounds.DEFAULT, params);
	}

	/*
	 * (non-Javadoc)
	 * @see com.arvato.npass.common.dao.HibernateDao#listByHql(java.lang.String, com.arvato.npass.common.dao.wrapper.RowBounds, java.util.Map)
	 */
	@Override
	public <T extends Object> List<T> listByHql(final String hql, final RowBounds rowBounds, final Map<String, ?> params) {
		return getHibernateTemplate().executeFind(new HibernateCallback<List<T>>() {
			@Override
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				setParameters(query, params);
				setPagination(query, rowBounds);
				return query.list();
			}
		});
	}
	
	
	/******************************************************************/
	/********          Hibernate Criteria Operation          **********/
	/******************************************************************/
	
	/*
	 * (non-Javadoc)
	 * @see com.arvato.npass.common.dao.HibernateDao#findByCriteria(java.lang.Class, org.hibernate.criterion.Criterion[])
	 */
	@Override
	public <T extends Object, E extends BaseEntity> T findByCriteria(final Class<E> persistentClass, 
			final Criterion... criterions) {
		return findByCriteria(persistentClass, null, criterions);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.arvato.npass.common.dao.HibernateDao#findByCriteria(java.lang.Class, org.hibernate.criterion.Projection, org.hibernate.criterion.Criterion[])
	 */
	@Override
	public <T extends Object, E extends BaseEntity> T findByCriteria(final Class<E> persistentClass, 
			final Projection projection, final Criterion... criterions) {
		return getHibernateTemplate().execute(new HibernateCallback<T>() {
			@Override
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(persistentClass);
				setCriterions(criteria, criterions);
				setProjection(criteria, projection);
				return (T) criteria.uniqueResult();
			}
		});
	}
	
	@Override
	public <T extends Object, E extends BaseEntity> T findByCriteria(final Class<E> persistentClass, final CriteriaWrapper cw) {
		return getHibernateTemplate().execute(new HibernateCallback<T>() {
			@Override
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(persistentClass);
				setCriterions(criteria, cw.getCriterions());
				setAlais(criteria, cw.getAliasWrappers());
				setFetchModes(criteria, cw.getFetchModeWrappers());
				setResultTransformer(criteria, cw.getResultTransformer());
				return (T) criteria.uniqueResult();
			}
		});
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.arvato.npass.common.dao.HibernateDao#countByCriteria(java.lang.Class, org.hibernate.criterion.Criterion[])
	 */
	@Override
	public <E extends BaseEntity> int countByCriteria(final Class<E> persistentClass, 
			final Criterion... criterions) {
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(persistentClass);
				setCriterions(criteria, criterions);
				setProjection(criteria, Projections.rowCount());
				return ((Long) criteria.uniqueResult()).intValue();
			}
		});
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.arvato.npass.common.dao.HibernateDao#countByCriteria(java.lang.Class, com.arvato.npass.common.dao.wrapper.CriteriaWrapper)
	 */
	@Override
	public <E extends BaseEntity> int countByCriteria(final Class<E> persistentClass, 
			final CriteriaWrapper cw) {
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(persistentClass);
				setCriterions(criteria, cw.getCriterions());
				setProjection(criteria, Projections.rowCount());
				setAlais(criteria, cw.getAliasWrappers());
				setFetchModes(criteria, cw.getFetchModeWrappers());
				setResultTransformer(criteria, cw.getResultTransformer());
				return ((Long) criteria.uniqueResult()).intValue();
			}
		});
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.arvato.npass.common.dao.HibernateDao#listByCriteria(java.lang.Class, org.hibernate.criterion.Criterion[])
	 */
	@Override
	public <T extends Object, E extends BaseEntity> List<T> listByCriteria(final Class<E> persistentClass, 
			final Criterion... criterions) {
		return listByCriteria(persistentClass, RowBounds.DEFAULT, criterions);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.arvato.npass.common.dao.HibernateDao#listByCriteria(java.lang.Class, com.arvato.npass.common.dao.wrapper.RowBounds, org.hibernate.criterion.Criterion[])
	 */
	@Override
	public <T extends Object, E extends BaseEntity> List<T> listByCriteria(final Class<E> persistentClass, 
			final RowBounds rowBounds, final Criterion... criterions) {
		return listByCriteria(persistentClass, rowBounds, null, criterions);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.arvato.npass.common.dao.HibernateDao#listByCriteria(java.lang.Class, com.arvato.npass.common.dao.wrapper.RowBounds, org.hibernate.criterion.Projection, org.hibernate.criterion.Criterion[])
	 */
	@Override
	public <T extends Object, E extends BaseEntity> List<T> listByCriteria(final Class<E> persistentClass, 
			final RowBounds rowBounds, Projection projection, final Criterion... criterions) {
		return listByCriteria(persistentClass, CollectionUtils.toList(criterions), 
				rowBounds, projection, null, null, null, null);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.arvato.npass.common.dao.HibernateDao#listByCriteria(java.lang.Class, com.arvato.npass.common.dao.wrapper.CriteriaWrapper)
	 */
	@Override
	public <T extends Object, E extends BaseEntity> List<T> listByCriteria(final Class<E> persistentClass, 
			CriteriaWrapper cw) {
		return listByCriteria(persistentClass, cw.getCriterions(),
				cw.getRowBounds(), cw.getProjection(), cw.getAliasWrappers(),
				cw.getFetchModeWrappers(), cw.getOrders(),
				cw.getResultTransformer());
	}

	/*
	 * (non-Javadoc)
	 * @see com.arvato.npass.common.dao.HibernateDao#listByCriteria(java.lang.Class, java.util.List, com.arvato.npass.common.dao.wrapper.RowBounds, org.hibernate.criterion.Projection, java.util.List, java.util.List, java.util.List, org.hibernate.transform.ResultTransformer)
	 */
	@Override
	public <T extends Object, E extends BaseEntity> List<T> listByCriteria(final Class<E> persistentClass, final List<Criterion> criterions, 
			final RowBounds rowBounds, final Projection projection, final List<AliasWrapper> aliasWrappers, 
			final List<FetchModeWrapper> fetchModeWrappers, final List<Order> orders,  final ResultTransformer resultTransformer) {
		return getHibernateTemplate().executeFind(new HibernateCallback<List<T>>() {
			@Override
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(persistentClass);
				// set criterion
				setCriterions(criteria, criterions);
				// set alias
				setAlais(criteria, aliasWrappers);
				// set order
				setOrders(criteria, orders);
				// set fetch mode
				setFetchModes(criteria, fetchModeWrappers);
				// set projection
				setProjection(criteria, projection);
				// set pagination
				setPagination(criteria, rowBounds);
				// set result transformer
				setResultTransformer(criteria, resultTransformer);
				return criteria.list();
			}
		});
	}
	
	/***********************      private methods      **************************/
	
	// Set result transformer
	private void setResultTransformer(Criteria criteria, ResultTransformer rt) {
		if (rt != null) criteria.setResultTransformer(rt);
	}
	
	// Set fetch mode to criteria
	private void setFetchModes(Criteria criteria, Collection<FetchModeWrapper> fetchModeWrappers) {
		if (CollectionUtils.isNotEmpty(fetchModeWrappers)) {
			for (FetchModeWrapper fw : fetchModeWrappers)
				criteria.setFetchMode(fw.getProperty(), fw.getFetchMode());
		}
	}
	
	// Set order to criteria
	private void setOrders(Criteria criteria, Collection<Order> orders) {
		if (CollectionUtils.isNotEmpty(orders)) {
			for (Order order : orders)
				criteria.addOrder(order);
		}
	}
	
	// Set alais to criteria
	private void setAlais(Criteria criteria, Collection<AliasWrapper> aliasWrappers) {
		if (CollectionUtils.isNotEmpty(aliasWrappers)) {
			for (AliasWrapper aw : aliasWrappers) {
				if (aw.getJoinType() == null)
					criteria.createAlias(aw.getProperty(), aw.getAlias());
				else
					criteria.createAlias(aw.getProperty(), aw.getAlias(), aw.getJoinType());
			}
		}
	}
	
	// Set criterion to criteria
	private void setCriterions(Criteria criteria, Criterion... criterions) {
		if (criterions != null) {
			for (Criterion criterion : criterions) {
				criteria.add(criterion);
			}
		}
	}
	
	// Set criterion to criteria
	private void setCriterions(Criteria criteria, Collection<Criterion> criterions) {
		if (CollectionUtils.isNotEmpty(criterions)) {
			for (Criterion criterion : criterions) {
				criteria.add(criterion);
			}
		}
	}
	
	// Set projection to criteria
	private void setProjection(Criteria criteria, Projection projection) {
		if (projection != null) criteria.setProjection(projection);
		
	}
	
	// Set parameter to query
	private void setParameters(Query query, Map<String, ?> params) {
		if (CollectionUtils.isNotEmpty(params)) {
			for (Entry<String, ?> entry : params.entrySet()) {
				Object value = entry.getValue();
				if (value != null) {
					if (value instanceof Object[])
						query.setParameterList(entry.getKey(), (Object[]) value);
					else if (value instanceof Collection<?>)
						query.setParameterList(entry.getKey(), (Collection<?>) value);
					else
						query.setParameter(entry.getKey(), value);
				}
			}
		}
	}
	
	// Set pagination to query
	private void setPagination(Query query, RowBounds rowBounds) {
		if (rowBounds != null && !rowBounds.equals(RowBounds.DEFAULT)) {
			query.setFirstResult(rowBounds.getFirstResult());
			query.setMaxResults(rowBounds.getMaxResults());
		}
	}
	
	// Set pagination to criteria
	private void setPagination(Criteria criteria, RowBounds rowBounds) {
		if (rowBounds != null && !rowBounds.equals(RowBounds.DEFAULT)) {
			criteria.setFirstResult(rowBounds.getFirstResult());
			criteria.setMaxResults(rowBounds.getMaxResults());
		}
	}
	
	// Check has key words in the hql query
	private boolean checkHasKeyWords(String hql, String regex) {
		String lowerHql = hql.toLowerCase();
		Pattern p = Pattern.compile(regex, 2);
		Matcher m = p.matcher(lowerHql);
		return m.find();
	}
	
	
}
