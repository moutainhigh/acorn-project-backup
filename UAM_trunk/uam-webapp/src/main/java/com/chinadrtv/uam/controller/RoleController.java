package com.chinadrtv.uam.controller;

import java.util.Date;
import java.util.List;

import com.chinadrtv.uam.model.cas.Site;
import com.chinadrtv.uam.service.SiteService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.uam.model.auth.Role;
import com.chinadrtv.uam.service.RoleService;
import com.chinadrtv.uam.validate.RoleValidate;

/**
 * Created with IntelliJ IDEA. User: zhoutaotao Date: 14-3-18 Time: 下午1:19 To
 * change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "/role")
public class RoleController extends BaseController {
	
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private RoleValidate roleValidate;
	
	@Autowired
	private RoleService roleService;
    @Autowired
    private SiteService siteService;
	
	/**
	 *
	 * @return
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public List<Role> getRoleList() {
		return roleService.getAll(Role.class);
	}

	/**
	 * 新增角色
	 * 
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String addRole(Role role,String siteName) {
		String validateMessage = roleValidate.addRoleValidate(role);
		if (StringUtils.isNotBlank(validateMessage)) {
			return validateMessage;
		}
        if(StringUtils.isNotBlank(siteName)){
            Site site = siteService.getSiteByName(siteName);
            if(site !=null){
                role.setSite(site);
            }
        }
        role.setCreateDate(new Date());
		Long roleId = roleService.save(role);

		if (roleId != null) {
			return SUCCESS;
		}
		return "add role failed";
	}

	/**
	 * 修改角色
	 * 
	 * @param role
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateRole(Role role,String siteName) {
		String validateMessage = roleValidate.modifyRoleValidate(role);
		if (StringUtils.isNotBlank(validateMessage)) {
			return validateMessage;
		}
        if(StringUtils.isNotBlank(siteName)){
            Site site = siteService.getSiteByName(siteName);
            role.setSite(site);
        }
        role.setUpdateDate(new Date());
		roleService.update(role);

		return "success";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String deleteRole(@PathVariable("id") Long id) {
        String validateMessage = roleValidate.deleteRoleValidate(id);
        if(StringUtils.isNotBlank(validateMessage)){
                return validateMessage;
        }

		roleService.delete(Role.class, id);
		return "success";
	}
}
