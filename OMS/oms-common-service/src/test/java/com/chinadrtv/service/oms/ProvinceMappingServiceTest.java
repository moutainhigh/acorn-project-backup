/**
 * 
 *	橡果国际ERP技术支持中心
 * Copyright (c) 2013-2013 Acorn International,Inc. All Rights Reserved.
 */
package com.chinadrtv.service.oms;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.dal.oms.dao.ProvinceMappingDao;
import com.chinadrtv.model.oms.ProvinceMapping;

/**
 * 
 * @author andrew
 * @version $Id: ProvinceMappingServiceTest.java, v 0.1 2013年11月11日 下午4:12:22 andrew Exp $
 */
public class ProvinceMappingServiceTest extends ServiceTest {

    @Autowired
    private ProvinceMappingDao provinceMappingDao;
    
    @Test
    public void testQuery(){
        ProvinceMapping pm = provinceMappingDao.findByName("北京省");
        Assert.assertNotNull(pm);
        Assert.assertNotNull(pm.getMappingProvinceid());
    }
}
