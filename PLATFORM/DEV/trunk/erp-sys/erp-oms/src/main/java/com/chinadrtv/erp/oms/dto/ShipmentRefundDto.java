/*
 * @(#)ShipmentRefundDto.java 1.0 2013-4-2下午3:56:50
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.oms.dto;

import com.chinadrtv.erp.model.Company;
import com.chinadrtv.erp.model.Warehouse;
import com.chinadrtv.erp.model.trade.ShipmentRefund;

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
 * @since 2013-4-2 下午3:56:50 
 * 
 */
public class ShipmentRefundDto extends ShipmentRefund {

	private static final long serialVersionUID = 1L;
	private String hisLabel;
	private String ids;
	private String warehouseId;
	private String companyId;
	private Company company;
	private Warehouse warehouse;

	public String getHisLabel() {
		return hisLabel;
	}
	public void setHisLabel(String hisLabel) {
		this.hisLabel = hisLabel;
	}
	public String getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public Warehouse getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
}
