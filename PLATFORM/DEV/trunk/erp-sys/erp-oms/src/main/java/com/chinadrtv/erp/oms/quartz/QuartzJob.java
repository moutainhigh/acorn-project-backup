/**
 * 
 */
package com.chinadrtv.erp.oms.quartz;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.constant.TradeSourceConstants;
import com.chinadrtv.erp.model.PreTrade;
import com.chinadrtv.erp.oms.controller.OrderPreprocessingController;
import com.chinadrtv.erp.oms.service.PreTradeService;
import com.chinadrtv.erp.oms.service.SourceConfigure;

/**
 *   quartz-JOB
 *   
 * @author haoleitao
 * @date 2013-1-16 下午4:42:29
 *
 */

public class QuartzJob {
	private static final Logger log = LoggerFactory.getLogger(QuartzJob.class);
    @Autowired
    private PreTradeService preTradeService;
    @Autowired
    private AutoOperator autoOperator;
    
	@Autowired
	private SourceConfigure sourceConfigure;
	
	/**
	 * 
	 *  茶马订单自动审核
	 * 
	 * @author haoleitao
	 * @date 2013-1-31 下午1:53:23
	 */
	 public void work()  
	  {
          log.info("begin job work");
		 List<PreTrade> list= preTradeService.getAllPreTrade(null,TradeSourceConstants.CHAMA_SOURCE_ID, null, null, null, null, null, null,null, null, null, 10, 0,0,null, null, 0, Integer.valueOf(sourceConfigure.getChamsAutoApprovalCount().trim()));
		 if(list != null && list.size()>0){
			 for(PreTrade preTrade:list){
                 log.info("begin add pre trade:"+preTrade.getTradeId());
				 //审核
				 try{
					if(sourceConfigure.getApprovalInterface() ==2){//json
						autoOperator.singleApproval_json(preTrade);
					}else{
					  autoOperator.singleApproval(preTrade);
					}
					 log.info("自动审核订单:"+preTrade.getTradeId()+ "审核结果: "+preTrade.getImpStatus()+","+ preTrade.getImpStatusRemark());
				 }catch(Exception e){
					 log.error("订单"+preTrade.getTradeId()+"自动审核失败,失败原因"+e.getMessage(),e);
				 }
                 log.info("end add pre trade:"+preTrade.getTradeId());
			 }
		 }else{
			 log.info("Quartz的任务调度！没有审核数据 ");	
		 }
		 log.info("Quartz的任务调度！！！"+sourceConfigure.getApprovalInterface());	      
	 }  
}
