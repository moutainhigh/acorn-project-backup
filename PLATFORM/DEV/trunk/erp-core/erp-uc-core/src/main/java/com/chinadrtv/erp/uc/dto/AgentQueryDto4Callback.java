/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.uc.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 2013-8-9 下午3:41:08
 * @version 1.0.0
 * @author yangfei
 *
 */
public class AgentQueryDto4Callback {
	private String lowDate; 
	private String highDate;
	private String level;
	private List<String> levels = new ArrayList<String>();
	private boolean isAbNormalShowingRequired = false;
	private String agentId;
	private List<String> groups;
	/**
	 * cType with value in[1,2,3,4,5]
	 * 	IVR("IVR",1),
		GIVEUP("放弃",2),
		CONNECTED("接通",3),
		VIRTUAL_PHONECALL("虚拟进线", 4),
		CALLBACK("Callback",5);
	 */
	private String cType;
	
	public String getLowDate() {
		return lowDate;
	}
	public void setLowDate(String lowDate) {
		this.lowDate = lowDate;
	}
	public String getHighDate() {
		return highDate;
	}
	public void setHighDate(String highDate) {
		this.highDate = highDate;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
/*		if(StringUtils.isBlank(level)) {
			return;
		}
		if(!level.contains(",")) {
			this.level = level;
		} else {
			String[] levels = level.split(",");
			this.levels=Arrays.asList(levels);
		}*/
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public List<String> getGroups() {
		return groups;
	}
	public void setGroups(List<String> groups) {
		this.groups = groups;
	}
	public String getcType() {
		return cType;
	}
	public void setcType(String cType) {
		this.cType = cType;
	}
	public List<String> getLevels() {
		return levels;
	}
	public void setLevels(List<String> levels) {
		this.levels = levels;
	}
	public boolean isAbNormalShowingRequired() {
		return isAbNormalShowingRequired;
	}
	public void setAbNormalShowingRequired(boolean isAbNormalShowingRequired) {
		this.isAbNormalShowingRequired = isAbNormalShowingRequired;
	}
}
