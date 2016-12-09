
package com.chinadrtv.web.session.wrapper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import com.chinadrtv.web.session.cookie.CookieOperator;
import com.chinadrtv.web.session.sessionstore.impl.LocalMapCacheSessionImpl;


public class PaffHttpSidSession extends PaffHttpSession {
    private String             sidValue;
    private HttpRequestContext requestContext;

    /**
     * @param session
     */
    public PaffHttpSidSession(HttpSession session, String sidValue,
                              HttpRequestContext requestContext) {
        super(session);
        this.sidValue = sidValue;
        this.requestContext = requestContext;
    }

    /** 
     * @see javax.servlet.http.HttpSession#invalidate()
     */
    @Override
    public void invalidate() {
        Cookie cookie = CookieOperator.getPaffCookie(requestContext.getRequest());
        cookie.setMaxAge(0);
        requestContext.getResponse().addCookie(cookie);
        LocalMapCacheSessionImpl.getInstance().removeSession(sidValue);
    }

}
