package com.chinadrtv.erp.model.inventory;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author gaodejian
 * @version 1.0
 * @since 2013-2-5 下午4:22:25
 * SCM收货通知单
 */
@Entity
@Table(name = "SCM_UPLOAD_RECEIPT_DETAIL_HIS")
public class ScmUploadReceiptDetailHis implements Serializable {

    private Long ruid;
    private String receiptid;
    private Long receiptLineNo;
    private String item;
    private Double totalQty;
    private String lot;
    private Date expirationDate;
    private Long itemStatus;
    private Long freeFlag;
    private String memo;
    private Long tasksno;
    private Double slprc;
    private Double amount;
    private Long internalId;
    private String batchId;
    private Date batchDate;
    private ScmUploadReceiptHeaderHis receipt;


	@Id
	@Column(name = "RUID", updatable=false)
	public Long getRuid() {
		return ruid;
	}

	public void setRuid(Long ruid) {
		this.ruid = ruid;
	}

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "RECEIPT_ID", referencedColumnName="RECEIPT_ID", insertable = false, updatable = false),
            @JoinColumn(name = "INTERNAL_ID", referencedColumnName="INTERNAL_ID", insertable = false, updatable = false)
    })
    public ScmUploadReceiptHeaderHis getReceipt() {
        return receipt;
    }

    public void setReceipt(ScmUploadReceiptHeaderHis receipt) {
        this.receipt = receipt;
    }

	@Column(name = "MEMO", updatable=false)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "TASKSNO", updatable=false)
	public Long getTasksno() {
		return tasksno;
	}

	public void setTasksno(Long tasksno) {
		this.tasksno = tasksno;
	}

	@Column(name = "RECEIPTID", updatable=false)
	public String getReceiptid() {
		return receiptid;
	}

	public void setReceiptid(String receiptid) {
		this.receiptid = receiptid;
	}

	@Column(name = "INTERNAL_ID", updatable=false)
	public Long getInternalId() {
		return internalId;
	}

	public void setInternalId(Long internalId) {
		this.internalId = internalId;
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

    @Column(name = "RECEIPT_LINE_NO", updatable=false)
    public Long getReceiptLineNo() {
        return receiptLineNo;
    }

    public void setReceiptLineNo(Long receiptLineNo) {
        this.receiptLineNo = receiptLineNo;
    }

    @Column(name = "ITEM", updatable=false)
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Column(name = "TOTAL_QTY", updatable=false)
    public Double getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Double totalQty) {
        this.totalQty = totalQty;
    }

    @Column(name = "LOT", updatable=false)
    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    @Column(name = "EXPIRATION_DATE", updatable=false)
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Column(name = "SLPRC", updatable=false)
    public Double getSlprc() {
        return slprc;
    }

    public void setSlprc(Double slprc) {
        this.slprc = slprc;
    }

    @Column(name = "ITEM_STATUS", updatable=false)
    public Long getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(Long itemStatus) {
        this.itemStatus = itemStatus;
    }

    @Column(name = "FREE_FLAG", updatable=false)
    public Long getFreeFlag() {
        return freeFlag;
    }

    public void setFreeFlag(Long freeFlag) {
        this.freeFlag = freeFlag;
    }

    @Column(name = "AMOUNT", updatable=false)
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
