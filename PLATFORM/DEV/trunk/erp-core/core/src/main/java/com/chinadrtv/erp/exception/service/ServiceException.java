package com.chinadrtv.erp.exception.service;

import com.chinadrtv.erp.exception.ErpException;
import com.chinadrtv.erp.exception.ExceptionConstant;

/**
 * 橡果国际IT系统集成部
 * User: liuhaidong
 * Email:liuhaidong@chinadrtv.com
 * Date: 13-5-21
 * Time: 下午2:43
 * SERVICE 层的异常
 */
public class ServiceException extends ErpException {
    public ServiceException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    public ServiceException(String errorCode, String message) {
        super(errorCode, message);
    }

    public ServiceException(String errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public ServiceException(String message) {
        super(ExceptionConstant.SERVICE_EXCEPTION, message);
    }
}
