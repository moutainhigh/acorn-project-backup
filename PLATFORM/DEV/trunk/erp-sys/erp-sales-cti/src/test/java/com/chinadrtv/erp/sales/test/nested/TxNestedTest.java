package com.chinadrtv.erp.sales.test.nested;

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
 * Date: 13-9-2
 * Time: 上午10:04
 * To add class comments
 */
public class TxNestedTest extends SpringTest {
    @Autowired
    private IntegrateService integrateService;

    @Test
    @Rollback(false)
    public void testNested(){
        integrateService.requiredNested(501051L);
    }
}
