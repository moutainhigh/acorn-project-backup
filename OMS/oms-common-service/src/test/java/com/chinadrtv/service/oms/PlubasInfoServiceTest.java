/**
 * 
 *	橡果国际ERP技术支持中心
 * Copyright (c) 2013-2013 Acorn International,Inc. All Rights Reserved.
 */
package com.chinadrtv.service.oms;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.dal.iagent.dao.PlubasInfoDao;

/**
 * 
 * @author andrew
 * @version $Id: PlubasInfoServiceTest.java, v 0.1 2013年11月11日 下午4:43:00 andrew Exp $
 */
public class PlubasInfoServiceTest extends ServiceTest {

    @Autowired
    private PlubasInfoDao plubasInfoDao;
    
    @Test
    public void testCheckSkuCode(){
       Boolean exists =  plubasInfoDao.checkSkuCode("32323");
       Assert.assertNull(exists);
    }
}
