package com.chinadrtv.erp.uc.dto;

import java.io.Serializable;

public class CallbackDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String startDate;
	private String endDate;
	private String usrGrp;
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getUsrGrp() {
		return usrGrp;
	}
	public void setUsrGrp(String usrGrp) {
		this.usrGrp = usrGrp;
	}

}
