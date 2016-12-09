package com.chinadrtv.erp.model.service;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CusnoteId implements Serializable {
    private String orderid;
    private Integer featstr;

    public CusnoteId() {
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public Integer getFeatstr() {
        return featstr;
    }

    public void setFeatstr(Integer featstr) {
        this.featstr = featstr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CusnoteId cusnoteId = (CusnoteId) o;

        if (featstr != null ? !featstr.equals(cusnoteId.featstr) : cusnoteId.featstr != null) return false;
        if (orderid != null ? !orderid.equals(cusnoteId.orderid) : cusnoteId.orderid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderid != null ? orderid.hashCode() : 0;
        result = 31 * result + (featstr != null ? featstr.hashCode() : 0);
        return result;
    }
}
