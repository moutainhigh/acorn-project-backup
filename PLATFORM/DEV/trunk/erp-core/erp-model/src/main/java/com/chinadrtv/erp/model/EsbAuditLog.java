package com.chinadrtv.erp.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * User: zhaizhanyi
 * Date: 12-12-28
 */
@javax.persistence.Table(name = "ESB_AUDIT_LOG", schema = "ACOAPP_OMS")
@Entity
public class EsbAuditLog implements Serializable{

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	private Long   id   ;
	private String   esbName;
	private String   esbDesc;
	private String   errorCode;
	private String   errorDesc;
	private String   companyId;
	private String   remark;
	private Date   crtDate;
	/**
	 * @return the id
	 */
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ESB_LOG")
    @SequenceGenerator(name = "SEQ_ESB_LOG", sequenceName = "ACOAPP_OMS.SEQ_ESB_LOG")
	@Column(name="ID")
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the esbName
	 */
	@Column(name="ESB_NAME")
	public String getEsbName() {
		return esbName;
	}
	/**
	 * @param esbName the esbName to set
	 */
	public void setEsbName(String esbName) {
		this.esbName = esbName;
	}
	/**
	 * @return the esbDesc
	 */
	@Column(name="ESB_DESC")
	public String getEsbDesc() {
		return esbDesc;
	}
	/**
	 * @param esbDesc the esbDesc to set
	 */
	public void setEsbDesc(String esbDesc) {
		this.esbDesc = esbDesc;
	}
	/**
	 * @return the errorCode
	 */
	@Column(name="ERROR_CODE")
	public String getErrorCode() {
		return errorCode;
	}
	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	/**
	 * @return the errorDesc
	 */
	@Column(name="ERROR_DESC")
	public String getErrorDesc() {
		return errorDesc;
	}
	/**
	 * @param errorDesc the errorDesc to set
	 */
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	/**
	 * @return the companyId
	 */
	@Column(name="COMPANY_ID")
	public String getCompanyId() {
		return companyId;
	}
	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	/**
	 * @return the remark
	 */
	@Column(name="REMARK")
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return the crtDate
	 */
	@Column(name="CRT_DATE")
	public Date getCrtDate() {
		return crtDate;
	}
	/**
	 * @param crtDate the crtDate to set
	 */
	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}
	/* (非 Javadoc)
	* <p>Title: hashCode</p>
	* <p>Description: </p>
	* @return
	* @see java.lang.Object#hashCode()
	*/ 
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((crtDate == null) ? 0 : crtDate.hashCode());
		result = prime * result
				+ ((errorCode == null) ? 0 : errorCode.hashCode());
		result = prime * result
				+ ((errorDesc == null) ? 0 : errorDesc.hashCode());
		result = prime * result + ((esbDesc == null) ? 0 : esbDesc.hashCode());
		result = prime * result + ((esbName == null) ? 0 : esbName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		return result;
	}
	/* (非 Javadoc)
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
		EsbAuditLog other = (EsbAuditLog) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (crtDate == null) {
			if (other.crtDate != null)
				return false;
		} else if (!crtDate.equals(other.crtDate))
			return false;
		if (errorCode == null) {
			if (other.errorCode != null)
				return false;
		} else if (!errorCode.equals(other.errorCode))
			return false;
		if (errorDesc == null) {
			if (other.errorDesc != null)
				return false;
		} else if (!errorDesc.equals(other.errorDesc))
			return false;
		if (esbDesc == null) {
			if (other.esbDesc != null)
				return false;
		} else if (!esbDesc.equals(other.esbDesc))
			return false;
		if (esbName == null) {
			if (other.esbName != null)
				return false;
		} else if (!esbName.equals(other.esbName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		return true;
	}

}
