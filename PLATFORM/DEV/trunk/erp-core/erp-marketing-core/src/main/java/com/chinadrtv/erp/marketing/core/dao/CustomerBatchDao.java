package com.chinadrtv.erp.marketing.core.dao;

import java.util.Date;
import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.marketing.CustomerBatch;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

/**
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * 客户群管理-批次数据DAO</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * 客户群管理-批次数据DAO</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午3:08:19
 * 
 */
public interface CustomerBatchDao extends
		GenericDao<CustomerBatch, java.lang.String> {

	public List<CustomerBatch> query(String groupCode, DataGridModel dataModel);

	public Integer queryCount(String groupCode);

	public Object[] queryFooter(String groupCode);

	public Long getSeqNextValue();

	public void genBatchDetail(String sqlContent, String jobId, String queueId,
			String batchId, String conSrc, String agentId, String startDate,
			String endDate, String contactInfo, String statisInfo, String valid);
	
	public void genBatchDetail(String sqlContent, String jobId, String queueId,
			String batchId, String conSrc, String agentId, String startDate,
			String endDate, String contactInfo, String statisInfo, String valid,Long campaignId);

	public int update(String batchCode, Long count, Long available);

	public List<CustomerBatch> queryGroupList(String groupCode);

	public Long queryCountByBatchCodeList(String[] groupCode);
	
	public List<CustomerBatch> queryBatchNumList(String sql,String groupCode, Date lastDate,Date now) ;
}
