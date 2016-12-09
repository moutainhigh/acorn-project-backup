package com.chinadrtv.erp.user.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.security.DataViewPermission;
import com.chinadrtv.erp.user.dao.DataViewPermissionDao;
import org.springframework.stereotype.Repository;

@Repository("dataViewPermissionDao")
public class DataViewPermissionDaoImpl extends GenericDaoHibernate<DataViewPermission, Long>
  implements DataViewPermissionDao
{
  public DataViewPermissionDaoImpl()
  {
    super(DataViewPermission.class);
  }
}