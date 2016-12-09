/*
 * @(#)OpsUpdateRequestDto.java 1.0 2014-7-1上午10:20:48
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.oms.internet.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

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
 * @since 2014-7-1 上午10:20:48 
 * 
 */
public class OpsUpdateRequestDto {

	private String ops_trade_id;
	private String tms_code;
	private String tms_logisitics_ids;
	private String weight;
	private Date created;
	
	public String getOps_trade_id() {
		return ops_trade_id;
	}
	public void setOps_trade_id(String ops_trade_id) {
		this.ops_trade_id = ops_trade_id;
	}
	public String getTms_code() {
		return tms_code;
	}
	public void setTms_code(String tms_code) {
		this.tms_code = tms_code;
	}
	public String getTms_logisitics_ids() {
		return tms_logisitics_ids;
	}
	public void setTms_logisitics_ids(String tms_logisitics_ids) {
		this.tms_logisitics_ids = tms_logisitics_ids;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	
	/** 
	 * <p>Title: toString</p>
	 * <p>Description: </p>
	 * @return
	 * @see java.lang.Object#toString()
	 */ 
	@Override
	public String toString() {
		return "OpsUpdateRequestDto [ops_trade_id=" + ops_trade_id + ", tms_code=" + tms_code + ", tms_logisitics_ids="
				+ tms_logisitics_ids + ", weight=" + weight + ", created=" + created + "]";
	}
	

}
