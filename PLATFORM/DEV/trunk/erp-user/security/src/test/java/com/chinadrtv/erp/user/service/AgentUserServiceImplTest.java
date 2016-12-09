/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.user.service;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.chinadrtv.erp.model.agent.UserLevel;
import com.chinadrtv.erp.test.SpringTest;
import com.chinadrtv.erp.user.dto.AgentUserInfo4TeleDist;

/**
 * 2013-7-25 上午10:26:48
 * @version 1.0.0
 * @author yangfei
 *
 */
public class AgentUserServiceImplTest extends SpringTest {

	@Autowired
	private AgentUserService agentUserService;
	
	@Test
	public void testQueryUserLevel() {
		String userId = "12650";
		AgentUserInfo4TeleDist aui = agentUserService.queryUserLevel(userId);
		assertTrue(aui != null);
	}
	
	@Test
	public void testQueryUserLevelInBatch() {
		List<String> userIds = new ArrayList<String>();
/*		userIds.add("12650");
		userIds.add("14594");*/
		Map<String, AgentUserInfo4TeleDist> users = agentUserService.queryUserLevelInBatch(userIds);
		assertTrue(users != null);
	}
	
	@Test
	@Rollback(false)
	public void batchMergeUserLevel() throws FileNotFoundException, IOException {
		URL url = AgentUserServiceImplTest.class.getResource("");
		File file = new File(url.getPath() + "/26日IAGENT级别导入.xls");
		
		Integer result = 0;
		if (file.exists()) {
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
			HSSFSheet sheet = workbook.getSheetAt(0);
			List<UserLevel> list = parse(sheet);
			result = agentUserService.removeAndSaveUserLevels(list);
		}
		
		Assert.assertTrue(result > 0);
	}
	
	private List<UserLevel> parse(HSSFSheet sheet) {
		List<UserLevel> result = new ArrayList<UserLevel>();
		for (int idx = 1; idx < sheet.getLastRowNum(); idx++) {
			Row row = sheet.getRow(idx);
			if (row != null) {
				UserLevel ul = new UserLevel();
				ul.setUserId(String.valueOf(row.getCell(0).getNumericCellValue()));
				ul.setLevelId(row.getCell(1) != null ? row.getCell(1).getStringCellValue() : null);
				ul.setLevelId2(row.getCell(2) != null ? row.getCell(2).getStringCellValue() : null);
				ul.setLevelId3(row.getCell(3) != null ? row.getCell(3).getStringCellValue() : null);
				ul.setModifyTime(new Date());
				ul.setValid("-1");
				result.add(ul);
			}
		}
		return result;
	}
}
