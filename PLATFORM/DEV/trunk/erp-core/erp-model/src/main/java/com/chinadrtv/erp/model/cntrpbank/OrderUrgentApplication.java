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
 * @since 2013-7-10 下午3:30:58 
 *
 */
@Entity
@Table(name = "ORDERURGENTAPPLICATION", schema = "ACOAPP_CNTRPBANK")
public class OrderUrgentApplication implements java.io.Serializable {

	private static final long serialVersionUID = -2177804719880004363L;
	private Long tuid;
	private String orderid;
	private Integer status;
	private String apppsn;
	private Date appdate;
	private String appdsc;
	private String chkpsn;
	private Date chkdate;
	private String chkreason;
	private String class_;
	private String applicationreason;
	private String endpsn;
	private Date enddate;

	// Constructors

	/** default constructor */
	public OrderUrgentApplication() {
	}

	/** minimal constructor */
	public OrderUrgentApplication(String orderid, Integer status, String apppsn,
			Date appdate) {
		this.orderid = orderid;
		this.status = status;
		this.apppsn = apppsn;
		this.appdate = appdate;
	}

	/** full constructor */
	public OrderUrgentApplication(String orderid, Integer status, String apppsn,
			Date appdate, String appdsc, String chkpsn, Date chkdate,
			String chkreason, String class_, String applicationreason,
			String endpsn, Date enddate) {
		this.orderid = orderid;
		this.status = status;
		this.apppsn = apppsn;
		this.appdate = appdate;
		this.appdsc = appdsc;
		this.chkpsn = chkpsn;
		this.chkdate = chkdate;
		this.chkreason = chkreason;
		this.class_ = class_;
		this.applicationreason = applicationreason;
		this.endpsn = endpsn;
		this.enddate = enddate;
	}

	@Id
	@Column(name = "TUID", unique = true, nullable = false, precision = 10, scale = 0)
	@SequenceGenerator(name = "generator", sequenceName = "ACOAPP_CNTRPBANK.sq_orderurgentapplication", allocationSize = 1)
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

	@Column(name = "STATUS", nullable = false, length=3)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "APPPSN", nullable = false, length = 20)
	public String getApppsn() {
		return this.apppsn;
	}

	public void setApppsn(String apppsn) {
		this.apppsn = apppsn;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "APPDATE", nullable = false, length = 7)
	public Date getAppdate() {
		return this.appdate;
	}

	public void setAppdate(Date appdate) {
		this.appdate = appdate;
	}

	@Column(name = "APPDSC", length = 4000)
	public String getAppdsc() {
		return this.appdsc;
	}

	public void setAppdsc(String appdsc) {
		this.appdsc = appdsc;
	}

	@Column(name = "CHKPSN", length = 20)
	public String getChkpsn() {
		return this.chkpsn;
	}

	public void setChkpsn(String chkpsn) {
		this.chkpsn = chkpsn;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CHKDATE", length = 7)
	public Date getChkdate() {
		return this.chkdate;
	}

	public void setChkdate(Date chkdate) {
		this.chkdate = chkdate;
	}

	@Column(name = "CHKREASON", length = 4000)
	public String getChkreason() {
		return this.chkreason;
	}

	public void setChkreason(String chkreason) {
		this.chkreason = chkreason;
	}

	@Column(name = "CLASS", length = 10)
	public String getClass_() {
		return this.class_;
	}

	public void setClass_(String class_) {
		this.class_ = class_;
	}

	@Column(name = "APPLICATIONREASON", length = 400)
	public String getApplicationreason() {
		return this.applicationreason;
	}

	public void setApplicationreason(String applicationreason) {
		this.applicationreason = applicationreason;
	}

	@Column(name = "ENDPSN", length = 20)
	public String getEndpsn() {
		return this.endpsn;
	}

	public void setEndpsn(String endpsn) {
		this.endpsn = endpsn;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ENDDATE", length = 7)
	public Date getEnddate() {
		return this.enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
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
		result = prime * result + ((appdate == null) ? 0 : appdate.hashCode());
		result = prime * result + ((appdsc == null) ? 0 : appdsc.hashCode());
		result = prime * result + ((applicationreason == null) ? 0 : applicationreason.hashCode());
		result = prime * result + ((apppsn == null) ? 0 : apppsn.hashCode());
		result = prime * result + ((chkdate == null) ? 0 : chkdate.hashCode());
		result = prime * result + ((chkpsn == null) ? 0 : chkpsn.hashCode());
		result = prime * result + ((chkreason == null) ? 0 : chkreason.hashCode());
		result = prime * result + ((class_ == null) ? 0 : class_.hashCode());
		result = prime * result + ((enddate == null) ? 0 : enddate.hashCode());
		result = prime * result + ((endpsn == null) ? 0 : endpsn.hashCode());
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
		OrderUrgentApplication other = (OrderUrgentApplication) obj;
		if (appdate == null) {
			if (other.appdate != null)
				return false;
		} else if (!appdate.equals(other.appdate))
			return false;
		if (appdsc == null) {
			if (other.appdsc != null)
				return false;
		} else if (!appdsc.equals(other.appdsc))
			return false;
		if (applicationreason == null) {
			if (other.applicationreason != null)
				return false;
		} else if (!applicationreason.equals(other.applicationreason))
			return false;
		if (apppsn == null) {
			if (other.apppsn != null)
				return false;
		} else if (!apppsn.equals(other.apppsn))
			return false;
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
		if (chkreason == null) {
			if (other.chkreason != null)
				return false;
		} else if (!chkreason.equals(other.chkreason))
			return false;
		if (class_ == null) {
			if (other.class_ != null)
				return false;
		} else if (!class_.equals(other.class_))
			return false;
		if (enddate == null) {
			if (other.enddate != null)
				return false;
		} else if (!enddate.equals(other.enddate))
			return false;
		if (endpsn == null) {
			if (other.endpsn != null)
				return false;
		} else if (!endpsn.equals(other.endpsn))
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