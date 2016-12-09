package com.chinadrtv.erp.model.security;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * 
 * <dl>
 *    <dt><b>Title:角色权限表</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:角色权限</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-4-9 下午2:16:20 
 *
 */
@javax.persistence.Table(name = "ROLE_PERMISSION", schema = "ACOAPP_SECURITY")
@Entity
public class RolePermission implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String create_user;
	private Date create_date;
	private Date update_date;
	private String role_id;
	/**
	 * @return the id
	 */
	@Id
	@SequenceGenerator(name = "SEQ_ROLE_PERMISSION", sequenceName = "SEQ_ROLE_PERMISSION",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ROLE_PERMISSION")
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
	/**
	 * @return the name
	 */
	@Column(name = "name")
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the update_date
	 */
	@Column(name = "update_date")
	public Date getUpdate_date() {
		return update_date;
	}
	/**
	 * @param update_date the update_date to set
	 */
	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}
	/**
	 * @return the role_id
	 */
	@Column(name = "role_id")
	public String getRole_id() {
		return role_id;
	}
	/**
	 * @param role_id the role_id to set
	 */
	public void setRole_id(String role_id) {
		this.role_id = role_id;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((role_id == null) ? 0 : role_id.hashCode());
		result = prime * result
				+ ((update_date == null) ? 0 : update_date.hashCode());
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
		RolePermission other = (RolePermission) obj;
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
		if (role_id == null) {
			if (other.role_id != null)
				return false;
		} else if (!role_id.equals(other.role_id))
			return false;
		if (update_date == null) {
			if (other.update_date != null)
				return false;
		} else if (!update_date.equals(other.update_date))
			return false;
		return true;
	}

}
