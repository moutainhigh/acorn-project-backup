package com.chinadrtv.uam.service.impl;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.LdapShaPasswordEncoder;
import org.springframework.stereotype.Service;

import com.chinadrtv.uam.dao.UserDao;
import com.chinadrtv.uam.model.auth.Department;
import com.chinadrtv.uam.model.auth.Role;
import com.chinadrtv.uam.model.auth.User;
import com.chinadrtv.uam.model.auth.UserGroup;
import com.chinadrtv.uam.model.auth.UserMapping;
import com.chinadrtv.uam.service.RoleService;
import com.chinadrtv.uam.service.UserService;

/**
 * Created with IntelliJ IDEA. User: zhoutaotao Date: 14-3-27 Time: 上午11:10 To
 * change this template use File | Settings | File Templates.
 */
@Service
public class UserServiceImpl extends ServiceSupportImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
    @Autowired
    private RoleService roleService;
	@Override
	public Department getDepartment(String source, String sourceId) {
		return userDao.getDepartment(source, sourceId);
	}

	@Override
	public List<Department> getDepartmentList(String source) {
		return userDao.getDepartmentList(source);
	}

	@Override
	public UserGroup getUserGroup(String source, String sourceId) {
		return userDao.getUserGroup(source, sourceId);
	}

	@Override
	public List<UserGroup> getUserGroupList(String source) {
		return userDao.getUserGroupList(source);
	}

	@Override
	public List<UserGroup> getUserGroupListByDepartment(String source,
			String departmentNum) {
		Department dept = getDepartment(source, departmentNum);
		if (dept != null) {
			return userDao.getUserGroupListByDepartment(source, dept.getId());
		}
		return Collections.emptyList();
	}

	@Override
	public UserMapping getUserMapping(String source, String sourceId) {
		return userDao.getUserMapping(source, sourceId);
	}
	
	@Override
	public UserMapping getUserMapping(String source, Long userId) {
		return userDao.getUserMapping(source, userId);
	}

	@Override
	public Map<String, Object> getUserList(int startRow, int rows) {
		int count = userDao.getUserCount();
		List<User> userList = userDao.getUserList(startRow, rows);
        if(userList !=null){
            for(User user :userList){
                List<Role> roleList = roleService.getRoleListByUserId(user.getId());
                Set<Role> roleSet  = new HashSet<Role>();
                roleSet.addAll(roleList);
                user.setRoles(roleSet);
            }
        }

        Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);
		map.put("rows", userList);
		return map;

	}

	@Override
	public User findUser(String source, String userId) {
		/*UserMapping userMapping = userDao.getUserMapping(source, userId);
		User user = new User();
		if (userMapping != null) {
			user = get(User.class, userMapping.getUserId());
			user.setClientUserId(userMapping.getSourceId());
            user.setSourcePassword(userMapping.getSourcePassword());
		}*/
        User user = userDao.findUser(source,userId);
		return user;
	}

	
	@Override
	public List<User> getUserList(String source, String groupCode) {
		UserGroup userGroup = userDao.getUserGroup(source, groupCode);
		if (userGroup == null) {
			return Collections.emptyList();
		}

		List<User> userList = userDao.getUserListByGroupId(userGroup.getId());
		for (User user : userList) {
			UserMapping um = userDao.getUserMapping(source, user.getId());
			user.setClientUserId(um.getSourceId());
            user.setSourcePassword(um.getSourcePassword());
		}
		return userList;
	}

	@Override
	public List<User> getUserListByFuzzyUid(String source, String likedUid) {
		List<UserMapping> ums = userDao.getUserMappingListByFuzzyUid(source, likedUid);
		if (ums == null || ums.size() == 0) {
			return Collections.emptyList();
		}
		List<Long> ids = new ArrayList<Long>(ums.size());
		for (UserMapping um : ums) {
			ids.add(um.getUserId());
		}
		List<User> users = userDao.getUserListByUids(ids);
		outter: for (User user : users) {
			for (UserMapping um : ums) {
				if (user.getId().equals(um.getUserId())) {
					user.setClientUserId(um.getSourceId());
                    user.setSourcePassword(um.getSourcePassword());
					continue outter;
				}
			}
		}
		return users;
	}

	@Override
    public boolean checkExistsParam(String param) {
        return  userDao.checkExistsParam(param);
    }

	/*
	 * @ReadThroughSingleCache(namespace = NAMESPACE, expiration = 3600) private
	 * User getUserById(@ParameterValueKeyProvider Long userId){ return
	 * this.get(User.class, userId); }
	 */
	
	@Override
	public List<UserGroup> getUserGroupListByCondition(Long deptId,
			List<String> roleNames) {
		return userDao.getUserGroupListByCondition(deptId, roleNames);
	}

	@Override
	public List<User> getUserListByGroupIdList(String source, List<Long> ids) {
		List<User> userList = userDao.getUserListByGroupIdList(ids);
		for (User user : userList) {
			UserMapping um = userDao.getUserMapping(source, user.getId());
			user.setClientUserId(um.getSourceId());
            user.setSourcePassword(um.getSourcePassword());
		}
		return userList;
	}

    /**
     * 密码判断：当用户表密码不为空时，使用用户表密码；
     * 当用户表密码为空时，使用ldap密码
     * @param source
     * @param userId
     * @param password
     * @return
     */
    @Override
    public Map<String, Object> validate(String source, String userId, String password) {
        Map<String, Object> loginMap = new HashMap<String, Object>();
        UserMapping userMapping = userDao.getUserMapping(source, userId);

        if (userMapping == null) {
            loginMap.put("result", "failure");
            loginMap.put("message", "用户不存在");
            return loginMap;
        }

        User user = this.get(User.class, userMapping.getUserId());
        user.setClientUserId(userMapping.getSourceId());
        user.setSourcePassword(userMapping.getSourcePassword());
        if(!user.getValid()){
            loginMap.put("result", "failure");
            loginMap.put("message", "无效用户，无法登录");
            return loginMap;
        }
        if (StringUtils.isNotBlank(user.getPassword())) {
            boolean valid = StringUtils.equalsIgnoreCase(password, user.getPassword());
            this.passwordResult(loginMap, user, valid);
        } else {
            LdapShaPasswordEncoder passwordEncoder = new LdapShaPasswordEncoder();
            boolean valid = passwordEncoder.isPasswordValid(userMapping.getSourcePassword(), password, null);
            this.passwordResult(loginMap, user, valid);
        }

        return loginMap;
    }

    private void passwordResult(Map<String, Object> loginMap, User user, boolean valid) {
        if (valid) {
            loginMap.put("result", "success");
            loginMap.put("user",user);
        }else {
            loginMap.put("result", "failure");
            loginMap.put("message", "用户密码错误");
        }
    }

    //至少8位，且必须包含数字+字母，字母不区分大小写，可以包含其他的字符
    //密码时间>90天，系统提醒修改密码，校验2次新密码相同，且必须与原密码不同
    @Override
    public Map<String,String> changePassword(String source, String userId, String oldPassword, String newPassword) {
        Map<String,String> map = new HashMap<String,String>();
        UserMapping userMapping = userDao.getUserMapping(source, userId);
        if (userMapping == null) {
            map.put("result", "false");
            map.put("message", "用户不存在");
            return map;
        }

        User user = this.get(User.class, userMapping.getUserId());

        if(!validUserOldPassword(oldPassword, user.getPassword(), userMapping.getSourcePassword())){
            map.put("result", "false");
            map.put("message", "用户原密码错误");
            return map;
        }
        if(StringUtils.equalsIgnoreCase(oldPassword,newPassword)){
            map.put("result", "false");
            map.put("message", "新密码与原密码相同");
            return map;
        }
        String regex = "^(?![^a-zA-Z]+$)(?!\\D+$).{8,}$";
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher=pattern.matcher(newPassword);
        if(!matcher.find()){
            map.put("result", "false");
            map.put("message", "新密码长度至少8位,且包含数字字母");
            return map;
        }

        map.put("result", "success");
        user.setPassword(newPassword);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        user.setAcornLastTime(simpleDateFormat.format(new Date()));
        this.update(user);
        return map;
    }

    @Override
    public Map<String, String> resetPassword(String source, String userId, String newPassword) {
        Map<String,String> map = new HashMap<String,String>();
        UserMapping userMapping = userDao.getUserMapping(source, userId);
        if (userMapping == null) {
            map.put("result", "false");
            map.put("message", "用户不存在");
            return map;
        }
        User user = this.get(User.class, userMapping.getUserId());
        user.setPassword(newPassword);
        this.update(user);
        map.put("result", "success");

        return map;
    }

    @Override
    public List<String> getUserListByDepartment(String source, String departmentNum) {
       List<String> idList= userDao.getUserIdList(source,departmentNum);
        return idList;
    }

    private boolean validUserOldPassword(String oldPassword, String userPassword, String userMappingPassword) {

        if (StringUtils.isNotBlank(userPassword)) {

            return StringUtils.equalsIgnoreCase(userPassword, oldPassword);
        } else {
            LdapShaPasswordEncoder passwordEncoder = new LdapShaPasswordEncoder();

            return passwordEncoder.isPasswordValid(userMappingPassword, oldPassword, null);
        }
    }
}
