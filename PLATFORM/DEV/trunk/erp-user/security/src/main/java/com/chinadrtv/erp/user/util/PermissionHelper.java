/*
 * @(#)PermissionHelper.java 1.0 2013-5-7上午9:39:11
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.user.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.chinadrtv.erp.constant.SecurityConstants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterLong;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.security.DataViewPermission;
import com.chinadrtv.erp.model.security.FieldPermission;
import com.chinadrtv.erp.model.security.FilterValue;
import com.chinadrtv.erp.user.dao.DataViewPermissionDao;
import com.chinadrtv.erp.user.dao.FieldPermissionDao;
import com.chinadrtv.erp.user.model.AgentRole;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.model.FilterCondition;
import com.chinadrtv.erp.util.ExpressionUtils;
import com.chinadrtv.erp.util.SpringUtil;

/**
 * <dl>
 * <dt><b>Title:权限工具帮助类</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:权限帮助类</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-5-7 上午9:39:11
 * 
 */
public class PermissionHelper {

	private static final Logger logger = LoggerFactory.getLogger(PermissionHelper.class);
	
	private static ThreadLocal<HttpServletRequest> requestLocal = new ThreadLocal<HttpServletRequest>();
	// private static ThreadLocal<HttpServletResponse> responseLocal= new
	// ThreadLocal<HttpServletResponse>();

	private static ThreadLocal<FilterCondition> whereConditionLocal = new ThreadLocal<FilterCondition>();

	/**
	 * 
	* @Description: 查询过滤条件
	* @param dataViewUrl
	* @return
	* @return FilterCondition
	* @throws
	 */
	public static FilterCondition getFilterCondition(String dataViewUrl) {

		FilterCondition filterCondition = new FilterCondition();

		AgentUser user = SecurityHelper.getLoginUser();
    	//Iterator<? extends GrantedAuthority> it = user.getAuthorities().iterator();
		Iterator<AgentRole> it = user.loadAgentRoles().iterator();
    	AgentRole role = null;DataViewPermissionDao dataViewPermissionDao = (DataViewPermissionDao) SpringUtil.getBean("dataViewPermissionDao");
    	
    	while(it.hasNext()){
    		role = (AgentRole)it.next();

    		 String roleId = role.getRoleName();
    		
    		 logger.info("roleId="+roleId+";url="+dataViewUrl);
    		// 根据dataViewUrl和角色id查询视图过滤权限
    		DataViewPermission dataViewPermission = dataViewPermissionDao.query(dataViewUrl, roleId);

    		// 如果数据视图权限不为null,解析filter条件
    		if (dataViewPermission != null) {
    			
    			if(dataViewPermission.getFilterPermission()!=null && dataViewPermission.getFilterPermission().getFilter() !=null){
    				String whereClause = dataViewPermission.getFilterPermission().getFilter()
        					.getWhere_clause();
        			
        			filterCondition.setWhereClause(whereClause);
        			filterCondition.setFilterValues(dataViewPermission.getFilterPermission().getFilterValues());
        			break;
    			}
    		}
    		
    	}
    	
		
		

		return filterCondition;
	}

	/**
	 * 替换静态变量
	* @Description: TODO
	* @param condition
	* @param filters
	* @return
	* @return String
	* @throws
	 */
	private static String replaceStaticValue(String condition, Set<FilterValue> filters) {
		Iterator<FilterValue> it = filters.iterator();
		FilterValue filterValue = null;
		while (it.hasNext()) {
			filterValue = it.next();
			condition = condition.replace("[" + filterValue.getName()+"]", filterValue.getValue());
		}
		return condition;
	}

	/**
	 * 
	* @Description: 获取带有占位符的过滤条件
	* @return
	* @return String
	* @throws
	 */
	public static String getFilterCondition() {
		FilterCondition whereCondition = whereConditionLocal.get();
		if(whereCondition!=null){
			return whereCondition.getPlaceHolderWhere()!=null?whereCondition.getPlaceHolderWhere():"";
		}
		return "";
	}
	
	/**
	 * 
	* @Description: 获取过滤参数
	* @return
	* @return Map<String,Parameter>
	* @throws
	 */
	public static Map<String,Parameter> getFilterParameter() {
		
		Map<String,Parameter> result = null;
		FilterCondition whereCondition = whereConditionLocal.get();
		if(whereCondition!=null){
			result =  whereCondition.getParmater();
		}else{
			result = new HashMap<String, Parameter>();
		}
		return result;
	}

	public static void removeThreadLocal(){
		requestLocal.remove();
		whereConditionLocal.remove();
	}
	/**
	 * 
	* @Description: 设置过滤条件到ThreadLocal
	* @return void
	* @throws
	 */
	public static void setWhereCondition(Integer urlDepth) {
		
		String requestUrl = getRequest().getServletPath();
		requestUrl = getUrl(requestUrl,urlDepth);
		
		FilterCondition filterCondition = getFilterCondition(requestUrl);
		
		if(filterCondition.getWhereClause()!=null){
			parseWhere(filterCondition);
			parseWhereValue(filterCondition);
			if(!StringUtils.isEmpty(filterCondition.getPlaceHolderWhere())){
				whereConditionLocal.set(filterCondition);
			}
		}
		
	}
	
	/**
	 * 
	* @Description: 解析where条件
	* @param filterCondition
	* @return
	* @return String
	* @throws
	 */
	public static String parseWhere(FilterCondition filterCondition){
		
		String whereCondition = filterCondition.getWhereClause();
		Pattern pattern = Pattern.compile("(\\$\\{[a-z0-9.]+\\}|\\[[a-z0-9]+\\])");
		Matcher matcher = pattern.matcher(whereCondition);
		Pattern varPattern = Pattern.compile("([a-z0-9.]+)");
		String varNamePlaceHolder = null;
		String varName = "";
		Matcher varMatcher = null;
		while(matcher.find()){
			varNamePlaceHolder = matcher.group();
			varMatcher = varPattern.matcher(varNamePlaceHolder);
			if(varMatcher.find()){
				varName =":"+varMatcher.group();
				if(varName.indexOf(".")>-1){
					varName = varName.replace(".", "");
				}
			}
			whereCondition = whereCondition.replace(varNamePlaceHolder, varName);
		}
		filterCondition.setPlaceHolderWhere(whereCondition);
		return whereCondition;
	}
	
	/**
	 * 
	* @Description: 获取过滤条件集合
	* @param filterValues
	* @return
	* @return Map<String,FilterValue>
	* @throws
	 */
	public static Map<String,FilterValue> getFilterValueMap(Set<FilterValue> filterValues){
		
		Map<String,FilterValue> map = new HashMap<String, FilterValue>();
		
		Iterator<FilterValue> it = filterValues.iterator();
		FilterValue filterValue = null;
		while (it.hasNext()) {
			filterValue = it.next();
			map.put(filterValue.getName(), filterValue);
		}
		
		return map;
	}
	
	/**
	 * 
	* @Description: 解析过滤条件值
	* @param filterCondition
	* @return
	* @return Map<String,Parameter>
	* @throws
	 */
	public static Map<String,Parameter> parseWhereValue(FilterCondition filterCondition){
		
		Map<String,Parameter> resultMap = new HashMap<String, Parameter>();
		Pattern pattern = Pattern.compile("(\\$\\{[a-zA-Z0-9.]+\\}|\\[[a-zA-Z0-9]+\\])");
		Matcher matcher = pattern.matcher(filterCondition.getWhereClause());
		Pattern varPattern = Pattern.compile("([a-zA-Z0-9.]+)");
		String varNamePlaceHolder = null;
		String varName = "";
		Matcher varMatcher = null;
		Map<String,FilterValue> filterValueMap = getFilterValueMap(filterCondition.getFilterValues());
		FilterValue filterValue = null;
		String value = "";
		
		AgentUser loginUser = SecurityHelper.getLoginUser();
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("user", loginUser);
		
				
		while(matcher.find()){
			varNamePlaceHolder = matcher.group();
			varMatcher = varPattern.matcher(varNamePlaceHolder);
			
			if(varMatcher.find()){
				varName =varMatcher.group();
				filterValue = filterValueMap.get(varName);
				if(filterValue!=null){
					if(varName.indexOf(".")>-1){
						varName = varName.replace(".", "");
					}
					
					value = filterValue.getValue();
					if(StringUtils.isEmpty(value) && varNamePlaceHolder.startsWith("$")){
						value = ExpressionUtils.evalString(varNamePlaceHolder, context);
					}
					
					if(filterValue.getType().equals("String")){
						resultMap.put(varName, new ParameterString(varName, value));
					}else if(filterValue.getType().equals("Integer")){
						resultMap.put(varName, new ParameterInteger(varName, Integer.parseInt(value)));
					}else if(filterValue.getType().equals("Long")){
						resultMap.put(varName, new ParameterLong(varName, Long.parseLong(value)));
					}
				}
			}
			
			
		}
		
		filterCondition.setParmater(resultMap);
		return resultMap;
	}
	
	
	/**
	 * 
	* @Description: TODO
	* @return void
	* @throws
	 */
//	public static void getFieldPermission() {
//		
//		String requestUrl = getRequest().getServletPath();
//		
//		requestUrl = getUrl(requestUrl);
//		FilterCondition filterCondition = getFilterCondition(requestUrl);
//		parseWhere(filterCondition);
//		parseWhereValue(filterCondition);
//		if(!StringUtils.isEmpty(filterCondition.getPlaceHolderWhere())){
//			whereConditionLocal.set(filterCondition);
//		}
//	}
	
	/**
	 * 
	* @Description: 获取视图权限
	* @return
	* @return DataViewPermission
	* @throws
	 */
	public static DataViewPermission getDataViewPermission(Integer urlDepth) {
		
		String requestUrl = getRequest().getServletPath();

		requestUrl = getUrl(requestUrl,urlDepth);
		
		AgentUser user = SecurityHelper.getLoginUser();
        if ("/contact/phoneList".equals(requestUrl) && user.hasRole(SecurityConstants.ROLE_SHOW_FULL_PHONE)) return null;
    	//Iterator<? extends GrantedAuthority> it = user.getAuthorities().iterator();
		Iterator<AgentRole> it = user.loadAgentRoles().iterator();
    	AgentRole role = null;
    	DataViewPermission dataViewPermission = null;
    	while(it.hasNext()){
    		role = (AgentRole)it.next();
    		dataViewPermission = getDataViewPermission(role.getRoleName(),urlDepth);

    		// 如果数据视图权限不为null,解析filter条件
    		if (dataViewPermission != null) {
    			return dataViewPermission;
    		}
    	}
		
		return null;		
	}
	
	/**
	 * 
	* @Description: 根据角色获取视图权限
	* @param roleName
	* @return
	* @return DataViewPermission
	* @throws
	 */
	public static DataViewPermission getDataViewPermission(String roleName,Integer urlDepth) {
		
		String requestUrl = getRequest().getServletPath();

		requestUrl = getUrl(requestUrl,urlDepth);
		
    		DataViewPermissionDao dataViewPermissionDao = (DataViewPermissionDao) SpringUtil.getBean("dataViewPermissionDao");

    		// 根据dataViewUrl和角色id查询视图过滤权限
    		DataViewPermission dataViewPermission = dataViewPermissionDao.query(requestUrl, roleName);

    		// 如果数据视图权限不为null,解析filter条件
    		if (dataViewPermission != null) {
    			return dataViewPermission;
    		}
		
    		return null;		
	}
	
	public static FieldPermission getFieldPermission(String fieldName) {
		
		FieldPermission result = null;
		String requestUrl = getRequest().getServletPath();
		requestUrl = getUrl(requestUrl,null);
		
		AgentUser user = SecurityHelper.getLoginUser();
    	//Iterator<? extends GrantedAuthority> it = user.getAuthorities().iterator();
		Iterator<AgentRole> it = user.loadAgentRoles().iterator();
    	AgentRole role = null;
    	FieldPermissionDao fieldPermissionDao = (FieldPermissionDao) SpringUtil.getBean("fieldPermissionDao");
    	while(it.hasNext()){
    		role = (AgentRole)it.next();
    		result = fieldPermissionDao.getFieldPermission(role.getRoleName(), requestUrl, fieldName);
    		if(result != null){
    			break;
    		}
    	}
		
		return result;
	}
	
	public static String getUrl(String url,Integer urlDepth){
		String result = "$1$2";
		if(urlDepth != null){
			result="";
			for(int i=1;i<=urlDepth;i++){
				result+="$"+i;
			}
		}
		
		return url.replaceAll("(/[0-9A-Za-z]+)(/[0-9A-Za-z]+)(.*)", result);
	}
	/**
	 * 
	* @Description: TODO
	* @return
	* @return HttpServletRequest
	* @throws
	 */
	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) requestLocal.get();
	}

	/**
	 * 
	* @Description: TODO
	* @param request
	* @return void
	* @throws
	 */
	public static void setRequest(HttpServletRequest request) {
		requestLocal.set(request);
	}

	
	/**
	 * 
	* @Description: TODO
	* @return
	* @return HttpSession
	* @throws
	 */
	public static HttpSession getSession() {
		return (HttpSession) ((HttpServletRequest) requestLocal.get()).getSession();
	}


	public static void main(String arg[]){
		
//		String whereCondition = " crtUser = ${user.name} and group=[group] and group=[group2] and crtUser = ${user.sex} ";
//		String whereCondition2 = " crtUser = test and group=inbund and group=inbund2 and crtUser = 1 ";
//		System.out.println(PermissionHelper.parseWhere(whereCondition));
		
//		Pattern pattern = Pattern.compile("(\\$\\{[a-z0-9.]+\\}|\\[[a-z0-9]+\\])");
//		Matcher matcher = pattern.matcher(whereCondition);
//		Pattern varPattern = Pattern.compile("([a-z0-9.]+)");
//		String varNamePlaceHolder = null;
//		String varName = "";
//		Matcher varMatcher = null;
//		while(matcher.find()){
//			varNamePlaceHolder = matcher.group();
//			varMatcher = varPattern.matcher(varNamePlaceHolder);
//			if(varMatcher.find()){
//				varName =":"+varMatcher.group();
//				if(varName.indexOf(".")>-1){
//					varName = varName.replace(".", "");
//				}
//				//System.out.println(varMatcher.group());
//			}
//			whereCondition = whereCondition.replace(varNamePlaceHolder, varName);
//			//System.out.println(varNamePlaceHolder);
//		}
//		
//		System.out.println(whereCondition);
		
		String url = "6131888";
		
		url = url.replaceAll("([0-9]{3})([-\\d]{1,4})([0-9]{3,4}$)", "$1$2****");
		
		System.out.println(url);
		
	}

	
}
