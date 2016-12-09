package com.chinadrtv.erp.uc.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.marketing.core.dto.LeadInteractionSearchDto;
import com.chinadrtv.erp.model.agent.CallHist;
import com.chinadrtv.erp.model.marketing.Lead;


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
 * @author dengqianyong@chinadrtv.com
 * @version 1.0
 * @since 2013-5-14 上午10:45:30 
 *
 */
public interface CallHistService {

	/**
	 * 
	 * <p>增加电话交互历史</p>
	 *
	 * @param lead 线索
	 * @param contactId 客户编号
	 * @param stTm 开始时间
	 * @param endTm 结束时间
	 * @param userId 座席
	 * @param batchId 业务编号
	 * @param queueId 队列编号
	 * @param callType 呼叫类型
	 */
	void addCallHist(Lead lead, String contactId, Date stTm, Date endTm, String userId,
			String batchId, String queueId, String callType);
	
	/**
	 * API-UC-30. 用户交互历史查询,默认时间限制1年之内
	* <p>Title: </p>
	* <p>Description: </p>
	* @param contactId 客户编号
	* @param sdt 开始时间
	* @param edt 结束时间
	* @param index 分页参数
	* @param size 分页参数
	* @return 查询结果集
	* @throws Exception 查询异常
	 */
	List<CallHist> getCallHistByContactId(String contactId, Date sdt, Date edt,int index, int size) throws Exception;
	
	/**
	 *API-UC-30.	查询用户交互历史 的数量,默认时间限制1年之内
	 * 
	* <p>Title: </p>
	* <p>Description: </p>
	* @param contactId 客户编号
	* @param sdt 开始时间
	* @param edt 结束时间
	* @return 分页数据,总条数
	 */
	int getCallHistByContactIdCount(String contactId, Date sdt, Date edt) throws Exception;

    /**
     * 保存呼叫历史
     * @param callHist
     */
    public void saveCallHist(CallHist callHist);

    /**
     * 更新客户联系历史
     * @param contactId
     * @param potentialContactId
     */
    public void updateCallHistContact(String contactId,String potentialContactId);
    /**
     * 查询用户交互历史，包含callhist  ob_callhist  leadinteraction 三个表的数据
     * @param contactId
     * @param dataGridModel
     * @return
     */
    Map<String,Object> getCallHistLeadInteraction(String contactId,DataGridModel dataGridModel);

}
