package com.chinadrtv.erp.model.inventory;

import javax.persistence.*;
import java.util.Date;

/**
 * 库存计算日志表
 * User: gaodejian
 * Date: 13-1-28
 * Time: 下午2:52
 * To change this template use File | Settings | File Templates.
 */
@Table(name="ITEM_INVENTORY_CALC_LOG", schema = "ACOAPP_OMS")
@Entity
public class ItemInventoryCalcLog implements java.io.Serializable {
    private Long id;
    private String batchId;
    private String startRevesion;
    private String endRevesion;
    private Long rowsAffected;
    private String status;
    private String statusRemark;
    private Date statusDate;
    private String createdBy;
    private String modifiedBy;
    private Date created;
    private Date  modified;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEM_INVENTORY_CALC_LOG_SEQ")
    @SequenceGenerator(name = "ITEM_INVENTORY_CALC_LOG_SEQ", sequenceName = "ACOAPP_OMS.ITEM_INVENTORY_CALC_LOG_SEQ", allocationSize = 1)
    @Column(name = "ID")
    public Long getId() {
        return this.id;
    }

    public void setId(Long value) {
        this.id = value;
    }

    @Column(name = "BATCH_ID")
    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String value) {
        this.batchId = value;
    }

    @Column(name = "START_REVESION")
    public String getStartRevesion() {
        return startRevesion;
    }

    public void setStartRevesion(String value) {
        this.startRevesion = value;
    }

    @Column(name = "END_REVESION")
    public String getEndRevesion() {
        return endRevesion;
    }

    public void setEndRevesion(String value) {
        this.endRevesion = value;
    }

    @Column(name = "ROWS_AFFECTED")
    public Long getRowsAffected() {
        return rowsAffected;
    }

    public void setRowsAffected(Long value) {
        this.rowsAffected = value;
    }

    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String value) {
        this.status = value;
    }

    @Column(name = "STATUS_REMARK")
    public String getStatusRemark() {
        return statusRemark;
    }

    public void setStatusRemark(String value) {
        this.statusRemark = value;
    }

    @Column(name = "STATUS_DATE")
    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date value) {
        this.statusDate = value;
    }

    @Column(name = "CREATED_BY")
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String value) {
        this.createdBy = value;
    }

    @Column(name = "MODIFIED_BY")
    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String value) {
        this.modifiedBy = value;
    }

    @Column(name = "CREATED")
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Column(name = "MODIFIED")
    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }
}
