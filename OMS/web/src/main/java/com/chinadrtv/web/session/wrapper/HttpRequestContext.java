package com.chinadrtv.web.session.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class HttpRequestContext {
    private HttpServletRequest  request;
    private HttpServletResponse response;

    /**
     * Getter method for property <tt>request</tt>.
     * 
     * @return property value of request
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * Setter method for property <tt>request</tt>.
     * 
     * @param request value to be assigned to property request
     */
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * Getter method for property <tt>response</tt>.
     * 
     * @return property value of response
     */
    public HttpServletResponse getResponse() {
        return response;
    }

    /**
     * Setter method for property <tt>response</tt>.
     * 
     * @param response value to be assigned to property response
     */
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

}
