package com.chinadrtv.erp.sales.core.util;

import com.chinadrtv.erp.exception.ErpException;
import com.chinadrtv.erp.tc.core.utils.OrderException;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-6-8
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class ExceptionMsgUtil {
    public static String getMessage(Exception exp)
    {
        StringBuilder strBld=new StringBuilder();
        getMessageInner(strBld, exp);
        return strBld.toString();
    }

    private static void getMessageInner(StringBuilder strBld, Throwable exp)
    {
        if(exp instanceof ErpException)
        {
            ErpException erpException=(ErpException)exp;
            if(exp.getCause()!=null)
            {
                strBld.append(erpException.getMessage()+":");
                getMessageInner(strBld, exp.getCause());
            }
            else
            {
                strBld.append(erpException.getMessage());
            }
        }
        else if(exp instanceof OrderException)
        {
            strBld.append(((OrderException)exp).getOrderReturnCode().getDesc());
        }
        else
        {
            strBld.append(exp.getMessage());
        }
    }
}
