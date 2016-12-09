package com.chinadrtv.erp.core.dao.query;
import org.hibernate.Query;

import java.util.Date;

/**
 * User: liuhaidong
 * Date: 12-11-9
 */
public class ParameterDate extends AbstractParameter<Date> {
    public ParameterDate(String name, Date value) {
        super(name, value);
    }

    public void setParameterValue(Query q) {
        q.setDate(getName(),getValue());
    }
}
