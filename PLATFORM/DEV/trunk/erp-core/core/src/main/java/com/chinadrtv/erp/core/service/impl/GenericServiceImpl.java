package com.chinadrtv.erp.core.service.impl;

import com.chinadrtv.erp.core.controller.Util.HqlUtil;
import com.chinadrtv.erp.core.dao.query.Criteria;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.model.client.EntityView;
import com.chinadrtv.erp.core.service.GenericService;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * User: liuhaidong
 * Date: 12-11-9
 */
public abstract class GenericServiceImpl<T, PK extends Serializable> implements GenericService<T, PK> {

    protected abstract GenericDao<T, PK> getGenericDao();

    public int findPageCount(EntityView entityView, Map<String, Parameter> parameters,Map<String,Criteria> criterias) {
        String hql = HqlUtil.getCountHqlStub(entityView);
        return getGenericDao().findPageCount(hql,parameters,criterias);
    }

    public List<T> findPageList(EntityView entityView,Map<String, Parameter> parameters,Map<String,Criteria> criterias) {
        String hql = HqlUtil.getPageHqlStub(entityView);
        return getGenericDao().findPageList(hql,parameters,criterias);
    }

    public void save(T model) {
        getGenericDao().save(model);
    }

    public void saveOrUpdate(T model) {
        getGenericDao().saveOrUpdate(model);
    }

    public void delete(PK id) {
        getGenericDao().remove(id);
    }
}
