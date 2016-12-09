package com.chinadrtv.uam.model.auth;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.chinadrtv.uam.model.BaseEntity;
import com.chinadrtv.uam.model.cas.Site;
import com.chinadrtv.uam.model.uam.SysUser;

@Entity
@Table(name = "UAM_ROLE", schema = "ACOAPP_UAM")
public class Role extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 12384500407674721L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLE_GENERATOR")
	@SequenceGenerator(name = "ROLE_GENERATOR", schema = "ACOAPP_UAM", 
		sequenceName = "SEQ_ROLE", allocationSize = 1)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "NAME", length = 50, nullable = false)
	private String name;
	
	@Column(name="DESCRIPTION",length =128,nullable = true)
	private String  description;
	
	@OneToOne(fetch = FetchType.EAGER)
	private Site site;
	
	@ManyToMany(mappedBy = "roles")
	private Set<User> users = new LinkedHashSet<User>();
	
	@ManyToMany(mappedBy = "roles")
	private Set<SysUser> sysUsers = new LinkedHashSet<SysUser>();
	
	@ManyToMany
	@JoinTable(
		name = "UAM_ROLE_RESOURCE", schema = "ACOAPP_UAM",
		joinColumns = @JoinColumn(name = "ROLE_ID"), 
		inverseJoinColumns = @JoinColumn(name = "RESOURCE_ID")
	)
	private Set<Resource> resources = new LinkedHashSet<Resource>();

    @Column(name="ACORN_PRIORITY",length =10,nullable = true)
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
    
    public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<SysUser> getSysUsers() {
		return sysUsers;
	}

	public void setSysUsers(Set<SysUser> sysUsers) {
		this.sysUsers = sysUsers;
	}

	public Set<Resource> getResources() {
        return resources;
    }

    public void setResources(Set<Resource> resources) {
        this.resources = resources;
    }

    public String getAcornPriority() {
        return acornPriority;
    }

    public void setAcornPriority(String acornPriority) {
        this.acornPriority = acornPriority;
    }
}
