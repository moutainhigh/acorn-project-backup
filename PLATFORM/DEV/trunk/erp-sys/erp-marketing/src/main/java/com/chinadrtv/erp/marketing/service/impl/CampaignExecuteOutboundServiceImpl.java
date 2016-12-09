/*
 * @(#)CampaignExecuteSmsServiceImpl.java 1.0 2013-4-26上午11:27:32
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.marketing.core.common.Constants;
import com.chinadrtv.erp.marketing.core.dao.CustomerBatchDao;
import com.chinadrtv.erp.marketing.dao.CustomerGroupDao;
import com.chinadrtv.erp.marketing.service.CampaignExecuteService;
import com.chinadrtv.erp.model.marketing.Campaign;
import com.chinadrtv.erp.model.marketing.CustomerBatch;
import com.chinadrtv.erp.model.marketing.CustomerGroup;
import com.chinadrtv.erp.util.DateUtil;

/**
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
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-4-26 上午11:27:32
 * 
 */
@Service("campaignOutboundExecuteService")
public class CampaignExecuteOutboundServiceImpl implements
		CampaignExecuteService {

	private static final Logger logger = LoggerFactory
			.getLogger(CampaignExecuteOutboundServiceImpl.class);

	@Autowired
	private CustomerGroupDao customerGroupDao;

	@Autowired
	private CustomerBatchDao customerBatchDao;

	/**
	 * <p>
	 * Title: execute 短信营销计划任务
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param campaignId
	 * @throws Exception
	 * @see com.chinadrtv.erp.marketing.service.CampaignExecuteService#execute(java.lang.Long)
	 */
	public void execute(Campaign campaign) throws Exception {

		CustomerGroup group = customerGroupDao.get(campaign.getAudience());
		logger.info("执行取数营销计划开始-计划id=" + campaign.getId() + "客户群"
				+ group.getGroupCode());

		StringBuffer sql = new StringBuffer(
				"select customer_id as contactid,o.contact_info as contactinfo,o.statis_info as statisinfo ,o.priority as priority,o.batch_code batchNum "
						+ "from acoapp_marketing.customer_detail o "
						+ "where o.group_code='" + group.getGroupCode() + "' ");

		StringBuffer sqlBatch = new StringBuffer("select o.batch_code "
				+ "from acoapp_marketing.customer_detail o "
				+ "where o.group_code=? ");

		Date now = new Date();
		if (campaign.getLastExecDate() != null) {
			sql.append("and o.crt_date>=to_date('"
					+ DateUtil.date2FormattedString(campaign.getLastExecDate(),
							"yyyy-MM-dd HH:mm:ss")
					+ "','yyyy-MM-dd hh24:mi:ss')");

			sqlBatch.append("and o.crt_date>=?");

		}
		sql.append("and o.crt_date<=to_date('"
				+ DateUtil.date2FormattedString(now, "yyyy-MM-dd HH:mm:ss")
				+ "','yyyy-MM-dd hh24:mi:ss')");

		sqlBatch.append("and o.crt_date<=?");
		sqlBatch.append(" group by o.batch_code ");

		// 执行跑批数据存储过程
		if (group.getExt1() == null || !group.getExt1().equals("on")) {

			logger.info("执行取数营销计划-sql=" + sql.toString());

			List<CustomerBatch> batchList = customerBatchDao.queryBatchNumList(
					sqlBatch.toString(), group.getGroupCode(),
					campaign.getLastExecDate(), now);
			logger.info("执行取数营销计划=" + campaign.getId() + ";批次列表="
					+ batchList.size());

			if (!batchList.isEmpty()) {

				String dataSource = Constants.OBCONTACT_DATASOURCE_CONTACT;
				if (group.getType() != null
						&& group.getType().equals(
								Constants.CUSTOMER_TYPE_LATENTCONTACT)) {
					dataSource = Constants.OBCONTACT_DATASOURCE_LATENTCONTACT;
				}
				for (CustomerBatch batch : batchList) {
					customerBatchDao.genBatchDetail(
							sql.toString() + " and o.batch_code='"
									+ batch.getBatchCode() + "'",
							group.getJobId(),
							group.getQueneId(),
							batch.getBatchCode(),
							dataSource,
							"root",
							group.getValidStartDate() != null ? DateUtil
									.date2FormattedString(new Date(),
											"yyyy-MM-dd") : "",
							group.getValidEndDate() != null ? DateUtil
									.date2FormattedString(DateUtil
											.addDays2Date(new Date(),
													group.getBatchStatus()),
											"yyyy-MM-dd") : "", "", group
									.getGroupName(), "", campaign.getId());

					logger.info("执行取数营销计划=" + campaign.getId() + ";批次="
							+ batch.getBatchCode());

				}
			}

			logger.info("执行取数营销计划 结束-计划id=" + campaign.getId());
		} else {
			logger.info("执行取数营销计划-客户群=" + group.getGroupCode()
					+ "只用于发送短信，不向取数表插入数据");
		}

	}

	/*
	 * (非 Javadoc) <p>Title: executeCoupon</p> <p>Description: </p>
	 * 
	 * @param map
	 * 
	 * @throws Exception
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.service.CampaignExecuteService#executeCoupon
	 * (java.util.Map)
	 */
	@Override
	public void executeCoupon(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub

	}

}
