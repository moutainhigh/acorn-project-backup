package com.chinadrtv.erp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**  前置订表头
 * User: liuhaidong
 * Date: 12-12-6
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class,property="@id")
@Entity
@Table(name = "PRE_TRADE", schema = "ACOAPP_OMS")
public class PreTrade implements java.io.Serializable{
    
    private Long id;


    @Version 
    private Long version;

    private PreTradeLot preTradeLot;

    private Set<PreTradeDetail> preTradeDetails = new HashSet<PreTradeDetail>(0);
    private Set<PreTradeCard> preTradeCards = new HashSet<PreTradeCard>(0);

    private Long sourceId;
    private String tradeId;
    private String opsTradeId;
    private String alipayTradeId;
    private String buyerAlipayId;
    private String customerId;
    private String tradeType;
    private String tradeFrom;
    private String shippingType;

    private String tmsCode;
    private Date crdt;
    private Date outCrdt;
    private String invoiceComment;
    private String invoiceTitle;
    private String buyerId;
    private String buyerNick;
   
    @Length(min = 2)
    private String receiverName;
   
    @Length(min = 2)
    private String receiverProvince;
   
    @Length(min = 2)
    private String receiverCity;
   
    @Length(min = 2)
    private String receiverCounty;
    private String receiverArea;
   
    @Length(max = 128)
    private String receiverAddress;
    private String receiverMobile;
    private String receiverPhone;
    private String receiverPostCode;

   
    @Min(0)
    private Double shippingFee;

   
    @Min(0)
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
    private Double commissionFee;
    private Double creditFee;
    private Double jhsFee;
    private Double platformCommissionFee;
    private Double retailerCommissionFee;
    private Double revenue;

    private Boolean isVaid;
    private String errMsg;

    private String feedbackStatus ;
    private String feedbackStatusRemark ;
    private String feedbackUser ;
    private Date feedbackDate ;
    private String feedbackSubmissionId ;
    private String feedbackMessageId ;
    
    private Integer refundStatus;//退款状态
    private Date refundDate;//退款日期
    private Boolean isApproval; //是否允许审核;
    
    private Integer refundStatusConfirm;
    private Date refundStatusConfirmDate;
    private String refundStatusConfirmUser;

    //记录创建时间
    
    private String payType;
    private Integer feedbackRetryCount ;
    private String mailType;
    private Integer splitFlag;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRE_TRADE_SEQ")
    @SequenceGenerator(name = "PRE_TRADE_SEQ", sequenceName = "ACOAPP_OMS.PRE_TRADE_SEQ")
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Version
    @Column(name = "VERSION")
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRE_TRADE_LOT_ID")
    public PreTradeLot getPreTradeLot() {
        return preTradeLot;
    }

    public void setPreTradeLot(PreTradeLot preTradeLot) {
        this.preTradeLot = preTradeLot;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "preTrade")
    public Set<PreTradeDetail> getPreTradeDetails() {
        return preTradeDetails;
    }

    public void setPreTradeDetails(Set<PreTradeDetail> preTradeDetails) {
        this.preTradeDetails = preTradeDetails;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "preTrade")
    public Set<PreTradeCard> getPreTradeCards() {
        return preTradeCards;
    }

    public void setPreTradeCards(Set<PreTradeCard> preTradeCards) {
        this.preTradeCards = preTradeCards;
    }

    @Column(name = "TRADE_ID", length = 100)
    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    @Column(name = "OPS_TRADE_ID", length = 100)
    public String getOpsTradeId() {
        return opsTradeId;
    }

    public void setOpsTradeId(String opsTradeId) {
        this.opsTradeId = opsTradeId;
    }

    @Column(name = "ALIPAY_TRADE_ID", length = 100)
    public String getAlipayTradeId() {
        return alipayTradeId;
    }

    public void setAlipayTradeId(String alipayTradeId) {
        this.alipayTradeId = alipayTradeId;
    }

    @Column(name = "BUYER_ALIPAY_ID", length = 100)
    public String getBuyerAlipayId() {
        return buyerAlipayId;

    }

    public void setBuyerAlipayId(String buyerAlipayId) {
        this.buyerAlipayId = buyerAlipayId;
    }

    @Column(name = "CUSTOMER_ID", length = 100)
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Column(name = "TRADE_TYPE", length = 100)
    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    @Column(name = "TRADE_FROM", length = 100)
    public String getTradeFrom() {
        return tradeFrom;
    }

    public void setTradeFrom(String tradeFrom) {
        this.tradeFrom = tradeFrom;
    }

    @Column(name = "SHIPPING_TYPE", length = 100)
    public String getShippingType() {
        return shippingType;
    }

    public void setShippingType(String shippingType) {
        this.shippingType = shippingType;
    }

    @Column(name = "TMS_CODE", length = 100)
    public String getTmsCode() {
        return tmsCode;
    }

    public void setTmsCode(String tmsCode) {
        this.tmsCode = tmsCode;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CRDT")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date getCrdt() {
        return crdt;
    }

    public void setCrdt(Date crdt) {
        this.crdt = crdt;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "OUT_CRDT")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date getOutCrdt() {
        return outCrdt;
    }

    public void setOutCrdt(Date outCrdt) {
        this.outCrdt = outCrdt;
    }

    @Column(name = "INVOICE_COMMENT", length = 100)
    public String getInvoiceComment() {
        return invoiceComment;
    }

    public void setInvoiceComment(String invoiceComment) {
        this.invoiceComment = invoiceComment;
    }

    @Column(name = "INVOICE_TITLE", length = 100)
    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    @Column(name = "BUYER_ID", length = 100)
    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    @Column(name = "BUYER_NICK", length = 100)
    public String getBuyerNick() {
        return buyerNick;
    }

    public void setBuyerNick(String buyerNick) {
        this.buyerNick = buyerNick;
    }

    @Column(name = "RECEIVER_NAME", length = 100)
    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    @Column(name = "RECEIVER_PROVINCE", length = 100)
    public String getReceiverProvince() {
        return receiverProvince;
    }

    public void setReceiverProvince(String receiverProvince) {
        this.receiverProvince = receiverProvince;
    }

    @Column(name = "RECEIVER_CITY", length = 100)
    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }


    @Column(name = "RECEIVER_AREA", length = 100)
    public String getReceiverArea() {
        return receiverArea;
    }

    public void setReceiverArea(String receiverArea) {
        this.receiverArea = receiverArea;
    }


    @Column(name = "RECEIVER_COUNTY", length = 100)
    public String getReceiverCounty() {
        return receiverCounty;
    }

    public void setReceiverCounty(String receiverCounty) {
        this.receiverCounty = receiverCounty;
    }

    @Column(name = "RECEIVER_ADDRESS", length = 200)
    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    @Column(name = "RECEIVER_MOBILE", length = 100)
    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    @Column(name = "RECEIVER_PHONE", length = 100)
    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    @Column(name = "RECEIVER_POST_CODE", length = 100)
    public String getReceiverPostCode() {
        return receiverPostCode;
    }

    public void setReceiverPostCode(String receiverPostCode) {
        this.receiverPostCode = receiverPostCode;
    }

    @Column(name = "SHIPPING_FEE")
    public Double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(Double shippingFee) {
        this.shippingFee = shippingFee;
    }

    @Column(name = "PAYMENT", length = 100)
    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }

    @Column(name = "IMP_USER", length = 100)
    public String getImpUser() {
        return impUser;
    }

    public void setImpUser(String impUser) {
        this.impUser = impUser;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "IMP_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date getImpDate() {
        return impDate;
    }

    public void setImpDate(Date impDate) {
        this.impDate = impDate;
    }

    @Column(name = "IMP_STATUS", length = 100)
    public String getImpStatus() {
        return impStatus;
    }

    public void setImpStatus(String impStatus) {
        this.impStatus = impStatus;
    }

    @Column(name = "IMP_STATUS_REMARK", length = 100)
    public String getImpStatusRemark() {
        return impStatusRemark;
    }

    public void setImpStatusRemark(String impStatusRemark) {
        this.impStatusRemark = impStatusRemark;
    }

    @Column(name = "SHIPMENT_ID", length = 100)
    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    @Column(name = "DISCOUNT_FEE")
    public Double getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(Double discountFee) {
        this.discountFee = discountFee;
    }

    @Column(name = "BUYER_MESSAGE", length = 100)
    public String getBuyerMessage() {
        return buyerMessage;
    }

    public void setBuyerMessage(String buyerMessage) {
        this.buyerMessage = buyerMessage;
    }

    @Column(name = "SELLER_MESSAGE", length = 100)
    public String getSellerMessage() {
        return sellerMessage;
    }

    public void setSellerMessage(String sellerMessage) {
        this.sellerMessage = sellerMessage;
    }

    @Column(name = "ADV_FEE")
    public Double getAdvFee() {
        return advFee;
    }

    public void setAdvFee(Double advFee) {
        this.advFee = advFee;
    }

    @Column(name = "COMISSSION_FEE")
    public Double getCommissionFee() {
        return commissionFee;
    }

    public void setCommissionFee(Double commissionFee) {
        this.commissionFee = commissionFee;
    }

    @Column(name = "CREDIT_FEE")
    public Double getCreditFee() {
        return creditFee;
    }

    public void setCreditFee(Double creditFee) {
        this.creditFee = creditFee;
    }

    @Column(name = "JHS_FEE")
    public Double getJhsFee() {
        return jhsFee;
    }

    public void setJhsFee(Double jhsFee) {
        this.jhsFee = jhsFee;
    }

    @Column(name = "PLATEFORM_COMMISSION_FEE")
    public Double getPlatformCommissionFee() {
        return platformCommissionFee;
    }

    public void setPlatformCommissionFee(Double platformCommissionFee) {
        this.platformCommissionFee = platformCommissionFee;
    }

    @Column(name = "RETAILER_COMMISSION_FEE")
    public Double getRetailerCommissionFee() {
        return retailerCommissionFee;
    }

    public void setRetailerCommissionFee(Double retailerCommissionFee) {
        this.retailerCommissionFee = retailerCommissionFee;
    }

    @Column(name = "REVENUE")
    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }

    @Column(name = "IS_VALID")
    public Boolean getIsVaid() {
        return isVaid;
    }

    public void setIsVaid(Boolean vaid) {
        isVaid = vaid;
    }

    @Column(name = "ERR_MSG",length = 300)
    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    @Column(name = "SOURCE_ID")
    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    @Column(name = "FEEDBACK_STATUS", length = 100)
    public String getFeedbackStatus() {
        return feedbackStatus;
    }

    public void setFeedbackStatus(String feedbackStatus) {
        this.feedbackStatus = feedbackStatus;
    }

    @Column(name = "FEEDBACK_STATUS_REMARK", length = 1000)
    public String getFeedbackStatusRemark() {
        return feedbackStatusRemark;
    }

    public void setFeedbackStatusRemark(String feedbackStatusRemark) {
        this.feedbackStatusRemark = feedbackStatusRemark;
    }

    @Column(name = "FEEDBACK_USER", length = 100)
    public String getFeedbackUser() {
        return feedbackUser;
    }

    public void setFeedbackUser(String feedbackUser) {
        this.feedbackUser = feedbackUser;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FEEDBACK_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
    }    

    @Column(name = "FEEDBACK_SUBMISSION_ID", length = 100)
    public String getFeedbackSubmissionId() {
        return feedbackSubmissionId;
    }

    public void setFeedbackSubmissionId(String feedbackSubmissionId) {
        this.feedbackSubmissionId = feedbackSubmissionId;
    }

    @Column(name = "FEEDBACK_MESSAGE_ID", length = 100)
    public String getFeedbackMessageId() {
        return feedbackMessageId;
    }

    public void setFeedbackMessageId(String feedbackMessageId) {
        this.feedbackMessageId = feedbackMessageId;
    }


    @Column(name = "REFUND_STATUS")
	public Integer getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}

    @Column(name = "REFUND_DATE")
	public Date getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}
	
	@Column(name = "IS_APPROVAL")
	public Boolean getIsApproval() {
		return isApproval;
	}

	public void setIsApproval(Boolean isApproval) {
		this.isApproval = isApproval;
	}

	@Column(name = "REFUND_STATUS_CONFIRM")
	public Integer getRefundStatusConfirm() {
		return refundStatusConfirm;
	}

	public void setRefundStatusConfirm(Integer refundStatusConfirm) {
		this.refundStatusConfirm = refundStatusConfirm;
	}
	@Column(name = "REFUND_STATUS_CONFIRM_DATE")
	public Date getRefundStatusConfirmDate() {
		return refundStatusConfirmDate;
	}

	public void setRefundStatusConfirmDate(Date refundStatusConfirmDate) {
		this.refundStatusConfirmDate = refundStatusConfirmDate;
	}
	
	@Column(name = "REFUND_STATUS_CONFIRM_USER")
	public String getRefundStatusConfirmUser() {
		return refundStatusConfirmUser;
	}

	public void setRefundStatusConfirmUser(String refundStatusConfirmUser) {
		this.refundStatusConfirmUser = refundStatusConfirmUser;
	}
	
	@Column(name = "PAYTYPE")
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}


    @Column(name = "FEEDBACK_RETRY_COUNT")
    public Integer getFeedbackRetryCount() {
        return feedbackRetryCount;
    }

    public void setFeedbackRetryCount(Integer feedbackRetryCount) {
        this.feedbackRetryCount = feedbackRetryCount;
    }

    @Column(name = "MAIL_TYPE")
    public String getMailType() {
        return mailType;
    }

    public void setMailType(String mailType) {
        this.mailType = mailType;
    }
    @Column(name = "SPLIT_FLAG")
    public Integer getSplitFlag() {
        return splitFlag;
    }

    public void setSplitFlag(Integer splitFlag) {
        this.splitFlag = splitFlag;
    }
}
