package com.chinadrtv.runtime.configcenter;

public interface LoadRemoteConfig {

    /**
     * 加载全部配置
     */
    public void loadConfig();

    /**
     * 加载单项配置
     */
    public void loadConfig(String key);

}
