/*
 * @(#)CampaignReceiverServiceImpl.java 1.0 2013年8月28日上午11:10:37
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskSourceType;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskStatus;
import com.chinadrtv.erp.marketing.core.common.Constants;
import com.chinadrtv.erp.marketing.core.dao.CampaignDao;
import com.chinadrtv.erp.marketing.core.dao.CampaignReceiverDao;
import com.chinadrtv.erp.marketing.core.dto.CampaignReceiverDto;
import com.chinadrtv.erp.marketing.core.service.CampaignBPMTaskService;
import com.chinadrtv.erp.marketing.core.service.CampaignReceiverService;
import com.chinadrtv.erp.marketing.core.service.ContactAssignHistService;
import com.chinadrtv.erp.marketing.core.service.LeadService;
import com.chinadrtv.erp.marketing.core.util.StringUtil;
import com.chinadrtv.erp.model.marketing.Campaign;
import com.chinadrtv.erp.model.marketing.CampaignReceiver;
import com.chinadrtv.erp.model.marketing.ContactAssignHist;
import com.chinadrtv.erp.model.marketing.Lead;
import com.chinadrtv.erp.model.marketing.LeadTask;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.chinadrtv.erp.util.PojoUtils;

/**
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
 * @author andrew
 * @version 1.0
 * @since 2013年8月28日 上午11:10:37 
 * 
 */
@Service("campaignReceiverService")
public class CampaignReceiverServiceImpl extends GenericServiceImpl<CampaignReceiver, Long> implements CampaignReceiverService {

	private static transient final Logger logger = Logger.getLogger(CampaignReceiverServiceImpl.class);
	
	@Autowired
	private CampaignReceiverDao campaignReceiverDao;
	@Autowired
	private ContactAssignHistService contactAssignHistService;
	@Autowired
	private CampaignDao campaignDao;
	@Autowired
	private UserService userService;

	@Autowired
	private LeadService leadService;
	@Autowired
	private CampaignBPMTaskService campaignBPMTaskService;
	
	@Override
	protected GenericDao<CampaignReceiver, Long> getGenericDao() {
		return campaignReceiverDao;
	}

	/** 
	 * <p>Title: queryAssignmentByGroup</p>
	 * <p>Description: </p>
	 * @param
	 * @return Model list
	 * @see com.chinadrtv.erp.marketing.core.service.CampaignReceiverService#(com.chinadrtv.erp.model.marketing.CampaignReceiver)
	 */ 
	@Override
	public List<CampaignReceiver> queryAssignmentByGroup(CampaignReceiverDto crDto) {
		return campaignReceiverDao.queryAssignmentByGroup(crDto);
	}

	/** 
	 * <p>Title: assignToAgent</p>
	 * <p>Description: </p>
	 * @param crDto
	 * @param agentList
	 * @see com.chinadrtv.erp.marketing.core.service.CampaignReceiverService#assignToAgent(com.chinadrtv.erp.marketing.core.dto.CampaignReceiverDto, java.util.List)
	 */ 
	@Override
	public void assignToAgent(CampaignReceiverDto crDto, List<Map<String, Object>> agentList, String assignMode) throws Exception{
		List<CampaignReceiver> receiverList = this.queryAssignmentByGroup(crDto);
		
		if(agentList.size() > receiverList.size()){
			ServiceException t =  new ServiceException("Invalid_Operation", "分配数量大于现存数量");
			logger.error("分配数量大于现存数量", t);
			throw t;
		}
		
		AgentUser user = SecurityHelper.getLoginUser();
		
		if("cycle".equalsIgnoreCase(assignMode)){
			this.cycleAssign(receiverList, agentList, user);
		}else if("order".equalsIgnoreCase(assignMode)){
			this.orderAssign(receiverList, agentList, user);
		}
	}


    /**
	 * <p>顺序分配</p>
	 * @param receiverList
	 * @param agentList
     * @throws ServiceException 
	 */
	private void orderAssign(List<CampaignReceiver> receiverList, List<Map<String, Object>> agentList, AgentUser user)
			throws ServiceException {
		
		int pos = 0;
		for(Map<String, Object> assignMap : agentList){
			String agent = null==assignMap.get("userId") ? "" : assignMap.get("userId").toString();
			Integer assignQty = null==assignMap.get("assignCount") ? 0 : Integer.parseInt(assignMap.get("assignCount").toString());
			for(int j=0; j<assignQty; j++){
				CampaignReceiver cr = receiverList.get(pos);
				cr.setAssignAgent(agent);
				cr.setAssignTime(new Date());
				cr.setAssignUser(user.getUserId());
				cr.setStatus(Constants.CAMPAIGN_RECEIVER_STATUS_ASSIGNED_AGENT);
				
				//保存历史
				ContactAssignHist cah = (ContactAssignHist) PojoUtils.convertDto2Pojo(cr, ContactAssignHist.class);
				contactAssignHistService.save(cah);
				
				LeadTask leadTask = leadService.saveLead(this.leadAdapter(cr, agent));
				Long leadId = leadTask.getLeadId();
				
				cr.setLeadId(leadId);
				cr.setBpmInstId(Long.parseLong(leadTask.getBpmInstanceId()));
				campaignReceiverDao.saveOrUpdate(cr);
				pos ++;
			}
		}
	}

	/**
	 * <p>循环分配</p>
	 * @param receiverList
	 * @param agentList
	 * @throws ServiceException 
	 */
	private void cycleAssign(List<CampaignReceiver> receiverList, List<Map<String, Object>> agentList, AgentUser user)
			throws ServiceException {
		
		//找出分配数量大的
		int maxQty = 0;
		for(Map<String, Object> assignMap : agentList){
			Integer assignQty = null==assignMap.get("assignCount") ? 0 : Integer.parseInt(assignMap.get("assignCount").toString());
			if(assignQty > maxQty){
				maxQty = assignQty;
			}
		}
		
		//待分配列表指针
		int pos = 0;
		for(int i=0; i<maxQty; i++){
			
			for(Map<String, Object> assignMap : agentList){
				String agent = null==assignMap.get("userId") ? "" : assignMap.get("userId").toString();
				Integer assignQty = null==assignMap.get("assignCount") ? 0 : Integer.parseInt(assignMap.get("assignCount").toString());
				
				//如果当前坐席分配数量小于这个波次，则分配
				if(i < assignQty){
					CampaignReceiver cr = receiverList.get(pos);
					cr.setAssignAgent(agent);
					cr.setAssignTime(new Date());
					cr.setAssignUser(user.getUserId());
					cr.setStatus(Constants.CAMPAIGN_RECEIVER_STATUS_ASSIGNED_AGENT);
					
					//保存历史
					ContactAssignHist cah = (ContactAssignHist) PojoUtils.convertDto2Pojo(cr, ContactAssignHist.class);
					contactAssignHistService.save(cah);
					
					LeadTask leadTask = leadService.saveLead(this.leadAdapter(cr, agent));
					Long leadId = leadTask.getLeadId();
					
					cr.setLeadId(leadId);
					cr.setBpmInstId(Long.parseLong(leadTask.getBpmInstanceId()));
					campaignReceiverDao.saveOrUpdate(cr);
					pos ++;
				}
			}
		}
	}

	/**
	 * <p></p>
	 * @param cr
	 * @return
     * @throws ServiceException 
	 */
	private Lead leadAdapter(CampaignReceiver cr, String agent) throws ServiceException {
		AgentUser user = SecurityHelper.getLoginUser();
		
		Lead lead = leadService.getLastestAliveLead(agent, cr.getCustomerId(), cr.getCampaignId()+"");
		Campaign campaign = campaignDao.get(cr.getCampaignId());
        if (lead == null) {
            lead = new Lead();
            lead.setContactId(cr.getCustomerId());
            lead.setCampaignId(cr.getCampaignId());
            lead.setDnis(campaign.getDnis());
            lead.setAni(campaign.getTollFreeNum());
            lead.setCallDirection(1L);
            lead.setUserGroup(user.getWorkGrp());
            lead.setCreateUser(user.getUserId());
            lead.setStatus(0L);
            String groupType = userService.getGroupType(user.getUserId());
            if("IN".equalsIgnoreCase(groupType)){
                lead.setOutbound(false);
            }
            else if("OUT".equalsIgnoreCase(groupType)){
                lead.setOutbound(true);
            }
        }
        lead.setOwner(agent);
        lead.setTaskSourcType(CampaignTaskSourceType.PUSH.getIndex());
    
		return lead;
	}

	@Override
    public int getAssigntotle(CampaignReceiverDto dto) {
        return campaignReceiverDao.getAssigntotle(dto);  //To change body of implemented methods use File | Settings | File Templates.
    }




    public void assignToGroup(CampaignReceiverDto dto, String groupIds,String groupNums ,AgentUser user, String alloStrategy) {
        String arrayGids[] = groupIds.split(",");
        String arrayGnums[] = groupNums.split(",");
        List<CampaignReceiver> list2 = new ArrayList();

        //验证分配数量
        if(Constants.SEQUENCIAL_ALLOCATION.equals(alloStrategy)) {
	        int startNum=0,endNum=0,arri=0;
	        for(String num : arrayGnums){
				if (!StringUtil.isNullOrBank(num)) {
					int gnum = Integer.valueOf(num);
					endNum = gnum;
	
					List<CampaignReceiver> list = campaignReceiverDao.getAssignList(dto, startNum, endNum);
					for (CampaignReceiver cr : list) {
						cr.setStatus(Constants.CAMPAIGN_RECEIVER_STATUS_ASSIGNED_GROUP);
						cr.setAssignGroup(arrayGids[arri]);
						cr.setAssignGroupTime(new Date());
						cr.setAssignGroupUser(user.getUserId());
						campaignReceiverDao.saveOrUpdate(cr);
						// 添加分配日志
						// 保存历史
						ContactAssignHist cah = (ContactAssignHist) PojoUtils.convertDto2Pojo(cr, ContactAssignHist.class);
						contactAssignHistService.save(cah);
					}
//					startNum = (endNum - gnum) + 1;
					arri++;
				}
	        }
        } else if(Constants.CYCLE_ALLOCATION.equals(alloStrategy)) {
        	List<CampaignReceiver> list = campaignReceiverDao.getAssignList(dto);
        	if(list != null && list.size() > 0) {
        		Map<Integer, List<Integer>> results = cycleAllocation(arrayGids, arrayGnums, list.size());
        		for(Integer i : results.keySet()) {
        			List<Integer> items = results.get(i);
        			for(Integer k : items) {
        				CampaignReceiver cr = list.get(k);
        				cr.setStatus(Constants.CAMPAIGN_RECEIVER_STATUS_ASSIGNED_GROUP);
						cr.setAssignGroup(arrayGids[i]);
						cr.setAssignGroupTime(new Date());
						cr.setAssignGroupUser(user.getUserId());
						campaignReceiverDao.saveOrUpdate(cr);
						// 添加分配日志
						// 保存历史
						ContactAssignHist cah = (ContactAssignHist) PojoUtils.convertDto2Pojo(cr, ContactAssignHist.class);
						contactAssignHistService.save(cah);
        			}
        		}
        	}
        	
        }
    }
    
    /**
     * [0, 3, 5]
	 * [1, 4, 6, 7, 8, 9]
	 * [2]
     * cycleAllocation
     * @param arrayGids
     * @param arrayGnums
     * @param totalNumber
     * @return 
     * Map<Integer,List<Integer>>
     * @exception 
     * @since  1.0.0
     */
    private Map<Integer, List<Integer>> cycleAllocation(String arrayGids[], String arrayGnums[], int totalNumber) {
    	Map<Integer, List<Integer>> allos = new HashMap<Integer, List<Integer>>();
    	Map<Integer, Integer> anticipationAllo = new HashMap<Integer, Integer>();
    	for(int i = 0; i<arrayGids.length; i++) {
    		anticipationAllo.put(i, Integer.valueOf(arrayGnums[i]));
    	}
    	int location = 0;
    	for(int i = 0; i < totalNumber; i++) {
    		int remainNumber = anticipationAllo.get(location);
    		//分配1个数量 
    		if(remainNumber > 0) {
	    		List<Integer> results = allos.get(location);
	    		if(results == null) {
	    			results = new ArrayList<Integer>();
	    			allos.put(location, results);
	    		}
	    		results.add(i);
	    		//还剩余更新
	    		anticipationAllo.put(location, --remainNumber);
    		} else {
    			--i;
    		}
    		//从头开始再分配 
    		if(location == arrayGids.length -1) {
    			location = 0;
    		} else {
    			location++;
    		}
//    		anticipationAllo.put(key, value)
    	}
    	return allos;
    }



    private ContactAssignHist  campaignReceiverToContactAssignHist(CampaignReceiver campaignReceiver){
        ContactAssignHist hist = new ContactAssignHist();

        hist.setCustomerId(campaignReceiver.getCustomerId());

        hist.setCampaignId(campaignReceiver.getCampaignId());

        hist.setBatchCode(campaignReceiver.getBatchCode());

        hist.setCreateDate(campaignReceiver.getCreateDate());

        hist.setCreateUser(campaignReceiver.getCreateUser());

        hist.setGroupCode(campaignReceiver.getGroupCode());

        hist.setContactInfo(campaignReceiver.getContactInfo());

        hist.setStatisInfo(campaignReceiver.getStatisInfo());

        hist.setJobid(campaignReceiver.getJobid());

        hist.setQueueid(campaignReceiver.getQueueid());

        hist.setPriority(campaignReceiver.getPriority());

        hist.setUpdateDate(campaignReceiver.getUpdateDate());

        hist.setUpdateUser(campaignReceiver.getUpdateUser());

        hist.setDataSource(campaignReceiver.getDataSource());

        hist.setAssignGroup( campaignReceiver.getAssignGroup());

        hist.setAssignGroupUser(campaignReceiver.getAssignGroupUser());

        hist.setAssignGroupTime(campaignReceiver.getAssignGroupTime());
        hist.setAssignAgent(campaignReceiver.getAssignAgent());

        hist.setAssignUser(campaignReceiver.getAssignUser());

        hist.setAssignTime(campaignReceiver.getAssignTime());
        hist.setLeadId(campaignReceiver.getLeadId());
        hist.setStatus(campaignReceiver.getStatus());

        return hist;
    }
    
/*    public static void main(String[] args) {
    	String[] arrayGids = new String[]{"beijingout1", "beijingout2", "beijingout3"};
    	String[] arrayGnums = new String[]{"3","6","1"};
    	int totalNumber = 10;
		CampaignReceiverServiceImpl campaignReceiverServiceImpl = new CampaignReceiverServiceImpl();
		Map<Integer, List<Integer>> results = campaignReceiverServiceImpl.cycleAllocation(arrayGids, arrayGnums, totalNumber);
		for(Integer i : results.keySet()) {
			List<Integer> items = results.get(i);
			System.out.println(Arrays.toString(items.toArray()));
		}
	}*/

	/** 
	 * <p>Title: recycleBatchData</p>
	 * <p>Description: </p>
	 * @param batchNo
	 * @see com.chinadrtv.erp.marketing.core.service.CampaignReceiverService#recycleBatchData(java.lang.String)
	 */ 
	@Override
	public void recycleBatchData(String batchCode) throws Exception {
		List<CampaignReceiver> crList = campaignReceiverDao.queryRecyclableByBatch(batchCode);
		AgentUser user = SecurityHelper.getLoginUser();
		
		for(CampaignReceiver cr : crList) {
			Long leadId = cr.getLeadId();
			Long bpmInstId = cr.getBpmInstId();
			try {
				//结束任务为系统终止
				if(null != leadId && null != bpmInstId) {
					campaignBPMTaskService.updateTaskStatusByLead(leadId, String.valueOf(CampaignTaskStatus.SYSTEMSTOP.getIndex()));
					leadService.updateStatus4OverdueLead(leadId);
				}
				
				cr.setLeadId(null);
				cr.setBpmInstId(null);
				cr.setAssignGroup(null);
				cr.setAssignGroupTime(null);
				cr.setAssignGroupUser(null);
				cr.setAssignAgent(null);
				cr.setAssignTime(null);
				cr.setAssignUser(null);
				cr.setStatus("0");
				
				cr.setUpdateDate(new Date());
				cr.setUpdateUser(null==user ? "" : user.getUserId());
				
				campaignReceiverDao.saveOrUpdate(cr);
			} catch (Exception e) {
				logger.error("回收任务数据失败", e);
				e.printStackTrace();
				throw new ServiceException(e.getMessage());
			}
		}
		
	}

	/** 
	 * <p>Title: queryByBpmInstId</p>
	 * <p>Description: </p>
	 * @param bpmInstId
	 * @return CampaignReceiver
	 * @see com.chinadrtv.erp.marketing.core.service.CampaignReceiverService#queryByBpmInstId(long)
	 */ 
	@Override
	public CampaignReceiver queryByBpmInstId(long bpmInstId) {
		return campaignReceiverDao.queryByBpmInstId(bpmInstId);
	}

	/** 
	 * <p>Title: queryBatchProgress</p>
	 * <p>Description: </p>
	 * @param batchCode
	 * @return Map<String, Object>
	 * @see com.chinadrtv.erp.marketing.core.service.CampaignReceiverService#queryBatchProgress(java.lang.String)
	 */ 
	@Override
	public Map<String, Integer> queryBatchProgress(String batchCode) {
		
		Integer totalQty = campaignReceiverDao.queryTotalQtyByBatch(batchCode);
		
		Integer recyclableQty = campaignReceiverDao.queryRecyclableQtyByBatch(batchCode);
		
		Map<String, Integer> rsMap = new HashMap<String, Integer>();
		rsMap.put("totalQty", totalQty);
		rsMap.put("unStartQty", recyclableQty);
		
		return rsMap;
	}

	/** 
	 * <p>Title: signStatus</p>
	 * <p>Description: </p>
	 * @param taskId
	 * @throws Exception
	 * @see com.chinadrtv.erp.marketing.core.service.CampaignReceiverService#signStatus(java.lang.String)
	 */ 
	@Override
	public void signStatus(Long taskId) throws Exception {
		
		CampaignReceiver cr = campaignReceiverDao.queryByBpmInstId(taskId);
		
        cr.setUpdateDate(new Date());
        cr.setUpdateUser("JOB");
        cr.setStatus(Constants.CAMPAIGN_RECEIVER_STATUS_EXECUTED);
        
        campaignReceiverDao.saveOrUpdate(cr);
	}

}
