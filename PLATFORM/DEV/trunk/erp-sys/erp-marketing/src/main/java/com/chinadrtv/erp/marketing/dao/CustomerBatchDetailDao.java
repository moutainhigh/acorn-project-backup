package com.chinadrtv.erp.marketing.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.marketing.CustomerBatchDetail;
import com.chinadrtv.erp.model.marketing.CustomerGroup;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

/**
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * 客户群管理-批次详情DAO</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * 客户群管理-批次详情DAO</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午3:08:19
 * 
 */
public interface CustomerBatchDetailDao extends
		GenericDao<CustomerBatchDetail, java.lang.String> {

	public List<CustomerBatchDetail> query(CustomerBatchDetail batchDetail,
			DataGridModel dataModel);

	public Integer queryCount(CustomerBatchDetail batchDetail);

	public int saveBatchDetail(String sql, String batchId, CustomerGroup group);

	public int deleteRepeatDetail(String batchId, String groupCode, Integer days);

	public int deleteCustomerDetail(String groupCode);

	public int deleteObContact(String groupCode);

	/**
	 * 根据groupcode 得到CustomerBatchDetail
	 * 
	 * @Description: TODO
	 * @param groupCode
	 * @return
	 * @return List<CustomerBatchDetail>
	 * @throws
	 */
	public List<CustomerBatchDetail> querBatchDetailsBygroupCode(
			String groupCode);

	public Integer getCounts(String groupCode, String flag);

	/**
	 * 
	 * @Description: 更新customer_detail 表的优先级
	 * @param batchId
	 * @param orderCol
	 * @return
	 * @return int
	 * @throws
	 */
	public int updateDetailPriority(String batchId, String orderCol);

	/**
	 * 
	 * @Description: 生成customer_detail 表的优先级插入临时表
	 * @param batchId
	 * @param orderCol
	 * @return
	 * @return int
	 * @throws
	 */
	public int insetDetailTemp(String batchId, String orderCol);

	public Integer getSqlCount(String sql);

	public List<Map<String, Object>> getSqlResult(String sql, Integer begin,
			Integer end);

	public Integer getCustomerDetailSeq();

	public Integer saveBatchDetials(String batchId, CustomerGroup group,
			List<Map<String, Object>> list, Date date);
}
