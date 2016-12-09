package com.chinadrtv.erp.user.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.security.Widget;
import com.chinadrtv.erp.user.dao.WidgetDao;
import org.springframework.stereotype.Repository;

@Repository("widgetDao")
public class WidgetDaoImpl extends GenericDaoHibernate<Widget, Long>
  implements WidgetDao
{
  public WidgetDaoImpl()
  {
    super(Widget.class);
  }
}