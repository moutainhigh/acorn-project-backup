package com.chinadrtv.web.session.sessionstore.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.chinadrtv.cache.client.exception.PaffCacheException;
//import com.chinadrtv.cache.util.CacheUtil;
import com.chinadrtv.common.log.LOG_TYPE;
import com.chinadrtv.web.session.sessionstore.SessionStoreService;


public class SessionStoreInCacheService implements SessionStoreService {
    private static final Logger logger = LoggerFactory.getLogger("paff.cache");

    private static final class Holder {
        static final SessionStoreInCacheService instance = new SessionStoreInCacheService();
    }

    public static SessionStoreInCacheService getInstance() {
        return Holder.instance;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Map getSession(String id) {
//        try {
//            Map session = (Map) CacheUtil.getObject(id);
//            if (session != null) {
//                return session;
//            }
//        } catch (PaffCacheException e) {
//            logger.error("查询缓存数据失败，sidValue=" + id, e);
//            e.printStackTrace();
//        }

        return new HashMap<String, Object>();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void saveSession(String id, Map session) {
//        try {
//            CacheUtil.putObject("test", "test", 600);
//        } catch (PaffCacheException e) {
//            logger.error("保存缓存数据失败，sidValue=" + id + ",session=" + session, e);
//            e.printStackTrace();
//        }
    }

    @Override
    public void removeSession(String id) {
//        try {
//            CacheUtil.removeObject(id);
//        } catch (PaffCacheException e) {
//            logger.error("移除缓存数据失败，sidValue=" + id, e);
//            e.printStackTrace();
//        }
    }

}
