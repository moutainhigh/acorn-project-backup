package com.chinadrtv.scheduler.web.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.chinadrtv.common.log.LOG_TYPE;

/**
 * @author kevin
 * @since 2013-6-14
 * @version 1.0.0
 */
public class CheckLoginInterceptor extends HandlerInterceptorAdapter {

	private List<String> mappingURL;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String path = request.getServletPath();

		// 不需要验证登录的url
		if (mappingURL.contains(path))
			return true;

		// 检查用户是否登录
		// User user = (User) request.getSession().getAttribute(
		// Constants.LOGIN_CONSOLE_USER);
		// if (user == null) {
		// LOG.info("[{}]需要登录", request.getAttribute(Constants.JOURNAL_SEQ_NO));
		//
		// response.sendRedirect(request.getContextPath() + "/relogin");
		// return false;
		// }
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	private static final Logger LOG = LoggerFactory
			.getLogger("LOG_TYPE.PAFF_COMMON.val");

	public List<String> getMappingURL() {
		return mappingURL;
	}

	public void setMappingURL(List<String> mappingURL) {
		this.mappingURL = mappingURL;
	}

}
