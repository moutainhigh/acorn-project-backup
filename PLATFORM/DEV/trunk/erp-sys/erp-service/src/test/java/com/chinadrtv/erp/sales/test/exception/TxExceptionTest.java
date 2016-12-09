package com.chinadrtv.erp.sales.test.exception;

import com.chinadrtv.erp.sales.service.OrderChangeTestService;
import com.chinadrtv.erp.test.SpringTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

/**
 * 橡果国际IT系统集成部
 * User: liuhaidong
 * Email:liuhaidong@chinadrtv.com
 * Date: 13-8-29
 * Time: 上午10:15
 * To add class comments
 */
public class TxExceptionTest extends SpringTest {
    @Autowired
    private OrderChangeTestService orderChangeTestService;

    @Test
    public void testGet(){
        //orderChangeTestService.getOrderChangeException(501051L);
    }

    @Test
    public void testRequired(){
        //orderChangeTestService.requiredOrderChangeException(501051L);
    }

   // @Test
   // @Rollback(false)
    public void testGetUpdate(){
        orderChangeTestService.getForUpdate(501051L);
    }

    @Test
    @Rollback(false)
    public void testUpdate(){
        orderChangeTestService.update(501051L);
    }
}
