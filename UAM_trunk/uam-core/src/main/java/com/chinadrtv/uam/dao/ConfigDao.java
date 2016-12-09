package com.chinadrtv.uam.dao;

import java.util.List;

import com.chinadrtv.uam.model.config.Config;
import com.chinadrtv.uam.model.config.ConfigGroup;
import com.chinadrtv.uam.model.config.ConfigProperty;

public interface ConfigDao {

	/**
	 * 
	 * @param name
	 * @return
	 */
	ConfigGroup findGroupByName(String name);

	/**
	 * 
	 * @param groupId
	 * @param name
	 * @return
	 */
	Config findConfigByName(Long groupId, String name);

	/**
	 * 
	 * @param configId
	 * @return
	 */
	List<ConfigProperty> listConfigProperties(Long configId);
}
