package com.chinadrtv.erp.exception.controller;

import com.chinadrtv.erp.exception.ErpException;
import com.chinadrtv.erp.exception.ExceptionConstant;

/**
 * 橡果国际IT系统集成部
 * User: liuhaidong
 * Date: 13-5-21
 * Time: 上午10:51
 *
 * Controller代码层中,如果客户端是返回页面的HTTP请求，响应请求发生异常后，如果没有明确的
 * 处理该异常的业务流程，则抛出此异常，由专门负责ActionException的类导向异常处理页面，并
 * 根据ERR CODE 显示详细异常信息。
 */
public class ActionException extends ErpException {
    public ActionException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    public ActionException(String errorCode, String message) {
        super(errorCode, message);
    }

    public ActionException(String errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public ActionException( Throwable cause) {
        super(ExceptionConstant.CONTROLLER_ACTION_EXCEPTION, cause);
    }
}
