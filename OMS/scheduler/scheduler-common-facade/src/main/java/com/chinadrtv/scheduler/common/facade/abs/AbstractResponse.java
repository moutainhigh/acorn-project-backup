/**
 * 
 *	平安付
 * Copyright (c) 2013-2013 PingAnFu,Inc.All Rights Reserved.
 */
package com.chinadrtv.scheduler.common.facade.abs;

import java.io.Serializable;

/**
 * 
 * @author xieen
 * @version $Id: AbstractResponse.java, v 0.1 2013-8-8 上午10:02:04 xieen Exp $
 */
public abstract class AbstractResponse implements Serializable {

    private static final long serialVersionUID = 5534594833343866897L;
    /** 系统返回码 */
    private String            respCode;

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    
}
