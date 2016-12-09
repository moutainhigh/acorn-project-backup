package com.chinadrtv.erp.tc.core.service.impl;

import com.chinadrtv.erp.tc.core.dao.OrderhistDao;
import com.chinadrtv.erp.tc.core.model.CompanyAssignQuantity;
import com.chinadrtv.erp.tc.core.service.CompanyAssignQuantityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 承运商当日运输量服务.
 * -目前直接放在内存中
 * -后期如果服务器切换，可以初始化时一次从订单表中获取即可
 * User: 徐志凯
 * Date: 13-4-22
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class CompanyAssignQuantityServiceImpl implements CompanyAssignQuantityService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CompanyAssignQuantityServiceImpl.class);

    private static List<CompanyAssignQuantity> companyAssignQuantityList = null;
    public void resetCompanyAssignQuantity()
    {
        companyAssignQuantityList=null;
    }

    public void updateCompanyAssignQuantity(Long companyId, Long addQuantity, BigDecimal addPrice)
    {
        if(companyAssignQuantityList==null)
        {
            return;
        }
        for(int i=0;i<companyAssignQuantityList.size();i++)
        {
            if(companyAssignQuantityList.get(i).getCompanyId().equals(companyId))
            {
                //检查数据是否当天的
                if(isCurrentDay(companyAssignQuantityList.get(i).getMdDate()))
                {
                    Long quantity= companyAssignQuantityList.get(i).getQuantity();
                    BigDecimal price=companyAssignQuantityList.get(i).getTotalPrice();
                    quantity+=addQuantity;
                    companyAssignQuantityList.get(i).setQuantity(quantity);
                    companyAssignQuantityList.get(i).setTotalPrice(price.add(addPrice));
                    return;
                }
                else
                {
                    CompanyAssignQuantity companyAssignQuantity=companyAssignQuantityList.get(i);
                    companyAssignQuantity.setQuantity(addQuantity);
                    companyAssignQuantity.setTotalPrice(addPrice);
                    companyAssignQuantity.setMdDate(new Date());
                    return;
                }
            }
        }

        CompanyAssignQuantity companyAssignQuantity=new CompanyAssignQuantity();
        companyAssignQuantity.setQuantity(addQuantity);
        companyAssignQuantity.setTotalPrice(addPrice);
        companyAssignQuantity.setMdDate(new Date());
        companyAssignQuantity.setCompanyId(companyId);

        companyAssignQuantityList.add(companyAssignQuantity);
        return;
    }

    public CompanyAssignQuantity getCompanyAssignQuantity(Long companyId)
    {
        if(companyAssignQuantityList==null)
        {
            logger.debug("begin fetch company quantity from db");
            companyAssignQuantityList=orderhistDao.getCurrentCompanyAssignQuantity();
            logger.debug("end fetch company quantity from db");
            if(companyAssignQuantityList==null)
            {
                companyAssignQuantityList=new ArrayList<CompanyAssignQuantity>();
            }
        }
        for(int i=0;i<companyAssignQuantityList.size();i++)
        {
            if(companyAssignQuantityList.get(i).getCompanyId().equals(companyId))
            {
                //检查数据是否当天的
                if(isCurrentDay(companyAssignQuantityList.get(i).getMdDate()))
                {
                    logger.info("company id:"+companyId+"-- quantity:"+companyAssignQuantityList.get(i).getQuantity());
                    return companyAssignQuantityList.get(i);
                }
                else
                {
                    companyAssignQuantityList.remove(i);
                    logger.info("company id:"+companyId+"-- remove");
                    return null;
                }
            }
        }
        return null;
    }

    @Autowired
    private OrderhistDao orderhistDao;

    private boolean isCurrentDay(Date dt)
    {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DATE);

        Calendar calendar2=Calendar.getInstance();
        calendar2.setTime(dt);
        int month2 = calendar2.get(Calendar.MONTH);
        int date2 = calendar2.get(Calendar.DATE);

        if(month==month2&&date==date2)
            return true;
        return false;
    }


    /*@Autowired
    private CompanyAssignQuantityDao companyAssignQuantityDao;

    public void updateCompanyAssignQuantity(Long companyId, Long addQuantity)
    {
        CompanyAssignQuantity companyAssignQuantity = null;
        if(!companyAssignQuantityDao.exists(companyId))
        {
            companyAssignQuantity=new CompanyAssignQuantity();
            companyAssignQuantity.setMdDate(new Date());
            companyAssignQuantity.setCompanyId(companyId);
            companyAssignQuantity.setQuantity(addQuantity);
        }
        else
        {
            companyAssignQuantity=companyAssignQuantityDao.get(companyId);
            //检查是否数量已经改变了
            companyAssignQuantity.setMdDate(new Date());
            companyAssignQuantity.setQuantity(companyAssignQuantity.getQuantity()+addQuantity);
        }

        companyAssignQuantityDao.saveOrUpdate(companyAssignQuantity);
    }

    public List<CompanyAssignQuantity> getAllCompanyAssignQuantity()
    {
        return companyAssignQuantityDao.getAll();
    }

    private Date lastClearDate;

    public void clearAncientCompanyAssignQuantity()
    {
        Date dtNow = new Date();
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(dtNow);

        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DATE);
        int year= calendar.get(Calendar.YEAR);

        if(lastClearDate==null)
        {
            //直接删除
            calendar.set(year,month,date,0,0,0);
            companyAssignQuantityDao.clearByDate(calendar.getTime());
            lastClearDate=dtNow;
        }
        else
        {
            //检查天数是否变化了
            Calendar calendarLast=Calendar.getInstance();
            calendarLast.setTime(lastClearDate);
            int lastMonth = calendarLast.get(Calendar.MONTH);
            int lastDate = calendarLast.get(Calendar.DATE);

            if(lastMonth!=month||lastDate!=date)
            {
                //删除操作
                calendar.set(year,month,date,0,0,0);
                companyAssignQuantityDao.clearByDate(calendar.getTime());
                lastClearDate=dtNow;
            }
        }
    }*/
}
