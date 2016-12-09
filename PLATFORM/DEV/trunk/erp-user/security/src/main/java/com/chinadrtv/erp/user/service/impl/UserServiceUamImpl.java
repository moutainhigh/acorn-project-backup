package com.chinadrtv.erp.user.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.NamingException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.user.dao.AgentUserDao;
import com.chinadrtv.erp.user.dto.DepartmentDto;
import com.chinadrtv.erp.user.dto.LoginDto;
import com.chinadrtv.erp.user.dto.RoleDto;
import com.chinadrtv.erp.user.dto.UserDto;
import com.chinadrtv.erp.user.dto.UserGroupDto;
import com.chinadrtv.erp.user.ldap.AcornRole;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.model.DepartmentInfo;
import com.chinadrtv.erp.user.model.GroupInfo;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.chinadrtv.erp.util.DateUtil;
import com.chinadrtv.erp.util.PojoUtils;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughAssignCache;
import com.google.code.ssm.api.ReadThroughSingleCache;

/**
 * Created by dengqianyong on 2014/4/9.
 */
@Service("userService")
public class UserServiceUamImpl implements UserService {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UserServiceUamImpl.class);
    
	public static final String NAMESPACE = "SALES_USER_SERVICE_com.chinadrtv.erp.user.service.impl.UserServiceUamImpl";
	
	//设置有效时间为30分钟
	public static final int EXPIRATION = 1800;

    @Autowired
    private AgentUserDao userDao;
    @Autowired
    @Qualifier("payRestTemplate")
    private RestTemplate restTemplate;
 
    @Value("${UAM_URL}")
	private String uamUrl;

    @Override
    @ReadThroughSingleCache(namespace = NAMESPACE + ".getUserGroup", expiration = EXPIRATION)
    public String getUserGroup(@ParameterValueKeyProvider String userName) throws ServiceException {
    	ResponseEntity<UserDto> response = null;
    	try {
    		response = restTemplate.getForEntity(uamUrl + "user/ldap/" + userName , UserDto.class, userName);
		} catch (RestClientException e) {
			logger.error("getUserGroup", e);
		}
    	
    	if(null ==response || response.getStatusCode().value() != 200) {
    		throw new ServiceException("调用UAM请求错误");
    	}
    	
    	UserDto userDto = response.getBody();
    	UserGroupDto userGroupDto = userDto.getUserGroup();
        return null == userGroupDto ? null : userGroupDto.getSourceId();
    }

    @Override
    @ReadThroughSingleCache(namespace = NAMESPACE + ".getDepartment", expiration = EXPIRATION)
    public String getDepartment(@ParameterValueKeyProvider String userName) throws ServiceException, NamingException {
    	ResponseEntity<UserDto> response = null;
    	try {
    		response = restTemplate.getForEntity(uamUrl + "user/ldap/" + userName , UserDto.class);
		} catch (RestClientException e) {
			logger.error("getUserGroup", e);
		}
    	
    	if(null ==response || response.getStatusCode().value() != 200) {
    		throw new ServiceException("调用UAM请求错误");
    	}
    	
    	UserDto userDto = response.getBody();
    	UserGroupDto userGroupDto = userDto.getUserGroup();
    	if(null == userGroupDto) {
    		return null;
    	}
    	
    	DepartmentDto department = userGroupDto.getDepartment();
        return null == department ? null : department.getSourceId();
    }

    @Override
    @ReadThroughSingleCache(namespace = NAMESPACE + ".getGroupAreaCode", expiration = EXPIRATION)
    public String getGroupAreaCode(@ParameterValueKeyProvider String userName) throws ServiceException {
    	ResponseEntity<UserDto> response = null;
    	try {
    		response = restTemplate.getForEntity(uamUrl + "user/ldap/" + userName , UserDto.class);
		} catch (RestClientException e) {
			logger.error("getUserGroup", e);
		}
    	
    	if(null ==response || response.getStatusCode().value() != 200) {
    		throw new ServiceException("调用UAM请求错误");
    	}
    	
    	UserDto userDto = response.getBody();
    	UserGroupDto userGroupDto = userDto.getUserGroup();
        return null == userGroupDto ? null : userGroupDto.getAcornAreaCode();
    }

    @Override
    @ReadThroughSingleCache(namespace = NAMESPACE + ".getGroupType", expiration = EXPIRATION)
    public String getGroupType(@ParameterValueKeyProvider String userName) throws ServiceException {
    	ResponseEntity<UserDto> response = null;
    	try {
    		response = restTemplate.getForEntity(uamUrl + "user/ldap/" + userName , UserDto.class);
		} catch (RestClientException e) {
			logger.error("getUserGroup", e);
		}
    	
    	if(null ==response || response.getStatusCode().value() != 200) {
    		throw new ServiceException("调用UAM请求错误");
    	}
    	
    	UserDto userDto = response.getBody();
    	UserGroupDto userGroupDto = userDto.getUserGroup();
        return null == userGroupDto ? null : userGroupDto.getAcornGroupType();
    }

    @Override
    @ReadThroughSingleCache(namespace = NAMESPACE + ".getAreaCode", expiration = EXPIRATION)
    public String getAreaCode(@ParameterValueKeyProvider String groupName) throws ServiceException {
    	ResponseEntity<UserGroupDto> response = null;
    	try {
    		response = restTemplate.getForEntity(uamUrl + "group/ldap/" + groupName , UserGroupDto.class);
		} catch (RestClientException e) {
			logger.error("getUserGroup", e);
		}
    	
    	if(null ==response || response.getStatusCode().value() != 200) {
    		throw new ServiceException("调用UAM请求错误");
    	}
    	
    	UserGroupDto userGroup = response.getBody();
        return null == userGroup ? null : userGroup.getAcornAreaCode();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void changePassword(String userId, String newPassWord, String oldPassword) throws ServiceException {
    	//同步到iagent.usr 表
    	if(userDao.exists(userId)){
            AgentUser user = userDao.get(userId);
            user.setPassword(newPassWord);
            userDao.save(user);
        }
    	
		ResponseEntity<Map> response = null;
    	String requestPath = uamUrl + "changePassword/ldap/" + userId + "/" + oldPassword + "/" + newPassWord;
    	try {
    		response = restTemplate.postForEntity(requestPath, null, Map.class);
		} catch (RestClientException e) {
			logger.error("getUserGroup", e);
		}
    	
    	if(null ==response || response.getStatusCode().value() != 200) {
    		throw new ServiceException("调用UAM请求错误");
    	}
    	
    	Map<String, Object> rsMap = response.getBody();
    	String success = null == rsMap.get("result") ? "" : rsMap.get("result").toString();
    	String message = null == rsMap.get("message") ? "" : rsMap.get("message").toString();
    	
    	if(!"success".equalsIgnoreCase(success)) {
    		throw new ServiceException(message);
    	}
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Deprecated
    public boolean checkPassword(String uId, String password) throws ServiceException {
    	
		ResponseEntity<Map> response = null;
    	String requestPath = uamUrl + "login/ldap/" + uId + "/" + password;
    	try {
    		response = restTemplate.postForEntity(requestPath, null, Map.class);
		} catch (RestClientException e) {
			logger.error("checkPassword", e);
		}
    	
    	if(null ==response || response.getStatusCode().value() != 200) {
    		throw new ServiceException("调用UAM请求错误");
    	}
    	
    	Map<String, Object> rsMap = response.getBody();
    	String success = null == rsMap.get("result") ? "" : rsMap.get("result").toString();
    	String message = null == rsMap.get("message") ? "" : rsMap.get("message").toString();
    	
    	if(!"success".equalsIgnoreCase(success)) {
    		throw new ServiceException(message);
    	}
    	
    	return true;
    }

    
	@Override
	@ReadThroughSingleCache(namespace = NAMESPACE + ".getUserList", expiration = EXPIRATION)
    public List<AgentUser> getUserList(@ParameterValueKeyProvider String groupCode) throws ServiceException {
    	ResponseEntity<UserDto[]> response = null;
    	try {
    		response = restTemplate.getForEntity(uamUrl + "user/list/ldap/" + groupCode , UserDto[].class);
		} catch (RestClientException e) {
			logger.error("getUserGroup", e);
		}
    	
    	if(null ==response || response.getStatusCode().value() != 200) {
    		throw new ServiceException("调用UAM请求错误");
    	}
    	
    	UserDto[] userDtoList = response.getBody();
    	List<AgentUser> userList = new ArrayList<AgentUser>();
		
		for(UserDto dto : userDtoList) {
			AgentUser agentUser = this.adapter(dto, AgentUser.class);
			userList.add(agentUser);
		}
        return userList;
    }


	/**
	 * <p></p>
	 * @param dto
	 * @param class1
	 * @return
	 */
	private AgentUser adapter(UserDto dto, Class<AgentUser> class_) {
		AgentUser user = (AgentUser) PojoUtils.convertDto2Pojo(dto, class_);
		
		user.setUserId(String.valueOf(dto.getClientUserId()));
		user.setTitle(dto.getUserTitle());
		user.setDefGrp(dto.getDefGrp());
		//user.setAdcGroup();
		user.setValid(dto.getValid());
		user.setLastDt(dto.getUpdateDate());
		user.setWorkGrp(dto.getWorkGroup());
		user.setDepartment(dto.getUserGroup().getDepartment().getSourceId());
		user.setDisplayName(dto.getName());
		
		Set<RoleDto> roleDtoSet = dto.getRoles();
		if(null != roleDtoSet) {
			Set<AcornRole> acornRoleSet = new HashSet<AcornRole>();
			for(RoleDto roleDto : roleDtoSet) {
				AcornRole acornRole = this.adapter(roleDto, AcornRole.class);
				acornRoleSet.add(acornRole);
			}
			user.grantAuthorities(acornRoleSet);
		}
		
		return user;
	}


	@Override
	@ReadThroughSingleCache(namespace = NAMESPACE + ".getDepartmentInfo", expiration = EXPIRATION)
    public DepartmentInfo getDepartmentInfo(@ParameterValueKeyProvider String departmentNum) throws ServiceException {
		ResponseEntity<DepartmentDto> response = null;
    	try {
    		response = restTemplate.getForEntity(uamUrl + "department/ldap/" + departmentNum , DepartmentDto.class);
		} catch (RestClientException e) {
			logger.error("getUserGroup", e);
		}
    	
    	if(null ==response || response.getStatusCode().value() != 200) {
    		throw new ServiceException("调用UAM请求错误");
    	}
    	
    	DepartmentDto deptDto = response.getBody();
    	DepartmentInfo deptInfo = new DepartmentInfo();
    	deptInfo.setAreaCode(deptDto.getAcornAreaCode());
    	deptInfo.setCode(deptDto.getCode());
    	//deptInfo.setDn(dn);
    	deptInfo.setId(String.valueOf(deptDto.getSourceId()));
    	deptInfo.setName(deptDto.getName());
        return deptInfo;
    }

    @Override
    @ReadThroughSingleCache(namespace = NAMESPACE + ".getGroupList", expiration = EXPIRATION)
    public List<GroupInfo> getGroupList(@ParameterValueKeyProvider String departmentNum) throws ServiceException {
    	ResponseEntity<UserGroupDto[]> response = null;
    	try {
    		response = restTemplate.getForEntity(uamUrl + "userGroup/list/ldap/" + departmentNum , UserGroupDto[].class);
		} catch (RestClientException e) {
			logger.error("getUserGroup", e);
		}
    	
    	if(null ==response || response.getStatusCode().value() != 200) {
    		throw new ServiceException("调用UAM请求错误");
    	}
    	
    	UserGroupDto[] userGroupList = response.getBody();
    	List<GroupInfo> groupInfoList = new ArrayList<GroupInfo>();
    	for(UserGroupDto dto : userGroupList) {
    		GroupInfo groupInfo = (GroupInfo) this.adapter(dto, GroupInfo.class);
    		groupInfoList.add(groupInfo);
    	}
        return groupInfoList;
    }

    /**
	 * <p></p>
	 * @param dto
	 * @param class1
	 * @return
	 */
	private GroupInfo adapter(UserGroupDto dto, Class<GroupInfo> class_) {
		GroupInfo groupInfo = new GroupInfo();
		groupInfo.setAreaCode(dto.getAcornAreaCode());
		groupInfo.setId(String.valueOf(dto.getSourceId()));
		groupInfo.setName(dto.getName());
		groupInfo.setType(dto.getAcornGroupType());
		return groupInfo;
	}

	@Override
	@ReadThroughSingleCache(namespace = NAMESPACE + ".getGroupInfo", expiration = EXPIRATION)
    public GroupInfo getGroupInfo(@ParameterValueKeyProvider String groupName) throws ServiceException {
		ResponseEntity<UserGroupDto> response = null;
    	try {
    		response = restTemplate.getForEntity(uamUrl + "userGroup/ldap/" + groupName , UserGroupDto.class);
		} catch (RestClientException e) {
			logger.error("getUserGroup", e);
		}
    	
    	if(null ==response || response.getStatusCode().value() != 200) {
    		throw new ServiceException("调用UAM请求错误");
    	}
    	
    	UserGroupDto dto = response.getBody();
    	GroupInfo groupInfo = this.adapter(dto, GroupInfo.class);
        return groupInfo;
    }

    @Override
    @ReadThroughSingleCache(namespace = NAMESPACE + ".getGroupDisplayName", expiration = EXPIRATION)
    public String getGroupDisplayName(@ParameterValueKeyProvider String groupName) throws ServiceException {
    	ResponseEntity<UserGroupDto> response = null;
    	try {
    		response = restTemplate.getForEntity(uamUrl + "userGroup/ldap/" + groupName , UserGroupDto.class);
		} catch (RestClientException e) {
			logger.error("getUserGroup", e);
		}
    	
    	if(null ==response || response.getStatusCode().value() != 200) {
    		throw new ServiceException("调用UAM请求错误");
    	}
    	
    	UserGroupDto dto = response.getBody();
    	GroupInfo groupInfo = this.adapter(dto, GroupInfo.class);
    	return null == groupInfo ? null : groupInfo.getName();
    }

    @Override
    @ReadThroughSingleCache(namespace = NAMESPACE + ".getRoleList", expiration = EXPIRATION)
    public List<AcornRole> getRoleList(@ParameterValueKeyProvider String userName) throws ServiceException {
    	ResponseEntity<RoleDto[]> response = null;
    	try {
    		response = restTemplate.getForEntity(uamUrl + "role/list/ldap/sales/" + userName , RoleDto[].class);
		} catch (RestClientException e) {
			logger.error("getUserGroup", e);
		}
    	
    	if(null ==response || response.getStatusCode().value() != 200) {
    		throw new ServiceException("调用UAM请求错误");
    	}
    	
    	RoleDto[] roleDtoList = response.getBody();
    	List<AcornRole> acronRoleList = new ArrayList<AcornRole>();
    	for(RoleDto dto : roleDtoList) {
    		AcornRole acornRole = (AcornRole) this.adapter(dto, AcornRole.class);
    		acronRoleList.add(acornRole);
    	}
        return acronRoleList;
    }

    /**
	 * <p></p>
	 * @param dto
	 * @param class1
	 * @return
	 */
	private AcornRole adapter(RoleDto dto, Class<AcornRole> class_) {
		AcornRole role = (AcornRole) PojoUtils.convertDto2Pojo(dto, class_);
		String upperName = role.getName().toUpperCase();
		role.setName(upperName);
		return role;
	}


	@Override
	@ReadThroughSingleCache(namespace = NAMESPACE + ".getManageGroupList", expiration = EXPIRATION)
    public List<String> getManageGroupList(@ParameterValueKeyProvider String departmentNum) throws ServiceException {
    	ResponseEntity<UserGroupDto[]> response = null;
    	List<String> params = new ArrayList<String>();
    	params.add("inbound_manager");
    	params.add("outbound_manager");
    	params.add("inbound_group_manager");
    	params.add("outbound_group_manager");
    	params.add("complain_manager");

    	try {
    		response = restTemplate.postForEntity(uamUrl + "userGroup/list/ldap/"+ departmentNum +"/manage" , 
    				params, UserGroupDto[].class);
		} catch (RestClientException e) {
			logger.error("getUserGroup", e);
		}
    	
    	if(null ==response || response.getStatusCode().value() != 200) {
    		throw new ServiceException("调用UAM请求错误");
    	}
    	
    	UserGroupDto[] groupDtoList = response.getBody();
    	List<String> groupCodeList = new ArrayList<String>();
    	for(UserGroupDto dto : groupDtoList) {
    		groupCodeList.add(dto.getSourceId());
    	}
        return groupCodeList;
    }

    @Override
    @ReadThroughAssignCache(namespace = NAMESPACE + ".getDepartments", assignedKey = "allDepartmentInfo",  expiration = EXPIRATION)
    public List<DepartmentInfo> getDepartments() throws ServiceException {
    	ResponseEntity<DepartmentDto[]> response = null;
    	try {
    		response = restTemplate.getForEntity(uamUrl + "department/list/ldap/" , DepartmentDto[].class);
		} catch (RestClientException e) {
			logger.error("getUserGroup", e);
		}
    	
    	if(null ==response || response.getStatusCode().value() != 200) {
    		throw new ServiceException("调用UAM请求错误");
    	}
    	
    	DepartmentDto[] deptDtoList = response.getBody();
    	List<DepartmentInfo> deptList = new ArrayList<DepartmentInfo>();
    	for(DepartmentDto dto : deptDtoList) {
    		DepartmentInfo deptInfo = (DepartmentInfo) this.adapter(dto, DepartmentInfo.class);
    		deptList.add(deptInfo);
    	}
        return deptList;
    }

    /**
	 * <p></p>
	 * @param dto
	 * @param class1
	 * @return
	 */
	private DepartmentInfo adapter(DepartmentDto dto, Class<DepartmentInfo> class_) {
		DepartmentInfo deptInfo = new DepartmentInfo();
		deptInfo.setAreaCode(dto.getAcornAreaCode());
		deptInfo.setCode(dto.getCode());
		//deptInfo.setDn();
		deptInfo.setId(String.valueOf(dto.getSourceId()));
		deptInfo.setName(dto.getName());
		return deptInfo;
	}

	@Override
	@ReadThroughSingleCache(namespace = NAMESPACE + "getManageUserList", expiration = EXPIRATION)
    public List<AgentUser> getManageUserList(@ParameterValueKeyProvider String departmentNum) throws ServiceException {
    	ResponseEntity<UserDto[]> response = null;
    	List<String> params = new ArrayList<String>();
    	params.add("inbound_manager");
    	params.add("outbound_manager");
    	params.add("inbound_group_manager");
    	params.add("outbound_group_manager");
    	
    	try {
    		response = restTemplate.postForEntity(uamUrl + "user/list/ldap/"+ departmentNum  +"/manage" , 
    				params, UserDto[].class);
		} catch (RestClientException e) {
			logger.error("getUserGroup", e);
		}
    	
    	if(null ==response || response.getStatusCode().value() != 200) {
    		throw new ServiceException("调用UAM请求错误");
    	}
    	
    	UserDto[] userDtoList = response.getBody();
    	List<AgentUser> userList = new ArrayList<AgentUser>();
    	for(UserDto dto : userDtoList) {
    		AgentUser agentUser = this.adapter(dto, AgentUser.class);
    		userList.add(agentUser);
    	}
        return userList;
    }

    @Override
    public String loginSuccess(String userId) throws ServiceException {
    	
		String response = null;
		AgentUser agentUser = SecurityHelper.getLoginUser();
		String lastTime = agentUser.getAcornLastTime();
		
		if (!StringUtils.isEmpty(lastTime)) {
			long days = DateUtil.getDateDeviations(lastTime, DateUtil.dateToString(new Date()), "yyyy-MM-dd");
			if (days >= 90) {
				response = "ppolicy.expired";
			}
		} else {
			response = "ppolicy.change.after.reset";
		}

		return response;
    }

    @Override
    @Deprecated
    public String loginFailure(String userId) throws ServiceException {
        return null;
    }

    @Override
    @Deprecated
    public String getUserAttribute(String uid, String attribute) throws ServiceException {
    	ResponseEntity<UserDto> response = null;
    	try {
    		response = restTemplate.getForEntity(uamUrl + "user/ldap/" + uid , UserDto.class);
		} catch (RestClientException e) {
			logger.error("getUserGroup", e);
		}
    	
    	if(null ==response || response.getStatusCode().value() != 200) {
    		throw new ServiceException("调用UAM请求错误");
    	}
    	
    	UserDto userDto = response.getBody();
    	AgentUser user = this.adapter(userDto, AgentUser.class);
    	
    	return PojoUtils.getObjectAttr(user, attribute);
    }

    @Override
    @ReadThroughSingleCache(namespace = NAMESPACE + ".findUserByUid", expiration = EXPIRATION)
    public List<AgentUser> findUserByUid(@ParameterValueKeyProvider String uid) throws ServiceException {
    	ResponseEntity<UserDto> response = null;
    	try {
    		response = restTemplate.getForEntity(uamUrl + "user/ldap/" + uid + "/roles/sales", UserDto.class);
		} catch (RestClientException e) {
			logger.error("getUserGroup", e);
		}
    	
    	if(null ==response || response.getStatusCode().value() != 200) {
    		throw new ServiceException("调用UAM请求错误");
    	}
    	
    	UserDto userDto = response.getBody();
    	List<AgentUser> userList = new ArrayList<AgentUser>();
    	
    	AgentUser user = this.adapter(userDto, AgentUser.class);
    	userList.add(user);
    	return userList;
    }

    @Override
    @ReadThroughAssignCache(namespace = NAMESPACE + ".getAllGroup", assignedKey = "allGroupInfo", expiration = EXPIRATION)
    public List<GroupInfo> getAllGroup() throws ServiceException {
    	ResponseEntity<UserGroupDto[]> response = null;
    	try {
    		response = restTemplate.getForEntity(uamUrl + "userGroup/list/ldap/", UserGroupDto[].class);
		} catch (RestClientException e) {
			logger.error("getUserGroup", e);
		}
    	
    	if(null ==response || response.getStatusCode().value() != 200) {
    		throw new ServiceException("调用UAM请求错误");
    	}
    	
    	UserGroupDto[] groupDtos = response.getBody();
    	List<GroupInfo> groupList = new ArrayList<GroupInfo>();
    	for(UserGroupDto dto : groupDtos) {
    		GroupInfo user = this.adapter(dto, GroupInfo.class);
    		groupList.add(user);
    	}
    	
    	return groupList;
    }

    @Override
    @ReadThroughSingleCache(namespace = NAMESPACE + ".findUserLikeUid", expiration = EXPIRATION)
    public List<AgentUser> findUserLikeUid(@ParameterValueKeyProvider String uid) throws ServiceException {
    	ResponseEntity<UserDto[]> response = null;
    	try {
    		response = restTemplate.getForEntity(uamUrl + "user/list/ldap/like/" + uid , UserDto[].class);
		} catch (RestClientException e) {
			logger.error("getUserGroup", e);
		}
    	
    	if(null ==response || response.getStatusCode().value() != 200) {
    		throw new ServiceException("调用UAM请求错误");
    	}
    	
    	UserDto[] userDtos = response.getBody();
    	List<AgentUser> userList = new ArrayList<AgentUser>();
    	for(UserDto dto : userDtos) {
    		AgentUser user = this.adapter(dto, AgentUser.class);
    		userList.add(user);
    	}
    	
    	return userList;
    }

    @Override
    @Deprecated
    public boolean releaseLock(String userId) throws ServiceException {
        return true;
    }

    @Deprecated
    public String getUserDN(String uId){
    	AgentUser user = null;
    	try {
			user = this.getUserById(uId);
		} catch (ServiceException e) {
			e.printStackTrace();
			logger.error("query user [" + uId + "] error", e);
		}
        return null == user ? null : user.getDisplayName();
    }

	/** 
	 * <p>Title: getDepartmentByGroup</p>
	 * <p>Description: </p>
	 * @param group
	 * @return
	 * @throws ServiceException
	 * @see com.chinadrtv.erp.user.service.UserService#getDepartmentByGroup(java.lang.String)
	 */ 
	@Override
	@ReadThroughSingleCache(namespace = NAMESPACE + ".getDepartmentByGroup", expiration = EXPIRATION)
	public String getDepartmentByGroup(@ParameterValueKeyProvider String group) throws ServiceException {
		ResponseEntity<UserGroupDto> response = null;
    	try {
    		response = restTemplate.getForEntity(uamUrl + "userGroup/ldap/" + group , UserGroupDto.class);
		} catch (RestClientException e) {
			logger.error("getUserGroup", e);
		}
    	
    	if(null ==response || response.getStatusCode().value() != 200) {
    		throw new ServiceException("调用UAM请求错误");
    	}
    	
    	UserGroupDto dto = response.getBody();
    	DepartmentInfo deptInfo = this.adapter(dto.getDepartment(), DepartmentInfo.class);
        return null == deptInfo ? null : deptInfo.getId();
	}

	/** 
	 * <p>Title: getUserDepartments</p>
	 * <p>Description: </p>
	 * @param userName
	 * @return
	 * @see com.chinadrtv.erp.user.service.UserService#getUserDepartments(java.lang.String)
	 */ 
	@Override
	@Deprecated
	public List<String> getUserDepartments(String userName) {
		return null;
	}

	/** 
	 * <p>Title: getDepartmentDisplayName</p>
	 * <p>Description: </p>
	 * @param deptName
	 * @return
	 * @throws ServiceException
	 * @see com.chinadrtv.erp.user.service.UserService#getDepartmentDisplayName(java.lang.String)
	 */ 
	@Override
	@ReadThroughSingleCache(namespace = NAMESPACE + ".getDepartmentDisplayName", expiration = EXPIRATION)
	public String getDepartmentDisplayName(@ParameterValueKeyProvider String deptName) throws ServiceException {
		ResponseEntity<DepartmentDto> response = null;
    	try {
    		response = restTemplate.getForEntity(uamUrl + "department/ldap/" + deptName , DepartmentDto.class);
		} catch (RestClientException e) {
			logger.error("getUserGroup", e);
		}
    	
    	if(null ==response || response.getStatusCode().value() != 200) {
    		throw new ServiceException("调用UAM请求错误");
    	}
    	
    	DepartmentDto dto = response.getBody();
        return null == dto ? null : dto.getName();
	}

	/** 
	 * <p>Title: getRoleByCode</p>
	 * <p>Description: </p>
	 * @param roleName
	 * @return
	 * @throws ServiceException
	 * @see com.chinadrtv.erp.user.service.UserService#getRoleByCode(java.lang.String)
	 */ 
	@Override
	@ReadThroughSingleCache(namespace = NAMESPACE + ".getRoleByName", expiration = EXPIRATION)
	public AcornRole getRoleByName(@ParameterValueKeyProvider String roleName) throws ServiceException {
		ResponseEntity<RoleDto> response = null;
    	try {
    		roleName = roleName.toLowerCase();
    		response = restTemplate.getForEntity(uamUrl + "role/" + roleName , RoleDto.class);
		} catch (RestClientException e) {
			logger.error("getUserGroup", e);
		}
    	
    	if(null ==response || response.getStatusCode().value() != 200) {
    		throw new ServiceException("调用UAM请求错误");
    	}
    	
    	RoleDto dto = response.getBody();
    	AcornRole acornRole = this.adapter(dto, AcornRole.class);
        return null == acornRole ? null : acornRole;
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
	@ReadThroughSingleCache(namespace = NAMESPACE + ".getUserById", expiration = EXPIRATION)
	public AgentUser getUserById(@ParameterValueKeyProvider String userId) throws ServiceException {
		ResponseEntity<UserDto> response = null;
    	try {
    		response = restTemplate.getForEntity(uamUrl + "user/ldap/" + userId + "/roles/sales", UserDto.class);
		} catch (RestClientException e) {
			logger.error("getUserGroup", e);
		}
    	
    	if(null ==response || response.getStatusCode().value() != 200) {
    		throw new ServiceException("调用UAM请求错误");
    	}
    	
    	UserDto userDto = response.getBody();
    	AgentUser user = this.adapter(userDto, AgentUser.class);
    	
    	return user;
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
		ResponseEntity<LoginDto> response = null;
    	String requestPath = uamUrl + "login/ldap/sales/" + userId + "/" + password;
    	try {
    		response = restTemplate.postForEntity(requestPath, null, LoginDto.class);
		} catch (RestClientException e) {
			logger.error("checkPassword", e);
		}
    	
    	if(null ==response || response.getStatusCode().value() != 200) {
    		throw new ServiceException("调用UAM请求错误");
    	}
    	
    	LoginDto loginDto  = response.getBody();
    	String success = loginDto.getResult();
    	String message = loginDto.getMessage();
    	UserDto userDto = loginDto.getUser();
    	
    	if(!"success".equalsIgnoreCase(success) || null == userDto || null==userDto.getClientUserId()) {
    		throw new ServiceException(message);
    	}
    	
		AgentUser agentUser = this.adapter(userDto, AgentUser.class);
		return agentUser;
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> resetUserPassword(String userId, String password) throws ServiceException {
		//同步到iagent.usr 表
    	if(userDao.exists(userId)){
            AgentUser user = userDao.get(userId);
            user.setPassword(password);
            userDao.save(user);
        }
    	
		ResponseEntity<Map> response = null;
    	try {
    		response = restTemplate.getForEntity(uamUrl + "resetPassword/ldap/" + userId + "/" + password, Map.class);
		} catch (RestClientException e) {
			logger.error("getUserGroup", e);
		}
    	
    	if(null ==response || response.getStatusCode().value() != 200) {
    		throw new ServiceException("调用UAM请求错误");
    	}
    	
    	Map<String, Object> rsMap = (Map<String, Object>)response.getBody();
    	
    	return rsMap;
	}

	@Override
	@ReadThroughSingleCache(namespace = NAMESPACE + ".getUserIdListByDept", expiration = EXPIRATION)
	public List<String> getUserIdListByDept(@ParameterValueKeyProvider String deptNum) throws Exception {
		ResponseEntity<String[]> response = null;
		String url = uamUrl + "userIdList/ldap/" + deptNum;
		
		logger.debug("get url : " + url);
		
    	try {
    		response = restTemplate.getForEntity(url, String[].class);
		} catch (RestClientException e) {
			logger.error("getUserGroup", e);
		}
    	
    	if(null ==response || response.getStatusCode().value() != 200) {
    		throw new ServiceException("调用UAM请求错误");
    	}
    	
    	String[] userIdArr = response.getBody();
    	return (List<String>) (null == userIdArr ? new ArrayList<String>() : Arrays.asList(userIdArr)); 
	}
	

}
