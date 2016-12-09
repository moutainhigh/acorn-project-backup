package com.chinadrtv.erp.core.dao.query;

import org.hibernate.Query;

/**
 * User: liuhaidong
 * Date: 12-11-20
 */
public interface Parameter<T> {
    void setName(String name);

    T getValue();

    void setValue(T value);

    String getName();

    void setParameterValue(Query q);

    void setForPageQuery(boolean b);

    boolean isForPageQuery();
}
