package com.chinadrtv.erp.marketing.service;

import java.util.Map;

import com.chinadrtv.erp.marketing.dto.RecommendConfDto;
import com.chinadrtv.erp.model.marketing.UsrTaskRecommend;

/**
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * 产品推荐记录接口</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * 产品推荐记录接口</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午3:50:47
 * 
 */
public interface UsrTaskRecommendService {
	public UsrTaskRecommend get(Long id);

	public void save(UsrTaskRecommend object);

	public int updateProductId(Integer id, String productId, String usr);

	public Map<String, Object> qryRecommend(RecommendConfDto obj);

	public Map<String, Object> qryRecommend(String businessKey);

	public Map<String, Object> getUnfinishedRecommend(String user);

	public Map<String, Object> postRecommendResult(RecommendConfDto obj);
}
