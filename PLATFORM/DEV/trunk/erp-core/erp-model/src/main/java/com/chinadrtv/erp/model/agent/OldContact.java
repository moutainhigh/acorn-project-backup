package com.chinadrtv.erp.model.agent;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
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
 * @since 2013-5-9 下午3:08:35 
 *
 */
@Entity
@Table(name="oldcontact", schema="iagent")
public class OldContact implements java.io.Serializable {

	private static final long serialVersionUID = 3499775596337593179L;
	private String contactid;
	private String usrid;
	private Integer orderTotal;
	private Integer finishTotal;
	private Date stime;
	private Date etime;
	private String status;
	private BigDecimal lastSnapId;
	private String cancelreason;

	// Constructors

	/** default constructor */
	public OldContact() {
	}

	/** minimal constructor */
	public OldContact(String contactid, String usrid, Integer orderTotal,
			Integer finishTotal, Date stime, Date etime, String status) {
		this.contactid = contactid;
		this.usrid = usrid;
		this.orderTotal = orderTotal;
		this.finishTotal = finishTotal;
		this.stime = stime;
		this.etime = etime;
		this.status = status;
	}

	/** full constructor */
	public OldContact(String contactid, String usrid, Integer orderTotal,
			Integer finishTotal, Date stime, Date etime, String status,
			BigDecimal lastSnapId, String cancelreason) {
		this.contactid = contactid;
		this.usrid = usrid;
		this.orderTotal = orderTotal;
		this.finishTotal = finishTotal;
		this.stime = stime;
		this.etime = etime;
		this.status = status;
		this.lastSnapId = lastSnapId;
		this.cancelreason = cancelreason;
	}

	// Property accessors

	@Id
	@Column(name = "CONTACTID", nullable = false, length = 20)
	public String getContactid() {
		return this.contactid;
	}

	public void setContactid(String contactid) {
		this.contactid = contactid;
	}

	@Column(name = "USRID", nullable = false, length = 10)
	public String getUsrid() {
		return this.usrid;
	}

	public void setUsrid(String usrid) {
		this.usrid = usrid;
	}

	@Column(name = "ORDER_TOTAL", nullable = false, precision = 4, scale = 0)
	public Integer getOrderTotal() {
		return this.orderTotal;
	}

	public void setOrderTotal(Integer orderTotal) {
		this.orderTotal = orderTotal;
	}

	@Column(name = "FINISH_TOTAL", nullable = false, precision = 4, scale = 0)
	public Integer getFinishTotal() {
		return this.finishTotal;
	}

	public void setFinishTotal(Integer finishTotal) {
		this.finishTotal = finishTotal;
	}

	@Column(name = "STIME", nullable = false, length = 7)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
	public Date getStime() {
		return this.stime;
	}

	public void setStime(Date stime) {
		this.stime = stime;
	}

	@Column(name = "ETIME", nullable = false, length = 7)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
	public Date getEtime() {
		return this.etime;
	}

	public void setEtime(Date etime) {
		this.etime = etime;
	}

	@Column(name = "STATUS", nullable = false, length = 2)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "LAST_SNAP_ID", precision = 22, scale = 0)
	public BigDecimal getLastSnapId() {
		return this.lastSnapId;
	}

	public void setLastSnapId(BigDecimal lastSnapId) {
		this.lastSnapId = lastSnapId;
	}

	@Column(name = "CANCELREASON", length = 200)
	public String getCancelreason() {
		return this.cancelreason;
	}

	public void setCancelreason(String cancelreason) {
		this.cancelreason = cancelreason;
	}

}