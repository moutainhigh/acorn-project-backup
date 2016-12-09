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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * 
 * <dl>
 * <dt><b>Title:视图——字段权限</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:视图——字段权限</b></dt>
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
@javax.persistence.Table(name = "FIELD_PERMISSION", schema = "ACOAPP_SECURITY")
@Entity
public class FieldPermission implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
//	private Long data_view_permission_id;
	private Long view_field_id;
	private String regex_replace;
	private String replace_char;
	private Integer is_visible;
	private Integer is_editable;
	private Date create_date;
	private String update_user;
	private Date update_date;

	private DataViewPermission dataViewPermission;
	private ViewField field;
	
	private String fieldName;
	/**
	 * @return the id
	 */
	@Id
	@SequenceGenerator(name = "SEQ_FIELD_PERMISSION", sequenceName = "SEQ_FIELD_PERMISSION",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FIELD_PERMISSION")
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
	 * @return the view_field_id
	 */
	@Column(name = "view_field_id")
	public Long getView_field_id() {
		return view_field_id;
	}

	/**
	 * @param view_field_id
	 *            the view_field_id to set
	 */
	public void setView_field_id(Long view_field_id) {
		this.view_field_id = view_field_id;
	}

	
	/**
	 * @return the regex_replace
	 */
	@Column(name = "regex_replace")
	public String getRegex_replace() {
		return regex_replace;
	}

	/**
	 * @param regex_replace
	 *            the regex_replace to set
	 */
	public void setRegex_replace(String regex_replace) {
		this.regex_replace = regex_replace;
	}

	
	/**
	 * @return the replace_char
	 */
	@Column(name = "replace_char")
	public String getReplace_char() {
		return replace_char;
	}

	/**
	 * @param replace_char the replace_char to set
	 */
	public void setReplace_char(String replace_char) {
		this.replace_char = replace_char;
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
	 * @return the is_editable
	 */
	@Column(name = "is_editable")
	public Integer getIs_editable() {
		return is_editable;
	}

	/**
	 * @param is_editable
	 *            the is_editable to set
	 */
	public void setIs_editable(Integer is_editable) {
		this.is_editable = is_editable;
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

	
	/**
	 * @return the field
	 */
	@OneToOne(optional = false, fetch=FetchType.LAZY)  
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "VIEW_FIELD_ID", referencedColumnName = "ID", unique = true,insertable=false,updatable=false)
	public ViewField getField() {
		return field;
	}

	/**
	 * @param field the field to set
	 */
	public void setField(ViewField field) {
		this.field = field;
	}

	/**
	 * @return the fieldName
	 */
	@Transient
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
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
		result = prime
				* result
				+ ((dataViewPermission == null) ? 0 : dataViewPermission
						.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((is_editable == null) ? 0 : is_editable.hashCode());
		result = prime * result
				+ ((is_visible == null) ? 0 : is_visible.hashCode());
		result = prime * result
				+ ((regex_replace == null) ? 0 : regex_replace.hashCode());
		result = prime * result
				+ ((update_date == null) ? 0 : update_date.hashCode());
		result = prime * result
				+ ((update_user == null) ? 0 : update_user.hashCode());
		result = prime * result
				+ ((view_field_id == null) ? 0 : view_field_id.hashCode());
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
		FieldPermission other = (FieldPermission) obj;
		if (create_date == null) {
			if (other.create_date != null)
				return false;
		} else if (!create_date.equals(other.create_date))
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
		if (is_editable == null) {
			if (other.is_editable != null)
				return false;
		} else if (!is_editable.equals(other.is_editable))
			return false;
		if (is_visible == null) {
			if (other.is_visible != null)
				return false;
		} else if (!is_visible.equals(other.is_visible))
			return false;
		if (regex_replace == null) {
			if (other.regex_replace != null)
				return false;
		} else if (!regex_replace.equals(other.regex_replace))
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
		if (view_field_id == null) {
			if (other.view_field_id != null)
				return false;
		} else if (!view_field_id.equals(other.view_field_id))
			return false;
		return true;
	}
	

}
