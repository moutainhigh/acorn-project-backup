/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.marketing.service.impl;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.chinadrtv.erp.test.SpringTest;

/**
 * 2013-5-8 下午4:57:11
 * @version 1.0.0
 * @author yangfei
 *
 */
public class JDBCTemplateTest extends SpringTest{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Test
	public void testQueryUsingJdbcTemplate() {
		String sql="select * from ( select ca.id caID, ca.name caName,ltp.name ltpName,con.contactid conid,con.name conName,lt.create_date ltCreateDate,ca.end_date endDate, lt.status ltStatus, lt.userID userID, lt.id tid from crmmarketing.lead_task lt left join iagent.contact con on lt.contact_id=con.contactid left join crmmarketing.lead le on lt.lead_id=le.id left join crmmarketing.campaign ca on le.campaign_id=ca.id left join crmmarketing.lead_type ltp on ca.lead_type_id = ltp.id where 1=1  AND lt.userID = ? ) where rownum <= ?";
		StringBuilder sb = new StringBuilder(sql);
		List<Map<String, Object>> objList = jdbcTemplate.queryForList(sb.toString(), new Object[]{"9124",2});
		System.out.println(objList.size());
	}

}
