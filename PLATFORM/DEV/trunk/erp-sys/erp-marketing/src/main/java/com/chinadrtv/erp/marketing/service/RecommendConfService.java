package com.chinadrtv.erp.marketing.service;

import java.util.Map;

import com.chinadrtv.erp.marketing.dto.RecommendConfDto;
import com.chinadrtv.erp.model.marketing.RecommendConf;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

/**
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * 推荐产品规则配置接口</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * 推荐产品规则配置接口</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午3:50:47
 * 
 */
public interface RecommendConfService {
	public Map<String, Object> queryList(DataGridModel dataModel);

	public RecommendConf getRecommendConf(RecommendConfDto recommendConfDto);

	public RecommendConf getRecommendConf(Long id);

	public void saveRecommendConf(RecommendConf obj);
}
