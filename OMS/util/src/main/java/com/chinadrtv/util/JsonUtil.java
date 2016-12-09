package com.chinadrtv.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;


public class JsonUtil {
    private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    /**
     *  json转化成 java bean
     * 
     * @param json
     * @param beanClass 
     * @return  java bean of beanClass
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> T toObject(String json, Class beanClass) {
        if (json == null) {
            logger.error("json is null");
            return null;
        }
        if (beanClass == null) {
            logger.error("beanClass is null");
            return null;
        }
        Gson gson = new Gson();
        T object;
        try {
            object = (T) gson.fromJson(json, beanClass);
        } catch (RuntimeException e) {
            logger.error("json:" + json, e);
            throw e;
        }
        return object;
    }

    /**
     * java bean 转json
     * 
     * @param object java bean
     * @param beanClass  需要转化成json的bean对象的class
     * @return  json string
     */
    @SuppressWarnings("rawtypes")
    public static String toJson(Object object, Class beanClass) {
        if (object == null) {
            logger.error("object is null");
            return null;
        }
        if (beanClass == null) {
            logger.error("beanClass is null");
            return null;
        }
        Gson gson = new Gson();
        return gson.toJson(object, beanClass);
    }
}
