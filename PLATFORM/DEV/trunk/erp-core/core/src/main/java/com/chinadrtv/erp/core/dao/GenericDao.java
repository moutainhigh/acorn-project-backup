/*
 * Copyright (c) 2012 Acorn International.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http:www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or  implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.chinadrtv.erp.core.dao;

import com.chinadrtv.erp.core.dao.query.Criteria;
import com.chinadrtv.erp.core.dao.query.Parameter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * Generic DAO (Data Access Object) with common methods to CRUD POJOs.
 *
 * <p>Extend this interface if you want typesafe (no casting necessary) DAO's for your
 * domain objects.
 *
 * @param <T> a type variable
 * @param <PK> the primary key for that type
 */
public interface GenericDao<T, PK extends Serializable> {

    /**
     * Generic method used to get all objects of a particular type. This
     * is the same as lookup up all rows in a table.
     * @return List of populated objects
     */
    List<T> getAll();

    /**
     * Gets all records without duplicates.
     * <p>Note that if you use this method, it is imperative that your model
     * classes correctly implement the hashcode/equals methods</p>
     * @return List of populated objects
     */
    List<T> getAllDistinct();

    /**
     * Generic method to get an object based on class and identifier. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if
     * nothing is found.
     *
     * @param id the identifier (primary key) of the object to get
     * @return a populated object
     * @see org.springframework.orm.ObjectRetrievalFailureException
     */
    T get(PK id);

    /**
     * Checks for existence of an object of type T using the id arg.
     * @param id the id of the entity
     * @return - true if it exists, false if it doesn't
     */
    boolean exists(PK id);

    /**
     * Generic method to save an object - handles both update and insert.
     * @param object the object to save
     * @return the persisted object
     */
    T save(T object);

    /**
     * Generic method to delete an object based on class and id
     * @param id the identifier (primary key) of the object to remove
     */
    void remove(PK id);
    
    /**
     * Copy the state of the given object onto the persistent object with the same identifier. 
     * Follows JSR-220 semantics. 
	 * Similar to saveOrUpdate, but never associates the given object with the current Hibernate Session. 
	 * n case of a new entity, the state will be copied over as well. 
	 * Note that merge will not update the identifiers in the passed-in object graph (in contrast to TopLink)! 
	 * Consider registering Spring's IdTransferringMergeEventListener 
	 * if you would like to have newly assigned ids transferred to the original object graph too.
	 * 
     * @param entity
     * @return
     */
    T merge(T entity);

    /**
     * Find a list of records by using a named query
     * @param queryName query name of the named query
     * @param queryParams a map of the query names and the values
     * @return a list of the records found
     */
    List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams);

    void saveOrUpdate(T object);

    int findPageCount(String hql, Map<String,Parameter> parameters);

    int findPageCount(String hql, Map<String, Parameter> parameters,Map<String,Criteria> criterias);

    List<T> findPageList(String hql, Map<String,Parameter> parameters);

    List<T> findPageList(String hql, Map<String, Parameter> parameters,Map<String,Criteria> criterias);

    List<T> findList(String hql, Map<String, Parameter> parameters);

    List<T> findList(String hql, Map<String, Parameter> parameters,Map<String,Criteria> criterias);

    T find(String hql, Map<String, Parameter> parameters);

    T find(String hql, Map<String, Parameter> parameters,Map<String,Criteria> criterias);

    int execUpdate(String hql, Map<String,Parameter> parameters);

    int execUpdate(String hql, Map<String, Parameter> parameters,Map<String,Criteria> criterias);

    T find(String hql, Parameter ... parameters);

    List<T> findList(String hql, Parameter ... parametersArray);

    int execUpdate(String hql, Parameter ... parametersArray);

}