package com.chinadrtv.erp.model;

import com.chinadrtv.erp.model.trade.ShipmentSettlement;

import javax.persistence.*;
import java.util.Date;

/**
 * 结算拍平对应表
 * User: gaodejian
 * Date: 13-4-8
 * Time: 下午3:45
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "COMPANY_PAYMENT_SETTLE", schema = "ACOAPP_OMS")
public class CompanyPaymentSettle {

    private Long id;
    private ShipmentSettlement settlement;
    private CompanyPayment payment;
    private Double amount;
    private String createUser;
    private Date createDate;
    private String updateUser;
    private Date updateDate;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMPANY_PAYMENT_SETTLE_SEQ")
    @SequenceGenerator(name = "COMPANY_PAYMENT_SETTLE_SEQ", sequenceName = "ACOAPP_OMS.COMPANY_PAYMENT_SETTLE_SEQ")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SHIPMENT_SETTLEMENT_ID")
    public ShipmentSettlement getSettlement() {
        return settlement;
    }

    public void setSettlement(ShipmentSettlement settlement) {
        this.settlement = settlement;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_PAYMENT_ID")
    public CompanyPayment getPayment() {
        return payment;
    }

    public void setPayment(CompanyPayment payment) {
        this.payment = payment;
    }

    @Column(name = "AMOUNT")
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Column(name = "CREATE_USER")
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Column(name = "CREATE_DATE")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "UPDATE_USER")
    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    @Column(name = "UPDATE_DATE")
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}

