package com.chinadrtv.erp.tc.core.dao;

import java.sql.SQLException;
import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.tc.core.model.Integarl;

/**
 * Created with IntelliJ IDEA.
 * User: liyu
 * Date: 13-1-4
 * Time: 下午3:07
 * To change this template use File | Settings | File Templates.
 */
@Deprecated
public interface JifenReviseDao extends GenericDao<OrderDetail, String>  {
     abstract void getJifenproc(Integarl integarl) throws SQLException;
     abstract  void callProcedure(String sql,List<ParameterString> parmList);
}

