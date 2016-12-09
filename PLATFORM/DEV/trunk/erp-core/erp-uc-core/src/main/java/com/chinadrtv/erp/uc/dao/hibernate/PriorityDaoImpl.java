package com.chinadrtv.erp.uc.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.Priority;
import com.chinadrtv.erp.uc.dao.PriorityDao;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * Title: PriorityDaoImpl
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
@Repository
public class PriorityDaoImpl extends GenericDaoHibernate<Priority, String> implements PriorityDao {
    public PriorityDaoImpl() {
        super(Priority.class);
    }
}
