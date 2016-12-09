package com.chinadrtv.erp.model.agent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GRP", schema = "IAGENT")
public class Grp implements java.io.Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	private String grpid;
	private String grpname;
	private String grpmgr;
	private String areacode;
	private String type;
	private String ordertype;
	private String from_ex;
	private String dept;
	private String bcenter;
	private String address_flag;

	/**
	 * @return the grpid
	 */
	@Id
	@Column(name = "grpid")
	public String getGrpid() {
		return grpid;
	}

	/**
	 * @param grpid
	 *            the grpid to set
	 */
	public void setGrpid(String grpid) {
		this.grpid = grpid;
	}

	/**
	 * @return the grpname
	 */
	@Column(name = "grpname")
	public String getGrpname() {
		return grpname;
	}

	/**
	 * @param grpname
	 *            the grpname to set
	 */
	public void setGrpname(String grpname) {
		this.grpname = grpname;
	}

	/**
	 * @return the grpmgr
	 */
	@Column(name = "grpmgr")
	public String getGrpmgr() {
		return grpmgr;
	}

	/**
	 * @param grpmgr
	 *            the grpmgr to set
	 */
	public void setGrpmgr(String grpmgr) {
		this.grpmgr = grpmgr;
	}

	/**
	 * @return the areacode
	 */
	@Column(name = "areacode")
	public String getAreacode() {
		return areacode;
	}

	/**
	 * @param areacode
	 *            the areacode to set
	 */
	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	/**
	 * @return the type
	 */
	@Column(name = "type")
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the ordertype
	 */
	@Column(name = "ordertype")
	public String getOrdertype() {
		return ordertype;
	}

	/**
	 * @param ordertype
	 *            the ordertype to set
	 */
	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

	/**
	 * @return the from_ex
	 */
	@Column(name = "from_ex")
	public String getFrom_ex() {
		return from_ex;
	}

	/**
	 * @param from_ex
	 *            the from_ex to set
	 */
	public void setFrom_ex(String from_ex) {
		this.from_ex = from_ex;
	}

	/**
	 * @return the dept
	 */
	@Column(name = "dept")
	public String getDept() {
		return dept;
	}

	/**
	 * @param dept
	 *            the dept to set
	 */
	public void setDept(String dept) {
		this.dept = dept;
	}

	/**
	 * @return the bcenter
	 */
	@Column(name = "bcenter")
	public String getBcenter() {
		return bcenter;
	}

	/**
	 * @param bcenter
	 *            the bcenter to set
	 */
	public void setBcenter(String bcenter) {
		this.bcenter = bcenter;
	}

	/**
	 * @return the address_flag
	 */
	@Column(name = "address_flag")
	public String getAddress_flag() {
		return address_flag;
	}

	/**
	 * @param address_flag
	 *            the address_flag to set
	 */
	public void setAddress_flag(String address_flag) {
		this.address_flag = address_flag;
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
				+ ((address_flag == null) ? 0 : address_flag.hashCode());
		result = prime * result
				+ ((areacode == null) ? 0 : areacode.hashCode());
		result = prime * result + ((bcenter == null) ? 0 : bcenter.hashCode());
		result = prime * result + ((dept == null) ? 0 : dept.hashCode());
		result = prime * result + ((from_ex == null) ? 0 : from_ex.hashCode());
		result = prime * result + ((grpid == null) ? 0 : grpid.hashCode());
		result = prime * result + ((grpmgr == null) ? 0 : grpmgr.hashCode());
		result = prime * result + ((grpname == null) ? 0 : grpname.hashCode());
		result = prime * result
				+ ((ordertype == null) ? 0 : ordertype.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Grp other = (Grp) obj;
		if (address_flag == null) {
			if (other.address_flag != null)
				return false;
		} else if (!address_flag.equals(other.address_flag))
			return false;
		if (areacode == null) {
			if (other.areacode != null)
				return false;
		} else if (!areacode.equals(other.areacode))
			return false;
		if (bcenter == null) {
			if (other.bcenter != null)
				return false;
		} else if (!bcenter.equals(other.bcenter))
			return false;
		if (dept == null) {
			if (other.dept != null)
				return false;
		} else if (!dept.equals(other.dept))
			return false;
		if (from_ex == null) {
			if (other.from_ex != null)
				return false;
		} else if (!from_ex.equals(other.from_ex))
			return false;
		if (grpid == null) {
			if (other.grpid != null)
				return false;
		} else if (!grpid.equals(other.grpid))
			return false;
		if (grpmgr == null) {
			if (other.grpmgr != null)
				return false;
		} else if (!grpmgr.equals(other.grpmgr))
			return false;
		if (grpname == null) {
			if (other.grpname != null)
				return false;
		} else if (!grpname.equals(other.grpname))
			return false;
		if (ordertype == null) {
			if (other.ordertype != null)
				return false;
		} else if (!ordertype.equals(other.ordertype))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	
}
