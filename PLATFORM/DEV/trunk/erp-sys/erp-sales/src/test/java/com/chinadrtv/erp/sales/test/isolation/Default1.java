package com.chinadrtv.erp.sales.test.isolation;

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
 * Time: 下午1:46
 * To add class comments
 */
public class Default1 extends SpringTest {
    @Autowired
    private OrderChangeTestService orderChangeTestService;

    @Autowired
    private IntegrateService integrateService;

    @Test
    @Rollback(false)
    public void testWrite(){
        orderChangeTestService.update(501051L);
    }
}
