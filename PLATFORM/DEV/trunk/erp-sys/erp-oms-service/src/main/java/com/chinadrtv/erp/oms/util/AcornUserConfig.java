package com.chinadrtv.erp.oms.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-28
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class AcornUserConfig {
    public static Map<String, String> userConfigInfo = new HashMap<String, String>();


    public static void setUserConfigInfo(Map<String, String> userConfigInfo) {
        AcornUserConfig.userConfigInfo = userConfigInfo;
    }
}
