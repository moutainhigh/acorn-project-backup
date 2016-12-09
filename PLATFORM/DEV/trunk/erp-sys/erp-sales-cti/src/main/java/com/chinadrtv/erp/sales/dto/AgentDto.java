package com.chinadrtv.erp.sales.dto;

import java.io.Serializable;

public class AgentDto extends SessionDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6647393015260628893L;
	
	private String serverIp;
	
	private Integer orderCount;
	
	private Integer callCount;

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	public Integer getCallCount() {
		return callCount;
	}

	public void setCallCount(Integer callCount) {
		this.callCount = callCount;
	}

}
