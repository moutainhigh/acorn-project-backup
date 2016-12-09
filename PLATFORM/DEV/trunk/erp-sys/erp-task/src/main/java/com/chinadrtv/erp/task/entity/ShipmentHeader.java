package com.chinadrtv.erp.task.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.chinadrtv.erp.task.core.orm.entity.IdEntity;

@Entity
@Table(name = "SHIPMENT_HEADER", schema = "ACOAPP_OMS")
public class ShipmentHeader extends IdEntity{
   
	private static final long serialVersionUID = -6770558548684024599L;

	@Column(name = "SHIPMENT_ID", length = 16)
    private String shipmentId;

	public String getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(String shipmentId) {
		this.shipmentId = shipmentId;
	}

}
