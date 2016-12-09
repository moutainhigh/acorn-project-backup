package com.chinadrtv.uam.test.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.chinadrtv.uam.model.auth.res.ResAction;
import com.chinadrtv.uam.service.ResourceService;
import com.chinadrtv.uam.test.BaseTest;

public class ResourceServiceTest extends BaseTest {
	
	@Resource
	private ResourceService resourceService;

	@SuppressWarnings("unused")
	@Test
	public void testLoad() {
		List<ResAction> actions = resourceService.loadUamResources();
	}
}
