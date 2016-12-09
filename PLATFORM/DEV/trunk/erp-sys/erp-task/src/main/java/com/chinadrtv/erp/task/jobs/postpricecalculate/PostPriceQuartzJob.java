package com.chinadrtv.erp.task.jobs.postpricecalculate;

import java.util.ArrayList;
import java.util.List;

import org.quartz.JobExecutionContext;

import com.chinadrtv.erp.task.core.job.Task;
import com.chinadrtv.erp.task.core.scheduler.SimpleJob;
import com.chinadrtv.erp.task.entity.LogisticsFeeRuleDetail;
import com.chinadrtv.erp.task.service.PostPriceService;

/**
 * 定时任务
 * @author zhangguosheng
 */
@Task(name="PostPriceQuartzJob", description="运费计算定时任务")
public class PostPriceQuartzJob extends SimpleJob{
	
	private final String BY_WEIGHT = "WEIGHT"; //按重量
	private final String BY_PRICE = "PRICE";	//按单，按金额
	
	private final String REFUSE = "6"; //拒收
	private final String NO_REFUSE = "5"; //正常
	
	private final String UP_ROUNDING = "1"; 	//向上取整
	private final String UP_ROUNDING_NO = "1"; 	//不向上取整
	
	private final String REFUSED_INCLUDE_POSTFEE = "1"; //拒收包含邮费
	private final String REFUSED_INCLUDE_POSTFEE_NO = "0"; //拒收不包含邮费
	
    private PostPriceService postPriceService;
    
    private List<PostPriceItem> items = new ArrayList<PostPriceItem>();
    
    @Override
	public void init(JobExecutionContext context) {
    	super.init(context);
    	if(applicationContext!=null){
    		postPriceService = (PostPriceService) applicationContext.getBean(PostPriceService.class);
    	}
	}
    
    @Override
	public void doExecute(JobExecutionContext context){
    	//读取
    	read();
    	//处理
    	process();
    	//回写
    	write();
	}
    
    /**
     * 读取
     */
	public void read(){
		items.addAll(postPriceService.loadPostPriceItems());
	} 
	
	/**
	 * 处理
	 */
	public void process(){
		for (PostPriceItem item : items) {
			if(item!=null){
				LogisticsFeeRuleDetail lfrd = postPriceService.getLogisticsFeeRuleDetail(item);
				if(lfrd!=null){
					if(BY_WEIGHT.equals(lfrd.getRuleType())){
						item = processByWeigth(item,lfrd);
					}else if(BY_PRICE.equals(lfrd.getRuleType())){
						item = processByPrice(item,lfrd);
					}	
				}else{
					item.setPostFee1(0D);
					item.setPostFee2(0D);
					item.setPostFee3(0D);
				}
			}
			logger.info("处理一个订单信息：" + item.getShipmentHeadId());
		}
	}
	
	/**
	 * 回写
	 */
	public void write(){
		postPriceService.updatePostPrice(items);
		for (PostPriceItem item : items) {
			logger.info("保存一个订单信息：" + item.getShipmentHeadId());
		}
	}
	
	/**
	 * 按价格计算
	 * @param item
	 * @return
	 */
	private PostPriceItem processByPrice(PostPriceItem item, LogisticsFeeRuleDetail lfrd){
		if(item!=null && lfrd!=null){
			
			Double totlePrice = null;
			
			if(item.getTotlePrice()!=null){
				totlePrice = item.getTotlePrice();	//订单代收费用
			}
			
			Double a = new Double(0); 	//成功费用
			Double b = new Double(0);	//成功费率
			Double c = new Double(0);	//拒收费用
			Double d = new Double(0);	//拒收费率
			
			Double postFee1 = new Double(0);
			Double postFee2 = new Double(0);
			Double postFee3 = new Double(0);
			
			if(lfrd.getSucceedFeeAmount()!=null){
				a = lfrd.getSucceedFeeAmount();
			}
			
			if(lfrd.getSucceedFeeRatio()!=null){
				b = lfrd.getSucceedFeeRatio();
			}
			
			if(lfrd.getRefusedFeeAmount()!=null){
				c = lfrd.getRefusedFeeAmount();
			}
			
			if(lfrd.getRefusedFeeRatio()!=null){
				d = lfrd.getRefusedFeeRatio();
			}
			
			if( NO_REFUSE.equals(item.getAccountStatusId()) ){	//成功
				postFee2 = a;
				postFee3 = b * totlePrice * 0.01;
			}else if( REFUSE.equals(item.getAccountStatusId()) ){	//拒收
				postFee2 = c;
				postFee3 = d * totlePrice * 0.01;
			}
			item.setPostFee1(postFee1);
			item.setPostFee2(postFee2);
			item.setPostFee3(postFee3);
		}
		return item;
	}

	/**
	 * 按重量计算
	 * @param item
	 * @return
	 */
	private PostPriceItem processByWeigth(PostPriceItem item, LogisticsFeeRuleDetail lfrd){
		if(item!=null && lfrd!=null){
			
			//设置默认值
			if(lfrd.getRefusedIncludePostfee()==null){
				lfrd.setRefusedIncludePostfee(REFUSED_INCLUDE_POSTFEE_NO);
			}
			
			//设置默认值
			if(lfrd.getRoundType()==null){
				lfrd.setRoundType(UP_ROUNDING_NO);
			}
			
			Double postFee1 = new Double(0);
			Double postFee2 = new Double(0);
			Double postFee3 = new Double(0);
			
			Double a = new Double(0); 	//邮费：首重费用+【（包裹重量-首重）/续重单位】*单位续重费用
			Double b = new Double(0);	//成功服务费：固定值
			Double c = new Double(0);	//代收手续费：成功费率*代收金额
			Double d = new Double(0);	//拒收服务费：固定值
//			Double e = new Double(0);	//保价费：固定值或订单金额*保价费率
			
			//计算邮费
			Double extWeight = null; //续重
			
			if(item.getWeight()!=null && lfrd.getWeightInitial()!=null){
				
				extWeight = item.getWeight()-lfrd.getWeightInitial(); //续重
				
				if(extWeight<=0 && lfrd.getWeightInitialFee()!=null){
					a = lfrd.getWeightInitialFee();
				}else if(lfrd.getWeightInitialFee()!=null && lfrd.getWeightIncrease()!=null && lfrd.getWeightIncreaseFee()!=null && lfrd.getWeightIncrease()>0){
					if(UP_ROUNDING.equals(lfrd.getRoundType())){	//向上取整
						a = lfrd.getWeightInitialFee() + ( Math.ceil(extWeight/lfrd.getWeightIncrease()) * lfrd.getWeightIncreaseFee() );
					}else{	//不向上取整
						a = lfrd.getWeightInitialFee() + ( (extWeight/lfrd.getWeightIncrease()) * lfrd.getWeightIncreaseFee() );
					}
				}
				
				if(lfrd.getSucceedFeeAmount()!=null){
					//计算成功服务费
					b = lfrd.getSucceedFeeAmount();
				}
				
				if(lfrd.getRefusedFeeAmount()!=null){
					//计算拒收服务费
					d = lfrd.getRefusedFeeAmount();
				}
				
				if( NO_REFUSE.equals(item.getAccountStatusId()) ){
					//成功
					if(item.getTotlePrice()!=null && lfrd.getSucceedFeeRatio()!=null){
						//计算代收手续费
						c = item.getTotlePrice() * lfrd.getSucceedFeeRatio() * 0.01;
					}
					postFee1 = a;
					postFee2 = b;
					postFee3 = c;
				}else if( REFUSE.equals(item.getAccountStatusId()) ){
					//拒收
					if(item.getTotlePrice()!=null && lfrd.getRefusedFeeRatio()!=null){
						//计算代收手续费
						c = item.getTotlePrice() * lfrd.getRefusedFeeRatio() * 0.01;
					}
					if(REFUSED_INCLUDE_POSTFEE.equals(lfrd.getRefusedIncludePostfee())){
						postFee1 = a;
						postFee2 = d;
						postFee3 = c;
					}else{
						postFee1 = 0D;
						postFee2 = d;
						postFee3 = c;
					}
				}
			}
			
			item.setPostFee1(postFee1);
			item.setPostFee2(postFee2);
			item.setPostFee3(postFee3);
		}
		return item;
	}
	

}
