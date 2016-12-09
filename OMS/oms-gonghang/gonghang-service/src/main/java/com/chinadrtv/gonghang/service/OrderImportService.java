package com.chinadrtv.gonghang.service;

import java.util.Date;
import java.util.List;

import com.chinadrtv.gonghang.dal.model.OrderConfig;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 14-4-22
 * Time: 下午3:38
 * To change this template use File | Settings | File Templates.
 */
public interface OrderImportService {

	/**
	 * <p>order import</p>
	 * @param gongHangOrderConfigList
	 * @param startDate
	 * @param endDate
	 */
	void input(List<OrderConfig> configList, Date startDate, Date endDate) throws Exception;
	
}
