package com.chinadrtv.erp.tc.core.utils;

import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 根据多主键获取实体的辅助类
 * User: 徐志凯
 * Date: 13-3-4
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class BulkListSplitter<T extends Serializable> {
    private int batchSize;
    private String tableName;
    private String keyName;

    public BulkListSplitter(int batchSize,String tableName,String keyName)
    {
        this.batchSize=batchSize;
        this.tableName=tableName;
        this.keyName=keyName;
    }
    public List<List<T>> splitList(List<T> list)
    {
        List<List<T>> listList=new ArrayList<List<T>>();

        int count=list.size() / batchSize;
        int lessCount=list.size()%batchSize;

        for(int i=0;i<count;i++)
        {
            List itemList=new ArrayList();
            for(int j=0;j<batchSize;j++)
            {
                itemList.add(list.get(i*batchSize+j));
            }
            listList.add(itemList);

        }
        if(lessCount>0)
        {
            List itemList=new ArrayList();
            for(int j=count*batchSize;j<list.size();j++)
            {
                itemList.add(list.get(j));
            }
            listList.add(itemList);
        }

        return listList;
    }

    public String getWhereHql(List<T> keyIdList,Map<String,T> parms)
    {
        StringBuilder hql=new StringBuilder(" where ");

        for(int i=0;i<keyIdList.size();i++)
        {
            if(i>0)
            {
                hql.append(" or ");
            }
            String name="id"+i;
            hql.append(keyName + "=:" + name);
            parms.put(name,keyIdList.get(i));
        }

        return hql.toString();
    }

    public String getHql(List<T> keyIdList,Map<String,T> parms)
    {
        return "from "+tableName+this.getWhereHql(keyIdList,parms);
        /*StringBuilder hql=new StringBuilder("from "+ tableName +" where ");

        for(int i=0;i<keyIdList.size();i++)
        {
            if(i>0)
            {
                hql.append(" or ");
            }
            String name="id"+i;
            hql.append(keyName + "=:" + name);
            parms.put(name,keyIdList.get(i));
        }

        return hql.toString();*/
    }

}
