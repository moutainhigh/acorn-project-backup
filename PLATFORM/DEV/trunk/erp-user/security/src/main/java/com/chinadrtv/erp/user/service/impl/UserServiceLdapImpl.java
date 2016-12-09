/*
 * @(#)UserServiceLdapImpl.java 1.0 2013-7-1下午1:23:32
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.user.service.impl;

import com.chinadrtv.erp.constant.SecurityConstants;
import com.chinadrtv.erp.exception.ExceptionConstant;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.user.dao.AgentUserDao;
import com.chinadrtv.erp.user.ldap.AcornRole;
import com.chinadrtv.erp.user.model.AgentRole;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.model.DepartmentInfo;
import com.chinadrtv.erp.user.model.GroupInfo;
import com.chinadrtv.erp.user.service.LdapService;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.chinadrtv.erp.util.DateUtil;
import com.chinadrtv.erp.util.StringUtil;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.LikeFilter;
import org.springframework.ldap.filter.WhitespaceWildcardsFilter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.SpringSecurityLdapTemplate;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import java.util.*;
import java.util.logging.Logger;

/**
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
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-7-1 下午1:23:32 
 * 
 */
//@Service("userService")
@Deprecated
public class UserServiceLdapImpl implements UserService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UserServiceLdapImpl.class);
	//@Autowired
	private SpringSecurityLdapTemplate ldapTemplate;
	
	//@Autowired
	private FilterBasedLdapUserSearch filterBasedLdapUserSearch;
	
	//@Autowired
	//@Qualifier("ldapService")
	private LdapService ldapService;
	
	@Autowired
	private AgentUserDao userDao;
	/* (非 Javadoc)
	* <p>Title: search</p>
	* <p>Description: </p>
	* @param userName
	* @return
	* @see com.chinadrtv.erp.user.service.UserService#search(java.lang.String)
	*/ 
	@Override
	public String getUserGroup(String userName) throws ServiceException {
		DirContextOperations ben = filterBasedLdapUserSearch.searchForUser(userName);
		if(ben != null){
			 //return SecurityHelper.getGroup(ben.getDn().toString());
			return null;
		}else{
			  throw new ServiceException(ExceptionConstant.SERVICE_USER_QUERY_EXCEPTION,"查询用户异常");
		}
	}

	@Override
	public String getDepartment(String userName) throws ServiceException, NamingException {
		DirContextOperations ben = filterBasedLdapUserSearch.searchForUser(userName);
		if(ben != null){
			 return ben.getStringAttribute("departmentNumber");
		}else{
			  throw new ServiceException(ExceptionConstant.SERVICE_USER_QUERY_EXCEPTION,"查询用户异常");
		}
	}
	
	@Override
	@ReadThroughSingleCache(namespace="com.chinadrtv.erp.user.service.getDepartmentByGroup",expiration=3600)
	public String getDepartmentByGroup(@ParameterValueKeyProvider String group) throws ServiceException {
		String groupDn = ldapService.getGroupDn(group);
		if(groupDn != null){
			 //return SecurityHelper.getDepartment(groupDn);
			return null;
		}else{
			  throw new ServiceException(ExceptionConstant.SERVICE_USER_QUERY_EXCEPTION,"查询用户异常");
		}
	}
	
	public List<String> getUserDepartments(String userName) {
		AndFilter andfilter = new AndFilter();
		EqualsFilter objectClassFilter = new EqualsFilter("ou", userName);
		andfilter.and(objectClassFilter);
		List depts = ldapTemplate.search("",
				andfilter.encode(), new AttributesMapper() {

					@Override
					public Object mapFromAttributes(Attributes attributes)
							throws NamingException {
						return null;
					}});
		return null;

	}
	/* (非 Javadoc)
	* <p>Title: getGroupAreaCode</p>
	* <p>Description: </p>
	* @param userName
	* @return
	* @throws ServiceException
	* @see com.chinadrtv.erp.user.service.UserService#getGroupAreaCode(java.lang.String)
	*/ 
	@Override
	public String getGroupAreaCode(String userName) throws ServiceException {
		
		String userGroup =  getUserGroup(userName);
		if(!StringUtils.isEmpty(userGroup)){
			String areaCode = "";
			Set<String> result = ldapTemplate.searchForSingleAttributeValues(SecurityConstants.SEARCH_GROUP_ROOT, SecurityConstants.SEARCH_GROUP_FILTER, new String[] {userGroup}, SecurityConstants.LDAP_GROUP_ATTRIBUTE_AREACODE);
			if(!result.isEmpty()){
				areaCode = result.toArray()[0].toString();
			}
			return areaCode;
		}else{
			  throw new ServiceException(ExceptionConstant.SERVICE_USER_QUERY_EXCEPTION,"查询用户异常");
		}
		
	}
	
	
	@Override
	public String getAreaCode(String groupName) throws ServiceException {
		
			return ldapService.searchAttribute(SecurityConstants.SEARCH_GROUP_ROOT, 
					SecurityConstants.SEARCH_GROUP_FILTER, 
					groupName, 
					SecurityConstants.LDAP_GROUP_ATTRIBUTE_AREACODE);
	}

	/* (非 Javadoc)
	* <p>Title: changePassword</p>
	* <p>Description: </p>
	* @param userId
	* @param oldPassword
	* @param newPassWord
	* @throws ServiceException
	* @see com.chinadrtv.erp.user.service.UserService#changePassword(java.lang.String, java.lang.String, java.lang.String)
	*/ 
	@Override
	public void changePassword(String userId,String newPassWord,String oldPassword)
			throws ServiceException {
		try{
			if(userDao.exists(userId)){
				AgentUser user = userDao.get(userId);
				user.setPassword(newPassWord);
				userDao.save(user);
			}
			
			String userDn = getUserDN(userId);
			ldapService.changePassword(userDn,newPassWord,oldPassword);
			
			
			String lastTime = ldapService.searchUserAttributeNoCache(userId, SecurityConstants.LDAP_USER_ATTRIBUTE_LASTTIME);
			if(!StringUtils.isEmpty(lastTime)){
				ldapService.updateUserAttribute(userId, SecurityConstants.LDAP_USER_ATTRIBUTE_LASTTIME, DateUtil.dateToString(new Date()));
			}else{
				ldapService.addUserAttribute(userId, SecurityConstants.LDAP_USER_ATTRIBUTE_LASTTIME, DateUtil.dateToString(new Date()));
			}
			
		}catch (Exception e) {
			String message = "密码修改错误";
			String errorCode = "0";
			if(e instanceof UsernameNotFoundException) {
				message = "用户不存在";
				errorCode = "2";
			} else if(e.getMessage().indexOf("Password should have a minmum of 8 characters") > 0) {
				message = "密码必须大于6位";
				errorCode="3";
			} else if(e.getMessage().indexOf("password present in password history") > 0) {
				message = "密码已存在于最近5次密码修改历史，不能做为密码";
				errorCode = "4";
			}
			throw new  ServiceException(errorCode,message);
		}
	}

	/* (非 Javadoc)
	* <p>Title: checkPassword</p>
	* <p>Description: </p>
	* @param uId
	* @param password
	* @return
	* @throws ServiceException
	* @see com.chinadrtv.erp.user.service.UserService#checkPassword(java.lang.String, java.lang.String)
	*/ 
	@Override
	public boolean checkPassword(String uId, String password) throws ServiceException {
		
		DirContextOperations ben = filterBasedLdapUserSearch.searchForUser(uId);
		if(ben != null){
			uId =  ben.getNameInNamespace();
		}
		return ldapService.checkPassword(uId, password);
	}

	public String getUserDN(String uId){
		DirContextOperations ben = filterBasedLdapUserSearch.searchForUser(uId);
		if(ben != null){
			 return ben.getDn().toString();
		}
		return "";
	}

	/* (非 Javadoc)
	* <p>Title: getGroupType</p>
	* <p>Description: </p>
	* @param userName
	* @return
	* @throws ServiceException
	* @see com.chinadrtv.erp.user.service.UserService#getGroupType(java.lang.String)
	*/ 
	@Override
	@ReadThroughSingleCache(namespace="com.chinadrtv.erp.user.service.impl",expiration=1800)
	public String getGroupType(@ParameterValueKeyProvider String userName) throws ServiceException {
		String userGroup =  getUserGroup(userName);
		if(!StringUtils.isEmpty(userGroup)){
			return ldapService.searchGroupAttribute(userGroup, SecurityConstants.LDAP_GROUP_ATTRIBUTE_GROUPTYPE);
		}else{
			  throw new ServiceException(ExceptionConstant.SERVICE_USER_QUERY_EXCEPTION,"查询用户组属性异常");
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<AgentUser> getUserList(String groupCode) throws ServiceException {
		EqualsFilter filter = new EqualsFilter("objectClass", "acornUser");
		
		try{
			String dn = ldapService.getGroupDn(groupCode);
			if(!StringUtils.isEmpty(dn)){
				List<AgentUser> list =  ldapTemplate.search(dn, filter
						.encode(), new AttributesMapper() {

					public Object mapFromAttributes(Attributes attrs)
							throws NamingException {
						AgentUser user = new AgentUser();
						user.setUserId(convert(attrs.get("uid").get()));
						user.setDisplayName(convert(attrs.get("displayName")!=null?attrs.get("displayName").get():""));
						user.setDepartment(convert(attrs.get("departmentNumber")!=null?attrs.get("departmentNumber").get():""));
						user.setEmployeeType(convert(attrs.get("employeeType")!=null?attrs.get("employeeType").get():""));
						return user;
					}
				});
                List<AgentUser> list2 = new ArrayList<AgentUser>();
                for(AgentUser u :list){
                    u.setDefGrp(groupCode);
                    list2.add(u);
                }
				
				Collections.sort(list2, new Comparator<AgentUser>() {
					public int compare(AgentUser a, AgentUser b) { 
						return a.getUserId().compareToIgnoreCase(b.getUserId());
					}
				});
					  
				return list2;
			}else{
				throw new ServiceException(ExceptionConstant.SERVICE_USER_QUERY_EXCEPTION,groupCode+"组没有找到");
			}
		}catch (Exception e) {
			throw new ServiceException(ExceptionConstant.SERVICE_USER_QUERY_EXCEPTION,groupCode+"组没有找到");
		}
		
    }

	public DepartmentInfo getDepartmentInfo(String departmentNum) throws ServiceException {
		if(!StringUtils.isEmpty(departmentNum)){
			AndFilter andfilter = new AndFilter();
			EqualsFilter objectClassFilter = new EqualsFilter("objectClass", "acornDept");
			EqualsFilter deptNumfilter = new EqualsFilter("departmentNumber", departmentNum);
			andfilter.and(objectClassFilter);
			andfilter.and(deptNumfilter);
			String tt = andfilter.encode();
			List list =  ldapTemplate.search(SecurityConstants.SEARCH_GROUP_ROOT, andfilter.encode(), 
					new AttributesMapper() {
						public DepartmentInfo mapFromAttributes(Attributes attrs)
								throws NamingException {
							
							DepartmentInfo departmentInfo = new DepartmentInfo();
							departmentInfo.setId(convert(attrs.get("departmentNumber")!=null?attrs.get("departmentNumber").get():""));
							departmentInfo.setName(convert(attrs.get("description")!=null?attrs.get("description").get():""));
							departmentInfo.setAreaCode(convert(attrs.get("acornAreaCode")!=null?attrs.get("acornAreaCode").get():""));
							departmentInfo.setCode(convert(attrs.get("ou").get()));
							return departmentInfo;
						}
			});
			
			if(!list.isEmpty()){
				return (DepartmentInfo)list.get(0);
			}
			return null;
		}else{
			throw new ServiceException(ExceptionConstant.SERVICE_USER_QUERY_EXCEPTION,"部门Id不能为空");
		}
    }
	
	@SuppressWarnings("unchecked")
	public List<GroupInfo> getGroupList(String departmentNum) throws ServiceException {
		
		DepartmentInfo dept = getDepartmentInfo(departmentNum);
		if(dept!=null){
			
			String dn = ldapService.getGroupDn(dept.getCode());
			
			
			AndFilter andfilter = new AndFilter();
			EqualsFilter objectClassFilter = new EqualsFilter("objectClass", "acornGroup");
			andfilter.and(objectClassFilter);
			String tt = andfilter.encode();
			  SearchControls searchCtls = new SearchControls(); // Create the
		      // 创建搜索控制器
		      searchCtls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
		      List<GroupInfo> list =  ldapTemplate.search(dn, andfilter.encode(), searchCtls, new AttributesMapper() {
								public GroupInfo mapFromAttributes(Attributes attrs)
										throws NamingException {
									
									GroupInfo group = new GroupInfo();
									group.setId(convert(attrs.get("ou").get()));
									group.setName(convert(attrs.get("description")!=null?attrs.get("description").get():""));
									return group;
								}
			});
					
			
			return list;
		}else{
			throw new ServiceException(ExceptionConstant.SERVICE_USER_QUERY_EXCEPTION,"部门不存在");
		}
    }
	
	@SuppressWarnings("unchecked")
	@ReadThroughSingleCache(namespace="com.chinadrtv.erp.security.userservice",expiration=43200)
	public List<AcornRole> getRoleList(@ParameterValueKeyProvider String userName) throws ServiceException {
		
		if(userName!=null){
			String userDn = ldapService.getUserDn(userName);
			
			if(!StringUtils.isEmpty(userDn)){
				AndFilter andfilter = new AndFilter();
				EqualsFilter objectClassFilter = new EqualsFilter("objectClass", "acornRole");
				andfilter.and(objectClassFilter);
				EqualsFilter uniqueMemberClassFilter = new EqualsFilter("uniqueMember", userDn+","+SecurityConstants.LDAP_ROOT_DN);
				andfilter.and(uniqueMemberClassFilter);
				
				  SearchControls searchCtls = new SearchControls(); // Create the
			      // 创建搜索控制器
			      searchCtls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
			      List<AcornRole> list =  ldapTemplate.search(SecurityConstants.SEARCH_ROLE_ROOT, andfilter.encode(), searchCtls, new AttributesMapper() {
									public AcornRole mapFromAttributes(Attributes attrs)
											throws NamingException {
										
										AcornRole role = new AcornRole();
										role.setName(convert(attrs.get("cn").get()));
										role.setAcornPriority(convert(
												attrs.get(SecurityConstants.LDAP_ROLE_ATTRIBUTE_ACORNPRIORITY)!=null
												?attrs.get(SecurityConstants.LDAP_ROLE_ATTRIBUTE_ACORNPRIORITY).get()
														:""));
										return role;
									}
				});
						
				
				return list;
			}else{
				throw new ServiceException(ExceptionConstant.SERVICE_USER_QUERY_EXCEPTION,"坐席id不存在");
			}
			
		}else{
			throw new ServiceException(ExceptionConstant.SERVICE_USER_QUERY_EXCEPTION,"坐席id不能为空");
		}
    }
	
	
	@SuppressWarnings("unchecked")
	public GroupInfo getGroupInfo(String groupName) throws ServiceException {
		
		if(!StringUtils.isEmpty(groupName)){
			
			
			
			AndFilter andfilter = new AndFilter();
			EqualsFilter objectClassFilter = new EqualsFilter("objectClass", "acornGroup");
			EqualsFilter ouFilter = new EqualsFilter("ou", groupName);
			andfilter.and(objectClassFilter);
			andfilter.and(ouFilter);
			  SearchControls searchCtls = new SearchControls(); // Create the
		      // 创建搜索控制器
		      searchCtls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
		      List<GroupInfo> list =  ldapTemplate.search(SecurityConstants.SEARCH_GROUP_ROOT, andfilter.encode(),  new AttributesMapper() {
								public GroupInfo mapFromAttributes(Attributes attrs)
										throws NamingException {
									
									GroupInfo group = new GroupInfo();
									group.setId(convert(attrs.get("ou").get()));
									group.setName(convert(attrs.get("description")!=null?attrs.get("description").get():""));
									group.setType(convert(attrs.get("acornGroupType")!=null?attrs.get("acornGroupType").get():""));
									group.setAreaCode(convert(attrs.get("acornAreaCode")!=null?attrs.get("acornAreaCode").get():""));
									return group;
								}
			});
					
			
		    if(!list.isEmpty()){
					return (GroupInfo)list.get(0);
			}else{
					return null;
			}
		}else{
			throw new ServiceException(ExceptionConstant.SERVICE_USER_QUERY_EXCEPTION,"部门ID不能为空");
		}
    }
	
	@ReadThroughSingleCache(namespace="com.chinadrtv.erp.security.userservice",expiration=43200)
	public String getGroupDisplayName(@ParameterValueKeyProvider String groupName) throws ServiceException {
		if (!StringUtils.isEmpty(groupName)) {
			AndFilter andfilter = new AndFilter();
			EqualsFilter objectClassFilter = new EqualsFilter("objectClass",
					"acornGroup");
			EqualsFilter ouFilter = new EqualsFilter("ou", groupName);
			andfilter.and(objectClassFilter);
			andfilter.and(ouFilter);
			SearchControls searchCtls = new SearchControls(); // Create the
			// 创建搜索控制器
			searchCtls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
			@SuppressWarnings("unchecked")
			List<GroupInfo> list = ldapTemplate.search(
					SecurityConstants.SEARCH_GROUP_ROOT, andfilter.encode(),
					new AttributesMapper() {
						public GroupInfo mapFromAttributes(Attributes attrs)
								throws NamingException {
							GroupInfo group = new GroupInfo();
							group.setId(convert(attrs.get("ou").get()));
							group.setName(convert(attrs.get("description") != null ? attrs
									.get("description").get() : ""));
							group.setType(convert(attrs.get("acornGroupType") != null ? attrs
									.get("acornGroupType").get() : ""));
							group.setAreaCode(convert(attrs
									.get("acornAreaCode") != null ? attrs.get(
									"acornAreaCode").get() : ""));
							return group;
						}
					});

			if (!list.isEmpty()) {
				return ((GroupInfo) list.get(0)).getName();
			} else {
				return null;
			}
		} else {
			throw new ServiceException(
					ExceptionConstant.SERVICE_USER_QUERY_EXCEPTION, "部门ID不能为空");
		}
	}
	
	@ReadThroughSingleCache(namespace="com.chinadrtv.erp.security.userservice.departmentDisplayName",expiration=3600)
	public String getDepartmentDisplayName(@ParameterValueKeyProvider String deptName) throws ServiceException {
		if (!StringUtils.isEmpty(deptName)) {
			AndFilter andfilter = new AndFilter();
			EqualsFilter objectClassFilter = new EqualsFilter("objectClass",
					"acornDept");
			EqualsFilter ouFilter = new EqualsFilter("ou", deptName);
			andfilter.and(objectClassFilter);
			andfilter.and(ouFilter);
			SearchControls searchCtls = new SearchControls(); // Create the
			// 创建搜索控制器
			searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			@SuppressWarnings("unchecked")
			List<DepartmentInfo> list = ldapTemplate.search(
					SecurityConstants.SEARCH_GROUP_ROOT, andfilter.encode(),
					new AttributesMapper() {
						public DepartmentInfo mapFromAttributes(Attributes attrs)
								throws NamingException {
							DepartmentInfo departmentInfo = new DepartmentInfo();
							departmentInfo.setId(convert(attrs.get("departmentNumber")!=null?attrs.get("departmentNumber").get():""));
							departmentInfo.setName(convert(attrs.get("description")!=null?attrs.get("description").get():""));
							departmentInfo.setAreaCode(convert(attrs.get("acornAreaCode")!=null?attrs.get("acornAreaCode").get():""));
							departmentInfo.setCode(convert(attrs.get("ou").get()));
							return departmentInfo;
						}
					});

			if (!list.isEmpty()) {
				return ((DepartmentInfo) list.get(0)).getName();
			} else {
				return null;
			}
		} else {
			throw new ServiceException(
					ExceptionConstant.SERVICE_USER_QUERY_EXCEPTION, "部门ID不能为空");
		}
	}
	
	 public String convert(Object o){
			if(o==null)
				return null;
			return o.toString();
	 }

	@SuppressWarnings("unchecked")
	public List<String> getManageGroupList(String departmentNum) throws ServiceException {
			
			DepartmentInfo dept = getDepartmentInfo(departmentNum);
			if(dept!=null){
				
				final String deptDn = ldapService.getGroupDn(dept.getCode());
				
				AndFilter andfilter = new AndFilter();
				EqualsFilter objectClassFilter = new EqualsFilter("objectClass", "acornRole");
				andfilter.and(objectClassFilter);
				
				LikeFilter uniqueMemberFilter = new LikeFilter("uniqueMember", "*"+deptDn+","+SecurityConstants.LDAP_ROOT_DN);
				andfilter.and(uniqueMemberFilter);
				
				final Map<String,String> groupsMap = new HashMap<String,String>();
				
				SearchControls searchCtls = new SearchControls(); // Create the
				// 创建搜索控制器
				searchCtls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
				ldapTemplate.search(SecurityConstants.SEARCH_ROLE_ROOT, andfilter.encode(), searchCtls, new AttributesMapper() {
								public String mapFromAttributes(Attributes attrs)
										throws NamingException {
									String role = convert(attrs.get("cn").get()).toUpperCase();
									String group = null;
									if(role.equals(SecurityConstants.ROLE_OUTBOUND_GROUP_MANAGER)
											||role.equals(SecurityConstants.ROLE_OUTBOUND_MANAGER)
											||role.equals(SecurityConstants.ROLE_INBOUND_GROUP_MANAGER)
											||role.equals(SecurityConstants.ROLE_INBOUND_MANAGER)){
										Attribute obj = attrs.get("uniqueMember");
										if(obj!=null){
											for(int i=0;i<obj.size();i++){
												if(obj.get(i).toString().indexOf(deptDn)>-1){
													//group = SecurityHelper.getGroup(obj.get(i).toString());
													groupsMap.put(group,group);
                                                    logger.debug(obj.get(i).toString());
												}

											}
										}
									}
									return group;
								}
				});
				
				List<String> list = new ArrayList<String>();	
				if(!groupsMap.isEmpty()){
					Set<String> set = groupsMap.keySet();
					list.addAll(set);
		      	}
				return list;
			}else{
				throw new ServiceException(ExceptionConstant.SERVICE_USER_QUERY_EXCEPTION,"部门不存在");
			}
	    }
	
	@SuppressWarnings("unchecked")
	public List<DepartmentInfo> getDepartments() throws ServiceException {
			AndFilter andfilter = new AndFilter();
			EqualsFilter objectClassFilter = new EqualsFilter("objectClass", "acornDept");
			andfilter.and(objectClassFilter);
			
//			SearchControls searchCtls = new SearchControls(); // Create the
//			// 创建搜索控制器
//			searchCtls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
			
			List<DepartmentInfo> list =  ldapTemplate.search(SecurityConstants.SEARCH_GROUP_ROOT, andfilter.encode(), 
					new AttributesMapper() {
						public DepartmentInfo mapFromAttributes(Attributes attrs)
								throws NamingException {
							
							DepartmentInfo departmentInfo = new DepartmentInfo();
							departmentInfo.setId(convert(attrs.get("departmentNumber")!=null?attrs.get("departmentNumber").get():""));
							departmentInfo.setName(convert(attrs.get("description")!=null?attrs.get("description").get():""));
							departmentInfo.setCode(convert(attrs.get("ou").get()));
							return departmentInfo;
						}
			});
			
			return list;
    }

	/* (非 Javadoc)
	* <p>Title: getManageUserList</p>
	* <p>Description: </p>
	* @param departmentNum
	* @return
	* @throws ServiceException
	* @see com.chinadrtv.erp.user.service.UserService#getManageUserList(java.lang.String)
	*/ 
	@Override
	public List<AgentUser> getManageUserList(String departmentNum) throws ServiceException {

		List<AgentUser> manageUsers = new ArrayList<AgentUser>();
		List<String> manageGroups = getManageGroupList(departmentNum);
		List<AgentUser> users = null;
		for(String group:manageGroups){
			users = getUserList(group);
			manageUsers.addAll(users);
		}
		return manageUsers;
	}

	/* (非 Javadoc)
	* <p>Title: loginSuccess</p>
	* <p>Description: </p>
	* @param userId
	* @return
	* @see com.chinadrtv.erp.user.service.UserService#loginSuccess(java.lang.String)
	*/ 
	@Override
	public String loginSuccess(String userId)  throws ServiceException{
		String response = null;
//		String isLock = ldapService.searchUserAttributeNoCache(userId, SecurityConstants.LDAP_USER_ATTRIBUTE_LOCK);
//		
//		if(!StringUtils.isEmpty(isLock) && Boolean.valueOf(isLock)){
//			throw new ServiceException("ppolicy.locked");
//		}
		
		String lastTime = ldapService.searchUserAttributeNoCache(userId, SecurityConstants.LDAP_USER_ATTRIBUTE_LASTTIME);
		if(!StringUtils.isEmpty(lastTime)){
			long days = DateUtil.getDateDeviations(lastTime,DateUtil.dateToString(new Date()),  "yyyy-MM-dd");
			if(days>=90){
				response = "ppolicy.expired";
			}
		}else{
//			ldapService.addUserAttribute(userId, SecurityConstants.LDAP_USER_ATTRIBUTE_LASTTIME, DateUtil.dateToString(new Date()));
			response = "ppolicy.change.after.reset";
		}
		
		String maxFailure = ldapService.searchUserAttributeNoCache(userId, SecurityConstants.LDAP_USER_ATTRIBUTE_MAXFAILURE);
		if(!StringUtils.isEmpty(maxFailure)){
			ldapService.updateUserAttribute(userId, SecurityConstants.LDAP_USER_ATTRIBUTE_MAXFAILURE, "0");
		}
		
		return response;
	}

	/* (非 Javadoc)
	* <p>Title: loginFailure</p>
	* <p>Description: </p>
	* @param userId
	* @return
	* @see com.chinadrtv.erp.user.service.UserService#loginFailure(java.lang.String)
	*/ 
	@Override
	public String loginFailure(String userId) throws ServiceException{
		String response = null;
		
//		String dn = ldapService.getUserDn(userId);
//		if(!StringUtils.isEmpty(dn)){
//			String isLock = ldapService.searchUserAttributeNoCache(userId, SecurityConstants.LDAP_USER_ATTRIBUTE_LOCK);
//			
//			if(!StringUtils.isEmpty(isLock) && Boolean.valueOf(isLock)){
//				throw new ServiceException(userId+"ppolicy.locked");
//			}
//			
//			String maxFailure = ldapService.searchUserAttributeNoCache(userId, SecurityConstants.LDAP_USER_ATTRIBUTE_MAXFAILURE);
//			long count =(maxFailure.equals("")) ? 1 : Long.valueOf(maxFailure)+1;
//			if(!StringUtils.isEmpty(maxFailure)){
//				ldapService.updateUserAttribute(userId, SecurityConstants.LDAP_USER_ATTRIBUTE_MAXFAILURE, String.valueOf(count));
//			}else{
//				ldapService.addUserAttribute(userId, SecurityConstants.LDAP_USER_ATTRIBUTE_MAXFAILURE, String.valueOf(count));
//			}
			
//			if(count>=5){
//				ldapService.updateUserAttribute(userId, SecurityConstants.LDAP_USER_ATTRIBUTE_LOCK, "true");
//			}
			
//		}
		
		return response;
	}

	/* (非 Javadoc)
	* <p>Title: getUserAttribute</p>
	* <p>Description: </p>
	* @param uid
	* @param attribute
	* @return
	* @throws ServiceException
	* @see com.chinadrtv.erp.user.service.UserService#getUserAttribute(java.lang.String, java.lang.String)
	*/ 
	@Override
	public String getUserAttribute(String uid, String attribute) throws ServiceException {
		return ldapService.searchUserAttribute(uid, attribute);
	}

    @Override
    public List<AgentUser> findUserByUid(String uid) throws ServiceException {

        if(StringUtil.isNullOrBank(uid)){
            logger.warn("findUserByUid(): uid is null");
            return null;
        }
        AgentUser result = new AgentUser();
        List<AgentUser> list2= new ArrayList();

        AndFilter andfilter = new AndFilter();

        EqualsFilter objectClassFilter = new EqualsFilter("uid", uid);
        andfilter.and(objectClassFilter);

        List<AgentUser> list =  ldapTemplate.search(SecurityConstants.SEARCH_SEAT_ROOT, andfilter.encode(),
                SearchControls.SUBTREE_SCOPE,
                new AttributesMapper() {
                    public AgentUser mapFromAttributes(Attributes attrs)
                            throws NamingException {
                        AgentUser agentUser = new AgentUser();
                        agentUser.setUserId(convert(attrs.get("uid")!=null?attrs.get("uid").get():""));
                        agentUser.setDisplayName(convert(attrs.get("displayName")!=null?attrs.get("displayName").get():""));
                        agentUser.setDepartment(convert(attrs.get("departmentNumber").get()));
                        agentUser.setDefGrp(convert(attrs.get("dn")));

                        return agentUser;
                    }
                });

        if(list != null){
            if(list.size()>0){
                result= list.get(0);
                result.setDefGrp(findUserGroupByUid(uid));
                list2.add(result);
            }
        }

        return list2;

    }




    public List<AgentUser> findUserLikeUid(String uid) throws ServiceException {

        if(StringUtil.isNullOrBank(uid)){
            logger.warn("findUserByUid(): uid is null");
            return null;
        }
        List<AgentUser> list2= new ArrayList();

        AndFilter andfilter = new AndFilter();

        WhitespaceWildcardsFilter objectClassFilter = new WhitespaceWildcardsFilter("uid", uid);
        andfilter.and(objectClassFilter);

        List<AgentUser> list =  ldapTemplate.search(SecurityConstants.SEARCH_SEAT_ROOT, andfilter.encode(),
                SearchControls.SUBTREE_SCOPE,
                new AttributesMapper() {
                    public AgentUser mapFromAttributes(Attributes attrs)
                            throws NamingException {
                        AgentUser agentUser = new AgentUser();
                        agentUser.setUserId(convert(attrs.get("uid")!=null?attrs.get("uid").get():""));
                        agentUser.setDisplayName(convert(attrs.get("displayName")!=null?attrs.get("displayName").get():""));
                        agentUser.setDepartment(convert(attrs.get("departmentNumber").get()));
                        agentUser.setDefGrp(convert(attrs.get("dn")));

                        return agentUser;
                    }
                });

        if(list != null){
            if(list.size()>0){
                for(AgentUser au :list){
                    au.setDefGrp(findUserGroupByUid(au.getUserId()));
                    list2.add(au);
                }


            }
        }

        return list2;

    }




    @Override
    public List<GroupInfo> getAllGroup() throws ServiceException {
        AndFilter andfilter = new AndFilter();
        EqualsFilter objectClassFilter = new EqualsFilter("objectclass", "acornGroup");
        andfilter.and(objectClassFilter);

        List<GroupInfo> list =  ldapTemplate.search(SecurityConstants.SEARCH_SEAT_ROOT, andfilter.encode(),
                SearchControls.SUBTREE_SCOPE,
                new AttributesMapper() {
                    public GroupInfo mapFromAttributes(Attributes attrs)
                            throws NamingException {
                        GroupInfo groupInfo = new GroupInfo();
                        groupInfo.setId(convert(attrs.get("ou").get()));
                        groupInfo.setName(convert(attrs.get("description")!=null?attrs.get("description").get():""));
                        return groupInfo;
                    }
                });

        return list;

    }


    public String findUserGroupByUid(String uid){


        String result="";
        AndFilter andfilter = new AndFilter();

        EqualsFilter objectClassFilter = new EqualsFilter("uid", uid);
        andfilter.and(objectClassFilter);

       List<Name> list =ldapTemplate.search(SecurityConstants.SEARCH_SEAT_ROOT, andfilter.encode(),
                SearchControls.SUBTREE_SCOPE, new ContextMapper() {
            public Object mapFromContext(Object ctx) {
                DirContextAdapter context = (DirContextAdapter)ctx;
                logger.debug(String.valueOf(context.getDn()));;
                return context.getDn();
            }});

        if(list != null){
            if(list.size()>0){
            return list.get(0).get(3).split("=")[1];
            }
        }
        return null;

    }
    
    public boolean releaseLock(String userId) throws ServiceException {
    	boolean isSuc = true;
    	try {
	    	String isLock = ldapService.searchUserAttributeNoCache(userId, SecurityConstants.LDAP_USER_ATTRIBUTE_LOCK);
	    	if(StringUtils.isNotBlank(isLock)) {
	    		ldapService.removeUserAttribute(userId, SecurityConstants.LDAP_USER_ATTRIBUTE_LOCK);
	    		ldapService.removeUserAttribute(userId, SecurityConstants.LDAP_USER_ATTRIBUTE_MAXFAILURE);
	    	}
    	} catch(Exception e) {
    		isSuc = false;
    	}
    	return isSuc;
    }

	/** 
	 * <p>Title: getUserById</p>
	 * <p>Description: </p>
	 * @param userId
	 * @return
	 * @throws ServiceException
	 * @see com.chinadrtv.erp.user.service.UserService#getUserById(java.lang.String)
	 */ 
	@Override
	public AgentUser getUserById(String userId) throws ServiceException {
		return null;
	}

	/** 
	 * <p>Title: getRoleByName</p>
	 * <p>Description: </p>
	 * @param roleName
	 * @return
	 * @throws ServiceException
	 * @see com.chinadrtv.erp.user.service.UserService#getRoleByName(java.lang.String)
	 */ 
	@Override
	public AcornRole getRoleByName(String roleName) throws ServiceException {
		return null;
	}

	/** 
	 * <p>Title: login</p>
	 * <p>Description: </p>
	 * @param userId
	 * @param password
	 * @return
	 * @throws ServiceException
	 * @see com.chinadrtv.erp.user.service.UserService#login(java.lang.String, java.lang.String)
	 */ 
	@Override
	public AgentUser login(String userId, String password) throws ServiceException {
		return null;
	}

	@Override
	public Map<String, Object> resetUserPassword(String userId, String password) throws ServiceException {
		return null;
	}

	@Override
	public List<String> getUserIdListByDept(String deptCode) throws Exception {
		return null;
	}

}
