package com.chinadrtv.service.oms;

import com.chinadrtv.model.oms.PreTradeLotRequest;


/**
 * User: liuhaidong
 * Date: 12-12-14
 */
public interface PreTradeLotRequestService {
    PreTradeLotRequest findLastSuccess(Integer sourceId);

	/**
	 * <p></p>
	 * @param preTradeLotRequest
	 */
	void insert(PreTradeLotRequest preTradeLotRequest);
}
