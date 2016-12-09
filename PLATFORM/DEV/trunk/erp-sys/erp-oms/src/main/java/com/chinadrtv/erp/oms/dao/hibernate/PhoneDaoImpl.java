package com.chinadrtv.erp.oms.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.Phone;
import com.chinadrtv.erp.oms.dao.PhoneDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-3-28
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Repository
public class PhoneDaoImpl extends GenericDaoHibernate<Phone, Long> implements PhoneDao {
    public PhoneDaoImpl()
    {
        super(Phone.class);
    }

    public List<Phone> findPhonesFromPHN(String phn, String phoneType)
    {
        if(StringUtils.isEmpty(phn))
            return null;

        String hql=null;
        Map<String, Parameter> parmMap =new HashMap<String, Parameter>();
        ParameterString parm=new ParameterString("phn2",phn);
        parmMap.put(parm.getName(),parm);

        if(StringUtils.isEmpty(phoneType))
        {
            hql="from Phone where phn2=:phn2";
        }
        else
        {
            hql="from Phone where phn2=:phn2 and phonetypid=:phoneType";

            ParameterString parmType=new ParameterString("phoneType",phoneType);
            parmMap.put(parmType.getName(),parmType);
        }


        return this.findList(hql, parmMap);
    }

    public List<Phone> findPhonesFromContactId(String contactId)
    {
        return this.findList("from Phone where contactid=:contactid", new ParameterString("contactid",contactId));
    }
}
