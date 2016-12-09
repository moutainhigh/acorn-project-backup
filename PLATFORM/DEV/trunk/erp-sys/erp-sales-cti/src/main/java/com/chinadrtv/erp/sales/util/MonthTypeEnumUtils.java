package com.chinadrtv.erp.sales.util;

import com.chinadrtv.erp.sales.core.constant.MonthType;
import org.apache.commons.lang.StringUtils;

import java.beans.PropertyEditorSupport;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-7-8
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class MonthTypeEnumUtils extends PropertyEditorSupport {
    @Override public void setAsText(final String text) throws IllegalArgumentException
    {
        if(StringUtils.isNotEmpty(text))
        {
            Integer index=Integer.parseInt(text);
            for(MonthType monthType:MonthType.values())
            {
                if(monthType.getIndex()==index.intValue())
                {
                    setValue(monthType);
                    break;
                }
            }
        }

    }
}
