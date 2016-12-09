/**
 * 
 */
package com.chinadrtv.uam.model.uam;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.chinadrtv.uam.model.BaseEntity;
import com.chinadrtv.uam.model.auth.Role;

/**
 * UAM 系统用户
 * @author dengqianyong
 *
 */
@Entity
@Table(name = "UAM_SYS_USER", schema = "ACOAPP_UAM")
public class SysUser extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5274985881538328774L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SY_USER_GENERATOR")
	@SequenceGenerator(name = "SY_USER_GENERATOR", schema = "ACOAPP_UAM", 
		sequenceName = "SEQ_USER", allocationSize = 1, initialValue = 1000000001)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "SIGN_NAME", length = 20, nullable = false)
	private String signName;
	
	@Column(name = "SIGN_PASS", length = 32, nullable = false)
	private String signPass;
	
	@Column(name = "USER_NAME", length = 20, nullable = false)
	private String userName;
	
	@Column(name = "IS_ENABLED", nullable = false)
	private boolean enabled = true;
	
	@ManyToMany
	@JoinTable(
		name = "UAM_SYS_USER_ROLE", schema = "ACOAPP_UAM",
		joinColumns = @JoinColumn(name = "SYS_USER_ID"), 
		inverseJoinColumns = @JoinColumn(name = "ROLE_ID")
	)
	private Set<Role> roles = new LinkedHashSet<Role>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}

	public String getSignPass() {
		return signPass;
	}

	public void setSignPass(String signPass) {
		this.signPass = signPass;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}
