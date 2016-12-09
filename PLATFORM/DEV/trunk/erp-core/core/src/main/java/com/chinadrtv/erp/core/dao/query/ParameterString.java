package com.chinadrtv.erp.core.dao.query;

import org.hibernate.Query;

/**
 * User: liuhaidong
 * Date: 12-11-9
 */
public class ParameterString extends AbstractParameter<String> {

    private boolean isLike;

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }


    public ParameterString(String name, String value) {
        super(name, value);
    }

    public void setParameterValue(Query q) {
        if (isLike)  {
            q.setString(getName(),"%"+getValue()+"%");
        }else {
            q.setString(getName(),getValue());
        }
    }
}
