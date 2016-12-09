package com.chinadrtv.erp.user.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.chinadrtv.erp.constant.SecurityConstants;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.user.ldap.AcornRole;
import com.chinadrtv.erp.user.model.AgentRole;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.util.SpringUtil;
import com.chinadrtv.erp.util.StringUtil;

/**
 * User: liuhaidong
 * Date: 12-11-26
 */
public class SecurityHelper{
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SecurityHelper.class);
	
	private static class OrderComparator implements Comparator<AgentRole>, Serializable {

		private static final long serialVersionUID = -6479004216876370873L;
		
		@Override
		public int compare(AgentRole o1, AgentRole o2) {
			if(o1.getPriority()!=null && o2.getPriority()!=null){
				return o1.getPriority() - o2.getPriority();
			}
			return 0;
		}
		
	}
    /**
     * Returns the  login user of current Request Session .
     * @param AgentUser
     * @return The {@code AgentUser} agentUser of Request, NULL if there is no user login.
     */
	public static AgentUser getLoginUser() {
    	AgentUser user = null;
        if (SecurityContextHolder.getContext() == null || SecurityContextHolder.getContext().getAuthentication() == null) {
        	logger.warn("SecurityContextHolder.getContext() is null");
            return null;
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if (principal instanceof String && principal.equals("anonymousUser")) {
        	 logger.warn("user is anonymousUser");
            return null;
        }
        
        
        if(principal instanceof AgentUser){
        	user = (AgentUser) principal;
        	
        	Collection<? extends GrantedAuthority> roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        	//user.setAgentRoles(roles);
        	Set<AgentRole> agentRoles = new TreeSet<AgentRole>(new OrderComparator());
        	
        	Iterator<? extends GrantedAuthority> it = roles.iterator();
        	AgentRole role = null;
        	SimpleGrantedAuthority simpleGrantedAuth = null;
        	
        	while(it.hasNext()){
        		simpleGrantedAuth = (SimpleGrantedAuthority)it.next();
        		role = new AgentRole();
        		AcornRole acornRole = getRole(simpleGrantedAuth.getAuthority());
        		String priority = null == acornRole.getAcornPriority() ? "0" : acornRole.getAcornPriority();
        		Integer prrorityInt = Integer.parseInt("".equals(priority) ? "0" : priority);
        		role.setPriority(prrorityInt);
        		role.setRoleName(simpleGrantedAuth.getAuthority());
        		role.setDescription(acornRole.getDescription());
        		agentRoles.add(role);
        	}
        	user.setAgentRoles(agentRoles);
        }else{
        	user = (AgentUser) principal;
        }
        
        user = (AgentUser) principal;
        	
        return user;
    }
    
    public static AcornRole getRole(String roleName) {
    	UserService userService = (UserService) SpringUtil.getBean("userService");
    	AcornRole role = null;
		try {
			role = userService.getRoleByName(roleName);
		} catch (ServiceException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
    	return role;
    }
    
    public static Integer getRolePriority(String roleName){
    	Integer priority = 1000;
    	
    	UserService userService = (UserService) SpringUtil.getBean("userService");
    			
    	try {
    		AcornRole role = userService.getRoleByName(roleName);
    		String result = role.getAcornPriority(); 
			if(!StringUtil.isNullOrBank(result)){
				priority = Integer.parseInt(result);
			}
		} catch (ServiceException e) {
            logger.error(e.getMessage(), e);
		}
    	
    	return priority;
    }
    
/*    public static String getRoleDesc(String roleName){
    	String desc = "";
    	
    	//LdapService ldapService = (LdapService)SpringUtil.getBean("ldapService");
    	UserService userService = (UserService) SpringUtil.getBean("userService");
    	
    	try {
			String result = ldapService.searchAttribute(SecurityConstants.SEARCH_ROLE_ROOT, 
					SecurityConstants.SEARCH_ROLE_FILTER, 
					roleName, 
					SecurityConstants.LDAP_ROLE_ATTRIBUTE_DESC);
  
    		AgentRole role = userService.getRoleByCode(roleName);
    		String result = "";//String.valueOf(role.getDescription());
			if(!StringUtil.isNullOrBank(result)){
				desc = result;
			}
		} catch (ServiceException e) {
            logger.error(e.getMessage(), e);
		}
    	
    	return desc;
    }*/
    
    public static boolean isManager(AgentUser user) {
		boolean isOutBoundDepartmentManager = user.hasRole(SecurityConstants.ROLE_OUTBOUND_MANAGER);
		boolean isInBoundDepartmentManager = user.hasRole(SecurityConstants.ROLE_INBOUND_MANAGER);
		boolean isOutBoundGroupManager = user.hasRole(SecurityConstants.ROLE_OUTBOUND_GROUP_MANAGER);
		boolean isInBoundGroupManager =user.hasRole(SecurityConstants.ROLE_INBOUND_GROUP_MANAGER);
		return (isOutBoundDepartmentManager || isInBoundDepartmentManager ||isOutBoundGroupManager || isInBoundGroupManager);
    }
    
    public static int getUserPriority(AgentUser user) {
    	int createUserPriority = 0;
		boolean isOutBoundDepartmentManager = user.hasRole(SecurityConstants.ROLE_OUTBOUND_MANAGER);
		boolean isInBoundDepartmentManager = user.hasRole(SecurityConstants.ROLE_INBOUND_MANAGER);
		boolean isOutBoundGroupManager = user.hasRole(SecurityConstants.ROLE_OUTBOUND_GROUP_MANAGER);
		boolean isInBoundGroupManager =user.hasRole(SecurityConstants.ROLE_INBOUND_GROUP_MANAGER);
		if(isOutBoundDepartmentManager) {
			createUserPriority = SecurityHelper.getRolePriority(SecurityConstants.ROLE_OUTBOUND_MANAGER);
		} else if(isInBoundDepartmentManager){
			createUserPriority = SecurityHelper.getRolePriority(SecurityConstants.ROLE_INBOUND_MANAGER);
		} else if(isOutBoundGroupManager) {
			createUserPriority = SecurityHelper.getRolePriority(SecurityConstants.ROLE_OUTBOUND_GROUP_MANAGER);
		} else if(isInBoundGroupManager){
			createUserPriority = SecurityHelper.getRolePriority(SecurityConstants.ROLE_INBOUND_GROUP_MANAGER);
		}
		return createUserPriority;
    }

    /**
     * Returns If there is a user login session.
     *
     * @return <tt>true</tt> if there is a user login session, otherwise
     * <tt>false</tt>
     */
    public static boolean isLogin() {
        return SecurityContextHolder.getContext() == null ||
                SecurityContextHolder.getContext().getAuthentication() == null ||
                SecurityContextHolder.getContext().getAuthentication().getPrincipal() == null;
    }
    
/*    public static String getGroup(String dn){
    
    	String result = "";
    	Pattern pattern = Pattern.compile("ou=([A-Za-z0-9]+)");
		Matcher matcher = pattern.matcher(dn);
		if(matcher.find()){
			result = matcher.group(1);
		}
		
		return result;
    }*/
    
/*    public static String getDepartment(String dn){
        
    	String result = "";
    	Pattern pattern = Pattern.compile("ou=([A-Za-z0-9]+_[A-Za-z0-9]+)");
		Matcher matcher = pattern.matcher(dn);
		if(matcher.find()){
			result = matcher.group(1);
		}
		return result;
    }*/
    
}
