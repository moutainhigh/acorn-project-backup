package com.chinadrtv.oms.paipai.dal.dao;

import java.util.List;
import java.util.Map;

import com.chinadrtv.model.oms.PreTrade;

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
 * @since 2014-11-13 下午2:08:44 
 *
 */
public interface PreTradePaiPaiDao {
    
	/**
	 * <p>Query group-buying empty address order</p>
	 * @param params
	 * @return
	 */
	List<PreTrade> queryGroupBuyingTrade(Map<String, Object> params);
	
	/**
	 * <p>Update group-buying empty address order</p>
	 * @param pt
	 */
	void updateGroupBuyingTrade(PreTrade pt);
}
