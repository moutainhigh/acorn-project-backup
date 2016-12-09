package com.chinadrtv.uam.service;

import java.util.List;

import com.chinadrtv.uam.dto.ConfigDto;
import com.chinadrtv.uam.model.config.Config;
import com.chinadrtv.uam.model.config.ConfigGroup;
import com.chinadrtv.uam.model.config.ConfigProperty;

public interface ConfigService extends ServiceSupport {
	
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
	 * @return
	 */
	List<ConfigDto> listConfig();
	
	/**
	 * 
	 * @param configId
	 * @return
	 */
	List<ConfigProperty> listConfigProperties(Long configId);

}
