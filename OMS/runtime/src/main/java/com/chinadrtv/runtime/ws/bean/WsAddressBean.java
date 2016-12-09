package com.chinadrtv.runtime.ws.bean;


public class WsAddressBean implements Comparable<WsAddressBean> {

    //服务端地址的url代理对象 clienProxyFactory
    private Object proxy;
    //有效状态 0失效，1有效
    private volatile int    status;
    //服务的优先级
    private int    priority;
    //服务地址
    private String url;

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Object getProxy() {
        return proxy;
    }

    public void setProxy(Object proxy) {
        this.proxy = proxy;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void disable() {
        status = 0;
    }

    //根据优先级排序
    @Override
    public int compareTo(WsAddressBean o) {
        if (this.getPriority() == o.getPriority()) {
            return -1;
        } else {
            return o.getPriority() - this.getPriority();
        }
    }

}
