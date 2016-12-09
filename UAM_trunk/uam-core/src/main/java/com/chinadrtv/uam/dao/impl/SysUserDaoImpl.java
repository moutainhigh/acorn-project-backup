/**
 * 
 */
package com.chinadrtv.uam.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.chinadrtv.uam.dao.SysUserDao;
import com.chinadrtv.uam.dao.wrapper.AliasWrapper;
import com.chinadrtv.uam.dao.wrapper.CriteriaWrapper;
import com.chinadrtv.uam.dao.wrapper.AliasWrapper.JoinType;
import com.chinadrtv.uam.model.uam.SysUser;

/**
 * @author dengqianyong
 *
 */
@Repository
public class SysUserDaoImpl extends HibernateDaoImpl implements SysUserDao {

	@Override
	public SysUser loadByName(String username) {
		CriteriaWrapper cw = new CriteriaWrapper();
		cw.addAliasWrapper(new AliasWrapper("roles", "r", JoinType.LEFT_JOIN));
		cw.addCriterion(Restrictions.eq("signName", username));
		return findByCriteria(SysUser.class, cw);
	}
}
