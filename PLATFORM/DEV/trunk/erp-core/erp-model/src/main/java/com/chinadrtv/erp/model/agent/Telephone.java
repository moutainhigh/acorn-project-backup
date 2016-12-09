/*
 * @(#)Card.java 1.0 2013-5-7下午3:33:58
 *
 * 橡果国际-系统集成�?
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.model.agent;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * <dl>
 * <dt><b>Title: cti 分机ip配置表</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-9-3 上午11:31:49
 * 
 */
@Entity
@Table(name = "TELEPHONE", schema = "IAGENT")
public class Telephone implements Serializable {

	private static final long serialVersionUID = -6004552692745006282L;

	private String telephone;
	private String ipaddress;
	private String channel;
	private String agentid;
	private String imlserver;
	private String route;
    private Integer phoneType;//1 模拟电话  2数字电话


    @Column(name = "PHONETYPE")
    public Integer getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(Integer phoneType) {
        this.phoneType = phoneType;
    }

    /**
	 * @return the telephone
	 */
	@Id
	@Column(name = "telephone")
	public String getTelephone() {
		return telephone;
	}

	/**
	 * @param telephone
	 *            the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * @return the ipaddress
	 */
	@Column(name = "ipaddress")
	public String getIpaddress() {
		return ipaddress;
	}

	/**
	 * @param ipaddress
	 *            the ipaddress to set
	 */
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	/**
	 * @return the channel
	 */
	@Column(name = "channel")
	public String getChannel() {
		return channel;
	}

	/**
	 * @param channel
	 *            the channel to set
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}

	/**
	 * @return the agentid
	 */
	@Column(name = "agentid")
	public String getAgentid() {
		return agentid;
	}

	/**
	 * @param agentid
	 *            the agentid to set
	 */
	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}

	/**
	 * @return the imlserver
	 */
	@Column(name = "imlserver")
	public String getImlserver() {
		return imlserver;
	}

	/**
	 * @param imlserver
	 *            the imlserver to set
	 */
	public void setImlserver(String imlserver) {
		this.imlserver = imlserver;
	}

	/**
	 * @return the route
	 */
	@Column(name = "route")
	public String getRoute() {
		return route;
	}

	/**
	 * @param route
	 *            the route to set
	 */
	public void setRoute(String route) {
		this.route = route;
	}

	/*
	 * (非 Javadoc) <p>Title: hashCode</p> <p>Description: </p>
	 * 
	 * @return
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agentid == null) ? 0 : agentid.hashCode());
		result = prime * result + ((channel == null) ? 0 : channel.hashCode());
		result = prime * result + ((imlserver == null) ? 0 : imlserver.hashCode());
		result = prime * result + ((ipaddress == null) ? 0 : ipaddress.hashCode());
		result = prime * result + ((route == null) ? 0 : route.hashCode());
		result = prime * result + ((telephone == null) ? 0 : telephone.hashCode());
		return result;
	}

	/*
	 * (非 Javadoc) <p>Title: equals</p> <p>Description: </p>
	 * 
	 * @param obj
	 * 
	 * @return
	 * 
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
		Telephone other = (Telephone) obj;
		if (agentid == null) {
			if (other.agentid != null)
				return false;
		} else if (!agentid.equals(other.agentid))
			return false;
		if (channel == null) {
			if (other.channel != null)
				return false;
		} else if (!channel.equals(other.channel))
			return false;
		if (imlserver == null) {
			if (other.imlserver != null)
				return false;
		} else if (!imlserver.equals(other.imlserver))
			return false;
		if (ipaddress == null) {
			if (other.ipaddress != null)
				return false;
		} else if (!ipaddress.equals(other.ipaddress))
			return false;
		if (route == null) {
			if (other.route != null)
				return false;
		} else if (!route.equals(other.route))
			return false;
		if (telephone == null) {
			if (other.telephone != null)
				return false;
		} else if (!telephone.equals(other.telephone))
			return false;
		return true;
	}

}
