/*
 * @(#)PreTradeDto.java 1.0 2013年11月1日下午4:26:35
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.model.oms.dto;

import java.util.ArrayList;
import java.util.List;

import com.chinadrtv.model.oms.PreTrade;
import com.chinadrtv.model.oms.PreTradeCard;
import com.chinadrtv.model.oms.PreTradeDetail;

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
 * @since 2013年11月1日 下午4:26:35 
 * 
 */
public class PreTradeDto extends PreTrade {
	
	private String invoiceType;

	private List<PreTradeDetail> preTradeDetails = new ArrayList<PreTradeDetail>();
	
	private List<PreTradeCard> preTradeCards = new ArrayList<PreTradeCard>();

	public List<PreTradeCard> getPreTradeCards() {
		return preTradeCards;
	}
	public void setPreTradeCards(List<PreTradeCard> preTradeCards) {
		this.preTradeCards = preTradeCards;
	}
    /**
     * Getter method for property <tt>preTradeDetails</tt>.
     * 
     * @return property value of preTradeDetails
     */
    public List<PreTradeDetail> getPreTradeDetails() {
        return preTradeDetails;
    }
    /**
     * Setter method for property <tt>preTradeDetails</tt>.
     * 
     * @param preTradeDetails value to be assigned to property preTradeDetails
     */
    public void setPreTradeDetails(List<PreTradeDetail> preTradeDetails) {
        this.preTradeDetails = preTradeDetails;
    }
	public String getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
}
