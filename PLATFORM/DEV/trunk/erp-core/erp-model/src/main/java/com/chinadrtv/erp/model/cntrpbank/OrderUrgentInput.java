package com.chinadrtv.erp.model.cntrpbank;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
 * @since 2013-7-10 下午3:30:52 
 *
 */
@Entity
@Table(name = "ORDERURGENTINPUT", schema = "ACOAPP_CNTRPBANK")
public class OrderUrgentInput implements java.io.Serializable {

	private static final long serialVersionUID = -6480858114821067557L;
	private Long tuid;
	private String orderid;
	private String enddsc;
	private Short errortype;
	private String errorname;
	private Short status;
	private String inpsn;
	private Date indate;
	private String chkpsn;
	private Date chkdate;


	/** default constructor */
	public OrderUrgentInput() {
	}

	/** minimal constructor */
	public OrderUrgentInput(String orderid, Short errortype, Short status) {
		this.orderid = orderid;
		this.errortype = errortype;
		this.status = status;
	}

	/** full constructor */
	public OrderUrgentInput(String orderid, String enddsc, Short errortype,
			String errorname, Short status, String inpsn, Date indate,
			String chkpsn, Date chkdate) {
		this.orderid = orderid;
		this.enddsc = enddsc;
		this.errortype = errortype;
		this.errorname = errorname;
		this.status = status;
		this.inpsn = inpsn;
		this.indate = indate;
		this.chkpsn = chkpsn;
		this.chkdate = chkdate;
	}

	@Id
	@Column(name = "TUID", unique = true, nullable = false, precision = 10, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "ACOAPP_CNTRPBANK.sq_orderurgentinput", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	public Long getTuid() {
		return this.tuid;
	}

	public void setTuid(Long tuid) {
		this.tuid = tuid;
	}

	@Column(name = "ORDERID", nullable = false, length = 20)
	public String getOrderid() {
		return this.orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	@Column(name = "ENDDSC", length = 4000)
	public String getEnddsc() {
		return this.enddsc;
	}

	public void setEnddsc(String enddsc) {
		this.enddsc = enddsc;
	}

	@Column(name = "ERRORTYPE", nullable = false, length=1)
	public Short getErrortype() {
		return this.errortype;
	}

	public void setErrortype(Short errortype) {
		this.errortype = errortype;
	}

	@Column(name = "ERRORNAME", length = 400)
	public String getErrorname() {
		return this.errorname;
	}

	public void setErrorname(String errorname) {
		this.errorname = errorname;
	}

	@Column(name = "STATUS", nullable = false, length=1)
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "INPSN", length = 20)
	public String getInpsn() {
		return this.inpsn;
	}

	public void setInpsn(String inpsn) {
		this.inpsn = inpsn;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "INDATE", length = 7)
	public Date getIndate() {
		return this.indate;
	}

	public void setIndate(Date indate) {
		this.indate = indate;
	}

	@Column(name = "CHKPSN", length = 20)
	public String getChkpsn() {
		return this.chkpsn;
	}

	public void setChkpsn(String chkpsn) {
		this.chkpsn = chkpsn;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CHKDATE", length = 7)
	public Date getChkdate() {
		return this.chkdate;
	}

	public void setChkdate(Date chkdate) {
		this.chkdate = chkdate;
	}

	/** 
	 * <p>Title: hashCode</p>
	 * <p>Description: </p>
	 * @return
	 * @see java.lang.Object#hashCode()
	 */ 
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chkdate == null) ? 0 : chkdate.hashCode());
		result = prime * result + ((chkpsn == null) ? 0 : chkpsn.hashCode());
		result = prime * result + ((enddsc == null) ? 0 : enddsc.hashCode());
		result = prime * result + ((errorname == null) ? 0 : errorname.hashCode());
		result = prime * result + ((errortype == null) ? 0 : errortype.hashCode());
		result = prime * result + ((indate == null) ? 0 : indate.hashCode());
		result = prime * result + ((inpsn == null) ? 0 : inpsn.hashCode());
		result = prime * result + ((orderid == null) ? 0 : orderid.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((tuid == null) ? 0 : tuid.hashCode());
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
		OrderUrgentInput other = (OrderUrgentInput) obj;
		if (chkdate == null) {
			if (other.chkdate != null)
				return false;
		} else if (!chkdate.equals(other.chkdate))
			return false;
		if (chkpsn == null) {
			if (other.chkpsn != null)
				return false;
		} else if (!chkpsn.equals(other.chkpsn))
			return false;
		if (enddsc == null) {
			if (other.enddsc != null)
				return false;
		} else if (!enddsc.equals(other.enddsc))
			return false;
		if (errorname == null) {
			if (other.errorname != null)
				return false;
		} else if (!errorname.equals(other.errorname))
			return false;
		if (errortype == null) {
			if (other.errortype != null)
				return false;
		} else if (!errortype.equals(other.errortype))
			return false;
		if (indate == null) {
			if (other.indate != null)
				return false;
		} else if (!indate.equals(other.indate))
			return false;
		if (inpsn == null) {
			if (other.inpsn != null)
				return false;
		} else if (!inpsn.equals(other.inpsn))
			return false;
		if (orderid == null) {
			if (other.orderid != null)
				return false;
		} else if (!orderid.equals(other.orderid))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (tuid == null) {
			if (other.tuid != null)
				return false;
		} else if (!tuid.equals(other.tuid))
			return false;
		return true;
	}

}