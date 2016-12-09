/*
 * @(#)CustomerSqlSource.java 1.0 2013-1-21下午2:09:48
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.model.marketing;

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
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.chinadrtv.erp.model.agent.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * 客户群管理——推荐产品规则配置</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午2:09:48
 * 
 */
@Table(name = "CONF_RECOMMEND", schema = "ACOAPP_MARKETING")
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class RecommendConf {

	private Long id;
	private String groupid;
	private String product1;
	private String product2;
	private String product3;
	private Date valid_start;
	private Date valid_end;
	private String crt_user;
	private Date crt_date;
	private String up_user;
	private Date up_date;
	private String status;
	private String process_defid;

	private CustomerGroup group;
	private Product prod1;
	private Product prod2;
	private Product prod3;

	/**
	 * @return the id
	 */
	@Id
	@SequenceGenerator(name = "SEQ_RECOMMEND", sequenceName = "ACOAPP_MARKETING.SEQ_RECOMMEND")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RECOMMEND")
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
	 * @return the groupid
	 */
	@Column(name = "groupid")
	public String getGroupid() {
		return groupid;
	}

	/**
	 * @param groupid
	 *            the groupid to set
	 */
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	/**
	 * @return the product1
	 */
	@Column(name = "product1")
	public String getProduct1() {
		return product1;
	}

	/**
	 * @param product1
	 *            the product1 to set
	 */
	public void setProduct1(String product1) {
		this.product1 = product1;
	}

	/**
	 * @return the product2
	 */
	@Column(name = "product2")
	public String getProduct2() {
		return product2;
	}

	/**
	 * @param product2
	 *            the product2 to set
	 */
	public void setProduct2(String product2) {
		this.product2 = product2;
	}

	/**
	 * @return the product3
	 */
	@Column(name = "product3")
	public String getProduct3() {
		return product3;
	}

	/**
	 * @param product3
	 *            the product3 to set
	 */
	public void setProduct3(String product3) {
		this.product3 = product3;
	}

	/**
	 * @return the valid_start
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(name = "valid_start")
	public Date getValid_start() {
		return valid_start;
	}

	/**
	 * @param valid_start
	 *            the valid_start to set
	 */
	public void setValid_start(Date valid_start) {
		this.valid_start = valid_start;
	}

	/**
	 * @return the valid_end
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(name = "valid_end")
	public Date getValid_end() {
		return valid_end;
	}

	/**
	 * @param valid_end
	 *            the valid_end to set
	 */
	public void setValid_end(Date valid_end) {
		this.valid_end = valid_end;
	}

	/**
	 * @return the crt_user
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(name = "crt_user")
	public String getCrt_user() {
		return crt_user;
	}

	/**
	 * @param crt_user
	 *            the crt_user to set
	 */
	public void setCrt_user(String crt_user) {
		this.crt_user = crt_user;
	}

	/**
	 * @return the crt_date
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(name = "crt_date")
	public Date getCrt_date() {
		return crt_date;
	}

	/**
	 * @param crt_date
	 *            the crt_date to set
	 */
	public void setCrt_date(Date crt_date) {
		this.crt_date = crt_date;
	}

	/**
	 * @return the status
	 */
	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the process_defid
	 */
	@Column(name = "process_defid")
	public String getProcess_defid() {
		return process_defid;
	}

	/**
	 * @param process_defid
	 *            the process_defid to set
	 */
	public void setProcess_defid(String process_defid) {
		this.process_defid = process_defid;
	}

	/**
	 * @return the up_user
	 */
	@Column(name = "up_user")
	public String getUp_user() {
		return up_user;
	}

	/**
	 * @param up_user
	 *            the up_user to set
	 */
	public void setUp_user(String up_user) {
		this.up_user = up_user;
	}

	/**
	 * @return the up_date
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(name = "up_date")
	public Date getUp_date() {
		return up_date;
	}

	/**
	 * @param up_date
	 *            the up_date to set
	 */
	public void setUp_date(Date up_date) {
		this.up_date = up_date;
	}

	/**
	 * @return the group
	 */
	@OneToOne(optional = false, fetch = FetchType.LAZY)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "groupid", referencedColumnName = "group_Code", unique = true, insertable = false, updatable = false)
	public CustomerGroup getGroup() {
		return group;
	}

	/**
	 * @param group
	 *            the group to set
	 */
	public void setGroup(CustomerGroup group) {
		this.group = group;
	}

	/**
	 * @return the prod1
	 */
	@OneToOne(optional = false, fetch = FetchType.LAZY)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "product1", referencedColumnName = "PRODID", unique = true, insertable = false, updatable = false)
	public Product getProd1() {
		return prod1;
	}

	/**
	 * @param prod1
	 *            the prod1 to set
	 */
	public void setProd1(Product prod1) {
		this.prod1 = prod1;
	}

	/**
	 * @return the prod2
	 */
	@OneToOne(optional = false, fetch = FetchType.LAZY)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "product2", referencedColumnName = "PRODID", unique = true, insertable = false, updatable = false)
	public Product getProd2() {
		return prod2;
	}

	/**
	 * @param prod2
	 *            the prod2 to set
	 */
	public void setProd2(Product prod2) {
		this.prod2 = prod2;
	}

	/**
	 * @return the prod3
	 */
	@OneToOne(optional = false, fetch = FetchType.LAZY)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "product3", referencedColumnName = "PRODID", unique = true, insertable = false, updatable = false)
	public Product getProd3() {
		return prod3;
	}

	/**
	 * @param prod3
	 *            the prod3 to set
	 */
	public void setProd3(Product prod3) {
		this.prod3 = prod3;
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
				+ ((crt_date == null) ? 0 : crt_date.hashCode());
		result = prime * result
				+ ((crt_user == null) ? 0 : crt_user.hashCode());
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + ((groupid == null) ? 0 : groupid.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((process_defid == null) ? 0 : process_defid.hashCode());
		result = prime * result + ((prod1 == null) ? 0 : prod1.hashCode());
		result = prime * result + ((prod2 == null) ? 0 : prod2.hashCode());
		result = prime * result + ((prod3 == null) ? 0 : prod3.hashCode());
		result = prime * result
				+ ((product1 == null) ? 0 : product1.hashCode());
		result = prime * result
				+ ((product2 == null) ? 0 : product2.hashCode());
		result = prime * result
				+ ((product3 == null) ? 0 : product3.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((up_date == null) ? 0 : up_date.hashCode());
		result = prime * result + ((up_user == null) ? 0 : up_user.hashCode());
		result = prime * result
				+ ((valid_end == null) ? 0 : valid_end.hashCode());
		result = prime * result
				+ ((valid_start == null) ? 0 : valid_start.hashCode());
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
		RecommendConf other = (RecommendConf) obj;
		if (crt_date == null) {
			if (other.crt_date != null)
				return false;
		} else if (!crt_date.equals(other.crt_date))
			return false;
		if (crt_user == null) {
			if (other.crt_user != null)
				return false;
		} else if (!crt_user.equals(other.crt_user))
			return false;
		if (group == null) {
			if (other.group != null)
				return false;
		} else if (!group.equals(other.group))
			return false;
		if (groupid == null) {
			if (other.groupid != null)
				return false;
		} else if (!groupid.equals(other.groupid))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (process_defid == null) {
			if (other.process_defid != null)
				return false;
		} else if (!process_defid.equals(other.process_defid))
			return false;
		if (prod1 == null) {
			if (other.prod1 != null)
				return false;
		} else if (!prod1.equals(other.prod1))
			return false;
		if (prod2 == null) {
			if (other.prod2 != null)
				return false;
		} else if (!prod2.equals(other.prod2))
			return false;
		if (prod3 == null) {
			if (other.prod3 != null)
				return false;
		} else if (!prod3.equals(other.prod3))
			return false;
		if (product1 == null) {
			if (other.product1 != null)
				return false;
		} else if (!product1.equals(other.product1))
			return false;
		if (product2 == null) {
			if (other.product2 != null)
				return false;
		} else if (!product2.equals(other.product2))
			return false;
		if (product3 == null) {
			if (other.product3 != null)
				return false;
		} else if (!product3.equals(other.product3))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (up_date == null) {
			if (other.up_date != null)
				return false;
		} else if (!up_date.equals(other.up_date))
			return false;
		if (up_user == null) {
			if (other.up_user != null)
				return false;
		} else if (!up_user.equals(other.up_user))
			return false;
		if (valid_end == null) {
			if (other.valid_end != null)
				return false;
		} else if (!valid_end.equals(other.valid_end))
			return false;
		if (valid_start == null) {
			if (other.valid_start != null)
				return false;
		} else if (!valid_start.equals(other.valid_start))
			return false;
		return true;
	}

}
