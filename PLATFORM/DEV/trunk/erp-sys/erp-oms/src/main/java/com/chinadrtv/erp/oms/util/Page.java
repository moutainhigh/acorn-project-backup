package com.chinadrtv.erp.oms.util;

import java.util.List;

/**
 * 分页数据
 * @author zhangguosheng
 * @param <T>
 */
public class Page<T> {
	
	private long totalElements;
	private List<T> content;
	
	public Page(List<T> content, long totalElements){
		this.content = content;
		this.totalElements = totalElements;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

}
