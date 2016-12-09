package com.chinadrtv.erp.user.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class UserDto{

	private Long id;
	
	private String name;
	
	private UserGroupDto userGroup;
	
    private String userTitle; //职务（配置表取出ID）

    private String workGroup;//工作组（配置表取出ID

    private String valid;// 是否可以登陆

    private Long lastLockId;//最后开关ID

    private Date lastUpdateTime;//最后编辑时间

    private Long lastUpdateId; //最后编辑帐号

    private String acornLastTime;

    private String acornMaxFailure;

    private String description;

    private String employeeType;
    
    private Date createDate;
    
    private Date updateDate;
    
    private String clientUserId;
    
    private String defGrp;
    
    private String password;
    
    private String sourcePassword;
    
    private Set<RoleDto> roles = new HashSet<RoleDto>();


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

    public UserGroupDto getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroupDto userGroup) {
        this.userGroup = userGroup;
    }


    public String getUserTitle() {
        return userTitle;
    }

    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle;
    }

    public String getWorkGroup() {
        return workGroup;
    }

    public void setWorkGroup(String workGroup) {
        this.workGroup = workGroup;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public Long getLastLockId() {
        return lastLockId;
    }

    public void setLastLockId(Long lastLockId) {
        this.lastLockId = lastLockId;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Long getLastUpdateId() {
        return lastUpdateId;
    }

    public void setLastUpdateId(Long lastUpdateId) {
        this.lastUpdateId = lastUpdateId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public String getAcornLastTime() {
        return acornLastTime;
    }

    public void setAcornLastTime(String acornLastTime) {
        this.acornLastTime = acornLastTime;
    }

    public String getAcornMaxFailure() {
        return acornMaxFailure;
    }

    public void setAcornMaxFailure(String acornMaxFailure) {
        this.acornMaxFailure = acornMaxFailure;
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

	public Set<RoleDto> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleDto> roles) {
		this.roles = roles;
	}

	public String getClientUserId() {
		return clientUserId;
	}

	public void setClientUserId(String clientUserId) {
		this.clientUserId = clientUserId;
	}

	public String getDefGrp() {
		return defGrp;
	}

	public void setDefGrp(String defGrp) {
		this.defGrp = defGrp;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSourcePassword() {
		return sourcePassword;
	}

	public void setSourcePassword(String sourcePassword) {
		this.sourcePassword = sourcePassword;
	}

}
