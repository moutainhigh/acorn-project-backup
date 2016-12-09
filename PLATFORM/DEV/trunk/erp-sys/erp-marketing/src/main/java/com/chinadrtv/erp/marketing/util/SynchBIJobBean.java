package com.chinadrtv.erp.marketing.util;

import java.util.Date;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.chinadrtv.erp.marketing.model.bi.IdlCrmUsrFlag;
import com.chinadrtv.erp.marketing.service.CrmUsrFlagService;
import com.chinadrtv.erp.marketing.service.IdlCrmUsrFlagService;
import com.chinadrtv.erp.model.marketing.CrmUsrFlag;
import com.chinadrtv.erp.util.DateUtil;
import com.chinadrtv.erp.util.SpringUtil;

public class SynchBIJobBean extends QuartzJobBean {

	// private CustomerBatchService customerBatchService;
	//
	// /**
	// * @param customerBatchService the customerBatchService to set
	// */
	// public void setCustomerBatchService(CustomerBatchService
	// customerBatchService) {
	// this.customerBatchService = customerBatchService;
	// }

	@Override
	protected void executeInternal(JobExecutionContext jobexecutioncontext)
			throws JobExecutionException {
		// Trigger trigger = jobexecutioncontext.getTrigger();
		// String triggerName = trigger.getName();
		// String group = trigger.getGroup();
		//
		// CustomerBatchService customerBatchService =
		// (CustomerBatchService)SpringUtil.getBean("customerBatchService");
		// // 根据Trigger组别调用不同的业务逻辑方法
		// if (StringUtils.equals(group, Constants.JOB_GROUP_CUSTOMER_GROUP)) {
		// String groupCode = jobexecutioncontext.getJobDetail().getName();
		// System.out.println("定时执行了job="+groupCode);
		// customerBatchService.genBatchData(groupCode);
		// }
		long startTime = System.currentTimeMillis();
		long endTime = 0l;
		IdlCrmUsrFlagService idlCrmUsrFlagService = (IdlCrmUsrFlagService) SpringUtil
				.getBean("idlCrmUsrFlagService");
		CrmUsrFlagService crmUsrFlagService = (CrmUsrFlagService) SpringUtil
				.getBean("crmUsrFlagService");

		int days = -8;
		Date now = new Date();
		String scope = null;
		int pageCount = 10000;
		int totalCount = 0;
		int pages = 0;
		int page = 0;
		boolean syncFlag = true;
		int startNm = 0;
		for (; days <= 0; days++) {
			scope = DateUtil.addDays(now, days);
			syncFlag = false;
			totalCount = idlCrmUsrFlagService.queryCount(scope);
			pages = getPages(totalCount, pageCount);
			String endScope = DateUtil.dateToString(now);
			int count = 1;
			page = 1;
			while (page <= pages) {
				startNm = pageCount * (page - 1);
				List<IdlCrmUsrFlag> idlUsrList = idlCrmUsrFlagService
						.queryListByJdbc(scope, startNm, startNm + pageCount);
				if (!idlUsrList.isEmpty()) {
					CrmUsrFlag crmUsrFlag = null;
					try {
						for (IdlCrmUsrFlag idlUsrFlag : idlUsrList) {
							crmUsrFlag = new CrmUsrFlag();
							copyProperties(crmUsrFlag, idlUsrFlag);

							crmUsrFlagService.saveOrUpdate(crmUsrFlag);

							count++;
							syncFlag = true;
						}
						System.out.println(count);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				page++;
			}

			if (syncFlag)
				idlCrmUsrFlagService.updateSyncFlag(scope, "1", "0");

			endTime = System.currentTimeMillis();

			System.out.println(scope + "use time long-------======"
					+ (endTime - startTime));

		}

	}

	private void copyProperties(CrmUsrFlag dest, IdlCrmUsrFlag orig) {
		dest.setAcc_2011_paid_clctn_qty(orig.getAcc_2011_paid_clctn_qty());
		dest.setAcc_2011_paid_dgtl_qty(orig.getAcc_2011_paid_dgtl_qty());
		dest.setAcc_2011_paid_mobile_qty(orig.getAcc_2011_paid_mobile_qty());
		dest.setAcc_2011_paid_sport_qty(orig.getAcc_2011_paid_sport_qty());
		dest.setAcc_2011_paid_watch_qty(orig.getAcc_2011_paid_watch_qty());
		dest.setAcc_paid_clctn_qty(orig.getAcc_paid_clctn_qty());
		dest.setAcc_paid_dgtl_qty(orig.getAcc_paid_dgtl_qty());
		dest.setAcc_paid_mobile_qty(orig.getAcc_paid_mobile_qty());
		dest.setAcc_paid_ordr_amt(orig.getAcc_paid_ordr_amt());
		dest.setAcc_paid_ordr_qty(orig.getAcc_paid_ordr_qty());
		dest.setAcc_paid_sport_qty(orig.getAcc_paid_sport_qty());
		dest.setAcc_paid_wathc_qty(orig.getAcc_paid_wathc_qty());
		dest.setArea(orig.getArea());
		dest.setBirthday(orig.getBirthday());
		dest.setCity(orig.getCity());
		dest.setCity_lev(orig.getCity_lev());
		dest.setContact_lev(orig.getContact_lev());
		dest.setContactid(orig.getContactid());
		dest.setData_date(orig.getData_date());
		dest.setCounty(orig.getCounty());
		dest.setFst_paid_media_co(orig.getFst_paid_media_co());
		dest.setFst_paid_media_prod(orig.getFst_paid_media_prod());
		dest.setFst_paid_ordr_amt(orig.getFst_paid_ordr_amt());
		dest.setFst_paid_ordr_date(orig.getFst_paid_ordr_date());
		dest.setFst_paid_prod(orig.getFst_paid_prod());
		dest.setFst_paid_prod_cat(orig.getFst_paid_prod_cat());
		dest.setGender(orig.getGender());
		dest.setId_num(orig.getId_num());
		dest.setImport_date(new Date());
		dest.setIntst_cat(orig.getIntst_cat());
		dest.setIs_bind_old_custm(orig.getIs_bind_old_custm());
		dest.setLst_callout_date(orig.getLst_callout_date());
		dest.setLst_inb_paid_clctn_date(orig.getLst_inb_paid_clctn_date());
		dest.setLst_inb_paid_dgtl_date(orig.getLst_inb_paid_dgtl_date());
		dest.setLst_inb_paid_mobile_date(orig.getLst_inb_paid_mobile_date());
		dest.setLst_inb_paid_sport_date(orig.getLst_inb_paid_sport_date());
		dest.setLst_inb_paid_watch_date(orig.getLst_inb_paid_watch_date());
		dest.setLst_ordr_date(orig.getLst_ordr_date());
		dest.setLst_paid_media_co(orig.getLst_paid_media_co());
		dest.setLst_paid_media_prod(orig.getLst_paid_media_prod());
		dest.setLst_paid_ordr_amt(orig.getLst_paid_ordr_amt());
		dest.setLst_paid_ordr_date(orig.getLst_paid_ordr_date());
		dest.setLst_paid_prod(orig.getLst_paid_prod());
		dest.setLst_paid_prod_cat(orig.getLst_paid_prod_cat());
		dest.setMbr_type(orig.getMbr_type());
		dest.setNames(orig.getNames());
		dest.setProvince(orig.getProvince());
		dest.setSync_flag(orig.getSync_flag());

	}

	private int getPages(int count, int pageSize) {
		int pages = 0;
		if (count > 0) {
			if (pageSize != 0) {
				pages = count / pageSize;
				if (count % pageSize != 0) {
					pages++;
				}
			}
		}

		return pages;
	}

}
