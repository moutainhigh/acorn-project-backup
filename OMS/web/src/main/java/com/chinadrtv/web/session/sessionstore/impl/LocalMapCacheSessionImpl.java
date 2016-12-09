package com.chinadrtv.web.session.sessionstore.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.chinadrtv.web.session.sessionstore.SessionStoreService;


public class LocalMapCacheSessionImpl implements SessionStoreService {
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private static Map<String, Map> sessionMap = Collections.synchronizedMap(new HashMap());

    /** 
     * @see com.chinadrtv.web.session.sessionstore.SessionStoreService#getSession(java.lang.String)
     */
    @SuppressWarnings("rawtypes")
    @Override
    public Map getSession(String id) {
        Map sessions = sessionMap.get(id);
        if (sessions == null) {
            return new HashMap();
        }
        return sessions;
    }

    /** 
     * @see com.chinadrtv.web.session.sessionstore.SessionStoreService#saveSession(java.lang.String, java.util.Map)
     */
    @SuppressWarnings("rawtypes")
	@Override
    public void saveSession(String id, Map session) {
        sessionMap.put(id, session);
    }

    /** 
     * @see com.chinadrtv.web.session.sessionstore.SessionStoreService#removeSession(java.lang.String)
     */
    @Override
    public void removeSession(String id) {
        sessionMap.remove(id);
    }

    private static final class Holder {
        static final LocalMapCacheSessionImpl instance = new LocalMapCacheSessionImpl();
    }

    public static LocalMapCacheSessionImpl getInstance() {
        return Holder.instance;
    }

}
