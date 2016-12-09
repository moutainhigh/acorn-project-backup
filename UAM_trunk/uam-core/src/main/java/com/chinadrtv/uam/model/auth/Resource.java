package com.chinadrtv.uam.model.auth;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.chinadrtv.uam.model.BaseEntity;
import com.chinadrtv.uam.model.cas.Site;

@Entity
@Table(name = "UAM_RESOURCE", schema = "ACOAPP_UAM")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Resource extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4736767771889927144L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESOURCE_GENERATOR")
	@SequenceGenerator(name = "RESOURCE_GENERATOR", schema = "ACOAPP_UAM", 
		sequenceName = "SEQ_RESOURCE", allocationSize = 1, initialValue = 1)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "NAME", length = 50, nullable = false)
	private String name;
	
	@Column(name = "DESCRIPTION", length = 255, nullable = true)
	private String description;
	
	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "resources")
	private Set<Role> roles = new LinkedHashSet<Role>();
	
	@OneToOne(fetch = FetchType.EAGER)
	private Site site;

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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }
}
