package com.chinadrtv.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.chinadrtv.common.log.LOG_TYPE;

public class ExcepionLogInterceptor extends SimpleMappingExceptionResolver {

    private Logger logger = LoggerFactory.getLogger("LOG_TYPE.PAFF_COMMON.val");

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object handler, Exception ex) {
        logger.error(ex.getMessage(), ex);
        return super.resolveException(request, response, handler, ex);

    }

}
