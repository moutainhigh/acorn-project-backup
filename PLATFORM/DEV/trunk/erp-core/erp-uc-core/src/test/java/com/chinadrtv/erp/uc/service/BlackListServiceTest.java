package com.chinadrtv.erp.uc.service;

import com.chinadrtv.erp.model.BlackList;
import com.chinadrtv.erp.test.SpringTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * Title: BlackListServiceTest
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
public class BlackListServiceTest extends SpringTest {
    @Autowired
    private BlackListService blackListService;

//    @Test
//    @Rollback(false)
//    public void testAddAddressBlackList() {
//        BlackList blackList = new BlackList("956812268", "28096312", "27427");
//        blackListService.addBlackList(blackList);
//    }

    @Test
//    @Rollback(false)
    public void testAddPhoneBlackList() {
        BlackList blackList = new BlackList("910698284", 2045878300L, 2L, "27428", "", 1L);
        blackListService.addBlackList(blackList);
    }

//    @Test
//    @Rollback(false)
//    public void testCalculateOrderAddressBlackList() {
//        blackListService.calculateOrderTransferBlackList(new Date(113, 5, 18, 7, 28, 40), new Date(113, 5, 18, 8, 28, 50));
//        blackListService.calculateOrderAddressBlackList(new Date(113, 5, 17, 13, 20, 40), new Date(113, 5, 17, 13, 40, 50));
//    }
}
