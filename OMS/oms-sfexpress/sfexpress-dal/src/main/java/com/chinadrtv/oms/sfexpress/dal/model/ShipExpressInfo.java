/*
 * @(#)ShipExpressInfo.java 1.0 2014-6-13上午10:04:07
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.oms.sfexpress.dal.model;

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
 * @since 2014-6-13 上午10:04:07 
 * 
 */
public class ShipExpressInfo implements Serializable {

	private static final long serialVersionUID = 6826926805201543295L;
	
	private String shipmentId;
	private String mailNo;
	private String returnTrackingNo;
	private String agentMailNo;
	private String originCode;
	private String destCode;
	private String filterResult;
	private String remark;

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 */ 
	public ShipExpressInfo() {
		super();
	}
	
	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param shipmentId
	 * @param mailNo
	 * @param returnTrackingNo
	 * @param agentMaiNo
	 * @param originCode
	 * @param destCode
	 * @param filterResult
	 * @param remark
	 */ 
	public ShipExpressInfo(String shipmentId, String mailNo, String returnTrackingNo, String agentMailNo,
			String originCode, String destCode, String filterResult, String remark) {
		super();
		this.shipmentId = shipmentId;
		this.mailNo = mailNo;
		this.returnTrackingNo = returnTrackingNo;
		this.agentMailNo = agentMailNo;
		this.originCode = originCode;
		this.destCode = destCode;
		this.filterResult = filterResult;
		this.remark = remark;
	}
	
	public String getShipmentId() {
		return shipmentId;
	}
	public void setShipmentId(String shipmentId) {
		this.shipmentId = shipmentId;
	}
	public String getMailNo() {
		return mailNo;
	}
	public void setMailNo(String mailNo) {
		this.mailNo = mailNo;
	}
	public String getReturnTrackingNo() {
		return returnTrackingNo;
	}
	public void setReturnTrackingNo(String returnTrackingNo) {
		this.returnTrackingNo = returnTrackingNo;
	}
	public String getOriginCode() {
		return originCode;
	}
	public void setOriginCode(String originCode) {
		this.originCode = originCode;
	}
	public String getDestCode() {
		return destCode;
	}
	public void setDestCode(String destCode) {
		this.destCode = destCode;
	}
	public String getFilterResult() {
		return filterResult;
	}
	public void setFilterResult(String filterResult) {
		this.filterResult = filterResult;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAgentMailNo() {
		return agentMailNo;
	}
	public void setAgentMailNo(String agentMailNo) {
		this.agentMailNo = agentMailNo;
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
		result = prime * result + ((agentMailNo == null) ? 0 : agentMailNo.hashCode());
		result = prime * result + ((destCode == null) ? 0 : destCode.hashCode());
		result = prime * result + ((filterResult == null) ? 0 : filterResult.hashCode());
		result = prime * result + ((mailNo == null) ? 0 : mailNo.hashCode());
		result = prime * result + ((originCode == null) ? 0 : originCode.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((returnTrackingNo == null) ? 0 : returnTrackingNo.hashCode());
		result = prime * result + ((shipmentId == null) ? 0 : shipmentId.hashCode());
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
		ShipExpressInfo other = (ShipExpressInfo) obj;
		if (agentMailNo == null) {
			if (other.agentMailNo != null)
				return false;
		} else if (!agentMailNo.equals(other.agentMailNo))
			return false;
		if (destCode == null) {
			if (other.destCode != null)
				return false;
		} else if (!destCode.equals(other.destCode))
			return false;
		if (filterResult == null) {
			if (other.filterResult != null)
				return false;
		} else if (!filterResult.equals(other.filterResult))
			return false;
		if (mailNo == null) {
			if (other.mailNo != null)
				return false;
		} else if (!mailNo.equals(other.mailNo))
			return false;
		if (originCode == null) {
			if (other.originCode != null)
				return false;
		} else if (!originCode.equals(other.originCode))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (returnTrackingNo == null) {
			if (other.returnTrackingNo != null)
				return false;
		} else if (!returnTrackingNo.equals(other.returnTrackingNo))
			return false;
		if (shipmentId == null) {
			if (other.shipmentId != null)
				return false;
		} else if (!shipmentId.equals(other.shipmentId))
			return false;
		return true;
	}

	/** 
	 * <p>Title: toString</p>
	 * <p>Description: </p>
	 * @return
	 * @see java.lang.Object#toString()
	 */ 
	@Override
	public String toString() {
		return "ShipExpressInfo [shipmentId=" + shipmentId + ", mailNo=" + mailNo + ", returnTrackingNo="
				+ returnTrackingNo + ", agentMailNo=" + agentMailNo + ", originCode=" + originCode + ", destCode="
				+ destCode + ", filterResult=" + filterResult + ", remark=" + remark + "]";
	}


}
