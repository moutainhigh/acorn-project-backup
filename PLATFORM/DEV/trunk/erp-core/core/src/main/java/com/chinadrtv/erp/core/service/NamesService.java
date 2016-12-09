package com.chinadrtv.erp.core.service;
import com.chinadrtv.erp.model.Names;
import com.chinadrtv.erp.model.NamesId;

import java.util.*;
public interface NamesService{
    Names getNamesById(String namesId);
    public Names getNamesByID(NamesId namesId);
    List<Names> getAllNames(String tid);
    List<Names> getAllNames(int index, int size);
    int getNamesCount();
    void saveNames(Names names);
    void addNames(Names names);
    void removeNames(Names names);
    List<Names> getAllNames(String tid,String tdsc);
}
