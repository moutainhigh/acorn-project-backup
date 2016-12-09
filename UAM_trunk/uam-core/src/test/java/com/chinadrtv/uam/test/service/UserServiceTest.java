package com.chinadrtv.uam.test.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.chinadrtv.uam.model.auth.UserGroup;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.chinadrtv.uam.model.auth.Role;
import com.chinadrtv.uam.model.auth.User;
import com.chinadrtv.uam.service.RoleService;
import com.chinadrtv.uam.service.UserService;
import com.chinadrtv.uam.test.BaseTest;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 14-4-1
 * Time: 下午1:22
 * To change this template use File | Settings | File Templates.
 */
public class UserServiceTest extends BaseTest{

    @Autowired
    private UserService userService;
     @Autowired
    private RoleService roleService;

    @SuppressWarnings("unchecked")
	@Test
    public void testGetUser(){

        Map<String, Object> value = userService.getUserList(1, 3);

        Assert.assertNotNull(value.get("total"));

        List<User> list = (List<User>) value.get("rows");


        Assert.assertNotNull(list);
    }

    @Test
    public void test_checkExistsParam(){
        boolean b =  userService.checkExistsParam("业务员");

        Assert.assertNotNull(b);
    }

    @Test
    //@Rollback(false)
    public void test_updateUserRole(){
        User perUser = userService.get(User.class, 1376L);

        Long[] roleIdArray = new Long[]{28L,29L};

        Set<Role> roleSet=new HashSet<Role>();
        for(Long id :roleIdArray){
            Role role = roleService.get(Role.class, id);
            roleSet.add(role);
        }
        perUser.setRoles(roleSet);
        userService.update(perUser);
    }

    @Test
    public void test_validatePassword(){
       Map<String, Object> value = userService.validate("LDAP", "18619", "11111111a");
       // Map<String, Object> value = userService.validate("LDAP", "18619", "123456");

        Assert.assertNotNull(value);
    }

    @Test
    @Rollback(false)
    public void test_changePassword(){
        Map<String, String> value = userService.changePassword("LDAP", "18619", "123456", "1111.aaaa");

        Assert.assertNotNull(value);
    }
    @Test
    @Rollback(false)
    public void test_resetPassword(){
        Map<String, String> value = userService.resetPassword("LDAP", "18619", "123456");

        Assert.assertNotNull(value);
    }
    @Test
    public void test_getUser(){
        User value = userService.findUser("LDAP", "18619");

        Assert.assertNotNull(value);
    }

    @Test
    public void test_getUserId(){
        List<String> value = userService.getUserListByDepartment("LDAP", "23");

        Assert.assertNotNull(value);
    }
}
