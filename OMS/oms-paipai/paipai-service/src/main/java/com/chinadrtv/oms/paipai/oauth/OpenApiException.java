package com.chinadrtv.oms.paipai.oauth;


public class OpenApiException extends Exception {
	
	private static final long serialVersionUID = 8243127099991355146L;
	private int code;
	
	/**
	 * �������Ϊ�ա�
	 */
	public final static int PARAMETER_EMPTY = 2001;
	
	/**
	 * ���������Ч��
	 */
	public final static int PARAMETER_INVALID = 2002;
	
	/**
	 * ��������Ӧ�����Ч��
	 */
	public final static int RESPONSE_DATA_INVALID = 2003;
	
	/**
	 * ���ǩ��ʧ�ܡ�
	 */
	public final static int MAKE_SIGNATURE_ERROR = 2004;

	/**
	 * �������
	 */
	public final static int NETWORK_ERROR = 3000;
	
	
	public OpenApiException(int code, String msg) {
		super(msg);
		this.code = code;
	}

	public OpenApiException(int code, Exception ex) {
		super(ex);
		this.code = code;
	}
	
	public int getErrorCode() {
		return code;
	}



}
