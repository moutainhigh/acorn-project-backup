/**
 * 
 */
package com.chinadrtv.erp.oms.util;

import static org.junit.Assert.*;

import org.junit.Test;




/*
 * StringUtil 测试
 * @author haoleitao
 * @date 2013-1-6 上午9:26:00
 *
 */
public class StringUtilTest {
	
	@Test
	public void nullToBlank(){
		assertTrue( StringUtil.nullToBlank(null).equals("") );
	}
	
	@Test
	public void isNullOrBank(){
		assertTrue(StringUtil.isNullOrBank(""));
		assertTrue(StringUtil.isNullOrBank(null));
	}

}
