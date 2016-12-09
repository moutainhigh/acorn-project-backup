package com.chinadrtv.uam.controller;

import com.chinadrtv.uam.model.auth.Role;
import com.chinadrtv.uam.model.auth.User;
import com.chinadrtv.uam.service.RoleService;
import com.chinadrtv.uam.service.UserService;
import com.chinadrtv.uam.validate.UserValidate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 14-3-31
 * Time: 下午2:12
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserValidate userValidate;

    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> list(int page, int rows) {
        return userService.getUserList(rows * (page - 1), rows);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public String update(User user, @RequestParam(value = "roleIdArray[]", required = false) Long[] roleIdArray) {
        String result = userValidate.validateUser(user);

        if (StringUtils.isNotBlank(result)) {
            return result;
        }
        User perUser = userService.get(User.class, user.getId());
        perUser.setWorkGroup(user.getWorkGroup());
        perUser.setUserTitle(user.getUserTitle());
        user.setLastUpdateTime(new Date());
        userService.update(perUser);

        Set<Role> roleSet = new HashSet<Role>();
        if (roleIdArray != null) {
            for (Long id : roleIdArray) {
                Role role = roleService.get(Role.class, id);
                roleSet.add(role);
            }
        }
        perUser.setRoles(roleSet);
        userService.update(perUser);

        return "success";
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(Long id) {
        if(id ==null){
               return "删除用户失败，请刷新";
        }
        User user = userService.get(User.class, id);
        user.setValid(false);
        userService.update(user);

        return "success";
    }


}
