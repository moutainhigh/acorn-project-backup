package com.chinadrtv.chama.bean;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**  前置订表头
 * User: liuhaidong
 * Date: 12-12-6
 */
public class PreTrademap implements java.io.Serializable{

    private Long id;

    private Set<PreTradeDetailmap> preTradeDetails = new HashSet<PreTradeDetailmap>(0);

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

    private String receiverName;

    private String receiverProvince;

    private String receiverCity;

    private String receiverCounty;
    private String receiverArea;

    private String receiverAddress;
    private String receiverMobile;
    private String receiverPhone;
    private String receiverPostCode;


    private String shippingFee;


    private String payment;

    private String impUser;
    private Date impDate;
    private String impStatus;
    private String impStatusRemark;
    private String shipmentId;
    private String discountFee;
    private String buyerMessage;
    private String sellerMessage;
    private String advFee;
    private String commissionFee;
    private String creditFee;
    private String jhsFee;
    private String platformCommissionFee;
    private String retailerCommissionFee;
    private String revenue;



    private boolean isVaid;
    private String errMsg;

    private String feedbackStatus ;
    private String feedbackStatusRemark ;
    private String feedbackUser ;
    private Date feedbackDate ;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<PreTradeDetailmap> getPreTradeDetails() {
        return preTradeDetails;
    }

    public void setPreTradeDetails(Set<PreTradeDetailmap> preTradeDetails) {
        this.preTradeDetails = preTradeDetails;
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

    public String getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(String shippingFee) {
        this.shippingFee = shippingFee;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
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

    public String getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(String discountFee) {
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

    public String getAdvFee() {
        return advFee;
    }

    public void setAdvFee(String advFee) {
        this.advFee = advFee;
    }

    public String getCommissionFee() {
        return commissionFee;
    }

    public void setCommissionFee(String commissionFee) {
        this.commissionFee = commissionFee;
    }

    public String getCreditFee() {
        return creditFee;
    }

    public void setCreditFee(String creditFee) {
        this.creditFee = creditFee;
    }

    public String getJhsFee() {
        return jhsFee;
    }

    public void setJhsFee(String jhsFee) {
        this.jhsFee = jhsFee;
    }

    public String getPlatformCommissionFee() {
        return platformCommissionFee;
    }

    public void setPlatformCommissionFee(String platformCommissionFee) {
        this.platformCommissionFee = platformCommissionFee;
    }

    public String getRetailerCommissionFee() {
        return retailerCommissionFee;
    }

    public void setRetailerCommissionFee(String retailerCommissionFee) {
        this.retailerCommissionFee = retailerCommissionFee;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public boolean isVaid() {
        return isVaid;
    }

    public void setVaid(boolean vaid) {
        isVaid = vaid;
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

}
