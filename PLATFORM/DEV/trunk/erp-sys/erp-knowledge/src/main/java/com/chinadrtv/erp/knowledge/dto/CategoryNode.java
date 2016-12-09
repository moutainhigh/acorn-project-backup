/**
 * 
 */
package com.chinadrtv.erp.knowledge.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.chinadrtv.erp.knowledge.enums.NodeState;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author dengqianyong
 * 
 */
@JsonIgnoreProperties({ "parent" })
public class CategoryNode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1425102034130420642L;

	public static final String ICON_FOLDER = "icon-folder";
	public static final String ICON_FILE = "icon-file";
	public static final String ICON_NEW = "icon-new";
	public static final String ICON_COLLECT = "icon-collect";
	public static final String ICON_HOT = "icon-hot";
	public static final String ICON_SEARCH = "searchbox-button";
	public static final String ICO_LABEL = "label-button";

	private Long id;

	private String text = StringUtils.EMPTY;

	private String iconCls = ICON_FOLDER;

	private NodeState state = NodeState.open;

	private Boolean checked = Boolean.FALSE;

	private NodeAttributes attributes = new NodeAttributes();

	private CategoryNode parent;

	private List<CategoryNode> children = new ArrayList<CategoryNode>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public NodeState getState() {
		return state;
	}

	public void setState(NodeState state) {
		this.state = state;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public NodeAttributes getAttributes() {
		return attributes;
	}

	public void setAttributes(NodeAttributes attributes) {
		this.attributes = attributes;
	}

	public CategoryNode getParent() {
		return parent;
	}

	public void setParent(CategoryNode parent) {
		this.parent = parent;
	}

	public List<CategoryNode> getChildren() {
		return children;
	}

	public void setChildren(List<CategoryNode> children) {
		this.children = children;
	}

	public class NodeAttributes implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -1696513007476618524L;

		private Long parentId;

		private String description;

		private String department;

		private String departType;

		private String groupId;

		private Boolean editable;

		private Integer position;

		private Integer type;

		private Boolean addable;

		private Boolean isDir;

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

		public String getDepartType() {
			return departType;
		}

		public void setDepartType(String departType) {
			this.departType = departType;
		}

		public Boolean getEditable() {
			return editable;
		}

		public void setEditable(Boolean editable) {
			this.editable = editable;
		}

		public Integer getPosition() {
			return position;
		}

		public void setPosition(Integer position) {
			this.position = position;
		}

		public Boolean getAddable() {
			return addable;
		}

		public void setAddable(Boolean addable) {
			this.addable = addable;
		}

		public Boolean getIsDir() {
			return isDir;
		}

		public void setIsDir(Boolean isDir) {
			this.isDir = isDir;
			if (isDir != null && isDir.booleanValue()) {
				setIconCls(ICON_FOLDER);
			}
		}

		public Integer getType() {
			return type;
		}

		public void setType(Integer type) {
			this.type = type;
		}

	}

}
