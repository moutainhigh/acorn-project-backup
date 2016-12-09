package com.chinadrtv.uam.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.chinadrtv.uam.dao.UserDao;
import com.chinadrtv.uam.dao.wrapper.CriteriaWrapper;
import com.chinadrtv.uam.dao.wrapper.RowBounds;
import com.chinadrtv.uam.model.auth.Department;
import com.chinadrtv.uam.model.auth.User;
import com.chinadrtv.uam.model.auth.UserGroup;
import com.chinadrtv.uam.model.auth.UserMapping;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 14-3-27
 * Time: 下午1:26
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class UserDaoImpl extends HibernateDaoImpl implements UserDao {
    @Override
    public Department getDepartment(String source, String sourceId) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("source", source);
        map.put("sourceId", sourceId);

        return (Department) findByHql("from Department r" +
                " where r.source =:source" +
                " and r.sourceId =:sourceId", map);
    }

    @Override
	public List<Department> getDepartmentList(String source) {
    	Map<String, String> map = new HashMap<String, String>();
        map.put("source", source);
        return listByHql("from Department r where r.source =:source", map);
	}

	@Override
    public UserGroup getUserGroup(String source, String sourceId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("source", source);
        map.put("sourceId", sourceId);

        return findByHql("from UserGroup r " +
                " where r.source =:source " +
                " and r.sourceId =:sourceId", map);
    }

    @Override
	public List<UserGroup> getUserGroupList(String source) {
    	Map<String, Object> map = new HashMap<String, Object>();
        map.put("source", source);
        return listByHql("from UserGroup r where r.source =:source", map);
	}

	@Override
	public List<UserGroup> getUserGroupListByDepartment(String source, Long deptId) {
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("source", source);
        map.put("deptId", deptId);
        return listByHql("from UserGroup r where " +
        		"r.source =:source " +
        		"and r.department.id = :deptId", map);
	}

	@Override
    public UserMapping getUserMapping(String source, String sourceId) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("source", source);
        map.put("sourceId", sourceId);

        return (UserMapping) findByHql("from UserMapping r" +
                " where r.source =:source" +
                " and r.sourceId =:sourceId", map);
    }
	
	@Override
    public UserMapping getUserMapping(String source, Long userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("source", source);
        map.put("userId", userId);

        return findByHql("from UserMapping r" +
                " where r.source =:source" +
                " and r.userId =:userId", map);
    }

    @Override
    public int getUserCount() {
        return countByHql("select count(*) from User u where u.valid=true");
    }

    @Override
    public List<User> getUserList(int startRow, int rows) {
        Map<String, String> map = new HashMap<String, String>();
        RowBounds rowBounds = new RowBounds(startRow, rows);

        List<User> resultList = listByHql("from User u where u.valid=true ", rowBounds, map);
        return resultList;
    }

    @Override
    public List<User> getUserListByGroupId(Long groupId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("groupId", groupId);

        List<User> resultList = listByHql("from User u where u.userGroup.id =:groupId", map);
        return resultList;
    }

	@Override
	public List<UserMapping> getUserMappingListByFuzzyUid(String source,
			String likedUid) {
		CriteriaWrapper cw = new CriteriaWrapper();
		cw.addCriterion(Restrictions.eq("source", source));
		cw.addCriterion(Restrictions.like("sourceId", likedUid, MatchMode.START));
		return listByCriteria(UserMapping.class, cw);
	}

	@Override
	public List<User> getUserListByUids(List<Long> ids) {
		CriteriaWrapper cw = new CriteriaWrapper();
		cw.addCriterion(Restrictions.in("id", ids));
		return listByCriteria(User.class, cw);
	}

    @Override
    public boolean checkExistsParam(String param) {
        Object[] value = findBySql("select * from iagent.names where  dsc=?", param);
        if (value != null) {
            return true;
        }
        return false;


    }
    
    @Override
    public List<UserGroup> getUserGroupListByCondition(Long deptId, List<String> roleNames) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("deptId", deptId);
    	map.put("names", roleNames);
    	return listByHql(
    			"select distinct ug from UserGroup ug " +
    			"join ug.users u join u.roles r " +
    			"where ug.department.id = :deptId " +
    			"and r.name in (:names)", map);
    	
    }
    
    @Override
    public List<User> getUserListByGroupIdList(List<Long> ids) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ids", ids);
        return listByHql("from User u where u.userGroup.id in (:ids)", map);
    }

    @Override
    public User findUser(String source, String sourceId) {

        Object[] value = findBySql(" select" +
                "        u.ID as ID8_2_," +
                "         to_char(u.CREATE_DATE,'yyyy-MM-dd hh:mm:ss') as CREATE2_8_2_," +
                "         to_char(u.UPDATE_DATE,'yyyy-MM-dd hh:mm:ss') as UPDATE3_8_2_," +
                "        u.ACORN_LAST_TIME as ACORN4_8_2_," +
                "        u.ACORN_MAX_FAILURE as ACORN5_8_2_," +
                "        u.DEF_GRP as DEF6_8_2_," +
                "        u.DESCRIPTION as DESCRIPT7_8_2_," +
                "        u.EMPLOYEE_TYPE as EMPLOYEE8_8_2_," +
                "        u.LAST_LOCK_SEQID  as LAST9_8_2_," +
                "        u.LAST_UPDATE_SEQID as LAST10_8_2_," +
                "        to_char(u.LAST_UPDATE_TIME ,'yyyy-MM-dd hh:mm:ss') as LAST11_8_2_," +
                "        u.NAME as NAME8_2_," +
                "        u.PASSWORD as PASSWORD8_2_," +
                "        u.GROUP_ID as GROUP17_8_2_," +
                "        u.USER_TITLE as USER14_8_2_," +
                "        u.VALID as VALID8_2_," +
                "        u.WORK_GROUP as WORK16_8_2_," +
                "        g.id as id9_0_," +
                "        g.CREATE_DATE as CREATE2_9_0_," +
                "        g.UPDATE_DATE as UPDATE3_9_0_," +
                "        g.ACORN_AREA_CODE as ACORN4_9_0_," +
                "        g.ACORN_GROUP_TYPE as ACORN5_9_0_," +
                "        g.DEPARTMENT_ID as DEPARTMENT9_9_0_," +
                "        g.name as name9_0_," +
                "        g.SOURCE as SOURCE9_0_," +
                "        g.SOURCE_ID as SOURCE8_9_0_," +  //index 25
                "        d.id as id0_1_," +
                "        to_char(d.CREATE_DATE ,'yyyy-MM-dd hh:mm:ss') as CREATE2_0_1_," +
                "         to_char(d.UPDATE_DATE ,'yyyy-MM-dd hh:mm:ss') as UPDATE3_0_1_," +
                "        d.ACORN_AREA_CODE as ACORN4_0_1_," +
                "        d.ACORN_NAME as ACORN5_0_1_," +
                "        d.code as code0_1_," +
                "        d.NAME as NAME0_1_," +
                "        d.source as source0_1_," +
                "        d.SOURCE_ID as SOURCE9_0_1_, " +
                "        useMapping.SOURCE_ID , " +
                "        useMapping.source_password  " +
                "    from" +
                "        ACOAPP_UAM.UAM_USER u " +
                "    left outer join" +
                "        ACOAPP_UAM.Uam_User_Mapping useMapping " +
                "            on u.ID=useMapping.User_Id      " +
                "    left outer join" +
                "        ACOAPP_UAM.UAM_USER_GROUP g " +
                "            on u.GROUP_ID=g.id " +
                "    left outer join" +
                "        ACOAPP_UAM.UAM_DEPARTMENT d " +
                "            on g.DEPARTMENT_ID=d.id " +
                "    where" +
                "        useMapping.Source=? and useMapping.Source_Id=?", source,sourceId);
        if(value==null){
            return  null;
        }

        User user    = new User();
        UserGroup userGroup  = new UserGroup();
        Department department = new Department();

        user.setId(convertToLong(value[0]));
        user.setCreateDate(convertToDate(value[1]));
        user.setUpdateDate(convertToDate(value[2]));
         user.setAcornLastTime(convertToString(value[3]));
        user.setAcornMaxFailure(convertToString(value[4]));
        user.setDefGrp(convertToString(value[5]));
        user.setDescription(convertToString(value[6]));
        user.setEmployeeType(convertToString(value[7]));
        user.setLastLockId(convertToLong(value[8])); //LAST_LOCK_SEQID
        user.setLastUpdateId(convertToLong(value[9]));//LAST_UPDATE_SEQID
        user.setLastUpdateTime(convertToDate(value[10]));
        user.setName(convertToString(value[11]));
        user.setPassword(convertToString(value[12]));
        //userGroup.setId(convertToLong(value[13]));
        user.setUserTitle(convertToString(value[14]));
        user.setValid(convertToBoolean(value[15]));
        user.setWorkGroup(convertToString(value[16]));

        userGroup.setId(convertToLong(value[17]));
        userGroup.setCreateDate(convertToDate(value[18]));
        userGroup.setUpdateDate(convertToDate(value[19]));
        userGroup.setAcornAreaCode(convertToString(value[20]));
        userGroup.setAcornGroupType(convertToString(value[21]));
        //department.setId(convertToLong(value[22]));
        userGroup.setName(convertToString(value[23]));
        userGroup.setSource(convertToString(value[24]));
        userGroup.setSourceId(convertToString(value[25]));  //g.SOURCE_ID as SOURCE

        department.setId(convertToLong(value[26]));
        department.setCreateDate(convertToDate(value[27]));
        department.setUpdateDate(convertToDate(value[28]));
        department.setAcornAreaCode(convertToString(value[29]));
        department.setAcornName(convertToString(value[30]));
        department.setCode(convertToString(value[31]));
        department.setName(convertToString(value[32]));
        department.setSource(convertToString(value[33]));
        department.setSourceId(convertToString(value[34]));

        userGroup.setDepartment(department);
        user.setUserGroup(userGroup);

        user.setClientUserId(convertToString(value[35]));
        user.setSourcePassword(convertToString(value[36]));
        return user;
    }

    @Override
    public List<String> getUserIdList(String source, String departmentNum) {
        List<Object[]> objList = listBySql("select  m.source_id " +
                "   from acoapp_uam.uam_user_mapping m" +
                "  where m.user_id in" +
                "       (select u.id from acoapp_uam.uam_user u" +
                "         where u.group_id in" +
                "               (select g.id from acoapp_uam.uam_user_group g" +
                "                 where g.department_id =" +
                "                       (select d.id from acoapp_uam.uam_department d" +
                "                         where d.source = ?" +
                "                           and d.source_id = ?)))",source,departmentNum);

        List<String> userIdList=  new ArrayList<String>();
        if(objList==null || objList.isEmpty()){
            return userIdList;
        }

        for(int i =0;i<objList.size();i++){
            String objects = String.valueOf(objList.get(i));
            userIdList.add(objects);
        }
        return userIdList;
    }

    private Boolean convertToBoolean(Object o) {
         if(o!=null){
             if("1".equals(o.toString())){
                 return true;
             }else {
                 return false;
             }
         }
        return false;
    }

    private Long convertToLong(Object o) {
       if(o!=null){
             return Long.parseLong(o.toString());
       }
        return null;
    }
    private String convertToString(Object o) {
        if(o!=null){
            return o.toString();
        }
        return null;
    }
    private Date convertToDate(Object o) {
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        if(o!=null){
            try {
                return simpleDateFormat.parse(o.toString());
            } catch (ParseException e) {
                return null;
            }
        }
        return null;
    }
}
