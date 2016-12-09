/*
 * @(#)Card.java 1.0 2013-5-7下午3:33:58
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.model.agent;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.constraints.Length;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>客户卡信息
 *    </dd>
 * </dl>
 *
 * @author dengqianyong@chinadrtv.com
 * @version 1.0
 * @since 2013-5-7 下午3:33:58 
 * 
 */
@Entity
@Table(name = "CARD", schema = "IAGENT")
public class Card implements Serializable {

	private static final long serialVersionUID = -6004552692745006282L;
	
	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CARD_SEQ_GEN")
    @SequenceGenerator(name = "CARD_SEQ_GEN", sequenceName = "IAGENT.SEQCARD", allocationSize = 1)
	@Column(name = "CARDID", unique = true, nullable = false, insertable = true, updatable = true, length = 10)
	private Long cardId;
	
	/**
	 * 客户编号
	 */
	@Length(max = 16)
	@Column(name = "CONTACTID", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	private String contactId;
	
	/**
	 * 客户所属公司编号
	 */
	@Length(max = 16)
	@Column(name = "ENTITYID", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	private String entityId;
	
	/**
	 * 客户证件类型
	 */
	@Length(max = 128)
	@Column(name = "TYPE", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
	private String type;
	
	/**
	 * 信用卡卡号
	 */
	@Length(max = 250)
	@Column(name = "DSC", unique = false, nullable = true, insertable = true, updatable = true, length = 250)
	private String cardNumber;
	
	/**
	 * 有效期
	 */
	@Length(max = 20)
	@Column(name = "VALIDDATE", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	private String validDate;
	
	/**
	 * 是否已校验
	 */
	@Length(max = 10)
	@Column(name = "CHECKED", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	private String checked;
	
	/**
	 * 附加码
	 */
	@Length(max = 10)
	@Column(name = "EXTRACODE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	private String extraCode;
	
	/**
	 * 信用额度
	 */
	@Length(max = 10)
	@Column(name = "LIMIT", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	private String limit;
	
	@Column(name = "LAST_LOCK_SEQID", unique = false, nullable = true, insertable = true, updatable = true, length = 22)
	private Long lastLockSeqid;
	
	@Column(name = "LAST_UPDATE_SEQID", unique = false, nullable = true, insertable = true, updatable = true, length = 22)
	private Long lastUpdateSeqid;
	
	@Column(name = "LAST_UPDATE_TIME",  unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	private java.util.Date lastUpdateTime;
	
	private Integer state;
	//columns END
	
	public Card() {
		super();
	}
	
	public Long getCardId() {
		return cardId;
	}

	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getValidDate() {
		return validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getExtraCode() {
		return extraCode;
	}

	public void setExtraCode(String extraCode) {
		this.extraCode = extraCode;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public Long getLastLockSeqid() {
		return lastLockSeqid;
	}

	public void setLastLockSeqid(Long lastLockSeqid) {
		this.lastLockSeqid = lastLockSeqid;
	}

	public Long getLastUpdateSeqid() {
		return lastUpdateSeqid;
	}

	public void setLastUpdateSeqid(Long lastUpdateSeqid) {
		this.lastUpdateSeqid = lastUpdateSeqid;
	}

	public java.util.Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(java.util.Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	@Column(name = "STATE", length = 2)
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Cardid",getCardId())
			.append("Contactid",getContactId())
			.append("Entityid",getEntityId())
			.append("Type",getType())
			.append("CardNumber",getCardNumber())
			.append("Validdate",getValidDate())
			.append("Checked",getChecked())
			.append("Extracode",getExtraCode())
			.append("Limit",getLimit())
			.append("LastLockSeqid",getLastLockSeqid())
			.append("LastUpdateSeqid",getLastUpdateSeqid())
			.append("LastUpdateTime",getLastUpdateTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder().append(getCardId()).toHashCode();
	}
	
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Card) || this != obj)
			return false;
		return new EqualsBuilder().append(this.getCardId(),
				((Card) obj).getCardId()).isEquals();
	}

}
