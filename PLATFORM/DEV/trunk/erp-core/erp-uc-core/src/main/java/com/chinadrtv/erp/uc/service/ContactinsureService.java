package com.chinadrtv.erp.uc.service;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.agent.Contactinsure;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-3-12
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface ContactinsureService extends GenericService<Contactinsure,String> {
    Contactinsure findContactinsure(String contactId);
    int saveContactinsure(Contactinsure contactinsure);
}
