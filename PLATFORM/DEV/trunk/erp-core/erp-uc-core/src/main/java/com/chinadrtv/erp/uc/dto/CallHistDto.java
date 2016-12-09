package com.chinadrtv.erp.uc.dto;

import java.util.Date;

public class CallHistDto {
	private String contactid;
	private Date sdt;
	private Date edt;
	public String getContactid() {
		return contactid;
	}
	public void setContactid(String contactid) {
		this.contactid = contactid;
	}
	public Date getSdt() {
		return sdt;
	}
	public void setSdt(Date sdt) {
		this.sdt = sdt;
	}
	public Date getEdt() {
		return edt;
	}
	public void setEdt(Date edt) {
		this.edt = edt;
	}

	
	
}
