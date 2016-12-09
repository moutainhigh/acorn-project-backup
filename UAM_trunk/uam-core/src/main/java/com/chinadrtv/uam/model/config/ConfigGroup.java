/**
 * 
 */
package com.chinadrtv.uam.model.config;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.chinadrtv.uam.model.BaseEntity;

/**
 * @author dengqianyong
 *
 */
@Entity
@Table(name = "UAM_CONFIG_GROUP", schema = "ACOAPP_UAM")
public class ConfigGroup extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7463696824849341616L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_CONFIG_GROUP")
	@SequenceGenerator(name = "GEN_CONFIG_GROUP", schema = "ACOAPP_UAM", 
		sequenceName = "SEQ_CONFIG", allocationSize = 1)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "GROUP_NAME", length = 50, nullable = false)
	private String groupName;
	
	@Column(name = "DESCRITION", length = 256, nullable = true)
	private String descrition;
	
	@Column(name = "IS_ENABLED", nullable = false)
	private boolean enabled = true;
	
	@OneToMany(mappedBy = "configGroup")
	private Set<Config> configs = new LinkedHashSet<Config>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getDescrition() {
		return descrition;
	}

	public void setDescrition(String descrition) {
		this.descrition = descrition;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Config> getConfigs() {
		return configs;
	}

	public void setConfigs(Set<Config> configs) {
		this.configs = configs;
	}

}
