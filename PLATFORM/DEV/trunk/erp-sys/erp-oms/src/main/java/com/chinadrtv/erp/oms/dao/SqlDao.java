package com.chinadrtv.erp.oms.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Component;

import com.chinadrtv.erp.util.CollectionUtil;

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
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SqlDao.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Map<String, Object>> getResultMapList(String sql) {
		final List<Map<String, Object>> al = new ArrayList<Map<String, Object>>();
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				ResultSetMetaData rsmd = rs.getMetaData();
				int numberOfColumns = rsmd.getColumnCount();
				Map<String, Object> ht = new HashMap<String, Object>();
				for (int i = 1; i <= numberOfColumns; i++) {
					ht.put(rsmd.getColumnName(i).trim().toLowerCase(), rs.getObject(i));
				}
				al.add(ht);
			}
		});
		return al;
	}

	/**
	 * 执行存储过程
	* @Description: 
	* @param spName		存储过程名字
	* @param inParams	输入参数
	* @param outSize	输出参数数量
	* @return List<?>	输出参数List
	* @throws
	 */
	public List<Object> exeStoredProcedure(final String spName, final List<Object> inParams, final int outSize) 
			throws SQLException{
		final List<Object> resultList = new ArrayList<Object>();
		
		int totalSize = inParams.size() + outSize;
		
		StringBuffer sb = new StringBuffer();
		sb.append("{call ");
		sb.append(spName);
		sb.append("(");
		for(int i=0; i<totalSize; i++){
			if(i==totalSize-1){
				sb.append(" ? ");
			}else{
				sb.append(" ?,");	
			}
			
		}
		sb.append(")}");
		
		final String callStr = sb.toString();
		
		jdbcTemplate.execute(new CallableStatementCreator(){
			public CallableStatement createCallableStatement(Connection con) throws SQLException {
				
				CallableStatement cs = con.prepareCall(callStr);
				
				//输入参数
				for(int i=0; i<inParams.size(); i++){
					if(null == inParams.get(i)){
						cs.setObject(i+1, "");
					}else{
						cs.setObject(i+1, inParams.get(i));
					}
				}
				
				//输出参数
				for(int i=1; i<outSize+1; i++){
					cs.registerOutParameter(inParams.size()+i, Types.CHAR);
				}
				
				return cs;
			}}, new CallableStatementCallback<Object>(){

			public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
				cs.execute();
				for(int i=1; i<outSize+1; i++){
					Object outParam = cs.getObject(inParams.size()+i);
					resultList.add(outParam);
				}
				return resultList;
			}});
		
		logger.info("execute stored procedure :" + spName +"("+ CollectionUtil.printStringList(inParams) + ")");
		
		return resultList;
	}

	public void execSql(String sql, Object[] objs) {
		jdbcTemplate.update(sql, objs);
	}
}
