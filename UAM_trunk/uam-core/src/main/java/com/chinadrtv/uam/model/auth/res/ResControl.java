package com.chinadrtv.uam.model.auth.res;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.chinadrtv.uam.model.auth.Resource;

/**
 * 对应控件
 * 
 * @author dengqianyong
 *
 */
@Entity
@Table(name = "UAM_RESOURCE_CONTROL", schema = "ACOAPP_UAM")
@PrimaryKeyJoinColumn(name = "ID")
public class ResControl extends Resource {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1002067628985817452L;
	
	@Column(name = "CONTROL_KEY", length = 50, nullable = false)
	private String controlKey;
	
	@Column(name = "IS_ENABLED")
	private boolean enabled;

	public String getControlKey() {
		return controlKey;
	}

	public void setControlKey(String controlKey) {
		this.controlKey = controlKey;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
}
