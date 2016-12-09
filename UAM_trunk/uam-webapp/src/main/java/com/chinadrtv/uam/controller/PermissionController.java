package com.chinadrtv.uam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/permissions")
public class PermissionController {

	@RequestMapping(value = "")
	public String viewPermissionIndex() {
		return "common/template2";
	}
	
	@RequestMapping(value = "/item1", method = RequestMethod.GET)
	public String viewPermissionsItem1() {
		return "permissions/content/item1";
	}
	
	@RequestMapping(value = "/item2", method = RequestMethod.GET)
	public String viewPermissionsItem2() {
		return "permissions/content/item2";
	}
	
	@RequestMapping(value = "/item3", method = RequestMethod.GET)
	public String viewPermissionsItem3() {
		return "permissions/content/item3";
	}
	
	@RequestMapping(value = "/item4", method = RequestMethod.GET)
	public String viewPermissionsItem4() {
		return "permissions/content/item4";
	}
	
	@RequestMapping(value = "/sys", method = RequestMethod.GET)
	public String viewPermissionsSys() {
		return "permissions/content/sys";
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String viewPermissionsUser() {
		return "permissions/content/user";
	}
}
