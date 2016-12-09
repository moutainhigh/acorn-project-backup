package com.chinadrtv.erp.core.dao.query;

import org.hibernate.Query;

/**
 * User: liuhaidong
 * Date: 12-11-9
 */
public class ParameterInteger extends AbstractParameter<Integer> {

    public ParameterInteger(String name, Integer value) {
        super(name, value);
    }

    public void setParameterValue(Query q) {
        q.setInteger(getName(),this.getValue());
    }
}
