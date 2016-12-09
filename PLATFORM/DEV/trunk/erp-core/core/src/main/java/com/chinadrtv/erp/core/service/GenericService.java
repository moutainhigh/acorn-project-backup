package com.chinadrtv.erp.core.service;

import com.chinadrtv.erp.core.dao.query.Criteria;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.model.client.EntityView;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * User: liuhaidong
 * Date: 12-11-9
 */
public interface GenericService<T, PK extends Serializable> {

    int findPageCount(EntityView entityView,Map<String,Parameter> parameters,Map<String,Criteria> criterias);

    List<T> findPageList(EntityView entityView,Map<String,Parameter> parameters,Map<String,Criteria> criterias);

    void save(T model);

    void saveOrUpdate(T model);

    void delete(PK id);
}
