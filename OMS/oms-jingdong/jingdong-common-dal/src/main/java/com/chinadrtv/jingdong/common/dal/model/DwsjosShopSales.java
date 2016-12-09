package com.chinadrtv.jingdong.common.dal.model;

import java.io.Serializable;

public class DwsjosShopSales implements Serializable{
   
	private static final long serialVersionUID = -9001849653328841426L;
	private Long dwsId;
    private String orderType;
    private Integer onlpronum;
    private Integer pv;
    private Integer visit;
    private Integer uv;
    private Integer ordqtty;
    private Integer ordpronum;
    private Double ordamount;
    private Integer ordcustnum;
    private Double avgordprice;
    private Double avgcustprice;
    private Double avgcustordnum;
    private Double custrate;
    private Double ordrate;
    private Integer datestamp;
    
	public Long getDwsId() {
		return dwsId;
	}
	public void setDwsId(Long dwsId) {
		this.dwsId = dwsId;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public Integer getOnlpronum() {
		return onlpronum;
	}
	public void setOnlpronum(Integer onlpronum) {
		this.onlpronum = onlpronum;
	}
	public Integer getPv() {
		return pv;
	}
	public void setPv(Integer pv) {
		this.pv = pv;
	}
	public Integer getVisit() {
		return visit;
	}
	public void setVisit(Integer visit) {
		this.visit = visit;
	}
	public Integer getUv() {
		return uv;
	}
	public void setUv(Integer uv) {
		this.uv = uv;
	}
	public Integer getOrdqtty() {
		return ordqtty;
	}
	public void setOrdqtty(Integer ordqtty) {
		this.ordqtty = ordqtty;
	}
	public Integer getOrdpronum() {
		return ordpronum;
	}
	public void setOrdpronum(Integer ordpronum) {
		this.ordpronum = ordpronum;
	}
	public Double getOrdamount() {
		return ordamount;
	}
	public void setOrdamount(Double ordamount) {
		this.ordamount = ordamount;
	}
	public Integer getOrdcustnum() {
		return ordcustnum;
	}
	public void setOrdcustnum(Integer ordcustnum) {
		this.ordcustnum = ordcustnum;
	}
	public Double getAvgordprice() {
		return avgordprice;
	}
	public void setAvgordprice(Double avgordprice) {
		this.avgordprice = avgordprice;
	}
	public Double getAvgcustprice() {
		return avgcustprice;
	}
	public void setAvgcustprice(Double avgcustprice) {
		this.avgcustprice = avgcustprice;
	}
	public Double getAvgcustordnum() {
		return avgcustordnum;
	}
	public void setAvgcustordnum(Double avgcustordnum) {
		this.avgcustordnum = avgcustordnum;
	}
	public Double getCustrate() {
		return custrate;
	}
	public void setCustrate(Double custrate) {
		this.custrate = custrate;
	}
	public Double getOrdrate() {
		return ordrate;
	}
	public void setOrdrate(Double ordrate) {
		this.ordrate = ordrate;
	}
	public Integer getDatestamp() {
		return datestamp;
	}
	public void setDatestamp(Integer datestamp) {
		this.datestamp = datestamp;
	}
    
	
}