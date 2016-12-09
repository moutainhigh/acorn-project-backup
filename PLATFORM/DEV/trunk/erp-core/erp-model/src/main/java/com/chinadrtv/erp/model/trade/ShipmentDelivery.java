package com.chinadrtv.erp.model.trade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 订单信息(TC)
 * User: 王健
 * Date: 2013-03-19
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Entity
@Table(name = "SHIPMENT_DELIVERY", schema = "ACOAPP_OMS")
public class ShipmentDelivery implements java.io.Serializable {

	private static final long serialVersionUID = -6812647300955619946L;
	private Long id;
	private String orderId;
	private String isCancel;
	private String remark;
	private Date crtDate;
	private String crtUsr;
	private String shipmentId;

	// Constructors

	/** default constructor */
	public ShipmentDelivery() {
	}

	/** full constructor */
	public ShipmentDelivery(String orderId, String isCancel, String remark, Date crtDate, String crtUsr, String shipmentId) {
		this.orderId = orderId;
		this.isCancel = isCancel;
		this.remark = remark;
		this.crtDate = crtDate;
		this.crtUsr = crtUsr;
		this.shipmentId = shipmentId;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SHIPMENT_DELIVERY_SEQ")
    @SequenceGenerator(name = "SHIPMENT_DELIVERY_SEQ", sequenceName = "SHIPMENT_DELIVERY_SEQ")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "ORDER_ID", length = 50)
	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Column(name = "IS_CANCEL", length = 1)
	public String getIsCancel() {
		return this.isCancel;
	}

	public void setIsCancel(String isCancel) {
		this.isCancel = isCancel;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "CRT_DATE", length = 7)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
	public Date getCrtDate() {
		return this.crtDate;
	}

	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}

	@Column(name = "CRT_USR", length = 50)
	public String getCrtUsr() {
		return this.crtUsr;
	}

	public void setCrtUsr(String crtUsr) {
		this.crtUsr = crtUsr;
	}

	@Column(name = "SHIPMENT_ID", length = 50)
	public String getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(String shipmentId) {
		this.shipmentId = shipmentId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((crtDate == null) ? 0 : crtDate.hashCode());
		result = prime * result + ((crtUsr == null) ? 0 : crtUsr.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isCancel == null) ? 0 : isCancel.hashCode());
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((shipmentId == null) ? 0 : shipmentId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShipmentDelivery other = (ShipmentDelivery) obj;
		if (crtDate == null) {
			if (other.crtDate != null)
				return false;
		} else if (!crtDate.equals(other.crtDate))
			return false;
		if (crtUsr == null) {
			if (other.crtUsr != null)
				return false;
		} else if (!crtUsr.equals(other.crtUsr))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isCancel == null) {
			if (other.isCancel != null)
				return false;
		} else if (!isCancel.equals(other.isCancel))
			return false;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (shipmentId == null) {
			if (other.shipmentId != null)
				return false;
		} else if (!shipmentId.equals(other.shipmentId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrderDelivery [id=" + id + ", orderId=" + orderId + ", isCancel=" + isCancel + ", remark=" + remark
				+ ", crtDate=" + crtDate + ", crtUsr=" + crtUsr + "]";
	}

}