package com.chinadrtv.erp.model.security;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * 
 * <dl>
 * <dt><b>Title:过滤权限</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:过滤权限</b></dt>
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
@javax.persistence.Table(name = "FILTER_PERMISSION", schema = "ACOAPP_SECURITY")
@Entity
public class FilterPermission implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	private Long  id;
//	private Long  data_view_permission_id ;
	private Long  data_view_filter_id ;
	private String  create_user ;
	private Date  create_date ;
	
	
	private DataViewPermission dataViewPermission;
	private Filter filter;
	
	 private Set<FilterValue> filterValues;
	
	/**
	 * @return the id
	 */
	@Id
	@SequenceGenerator(name = "SEQ_FILTER_PERMISSION", sequenceName = "SEQ_FILTER_PERMISSION",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FILTER_PERMISSION")
	@Column(name = "id")
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
//	/**
//	 * @return the data_view_permission_id
//	 */
//	@Column(name = "data_view_permission_id")
//	public Long getData_view_permission_id() {
//		return data_view_permission_id;
//	}
//	/**
//	 * @param data_view_permission_id the data_view_permission_id to set
//	 */
//	public void setData_view_permission_id(Long data_view_permission_id) {
//		this.data_view_permission_id = data_view_permission_id;
//	}
	/**
	 * @return the data_view_filter_id
	 */
	@Column(name = "data_view_filter_id")
	public Long getData_view_filter_id() {
		return data_view_filter_id;
	}
	/**
	 * @param data_view_filter_id the data_view_filter_id to set
	 */
	public void setData_view_filter_id(Long data_view_filter_id) {
		this.data_view_filter_id = data_view_filter_id;
	}
	/**
	 * @return the create_user
	 */
	@Column(name = "create_user")
	public String getCreate_user() {
		return create_user;
	}
	/**
	 * @param create_user the create_user to set
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
	 * @param create_date the create_date to set
	 */
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	
	/**
	 * @return the dataViewPermission
	 */
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)  
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "data_view_permission_id")
	public DataViewPermission getDataViewPermission() {
		return dataViewPermission;
	}

	/**
	 * @param dataViewPermission the dataViewPermission to set
	 */
	public void setDataViewPermission(DataViewPermission dataViewPermission) {
		this.dataViewPermission = dataViewPermission;
	}
	
	
	
	/**
	 * @return the filter
	 */
	@OneToOne(optional = false, fetch=FetchType.LAZY)  
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "DATA_VIEW_FILTER_ID", referencedColumnName="ID", unique = true,insertable=false,updatable=false)
	public Filter getFilter() {
		return filter;
	}
	/**
	 * @param filter the filter to set
	 */
	public void setFilter(Filter filter) {
		this.filter = filter;
	}
	
	
	/**
	 * @return the filterValues
	 */
	@OneToMany(mappedBy = "filterPermission",fetch=FetchType.EAGER)
	public Set<FilterValue> getFilterValues() {
		return filterValues;
	}
	/**
	 * @param filterValues the filterValues to set
	 */
	public void setFilterValues(Set<FilterValue> filterValues) {
		this.filterValues = filterValues;
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
		result = prime * result
				+ ((dataViewPermission == null) ? 0 : dataViewPermission.hashCode());
		result = prime * result
				+ ((data_view_filter_id == null) ? 0 : data_view_filter_id.hashCode());
		result = prime * result + ((filterValues == null) ? 0 : filterValues.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		FilterPermission other = (FilterPermission) obj;
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
		if (dataViewPermission == null) {
			if (other.dataViewPermission != null)
				return false;
		} else if (!dataViewPermission.equals(other.dataViewPermission))
			return false;
		if (data_view_filter_id == null) {
			if (other.data_view_filter_id != null)
				return false;
		} else if (!data_view_filter_id.equals(other.data_view_filter_id))
			return false;
		if (filterValues == null) {
			if (other.filterValues != null)
				return false;
		} else if (!filterValues.equals(other.filterValues))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	} 
	
	
}
