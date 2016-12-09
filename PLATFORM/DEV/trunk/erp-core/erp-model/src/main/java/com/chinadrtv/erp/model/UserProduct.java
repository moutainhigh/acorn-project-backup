package com.chinadrtv.erp.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 坐席关注产品
 * User: gaodejian
 * Date: 13-7-8
 * Time: 上午10:42
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "USER_PRODUCT", schema = "IAGENT")
public class UserProduct implements Serializable {
    private Long id;
    private String userId;
    private String productId;
    private String productType;
    private Date createDate;
    private String createUser;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_PRODUCT_SEQ")
    @SequenceGenerator(name = "USER_PRODUCT_SEQ", sequenceName = "IAGENT.USER_PRODUCT_SEQ", allocationSize = 1)
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "USER_ID")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "PRODUCT_ID")
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Column(name = "PRODUCT_TYPE")
    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    @Column(name = "CREATE_DATE")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "CREATE_USER")
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
}
