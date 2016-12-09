package com.chinadrtv.taobao.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: guoguo
 * Date: 13-1-29
 * Time: 下午1:24
 * To change this template use File | Settings | File Templates.
 */
public class TaobaoOrderConfigMap {
    public static Map<String, TaobaoOrderConfig> orderTypeMap = new HashMap<String, TaobaoOrderConfig>() ;

    public static void setOrderTypeMap(Map<String, TaobaoOrderConfig> orderTypeMap){
        TaobaoOrderConfigMap.orderTypeMap = orderTypeMap;
    }

}
