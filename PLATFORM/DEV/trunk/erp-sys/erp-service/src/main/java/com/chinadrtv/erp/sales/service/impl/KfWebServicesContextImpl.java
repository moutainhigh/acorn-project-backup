package com.chinadrtv.erp.sales.service.impl;

import com.chinadrtv.erp.service.core.service.KfServicesContext;
import com.chinadrtv.erp.util.StringUtil;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-5-5
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Primary
@Service
public class KfWebServicesContextImpl implements KfServicesContext {
    @Override
    public String getParentOrderId() {
        Object obj=RequestContextHolder.getRequestAttributes().getAttribute("services_parentOrderId",RequestAttributes.SCOPE_REQUEST);
        if(obj!=null)
        {
            return (String)obj;
        }
        return null;
    }

    @Override
    public void setParentOrderId(String parentId) {
        if(parentId!=null)
        {
            if("undefined".equalsIgnoreCase(parentId))
            {
                parentId=null;
            }
            else if("null".equalsIgnoreCase(parentId))
            {
                parentId=null;
            }
            else
            {
                if(!StringUtil.isNumeric(parentId))
                {
                   parentId=null;
                }
            }
        }
        if(parentId!=null)
            RequestContextHolder.getRequestAttributes().setAttribute("services_parentOrderId", parentId, RequestAttributes.SCOPE_REQUEST);
    }
}
