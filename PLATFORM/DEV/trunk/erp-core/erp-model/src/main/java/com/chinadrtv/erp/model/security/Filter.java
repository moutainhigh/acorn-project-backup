package com.chinadrtv.erp.model.security;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * 
 * <dl>
 * <dt><b>Title:过滤</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:过滤</b></dt>
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
@javax.persistence.Table(name = "FILTER", schema = "ACOAPP_SECURITY")
@Entity
public class Filter implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private Long data_view_id;
	private String where_clause;
	private String comments;
	private String create_user;
	private Date create_date;
	private String update_user;
	private Date update_date;

	
	private DataView dataView;
//	private FilterPermission filterPermission;
	/**
	 * @return the id
	 */
	@Id
	@SequenceGenerator(name = "SEQ_FILTER", sequenceName = "SEQ_FILTER",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FILTER")
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
	 * @return the data_view_id
	 */
	@Column(name = "data_view_id")
	public Long getData_view_id() {
		return data_view_id;
	}

	/**
	 * @param data_view_id
	 *            the data_view_id to set
	 */
	public void setData_view_id(Long data_view_id) {
		this.data_view_id = data_view_id;
	}

	/**
	 * @return the dataView
	 */
	@OneToOne(optional = false, fetch=FetchType.LAZY)  
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "DATA_VIEW_ID", insertable=false,updatable=false)
	public DataView getDataView() {
		return dataView;
	}

	/**
	 * @param dataView the dataView to set
	 */
	public void setDataView(DataView dataView) {
		this.dataView = dataView;
	}

	/**
	 * @return the where_clause
	 */
	@Column(name = "where_clause")
	public String getWhere_clause() {
		return where_clause;
	}

	
	/**
	 * @param where_clause
	 *            the where_clause to set
	 */
	public void setWhere_clause(String where_clause) {
		this.where_clause = where_clause;
	}

	/**
	 * @return the comments
	 */
	@Column(name = "comments")
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments
	 *            the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

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

	
//	/**
//	 * @return the filterPermission
//	 */
//	@OneToOne(mappedBy="filter")
//	public FilterPermission getFilterPermission() {
//		return filterPermission;
//	}
//
//	/**
//	 * @param filterPermission the filterPermission to set
//	 */
//	public void setFilterPermission(FilterPermission filterPermission) {
//		this.filterPermission = filterPermission;
//	}

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
				+ ((comments == null) ? 0 : comments.hashCode());
		result = prime * result
				+ ((create_date == null) ? 0 : create_date.hashCode());
		result = prime * result
				+ ((create_user == null) ? 0 : create_user.hashCode());
		result = prime * result
				+ ((dataView == null) ? 0 : dataView.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((update_date == null) ? 0 : update_date.hashCode());
		result = prime * result
				+ ((update_user == null) ? 0 : update_user.hashCode());
		result = prime * result
				+ ((where_clause == null) ? 0 : where_clause.hashCode());
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
		Filter other = (Filter) obj;
		if (comments == null) {
			if (other.comments != null)
				return false;
		} else if (!comments.equals(other.comments))
			return false;
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
		if (dataView == null) {
			if (other.dataView != null)
				return false;
		} else if (!dataView.equals(other.dataView))
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
		if (where_clause == null) {
			if (other.where_clause != null)
				return false;
		} else if (!where_clause.equals(other.where_clause))
			return false;
		return true;
	}

	
}
