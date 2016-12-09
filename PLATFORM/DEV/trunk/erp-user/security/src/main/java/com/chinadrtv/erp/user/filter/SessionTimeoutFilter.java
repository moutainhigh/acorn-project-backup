package com.chinadrtv.erp.user.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class SessionTimeoutFilter extends GenericFilterBean {

    private SessionRegistry sessionRegistry;
    private String expiredUrl;

    public SessionTimeoutFilter(SessionRegistry sessionRegistry) {
        this(sessionRegistry, null);
    }

    public SessionTimeoutFilter(SessionRegistry sessionRegistry, String expiredUrl) {
        super();
        this.sessionRegistry = sessionRegistry;
        this.expiredUrl = expiredUrl;
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(sessionRegistry, "sessionRegistry required");
        Assert.isTrue(UrlUtils.isValidRedirectUrl(expiredUrl), expiredUrl + " isn't a valid redirect URL");
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {



        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        String requestURI = UrlUtils.buildRequestUrl(httpRequest);

        Authentication authentication = null;
        SecurityContext context = SecurityContextHolder.getContext();
        if(context != null){
            authentication = context.getAuthentication();
        }
        if(requestURI.equalsIgnoreCase(expiredUrl)){
            if (httpRequest.getHeader("x-requested-with") != null &&
                httpRequest.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
                httpResponse.addHeader("session-status", "timeout");
                httpResponse.addHeader("expired-url", expiredUrl);
            }
            chain.doFilter(request, response);
        } else if (session != null) {
            SessionInformation info = sessionRegistry.getSessionInformation(session.getId());
            if (info == null || info.isExpired()) {
                session.setMaxInactiveInterval(0);
                if (httpRequest.getHeader("x-requested-with") != null &&
                    httpRequest.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
                    httpResponse.addHeader("session-status", "timeout");
                    httpResponse.addHeader("expired-url", expiredUrl);
                    chain.doFilter(request, response);
                } else if (authentication != null){
                    sessionRegistry.registerNewSession(session.getId(), authentication.getPrincipal());
                    chain.doFilter(request, response);
                } else {
                    //其他filter没有验证
                    httpResponse.sendRedirect(expiredUrl);
                }
            } else {
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }
}