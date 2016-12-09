package com.chinadrtv.erp.marketing.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.marketing.core.common.Constants;
import com.chinadrtv.erp.marketing.core.dao.CustomerBatchDao;
import com.chinadrtv.erp.marketing.dao.CustomerBatchDetailDao;
import com.chinadrtv.erp.marketing.dao.CustomerGroupDao;
import com.chinadrtv.erp.marketing.dao.CustomerRecoveryDao;
import com.chinadrtv.erp.marketing.dao.CustomerSQLSourceDao;
import com.chinadrtv.erp.marketing.dto.CustomerBatchDto;
import com.chinadrtv.erp.marketing.service.CustomerBatchService;
import com.chinadrtv.erp.marketing.service.CustomerMonitorService;
import com.chinadrtv.erp.marketing.service.SchedulerService;
import com.chinadrtv.erp.model.marketing.CustomerBatch;
import com.chinadrtv.erp.model.marketing.CustomerBatchDetail;
import com.chinadrtv.erp.model.marketing.CustomerGroup;
import com.chinadrtv.erp.model.marketing.CustomerMonitor;
import com.chinadrtv.erp.model.marketing.CustomerSqlSource;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.util.DateTimeUtil;
import com.chinadrtv.erp.util.DateUtil;

/**
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * 客户群管理--服务类</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * 客户群管理-sql数据源-服务类</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午3:52:50
 * 
 */
@Service("customerBatchService")
public class CustomerBatchServiceImpl implements Serializable,
		CustomerBatchService {

	private static final Logger logger = LoggerFactory
			.getLogger(CustomerBatchServiceImpl.class);
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private CustomerBatchDao customerBatchDao;

	@Autowired
	private CustomerGroupDao customerGroupDao;

	@Autowired
	private CustomerSQLSourceDao customerSQLSourceDao;

	@Autowired
	private CustomerBatchDetailDao customerBatchDetailDao;
	@Autowired
	private CustomerRecoveryDao customerRecoveryDao;
	@Autowired
	private SchedulerService schedulerService;
	@Autowired
	private CustomerMonitorService customerMonitorService;

	// @Autowired
	// private AmqJmxUtil amqJmxUtil;

	// @Value("${marketing_dbname}")
	// private String marketingDbName;

	/**
	 * 查询历史批次
	 */
	public Map<String, Object> queryBatch(String groupCode,
			DataGridModel dataModel) {

		Map<String, Object> result = new HashMap<String, Object>();
		List<CustomerBatch> list = customerBatchDao.query(groupCode, dataModel);
		Integer total = customerBatchDao.queryCount(groupCode);
		Object[] count = customerBatchDao.queryFooter(groupCode);
		Map<String, String> footer = new HashMap<String, String>();
		Map<String, Object> datacount = new HashMap<String, Object>();
		if (count != null) {
			footer.put("type", "合计");
			// footer.put("batchCode", count[0] != null ? count[0].toString()
			// : "0");
			// footer.put("genDate", "累积量");
			footer.put("count", count[1] != null ? count[1].toString() : "0");
		}
		List<Map<String, String>> footerList = new ArrayList<Map<String, String>>();
		// cuiming 修改
		List<CustomerBatchDto> recoveryList = new ArrayList<CustomerBatchDto>();
		// 跟换批次号查找回收数据量
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				CustomerBatchDto customerBatch = new CustomerBatchDto();
				customerBatch.setAvailable(list.get(i).getAvailable());
				customerBatch.setBatchCode(list.get(i).getBatchCode());
				customerBatch.setCount(list.get(i).getCount());
				customerBatch.setGenDate(DateTimeUtil.sim3.format(list.get(i)
						.getGenDate()));
				customerBatch.setGroupCode(groupCode);
				// 未使用的
				customerBatch.setUnusedCount(customerRecoveryDao
						.queryObContactUnuse(list.get(i).getBatchCode()));
				// 已使用的
				customerBatch.setUsedCount(customerRecoveryDao
						.queryObContactUsed(list.get(i).getBatchCode()));
				if (customerRecoveryDao.getCustomerRecovery(list.get(i)
						.getBatchCode()) != null
						&& !customerRecoveryDao.getCustomerRecovery(
								list.get(i).getBatchCode()).isEmpty()) {
					customerBatch.setRecoveryCount(Long
							.valueOf(customerRecoveryDao.getCustomerRecovery(
									list.get(i).getBatchCode()).size()));
				} else {
					customerBatch.setRecoveryCount(0L);
				}
				customerBatch.setType(list.get(i).getType());
				recoveryList.add(customerBatch);
			}
			datacount = showData(groupCode);
			footer.put("usedCount", datacount.get("usedCount").toString());
			footer.put("unusedCount", datacount.get("unusedCount").toString());
			footer.put("recoveryCount", datacount.get("totalRecoveryCount")
					.toString());
			footerList.add(footer);
		}
		result.put("rows", recoveryList);
		result.put("total", total);
		result.put("footer", footerList);
		return result;
	}

	public Map<String, Object> showData(String groupCode) {
		// 查出所有的数据
		List<CustomerBatch> cuslist = customerBatchDao
				.queryGroupList(groupCode);
		StringBuffer param = new StringBuffer();
		Long usedCount = 0l;
		Long unusedCount = 0l;
		Long recoveryCount = 0l;
		Long totalRecoveryCount = 0l;
		String temp = "";
		Map<String, Object> maps = new HashMap<String, Object>();
		for (int i = 0; i < cuslist.size(); i++) {
			param.append("'" + cuslist.get(i).getBatchCode() + "'" + ",");
			recoveryCount = Long.valueOf(customerRecoveryDao
					.getCustomerRecovery(cuslist.get(i).getBatchCode()).size());
			totalRecoveryCount = totalRecoveryCount + recoveryCount;
		}
		if (param.length() > 0) {
			temp = param.toString().substring(0,
					param.toString().lastIndexOf(","));
			unusedCount = customerRecoveryDao
					.queryTotalObContactUsed(temp, "1");
			usedCount = customerRecoveryDao.queryTotalObContactUsed(temp, "2");
		} else {
			unusedCount = 0l;
			usedCount = 0l;
		}
		maps.put("usedCount", usedCount);
		maps.put("unusedCount", unusedCount);
		maps.put("totalRecoveryCount", totalRecoveryCount);
		return maps;
	}

	/**
	 * 生成批次数据
	 * 
	 * @Description: TODO
	 * @param groupCode
	 * @return void
	 * @throws
	 */
	public void genBatchData(String groupCode) {

		CustomerGroup group = customerGroupDao.get(groupCode);
		// 记录开始时间
		Long startTimes = System.currentTimeMillis();// startDate.getTime();
		/*
		 * 客户群存在并且状态可用
		 */if (group != null && group.getStatus().equals("Y")) {
			CustomerSqlSource sqlSource = group.getSqlSource();
			// 创建监控
			CustomerMonitor customerMonitor = new CustomerMonitor();

			Long monitorSeq = customerMonitorService.getSeq();
			/*
			 * x 当前存在sql数据源
			 */
			String batchNum = genBatchNum();

			logger.info("客户群" + group.getGroupCode() + "生成批次号" + batchNum);
			String sql = "select customer_id as contactid,o.contact_info as contactinfo,o.statis_info as statisinfo ,o.priority as priority "
					+ "from acoapp_marketing.customer_detail o "
					+ "where o.batch_code='" + batchNum + "'";
			Long batchCount = 0l;

			// 保存批次信息
			logger.info("客户群=" + group.getGroupCode() + ";批次号=" + batchNum
					+ ";保存批次信息");
			CustomerBatch batch = new CustomerBatch();
			batch.setBatchCode(batchNum);
			batch.setGroupCode(groupCode);
			batch.setGenDate(new Date());
			batch.setType("1");
			batch.setCount(batchCount);
			batch.setAvailable(batchCount);

			customerBatchDao.save(batch);
			// 设置监控信息
			customerMonitor.setId(monitorSeq);
			customerMonitor.setBatchCode(batchNum);
			customerMonitor.setGroupCode(groupCode);
			customerMonitor.setGroupName(group.getGroupName());
			customerMonitor.setSqlContent(group.getSqlSource().getSqlContent());
			// 1为初始状态
			customerMonitor.setStatus("1");
			// 保存初始状态
			customerMonitorService.saves(customerMonitor);
			if (sqlSource != null) {
				// 执行跑批数据存储过程
				try {
					/*
					 * 生成批次之前，先将当前obContact表中未领用的数据删除
					 */
					if (!StringUtils.isEmpty(group.getIsRecover())
							&& group.getIsRecover().equals("1")) {
						logger.info("客户群=" + group.getGroupCode() + ";批次号="
								+ batchNum + ";记录回收数据");
						customerRecoveryDao.insertRecoverCount(group
								.getGroupCode());
						logger.info("客户群=" + group.getGroupCode() + ";批次号="
								+ batchNum + ";执行回收obContact未使用数据");
						customerBatchDetailDao.deleteCustomerDetail(groupCode);
						customerBatchDetailDao.deleteObContact(groupCode);
						customerMonitor.setCustomerRecovery("1");
						// 状态2为回收数据
						customerMonitor.setStatus("2");
						// 更新监控状态
						customerMonitorService.updates(customerMonitor);
						logger.info("客户群=" + group.getGroupCode() + ";批次号="
								+ batchNum + ";执行回收清空远程AMQ中的消息");
						// amqJmxUtil.removeQueue(group.getJobId());
						logger.info("执行回收清空远程AMQ中的消息_success");
					}

					logger.info("客户群=" + group.getGroupCode() + ";批次号="
							+ batchNum + ";生成批次数据");
					/*
					 * 保存
					 */
					customerBatchDetailDao.saveBatchDetail(
							sqlSource.getSqlContent(), batchNum, group);
					// 保存数据到customerBatchDetai
					customerMonitor.setStatus("3");
					customerMonitorService.updates(customerMonitor);

					// 查出总数 备库查询 备份 cuiming add by 12月 25日
					// Integer count = customerBatchDetailDao
					// .getSqlCount(sqlSource.getSqlContent());
					// Integer begin = 0;
					// Integer end = 0;
					// Integer temps = 20000;
					// // 执行次数
					// Integer x = count / temps;
					// // 余数
					// Integer y = count % temps;
					// Date date = new Date();
					// if (x > 1) {
					// for (int i = 0; i < x; i++) {
					// end = temps * (i + 1);
					// if ((i + 1) == x) {
					// end = end + y;
					// }
					// savesCustomerDetail(sqlSource.getSqlContent(),
					// group, batchNum, begin, end, date);
					// begin = end;
					// }
					// } else {
					// end = count;
					// savesCustomerDetail(sqlSource.getSqlContent(), group,
					// batchNum, begin, end, date);
					// }
					logger.info("客户群=" + group.getGroupCode() + ";批次号="
							+ batchNum + ";剔除重复数据");
					/*
					 * 删除重复数据
					 */
					customerBatchDetailDao.deleteRepeatDetail(batchNum,
							groupCode, group.getRejectCycle() == null ? 0
									: group.getRejectCycle());
					// 删除重复数据
					customerMonitor.setStatus("4");
					customerMonitorService.updates(customerMonitor);
					logger.info("客户群=" + group.getGroupCode() + ";批次号="
							+ batchNum + ";统计批次数量");
					/*
					 * 统计数量
					 */
					batchCount = customerSQLSourceDao.querySQLCount(sql);

					/*
					 * 更新批次数据分配优先级 如果是潜客，则不执行该逻辑
					 */
					if (group.getType() != null
							&& !group.getType().equals(
									Constants.CUSTOMER_TYPE_LATENTCONTACT)) {

						if (group.getAssignType() != null) {
							String orderCondition = getOrderCondition(group
									.getAssignType());
							if (batchCount > 0
									&& !StringUtils.isEmpty(orderCondition)) {
								logger.info("客户群=" + group.getGroupCode()
										+ ";批次号=" + batchNum + ";更新批次数据分配优先级");
								customerBatchDetailDao.insetDetailTemp(
										batchNum, orderCondition);
								customerBatchDetailDao.updateDetailPriority(
										batchNum, orderCondition);
							}
						}
					}
					// 更新批次数据分配优先级

					customerBatchDao.update(batchNum, batchCount, batchCount);

					logger.info("客户群=" + group.getGroupCode() + ";批次号="
							+ batchNum + ";调用存储过程");
					// 执行跑批数据存储过程
					// if (group.getExt1() == null
					// || !group.getExt1().equals("on")) {
					// customerBatchDao
					// .genBatchDetail(
					// sql,
					// group.getJobId(),
					// group.getQueneId(),
					// batchNum,
					// "CONTACT",
					// "root",
					// group.getValidStartDate() != null ? DateUtil
					// .date2FormattedString(
					// new Date(),
					// "yyyy-MM-dd") : "",
					// group.getValidEndDate() != null ?
					// DateUtil.date2FormattedString(
					// DateUtil.addDays2Date(
					// new Date(),
					// group.getBatchStatus()),
					// "yyyy-MM-dd")
					// : "", "", group.getGroupName(),
					// "");
					//
					// }
					Long endTimes = System.currentTimeMillis();
					Long costTimes = endTimes - startTimes;
					Date d = new Date(costTimes);
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
							"mm分:ss秒");
					customerMonitor.setStatus("5");
					customerMonitor.setSqlCount("" + batchCount);
					customerMonitor.setCostTimes(simpleDateFormat.format(d));
					customerMonitorService.updates(customerMonitor);
				} catch (Exception e) {
					e.printStackTrace();
					customerMonitor.setStatus("6");
					customerMonitor.setException(e.toString());
					customerMonitorService.updates(customerMonitor);
					logger.info("执行错误:客户群=" + group.getGroupCode() + ";批次号="
							+ batchNum + ";统计批次数量" + e.toString());
				}

			}
		} else {
			// 客户群停用时 销毁定时任务
			if (group.getStatus().equals("N")) {
				try {
					schedulerService.deleteJob(groupCode,
							Constants.JOB_GROUP_CUSTOMER_GROUP);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("销毁定时任务执行错误:客户群=" + group.getGroupCode()
							+ ";批次号=" + group.getCurrBatchNum() + ";统计批次数量"
							+ e.toString());
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * @Description: 保存到customerDetail
	 * @param batchId
	 * @param group
	 * @param batchid
	 * @return void
	 * @throws
	 */
	public Integer savesCustomerDetail(String sql, CustomerGroup group,
			String batchid, Integer begin, Integer end, Date date) {
		List<Map<String, Object>> list = customerBatchDetailDao.getSqlResult(
				sql, begin, end);
		return customerBatchDetailDao.saveBatchDetials(batchid, group, list,
				date);
	}

	/**
	 * 获取批号
	 */
	public String genBatchNum() {
		StringBuffer batchNum = new StringBuffer();

		try {
			batchNum.append(DateUtil.date2FormattedString(new Date(),
					"yyyyMMddHHmmss"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Long nextValue = customerBatchDao.getSeqNextValue();
		Long val = 100000000l;
		if (nextValue < val) {
			batchNum.append(String.valueOf(val + nextValue).substring(1));
		} else {
			batchNum.append(String.valueOf(val + nextValue));
		}

		return batchNum.toString();

	}

	/*
	 * (非 Javadoc) <p>Title: querBatchDetailsBygroupCode</p> <p>Description:
	 * </p>
	 * 
	 * @param groupCode
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.service.CustomerBatchService#
	 * querBatchDetailsBygroupCode(java.lang.String)
	 */
	public List<CustomerBatchDetail> querBatchDetailsBygroupCode(
			String groupCode) {
		// TODO Auto-generated method stub

		return customerBatchDetailDao.querBatchDetailsBygroupCode(groupCode);
	}

	/*
	 * (非 Javadoc) <p>Title: getCounts</p> <p>Description: </p>
	 * 
	 * @param groupCode
	 * 
	 * @param flag
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.service.CustomerBatchService#getCounts(java
	 * .lang.String, java.lang.String)
	 */
	public Integer getCounts(String groupCode, String flag) {
		// TODO Auto-generated method stub
		return customerBatchDetailDao.getCounts(groupCode, flag);
	}

	/**
	 * 
	 * @Description: 获取customer_detail表的批次数据优先级排序条件
	 * @return
	 * @return String
	 * @throws
	 */
	public String getOrderCondition(String assignType) {

		String orderCol = null;
		if (assignType.equals(Constants.ASSIGNTYPE_LST_PAID_ORDR_DATE)) {
			orderCol = "LST_PAID_ORDR_DATE DESC";
		} else if (assignType.equals(Constants.ASSIGNTYPE_FST_PAID_ORDR_DATE)) {
			orderCol = "LST_PAID_ORDR_DATE";
		} else if (assignType.equals(Constants.ASSIGNTYPE_LST_CALLOUT_DATE)) {
			orderCol = "LST_CALLOUT_DATE DESC";
		} else if (assignType.equals(Constants.ASSIGNTYPE_FST_CALLOUT_DATE)) {
			orderCol = "LST_CALLOUT_DATE";
		} else if (assignType
				.equals(Constants.ASSIGNTYPE_ACC_PAID_ORDR_QTY_MAX)) {
			orderCol = "ACC_PAID_ORDR_QTY DESC";
		} else if (assignType
				.equals(Constants.ASSIGNTYPE_ACC_PAID_ORDR_QTY_MIN)) {
			orderCol = "ACC_PAID_ORDR_QTY";
		} else if (assignType
				.equals(Constants.ASSIGNTYPE_ACC_PAID_ORDR_AMT_MAX)) {
			orderCol = "ACC_PAID_ORDR_AMT DESC";
		} else if (assignType
				.equals(Constants.ASSIGNTYPE_ACC_PAID_ORDR_AMT_MIN)) {
			orderCol = "ACC_PAID_ORDR_AMT";
		}
		return orderCol;
	}
}
