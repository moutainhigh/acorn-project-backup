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
@IdClass(AmortisationPK.class)
@Entity
@Table(name = "AMORTISATION", schema = "IAGENT")
public class Amortisation implements Serializable, Comparable {
    private String cardtypeid;
    private Integer amortisation;
    private Integer lprice;
    private Integer uprice;
    private String valid;

    @Id
    @Column(name = "CARDTYPEID", length = 10)
    public String getCardtypeid() {
        return cardtypeid;
    }

    public void setCardtypeid(String cardtypeid) {
        this.cardtypeid = cardtypeid;
    }

    @Id
    @Column(name = "AMORTISATION")
    public Integer getAmortisation() {
        return amortisation;
    }

    public void setAmortisation(Integer amortisation) {
        this.amortisation = amortisation;
    }

    @Column(name = "LPRICE")
    public Integer getLprice() {
        return lprice;
    }

    public void setLprice(Integer lprice) {
        this.lprice = lprice;
    }

    @Column(name = "UPRICE")
    public Integer getUprice() {
        return uprice;
    }

    public void setUprice(Integer uprice) {
        this.uprice = uprice;
    }

    @Column(name = "VALID", length = 2)
    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public int compareTo(Object o) {
        if(o instanceof Amortisation)
        {
            Amortisation other=(Amortisation)o;
            if(this.amortisation!=null)
            {
                if(other.amortisation==null)
                    return 1;
                else
                    return this.amortisation.compareTo(other.amortisation);
            }
        }

        return -1;
    }
}

