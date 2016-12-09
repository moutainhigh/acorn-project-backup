package com.chinadrtv.erp.knowledge.service;

import javax.annotation.Resource;

import org.junit.Test;

import com.chinadrtv.erp.knowledge.dto.CategoryNode;
import com.chinadrtv.erp.test.SpringTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CategoryServiceTest extends SpringTest {

	@Resource
	private CategoryService categoryService;
	
	@Test
	public void testTreeCategories() throws JsonProcessingException {
		CategoryNode obj = categoryService.treeCategories("OUT");
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(obj));
	}
}
