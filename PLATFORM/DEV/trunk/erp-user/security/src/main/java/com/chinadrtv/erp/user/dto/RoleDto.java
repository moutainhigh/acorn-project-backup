package com.chinadrtv.erp.user.dto;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.security.core.userdetails.User;

public class RoleDto{

	private Long id;
	
	private String name;

	private String  description;

	private Object site;
	
	private Set<User> users = new LinkedHashSet<User>();
	
	private Set<User> sysUsers = new LinkedHashSet<User>();
	
	private String resources;
	
	private Date createDate;
	
	private Date updateDate;
	
	private String acornPriority;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public Object getSite() {
		return site;
	}

	public void setSite(Object site) {
		this.site = site;
	}

	public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<User> getSysUsers() {
		return sysUsers;
	}

	public void setSysUsers(Set<User> sysUsers) {
		this.sysUsers = sysUsers;
	}

	public String getResources() {
		return resources;
	}

	public void setResources(String resources) {
		this.resources = resources;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getAcornPriority() {
		return acornPriority;
	}

	public void setAcornPriority(String acornPriority) {
		this.acornPriority = acornPriority;
	}
}
