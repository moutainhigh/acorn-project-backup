package com.chinadrtv.erp.task.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Length;

import com.chinadrtv.erp.task.core.orm.entity.BaseEntity;

/**  前置订表头
 * User: liuhaidong
 * Date: 12-12-6
 */
@Entity
@Table(name = "PRE_TRADE", schema = "ACOAPP_OMS")
public class PreTrade extends BaseEntity{
    
	private static final long serialVersionUID = -8208724918332682107L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRE_TRADE_SEQ")
    @SequenceGenerator(name = "PRE_TRADE_SEQ", sequenceName = "ACOAPP_OMS.PRE_TRADE_SEQ")
    @Column(name = "ID")
	private Long id;

    @Version
    @Column(name = "VERSION")
    private Long version;

    @Column(name = "SOURCE_ID")
    private Long sourceId;
    
    @Column(name = "TRADE_ID", length = 100)
    private String tradeId;
    
    @Column(name = "OPS_TRADE_ID", length = 100)
    private String opsTradeId;
    
    @Column(name = "ALIPAY_TRADE_ID", length = 100)
    private String alipayTradeId;
    
    @Column(name = "BUYER_ALIPAY_ID", length = 100)
    private String buyerAlipayId;
    
    @Column(name = "CUSTOMER_ID", length = 100)
    private String customerId;
    
    @Column(name = "TRADE_TYPE", length = 100)
    private String tradeType;
    
    @Column(name = "TRADE_FROM", length = 100)
    private String tradeFrom;
    
    @Column(name = "SHIPPING_TYPE", length = 100)
    private String shippingType;

    @Column(name = "TMS_CODE", length = 100)
    private String tmsCode;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CRDT")
    private Date crdt;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "OUT_CRDT")
    private Date outCrdt;
    
    @Column(name = "INVOICE_COMMENT", length = 100)
    private String invoiceComment;
    
    @Column(name = "INVOICE_TITLE", length = 100)
    private String invoiceTitle;
    
    @Column(name = "BUYER_ID", length = 100)
    private String buyerId;
    
    @Column(name = "BUYER_NICK", length = 100)
    private String buyerNick;
   
    @Length(min = 2)
    @Column(name = "RECEIVER_NAME", length = 100)
    private String receiverName;
   
    @Length(min = 2)
    @Column(name = "RECEIVER_PROVINCE", length = 100)
    private String receiverProvince;
   
    @Length(min = 2)
    @Column(name = "RECEIVER_CITY", length = 100)
    private String receiverCity;
   
    @Length(min = 2)
    @Column(name = "RECEIVER_COUNTY", length = 100)
    private String receiverCounty;
    
    @Column(name = "RECEIVER_AREA", length = 100)
    private String receiverArea;
   
    @Length(max = 128)
    @Column(name = "RECEIVER_ADDRESS", length = 200)
    private String receiverAddress;
    
    @Column(name = "RECEIVER_MOBILE", length = 100)
    private String receiverMobile;
    
    @Column(name = "RECEIVER_PHONE", length = 100)
    private String receiverPhone;
    
    @Column(name = "RECEIVER_POST_CODE", length = 100)
    private String receiverPostCode;

    @Min(0)
    @Column(name = "SHIPPING_FEE")
    private Double shippingFee;

    @Min(0)
    @Column(name = "PAYMENT", length = 100)
    private Double payment;

    @Column(name = "IMP_USER", length = 100)
    private String impUser;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "IMP_DATE")
    private Date impDate;
    
    @Column(name = "IMP_STATUS", length = 100)
    private String impStatus;
    
    @Column(name = "IMP_STATUS_REMARK", length = 100)
    private String impStatusRemark;
    
    @Column(name = "SHIPMENT_ID", length = 100)
    private String shipmentId;
    
    @Column(name = "DISCOUNT_FEE")
    private Double discountFee;
    
    @Column(name = "BUYER_MESSAGE", length = 100)
    private String buyerMessage;
    
    @Column(name = "SELLER_MESSAGE", length = 100)
    private String sellerMessage;
    
    @Column(name = "ADV_FEE")
    private Double advFee;
    
    @Column(name = "COMISSSION_FEE")
    private Double commissionFee;
    
    @Column(name = "CREDIT_FEE")
    private Double creditFee;
    
    @Column(name = "JHS_FEE")
    private Double jhsFee;
    
    @Column(name = "PLATEFORM_COMMISSION_FEE")
    private Double platformCommissionFee;
    
    @Column(name = "RETAILER_COMMISSION_FEE")
    private Double retailerCommissionFee;
    
    @Column(name = "REVENUE")
    private Double revenue;

    @Column(name = "IS_VALID")
    private Boolean isVaid;
    
    @Column(name = "ERR_MSG",length = 300)
    private String errMsg;

    @Column(name = "FEEDBACK_STATUS", length = 100)
    private String feedbackStatus ;
    
    @Column(name = "FEEDBACK_STATUS_REMARK", length = 1000)
    private String feedbackStatusRemark ;
    
    @Column(name = "FEEDBACK_USER", length = 100)
    private String feedbackUser ;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FEEDBACK_DATE")
    private Date feedbackDate ;
    
    @Column(name = "FEEDBACK_SUBMISSION_ID", length = 100)
    private String feedbackSubmissionId ;
    
    @Column(name = "FEEDBACK_MESSAGE_ID", length = 100)
    private String feedbackMessageId ;
    
    @Column(name = "REFUND_STATUS")
    private Integer refundStatus;//退款状态
    
    @Column(name = "REFUND_DATE")
    private Date refundDate;//退款日期
    
    @Column(name = "IS_APPROVAL")
    private Boolean isApproval; //是否允许审核;
    
    @Column(name = "REFUND_STATUS_CONFIRM")
    private Integer refundStatusConfirm;
    
    @Column(name = "REFUND_STATUS_CONFIRM_DATE")
    private Date refundStatusConfirmDate;
    
    @Column(name = "REFUND_STATUS_CONFIRM_USER")
    private String refundStatusConfirmUser;

    //记录创建时间
    @Column(name = "PAYTYPE")
    private String payType;
    
    @Column(name = "FEEDBACK_RETRY_COUNT")
    private Integer feedbackRetryCount ;
    
    @Column(name = "MAIL_TYPE")
    private String mailType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public String getOpsTradeId() {
		return opsTradeId;
	}

	public void setOpsTradeId(String opsTradeId) {
		this.opsTradeId = opsTradeId;
	}

	public String getAlipayTradeId() {
		return alipayTradeId;
	}

	public void setAlipayTradeId(String alipayTradeId) {
		this.alipayTradeId = alipayTradeId;
	}

	public String getBuyerAlipayId() {
		return buyerAlipayId;
	}

	public void setBuyerAlipayId(String buyerAlipayId) {
		this.buyerAlipayId = buyerAlipayId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getTradeFrom() {
		return tradeFrom;
	}

	public void setTradeFrom(String tradeFrom) {
		this.tradeFrom = tradeFrom;
	}

	public String getShippingType() {
		return shippingType;
	}

	public void setShippingType(String shippingType) {
		this.shippingType = shippingType;
	}

	public String getTmsCode() {
		return tmsCode;
	}

	public void setTmsCode(String tmsCode) {
		this.tmsCode = tmsCode;
	}

	public Date getCrdt() {
		return crdt;
	}

	public void setCrdt(Date crdt) {
		this.crdt = crdt;
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
		this.invoiceComment = invoiceComment;
	}

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getBuyerNick() {
		return buyerNick;
	}

	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverProvince() {
		return receiverProvince;
	}

	public void setReceiverProvince(String receiverProvince) {
		this.receiverProvince = receiverProvince;
	}

	public String getReceiverCity() {
		return receiverCity;
	}

	public void setReceiverCity(String receiverCity) {
		this.receiverCity = receiverCity;
	}

	public String getReceiverCounty() {
		return receiverCounty;
	}

	public void setReceiverCounty(String receiverCounty) {
		this.receiverCounty = receiverCounty;
	}

	public String getReceiverArea() {
		return receiverArea;
	}

	public void setReceiverArea(String receiverArea) {
		this.receiverArea = receiverArea;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public String getReceiverMobile() {
		return receiverMobile;
	}

	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getReceiverPostCode() {
		return receiverPostCode;
	}

	public void setReceiverPostCode(String receiverPostCode) {
		this.receiverPostCode = receiverPostCode;
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
		this.impUser = impUser;
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
		this.impStatus = impStatus;
	}

	public String getImpStatusRemark() {
		return impStatusRemark;
	}

	public void setImpStatusRemark(String impStatusRemark) {
		this.impStatusRemark = impStatusRemark;
	}

	public String getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(String shipmentId) {
		this.shipmentId = shipmentId;
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
		this.buyerMessage = buyerMessage;
	}

	public String getSellerMessage() {
		return sellerMessage;
	}

	public void setSellerMessage(String sellerMessage) {
		this.sellerMessage = sellerMessage;
	}

	public Double getAdvFee() {
		return advFee;
	}

	public void setAdvFee(Double advFee) {
		this.advFee = advFee;
	}

	public Double getCommissionFee() {
		return commissionFee;
	}

	public void setCommissionFee(Double commissionFee) {
		this.commissionFee = commissionFee;
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

	public Double getPlatformCommissionFee() {
		return platformCommissionFee;
	}

	public void setPlatformCommissionFee(Double platformCommissionFee) {
		this.platformCommissionFee = platformCommissionFee;
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

	public Boolean getIsVaid() {
		return isVaid;
	}

	public void setIsVaid(Boolean isVaid) {
		this.isVaid = isVaid;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getFeedbackStatus() {
		return feedbackStatus;
	}

	public void setFeedbackStatus(String feedbackStatus) {
		this.feedbackStatus = feedbackStatus;
	}

	public String getFeedbackStatusRemark() {
		return feedbackStatusRemark;
	}

	public void setFeedbackStatusRemark(String feedbackStatusRemark) {
		this.feedbackStatusRemark = feedbackStatusRemark;
	}

	public String getFeedbackUser() {
		return feedbackUser;
	}

	public void setFeedbackUser(String feedbackUser) {
		this.feedbackUser = feedbackUser;
	}

	public Date getFeedbackDate() {
		return feedbackDate;
	}

	public void setFeedbackDate(Date feedbackDate) {
		this.feedbackDate = feedbackDate;
	}

	public String getFeedbackSubmissionId() {
		return feedbackSubmissionId;
	}

	public void setFeedbackSubmissionId(String feedbackSubmissionId) {
		this.feedbackSubmissionId = feedbackSubmissionId;
	}

	public String getFeedbackMessageId() {
		return feedbackMessageId;
	}

	public void setFeedbackMessageId(String feedbackMessageId) {
		this.feedbackMessageId = feedbackMessageId;
	}

	public Integer getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}

	public Date getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
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
		this.refundStatusConfirmUser = refundStatusConfirmUser;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public Integer getFeedbackRetryCount() {
		return feedbackRetryCount;
	}

	public void setFeedbackRetryCount(Integer feedbackRetryCount) {
		this.feedbackRetryCount = feedbackRetryCount;
	}

	public String getMailType() {
		return mailType;
	}

	public void setMailType(String mailType) {
		this.mailType = mailType;
	}

}
