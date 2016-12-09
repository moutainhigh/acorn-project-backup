package com.chinadrtv.erp.exception.dao;

import com.chinadrtv.erp.exception.ErpException;
import com.chinadrtv.erp.exception.ExceptionConstant;

/**
 * 橡果国际IT系统集成部
 * User: liuhaidong
 * Date: 13-5-21
 * Time: 上午10:58
 *
 * 数据查询的异常，如返回对象是null或List为空
 */
public class DaoException extends ErpException {
    public DaoException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    public DaoException(String errorCode, String message) {
        super(errorCode, message);
    }

    public DaoException(String errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public DaoException(String message) {
        super(ExceptionConstant.DAO_EXCEPTION, message);
    }
}
