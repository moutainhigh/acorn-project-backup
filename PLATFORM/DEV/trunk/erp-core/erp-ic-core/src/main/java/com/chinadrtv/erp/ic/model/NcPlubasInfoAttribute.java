package com.chinadrtv.erp.ic.model;

import java.io.Serializable;

/**
 * NC商品属性
 * User: Administrator
 * Date: 13-5-29
 * Time: 上午11:36
 * To change this template use File | Settings | File Templates.
 */
public class NcPlubasInfoAttribute implements Serializable {
    private String ncfree;
    private String ncfreename;
	public String getNcfree() {
		return ncfree;
	}
	public void setNcfree(String ncfree) {
		this.ncfree = ncfree;
	}
	public String getNcfreename() {
		return ncfreename;
	}
	public void setNcfreename(String ncfreename) {
		this.ncfreename = ncfreename;
	}
    
    
}
