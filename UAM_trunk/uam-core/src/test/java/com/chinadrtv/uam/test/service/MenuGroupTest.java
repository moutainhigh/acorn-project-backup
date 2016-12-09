package com.chinadrtv.uam.test.service;

import java.util.HashSet;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.chinadrtv.uam.model.auth.MenuGroup;
import com.chinadrtv.uam.model.auth.MenuGroupMenu;
import com.chinadrtv.uam.model.auth.res.ResMenu;
import com.chinadrtv.uam.service.MenuGroupService;
import com.chinadrtv.uam.service.ResourceService;
import com.chinadrtv.uam.test.BaseTest;

/**
 * Created with IntelliJ IDEA. User: zhoutaotao Date: 14-3-20 Time: 上午11:03 To
 * change this template use File | Settings | File Templates.
 */
public class MenuGroupTest extends BaseTest {

	@Autowired
	public ResourceService resourceService;
	@Autowired
	public MenuGroupService menuGroupService;

	@Test
	@Rollback(false)
	public void test_menuGroupSave() {

		ResMenu resMenu = new ResMenu();
		resMenu.setName("查询订单");
		resMenu.setDescription("查询订单描述");
		resMenu.setMenuUrl("www.order.com");
		resourceService.save(resMenu);

		MenuGroup menuGroup = new MenuGroup();
		menuGroup.setGroupName("订单管理");
		menuGroup.setDescription("订单管理描述");
		menuGroupService.save(menuGroup);

		MenuGroupMenu menuGroupMenu = new MenuGroupMenu();
		menuGroupMenu.setMenu(resMenu);
		menuGroupMenu.setSort(1);
		menuGroupMenu.setGroup(menuGroup);

		menuGroupService.save(menuGroupMenu);

	}

	@Test
	//@Rollback(false)
	public void testMenuGroupMenu() {
		ResMenu m1 = new ResMenu();
		m1.setName("新增订单");
		m1.setDescription("新增订单描述");
		m1.setMenuUrl("add");
		resourceService.save(m1);
		
		ResMenu m2 = new ResMenu();
		m2.setName("修改订单");
		m2.setDescription("修改订单描述");
		m2.setMenuUrl("update");
		resourceService.save(m2);
		
		MenuGroup group = new MenuGroup();
		group.setGroupName("测试组");
		group.setDescription("desc");
		group.setMenuGroupMenus(new HashSet<MenuGroupMenu>());
		
		MenuGroupMenu mgm1 = new MenuGroupMenu();
		mgm1.setGroup(group);
		mgm1.setMenu(m1);
		mgm1.setSort(1);
		
		group.getMenuGroupMenus().add(mgm1);
		
		MenuGroupMenu mgm2 = new MenuGroupMenu();
		mgm2.setGroup(group);
		mgm2.setMenu(m2);
		mgm2.setSort(2);
		
		group.getMenuGroupMenus().add(mgm2);


		menuGroupService.saveOrUpdate(group);

	}
	
	@Test
	@Rollback(false)
	public void testAddNewMenuGroupMenu() {
		MenuGroup group = menuGroupService.get(MenuGroup.class, Long.valueOf(10));
		
		for (MenuGroupMenu mgm : group.getMenuGroupMenus()) {
			mgm.setSort(mgm.getSort() + 10);
		}
		
		ResMenu m1 = new ResMenu();
		m1.setName("删除订单");
		m1.setDescription("删除订单描述");
		m1.setMenuUrl("delete");
		resourceService.save(m1);
		
		MenuGroupMenu mgm = new MenuGroupMenu();
		mgm.setGroup(group);
		mgm.setMenu(m1);
		mgm.setSort(100);
		
		group.getMenuGroupMenus().add(mgm);
		
		menuGroupService.update(group);
	}
	
	@Test
	@Rollback(false)
	public void testRemoveNewMenuGroupMenu() {
		MenuGroup group = menuGroupService.get(MenuGroup.class, Long.valueOf(10));
		group.setMenuGroupMenus(null);
		menuGroupService.update(group);
	}

	@Test
	@Rollback(false)
	public void test_deleteMenuGroup() {

		menuGroupService.delete(MenuGroup.class, 2L);
	}

}
