package com.chinadrtv.uam.test.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.uam.sync.service.SyncService;
import com.chinadrtv.uam.test.BaseTest;
import org.springframework.test.annotation.Rollback;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 14-4-25
 * Time: 下午1:24
 * To change this template use File | Settings | File Templates.
 */
public class SyncServiceTest extends BaseTest {
    @Autowired
    private SyncService syncService;

    @Test
    @Rollback(false)
     public void test_sycn(){
        //同步
       syncService.syncLdapInfo();

        //同步权限
       syncService.syncLdapRoles();

     }


    @Test
    @Rollback(false)
    public void test_sycnUser(){
        String[] array= new String[]{"12941","14716","27321","27785","27835","28511","29200","29201","16347","16373","16109","16448","16549","18197","18546","18754","18756","18843","19012","19218","19212","19199","19194","19191","19568","19579","14638","13092","12707","12752","12749","12672","12684","12768","12816","12814","14126","13287","12904","12939","12946","12956","12958","14272","28402","13489","14434","14465","14598","14596","14603","14608","14732","14750","19257","19258","24777","27083","27084","27108","27308","27309","27811","27830","27937","27987","28481","29187","29203","16072","18063","18167","12753","12916"};


        for(String id :array){
           //同步
           syncService.syncUser(id,"lizhi1");
       }


    }




}
