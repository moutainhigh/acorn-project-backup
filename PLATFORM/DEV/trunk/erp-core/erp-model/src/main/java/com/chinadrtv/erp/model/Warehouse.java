package com.chinadrtv.erp.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-3-6
 * Time: 下午6:44
 * To change this template use File | Settings | File Templates.
 */
@Table(name="WAREHOUSE", schema = "ACOAPP_OMS")
@Entity
public class Warehouse implements Serializable {

    private Long warehouseId;
    private String warehouseCode;
    private String warehouseName;
    private String description;


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WAREHOUSE_SEQ")
    @SequenceGenerator(name = "WAREHOUSE_SEQ", sequenceName = "ACOAPP_OMS.WAREHOUSE_SEQ")
    @Column(name = "WAREHOUSE_ID")
    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    @Column(name = "WAREHOUSE_CODE")
    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    @Column(name = "WAREHOUSE_NAME")
    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
