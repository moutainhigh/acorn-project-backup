package com.chinadrtv.uam.model.auth;

import com.chinadrtv.uam.model.BaseEntity;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * 部门 目前为ldap中部门
 */
@Entity
@Table(name = "UAM_DEPARTMENT", schema = "ACOAPP_UAM")
public class Department extends BaseEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7387923270071007866L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEPARTMENT_GENERATOR")
    @SequenceGenerator(name = "DEPARTMENT_GENERATOR", schema = "ACOAPP_UAM",
            sequenceName = "SEQ_DEPARTMENT", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "NAME", length = 60)
    private String name;

    @OneToMany(mappedBy = "department")
    @Cascade(CascadeType.ALL)
    private Set<UserGroup> userGroups = new HashSet<UserGroup>();

    @Column(name = "SOURCE_ID", length = 30)
    private String sourceId;
    
    @Column(name = "source", length = 30)
    private String source;

    @Column(name = "ACORN_AREA_CODE", length = 30)
    private String acornAreaCode;
    
    @Column(name = "ACORN_NAME", length = 30)
    private String acornName;
    
    @Column(name = "code", length = 30)
    private String code;

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

    public Set<UserGroup> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(Set<UserGroup> userGroups) {
        this.userGroups = userGroups;
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
}
