package com.chinadrtv.erp.model.agent;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
 * @since 2013-6-25 下午1:39:38 
 *
 */
@Entity
@Table(name = "OLDCONTACTEX", schema = "IAGENT")
public class OldContactex implements java.io.Serializable {

	private static final long serialVersionUID = -7403422364525801343L;
	// Fields

	private String contactid;
	private String usrid;
	private Integer orderTotal;
	private Date stime;
	private Date etime;
	private String status;
	private BigDecimal lastSnapId;
	private Integer finishTotal;
	private String cancelreason;
	private Date mddt;
	private String updatetype;

	// Constructors

	/** default constructor */
	public OldContactex() {
	}

	/** minimal constructor */
	public OldContactex(String contactid, String usrid, Integer orderTotal,
			Date stime, Date etime, String status, Integer finishTotal, Date mddt) {
		this.contactid = contactid;
		this.usrid = usrid;
		this.orderTotal = orderTotal;
		this.stime = stime;
		this.etime = etime;
		this.status = status;
		this.finishTotal = finishTotal;
		this.mddt = mddt;
	}

	/** full constructor */
	public OldContactex(String contactid, String usrid, Integer orderTotal,
			Date stime, Date etime, String status, BigDecimal lastSnapId,
			Integer finishTotal, String cancelreason, Date mddt, String updatetype) {
		this.contactid = contactid;
		this.usrid = usrid;
		this.orderTotal = orderTotal;
		this.stime = stime;
		this.etime = etime;
		this.status = status;
		this.lastSnapId = lastSnapId;
		this.finishTotal = finishTotal;
		this.cancelreason = cancelreason;
		this.mddt = mddt;
		this.updatetype = updatetype;
	}
	
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

	@Column(name = "STIME", nullable = false, length = 7)
	public Date getStime() {
		return this.stime;
	}

	public void setStime(Date stime) {
		this.stime = stime;
	}

	@Column(name = "ETIME", nullable = false, length = 7)
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

	@Column(name = "FINISH_TOTAL", nullable = false, precision = 4, scale = 0)
	public Integer getFinishTotal() {
		return this.finishTotal;
	}

	public void setFinishTotal(Integer finishTotal) {
		this.finishTotal = finishTotal;
	}

	@Column(name = "CANCELREASON", length = 200)
	public String getCancelreason() {
		return this.cancelreason;
	}

	public void setCancelreason(String cancelreason) {
		this.cancelreason = cancelreason;
	}

	@Column(name = "MDDT", nullable = false, length = 7)
	public Date getMddt() {
		return this.mddt;
	}

	public void setMddt(Date mddt) {
		this.mddt = mddt;
	}

	@Column(name = "UPDATETYPE", length = 2)
	public String getUpdatetype() {
		return this.updatetype;
	}

	public void setUpdatetype(String updatetype) {
		this.updatetype = updatetype;
	}

	/** 
	 * <p>Title: hashCode</p>
	 * <p>Description: </p>
	 * @return Integer
	 * @see java.lang.Object#hashCode()
	 */ 
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cancelreason == null) ? 0 : cancelreason.hashCode());
		result = prime * result + ((contactid == null) ? 0 : contactid.hashCode());
		result = prime * result + ((etime == null) ? 0 : etime.hashCode());
		result = prime * result + finishTotal;
		result = prime * result + ((lastSnapId == null) ? 0 : lastSnapId.hashCode());
		result = prime * result + ((mddt == null) ? 0 : mddt.hashCode());
		result = prime * result + orderTotal;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((stime == null) ? 0 : stime.hashCode());
		result = prime * result + ((updatetype == null) ? 0 : updatetype.hashCode());
		result = prime * result + ((usrid == null) ? 0 : usrid.hashCode());
		return result;
	}

	/** 
	 * <p>Title: equals</p>
	 * <p>Description: </p>
	 * @param obj
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */ 
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OldContactex other = (OldContactex) obj;
		if (cancelreason == null) {
			if (other.cancelreason != null)
				return false;
		} else if (!cancelreason.equals(other.cancelreason))
			return false;
		if (contactid == null) {
			if (other.contactid != null)
				return false;
		} else if (!contactid.equals(other.contactid))
			return false;
		if (etime == null) {
			if (other.etime != null)
				return false;
		} else if (!etime.equals(other.etime))
			return false;
		if (finishTotal != other.finishTotal)
			return false;
		if (lastSnapId == null) {
			if (other.lastSnapId != null)
				return false;
		} else if (!lastSnapId.equals(other.lastSnapId))
			return false;
		if (mddt == null) {
			if (other.mddt != null)
				return false;
		} else if (!mddt.equals(other.mddt))
			return false;
		if (orderTotal != other.orderTotal)
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (stime == null) {
			if (other.stime != null)
				return false;
		} else if (!stime.equals(other.stime))
			return false;
		if (updatetype == null) {
			if (other.updatetype != null)
				return false;
		} else if (!updatetype.equals(other.updatetype))
			return false;
		if (usrid == null) {
			if (other.usrid != null)
				return false;
		} else if (!usrid.equals(other.usrid))
			return false;
		return true;
	}

	
}