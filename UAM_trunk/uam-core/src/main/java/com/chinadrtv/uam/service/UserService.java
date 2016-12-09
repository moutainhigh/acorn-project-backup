package com.chinadrtv.uam.service;

import java.util.List;
import java.util.Map;

import com.chinadrtv.uam.model.auth.Department;
import com.chinadrtv.uam.model.auth.User;
import com.chinadrtv.uam.model.auth.UserGroup;
import com.chinadrtv.uam.model.auth.UserMapping;

/**
 * Created with IntelliJ IDEA. User: zhoutaotao Date: 14-3-27 Time: 上午11:10 To
 * change this template use File | Settings | File Templates.
 */
public interface UserService extends ServiceSupport {
	
	/**
	 * 获取用户部门
	 * 
	 * @param source
	 *            来源 目前LDAP
	 * @param sourceId
	 *            来源id
	 * @return
	 */
	Department getDepartment(String source, String sourceId);

	/**
	 * 获取部门列表
	 * 
	 * @param source
	 *            来源 目前LDAP
	 * @return
	 */
	List<Department> getDepartmentList(String source);

	/**
	 * 获取用户组
	 * 
	 * @param source
	 *            来源 目前LDAP
	 * @param sourceId
	 *            来源id
	 * @return
	 */
	UserGroup getUserGroup(String source, String sourceId);

	/**
	 * 获取用户组列表
	 * 
	 * @param source
	 *            来源 目前LDAP
	 * @return
	 */
	List<UserGroup> getUserGroupList(String source);
	

	/**
	 * 根据部门号找组列表
	 * 
	 * @param source
	 *            来源 目前LDAP
	 * @param departmentNum
	 *            部门号
	 * @return
	 */
	List<UserGroup> getUserGroupListByDepartment(String source,
			String departmentNum);
	
	/**
	 * 获取用户映射表
	 * 
	 * @param source
	 *            来源 目前LDAP
	 * @param sourceId
	 *            来源id
	 * @return
	 */
	UserMapping getUserMapping(String source, String sourceId);
	
	/**
	 * 
	 * @param source
	 * @param userId
	 * @return
	 */
	UserMapping getUserMapping(String source, Long userId);

	/**
	 * 
	 * @param startRow
	 * @param rows
	 * @return
	 */
	public Map<String, Object> getUserList(int startRow, int rows);

	/**
	 * 查询用户
	 * 
	 * @param source
	 *            ldap
	 * @param userId
	 *            ldap userId
	 * @return
	 */
	User findUser(String source, String userId);
	
	/**
	 * 
	 * @param source
	 * @param groupCode
	 * @return
	 */
	List<User> getUserList(String source, String groupCode);

	/**
	 * 根据uid进行模糊查询
	 * 
	 * @param source
	 * @param likedUid
	 * @return
	 */
	List<User> getUserListByFuzzyUid(String source, String likedUid);
	
    /**
     * 检测用户 职务跟工作组
     * @param param
     * @return
     */
    boolean checkExistsParam(String param);

    /**
     * 
     * @param deptId
     * @param roleNames
     * @return
     */
	List<UserGroup> getUserGroupListByCondition(Long deptId,
			List<String> roleNames);

	/**
	 * 
	 * @param source
	 * @param ids
	 * @return
	 */
	List<User> getUserListByGroupIdList(String source, List<Long> ids);

    /**
     * 用户登陆验证
     * @param source
     * @param userId
     * @param password
     * @return
     */
    Map<String,Object> validate(String source, String userId, String password);

    /**
     * 修改密码
     * @param source
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @return
     */
    Map<String,String> changePassword(String source, String userId, String oldPassword, String newPassword);

    /**
     *
     * @param source
     * @param userId
     * @param newPassword
     * @return
     */
    Map<String,String> resetPassword(String source, String userId, String newPassword);

    /**
     * 通过部门查询用户id列表
     * @param value
     * @param departmentNum
     * @return
     */
    List<String> getUserListByDepartment(String value, String departmentNum);
}
