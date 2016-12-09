package com.chinadrtv.erp.model.inventory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author gaodejian
 * @version 1.0
 * @since 2013-2-5 下午4:22:25
 * 商品库存调整
 */
@Entity
@Table(name = "SCM_ONHAND_ADJUSTMENT_HIS")
public class ScmOnhandAdjustmentHis implements Serializable {

    private Long ruid;
    private BigDecimal adjustmentId;
    private Long adjustmentType;
    private String warehouse;
    private Long itemStatus;
    private String item;
    private Long totalQty;
    private String lot;
    private Date dateTimeStamp;
    private String memo;
    private Long tasksno;
    private String recpsn;
    private String batchId;
    private Date batchDate;

	@Id
	@Column(name = "RUID", updatable=false)
	public Long getRuid() {
		return ruid;
	}

	public void setRuid(Long ruid) {
		this.ruid = ruid;
	}

    @Column(name = "adjustment_id", updatable=false)
    public BigDecimal getAdjustmentId() {
        return adjustmentId;
    }

    public void setAdjustmentId(BigDecimal adjustmentId) {
        this.adjustmentId = adjustmentId;
    }

    @Column(name = "adjustment_type", updatable=false)
    public Long getAdjustmentType() {
        return adjustmentType;
    }

    public void setAdjustmentType(Long adjustmentType) {
        this.adjustmentType = adjustmentType;
    }

    @Column(name = "item_status", updatable=false)
    public Long getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(Long itemStatus) {
        this.itemStatus = itemStatus;
    }

    @Column(name = "warehouse", updatable=false)
    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

	@Column(name = "TASKSNO", updatable=false)
	public Long getTasksno() {
		return tasksno;
	}

	public void setTasksno(Long tasksno) {
		this.tasksno = tasksno;
	}

    @Column(name = "memo", updatable=false)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

	@Column(name = "BATCH_ID")
	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	@Column(name = "BATCH_DATE")
	public Date getBatchDate() {
		return batchDate;
	}

	public void setBatchDate(Date batchDate) {
		this.batchDate = batchDate;
	}

    @Column(name = "ITEM", updatable=false)
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Column(name = "TOTAL_QTY", updatable=false)
    public Long getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Long totalQty) {
        this.totalQty = totalQty;
    }

    @Column(name = "LOT", updatable=false)
    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    @Column(name = "recpsn", updatable=false)
    public String getRecpsn() {
        return recpsn;
    }

    public void setRecpsn(String recpsn) {
        this.recpsn = recpsn;
    }

    @Column(name = "date_time_stamp", updatable=false)
    public Date getDateTimeStamp() {
        return dateTimeStamp;
    }

    public void setDateTimeStamp(Date dateTimeStamp) {
        this.dateTimeStamp = dateTimeStamp;
    }


}
