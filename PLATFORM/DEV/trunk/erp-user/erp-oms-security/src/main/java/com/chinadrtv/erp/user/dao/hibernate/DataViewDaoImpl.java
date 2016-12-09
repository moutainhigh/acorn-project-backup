package com.chinadrtv.erp.user.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.security.DataView;
import com.chinadrtv.erp.user.dao.DataViewDao;
import org.springframework.stereotype.Repository;

@Repository("dataViewDao")
public class DataViewDaoImpl extends GenericDaoHibernate<DataView, Long>
  implements DataViewDao
{
  public DataViewDaoImpl()
  {
    super(DataView.class);
  }
}