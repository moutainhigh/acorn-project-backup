package com.chinadrtv.uam.service;

import java.io.Serializable;
import java.util.List;

import com.chinadrtv.uam.model.BaseEntity;

public interface ServiceSupport {

	/**
	 * Return the persistent instance of the given entity class with the given identifier, or null if not found.
	 * 
	 * @param entityClass
	 *            a persistent class
	 * @param id
	 *            identifier
	 * @return the persistent instance, or null if not found
	 */
	<E extends BaseEntity> E get(final Class<E> entityClass, final Serializable id);
	
	/**
	 * Return the persistent instance of the given entity class with the given identifier, throwing an exception if not found. 
	 * 
	 * @param entityClass
	 *            a persistent class
	 * @param id
	 *            identifier
	 * @return the persistent instance
	 */
	<E extends BaseEntity> E load(final Class<E> entityClass, final Serializable id);
	
	/**
	 * Return all persistent instances of the given entity class. Note: Use queries or criteria for retrieving a specific subset. 
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
	 * Copy the state of the given object onto the persistent object with the same identifier. Follows JSR-220 semantics.
	 * 
	 * @param e
	 *            a persistent class
	 * @return the updated, registered persistent instance
	 */
	<E extends BaseEntity> E merge(final E e);
	
	/**
	 * Save or update the given persistent instance, according to its id (matching the configured "unsaved-value"?). 
	 * Associates the instance with the current Hibernate Session.
	 * 
	 * @param e
	 *            the transient instance to persist or update
	 */
	<E extends BaseEntity> void saveOrUpdate(final E e);
	
	/**
	 * Update the given persistent instance, associating it with the current Hibernate Session.
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
	<K extends Serializable, E extends BaseEntity> void delete(final Class<E> entityClass, final K id);

	/**
	 * Remove the given object from cache.
	 * 
	 * @param e
	 *            the persistent instance to evict
	 */
	<E extends BaseEntity> void evict(final E e);
	
}
