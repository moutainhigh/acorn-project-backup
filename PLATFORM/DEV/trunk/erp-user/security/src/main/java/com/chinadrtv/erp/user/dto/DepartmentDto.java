package com.chinadrtv.erp.user.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.chinadrtv.erp.user.model.UserGroup;


/**
 * 部门 目前为ldap中部门
 */
public class DepartmentDto{
   
    private Long id;

    private String name;

    private String sourceId;

    private String source;

    private String acornAreaCode;

    private String acornName;

    private String code;
    
    private Date createDate;
    
    private Date updateDate;
    
    private Set<UserGroup> userGroups = new HashSet<UserGroup>();

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

    public String getAcornName() {
        return acornName;
    }

    public void setAcornName(String acornName) {
        this.acornName = acornName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

	public Set<UserGroup> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(Set<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}
}
