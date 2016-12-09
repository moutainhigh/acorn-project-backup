package com.chinadrtv.acorn.bean;

import java.util.HashMap;
import java.util.Map;

public class UserConfig {

    public static Map<String, String> userConfigInfo = new HashMap<String, String>();


    public static void setUserConfigInfo(Map<String, String> userConfigInfo) {
        UserConfig.userConfigInfo = userConfigInfo;
    }


}
