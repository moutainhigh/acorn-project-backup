/**
 * 
 */
package com.chinadrtv.uam.model.config;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.chinadrtv.uam.model.BaseEntity;

/**
 * @author dengqianyong
 *
 */
@Entity
@Table(name = "UAM_CONFIG", schema = "ACOAPP_UAM")
public class Config extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6320177267389642634L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_CONFIG")
	@SequenceGenerator(name = "GEN_CONFIG", schema = "ACOAPP_UAM", 
		sequenceName = "SEQ_CONFIG", allocationSize = 1)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "NAME", length = 128)
	private String name;
	
	@Column(name = "IS_ENABLED", nullable = false)
	private boolean enabled = true;
	
	@ManyToOne
	@JoinColumn(name = "CONFIG_GROUP_ID")
	private ConfigGroup configGroup;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "config", orphanRemoval = true)
	private Set<ConfigProperty> configProperties;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public ConfigGroup getConfigGroup() {
		return configGroup;
	}

	public void setConfigGroup(ConfigGroup configGroup) {
		this.configGroup = configGroup;
	}

	public Set<ConfigProperty> getConfigProperties() {
		return configProperties;
	}

	public void setConfigProperties(Set<ConfigProperty> configProperties) {
		this.configProperties = configProperties;
	}
	
}
