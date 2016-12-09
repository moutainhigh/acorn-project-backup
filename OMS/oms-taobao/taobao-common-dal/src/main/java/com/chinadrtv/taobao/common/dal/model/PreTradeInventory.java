/*
 * @(#)PreTradeInventoryConfig.java 1.0 2014-7-14上午11:09:34
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.taobao.common.dal.model;

import java.io.Serializable;
import java.util.Date;

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
 * @since 2014-7-14 上午11:09:34 
 * 
 */
@SuppressWarnings("serial")
public class PreTradeInventory implements Serializable {

	private String numIid;
	private String skuId;
	private String outerId;
	private String title;
	private String nick;
	private String tradeType;
	private Date listTime;
	private Date modified;
	private Date createDate;
	private Date updateDate;
	private Long updateNum;
	private String syncType;
	
	public String getNumIid() {
		return numIid;
	}
	public void setNumIid(String numIid) {
		this.numIid = numIid;
	}
	public String getOuterId() {
		return outerId;
	}
	public void setOuterId(String outerId) {
		this.outerId = outerId;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public Date getListTime() {
		return listTime;
	}
	public void setListTime(Date listTime) {
		this.listTime = listTime;
	}
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public Long getUpdateNum() {
		return updateNum;
	}
	public void setUpdateNum(Long updateNum) {
		this.updateNum = updateNum;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getSyncType() {
		return syncType;
	}
	public void setSyncType(String syncType) {
		this.syncType = syncType;
	}
	
	/** 
	 * <p>Title: toString</p>
	 * <p>Description: </p>
	 * @return
	 * @see java.lang.Object#toString()
	 */ 
	@Override
	public String toString() {
		return "PreTradeInventory [numIid=" + numIid + ", skuId=" + skuId + ", outerId=" + outerId + ", title=" + title
				+ ", nick=" + nick + ", tradeType=" + tradeType + ", listTime=" + listTime + ", modified=" + modified
				+ ", createDate=" + createDate + ", updateDate=" + updateDate + ", updateNum=" + updateNum
				+ ", syncType=" + syncType + "]";
	}
	
}
