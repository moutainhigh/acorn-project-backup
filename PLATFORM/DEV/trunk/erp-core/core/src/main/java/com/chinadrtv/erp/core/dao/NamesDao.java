package com.chinadrtv.erp.core.dao;
import java.util.List;

import com.chinadrtv.erp.model.Names;
import com.chinadrtv.erp.model.NamesId;
import com.chinadrtv.erp.core.dao.GenericDao;


public interface NamesDao extends GenericDao<Names,java.lang.String>{
	public List<Names> getAllNames(String tid);
	public Names getNamesByID(NamesId namesId);
	
	Names getNamesById(String tid, String id);

    Names getNamesByTidAndId(String tid, String id);

    void addNames(Names names);

    public List<Names> getAllNames(String tid, String tdsc);
}
