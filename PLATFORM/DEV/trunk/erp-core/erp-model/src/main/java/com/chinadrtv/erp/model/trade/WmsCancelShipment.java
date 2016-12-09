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
* WMS取消运单表
* User: 王健
* Date: 2013-2-18
* 橡果国际-系统集成部
* Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
*/
@Entity
@Table(name = "WMS_CANCEL_SHIPMENT", schema = "ACOAPP_DBTRANSF")
public class WmsCancelShipment implements java.io.Serializable {

	private static final long serialVersionUID = -9081576941505695725L;
	private Long ruid;
	private String shipmentId;
	private String warehouse;
	private String carrier;
	private Date cancelDate;
	private Integer isupdate;
	private Integer taskno;
	private Date updat;
	private Integer dnstatus;
	private String dsc;
	private String revision;

	// Constructors

	/** default constructor */
	public WmsCancelShipment() {
	}

	/** minimal constructor */
	public WmsCancelShipment(String shipmentId, String warehouse,
			Integer isupdate, Date updat, Integer dnstatus) {
		this.shipmentId = shipmentId;
		this.warehouse = warehouse;
		this.isupdate = isupdate;
		this.updat = updat;
		this.dnstatus = dnstatus;
	}

	/** full constructor */
	public WmsCancelShipment(String shipmentId, String warehouse,
			String carrier, Date cancelDate, Integer isupdate,
			Integer taskno, Date updat, Integer dnstatus, String dsc,
			String revision) {
		this.shipmentId = shipmentId;
		this.warehouse = warehouse;
		this.carrier = carrier;
		this.cancelDate = cancelDate;
		this.isupdate = isupdate;
		this.taskno = taskno;
		this.updat = updat;
		this.dnstatus = dnstatus;
		this.dsc = dsc;
		this.revision = revision;
	}

	@Id
	@Column(name = "RUID", unique = true, nullable = false, precision = 22, scale = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_wms_cancel_shipment")
    @SequenceGenerator(name = "s_wms_cancel_shipment", sequenceName = "s_wms_cancel_shipment")
	public Long getRuid() {
		return this.ruid;
	}

	public void setRuid(Long ruid) {
		this.ruid = ruid;
	}

	@Column(name = "SHIPMENT_ID", nullable = false, length = 20)
	public String getShipmentId() {
		return this.shipmentId;
	}

	public void setShipmentId(String shipmentId) {
		this.shipmentId = shipmentId;
	}

	@Column(name = "WAREHOUSE", nullable = false, length = 20)
	public String getWarehouse() {
		return this.warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	@Column(name = "CARRIER", length = 25)
	public String getCarrier() {
		return this.carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	@Column(name = "CANCEL_DATE", length = 7)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
	public Date getCancelDate() {
		return this.cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	@Column(name = "ISUPDATE", nullable = false, precision = 22, scale = 0, insertable = false, updatable = false)
	public Integer getIsupdate() {
		return this.isupdate;
	}

	public void setIsupdate(Integer isupdate) {
		this.isupdate = isupdate;
	}

	@Column(name = "TASKNO", precision = 22, scale = 0)
	public Integer getTaskno() {
		return this.taskno;
	}

	public void setTaskno(Integer taskno) {
		this.taskno = taskno;
	}

	@Column(name = "UPDAT", nullable = false, length = 7)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
	public Date getUpdat() {
		return this.updat;
	}

	public void setUpdat(Date updat) {
		this.updat = updat;
	}

	@Column(name = "DNSTATUS", nullable = false, precision = 22, scale = 0, insertable = false, updatable = false)
	public Integer getDnstatus() {
		return this.dnstatus;
	}

	public void setDnstatus(Integer dnstatus) {
		this.dnstatus = dnstatus;
	}

	@Column(name = "DSC", length = 200)
	public String getDsc() {
		return this.dsc;
	}

	public void setDsc(String dsc) {
		this.dsc = dsc;
	}

	@Column(name = "REVISION", length = 20)
	public String getRevision() {
		return this.revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}

}