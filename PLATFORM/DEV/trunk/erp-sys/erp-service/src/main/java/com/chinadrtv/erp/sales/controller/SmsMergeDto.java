/**
 * 
 */
package com.chinadrtv.erp.sales.controller;

import java.io.Serializable;

/**
 * @author dengqianyong
 *
 */
public class SmsMergeDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1756261121233943141L;

	private String type;
	
	private String contactId;
	
	private String mobile;
	
	private String orderId;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
}
