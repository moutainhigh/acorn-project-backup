package com.chinadrtv.erp.marketing.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.marketing.core.common.Constants;
import com.chinadrtv.erp.marketing.core.dao.CustomerBatchDao;
import com.chinadrtv.erp.marketing.dao.CustomerGroupDao;
import com.chinadrtv.erp.marketing.dao.CustomerSQLSourceDao;
import com.chinadrtv.erp.marketing.dto.CustomerGroupDto;
import com.chinadrtv.erp.marketing.service.CustomerGroupService;
import com.chinadrtv.erp.marketing.service.SchedulerService;
import com.chinadrtv.erp.marketing.util.JobCronSet;
import com.chinadrtv.erp.model.marketing.CustomerGroup;
import com.chinadrtv.erp.model.marketing.CustomerSqlSource;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.user.aop.RowAuth;

/**
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * 客户群管理-服务类</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * 客户群管理-服务类</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午3:52:50
 * 
 */
@Service("customerGroupService")
public class CustomerGroupServiceImpl implements CustomerGroupService {

	@Autowired
	private CustomerGroupDao customerGroupDao;

	@Autowired
	private CustomerSQLSourceDao customerSQLSourceDao;

	@Autowired
	private SchedulerService schedulerService;
	@Autowired
	private CustomerBatchDao customerBatchDao;

	/**
	 * <p>
	 * Title: getCustomerGroupById
	 * </p>
	 * <p>
	 * Description: 根据groupCode获取客户群组
	 * </p>
	 * 
	 * @param groupCode
	 * @return
	 * @see com.chinadrtv.erp.marketing.service.CustomerGroupService#getCustomerGroupById(java.lang.String)
	 */
	public CustomerGroup getCustomerGroupById(String groupCode) {
		return customerGroupDao.get(groupCode);
	}

	/**
	 * <p>
	 * Title: saveCustomerGroup
	 * </p>
	 * <p>
	 * Description:保存并更新客户群组
	 * </p>
	 * 
	 * @param customerGroup
	 * @see com.chinadrtv.erp.marketing.service.CustomerGroupService#saveCustomerGroup(com.chinadrtv.erp.marketing.model.CustomerGroup)
	 */
	public void saveCustomerGroup(CustomerGroup customerGroup, String user,
			JobCronSet jobCronSet, CustomerSqlSource sqlSource) {

		customerGroup.setUpDate(new Date());
		customerGroup.setUpUser(user);
		customerGroup.setExt1(customerGroup.getExt1());

		try {
			if (customerGroup.getExecuteCycel().equals(
					Constants.SCHEDULE_TYPE_ATONCE)
					|| customerGroup.getExecuteCycel().equals(
							Constants.SCHEDULE_TYPE_TIMING)) {
				customerGroup.setExecuteType("1");
				customerGroup.setIsRecover("0");
			} else {
				// 是否当天数据回收
				if (StringUtils.isEmpty(customerGroup.getIsRecover())) {
					customerGroup.setIsRecover("0");
				} else {
					customerGroup.setIsRecover("1");
				}

				customerGroup.setExecuteType("2");
			}

			jobCronSet.setFrequency(customerGroup.getExecuteCycel());
			if (StringUtils.isEmpty(customerGroup.getGroupCode())) {

				// String nextVal = getNextVal();
				customerGroup.setGroupCode(customerGroup.getGroupCodeTmp());
				customerGroup.setCrtUser(user);
				customerGroup.setCrtDate(new Date());
				// 保存sql
				customerGroupDao.save(customerGroup);
				customerSQLSourceDao.save(sqlSource);
				jobCronSet.setJobName(customerGroup.getGroupCodeTmp());
				jobCronSet.setGroup(Constants.JOB_GROUP_CUSTOMER_GROUP);
				schedulerService.scheduleAddJob(jobCronSet);

				// CustomerSqlSource sqlSource = new CustomerSqlSource();
				// sqlSource.setSqlContent(sqlCom);
				// sqlSource.setCrtDate(new Date());
				// sqlSource.setCrtUser(user);//
				// sqlSource.setGroup(customerGroup);
				// sqlSource.setGroupCode(nextVal);
				// customerSQLSourceDao.save(sqlSource);
			} else {
				JobDetail jobDetail = schedulerService.getJobDetail(
						customerGroup.getGroupCode(),
						Constants.JOB_GROUP_CUSTOMER_GROUP);
				if (jobDetail != null) {
					jobCronSet.setJobName(jobDetail.getName());
					jobCronSet.setGroup(jobDetail.getGroup());
					schedulerService.scheduleUpdateJob(jobCronSet);
				} else {
					jobCronSet.setJobName(customerGroup.getGroupCode());
					jobCronSet.setGroup(Constants.JOB_GROUP_CUSTOMER_GROUP);
					schedulerService.scheduleAddJob(jobCronSet);
				}
				CustomerGroup orgiGroup = customerGroupDao.get(customerGroup
						.getGroupCode());
				orgiGroup.setGroupName(customerGroup.getGroupName());
				orgiGroup.setAssignType(customerGroup.getAssignType());
				orgiGroup.setBusiCode(customerGroup.getBusiCode());
				orgiGroup.setDepartment(customerGroup.getDepartment());
				orgiGroup.setExecuteCycel(customerGroup.getExecuteCycel());
				orgiGroup.setExecuteType(customerGroup.getExecuteType());
				orgiGroup.setRemark(customerGroup.getRemark());
				orgiGroup.setValidEndDate(customerGroup.getValidEndDate());
				orgiGroup.setValidStartDate(customerGroup.getValidStartDate());
				orgiGroup.setStatus(customerGroup.getStatus());
				orgiGroup.setUpDate(customerGroup.getUpDate());
				orgiGroup.setUpUser(customerGroup.getUpUser());
				orgiGroup.setJobId(customerGroup.getJobId());
				orgiGroup.setQueneId(customerGroup.getQueneId());
				orgiGroup.setRejectCycle(customerGroup.getRejectCycle());
				orgiGroup.setIsRecover(customerGroup.getIsRecover());
				orgiGroup.setExt1(customerGroup.getExt1());
				orgiGroup.setBatchStatus(customerGroup.getBatchStatus());
				orgiGroup.setType(customerGroup.getType());
				orgiGroup.setCreateDepartment(customerGroup.getCreateDepartment());
				// customerGroupDao.saveOrUpdate(customerGroup);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>
	 * Title: removeCustomerGroup
	 * </p>
	 * <p>
	 * Description: 删除客户群组
	 * </p>
	 * 
	 * @param customerGroup
	 * @see com.chinadrtv.erp.marketing.service.CustomerGroupService#removeCustomerGroup(com.chinadrtv.erp.marketing.model.CustomerGroup)
	 */
	public void removeCustomerGroup(String groupId) {
		customerGroupDao.remove(groupId);

	}

	/**
	 * <p>
	 * Title: query
	 * </p>
	 * <p>
	 * Description: 查询客户群组列表
	 * </p>
	 * 
	 * @param customerGroup
	 * @param dataModel
	 * @return
	 * @see com.chinadrtv.erp.marketing.service.CustomerGroupService#query(com.chinadrtv.erp.marketing.model.CustomerGroup,
	 *      com.chinadrtv.erp.marketing.common.DataGridModel)
	 */
	@RowAuth
	public Map<String, Object> query(CustomerGroupDto customerGroup,
			DataGridModel dataModel) {

		Map<String, Object> result = new HashMap<String, Object>();
		List<CustomerGroup> list = customerGroupDao.query(customerGroup,
				dataModel);
		Integer total = customerGroupDao.queryCount(customerGroup);
		result.put("rows", list);
		result.put("total", total);

		return result;
	}

	/**
	 * 生成客户群组编号
	 */
	public String getNextVal() {
		String nextVal = "G";
		Long val = 1000000000l;
		Long next = customerGroupDao.getSeqNextValue();
		if (next < val) {
			return nextVal + (String.valueOf(val + next).substring(1));
		} else {
			return nextVal + next;
		}
	}

	/*
	 * (非 Javadoc) <p>Title: queryList</p> <p>Description: </p>
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.service.CustomerGroupService#queryList()
	 */
	public List<CustomerGroup> queryList() {
		// TODO Auto-generated method stub
		return customerGroupDao.queryList();
	}

	/*
	 * 查出所选的客户组可用数据
	 * 
	 * @param groupCode
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.service.CustomerGroupService#queryCount(java
	 * .lang.String[])
	 */
	public Long queryCount(String[] groupCode) {
		// TODO Auto-generated method stub
		return customerBatchDao.queryCountByBatchCodeList(groupCode);
	}

}
