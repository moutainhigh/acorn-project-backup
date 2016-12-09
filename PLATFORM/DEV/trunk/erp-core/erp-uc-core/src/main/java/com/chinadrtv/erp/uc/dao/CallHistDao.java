package com.chinadrtv.erp.uc.dao;

import java.util.Date;
import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.agent.CallHist;
import com.chinadrtv.erp.model.agent.CallHistLeadInteraction;

/**
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author dengqianyong@chinadrtv.com
 * @version 1.0
 * @since 2013-5-14 上午10:42:22
 * 
 */
public interface CallHistDao extends GenericDao<CallHist, Long> {

	/**
	 * 用户交互历史查询,默认时间限制1年之内
	 * 
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param contactId
	 *            联系人ID
	 * @param sdt
	 *            开始时间
	 * @param edt
	 *            结束时间
	 * @param index
	 *            第几页
	 * @param size
	 *            每页多少条
	 * @return 符合条件的结果集
	 */
	List<CallHist> getCallHistByContactId(String contactId, Date sdt, Date edt, int index, int size) throws Exception;

	/**
	 * 用户交互历史查询的数量,默认时间限制1年之内
	 * 
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param contactId
	 *            联系人ID
	 * @param sdt
	 *            开始时间
	 * @param edt
	 *            结束时间
	 * @return 符合条件数据的数量
	 */
	int getCallHistByContactIdCount(String contactId, Date sdt, Date edt) throws Exception;

	Long getCallHistLeadInteraction(String contactId);

	List<CallHistLeadInteraction> getCallHistLeadInteraction(String contactId, int startRow, int rows);

	/**
	 * 更新callhist联系人
	 * 
	 * @param contactId
	 * @param potentialContactId
	 */
	void updateCallHistContact(String contactId, String potentialContactId);

}
