package com.chinadrtv.erp.sales.service;

import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.model.marketing.BaseConfig;
import com.chinadrtv.erp.sales.dto.PhoneHookDto;

import java.util.List;
import java.util.Map;

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
   /**
    * 消费取数
    * @param dto
    * @return
    * @throws ServiceException
    */
   public Map fetchMessage(PhoneHookDto dto) throws ServiceException ;

   public Boolean interrupt(PhoneHookDto dto) throws ServiceException;

   public List<BaseConfig> getNormalPhone();
   public void fetchMessageCallBack(PhoneHookDto dto);
   public void saveCtiInfo(PhoneHookDto dto) throws ServiceException;

   public void saveCallbackCtiInfo(PhoneHookDto dto) throws ServiceException;
}
