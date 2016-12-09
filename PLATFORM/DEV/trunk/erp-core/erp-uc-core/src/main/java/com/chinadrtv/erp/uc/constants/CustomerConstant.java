/*
 * @(#)CustomerConstant.java 1.0 2013-5-9下午3:30:20
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.constants;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013-5-9 下午3:30:20 
 * 
 */
public class CustomerConstant {

	/*客户来源*/
	public static final String OB_CUSTOMER_TYPE = "OB_CUSTOMER_TYPE";
	/*老客户*/
	public static final int OB_CUSTOMER_TYPE_OLD = 1;
	/*自取客户*/
	public static final int OB_CUSTOMER_TYPE_SELF = 2;
	/*历史成单客户*/
	public static final int OB_CUSTOMER_TYPE_ORDER = 3;
	
	
	/*客户归属*/
	public static final String CUSTOMER_OWNER = "CUSTOMER_OWNER";
	/*专属客户*/
	public static final int CUSTOMER_OWNER_DEDICATED = 1;
	/*锁定*/
	public static final int CUSTOMER_OWNER_LOCKED = 2;
	/*可用*/
	public static final int CUSTOMER_OWNER_AVALIABLE = 3;
	
	
	/*联系结果*/
	public static final String CALL_RESULT = "CALL_RESULT";
	/*再联系*/
	public static final int CALL_RESULT_CALLBACK = 1;
	/*不再联系*/
	public static final int CALL_RESULT_DENY = 2;
	/*成单*/
	public static final int CALL_RESULT_SUCCESS = 3;
	
	/*电话类型*/
	public static final int PHONE_TYPE_HOME = 1;
	public static final int PHONE_TYPE_OFFICE = 2;
	public static final int PHONE_TYPE_BP = 3;
	public static final int PHONE_TYPE_CELL = 4;
	public static final int PHONE_TYPE_FAX = 5;
	public static final int PHONE_TYPE_UNKNOWN = 6;

    /*进线类型*/
    public static final String INBOUND_TYPE = "IN";
    public static final String OUTBOUNR_TYPE = "OUT";

    /*客户审批状态*/
    public static final int CUSTOMER_AUDIT_STATUS_UNAUDITED = 0;
    public static final int CUSTOMER_AUDIT_STATUS_AUDITING = 1;
    public static final int CUSTOMER_AUDIT_STATUS_AUDITED = 2;
    public static final int CUSTOMER_AUDIT_STATUS_REJECTED = 3;
}
