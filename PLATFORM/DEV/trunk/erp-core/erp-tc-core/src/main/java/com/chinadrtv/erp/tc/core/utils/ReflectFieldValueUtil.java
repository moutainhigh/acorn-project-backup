package com.chinadrtv.erp.tc.core.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Date: 13-1-28
 */
public class ReflectFieldValueUtil<T extends Object> {
    protected Set<Field> fields = new HashSet<Field>();

    public void init()
    {
        //Class<?> genClass;
        Type genType=getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Class<T> tempClass = (Class) params[0];
            for(Field field : tempClass.getDeclaredFields())
            {
                if(!Modifier.isStatic(field.getModifiers()))
                {
                    field.setAccessible(true);

                    fields.add(field);
                }
            }

        //查找父类中的属性
        Class<?> clazz=tempClass.getSuperclass();
        while (clazz!=Object.class)
        {
            for(Field field : clazz.getDeclaredFields())
            {
                if(!Modifier.isStatic(field.getModifiers()))
                {
                    field.setAccessible(true);

                    fields.add(field);
                }
            }
            clazz=clazz.getSuperclass();
        }
    }

    public void CopyNotNullValue(T orgObj,T toObj)
    {
        try
        {
            for(Field field:this.fields)
            {
                Object fieldValue=field.get(orgObj);
                if(fieldValue!=null)
                {
                    //如果是空字符串，现在默认为null
                    if("".equals(fieldValue))
                    {
                        field.set(toObj,null);
                    }
                    else
                    {
                        field.set(toObj,fieldValue);
                    }
                }
            }
        }catch (Exception e)
        {

        }
    }
}
