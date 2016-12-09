package com.chinadrtv.companymail.common.dal.dao.test;

import com.chinadrtv.companymail.common.dal.daowms.WmsDataInfoDao;
import com.chinadrtv.companymail.common.dal.model.ShippingLoad;
import com.chinadrtv.companymail.common.dal.model.ZMMRPTEMSMailList;
import com.chinadrtv.companymail.common.dal.model.ZMMRPTReceivableslist;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created with (JD).
 * User: 刘宽
 * Date: 13-11-5
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */

public class WmsDataInfoDaoTest {

    private WmsDataInfoDao dao;

    @Before
    public void init() {

        String[] contextFileArr = {"spring/applicationContext-dal.xml"/*, "mybatis-config.xml"*/};
        String beanName = "wmsDataInfoDao";
        try {
            ApplicationContext appCont = new ClassPathXmlApplicationContext(contextFileArr);
            dao = (WmsDataInfoDao) appCont.getBean(beanName);
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testMok()
    {
        if(dao!=null)
        {
            //获取wms承运商信息
            try{
                int i = dao.updateShippingLoad(42081);
                System.out.println("result = "+i);
            }catch (Exception ex){
               ex.printStackTrace();
            }
            List<ShippingLoad> shippingLoadList = dao.findShippingLoad();

            System.out.println("获取wms承运商信息条数："+shippingLoadList.size());

            int loadnum = 48514;
            List<ZMMRPTReceivableslist> R54 = dao.findZMMRPTReceivableslist(loadnum);

            if(R54 != null){
                System.out.println("R54Exce数据信息条数："+R54.size());
            } else {
                System.out.println("R54数据信息为空");
            }

            List<ZMMRPTEMSMailList> R56 = dao.findZMMRPTEMSMailList(loadnum);

            if(R56 != null){
                System.out.println("R56数据信息条数："+R56.size());
            } else {
                System.out.println("R56数据信息为空");
            }

        }
        else
        {
            System.out.println("it's error!");
        }
    }
}
