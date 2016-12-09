package com.chinadrtv.web.session.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinadrtv.common.log.LOG_TYPE;


public class CookieOperator {
    private static String       SESSION_ID   = null;
    private static String       COOKIEDOMAIN = null;
    private static final String COOKIEPATH   = "/";
    private static final Logger logger       = LoggerFactory.getLogger("LOG_TYPE.PAFF_COMMON.val");

    static {

        COOKIEDOMAIN = System.getProperty("session.domain");
        SESSION_ID = System.getProperty("session.id");
        if (COOKIEDOMAIN == null) {
            logger.error("找不到域名");
        }
        if (SESSION_ID == null) {
            logger.error("找不到session id");
        }
    }

    public static Cookie getPaffCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Cookie sCookie = null;

        if (cookies != null && cookies.length > 0) {
            for (int i = 0; i < cookies.length; i++) {
                sCookie = cookies[i];
                if (sCookie.getName().equals(SESSION_ID)
                    && sCookie.getDomain().equals(COOKIEDOMAIN)) {
                    return sCookie;

                }
            }
        }
        return null;
    }

    /**
     * 返回一个cookie，作为客户端的session;
     * 生命周期为客户端游览器关闭之前
     * 域的范围为COOKIEDOMAIN
     * 路径的范围为同一服务器下的路径
     * @param sidValue  
     * @return  
     */
    public static Cookie creatCookie(String sidValue) {
        Cookie paffCookies = new Cookie(SESSION_ID, sidValue);
        paffCookies.setMaxAge(-1);
        if (COOKIEDOMAIN != null && COOKIEDOMAIN.length() > 0) {
            paffCookies.setDomain(COOKIEDOMAIN);
        }
        paffCookies.setPath(COOKIEPATH);
        return paffCookies;
    }

}
