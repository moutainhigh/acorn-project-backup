package com.chinadrtv.erp.tc.core.constant;

/**
 * 结算状态
 * User: xuzk
 * Date: 13-2-5
 */
public interface AccountStatusConstants {
    /**
     * 取消
     */
    public static String ACCOUNT_CANCEL = "0";
    /**
     * 生成（订购）
     */
    public static String ACCOUNT_NEW = "1";
    /**
     * 放单（分拣）
     */
    public static String ACCOUNT_TRANS = "2";
    /**
     * 完成
     */
    public static String ACCOUNT_FINI = "5";
    /**
     * 拒收
     */
    public static String ACCOUNT_REJECTION = "6";
    /**
     * 压单
     */
    public static String ACCOUNT_NOSEND = "7";
    /**
     * 物流取消（保持原有兼容）
     */
    public static String ACCOUNT_WMSCANCEL = "8";
    /**
     * 装车(已废弃不用)
     */
    public static String ACCOUNT_ACCOUNT = "9";

}
