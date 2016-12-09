/*
 * @(#)ShipmentHeaderServiceTestCase.java 1.0 2013-2-27下午1:40:22
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.tc.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.tc.core.dto.ShipmentSenderDto;
import com.chinadrtv.erp.tc.core.service.OrderSkuSplitService;
import com.chinadrtv.erp.tc.service.OrderhistService;
import com.chinadrtv.erp.tc.service.ShipmentHeaderService;

/**
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none
 * </dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>none
 * </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013-2-27 下午1:40:22
 */
public class ShipmentHeaderServiceTest extends TCTest {

    @Autowired
    private ShipmentHeaderService shipmentHeaderService;

    @Autowired
    private OrderhistService orderhistService;

    @Autowired
    private OrderSkuSplitService orderSkuSplitService;

    /**
     * SR3.6_1.8运单重发
     *
     * @return void
     * @throws Exception
     * @throws
     * @Description:
     */
    //@Test
    public void testResendWaybill() throws Exception {
        ShipmentSenderDto ssDto = shipmentHeaderService.queryWaybill("997700222");
        ssDto.setNewCompanyId("15");

        shipmentHeaderService.resendWaybill(ssDto);
    }

    //@Test
    public void cancelWayBill() throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderid", "997700222");
        params.put("revision", 111);
        params.put("mdusr", "1120");
        shipmentHeaderService.cancelWayBill(params);
    }

    //@Test
    public void testGenerateWaybill() throws Exception {
        Order orderhist = orderhistService.getOrderHistByOrderid("997700222");
        //orderhist.setOrderid("997700222");
        orderhist.setMdusr("USR111");
        shipmentHeaderService.generateShipmentHeader(orderhist, false);
    }

    //@Test
    public void testGenerateWaybill2() throws Exception {
        Order orderhist = orderhistService.getOrderHistByOrderid("997700222");
        //orderhist.setOrderid("997700222");
        orderhist.setMdusr("USR111");
        shipmentHeaderService.generateShipmentHeader(orderhist, "123434", "1");
    }

    //@Test
    public void testSplitOrderSku() throws Exception {
        String orderId = "928904282";
        Order oh = orderhistService.getOrderHistByOrderid(orderId);
        List<Map<String, Object>> resultList = orderSkuSplitService.orderSkuSplit(oh);
        Assert.assertNotNull(resultList);
    }

    @Test
    public void testSettleAccountsShipment() throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("companyid", "87");
        params.put("clearid", "291176800");
        params.put("userId", "test");
        boolean result = shipmentHeaderService.settleAccountsShipment(params);
        Assert.assertNotNull(result);
    }
}
