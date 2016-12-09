package com.chinadrtv.erp.exception.controller;

import com.chinadrtv.erp.exception.ErpException;
import com.chinadrtv.erp.exception.ExceptionConstant;

/**
 * 橡果国际IT系统集成部
 * User: liuhaidong
 * Date: 13-5-21
 * Time: 上午10:52
 * Controller代码层中,如果客户端是返回AJAX的HTTP请求，响应请求发生异常后，如果没有明确的
 * 处理该异常的业务流程，则抛出此异常，由专门负责AjaxException的类产生异常的JSON对象，界面
 * 负责处理异常的JAVASCRIPT 显示异常信息，处理界面逻辑。
 */
public class AjaxException  extends ErpException {
    public AjaxException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    public AjaxException(String errorCode, String message) {
        super(errorCode, message);
    }

    public AjaxException(String errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public AjaxException( String message) {
        super(ExceptionConstant.CONTROLLER_AJAX_EXCEPTION, message);
    }
}
