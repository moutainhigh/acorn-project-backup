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
import com.chinadrtv.erp.model.agent.Grp;
import com.chinadrtv.erp.user.dao.AgentUserDao;
import com.chinadrtv.erp.user.dao.GrpDao;
import com.chinadrtv.erp.user.ldap.AcornGroup;
import com.chinadrtv.erp.user.ldap.AcornUser;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.service.LdapService;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.ldap.AttributeInUseException;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.security.authentication.encoding.LdapShaPasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.SpringSecurityLdapTemplate;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.stereotype.Service;

import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.directory.*;
import java.util.List;
import java.util.Set;

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
//@Service("ldapService")
@Deprecated
public class LdapServiceImpl implements LdapService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(LdapServiceImpl.class);

	@Autowired
	private SpringSecurityLdapTemplate ldapTemplate;
	
	@Autowired
	private GrpDao grpDao;
	
	@Autowired
	private AgentUserDao userDAO;
	

	/* (非 Javadoc)
	* <p>Title: searchAttribute</p>
	* <p>Description: </p>
	* @param base
	* @param filter
	* @param param
	* @param attribute
	* @return
	* @throws ServiceException
	* @see com.chinadrtv.erp.user.service.LdapService#searchAttribute(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	*/ 
	@Override
	@ReadThroughSingleCache(namespace="com.chinadrtv.erp.user.service.ldap.impl",expiration=600)
	public String searchAttribute(@ParameterValueKeyProvider(order=0) String base, @ParameterValueKeyProvider(order=1) String filter, @ParameterValueKeyProvider(order=2) String param, @ParameterValueKeyProvider(order=3) String attribute)
			throws ServiceException {
		return searchAttributeNoCache( base, filter,  param, attribute);
	}
	
	public String searchAttributeNoCache(String base,String filter, String param,String attribute)
			throws ServiceException {
		String value = "";
		Set<String> result = ldapTemplate.searchForSingleAttributeValues(base, filter, new String[] {param}, attribute);
		if(!result.isEmpty()){
			value = result.toArray()[0].toString();
		}
		return value;
	}


	/* (非 Javadoc)
	* <p>Title: changePassword</p>
	* <p>Description: </p>
	* @param userId
	* @param oldPassword
	* @param newPassWord
	* @throws ServiceException
	* @see com.chinadrtv.erp.user.service.LdapService#changePassword(java.lang.String, java.lang.String, java.lang.String)
	*/ 
	@Override
	public void changePassword(String dn, String newPassWord,String oldPassWord)
			throws ServiceException {
		Attribute newPasswordAttribute = new BasicAttribute( "userPassword", newPassWord);
		ModificationItem newPassword = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, newPasswordAttribute);
		ldapTemplate.modifyAttributes(dn, new ModificationItem[]{newPassword});
	}
	
	public void changePasswordByRebind(String dn, String newPassWord,String oldPassWord)
			throws ServiceException {
		Attribute newPasswordAttribute = new BasicAttribute( "userPassword", newPassWord);
		 Attributes attrs = new BasicAttributes();
		 attrs.put(newPasswordAttribute);
//		ModificationItem newPassword = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, newPasswordAttribute);
		ldapTemplate.rebind(dn, null, attrs);
		logger.debug("------");
	}
	
	
	@Override
	public boolean checkPassword(String uId, String password)throws ServiceException {
		try{
			ldapTemplate.getContextSource().getContext(uId, password);
		}catch (Exception e) {
			return false;
		}
		return true;
	}

	private Attributes buildAttributes(final String password) {

	     Attributes attrs = new BasicAttributes();

	     LdapShaPasswordEncoder passwordEncoder  = new LdapShaPasswordEncoder();
	     attrs.put("userPassword", passwordEncoder.encodePassword(password, null));

	     return attrs;

	    }


	/* (非 Javadoc)
	* <p>Title: searchGroupAttribute</p>
	* <p>Description: </p>
	* @param param
	* @param attribute
	* @return
	* @throws ServiceException
	* @see com.chinadrtv.erp.user.service.LdapService#searchGroupAttribute(java.lang.String, java.lang.String)
	*/ 
	@Override
	@ReadThroughSingleCache(namespace="com.chinadrtv.erp.user.service.impl.searchGroupAttribute",expiration=600)
	public String searchGroupAttribute(@ParameterValueKeyProvider(order=0)String param, @ParameterValueKeyProvider(order=1)String attribute) throws ServiceException {
		return searchAttribute(SecurityConstants.SEARCH_GROUP_ROOT, 
					SecurityConstants.SEARCH_GROUP_FILTER, 
					param, 
					attribute);
	}


	/* (非 Javadoc)
	* <p>Title: searchUserAttribute</p>
	* <p>Description: </p>
	* @param param
	* @param attribute
	* @return
	* @throws ServiceException
	* @see com.chinadrtv.erp.user.service.LdapService#searchUserAttribute(java.lang.String, java.lang.String)
	*/ 
	@Override
	@ReadThroughSingleCache(namespace="com.chinadrtv.erp.user.service.impl.searchUserAttribute",expiration=600)
	public String searchUserAttribute(@ParameterValueKeyProvider(order=0)String param, @ParameterValueKeyProvider(order=1)String attribute) throws ServiceException {
		return searchUserAttributeNoCache( param,  attribute);
	}
	
	public String searchUserAttributeNoCache(String param, String attribute) throws ServiceException {
		return searchAttribute(SecurityConstants.SEARCH_GROUP_ROOT, 
				SecurityConstants.SEARCH_USER_FILTER, 
				param, 
				attribute);
	}

	public String getGroupDn(String groupCode){
		try{
			DirContextOperations dir =  ldapTemplate.searchForSingleEntry(SecurityConstants.SEARCH_GROUP_ROOT, SecurityConstants.SEARCH_GROUP_FILTER, new String[] {groupCode});
		
			if(dir!=null){
				return dir.getDn().toString();
			}else{
				return "";
			}
		}catch (Exception e) {
			return "";
		}
	}
	
	public String getRoleDn(String role){
		
		return "cn="+role.toLowerCase()+",ou=roles";
/*		DirContextOperations dir =  ldapTemplate.searchForSingleEntry(SecurityConstants.SEARCH_ROLE_ROOT, SecurityConstants.SEARCH_ROLE_FILTER, new String[] {role});
		
		if(dir!=null){
			return dir.getDn().toString();
		}else{
			return "";
		}*/
	}

	public String getUserDn(String uid){
		
		try{
			DirContextOperations dir =  ldapTemplate.searchForSingleEntry(SecurityConstants.SEARCH_GROUP_ROOT, SecurityConstants.SEARCH_USER_FILTER, new String[] {uid});
			
			if(dir!=null){
				return dir.getDn().toString();
			}else{
				return "";
			}
		}catch (Exception e) {
			return "";
		}
		
	}
	
	
	/* (非 Javadoc)
	* <p>Title: create</p>
	* <p>Description: </p>
	* @param user
	* @see com.chinadrtv.erp.user.service.UserService#create(com.chinadrtv.erp.user.ldap.AcornUser)
	*/ 
	@Override
	public void createUser(AcornUser user)   throws ServiceException {

		try {
			DirContextAdapter context = new DirContextAdapter(buildDn(user));
			 mapToContext(user, context);
		      ldapTemplate.bind(context);
		} catch (InvalidNameException e) {
			logger.error(e.getMessage(),e);
			throw new ServiceException(ExceptionConstant.SERVICE_USER_QUERY_EXCEPTION,"用户添加失败");
		}
	     
		
	}

	/* (非 Javadoc)
	* <p>Title: update</p>
	* <p>Description: </p>
	* @param user
	* @see com.chinadrtv.erp.user.service.UserService#update(com.chinadrtv.erp.user.ldap.AcornUser)
	*/ 
	@Override
	public void updateUser(AcornUser user) {
		
	}

	/* (非 Javadoc)
	* <p>Title: delete</p>
	* <p>Description: </p>
	* @param user
	* @see com.chinadrtv.erp.user.service.UserService#delete(com.chinadrtv.erp.user.ldap.AcornUser)
	*/ 
	@Override
	public void deleteUser(AcornUser user) {
		
	}
	
	protected Name buildDn(AcornUser user) throws InvalidNameException {
	      return buildDn(user.getUid(), user.getGroup());
	 }

   protected Name buildDn(String fullname, String group) throws InvalidNameException {
	   
	   DirContextOperations dir =  ldapTemplate.searchForSingleEntry(SecurityConstants.SEARCH_GROUP_ROOT, SecurityConstants.SEARCH_GROUP_FILTER, new String[] {group});
	   
	   DistinguishedName dn = null;
	   if(dir!=null){
		   logger.debug(dir.getDn()!=null?dir.getDn().toString():"");
		  dn = new DistinguishedName(dir.getDn());
	      dn.add("uid", fullname);
	   }
     
      return dn;
   }
   
   protected Name buildDnForGroup(String departmentNum,String group) throws InvalidNameException {
	   AndFilter andfilter = new AndFilter();
	   EqualsFilter objectClassFilter = new EqualsFilter("objectClass", "acornDept");
		EqualsFilter deptNumfilter = new EqualsFilter("departmentNumber", departmentNum);
		andfilter.and(objectClassFilter);
		andfilter.and(deptNumfilter);
		DirContextOperations dir = ldapTemplate.searchForSingleEntry(SecurityConstants.SEARCH_GROUP_ROOT, andfilter.encode(), null);
	   
	   DistinguishedName dn = null;
	   if(dir!=null){
		  dn = new DistinguishedName(dir.getDn());

//	      dn.add(dir.getDn().toString());
	      dn.add("ou", group);
	   }
     
      return dn;
   }
   
   protected void mapToContext(AcornUser user, DirContextOperations context) {
	      context.setAttributeValues("objectclass", new String[] {"top", "person","organizationalPerson", "inetOrgPerson", "acornUser"});
	      context.setAttributeValue("cn", "SHA Password User");
	      context.setAttributeValue("sn", "User");
	      context.setAttributeValue("departmentNumber", user.getDepartmentNumber());
	      context.setAttributeValue("description", "A SHA Password inetOrgPerson");
	      context.setAttributeValue("displayName", user.getDisplayName());
	      context.setAttributeValue("employeeType", user.getEmployeeType());
	      ShaPasswordEncoder encoder = new ShaPasswordEncoder();
	      String password  = user.getUserPassword();
	      if(!StringUtils.isEmpty(password)){
	    	  context.setAttributeValue("userPassword", ""+password);
	      }
	      
   }
   
   protected void mapToContextGroup(AcornGroup group, DirContextOperations context) {
	      context.setAttributeValues("objectclass", new String[] {"top", "organizationalUnit","acornGroup"});
	      context.setAttributeValue("acornAreaCode", group.getAcornAreaCode());
	      context.setAttributeValue("acornGroupType", group.getAcornGroupType());
	      context.setAttributeValue("description", group.getDescription());
   }
	 
	public static void main(String arg[]){
		LdapShaPasswordEncoder passwordEncoder  = new LdapShaPasswordEncoder();
		String encodedPassword = passwordEncoder.encodePassword("234567", "234567".getBytes());
		System.out.println(encodedPassword);
	}


	/* (非 Javadoc)
	* <p>Title: createGroup</p>
	* <p>Description: </p>
	* @param group
	* @throws ServiceException
	* @see com.chinadrtv.erp.user.service.LdapService#createGroup(com.chinadrtv.erp.user.ldap.AcornGroup)
	*/ 
	@Override
	public void createGroup(AcornGroup group) throws ServiceException {
		try {
			DirContextAdapter context = new DirContextAdapter(buildDnForGroup(group.getDepartmentNumber(),group.getName()));
			mapToContextGroup(group, context);
		      ldapTemplate.bind(context);
		} catch (InvalidNameException e) {
			logger.error(e.getMessage(),e);
			throw new ServiceException(ExceptionConstant.SERVICE_USER_QUERY_EXCEPTION,"用户添加失败");
		}
		
	}


	/* (非 Javadoc)
	* <p>Title: updateGroup</p>
	* <p>Description: </p>
	* @param group
	* @throws ServiceException
	* @see com.chinadrtv.erp.user.service.LdapService#updateGroup(com.chinadrtv.erp.user.ldap.AcornGroup)
	*/ 
	@Override
	public void updateGroup(AcornGroup group) throws ServiceException {
		// TODO Auto-generated method stub
		
	}


	/* (非 Javadoc)
	* <p>Title: deleteGroup</p>
	* <p>Description: </p>
	* @param group
	* @throws ServiceException
	* @see com.chinadrtv.erp.user.service.LdapService#deleteGroup(com.chinadrtv.erp.user.ldap.AcornGroup)
	*/ 
	@Override
	public void deleteGroup(AcornGroup group) throws ServiceException {
		// TODO Auto-generated method stub
		
	}


	/* (非 Javadoc)
	* <p>Title: assignUser</p>
	* <p>Description: </p>
	* @param username
	* @param groupName
	* @throws ServiceException
	* @see com.chinadrtv.erp.user.service.LdapService#assignUser(java.lang.String, java.lang.String)
	*/ 
	@Override
	public void assignUser(String username, String role) throws ServiceException {
		try {
            ModificationItem[] mods = new ModificationItem[1];

            Attribute mod = new BasicAttribute("uniqueMember",getUserDn(username)+","+SecurityConstants.LDAP_ROOT_DN);
            mods[0] =new ModificationItem(DirContext.ADD_ATTRIBUTE, mod);
            logger.debug(role+"角色");
            logger.debug(getRoleDn(role));
            ldapTemplate.modifyAttributes(getRoleDn(role), mods);
        } catch (AttributeInUseException e) {
            // If user is already added, ignore exception
        }
		
	}


	/* (非 Javadoc)
	* <p>Title: creatAllGroup</p>
	* <p>Description: </p>
	* @param departmentNum
	* @see com.chinadrtv.erp.user.service.LdapService#creatAllGroup(java.lang.String)
	*/ 
	@Override
	public void creatAllGroup(String departmentNum) {
		List<Grp> grpList = grpDao.getGrpList(departmentNum);
		
		AcornGroup acornGroup = null;
		try {
			for(Grp grp:grpList){
				if(StringUtils.isEmpty(getGroupDn(grp.getGrpid()))){
					acornGroup = new AcornGroup();
					acornGroup.setAcornAreaCode(grp.getAreacode());
					acornGroup.setAcornGroupType(grp.getType());
					acornGroup.setDepartmentNumber(grp.getDept());
					acornGroup.setDescription(grp.getGrpname());
					acornGroup.setName(grp.getGrpid());
					
					createGroup(acornGroup);
				}else{
					logger.warn(grp.getGrpid()+"已经存在");
				}
			}
		} catch (ServiceException e) {
            logger.error(acornGroup.getName()+":"+e.getMessage(), e);
		}
	}


	/* (非 Javadoc)
	* <p>Title: creatAllUser</p>
	* <p>Description: </p>
	* @param departmentNum
	* @see com.chinadrtv.erp.user.service.LdapService#creatAllUser(java.lang.String)
	*/ 
	@Override
	public void creatAllUser(String departmentNum) {
		List<Grp> grpList = grpDao.getGrpList(departmentNum);
		AcornUser acornUser = null;
		
		try {
			for(Grp grp:grpList){
				boolean isManger = grpDao.checkGrpMangerRole(grp.getGrpid());
				List<AgentUser> users = userDAO.queryAgentUserByGroup(grp.getGrpid());
				for(AgentUser user:users){
					if(StringUtils.isEmpty(getUserDn(user.getUserId()))){
						acornUser = new AcornUser();
						
						acornUser.setDepartmentNumber(grp.getDept());
						acornUser.setEmployeeType(getEmployeeType(user.getUserId()));
						acornUser.setGroup(grp.getGrpid());
						acornUser.setUid(user.getUserId());
						acornUser.setUserPassword(user.getPassword());
						acornUser.setDisplayName(user.getName());
						
						createUser(acornUser);
						
						assignRole(user.getUserId(),grp.getType(),isManger);
                        logger.debug(user.getUserId()+"添加成功");
					}else{
                        logger.debug(user.getUserId()+"已经存在");
					}
				}
			}
		} catch (ServiceException e) {
            logger.error(e.getMessage(),e);
		}
	}
	
	public String getEmployeeType(String userId){
		String type = userDAO.queryAgentUserType(userId);
		if(!StringUtils.isEmpty(type)){
			if(type.equals("1")){
				type="SVIP";
			}else if(type.equals("2")){
				type="VIP";
			}else if(type.equals("3")){
				type="YOU";
			}else if(type.equals("4")){
				type="YOU";
			}else if(type.equals("5")){
				type="LIANG";
			}else if(type.equals("6")){
				type="LIANG";
			}else if(type.equals("7")){
				type="ZHONG";
			}else if(type.equals("99")){
				type="CHANGGUI";
			}else{
				type="--";
			}
			
		}else{
			type="--";
		}
		
		return type;
	}
	public void assignRole(String userName,String groupType,boolean isManager) throws ServiceException{
		String roleName = SecurityConstants.ROLE_OTHER;
		if(!StringUtils.isEmpty(groupType)){
			groupType = groupType.toUpperCase();
			if(groupType.equals("OUT")){
				if(isManager){
					roleName = SecurityConstants.ROLE_OUTBOUND_MANAGER;
				}else{
					roleName = SecurityConstants.ROLE_OUTBOUND_AGENT;
				}
			}else if(groupType.equals("IN")){
				if(isManager){
					roleName = SecurityConstants.ROLE_INBOUND_MANAGER;
				}else{
					roleName = SecurityConstants.ROLE_INBOUND_AGENT;
				}
			}
		}
		
		assignUser(userName, roleName);
	}


	/* (非 Javadoc)
	* <p>Title: updateUserAttribute</p>
	* <p>Description: </p>
	* @param userId
	* @param attr
	* @param value
	* @throws ServiceException
	* @see com.chinadrtv.erp.user.service.LdapService#updateUserAttribute(java.lang.String, java.lang.String, java.lang.String)
	*/ 
	@Override
	public void updateUserAttribute(String userId, String attr, String value)
			throws ServiceException {
		Attribute attribute = new BasicAttribute( attr, value);
		ModificationItem item = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attribute);
		ldapTemplate.modifyAttributes(getUserDn(userId), new ModificationItem[]{item});
		
	}
	
	public void removeUserAttribute(String userId, String attr) {
		Attribute attribute = new BasicAttribute( attr);
		ModificationItem item = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, attribute);
		ldapTemplate.modifyAttributes(getUserDn(userId), new ModificationItem[]{item});
	}
	
	@Override
	public void addUserAttribute(String userId, String attr, String value)
			throws ServiceException {
		Attribute attribute = new BasicAttribute( attr, value);
		ModificationItem item = new ModificationItem(DirContext.ADD_ATTRIBUTE, attribute);
		ldapTemplate.modifyAttributes(getUserDn(userId), new ModificationItem[]{item});
		
	}

    @Override
    public LdapUserDetails findUserByUid(String userId) throws ServiceException {
        if (logger.isDebugEnabled()) {
            logger.debug("Searching for user '" + userId + "', with user search "
                    + this.toString());
        }

        try {
            LdapUserDetailsImpl.Essence user = (LdapUserDetailsImpl.Essence) ldapTemplate.searchForSingleEntry(SecurityConstants.SEARCH_SEAT_ROOT,
                    SecurityConstants.SEARCH_GROUP_FILTER, new String[] {userId});
            return user.createUserDetails();
        } catch (IncorrectResultSizeDataAccessException notFound) {
            if (notFound.getActualSize() == 0) {
                throw new UsernameNotFoundException("User " + userId + " not found in directory.");
            }
            // Search should never return multiple results if properly configured, so just rethrow
            throw notFound;
        }



    }
}
