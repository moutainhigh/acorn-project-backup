package com.chinadrtv.erp.sales.service;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.model.marketing.BaseConfig;
import com.chinadrtv.erp.sales.dto.PhoneHookDto;

/**
 * 
 * 为CommonController.java 提供服务
 * 
 * @author haoleitao
 *
 */
public interface CommonService {
   /**
	* 挂机
	*/
   public Map phoneHook(PhoneHookDto dto) throws ServiceException;

   public Boolean interrupt(PhoneHookDto dto) throws ServiceException;

    public List<BaseConfig> getNormalPhone();
}
