package com.chinadrtv.erp.oms.dto;

import java.util.Date;

import com.chinadrtv.erp.model.EdiClear;

public class EdiClearDto implements java.io.Serializable  {
	private String entityid;	//送货公司Id
	private String clearid;       //ID
    private String orderid;     //订单号
    private String mailid;      //邮件号
    private Date senddt;        //交寄时间
    private String status;      //反馈状态
    private String reason;      //导入备注信息
    private Double totalprice;  //应收金额
    private Double nowmoney;    //实际结算金额
    private Date impdt;          //结账时间
    private String impusr;       //结算人
    private Integer type;        //结账类型
    private String remark;       //结算描述
    private Double postFee;    //投递费
    private String company; //送货公司
    
	public String getClearid() {
		return clearid;
	}
	public void setClearid(String clearid) {
		this.clearid = clearid;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getMailid() {
		return mailid;
	}
	public void setMailid(String mailid) {
		this.mailid = mailid;
	}
	public Date getSenddt() {
		return senddt;
	}
	public void setSenddt(Date senddt) {
		this.senddt = senddt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Double getTotalprice() {
		return totalprice;
	}
	public void setTotalprice(Double totalprice) {
		this.totalprice = totalprice;
	}
	public Double getNowmoney() {
		return nowmoney;
	}
	public void setNowmoney(Double nowmoney) {
		this.nowmoney = nowmoney;
	}
	public Date getImpdt() {
		return impdt;
	}
	public void setImpdt(Date impdt) {
		this.impdt = impdt;
	}
	public String getImpusr() {
		return impusr;
	}
	public void setImpusr(String impusr) {
		this.impusr = impusr;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Double getPostFee() {
		return postFee;
	}
	public void setPostFee(Double postFee) {
		this.postFee = postFee;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getEntityid() {
		return entityid;
	}
	public void setEntityid(String entityid) {
		this.entityid = entityid;
	}

	
	
    
    
}
