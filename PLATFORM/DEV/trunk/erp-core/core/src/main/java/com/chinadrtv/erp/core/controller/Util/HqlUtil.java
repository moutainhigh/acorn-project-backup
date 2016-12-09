package com.chinadrtv.erp.core.controller.Util;

import com.chinadrtv.erp.core.model.client.EntityView;

/**
 * User: liuhaidong
 * Date: 12-11-13
 */
public class HqlUtil {
    public static String getCountHqlStub(EntityView entityView){
        String hql =  "select count(a.id) from " + entityView.getEntityName() + " a  where 1=1 ";
        return hql;
    }

    public static String getPageHqlStub(EntityView entityView){
        String hql = "from " + entityView.getEntityName() + " a  where 1=1 ";
        return hql;
    }
}
