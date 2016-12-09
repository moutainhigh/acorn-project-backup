package com.chinadrtv.scheduler.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.chinadrtv.common.log.LOG_TYPE;
import com.chinadrtv.scheduler.web.constants.Constants;
import com.chinadrtv.scheduler.web.util.ParamUtils;
import com.chinadrtv.scheduler.web.util.SearchBox;

/**
 * @author kevin
 * @since 2013-6-14
 * @version 1.0.0
 */
public class InitSearchBoxInterceptor extends HandlerInterceptorAdapter {
	private static final Logger LOG = LoggerFactory
			.getLogger("LOG_TYPE.ACORN_COMMON.val");

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();

		SearchBox searchBox = (SearchBox) session
				.getAttribute(Constants.SEARCH_ACTION.val);
		searchBox = searchBox == null ? new SearchBox() : searchBox;
		String searchName = request.getParameter(Constants.SEARCH_NAME.val);

		if (StringUtils.isNotEmpty(searchName)
				&& searchName.trim().toLowerCase()
						.equals(Constants.SEARCH_PARAM_CACHE.val)) {
			searchBox.setParameterBox(request.getParameterMap());
		} else if (StringUtils.isNotEmpty(searchName)
				&& searchName.trim().toLowerCase()
						.equals(Constants.SEARCH_PARAM_CLEAN)) {
			searchBox.clear();
		}

		searchBox.setPageSize(ParamUtils.getIntParameter(request
				.getParameterMap().get(Constants.PAGE_SIZE.val), searchBox
				.getPageSize()));

		searchBox.setCurrentPage(ParamUtils.getIntParameter(request
				.getParameterMap().get(Constants.CURRENT_PAGE.val), searchBox
				.getCurrentPage()));

		session.setAttribute(Constants.SEARCH_ACTION.val, searchBox);

		return true;
	}
}
