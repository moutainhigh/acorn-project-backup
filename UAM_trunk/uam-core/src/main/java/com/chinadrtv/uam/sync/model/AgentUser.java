package com.chinadrtv.uam.sync.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * User: liuhaidong
 * Date: 12-11-19
 */
@SuppressWarnings("serial")
public class AgentUser implements Serializable, UserDetails {
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

    private String description;
    private String lastTime;
    private String maxFailure;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDefGrp() {
        return defGrp;
    }

    public void setDefGrp(String defGrp) {
        this.defGrp = defGrp;
    }

    public String getAdcGroup() {
        return adcGroup;
    }

    public void setAdcGroup(String adcGroup) {
        this.adcGroup = adcGroup;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public Date getLastDt() {
        return lastDt;
    }

    public void setLastDt(Date lastDt) {
        this.lastDt = lastDt;
    }

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

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return agentRoles;
    }


    public boolean hasRole(String roleName){
    	
    	Collection<? extends GrantedAuthority> roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    	SimpleGrantedAuthority ga = new SimpleGrantedAuthority(roleName);
    	return roles.contains(ga);

    }
    
    public void setPassword(String password) {
        this.password = password;
    }


    public String getPassword() {
        return password;
    }


    public String getUsername() {
        return name;
    }


    public boolean isAccountNonExpired() {
        return true;
    }


    public boolean isAccountNonLocked() {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }


    public boolean isCredentialsNonExpired() {
        return true;
    }


    public boolean isEnabled() {
        return (valid != null && valid.equalsIgnoreCase("-1"));
    }

	/**
	 * @return the department
	 */

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

	public String getEmployeeType() {
		return employeeType;
	}

	/**
	 * @param employeeType the employeeType to set
	 */
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getMaxFailure() {
        return maxFailure;
    }

    public void setMaxFailure(String maxFailure) {
        this.maxFailure = maxFailure;
    }
}
