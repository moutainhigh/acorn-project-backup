package com.chinadrtv.erp.user.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.userdetails.User;



/**
 * 用户组：目前为ldap中组
 */
public class UserGroupDto {
	
    private Long id;

    private String name;

    private DepartmentDto department;

    private String sourceId;

    private String source;

    private String acornAreaCode;

    private String acornGroupType;
    
    private Date createDate;
    
    private Date updateDate;

    private Set<User> users = new HashSet<User>();
    
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

    public DepartmentDto getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDto department) {
        this.department = department;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAcornAreaCode() {
        return acornAreaCode;
    }

    public void setAcornAreaCode(String acornAreaCode) {
        this.acornAreaCode = acornAreaCode;
    }

    public String getAcornGroupType() {
        return acornGroupType;
    }

    public void setAcornGroupType(String acornGroupType) {
        this.acornGroupType = acornGroupType;
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

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
}
