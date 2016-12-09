package com.chinadrtv.jingdong.service;

import com.chinadrtv.jingdong.model.JingdongOrderConfig;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-11-8
 * Time: 下午2:36
 * To change this template use File | Settings | File Templates.
 */
public interface OrderImportJDService {
	
    void importPreOrdersInfo(List<JingdongOrderConfig> taobaoOrderConfigList,Date startDate, Date endDate) throws Exception;

	/**
	 * <p></p>
	 * @param fbpList
	 * @param startDate
	 * @param endDate
	 */
	void importFBPOrder(List<JingdongOrderConfig> fbpList, Date startDate, Date endDate) throws Exception;
}
