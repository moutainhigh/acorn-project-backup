package com.chinadrtv.erp.marketing.core.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Component;

/**
 * 
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013-1-22 下午2:06:55 
 *
 */
@Component
public class SqlDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Map<String, Object>> getResultMapList(String sql) {
		final List<Map<String, Object>> al = new ArrayList<Map<String, Object>>();
//		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				ResultSetMetaData rsmd = rs.getMetaData();
				int numberOfColumns = rsmd.getColumnCount();
				Map<String, Object> ht = new HashMap<String, Object>();
				for (int i = 1; i <= numberOfColumns; i++) {
//					ht.put(rsmd.getColumnName(i).trim().toLowerCase(), StringUtils.trim(rs.getString(i)));
					ht.put(rsmd.getColumnName(i).trim().toLowerCase(), rs.getObject(i));
				}
				al.add(ht);
			}
		});
//		return (Map[]) al.toArray(new Map[al.size()]);
		return al;
	}

	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
