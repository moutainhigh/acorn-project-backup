/*
 * @(#)Variables.java 1.0 2013-2-19下午1:21:15
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.dto;

import java.io.Serializable;

import com.chinadrtv.erp.smsapi.util.ChannelConverter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-2-19 下午1:21:15
 * 
 */
@XStreamAlias("variable")
@XStreamConverter(ChannelConverter.class)
public class Variables implements Serializable {
	@XStreamAlias("name")
	@XStreamAsAttribute
	private String name;
	@XStreamAlias("value")
	@XStreamAsAttribute
	private String value;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	public Variables() {

	}

	public static void main(String[] args) {
		Variables variables = new Variables();
		variables.setName("111");
		variables.setValue("222");
		XStream xstream = new XStream(new DomDriver());
		xstream.autodetectAnnotations(true);
		System.out.println(xstream.toXML(variables));
	}

}
