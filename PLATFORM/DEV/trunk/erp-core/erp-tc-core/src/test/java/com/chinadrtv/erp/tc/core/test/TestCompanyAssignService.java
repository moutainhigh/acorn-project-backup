package com.chinadrtv.erp.tc.core.test;

import com.chinadrtv.erp.model.AddressExt;
import com.chinadrtv.erp.model.Company;
import com.chinadrtv.erp.model.CompanyContract;
import com.chinadrtv.erp.model.OrderAssignRule;
import com.chinadrtv.erp.model.agent.*;
import com.chinadrtv.erp.tc.core.constant.model.OrderhistAssignInfo;
import com.chinadrtv.erp.tc.core.dao.CompanyContractDao;
import com.chinadrtv.erp.tc.core.dao.CompanyDao;
import com.chinadrtv.erp.tc.core.dao.OrderAssignRuleDao;
import com.chinadrtv.erp.tc.core.dao.OrderhistDao;
import com.chinadrtv.erp.tc.core.service.OrderhistCompanyAssignService;
import com.chinadrtv.erp.test.SpringTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-4-26
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class TestCompanyAssignService extends SpringTest {
    @Autowired
    private OrderhistCompanyAssignService orderhistCompanyAssignService;

    @Test
    public void testSimpleOrderAssignService()
    {
        Order order=new Order();
        order.setId(1L);
        order.setOrderid("100");
        AddressExt addressExt=new AddressExt();
        AreaAll areaAll=new AreaAll();
        areaAll.setAreaid(1);
        CountyAll countyAll=new CountyAll();
        countyAll.setCountyid(1);
        Province province=new Province();
        province.setProvinceid("1");
        CityAll cityAll=new CityAll();
        cityAll.setCityid(1);

        addressExt.setArea(areaAll);
        addressExt.setCity(cityAll);
        addressExt.setProvince(province);
        addressExt.setCounty(countyAll);

        order.setAddress(addressExt);
        order.setOrdertype("1");
        order.setPaytype("1");
        order.setRevision(1);
        order.setVersion(1L);

        order.setTotalprice(new BigDecimal("300"));
        order.setMailprice(new BigDecimal("10"));
        order.setProdprice(new BigDecimal("290"));
        order.setMailtype("1");
        order.setEntityid("1");
        order.setCompanyid("1");
        order.setStatus("1");

        List<Order> orderList=new ArrayList<Order>();
        orderList.add(order);
        List<OrderhistAssignInfo> orderhistAssignInfoList=new ArrayList<OrderhistAssignInfo>();
        OrderhistAssignInfo orderhistAssignInfo1=orderhistCompanyAssignService.findCompanyFromRule(order);
        orderhistAssignInfoList.add(orderhistAssignInfo1);
        for(OrderhistAssignInfo orderhistAssignInfo:orderhistAssignInfoList)
        {
            if(orderhistAssignInfo.isSucc())
            {
                System.out.println(orderhistAssignInfo.getEntityId());
            }
            else
            {
                System.out.println(orderhistAssignInfo.getErrorMsg());
            }
        }
    }

    @Autowired
    private CompanyContractDao companyContractDao;

    @Autowired
    private OrderAssignRuleDao orderAssignRuleDao;

    @Autowired
    private CompanyDao companyDao;

    //@Test
    public void testCompanyContractDao()
    {
        /*List<Integer> companyIdList=new ArrayList<Integer>();
        companyIdList.add(17);
        companyIdList.add(42);
        List<CompanyContract> companyContractList=companyContractDao.findCompanyContracts(companyIdList);
        for(CompanyContract companyContract:companyContractList)
        {
            System.out.println(companyContract.getId());
        }*/

        Company company=companyDao.getCompany("17");
        System.out.println(company.getName());

        companyDao.getCompany("17");
        System.out.println(company.getName());

    }

    @Autowired
    private OrderhistDao orderhistDao;
    //@Test
    public void testCompanyFind()
    {
        List<Order> orderhistList =new ArrayList<Order>();
        Order order=orderhistDao.getOrderHistByOrderid("999900803280");
        orderhistList.add(order);
        List<OrderhistAssignInfo> orderhistAssignInfoList=orderhistCompanyAssignService.findOrderMatchRule(orderhistList);
        for(OrderhistAssignInfo orderhistAssignInfo: orderhistAssignInfoList)
        {
            System.out.println(orderhistAssignInfo.getErrorMsg());
        }
    }
}
