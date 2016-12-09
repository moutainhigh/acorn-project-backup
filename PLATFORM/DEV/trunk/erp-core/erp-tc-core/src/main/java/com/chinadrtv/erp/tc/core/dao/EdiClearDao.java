package com.chinadrtv.erp.tc.core.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.EdiClear;

/**
 * Created with IntelliJ IDEA.
 * User: liyu
 * Date: 13-4-26
 * Time: 下午2:38
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 * To change this template use File | Settings | File Templates.
 */

public interface EdiClearDao extends GenericDao<EdiClear, String> {

    EdiClear getEdiClear(String ediClearId);

}
