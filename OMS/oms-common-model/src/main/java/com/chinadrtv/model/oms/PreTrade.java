package com.chinadrtv.model.oms;

import java.io.Serializable;
import java.util.Date;

public class PreTrade implements Serializable{
   
	private static final long serialVersionUID = -7416041726982485911L;

	private Long id;
    private Date crdt;
    private String tradeId;
    private String opsTradeId;
    private String alipayTradeId;
    private String buyerAlipayId;
    private String customerId;
    private String tradeType;
    private String tradeFrom;
    private String shippingType;
    private String tmsCode;
    private Date outCrdt;
    private String invoiceComment;
    private String invoiceTitle;
    private String buyerId;
    private String buyerNick;
    private String receiverName;
    private String receiverProvince;
    private String receiverCity;
    private String receiverArea;
    private String receiverCounty;
    private String receiverAddress;
    private String receiverMobile;
    private String receiverPhone;
    private String receiverPostCode;
    private Double shippingFee;
    private Double payment;
    private String impUser;
    private Date impDate;
    private String impStatus;
    private String impStatusRemark;
    private String shipmentId;
    private Double discountFee;
    private String buyerMessage;
    private String sellerMessage;
    private Double advFee;
    private Double comisssionFee;
    private Double creditFee;
    private Double jhsFee;
    private Double plateformCommissionFee;
    private Double retailerCommissionFee;
    private Double revenue;
    private Long preTradeLotId;
    private String errMsg;
    private Long sourceId;
    private Boolean isValid;
    private String feedbackStatus;
    private String feedbackStatusRemark;
    private String feedbackUser;
    private Date feedbackDate;
    private String fromanddate;
    private String info;
    private String remark;
    private String ordermoney;
    private String receiverarea;
    private String sendinfo;
    private String feedbackSubmissionId;
    private String feedbackMessageId;
    private Date createDate;
    private Long version;
    private Boolean isStat;
    private Integer refundStatus;
    private Date refundDate;
    private Boolean isApproval;
    private Integer refundStatusConfirm;
    private Date refundStatusConfirmDate;
    private String refundStatusConfirmUser;
    private String paytype;
    private Integer feedbackRetryCount;
    private String mailType;
    private Integer splitFlag;
    private String partTradeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCrdt() {
        return crdt;
    }

    public void setCrdt(Date crdt) {
        this.crdt = crdt;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId == null ? null : tradeId.trim();
    }

    public String getOpsTradeId() {
        return opsTradeId;
    }

    public void setOpsTradeId(String opsTradeId) {
        this.opsTradeId = opsTradeId == null ? null : opsTradeId.trim();
    }

    public String getAlipayTradeId() {
        return alipayTradeId;
    }

    public void setAlipayTradeId(String alipayTradeId) {
        this.alipayTradeId = alipayTradeId == null ? null : alipayTradeId.trim();
    }

    public String getBuyerAlipayId() {
        return buyerAlipayId;
    }

    public void setBuyerAlipayId(String buyerAlipayId) {
        this.buyerAlipayId = buyerAlipayId == null ? null : buyerAlipayId.trim();
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType == null ? null : tradeType.trim();
    }

    public String getTradeFrom() {
        return tradeFrom;
    }

    public void setTradeFrom(String tradeFrom) {
        this.tradeFrom = tradeFrom == null ? null : tradeFrom.trim();
    }

    public String getShippingType() {
        return shippingType;
    }

    public void setShippingType(String shippingType) {
        this.shippingType = shippingType == null ? null : shippingType.trim();
    }

    public String getTmsCode() {
        return tmsCode;
    }

    public void setTmsCode(String tmsCode) {
        this.tmsCode = tmsCode == null ? null : tmsCode.trim();
    }

    public Date getOutCrdt() {
        return outCrdt;
    }

    public void setOutCrdt(Date outCrdt) {
        this.outCrdt = outCrdt;
    }

    public String getInvoiceComment() {
        return invoiceComment;
    }

    public void setInvoiceComment(String invoiceComment) {
        this.invoiceComment = invoiceComment == null ? null : invoiceComment.trim();
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle == null ? null : invoiceTitle.trim();
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId == null ? null : buyerId.trim();
    }

    public String getBuyerNick() {
        return buyerNick;
    }

    public void setBuyerNick(String buyerNick) {
        this.buyerNick = buyerNick == null ? null : buyerNick.trim();
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName == null ? null : receiverName.trim();
    }

    public String getReceiverProvince() {
        return receiverProvince;
    }

    public void setReceiverProvince(String receiverProvince) {
        this.receiverProvince = receiverProvince == null ? null : receiverProvince.trim();
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    
    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity == null ? null : receiverCity.trim();
    }

    
    public String getReceiverArea() {
        return receiverArea;
    }

    public void setReceiverArea(String receiverArea) {
        this.receiverArea = receiverArea == null ? null : receiverArea.trim();
    }
    
    public String getReceiverCounty() {
        return receiverCounty;
    }
    
    public void setReceiverCounty(String receiverCounty) {
        this.receiverCounty = receiverCounty == null ? null : receiverCounty.trim();
    }
    
    public String getReceiverAddress() {
        return receiverAddress;
    }
    
    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress == null ? null : receiverAddress.trim();
    }
    
    public String getReceiverMobile() {
        return receiverMobile;
    }
    
    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile == null ? null : receiverMobile.trim();
    }
    
    public String getReceiverPhone() {
        return receiverPhone;
    }
    
    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone == null ? null : receiverPhone.trim();
    }
    
    public String getReceiverPostCode() {
        return receiverPostCode;
    }
    
    public void setReceiverPostCode(String receiverPostCode) {
        this.receiverPostCode = receiverPostCode == null ? null : receiverPostCode.trim();
    }
    
    public Double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(Double shippingFee) {
        this.shippingFee = shippingFee;
    }
    
    public Double getPayment() {
        return payment;
    }
    
    public void setPayment(Double payment) {
        this.payment = payment;
    }
    
    public String getImpUser() {
        return impUser;
    }
    
    public void setImpUser(String impUser) {
        this.impUser = impUser == null ? null : impUser.trim();
    }
    
    public Date getImpDate() {
        return impDate;
    }
    
    public void setImpDate(Date impDate) {
        this.impDate = impDate;
    }
    
    public String getImpStatus() {
        return impStatus;
    }
    
    public void setImpStatus(String impStatus) {
        this.impStatus = impStatus == null ? null : impStatus.trim();
    }
    
    public String getImpStatusRemark() {
        return impStatusRemark;
    }
    
    public void setImpStatusRemark(String impStatusRemark) {
        this.impStatusRemark = impStatusRemark == null ? null : impStatusRemark.trim();
    }
    
    public String getShipmentId() {
        return shipmentId;
    }
    
    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId == null ? null : shipmentId.trim();
    }
    
    public Double getDiscountFee() {
        return discountFee;
    }
    
    public void setDiscountFee(Double discountFee) {
        this.discountFee = discountFee;
    }
    
    public String getBuyerMessage() {
        return buyerMessage;
    }
    
    public void setBuyerMessage(String buyerMessage) {
        this.buyerMessage = buyerMessage == null ? null : buyerMessage.trim();
    }
    
    public String getSellerMessage() {
        return sellerMessage;
    }
    
    public void setSellerMessage(String sellerMessage) {
        this.sellerMessage = sellerMessage == null ? null : sellerMessage.trim();
    }
    
    public Double getAdvFee() {
        return advFee;
    }
    
    public void setAdvFee(Double advFee) {
        this.advFee = advFee;
    }
    
    public Double getComisssionFee() {
        return comisssionFee;
    }
    
    public void setComisssionFee(Double comisssionFee) {
        this.comisssionFee = comisssionFee;
    }
    
    public Double getCreditFee() {
        return creditFee;
    }
    
    public void setCreditFee(Double creditFee) {
        this.creditFee = creditFee;
    }
    
    public Double getJhsFee() {
        return jhsFee;
    }
    
    public void setJhsFee(Double jhsFee) {
        this.jhsFee = jhsFee;
    }
    
    public Double getPlateformCommissionFee() {
        return plateformCommissionFee;
    }

    public void setPlateformCommissionFee(Double plateformCommissionFee) {
        this.plateformCommissionFee = plateformCommissionFee;
    }
    
    public Double getRetailerCommissionFee() {
        return retailerCommissionFee;
    }
    
    public void setRetailerCommissionFee(Double retailerCommissionFee) {
        this.retailerCommissionFee = retailerCommissionFee;
    }
    
    public Double getRevenue() {
        return revenue;
    }
    
    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }
    
    public Long getPreTradeLotId() {
        return preTradeLotId;
    }
    
    public void setPreTradeLotId(Long preTradeLotId) {
        this.preTradeLotId = preTradeLotId;
    }
    
    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg == null ? null : errMsg.trim();
    }
    
    public Long getSourceId() {
        return sourceId;
    }
    
    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getFeedbackStatus() {
        return feedbackStatus;
    }

    public void setFeedbackStatus(String feedbackStatus) {
        this.feedbackStatus = feedbackStatus == null ? null : feedbackStatus.trim();
    }
    
    public String getFeedbackStatusRemark() {
        return feedbackStatusRemark;
    }
    
    public void setFeedbackStatusRemark(String feedbackStatusRemark) {
        this.feedbackStatusRemark = feedbackStatusRemark == null ? null : feedbackStatusRemark.trim();
    }
    
    public String getFeedbackUser() {
        return feedbackUser;
    }

    public void setFeedbackUser(String feedbackUser) {
        this.feedbackUser = feedbackUser == null ? null : feedbackUser.trim();
    }
    
    public Date getFeedbackDate() {
        return feedbackDate;
    }
    
    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
    }
    
    public String getFromanddate() {
        return fromanddate;
    }
    
    public void setFromanddate(String fromanddate) {
        this.fromanddate = fromanddate == null ? null : fromanddate.trim();
    }
    
    public String getInfo() {
        return info;
    }
    
    public void setInfo(String info) {
        this.info = info == null ? null : info.trim();
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
    
    public String getOrdermoney() {
        return ordermoney;
    }
    
    public void setOrdermoney(String ordermoney) {
        this.ordermoney = ordermoney == null ? null : ordermoney.trim();
    }
    
    public String getReceiverarea() {
        return receiverarea;
    }
    
    public void setReceiverarea(String receiverarea) {
        this.receiverarea = receiverarea == null ? null : receiverarea.trim();
    }
    
    public String getSendinfo() {
        return sendinfo;
    }
    
    public void setSendinfo(String sendinfo) {
        this.sendinfo = sendinfo == null ? null : sendinfo.trim();
    }
    
    public String getFeedbackSubmissionId() {
        return feedbackSubmissionId;
    }
    
    public void setFeedbackSubmissionId(String feedbackSubmissionId) {
        this.feedbackSubmissionId = feedbackSubmissionId == null ? null : feedbackSubmissionId.trim();
    }
    
    public String getFeedbackMessageId() {
        return feedbackMessageId;
    }
    
    public void setFeedbackMessageId(String feedbackMessageId) {
        this.feedbackMessageId = feedbackMessageId == null ? null : feedbackMessageId.trim();
    }
    
    public Date getCreateDate() {
        return createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    public Long getVersion() {
        return version;
    }
    
    public void setVersion(Long version) {
        this.version = version;
    }
    
    public Date getRefundDate() {
        return refundDate;
    }
    
    public void setRefundDate(Date refundDate) {
        this.refundDate = refundDate;
    }
    
    public Date getRefundStatusConfirmDate() {
        return refundStatusConfirmDate;
    }
    
    public void setRefundStatusConfirmDate(Date refundStatusConfirmDate) {
        this.refundStatusConfirmDate = refundStatusConfirmDate;
    }
    
    public String getRefundStatusConfirmUser() {
        return refundStatusConfirmUser;
    }
    
    public void setRefundStatusConfirmUser(String refundStatusConfirmUser) {
        this.refundStatusConfirmUser = refundStatusConfirmUser == null ? null : refundStatusConfirmUser.trim();
    }
    
    public String getPaytype() {
        return paytype;
    }
    
    public void setPaytype(String paytype) {
        this.paytype = paytype == null ? null : paytype.trim();
    }
    
    public String getMailType() {
        return mailType;
    }
    
    public void setMailType(String mailType) {
        this.mailType = mailType == null ? null : mailType.trim();
    }
	
	public Boolean getIsValid() {
		return isValid;
	}
	
	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}
	
	public Boolean getIsStat() {
		return isStat;
	}
	
	public void setIsStat(Boolean isStat) {
		this.isStat = isStat;
	}
	
	public Integer getRefundStatus() {
		return refundStatus;
	}
	
	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}
	
	public Boolean getIsApproval() {
		return isApproval;
	}
	
	public void setIsApproval(Boolean isApproval) {
		this.isApproval = isApproval;
	}
	
	public Integer getRefundStatusConfirm() {
		return refundStatusConfirm;
	}
	
	public void setRefundStatusConfirm(Integer refundStatusConfirm) {
		this.refundStatusConfirm = refundStatusConfirm;
	}
	
	public Integer getFeedbackRetryCount() {
		return feedbackRetryCount;
	}
	
	public void setFeedbackRetryCount(Integer feedbackRetryCount) {
		this.feedbackRetryCount = feedbackRetryCount;
	}

	public Integer getSplitFlag() {
		return splitFlag;
	}

	public void setSplitFlag(Integer splitFlag) {
		this.splitFlag = splitFlag;
	}

	/** 
	 * <p>Title: toString</p>
	 * <p>Description: </p>
	 * @return
	 * @see java.lang.Object#toString()
	 */ 
	@Override
	public String toString() {
		return "PreTrade [id=" + id + ", crdt=" + crdt + ", tradeId=" + tradeId + ", opsTradeId=" + opsTradeId
				+ ", alipayTradeId=" + alipayTradeId + ", buyerAlipayId=" + buyerAlipayId + ", customerId="
				+ customerId + ", tradeType=" + tradeType + ", tradeFrom=" + tradeFrom + ", shippingType="
				+ shippingType + ", tmsCode=" + tmsCode + ", outCrdt=" + outCrdt + ", invoiceComment=" + invoiceComment
				+ ", invoiceTitle=" + invoiceTitle + ", buyerId=" + buyerId + ", buyerNick=" + buyerNick
				+ ", receiverName=" + receiverName + ", receiverProvince=" + receiverProvince + ", receiverCity="
				+ receiverCity + ", receiverArea=" + receiverArea + ", receiverCounty=" + receiverCounty
				+ ", receiverAddress=" + receiverAddress + ", receiverMobile=" + receiverMobile + ", receiverPhone="
				+ receiverPhone + ", receiverPostCode=" + receiverPostCode + ", shippingFee=" + shippingFee
				+ ", payment=" + payment + ", impUser=" + impUser + ", impDate=" + impDate + ", impStatus=" + impStatus
				+ ", impStatusRemark=" + impStatusRemark + ", shipmentId=" + shipmentId + ", discountFee="
				+ discountFee + ", buyerMessage=" + buyerMessage + ", sellerMessage=" + sellerMessage + ", advFee="
				+ advFee + ", comisssionFee=" + comisssionFee + ", creditFee=" + creditFee + ", jhsFee=" + jhsFee
				+ ", plateformCommissionFee=" + plateformCommissionFee + ", retailerCommissionFee="
				+ retailerCommissionFee + ", revenue=" + revenue + ", preTradeLotId=" + preTradeLotId + ", errMsg="
				+ errMsg + ", sourceId=" + sourceId + ", isValid=" + isValid + ", feedbackStatus=" + feedbackStatus
				+ ", feedbackStatusRemark=" + feedbackStatusRemark + ", feedbackUser=" + feedbackUser
				+ ", feedbackDate=" + feedbackDate + ", fromanddate=" + fromanddate + ", info=" + info + ", remark="
				+ remark + ", ordermoney=" + ordermoney + ", receiverarea=" + receiverarea + ", sendinfo=" + sendinfo
				+ ", feedbackSubmissionId=" + feedbackSubmissionId + ", feedbackMessageId=" + feedbackMessageId
				+ ", createDate=" + createDate + ", version=" + version + ", isStat=" + isStat + ", refundStatus="
				+ refundStatus + ", refundDate=" + refundDate + ", isApproval=" + isApproval + ", refundStatusConfirm="
				+ refundStatusConfirm + ", refundStatusConfirmDate=" + refundStatusConfirmDate
				+ ", refundStatusConfirmUser=" + refundStatusConfirmUser + ", paytype=" + paytype
				+ ", feedbackRetryCount=" + feedbackRetryCount + ", mailType=" + mailType + ", splitFlag=" + splitFlag
				+ "]";
	}

	public String getPartTradeId() {
		return partTradeId;
	}

	public void setPartTradeId(String partTradeId) {
		this.partTradeId = partTradeId;
	}
}