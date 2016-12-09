package com.chinadrtv.erp.user.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.security.FilterPermission;
import com.chinadrtv.erp.user.dao.FilterPermissionDao;
import org.springframework.stereotype.Repository;

@Repository("filterPermissionDao")
public class FilterPermissionDaoImpl extends GenericDaoHibernate<FilterPermission, Long>
  implements FilterPermissionDao
{
  public FilterPermissionDaoImpl()
  {
    super(FilterPermission.class);
  }
}