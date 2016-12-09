package com.chinadrtv.erp.exception.dao;

import com.chinadrtv.erp.exception.ErpException;
import com.chinadrtv.erp.exception.ExceptionConstant;

/**
 * 橡果国际IT系统集成部
 * User: liuhaidong
 * Date: 13-5-21
 * Time: 上午11:07
 *
 *  * 数据更新的异常，如更新对象报错，需要回滚事务
 */
public class DaoTransactionException extends ErpException {
    public DaoTransactionException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    public DaoTransactionException(String errorCode, String message) {
        super(errorCode, message);
    }

    public DaoTransactionException(String errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public DaoTransactionException(String message) {
        super(ExceptionConstant.DAO_TRANSACTION_EXCEPTION, message);
    }
}
