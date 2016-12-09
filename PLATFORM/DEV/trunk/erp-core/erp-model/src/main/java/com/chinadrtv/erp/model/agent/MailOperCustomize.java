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
 *  
 * @author haoleitao
 * @date 2013-2-27 下午2:07:11
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Entity
@Table(name = "MAIL_OPER_CUSTOMIZE", schema = "IAGENT")
public class MailOperCustomize implements java.io.Serializable{
	
	

	
	@Length(max=20)
	private java.lang.String operatype;  //operatype  
	@Length(max=20)
	private java.lang.String customizestatus;  //customizestatus  
	@Length(max=2)
	private java.lang.String isvalid;  //isvalid  
	//columns END


	public MailOperCustomize(){
	}

	
	@Id
	@Column(name = "OPERATYPE", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public java.lang.String getOperatype() {
		return this.operatype;
	}
	
	public void setOperatype(java.lang.String value) {
		this.operatype = value;
	}
	
	@Column(name = "CUSTOMIZESTATUS", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public java.lang.String getCustomizestatus() {
		return this.customizestatus;
	}
	
	public void setCustomizestatus(java.lang.String value) {
		this.customizestatus = value;
	}
	
	@Column(name = "ISVALID", unique = false, nullable = true, insertable = true, updatable = true, length = 2)
	public java.lang.String getIsvalid() {
		return this.isvalid;
	}
	
	public void setIsvalid(java.lang.String value) {
		this.isvalid = value;
	}
	

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Operatype",getOperatype())
			.append("Customizestatus",getCustomizestatus())
			.append("Isvalid",getIsvalid())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MailOperCustomize == false) return false;
		if(this == obj) return true;
		MailOperCustomize other = (MailOperCustomize)obj;
		return new EqualsBuilder()
			.isEquals();
	}
}

