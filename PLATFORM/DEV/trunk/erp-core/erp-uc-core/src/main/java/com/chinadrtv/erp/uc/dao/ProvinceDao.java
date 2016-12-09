package com.chinadrtv.erp.uc.dao;


import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.agent.Province;

import java.util.List;

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
 * @since 2013-5-3 下午3:24:04 
 *
 */
public interface ProvinceDao extends GenericDao<Province, String> {
    List<Province> getAllProvinces();
}
