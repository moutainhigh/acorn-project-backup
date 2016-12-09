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
 * 使用积分
 *  
 * @author haoleitao
 * @date 2013-5-6 上午11:24:06
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Entity
@Table(name = "V_CONPOINTUSE", schema = "IAGENT")
public class Conpointuse implements java.io.Serializable{
	
	//columns START
	@Length(max=20)
	private java.lang.String id;  //编号  
	@Length(max=16)
	private java.lang.String corderid;  //产生订单编号  
	@Length(max=16)
	private java.lang.String actorderid;  //使用积分订单编号  
	@Length(max=16)
	private java.lang.String contactid;  //客户编号  
	
	private Double pointvalue;  //使用金额  
	@Length(max=20)
	private java.lang.String type;  //使用类型（99为正常订单使用,其余为产生积分订单反馈状态改变使用,对应Names表中的ORDERFEEDBACK,0是取消、1是无反馈、2是完成、3是拒收、4是退货、5是换货、6是压单  
//	@Length(max=20)
//	private java.lang.String status;  //未使用  
	
	private java.util.Date crdt;  //创建日期  
	
	private java.util.Date mddt;  //修改日期  
	@Length(max=20)
	private java.lang.String crusr;  //创建人  
	@Length(max=20)
	private java.lang.String mdusr;  //修改人  
	
//	private Long lastLockSeqid;  //lastLockSeqid  
//	
//	private Long lastUpdateSeqid;  //lastUpdateSeqid  
//	
//	private java.util.Date lastUpdateTime;  //lastUpdateTime  
	//columns END


	public Conpointuse(){
	}

	public Conpointuse(
		java.lang.String id
	){
		this.id = id;
	}

	

	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONPOINTUSE_SEQ")
    @SequenceGenerator(name = "CONPOINTUSE_SEQ", sequenceName = "IAGENT.CONPOINTUSE_SEQ",allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, insertable = true, updatable = true, length = 20)
	public java.lang.String getId() {
		return this.id;
	}
	
	@Column(name = "CORDERID", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public java.lang.String getCorderid() {
		return this.corderid;
	}
	
	public void setCorderid(java.lang.String value) {
		this.corderid = value;
	}
	
	@Column(name = "ACTORDERID", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public java.lang.String getActorderid() {
		return this.actorderid;
	}
	
	public void setActorderid(java.lang.String value) {
		this.actorderid = value;
	}
	
	@Column(name = "CONTACTID", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public java.lang.String getContactid() {
		return this.contactid;
	}
	
	public void setContactid(java.lang.String value) {
		this.contactid = value;
	}
	
	@Column(name = "POINTVALUE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Double getPointvalue() {
		return this.pointvalue;
	}
	
	public void setPointvalue(Double value) {
		this.pointvalue = value;
	}
	
	@Column(name = "TYPE", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public java.lang.String getType() {
		return this.type;
	}
	
	public void setType(java.lang.String value) {
		this.type = value;
	}
	
//	@Column(name = "STATUS", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
//	public java.lang.String getStatus() {
//		return this.status;
//	}
//	
//	public void setStatus(java.lang.String value) {
//		this.status = value;
//	}
	
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
	
	@Transient
	public String getMddtString() {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		df.format(getMddt());
		return df.format(getMddt());
	}
	public void setMddtString(String value)  {		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		try {
		setMddt(df.parse(value));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Column(name = "MDDT", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	public java.util.Date getMddt() {
		return this.mddt;
	}
	
	public void setMddt(java.util.Date value) {
		this.mddt = value;
	}
	
	@Column(name = "CRUSR", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public java.lang.String getCrusr() {
		return this.crusr;
	}
	
	public void setCrusr(java.lang.String value) {
		this.crusr = value;
	}
	
	@Column(name = "MDUSR", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public java.lang.String getMdusr() {
		return this.mdusr;
	}
	
	public void setMdusr(java.lang.String value) {
		this.mdusr = value;
	}
	
//	@Column(name = "LAST_LOCK_SEQID", unique = false, nullable = true, insertable = true, updatable = true, length = 22)
//	public Long getLastLockSeqid() {
//		return this.lastLockSeqid;
//	}
//	
//	public void setLastLockSeqid(Long value) {
//		this.lastLockSeqid = value;
//	}
//	
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
	

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Corderid",getCorderid())
			.append("Actorderid",getActorderid())
			.append("Contactid",getContactid())
			.append("Pointvalue",getPointvalue())
			.append("Type",getType())
//			.append("Status",getStatus())
			.append("Crdt",getCrdt())
			.append("Mddt",getMddt())
			.append("Crusr",getCrusr())
			.append("Mdusr",getMdusr())
//			.append("LastLockSeqid",getLastLockSeqid())
//			.append("LastUpdateSeqid",getLastUpdateSeqid())
//			.append("LastUpdateTime",getLastUpdateTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Conpointuse == false) return false;
		if(this == obj) return true;
		Conpointuse other = (Conpointuse)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

