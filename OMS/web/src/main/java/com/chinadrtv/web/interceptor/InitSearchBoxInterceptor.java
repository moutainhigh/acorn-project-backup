package com.chinadrtv.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.chinadrtv.common.log.LOG_TYPE;
import com.chinadrtv.common.pagination.Page;
import com.chinadrtv.util.WebConstants;
import com.chinadrtv.web.util.ParamUtils;
import com.chinadrtv.web.util.SearchBox;


public class InitSearchBoxInterceptor extends HandlerInterceptorAdapter {
    @SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger("LOG_TYPE.PAFF_COMMON.val");

    @SuppressWarnings("unchecked")
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        String searchAction = request.getParameter(WebConstants.SEARCH_ACTION);

        if (StringUtils.isNotEmpty(searchAction)
            && searchAction.trim().toLowerCase().equals(WebConstants.SEARCH_PARAM_CACHE)) {
            SearchBox.putParameterBox(request.getParameterMap());
        } else if (StringUtils.isNotEmpty(searchAction)
                   && searchAction.trim().toLowerCase().equals(WebConstants.SEARCH_PARAM_CLEAN)) {
            SearchBox.clear();
        }

        int pageSize = ParamUtils.getIntParameter(request.getParameter(WebConstants.PAGE_SIZE), 20);
        int currPage = ParamUtils.getIntParameter(request.getParameter(WebConstants.CURRENT_PAGE),
            1);

        Page page = new Page(pageSize, currPage);
        request.setAttribute(WebConstants.PAGE, page);
        return true;
    }
}
