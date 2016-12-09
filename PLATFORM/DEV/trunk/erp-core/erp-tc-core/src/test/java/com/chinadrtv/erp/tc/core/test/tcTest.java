package com.chinadrtv.erp.tc.core.test;

import com.chinadrtv.erp.model.AddressExt;
import com.chinadrtv.erp.model.AreaGroupDetail;
import com.chinadrtv.erp.model.Ems;
import com.chinadrtv.erp.model.OrderAssignRule;
import com.chinadrtv.erp.model.agent.AreaAll;
import com.chinadrtv.erp.model.agent.CityAll;
import com.chinadrtv.erp.model.agent.CountyAll;
import com.chinadrtv.erp.model.agent.Province;
import com.chinadrtv.erp.tc.core.dao.AreaGroupDao;
import com.chinadrtv.erp.tc.core.dao.OrderAssignRuleDao;
import com.chinadrtv.erp.tc.core.dao.OrderhistDao;
import com.chinadrtv.erp.tc.core.dao.ProductDao;
import com.chinadrtv.erp.tc.core.model.CompanyAssignQuantity;
import com.chinadrtv.erp.tc.core.service.AddressExtService;
import com.chinadrtv.erp.test.SpringTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-4-24
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class tcTest extends SpringTest {

    @Autowired
    private OrderhistDao orderhistDao;

    //@Test
    public void testOrderDao()
    {
        List<CompanyAssignQuantity> companyAssignQuantityList= orderhistDao.getCurrentCompanyAssignQuantity();
        for(CompanyAssignQuantity companyAssignQuantity:companyAssignQuantityList)
        {
            System.out.println(companyAssignQuantity.getCompanyId());
        }
    }

    @Autowired
    private AreaGroupDao areaGroupDao;

    //@Test
    public void testAreaGroup()
    {
        AreaGroupDetail areaGroupDetail=new AreaGroupDetail();
        areaGroupDetail.setAreaId(1);
        areaGroupDetail.setCityId(1);
        areaGroupDetail.setCountyId(1);
        areaGroupDetail.setProvinceId(-2);
        List<Long> list=areaGroupDao.getAreaGroupIdFromDetail(areaGroupDetail);
        if(list!=null)
        {
            for(Long ll:list)
            {
                System.out.println(ll);
            }
        }
    }

    @Autowired
    private OrderAssignRuleDao orderAssignRuleDao;

    //@Test
    public void testOrderAssignRuleDao()
    {
        List<OrderAssignRule> orderAssignRuleList=orderAssignRuleDao.getAllValidRules();
        if(orderAssignRuleList!=null)
        {
            System.out.println(orderAssignRuleList.size());
        }

    }

    @Autowired
    private AddressExtService addressExtService;

    //@Test
    public void testAddressExtService()
    {
        AddressExt addressExt=new AddressExt();
        Province province=new Province();
        province.setProvinceid("03");
        CityAll cityAll=new CityAll();
        cityAll.setCityid(3);
        CountyAll countyAll=new CountyAll();
        countyAll.setCountyid(55);
        AreaAll areaAll=new AreaAll();
        areaAll.setAreaid(846);

        addressExt.setProvince(province);
        addressExt.setCity(cityAll);
        addressExt.setCounty(countyAll);
        addressExt.setArea(areaAll);
        Ems ems=null;
        addressExtService.createAddressExt(addressExt,ems);
    }

    @Autowired
    private ProductDao productDao;

    @Test
    public void testProductDao()
    {
        List<String> list=new ArrayList<String>();
        list.add("1200600800");
        list.add("1200600900");
        list.add("1200507900");

        productDao.getProductsFromProdIds(list);
    }
}
