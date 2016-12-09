/**
 * 
 */
package com.chinadrtv.erp.knowledge.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author dengqianyong
 *
 */
@Entity
@Table(name = "KNOWLEDGE_CATEGORY", schema = "ACOAPP_MARKETING")
public class KnowledgeCategory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7950221299271496017L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CATEGORY_SEQ_GEN")
	@SequenceGenerator(name = "CATEGORY_SEQ_GEN", sequenceName = "ACOAPP_MARKETING.SEQ_KNOWLEDGE_CATEGORY", allocationSize = 1)
	@Column(name = "ID", length = 11, nullable = false)
	private Long id;
	
	@Column(name = "NAME", length = 50, nullable = true)
	private String name;
	
	@Column(name = "PARENT_ID", length = 11, nullable = true)
	private Long parentId;
	
	@Column(name = "DESCRIPTION", length = 200, nullable = true)
	private String description;
	
	@Column(name = "SHORT_PATH", length = 255, nullable = true)
	private String sortPath;
	
	@Column(name = "FULL_PATH", length = 255, nullable = true)
	private String fullPath;
	
	@Column(name = "DEPARTMENT", length = 50, nullable = true)
	private String department;
	
	@Column(name = "GROUP_ID", length = 50, nullable = true)
	private String groupId;
	
	@Column(name = "ARTICLE_TYPE", nullable = true)
	private Integer articleType;
	
	@Column(name = "IS_ADD", length = 1, nullable = true)
	private String isAdd;
	
	@Column(name = "IS_EDIT", length = 1, nullable = true)
	private String isEdit;
	
	@Column(name = "POSITION", nullable = true)
	private Integer position;
	
	@Column(name = "DEPART_TYPE", length = 20, nullable = true)
	private String departType;
	
	@Column(name = "CREATE_USER", length = 50, nullable = true)
	private String createUser;
	
	@Column(name = "CREATE_DATE", nullable = true)
	private Date createDate;
	
	@Column(name = "UPDATE_USER", length = 50, nullable = true)
	private String updateUser;
	
	@Column(name = "UPDATE_DATE", nullable = true)
	private Date updateDate;

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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSortPath() {
		return sortPath;
	}

	public void setSortPath(String sortPath) {
		this.sortPath = sortPath;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Integer getArticleType() {
		return articleType;
	}

	public void setArticleType(Integer articleType) {
		this.articleType = articleType;
	}

	public String getIsAdd() {
		return isAdd;
	}

	public void setIsAdd(String isAdd) {
		this.isAdd = isAdd;
	}

	public String getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getDepartType() {
		return departType;
	}

	public void setDepartType(String departType) {
		this.departType = departType;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
}
