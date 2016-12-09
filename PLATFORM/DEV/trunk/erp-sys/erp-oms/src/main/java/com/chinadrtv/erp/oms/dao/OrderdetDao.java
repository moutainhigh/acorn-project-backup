package com.chinadrtv.erp.oms.dao;
import java.util.List;

import com.chinadrtv.erp.model.Orderdet;
import com.chinadrtv.erp.core.dao.GenericDao;


public interface OrderdetDao extends GenericDao<Orderdet,java.lang.String>{
public List<Orderdet> getAllOrderdet(String orderid);
}
