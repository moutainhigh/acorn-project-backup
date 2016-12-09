package com.chinadrtv.erp.admin.model;

import java.util.Date;


/**
 * SCM 促销反馈结果
 *  
 * @author haoleitao
 * @date 2013-5-8 下午5:40:34
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class ScmPromotion {
	 private Long ruid ; //
     private String docno; //促销单号
     private String docname;//促销名称
     private int status; //单据状态
     private String bdat ;//开始日期
     private String edat ;//结束日期
     private String btime ;//开始时间
     private String etime ;//结束时间
     private String wcycle;//星期循环
     private String dsc;//备注信息
     private String type;//类型
     private Double fullamount; //促销满金额
     private Double reductionamount;//促销减金额
     private String pluid; //单品iD
     private String prodname;//产品名称
     private String prodtype;
     private String proddsc;
     private String ruiddet;
     private Double slprc;
	public Long getRuid() {
		return ruid;
	}
	public void setRuid(Long ruid) {
		this.ruid = ruid;
	}
	public String getDocno() {
		return docno;
	}
	public void setDocno(String docno) {
		this.docno = docno;
	}
	public String getDocname() {
		return docname;
	}
	public void setDocname(String docname) {
		this.docname = docname;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	
	public String getBdat() {
		return bdat;
	}
	public void setBdat(String bdat) {
		this.bdat = bdat;
	}
	public String getEdat() {
		return edat;
	}
	public void setEdat(String edat) {
		this.edat = edat;
	}
	public String getBtime() {
		return btime;
	}
	public void setBtime(String btime) {
		this.btime = btime;
	}
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}
	public String getWcycle() {
		return wcycle;
	}
	public void setWcycle(String wcycle) {
		this.wcycle = wcycle;
	}
	public String getDsc() {
		return dsc;
	}
	public void setDsc(String dsc) {
		this.dsc = dsc;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double getFullamount() {
		return fullamount;
	}
	public void setFullamount(Double fullamount) {
		this.fullamount = fullamount;
	}
	public Double getReductionamount() {
		return reductionamount;
	}
	public void setReductionamount(Double reductionamount) {
		this.reductionamount = reductionamount;
	}
	public String getPluid() {
		return pluid;
	}
	public void setPluid(String pluid) {
		this.pluid = pluid;
	}
	public String getProdname() {
		return prodname;
	}
	public void setProdname(String prodname) {
		this.prodname = prodname;
	}
	public String getProdtype() {
		return prodtype;
	}
	public void setProdtype(String prodtype) {
		this.prodtype = prodtype;
	}
	public String getProddsc() {
		return proddsc;
	}
	public void setProddsc(String proddsc) {
		this.proddsc = proddsc;
	}
	public String getRuiddet() {
		return ruiddet;
	}
	public void setRuiddet(String ruiddet) {
		this.ruiddet = ruiddet;
	}
	public Double getSlprc() {
		return slprc;
	}
	public void setSlprc(Double slprc) {
		this.slprc = slprc;
	}
     
     
}
