package com.chinadrtv.erp.oms.service.impl;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.Phone;
import com.chinadrtv.erp.oms.dao.PhoneDao;
import com.chinadrtv.erp.oms.service.PhoneService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-3-28
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class PhoneServiceImpl extends GenericServiceImpl<Phone, Long> implements PhoneService {

    @Autowired
    private PhoneDao phoneDao;

    @Override
    protected GenericDao<Phone, Long> getGenericDao()
    {
        return phoneDao;
    }

    public List<Phone> findPhoneFromPhn(String phoneNumber,String phoneType)
    {
        //只获取手机电话号码
        return phoneDao.findPhonesFromPHN(phoneNumber,phoneType);
    }

    public String findMainPhoneFromContactId(String contactId)
    {
        List<Phone> phoneList= phoneDao.findPhonesFromContactId(contactId);
        if(phoneList!=null)
        {
            for(Phone phone:phoneList)
            {
                if("Y".equals(phone.getPrmphn()))
                {
                    if(!"4".equals(phone.getPhonetypid()))
                    {
                        String phoneNumb="";
                        if(StringUtils.isNotEmpty(phone.getPhn1()))
                        {
                            phoneNumb+= phone.getPhn1();
                            phoneNumb+="-";
                        }
                        phoneNumb+=phone.getPhn2();
                        if(StringUtils.isNotEmpty(phone.getPhn3()))
                        {
                             phoneNumb+="-";
                             phoneNumb+=phone.getPhn3();
                        }
                        return phoneNumb;
                    }
                    else
                    {
                        return phone.getPhn2();
                    }
                }
            }
        }
        return null;
    }
}
