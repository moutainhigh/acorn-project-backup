package com.chinadrtv.erp.tc.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.erp.tc.core.constant.OrderCode;
import com.chinadrtv.erp.tc.core.model.OrderReturnCode;
import com.chinadrtv.erp.tc.core.model.ReturnInfo;

/**
 * 控制器基类 (TC).
 * User: 徐志凯
 * Date: 13-2-26
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public abstract class TCBaseController {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TCBaseController.class);
    @ExceptionHandler
    @ResponseBody
    public ReturnInfo handleException(Exception ex, HttpServletRequest request) {
        logger.error(request.getRequestURI()+ "-base error:", ex);

        if(ex instanceof ConstraintViolationException)
        {
            OrderReturnCode orderReturnCode=new OrderReturnCode();
            orderReturnCode.setCode(OrderCode.FIELD_INVALID);
            orderReturnCode.setDesc(this.getConstraintErrorDesc((ConstraintViolationException)ex));
            return orderReturnCode;
        }
        else if(ex instanceof HttpMessageNotReadableException)
        {
            //HttpMessageNotReadableException httpMessageNotReadableException=(HttpMessageNotReadableException)ex;
            return new OrderReturnCode(OrderCode.INPUT_PARM_ERROR,"输入参数形式不正确。");
        }
        else
        {
            return new OrderReturnCode(OrderCode.SYSTEM_ERROR,ex.getMessage());
        }
    }

    protected String getConstraintErrorDesc(ConstraintViolationException constraintExp)
    {
        Set<ConstraintViolation<?>> errorSet= constraintExp.getConstraintViolations();
        if(errorSet!=null&&errorSet.size()>0)
        {
            for(ConstraintViolation<?> error: errorSet)
            {
                return error.getPropertyPath()+":" +error.getMessage();
            }
        }
        return "";
    }
}
