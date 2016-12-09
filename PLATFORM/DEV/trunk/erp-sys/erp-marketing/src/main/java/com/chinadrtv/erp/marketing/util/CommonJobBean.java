package com.chinadrtv.erp.marketing.util;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.chinadrtv.erp.marketing.component.CampaignComponent;
import com.chinadrtv.erp.marketing.core.common.Constants;
import com.chinadrtv.erp.marketing.service.CampaignExecuteService;
import com.chinadrtv.erp.marketing.service.CampaignService;
import com.chinadrtv.erp.marketing.service.CustomerBatchService;
import com.chinadrtv.erp.marketing.service.SmsAnswersService;
import com.chinadrtv.erp.model.marketing.Campaign;
import com.chinadrtv.erp.smsapi.model.CampaignMonitor;
import com.chinadrtv.erp.util.SpringUtil;

public class CommonJobBean extends QuartzJobBean implements StatefulJob {

	private static final Logger logger = LoggerFactory
			.getLogger(CommonJobBean.class);

	@Override
	protected void executeInternal(JobExecutionContext jobexecutioncontext)
			throws JobExecutionException {
		Trigger trigger = jobexecutioncontext.getTrigger();
		String triggerName = trigger.getName();
		String group = trigger.getGroup();

		// 根据Trigger组别调用不同的业务逻辑方法
		if (StringUtils.equals(group, Constants.JOB_GROUP_CUSTOMER_GROUP)) {

			CustomerBatchService customerBatchService = (CustomerBatchService) SpringUtil
					.getBean("customerBatchService");

			String groupCode = jobexecutioncontext.getJobDetail().getName();
			logger.info("定时执行了job=" + groupCode);
			customerBatchService.genBatchData(groupCode);

		} else if (StringUtils.equals(group, Constants.JOB_GROUP_SMS_SEND)) {

			SmsAnswersService smsAnswersService = (SmsAnswersService) SpringUtil
					.getBean("smsAnswersService");
			// 定时执行发送短信
			String batchid = jobexecutioncontext.getJobDetail().getName();
			logger.info("定时执行了batchid=" + batchid);
			// 具体执行方法
			smsAnswersService.newSmsSend(batchid);

		} else if (StringUtils.equals(group, Constants.JOB_GROUP_CAMPAIGN)) {

			CampaignService campaignService = (CampaignService) SpringUtil
					.getBean("campaignService");

			CampaignComponent campaignComponent = (CampaignComponent) SpringUtil
					.getBean("campaignComponent");
			// 定时执行发送短信
			JobCronSet jobCronSet = (JobCronSet) jobexecutioncontext
					.getJobDetail().getJobDataMap().get("JOB_CRON_SET");
			Long campaignId = (Long) jobexecutioncontext.getJobDetail()
					.getJobDataMap().get("campaignId");
			logger.info("定时执行了营销计划id="
					+ jobexecutioncontext.getJobDetail().getJobDataMap()
							.get("campaignId"));
			// 具体执行方法
			Campaign campaign = campaignService.getCampaignById(campaignId);

			try {
				Date now = new Date();
				if (now.after(campaign.getEndDate())) {
					campaign.setStatus(Constants.CAMPAIGN_COMPLETE);
					campaignService.saveOrUpdate(campaign);
				} else if (!campaign.getStatus()
						.equals(Constants.CAMPAIGN_STOP)
						&& !campaign.getStatus().equals(
								Constants.CAMPAIGN_COMPLETE)) {
					String serviceName = campaign.getCampaignType().getTaskId();
					CampaignExecuteService campaignExecuteService = (CampaignExecuteService) SpringUtil
							.getBean(serviceName);
					// 保存 campaignMonitor
					if (campaign.getCampaignType().getId() == 1l
							|| campaign.getCampaignType().getId() == 5l) {
						CampaignMonitor campaignMonitor = campaignService
								.queryMonitorByCampaignId(campaignId);
						if (campaignMonitor == null) {
							campaignMonitor = new CampaignMonitor();
							campaignMonitor.setCreateTime(new Date());
							campaignMonitor.setCreateUser(campaign
									.getCreateUser());
							campaignMonitor.setDeparment(campaign
									.getDepartment());
							campaignMonitor.setType("2");
							campaignMonitor.setStatus("0");
							campaignMonitor.setCampaignId(campaignId);
							campaignService
									.insertCampaignMonitor(campaignMonitor);
						}
					}
					if (campaign.getCampaignType().getId() == 5l) {
						// 生成优惠券
						Map<String, Object> coupons = campaignComponent
								.newCouponCrs(campaign);
						// 调用短信接口发送优惠券
						campaignExecuteService.executeCoupon(coupons);
					} else {
						campaignExecuteService.execute(campaign);
					}

					// 设置营销计划状态
					Date nextFireTime = jobexecutioncontext.getNextFireTime();
					if (campaign.getCampaignType().getId() == 5l) {
						campaign.setStatus(Constants.CAMPAIGN_COUPON);
					} else {
						campaign.setStatus(Constants.CAMPAIGN_SCHEDULE);
					}
					if (jobCronSet.getFrequency().equals(
							Constants.SCHEDULE_TYPE_ATONCE)
							|| jobCronSet.getFrequency().equals(
									Constants.SCHEDULE_TYPE_TIMING)
							|| nextFireTime == null) {
						campaign.setStatus(Constants.CAMPAIGN_COMPLETE);
					}

					campaign.setLastExecDate(now);
					campaignService.saveOrUpdate(campaign);
				}

			} catch (Exception e) {
				logger.info("营销计划执行出错,id=" + campaignId + ",error="
						+ e.getMessage());
				e.printStackTrace();

			}

		}
	}
	// public static void main(String arg[]){
	// Date d1 = new Date();
	// try {
	// Thread.sleep(2000);
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// Date d2 = new Date();
	// System.out.println(d1.getTime());
	// System.out.println(d2.getTime());
	// System.out.println(d2.after(d1));
	// }
}
