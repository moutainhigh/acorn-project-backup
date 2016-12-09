package com.chinadrtv.uam.dto;

import java.io.Serializable;
import java.util.List;

public class ResMenuTreeDto  implements Serializable {

    private Long id;

    private String name;

	private String menuUrl;

	private Long parentId;

    private int sort;

    private String description;

    private List<ResMenuTreeDto> childrens;

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

    public List<ResMenuTreeDto> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<ResMenuTreeDto> childrens) {
        this.childrens = childrens;
    }
}
