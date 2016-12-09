package com.chinadrtv.erp.model.trade;

import java.math.BigDecimal;
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
* WMS发货详情
* User: 王健
* Date: 2013-2-18
* 橡果国际-系统集成部
* Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
*/
@Entity
@Table(name = "WMS_SHIPMENT_DETAIL1", schema = "ACOAPP_DBTRANSF")
public class WmsShipmentDetail1 implements java.io.Serializable {

	private static final long serialVersionUID = 6480654945277201211L;
	private Long ruid;
	private String shipmentId;
	private String revision;
	private Long shipmentLineNum;
	private String item;
	private String itemDesc;
	private Long totalQty;
	private BigDecimal unitPrice;
	private String status;
	private String quantityum;
	private Integer freeFlag;
	private String memo;
	private Integer tasksno;
	private Integer carriage;
	private String batchId;
	private Date batchDate;

	// Constructors

	/** default constructor */
	public WmsShipmentDetail1() {
	}

	/** minimal constructor */
	public WmsShipmentDetail1(Integer tasksno) {
		this.tasksno = tasksno;
	}

	/** full constructor */
	public WmsShipmentDetail1(String shipmentId, String revision,
			Long shipmentLineNum, String item, String itemDesc,
			Long totalQty, BigDecimal unitPrice, String status,
			String quantityum, Integer freeFlag, String memo,
			Integer tasksno, Integer carriage, String batchId,
			Date batchDate) {
		this.shipmentId = shipmentId;
		this.revision = revision;
		this.shipmentLineNum = shipmentLineNum;
		this.item = item;
		this.itemDesc = itemDesc;
		this.totalQty = totalQty;
		this.unitPrice = unitPrice;
		this.status = status;
		this.quantityum = quantityum;
		this.freeFlag = freeFlag;
		this.memo = memo;
		this.tasksno = tasksno;
		this.carriage = carriage;
		this.batchId = batchId;
		this.batchDate = batchDate;
	}

	@Id
	@Column(name = "RUID", unique = true, nullable = false, precision = 22, scale = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_wms_shipment_detail1")
    @SequenceGenerator(name = "s_wms_shipment_detail1", sequenceName = "s_wms_shipment_detail1")
	public Long getRuid() {
		return this.ruid;
	}

	public void setRuid(Long ruid) {
		this.ruid = ruid;
	}

	@Column(name = "SHIPMENT_ID", length = 20)
	public String getShipmentId() {
		return this.shipmentId;
	}

	public void setShipmentId(String shipmentId) {
		this.shipmentId = shipmentId;
	}

	@Column(name = "REVISION", length = 10)
	public String getRevision() {
		return this.revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}

	@Column(name = "SHIPMENT_LINE_NUM", precision = 22, scale = 0)
	public Long getShipmentLineNum() {
		return this.shipmentLineNum;
	}

	public void setShipmentLineNum(Long shipmentLineNum) {
		this.shipmentLineNum = shipmentLineNum;
	}

	@Column(name = "ITEM", length = 20)
	public String getItem() {
		return this.item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	@Column(name = "ITEM_DESC", length = 100)
	public String getItemDesc() {
		return this.itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	@Column(name = "TOTAL_QTY", precision = 22, scale = 0)
	public Long getTotalQty() {
		return this.totalQty;
	}

	public void setTotalQty(Long totalQty) {
		this.totalQty = totalQty;
	}

	@Column(name = "UNIT_PRICE", scale = 6)
	public BigDecimal getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	@Column(name = "STATUS", length = 25)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "QUANTITYUM", length = 25)
	public String getQuantityum() {
		return this.quantityum;
	}

	public void setQuantityum(String quantityum) {
		this.quantityum = quantityum;
	}

	@Column(name = "FREE_FLAG", precision = 22, scale = 0)
	public Integer getFreeFlag() {
		return this.freeFlag;
	}

	public void setFreeFlag(Integer freeFlag) {
		this.freeFlag = freeFlag;
	}

	@Column(name = "MEMO", length = 200)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "TASKSNO", nullable = false, precision = 22, scale = 0)
	public Integer getTasksno() {
		return this.tasksno;
	}

	public void setTasksno(Integer tasksno) {
		this.tasksno = tasksno;
	}

	@Column(name = "CARRIAGE", precision = 22, scale = 0)
	public Integer getCarriage() {
		return this.carriage;
	}

	public void setCarriage(Integer carriage) {
		this.carriage = carriage;
	}

	@Column(name = "BATCH_ID", length = 50)
	public String getBatchId() {
		return this.batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	@Column(name = "BATCH_DATE", length = 7)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
	public Date getBatchDate() {
		return this.batchDate;
	}

	public void setBatchDate(Date batchDate) {
		this.batchDate = batchDate;
	}

}