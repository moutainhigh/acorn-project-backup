package com.chinadrtv.erp.uc.service;

import com.chinadrtv.erp.uc.test.AppTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: haoleitao
 * Date: 13-11-4
 * Time: 下午3:25
 * To change this template use File | Settings | File Templates.
 */
public class CityServiceTest extends AppTest {

    @Autowired
    private CityService cityService;

    @Autowired
    private AreaService areaService;



    @Test
    public void initTest(){
        Assert.assertNotNull(cityService);
    }

    @Test
    public void getOne(){
        System.out.println(areaService.get(Integer.valueOf("14166")));
       Assert.assertNotNull(areaService.get(Integer.valueOf("14166")));
    }




}
