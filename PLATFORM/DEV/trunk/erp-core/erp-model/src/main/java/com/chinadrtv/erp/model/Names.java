package com.chinadrtv.erp.model;

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


@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name = "NAMES", schema = "IAGENT")
public class Names implements java.io.Serializable{
	
	private NamesId id;

	private java.lang.String tdsc;     //静态信息类型描述

	private java.lang.String dsc;     //静态信息描述

	private java.lang.String valid;     //是否有效

	private String tmpId;

	public Names(){
	}
	
	public Names(NamesId id) {
		this.id = id;
	}
	
	@EmbeddedId
	public NamesId getId() {
		return this.id;
	}
	
	public void setId(NamesId id) {
		this.id = id;
	}
	
	@Column(name = "TDSC", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public java.lang.String getTdsc() {
		return this.tdsc;
	}
	
	public void setTdsc(java.lang.String value) {
		this.tdsc = value;
	}
	
	@Column(name = "DSC", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public java.lang.String getDsc() {
		return this.dsc;
	}
	
	public void setDsc(java.lang.String value) {
		this.dsc = value;
	}
	
	@Column(name = "VALID", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getValid() {
		return this.valid;
	}
	
	public void setValid(java.lang.String value) {
		this.valid = value;
	}
	

	/**
	 * @return the tmpId
	 */
	@Transient
	public String getTmpId() {
		return id.getId();
	}

	/**
	 * @param tmpId the tmpId to set
	 */
	public void setTmpId(String tmpId) {
		this.tmpId = tmpId;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Names == false) return false;
		if(this == obj) return true;
		Names other = (Names)obj;
		return new EqualsBuilder()
			.isEquals();
	}
}

