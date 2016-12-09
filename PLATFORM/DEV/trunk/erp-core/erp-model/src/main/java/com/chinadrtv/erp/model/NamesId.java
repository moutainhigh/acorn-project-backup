package com.chinadrtv.erp.model;

import javax.persistence.*;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import java.util.*;



@Embeddable
public class NamesId  implements java.io.Serializable{
	
	private java.lang.String tid;
	private java.lang.String id;

	public NamesId(){
	}

	public NamesId(
		java.lang.String tid,
		java.lang.String id
	){
		this.tid = tid;
		this.id = id;
	}

	
	
	public void setTid(java.lang.String value) {
		this.tid = value;
	}
	
	@Column(name = "TID", unique = false, nullable = false, insertable = true, updatable = true, length = 20)	
	public java.lang.String getTid() {
		return this.tid;
	}
	
	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	@Column(name = "ID", unique = false, nullable = false, insertable = true, updatable = true, length = 20)	
	public java.lang.String getId() {
		return this.id;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	public boolean equals(Object other) {
		return EqualsBuilder.reflectionEquals(this, other);
	}
}