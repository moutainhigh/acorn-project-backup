package com.chinadrtv.erp.user.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.chinadrtv.erp.user.ldap.AcornRole;

/**
 * User: liuhaidong
 * Date: 12-11-19
 */
@Entity
@Table(name = "USR", schema = "IAGENT")
public class AgentUser  implements Serializable, UserDetails {
    
	private static final long serialVersionUID = -2146357249921961190L;

	private String userId;

    private String name;

    private String password;

    private String title;

    private String defGrp;

    private String adcGroup;

    private String valid;

    private Date lastDt;

    private String workGrp;

    private String department;
    
    private String displayName;//中文名
    
    private String employeeType;//坐席类型等级
    
    @Transient
    private String acornLastTime;	//最后登陆时间
    
    @Id
    @Column(name = "USRID")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "DEFGRP")
    public String getDefGrp() {
        return defGrp;
    }

    public void setDefGrp(String defGrp) {
        this.defGrp = defGrp;
    }

    @Column(name = "ACDGROUP")
    public String getAdcGroup() {
        return adcGroup;
    }

    public void setAdcGroup(String adcGroup) {
        this.adcGroup = adcGroup;
    }

    @Column(name = "VALID")
    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    @Column(name = "LASTDT")
    public Date getLastDt() {
        return lastDt;
    }

    public void setLastDt(Date lastDt) {
        this.lastDt = lastDt;
    }

    @Column(name = "WORKGRP")
    public String getWorkGrp() {
        return workGrp;
    }

    public void setWorkGrp(String workGrp) {
        this.workGrp = workGrp;
    }


    public void setAgentRoles(Set<AgentRole> agentRoles) {
        this.agentRoles = agentRoles;
    }

    private Set<AgentRole> agentRoles = new TreeSet<AgentRole>();
    
    @Transient
    public Set<AgentRole> loadAgentRoles() {
    	return agentRoles;
    }
    
    @Transient
    private Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //return agentRoles;
    	return authorities;
    }
    
    @Transient
    public void grantAuthorities(Set<AcornRole> roleSet) {
        for (AcornRole role : roleSet) { 
        	authorities.add(new SimpleGrantedAuthority(role.getName()));
        }    
    }  

    @Transient
    public boolean hasRole(String roleName){
    	
    	/*Collection<? extends GrantedAuthority> roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    	SimpleGrantedAuthority ga = new SimpleGrantedAuthority(roleName);
    	return roles.contains(ga);*/
    	
    	SimpleGrantedAuthority ga = new SimpleGrantedAuthority(roleName);
    	return authorities.contains(ga);
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }

    @Transient
    public String getUsername() {
        return userId;
    }

    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Transient
    public boolean isAccountNonLocked() {
        return true;
    }

    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Transient
    public boolean isEnabled() {
        return (valid != null && Boolean.parseBoolean(valid));
    }

	/**
	 * @return the department
	 */
    @Transient
	public String getDepartment() {
		return department;
	}

	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * @return the displayName
	 */
	@Transient
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return the employeeType
	 */
	@Transient
	public String getEmployeeType() {
		return employeeType;
	}

	/**
	 * @param employeeType the employeeType to set
	 */
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}
	
	@Transient
	public String getAcornLastTime() {
		return acornLastTime;
	}

	@Transient
	public void setAcornLastTime(String acornLastTime) {
		this.acornLastTime = acornLastTime;
	}

//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((adcGroup == null) ? 0 : adcGroup.hashCode());
//		result = prime * result + ((agentRoles == null) ? 0 : agentRoles.hashCode());
//		result = prime * result + ((defGrp == null) ? 0 : defGrp.hashCode());
//		result = prime * result + ((department == null) ? 0 : department.hashCode());
//		result = prime * result + ((displayName == null) ? 0 : displayName.hashCode());
//		result = prime * result + ((employeeType == null) ? 0 : employeeType.hashCode());
//		result = prime * result + ((lastDt == null) ? 0 : lastDt.hashCode());
//		result = prime * result + ((name == null) ? 0 : name.hashCode());
//		result = prime * result + ((password == null) ? 0 : password.hashCode());
//		result = prime * result + ((title == null) ? 0 : title.hashCode());
//		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
//		result = prime * result + ((valid == null) ? 0 : valid.hashCode());
//		result = prime * result + ((workGrp == null) ? 0 : workGrp.hashCode());
//		return result;
//	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AgentUser other = (AgentUser) obj;
		if (adcGroup == null) {
			if (other.adcGroup != null)
				return false;
		} else if (!adcGroup.equals(other.adcGroup))
			return false;
		if (agentRoles == null) {
			if (other.agentRoles != null)
				return false;
		} else if (!agentRoles.equals(other.agentRoles))
			return false;
		if (defGrp == null) {
			if (other.defGrp != null)
				return false;
		} else if (!defGrp.equals(other.defGrp))
			return false;
		if (department == null) {
			if (other.department != null)
				return false;
		} else if (!department.equals(other.department))
			return false;
		if (displayName == null) {
			if (other.displayName != null)
				return false;
		} else if (!displayName.equals(other.displayName))
			return false;
		if (employeeType == null) {
			if (other.employeeType != null)
				return false;
		} else if (!employeeType.equals(other.employeeType))
			return false;
		if (lastDt == null) {
			if (other.lastDt != null)
				return false;
		} else if (!lastDt.equals(other.lastDt))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (valid == null) {
			if (other.valid != null)
				return false;
		} else if (!valid.equals(other.valid))
			return false;
		if (workGrp == null) {
			if (other.workGrp != null)
				return false;
		} else if (!workGrp.equals(other.workGrp))
			return false;
		return true;
	}
 
}
