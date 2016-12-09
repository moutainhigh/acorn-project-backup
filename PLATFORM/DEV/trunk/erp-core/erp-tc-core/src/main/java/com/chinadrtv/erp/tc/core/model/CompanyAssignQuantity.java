package com.chinadrtv.erp.tc.core.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-4-24
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class CompanyAssignQuantity  implements java.io.Serializable,Cloneable{
    private Long companyId;
    private Date mdDate;
    private Long quantity;


    public CompanyAssignQuantity()
    {
        totalPrice=BigDecimal.ZERO;
        quantity=0L;
        mdDate=new Date();
    }
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    private BigDecimal totalPrice;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Date getMdDate() {
        return mdDate;
    }

    public void setMdDate(Date mdDate) {
        this.mdDate = mdDate;
    }


    public CompanyAssignQuantity copy(){
        CompanyAssignQuantity companyAssignQuantity=new CompanyAssignQuantity();
        companyAssignQuantity.setTotalPrice(this.getTotalPrice());
        companyAssignQuantity.setMdDate(this.getMdDate());
        companyAssignQuantity.setCompanyId(this.getCompanyId());
        companyAssignQuantity.setQuantity(this.getQuantity());
        return companyAssignQuantity;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }



}
