package com.chinadrtv.erp.core.dao.hibernate;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.dao.query.Criteria;
import com.chinadrtv.erp.core.dao.query.Page;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.exception.IllegalQueryParameterException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import java.io.Serializable;
import java.util.*;

/**
 * User: liuhaidong
 * Date: 12-12-12
 * Please extend from this class if need the @Qualifier("personA")
 */
public abstract class GenericDaoHibernateBase<T, PK extends Serializable> implements GenericDao<T, PK> {
    /**
     * Log variable for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
     */
    protected final Log log = LogFactory.getLog(getClass());
    protected Class<T> persistentClass;
    protected HibernateTemplate hibernateTemplate;
    protected SessionFactory sessionFactory;

    protected Session getSession(){
        return  SessionFactoryUtils.getSession(getSessionFactory(), true);
    }

    public HibernateTemplate getHibernateTemplate() {
        return this.hibernateTemplate;
    }

    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }


    protected void doSetSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    public abstract void setSessionFactory(SessionFactory sessionFactory);

    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing.
     *
     * @param persistentClass the class type you'd like to persist
     */
    public GenericDaoHibernateBase(final Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    /**
     * Constructor that takes in a class and sessionFactory for easy creation of DAO.
     *
     * @param persistentClass the class type you'd like to persist
     * @param sessionFactory  the pre-configured Hibernate SessionFactory
     */
    public GenericDaoHibernateBase(final Class<T> persistentClass, SessionFactory sessionFactory) {
        this.persistentClass = persistentClass;
        this.sessionFactory = sessionFactory;
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        return hibernateTemplate.loadAll(this.persistentClass);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> getAllDistinct() {
        Collection result = new LinkedHashSet(getAll());
        return new ArrayList(result);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public T get(PK id) {
        T entity = (T) hibernateTemplate.get(this.persistentClass, id);

        if (entity == null) {
            log.warn("Uh oh, '" + this.persistentClass + "' object with id '" + id + "' not found...");
            throw new ObjectRetrievalFailureException(this.persistentClass, id);
        }

        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public boolean exists(PK id) {
        T entity = (T) hibernateTemplate.get(this.persistentClass, id);
        return entity != null;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public T save(T object) {
        return (T) hibernateTemplate.merge(object);
    }

    /**
     * {@inheritDoc}
     */
    public void remove(PK id) {
        hibernateTemplate.delete(this.get(id));
    }

	public T merge(T entity) {
    	return hibernateTemplate.merge(entity);
	}

	/**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams) {
        String[] params = new String[queryParams.size()];
        Object[] values = new Object[queryParams.size()];

        int index = 0;
        for (String s : queryParams.keySet()) {
            params[index] = s;
            values[index++] = queryParams.get(s);
        }

        return hibernateTemplate.findByNamedQueryAndNamedParam(queryName, params, values);
    }

    public void saveOrUpdate(T object) {
        hibernateTemplate.saveOrUpdate(object);
    }

    private void setParamters(Map<String, Parameter> parameters, Query q) {
        for(String paraName : parameters.keySet()){
            Parameter p = parameters.get(paraName);
            if (!p.isForPageQuery()){
                parameters.get(paraName).setParameterValue(q);
            }
        }
    }

    private String setCriterias(String hql, Map<String, Parameter> parameters, Map<String, Criteria> criterias) {
        StringBuffer stringBuffer = new StringBuffer(hql);
        for(String paraName: parameters.keySet()){
            Parameter p = parameters.get(paraName);
            if (p.getValue()!=null && !p.isForPageQuery()){
                Criteria criteria = criterias.get(paraName);
                if (criteria != null){
                    stringBuffer.append(criteria.getRestriction());
                }
            }
        }
        hql = stringBuffer.toString();
        return hql;
    }

    private void setPageQuery(Map<String, Parameter> parameters,Query q){
        ParameterInteger startIndex = (ParameterInteger)parameters.get(Page.PARAM_PAGE);
        if (startIndex != null){
            q.setFirstResult(startIndex.getValue());
        } else {
            throw  new IllegalQueryParameterException("no startIndex parameter set for query: "+q.getQueryString());
        }
        ParameterInteger  pageSize = (ParameterInteger)parameters.get(Page.PARAM_PAGE_SIZE);
        if (pageSize != null){
            q.setMaxResults(pageSize.getValue());
        }  else {
            throw  new IllegalQueryParameterException("no pageSize parameter set for query: "+q.getQueryString());
        }
    }

    public int findPageCount(String hql, Map<String, Parameter> parameters) {
        Query q = getSession().createQuery(hql);
        setParamters(parameters, q);
        List list = q.list();
        return Integer.valueOf(list.get(0).toString());
    }

    public int findPageCount(String hql, Map<String, Parameter> parameters,Map<String,Criteria> criterias) {
        hql = setCriterias(hql, parameters, criterias);
        Query q = getSession().createQuery(hql);
        setParamters(parameters,q);
        List list = q.list();
        return Integer.valueOf(list.get(0).toString());
    }

    public List<T> findPageList(String hql, Map<String, Parameter> parameters) {
        Query q = getSession().createQuery(hql);
        setParamters(parameters, q);
        setPageQuery(parameters,q);
        List list = q.list();
        return list;
    }

    public List<T> findPageList(String hql, Map<String, Parameter> parameters,Map<String,Criteria> criterias) {
        hql = setCriterias(hql, parameters, criterias);
        Query q = getSession().createQuery(hql);
        setParamters(parameters, q);
        setPageQuery(parameters,q);
        List list = q.list();
        return list;
    }

    public List<T> findList(String hql, Map<String, Parameter> parameters) {
        Query q = getSession().createQuery(hql);
        setParamters(parameters, q);
        List list = q.list();
        return list;
    }

    public List<T> findList(String hql, Parameter ... parametersArray) {
        Map<String, Parameter> parameters = convertToParameterMap(parametersArray);
        return findList(hql,parameters);
    }

    private Map<String, Parameter> convertToParameterMap(Parameter[] parametersArray) {
        Map<String, Parameter> parameters = new HashMap<String, Parameter>();
        for (Parameter p : parametersArray){
            parameters.put(p.getName(),p);
        }
        return parameters;
    }

    public List<T> findList(String hql, Map<String, Parameter> parameters,Map<String,Criteria> criterias) {
        hql = setCriterias(hql, parameters, criterias);
        Query q = getSession().createQuery(hql);
        setParamters(parameters, q);
        List list = q.list();
        return list;
    }

    public T find(String hql, Map<String, Parameter> parameters) {
        List<T> list = findList(hql,parameters);
        return list.size()>0?list.get(0):null;
    }

    public T find(String hql, Parameter... parameters) {
        List<T> list = findList(hql,parameters);
        return list.size()>0?list.get(0):null;
    }

    public T find(String hql, Map<String, Parameter> parameters, Map<String, Criteria> criterias) {
        List<T> list = findList(hql, parameters, criterias);
        return list.size()>0?list.get(0):null;
    }

    public int execUpdate(String hql, Map<String, Parameter> parameters) {
        Query q = getSession().createQuery(hql);
        setParamters(parameters, q);
        return q.executeUpdate();
    }

    public int execUpdate(String hql, Parameter ... parametersArray) {
        Map<String, Parameter> parameters = convertToParameterMap(parametersArray);
        return execUpdate(hql, parameters);
    }

    public int execUpdate(String hql, Map<String, Parameter> parameters, Map<String, Criteria> criterias) {
        hql = setCriterias(hql, parameters, criterias);
        Query q = getSession().createQuery(hql);
        setParamters(parameters, q);
        return q.executeUpdate();
    }
}
