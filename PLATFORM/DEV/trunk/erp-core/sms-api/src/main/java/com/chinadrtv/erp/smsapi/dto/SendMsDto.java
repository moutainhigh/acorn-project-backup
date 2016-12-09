/*
 * @(#)SendMsDto.java 1.0 2013-2-18下午1:34:07
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-2-18 下午1:34:07
 * 
 */
@XStreamAlias("")
public class SendMsDto implements Serializable {

	private String uuid;// 唯一id
	private String timestamp;// 时间戳
	private String batchId;// 批次号
	private Long recordCount;// 总发送数量
	private String department;// 部门号
	private String fileName;// 发送短信csv文件名
	private Map<String, String> templateMap;// 模板参数
	private String smscontent;// 短信内容
	private Long smsType;// 粒度 单条为1 批量为2
	private Map<String, String> channelMap;// 通道号列表
	private String allowChangeChannel;// 是否允许转通道：Y/N
	private String startTime;// 开始时间
	private String endTime;// 结束时间
	private List<Map<String, Object>> timeScopes;//
	private Long priority;//
	private String isReply;//
	private String realTime;//
	private String signiture;//
	private String secureHash;//

}
