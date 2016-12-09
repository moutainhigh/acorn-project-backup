package com.chinadrtv.erp.uc.service;

import com.chinadrtv.erp.uc.test.AppTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * Title: TransferBlackListServiceTest
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
public class TransferBlackListServiceTest extends AppTest {

    @Autowired
    private TransferBlackListService transferBlackListService;

    @Test
    public void testCheckContactBlackList() throws Exception {
        transferBlackListService.checkContactBlackList("11223344");
    }
}
