package com.chinadrtv.uam.test.service;

import com.chinadrtv.uam.model.auth.Role;
import com.chinadrtv.uam.service.RoleService;
import com.chinadrtv.uam.test.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 14-4-11
 * Time: 上午11:09
 * To change this template use File | Settings | File Templates.
 */
public class RoleServiceTest extends BaseTest {
     @Autowired
    private RoleService roleService;
    @Test
    public void test_getRoleListByUserId(){

        List<Role> roleList = roleService.getRoleListByUserId(3275L);

        Assert.assertNotNull(roleList);
    }

}
