/*
 * @(#)CampaignApiServiceImpl.java 1.0 2013-5-10上午10:56:46
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterLong;
import com.chinadrtv.erp.exception.ExceptionConstant;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.common.Constants;
import com.chinadrtv.erp.marketing.core.dao.CampaignApiDao;
import com.chinadrtv.erp.marketing.core.dao.CampaignDao;
import com.chinadrtv.erp.marketing.core.dto.CampaignDto;
import com.chinadrtv.erp.marketing.core.dto.MediaPlan;
import com.chinadrtv.erp.marketing.core.service.CampaignApiService;
import com.chinadrtv.erp.marketing.core.util.StringUtil;
import com.chinadrtv.erp.model.marketing.Campaign;
import com.chinadrtv.erp.model.marketing.CampaignType;
import com.chinadrtv.erp.model.marketing.LeadType;
import com.chinadrtv.erp.util.DateUtil;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-5-10 上午10:56:46
 * 
 */
@Service("campaignApiService")
public class CampaignApiServiceImpl implements CampaignApiService {

	private static final Logger logger = LoggerFactory
			.getLogger(CampaignApiServiceImpl.class);

	@Autowired
	private CampaignApiDao campaignApiDao;
	
	@Autowired
	private CampaignDao campaignDao;

	public Campaign getCampaignById(Long id){
		return campaignDao.get(id);
	}
	
	/**
	 * 根据id获取营销计划信息
	 */
	public CampaignDto getCampaign(Long id) {
		Campaign campaign =  campaignDao.get(id);
		
		CampaignDto dto = null;
		
		if(campaign!=null){
			dto = new CampaignDto();
				dto.setId(campaign.getId());
				dto.setName(campaign.getName());
				dto.setStatus(campaign.getStatus());
				try {
					dto.setStartDate(DateUtil.date2FormattedString(campaign.getStartDate(), "yyyy-MM-dd HH:mm:ss"));
					dto.setEndDate(DateUtil.date2FormattedString(campaign.getEndDate(), "yyyy-MM-dd HH:mm:ss"));
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				dto.setOwner(campaign.getOwner());
				dto.setDescription(campaign.getDescription());
				dto.setCreateUser(campaign.getCreateUser());
				dto.setDepartment(campaign.getDepartment());
				dto.setTollFreeNum(campaign.getTollFreeNum());
				dto.setDnis(campaign.getDnis());
				dto.setCampaignType(campaign.getCampaignType());
				dto.setLeadType(campaign.getLeadType());
				dto.setExecDepartment(campaign.getExecDepartment());
			
		}
		return dto;
	}

	
	/**
	 * <p>
	 * Title: queryInboundCampaign
	 * </p>
	 * <p>
	 * Description: 根据400 号码 呼入时间查询 营销计划
	 * </p>
	 * 
	 * @param tollFreeNum
	 * @param callTime
	 * @return
	 * @throws ServiceException
	 */
	@ReadThroughSingleCache(namespace="com.chinadrtv.erp.marketing.core.service.inboundCampaign",expiration=1800)
	public CampaignDto queryInboundCampaign(@ParameterValueKeyProvider String dnis, String callTime)
			throws ServiceException {
		// TODO Auto-generated method stub
		if (StringUtil.isNullOrBank(dnis)) {
			throw new ServiceException(
					ExceptionConstant.SERVICE_PARAMETER_EXCEPTION, "dnis为空");
		}
		List<CampaignDto> list = new ArrayList<CampaignDto>();
		try {
			list = campaignApiDao.queryInboundCampaign(dnis, callTime);
			if (list != null && !list.isEmpty()) {
				return list.get(0);
			} else {
				//返回系统默认的营销计划
				list =  queryDefaultCampaign();
				if (list != null && !list.isEmpty()) {
					return list.get(0);
				}else{
					return null;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("400号码查询营销计划异常");
			throw new ServiceException(ExceptionConstant.SERVICE_EXCEPTION, e);
		}
	}

	public List<CampaignDto> queryDefaultCampaign(){
		
		List<CampaignDto> result = new ArrayList<CampaignDto>();
		CampaignDto campaignDto= new CampaignDto();
		Campaign campaign= campaignDao.get(Constants.CAMPAIGN_VIRTUAL_ID);
		
		if(campaign!=null){
				campaignDto.setId(campaign.getId());
				campaignDto.setName(campaign.getName());
				campaignDto.setStatus(campaign.getStatus());
				try {
					campaignDto.setStartDate(DateUtil.date2FormattedString(campaign.getStartDate(),"yyyy-MM-dd HH:mm:ss"));
					campaignDto.setEndDate(DateUtil.date2FormattedString(campaign.getEndDate(),"yyyy-MM-dd HH:mm:ss"));
				} catch (SQLException e) {
					logger.error("日期转换失败");
					e.printStackTrace();
					
				}
				
				campaignDto.setOwner(campaign.getOwner());
				campaignDto.setDescription(campaign.getDescription());
				campaignDto.setCreateUser(campaign.getCreateUser());
				campaignDto.setDepartment(campaign.getDepartment());
				campaignDto.setTollFreeNum(campaign.getTollFreeNum());
				campaignDto.setDnis(campaign.getDnis());
				campaignDto.setCampaignType(campaign.getCampaignType());
				campaignDto.setLeadType(campaign.getLeadType());
				campaignDto.setExecDepartment(campaign.getExecDepartment());
				result.add(campaignDto);
		}
		
		return result;
	}
	/**
	 * <p>
	 * Title: queryOldCustomerCampaign
	 * </p>
	 * <p>
	 * Description: 查询老客户营销活动
	 * </p>
	 * 
	 * @param execDepartment
	 * @return
	 */
	public List<CampaignDto> queryOldCustomerCampaign(String execDepartment)
			throws ServiceException {
		if (StringUtil.isNullOrBank(execDepartment)) {
			throw new ServiceException(
					ExceptionConstant.SERVICE_PARAMETER_EXCEPTION,
					"execDepartment为空");
		}
		List<CampaignDto> list = new ArrayList<CampaignDto>();
		try {
			list = campaignApiDao.queryOldCustomerCampaign(execDepartment);
			
			//如果老客户营销计划为空,则返回系统默认的营销计划
			if (!list.isEmpty()) {
				return list;
			}else{
				return queryDefaultCampaign();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("查询老客户营销活动异常");
			throw new ServiceException(ExceptionConstant.SERVICE_EXCEPTION, e);
		}

	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: queryMediaByDnis
	 * </p>
	 * <p>
	 * Description:根据落地号码 查询媒体名称和400电话
	 * </p>
	 * 
	 * @param dnis
	 * 
	 * @return
	 * @throws ServiceException
	 * 
	 * @see com.chinadrtv.erp.marketing.core.service.CampaignApiService#queryMediaByDnis
	 *      (java.lang.String)
	 */
	@Override
	public Map<String, Object> queryMediaByDnis(String dnis)
			throws ServiceException {
		// TODO Auto-generated method stub
		if (StringUtil.isNullOrBank(dnis)) {
			throw new ServiceException(
					ExceptionConstant.SERVICE_PARAMETER_EXCEPTION, "dnis为空");
		}
		try {
			return campaignApiDao.queryMedia(dnis);
		} catch (Exception e) {
			// TODO: handle exception
			throw new ServiceException(ExceptionConstant.DAO_EXCEPTION, e);
		}
	}

	/* (非 Javadoc)
	* <p>Title: queryCampaignForTask</p>
	* <p>Description: </p>
	* @return
	* @see com.chinadrtv.erp.marketing.core.service.CampaignApiService#queryCampaignForTask()
	*/ 
	@Override
	public List<CampaignDto> queryCampaignForTask() {
		return campaignApiDao.queryCampaignForTask();
	}

	/* (非 Javadoc)
	* <p>Title: getMediaPlan</p>
	* <p>Description: </p>
	* @param dnis
	* @param campaign
	* @return
	* @see com.chinadrtv.erp.marketing.core.service.CampaignApiService#getMediaPlan(java.lang.String, com.chinadrtv.erp.model.marketing.Campaign)
	*/ 
	@Override
	@ReadThroughSingleCache(namespace="com.chinadrtv.erp.marketing.core.service.media",expiration=86400)
	public MediaPlan getMediaPlan(@ParameterValueKeyProvider String dnis, Campaign campaign) throws ServiceException {
		if(campaign==null){
			throw new ServiceException(ExceptionConstant.SERVICE_PARAMETER_EXCEPTION, "营销计划 为null");
		}
		
		MediaPlan mediaPlan = new MediaPlan();
		String campaignName = campaign.getName();
		String[] str = campaignName.split("_");
		
		if(str.length>1){
			mediaPlan.setMediaName(str[0]);
			mediaPlan.setProductName(str[1]);
		}else{
			mediaPlan.setMediaName(str[0]);
		}
		
		mediaPlan.setTollFreeNum(campaign.getTollFreeNum());
		mediaPlan.setDnis(campaign.getDnis());
		
		return mediaPlan;
	}

	/* (非 Javadoc)
	* <p>Title: getMediaPlan</p>
	* <p>Description: </p>
	* @param dnis
	* @return
	* @see com.chinadrtv.erp.marketing.core.service.CampaignApiService#getMediaPlan(java.lang.String)
	*/ 
	@Override
	@ReadThroughSingleCache(namespace="com.chinadrtv.erp.marketing.core.service.media",expiration=86400)
	public MediaPlan getMediaPlan(@ParameterValueKeyProvider String dnis) throws ServiceException{

		// TODO Auto-generated method stub
				if (StringUtil.isNullOrBank(dnis)) {
					throw new ServiceException(
							ExceptionConstant.SERVICE_PARAMETER_EXCEPTION, "dnis为空");
				}
				MediaPlan mediaPlan = new MediaPlan();;
				List<CampaignDto> list = null;
				try {
					Date now = new Date();
					list = campaignApiDao.queryInboundCampaign(dnis, DateUtil.date2FormattedString(now,"yyyy-MM-dd HH:mm:ss"));
					if (!list.isEmpty()) {
						CampaignDto campaignDto = list.get(0);
						
						if(!StringUtils.isEmpty(campaignDto.getTollFreeNum())){
							String campaignName = campaignDto.getName();
							String[] str = campaignName.split("_");
							
							if(str.length>1){
								mediaPlan.setMediaName(str[0]);
								mediaPlan.setProductName(str[1]);
							}else{
								mediaPlan.setMediaName(str[0]);
							}
							
							mediaPlan.setTollFreeNum(campaignDto.getTollFreeNum());
							mediaPlan.setDnis(campaignDto.getDnis());
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
					logger.error(dnis+"落地号码号码查询营销计划异常");
					throw new ServiceException(ExceptionConstant.SERVICE_EXCEPTION, e);
				}
				
		return mediaPlan;
	}


    public List<Campaign> findCampaignByType(Long type) {

        String hql = "from Campaign c where c.campaignTypeId=:type";
        Map<String, Parameter> parameters = new HashMap<String, Parameter>();
        Parameter parameter = new ParameterLong("type",type);

        return   campaignApiDao.findList(hql,parameter);
    }

	/* (非 Javadoc)
	* <p>Title: queryList</p>
	* <p>Description: </p>
	* @param jobId
	* @return
	* @see com.chinadrtv.erp.marketing.core.service.CampaignApiService#queryList(java.lang.String)
	*/ 
	@Override
	public List<Campaign> queryList(String jobId) {
		Date now = new Date();
		return campaignDao.queryList(jobId,Constants.CAMPAIGN_TYPE_ASSIGN,now);
	}
}
