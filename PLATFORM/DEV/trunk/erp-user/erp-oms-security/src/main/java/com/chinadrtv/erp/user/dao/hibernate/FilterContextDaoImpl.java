package com.chinadrtv.erp.user.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.security.FilterContext;
import com.chinadrtv.erp.user.dao.FilterContextDao;
import org.springframework.stereotype.Repository;

@Repository("filterContextDao")
public class FilterContextDaoImpl extends GenericDaoHibernate<FilterContext, Long>
  implements FilterContextDao
{
  public FilterContextDaoImpl()
  {
    super(FilterContext.class);
  }
}