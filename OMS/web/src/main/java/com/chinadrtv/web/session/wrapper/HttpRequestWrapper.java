package com.chinadrtv.web.session.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class HttpRequestWrapper extends HttpServletRequestWrapper {
    String                     sidValue = "";
    private PaffHttpSidSession paffHttpSidSession;
    private HttpRequestContext requestContext;

    /**
     * @param request
     */
    public HttpRequestWrapper(String sidValue, HttpServletRequest request,
                              HttpServletResponse response) {
        super(request);
        this.sidValue = sidValue;
        this.requestContext = new HttpRequestContext();
        requestContext.setRequest(request);
        requestContext.setResponse(response);
    }

    @Override
    public HttpSession getSession() {
        if (paffHttpSidSession == null) {
            paffHttpSidSession = new PaffHttpSidSession(super.getSession(), sidValue,
                requestContext);
        }
        return paffHttpSidSession;
    }

    @Override
    public HttpSession getSession(boolean create) {
        if (paffHttpSidSession == null) {
            paffHttpSidSession = new PaffHttpSidSession(super.getSession(create), sidValue,
                requestContext);
        }
        return paffHttpSidSession;
    }

}
