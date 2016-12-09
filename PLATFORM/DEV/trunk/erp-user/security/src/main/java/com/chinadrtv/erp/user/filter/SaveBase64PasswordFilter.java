package com.chinadrtv.erp.user.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import com.chinadrtv.erp.util.Base64Encryptor;

public class SaveBase64PasswordFilter extends GenericFilterBean {
	
	private String filterProcessesUrl = "/j_spring_security_check";
	
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "j_password";

    private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		if (requiresAuthentication(request, response)) {
			String password = req.getParameter(passwordParameter);
			try {
				password = Base64Encryptor.encrypt(password.trim());
			} catch (Exception e) {
			}
			if (password != null && !"".equals(password)) {
				request.getSession().setAttribute("BASE64_PASSWORD", password);
			}
		}
		
		chain.doFilter(request, response);
	}

	/**
     * Indicates whether this filter should attempt to process a login request for the current invocation.
     * <p>
     * It strips any parameters from the "path" section of the request URL (such
     * as the jsessionid parameter in
     * <em>http://host/myapp/index.html;jsessionid=blah</em>) before matching
     * against the <code>filterProcessesUrl</code> property.
     * <p>
     * Subclasses may override for special requirements, such as Tapestry integration.
     *
     * @return <code>true</code> if the filter should attempt authentication, <code>false</code> otherwise.
     */
    private boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String uri = request.getRequestURI();
        int pathParamIndex = uri.indexOf(';');

        if (pathParamIndex > 0) {
            // strip everything after the first semi-colon
            uri = uri.substring(0, pathParamIndex);
        }

        if ("".equals(request.getContextPath())) {
            return uri.endsWith(filterProcessesUrl);
        }

        return uri.endsWith(request.getContextPath() + filterProcessesUrl);
    }

	public void setFilterProcessesUrl(String filterProcessesUrl) {
		this.filterProcessesUrl = filterProcessesUrl;
	}

	public void setPasswordParameter(String passwordParameter) {
		this.passwordParameter = passwordParameter;
	}
    
}
