package com.chinadrtv.uam.model.auth;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.chinadrtv.uam.model.BaseEntity;

@Entity
@Table(name = "UAM_MENU_GROUP", schema = "ACOAPP_UAM")
public class MenuGroup extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5406789406408610536L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MENU_GROUP_GENERATOR")
	@SequenceGenerator(name = "MENU_GROUP_GENERATOR", schema = "ACOAPP_UAM", 
		sequenceName = "SEQ_MENU_GROUP", allocationSize = 1, initialValue = 1)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "GROUP_NAME", length = 50, nullable = false)
	private String groupName;
	
	@Column(name = "DESCRIPTION", length = 255, nullable = true)
	private String description;
	
	@OneToMany(mappedBy = "group", orphanRemoval = true)
	@Cascade(CascadeType.ALL)
	private Set<MenuGroupMenu> menuGroupMenus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public Set<MenuGroupMenu> getMenuGroupMenus() {
		return menuGroupMenus;
	}

	public void setMenuGroupMenus(Set<MenuGroupMenu> menuGroupMenus) {
		this.menuGroupMenus = menuGroupMenus;
	}

}
