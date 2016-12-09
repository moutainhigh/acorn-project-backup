package com.chinadrtv.erp.user.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.security.Filter;
import com.chinadrtv.erp.user.dao.FilterDao;
import org.springframework.stereotype.Repository;

@Repository("filterDao")
public class FilterDaoImpl extends GenericDaoHibernate<Filter, Long>
  implements FilterDao
{
  public FilterDaoImpl()
  {
    super(Filter.class);
  }
}