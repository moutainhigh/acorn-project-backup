package com.chinadrtv.erp.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 橡果国际IT系统集成部
 * User: liuhaidong
 * Date: 13-5-20
 * Time: 上午10:01
 *
 * 自定义异常的基类，创建异常后，LOG记录异常代码和异常信息
 */
public class ErpException extends Exception {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(ErpException.class);


    private String errorCode;

    public ErpException(String errorCode,String message, Throwable cause) {
        super(message, cause);
        getLogger().error(errorCode + " : " + message, cause);
    }

    public ErpException(String errorCode,String message) {
        super(message);
        this.errorCode = errorCode;
        getLogger().error(errorCode + " : " + message, this);
    }

    public ErpException(String errorCode,Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        getLogger().error(errorCode + " : " + cause.getMessage(), cause);
    }

    /**
     * This method returns a logger which can be used by a subclass. The logger is specific for the
     * instance of the Exception (the subclass).
     *
     * @return the class-specific Logger
     */
    protected Logger getLogger() {
     return log;
    }

	public String getErrorCode() {
		return errorCode;
	}
}
