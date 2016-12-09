package com.chinadrtv.erp.uc.service.impl;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.compass.core.util.CollectionUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.constant.LeadInteractionType;
import com.chinadrtv.erp.constant.SecurityConstants;
import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.exception.BizException;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.exception.ErpException;
import com.chinadrtv.erp.exception.ExceptionConstant;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskSourceType;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskSourceType2;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskSourceType3;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskStatus;
import com.chinadrtv.erp.marketing.core.common.LeadStatus;
import com.chinadrtv.erp.marketing.core.dao.LeadDao;
import com.chinadrtv.erp.marketing.core.dao.LeadInterActionDao;
import com.chinadrtv.erp.marketing.core.dao.LeadTaskDao;
import com.chinadrtv.erp.marketing.core.dto.CampaignDto;
import com.chinadrtv.erp.marketing.core.dto.LeadInteractionSearchDto;
import com.chinadrtv.erp.marketing.core.service.CampaignApiService;
import com.chinadrtv.erp.marketing.core.service.CampaignBPMTaskService;
import com.chinadrtv.erp.marketing.core.service.LeadInterActionService;
import com.chinadrtv.erp.marketing.core.service.LeadService;
import com.chinadrtv.erp.model.Contact;
import com.chinadrtv.erp.model.PotentialContact;
import com.chinadrtv.erp.model.marketing.Callback;
import com.chinadrtv.erp.model.marketing.CallbackLog;
import com.chinadrtv.erp.model.marketing.CallbackSpecification;
import com.chinadrtv.erp.model.marketing.Lead;
import com.chinadrtv.erp.model.marketing.LeadInteraction;
import com.chinadrtv.erp.model.marketing.LeadInteractionOrder;
import com.chinadrtv.erp.model.marketing.LeadTask;
import com.chinadrtv.erp.uc.constants.CallbackPriority;
import com.chinadrtv.erp.uc.constants.CallbackStatus;
import com.chinadrtv.erp.uc.constants.CallbackType;
import com.chinadrtv.erp.uc.dao.CallBackDao;
import com.chinadrtv.erp.uc.dao.CallbackLogDao;
import com.chinadrtv.erp.uc.dao.IvrcallbackDao;
import com.chinadrtv.erp.uc.dto.AgentQueryDto4Callback;
import com.chinadrtv.erp.uc.dto.AssignAgentDto;
import com.chinadrtv.erp.uc.dto.CallbackDto;
import com.chinadrtv.erp.uc.dto.GroupDto;
import com.chinadrtv.erp.uc.dto.IvrDeptRate;
import com.chinadrtv.erp.uc.dto.PhoneAddressDto;
import com.chinadrtv.erp.uc.service.CallbackService;
import com.chinadrtv.erp.uc.service.ContactService;
import com.chinadrtv.erp.uc.service.PhoneService;
import com.chinadrtv.erp.uc.service.PotentialContactService;
import com.chinadrtv.erp.user.dto.AgentUserInfo4TeleDist;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.model.GroupInfo;
import com.chinadrtv.erp.user.service.AgentUserService;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.chinadrtv.erp.util.DateUtil;
import com.chinadrtv.erp.util.PojoUtils;

/**
 * Created with IntelliJ IDEA.
 * User: haoleitao
 * Date: 13-7-19
 * Time: 下午2:19
 * To change this template use File | Settings | File Templates.
 */
@Service("callbackService")
public class CallbackServiceImpl extends GenericServiceImpl<Callback, Long> implements CallbackService {
    private static final Logger logger = Logger.getLogger(CallbackServiceImpl.class.getName());
    @Autowired
    private CampaignBPMTaskService campaignBPMTaskService;
    @Autowired
    private UserService userService;
    @Autowired
    private CampaignApiService campaignApiService;
    @Autowired
    private LeadService leadService;

	@Autowired
    private CallbackLogDao callbackLogDao;
    @Autowired
    private CallBackDao callBackDao;

    @Autowired
    private LeadTaskDao leadTaskDao;
    @Autowired
    private LeadDao leadDao;

    @Autowired
    private ContactService contactService;
    @Autowired
    private LeadInterActionService leadInterActionService;
    @Autowired
    private PotentialContactService potentialContactService;
    @Autowired
    private PhoneService phoneService;
    @Autowired
    private AgentUserService agentUserService;
    @Autowired
    private LeadInterActionDao leadInterActionDao;
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private IvrcallbackDao ivrcallbackDao;
    
    @Override
    protected GenericDao<Callback, Long> getGenericDao() {
        return callBackDao;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<GroupDto> findValidGroup(String userId, String groupid, Long grpType, Long callType, String grpName)  {
    	List<GroupDto> results = callBackDao.findValidGroup(userId, groupid, grpType, callType, grpName);
		Collections.sort(results, new Comparator<GroupDto>() {
			@Override
			public int compare(GroupDto o1,
					GroupDto o2) {
				return (((GroupDto)o1).getGrpid()).compareTo(((GroupDto)o2).getGrpid());
			}
		});

        if(callType == 3){ //通话ABC
            CallbackSpecification specification = new CallbackSpecification();
            specification.setCallType(String.valueOf(callType));
            for(GroupDto dto : results) {
                specification.setWorkGroup(dto.getGrpid());
                try{
                    Integer qty = callBackDao.findExecutedCallbackCount(specification);
                    dto.setGrpqty(qty);
                }catch(Exception ex){
                    logger.severe("统计组"+dto.getGrpid()+"通话ABC出错");
                    dto.setGrpqty(0);
                }
                try
                {
                    String deptId = userService.getDepartmentByGroup(dto.getGrpid());
                    String deptName = "";
                    if(StringUtils.isNotBlank(deptId)){
                       deptName = userService.getDepartmentDisplayName(deptId);
                    }
                    dto.setDeptid(deptId);
                    dto.stDeptname(deptName);
                } catch (Exception ex) {
                    dto.setDeptid(null);
                    dto.stDeptname(null);
                }
            }
        }
        else {
            for(GroupDto dto : results) {
                try
                {
                    String deptId = userService.getDepartmentByGroup(dto.getGrpid());
                    String deptName = "";
                    if(StringUtils.isNotBlank(deptId)){
                        deptName = userService.getDepartmentDisplayName(deptId);
                    }
                    dto.setDeptid(deptId);
                    dto.stDeptname(deptName);
                } catch (Exception ex) {
                    dto.setDeptid(null);
                    dto.stDeptname(null);
                }
            }
        }
        return results;
    }

    public List<GroupDto> findValidGroup(CallbackSpecification specification)  {
        //0:分配 3 通话
        List<GroupDto> results =callBackDao.findValidGroup(0L, Long.parseLong(specification.getCallType()), specification.getWorkGroup());
        for(GroupDto dto : results){
            specification.setWorkGroup(dto.getGrpid());
            try{
                Integer qty = callBackDao.findExecutedCallbackCount(specification);
                dto.setGrpqty(qty);
            }catch(Exception ex){
                dto.setGrpqty(0);
            }
        }
        return results;
    }

    /**
	 * <p>Title: assignToGroup</p>
	 * <p>Description: </p>
	 * @param liDto
	 * @param agentGroups
	 * @param averageAssign
	 * @return Boolean
	 * @throws Exception 
	 * @see com.chinadrtv.erp.uc.service.CallbackService#assignToGroup(com.chinadrtv.erp.model.marketing.LeadInteraction, java.util.List, java.lang.Boolean)
	 */ 
    
	@Override
	public synchronized Map<String, Object>  assignToGroup(LeadInteractionSearchDto liDto, List<Map<String, Object>> agentGroups) throws Exception {
	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String batchId = sdf.format(new Date())+"0";
		Map<String, Object> rsMap = new HashMap<String, Object>();
		int assinedCount = 0;
		
		AgentUser user = SecurityHelper.getLoginUser();
		
		liDto.getLeadInteractionType().add(LeadInteractionType.INBOUND_IN.getIndex() + "");
		liDto.getLeadInteractionType().add(LeadInteractionType.OUTBOUND_IN.getIndex() + "");
		liDto.setDeptId(user.getDepartment());
		List<Object> liIdList = leadInterActionService.queryAllocatableLeadInteraction(liDto);
		List<LeadInteraction> liList = new ArrayList<LeadInteraction>();
		for(Object id : liIdList){
			LeadInteraction li = leadInterActionService.get(new Long(id.toString()));
			liList.add(li);
		}
		
		Integer products = liList.size();
		Integer consumers = agentGroups.size();
		if(products == 0){
			throw new ServiceException("Invalid_Parameter", "没有可分配的数量");
		}
		if(consumers == 0){
			throw new ServiceException("Invalid_Parameter", "没有待分配的坐席");
		}
		
		//校验总行数
		int total = 0;
		for(Map<String, Object> groupMap : agentGroups){
			Integer assignCount = Integer.parseInt(null==groupMap.get("assignCount")? "0" : groupMap.get("assignCount").toString());
			total += assignCount;
		}
		
		if(total>products){
			throw new ServiceException("Invalid_Parameter", "预分配的总行数不能大于可分配量");
		}
		
		for(Map<String, Object> groupMap : agentGroups){
			assinedCount += consumeCustomCount(groupMap, liList, false, batchId, liDto);
		}
		rsMap.put("batchId", batchId);
		rsMap.put("assinedCount", assinedCount);
		
		return rsMap;
	}
	

	/**
	 * <p>分配给坐席固定数量的回呼客户</p>
	 * @param groupMap
	 * @param liList
	 * @throws ServiceException 
	 */
	private int consumeCustomCount(Map<String, Object> groupMap, List<LeadInteraction> liList, Boolean assignToUser, 
			String batchId, LeadInteractionSearchDto liDto) throws ServiceException {
		//分配数量
		Integer assignCount = Integer.parseInt(null == groupMap.get("assignCount") ? "0" : groupMap.get("assignCount").toString());
		//已分配数量
		Integer assignedCount = 0;
		
		for(int i=liList.size()-1; i>=0; i--){
			LeadInteraction li = liList.get(i);
			
			if(assignedCount < assignCount){
				//String agentId = groupMap.get("userId").toString();
				String userGroup = groupMap.get("userGroup").toString();
				//保存数据
				this.assignSaveData(li, userGroup, assignToUser, batchId, liDto);
				
				assignedCount ++;
				liList.remove(i);
			}else{
				break;
			}
		}
		
		return assignedCount;
	}

	/**
	 * <p>分配给坐席一个回呼客户</p>
	 * @param groupMap
	 * @param liList
	 * @throws ServiceException 
	 */
/*	private int consumeSingle(Map<String, Object> groupMap, List<LeadInteraction> liList, Boolean assignToUser,
			String batchId, LeadInteractionSearchDto liDto) throws ServiceException {
				
		Integer size = liList.size();
		LeadInteraction li = liList.get(size-1);
		
		String agentId = groupMap.get("userId").toString();
		String userGroup = groupMap.get("userGroup").toString();
		//保存数据
		int assinedCount = this.assignSaveData(li, agentId, userGroup, assignToUser, batchId, liDto);
				
		liList.remove(li);
		
		return assinedCount;
	}*/

	/**
	 * <p>平均分配给坐席回呼客户，</p>
	 * @param groupMap
	 * @param liList
	 * @param averageAssign
	 * @param average
	 * @throws ServiceException 
	 */
/*	private int consumeSameCount(Map<String, Object> groupMap, List<LeadInteraction> liList, 
			Integer average, Boolean assignToUser, String batchId, LeadInteractionSearchDto liDto) throws ServiceException {
		
		//消费计数器
		Integer consumedCount = 0;
		for(int i=liList.size()-1; i>=0; i--){
			if(consumedCount < average){
				LeadInteraction li = liList.get(i);
				
				String agentId = groupMap.get("userId").toString();
				String userGroup = groupMap.get("userGroup").toString();
				//保存数据
				this.assignSaveData(li, agentId, userGroup, assignToUser, batchId, liDto);
				
				consumedCount ++;
				liList.remove(i);
			}else{
				break;
			}
		}
		
		return consumedCount;
	}*/
	

	/**
	 * <p></p>
	 * @param li
	 * @param agentId
	 * @param userGroup
	 * @throws ServiceException 
	 */
	private int assignSaveData(LeadInteraction li, String userGroup, boolean assignToUser, String batchId,
			LeadInteractionSearchDto liDto) throws ServiceException {
		AgentUser user = SecurityHelper.getLoginUser();
		String userId = user.getUserId();
		
		Callback callback = this.adapter(li);
		
		callback.setUsrGrp(userGroup);
		callback.setGroupAssigner(userId);
		
		callback.setDbusrId(userId);
		callback.setDbdt(new Date());
		callback.setFirstdt(new Date());
		callback.setOpusr(userId);
		
		Lead lead = li.getLead();
		sessionFactory.getCurrentSession().evict(lead);
		
		Lead newLead = new Lead();
		BeanUtils.copyProperties(lead, newLead);
		newLead.setId(null);
		newLead.setEndDate(null);
		newLead.setBeginDate(null);
		newLead.setCreateDate(new Date());
		newLead.setPreviousLeadId(lead.getId());
		newLead.setStatus(Long.valueOf(LeadStatus.STARTED.getIndex()));
		newLead.setCreateUser(userId);
		newLead.setOwner(null);
		newLead.setUpdateDate(null);
		newLead.setUpdateUser(null);
		newLead.setUserGroup(userGroup);
		//lead.setOwner(agentId);
		
		Long timeLength = li.getTimeLength();
		newLead.setTaskSourcType(CampaignTaskSourceType.INCOMING.getIndex());
		newLead.setTaskSourcType2(new Long(CampaignTaskSourceType2.CONNECTED.getIndex()));
        if(timeLength <= 20) {
        	newLead.setTaskSourcType3(new Long(CampaignTaskSourceType3.A.getIndex()));	
        }else if(timeLength > 20 && timeLength < 180 ) {
        	newLead.setTaskSourcType3(new Long(CampaignTaskSourceType3.B.getIndex()));
        }else if(timeLength >= 180) {
        	newLead.setTaskSourcType3(new Long(CampaignTaskSourceType3.C.getIndex()));
        }
        //创建一个没有owner的task
        LeadTask leadTask = leadService.saveLead(newLead);
        
        Long bpmInstId =  Long.parseLong(leadTask.getBpmInstanceId());
        callback.setTaskId(bpmInstId);
        
		Long allocateQty = null==li.getAllocateCount() ? 0L : li.getAllocateCount();
		allocateQty++;
		callback.setAllocateCount(new Long(allocateQty));
		callback.setBatchId(batchId);
		callBackDao.saveOrUpdate(callback);
		
		li.setAllocateCount(allocateQty);
		li.setBpmInstId(String.valueOf(bpmInstId));
		li.setUpdateDate(new Date());
		li.setUpdateUser(userId);
		leadInterActionService.saveOrUpdate(li);
		
		CallbackLog cl = (CallbackLog) PojoUtils.convertDto2Pojo(callback, CallbackLog.class);
		
		cl.setBatchId(batchId);
		callbackLogDao.save(cl);
		
		//TODO 去重， 可能有性能问题，待优化
		liDto.getSelectedInteractions().clear();
		liDto.getSelectedInteractions().add(li.getId());
		List<Object> duplicationList = (List<Object>)leadInterActionService.queryObsoleteInteraction(liDto);
		for(Object liId : duplicationList){
			Long _liId = Long.parseLong(null==liId ? null : liId.toString());
			LeadInteraction duplicationLi = leadInterActionDao.get(_liId);
			duplicationLi.setIsValid(0L);
			duplicationLi.setBpmInstId(String.valueOf(bpmInstId));
			duplicationLi.setUpdateDate(new Date());
			duplicationLi.setUpdateUser(userId);
			leadInterActionDao.saveOrUpdate(duplicationLi);
		}
		
		return 1;
	}

	/**
	 * <p>LeadInteraction适配器</p>
	 * @param li
	 * @return Callback
	 * @throws ServiceException 
	 */
	private Callback adapter(LeadInteraction li) throws ServiceException {
		
		AgentUser user = SecurityHelper.getLoginUser();
		String grpId = user.getWorkGrp();
		GroupInfo grp = userService.getGroupInfo(grpId);
		
		Callback cb = new Callback();
		String contactId = li.getContactId();
		Lead lead = li.getLead();
		cb.setLeadInteractionId(li.getId());

		if(lead.getIspotential()==0 && null != contactId && !"".equals(contactId)){
			Contact contact = contactService.findById(contactId);
			cb.setName(contact.getName());
		}else{
			String potentialContactId = lead.getPotentialContactId();
			PotentialContact pc = potentialContactService.findById(new Long(potentialContactId));
			if(null != pc){
				cb.setName(pc.getName());
			}
			cb.setLatentcontactId(potentialContactId);
		}
		
		Set<LeadInteractionOrder> lioSet = li.getLeadInteractionOrders();
		String orderIds = "";
		for(LeadInteractionOrder lio : lioSet){
			StringBuffer sb = new StringBuffer();
			sb.append(lio.getOrderId() + ",");
			orderIds = sb.toString();
			if(orderIds.length()>2){
				orderIds = orderIds.substring(0, orderIds.length()-1);
			}
		}
		
		cb.setContactId(contactId);
		cb.setOrderId(orderIds);
		cb.setStatus(String.valueOf(CallbackStatus.NOT_HANDLE.getIndex()));
		cb.setRequiredt(li.getReserveDate());
		//TODO 待确认
		cb.setPriority("100");
		cb.setFlag("0");
		
		String ani = li.getAni();
		if(null!=ani && !"".equals(ani)){
			PhoneAddressDto paDto = phoneService.splicePhone(ani);
			cb.setPhn1(paDto.getPhn1());
			cb.setPhn2(paDto.getPhn2());
			cb.setPhn3(paDto.getPhn3());
		}
		
		cb.setRemark(li.getComments());
		cb.setMediaplanId(String.valueOf(lead.getCampaignId()));
		cb.setCrdt(new Date());
		cb.setDnis(li.getDnis());
		cb.setAreacode(grp.getAreaCode());
		cb.setType(String.valueOf(CallbackType.CONNECTED.getIndex()));
		cb.setAni(li.getAni());
		cb.setCaseId(li.getCallId());
		cb.setAcdGroup(li.getAcdGroup());
		cb.setAllocatedManual(true);
		
		return cb;
	}

	/** 
	 * <p>Title: assignToAgent</p>
	 * <p>Description: </p>
	 * @param liDto
	 * @param agentUsers
	 * @param averageAssign
	 * @return Boolean
	 * @see com.chinadrtv.erp.uc.service.CallbackService#assignToAgent(com.chinadrtv.erp.model.marketing.LeadInteraction, java.util.List, java.lang.Boolean)
	 */ 
	@Override
	public synchronized Map<String, Object> assignToAgent(LeadInteractionSearchDto liDto, List<Map<String, Object>> agentUsers, Boolean averageAssign)
		throws Exception{
		
		Map<String, Object> rsMap = new HashMap<String, Object>();
		int assinedCount = 0;

		Integer products = callBackDao.queryAvaliableQtyByGroups(agentUsers);
		Integer consumers = agentUsers.size();
		if(products == 0){
			throw new ServiceException("Invalid_Parameter", "没有可分配的数量");
		}
		if(consumers == 0){
			throw new ServiceException("Invalid_Parameter", "没有待分配的坐席");
		}
		
		//校验总行数
		if(!averageAssign){
			int total = 0;
			for(Map<String, Object> userMap : agentUsers){
				Integer assignCount = Integer.parseInt(null==userMap.get("assignCount")? "0" : userMap.get("assignCount").toString());
				total += assignCount;
			}
			
			if(total>products){
				throw new ServiceException("Invalid_Parameter", "预分配的总行数不能大于可分配量");
			}
		}
		
		for(Map<String, Object> userMap : agentUsers){
			assinedCount += consumeCustomCount(userMap, true);
		}
	
		rsMap.put("assinedCount", assinedCount);
		
		return rsMap;
	}

	
	/**
	 * <p></p>
	 * @param userMap
	 * @param b
	 * @param batchId
	 * @param liDto
	 * @return
	 * @throws ServiceException 
	 */
	private int consumeCustomCount(Map<String, Object> userMap, boolean assignToUser) throws ServiceException {
		
		//分配数量
		Integer assignCount = Integer.parseInt(null == userMap.get("assignCount") ? "0" : userMap.get("assignCount").toString());
		String agentId = userMap.get("userId").toString();
		String userGroup = userMap.get("userGroup").toString();
		
		//已分配数量
		Integer assignedCount = 0;
		
		List<Callback> aliviableList = callBackDao.queryAvaliableListByGroup(userGroup);
		
		for(Callback cb : aliviableList) {
			if(assignedCount < assignCount){
				//保存数据
				this.assignSaveData(cb, agentId, userGroup, assignToUser);
				
				assignedCount ++;
			}else{
				break;
			}
		}
		
		return assignedCount;
	}

	/**
	 * <p></p>
	 * @param cb
	 * @param agentId
	 * @param userGroup
	 * @param assignToUser
	 * @throws ServiceException 
	 */
	private void assignSaveData(Callback cb, String agentId, String userGroup, boolean assignToUser) throws ServiceException {
		AgentUser user = SecurityHelper.getLoginUser();
		String userId = user.getUserId();
		Long taskId = cb.getTaskId();
		String assignee = agentId;
		
		//保存callback
		cb.setUsrId(assignee);
		cb.setUserAssigner(userId);
		cb.setDbusrId(userId);
		cb.setRdbusrId(assignee);
		cb.setDbdt(new Date());
		cb.setFirstusrId(assignee);
		cb.setFirstdt(new Date());
		cb.setOpusr(userId);
		callBackDao.saveOrUpdate(cb);
		
		CallbackLog cbLog = (CallbackLog) PojoUtils.convertDto2Pojo(cb, CallbackLog.class);
		cbLog.setId(null);
		callbackLogDao.save(cbLog);
		
		//保存lead
		LeadTask task = leadTaskDao.queryInst(String.valueOf(taskId));
		Lead lead = task.getLead();
		lead.setOwner(assignee);
		lead.setUpdateDate(new Date());
		lead.setUpdateUser(userId);
		lead.setCreateDate(new Date());
		leadDao.saveOrUpdate(lead);
		
		//保存leadTask
		task.setUserid(assignee);
		task.setUpdateDate(new Date());
		task.setUpdateUser(userId);
		task.setCreateDate(new Date());
		leadTaskDao.saveOrUpdate(task);
	}

	@Override
    public boolean assignCallBackToAgentGrp(CallbackSpecification specification, List grpIds, Integer count) {
        //如果页面勾选‘分配到坐席’，则分配到坐席组只是计算各组分配的数量，不对数据库进行任何修改，前段直接处理
        //如果未勾选，则分配到坐席组会平均分配callback并将callback的usrGrp进行更新
        // 1. 根据查询接口查询出符合条件的callback
        List<Callback> callbacks = findCallbacks(specification);
        // 2. 根据坐席组IDs 平均分配 更新callback记录
        int grpSize = grpIds.size();
        for (int i = 0; i < count && i < callbacks.size(); i++) {
            Callback callback = callbacks.get(i);
            callback.setUsrGrp(grpIds.get(i % grpSize).toString());
            callback.setGroupAssigner(SecurityHelper.getLoginUser().getUserId());
            callBackDao.saveOrUpdate(callback);
        }
        return true;
    }

//    @Override
    public boolean assignCallBackToAgent(CallbackSpecification specification, List<String> grpIds, List<AgentUser> agents, Integer count) {
        // 1. 查找出符合分配条件的callback
        List<Callback> callbacks = findCallbacks(specification);

        // 2. 将查找到的callback平均分配到坐席组
        int grpSize = grpIds.size();
        Map<String, List<Callback>> grpCallbackMap = new HashMap<String, List<Callback>>();
        for (int i = 0; i < count && i < callbacks.size(); i++) {
            Callback callback = callbacks.get(i);
            List list = grpCallbackMap.get(grpIds.get(i % grpSize).toString());
            if (list == null) {
                list = new ArrayList();
                list.add(callback);
                grpCallbackMap.put(grpIds.get(i % grpSize).toString(), list);
            } else list.add(callback);
        }

        // 3. 将参数坐席按坐席组分类
        Map<String, List<AgentUser>> grpAgentMap = new HashMap<String, List<AgentUser>>();
        for (String grpId : grpIds) {
            for (AgentUser agentUser : agents) {
                if (agentUser.getWorkGrp().equals(grpId)) {
                    List grpAgents = grpAgentMap.get(grpId);
                    if (grpAgents == null) {
                        grpAgents = new ArrayList();
                        grpAgents.add(agentUser);
                        grpAgentMap.put(grpId, grpAgents);
                    } else grpAgents.add(agentUser);
                }
            }
        }

        // 4. 将每个坐席组分配到的callback再平均分配到该组坐席
        for (String grpId : grpIds) {
            int grpAgentSize = grpAgentMap.get(grpId).size();
            for (int i = 0; i < grpCallbackMap.get(grpId).size(); i++) {
                this.setBaseAssignInfo(grpCallbackMap.get(grpId).get(i), SecurityHelper.getLoginUser(), grpAgentMap.get(grpId).get(i % grpAgentSize).getUserId());
                callBackDao.saveOrUpdate(grpCallbackMap.get(grpId).get(i));
            }
        }
        return true;
    }

    @Override
    public boolean assignCallBackToAgent(CallbackSpecification specification, List<AssignAgentDto> assignAgentDtos) throws ServiceException {
        // 1. 查找出符合分配条件的callback
        List<Callback> callbacks = findCallbacks(specification);
        if (callbacks == null || callbacks.size() == 0) {
            throw new ServiceException("没有找到符合条件的Callback.");
        }
        String batchId = generateBatchId();
        AgentUser user =  SecurityHelper.getLoginUser();
        int assignedCount = 0;
        for (AssignAgentDto assignAgentDto : assignAgentDtos) {
            if (assignedCount + assignAgentDto.getAssignCount() > callbacks.size()) {
                throw new ServiceException("分配总数额大于符合条件的Callback数额, 符合条件的Callback数为" + callbacks.size());
            }
            for (int i = assignedCount; i < assignedCount + assignAgentDto.getAssignCount(); i++) {
                Callback callback = callbacks.get(i);
                if(callback != null){
                    //为坐席创建外呼任务
                    if(callback.getTaskId() == null){
                        LeadTask task = createUserTask(user, callback, assignAgentDto.getUserId(), assignAgentDto.getWorkGrp());
                        if(task != null){
                            callback.setTaskId(Long.parseLong(task.getBpmInstanceId()));
                        }
                        //检查重复Callback并赋予并回写数据(潜客/正式客户)
                        List<Callback> exclusions = findExcludedCallbacks(specification, callback);
                        if(exclusions != null && exclusions.size() > 0){
                            for(Callback exclusion: exclusions){
                                exclusion.setTaskId(callback.getTaskId());
                                setAssignInfo(exclusion, user.getUserId(), assignAgentDto.getUserId(), assignAgentDto.getWorkGrp(), batchId,specification.getAllocatedNumber()+1);
                                invalidateFromCallback(exclusion);
                                callBackDao.saveOrUpdate(exclusion);
                            }
                        }
                    }
                    //记录登记分配历史
                    CallbackLog callbackLog = new CallbackLog();
                    callbackLog.setBatchId(batchId);
                    callbackLog.setFirstusrId(callback.getFirstusrId());
                    callbackLog.setFirstdt(callback.getFirstdt());
                    this.setAssignInfo(callback, user.getUserId(), assignAgentDto.getUserId(), assignAgentDto.getWorkGrp(), batchId,specification.getAllocatedNumber()+1);
                    BeanUtils.copyProperties(callback, callbackLog);
                    callbackLog.setAcdGroup(callback.getAcdGroup());
                    callbackLog.setTaskId(callback.getTaskId());
                    callbackLog.setAllocateCount(callback.getAllocateCount());
                    callbackLog.setAllocatedManual(callback.getAllocatedManual());
                    callbackLog.setCrdt(new Date());
                    callBackDao.saveOrUpdate(callback);
                    callbackLogDao.save(callbackLog);
                }
            }
            assignedCount += assignAgentDto.getAssignCount();
        }
        return true;
    }

    private LeadTask createUserTask(AgentUser agentUser, Callback callback, String userId, String workGrp) throws ServiceException{

         //判断是否创建线索与交互
        if(callback.getLeadInteractionId() != null){
            try {
                 LeadInteraction li = leadInterActionService.get(callback.getLeadInteractionId());
                 if(li != null && li.getLeadId() != null){
                     //不考虑自建任务或samba任务,创建个新任务
                     //List<Object> instIds = leadTaskDao.queryAliveInstsByLead(li.getLeadId());
                     //if(instIds.size() > 0){
                     //    String instId = String.valueOf(instIds.get(0));
                     //    if(instId != null) {
                     //        li.setIsValid(1L);
                     //        li.setBpmInstId(instId);
                     //    }
                     //    return leadTaskDao.queryInst(instId);
                     //} else {
                         Lead lead = leadService.get(li.getLeadId());
                         if(lead != null) {
                             //分配给坐席
                             lead.setOwner(userId);
                             lead.setUserGroup(workGrp);
                             lead.setUpdateUser(agentUser.getUserId());
                             lead.setUpdateDate(new Date());
                             lead.setAppointDate(callback.getRequiredt());
                             lead.setTaskRemark(callback.getRemark());
                             lead.setTaskSourcType((CampaignTaskSourceType.INCOMING.getIndex()));
                             lead.setTaskSourcType2((CampaignTaskSourceType2.SNATCHIN.getIndex()));
                             lead.setTaskSourcType3(-1);
                             if(!leadService.isAlive(lead)) {
                                 Lead lead2 = leadService.getLastestAliveLead(userId, li.getContactId(), String.valueOf(lead.getCampaignId()));
                                 if(lead2 == null) {
                                     lead2 = new Lead();
                                     BeanUtils.copyProperties(lead, lead2);
                                     lead2.setId(null);
                                     lead2.setOwner(userId);
                                     lead2.setStatus(Long.valueOf(LeadStatus.STARTED.getIndex()));
                                     lead2.setEndDate(null);
                                     lead2.setBeginDate(null);
                                     lead2.setPreviousLeadId(lead.getId());
                                     lead2.setUpdateDate(null);
                                     lead2.setUpdateUser(null);
                                     lead2.setCreateDate(new Date());
                                     lead2.setCreateUser(agentUser.getUserId());
                                 }
                                 lead = lead2;
                             }

                             LeadTask task = leadService.saveLead(lead);
                             if(task != null) {
                                 li.setIsValid(1L);
                                 li.setBpmInstId(task.getBpmInstanceId());
                             }
                             return task;
                         }
                     //}
                 }
            } catch (ServiceException ex){
                logger.severe("Task create failure:{callback:"+ callback.getCallbackId() +", li:'"+callback.getLeadInteractionId()+"'}");
                throw ex;
            }
         }
        return null;
    }

    private void invalidateFromCallback(Callback callback) throws ServiceException {
        try {
            LeadInteraction li = leadInterActionService.get(callback.getLeadInteractionId());
            if(li != null){
                li.setIsValid(0L);
                li.setBpmInstId(String.valueOf(callback.getTaskId()));
            }
        } catch (Exception ex){
            /* do nothing */
            logger.severe("Write back failure:{callback:"+ callback.getCallbackId() +", li:'"+callback.getLeadInteractionId()+"'}");
            throw new ServiceException("-1", ex);
        }
    }

    public String generateBatchId() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + callBackDao.getBatchSeq();
    }

    private void setAssignInfo(Callback callback, String assignUserId, String userId, String workGrp, String batchId,Long allocateCount) {
        //callback.setUsrId(userId);
        //callback.setUsrGrp(workGrp);
        callback.setUserAssigner(assignUserId);
        callback.setGroupAssigner(assignUserId);
        callback.setDbusrId(assignUserId);
        callback.setDbdt(new Date());
        callback.setRdbusrId(userId);
        callback.setBatchId(batchId);
        callback.setAllocateCount(allocateCount);
        if (StringUtils.isEmpty(callback.getOpusr())) {
            callback.setOpusr(assignUserId);
        }
        if (StringUtils.isEmpty(callback.getFirstusrId())) {
            callback.setFirstusrId(userId);
        }
        if (callback.getFirstdt() == null) {
            callback.setFirstdt(new Date());
        }
    }

    public void  assignCallBackToAgentByGrpManager(Map<Long,String> mapCallbackId2UsrId) throws ErpException
    {
        if(mapCallbackId2UsrId==null||mapCallbackId2UsrId.size()==0)
            return;
        //首先检查当前登录用户是否是组管理员
        AgentUser user = SecurityHelper.getLoginUser();
        boolean isGroupManager = user.hasRole(SecurityConstants.ROLE_OUTBOUND_GROUP_MANAGER);
        if(isGroupManager==false)
            throw new ErpException(ExceptionConstant.SECURITY_PERMISSION_EXCEPTION, "当前用户没有组权限");
        //检查callback记录里面的组Id是否是当前登陆用户的组
        for(Map.Entry<Long,String> entry: mapCallbackId2UsrId.entrySet())
        {
            Callback callback= callBackDao.get(entry.getKey());
            if(callback.getUsrGrp()!=null && !callback.getUsrGrp().equals(user.getWorkGrp()))
            {
                throw new ErpException(ExceptionConstant.SERVICE_CALLBACK_ASSIGN_EXCEPTION, "当前座席组才能分配");
            }
            //已经处理了，保存
            if(callback.getFlag()!=null&&!callback.getFlag().equals(String.valueOf(CallbackStatus.NOT_HANDLE.getIndex())))
            {
                throw new ErpException(ExceptionConstant.SERVICE_CALLBACK_ASSIGN_EXCEPTION, "callback id:"+callback.getCallbackId()+ "正在被处理中");
            }
            //增加类型判断，只处理callback类型
            if(StringUtils.isEmpty(callback.getType()) || !callback.getType().equals(String.valueOf(CallbackType.VIRTUAL_PHONECALL.getIndex())))
            {
                throw new ErpException(ExceptionConstant.SERVICE_CALLBACK_ASSIGN_EXCEPTION, "callback id:"+callback.getCallbackId()+ "非Callback类型");
            }

            //如果是从一个座席重新分配给另外一个座席，那么需要记录分配日志
            if(StringUtils.isNotEmpty(callback.getUsrId())&&StringUtils.isNotEmpty(entry.getValue()))
            {
                if(entry.getValue().equals(callback.getUsrId()))
                {
                    throw new ErpException(ExceptionConstant.SERVICE_CALLBACK_ASSIGN_EXCEPTION, "callback id:"+callback.getCallbackId()+ "已经分配给座席"+callback.getUsrId());
                }
                else
                {
                    //记录更新前日志
                    CallbackLog callbackLog=new CallbackLog();
                    BeanUtils.copyProperties(callback,callbackLog);
                    callbackLogDao.save(callbackLog);
                }
            }
            this.setBaseAssignInfo(callback,user,entry.getValue());

            callBackDao.saveOrUpdate(callback);
        }
    }

    private void setBaseAssignInfo(Callback callback, AgentUser assignUser, String assignedUsrId)
    {
        callback.setUsrId(assignedUsrId);
        callback.setUserAssigner(assignUser.getUserId());
        callback.setUsrGrp(assignUser.getWorkGrp());
        callback.setDbusrId(assignedUsrId);
        callback.setDbdt(new Date());
        callback.setRdbusrId(assignUser.getUserId());
        if(StringUtils.isEmpty(callback.getOpusr()))
        {
            callback.setOpusr(assignUser.getUserId());
        }
        if(StringUtils.isEmpty(callback.getFirstusrId()))
        {
            callback.setFirstusrId(assignedUsrId);
        }
        if(callback.getFirstdt()==null)
        {
            callback.setFirstdt(new Date());
        }
    }

    public List<Callback> findCallbacks(CallbackSpecification specification){
        return callBackDao.findCallbacks(specification);
    }

    public List<Callback> findExcludedCallbacks(CallbackSpecification specification, Callback callback){
        return callBackDao.findExcludedCallbacks(specification, callback);
    }
    
    public Long findCallbackCount(CallbackSpecification specification) throws SQLException {
        return callBackDao.findCallbackCount(specification);
    }
    
    public Integer findExecutedCallbackCount(CallbackSpecification specification) throws SQLException {
    	return callBackDao.findExecutedCallbackCount(specification);
    }


    public Boolean assignCallbackToGrpMember(LeadInteractionSearchDto liDto, String grpId, List<String> grpMembers, Integer quantity)
            throws ServiceException
    {
        AgentUser user = SecurityHelper.getLoginUser();
        if(user != null){
            List<Object> liIdList = leadInterActionService.queryAllocatableLeadInteraction(liDto);
            if(liIdList.size() < quantity){
                throw new ServiceException("Invalid_Parameter", "没有可分配的数量");
            }
            List<LeadInteraction> liList = new ArrayList<LeadInteraction>();
            for(Object id : liIdList){
                if(quantity > 0){
                    liList.add(leadInterActionService.get(new Long(id.toString())));
                    quantity--;
                } else break;
            }
            int startAt = 0;
            while (liList.size() > 0){
                LeadInteraction li = liList.remove(0);
                if(startAt < grpMembers.size()){
                    assignCallbackToGrpMember(user, li, grpId, grpMembers.get(startAt));
                    startAt++;
                }
                else{
                    startAt = 0;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private void assignCallbackToGrpMember(AgentUser user, LeadInteraction li, String grpId, String grpMember) throws ServiceException{
        Callback callback = adapter(li);
        callback.setUsrId(grpMember);
        callback.setUsrGrp(grpId);
        callback.setRdbusrId(grpMember);
        callback.setUserAssigner(user.getUserId());
        callback.setGroupAssigner(user.getUserId());
        callback.setDbusrId(user.getUserId());
        callback.setDbdt(new Date());
        callback.setFirstusrId(user.getUserId());
        callback.setFirstdt(new Date());
        callback.setOpusr(user.getUserId());
        callBackDao.saveOrUpdate(callback);
        Long allocateQty = null==li.getAllocateCount() ? 0L : li.getAllocateCount();
		allocateQty++;
		li.setAllocateCount(allocateQty);
        leadInterActionService.saveOrUpdate(li);
    }






    /**
     * 保存虚拟进线
     * 并生成相应销售线索和任务
     * 必须设置的属性 - 客户、电话
     */
    public void saveVirtualCallback(Callback virtualCallback) throws ErpException
    {
        List<CampaignDto> campaignDtoList = campaignApiService.queryDefaultCampaign();
        if(campaignDtoList==null||campaignDtoList.size()==0)
        {
            throw new ErpException(ExceptionConstant.SERVICE_CALLBACK_VAITUAL_EXCEPTION, "没有默认的营销计划");
        }
        if(StringUtils.isNotEmpty(virtualCallback.getType()))
        {
            if(!virtualCallback.getType().equals(String.valueOf(CallbackType.VIRTUAL_PHONECALL.getIndex())))
            {
                throw new ErpException(ExceptionConstant.SERVICE_CALLBACK_VAITUAL_EXCEPTION, "保存的进线类型不正确");
            }
        }
        else
        {
            virtualCallback.setType(String.valueOf(CallbackType.VIRTUAL_PHONECALL.getIndex()));
        }
        if(StringUtils.isEmpty(virtualCallback.getContactId())&&StringUtils.isEmpty(virtualCallback.getLatentcontactId()))
        {
            throw new ErpException(ExceptionConstant.SERVICE_CALLBACK_VAITUAL_EXCEPTION, "未提供客户Id");
        }
        if(StringUtils.isEmpty(virtualCallback.getPhn2()))
        {
            throw new ErpException(ExceptionConstant.SERVICE_CALLBACK_VAITUAL_EXCEPTION, "未提供客户电话信息");
        }

        AgentUser user = SecurityHelper.getLoginUser();

        if(virtualCallback.getCrdt()==null)
            virtualCallback.setCrdt(new Date());
        if(StringUtils.isEmpty(virtualCallback.getPriority()))
        {
            virtualCallback.setPriority(String.valueOf(CallbackPriority.NORMAL.getIndex()));
        }
        virtualCallback.setFlag(String.valueOf(CallbackStatus.NOT_HANDLE.getIndex()));
        if(StringUtils.isNotEmpty(virtualCallback.getUsrId())) {
            //已经直接分配了
            this.setBaseAssignInfo(virtualCallback,user,virtualCallback.getUsrId());
        }
        else
        {
            virtualCallback.setUsrGrp(user.getWorkGrp());
        }
        callBackDao.save(virtualCallback);

        Lead lead=new Lead();
        lead.setContactId(virtualCallback.getContactId());
        lead.setPotentialContactId(virtualCallback.getLatentcontactId());
        if(StringUtils.isNotEmpty(virtualCallback.getLatentcontactId()))
        {
            lead.setIspotential(1L);
        }
        else
        {
            lead.setIspotential(0L);
        }
        lead.setCampaignId(campaignDtoList.get(0).getId());

        if(StringUtils.isNotEmpty(virtualCallback.getPriority()))
        {
            int priority=Integer.parseInt(virtualCallback.getPriority());
            if(priority== CallbackPriority.URGENT.getIndex())
            {
                //lead.setPriority();
            }
        }
        lead.setStatus(0L);
        lead.setDnis(null);//虚拟进线应该没有被叫号
        lead.setAni(null);
        lead.setOwner(user.getUserId());
        lead.setUserGroup(user.getWorkGrp());
        lead.setCallDirection(1L);
        String groupType = userService.getGroupType(user.getUserId());
        if("IN".equalsIgnoreCase(groupType)){
            lead.setOutbound(false);
        }
        else if("OUT".equalsIgnoreCase(groupType)){
            lead.setOutbound(true);
        }
        lead.setCallbackTask(true);
        lead.setTaskSourcType(CampaignTaskSourceType.CALLBACK.getIndex());
        lead.setCreateDate(new Date());
        lead.setCreateUser(user.getUserId());

        LeadTask leadTask = leadService.saveLead(lead);

        campaignBPMTaskService.updateTaskStatus(leadTask.getBpmInstanceId(),String.valueOf(CampaignTaskStatus.ACTIVE.getIndex()),null,null);
    }

	@Override
	public Integer countAssignableVirtualCallback(CallbackDto callbackDto)
			throws ErpException {
		if (StringUtils.isNotEmpty(callbackDto.getEndDate())) {
			try {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Date ed = df.parse(callbackDto.getEndDate());
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(ed);
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				callbackDto.setEndDate(df.format(calendar.getTime()));
			} catch (ParseException e) {
			}
		}
		return callBackDao.countAssignableVirtualCallback(callbackDto);
	}
	
	@Override
	public List<Callback> queryAssignableVirtualCallback(CallbackDto callbackDto)
			throws ErpException {
		if (StringUtils.isNotEmpty(callbackDto.getEndDate())) {
			try {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Date ed = df.parse(callbackDto.getEndDate());
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(ed);
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				callbackDto.setEndDate(df.format(calendar.getTime()));
			} catch (ParseException e) {
			}
		}
		return callBackDao.queryAssignableVirtualCallback(callbackDto);
	}
	
	@SuppressWarnings("unchecked")
	public List<AgentUserInfo4TeleDist> queryQualifiedAgentUser(AgentQueryDto4Callback agentQueryDto4Callback) {
		List<AgentUserInfo4TeleDist> userInfo4TeleDists= new ArrayList<AgentUserInfo4TeleDist>();
		if(StringUtils.isNotBlank(agentQueryDto4Callback.getAgentId())) {
			String usrId = agentQueryDto4Callback.getAgentId();
			AgentUserInfo4TeleDist agentUserInfo4TeleDist = agentUserService.queryUserLevel(agentQueryDto4Callback.getAgentId());
			try {
				String grpId = userService.getUserGroup(agentQueryDto4Callback.getAgentId());
				String groupDisplayName = userService.getGroupDisplayName(grpId);
				agentUserInfo4TeleDist.setUserWorkGrpName(groupDisplayName);
				agentUserInfo4TeleDist.setUserWorkGrp(grpId);
				//查询count for users
				CallbackSpecification cbSpecs = new CallbackSpecification();
				if(StringUtils.isNotBlank(agentQueryDto4Callback.getLowDate())) {
					cbSpecs.setStartDateString(agentQueryDto4Callback.getLowDate());
				}
				if(StringUtils.isNotBlank(agentQueryDto4Callback.getHighDate())) {
					cbSpecs.setEndDateString(agentQueryDto4Callback.getHighDate());
				}
				if(StringUtils.isBlank(agentQueryDto4Callback.getLowDate()) 
						&& StringUtils.isBlank(agentQueryDto4Callback.getHighDate())) {
					String lowDate, highDate=null;
					Calendar  calendar=Calendar.getInstance();  
					int year=calendar.get(Calendar.YEAR);  
					int month=calendar.get(Calendar.MONTH);
					month++;
					int date=calendar.get(Calendar.DATE);
					lowDate=year+"-"+month+"-"+date;
					try {
						highDate = DateUtil.addDays(lowDate, 1);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					cbSpecs.setStartDateString(lowDate);
					cbSpecs.setEndDateString(highDate);
				}
				List<String> userIds = new ArrayList<String>();
				userIds.add(usrId);
				cbSpecs.setCallType(agentQueryDto4Callback.getcType());
				cbSpecs.setAgentUserIds(userIds);
				Map<String, AgentUserInfo4TeleDist> agentUsersAlloCounts = callBackDao.findAllocatedCallbackCountInBatch(cbSpecs);
				
				cbSpecs.setQueryExecuted(true);
				Map<String, AgentUserInfo4TeleDist> agentUsersExecCounts = callBackDao.findExecutedCallbackCountInBatch(cbSpecs);
				
				Map<String, AgentUserInfo4TeleDist> agentUsersInlineCounts = 
						getAgentUsersInlineCounts(agentQueryDto4Callback, userIds);
				if(agentUsersAlloCounts.get(usrId) != null) {
					agentUserInfo4TeleDist.setIntradayAllocatedCount(agentUsersAlloCounts.get(usrId).getIntradayAllocatedCount());
				}
				if(agentUsersExecCounts.get(usrId) != null) {
					agentUserInfo4TeleDist.setIntradayExecutedCount(agentUsersExecCounts.get(usrId).getIntradayExecutedCount());
				}
				if(agentUsersInlineCounts.get(usrId) != null) {
					agentUserInfo4TeleDist.setIntradayInlineCount(agentUsersInlineCounts.get(usrId).getIntradayInlineCount());
				}
			} catch (Exception e) {
				logger.log(Level.SEVERE, "Work Group display name not found.", e);
			}
			if(agentUserInfo4TeleDist != null && checkAgentUserQualification(agentQueryDto4Callback, agentUserInfo4TeleDist)) {
				userInfo4TeleDists.add(agentUserInfo4TeleDist);
			}
		} else {
			for(String group : agentQueryDto4Callback.getGroups()) {
				try {
					List<AgentUserInfo4TeleDist> userInfo4TeleDistsGrp= new ArrayList<AgentUserInfo4TeleDist>();
					
					List<AgentUser> users= userService.getUserList(group);
					
					List<String> userIds = extractUserId(users);
					if(userIds == null || userIds.size() == 0) {
						continue;
					}
					Map<String, AgentUserInfo4TeleDist> agentUsersLdap = convertAgentUser(users);
					//查询level for users
					Map<String, AgentUserInfo4TeleDist> agentUsersLevels = agentUserService.queryUserLevelInBatch(userIds);
					//查询count for users
					CallbackSpecification cbSpecs = new CallbackSpecification();
					if(StringUtils.isNotBlank(agentQueryDto4Callback.getLowDate())) {
						cbSpecs.setStartDateString(agentQueryDto4Callback.getLowDate());
					}
					if(StringUtils.isNotBlank(agentQueryDto4Callback.getHighDate())) {
						cbSpecs.setEndDateString(agentQueryDto4Callback.getHighDate());
					}
					if(StringUtils.isBlank(agentQueryDto4Callback.getLowDate()) 
							&& StringUtils.isBlank(agentQueryDto4Callback.getHighDate())) {
						String lowDate, highDate=null;
						Calendar  calendar=Calendar.getInstance();  
						int year=calendar.get(Calendar.YEAR);  
						int month=calendar.get(Calendar.MONTH);
						month++;
						int date=calendar.get(Calendar.DATE);
						lowDate=year+"-"+month+"-"+date;
						try {
							highDate = DateUtil.addDays(lowDate, 1);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						cbSpecs.setStartDateString(lowDate);
						cbSpecs.setEndDateString(highDate);
					}
					cbSpecs.setCallType(agentQueryDto4Callback.getcType());
					cbSpecs.setAgentUserIds(userIds);
					Map<String, AgentUserInfo4TeleDist> agentUsersAlloCounts = callBackDao.findAllocatedCallbackCountInBatch(cbSpecs);
					
					cbSpecs.setQueryExecuted(true);
					Map<String, AgentUserInfo4TeleDist> agentUsersExecCounts = callBackDao.findExecutedCallbackCountInBatch(cbSpecs);
					
					String grpDisplayName = userService.getGroupDisplayName(group);
					
					Map<String, AgentUserInfo4TeleDist> agentUsersInlineCounts = 
							getAgentUsersInlineCounts(agentQueryDto4Callback, userIds);
					
					for(String usrId : agentUsersLevels.keySet()) {
						String lv1 = agentUsersLevels.get(usrId).getLevelId();
						String lv2 = agentUsersLevels.get(usrId).getLevelId2();
						String lv3 = agentUsersLevels.get(usrId).getLevelId3();
						if(agentUsersLdap.get(usrId) != null) {
							agentUsersLdap.get(usrId).setLevelId(lv1);
							agentUsersLdap.get(usrId).setLevelId2(lv2);
							agentUsersLdap.get(usrId).setLevelId3(lv3);
						}
					}
					for(String usrId : agentUsersAlloCounts.keySet()) {
						int intradayAllocatedCount = agentUsersAlloCounts.get(usrId).getIntradayAllocatedCount();
						if(agentUsersLdap.get(usrId) != null) {
							agentUsersLdap.get(usrId).setIntradayAllocatedCount(intradayAllocatedCount);
						}
					}
					for(String usrId : agentUsersExecCounts.keySet()) {
						int intradayExecutedCount = agentUsersExecCounts.get(usrId).getIntradayExecutedCount();
						if(agentUsersLdap.get(usrId) != null) {
							agentUsersLdap.get(usrId).setIntradayExecutedCount(intradayExecutedCount);
						}
					}
					for(String usrId : agentUsersInlineCounts.keySet()) {
						int intradayInlineCount = agentUsersInlineCounts.get(usrId).getIntradayInlineCount();
						if(agentUsersLdap.get(usrId) != null) {
							agentUsersLdap.get(usrId).setIntradayInlineCount(intradayInlineCount);
						}
					}
					userInfo4TeleDistsGrp = filterAgentUser(agentUsersLdap, agentQueryDto4Callback, grpDisplayName,group);
					userInfo4TeleDists.addAll(userInfo4TeleDistsGrp);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			try {
				sortAgents(userInfo4TeleDists);
			} catch(Exception e) {
				logger.log(Level.WARNING, "Sort failed", e);
			}
		}
		return userInfo4TeleDists;
	}
	
	private Map<String, AgentUserInfo4TeleDist> convertAgentUser(List<AgentUser> users) {
		Map<String, AgentUserInfo4TeleDist> agentUsersMap = new HashMap<String, AgentUserInfo4TeleDist>();
		for(AgentUser agentUser : users) {
			AgentUserInfo4TeleDist aUser = new AgentUserInfo4TeleDist();
			aUser.setUserId(agentUser.getUserId());
			aUser.setUserName(agentUser.getDisplayName());
			agentUsersMap.put(agentUser.getUserId(), aUser);
		}
		return agentUsersMap;
	}
	
	public void sortAgents(List<AgentUserInfo4TeleDist> userInfo4TeleDists) throws Exception {
		
		final Field field = getMostFrequentAgentLevel(userInfo4TeleDists);
		if(field == null) {
			return;
		} else {
			field.setAccessible(true);
		}

		Collections.sort(userInfo4TeleDists, new Comparator<AgentUserInfo4TeleDist>() {
			@Override
			public int compare(AgentUserInfo4TeleDist o1,
					AgentUserInfo4TeleDist o2) {
				int result = -1;
				try {
					String level1 = (String)field.get(o1);
					String level2 = (String)field.get(o2);
					if(StringUtils.isBlank(level1) && StringUtils.isNotBlank(level2)) {
						result = 1;
					} else if(StringUtils.isNotBlank(level1) && StringUtils.isNotBlank(level2)) {
						if(!level1.equals(level2)) {
							result = level1.toUpperCase().charAt(0) - level2.toUpperCase().charAt(0);
						} else {
							//如果工号非数字，排到最后
							result = Integer.parseInt(o1.getUserId())-Integer.parseInt(o2.getUserId());
						}
					} else if(StringUtils.isBlank(level1) && StringUtils.isBlank(level2)) {
						result = Integer.parseInt(o1.getUserId())-Integer.parseInt(o2.getUserId());
					}
				} catch (Exception e) {
					logger.log(Level.WARNING, "compare failed", e);
				}
				return result;
			}
		});
	}
	
	public Field getMostFrequentAgentLevel(List<AgentUserInfo4TeleDist> userInfo4TeleDists) throws Exception {
		Field field = null;
		int levelACount = 0, levelBCount = 0, levelCCount = 0, maxCount = 0;
		for(AgentUserInfo4TeleDist agentUser : userInfo4TeleDists) {
			if(StringUtils.isNotBlank(agentUser.getLevelId())) {
				levelACount++;
			}
			if(StringUtils.isNotBlank(agentUser.getLevelId2())) {
				levelBCount++;
			}
			if(StringUtils.isNotBlank(agentUser.getLevelId3())) {
				levelCCount++;
			}
		}
		maxCount = levelACount;
		if(levelBCount > maxCount) {
			maxCount = levelBCount;
		}
		if(levelCCount > maxCount) {
			maxCount = levelCCount;
		}
		try {
			if(levelACount == maxCount) {
				field = AgentUserInfo4TeleDist.class.getDeclaredField("levelId");
			} else if(levelBCount == maxCount) {
				field = AgentUserInfo4TeleDist.class.getDeclaredField("levelId2");
			} else if(levelCCount == maxCount) {
				field = AgentUserInfo4TeleDist.class.getDeclaredField("levelId3");
			}
		} catch(Exception e) {
			logger.severe("Field fetching excepiton");
			throw e;
		}
		return field;
	}
	
	private List<String> extractUserId(List<AgentUser> users) {
		List<String> userIds = new ArrayList<String>();
		if(users != null && users.size() > 0) {
			for(AgentUser aUser : users) {
				userIds.add(aUser.getUserId());
			}
		}
		return userIds;
	}
	
	private List<AgentUserInfo4TeleDist> filterAgentUser(Map<String, AgentUserInfo4TeleDist> agentUsers, 
			AgentQueryDto4Callback agentQueryDto4Callback, String grpDisplayName, String group) {
		List<AgentUserInfo4TeleDist> userList = new ArrayList<AgentUserInfo4TeleDist>();
		String lowDate = agentQueryDto4Callback.getLowDate();
		String highDate = agentQueryDto4Callback.getHighDate();
		for(String userId : agentUsers.keySet()) {
			boolean isQualified = true;
			AgentUserInfo4TeleDist agentUser = agentUsers.get(userId);
			if(StringUtils.isNotBlank(agentQueryDto4Callback.getLevel())) {
				String levelQueried = agentQueryDto4Callback.getLevel();
				if(!levelQueried.equals(agentUser.getLevelId()) 
						&& !levelQueried.equals(agentUser.getLevelId2())
						&& !levelQueried.equals(agentUser.getLevelId3())) {
					isQualified = false;
				}
			} else if(agentQueryDto4Callback.getLevels() != null 
					&& agentQueryDto4Callback.getLevels().size() > 0) {
				Set<String> set = new HashSet<String>(agentQueryDto4Callback.getLevels());
				if(agentUser.isLevelNormal()) {
					if(!set.contains(agentUser.getLevelId())
							&& !set.contains(agentUser.getLevelId2())
							&& !set.contains(agentUser.getLevelId3())) {
						isQualified = false;
					}
				} else {
					if(!agentQueryDto4Callback.isAbNormalShowingRequired()) {
						isQualified = false;
					}
				}
			}
			if(StringUtils.isNotBlank(lowDate) || StringUtils.isNotBlank(highDate)) {
				int inlineNum = agentUser.getIntradayInlineCount();
				if(inlineNum < 1) {
					isQualified = false;
				}
			}
			if(isQualified) {
				agentUser.setUserWorkGrpName(grpDisplayName);
				agentUser.setUserWorkGrp(group);
				userList.add(agentUser);
			}
		}
		return userList;
	}
	
	private boolean checkAgentUserQualification(AgentQueryDto4Callback agentQueryDto4Callback, AgentUserInfo4TeleDist agentUserInfo4TeleDist) {
		boolean isQualified = true;
		String lowDate = agentQueryDto4Callback.getLowDate();
		String highDate = agentQueryDto4Callback.getHighDate();
		if(StringUtils.isNotBlank(lowDate) || StringUtils.isNotBlank(highDate)) {
			LeadInteractionSearchDto leadInteractionSearchDto = new LeadInteractionSearchDto();
			leadInteractionSearchDto.setIncomingLowDate(lowDate);
			leadInteractionSearchDto.setIncomingHighDate(highDate);
			leadInteractionSearchDto.setUserId(agentUserInfo4TeleDist.getUserId());
			leadInteractionSearchDto.setOrderRecordEmilationNeeded(false);
			int number = leadInterActionService.queryLeadInteractionCount(leadInteractionSearchDto);
			if(number < 1) {
				isQualified = false;
			} else {
				agentUserInfo4TeleDist.setIntradayInlineCount(number);
			}
		}
		if(StringUtils.isBlank(lowDate) && StringUtils.isBlank(highDate)) {
			LeadInteractionSearchDto leadInteractionSearchDto = new LeadInteractionSearchDto();
			Calendar  calendar=Calendar.getInstance();  
			int year=calendar.get(Calendar.YEAR);  
			int month=calendar.get(Calendar.MONTH);  
			month++; 
			int date=calendar.get(Calendar.DATE);  
			lowDate=year+"-"+month+"-"+date;
			try {
				highDate = DateUtil.addDays(lowDate, 1);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			leadInteractionSearchDto.setIncomingLowDate(lowDate);
			leadInteractionSearchDto.setIncomingHighDate(highDate);
			leadInteractionSearchDto.setUserId(agentUserInfo4TeleDist.getUserId());
			leadInteractionSearchDto.setOrderRecordEmilationNeeded(false);
			int number = leadInterActionService.queryLeadInteractionCount(leadInteractionSearchDto);
			agentUserInfo4TeleDist.setIntradayInlineCount(number);
		}
		if(StringUtils.isNotBlank(agentQueryDto4Callback.getLevel())) {
			String levelQueried = agentQueryDto4Callback.getLevel();
			if(!levelQueried.equals(agentUserInfo4TeleDist.getLevelId()) 
					&& !levelQueried.equals(agentUserInfo4TeleDist.getLevelId2())
					&& !levelQueried.equals(agentUserInfo4TeleDist.getLevelId3())) {
				isQualified = false;
			}
		}
		return isQualified;
	}
	
	protected Map<String, AgentUserInfo4TeleDist> getAgentUsersInlineCounts(AgentQueryDto4Callback agentQueryDto4Callback,
			List<String> userIds) {
		String lowDate = agentQueryDto4Callback.getLowDate();
		String highDate = agentQueryDto4Callback.getHighDate();
		LeadInteractionSearchDto leadInteractionSearchDto = new LeadInteractionSearchDto();
		leadInteractionSearchDto.setUserIds(userIds);
		leadInteractionSearchDto.setOrderRecordEmilationNeeded(false);
		if(StringUtils.isNotBlank(lowDate) || StringUtils.isNotBlank(highDate)) {
			leadInteractionSearchDto.setIncomingLowDate(lowDate);
			leadInteractionSearchDto.setIncomingHighDate(highDate);
		} else if(StringUtils.isBlank(lowDate) && StringUtils.isBlank(highDate)) {
			Calendar  calendar=Calendar.getInstance();  
			int year=calendar.get(Calendar.YEAR);  
			int month=calendar.get(Calendar.MONTH);  
			month++; 
			int date=calendar.get(Calendar.DATE);  
			lowDate=year+"-"+month+"-"+date;
			try {
				highDate = DateUtil.addDays(lowDate, 1);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			leadInteractionSearchDto.setIncomingLowDate(lowDate);
			leadInteractionSearchDto.setIncomingHighDate(highDate);
		}
		return leadInterActionService.queryLeadInteractionCountInBatch(leadInteractionSearchDto);
	}
	public String queryPhoneByTaskId(String taskId) {
		return callBackDao.queryPhoneByTaskId(taskId);
	}

    @Override
    public boolean assignVirtualCallbackToAgentGrp(CallbackDto callbackDto, List grpIds, Integer count) throws Exception {
        //如果页面勾选‘分配到坐席’，则分配到坐席组只是计算各组分配的数量，不对数据库进行任何修改，前段直接处理
        //如果未勾选，则分配到坐席组会平均分配虚拟进线并将callback的usrGrp进行更新
        // 1. 根据查询接口查询出符合条件的虚拟进线
        List<Callback> callbacks = queryAssignableVirtualCallback(callbackDto);
        // 2. 根据坐席组IDs 平均分配 更新callback记录
        int grpSize = grpIds.size();
        for (int i = 0; i < count && i < callbacks.size(); i++) {
            Callback callback = callbacks.get(i);
            callback.setUsrGrp(grpIds.get(i % grpSize).toString());
            callback.setGroupAssigner(SecurityHelper.getLoginUser().getUserId());
            callBackDao.saveOrUpdate(callback);
        }
        return true;
    }

	@Override
	public Boolean assignVirtualCallbackToAgent(CallbackDto callbackDto,
			List<String> agents, Integer count) throws ErpException {
		List<Callback> callbacks = queryAssignableVirtualCallback(callbackDto);
		if (CollectionUtils.isEmpty(callbacks)) {
			return false;
		}
		for (int i = 0; i < count && i < callbacks.size(); i++) {
            Callback callback = callbacks.get(i);
            callback.setUserAssigner(agents.get(i % agents.size()));
            callback.setGroupAssigner(SecurityHelper.getLoginUser().getUserId());
            callBackDao.saveOrUpdate(callback);
        }
		return true;
	}
    
	@Override
    public boolean assignVirtualCallbackToAgentByDM(CallbackDto callbackDto, List<String> grpIds, List<AgentUser> agents, Integer count) {
        // 1. 查找出符合分配条件的callback
        List<Callback> callbacks = null;
		try {
			callbacks = queryAssignableVirtualCallback(callbackDto);
		} catch (ErpException e) {
		}
		if(callbacks == null || callbacks.size() == 0) {
			return false;
		}
        // 2. 将查找到的callback平均分配到坐席组
        int grpSize = grpIds.size();
        Map<String, List<Callback>> grpCallbackMap = new HashMap<String, List<Callback>>();
        for (int i = 0; i < count && i < callbacks.size(); i++) {
            Callback callback = callbacks.get(i);
            List<Callback> list = grpCallbackMap.get(grpIds.get(i % grpSize).toString());
            if (list == null) {
                list = new ArrayList<Callback>();
                list.add(callback);
                grpCallbackMap.put(grpIds.get(i % grpSize).toString(), list);
            } else list.add(callback);
        }

        // 3. 将参数坐席按坐席组分类
        Map<String, List<AgentUser>> grpAgentMap = new HashMap<String, List<AgentUser>>();
        for (String grpId : grpIds) {
            for (AgentUser agentUser : agents) {
                if (agentUser.getWorkGrp().equals(grpId)) {
                    List<AgentUser> grpAgents = grpAgentMap.get(grpId);
                    if (grpAgents == null) {
                        grpAgents = new ArrayList<AgentUser>();
                        grpAgents.add(agentUser);
                        grpAgentMap.put(grpId, grpAgents);
                    } else grpAgents.add(agentUser);
                }
            }
        }

        // 4. 将每个坐席组分配到的callback再平均分配到该组坐席
        for (String grpId : grpIds) {
            int grpAgentSize = grpAgentMap.get(grpId).size();
            for (int i = 0; i < grpCallbackMap.get(grpId).size(); i++) {
                this.setBaseAssignInfo(grpCallbackMap.get(grpId).get(i), SecurityHelper.getLoginUser(), grpAgentMap.get(grpId).get(i % grpAgentSize).getUserId());
                callBackDao.saveOrUpdate(grpCallbackMap.get(grpId).get(i));
            }
        }
        return true;
    }

	/** 
	 * <p>Title: assignToDept</p>
	 * <p>Description: </p>
	 * @param liDto
	 * @throws Exception
	 * @see com.chinadrtv.erp.uc.service.CallbackService#assignToDept(com.chinadrtv.erp.marketing.core.dto.LeadInteractionSearchDto)
	 */ 
	@Override
	public synchronized void assignToDept(LeadInteractionSearchDto liDto, String ivrType) throws Exception {
		List<IvrDeptRate> quotaList = new ArrayList<IvrDeptRate>();
		if(ivrType.equals("Snatch-In")) {
			quotaList =  ivrcallbackDao.getIvrDeptRates("B");
		}else{
			quotaList =  ivrcallbackDao.getIvrDeptRates(ivrType);
		}
		
		liDto.setManagerGrp(SecurityHelper.getLoginUser().getWorkGrp());
		liDto.getLeadInteractionType().add(LeadInteractionType.INBOUND_IN.getIndex() + "");
		liDto.getLeadInteractionType().add(LeadInteractionType.OUTBOUND_IN.getIndex() + "");
		liDto.setDeptIdNotNullNeeded(true);
		liDto.setDeDuplicatedNeeded(false);
		Integer qty = leadInterActionService.queryAllocatableLeadInteractionCount(liDto);
		List<Object> avaliableList = leadInterActionService.queryAllocatableLeadInteraction(liDto);
		
		if(qty == 0) {
			throw new BizException("可分配数量为0");
		}
		
		int step = 0;
		int lastIndex = 0;
		//总分配量
		int total = 0;
		for(IvrDeptRate rate : quotaList) {
			String deptId = rate.getDeptid();
			Double rateIndex = rate.getRate();
			
			long affectedQty = Math.round(qty * rateIndex * 0.01);
			
			for(; step<avaliableList.size(); step++) {
				if(step < (affectedQty + lastIndex)) {
					//单条更新，批量更新会导致SQL过长，无法执行
					String leadInteractionId = String.valueOf(avaliableList.get(step));
					leadInterActionDao.updateDeptByIndex(leadInteractionId, deptId);
					total ++;
				}else{
					break;
				}
			}
			
			lastIndex = step;
		}
		
		//如果只有一条数据，按配额计算下来不足以分配，则取最大的配额分配
		int diff = avaliableList.size() - total;
		int left = diff; 
		for(int i=0; i < diff && left > 0; i++) {
			List<IvrDeptRate> orderedRateList = this.orderRate(quotaList);
			while(left > 0) {
				for(int j = 0; j < orderedRateList.size() && left > 0; j++) {
					IvrDeptRate rate = orderedRateList.get(j);
					String leadInteractionId = String.valueOf(avaliableList.get(total));
					leadInterActionDao.updateDeptByIndex(leadInteractionId, rate.getDeptid());
					
					total ++;
					left --;
				}	
			}	
		}
	}

	/**
	 * <p></p>
	 * @param quotaList
	 * @return
	 */
	private List<IvrDeptRate> orderRate(List<IvrDeptRate> quotaList) {
		for(int i = 0; i < quotaList.size(); i++) {
			IvrDeptRate rate = quotaList.get(i);
			for(int j = i; j < quotaList.size(); j++) {
				IvrDeptRate rateJ = quotaList.get(j);
				if(rate.getRate() < rateJ.getRate()) {
					quotaList.set(i, rateJ);
					quotaList.set(j, rate);
				}
			}
		}
		return quotaList;
	}
    
}
