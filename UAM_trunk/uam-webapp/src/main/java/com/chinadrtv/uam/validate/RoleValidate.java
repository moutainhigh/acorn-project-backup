package com.chinadrtv.uam.validate;

import com.chinadrtv.uam.model.auth.Role;
import com.chinadrtv.uam.service.RoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 14-3-18
 * Time: 下午3:43
 * To change this template use File | Settings | File Templates.
 */
@Component
public class RoleValidate {
    @Autowired
    private RoleService roleService;

    public String addRoleValidate(Role role) {
        if(StringUtils.isBlank(role.getName())){
            return "请填写角色名";
        }

        Role persistent_role =roleService.getRoleByName(role.getName());
        if(persistent_role !=null){
            return "角色已经存在";
        }
        return null;
    }

    public String modifyRoleValidate(Role role) {
        if(role.getId()==null){
            return "该角色不存在";
        }
        if(StringUtils.isBlank(role.getName())){
            return "请填写角色名";
        }
        Role persistent_role =roleService.getRoleByName(role.getName());
        if(persistent_role !=null && !persistent_role.getId().equals(role.getId())){
            return "角色已经存在";
        }

        return null;
    }

    public String deleteRoleValidate(Long id) {
        Role role = roleService.get(Role.class, id);
        if(role ==null){
           return  "删除角色失败，请刷新";
        }

        if(!role.getUsers().isEmpty()) {
            return  "角色关联用户，请到用户界面修改";
        }
        return null;

    }
}
