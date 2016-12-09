package com.chinadrtv.erp.exception.service;

import com.chinadrtv.erp.exception.ErpException;
import com.chinadrtv.erp.exception.ExceptionConstant;

/**
 * 橡果国际IT系统集成部
 * User: liuhaidong
 * Email:liuhaidong@chinadrtv.com
 * Date: 13-5-21
 * Time: 下午2:39
 *SERVICE 层方法参数校验异常，如日期参数范围过大，超过半年
 */
public class ServiceParameterException extends ErpException {
    public ServiceParameterException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    public ServiceParameterException(String errorCode, String message) {
        super(errorCode, message);
    }

    public ServiceParameterException(String errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public ServiceParameterException(String message) {
        super(ExceptionConstant.SERVICE_PARAMETER_EXCEPTION, message);
    }
}
