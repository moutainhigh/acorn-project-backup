package com.chinadrtv.erp.oms.dto;

import java.util.Date;


/**
 * zhangguosheng
 */
public class OrderPaymentDto {

	private Long id;
    private String orderId;    	//订单编号
    private Date crdt;    		//订单提交时间
    private String dsca;		//订单状态
    private Float totalPrice;	//订单金额
    private String contactId;	//客户编号
    private String name;		//客户姓名
    private String payType;		//支付方式
    public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	private String dscb;		//支付方式 
    private String confirm;		//是否已付款
    
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
	public Date getCrdt() {
		return crdt;
	}
	public void setCrdt(Date crdt) {
		this.crdt = crdt;
	}
	public String getDsca() {
		return dsca;
	}
	public void setDsca(String dsca) {
		this.dsca = dsca;
	}
	public Float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getContactId() {
		return contactId;
	}
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDscb() {
		return dscb;
	}
	public void setDscb(String dscb) {
		this.dscb = dscb;
	}
	public String getConfirm() {
		return confirm;
	}
	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

}
