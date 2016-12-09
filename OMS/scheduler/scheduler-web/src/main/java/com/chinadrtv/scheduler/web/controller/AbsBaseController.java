package com.chinadrtv.scheduler.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinadrtv.common.log.LOG_TYPE;
import com.chinadrtv.scheduler.web.util.SearchBox;

public abstract class AbsBaseController {

	protected static final Logger LOG = LoggerFactory
			.getLogger("LOG_TYPE.PAFF_COMMON.val");
	
	protected Map<String, Object> getParamMap(String maxCount, String currPage,
			String pageSize, SearchBox searchBox) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (searchBox == null) {
			paramMap.put(
					"maxCount",
					StringUtils.isNotEmpty(maxCount) ? Integer
							.valueOf(maxCount) : null);
			paramMap.put(
					"currentPage",
					StringUtils.isNotEmpty(currPage) ? Integer
							.valueOf(currPage) : 0);
			paramMap.put(
					"pageSize",
					StringUtils.isNotEmpty(pageSize) ? Integer
							.valueOf(pageSize) : 20);
		} else {
			paramMap.put("currentPage", searchBox.getCurrentPage());
			paramMap.put("pageSize", searchBox.getPageSize());
		}

		return paramMap;
	}
}
