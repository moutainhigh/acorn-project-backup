package com.chinadrtv.erp.exception;

/**
 * 橡果国际IT系统集成部
 * User: liuhaidong
 * Date: 13-5-21
 * Time: 上午11:17
 * 异常的ERROR CODE 常量定义
 */
public class ExceptionConstant {
    //com.chinadrtv.erp.exception.controller.* 异常编码1开始
    public static String CONTROLLER_ACTION_EXCEPTION = "100";
    public static String CONTROLLER_ACTION_PARAMSE_EXCEPTION = "101";
    public static String CONTROLLER_ACTION_SERVICE_EXCEPTION = "102";

    public static String CONTROLLER_AJAX_EXCEPTION = "150";
    public static String CONTROLLER_AJAX_PARAMSE_EXCEPTION = "151";
    public static String CONTROLLER_AJAX_SERVICE_EXCEPTION = "152";

    //com.chinadrtv.erp.exception.service.* 异常编码2开始
    public static String SERVICE_EXCEPTION = "200";
    public static String SERVICE_PARAMETER_EXCEPTION = "201";
    public static String SERVICE_TRANSACTION_EXCEPTION = "202";

    //库存查询异常
    public static String SERVICE_INVENTORY_QUERY_EXCEPTION = "210";
    //库存更新异常
    public static String SERVICE_INVENTORY_UPDATE_EXCEPTION = "211";

    //订单查询异常
    public static String SERVICE_ORDER_QUERY_EXCEPTION = "220";
    //订单更新异常
    public static String SERVICE_ORDER_UPDATE_EXCEPTION = "221";

    public static String SERVICE_ORDER_CORRELATIVE_QUERY_EXCEPTION="222";

    public static String SERVICE_ORDER_CANCEL_EXCEPTION = "223";

    public static String SERVICE_ORDER_CREATE_EXCEPTION="224";

    //用户查询异常
    public static String SERVICE_USER_QUERY_EXCEPTION = "230";
    //用户更新异常
    public static String SERVICE_USER_UPDATE_EXCEPTION = "231";

    //短信发送异常
    public static String SERVICE_SMS_SEND_EXCEPTION = "240";

    //消息异常
    public static String SERVICE_SYSMESSAGE_EXCEPTION="250";

    public static String SERVICE_ORDER_CONSULT_EXCEPTION = "260";

     //com.chinadrtv.erp.exception.security.* 异常编码3开始
    public static String SECURITY_LOGIN_EXCEPTION = "300";
    public static String SECURITY_PERMISSION_EXCEPTION = "301";

    //com.chinadrtv.erp.exception.dao.* 异常编码4开始
    public static String DAO_EXCEPTION = "400";
    public static String DAO_PARAMETER_EXCEPTION = "401";
    public static String DAO_TRANSACTION_EXCEPTION = "402" ;

    public static String SERVICE_CALLBACK_ASSIGN_EXCEPTION="500";
    public static String SERVICE_CALLBACK_VAITUAL_EXCEPTION="501";
}
