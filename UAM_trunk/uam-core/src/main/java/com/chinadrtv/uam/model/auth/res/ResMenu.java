package com.chinadrtv.uam.model.auth.res;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.chinadrtv.uam.model.auth.MenuGroupMenu;
import com.chinadrtv.uam.model.auth.Resource;

@Entity
@Table(name = "UAM_RESOURCE_MENU", schema = "ACOAPP_UAM")
@PrimaryKeyJoinColumn(name = "ID")
public class ResMenu extends Resource {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4771307639651256414L;

	@Column(name = "menu_url", length = 255)
	private String menuUrl;
    @Column(name = "parent_Id", length = 19)
	private Long parentId;
    @Column(name = "sort",length = 2)
    private int sort;
    @Column(name = "description", length = 255)
    private String description;
    @Column(name = "alias", length = 30)
    private String alias;

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
