/**
 * 
 */
package com.chinadrtv.uam.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.chinadrtv.uam.dao.RoleDao;
import com.chinadrtv.uam.model.auth.Role;

/**
 * @author dengqianyong
 *
 */
@Repository
public class RoleDaoImpl extends HibernateDaoImpl implements RoleDao {

	/* (non-Javadoc)
	 * @see com.chinadrtv.uam.dao.RoleDao#getRoleByName(java.lang.String)
	 */
	@Override
	public Role getRoleByName(String name) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", name);
		return (Role) findByHql("from Role r where r.name =:name", map);
	}

	@Override
    public List<Role> getRoleListByUserId(Long userId) {
        Map<String, Long> map = new HashMap<String, Long>();
        map.put("userId", userId);
        return listByHql("select distinct r from Role r join r.users u " +
        		"where u.id =:userId", map);
    }
	
	@Override
    public List<Role> getRoleListByUserId(Long userId, Long siteId) {
        Map<String,Long> map = new HashMap<String,Long>();
        map.put("userId", userId);
        map.put("siteId", siteId);
        return listByHql("select distinct r from Role r join r.users u " +
        		"where u.id =:userId and r.site.id = :siteId", map);
    }

}
