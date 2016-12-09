package com.chinadrtv.erp.model.agent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.SEQUENCE;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:会员类型</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013-5-23 下午1:26:51 
 *
 */
@Entity
@Table(name = "MEMBERTYPE", schema = "IAGENT")
public class MemberType implements java.io.Serializable {

	private static final long serialVersionUID = 6939087068357484102L;
	
	private String id;
	private String dsc;
	private Integer priority;
	private String status;

	public MemberType() {
	}

	
	public MemberType(String id) {
		this.id = id;
	}
	
	public MemberType(String id, String dsc, Integer priority, String status) {
		this.id = id;
		this.dsc = dsc;
		this.priority = priority;
		this.status = status;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 2)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "DSC", length = 20)
	public String getDsc() {
		return this.dsc;
	}

	public void setDsc(String dsc) {
		this.dsc = dsc;
	}

	@Column(name = "PRIORITY", length=2)
	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	@Column(name = "STATUS", length = 2)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/** 
	 * <p>Title: hashCode</p>
	 * <p>Description: </p>
	 * @return
	 * @see java.lang.Object#hashCode()
	 */ 
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dsc == null) ? 0 : dsc.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((priority == null) ? 0 : priority.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	/** 
	 * <p>Title: equals</p>
	 * <p>Description: </p>
	 * @param obj
	 * @return
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
		MemberType other = (MemberType) obj;
		if (dsc == null) {
			if (other.dsc != null)
				return false;
		} else if (!dsc.equals(other.dsc))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (priority == null) {
			if (other.priority != null)
				return false;
		} else if (!priority.equals(other.priority))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

}