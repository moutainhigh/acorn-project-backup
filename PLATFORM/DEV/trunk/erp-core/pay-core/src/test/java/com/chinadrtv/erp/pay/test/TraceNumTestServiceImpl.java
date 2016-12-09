package com.chinadrtv.erp.pay.test;

import com.chinadrtv.erp.pay.core.service.TraceNumService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-15
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Primary
@Service
public class TraceNumTestServiceImpl implements TraceNumService {
    private Integer count;
    public Integer getTraceNum() {
        if(count==null)
            count=1;
        System.out.println("count nub:"+count);
        return count++;
    }
}
