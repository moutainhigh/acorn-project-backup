package com.chinadrtv.erp.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-5-22
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class AmortisationPK implements Serializable {
    private String cardtypeid;

    @Id
    @Column(name = "CARDTYPEID", length = 10)
    public String getCardtypeid() {
        return cardtypeid;
    }

    public void setCardtypeid(String cardtypeid) {
        this.cardtypeid = cardtypeid;
    }

    private Integer amortisation;

    @Id
    @Column(name = "AMORTISATION", nullable = false, insertable = true, updatable = true, length = 2, precision = 0)
    public Integer getAmortisation() {
        return amortisation;
    }

    public void setAmortisation(Integer amortisation) {
        this.amortisation = amortisation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AmortisationPK that = (AmortisationPK) o;

        if (amortisation != null ? !amortisation.equals(that.amortisation) : that.amortisation != null) return false;
        if (cardtypeid != null ? !cardtypeid.equals(that.cardtypeid) : that.cardtypeid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cardtypeid != null ? cardtypeid.hashCode() : 0;
        result = 31 * result + (amortisation != null ? amortisation.hashCode() : 0);
        return result;
    }
}
