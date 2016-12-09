package com.chinadrtv.erp.marketing.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.marketing.core.dao.CustomerBatchDao;
import com.chinadrtv.erp.marketing.dao.RecommendDao;
import com.chinadrtv.erp.marketing.dto.RecommendConfDto;
import com.chinadrtv.erp.marketing.service.RecommendConfService;
import com.chinadrtv.erp.model.marketing.RecommendConf;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

/**
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * 推荐产品规则配置</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * 推荐产品规则配置</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午3:52:50
 * 
 */
@Service("recommendConfService")
public class RecommendConfServiceImpl implements RecommendConfService {

	@Autowired
	private RecommendDao recommendDao;

	@Autowired
	private CustomerBatchDao customerBatchDao;

	/**
	 * 分页查询产品配置规则
	 */
	public Map<String, Object> queryList(DataGridModel dataModel) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<RecommendConf> list = recommendDao.queryList(dataModel);
		result.put("rows", list);
		return result;
	}

	/**
	 * 根据条件查询单条配置规则，没有则返回null
	 */
	public RecommendConf getRecommendConf(RecommendConfDto recommendConfDto) {
		return recommendDao.getRecommendConf(recommendConfDto);
	}

	/**
	 * 根据条件查询单条配置规则，没有则返回null
	 */
	public RecommendConf getRecommendConf(Long id) {
		return recommendDao.get(id);
	}

	/**
	 * 保存或更新
	 */
	public void saveRecommendConf(RecommendConf object) {

		if (object.getId() == null) {
			object.setCrt_date(new Date());
			object.setUp_date(new Date());
			recommendDao.saveOrUpdate(object);
		} else {
			RecommendConf oldRecommend = recommendDao.get(object.getId());
			oldRecommend.setUp_date(new Date());
			oldRecommend.setGroupid(object.getGroupid());
			oldRecommend.setProcess_defid(object.getProcess_defid());
			oldRecommend.setProduct1(object.getProduct1());
			oldRecommend.setProduct2(object.getProduct2());
			oldRecommend.setProduct3(object.getProduct3());
			oldRecommend.setValid_start(object.getValid_start());
			oldRecommend.setValid_end(object.getValid_end());
			oldRecommend.setStatus(object.getStatus());
		}

	}
}
