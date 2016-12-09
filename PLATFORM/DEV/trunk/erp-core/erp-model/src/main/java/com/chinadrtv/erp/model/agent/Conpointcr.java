package com.chinadrtv.erp.model.agent;

import javax.persistence.*;
import java.util.*;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.constraints.Length;
import org.hibernate.annotations.GenericGenerator;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 创建积分
 *  
 * @author haoleitao
 * @date 2013-5-6 上午11:23:18
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Entity
@Table(name = "V_CONPOINTCR", schema = "IAGENT")
public class Conpointcr implements java.io.Serializable{
	
	
	@Length(max=16)
	private java.lang.String corderid;  //产生积分订单编号  
	@Length(max=16)
	private java.lang.String contactid;  //客户编号  
	
	private java.util.Date startdt;  //积分有效期开始日期  
	
	private java.util.Date enddt;  //积分有效期结束日期  
//	@Length(max=20)
//	private java.lang.String status;  //积分状态  
	
	private Double pointvalue;  //积分值  
	@Length(max=20)
	private java.lang.String crusr;  //创建人  
	
	private java.util.Date crdt;  //创建日期  
	
//	private Long lastLockSeqid;  //lastLockSeqid  
//	
//	private Long lastUpdateSeqid;  //lastUpdateSeqid  
//	
//	private java.util.Date lastUpdateTime;  //lastUpdateTime  
	//columns END


	public Conpointcr(){
	}

	public Conpointcr(
		java.lang.String corderid
	){
		this.corderid = corderid;
	}

	

	public void setCorderid(java.lang.String value) {
		this.corderid = value;
	}
	
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONPOINTCR_SEQ")
    @SequenceGenerator(name = "CONPOINTCR_SEQ", sequenceName = "IAGENT.CONPOINTCR_SEQ",allocationSize = 1)
	@Column(name = "CORDERID", unique = true, nullable = false, insertable = true, updatable = true, length = 16)
	public java.lang.String getCorderid() {
		return this.corderid;
	}
	
	@Column(name = "CONTACTID", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public java.lang.String getContactid() {
		return this.contactid;
	}
	
	public void setContactid(java.lang.String value) {
		this.contactid = value;
	}
	
	@Transient
	public String getStartdtString() {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		df.format(getStartdt());
		return df.format(getStartdt());
	}
	public void setStartdtString(String value)  {		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		try {
		setStartdt(df.parse(value));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Column(name = "STARTDT", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	public java.util.Date getStartdt() {
		return this.startdt;
	}
	
	public void setStartdt(java.util.Date value) {
		this.startdt = value;
	}
	
	@Transient
	public String getEnddtString() {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		df.format(getEnddt());
		return df.format(getEnddt());
	}
	public void setEnddtString(String value)  {		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		try {
		setEnddt(df.parse(value));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Column(name = "ENDDT", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	public java.util.Date getEnddt() {
		return this.enddt;
	}
	
	public void setEnddt(java.util.Date value) {
		this.enddt = value;
	}
	
//	@Column(name = "STATUS", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
//	public java.lang.String getStatus() {
//		return this.status;
//	}
//	
//	public void setStatus(java.lang.String value) {
//		this.status = value;
//	}
	
	@Column(name = "POINTVALUE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Double getPointvalue() {
		return this.pointvalue;
	}
	
	public void setPointvalue(Double value) {
		this.pointvalue = value;
	}
	
	@Column(name = "CRUSR", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public java.lang.String getCrusr() {
		return this.crusr;
	}
	
	public void setCrusr(java.lang.String value) {
		this.crusr = value;
	}
	
	@Transient
	public String getCrdtString() {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		df.format(getCrdt());
		return df.format(getCrdt());
	}
	public void setCrdtString(String value)  {		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		try {
		setCrdt(df.parse(value));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Column(name = "CRDT", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	public java.util.Date getCrdt() {
		return this.crdt;
	}
	
	public void setCrdt(java.util.Date value) {
		this.crdt = value;
	}
	
//	@Column(name = "LAST_LOCK_SEQID", unique = false, nullable = true, insertable = true, updatable = true, length = 22)
//	public Long getLastLockSeqid() {
//		return this.lastLockSeqid;
//	}
//	
//	public void setLastLockSeqid(Long value) {
//		this.lastLockSeqid = value;
//	}
	
//	@Column(name = "LAST_UPDATE_SEQID", unique = false, nullable = true, insertable = true, updatable = true, length = 22)
//	public Long getLastUpdateSeqid() {
//		return this.lastUpdateSeqid;
//	}
//	
//	public void setLastUpdateSeqid(Long value) {
//		this.lastUpdateSeqid = value;
//	}
	
//	@Transient
//	public String getLastUpdateTimeString() {
//		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
//		df.format(getLastUpdateTime());
//		return df.format(getLastUpdateTime());
//	}
//	public void setLastUpdateTimeString(String value)  {		
//		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
//		try {
//		setLastUpdateTime(df.parse(value));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
//	@Column(name = "LAST_UPDATE_TIME", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
//	public java.util.Date getLastUpdateTime() {
//		return this.lastUpdateTime;
//	}
//	
//	public void setLastUpdateTime(java.util.Date value) {
//		this.lastUpdateTime = value;
//	}
//	

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Corderid",getCorderid())
			.append("Contactid",getContactid())
			.append("Startdt",getStartdt())
			.append("Enddt",getEnddt())
		//	.append("Status",getStatus())
			.append("Pointvalue",getPointvalue())
			.append("Crusr",getCrusr())
			.append("Crdt",getCrdt())
		//	.append("LastLockSeqid",getLastLockSeqid())
		//	.append("LastUpdateSeqid",getLastUpdateSeqid())
		//	.append("LastUpdateTime",getLastUpdateTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getCorderid())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Conpointcr == false) return false;
		if(this == obj) return true;
		Conpointcr other = (Conpointcr)obj;
		return new EqualsBuilder()
			.append(getCorderid(),other.getCorderid())
			.isEquals();
	}
}

