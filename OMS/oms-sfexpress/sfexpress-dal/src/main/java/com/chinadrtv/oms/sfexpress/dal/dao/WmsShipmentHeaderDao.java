package com.chinadrtv.oms.sfexpress.dal.dao;

import java.util.List;
import java.util.Map;

import com.chinadrtv.oms.sfexpress.dal.model.WmsShipmentDetail;

/**
 * 
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2014-6-11 上午10:20:10 
 *
 */
public interface WmsShipmentHeaderDao {
	
	/**
	 * <p>从wms库获取反馈数据</p>
	 * @return
	 */
	List<Map<String, Object>> findShipmentHeader();

	/**
	 * <p>更新标记</p>
	 * @param shipmentId
	 * @return
	 */
	int updateShipmentHeader(Map<String, Object> params);

	/**
	 * <p>获取商品明细</p>
	 * @param shipmentId
	 * @return
	 */
	List<WmsShipmentDetail> findDetails(String shipmentId);
}
