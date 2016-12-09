package com.chinadrtv.erp.oms.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-28
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class MD5Utils {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MD5Utils.class);

    public static String getMD5(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] b = md.digest(message.getBytes("utf-8"));
            return byteToHexStringSingle(b);
        } catch (Exception e) {
            logger.error("md5 error:",e);
        }
        return null;
    }

    public static String byteToHexStringSingle(byte[] byteArray) {
        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(
                        Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }

        return md5StrBuff.toString();
    }
}
