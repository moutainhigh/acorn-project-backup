package com.chinadrtv.erp.ic.test.api;

import com.chinadrtv.erp.ic.model.CompanyDeliverySpan;
import com.chinadrtv.erp.ic.service.CompanyDeliverySpanService;
import com.chinadrtv.erp.ic.test.ErpIcContextTests;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-5-8
 * Time: 下午4:28
 * To change this template use File | Settings | File Templates.
 */
public class CompanyDeliverySpanServiceTests extends ErpIcContextTests {

    @Autowired
    private CompanyDeliverySpanService companyDeliverySpanService;


    @Test
    public void getDeliverySpanTest()
    {
        //承运商地址匹配规则与承运商时效匹配规则不是同一条规则
        CompanyDeliverySpan span = companyDeliverySpanService.getDeliverySpan("185", 1L, 2L, 1L, 1L, 1L, 1L);
        Assert.assertNotNull(span);
    }

}
