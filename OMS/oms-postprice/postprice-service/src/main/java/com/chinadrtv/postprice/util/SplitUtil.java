package com.chinadrtv.postprice.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-22
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class SplitUtil {
    public static <T> List<List<T>> split(List<T> list, int fetchSize)
    {
        List<List<T>> listList=new ArrayList<List<T>>();

        int count=list.size() / fetchSize ;
        int lessCount=list.size()%fetchSize;

        for(int i=0;i<count;i++)
        {
            List itemList=new ArrayList();
            for(int j=0;j<fetchSize;j++)
            {
                itemList.add(list.get(i*fetchSize+j));
            }
            listList.add(itemList);

        }
        if(lessCount>0)
        {
            List itemList=new ArrayList();
            for(int j=count*fetchSize;j<list.size();j++)
            {
                itemList.add(list.get(j));
            }
            listList.add(itemList);
        }

        return listList;
    }
}
