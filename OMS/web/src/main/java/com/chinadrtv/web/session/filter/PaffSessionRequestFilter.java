package com.chinadrtv.web.session.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import com.chinadrtv.web.session.cookie.CookieOperator;
import com.chinadrtv.web.session.idfactory.RandomSessionIDBroker;
import com.chinadrtv.web.session.wrapper.HttpRequestWrapper;
import com.chinadrtv.web.session.wrapper.SessionWrapper;


public class PaffSessionRequestFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(PaffSessionRequestFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String sidValue = null;
        SessionWrapper sessionWrapper = null;

        try {
            Cookie cookie = CookieOperator.getPaffCookie(request);
            if (cookie != null) {
                sidValue = cookie.getValue();
            }
            sessionWrapper = new SessionWrapper();
            if (sidValue != null) {
                request = new HttpRequestWrapper(sidValue, request, response);
                sessionWrapper.setAttributes(request.getSession(), sidValue);
            }
            if (sidValue == null) {
                sidValue = creatSessionId();
                response.addCookie(CookieOperator.creatCookie(sidValue));
            }
            //设置HttpSession的失效时间，客户端的下次访问失效
            request.getSession().setMaxInactiveInterval(1);

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            logger.error("", e);
            e.printStackTrace();
        } finally {

            if (sessionWrapper != null) {
                try {
                    sessionWrapper.commit(request.getSession(), sidValue);
                } catch (Exception e) {
                    logger.error("sessionId:" + sidValue + "保存session信息失败", e);
                }
            }
        }

    }

    /**
     * @return 客户端的唯一标识，确定缓存服务器里面的session信息
     */
    private String creatSessionId() {
        RandomSessionIDBroker randomSessionIDBroker = new RandomSessionIDBroker();
        randomSessionIDBroker.init();
        return randomSessionIDBroker.generateSessionID();
    }

}
