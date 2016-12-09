/*
 * @(#)Content.java 1.0 2013-2-19下午1:20:09
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.chinadrtv.erp.smsapi.util.ChannelConverter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-2-19 下午1:20:09
 * 
 */
@XStreamAlias("content")
public class Content implements Serializable {
	@XStreamAlias("template")
	private String template;
	private List<Variables> variables = new ArrayList<Variables>();

	public Content() {

	}

	/**
	 * @return the template
	 */
	public String getTemplate() {
		return template;
	}

	/**
	 * @param template
	 *            the template to set
	 */
	public void setTemplate(String template) {
		this.template = template;
	}

	/**
	 * @return the variables
	 */
	public List<Variables> getVariables() {
		return variables;
	}

	/**
	 * @param variables
	 *            the variables to set
	 */
	public void setVariables(List<Variables> variables) {
		this.variables = variables;
	}

	public static void main(String[] args) {
		Content content = new Content();
		List<Variables> list = new ArrayList<Variables>();
		Variables variable = new Variables();
		variable.setName("111");
		variable.setValue("222");
		Variables variable2 = new Variables();
		variable2.setName("abc");
		variable2.setValue("efg");
		list.add(variable);
		list.add(variable2);
		content.setTemplate("123");
		content.setVariables(list);

		XStream xstream = new XStream(new DomDriver());
		xstream.autodetectAnnotations(true);
		// System.out.println(xstream.toXML(content));
		String xml = xstream.toXML(content);
		Content contents = new Content();
		contents = (Content) xstream.fromXML(xml);
		xstream.registerConverter(new ChannelConverter());
		System.out.println(contents.getVariables().get(0).getName() + "==="
				+ contents.getVariables().get(0).getValue() + "===="
				+ contents.getVariables().get(1).getName());
	}

}
