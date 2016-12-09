package com.chinadrtv.scheduler.web.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;


public class SearchBox {
	private Map<String,Object> parameterBox = null;

	private int pageSize;

	private int currentPage;

	public SearchBox(int pageSize, int currentPage, Map<String,Object> map) {
		super();
		this.parameterBox = map;
		this.pageSize = pageSize;
		this.currentPage = currentPage;
	}

	public SearchBox() {
		this.parameterBox = new HashMap<String,Object>();
		this.pageSize = 20;
		this.currentPage = 1;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Map<String,Object> getParameterBox() {
		return parameterBox;
	}

	public void setParameterBox(Map<String,String[]> parameterBox) {
		clearParameterBox();
		Set<String> set = parameterBox.keySet();
		for (Iterator<String> iter = set.iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			this.parameterBox.put(key, parameterBox.get(key));
		}
	}
	
	
	public void setParameter(String key,Object value){
		parameterBox.put(key, value);
	}

	public String getParameter(String key) {
		String[] str = (String[]) parameterBox.get(key);
		if (str == null)
			return null;
		return StringUtils.trim(getVal(str));
	}

	
	public String[] getArrayParameter(String key){
		String[] str = (String[]) parameterBox.get(key);
		if (str == null)
			return null;
		return str;
	}
	
	public Long getLongParameter(String key) {
		String[] str = (String[]) parameterBox.get(key);
		return str == null ? null : getVal(str).equals("") ? null : Long
				.parseLong(getVal(str));
	}

	public int getIntParameter(String key) {
		String[] str = (String[]) parameterBox.get(key);
		return str == null ? 0 : Integer.parseInt(getVal(str).equals("") ? "0"
				: getVal(str));
	}

	public Integer getIntegerParameter(String key) {
		String[] str = (String[]) parameterBox.get(key);
		return str == null ? null : getVal(str).equals("") ? null : Integer
				.parseInt(getVal(str));
	}

	public void clearParameterBox() {
		parameterBox.clear();
	}

	public void clear() {
		clearParameterBox();
		this.pageSize = 10;
		this.currentPage = 1;
	}
	
	public String getVal(String[] str){
		String valueStr="";
		for (int i = 0; i < str.length; i++) {
			valueStr = (i == str.length - 1) ? valueStr + str[i]
					: valueStr + str[i] + ",";
		}
		return valueStr;
	}


	public String toString() {
		return "SearchBox [parameterBox=" + parameterBox + "]";
	}

}
