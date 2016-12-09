package com.chinadrtv.erp.model.inventory;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-4-3
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Embeddable
public class ProductSuiteTypeId implements java.io.Serializable {
    private String prodSuiteScmId;
    private String prodScmId;

    public ProductSuiteTypeId()
    {

    }

    public ProductSuiteTypeId(String prodSuiteScmId, String prodScmId) {
        this.prodSuiteScmId = prodSuiteScmId;
        this.prodScmId = prodScmId;
    }

    @Column(name = "PRODSCMID")
    public String getProdSuiteScmId() {
        return prodSuiteScmId;
    }

    public void setProdSuiteScmId(String prodSuiteScmId) {
        this.prodSuiteScmId = prodSuiteScmId;
    }

    @Column(name = "PRODSUITESCMID")
    public String getProdScmId() {
        return prodScmId;
    }

    public void setProdScmId(String prodScmId) {
        this.prodScmId = prodScmId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductSuiteTypeId)) return false;

        ProductSuiteTypeId that = (ProductSuiteTypeId) o;

        if (prodScmId != null ? !prodScmId.equals(that.prodScmId) : that.prodScmId != null) return false;
        if (prodSuiteScmId != null ? !prodSuiteScmId.equals(that.prodSuiteScmId) : that.prodSuiteScmId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = prodSuiteScmId != null ? prodSuiteScmId.hashCode() : 0;
        result = 31 * result + (prodScmId != null ? prodScmId.hashCode() : 0);
        return result;
    }
}
