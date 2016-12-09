package com.chinadrtv.uam.rest;

import java.util.*;

import com.chinadrtv.uam.dto.ResMenuTreeDto;
import com.chinadrtv.uam.service.ResMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.uam.enums.Source;
import com.chinadrtv.uam.model.auth.Department;
import com.chinadrtv.uam.model.auth.Role;
import com.chinadrtv.uam.model.auth.User;
import com.chinadrtv.uam.model.auth.UserGroup;
import com.chinadrtv.uam.model.auth.UserMapping;
import com.chinadrtv.uam.model.cas.Site;
import com.chinadrtv.uam.service.RoleService;
import com.chinadrtv.uam.service.SiteService;
import com.chinadrtv.uam.service.UserService;

/**
 * Created with IntelliJ IDEA. User: zhoutaotao Date: 14-4-8 Time: 上午10:11 To
 * change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "/service")
public class ServiceRest {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private SiteService siteService;

    @Autowired
    private ResMenuService resMenuService;

	/**
	 * 得到用户
	 * 
	 * @param source
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/user/{source}/{userId}")
	@ResponseBody
	public User getUser(@PathVariable("source") Source source,
			@PathVariable("userId") String userId) {
		return userService.findUser(source.getValue(), userId);
	}
	
	/**
	 * 得到用户包括用户的权限
	 * 
	 * @param source
	 * @param siteName
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/user/{source}/{userId}/roles/{siteName}")
	@ResponseBody
	public User getUserAndRoles(@PathVariable("source") Source source,
			@PathVariable("siteName") String siteName,
			@PathVariable("userId") String userId) {
		User user = userService.findUser(source.getValue(), userId);
		Site site = siteService.getSiteByName(siteName);
		if (site == null) {
			return user;
		}

		List<Role> roles = roleService.getRoleListByUserId(user.getId(), site.getId());
		Set<Role> roleSet = new LinkedHashSet<Role>(roles);
		user.setRoles(roleSet);
		return user;
	}

	/**
	 * 得到用户列表
	 * 
	 * @param source
	 * @param groupCode
	 * @return
	 */
	@RequestMapping(value = "/user/list/{source}/{groupCode}")
	@ResponseBody
	public List<User> getUserList(@PathVariable("source") Source source,
			@PathVariable("groupCode") String groupCode) {
		return userService.getUserList(source.getValue(), groupCode);
	}

    /**
     * 模糊查询用户
     * 
     * @param source
     * @param likedUid
     * @return
     */
	@RequestMapping(value = "/user/list/{source}/like/{likedUid}")
	@ResponseBody
	public List<User> getUserByFuzzyUid(@PathVariable("source") Source source,
			@PathVariable("likedUid") String likedUid) {
		return userService.getUserListByFuzzyUid(source.getValue(), likedUid);
	}

	/**
	 * 得到管理员用户列表
	 * 
	 * @param source
	 * @param deptNum
	 * @param names
	 * @return
	 */
	@RequestMapping(value = "/user/list/{source}/{deptNum}/manage", method = RequestMethod.POST)
	@ResponseBody
	public List<User> getManageUserList(@PathVariable("source") Source source,
			@PathVariable("deptNum") String deptNum,
			@RequestBody List<String> names) {
		Department dept = userService.getDepartment(source.getValue(), deptNum);

		if (dept == null) {
			return Collections.emptyList();
		}
		
		List<UserGroup> groups = userService.getUserGroupListByCondition(dept.getId(), names);
        if (groups == null) {
            return Collections.emptyList();
        }
		List<Long> ids = new ArrayList<Long>();
		for (UserGroup ug : groups) {
			ids.add(ug.getId());
		}

		return userService.getUserListByGroupIdList(source.getValue(), ids);
	}

    /**
     * 得到权限列表
     * @param roleName
     * @return
     */
	@RequestMapping(value = "/role/{name}")
	@ResponseBody
	public Role getRole(@PathVariable("name") String roleName) {
		Role role = roleService.getRoleByName(roleName);
		return role != null ? role : new Role();
	}

	/**
	 * 得到权限列表
	 * 
	 * @param source
	 * @param siteName
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/role/list/{source}/{siteName}/{userId}")
	@ResponseBody
	public List<Role> getRoleListByUid(@PathVariable("source") Source source,
			@PathVariable("siteName") String siteName,
			@PathVariable("userId") String userId) {
		Site site = siteService.getSiteByName(siteName);
		if (site == null) {
			return Collections.emptyList();
		}

		UserMapping um = userService.getUserMapping(source.getValue(), userId);
		if (um == null) {
			return Collections.emptyList();
		}

		return roleService.getRoleListByUserId(um.getUserId(), site.getId());
	}

	/**
	 * 得到用户组
	 * 
	 * @param source
	 * @param groupCode
	 * @return
	 */
	@RequestMapping(value = "/userGroup/{source}/{groupCode}")
	@ResponseBody
	public UserGroup getUserGroup(@PathVariable("source") Source source,
			@PathVariable("groupCode") String groupCode) {
		return userService.getUserGroup(source.getValue(), groupCode);
	}

	/**
	 * 得到用户组列表
	 * 
	 * @param source
	 * @return
	 */
	@RequestMapping(value = "/userGroup/list/{source}")
	@ResponseBody
	public List<UserGroup> getUserGroupList(
			@PathVariable("source") Source source) {
		return userService.getUserGroupList(source.getValue());
	}

	/**
	 * 根据部门得到用户组列表
	 * 
	 * @param source
	 * @param deptNum
	 * @return
	 */
	@RequestMapping(value = "/userGroup/list/{source}/{deptNum}")
	@ResponseBody
	public List<UserGroup> getUserGroupListByDepartment(
			@PathVariable("source") Source source,
			@PathVariable("deptNum") String deptNum) {
		return userService.getUserGroupListByDepartment(source.getValue(),
				deptNum);
	}

	/**
	 * 得到管理员用户组
	 * 
	 * @param source
	 * @param deptNum
	 * @param names
	 * @return
	 */
	@RequestMapping(value = "/userGroup/list/{source}/{deptNum}/manage",method = RequestMethod.POST)
	@ResponseBody
	public List<UserGroup> getManageGroupList(
			@PathVariable("source") Source source,
			@PathVariable("deptNum") String deptNum,
			@RequestBody List<String> names) {
		Department dept = userService.getDepartment(source.getValue(), deptNum);

		if (dept == null) {
			return Collections.emptyList();
		}

		return userService.getUserGroupListByCondition(dept.getId(), names);
	}

	/**
	 * 得到部门
	 * 
	 * @param source
	 * @param deptNum
	 * @return
	 */
	@RequestMapping(value = "/department/{source}/{deptNum}")
	@ResponseBody
	public Department getDepartment(@PathVariable("source") Source source,
			@PathVariable("deptNum") String deptNum) {
		return userService.getDepartment(source.getValue(), deptNum);
	}

	/**
	 * 得到部门列表
	 * 
	 * @param source
	 * @return
	 */
	@RequestMapping(value = "/department/list/{source}")
	@ResponseBody
	public List<Department> getDepartmentList(
			@PathVariable("source") Source source) {
		return userService.getDepartmentList(source.getValue());
	}

	/**
	 * 登录
	 * 
	 * @param source
	 * @param userId
	 * @param password
	 * @return
	 */
    @RequestMapping(value = "/login/{source}/{siteName}/{userId}/{password}")
    @ResponseBody
    public Map<String, Object> login(@PathVariable("source") Source source,
                                     @PathVariable("siteName") String siteName,
                                     @PathVariable("userId") String userId,
                                     @PathVariable("password") String password) {
        Map<String, Object> value = userService.validate(source.getValue(), userId, password);

        User user = (User) value.get("user");
        if (user != null) {
            Site site = siteService.getSiteByName(siteName);
            if (site != null) {
                List<Role> roleList = roleService.getRoleListByUserId(user.getId(), site.getId());
                Set<Role> set = new HashSet<Role>();
                set.addAll(roleList);
                user.setRoles(set);
                value.put("user", user);
            }

        }
        return value;

    }

	/**
	 * 改密码
	 * 
	 * @param source
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
    @RequestMapping(value = "/changePassword/{source}/{userId}/{oldPassword}/{newPassword}")
    @ResponseBody
    public Map<String,String> changePassword(
            @PathVariable("source") Source source,@PathVariable("userId") String userId,
            @PathVariable("oldPassword") String oldPassword,@PathVariable("newPassword") String newPassword) {

        return userService.changePassword(source.getValue(),userId,oldPassword,newPassword);
    }


    @RequestMapping(value = "/resetPassword/{source}/{userId}/{newPassword}")
    @ResponseBody
    public Map<String,String> resetPassword(
            @PathVariable("source") Source source,@PathVariable("userId") String userId,
            @PathVariable("newPassword") String newPassword) {

        return userService.resetPassword(source.getValue(),userId,newPassword);
    }

    /**
     * 通过部门code获取成员ID列表
     * @param source
     * @param departmentNum
     * @return
     */
    @RequestMapping(value = "/userIdList/{source}/{departmentNum}")
    @ResponseBody
    private List<String> getUserIdList(@PathVariable("source") Source source,
                                       @PathVariable("departmentNum") String departmentNum){

        List<String> userIdList =userService.getUserListByDepartment(source.getValue(),departmentNum);

        return userIdList;
    }

    /**
     * 得到菜单列表
     *
     * @param siteName
     * @param userId
     * @return
     */
    @RequestMapping(value = "/menus/list/{siteName}/{userId}")
    @ResponseBody
    public List<ResMenuTreeDto> getMenuTreeByUid(
                                       @PathVariable("siteName") String siteName,
                                       @PathVariable("userId") String userId) {
        return resMenuService.getMenuTreeListByUserId(Long.valueOf(userId), siteName);
    }
}
