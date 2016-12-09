package com.chinadrtv.erp.core.service;

import com.chinadrtv.erp.core.model.client.EntityView;

/**
 * User: liuhaidong
 * Date: 12-11-12
 */
public interface EntityViewService extends GenericService<EntityView, Long> {
    EntityView findByName(String name);
}
