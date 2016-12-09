package com.chinadrtv.erp.constant;

/**
 * 货位类型
 * User: gaodejian
 * Date: 13-1-29
 * Time: 下午4:40
 * To change this template use File | Settings | File Templates.
 * 1正品；2残品；3待报废；4待特卖
 */
public class LocationTypeConstants {
    public static final String INVENTORY_QUALITY_PRODUCT  = "1";
    public static final String INVENTORY_DEFECTIVE_PRODUCT  = "2";
    public static final String INVENTORY_WORTHLESS_PRODUCT  = "3";
    public static final String INVENTORY_SPECIAL_PRODUCT  = "4";

    public static String status2LocationType(Long status){
        //当前业务Status与LocationType是一致的
        return String.valueOf(status);
    }

    public static Long locationType2Status(String locationType){
        //当前业务Status与LocationType是一致的
        return Long.parseLong(locationType);
    }
}
