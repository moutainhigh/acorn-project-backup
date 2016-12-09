package com.chinadrtv.uam.dao;

import java.util.List;

import com.chinadrtv.uam.model.auth.Department;
import com.chinadrtv.uam.model.auth.User;
import com.chinadrtv.uam.model.auth.UserGroup;
import com.chinadrtv.uam.model.auth.UserMapping;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 14-3-27
 * Time: 下午1:25
 * To change this template use File | Settings | File Templates.
 */
public interface UserDao {
	
    Department getDepartment(String source, String sourceId);
    
	List<Department> getDepartmentList(String source);

    UserGroup getUserGroup(String source, String sourceId);
    
    List<UserGroup> getUserGroupList(String source);
    
    List<UserGroup> getUserGroupListByDepartment(String source, Long deptId);

    UserMapping getUserMapping(String source, String sourceId);
    
    UserMapping getUserMapping(String source, Long userId);

    int getUserCount();

    List<User> getUserList(int startRow,int rows);

    List<User> getUserListByGroupId(Long groupId);

	List<UserMapping> getUserMappingListByFuzzyUid(String source,
			String likedUid);

	List<User> getUserListByUids(List<Long> ids);

    boolean checkExistsParam(String param);

	List<UserGroup> getUserGroupListByCondition(Long deptId,
			List<String> roleNames);

	List<User> getUserListByGroupIdList(List<Long> ids);

    User findUser(String source, String sourceId);

    List<String> getUserIdList(String source, String departmentCode);
}
