package com.chinadrtv.amazon.common.integration;

public interface WebServiceClientUtil {
    
    /**
     * obj 传输对象
     * beanClasses 需要依赖类的的 Class
     */
    public Object doInvokeWs(Object obj,String type,Class... beanClasses);
}
