/*
 * @(#)TestRnd.java 1.0 2014-6-4上午10:25:30
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.gonghang.service.test;

import java.util.UUID;

import junit.framework.TestCase;

import org.junit.Test;


/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2014-6-4 上午10:25:30 
 * 
 */
public class TestRnd extends TestCase {

	 
	@Test
    public void testRnd() {
		for(int i=0; i<10000; i++) {
			UUID uuid = UUID.randomUUID();
			String rndUid = uuid.toString();
			Integer rnd = Math.abs(rndUid.hashCode());
			String val = (String.valueOf(rnd) + "01").substring(0, 8);
			System.out.println(val);	
		}
    }
}
