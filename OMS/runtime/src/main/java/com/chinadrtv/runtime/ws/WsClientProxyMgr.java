package com.chinadrtv.runtime.ws;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import javax.transaction.Synchronization;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinadrtv.common.log.LOG_TYPE;
import com.chinadrtv.runtime.ws.bean.WsAddressBean;

public class WsClientProxyMgr {
    private static final Logger logger = LoggerFactory
            .getLogger("LOG_TYPE.PAFF_COMMON.val");
    // 服务接口信息
    private Class serviceClass;
    // 相关策略
    private String policy;

    // 最高优先级并且有效状态服务
    private volatile WsAddressBean currentWsAddressBean;
    //提供此服务的集群列表信息
    private Set<WsAddressBean> wsAddressBeanSet = new TreeSet<WsAddressBean>();

    public WsClientProxyMgr() {
        super();
    }

    public WsClientProxyMgr(Class serviceClass) {
        super();
        this.serviceClass = serviceClass;
    }

    // 刷新当前有效的地址,获取最高优先级，并且状态为有效状态的服务
    public synchronized void refreshCurrentWsAddressBean() {
        logger.info("查找服务:" + "[" + this.serviceClass + "]下，最高优先级的有效服务!");
        for (WsAddressBean ws : wsAddressBeanSet) {
            if (ws.getStatus() == 1) {
                if (ws.getProxy() == null) {
                    Object target = PaffWsProxyFactory.getAppointRemoteBean(serviceClass,
                            ws.getUrl());
                    WsInvocationHandler invocationHandler = new WsInvocationHandler(target,
                            ws.getUrl());
                    ws.setProxy(invocationHandler.getProxy());
                }
                currentWsAddressBean = ws;
                logger.info("查找到最高优先级有效服务:" + "[" + this.serviceClass + "]url[" + ws.getUrl()
                        + "]priority[" + ws.getPriority() + "]");
                break;
            }
        }
        if (currentWsAddressBean == null) {
            logger.info("查找服务:" + "[" + this.serviceClass + "]下,不存在任何有效服务!");
        }
    }

    // 当前服务地址失效
    public void disableCurrentWsAddressBean() {
        if (currentWsAddressBean != null) {
            logger.info("失效服务:" + currentWsAddressBean.getUrl());
            currentWsAddressBean.disable();
            currentWsAddressBean = null;

        }

    }

    //根据url失效某个地址
    public synchronized void disableCurrentWsAddressBeanByUrl(String url) {
        for (WsAddressBean ws : wsAddressBeanSet) {
            if (StringUtils.equalsIgnoreCase(ws.getUrl(), url)) {
                ws.setStatus(0);
            }
        }
    }

    /**
     * 增加服务地址,后要重新获取优先级最高的有效服务
     */
    public synchronized void addWsAddressBean(String url, int status, int priority) {
        logger.info("增加服务[" + this.serviceClass + "]地址:url[" + url + "]status[" + status
                + "]priority[" + priority + "]");
        WsAddressBean wb = new WsAddressBean();
        wb.setPriority(priority);
        wb.setStatus(status);
        wb.setUrl(url);
        wsAddressBeanSet.add(wb);
        refreshCurrentWsAddressBean();
    }

    /**
     * 获取服务代理,如不存在代理则新创建代理，如存在直接引用
     *
     * @return
     */
    public Object getRemoteService() {
        if (currentWsAddressBean == null) {
            //当add时,获取可能为空，所以再次加锁获取，等待有可能add后最终有效服务
            refreshCurrentWsAddressBean();
            if (currentWsAddressBean == null) {
                throw new RuntimeException("[" + serviceClass + "]下无有效服务!");
            } else {
                logger.debug("客户端请求的url:" + currentWsAddressBean.getUrl() + "当前优先级为:" + currentWsAddressBean.getPriority());
                return currentWsAddressBean;
            }
        } else {
            Object obj = currentWsAddressBean.getProxy();
            logger.debug("客户端请求的url:" + currentWsAddressBean.getUrl() + "当前优先级为:" + currentWsAddressBean.getPriority());
            return obj;
        }
    }

    public class WsInvocationHandler implements InvocationHandler {
        private Object target;

        private String url;

        public WsInvocationHandler(Object target, String url) {
            super();
            this.target = target;
            this.url = url;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object result = null;
            try {
                result = method.invoke(target, args);

              /*  int a = new Random().nextInt(10 + 1);
                if (a < 3) {
                    int x = 3 / 0;
                }*/
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                logger.error("失效信息:服务[" + serviceClass + "]" + "方法[" + method + "]参数[" + args + "]"
                        + "地址:[" + this.url + "]");
                disableCurrentWsAddressBean();

                logger.error("重新加载服务");
                refreshCurrentWsAddressBean();
                if (currentWsAddressBean != null) {
                    // 从最高优先级加载服务
                    Object obj = getRemoteService();
                    result = method.invoke(obj, args);
                } else {
                    throw new Exception("服务[" + serviceClass + "]-->所有服务失效!");
                }
            }
            return result;
        }

        /**
         * 获取目标对象的代理对象
         *
         * @return 代理对象
         */
        public Object getProxy() {
            return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), target
                    .getClass().getInterfaces(), this);
        }

    }

    public Class getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(Class serviceClass) {
        this.serviceClass = serviceClass;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

}
