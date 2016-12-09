/*
 * @(#)OmsDeliveryRegulationServiceImpl.java 1.0 2013-3-25上午11:01:08
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.oms.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.exception.BizException;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.AreaGroupDetail;
import com.chinadrtv.erp.model.OrderAssignRule;
import com.chinadrtv.erp.model.OrderChannel;
import com.chinadrtv.erp.model.Warehouse;
import com.chinadrtv.erp.model.inventory.PlubasInfo;
import com.chinadrtv.erp.oms.common.DataGridModel;
import com.chinadrtv.erp.oms.dao.AreaGroupDetailDao;
import com.chinadrtv.erp.oms.dao.OrderAssignRuleDao;
import com.chinadrtv.erp.oms.dao.PlubasInfoDao;
import com.chinadrtv.erp.oms.dao.WarehouseDao;
import com.chinadrtv.erp.oms.dto.OrderAssignRuleDto;
import com.chinadrtv.erp.oms.service.OrderAssignRuleService;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.google.code.ssm.api.InvalidateAssignCache;

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
 * @since 2013-3-25 上午11:01:08 
 * 
 */
@Service
public class OrderAssignRuleServiceImpl  extends GenericServiceImpl<OrderAssignRule,Long> 
									implements OrderAssignRuleService{

	@Autowired
	private OrderAssignRuleDao orderAssignRuleDao;
	@Autowired
	private AreaGroupDetailDao areaGroupDetailDao;
	@Autowired
	private WarehouseDao warehouseDao;
	@Autowired
	private PlubasInfoDao plubasInfoDao;
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	protected GenericDao<OrderAssignRule, Long> getGenericDao() {
		return orderAssignRuleDao;
	}

	@Qualifier("sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	@InvalidateAssignCache(namespace= "com.chinadrtv.erp.mode.OrderAssignRule", assignedKey = "List")
	public void releaseMemcached(){}
	
	/*
	 * 分页查询
	* <p>Title: queryPageList</p>
	* <p>Description: </p>
	* @param odr
	* @param dataModel
	* @return
	* @see com.chinadrtv.erp.oms.service.OrderAssignRuleService#queryPageList(com.chinadrtv.erp.model.OrderAssignRule, com.chinadrtv.erp.oms.common.DataGridModel)
	 */
	public Map<String, Object>  queryPageList(OrderAssignRuleDto odr, DataGridModel dataModel) {
		Map<String, Object> pageMap = new HashMap<String, Object>();
		
		int count = orderAssignRuleDao.queryPageCount(odr);
		List<OrderAssignRule> pageList = orderAssignRuleDao.queryPageList(odr, dataModel);
		
	/*	Session session = sessionFactory.getCurrentSession();
		for(OrderAssignRule oar: pageList){
			//FIXME 全局事务配置有问题，不执行 saveOrUpdate 操作，不应该将POJO 保存到库中去
			session.evict(oar.getOrderChannel());
			session.evict(oar.getAreaGroup());
			session.evict(oar);
			oar.getOrderChannel().setOrderPayTypes(null);
			oar.getAreaGroup().setAreaGroupDetails(null);
			oar.getAreaGroup().setOrderAssignRules(null);
			oar.getAreaGroup().setOrderChannel(null);
		}*/
		
		pageMap.put("total", count);
		pageMap.put("rows", pageList);
		
		return pageMap;
	}

	
	
	@Override
	public void saveOrUpdate(OrderAssignRule oar) {
		
		OrderChannel channel = oar.getOrderChannel();
		Long priority = oar.getPriority();
		
		List<OrderAssignRule> existList = orderAssignRuleDao.queryCrossList(oar, channel.getId(), priority);
		boolean needCheckAreaGroup = true;
		
		if(null!=oar.getProdCode() && !"".equals(oar.getProdCode())){
			
			needCheckAreaGroup = false;
			
			PlubasInfo plubasInfo = plubasInfoDao.getPlubasInfo(oar.getProdCode());
			if(null==plubasInfo || null==plubasInfo.getRuid()){
				throw new BizException("编码["+oar.getProdCode()+"]对应的商品不存在");
			}else if(plubasInfo.getIssuiteplu().equals("1")){
				throw new BizException("编码["+oar.getProdCode()+"]不是有效的单品");
			}
		}
		
		if(null != oar.getMinAmount()){
			needCheckAreaGroup = false;
		}
		
		//如果同一渠道与优先级有数据
		if(needCheckAreaGroup && existList.size()>0){
			Long currAreaGroupId = oar.getAreaGroup().getId();
			List<AreaGroupDetail> currAgdList = areaGroupDetailDao.getDetailListByGroupId(currAreaGroupId);
			
			for(OrderAssignRule model : existList){
				//如果当前规则是 地址匹配
				if(null != model.getAreaGroup()){
					Long compareAreaGroupId = model.getAreaGroup().getId();
					if(currAreaGroupId.equals(compareAreaGroupId)){
						throw new BizException("当前地址组编号["+currAreaGroupId+"]与编号为["+model.getId()+"]的匹配规则有交叉，请检查");
					}
					
					List<AreaGroupDetail> agdList = areaGroupDetailDao.getDetailListByGroupId(compareAreaGroupId);
					boolean  confilct=  this.compareAreaGroupDetail(currAgdList, agdList);
					if(confilct){
						throw new BizException("当前地址组编号["+currAreaGroupId+"]与编号为["+model.getId()+"]的匹配规则有交叉，请检查");
					}
				}
			}
		}
		
		AgentUser user = SecurityHelper.getLoginUser();
		//如果是添加，则状态是可用
		if(null==oar.getId() || null==oar.isActive()){
			oar.setActive(true);
			oar.setCrDT(new Date());
			oar.setCrUser(user.getUserId());
		}else{
			oar.setMdDT(new Date());
			oar.setMdUser(user.getUserId());
		}
		
		if(null==oar.getAreaGroup() || null==oar.getAreaGroup().getId()){
			oar.setAreaGroup(null);
		}
		
		Warehouse wh = warehouseDao.get(oar.getWarehouseId());
		oar.setWarehouseName(wh.getWarehouseName());
		
		try {
			orderAssignRuleDao.saveOrUpdate(oar);
			sessionFactory.getCurrentSession().flush();
		} catch (ConstraintViolationException e) {
			e.printStackTrace();
			throw new BizException("无效的地址组编号");
		}
	}

	/**
	* @Description: 比较两个AreaGroupDetail是否有交叉
	* @param existList
	* @param currAgdList
	* @return
	* @return AreaGroupDetail
	* @throws
	*/ 
	private boolean compareAreaGroupDetail(List<AreaGroupDetail> currAgdList, List<AreaGroupDetail> compareAgdList) {
		for(AreaGroupDetail currAgd : currAgdList){
			for(AreaGroupDetail compareAgd : compareAgdList){
				
				//如果是0比较 CountryId	
				if(currAgd.getAreaId().equals(0)){
					if(currAgd.getCountyId().equals(compareAgd.getCountyId())){
						return true;
					}
				//如果不是0，比较AreaId
				}else{
					if(currAgd.getAreaId().equals(compareAgd.getAreaId())){
						return true;
					}
				}
			}
		}
		
		return false;
	}

	/* (none Javadoc)
	* <p>Title: changeStatus</p>
	* <p>Description: </p>
	* @param oar
	* @see com.chinadrtv.erp.oms.service.OrderAssignRuleService#changeStatus(com.chinadrtv.erp.model.OrderAssignRule)
	*/ 
	public void changeStatus(OrderAssignRule oar) {
		OrderAssignRule updateOar = orderAssignRuleDao.get(oar.getId());
		updateOar.setActive(oar.isActive());
		this.saveOrUpdate(updateOar);
	}

}
