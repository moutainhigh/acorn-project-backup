package com.chinadrtv.erp.model.inventory;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author gaodejian
 * @version 1.0
 * @since 2013-2-5 下午4:22:25
 * 商品状态调整
 */
@Entity
@Table(name = "SCM_ITEM_STATUS_CHANGE_HIS")
public class ScmItemStatusChangeHis implements Serializable {

    private Long ruid;
    private String changeId;
    private String warehouse;
    private Long fromStatus;
    private Long toStatus;
    private String item;
    private Long totalQty;
    private String lot;
    private Date dateTimeStamp;
    private Long tasksno;
    private Date dndate;
    private String dnuser;
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

    @Column(name = "CHANGE_ID", updatable=false)
    public String getChangeId() {
        return changeId;
    }

    public void setChangeId(String changeId) {
        this.changeId = changeId;
    }

	@Column(name = "TASKSNO", updatable=false)
	public Long getTasksno() {
		return tasksno;
	}

	public void setTasksno(Long tasksno) {
		this.tasksno = tasksno;
	}

    @Column(name = "WAREHOUSE", updatable=false)
    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
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

    @Column(name = "DNDAT", updatable=false)
    public Date getDndate() {
        return dndate;
    }

    public void setDndate(Date dndate) {
        this.dndate = dndate;
    }

    @Column(name = "dnuser", updatable=false)
    public String getDnuser() {
        return dnuser;
    }

    public void setDnuser(String dnuser) {
        this.dnuser = dnuser;
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

    @Column(name = "from_status", updatable=false)
    public Long getFromStatus() {
        return fromStatus;
    }

    public void setFromStatus(Long fromStatus) {
        this.fromStatus = fromStatus;
    }

    @Column(name = "to_status", updatable=false)
    public Long getToStatus() {
        return toStatus;
    }

    public void setToStatus(Long toStatus) {
        this.toStatus = toStatus;
    }
}
