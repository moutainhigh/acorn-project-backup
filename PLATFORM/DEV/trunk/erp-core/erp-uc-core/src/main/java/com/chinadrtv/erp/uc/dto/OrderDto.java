/*
 * @(#)OrderDto.java 1.0 2013-6-17下午3:12:35
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.chinadrtv.erp.model.agent.Order;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013-6-17 下午3:12:35 
 * 
 */
public class OrderDto{
	
	private String orderid;
	private Date crdt;
	private String status;
	private BigDecimal totalprice;
	private String crusr;
	private String grpid;
	private String shipmentId;
	private String logisticsStatusId;
	private String grpName;
	
	public String getShipmentId() {
		return shipmentId;
	}
	public void setShipmentId(String shipmentId) {
		this.shipmentId = shipmentId;
	}
	public String getLogisticsStatusId() {
		return logisticsStatusId;
	}
	public void setLogisticsStatusId(String logisticsStatusId) {
		this.logisticsStatusId = logisticsStatusId;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public Date getCrdt() {
		return crdt;
	}
	public void setCrdt(Date crdt) {
		this.crdt = crdt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public BigDecimal getTotalprice() {
		return totalprice;
	}
	public void setTotalprice(BigDecimal totalprice) {
		this.totalprice = totalprice;
	}
	public String getCrusr() {
		return crusr;
	}
	public void setCrusr(String crusr) {
		this.crusr = crusr;
	}
	public String getGrpid() {
		return grpid;
	}
	public void setGrpid(String grpid) {
		this.grpid = grpid;
	}
	public String getGrpName() {
		return grpName;
	}
	public void setGrpName(String grpName) {
		this.grpName = grpName;
	}
}
