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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * 用户任务推荐记录</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午2:09:48
 * 
 */
@Table(name = "USRTASK_RECOMMEND", schema = "ACOAPP_MARKETING")
@Entity
public class UsrTaskRecommend {

	private Long id;
	private String groupid;// 坐席组
	private String user_id;// 坐席id
	private String contactid;// 客户id
	private String recommend_prod1;// 推荐产品1
	private String recommend_prod2;// 推荐产品2
	private String recommend_prod3;// 推荐产品3
	private String other_recommend_prod;//其它推荐产品（营销计划外的产品）
	private String prouduct_id1;// 实际推荐产品1
	private String prouduct_id2;// 实际推荐产品2
	private String prouduct_id3;// 实际推荐产品3
	private String process_defid;// 执行流程id
	private Date crt_date;// 创建时间
	private String up_user;// 更新userid
	private Date up_date;// 更新时间

	private String process_insid;// 流程实例id
	private String leadTaskid;

	private Date valid_date;
	private String pd_customer_id;
	private String is_finished;

	/**
	 * @return the id
	 */
	@Id
	@SequenceGenerator(name = "SEQ_USRTASK_RECOMMEND", sequenceName = "ACOAPP_MARKETING.SEQ_USRTASK_RECOMMEND")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USRTASK_RECOMMEND")
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
	@Column(name = "group_id")
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
	 * @return the user_id
	 */
	@Column(name = "user_id")
	public String getUser_id() {
		return user_id;
	}

	/**
	 * @param user_id
	 *            the user_id to set
	 */
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	/**
	 * @return the contactid
	 */
	@Column(name = "contact_id")
	public String getContactid() {
		return contactid;
	}

	/**
	 * @param contactid
	 *            the contactid to set
	 */
	public void setContactid(String contactid) {
		this.contactid = contactid;
	}

	/**
	 * @return the recommend_prod1
	 */
	@Column(name = "recommend_prod1")
	public String getRecommend_prod1() {
		return recommend_prod1;
	}

	/**
	 * @param recommend_prod1
	 *            the recommend_prod1 to set
	 */
	public void setRecommend_prod1(String recommend_prod1) {
		this.recommend_prod1 = recommend_prod1;
	}

	/**
	 * @return the recommend_prod2
	 */
	@Column(name = "recommend_prod2")
	public String getRecommend_prod2() {
		return recommend_prod2;
	}

	/**
	 * @param recommend_prod2
	 *            the recommend_prod2 to set
	 */
	public void setRecommend_prod2(String recommend_prod2) {
		this.recommend_prod2 = recommend_prod2;
	}

	/**
	 * @return the recommend_prod3
	 */
	@Column(name = "recommend_prod3")
	public String getRecommend_prod3() {
		return recommend_prod3;
	}

	/**
	 * @param recommend_prod3
	 *            the recommend_prod3 to set
	 */
	public void setRecommend_prod3(String recommend_prod3) {
		this.recommend_prod3 = recommend_prod3;
	}
	
	@Column(name = "OTHER_RECOMMEND_PROD")
	public String getOther_recommend_prod() {
		return other_recommend_prod;
	}

	public void setOther_recommend_prod(String other_recommend_prod) {
		this.other_recommend_prod = other_recommend_prod;
	}

	/**
	 * @return the prouduct_id
	 */
	@Column(name = "prouduct_id1")
	public String getProuduct_id1() {
		return prouduct_id1;
	}

	/**
	 * @param prouduct_id
	 *            the prouduct_id to set
	 */
	public void setProuduct_id1(String prouduct_id1) {
		this.prouduct_id1 = prouduct_id1;
	}

	/**
	 * @return the prouduct_id2
	 */
	@Column(name = "prouduct_id2")
	public String getProuduct_id2() {
		return prouduct_id2;
	}

	/**
	 * @param prouduct_id2
	 *            the prouduct_id2 to set
	 */
	public void setProuduct_id2(String prouduct_id2) {
		this.prouduct_id2 = prouduct_id2;
	}

	/**
	 * @return the prouduct_id3
	 */
	@Column(name = "prouduct_id3")
	public String getProuduct_id3() {
		return prouduct_id3;
	}

	/**
	 * @param prouduct_id3
	 *            the prouduct_id3 to set
	 */
	public void setProuduct_id3(String prouduct_id3) {
		this.prouduct_id3 = prouduct_id3;
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
	 * @return the crt_date
	 */
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
	 * @return the process_insid
	 */
	@Column(name = "process_insid")
	public String getProcess_insid() {
		return process_insid;
	}

	/**
	 * @param process_insid
	 *            the process_insid to set
	 */
	public void setProcess_insid(String process_insid) {
		this.process_insid = process_insid;
	}

	/**
	 * @return the valid_date
	 */
	@Column(name = "valid_date")
	public Date getValid_date() {
		return valid_date;
	}

	/**
	 * @param valid_date
	 *            the valid_date to set
	 */
	public void setValid_date(Date valid_date) {
		this.valid_date = valid_date;
	}

	/**
	 * @return the pd_customer_id
	 */
	@Column(name = "pd_customer_id")
	public String getPd_customer_id() {
		return pd_customer_id;
	}

	/**
	 * @param pd_customer_id
	 *            the pd_customer_id to set
	 */
	public void setPd_customer_id(String pd_customer_id) {
		this.pd_customer_id = pd_customer_id;
	}

	/**
	 * @return the is_finished
	 */
	@Column(name = "is_finished")
	public String getIs_finished() {
		return is_finished;
	}

	/**
	 * @param is_finished
	 *            the is_finished to set
	 */
	public void setIs_finished(String is_finished) {
		this.is_finished = is_finished;
	}

	@Column(name = "lead_task_id")
	public String getLeadTaskid() {
		return leadTaskid;
	}

	public void setLeadTaskid(String leadTaskid) {
		this.leadTaskid = leadTaskid;
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
				+ ((contactid == null) ? 0 : contactid.hashCode());
		result = prime * result
				+ ((crt_date == null) ? 0 : crt_date.hashCode());
		result = prime * result + ((groupid == null) ? 0 : groupid.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((is_finished == null) ? 0 : is_finished.hashCode());
		result = prime * result
				+ ((pd_customer_id == null) ? 0 : pd_customer_id.hashCode());
		result = prime * result
				+ ((process_defid == null) ? 0 : process_defid.hashCode());
		result = prime * result
				+ ((process_insid == null) ? 0 : process_insid.hashCode());
		result = prime * result
				+ ((prouduct_id1 == null) ? 0 : prouduct_id1.hashCode());
		result = prime * result
				+ ((prouduct_id2 == null) ? 0 : prouduct_id2.hashCode());
		result = prime * result
				+ ((prouduct_id3 == null) ? 0 : prouduct_id3.hashCode());
		result = prime * result
				+ ((recommend_prod1 == null) ? 0 : recommend_prod1.hashCode());
		result = prime * result
				+ ((recommend_prod2 == null) ? 0 : recommend_prod2.hashCode());
		result = prime * result
				+ ((recommend_prod3 == null) ? 0 : recommend_prod3.hashCode());
		result = prime * result + ((up_date == null) ? 0 : up_date.hashCode());
		result = prime * result + ((up_user == null) ? 0 : up_user.hashCode());
		result = prime * result + ((user_id == null) ? 0 : user_id.hashCode());
		result = prime * result
				+ ((valid_date == null) ? 0 : valid_date.hashCode());
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
		UsrTaskRecommend other = (UsrTaskRecommend) obj;
		if (contactid == null) {
			if (other.contactid != null)
				return false;
		} else if (!contactid.equals(other.contactid))
			return false;
		if (crt_date == null) {
			if (other.crt_date != null)
				return false;
		} else if (!crt_date.equals(other.crt_date))
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
		if (is_finished == null) {
			if (other.is_finished != null)
				return false;
		} else if (!is_finished.equals(other.is_finished))
			return false;
		if (pd_customer_id == null) {
			if (other.pd_customer_id != null)
				return false;
		} else if (!pd_customer_id.equals(other.pd_customer_id))
			return false;
		if (process_defid == null) {
			if (other.process_defid != null)
				return false;
		} else if (!process_defid.equals(other.process_defid))
			return false;
		if (process_insid == null) {
			if (other.process_insid != null)
				return false;
		} else if (!process_insid.equals(other.process_insid))
			return false;
		if (prouduct_id1 == null) {
			if (other.prouduct_id1 != null)
				return false;
		} else if (!prouduct_id1.equals(other.prouduct_id1))
			return false;
		if (prouduct_id2 == null) {
			if (other.prouduct_id2 != null)
				return false;
		} else if (!prouduct_id2.equals(other.prouduct_id2))
			return false;
		if (prouduct_id3 == null) {
			if (other.prouduct_id3 != null)
				return false;
		} else if (!prouduct_id3.equals(other.prouduct_id3))
			return false;
		if (recommend_prod1 == null) {
			if (other.recommend_prod1 != null)
				return false;
		} else if (!recommend_prod1.equals(other.recommend_prod1))
			return false;
		if (recommend_prod2 == null) {
			if (other.recommend_prod2 != null)
				return false;
		} else if (!recommend_prod2.equals(other.recommend_prod2))
			return false;
		if (recommend_prod3 == null) {
			if (other.recommend_prod3 != null)
				return false;
		} else if (!recommend_prod3.equals(other.recommend_prod3))
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
		if (user_id == null) {
			if (other.user_id != null)
				return false;
		} else if (!user_id.equals(other.user_id))
			return false;
		if (valid_date == null) {
			if (other.valid_date != null)
				return false;
		} else if (!valid_date.equals(other.valid_date))
			return false;
		return true;
	}

}
