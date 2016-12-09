package com.chinadrtv.erp.exception.security;

import com.chinadrtv.erp.exception.ErpException;
import com.chinadrtv.erp.exception.ExceptionConstant;

/**
 * 橡果国际IT系统集成部
 * User: liuhaidong
 * Email:liuhaidong@chinadrtv.com
 * Date: 13-5-21
 * Time: 上午11:55
 *
 * 用户登录异常，如用户名，密码不正确
 */
public class LoginException extends ErpException {
    public LoginException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    public LoginException(String errorCode, String message) {
        super(errorCode, message);
    }

    public LoginException(String errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public LoginException(String message) {
        super(ExceptionConstant.SECURITY_LOGIN_EXCEPTION, message);
    }
}
