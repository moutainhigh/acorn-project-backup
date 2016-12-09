package com.chinadrtv.erp.marketing.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.marketing.dao.CustomerGroupDao;
import com.chinadrtv.erp.marketing.dao.CustomerSQLSourceDao;
import com.chinadrtv.erp.marketing.dao.CustomerSQLSourceHisDao;
import com.chinadrtv.erp.marketing.service.CustomerSqlSourceService;
import com.chinadrtv.erp.model.marketing.CustomerGroup;
import com.chinadrtv.erp.model.marketing.CustomerSqlSource;
import com.chinadrtv.erp.model.marketing.CustomerSqlSourceHis;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

/**
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * 客户群管理-sql数据源-服务类</dd>
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
@Service("customerSqlSourceService")
public class CustomerSqlSourceServiceImpl implements CustomerSqlSourceService {

	@Autowired
	private CustomerSQLSourceDao sqlSourceDao;

	@Autowired
	private CustomerSQLSourceHisDao sqlSourceHisDao;

	@Autowired
	private CustomerGroupDao customerGroupDao;

	/**
	 * <p>
	 * Title: getCustomerSqlSourceById
	 * </p>
	 * <p>
	 * Description: 根据id获取客户群组sql数据源
	 * </p>
	 * 
	 * @param groupCode
	 * @return
	 * @see com.chinadrtv.erp.marketing.service.CustomerSqlSourceService#getCustomerSqlSourceById(java.lang.String)
	 */
	public CustomerSqlSource getCustomerSqlSourceById(Long id) {
		return sqlSourceDao.get(id);
	}

	/**
	 * <p>
	 * Title: saveCustomerSqlSource
	 * </p>
	 * <p>
	 * Description: 保存sql数据眼
	 * </p>
	 * 
	 * @param sqlSource
	 * @see com.chinadrtv.erp.marketing.service.CustomerSqlSourceService#saveCustomerSqlSource(com.chinadrtv.erp.marketing.model.CustomerSqlSource)
	 */
	public Long saveCustomerSqlSource(CustomerSqlSource sqlSource,
			String groupCode, String user) {
		Long temp = 1l;
		if (!sqlSource.getSqlContent().contains("contactid")
				|| !sqlSource.getSqlContent().contains("contactinfo")
				|| !sqlSource.getSqlContent().contains("statisinfo")) {
			temp = 0l;
		} else {
			CustomerGroup group = customerGroupDao.get(groupCode);
			if (group.getSqlSource() == null) {
				sqlSource.setGroup(group);
				sqlSource.setCrtUser(user);
				sqlSource.setCrtDate(new Date());
				// sqlSource.setGroupCode(groupCode);
				sqlSourceDao.save(sqlSource);
			} else {
				try {
					CustomerSqlSource original = group.getSqlSource();
					CustomerSqlSourceHis newHis = new CustomerSqlSourceHis();
					newHis.setSqlContent(original.getSqlContent());
					newHis.setCrtDate(original.getCrtDate());
					newHis.setCrtUser(original.getCrtUser());
					newHis.setGroupCode(original.getGroup().getGroupCode());
					sqlSourceHisDao.save(newHis);
					original.setCrtUser(user);
					original.setCrtDate(new Date());
					original.setSqlContent(sqlSource.getSqlContent());
				} catch (Exception e) {
					temp = -1l;
					e.printStackTrace();
				}
			}
		}
		return temp;
	}

	/**
	 * <p>
	 * Title: removeCustomerSqlSource
	 * </p>
	 * <p>
	 * Description: 删除客户群sql数据源
	 * </p>
	 * 
	 * @param sqlSource
	 * @see com.chinadrtv.erp.marketing.service.CustomerSqlSourceService#removeCustomerSqlSource(com.chinadrtv.erp.marketing.model.CustomerSqlSource)
	 */
	public void removeCustomerSqlSource(Long id) {
		sqlSourceDao.remove(id);

	}

	/**
	 * <p>
	 * Title: query
	 * </p>
	 * <p>
	 * Description: 查询sql数据源历史记录
	 * </p>
	 * 
	 * @param sqlSource
	 * @param dataModel
	 * @return
	 * @see com.chinadrtv.erp.marketing.service.CustomerSqlSourceService#query(com.chinadrtv.erp.marketing.model.CustomerSqlSource,
	 *      com.chinadrtv.erp.marketing.common.DataGridModel)
	 */
	public Map<String, Object> querySqlHis(String groupCode,
			DataGridModel dataModel) {

		Map<String, Object> result = new HashMap<String, Object>();
		List<CustomerSqlSourceHis> list = sqlSourceHisDao.query(groupCode,
				dataModel);
		Integer total = sqlSourceHisDao.queryCount(groupCode);
		result.put("rows", list);
		result.put("total", total);
		return result;
	}

	/**
	 * <p>
	 * Title: querySQLCount
	 * </p>
	 * <p>
	 * Description: 预览sql的数据量
	 * </p>
	 * 
	 * @param sql
	 * @return
	 * @see com.chinadrtv.erp.marketing.service.CustomerSqlSourceService#querySQLCount(java.lang.String)
	 */
	public Long querySQLCount(String sql) {
		Long temp = 1l;
		if (!sql.contains("contactid") || !sql.contains("contactinfo")
				|| !sql.contains("statisinfo")) {
			temp = 0l;
		} else {
			temp = sqlSourceDao.querySQLCount(sql);
		}
		return temp;
	}

}
