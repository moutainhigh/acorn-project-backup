/**
 * 
 */
package com.chinadrtv.erp.sales.dao;

import com.chinadrtv.erp.model.agent.AreaAll;
import com.chinadrtv.erp.model.agent.CityAll;
import com.chinadrtv.erp.model.agent.CountyAll;
import com.chinadrtv.erp.model.agent.Province;

/**
 * @author dengqianyong
 *
 */
public interface AddressExtendDao {

	Province getProvinceById(String provinceId);
	
	CityAll getCityById(String cityId);
	
	CountyAll getCountyById(String countyId);
	
	AreaAll getAreaById(String areaId);
}
