package com.chinadrtv.uam.controller;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.uam.dto.ConfigDto;
import com.chinadrtv.uam.model.config.Config;
import com.chinadrtv.uam.model.config.ConfigGroup;
import com.chinadrtv.uam.model.config.ConfigProperty;
import com.chinadrtv.uam.service.ConfigService;

@Controller
@RequestMapping("/param")
public class ParamController {
	
	@Autowired
	private ConfigService configService;

	@RequestMapping(value = "")
	public String viewParamIndex() {
		return "common/template2";
	}
	
	@RequestMapping(value = "/item1", method = RequestMethod.GET)
	public String viewParamItem1() {
		return "param/content/item1";
	}
	
	@RequestMapping(value = "/userParam", method = RequestMethod.GET)
	public String userParam() {
		return "param/content/userParam";
	}	
	@RequestMapping(value = "/group", method = RequestMethod.GET)
	public String viewParamItem3() {
		return "param/content/group";
	}
	
	@RequestMapping(value = "/config", method = RequestMethod.GET)
	public String viewParamItem4() {
		return "param/content/config";
	}
	
	@RequestMapping(value = "/sys", method = RequestMethod.GET)
	public String viewParamSys() {
		return "param/content/sys";
	}
	
	/**** Parameter Config Operations ****/
	@ResponseBody
	@RequestMapping(value = "/group/list")
	public List<ConfigGroup> listConfigGroup() {
		return configService.getAll(ConfigGroup.class);
	}
	
	@ResponseBody
	@RequestMapping(value = "/group/save", method = RequestMethod.POST)
	public Map<String, String> saveConfigGroup(@RequestBody ConfigGroup group) {
		Map<String, String> result = new HashMap<String, String>();

		boolean checkNameExists = true;
		if (group.getId() != null) {
			ConfigGroup existGroup = configService.get(ConfigGroup.class,
					group.getId());
			if (existGroup.getGroupName().equals(group.getGroupName())) {
				checkNameExists = false;
			}
		}
		if (checkNameExists
				&& configService.findGroupByName(group.getGroupName()) != null) {
			result.put("res", "-1");
			result.put("msg", "相同配置组名称已存在");
			return result;
		}

		configService.saveOrUpdate(group);
		result.put("res", "1");
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/group/remove/{id}")
	public String removeConfigGroup(@PathVariable Long id) {
		ConfigGroup group = configService.get(ConfigGroup.class, id);
		if (group != null) {
			configService.delete(group);
		}
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "/config/list", method = RequestMethod.POST)
	public List<ConfigDto> listConfig() {
		return configService.listConfig();
	}
	
	@ResponseBody
	@RequestMapping(value = "/config/save", method = RequestMethod.POST)
	public Map<String, String> saveConfig(@RequestBody ConfigDto configDto) {
		Map<String, String> result = new HashMap<String, String>();
		boolean checkNameExists = true;
		if (configDto.getConfigId() != null) {
			Config existConfig = configService.get(Config.class,
					configDto.getConfigId());

			if (existConfig.getName().equals(configDto.getConfigName())
					&& existConfig.getConfigGroup().getId().equals(configDto.getGroupId())) {
				checkNameExists = false;
			}
		}

		if (checkNameExists
				&& configService.findConfigByName(configDto.getGroupId(),
						configDto.getConfigName()) != null) {
			result.put("res", "-1");
			result.put("msg", "相同配置项名称已存在");
			return result;
		}

		ConfigGroup group = configService.get(ConfigGroup.class,
				configDto.getGroupId());
		Config config = new Config();
		if (configDto.getConfigId() != null) {
			config.setId(configDto.getConfigId());
		}
		config.setConfigGroup(group);
		config.setName(configDto.getConfigName());

		configService.saveOrUpdate(config);
		result.put("res", "1");
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/config/remove/{id}")
	public String removeConfig(@PathVariable Long id) {
		Config config = configService.get(Config.class, id);
		if (config != null) {
			configService.delete(config);
		}
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "/property/save")
	public Map<String, String> saveConfigProperty(
			@RequestParam(value = "configId", required = true) Long configId,
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "key", required = true) String key,
			@RequestParam(value = "value", required = true) String value) {
		Map<String, String> result = new HashMap<String, String>();
		
		if (id != null) {
			ConfigProperty cp = configService.get(ConfigProperty.class, id);
			cp.setKey(key);
			cp.setValue(value);
			configService.update(cp);
			
			result.put("res", "1");
			return result;
		}
		
		ConfigProperty cp = new ConfigProperty();
		Config config = configService.get(Config.class, configId);
		cp.setConfig(config);
		cp.setKey(key);
		cp.setValue(value);
		configService.save(cp);
		
		result.put("res", "1");
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/property/remove/{id}")
	public String removeProperty(@PathVariable Long id) {
		ConfigProperty cp = configService.get(ConfigProperty.class, id);
		if (cp != null) {
			configService.delete(cp);
		}
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "/config/{id}/properties")
	public Collection<ConfigProperty> listConfigProperties(@PathVariable Long id) {
		return configService.listConfigProperties(id);
	}
}
