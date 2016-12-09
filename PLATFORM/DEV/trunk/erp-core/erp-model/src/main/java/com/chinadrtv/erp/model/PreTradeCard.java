package com.chinadrtv.erp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: guoguo
 * Date: 13-6-13
 * Time: 下午2:49
 * To change this template use File | Settings | File Templates.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "PRE_TRADE_CARD", schema = "ACOAPP_OMS")
public class PreTradeCard implements java.io.Serializable{
    private Long id ;
    private PreTrade preTrade;

    private String tradeId ;
    private String bankCode ;
    private String authCode ;
    private String idCardNumber ;
    private String creditCardNumber ;
    private String creditCardExpire ;
    private Integer creditCardCycles;
    private String createUser ;
    private Date createDate ;
    private String updateUser ;
    private Date updateDate ;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRE_TRADE_CARD_SEQ")
    @SequenceGenerator(name = "PRE_TRADE_CARD_SEQ", sequenceName = "ACOAPP_OMS.PRE_TRADE_CARD_SEQ")
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "TRADE_ID")
    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    @Column(name = "BANK_CODE")
    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    @Column(name = "AUTH_CODE")
    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    @Column(name = "ID_CARD_NUMBER")
    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    @Column(name = "CREDIT_CARD_NUMBER")
    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    @Column(name = "CREDIT_CARD_EXPIRE")
    public String getCreditCardExpire() {
        return creditCardExpire;
    }

    public void setCreditCardExpire(String creditCardExpire) {
        this.creditCardExpire = creditCardExpire;
    }

    @Column(name = "CREDIT_CARD_CYCLES")
    public Integer getCreditCardCycles() {
        return creditCardCycles;
    }

    public void setCreditCardCycles(Integer creditCardCycles) {
        this.creditCardCycles = creditCardCycles;
    }

    @Column(name = "CREATE_USER")
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATE_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRE_TRADE_ID")
    public PreTrade getPreTrade() {
        return preTrade;
    }

    public void setPreTrade(PreTrade preTrade) {
        this.preTrade = preTrade;
    }
}
