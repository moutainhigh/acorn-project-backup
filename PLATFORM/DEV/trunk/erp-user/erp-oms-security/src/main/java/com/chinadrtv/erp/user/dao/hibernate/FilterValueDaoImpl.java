package com.chinadrtv.erp.user.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.security.FilterValue;
import com.chinadrtv.erp.user.dao.FilterValueDao;
import org.springframework.stereotype.Repository;

@Repository("filterValueDao")
public class FilterValueDaoImpl extends GenericDaoHibernate<FilterValue, Long>
  implements FilterValueDao
{
  public FilterValueDaoImpl()
  {
    super(FilterValue.class);
  }
}