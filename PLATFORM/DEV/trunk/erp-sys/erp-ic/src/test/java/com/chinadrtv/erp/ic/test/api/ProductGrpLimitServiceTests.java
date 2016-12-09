package com.chinadrtv.erp.ic.test.api;

import com.chinadrtv.erp.ic.service.ProductGrpLimitService;
import com.chinadrtv.erp.ic.test.ErpIcContextTests;
import com.chinadrtv.erp.model.inventory.PlubasInfo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gaodejian
 * Date: 13-5-14
 * Time: 上午11:27
 * To change this template use File | Settings | File Templates.
 */
public class ProductGrpLimitServiceTests extends ErpIcContextTests {

    @Autowired
    private ProductGrpLimitService productGrpLimitService;

    @Test
    public void isValidPrice() {
        Assert.assertTrue(productGrpLimitService.isValidPrice("outdialbc25", "1140400202",269.00));
        Assert.assertTrue(productGrpLimitService.isValidPrice("outdialbc25", "1140400202",268.00));
    }

    @Test
    public void isInvalidPrice() {
        Assert.assertFalse(productGrpLimitService.isValidPrice("outdialbc25", "1140400202",267.00));
    }

    @Test
    public void getLpPrice() {
        Assert.assertTrue(productGrpLimitService.getLpPrice("outdialbc25", "1140400202") == 268D);
    }
}
