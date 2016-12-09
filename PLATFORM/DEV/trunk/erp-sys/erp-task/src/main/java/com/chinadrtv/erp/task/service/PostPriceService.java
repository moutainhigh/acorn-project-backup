package com.chinadrtv.erp.task.service;

import java.util.List;

import com.chinadrtv.erp.task.core.service.BaseService;
import com.chinadrtv.erp.task.entity.LogisticsFeeRuleDetail;
import com.chinadrtv.erp.task.entity.ShipmentHeader;
import com.chinadrtv.erp.task.jobs.postpricecalculate.PostPriceItem;

public interface PostPriceService extends BaseService<ShipmentHeader, Long>{
	
	/**
	 * 获取一批需要计算的运费信息
	 * @return
	 */
	public List<PostPriceItem> loadPostPriceItems();

	/**
	 * 更新运费信息
	 * @param items
	 */
	public void updatePostPrice(List<? extends PostPriceItem> items);

	/**
	 * 查询计算规则
	 * @param shipmentHeadId
	 * @return
	 */
	public LogisticsFeeRuleDetail getLogisticsFeeRuleDetail(PostPriceItem item);
	
}
