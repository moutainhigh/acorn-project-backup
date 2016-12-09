/*
 * @(#)CustomerSqlSource.java 1.0 2013-1-21下午2:09:48
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.model.bi;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

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
 * 客户群管理——电话清洗</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午2:09:48
 * 
 */
@Table(name = "IDL_APP_CRM_PHONERANK_FT", schema = "acorn_dm")
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class IdlCrmPhoneRank {

	private String phoneid;
	private String phone_rank;
	private Date last_modify_date;
	private String dml_type;
	private String sync_flag;

	/**
	 * @return the phoneid
	 */
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "assigned")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "phoneid")
	public String getPhoneid() {
		return phoneid;
	}

	/**
	 * @param phoneid
	 *            the phoneid to set
	 */
	public void setPhoneid(String phoneid) {
		this.phoneid = phoneid;
	}

	/**
	 * @return the phone_rank
	 */
	@Column(name = "phone_rank")
	public String getPhone_rank() {
		return phone_rank;
	}

	/**
	 * @param phone_rank
	 *            the phone_rank to set
	 */
	public void setPhone_rank(String phone_rank) {
		this.phone_rank = phone_rank;
	}

	/**
	 * @return the last_modify_date
	 */
	@Column(name = "last_modify_date")
	public Date getLast_modify_date() {
		return last_modify_date;
	}

	/**
	 * @param last_modify_date
	 *            the last_modify_date to set
	 */
	public void setLast_modify_date(Date last_modify_date) {
		this.last_modify_date = last_modify_date;
	}

	/**
	 * @return the dml_type
	 */
	@Column(name = "dml_type")
	public String getDml_type() {
		return dml_type;
	}

	/**
	 * @param dml_type
	 *            the dml_type to set
	 */
	public void setDml_type(String dml_type) {
		this.dml_type = dml_type;
	}

	/**
	 * @return the sync_flag
	 */
	@Column(name = "sync_flag")
	public String getSync_flag() {
		return sync_flag;
	}

	/**
	 * @param sync_flag
	 *            the sync_flag to set
	 */
	public void setSync_flag(String sync_flag) {
		this.sync_flag = sync_flag;
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
		result = prime * result + ((dml_type == null) ? 0 : dml_type.hashCode());
		result = prime * result + ((last_modify_date == null) ? 0 : last_modify_date.hashCode());
		result = prime * result + ((phone_rank == null) ? 0 : phone_rank.hashCode());
		result = prime * result + ((phoneid == null) ? 0 : phoneid.hashCode());
		result = prime * result + ((sync_flag == null) ? 0 : sync_flag.hashCode());
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
		IdlCrmPhoneRank other = (IdlCrmPhoneRank) obj;
		if (dml_type == null) {
			if (other.dml_type != null)
				return false;
		} else if (!dml_type.equals(other.dml_type))
			return false;
		if (last_modify_date == null) {
			if (other.last_modify_date != null)
				return false;
		} else if (!last_modify_date.equals(other.last_modify_date))
			return false;
		if (phone_rank == null) {
			if (other.phone_rank != null)
				return false;
		} else if (!phone_rank.equals(other.phone_rank))
			return false;
		if (phoneid == null) {
			if (other.phoneid != null)
				return false;
		} else if (!phoneid.equals(other.phoneid))
			return false;
		if (sync_flag == null) {
			if (other.sync_flag != null)
				return false;
		} else if (!sync_flag.equals(other.sync_flag))
			return false;
		return true;
	}

}
