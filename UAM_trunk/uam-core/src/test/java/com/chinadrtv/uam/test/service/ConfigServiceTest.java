package com.chinadrtv.uam.test.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.chinadrtv.uam.model.config.ConfigGroup;
import com.chinadrtv.uam.service.ConfigService;
import com.chinadrtv.uam.test.BaseTest;

public class ConfigServiceTest extends BaseTest {

	@Resource
	private ConfigService configService;
	
	@Test
	@Rollback(false)
	public void testAddConfigGroup() {
		ConfigGroup group = new ConfigGroup();
		group.setGroupName("ip对应组");
		group.setDescrition("desc");
		
		configService.save(group);
	}
	
	@Test
	@Rollback(false)
	public void testMerge() {
		ConfigGroup group = configService.get(ConfigGroup.class, 14L);
		group.setGroupName("aaaa");
		
		configService.merge(group);
	}
}
