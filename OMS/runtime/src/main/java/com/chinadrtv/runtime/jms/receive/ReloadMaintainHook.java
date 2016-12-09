package com.chinadrtv.runtime.jms.receive;

public interface ReloadMaintainHook {

    public void addConfig(ConfigBean cb) throws Exception;

    public void updateConfig(ConfigBean cb) throws Exception;

    public void removeConfig(ConfigBean cb) throws Exception;
}
