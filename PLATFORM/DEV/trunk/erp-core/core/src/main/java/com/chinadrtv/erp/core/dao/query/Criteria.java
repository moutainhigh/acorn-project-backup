package com.chinadrtv.erp.core.dao.query;

/**
 * User: liuhaidong
 * Date: 12-11-9
 */
public class Criteria {

    public Criteria(String restriction, String parameterName) {
        this.restriction = restriction;
        this.parameterName = parameterName;
    }

    private String restriction;


    private String parameterName;

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getRestriction() {
        return restriction;
    }

    public void setRestriction(String restriction) {
        this.restriction = restriction;
    }


}
