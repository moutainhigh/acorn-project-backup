package com.chinadrtv.taobao.common.dal.model;

import java.util.ArrayList;
import java.util.List;


public class TradeFeedback implements java.io.Serializable {
    
	private static final long serialVersionUID = 1908431163055074812L;
	
	private String tradeId;
    private String companyCode;
    private String mailId ;
    private String orderId;
    private String oids;
    
    private String senderName;
    private String senderTelephone;
    private String senderMobilePhone;
    private String senderAddress;
    private String senderZipCode;
    private String receiverName;
    private String receiverTelephone;
    private String receiverMobilePhone;
    private String receiverAddress;
    private String receiverZipCode;
    private String sellerWangwangId;
    
    private List<TradeFeedbackDetail> details = new ArrayList<TradeFeedbackDetail>();

    public TradeFeedback(){

    }

    public TradeFeedback(String tradeId, String mapCode, String mailId, String orderId){
        this.tradeId = tradeId ;
        this.mailId = mailId ;
        this.companyCode = mapCode ;
        this.orderId = orderId ;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

	public String getOids() {
		return oids;
	}

	public void setOids(String oids) {
		this.oids = oids;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderTelephone() {
		return senderTelephone;
	}

	public void setSenderTelephone(String senderTelephone) {
		this.senderTelephone = senderTelephone;
	}

	public String getSenderMobilePhone() {
		return senderMobilePhone;
	}

	public void setSenderMobilePhone(String senderMobilePhone) {
		this.senderMobilePhone = senderMobilePhone;
	}

	public String getSenderAddress() {
		return senderAddress;
	}

	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}

	public String getSenderZipCode() {
		return senderZipCode;
	}

	public void setSenderZipCode(String senderZipCode) {
		this.senderZipCode = senderZipCode;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverTelephone() {
		return receiverTelephone;
	}

	public void setReceiverTelephone(String receiverTelephone) {
		this.receiverTelephone = receiverTelephone;
	}

	public String getReceiverMobilePhone() {
		return receiverMobilePhone;
	}

	public void setReceiverMobilePhone(String receiverMobilePhone) {
		this.receiverMobilePhone = receiverMobilePhone;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public String getReceiverZipCode() {
		return receiverZipCode;
	}

	public void setReceiverZipCode(String receiverZipCode) {
		this.receiverZipCode = receiverZipCode;
	}

	public String getSellerWangwangId() {
		return sellerWangwangId;
	}

	public void setSellerWangwangId(String sellerWangwangId) {
		this.sellerWangwangId = sellerWangwangId;
	}

	public List<TradeFeedbackDetail> getDetails() {
		return details;
	}

	public void setDetails(List<TradeFeedbackDetail> details) {
		this.details = details;
	}
}
