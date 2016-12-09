package com.chinadrtv.uam.model.auth.res;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.chinadrtv.uam.model.auth.Resource;

/**
 * 对应菜单链接
 * 
 * @author dengqianyong
 *
 */
@Entity
@Table(name = "UAM_RESOURCE_ACTION", schema = "ACOAPP_UAM")
@PrimaryKeyJoinColumn(name = "ID")
public class ResAction extends Resource {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4161046544833671280L;

	/**
	 * 链接请求地址
	 */
	@Column(name = "ACTION_URL", length = 255, nullable = false)
	private String actionUrl;

	public String getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}
	
}
