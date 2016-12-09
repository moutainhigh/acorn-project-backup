package com.chinadrtv.uam.model.auth;


import java.util.HashSet;
import java.util.Set;

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
 * 用户组：目前为ldap中组
 */
@Entity
@Table(name = "UAM_USER_GROUP",schema = "ACOAPP_UAM")
public class UserGroup extends BaseEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2148891831898974734L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_GROUP_GENERATOR")
    @SequenceGenerator(name = "USER_GROUP_GENERATOR",schema ="ACOAPP_UAM",
            sequenceName = "SEQ_USER_GROUP",initialValue = 1,allocationSize = 1)
    private Long id;

    @Column(name = "name",length = 60)
    private String name;

    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID")
    private Department department;

    @OneToMany(mappedBy = "userGroup")
    private Set<User> users = new HashSet<User>();

    @Column(name = "SOURCE_ID",length = 30)
    private String sourceId;
    
    @Column(name = "SOURCE",length = 30)
    private String source;

    @Column(name = "ACORN_AREA_CODE",length = 30)
    private String acornAreaCode;
    
    @Column(name = "ACORN_GROUP_TYPE",length = 30)
    private String acornGroupType;

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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
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
}
