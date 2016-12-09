package com.chinadrtv.erp.exception.security;

import com.chinadrtv.erp.exception.ErpException;
import com.chinadrtv.erp.exception.ExceptionConstant;

/**
 * 橡果国际IT系统集成部
 * User: liuhaidong
 * Email:liuhaidong@chinadrtv.com
 * Date: 13-5-21
 * Time: 下午3:39
 *
 * 用户没有执行当前操作的权限
 */
public class PermissionException  extends ErpException {
    public PermissionException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    public PermissionException(String errorCode, String message) {
        super(errorCode, message);
    }

    public PermissionException(String errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public PermissionException(String message) {
        super(ExceptionConstant.SECURITY_PERMISSION_EXCEPTION, message);
    }
}
