/*
 * @(#)WxOcsCalllistDaoImpl.java 1.0 2013-12-16下午2:46:09
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.dao.hibernate;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.marketing.core.dao.WxOcsCalllistDao;
import com.chinadrtv.erp.model.marketing.WxOcsCalllist;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-12-16 下午2:46:09
 * 
 * 
 *        预测外呼 隐藏 2014年3月24日
 */
// @Repository
public class WxOcsCalllistDaoImpl extends
		GenericDaoHibernate<WxOcsCalllist, java.lang.Integer> implements
		Serializable, WxOcsCalllistDao {
	// @Autowired
	// @Qualifier("jdbcTemplateCti")
	public JdbcTemplate jdbcTemplateCti;

	public WxOcsCalllistDaoImpl() {
		super(WxOcsCalllist.class);
	}

	public Integer batchInsert(List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		final List<Map<String, Object>> lists = list;
		String sql = "insert into ACOAPP_GENE_URS3.WX_OCS_CALLLIST (record_id,contact_info,contact_info_type,record_type,record_status,"
				+ "attempt,daily_from,daily_till,tz_dbid,batch,chain_id,chain_n) "
				+ " values (ACOAPP_GENE_URS3.SEQ_WX_OCS_CALLLIST.nextval,?,0,2,1,0,?,?,112,?,?,?)";
		int[] result = jdbcTemplateCti.batchUpdate(sql,
				new BatchPreparedStatementSetter() {
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						String contact_info = ""
								+ lists.get(i).get("contact_info");
						Integer daily_from = 0;
						Integer daily_till = 0;
						ps.setString(1, contact_info);
						ps.setLong(
								2,
								Long.valueOf(lists.get(i).get("daily_from")
										.toString()));
						ps.setLong(
								3,
								Long.valueOf(lists.get(i).get("daily_till")
										.toString()));
						ps.setLong(4,
								Long.valueOf((Long) lists.get(i).get("batch")));
						ps.setLong(
								5,
								Long.valueOf(lists.get(i).get("chain_id")
										.toString()));
						ps.setLong(
								6,
								Long.valueOf(lists.get(i).get("chain_n")
										.toString()));
					}

					public int getBatchSize() {
						return lists.size();
					}
				});

		return result.length;
	}
}
