package com.chinadrtv.erp.marketing.core.dao;

import java.util.Date;
import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.marketing.core.dto.CampaignReceiverDto;
import com.chinadrtv.erp.model.marketing.Campaign;
import com.chinadrtv.erp.model.marketing.CampaignReceiver;

/**
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * 营销计划执行结果DAO</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * 营销计划执行结果DAO</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午3:08:19
 * 
 */
public interface CampaignReceiverDao extends
		GenericDao<CampaignReceiver, java.lang.Long> {

	public Integer insertCampaignReceiver(Campaign campaign, Date now);

	/**
	 * <p>根据组查询待分配的任务</p>
	 * @param crDto
	 * @return Model list
	 */
	public List<CampaignReceiver> queryAssignmentByGroup(CampaignReceiverDto crDto);

    /**
     * <p>获取分配到组的数量</p>
     *  dto ==>Long campaignId,String batchCode,String jobId
     * @param dto
     * @return
     */
    int getAssigntotle(CampaignReceiverDto dto);

    /**
     * <p>获取分配的数据</p>
     *  dto ==>Long campaignId,String batchCode,String jobId
     * @param dto
     * @return
     */
    public  List<CampaignReceiver> getAssignList(CampaignReceiverDto dto,int start,int rows );
    
    public  List<CampaignReceiver> getAssignList(CampaignReceiverDto dto);

	/**
	 * <p>根据lead和contactId 查询</p>
	 * @param leadId
	 * @param contactId
	 * @return CampaignReceiver
	 */
	public CampaignReceiver queryByLeadContact(long leadId, String contactId);

	/**
	 * <p>查询未开始执行的任务</p>
	 * @param batchCode
	 * @return
	 */
	List<CampaignReceiver> queryRecyclableByBatch(String batchCode);

	/**
	 * <p>根据bpmInstId 查询 CampaignReceiver</p>
	 * @param bpmInstId
	 * @return CampaignReceiver
	 */
	CampaignReceiver queryByBpmInstId(long bpmInstId);

	/**
	 * <p>根据批次查询总量</p>
	 * @param batchCode
	 * @return Integer
	 */
	Integer queryTotalQtyByBatch(String batchCode);

	/**
	 * <p>根据批次查询未拨打的任务数</p>
	 * @param batchCode
	 * @return Integer
	 */
	Integer queryRecyclableQtyByBatch(String batchCode);
}
