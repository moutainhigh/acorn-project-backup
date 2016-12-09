package com.chinadrtv.erp.core.dao.query;
import org.hibernate.Query;

import java.util.Date;

/**
 * User: zhaizy
 * Date: 12-11-9
 */
public class ParameterTimestamp extends AbstractParameter<Date> {
    public ParameterTimestamp(String name, Date value) {
        super(name, value);
    }

    public void setParameterValue(Query q) {
        q.setTimestamp(getName(),getValue());
    }
}
