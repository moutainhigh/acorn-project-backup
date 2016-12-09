/*
 * @(#)CampaignApiController.java 1.0 2013-5-7下午3:37:02
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.erp.marketing.core.common.Constants;
import com.chinadrtv.erp.marketing.core.common.ResponseListResult;
import com.chinadrtv.erp.marketing.core.common.ResponseMapResult;
import com.chinadrtv.erp.marketing.core.common.ResponseResult;
import com.chinadrtv.erp.marketing.core.dto.CampaignDto;
import com.chinadrtv.erp.marketing.core.dto.CampaignRequest;
import com.chinadrtv.erp.marketing.service.CampaignService;
import com.chinadrtv.erp.model.marketing.Campaign;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-5-7 下午3:37:02
 * 
 */
@RequestMapping("api/campaign")
@Controller
public class CampaignApiController extends BaseController {
	private static final Logger logger = LoggerFactory
			.getLogger(CampaignApiController.class);
	@Autowired
	private CampaignService campaignService;

	/**
	 * 查询INBOUND进线营销活动
	 * 
	 * @Description: TODO
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping("/queryInboundCampaign")
	@ResponseBody
	public ResponseListResult queryInboundCampaign(
			@RequestBody CampaignDto campaign) {
		logger.info("400电话=" + campaign.getTollFreeNum() + "=callTime="
				+ campaign.getCallTime());
		return campaignService.queryInboundCampaign(campaign.getTollFreeNum(),
				campaign.getCallTime());
	}

	/**
	 * 查询老客户营销活动
	 * 
	 * @Description: TODO
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping("/queryOldCustomerCampaign")
	@ResponseBody
	public ResponseListResult queryOldCustomerCampaign(
			@RequestBody CampaignDto campaign) {
		logger.info("execDepartment=" + campaign.getExecDepartment());
		return campaignService.queryOldCustomerCampaign(campaign
				.getExecDepartment());
	}

	/**
	 * 新增营销计划
	 * 
	 * @Description: TODO
	 * @param campaignRequest
	 * @return
	 * @throws IOException
	 * @return ResponseMapResult
	 * @throws
	 */
	@RequestMapping(value = "addCampaign", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMapResult addCampaign(
			@RequestBody CampaignRequest campaignRequest) throws IOException {

		ResponseMapResult result = new ResponseMapResult();
		try {
			// todo 增加 判断 该400 号是否有campaign 存在
			campaignRequest.setAudience(Constants.CAMPAIGN_OLD_GROUP);
			Campaign campaign = campaignService.saveCampaign(campaignRequest);

			result.setStatus(Constants.RESPONSE_STATUS_SUCCESS);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("campaignId", campaign.getId());
			result.setResult(map);
		} catch (Exception e) {
			result.setStatus(Constants.RESPONSE_STATUS_ERROR);
			result.setMsg("新增营销计划出错,信息=" + e.getMessage());
		}

		return result;
	}

	/**
	 * 
	 * @Description: 更新营销计划
	 * @param campaignRequest
	 * @return
	 * @throws IOException
	 * @return ResponseResult
	 * @throws
	 */
	@RequestMapping(value = "updateCampaign", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult updateCampaign(
			@RequestBody CampaignRequest campaignRequest) throws IOException {

		ResponseResult result = new ResponseResult();
		try {
			campaignRequest.setAudience(Constants.CAMPAIGN_OLD_GROUP);
			campaignService.updateCampaign(campaignRequest);
			result.setStatus(Constants.RESPONSE_STATUS_SUCCESS);
		} catch (Exception e) {
			result.setStatus(Constants.RESPONSE_STATUS_ERROR);
			result.setMsg("新增营销计划出错,信息=" + e.getMessage());
		}

		return result;
	}
}
