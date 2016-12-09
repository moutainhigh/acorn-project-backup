package com.chinadrtv.erp.tc.core.test;

import com.chinadrtv.erp.tc.core.service.UserProductService;
import com.chinadrtv.erp.test.SpringTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户关注
 * User: gaodejian
 * Date: 13-5-21
 * Time: 上午10:01
 * To change this template use File | Settings | File Templates.
 */
public class UserProductServiceTest extends SpringTest {

    @Autowired
    private UserProductService serProductService;

    @Test
    //@Rollback(false)
    public void getUserProducts()
    {
        serProductService.getUserProducts("sa");
    }

}
