package com.chinadrtv.uam.test.service;

import com.chinadrtv.uam.sync.model.AcornRole;
import com.chinadrtv.uam.sync.model.AgentUser;
import com.chinadrtv.uam.sync.service.LdapService;
import com.chinadrtv.uam.sync.service.SyncService;
import com.chinadrtv.uam.test.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.LdapShaPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 14-3-26
 * Time: 下午2:49
 * To change this template use File | Settings | File Templates.
 */
public class LdapServiceTest extends BaseTest {
    @Autowired
    private SyncService syncService;
    @Autowired
    private LdapService ldapService;

    @Test
    @Rollback(false)
    public void test_getDepartments(){
        syncService.syncLdapInfo();
    }

    @Test
    public void test_getAcornRole(){
        List<AcornRole> arl = ldapService.getAcornRoleList();
        Assert.assertNotNull(arl);
    }

    @Test
    public void test_getUserIdList(){
        List<String> arl = ldapService.getUserIdList("insureprompt");

        Assert.assertNotNull(arl);
    }

    @Test
    @Rollback(false)
    public void test_syncLdapRoles(){
        syncService.syncLdapRoles();
    }
    @Test
    public void test_getManagerGroup(){
        List<String> value = ldapService.getManageGroupList("6");
        Assert.assertNotNull(value);
    }
    @Test
    public void test_getUserList(){
        List<AgentUser> userList = ldapService.getUserList("outbund_wx");
        Assert.assertNotNull(userList);
    }

    @Test
    public void test_getUserDetail(){
        /*AgentUser value = ldapService.findUserByUid("10005");
        Assert.assertNotNull(value);

        LdapShaPasswordEncoder passwordEncoder  = new LdapShaPasswordEncoder();
        System.out.println(passwordEncoder.encodePassword("123456", null));*/

        AgentUser value = ldapService.findUserByUid("18619");
        Assert.assertNotNull(value);

        LdapShaPasswordEncoder passwordEncoder  = new LdapShaPasswordEncoder();
        String password = passwordEncoder.encodePassword("11111111a", null);

       // Assert.assertEquals(password,value.getPassword());
        boolean valid = passwordEncoder.isPasswordValid(value.getPassword(), "11111111a", null);
        System.out.println(password);
        System.out.println(valid);

    }

    @Test
    public void test_matter(){
        String newPassword ="12321a23";
        Pattern pattern=Pattern.compile("^(?![a-zA-Z]+$)(?![0-9]+$)[a-zA-Z0-9]{8,}$");
        Matcher matcher=pattern.matcher(newPassword);
        if(matcher.find()){
            System.out.println("aaaaaa");
        }
    }
}
