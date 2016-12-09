/**
 * 
 */
package com.chinadrtv.erp.customer.services.dto;

import java.io.Serializable;

/**
 * @author dengqianyong
 *
 */
public class CustomerRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6696592831004260481L;
	
	private String name;
	
	private String phone;
	
	private String province;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

}
