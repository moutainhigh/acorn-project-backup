package com.chinadrtv.erp.oms.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-6-12
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class AcornAreaResult extends AcornResult implements Serializable {
    public AcornAreaResult()
    {
        areas=new ArrayList<String>();
    }
    public List<String> getAreas() {
        return areas;
    }

    public void setAreas(List<String> areas) {
        this.areas = areas;
    }

    protected List<String> areas;
}
