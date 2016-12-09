package com.chinadrtv.web.session.sessionstore;

import java.util.Map;


public interface SessionStoreService {
    @SuppressWarnings("rawtypes")
    public Map getSession(String id);

    @SuppressWarnings("rawtypes")
    public void saveSession(String id, Map session);

    public void removeSession(String id);
}
