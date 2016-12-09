package com.chinadrtv.erp.model.trade;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
* WMS发货详情
* User: 王健
* Date: 2013-2-18
* 橡果国际-系统集成部
* Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
*/
@Entity
@Table(name = "WMS_SHIPMENT_DETAIL2", schema = "ACOAPP_DBTRANSF")
public class WmsShipmentDetail2 implements java.io.Serializable {

	private static final long serialVersionUID = 537897108776823996L;
	private Long ruid;
	private String shipmentId;
	private String revision;
	private Long shipmentLineNum;
	private String item;
	private String kits;
	private Long totalQty;
	private BigDecimal unitPrice;
	private String quantityum;
	private Integer tasksno;
	private Integer carriage;
	private String isAssembly;

	// Constructors

	/** default constructor */
	public WmsShipmentDetail2() {
	}

	/** minimal constructor */
	public WmsShipmentDetail2(Integer tasksno) {
		this.tasksno = tasksno;
	}

	/** full constructor */
	public WmsShipmentDetail2(String shipmentId, String revision,
			Long shipmentLineNum, String item, String kits,
			Long totalQty, BigDecimal unitPrice, String quantityum,
			Integer tasksno, Integer carriage, String isAssembly) {
		this.shipmentId = shipmentId;
		this.revision = revision;
		this.shipmentLineNum = shipmentLineNum;
		this.item = item;
		this.kits = kits;
		this.totalQty = totalQty;
		this.unitPrice = unitPrice;
		this.quantityum = quantityum;
		this.tasksno = tasksno;
		this.carriage = carriage;
		this.isAssembly = isAssembly;
	}

	@Id
	@Column(name = "RUID", unique = true, nullable = false, precision = 22, scale = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_wms_shipment_detail2")
    @SequenceGenerator(name = "s_wms_shipment_detail2", sequenceName = "s_wms_shipment_detail2")
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

	@Column(name = "KITS", length = 100)
	public String getKits() {
		return this.kits;
	}

	public void setKits(String kits) {
		this.kits = kits;
	}

	@Column(name = "TOTAL_QTY", precision = 22, scale = 0)
	public Long getTotalQty() {
		return this.totalQty;
	}

	public void setTotalQty(Long totalQty) {
		this.totalQty = totalQty;
	}

	@Column(name = "UNIT_PRICE", scale = 5)
	public BigDecimal getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	@Column(name = "QUANTITYUM", length = 25)
	public String getQuantityum() {
		return this.quantityum;
	}

	public void setQuantityum(String quantityum) {
		this.quantityum = quantityum;
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

	@Column(name = "IS_ASSEMBLY", length = 4)
	public String getIsAssembly() {
		return this.isAssembly;
	}

	public void setIsAssembly(String isAssembly) {
		this.isAssembly = isAssembly;
	}

}