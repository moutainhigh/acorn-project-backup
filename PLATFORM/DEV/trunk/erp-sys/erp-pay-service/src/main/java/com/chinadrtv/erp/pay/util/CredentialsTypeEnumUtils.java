package com.chinadrtv.erp.pay.util;

import com.chinadrtv.erp.pay.core.model.CredentialsType;
import org.apache.commons.lang.StringUtils;

import java.beans.PropertyEditorSupport;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-10-12
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class CredentialsTypeEnumUtils extends PropertyEditorSupport {
    @Override
    public void setAsText(final String text) throws IllegalArgumentException
    {
        if(StringUtils.isNotEmpty(text))
        {
            Integer index=Integer.parseInt(text);
            for(CredentialsType messageType:CredentialsType.values())
            {
                if(messageType.getIndex()==index.intValue())
                {
                    setValue(messageType);
                    break;
                }
            }
        }

    }
}