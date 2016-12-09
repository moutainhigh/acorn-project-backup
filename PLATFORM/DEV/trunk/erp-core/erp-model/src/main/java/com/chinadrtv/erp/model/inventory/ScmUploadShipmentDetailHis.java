package com.chinadrtv.erp.model.inventory;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author cuiming
 * @version 1.0
 * @since 2013-2-6 下午1:25:27
 * SCM出库单明细
 */
@Entity
@Table(name = "SCM_UPLOAD_SHIPMENT_DETAIL_HIS")
public class ScmUploadShipmentDetailHis implements Serializable {

	private Long ruid;
	private Long shipmentLineNum;
	private String item;
	private Long totalQty;
	private String lot;
	private Long itemStatus;
	private Long freeFlag;
	private String memo;
	private Long unitPrice;
	private String str3c;
	private Long tasksno;
	private Long amount;
	private Long amount2;
    private String shipmentid;
	private Long internalId;
	private String batchId;
	private Date batchDate;

    private ScmUploadShipmentHeaderHis shipment;

    /*
      关联字段 internal_id才会唯一,2013-04-09,gaodejian
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "INTERNAL_ID", referencedColumnName="INTERNAL_ID", insertable=false, updatable=false)
    public ScmUploadShipmentHeaderHis getShipment() {
        return shipment;
    }

    public void setShipment(ScmUploadShipmentHeaderHis shipment) {
        this.shipment = shipment;
    }

    /**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 */
	public ScmUploadShipmentDetailHis() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the ruid
	 */
	@Id
	@Column(name = "RUID")
	public Long getRuid() {
		return ruid;
	}

	/**
	 * @param ruid
	 *            the ruid to set
	 */
	public void setRuid(Long ruid) {
		this.ruid = ruid;
	}

	/**
	 * @return the shipmentLineNum
	 */
	@Column(name = "SHIPMENT_LINE_NUM")
	public Long getShipmentLineNum() {
		return shipmentLineNum;
	}

	/**
	 * @param shipmentLineNum
	 *            the shipmentLineNum to set
	 */
	public void setShipmentLineNum(Long shipmentLineNum) {
		this.shipmentLineNum = shipmentLineNum;
	}

	/**
	 * @return the item
	 */
	@Column(name = "ITEM")
	public String getItem() {
		return item;
	}

	/**
	 * @param item
	 *            the item to set
	 */
	public void setItem(String item) {
		this.item = item;
	}

	/**
	 * @return the totalQty
	 */
	@Column(name = "TOTAL_QTY")
	public Long getTotalQty() {
		return totalQty;
	}

	/**
	 * @param totalQty
	 *            the totalQty to set
	 */
	public void setTotalQty(Long totalQty) {
		this.totalQty = totalQty;
	}

	/**
	 * @return the lot
	 */
	@Column(name = "LOT")
	public String getLot() {
		return lot;
	}

	/**
	 * @param lot
	 *            the lot to set
	 */
	public void setLot(String lot) {
		this.lot = lot;
	}

	/**
	 * @return the itemStatus
	 */
	@Column(name = "ITEM_STATUS")
	public Long getItemStatus() {
		return itemStatus;
	}

	/**
	 * @param itemStatus
	 *            the itemStatus to set
	 */
	public void setItemStatus(Long itemStatus) {
		this.itemStatus = itemStatus;
	}

	/**
	 * @return the freeFlag
	 */
	@Column(name = "FREE_FLAG")
	public Long getFreeFlag() {
		return freeFlag;
	}

	/**
	 * @param freeFlag
	 *            the freeFlag to set
	 */
	public void setFreeFlag(Long freeFlag) {
		this.freeFlag = freeFlag;
	}

	/**
	 * @return the memo
	 */
	@Column(name = "MEMO")
	public String getMemo() {
		return memo;
	}

	/**
	 * @param memo
	 *            the memo to set
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * @return the unitPrice
	 */
	@Column(name = "UNIT_PRICE")
	public Long getUnitPrice() {
		return unitPrice;
	}

	/**
	 * @param unitPrice
	 *            the unitPrice to set
	 */
	public void setUnitPrice(Long unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * @return the str3c
	 */
	@Column(name = "STR3C")
	public String getStr3c() {
		return str3c;
	}

	/**
	 * @param str3c
	 *            the str3c to set
	 */
	public void setStr3c(String str3c) {
		this.str3c = str3c;
	}

	/**
	 * @return the tasksno
	 */
	@Column(name = "TASKSNO")
	public Long getTasksno() {
		return tasksno;
	}

	/**
	 * @param tasksno
	 *            the tasksno to set
	 */
	public void setTasksno(Long tasksno) {
		this.tasksno = tasksno;
	}

	/**
	 * @return the amount
	 */
	@Column(name = "AMOUNT")
	public Long getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(Long amount) {
		this.amount = amount;
	}

	/**
	 * @return the amount2
	 */
	@Column(name = "AMOUNT2")
	public Long getAmount2() {
		return amount2;
	}

	/**
	 * @param amount2
	 *            the amount2 to set
	 */
	public void setAmount2(Long amount2) {
		this.amount2 = amount2;
	}

	/**
	 * @return the shipmentid
	 */
	@Column(name = "SHIPMENTID")
	public String getShipmentid() {
		return shipmentid;
	}

	/**
	 * @param shipmentid
	 *            the shipmentid to set
	 */
	public void setShipmentid(String shipmentid) {
		this.shipmentid = shipmentid;
	}

	/**
	 * @return the internalId
	 */
	@Column(name = "INTERNAL_ID")
	public Long getInternalId() {
		return internalId;
	}

	/**
	 * @param internalId
	 *            the internalId to set
	 */
	public void setInternalId(Long internalId) {
		this.internalId = internalId;
	}

	/**
	 * @return the batchId
	 */
	@Column(name = "BATCH_ID")
	public String getBatchId() {
		return batchId;
	}

	/**
	 * @param batchId
	 *            the batchId to set
	 */
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	/**
	 * @return the batchDate
	 */
	@Column(name = "BATCH_DATE")
	public Date getBatchDate() {
		return batchDate;
	}

	/**
	 * @param batchDate
	 *            the batchDate to set
	 */
	public void setBatchDate(Date batchDate) {
		this.batchDate = batchDate;
	}

}

