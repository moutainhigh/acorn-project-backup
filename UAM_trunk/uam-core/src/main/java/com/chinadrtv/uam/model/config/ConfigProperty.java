/**
 * 
 */
package com.chinadrtv.uam.model.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.chinadrtv.uam.model.BaseEntity;

/**
 * @author dengqianyong
 *
 */
@Entity
@Table(name = "UAM_CONFIG_PROPERTY", schema = "ACOAPP_UAM")
public class ConfigProperty extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 256065644339696055L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_CONFIG_PROPERTY")
	@SequenceGenerator(name = "GEN_CONFIG_PROPERTY", schema = "ACOAPP_UAM", 
		sequenceName = "SEQ_CONFIG", allocationSize = 1)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "CONFIG_KEY", length = 128, nullable = false)
	private String key;
	
	@Column(name = "CONFIG_VALUE", length = 256, nullable = false)
	private String value;
	
	@ManyToOne
	@JoinColumn(name = "CONFIG_ID")
	private Config config;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}
	
}
