package com.chinadrtv.erp.tc.core.utils;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;

import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.OrderDetail;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 13-1-28
 */
public class OrderhistUtil extends ReflectFieldValueUtil<Order> {

    @Override
    public void init()
    {
        super.init();
        //去掉Id和orderId
        Set<Field> newFields = new HashSet<Field>();
        for(Field field: fields)
        {
            if(!field.getName().equalsIgnoreCase("orderid")&&!field.getName().equalsIgnoreCase("orderdets"))
            {
               newFields.add(field);
            }
        }
        fields=newFields;
    }

    public static void copy(Order orderhist, Order orderhistSource)
    {
        BeanUtils.copyProperties(orderhistSource, orderhist);
        if(orderhistSource.getOrderdets()!=null)
        {
            orderhist.setOrderdets(new HashSet<OrderDetail>());
            for(OrderDetail orderdet: orderhistSource.getOrderdets())
            {
            	OrderDetail orderdet1=new OrderDetail();
                BeanUtils.copyProperties(orderdet,orderdet1);
                orderhist.getOrderdets().add(orderdet1);
                orderdet1.setOrderhist(orderhist);
            }
        }
    }
}
