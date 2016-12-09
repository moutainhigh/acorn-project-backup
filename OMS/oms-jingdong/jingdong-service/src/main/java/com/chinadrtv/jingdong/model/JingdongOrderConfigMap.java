package com.chinadrtv.jingdong.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-11-8
 * Time: 下午2:20
 * To change this template use File | Settings | File Templates.
 */
public class JingdongOrderConfigMap {
    public static Map<String, JingdongOrderConfig> orderTypeMap = new HashMap<String, JingdongOrderConfig>() ;

    public static void setOrderTypeMap(Map<String, JingdongOrderConfig> orderTypeMap){
        JingdongOrderConfigMap.orderTypeMap = orderTypeMap;
    }
}
