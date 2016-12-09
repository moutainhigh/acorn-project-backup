/*
 * Copyright (c) 2012 Acorn International.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http:www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or  implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.chinadrtv.erp.model;

/**
 * Match product.
 */

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author richard.mao
 */
@Entity
@DiscriminatorValue("MatchProduct")
public class PromotionRuleResult_MatchProduct extends PromotionRuleResult implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1798678801822597146L;

    private String productId;

    private String skuCode;

    private String productName;

    private Integer unitNumber;

    private String optionProducts;
    
    private Double discount;
    
    private Boolean isPer;

    public PromotionRuleResult_MatchProduct() {

    }

    public PromotionRuleResult_MatchProduct(String resultDescription, String productId, String skuCode,
                                           String productName, int unitNumber) {
        super(resultDescription);
        this.productId = productId;
        this.skuCode = skuCode;
        this.productName = productName;
        this.unitNumber = unitNumber;
    }
    

    @Column(name = "PRODUCT_ID")
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Column(name = "PRODUCT_NAME", length = 255)
    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Column(name = "UNIT_NUMBER")
    public Integer getUnitNumber() {
        return this.unitNumber;
    }

    public void setUnitNumber(Integer unitNumber) {
        this.unitNumber = unitNumber;
    }

    public String toString() {
        StringBuffer buff = new StringBuffer();
        buff.append(super.toString());
        buff.append(",productname:").append(productName);
        buff.append(",unitNumber:").append(unitNumber);
        return buff.toString();
    }

    @Column(name = "SKU_CODE", length = 255)
    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    @Column(name = "OPTION_PRODUCTS", length = 2000)
    public String getOptionProducts() {
        return optionProducts;
    }

    public void setOptionProducts(String optionProducts) {
        this.optionProducts = optionProducts;
    }

    @Column(name = "DISCOUNT")
	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	@Column(name = "ISPER")
	public Boolean getIsPer() {
		return isPer;
	}

	public void setIsPer(Boolean isPer) {
		this.isPer = isPer;
	}
    
    
}
