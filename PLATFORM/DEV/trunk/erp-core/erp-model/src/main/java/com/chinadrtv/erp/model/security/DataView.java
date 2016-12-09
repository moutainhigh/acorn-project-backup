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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

/**
 * 
 * <dl>
 *    <dt><b>Title:数据视图</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:数据视图，对应一个前台访问url页面</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-4-9 下午2:17:15 
 *
 */
@javax.persistence.Table(name = "DATA_VIEW", schema = "ACOAPP_SECURITY")
@Entity
public class DataView implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String url;
	private Integer is_page;
	private String entity_name;
	private String entity_alias;
	private String comments;
	private String create_user;
	private Date create_date;
	private String update_user;
	private Date update_date;
	private Long domain_id;
	private Domain domain;

	private Filter filter;
	/**
	 * @return the id
	 */
	@Id
    @SequenceGenerator(name = "SEQ_DATA_VIEW", sequenceName = "SEQ_DATA_VIEW",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DATA_VIEW")
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
	 * @return the url
	 */
	@Column(name = "url")
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the is_page
	 */
	@Column(name = "is_page")
	public Integer getIs_page() {
		return is_page;
	}

	/**
	 * @param is_page
	 *            the is_page to set
	 */
	public void setIs_page(Integer is_page) {
		this.is_page = is_page;
	}

	/**
	 * @return the entity_name
	 */
	@Column(name = "entity_name")
	public String getEntity_name() {
		return entity_name;
	}

	/**
	 * @param entity_name
	 *            the entity_name to set
	 */
	public void setEntity_name(String entity_name) {
		this.entity_name = entity_name;
	}

	/**
	 * @return the entity_alias
	 */
	@Column(name = "entity_alias")
	public String getEntity_alias() {
		return entity_alias;
	}

	/**
	 * @param entity_alias
	 *            the entity_alias to set
	 */
	public void setEntity_alias(String entity_alias) {
		this.entity_alias = entity_alias;
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
	

	/**
	 * @return the filters
	 */
	@OneToOne(mappedBy="dataView")
	public Filter getFilter() {
		return filter;
	}

	/**
	 * @param filters the filters to set
	 */
	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	
	/**
	 * @return the domain_id
	 */
	@Column(name = "domain_id")
	public Long getDomain_id() {
		return domain_id;
	}

	/**
	 * @param domain_id the domain_id to set
	 */
	public void setDomain_id(Long domain_id) {
		this.domain_id = domain_id;
	}

	/**
	 * @return the domain
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOMAIN_ID",referencedColumnName="ID",insertable=false,updatable=false)
	public Domain getDomain() {
		return domain;
	}

	/**
	 * @param domain the domain to set
	 */
	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	/*
	 * (非 Javadoc) <p>Title: hashCode</p> <p>Description: </p>
	 * 
	 * @return
	 * 
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
				+ ((entity_alias == null) ? 0 : entity_alias.hashCode());
		result = prime * result
				+ ((entity_name == null) ? 0 : entity_name.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((is_page == null) ? 0 : is_page.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((update_date == null) ? 0 : update_date.hashCode());
		result = prime * result
				+ ((update_user == null) ? 0 : update_user.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	/*
	 * (非 Javadoc) <p>Title: equals</p> <p>Description: </p>
	 * 
	 * @param obj
	 * 
	 * @return
	 * 
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
		DataView other = (DataView) obj;
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
		if (entity_alias == null) {
			if (other.entity_alias != null)
				return false;
		} else if (!entity_alias.equals(other.entity_alias))
			return false;
		if (entity_name == null) {
			if (other.entity_name != null)
				return false;
		} else if (!entity_name.equals(other.entity_name))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (is_page == null) {
			if (other.is_page != null)
				return false;
		} else if (!is_page.equals(other.is_page))
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
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

}
