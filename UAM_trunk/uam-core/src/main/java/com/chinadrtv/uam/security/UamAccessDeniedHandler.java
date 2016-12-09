/**
 * 
 */
package com.chinadrtv.uam.security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.context.ServletContextAware;

/**
 * @author dengqianyong
 *
 */
public class UamAccessDeniedHandler implements AccessDeniedHandler, ServletContextAware, InitializingBean {
	//~ Static fields/initializers =====================================================================================

	private static final String HEADER_NAME = "X-Requested-With";
	private static final String AJAX_REQUEST = "XMLHttpRequest";
	
    //~ Instance fields ================================================================================================

    private String errorPage;
    private boolean supportAjax = true;
    private StringBuilder deniedContent = new StringBuilder();
    private ServletContext servletContext;

	@Override
	public void afterPropertiesSet() throws Exception {
		InputStream is = servletContext.getResourceAsStream(errorPage);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String content;
		while ((content = br.readLine()) != null) {
			deniedContent.append(content);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.web.access.AccessDeniedHandler#handle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.access.AccessDeniedException)
	 */
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException,
			ServletException {
		if (supportAjax && isAjaxRequest(request)) {
			response.setContentType("text/plain;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(deniedContent);
            out.flush();
            out.close();
            return;  
		} else if (!response.isCommitted()) {
            if (errorPage != null) {
                // Put exception into request scope (perhaps of use to a view)
                request.setAttribute(WebAttributes.ACCESS_DENIED_403, accessDeniedException);

                // Set the 403 status code.
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);

                // forward to error page.
                RequestDispatcher dispatcher = request.getRequestDispatcher(errorPage);
                dispatcher.forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
            }
        }

	}
	
	private boolean isAjaxRequest(HttpServletRequest request) {
		String header = request.getHeader(HEADER_NAME);
		return header != null && AJAX_REQUEST.equals(header); 
	}
	
	/**
     * The error page to use. Must begin with a "/" and is interpreted relative to the current context root.
     *
     * @param errorPage the dispatcher path to display
     *
     * @throws IllegalArgumentException if the argument doesn't comply with the above limitations
     */
    public void setErrorPage(String errorPage) {
        if ((errorPage != null) && !errorPage.startsWith("/")) {
            throw new IllegalArgumentException("errorPage must begin with '/'");
        }

        this.errorPage = errorPage;
    }

	public void setSupportAjax(boolean supportAjax) {
		this.supportAjax = supportAjax;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
    
}
