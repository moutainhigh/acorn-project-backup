package com.chinadrtv.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-10-30
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class HttpParamUtils {
    /**
     * 转换请求参数
     *
     * @param url
     * @return
     * @throws java.io.UnsupportedEncodingException
     */
    public static Map<String, String> getUrlParameters(String url)
            throws UnsupportedEncodingException {

        Map<String, String> params = new HashMap<String, String>();

        if (url.length() > 1) {
            String query = url;
            for (String param : query.split("&")) {
                String pair[] = param.split("=");
                if (pair.length > 1) {
                    if (pair[1] != null) {
                        String key = URLDecoder.decode(pair[0], "UTF-8");
                        String value = URLDecoder.decode(pair[1], "UTF-8");
                        params.put(key, value);
                    }
                }
            }
        }

        return params;
    }
}
