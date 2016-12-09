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
 * 
 * 事件类型 
 * @author haoleitao
 * @date 2013-5-9 下午1:04:53
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Entity
@Table(name = "CASETYPE", schema = "IAGENT")
public class Casetype implements java.io.Serializable{
	
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	@Length(max=16)
	private java.lang.String casetypeid;  //事件类型编号 PK  
	@Length(max=30)
	private java.lang.String dsc;  //事件类型描述  
	@Length(max=16)
	private java.lang.String category;  //事件子类CATEGORY表 FK  
	@Length(max=20)
	private java.lang.String wfname;  //工作流名  
	@Length(max=10)
	private java.lang.String priorityid;  //事件优先级PRIORITY表 FK  
	//columns END


	public Casetype(){
	}

	public Casetype(
		java.lang.String casetypeid
	){
		this.casetypeid = casetypeid;
	}

	

	public void setCasetypeid(java.lang.String value) {
		this.casetypeid = value;
	}
	
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CASETYPE_SEQ")
    @SequenceGenerator(name = "CASETYPE_SEQ", sequenceName = "IAGENT.CASETYPE_SEQ")
	@Column(name = "CASETYPEID", unique = true, nullable = false, insertable = true, updatable = true, length = 16)
	public java.lang.String getCasetypeid() {
		return this.casetypeid;
	}
	
	@Column(name = "DSC", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	public java.lang.String getDsc() {
		return this.dsc;
	}
	
	public void setDsc(java.lang.String value) {
		this.dsc = value;
	}
	
	@Column(name = "CATEGORY", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public java.lang.String getCategory() {
		return this.category;
	}
	
	public void setCategory(java.lang.String value) {
		this.category = value;
	}
	
	@Column(name = "WFNAME", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public java.lang.String getWfname() {
		return this.wfname;
	}
	
	public void setWfname(java.lang.String value) {
		this.wfname = value;
	}
	
	@Column(name = "PRIORITYID", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getPriorityid() {
		return this.priorityid;
	}
	
	public void setPriorityid(java.lang.String value) {
		this.priorityid = value;
	}
	

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Casetypeid",getCasetypeid())
			.append("Dsc",getDsc())
			.append("Category",getCategory())
			.append("Wfname",getWfname())
			.append("Priorityid",getPriorityid())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getCasetypeid())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Casetype == false) return false;
		if(this == obj) return true;
		Casetype other = (Casetype)obj;
		return new EqualsBuilder()
			.append(getCasetypeid(),other.getCasetypeid())
			.isEquals();
	}
}

