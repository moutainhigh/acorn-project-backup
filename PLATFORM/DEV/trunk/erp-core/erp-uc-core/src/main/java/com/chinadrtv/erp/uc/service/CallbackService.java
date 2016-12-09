package com.chinadrtv.erp.uc.service;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.exception.ErpException;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.dto.LeadInteractionSearchDto;
import com.chinadrtv.erp.model.marketing.Callback;
import com.chinadrtv.erp.model.marketing.CallbackSpecification;
import com.chinadrtv.erp.uc.dto.AgentQueryDto4Callback;
import com.chinadrtv.erp.uc.dto.AssignAgentDto;
import com.chinadrtv.erp.uc.dto.CallbackDto;
import com.chinadrtv.erp.uc.dto.GroupDto;
import com.chinadrtv.erp.user.dto.AgentUserInfo4TeleDist;
import com.chinadrtv.erp.user.model.AgentUser;

/**
 * callback模式进线数据记录.
 * User: haoleitao
 * Date: 13-7-19
 * Time: 下午2:16
 * To change this template use File | Settings | File Templates.
 */
public interface CallbackService extends GenericService<Callback, Long> {

    /**
     * 2查询可分配话务的座席组
     * @param userId  //坐席ID
     * @param groupid //坐席组ID
     * @param grpType //0:可分配组 1:可取数组
     * @param callType
     * @return
     */

    public List<GroupDto> findValidGroup(String userId,String groupid, Long grpType, Long callType, String grpName);

	/**
	 * <p>API-CALLBACK-5.分配接通电话到座席组</p>
	 * @param liDto
	 * @param agentGroups		分配的坐席组与数量{'assignCount': 4, 'userGroup': 'abc'}
	 * @param averageAssign		是否平均分配	
	 * @return Boolean
	 * @throws Exception 
	 */
    Map<String, Object>  assignToGroup(LeadInteractionSearchDto liDto, List<Map<String, Object>> agentGroups) throws Exception;
	
	/**
	 * <p>API-CALLBACK-6.部门管理员分配接通电话到座席</p>
	 * @param liDto
	 * @param agentUsers		分配的坐席与数量{'assignCount': 4, 'userGroup':'abc', 'userId': 'abc'}
	 * @param averageAssign		是否平均分配
	 * @return Boolean
	 * @throws Exception 
	 */
	Map<String, Object> assignToAgent(LeadInteractionSearchDto liDto, List<Map<String, Object>> agentUsers, Boolean averageAssign) throws Exception;


    /**
     * 分配CALL_BACK到座席组
     *
     * @param specification 查找call_back条件
     * @param grpIds
     * @param count    分配数量
     * @return
     */
    boolean assignCallBackToAgentGrp(CallbackSpecification specification, List grpIds, Integer count);

    /**
     * 分配CALL_BACK到座席
     * @param specification 查找call_back条件
     * @param assignAgentDtos 坐席及对应的分配数
     * @return
     */
    boolean assignCallBackToAgent(CallbackSpecification specification, List<AssignAgentDto> assignAgentDtos) throws ServiceException;




    /**
     * 组管理员分配callback到座席
     * @param mapCallbackId2UsrId  callbackId - 座席id 映射
     * @throws ErpException
     */
    void  assignCallBackToAgentByGrpManager(Map<Long,String> mapCallbackId2UsrId) throws ErpException;

    /**
     * API-CALLBACK-7.	组管理员分配接通电话到座席
     */
    Boolean assignCallbackToGrpMember(LeadInteractionSearchDto liDto, String grpId, List<String> grpMembers, Integer quantity) throws ServiceException;




    /**
     * 保存虚拟进线
     * 并生成相应销售线索和任务
     */
    void saveVirtualCallback(Callback virtualCallback) throws ErpException;
    
    /**
     * API-CALLBACK-14 查询可分配的虚拟进线
     * 根据参数查询可进行话务分配的CALL_BACK数量
     * 
     * @param callbackDto
     * @return
     */
    Integer countAssignableVirtualCallback(CallbackDto callbackDto) throws ErpException;
    
    /**
     * API-CALLBACK-14 查询可分配的虚拟进线
     * 根据参数查询可进行话务分配的CALL_BACK
     * 
     * @param callbackDto
     * @return
     */
    List<Callback> queryAssignableVirtualCallback(CallbackDto callbackDto) throws ErpException;
    
    /**
     * 查询满足条件的座席
     * queryQualifiedAgentUser
     * @param agentQueryDto4Callback
     * @return 
     * List<AgentUserInfo4TeleDist>
     * @exception 
     * @since  1.0.0
     */
    List<AgentUserInfo4TeleDist> queryQualifiedAgentUser(AgentQueryDto4Callback agentQueryDto4Callback);

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
     * 分配虚拟进线到座席组
     *
     * @param callbackDto 查找call_back条件
     * @param grpIds 坐席组
     * @param count    分配数量
     * @return
     */
    boolean assignVirtualCallbackToAgentGrp(CallbackDto callbackDto, List grpIds, Integer count) throws Exception;

	/**
	 * API-CALLBACK-16. 部门管理员分配虚拟进线到座席
	 * assignVirtualCallbackToAgentByDM
	 * @param callbackDto
	 * @param grpIds
	 * @param agents
	 * @param count
	 * @return 
	 * boolean
	 * @exception 
	 * @since  1.0.0
	 */
	boolean assignVirtualCallbackToAgentByDM(CallbackDto callbackDto, List<String> grpIds, List<AgentUser> agents, Integer count);
    
	/**
	 * API-CALLBACK-17. 组管理员分配虚拟进线到座席
	 * 
	 * @param callbackDto
	 * @param agents
	 * @param count
	 * @return
	 * @throws ErpException
	 */
	Boolean assignVirtualCallbackToAgent(CallbackDto callbackDto,
			List<String> agents, Integer count) throws ErpException;

    /**
     * API-CALLBACK-8.	查询可分配的CALL_BACK
     * @return
     */
    List<Callback> findCallbacks(CallbackSpecification specification);
    
    Long findCallbackCount(CallbackSpecification specification) throws SQLException;
    
    Integer findExecutedCallbackCount(CallbackSpecification specification) throws SQLException;

    /**
     * 生成分配批次号
     * @return
     */
    String generateBatchId();

	/**
	 * <p>按比例分配到部门</p>
	 * @param liDto
	 */
	public void assignToDept(LeadInteractionSearchDto liDto, String ivrType) throws Exception;
	
	void sortAgents(List<AgentUserInfo4TeleDist> userInfo4TeleDists) throws Exception;
	
	Field getMostFrequentAgentLevel(List<AgentUserInfo4TeleDist> userInfo4TeleDists) throws Exception;
	
}
