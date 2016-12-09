/*
 * @(#)LeadTest.java 1.0 2013-5-10下午4:55:41
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.service;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.marketing.core.dto.SearchContactAssignHistDto;
import com.chinadrtv.erp.marketing.core.service.ContactAssignHistService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext*.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class ContactAssignHistTest extends
        AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private ContactAssignHistService contactAssignHistService;

    @Test
    public void testfindPageList() {
        DataGridModel dataGridModel = new DataGridModel();
        SearchContactAssignHistDto dto = new SearchContactAssignHistDto();
        dto.setBatchCode("1111");
        Map map = contactAssignHistService.findPageList(dto, dataGridModel);
    }

}
