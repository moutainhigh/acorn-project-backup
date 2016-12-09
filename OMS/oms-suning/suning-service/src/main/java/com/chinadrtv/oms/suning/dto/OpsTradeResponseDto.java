/*
 * @(#)OpsTradeResponse.java 1.0 2014-6-30下午1:39:11
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.oms.suning.dto;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2014-6-30 下午1:39:11 
 * 
 */
public class OpsTradeResponseDto {

	private String ops_trade_id;
	private String agent_trade_id;
	private Boolean result;
	private String message_code;
	private String message;
	
	public String getOps_trade_id() {
		return ops_trade_id;
	}
	public void setOps_trade_id(String ops_trade_id) {
		this.ops_trade_id = ops_trade_id;
	}
	public String getAgent_trade_id() {
		return agent_trade_id;
	}
	public void setAgent_trade_id(String agent_trade_id) {
		this.agent_trade_id = agent_trade_id;
	}
	public Boolean getResult() {
		return result;
	}
	public void setResult(Boolean result) {
		this.result = result;
	}
	public String getMessage_code() {
		return message_code;
	}
	public void setMessage_code(String message_code) {
		this.message_code = message_code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	/** 
	 * <p>Title: toString</p>
	 * <p>Description: </p>
	 * @return
	 * @see java.lang.Object#toString()
	 */ 
	@Override
	public String toString() {
		return "OpsTradeResponseDto [ops_trade_id=" + ops_trade_id + ", agent_trade_id=" + agent_trade_id + ", result="
				+ result + ", message_code=" + message_code + ", message=" + message + "]";
	}

}
