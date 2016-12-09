package com.chinadrtv.erp.task.core.view;

import com.chinadrtv.erp.task.core.enums.ViewDataStatus;

/**
 * 页面数据对象
 * @author zhangguosheng
 */
public class ViewData {

	/**
	 * 请求返回状态
	 */
	private ViewDataStatus status = ViewDataStatus.SUCCESS;
	
	/**
	 * 请求返回信息
	 */
	private String msg;
	
	/**
	 * 请求返回数据
	 */
	private Object data;
	
	public ViewData(ViewDataStatus status, String msg, Object data){
		this.status = status;
		this.msg = msg;
		this.data = data;
	}
	
	public ViewDataStatus getStatus() {
		return status;
	}
	public void setStatus(ViewDataStatus status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

}
