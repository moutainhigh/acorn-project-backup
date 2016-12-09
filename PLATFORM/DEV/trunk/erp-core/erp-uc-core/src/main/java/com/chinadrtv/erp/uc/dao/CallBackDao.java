package com.chinadrtv.erp.uc.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.marketing.Callback;
import com.chinadrtv.erp.model.marketing.CallbackSpecification;
import com.chinadrtv.erp.uc.dto.CallbackDto;
import com.chinadrtv.erp.uc.dto.GroupDto;
import com.chinadrtv.erp.user.dto.AgentUserInfo4TeleDist;

/**
 * Created with IntelliJ IDEA.
 * User: haoleitao
 * Date: 13-7-19
 * Time: 下午2:10
 * To change this template use File | Settings | File Templates.
 */
public interface CallBackDao extends GenericDao<Callback, Long> {
	
    /**
     * API-CALLBACK-2.	查询可分配话务的座席组
     * @param userId
     * @param grpType (0:可分配组,1:可取数组)
     * @param callType 进线类型
     * @param groupid
     * @return
     */
    List<GroupDto> findValidGroup(String userId, String groupid, Long grpType, Long callType, String grpName);
    List<GroupDto> findValidGroup(Long grpType, Long callType, String workGroups);
    /**
     * 查询已分配Callback数量
     * @param specification
     * @return
     */
    Long findAllocatedCount(CallbackSpecification specification);

    /**
     * 已分配电话号码及分配数量
     * @param specification
     * @return
     */
    HashMap<String, Long> findAllocatedNumbers(CallbackSpecification specification);
    /**
     * 查询已分配Callback
     * @param specification
     * @return
     */
    List<Callback> findAllocatedCallbacks(CallbackSpecification specification);
    /*
     * 查找制定caseId的Callback
     */
    List<Callback> findCallbacksByCaseId(String caseId);
    /**
     * API-CALLBACK-8.	查询可分配的CALL_BACK
     * @return
     */
    List<Callback> findCallbacks(CallbackSpecification specification);

    /**
     * 获取指定客户、电话、媒体产品的重复排除数据
     * @param specification
     * @param contactId
     * @param latentcontactId
     * @param phn2
     * @param mediaplanId
     * @return
     */
    List<Callback> findExcludedCallbacks(CallbackSpecification specification, Callback callback);

    Long findCallbackCount(CallbackSpecification specification) throws SQLException;
    
    /**
     * findExecutedCallbackCount
     * @param specification
     * @return
     * @throws SQLException 
     * Integer
     * @exception 
     * @since  1.0.0
     */
    Integer findExecutedCallbackCount(CallbackSpecification specification) throws SQLException;
    
    Map<String, AgentUserInfo4TeleDist> findAllocatedCallbackCountInBatch(CallbackSpecification specification) throws SQLException;
    
    Map<String, AgentUserInfo4TeleDist> findExecutedCallbackCountInBatch(CallbackSpecification specification) throws SQLException;
    
    /**
     * 根据任务查询电话
     * queryPhoneByTaskId
     * @param taskId
     * @return 
     * String
     * @exception 
     * @since  1.0.0
     */
    String queryPhoneByTaskId(String taskId);

	/**
	 * 查询可分配的虚拟进线数量
	 * 
	 * @param callbackDto
	 * @return
	 */
	Integer countAssignableVirtualCallback(CallbackDto callbackDto);
	
	/**
	 * 查询可分配的虚拟进线
	 * 
	 * @param callbackDto
	 * @return
	 */
	List<Callback> queryAssignableVirtualCallback(final CallbackDto callbackDto);

    /**
     * 获取分配批次的batch
     * @return
     */
    String getBatchSeq();
	
    /**
	 * <p></p>
	 * @param agentUsers
	 * @return
	 */
	Integer queryAvaliableQtyByGroups(List<Map<String, Object>> agentUsers);
	
	/**
	 * <p></p>
	 * @param userGroup
	 * @return
	 */
	List<Callback> queryAvaliableListByGroup(String userGroup);
}
