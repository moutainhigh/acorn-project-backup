package com.chinadrtv.companymail.common.dal.dao.test;

import com.chinadrtv.companymail.common.dal.dao.CompanyContractDao;
import com.chinadrtv.companymail.common.dal.model.CompanyContract;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.List;

/**
 * Created with (JD).
 * User: 刘宽
 * Date: 13-11-5
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */

public class CompanyContractDaoTest {

    private CompanyContractDao dao;

    @Before
    public void init() {

        String[] contextFileArr = {"spring/applicationContext-dal.xml"/*, "mybatis-config.xml"*/};
        String beanName = "companyContractDao";
        try {
            ApplicationContext appCont = new ClassPathXmlApplicationContext(contextFileArr);
            dao = (CompanyContractDao) appCont.getBean(beanName);
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testMok()
    {
        if(dao!=null)
        {
            CompanyContract c = dao.findCompanyContractByName("上海EMS");

            if(c != null){
                System.out.println("送货公司邮件模板编号："+c.getTemplateId());
            }

        }
        else
        {
            System.out.println("it's error!");
        }
    }
}
