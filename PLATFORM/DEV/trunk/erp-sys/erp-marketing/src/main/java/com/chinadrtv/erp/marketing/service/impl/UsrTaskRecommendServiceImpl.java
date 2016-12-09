package com.chinadrtv.erp.marketing.service.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.bpm.service.BpmProcessService;
import com.chinadrtv.erp.core.service.ProductService;
import com.chinadrtv.erp.marketing.core.dao.CustomerBatchDao;
import com.chinadrtv.erp.marketing.core.dao.ObContactDao;
import com.chinadrtv.erp.marketing.core.dao.UsrTaskRecommendDao;
import com.chinadrtv.erp.marketing.dao.RecommendDao;
import com.chinadrtv.erp.marketing.dto.ProductObject;
import com.chinadrtv.erp.marketing.dto.RecommendConfDto;
import com.chinadrtv.erp.marketing.dto.UnfinishedRecommend;
import com.chinadrtv.erp.marketing.service.UsrTaskRecommendService;
import com.chinadrtv.erp.model.agent.ObContact;
import com.chinadrtv.erp.model.agent.Product;
import com.chinadrtv.erp.model.marketing.CustomerBatch;
import com.chinadrtv.erp.model.marketing.RecommendConf;
import com.chinadrtv.erp.model.marketing.UsrTaskRecommend;
import com.chinadrtv.erp.util.DateUtil;

/**
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * 推荐产品规则记录</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * 推荐产品规则记录</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午3:52:50
 * 
 */
@Service("usrTaskRecommendService")
public class UsrTaskRecommendServiceImpl implements UsrTaskRecommendService {

	@Autowired
	private UsrTaskRecommendDao usrTaskRecommendDao;

	@Autowired
	private RecommendDao recommendDao;

	@Autowired
	private BpmProcessService bpmProcessService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ObContactDao obContactDao;

	@Autowired
	private CustomerBatchDao customerBatchDao;

	@Value("${task_valid_days}")
	private int valid_days;

	/**
	 * 根据id获取对象
	 */
	public UsrTaskRecommend get(Long id) {
		return usrTaskRecommendDao.get(id);
	}

	/**
	 * 保存对象
	 */
	public void save(UsrTaskRecommend object) {
		usrTaskRecommendDao.saveOrUpdate(object);
	}

	/**
	 * 更新实际推荐产品
	 */
	public int updateProductId(Integer id, String productId, String usr) {
		return usrTaskRecommendDao.updateProductId(id, productId, usr);
	}

	/**
	 * 根据条件获取推荐给用户的铲平 并且启动任务
	 */
	public Map<String, Object> qryRecommend(RecommendConfDto obj) {

		Map<String, Object> result = new HashMap<String, Object>();

		ObContact obcontact = obContactDao.get(obj.getPdCustomerId());

		CustomerBatch customerBatch = customerBatchDao.get(obcontact
				.getBatchid());

		RecommendConf recommend = recommendDao
				.getRecommendConf(customerBatch != null ? customerBatch
						.getGroupCode() : "");
		if (recommend != null) {
			// 生成推荐记录
			UsrTaskRecommend usrTaskRecommend = new UsrTaskRecommend();

			usrTaskRecommend.setCrt_date(new Date());
			usrTaskRecommend.setUp_date(new Date());
			usrTaskRecommend.setGroupid(customerBatch.getGroupCode());
			usrTaskRecommend.setProcess_defid(recommend.getProcess_defid());
			usrTaskRecommend.setRecommend_prod1(recommend.getProduct1());
			usrTaskRecommend.setRecommend_prod2(recommend.getProduct2());
			usrTaskRecommend.setRecommend_prod3(recommend.getProduct3());
			usrTaskRecommend.setUser_id(obj.getUserId());
			usrTaskRecommend.setValid_date(DateUtil.addDays2Date(new Date(),
					valid_days));
			usrTaskRecommend.setIs_finished("N");

			usrTaskRecommend.setContactid(obcontact.getContactid());
			usrTaskRecommend.setPd_customer_id(obj.getPdCustomerId());
			usrTaskRecommendDao.saveOrUpdate(usrTaskRecommend);

			Map<String, Object> var = new HashMap<String, Object>();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			var.put("jieshu",
					sf.format(DateUtil.addDays2Date(new Date(), valid_days)));
			// var.put("jieshu", "2013-03-26T15:18:00");
			// 启动task
			String processInsId = bpmProcessService.startProcessInstance(
					recommend.getProcess_defid(), usrTaskRecommend.getId()
							.toString(), obj.getUserId(), var);
			usrTaskRecommend.setProcess_insid(processInsId);

			// 查询产品信息
			Product p1 = productService.query(recommend.getProduct1());
			Product p2 = productService.query(recommend.getProduct2());
			Product p3 = productService.query(recommend.getProduct3());

			ProductObject proObj1 = new ProductObject();
			proObj1.setProductId(recommend.getProduct1());
			proObj1.setProductName(p1.getProdname());

			ProductObject proObj2 = new ProductObject();
			proObj2.setProductId(recommend.getProduct2());
			proObj2.setProductName(p2.getProdname());

			ProductObject proObj3 = new ProductObject();
			proObj3.setProductId(recommend.getProduct3());
			proObj3.setProductName(p3.getProdname());

			List<ProductObject> products = new ArrayList<ProductObject>();
			products.add(proObj1);
			products.add(proObj2);
			products.add(proObj3);

			result.put("code", "000");

			Map<String, Object> value = new HashMap<String, Object>();

			try {
				value.put("validDate",
						DateUtil.date2FormattedString(
								usrTaskRecommend.getValid_date(),
								"yyyy-MM-dd HH:mm:ss"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			value.put("businessKey", usrTaskRecommend.getId());
			result.put("value", value);
			result.put("resultdt", products);

		} else {
			result.put("code", "100");
			result.put("desc", "没有找到推荐产品");
		}
		return result;
	}

	/**
	 * <p>
	 * Title: postRecommendResult
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param obj
	 * @return
	 * @see com.chinadrtv.erp.marketing.service.UsrTaskRecommendService#postRecommendResult(com.chinadrtv.erp.marketing.dto.RecommendConfDto)
	 */
	public Map<String, Object> postRecommendResult(RecommendConfDto obj) {
		Map<String, Object> result = new HashMap<String, Object>();
		UsrTaskRecommend usrTaskRecommend = usrTaskRecommendDao.get(Long
				.valueOf(obj.getBusinessKey()));

		if (usrTaskRecommend != null) {
			// usrTaskRecommend.setProuduct_id(obj.getProductId());
			if (obj.getProductIds() != null) {
				String productId = null;
				for (ProductObject p : obj.getProductIds()) {
					productId = p.getProductId();
					if (productId.equals(usrTaskRecommend.getRecommend_prod1())) {
						usrTaskRecommend.setProuduct_id1(productId);
					} else if (productId.equals(usrTaskRecommend
							.getRecommend_prod2())) {
						usrTaskRecommend.setProuduct_id2(productId);
					} else if (productId.equals(usrTaskRecommend
							.getRecommend_prod3())) {
						usrTaskRecommend.setProuduct_id3(productId);
					}
				}
			}
			usrTaskRecommend.setUp_date(new Date());
			usrTaskRecommend.setUp_user(obj.getUserId());
			if (!StringUtils.isEmpty(obj.getIsFinish())
					&& obj.getIsFinish().equals("Y")) {
				usrTaskRecommend.setIs_finished("Y");
				bpmProcessService.completeByProcessInsId(
						usrTaskRecommend.getProcess_defid(),
						usrTaskRecommend.getProcess_insid(),
						usrTaskRecommend.getUser_id());
			}

			result.put("code", "000");
			result.put("desc", "");
		} else {
			result.put("code", "100");
			result.put("desc", "没有找到推荐产品记录");
		}
		return result;
	}

	/**
	 * <p>
	 * Title: getUnfinishedRecommend获取未完成的任务
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param user
	 * @return
	 * @see com.chinadrtv.erp.marketing.service.UsrTaskRecommendService#getUnfinishedRecommend(java.lang.String)
	 */
	public Map<String, Object> getUnfinishedRecommend(String user) {
		Map<String, Object> map = new HashMap<String, Object>();

		Map<String, String> taskList = bpmProcessService.queryTaskAssignee(
				null, user);
		Iterator<String> it = taskList.keySet().iterator();
		UsrTaskRecommend usrTaskRecommend = null;
		String taskName = "";
		String businessKey = "";
		UnfinishedRecommend unfinishedRecommend = null;
		List<UnfinishedRecommend> result = new ArrayList<UnfinishedRecommend>();
		// List<ProductObject> products = null;
		while (it.hasNext()) {
			businessKey = it.next();
			usrTaskRecommend = usrTaskRecommendDao.get(Long
					.valueOf(businessKey));
			if (usrTaskRecommend != null) {
				taskName = taskList.get(businessKey);
				unfinishedRecommend = new UnfinishedRecommend();
				unfinishedRecommend.setTaskName(taskName);
				unfinishedRecommend.setBusinessKey(businessKey);
				unfinishedRecommend.setContactid(usrTaskRecommend.getUser_id());
				unfinishedRecommend.setCreateDate(usrTaskRecommend
						.getCrt_date());
				unfinishedRecommend.setLastUpdateDate(usrTaskRecommend
						.getUp_date());
				unfinishedRecommend.setValidDate(usrTaskRecommend
						.getValid_date());
				// 查询产品信息
				Product p1 = productService.query(usrTaskRecommend
						.getRecommend_prod1());
				Product p2 = productService.query(usrTaskRecommend
						.getRecommend_prod2());
				Product p3 = productService.query(usrTaskRecommend
						.getRecommend_prod3());

				unfinishedRecommend.setProductId1(usrTaskRecommend
						.getRecommend_prod1());
				unfinishedRecommend.setProductName1(p1.getProdname());
				if (!StringUtils.isEmpty(usrTaskRecommend.getProuduct_id1())) {
					unfinishedRecommend.setIsRecommended1("Y");
				}

				unfinishedRecommend.setProductId2(usrTaskRecommend
						.getRecommend_prod2());
				unfinishedRecommend.setProductName2(p2.getProdname());
				if (!StringUtils.isEmpty(usrTaskRecommend.getProuduct_id2())) {
					unfinishedRecommend.setIsRecommended2("Y");
				}

				unfinishedRecommend.setProductId3(usrTaskRecommend
						.getRecommend_prod3());
				unfinishedRecommend.setProductName3(p3.getProdname());
				if (!StringUtils.isEmpty(usrTaskRecommend.getProuduct_id3())) {
					unfinishedRecommend.setIsRecommended3("Y");
				}

				result.add(unfinishedRecommend);

			}

			map.put("code", "000");
			map.put("resultdt", result);
		}
		return map;
	}

	/**
	 * 根据条件获未完成的推荐任务详情
	 * 
	 */
	public Map<String, Object> qryRecommend(String businessKey) {

		Map<String, Object> result = new HashMap<String, Object>();
		UsrTaskRecommend recommend = usrTaskRecommendDao.get(Long
				.valueOf(businessKey));
		if (recommend != null) {

			// 查询产品信息
			Product p1 = productService.query(recommend.getRecommend_prod1());
			Product p2 = productService.query(recommend.getRecommend_prod2());
			Product p3 = productService.query(recommend.getRecommend_prod3());

			ProductObject proObj1 = new ProductObject();
			proObj1.setProductId(recommend.getRecommend_prod1());
			proObj1.setProductName(p1.getProdname());

			ProductObject proObj2 = new ProductObject();
			proObj2.setProductId(recommend.getRecommend_prod2());
			proObj2.setProductName(p2.getProdname());

			ProductObject proObj3 = new ProductObject();
			proObj3.setProductId(recommend.getRecommend_prod3());
			proObj3.setProductName(p3.getProdname());

			result.put("status", "200");
			result.put("product1", proObj1);
			result.put("product2", proObj2);
			result.put("product3", proObj3);
			result.put("businessKey", businessKey);
		} else {
			result.put("status", "-1");
			result.put("errorCode", "-1");
			result.put("errorMsg", "没有找到推荐产品");
		}
		return result;
	}

}
