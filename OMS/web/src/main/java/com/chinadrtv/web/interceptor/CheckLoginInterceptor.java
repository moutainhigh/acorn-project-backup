package com.chinadrtv.web.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.chinadrtv.common.log.LOG_TYPE;
import com.chinadrtv.util.WebConstants;
import com.chinadrtv.web.tag.TagConstant;


public class CheckLoginInterceptor extends HandlerInterceptorAdapter {

    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger("LOG_TYPE.PAFF_COMMON.val");

    private List<String>        mappingURL;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        String path = request.getServletPath();

        // 不需要验证登录的url
        if (mappingURL.contains(path))
            return true;

        // 检查用户是否登录
        Object object = request.getSession().getAttribute(TagConstant.USER_KEY);
//        if (object == null) {
//            LOG.info("[{}]需要登录", request.getAttribute(WebConstants.SERIAL_NUMBER));
//
//            response.sendRedirect(request.getContextPath() + "/relogin");
//            return false;
//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {

    }

    public List<String> getMappingURL() {
        return mappingURL;
    }

    public void setMappingURL(List<String> mappingURL) {
        this.mappingURL = mappingURL;
    }

}
