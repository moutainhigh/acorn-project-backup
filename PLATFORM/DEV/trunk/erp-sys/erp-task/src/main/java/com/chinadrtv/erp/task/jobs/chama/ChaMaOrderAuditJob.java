package com.chinadrtv.erp.task.jobs.chama;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.task.core.job.Task;
import com.chinadrtv.erp.task.core.scheduler.SimpleJob;
import com.chinadrtv.erp.task.entity.PreTrade;
import com.chinadrtv.erp.task.service.PreTradeService;
import com.chinadrtv.erp.task.service.SourceConfigure;

/**
 * 茶马订单自动审核
 */
@Task(name="ChaMaOrderAuditJob", description="茶马订单自动审核任务")
public class ChaMaOrderAuditJob extends SimpleJob {

	@Autowired
	private PreTradeService preTradeService;

	@Autowired
	private AutoOperator autoOperator;

	@Autowired
	private SourceConfigure sourceConfigure;
	
    @Override
	public void init(JobExecutionContext context) {
    	super.init(context);
    	if(applicationContext!=null){
    		preTradeService = (PreTradeService) applicationContext.getBean(PreTradeService.class);
    		autoOperator = (AutoOperator) applicationContext.getBean(AutoOperator.class);
    		sourceConfigure = (SourceConfigure) applicationContext.getBean(SourceConfigure.class);
    	}
	}

	@Override
	public void doExecute(JobExecutionContext context) throws JobExecutionException {

		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("sourceId", TradeSourceConstants.CHAMA_SOURCE_ID);

		List<Object[]> list = preTradeService.getAllPreTrade(parms, 10, 0, 0, 1, Integer.valueOf(sourceConfigure.getChamsAutoApprovalCount().trim()));
		if (list != null && list.size() > 0) {
			for (Object[] obj : list) {
				if(obj.length>0){
					BigDecimal id = (BigDecimal) obj[0];
					PreTrade preTrade = preTradeService.get(id.longValue());
					// 审核
					try {
						if (sourceConfigure.getApprovalInterface() == 2) {// json
							autoOperator.singleApproval_json(preTrade);
						} else {
							autoOperator.singleApproval(preTrade);
						}
						logger.info("自动审核订单:" + preTrade.getTradeId() + "审核结果: " + preTrade.getImpStatus() + "," + preTrade.getImpStatusRemark());
					} catch (Exception e) {
						logger.info("订单" + preTrade.getTradeId() + "自动审核失败,失败原因" + e.getMessage());
					}
				}

			}
		} else {
			logger.info("Quartz的任务调度！没有审核数据 ");
		}
		logger.info("Quartz的任务调度！！！" + sourceConfigure.getApprovalInterface());
	}

}
