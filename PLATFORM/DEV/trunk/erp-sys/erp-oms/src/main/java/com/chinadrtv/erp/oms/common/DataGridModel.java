package com.chinadrtv.erp.oms.common;

/**
 * 对easyui的datagrid控件分页参数分装
 * @author zhaizhanyi
 *
 */
public class DataGridModel {

	private int page;// 当前页
	private int rows = 10;// 当前页条数
	private int count;// 总记录数
	private int startRow;// 开始行

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getStartRow() {
		return rows*(page-1);
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

}
