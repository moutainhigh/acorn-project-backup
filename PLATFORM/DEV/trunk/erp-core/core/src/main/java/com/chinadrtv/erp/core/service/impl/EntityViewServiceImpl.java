package com.chinadrtv.erp.core.service.impl;

import com.chinadrtv.erp.core.dao.EntityViewDao;
import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.model.client.EntityView;
import com.chinadrtv.erp.core.service.EntityViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: liuhaidong
 * Date: 12-11-12
 */
@Service
public class EntityViewServiceImpl extends GenericServiceImpl<EntityView,Long> implements EntityViewService {

    @Autowired
    private EntityViewDao entityViewDao;

    @Override
    protected GenericDao<EntityView, Long> getGenericDao() {
        return entityViewDao;
    }

    public EntityView findByName(String name) {
        return entityViewDao.findByName(name);
    }
}
