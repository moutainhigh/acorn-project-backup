/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.user.dto;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * 2013-8-1 上午11:07:29
 * @version 1.0.0
 * @author yangfei
 *
 */
public class AgentUserInfo4TeleDist {
	private String userWorkGrp;
	private String userWorkGrpName;
	private String userId;
	private String userName;
	private String levelId;
	private String levelId2;
	private String levelId3;
	private boolean isLevelNormal = false;
	
	private static Set<String> normalLevels = new HashSet<String>();
	
	static {
		normalLevels.add("A");
		normalLevels.add("B");
		normalLevels.add("C");
		normalLevels.add("D");
	}
	
	/**
	 * 当天进线数
	 */
	private int intradayInlineCount;
	
	/**
	 * 指定时间内已执行接通话务数量
	 */
	private int intradayAllocatedCount;
	
	/**
	 * 指定时间内已分配接通话务数量
	 */
	private int intradayExecutedCount;
	
	public String getUserWorkGrp() {
		return userWorkGrp;
	}
	public void setUserWorkGrp(String userWorkGrp) {
		this.userWorkGrp = userWorkGrp;
	}
	public String getUserWorkGrpName() {
		return userWorkGrpName;
	}
	public void setUserWorkGrpName(String userWorkGrpName) {
		this.userWorkGrpName = userWorkGrpName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLevelId() {
		return levelId;
	}
	public void setLevelId(String levelId) {
		this.levelId = levelId;
		
		if(StringUtils.isNotBlank(levelId) && normalLevels.contains(levelId.toUpperCase())) {
			this.isLevelNormal = true;
		}
	}
	public String getLevelId2() {
		return levelId2;
	}
	public void setLevelId2(String levelId2) {
		this.levelId2 = levelId2;
		if(StringUtils.isNotBlank(levelId2) && normalLevels.contains(levelId2.toUpperCase())) {
			this.isLevelNormal = true;
		}
	}
	public String getLevelId3() {
		return levelId3;
	}
	public void setLevelId3(String levelId3) {
		this.levelId3 = levelId3;
		if(StringUtils.isNotBlank(levelId3) && normalLevels.contains(levelId3.toUpperCase())) {
			this.isLevelNormal = true;
		}
	}
	public int getIntradayInlineCount() {
		return intradayInlineCount;
	}
	public void setIntradayInlineCount(int intradayInlineCount) {
		this.intradayInlineCount = intradayInlineCount;
	}
	public int getIntradayAllocatedCount() {
		return intradayAllocatedCount;
	}
	public void setIntradayAllocatedCount(int intradayAllocatedCount) {
		this.intradayAllocatedCount = intradayAllocatedCount;
	}
	public int getIntradayExecutedCount() {
		return intradayExecutedCount;
	}
	public void setIntradayExecutedCount(int intradayExecutedCount) {
		this.intradayExecutedCount = intradayExecutedCount;
	}
	public boolean isLevelNormal() {
		return isLevelNormal;
	}
	public void setLevelNormal(boolean isLevelNormal) {
		this.isLevelNormal = isLevelNormal;
	}
	
}
