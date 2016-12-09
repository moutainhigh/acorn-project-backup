package com.chinadrtv.runtime.configcenter;

import java.util.HashMap;
import java.util.Map;

import com.chinadrtv.common.context.ApplicationContextConfig;

public class LoadRemoteConfigImpl implements LoadRemoteConfig {

    @Override
    public void loadConfig() {
        Map<String, String> confs = new HashMap<String, String>();
        for (String key : confs.keySet()) {
            ApplicationContextConfig.put(key, confs.get(key));
        }
    }

    @Override
    public void loadConfig(String key) {
        String value = "";
        ApplicationContextConfig.put(key, value);
    }

}
