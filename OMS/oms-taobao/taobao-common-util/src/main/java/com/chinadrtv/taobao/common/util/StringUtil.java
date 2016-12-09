package com.chinadrtv.taobao.common.util;

public class StringUtil {
    
    public static boolean isBlank(String str) {
        if (str == null || "".equals(str)) {
            return true;
        }
        return false;
    }

    public static boolean isNotBlank(String str) {
        if (str == null || "".equals(str)) {
            return false;
        }
        return true;
    }

    public static boolean isSameString(String str1, String str2) {
        if (str1 == null || "".equals(str1)) {
            if (str2 == null || "".equals(str2)) {
                return true;
            }
            return false;
        }
        if (str1 != null && str1.equals(str2)) {
            return true;
        }
        return false;
    }
    
    public static boolean equalsIgnoreCase(String str1, String str2) {
        return ((str1 == null) ? false : (str2 == null) ? true : str1.equalsIgnoreCase(str2));
    }

}
