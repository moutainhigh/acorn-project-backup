package com.chinadrtv.erp.report.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.util.StringUtils;

/**
 * 处理登录后显示登录前请求的url
 * @author zhangguosheng
 *
 */
public class ReportSimpleUrlAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	/* (non-Javadoc)
	 * @see org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler#determineTargetUrl(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
        if (this.getTargetUrlParameter() != null  ) {
        	String targetUrl = (String) request.getSession().getAttribute(this.getTargetUrlParameter());
            if (StringUtils.hasText(targetUrl)) {
                logger.debug("Found targetUrlParameter in session: " + targetUrl);
                request.getSession().removeAttribute(this.getTargetUrlParameter());
                return targetUrl;
            }
        }
		return super.determineTargetUrl(request, response);
	}

}
