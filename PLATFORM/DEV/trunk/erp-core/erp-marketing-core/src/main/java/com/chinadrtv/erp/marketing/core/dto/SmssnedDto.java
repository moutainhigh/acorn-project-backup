/*
 * @(#)SmssnedDto.java 1.0 2013-3-8下午2:20:29
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.dto;

import java.lang.reflect.Field;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-3-8 下午2:20:29
 * 
 */
public class SmssnedDto {

	private String maxsend;
	// 分段
	private String timescope;
	// 分段开始时间
	private String starttime;
	// 分段结束时间
	private String endtime;
	// 客户群
	private String customers;
	// 模板
	private String template;
	// 优先级
	private String priority;
	// 移动渠道
	private String cmcc;
	// 联通渠道
	private String cucc;
	// 电信渠道
	private String cha;
	// 接收回复内容
	private String isreply;
	// 及时反馈客户回执
	private String realtime;
	// 是否允许转通道
	private String allowChannel;
	// 排除短信黑名单
	private String blackListFilter;
	// 多个号码时，只发一个号码，默认主号码
	private String mainNum;
	// 指定通道
	private String checkBoxs;
	// 开始时间
	private String stime;
	// 结束时间
	private String etime;
	// 创建时间
	private String createTime;
	// 批次号
	private String batchId;

	// 客户群名称
	private String groupName;
	// 客户群编码
	private String groupCode;
	// 客户群编码
	private String smsName;

	// 部门号
	private String department;
	// 创建者
	private String creator;
	// 短信停止状态
	private String smsstopStatus;

	private String sendStatus;

	// 静态变量名集合
	private String varNames;
	// 静态变量值集合
	private String varValues;

	private String templateId;
	private String templateTitle;

	private String dynamicTemplate;

	private Long receiveCount;
	private Long recordcount;

	public SmssnedDto() {
		super();
	}

	/**
	 * @return the maxsend
	 */
	public String getMaxsend() {
		return maxsend;
	}

	/**
	 * @param maxsend
	 *            the maxsend to set
	 */
	public void setMaxsend(String maxsend) {
		this.maxsend = maxsend;
	}

	/**
	 * @return the timescope
	 */
	public String getTimescope() {
		return timescope;
	}

	/**
	 * @param timescope
	 *            the timescope to set
	 */
	public void setTimescope(String timescope) {
		this.timescope = timescope;
	}

	/**
	 * @return the starttime
	 */
	public String getStarttime() {
		return starttime;
	}

	/**
	 * @param starttime
	 *            the starttime to set
	 */
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	/**
	 * @return the endtime
	 */
	public String getEndtime() {
		return endtime;
	}

	/**
	 * @param endtime
	 *            the endtime to set
	 */
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	/**
	 * @return the customers
	 */
	public String getCustomers() {
		return customers;
	}

	/**
	 * @param customers
	 *            the customers to set
	 */
	public void setCustomers(String customers) {
		this.customers = customers;
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
	 * @return the priority
	 */
	public String getPriority() {
		return priority;
	}

	/**
	 * @param priority
	 *            the priority to set
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}

	/**
	 * @return the cmcc
	 */
	public String getCmcc() {
		return cmcc;
	}

	/**
	 * @param cmcc
	 *            the cmcc to set
	 */
	public void setCmcc(String cmcc) {
		this.cmcc = cmcc;
	}

	/**
	 * @return the cucc
	 */
	public String getCucc() {
		return cucc;
	}

	/**
	 * @param cucc
	 *            the cucc to set
	 */
	public void setCucc(String cucc) {
		this.cucc = cucc;
	}

	/**
	 * @return the cha
	 */
	public String getCha() {
		return cha;
	}

	/**
	 * @param cha
	 *            the cha to set
	 */
	public void setCha(String cha) {
		this.cha = cha;
	}

	/**
	 * @return the isreply
	 */
	public String getIsreply() {
		return isreply;
	}

	/**
	 * @param isreply
	 *            the isreply to set
	 */
	public void setIsreply(String isreply) {
		this.isreply = isreply;
	}

	/**
	 * @return the realtime
	 */
	public String getRealtime() {
		return realtime;
	}

	/**
	 * @param realtime
	 *            the realtime to set
	 */
	public void setRealtime(String realtime) {
		this.realtime = realtime;
	}

	/**
	 * @return the allowChannel
	 */
	public String getAllowChannel() {
		return allowChannel;
	}

	/**
	 * @param allowChannel
	 *            the allowChannel to set
	 */
	public void setAllowChannel(String allowChannel) {
		this.allowChannel = allowChannel;
	}

	/**
	 * @return the blackListFilter
	 */
	public String getBlackListFilter() {
		return blackListFilter;
	}

	/**
	 * @param blackListFilter
	 *            the blackListFilter to set
	 */
	public void setBlackListFilter(String blackListFilter) {
		this.blackListFilter = blackListFilter;
	}

	/**
	 * @return the mainNum
	 */
	public String getMainNum() {
		return mainNum;
	}

	/**
	 * @param mainNum
	 *            the mainNum to set
	 */
	public void setMainNum(String mainNum) {
		this.mainNum = mainNum;
	}

	/**
	 * @return the checkBoxs
	 */
	public String getCheckBoxs() {
		return checkBoxs;
	}

	/**
	 * @param checkBoxs
	 *            the checkBoxs to set
	 */
	public void setCheckBoxs(String checkBoxs) {
		this.checkBoxs = checkBoxs;
	}

	/**
	 * @return the stime
	 */
	public String getStime() {
		return stime;
	}

	/**
	 * @param stime
	 *            the stime to set
	 */
	public void setStime(String stime) {
		this.stime = stime;
	}

	/**
	 * @return the etime
	 */
	public String getEtime() {
		return etime;
	}

	/**
	 * @param etime
	 *            the etime to set
	 */
	public void setEtime(String etime) {
		this.etime = etime;
	}

	/**
	 * @return the createTime
	 */
	public String getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the batchId
	 */
	public String getBatchId() {
		return batchId;
	}

	/**
	 * @param batchId
	 *            the batchId to set
	 */
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName
	 *            the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the groupCode
	 */
	public String getGroupCode() {
		return groupCode;
	}

	/**
	 * @param groupCode
	 *            the groupCode to set
	 */
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	/**
	 * @return the smsName
	 */
	public String getSmsName() {
		return smsName;
	}

	/**
	 * @param smsName
	 *            the smsName to set
	 */
	public void setSmsName(String smsName) {
		this.smsName = smsName;
	}

	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * @param department
	 *            the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * @return the creator
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * @param creator
	 *            the creator to set
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * @return the smsstopStatus
	 */
	public String getSmsstopStatus() {
		return smsstopStatus;
	}

	/**
	 * @param smsstopStatus
	 *            the smsstopStatus to set
	 */
	public void setSmsstopStatus(String smsstopStatus) {
		this.smsstopStatus = smsstopStatus;
	}

	/**
	 * @return the sendStatus
	 */
	public String getSendStatus() {
		return sendStatus;
	}

	/**
	 * @param sendStatus
	 *            the sendStatus to set
	 */
	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}

	/**
	 * @return the varNames
	 */
	public String getVarNames() {
		return varNames;
	}

	/**
	 * @param varNames
	 *            the varNames to set
	 */
	public void setVarNames(String varNames) {
		this.varNames = varNames;
	}

	/**
	 * @return the varValues
	 */
	public String getVarValues() {
		return varValues;
	}

	/**
	 * @param varValues
	 *            the varValues to set
	 */
	public void setVarValues(String varValues) {
		this.varValues = varValues;
	}

	/**
	 * 获取属性值
	 * 
	 * @Description: TODO
	 * @param property
	 * @return
	 * @return String
	 * @throws
	 */
	public String genParamValues(String property) {
		String result = "";
		Field[] fields = this.getClass().getDeclaredFields();
		try {
			for (Field f : fields) {
				if (f.getName().equals(property)) {
					result = f.get(this).toString();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @return the templateId
	 */
	// @Column(name = "templateId")
	public String getTemplateId() {
		return templateId;
	}

	/**
	 * @param templateId
	 *            the templateId to set
	 */
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	/**
	 * @return the templateTitle
	 */
	// @Column(name = "templateTitle")
	public String getTemplateTitle() {
		return templateTitle;
	}

	/**
	 * @param templateTitle
	 *            the templateTitle to set
	 */
	public void setTemplateTitle(String templateTitle) {
		this.templateTitle = templateTitle;
	}

	/**
	 * 设置属性值
	 * 
	 * @Description: TODO
	 * @param property
	 * @return
	 * @return String
	 * @throws
	 */
	public void setParamValues(Object obj, String property, String value) {
		Field[] fields = this.getClass().getDeclaredFields();
		try {
			for (Field f : fields) {
				if (f.getName().equals(property)) {
					f.set(obj, value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the dynamicTemplate
	 */
	public String getDynamicTemplate() {
		return dynamicTemplate;
	}

	/**
	 * @param dynamicTemplate
	 *            the dynamicTemplate to set
	 */
	public void setDynamicTemplate(String dynamicTemplate) {
		this.dynamicTemplate = dynamicTemplate;
	}

	/**
	 * @return the receiveCount
	 */
	public Long getReceiveCount() {
		return receiveCount;
	}

	/**
	 * @param receiveCount
	 *            the receiveCount to set
	 */
	public void setReceiveCount(Long receiveCount) {
		this.receiveCount = receiveCount;
	}

	/**
	 * @return the recordcount
	 */
	public Long getRecordcount() {
		return recordcount;
	}

	/**
	 * @param recordcount the recordcount to set
	 */
	public void setRecordcount(Long recordcount) {
		this.recordcount = recordcount;
	}
}
