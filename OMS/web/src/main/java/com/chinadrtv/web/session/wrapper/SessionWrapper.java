package com.chinadrtv.web.session.wrapper;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.chinadrtv.web.session.sessionstore.impl.SessionStoreInCacheService;


public class SessionWrapper {

    /**
     * 在dofilter之前把缓存里面的session信息赋值给HttpSession
     * 
     * @param session
     * @param sValue
     */
    @SuppressWarnings("unchecked")
    public void setAttributes(HttpSession session, String sValue) {
        Map<String, Object> attributes = SessionStoreInCacheService.getInstance()
            .getSession(sValue);
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            session.setAttribute(entry.getKey(), entry.getValue());
        }
    }

    /**
     * dofilter之后把session的信息存入缓存
     * 
     * @param session
     * @param sValue
     * @return true表示向缓存服务器中保存了信息，false表示没有向缓存服务器中保存信息
     */
    @SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
    public void commit(HttpSession session, String sValue) {
        Map sessionMap = new Hashtable();
        Enumeration enums = session.getAttributeNames();
        while (enums.hasMoreElements()) {
            String key = enums.nextElement().toString();
            sessionMap.put(key, session.getValue(key));
        }
        if (!sessionMap.isEmpty()) {
            SessionStoreInCacheService.getInstance().saveSession(sValue, sessionMap);
        }
    }

}
