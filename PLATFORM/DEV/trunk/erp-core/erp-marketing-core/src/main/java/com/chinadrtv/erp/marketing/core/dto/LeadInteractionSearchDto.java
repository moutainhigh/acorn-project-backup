/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.marketing.core.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 2013-7-31 下午2:39:24
 * @version 1.0.0
 * @author yangfei
 *
 */
public class LeadInteractionSearchDto {
	private int lowCallDuration = -1;                           //通话时长下限
	private int highCallDuration = -1;                          //通话时长上限
	private String incomingLowDate;                             //通话日期下限
	private String incomingHighDate;                            //通话日期上限
	private String acdGroup;									//ACD组
	private String ani;											//主叫号码
	private String dnis;										//被叫号码
	private String userId;										//创建坐席ID
    private List<String> userIds;							    //批量查询座席id
	private String managerGrp;									//分配主管所在组,用作查询能分配的组
	private String contactId;									//客户ID
	private boolean isOrderRecordEmilationNeeded = true;		//是否需要排除成单数据
	private String deptId;
	private boolean isDeptIdNotNullNeeded = false;
	private boolean isDeDuplicatedNeeded = true;
//	private boolean isAllocatedRecordEmilationNeeded = false;	//是否排除已分配数据
	private List<Integer> allocatedNumbers = new ArrayList<Integer>();                           //已分配数量（默认是0）
	private List<Long> selectedInteractions = new ArrayList<Long>();
	private List<String> leadInteractionType = new ArrayList<String>();
	private List<String> callType = new ArrayList<String>();
	private List<String> callResult = new ArrayList<String>();
	private List<String> acdGroups = new ArrayList<String>();
	
	public int getLowCallDuration() {
		return lowCallDuration;
	}
	public void setLowCallDuration(int lowCallDuration) {
		this.lowCallDuration = lowCallDuration;
	}
	public int getHighCallDuration() {
		return highCallDuration;
	}
	public void setHighCallDuration(int highCallDuration) {
		this.highCallDuration = highCallDuration;
	}
	public String getIncomingLowDate() {
		return incomingLowDate;
	}
	public void setIncomingLowDate(String incomingLowDate) {
		this.incomingLowDate = incomingLowDate;
	}
	public String getIncomingHighDate() {
		return incomingHighDate;
	}
	public void setIncomingHighDate(String incomingHighDate) {
		this.incomingHighDate = incomingHighDate;
	}
	public String getAcdGroup() {
		return acdGroup;
	}
	
	public void setAcdGroup(String acdGroup) {
		if(StringUtils.isNotBlank(acdGroup) && acdGroup.contains(",")) {
			this.acdGroups = Arrays.asList(acdGroup.split(","));
		} else {
			this.acdGroup = acdGroup;
		}
	}
	
	public String getAni() {
		return ani;
	}
	public void setAni(String ani) {
		this.ani = ani;
	}
	public String getDnis() {
		return dnis;
	}
	public void setDnis(String dnis) {
		this.dnis = dnis;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<String> getUserIds() {
		return userIds;
	}
	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}
	public String getContactId() {
		return contactId;
	}
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
	public boolean isOrderRecordEmilationNeeded() {
		return isOrderRecordEmilationNeeded;
	}
	public void setOrderRecordEmilationNeeded(boolean isOrderRecordEmilationNeeded) {
		this.isOrderRecordEmilationNeeded = isOrderRecordEmilationNeeded;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public List<Integer> getAllocatedNumbers() {
		return allocatedNumbers;
	}
	public void setAllocatedNumbers(List<Integer> allocatedNumbers) {
		this.allocatedNumbers = allocatedNumbers;
	}
	public List<Long> getSelectedInteractions() {
		return selectedInteractions;
	}
	public void setSelectedInteractions(List<Long> selectedInteractions) {
		this.selectedInteractions = selectedInteractions;
	}
	public List<String> getLeadInteractionType() {
		return leadInteractionType;
	}
	public void setLeadInteractionType(List<String> leadInteractionType) {
		this.leadInteractionType = leadInteractionType;
	}
	public List<String> getCallType() {
		return callType;
	}
	public void setCallType(List<String> callType) {
		this.callType = callType;
	}
	public List<String> getCallResult() {
		return callResult;
	}
	public void setCallResult(List<String> callResult) {
		this.callResult = callResult;
	}
	public List<String> getAcdGroups() {
		return acdGroups;
	}
	public void setAcdGroups(List<String> acdGroups) {
		this.acdGroups = acdGroups;
	}
	public String getManagerGrp() {
		return managerGrp;
	}
	public void setManagerGrp(String managerGrp) {
		this.managerGrp = managerGrp;
	}
	public boolean isDeptIdNotNullNeeded() {
		return isDeptIdNotNullNeeded;
	}
	public void setDeptIdNotNullNeeded(boolean isDeptIdNotNullNeeded) {
		this.isDeptIdNotNullNeeded = isDeptIdNotNullNeeded;
	}
	public boolean isDeDuplicatedNeeded() {
		return isDeDuplicatedNeeded;
	}
	public void setDeDuplicatedNeeded(boolean isDeDuplicatedNeeded) {
		this.isDeDuplicatedNeeded = isDeDuplicatedNeeded;
	}
	
	public static void main(String[] args) {
		LeadInteractionSearchDto lisd = new LeadInteractionSearchDto();
		lisd.setAcdGroup("sh1,sh2,sh3");
		System.out.println(lisd.getAcdGroup());
		System.out.println(lisd.getAcdGroups());
		lisd.setAcdGroup("sh1234sh2sh3");
	}
}
