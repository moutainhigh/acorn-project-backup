package com.chinadrtv.erp.marketing.util;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.chinadrtv.erp.marketing.model.bi.IdlCrmPhoneRank;
import com.chinadrtv.erp.marketing.service.IdlCrmPhoneRankService;
import com.chinadrtv.erp.model.Phone;
import com.chinadrtv.erp.uc.service.PhoneService;
import com.chinadrtv.erp.util.SpringUtil;

public class SynchBIPhoneRankJobBean extends QuartzJobBean {

	private static final Logger logger = LoggerFactory.getLogger(SynchBIPhoneRankJobBean.class);
	@Override
	protected void executeInternal(JobExecutionContext jobexecutioncontext)
			throws JobExecutionException {
		long startTime = System.currentTimeMillis();
		long endTime = 0l;
		IdlCrmPhoneRankService idlCrmPhoneRankService = (IdlCrmPhoneRankService) SpringUtil
				.getBean("idlCrmPhoneRankService");
		PhoneService phoneService = (PhoneService) SpringUtil
				.getBean("phoneService");

		int startNum = 0;
		int endNum = 10000;
		Phone phone = null;
		boolean finishFlag = false;
		while(!finishFlag){
			List<IdlCrmPhoneRank> idlPhoneList = idlCrmPhoneRankService.queryListByJdbc(startNum, endNum);
			if (!idlPhoneList.isEmpty()) {
				
				try {
					for (IdlCrmPhoneRank IdlCrmPhoneRank : idlPhoneList) {
						phone = phoneService.get(Long.parseLong(IdlCrmPhoneRank.getPhoneid()));
						if(!StringUtils.isEmpty(IdlCrmPhoneRank.getPhone_rank())){
							phone.setPhoneRank(Integer.parseInt(IdlCrmPhoneRank.getPhone_rank()));
							phoneService.save(phone);
						}
						idlCrmPhoneRankService.updateSyncFlag(IdlCrmPhoneRank.getPhoneid(), IdlCrmPhoneRank.getPhone_rank(), IdlCrmPhoneRank.getLast_modify_date(), "1", "0");
						logger.info( "phoneId-------======"+ IdlCrmPhoneRank.getPhoneid());
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				finishFlag = true;
			}
		}
		

		endTime = System.currentTimeMillis();
		logger.info( "sync Phone rank use time long-------======"+ (endTime - startTime));


	}

}
