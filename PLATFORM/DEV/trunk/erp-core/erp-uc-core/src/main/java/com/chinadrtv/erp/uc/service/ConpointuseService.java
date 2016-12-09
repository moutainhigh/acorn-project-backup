package com.chinadrtv.erp.uc.service;
import java.util.List;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.model.agent.Conpointuse;
/**
 * 积分消费服务
 *  
 * @author haoleitao
 * @date 2013-5-7 上午11:06:46
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface ConpointuseService extends GenericService<Conpointuse,String>{
    Conpointuse getConpointuseById(String conpointuseId);
    /**
     * API-UC-27.	查询积分消费历史 
    * <p>Title: </p>
    * <p>Description: </p>
    * @param contactId 客户编号
    * @param index 分页参数
    * @param size 分页参数
    * @return 结果集
     */
    List<Conpointuse> getAllConpointuseByContactId(String contactId,int index, int size);
    /**
     * API-UC-27.	查询积分消费历史 
    * <p>Title: </p>
    * <p>Description: </p>
    * @param contactId 客户编号 
    * @return 分页总数量
     */
    int getAllConpointuseByContactIdCount(String contactId);
    /**
     * 获取用户消费积分
     * @param contactId
     * @return
     */
    Double getUseIntegralByContactId(String contactId);
    
    void saveConpointuse(Conpointuse conpointuse) throws ServiceException;

    void addConpointuse(Conpointuse conpointuse) throws ServiceException;
    
    void removeConpointuse(Conpointuse conpointuse);
    
    
    
}
