package com.chinadrtv.erp.tc.core.utils;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import com.chinadrtv.erp.model.agent.OrderDetail;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 13-1-30
 */
public class OrderdetUtil extends ReflectFieldValueUtil<OrderDetail> {
    @Override
    public void init()
    {
        super.init();
        //去掉Id和orderId
        Set<Field> newFields = new HashSet<Field>();
        for(Field field: fields)
        {
            if(!field.getName().equalsIgnoreCase("id")&&!field.getName().equalsIgnoreCase("orderhist"))
            {
                newFields.add(field);
            }
        }
        fields=newFields;
    }
}
