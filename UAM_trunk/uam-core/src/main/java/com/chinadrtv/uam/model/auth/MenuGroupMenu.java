package com.chinadrtv.uam.model.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.chinadrtv.uam.model.BaseEntity;
import com.chinadrtv.uam.model.auth.res.ResMenu;

/**
 * 可针对不容易角色进行自定菜单排列
 * 
 * @author dengqianyong
 *
 */
@Entity
@Table(name = "UAM_MENU_GROUP_MENU", schema = "ACOAPP_UAM")
public class MenuGroupMenu extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4690490866919758984L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MENU_GROUP_MENU_GENERATOR")
	@SequenceGenerator(name = "MENU_GROUP_MENU_GENERATOR", schema = "ACOAPP_UAM", 
		sequenceName = "SEQ_MENU_GROUP_MENU_GROUP", allocationSize = 1, initialValue = 1)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "MENU_GROUP_ID")
	private MenuGroup group;
	
	@ManyToOne
	@JoinColumn(name = "MENU_ID")
	private ResMenu menu;
	
	@Column(name = "SORT")
	private int sort;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MenuGroup getGroup() {
        return group;
    }

    public void setGroup(MenuGroup group) {
        this.group = group;
    }

    public ResMenu getMenu() {
        return menu;
    }

    public void setMenu(ResMenu menu) {
        this.menu = menu;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
