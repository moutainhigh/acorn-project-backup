package com.chinadrtv.erp.exception.dao;

import com.chinadrtv.erp.exception.ErpException;
import com.chinadrtv.erp.exception.ExceptionConstant;

/**
 * 橡果国际IT系统集成部
 * User: liuhaidong
 * Email:liuhaidong@chinadrtv.com
 * Date: 13-5-22
 * Time: 上午10:43
 * DAO层参数校验异常，如日期参数范围过大，超过半年
 */
public class DaoParameterException  extends ErpException {
    public DaoParameterException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    public DaoParameterException(String errorCode, String message) {
        super(errorCode, message);
    }

    public DaoParameterException(String errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public DaoParameterException(String message) {
        super(ExceptionConstant.DAO_PARAMETER_EXCEPTION, message);
    }
}
