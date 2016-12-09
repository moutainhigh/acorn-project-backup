package com.chinadrtv.erp.sales.test.required;

import com.chinadrtv.erp.sales.service.IntegrateService;
import com.chinadrtv.erp.sales.service.OrderChangeTestService;
import com.chinadrtv.erp.test.SpringTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

/**
 * 橡果国际IT系统集成部
 * User: liuhaidong
 * Email:liuhaidong@chinadrtv.com
 * Date: 13-8-29
 * Time: 下午5:34
 * To add class comments
 */
public class TxRequiredTest extends SpringTest {

    @Autowired
    private IntegrateService integrateService;

    @BeforeClass
    public static void beforeClass(){
        System.out.println("beforeClass");
    }

    @AfterClass
    public static void afterClass(){
        System.out.println("afterClass");
    }

    //@Test
    //@Rollback(false)
    public void requiredTest(){
        integrateService.requiredTwo(501051L);
    }

    //@Test
   // @Rollback(false)
    public void requiredExceptionTest(){
        integrateService.requiredTwoException(501051L);
    }

    @Test
    @Rollback(false)
    public void requiredCatchExceptionTest(){
        integrateService.requiredCatchException(501051L);
    }

}
