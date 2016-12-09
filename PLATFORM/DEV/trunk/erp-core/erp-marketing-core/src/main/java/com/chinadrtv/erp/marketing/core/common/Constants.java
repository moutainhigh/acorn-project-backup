/*
 * @(#)Constants.java 1.0 2013-1-25下午2:56:27
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.common;

import java.util.HashMap;
import java.util.Map;

import com.chinadrtv.erp.smsapi.dto.Result;

/**
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-25 下午2:56:27
 * 
 */
public class Constants {

	public final static String JOB_GROUP_CUSTOMER_GROUP = "JOB_GROUP_CUSTOMER_GROUP";
	public final static String JOB_GROUP_SMS_SEND = "JOB_GROUP_SMS_SEND";
	public final static String JOB_GROUP_CAMPAIGN = "JOB_GROUP_CAMPAIGN";
	public final static String JOB_NAME_CAMPAIGN_PREFIX = "CAMPAIGN_JOB_";

	public final static String ASSIGNTYPE_LST_PAID_ORDR_DATE = "1";
	public final static String ASSIGNTYPE_FST_PAID_ORDR_DATE = "2";
	public final static String ASSIGNTYPE_LST_CALLOUT_DATE = "3";
	public final static String ASSIGNTYPE_FST_CALLOUT_DATE = "4";
	public final static String ASSIGNTYPE_ACC_PAID_ORDR_QTY_MAX = "5";
	public final static String ASSIGNTYPE_ACC_PAID_ORDR_QTY_MIN = "6";
	public final static String ASSIGNTYPE_ACC_PAID_ORDR_AMT_MAX = "7";
	public final static String ASSIGNTYPE_ACC_PAID_ORDR_AMT_MIN = "8";

	public static final String TRIGGERNAME = "triggerName";
	public static final String TRIGGERGROUP = "triggerGroup";
	public static final String STARTTIME = "startTime";
	public static final String ENDTIME = "endTime";
	public static final String REPEATCOUNT = "repeatCount";
	public static final String REPEATINTERVEL = "repeatInterval";

	// 营销计划状态
	public static final String CAMPAIGN_ACTIVE = "0";
	public static final String CAMPAIGN_COMPLETE = "1";
	public static final String CAMPAIGN_SCHEDULE = "2";
	public static final String CAMPAIGN_STOP = "3";
	public static final String CAMPAIGN_COUPON = "5";

	// 营销计划类型
	public static final int CAMPAIGN_TYPE_SMS = 1;// 短信
	public static final int CAMPAIGN_TYPE_OUTBOUND = 2;// 外呼自助取数
	public static final int CAMPAIGN_TYPE_SCMDRTV = 3;// SCM媒体
	public static final Long CAMPAIGN_TYPE_CUSTOME_TASK = 4l;//主键任务
	public static final int CAMPAIGN_TYPE_COUPON = 5;// 优惠券
	public static final int CAMPAIGN_TYPE_ASSIGN = 6;// 外呼人工分配

	// 客户类型
	// 正式客户
	public static final String CUSTOMER_TYPE_CONTACT = "1";
	// 潜客
	public static final String CUSTOMER_TYPE_LATENTCONTACT = "2";

	// 老客户组
	public static final String CAMPAIGN_OLD_GROUP = "V000000000";
	public static final String CAMPAIGN_OLD_GROUP_NAME = "老客户组";

	// 系统默认虚拟营销计划id CAMPAIGN_VIRTUAL_ID
	public static final Long CAMPAIGN_VIRTUAL_ID = 1l;

	// LEAD_TYPE类别
	public static final String LEAD_TYPE_CAMPAIGN = "CAMPAIGN";
	public static final String LEAD_TYPE_CONTACT = "CONTACT";
	public static final String LEAD_TYPE_REPEAT = "REPEAT";

	

	public static final String SCHEDULE_TYPE_ATONCE = "0";
	public static final String SCHEDULE_TYPE_TIMING = "5";

	public static final String CAMPAIGN_BATCH_STATUS_NORMAL = "0";
	public static final String CAMPAIGN_BATCH_STATUS_FINISH = "1";

	public static final String SMS_STOP_STATUS_SUCCESS = "1";
	public static final String SMS_STOP_STATUS_RECOVER = "2";

	public static final String RESPONSE_STATUS_SUCCESS = "1";
	public static final String RESPONSE_STATUS_ERROR = "-1";
	// LEAD_TASK STATUS
	public static final String LEAD_TASK_ACTIVE = "0";
	public static final String LEAD_TASK_STOP = "1";

	public static final String CHNAGE_PROCESS_DEF_NAME = "changeRequest";
	/*
	 * public static final String USER_TASK_UNASSIGNED = "0"; public static
	 * final String USER_TASK_APPROEED = "1"; public static final String
	 * USER_TASK_REJECTED = "2"; public static final String USER_TASK_CANCELED =
	 * "3";
	 */
	public static final String INTERACTION_TYPE_PHONE = "PHONE";
	public static final String INTERACTION_TYPE_SMS = "SMS";
	public static final String MARKETING_SCHEMA = "ACOAPP_MARKETING";
	public static final String IAGENT_SCHEMA = "IAGENT";

	public static final String OBCONTACT_STATUS_UNASSIGN = "0";
	public static final String OBCONTACT_STATUS_ASSIGNED = "-1";
	public static final String OBCONTACT_STATUS_LOCKED = "1";

	public static final int OBCONTACT_GET_NONE = 0;
	public static final int OBCONTACT_GET_ERROR = -1;
	public static final int OBCONTACT_GET_SUCCESS = 1;

	public static final long maxQueryTimeRange = 180 * 24 * 3600 * 1000L;

	public static final Map<String, String> status = new HashMap<String, String>();
	static {
		status.put("ACQUIRED", "运行中");
		status.put("PAUSED", "暂停中");
		status.put("WAITING", "等待中");
	}
	// 过滤敏感词
	public static String xml = "";

	public static Result result;

	// 取数数据来源
	// 正式客户
	public static final String OBCONTACT_DATASOURCE_CONTACT = "CONTACT";
	// 潜客
	public static final String OBCONTACT_DATASOURCE_LATENTCONTACT = "LATENTCONTACT";

	// Campaign Receiver 分配状态
	public static final String CAMPAIGN_RECEIVER_STATUS_UNASSIGNED = "0";
	public static final String CAMPAIGN_RECEIVER_STATUS_ASSIGNED_GROUP = "1";
	public static final String CAMPAIGN_RECEIVER_STATUS_ASSIGNED_AGENT = "2";
	public static final String CAMPAIGN_RECEIVER_STATUS_EXECUTED = "3";
	
	public static final String SEQUENCIAL_ALLOCATION = "order";
	public static final String CYCLE_ALLOCATION = "cycle";
}
