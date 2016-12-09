package com.chinadrtv.erp.tc.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 13-1-24
 */
@ContextConfiguration("classpath:applicationContext-*.xml")
public class OrderRESTClient<FParam,RParam extends Object> extends AbstractJUnit4SpringContextTests {
    @Autowired
    private RestTemplate template;

    //protected Class<RParam> returnClass;

    //private final static String url = "http://localhost:8080/erp-tc/order";

    public RParam postData(String url,FParam param)
    {

        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Class<RParam> returnClass = (Class) params[1];

        return template.postForObject(url, param, returnClass);
    }
    /*public MyData getData()
    {
        MyData myData=new MyData();
        myData.setlData(1L);
        myData.setStrData("test");

        return template.postForObject(url + "/save", myData, MyData.class);
    }*/



}
