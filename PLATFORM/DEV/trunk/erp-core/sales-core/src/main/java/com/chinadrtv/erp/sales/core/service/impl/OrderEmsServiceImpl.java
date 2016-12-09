package com.chinadrtv.erp.sales.core.service.impl;

import com.chinadrtv.erp.core.service.NamesService;
import com.chinadrtv.erp.model.AddressExt;
import com.chinadrtv.erp.model.Names;
import com.chinadrtv.erp.sales.core.service.OrderEmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-9-5
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class OrderEmsServiceImpl implements OrderEmsService {
    @Autowired
    private NamesService namesService;

    @Value("${com.chinadrtv.erp.sales.core.service.OrderEmsService.EMSCOMPANY}")
    private String emsTid;

    public String getEmsCompanyId(AddressExt addressExt)
    {
        List<Names> namesList = namesService.getAllNames(emsTid);
        for(Names names:namesList)
        {
            if(addressExt.getProvince().getProvinceid().equals(names.getId().getId()))
            {
                return names.getDsc();
            }
        }
        return ShipmentHeaderServiceImpl.COMPANY_EMS;
    }

    public List<String> getAllEmsCompanyIds()
    {
        List<String> list=new ArrayList<String>();
        List<Names> namesList = namesService.getAllNames(emsTid);
        for(Names names:namesList)
        {
            if(!list.contains(names.getDsc()))
            {
                list.add(names.getDsc());
            }
        }

        return list;
    }
}
