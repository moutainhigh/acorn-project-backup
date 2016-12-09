package com.chinadrtv.erp.sales.test.serializable;

import com.chinadrtv.erp.sales.service.IntegrateService;
import com.chinadrtv.erp.sales.service.OrderChangeTestService;
import com.chinadrtv.erp.test.SpringTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

/**
 * 橡果国际IT系统集成部
 * User: liuhaidong
 * Email:liuhaidong@chinadrtv.com
 * Date: 13-9-3
 * Time: 下午3:09
 * To add class comments
 */
public class Serial1 extends SpringTest {
    @Autowired
    private OrderChangeTestService orderChangeTestService;

    @Autowired
    private IntegrateService integrateService;

    @Test
    @Rollback(false)
    public void testInsert(){
        orderChangeTestService.updateNew();
    }

    @Test
    @Rollback(false)
    public void testDelete(){
        orderChangeTestService.updateRemove();
    }
}
