package com.chinadrtv.erp.model.marketing;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-4-17 
 * 
 */
@Table(name = "LEAD_TYPE", schema = "ACOAPP_MARKETING")
@Entity
public class LeadType  implements Serializable {

	private Long id;
	private String name;
	private String bpmDefId;
	private String addUser;
	private Date addDate;
	private String updateUser;
	private Date updateDate;
	
	private String type;

	/**
	 * @return the id
	 */
	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "generator", sequenceName = "ACOAPP_MARKETING.SEQ_LEAD_TYPE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the bpmDefId
	 */
	@Column(name = "BPM_DEF_ID")
	public String getBpmDefId() {
		return bpmDefId;
	}

	/**
	 * @param bpmDefId
	 *            the bpmDefId to set
	 */
	public void setBpmDefId(String bpmDefId) {
		this.bpmDefId = bpmDefId;
	}

	/**
	 * @return the addUser
	 */
	@Column(name = "ADD_USER")
	public String getAddUser() {
		return addUser;
	}

	/**
	 * @param addUser
	 *            the addUser to set
	 */
	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}

	/**
	 * @return the addDate
	 */
	@Column(name = "ADD_DATE")
	public Date getAddDate() {
		return addDate;
	}

	/**
	 * @param addDate
	 *            the addDate to set
	 */
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	/**
	 * @return the updateUser
	 */
	@Column(name = "UPDATE_USER")
	public String getUpdateUser() {
		return updateUser;
	}

	/**
	 * @param updateUser
	 *            the updateUser to set
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	/**
	 * @return the updateDate
	 */
	@Column(name = "UPDATE_DATE")
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate
	 *            the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the type
	 */
	@Column(name = "type")
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
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
		result = prime * result + ((addDate == null) ? 0 : addDate.hashCode());
		result = prime * result + ((addUser == null) ? 0 : addUser.hashCode());
		result = prime * result + ((bpmDefId == null) ? 0 : bpmDefId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((updateDate == null) ? 0 : updateDate.hashCode());
		result = prime * result + ((updateUser == null) ? 0 : updateUser.hashCode());
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
		LeadType other = (LeadType) obj;
		if (addDate == null) {
			if (other.addDate != null)
				return false;
		} else if (!addDate.equals(other.addDate))
			return false;
		if (addUser == null) {
			if (other.addUser != null)
				return false;
		} else if (!addUser.equals(other.addUser))
			return false;
		if (bpmDefId == null) {
			if (other.bpmDefId != null)
				return false;
		} else if (!bpmDefId.equals(other.bpmDefId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (updateDate == null) {
			if (other.updateDate != null)
				return false;
		} else if (!updateDate.equals(other.updateDate))
			return false;
		if (updateUser == null) {
			if (other.updateUser != null)
				return false;
		} else if (!updateUser.equals(other.updateUser))
			return false;
		return true;
	}

}
