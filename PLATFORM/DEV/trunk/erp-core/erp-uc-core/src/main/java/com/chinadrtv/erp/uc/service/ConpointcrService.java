package com.chinadrtv.erp.uc.service;
import java.sql.SQLException;
import java.util.List;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.model.agent.Conpointcr;
/**
 * 积分生成Service
 *  
 * @author haoleitao
 * @date 2013-5-7 上午11:06:14
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface ConpointcrService extends GenericService<Conpointcr,String>{
    Conpointcr getConpointcrById(String conpointcrId);
    List<Conpointcr> getAllConpointcr();
    /**
     * 
     *API-UC-25.	查询积分生成历史
     * 
    * <p>Title: </p>
    * <p>Description: </p>
    * @param contactId
    * @param index
    * @param size
    * @return
     */
    List<Conpointcr> getAllConpointcrByContactId(String contactId,int index, int size);
    
    /**
     * API-UC-25.	查询积分生成历史分页数据
    * <p>Title: </p>
    * <p>Description: </p>
    * @param contactId
    * @return
     */
    int getAllConpointcrByContactIdCount(String contactId);
    void saveConpointcr(Conpointcr conpointcr) throws ServiceException;
    void addConpointcr(Conpointcr conpointcr) throws ServiceException;
    
    /**
     * 
    * <p>Title: </p>
    * <p>Description: </p>
    * @param conpointcr
     */
    void removeConpointcr(Conpointcr conpointcr) throws ServiceException;
    
    /**
     *    
     * 	校验积分
     * 
    * <p>Title: </p>
    * <p>Description: </p>
    * @param sorderid 订单号
    * @param scrusr: 坐席
    * @throws SQLException 异常信息
     */
    void conpointfeedback(String sorderid,String scrusr)  throws ServiceException;
}
