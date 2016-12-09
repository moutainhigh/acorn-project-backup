package com.chinadrtv.wms2iag.sync.biz;

import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-21
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface WmsSyncBizHandler {
    boolean wmsSync(Date startDate);
}
