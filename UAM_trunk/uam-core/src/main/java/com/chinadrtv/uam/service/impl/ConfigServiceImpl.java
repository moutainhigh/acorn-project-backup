package com.chinadrtv.uam.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.uam.dao.ConfigDao;
import com.chinadrtv.uam.dto.ConfigDto;
import com.chinadrtv.uam.model.config.Config;
import com.chinadrtv.uam.model.config.ConfigGroup;
import com.chinadrtv.uam.model.config.ConfigProperty;
import com.chinadrtv.uam.service.ConfigService;

@Service
public class ConfigServiceImpl extends ServiceSupportImpl implements ConfigService {

	@Autowired
	private ConfigDao configDao;
	
	@Override
	public ConfigGroup findGroupByName(String name) {
		return configDao.findGroupByName(name);
	}

	@Override
	public Config findConfigByName(Long groupId, String name) {
		return configDao.findConfigByName(groupId, name);
	}

	@Override
	public List<ConfigDto> listConfig() {
		List<Config> configs = getHibernateDao().getAll(Config.class);
		List<ConfigDto> dtos = new ArrayList<ConfigDto>();
		for (Config c : configs) {
			ConfigDto dto = new ConfigDto();
			ConfigGroup gr = c.getConfigGroup();
			dto.setGroupId(gr.getId());
			dto.setGroupName(gr.getGroupName());
			dto.setConfigId(c.getId());
			dto.setConfigName(c.getName());
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public List<ConfigProperty> listConfigProperties(Long configId) {
		return configDao.listConfigProperties(configId);
	}

}
