package com.chinadrtv.erp.model.security;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/**
 * 
 * <dl>
 * <dt><b>Title:过滤参数值</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:过滤参数值</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-4-9 下午2:17:15
 * 
 */
@javax.persistence.Table(name = "FILTER_VALUE", schema = "ACOAPP_SECURITY")
@Entity
public class FilterValue implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String value;
	private String type;
//	private Long filter_permission_id;
	private String create_user;
	private Date create_date;
	private String update_user;
	private Date update_date;

	private FilterPermission filterPermission;
	
	/**
	 * @return the id
	 */
	@Id
	@SequenceGenerator(name = "SEQ_FILTER_VALUE", sequenceName = "SEQ_FILTER_VALUE",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FILTER_VALUE")
	@Column(name = "id")
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
	 @Column(name = "name")
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
	 * @return the value
	 */
	 @Column(name = "value")
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
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

//	/**
//	 * @return the filter_permission_id
//	 */
//	// @Column(name = "filter_permission_id")
//	public Long getFilter_permission_id() {
//		return filter_permission_id;
//	}
//
//	/**
//	 * @param filter_permission_id
//	 *            the filter_permission_id to set
//	 */
//	public void setFilter_permission_id(Long filter_permission_id) {
//		this.filter_permission_id = filter_permission_id;
//	}

	/**
	 * @return the create_user
	 */
	 @Column(name = "create_user")
	public String getCreate_user() {
		return create_user;
	}

	/**
	 * @param create_user
	 *            the create_user to set
	 */
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	/**
	 * @return the create_date
	 */
	 @Column(name = "create_date")
	public Date getCreate_date() {
		return create_date;
	}

	/**
	 * @param create_date
	 *            the create_date to set
	 */
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	/**
	 * @return the update_user
	 */
	 @Column(name = "update_user")
	public String getUpdate_user() {
		return update_user;
	}

	/**
	 * @param update_user
	 *            the update_user to set
	 */
	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}

	/**
	 * @return the update_date
	 */
	 @Column(name = "update_date")
	public Date getUpdate_date() {
		return update_date;
	}

	/**
	 * @param update_date
	 *            the update_date to set
	 */
	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	/**
	 * @return the filterPermission
	 */
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "FILTER_PERMISSION_ID",unique=true,insertable = false,updatable=false)
	public FilterPermission getFilterPermission() {
		return filterPermission;
	}

	/**
	 * @param filterPermission the filterPermission to set
	 */
	public void setFilterPermission(FilterPermission filterPermission) {
		this.filterPermission = filterPermission;
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
		result = prime * result + ((create_date == null) ? 0 : create_date.hashCode());
		result = prime * result + ((create_user == null) ? 0 : create_user.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((update_date == null) ? 0 : update_date.hashCode());
		result = prime * result + ((update_user == null) ? 0 : update_user.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		FilterValue other = (FilterValue) obj;
		if (create_date == null) {
			if (other.create_date != null)
				return false;
		} else if (!create_date.equals(other.create_date))
			return false;
		if (create_user == null) {
			if (other.create_user != null)
				return false;
		} else if (!create_user.equals(other.create_user))
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
		if (update_date == null) {
			if (other.update_date != null)
				return false;
		} else if (!update_date.equals(other.update_date))
			return false;
		if (update_user == null) {
			if (other.update_user != null)
				return false;
		} else if (!update_user.equals(other.update_user))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	

	
}
