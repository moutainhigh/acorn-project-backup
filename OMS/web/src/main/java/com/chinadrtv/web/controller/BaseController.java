package com.chinadrtv.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinadrtv.common.log.LOG_TYPE;


public abstract class BaseController {

    protected static final Logger LOG = LoggerFactory.getLogger("LOG_TYPE.PAFF_COMMON.val");

   /* protected Map<String, Object> getParamMap(String maxCount, String currPage, String pageSize,
                                              SearchBox searchBox) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (searchBox == null) {
            paramMap.put(WebConstants.MAX_COUNT,
                StringUtils.isNotEmpty(maxCount) ? Integer.valueOf(maxCount) : null);
            paramMap.put(WebConstants.CURRENT_PAGE,
                StringUtils.isNotEmpty(currPage) ? Integer.valueOf(currPage) : 0);
            paramMap.put(WebConstants.PAGE_SIZE,
                StringUtils.isNotEmpty(pageSize) ? Integer.valueOf(pageSize) : 20);
        } else {
            paramMap.put(WebConstants.CURRENT_PAGE, searchBox.getCurrentPage());
            paramMap.put(WebConstants.PAGE_SIZE, searchBox.getPageSize());
        }

        return paramMap;
    }*/
}
