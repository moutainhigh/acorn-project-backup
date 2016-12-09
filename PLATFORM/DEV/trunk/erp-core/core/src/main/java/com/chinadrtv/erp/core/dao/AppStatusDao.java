/*
 * @(#)AppStatusDao.java 1.0 2013-3-13下午3:08:20
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.core.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-3-13 下午3:08:20
 * 
 */
public interface AppStatusDao {

	public boolean testConStatus(Connection con)  throws SQLException;
}
