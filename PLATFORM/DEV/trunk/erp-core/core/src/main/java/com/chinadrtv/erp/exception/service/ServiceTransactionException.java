package com.chinadrtv.erp.exception.service;

import com.chinadrtv.erp.exception.ErpException;
import com.chinadrtv.erp.exception.ExceptionConstant;

/**
 * 橡果国际IT系统集成部
 * User: liuhaidong
 * Email:liuhaidong@chinadrtv.com
 * Date: 13-5-21
 * Time: 下午3:09
 * SERVICE 层方法严重异常，需要回滚事务
 */
public class ServiceTransactionException extends ErpException {
    public ServiceTransactionException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    public ServiceTransactionException(String errorCode, String message) {
        super(errorCode, message);
    }

    public ServiceTransactionException(String errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public ServiceTransactionException(String message) {
        super(ExceptionConstant.SERVICE_TRANSACTION_EXCEPTION, message);
    }
}
