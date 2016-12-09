package com.chinadrtv.erp.core.dao.hibernate;

import com.chinadrtv.erp.core.dao.EntityViewDao;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.core.model.client.EntityView;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * User: liuhaidong
 * Date: 12-11-12
 */
@Repository
public class EntityViewDaoImpl extends GenericDaoHibernate<EntityView,Long> implements EntityViewDao {
    public EntityViewDaoImpl() {
        super(EntityView.class);
    }

    public EntityView findByName(String name) {
        ParameterString parameterString = new ParameterString("name",name);
        Map<String, Parameter> parameterMap = new HashMap<String, Parameter>();
        parameterMap.put(parameterString.getName(),parameterString);
        String hql = "from EntityView m where m.name like :name";
        EntityView entityView = this.find(hql,parameterMap);
        return entityView;
    }
}
