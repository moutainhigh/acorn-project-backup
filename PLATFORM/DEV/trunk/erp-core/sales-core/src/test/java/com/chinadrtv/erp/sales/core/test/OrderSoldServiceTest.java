package com.chinadrtv.erp.sales.core.test;

import com.chinadrtv.erp.sales.core.model.CreditcardOnlineAuthorization;
import com.chinadrtv.erp.sales.core.service.CreditcardOnlineAuthorizationService;
import com.chinadrtv.erp.sales.core.service.MerchantsCreditcardTracenumService;
import com.chinadrtv.erp.sales.core.service.OrderSoldService;
import com.chinadrtv.erp.tc.core.dao.NamesDao;
import com.chinadrtv.erp.test.SpringTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with (TC).
 * User: gao
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
//@TransactionConfiguration( defaultRollback = false)
public class OrderSoldServiceTest extends SpringTest {

    @Autowired
    private OrderSoldService orderSoldService;

    @Test
    public void testGetTop20Favorites()
    {
        orderSoldService.getTop20Favorites("acorntest");
    }
}
