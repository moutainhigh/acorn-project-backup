package com.chinadrtv.uam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/archives")
public class ArchiveController {

	@RequestMapping(value = "")
	public String viewArchiveIndex() {
		return "common/template1";
	}
	
	@RequestMapping(value = "/left", method = RequestMethod.GET)
	public String viewArchivesLeft() {
		return "archives/left";
	}
	
    @RequestMapping(value = "/role", method = RequestMethod.GET)
    public String viewArchivesRole() {
       return "archives/content/role";
    }
    @RequestMapping(value = "/department", method = RequestMethod.GET)
    public String viewArchivesDepartment() {
        return "archives/content/department";
    }
    @RequestMapping(value = "/group", method = RequestMethod.GET)
    public String viewArchivesGroup() {
        return "archives/content/group";
    }
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String viewArchivesUser() {
        return "archives/content/user";
    }
    
	@RequestMapping(value = "/menuGroup", method = RequestMethod.GET)
	public String viewArchivesMenuG() {
		return "archives/content/menuGroup";
	}
	
	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	public String viewArchivesMenu() {
		return "archives/content/menu";
	}
	
	@RequestMapping(value = "/togroup", method = RequestMethod.GET)
	public String viewArchivesTogroup() {
		return "archives/content/togroup";
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public String viewArchivesPage() {
		return "archives/content/page";
	}
	
	@RequestMapping(value = "/buttons", method = RequestMethod.GET)
	public String viewArchivesBtns() {
		return "archives/content/buttons";
	}
	
	@RequestMapping(value = "/dataFilter", method = RequestMethod.GET)
	public String viewArchivesFilter() {
		return "archives/content/dataFilter";
	}
	
	@RequestMapping(value = "/sys", method = RequestMethod.GET)
	public String viewArchivesSys() {
		return "archives/content/sys";
	}
}
