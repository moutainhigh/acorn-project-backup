/**
 * 
 */
package com.chinadrtv.uam.dao.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.chinadrtv.uam.dao.ResourceDao;
import com.chinadrtv.uam.dao.wrapper.AliasWrapper;
import com.chinadrtv.uam.dao.wrapper.AliasWrapper.JoinType;
import com.chinadrtv.uam.dao.wrapper.CriteriaWrapper;
import com.chinadrtv.uam.model.auth.res.ResAction;

/**
 * @author dengqianyong
 *
 */
@Repository
public class ResourceDaoImpl extends HibernateDaoImpl implements ResourceDao {

	@Override
	public List<ResAction> loadUamResources() {
		CriteriaWrapper cw = new CriteriaWrapper();
		cw.addAliasWrapper(new AliasWrapper("site", "s", JoinType.LEFT_JOIN));
		cw.addAliasWrapper(new AliasWrapper("roles", "r", JoinType.LEFT_JOIN));
		cw.addCriterion(Restrictions.isNull("s.id"));
		return listByCriteria(ResAction.class, cw);
	}

}
