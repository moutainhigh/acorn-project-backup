/**
 * 
 */
package com.chinadrtv.erp.sales.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单库外状态
 * 
 * @author dengqianyong
 */
public class ExpressStateDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4651146734573150305L;
	
	/**
	 * 日期
	 */
	private String date;
	
	/**
	 * 描述
	 */
	private String desc;
	
	/**
	 * 操作员
	 */
	private String operator = "";
	
	/**
	 * 快递单号
	 */
	private String expressId  = "";
	
	/**
	 * 快递公司
	 */
	private String expressName = "";

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getExpressId() {
		return expressId;
	}

	public void setExpressId(String expressId) {
		this.expressId = expressId;
	}

	public String getExpressName() {
		return expressName;
	}

	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}

}
