package com.chinadrtv.model.constant;

/**
 * 物流状态
 * User: xuzk
 * Date: 13-2-5
 */
public interface LogisticsStatusConstants {
    /**
     * 正常
     */
    public static String LOGISTICS_NORMAL = "0";
    /**
     * 已取消
     */
    public static String LOGISTICS_CANCEL = "1";
    /**
     * 已出货
     */
    public static String LOGISTICS_OUT = "2";
    /**
     * 拣货确认（已废弃）
     */
    public static String LOGISTICS_TRANS = "3";
    /**
     * 等待仓库处理
     */
    public static String LOGISTICS_WMS_WAIT = "-100";
    /**
     * 正在加入处理列表
     */
    public static String LOGISTICS_WMS_HANDLING = "-200";
    /**
     * 分配拣货
     */
    public static String LOGISTICS_WMS_DISTRIBUTION="-300";
    /**
     * 拣货完成，等待包装
     */
    public static String LOGISTICS_WMS_PICKING = "-400";
    /**
     * 包装中
     */
    public static String LOGISTICS_WMS_PACKING = "-401";
    /**
     * 等待出库扫描
     */
    public static String LOGISTICS_WMS_SCANNING = "-700";
    /**
     * 出库扫描完成，等待快递上交接
     */
    public static String LOGISTICS_WMS_POSTING = "-701";
    /**
     * 已出库，快递商接收 （仓库交接给送货公司）
     */
    public static String LOGISTICS_WMS_OUT = "-900";
    /**
     * 送货公司签收 （已废弃不用）
     */
    public static String LOGISTICS_COMAPNY_SIGN = "4";
    /**
     * 客户签收
     */
    public static String LOGISTICS_CONTACT_SIGN = "5";
    /**
     * 拒收未入库
     */
    public static String LOGISTICS_REJECTION = "6";
    /**
     * 问题单
     */
    public static String LOGISTICS_PROBLEM = "7";
    /**
     * 结算完成
     */
    public static String LOGISTICS_ACCOUNT = "8";
    /**
     * 拒收入库
     */
    public static String LOGISTICS_REJECTION_STOCKIN = "9";
    /**
     * 配送异常
     */
    public static String LOGISTICS_SEND_EXCEPTION = "10";
    /**
     * 总站收件
     */
    public static String LOGISTICS_TERMINUS_RECIPIENT = "11";
    /**
     * 派送点收件
     */
    public static String LOGISTICS_DELIVERTYPOINT_RECIPIENT = "12";
    /**
     * 派件
     */
    public static String LOGISTICS_SEND_PIECES = "13";
    /**
     * 部分签收
     */
    public static String LOGISTICS_PARTIAL_SIGN = "14";
    /**
     * 丢失
     */
    public static String LOGISTICS_LOST = "15";
    /**
     * 退回
     */
    public static String LOGISTICS_RETURN = "16";
    /**
     * 转站
     */
    public static String LOGISTICS_TRANSFER_STATION = "17";
    /**
     * 二次配送
     */
    public static String LOGISTICS_SECONDARY_DISTRIBUTION = "18";
    /**
     * 三次配送
     */
    public static String LOGISTICS_THREE_DISTRIBUTION = "19";
}
