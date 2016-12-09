package com.chinadrtv.erp.user.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.security.FieldPermission;
import com.chinadrtv.erp.user.dao.FieldPermissionDao;
import org.springframework.stereotype.Repository;

@Repository("fieldPermissionDao")
public class FieldPermissionDaoImpl extends GenericDaoHibernate<FieldPermission, Long>
  implements FieldPermissionDao
{
  public FieldPermissionDaoImpl()
  {
    super(FieldPermission.class);
  }
}