package com.chinadrtv.runtime.ws;

import com.chinadrtv.common.pagination.ReflectUtil;

public class BaseWSClient<T> {

    private Class<T> clazz = ReflectUtil.getSuperClassGenericType(getClass());

    protected String wsUrlKey;

    protected String address;

    public BaseWSClient() {

    }

    public BaseWSClient(String wsUrlKey) {
        this.wsUrlKey = wsUrlKey;
    }

    public T getBizRemoteBean() {
        return PaffWsProxyFactory.getBizInstance(clazz, wsUrlKey);
    }

    public T getSysRemoteBean() {
        return PaffWsProxyFactory.getSysInstance(clazz, wsUrlKey);
    }

    public T getAppointRemoteBean(String address) {
        return PaffWsProxyFactory.getAppointRemoteBean(clazz, address);
    }
    
    public T getAppointRemoteBean() {
        return PaffWsProxyFactory.getAppointRemoteBean(clazz, address);
    }

    public void setWsUrlKey(String wsUrlKey) {
        this.wsUrlKey = wsUrlKey;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
