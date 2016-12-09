package com.chinadrtv.erp.sales.core.util;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-5-13
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class FieldValueCompareUtil {

    private static <T> T getEntity(T obj)
    {
        if(obj instanceof HibernateProxy)
        {
            return  (T)((HibernateProxy)obj).getHibernateLazyInitializer().getImplementation();
        }
        else
        {
            return obj;
        }
    }
    public static <T> List<String> compare(T obj1, T Obj2, List<String> ignoreFldNameList){

        List<String> result = new ArrayList<String>();
        obj1=getEntity(obj1);
        Obj2=getEntity(Obj2);
        Field[] fs = obj1.getClass().getDeclaredFields();
        for (Field f : fs) {
            if(ignoreFldNameList!=null && ignoreFldNameList.contains(f.getName()))
                continue;
            if(Modifier.isStatic(f.getModifiers()))
                continue;
            f.setAccessible(true);
            try
            {
                Object v1 = f.get(obj1);
                Object v2 = f.get(Obj2);
                if(!equals(v1,v2))
                   result.add(f.getName());
            }catch (Exception exp)
            {

            }
        }
        return result;
    }


    public static boolean equals(Object obj1, Object obj2) {
        obj1=getEntity(obj1);
        obj2=getEntity(obj2);

        if (obj1 == obj2) {
            return true;
        }
        if (obj1 == null || obj2 == null) {
            return false;
        }
        return obj1.equals(obj2);
    }

    public static <T> void copyFlds(T srcObj,T detObj,List<String> fldNameList)
    {
        srcObj=getEntity(srcObj);
        detObj=getEntity(detObj);
        Field[] fs = srcObj.getClass().getDeclaredFields();
        for (Field f : fs) {
            if(!fldNameList.contains(f.getName()))
                continue;
            if(Modifier.isStatic(f.getModifiers()))
                continue;
            f.setAccessible(true);
            try
            {
                Object v1 = f.get(srcObj);
                f.set(detObj,v1);
            }catch (Exception exp)
            {

            }
        }
    }

    public static <T> void copy(T srcObj,T detObj,List<String> fldNameList)
    {
        srcObj=getEntity(srcObj);
        detObj=getEntity(detObj);
        Field[] fs = srcObj.getClass().getDeclaredFields();
        for (Field f : fs) {
            if(fldNameList.contains(f.getName()))
                continue;
            if(Modifier.isStatic(f.getModifiers()))
                continue;
            f.setAccessible(true);
            try
            {
                Object v1 = f.get(srcObj);
                f.set(detObj,v1);
            }catch (Exception exp)
            {

            }
        }
    }

    public static <T> void copyNullFld(T srcObj,T detObj)
    {
        srcObj=getEntity(srcObj);
        detObj=getEntity(detObj);
        Field[] fs = srcObj.getClass().getDeclaredFields();
        for (Field f : fs) {
            if(Modifier.isStatic(f.getModifiers()))
                continue;
            f.setAccessible(true);
            try
            {
                Object v1 = f.get(srcObj);
                Object v2=f.get(detObj);
                if(v2==null)
                    f.set(detObj,v1);
            }catch (Exception exp)
            {

            }
        }
    }

    public static <T> void copyNotNullFld(T srcObj,T detObj)
    {
        srcObj=getEntity(srcObj);
        detObj=getEntity(detObj);
        Field[] fs = srcObj.getClass().getDeclaredFields();
        for (Field f : fs) {
            if(Modifier.isStatic(f.getModifiers()))
                continue;

            f.setAccessible(true);
            try
            {
                Object v1 = f.get(srcObj);
                if(v1!=null)
                    f.set(detObj,v1);
            }catch (Exception exp)
            {

            }
        }
    }

    public static <T> boolean isValueNull(T obj,List<String> fldNameList)
    {
        obj=getEntity(obj);
        Field[] fs = obj.getClass().getDeclaredFields();
        for (Field f : fs) {
            if(fldNameList!=null&& fldNameList.contains(f.getName()))
                continue;
            if(Modifier.isStatic(f.getModifiers()))
                continue;
            f.setAccessible(true);
            try
            {
                Object v1 = f.get(obj);
                if(v1!=null)
                    return false;
            }catch (Exception exp)
            {

            }
        }
        return true;
    }

}
