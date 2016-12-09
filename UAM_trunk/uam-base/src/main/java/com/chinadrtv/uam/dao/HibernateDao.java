package com.chinadrtv.uam.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.transform.ResultTransformer;

import com.chinadrtv.uam.dao.wrapper.AliasWrapper;
import com.chinadrtv.uam.dao.wrapper.CriteriaWrapper;
import com.chinadrtv.uam.dao.wrapper.FetchModeWrapper;
import com.chinadrtv.uam.dao.wrapper.RowBounds;
import com.chinadrtv.uam.model.BaseEntity;

/**
 * 
 * @author Qianyong,Deng
 * @since Oct 2, 2012
 * 
 */
public interface HibernateDao {

	/******************************************************************/
	/********** Hibernate Base Operation ************/
	/******************************************************************/

	/**
	 * Return the persistent instance of the given entity class with the given
	 * identifier, or null if not found.
	 * 
	 * @param entityClass
	 *            a persistent class
	 * @param id
	 *            identifier
	 * @return the persistent instance, or null if not found
	 */
	<E extends BaseEntity> E get(final Class<E> entityClass,
			final Serializable id);

	/**
	 * Return the persistent instance of the given entity class with the given
	 * identifier, throwing an exception if not found.
	 * 
	 * @param entityClass
	 *            a persistent class
	 * @param id
	 *            identifier
	 * @return the persistent instance
	 */
	<E extends BaseEntity> E load(final Class<E> entityClass,
			final Serializable id);

	/**
	 * Return all persistent instances of the given entity class. Note: Use
	 * queries or criteria for retrieving a specific subset.
	 * 
	 * @param entityClass
	 *            a persistent class
	 * @return a List containing 0 or more persistent instances
	 */
	<E extends BaseEntity> List<E> getAll(final Class<E> entityClass);

	/**
	 * Persist the given transient instance.
	 * 
	 * @param e
	 *            the transient instance to persist
	 * @return the generated identifier
	 */
	<K extends Serializable, E extends BaseEntity> K save(final E e);

	/**
	 * Copy the state of the given object onto the persistent object with the
	 * same identifier. Follows JSR-220 semantics.
	 * 
	 * @param e
	 *            a persistent class
	 * @return the updated, registered persistent instance
	 */
	<E extends BaseEntity> E merge(final E e);

	/**
	 * Save or update the given persistent instance, according to its id
	 * (matching the configured "unsaved-value"?). Associates the instance with
	 * the current Hibernate Session.
	 * 
	 * @param e
	 *            the transient instance to persist or update
	 */
	<E extends BaseEntity> void saveOrUpdate(final E e);

	/**
	 * Update the given persistent instance, associating it with the current
	 * Hibernate Session.
	 * 
	 * @param e
	 *            the transient instance to update
	 */
	<E extends BaseEntity> void update(final E e);

	/**
	 * Delete the given persistent instance.
	 * 
	 * @param e
	 *            the transient instance to delete
	 */
	<E extends BaseEntity> void delete(final E e);

	/**
	 * Delete a persistent instance according to the given identifier.
	 * 
	 * @param entityClass
	 *            a persistent class
	 * @param id
	 *            identifier
	 */
	<K extends Serializable, E extends BaseEntity> void delete(
			final Class<E> entityClass, final K id);

	/**
	 * Remove the given object from cache.
	 * 
	 * @param entity
	 *            the persistent instance to evict
	 */
	<E extends BaseEntity> void evict(final E entity);

	/******************************************************************/
	/********** Hibernate SQL Operation ************/
	/******************************************************************/

	/**
	 * Execute update or delete according to the given sql.
	 * 
	 * @param sql
	 *            sql query
	 * @return the number of instances updated/deleted
	 */
	int executeBySql(final String sql);

	/**
	 * Execute update or delete according to the given sql, binding a number of
	 * values to "?" parameters in the query string.
	 * 
	 * @param sql
	 *            sql query
	 * @param param
	 *            a number of query parameter
	 * @return the number of instances updated/deleted
	 */
	int executeBySql(final String sql, final Object... params);

	/**
	 * Execute query according to the sql, the results are returned in an
	 * instance of Object[].
	 * 
	 * @param sql
	 *            sql query
	 * @return array of columns
	 */
	Object[] findBySql(final String sql);

	/**
	 * Execute query according to the sql, binding a number of values to "?"
	 * parameters in the query string, the results are returned in an instance
	 * of Object[].
	 * 
	 * @param sql
	 *            sql query
	 * @param param
	 *            a number of query parameter
	 * @return array of columns
	 */
	Object[] findBySql(final String sql, final Object... params);

	/**
	 * Execute query according to the sql, return result as a List, the results
	 * are returned in an instance of Object[].
	 * 
	 * @param sql
	 *            sql query
	 * @return the result list
	 */
	List<Object[]> listBySql(final String sql);

	/**
	 * Execute query according to the sql, return result as a List, binding a
	 * number of values to "?" parameters in the query string, the results are
	 * returned in an instance of Object[].
	 * 
	 * @param sql
	 *            sql query
	 * @param param
	 *            a number of query parameter
	 * @return the result list
	 */
	List<Object[]> listBySql(final String sql, final Object... params);

	/**
	 * Execute query according to the sql, return result as a List, binding a
	 * number of values to "?" parameters in the query string, the results are
	 * returned in an instance of Object[]. RowBounds should be used for
	 * pagination.
	 * 
	 * @param sql
	 *            sql query
	 * @param rowBounds
	 *            bounds of the row
	 * @param param
	 *            a number of query parameter
	 * @return the result list
	 */
	List<Object[]> listBySql(final String sql, final RowBounds rowBounds,
			final Object... params);

	/******************************************************************/
	/********** Hibernate HQL Operation ************/
	/******************************************************************/

	/**
	 * Execute update or delete according to the given hql.
	 * 
	 * @param hql
	 *            hql query
	 * @return the number of instances updated/deleted
	 */
	int executeByHql(final String hql);

	/**
	 * Execute update or delete according to the given hql, binding a number of
	 * values to ":" placeholder in the query string.
	 * 
	 * @param hql
	 *            hql query
	 * @param params
	 *            a number of query parameter
	 * @return the number of instances updated/deleted
	 */
	int executeByHql(final String hql, final Map<String, ?> params);

	/**
	 * Execute query count according to the hql, return result as an Integer.
	 * 
	 * @param hql
	 *            hql query
	 * @return count of persistent instance
	 */
	int countByHql(final String hql);

	/**
	 * Execute query count according to the hql, return result as an Integer,
	 * binding a number of values to ":" placeholder in the query string.
	 * 
	 * @param hql
	 *            hql query
	 * @param params
	 *            a number of query parameter
	 * @return count of persistent instance
	 */
	int countByHql(final String hql, final Map<String, ?> params);

	/**
	 * Execute query according to the hql, return result as an Object.
	 * 
	 * @param hql
	 *            hql query
	 * @return persistent instance
	 */
	<T extends Object> T findByHql(final String hql);

	/**
	 * Execute query according to the hql, return result as an Object, binding a
	 * number of values to ":" placeholder in the query string.
	 * 
	 * @param hql
	 *            hql query
	 * @param params
	 *            a number of query parameter
	 * @return persistent instance
	 */
	<T extends Object> T findByHql(final String hql, final Map<String, ?> params);

	/**
	 * Execute query according to the hql, return result as a List. All columns
	 * in {@link Map}, key is column name and value is column value.
	 * 
	 * @param hql
	 *            hql query
	 * @return the result list
	 */
	List<Map<String, ?>> mapByHql(final String hql);

	/**
	 * Execute query according to the hql, return result as a List, binding a
	 * number of values to ":" placeholder in the query string. All columns in
	 * {@link Map}, key is column name and value is column value.
	 * 
	 * @param hql
	 *            hql query
	 * @param params
	 *            a number of query parameter
	 * @return the result list
	 */
	List<Map<String, ?>> mapByHql(final String hql, final Map<String, ?> params);

	/**
	 * Execute query according to the hql, return result as a List, binding a
	 * number of values to ":" placeholder in the query string. All columns in
	 * {@link Map}, key is column name and value is column value. RowBounds
	 * should be used for pagination.
	 * 
	 * @param hql
	 *            hql query
	 * @param rowBounds
	 *            bounds of the row
	 * @param param
	 *            a number of query parameter
	 * @return the result list
	 */
	List<Map<String, ?>> mapByHql(final String hql, final RowBounds rowBounds,
			final Map<String, ?> params);

	/**
	 * Execute query according to the hql, return result as a List.
	 * 
	 * @param hql
	 *            hql query
	 * @return the result list
	 */
	<T extends Object> List<T> listByHql(final String hql);

	/**
	 * Execute query according to the hql, return result as a List, binding a
	 * number of values to ":" placeholder in the query string.
	 * 
	 * @param hql
	 *            hql query
	 * @param params
	 *            a number of query parameter
	 * @return the result list
	 */
	<T extends Object> List<T> listByHql(final String hql,
			final Map<String, ?> params);

	/**
	 * Execute query according to the hql, return result as a List, binding a
	 * number of values to ":" placeholder in the query string. RowBounds should
	 * be used for pagination.
	 * 
	 * @param hql
	 *            hql query
	 * @param rowBounds
	 *            bounds of the row
	 * @param param
	 *            a number of query parameter
	 * @return the result list
	 */
	<T extends Object> List<T> listByHql(final String hql,
			final RowBounds rowBounds, final Map<String, ?> params);

	/******************************************************************/
	/******** Hibernate Criteria Operation **********/
	/******************************************************************/

	/**
	 * Execute query according to the criteria, return result as a type E.
	 * 
	 * @param persistentClass
	 *            a persistent class
	 * @param criterions
	 *            array of criterion for criteria
	 * @return persistent instance
	 */
	<T extends Object, E extends BaseEntity> T findByCriteria(
			final Class<E> persistentClass, final Criterion... criterions);

	/**
	 * Execute query according to the criteria, return result as a type E.
	 * 
	 * @param persistentClass
	 *            a persistent class
	 * @param projection
	 *            projection for criteria
	 * @param criterions
	 *            array of criterion for criteria
	 * @return persistent instance
	 */
	<T extends Object, E extends BaseEntity> T findByCriteria(
			final Class<E> persistentClass, final Projection projection,
			final Criterion... criterions);

	/**
	 * Execute query according to the criteria, return result as a type E.
	 * 
	 * @param persistentClass
	 *            a persistent class
	 * @param criteriaWrapper
	 *            {@link CriteriaWrapper}
	 * @return persistent instance
	 */
	<T extends Object, E extends BaseEntity> T findByCriteria(
			final Class<E> persistentClass, final CriteriaWrapper cw);

	/**
	 * Execute query count according to the criteria, return result as an
	 * Integer.
	 * 
	 * @param persistentClass
	 *            a persistent class
	 * @param criterions
	 *            array of criterion for criteria
	 * @return count of persistent instance
	 */
	<E extends BaseEntity> int countByCriteria(final Class<E> persistentClass,
			final Criterion... criterions);

	/**
	 * Execute query count according to the {@link CriteriaWrapper}, return
	 * result as an Integer. Just use {@link CriteriaWrapper#getCriterions()},
	 * {@link CriteriaWrapper#getAliasWrappers()},
	 * {@link CriteriaWrapper#getFetchModeWrappers()} and
	 * {@link CriteriaWrapper#getResultTransformer()}
	 * 
	 * @param persistentClass
	 *            a persistent class
	 * @param criteriaWrapper
	 *            {@link CriteriaWrapper}
	 * @return count of persistent instance
	 */
	<E extends BaseEntity> int countByCriteria(Class<E> persistentClass,
			CriteriaWrapper criteriaWrapper);

	/**
	 * Execute query according to the criteria, return result as a List.
	 * 
	 * @param persistentClass
	 *            a persistent class
	 * @param criterions
	 *            array of criterion for criteria
	 * @return the result list
	 */
	<T extends Object, E extends BaseEntity> List<T> listByCriteria(
			Class<E> persistentClass, Criterion... criterions);

	/**
	 * Execute query according to the criteria, return result as a List.
	 * RowBounds should be used for pagination.
	 * 
	 * @param persistentClass
	 *            a persistent class
	 * @param rowBounds
	 *            bounds of the row
	 * @param criterions
	 *            array of criterion for criteria
	 * @return the result list
	 */
	<T extends Object, E extends BaseEntity> List<T> listByCriteria(
			Class<E> persistentClass, RowBounds rowBounds,
			Criterion... criterions);

	/**
	 * Execute query according to the criteria, return result as a List.
	 * RowBounds should be used for pagination.
	 * 
	 * @param persistentClass
	 *            a persistent class
	 * @param rowBounds
	 *            bounds of the row
	 * @param projection
	 *            projection for criteria
	 * @param criterions
	 *            array of criterion for criteria
	 * @return the result list
	 */
	<T extends Object, E extends BaseEntity> List<T> listByCriteria(
			Class<E> persistentClass, RowBounds rowBounds,
			Projection projection, Criterion... criterions);

	/**
	 * Execute query count according to the {@link CriteriaWrapper}, return
	 * result as a List.
	 * 
	 * @param persistentClass
	 *            a persistent class
	 * @param criteriaWrapper
	 *            {@link CriteriaWrapper}
	 * @return the result list
	 */
	<T extends Object, E extends BaseEntity> List<T> listByCriteria(
			Class<E> persistentClass, CriteriaWrapper criteriaWrapper);

	/**
	 * Execute query count according to the criteria, return result as a List.
	 * 
	 * @param persistentClass
	 *            a persistent class
	 * @param criterions
	 *            list of criterion for criteria
	 * @param rowBounds
	 *            bounds of the row
	 * @param projection
	 *            projection for criteria
	 * @param aliasWrappers
	 *            list of alias for criteria, see {@link AliasWrapper}
	 * @param fetchModeWrappers
	 *            list of fetch mode for criteria, see {@link FetchModeWrapper}
	 * @param orders
	 *            list of order for criteria
	 * @param resultTransformer
	 *            result transformer for criteria
	 * @return the result list
	 */
	<T extends Object, E extends BaseEntity> List<T> listByCriteria(
			Class<E> persistentClass, List<Criterion> criterions,
			RowBounds rowBounds, Projection projection,
			List<AliasWrapper> aliasWrappers,
			List<FetchModeWrapper> fetchModeWrappers, List<Order> orders,
			ResultTransformer resultTransformer);

}
