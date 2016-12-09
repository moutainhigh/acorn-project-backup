package com.chinadrtv.erp.core.dao;

import com.chinadrtv.erp.core.model.client.EntityView;

/**
 * User: liuhaidong
 * Date: 12-11-12
 */
public interface EntityViewDao extends GenericDao<EntityView,Long> {
    EntityView findByName(String name);
}
