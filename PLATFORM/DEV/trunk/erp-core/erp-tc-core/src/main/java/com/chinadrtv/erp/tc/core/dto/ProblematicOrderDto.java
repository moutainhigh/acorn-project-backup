package com.chinadrtv.erp.tc.core.dto;

import java.math.BigDecimal;
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
 * @author yangfei
 * @version 1.0
 * @since 2013-7-23 下午1:26:17 
 * 
 */
public class ProblematicOrderDto {

	private String problemId;
	private String orderid;
	private String contactId;
	private String orderStatus;
	//申请状态
	private String processStatus;
	private String mailId;
	private String contactName;
	private String beginCrDate;
	private String endCrDate;
	private String crusr;
	
	//optional
	private String receiverName;
	private String receiverPhone;
	private String parentOrderId;
	private String provinceid;
	private Integer cityid;
	private Integer countyid;
	private Integer areaid;
	private String paytype;
	private String itemCode;
	private String beginRfoutdt;
	private String endRfoutdt;
	
	//reply field
	private String replyContent;
	
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getContactId() {
		return contactId;
	}
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
	
	public String getMailId() {
		return mailId;
	}
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getCrusr() {
		return crusr;
	}
	public void setCrusr(String crusr) {
		this.crusr = crusr;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverPhone() {
		return receiverPhone;
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	public String getParentOrderId() {
		return parentOrderId;
	}
	public void setParentOrderId(String parentOrderId) {
		this.parentOrderId = parentOrderId;
	}
	public String getProvinceid() {
		return provinceid;
	}
	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}
	public Integer getCityid() {
		return cityid;
	}
	public void setCityid(Integer cityid) {
		this.cityid = cityid;
	}
	public Integer getCountyid() {
		return countyid;
	}
	public void setCountyid(Integer countyid) {
		this.countyid = countyid;
	}
	public Integer getAreaid() {
		return areaid;
	}
	public void setAreaid(Integer areaid) {
		this.areaid = areaid;
	}
	public String getPaytype() {
		return paytype;
	}
	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getBeginCrDate() {
		return beginCrDate;
	}
	public void setBeginCrDate(String beginCrDate) {
		this.beginCrDate = beginCrDate;
	}
	public String getEndCrDate() {
		return endCrDate;
	}
	public void setEndCrDate(String endCrDate) {
		this.endCrDate = endCrDate;
	}
	public String getBeginRfoutdt() {
		return beginRfoutdt;
	}
	public void setBeginRfoutdt(String beginRfoutdt) {
		this.beginRfoutdt = beginRfoutdt;
	}
	public String getEndRfoutdt() {
		return endRfoutdt;
	}
	public void setEndRfoutdt(String endRfoutdt) {
		this.endRfoutdt = endRfoutdt;
	}
	public String getProblemId() {
		return problemId;
	}
	public void setProblemId(String problemId) {
		this.problemId = problemId;
	}
	public String getReplyContent() {
		return replyContent;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

}
