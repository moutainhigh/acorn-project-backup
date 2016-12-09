package com.chinadrtv.erp.core.service;
import com.chinadrtv.erp.model.Ems;
import java.util.*;

/**
 * EmsService
 *  
 * @author haoleitao
 * @date 2013-3-4 下午2:46:08
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface EmsService{
    Ems getEmsById(String emsId);
    String getCityNameById(String cityId);
    List<Ems> getAllEms();
    List<Ems> getAllEms(int index, int size);
    int getEmsCount();
    void saveEms(Ems ems);
    void addEms(Ems ems);
    void removeEms(Ems ems);

    /**
     * 根据spellid获取EMS
     * @param spellid
     * @return
     */
    Ems getEmsBySpellid(Integer spellid);
}
