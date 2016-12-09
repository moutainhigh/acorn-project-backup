package com.chinadrtv.web.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;


public class SearchBox {
    private static Map<String, Object> parameterBox = new HashMap<String, Object>();

    public static Map<String, Object> getParameterBox() {
        return parameterBox;
    }

    public static void putParameterBox(Map<String, String[]> parameterBox) {
        parameterBox.putAll(parameterBox);
    }

    public static String getParameter(String key) {
        String[] str = (String[]) parameterBox.get(key);
        if (str == null)
            return null;
        return StringUtils.trim(getVal(str));
    }

    public static String[] getArrayParameter(String key) {
        String[] str = (String[]) parameterBox.get(key);
        if (str == null)
            return null;
        return str;
    }

    public static Long getLongParameter(String key) {
        String[] str = (String[]) parameterBox.get(key);
        return str == null ? null : getVal(str).equals("") ? null : Long.parseLong(getVal(str));
    }

    public static int getIntParameter(String key) {
        String[] str = (String[]) parameterBox.get(key);
        return str == null ? 0 : Integer.parseInt(getVal(str).equals("") ? "0" : getVal(str));
    }

    public static Integer getIntegerParameter(String key) {
        String[] str = (String[]) parameterBox.get(key);
        return str == null ? null : getVal(str).equals("") ? null : Integer.parseInt(getVal(str));
    }

    public static void clear() {
        parameterBox.clear();
    }

    public static String getVal(String[] str) {
        String valueStr = "";
        for (int i = 0; i < str.length; i++) {
            valueStr = (i == str.length - 1) ? valueStr + str[i] : valueStr + str[i] + ",";
        }
        return valueStr;
    }

}
