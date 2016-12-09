/*
 * @(#)PreTradeStopSyncSkuList.java 1.0 2014-7-17下午5:09:31
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.taobao.common.dal.model;

import java.io.Serializable;

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
 * @since 2014-7-17 下午5:09:31 
 * 
 */
@SuppressWarnings("serial")
public class PreTradeStopSyncSkuList implements Serializable {

	private String pluId;
	private String pluName;
	private Boolean active;
	private String tradeType;
	
	public String getPluId() {
		return pluId;
	}
	public void setPluId(String pluId) {
		this.pluId = pluId;
	}
	public String getPluName() {
		return pluName;
	}
	public void setPluName(String pluName) {
		this.pluName = pluName;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
}
