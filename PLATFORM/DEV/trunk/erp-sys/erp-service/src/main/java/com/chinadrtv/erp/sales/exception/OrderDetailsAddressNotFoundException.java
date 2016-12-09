package com.chinadrtv.erp.sales.exception;

public class OrderDetailsAddressNotFoundException extends Exception implements
		ExceptionErrorTitle {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6841332462998973746L;

	private static final String ERROR_TITLE = "历史订单找不到地址信息，无法查询";

	public OrderDetailsAddressNotFoundException() {
		super();
	}

	public OrderDetailsAddressNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public OrderDetailsAddressNotFoundException(String message) {
		super(message);
	}

	public OrderDetailsAddressNotFoundException(Throwable cause) {
		super(cause);
	}

	@Override
	public String getErrorTtile() {
		return ERROR_TITLE;
	}
}
