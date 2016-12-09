package com.chinadrtv.erp.model.trade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * zhangguosheng
 */
@Entity
@Table(name="ORDER_PAYMENT_CONFIRM", schema="ACOAPP_OMS")
public class OrderPaymentConfirm implements Serializable{
	
	private static final long serialVersionUID = 9164917942660444777L;

	@Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDER_PAYMENT_CONFIRM_SEQ")
    @SequenceGenerator(name = "ORDER_PAYMENT_CONFIRM_SEQ", sequenceName = "ACOAPP_OMS.ORDER_PAYMENT_CONFIRM_SEQ")
    private Long id;
    
    @Column(name="ORDERID")
    private String orderId;
    
    @Column(name="PAYTYPE")
    private String payType;
    
    @Column(name="PAY_NO")
    private String payNo;
    
    @Column(name="PAY_USER")
    private String payUser;
    
    @Column(name="PAY_DATE")
    private Date payDate;
    
    @Column(name="CREATE_USER")
    private String createUser;
    
    @Column(name="CREATE_DATE")
    private Date createDate;
    
    @Column(name="UPDATE_USER")
    private String updateUser;
    
    @Column(name="UPDATE_DATE")
    private Date updateDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public String getPayUser() {
		return payUser;
	}

	public void setPayUser(String payUser) {
		this.payUser = payUser;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}

