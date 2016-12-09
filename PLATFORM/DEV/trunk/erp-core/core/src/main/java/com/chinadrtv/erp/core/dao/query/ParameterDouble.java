package com.chinadrtv.erp.core.dao.query;

import org.hibernate.Query;

/**
 * User: liuhaidong
 * Date: 12-11-9
 */
public class ParameterDouble extends AbstractParameter<Double> {
    public ParameterDouble(String name, Double value) {
        super(name, value);
    }

    public void setParameterValue(Query q) {
        q.setDouble(getName(),getValue());
    }
}
