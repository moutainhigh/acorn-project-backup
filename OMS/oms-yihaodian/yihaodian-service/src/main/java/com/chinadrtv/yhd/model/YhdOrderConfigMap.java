package com.chinadrtv.yhd.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 14-03-20
 * Time: 下午1:24
 * To change this template use File | Settings | File Templates.
 */
public class YhdOrderConfigMap {
    public static Map<String, YhdOrderConfig> orderTypeMap = new HashMap<String, YhdOrderConfig>() ;

    public static void setOrderTypeMap(Map<String, YhdOrderConfig> orderTypeMap){
        YhdOrderConfigMap.orderTypeMap = orderTypeMap;
    }

}
