package com.chinadrtv.erp.ic.test;

import java.io.FileNotFoundException;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.InitializationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.util.Log4jConfigurer;

/**
 * 单元测试基类
 * user: gaodeajian
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration("classpath*:com/chinadrtv/erp/ic/test/applicationContext-*.xml")
@TransactionConfiguration(transactionManager="springTransactionManager", defaultRollback=true)
public class ErpIcContextTests extends AbstractJUnit4SpringContextTests {

    static {
        try {
            Log4jConfigurer.initLogging("classpath:com/chinadrtv/erp/ic/test/log4j.properties");
        }
        catch( FileNotFoundException ex ) {
            System.err.println( "Cannot Initialize log4j" );
        }
    }
}
