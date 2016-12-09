package com.chinadrtv.uam.model.auth;

import java.util.Date;
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
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.chinadrtv.uam.model.BaseEntity;

@Entity
@Table(name = "UAM_USER", schema = "ACOAPP_UAM")
public class User extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3960417144135529407L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_GENERATOR")
	@SequenceGenerator(name = "USER_GENERATOR", schema = "ACOAPP_UAM", 
		sequenceName = "SEQ_USER", allocationSize = 1, initialValue = 1000000001)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "NAME", length = 20)
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "GROUP_ID")
	private UserGroup userGroup;
	
	@ManyToMany
	@JoinTable(
		name = "UAM_USER_ROLE", schema = "ACOAPP_UAM",
		joinColumns = @JoinColumn(name = "USER_ID"), 
		inverseJoinColumns = @JoinColumn(name = "ROLE_ID")
	)
	private Set<Role> roles = new LinkedHashSet<Role>();

    @Column(name = "USER_TITLE", length = 30)
    private String userTitle; //职务（配置表取出ID）
    
    @Column(name = "WORK_GROUP", length = 30)
    private String workGroup;//工作组（配置表取出ID
    
    @Column(name = "VALID")
    private Boolean valid;// 是否可以登陆

    @Column(name = "LAST_LOCK_SEQID", length = 30)
    private Long lastLockId;//最后开关id
    
    @Column(name = "LAST_UPDATE_TIME")
    private Date lastUpdateTime;//最后编辑时间
    
    @Column(name = "LAST_UPDATE_SEQID", length = 30)
    private Long lastUpdateId; //最后编辑帐号
    
    @Column(name = "ACORN_LAST_TIME", length = 30)
    private String acornLastTime;//最后登陆时间
    
    @Column(name = "ACORN_MAX_FAILURE", length = 30)
    private String acornMaxFailure;
    
    @Column(name = "DESCRIPTION", length = 30)
    private String description;
    
    @Column(name = "EMPLOYEE_TYPE", length = 30)
    private String employeeType;
    
    @Transient
    private String clientUserId; // 对应客户端的userId
    @Transient
    private String sourcePassword; // 对应客户端的user密码

    @Column(name = "DEF_GRP", length = 30)
    private String defGrp;

    @Column(name = "PASSWORD", length = 30)
    private String password;

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

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
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
    public String getDefGrp() {
        return defGrp;
    }
    public void setDefGrp(String defGrp) {
        this.defGrp = defGrp;
    }

	public String getClientUserId() {
		return clientUserId;
	}

	public void setClientUserId(String clientUserId) {
		this.clientUserId = clientUserId;
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
