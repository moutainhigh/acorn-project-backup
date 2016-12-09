package com.chinadrtv.erp.uc.service;

import com.chinadrtv.erp.model.Priority;
import com.chinadrtv.erp.uc.test.AppTest;
import junit.framework.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by xieguoqiang on 14-1-8.
 */
public class PriorityServiceTest extends AppTest {
    @Autowired
    private PriorityService priorityService;

    @Test
    public void testFindAll() {
        List<Priority> priorities = priorityService.findAll();
        Assert.assertTrue(priorities.size() == 4);
    }
}
