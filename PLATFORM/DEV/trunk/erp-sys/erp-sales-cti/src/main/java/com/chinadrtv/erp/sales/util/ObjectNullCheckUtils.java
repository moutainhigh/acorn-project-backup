package com.chinadrtv.erp.sales.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-6-6
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class ObjectNullCheckUtils {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ObjectNullCheckUtils.class);

    public static boolean isFieldsNull(Object obj,List<String> ignoreFieldList)
    {
        if(obj ==null)
            return true;
        Class<?> clazz=obj.getClass();
        while (clazz!=Object.class)
        {
            for(Field field : clazz.getDeclaredFields())
            {
                if(ignoreFieldList.contains(field.getName().toUpperCase()))
                    continue;
                if(!Modifier.isStatic(field.getModifiers()))
                {
                    field.setAccessible(true);

                    try{
                        Object fieldValue=field.get(obj);
                        if(fieldValue==null||"".equals(fieldValue))
                        {

                        }
                        else
                            return false;
                    }catch (Exception exp)
                    {
                        logger.error("field get error:",exp);
                        return false;
                    }
                }
            }
            clazz=clazz.getSuperclass();
        }

        return true;
    }
}
