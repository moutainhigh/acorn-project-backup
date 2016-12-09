/**
 * 
 */
package com.chinadrtv.uam.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.chinadrtv.uam.model.config.ConfigProperty;

/**
 * @author dengqianyong
 *
 */
public class ConfigDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3334773598169961868L;

	private Long groupId;
	
	private String groupName;
	
	private String gruopDesc;
	
	private Long configId;
	
	private String configName;
	
	private List<ConfigProperty> properties = new ArrayList<ConfigProperty>();

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGruopDesc() {
		return gruopDesc;
	}

	public void setGruopDesc(String gruopDesc) {
		this.gruopDesc = gruopDesc;
	}

	public Long getConfigId() {
		return configId;
	}

	public void setConfigId(Long configId) {
		this.configId = configId;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public List<ConfigProperty> getProperties() {
		return properties;
	}

	public void setProperties(List<ConfigProperty> properties) {
		this.properties = properties;
	}
	
}
