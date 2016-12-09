package com.chinadrtv.erp.model.security;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/**
 * 
 * <dl>
 * <dt><b>Title:控件权限</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:控件权限</b></dt>
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
@javax.persistence.Table(name = "WIDGET_PERMISSION", schema = "ACOAPP_SECURITY")
@Entity
public class WidgetPermission implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
//	private Long data_view_permission_id;
	private Long widget_id;
	private Integer is_visible;
	private Integer is_enable;
	private String create_user;
	private Date create_date;
	private String update_user;
	private Date update_date;

	private DataViewPermission dataViewPermission;
	/**
	 * @return the id
	 */
	@Id
	@SequenceGenerator(name = "SEQ_WIDGET_PERMISSION", sequenceName = "SEQ_WIDGET_PERMISSION",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WIDGET_PERMISSION")
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

//	/**
//	 * @return the data_view_permission_id
//	 */
//	@Column(name = "data_view_permission_id")
//	public Long getData_view_permission_id() {
//		return data_view_permission_id;
//	}
//
//	/**
//	 * @param data_view_permission_id
//	 *            the data_view_permission_id to set
//	 */
//	public void setData_view_permission_id(Long data_view_permission_id) {
//		this.data_view_permission_id = data_view_permission_id;
//	}

	/**
	 * @return the widget_id
	 */
	@Column(name = "widget_id")
	public Long getWidget_id() {
		return widget_id;
	}

	/**
	 * @param widget_id
	 *            the widget_id to set
	 */
	public void setWidget_id(Long widget_id) {
		this.widget_id = widget_id;
	}

	/**
	 * @return the is_visible
	 */
	@Column(name = "is_visible")
	public Integer getIs_visible() {
		return is_visible;
	}

	/**
	 * @param is_visible
	 *            the is_visible to set
	 */
	public void setIs_visible(Integer is_visible) {
		this.is_visible = is_visible;
	}

	/**
	 * @return the is_enable
	 */
	@Column(name = "is_enable")
	public Integer getIs_enable() {
		return is_enable;
	}

	/**
	 * @param is_enable
	 *            the is_enable to set
	 */
	public void setIs_enable(Integer is_enable) {
		this.is_enable = is_enable;
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

	/**
	 * @return the dataViewPermission
	 */
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
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
				+ ((create_date == null) ? 0 : create_date.hashCode());
		result = prime * result
				+ ((create_user == null) ? 0 : create_user.hashCode());
		result = prime
				* result
				+ ((dataViewPermission == null) ? 0 : dataViewPermission
						.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((is_enable == null) ? 0 : is_enable.hashCode());
		result = prime * result
				+ ((is_visible == null) ? 0 : is_visible.hashCode());
		result = prime * result
				+ ((update_date == null) ? 0 : update_date.hashCode());
		result = prime * result
				+ ((update_user == null) ? 0 : update_user.hashCode());
		result = prime * result
				+ ((widget_id == null) ? 0 : widget_id.hashCode());
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
		WidgetPermission other = (WidgetPermission) obj;
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (is_enable == null) {
			if (other.is_enable != null)
				return false;
		} else if (!is_enable.equals(other.is_enable))
			return false;
		if (is_visible == null) {
			if (other.is_visible != null)
				return false;
		} else if (!is_visible.equals(other.is_visible))
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
		if (widget_id == null) {
			if (other.widget_id != null)
				return false;
		} else if (!widget_id.equals(other.widget_id))
			return false;
		return true;
	}

	

}
