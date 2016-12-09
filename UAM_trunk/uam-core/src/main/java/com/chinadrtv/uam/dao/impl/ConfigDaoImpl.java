/**
 * 
 */
package com.chinadrtv.uam.dao.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.chinadrtv.uam.dao.ConfigDao;
import com.chinadrtv.uam.dao.wrapper.CriteriaWrapper;
import com.chinadrtv.uam.model.config.Config;
import com.chinadrtv.uam.model.config.ConfigGroup;
import com.chinadrtv.uam.model.config.ConfigProperty;

/**
 * @author dengqianyong
 *
 */
@Repository
public class ConfigDaoImpl extends HibernateDaoImpl implements ConfigDao {

	@Override
	public ConfigGroup findGroupByName(final String name) {
		CriteriaWrapper cw = new CriteriaWrapper();
		cw.addCriterion(Restrictions.eq("groupName", name));
		return findByCriteria(ConfigGroup.class, cw);
	}

	@Override
	public Config findConfigByName(Long groupId, String name) {
		CriteriaWrapper cw = new CriteriaWrapper();
		cw.addCriterion(Restrictions.eq("name", name));
		cw.addCriterion(Restrictions.eq("configGroup.id", groupId));
		return findByCriteria(Config.class, cw);
	}

	@Override
	public List<ConfigProperty> listConfigProperties(Long configId) {
		CriteriaWrapper cw = new CriteriaWrapper();
		cw.addCriterion(Restrictions.eq("config.id", configId));
		return listByCriteria(ConfigProperty.class, cw);
	}

}
