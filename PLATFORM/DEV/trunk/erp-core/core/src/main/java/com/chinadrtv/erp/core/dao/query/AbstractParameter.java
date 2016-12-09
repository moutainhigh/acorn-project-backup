package com.chinadrtv.erp.core.dao.query;

import org.hibernate.Query;

/**
 * User: liuhaidong
 * Date: 12-11-9
 */
public abstract class AbstractParameter<T> implements Parameter<T> {

    private boolean isForPageQuery;

    private String name;

    private T value;

    public void setName(String name) {
        this.name = name;
    }

    protected AbstractParameter(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getName(){
        return name;
    }

    public boolean isForPageQuery(){
        return isForPageQuery;
    }

    public void setForPageQuery(boolean b){
        this.isForPageQuery = b;
    }

}
