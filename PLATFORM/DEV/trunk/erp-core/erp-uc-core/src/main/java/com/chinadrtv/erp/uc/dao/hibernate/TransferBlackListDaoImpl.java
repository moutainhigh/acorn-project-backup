package com.chinadrtv.erp.uc.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.TransferBlackList;
import com.chinadrtv.erp.uc.dao.TransferBlackListDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * Title: TransferBlackListDaoImpl
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
@Repository
public class TransferBlackListDaoImpl extends GenericDaoHibernate<TransferBlackList, String> implements TransferBlackListDao {

    public TransferBlackListDaoImpl() {
        super(TransferBlackList.class);
    }

    protected Session getSession() {
        return SessionFactoryUtils.getSession(getSessionFactory(), true);
    }

    @Override
    public String getValidContactBlackList(String contactId) {
        Query sqlQuery = getSession().createSQLQuery("select s.contactId from iagent.TRANSFER_PHNBLACKLIST s where s.VALID='-1' and s.COMPLETERATE < 0.2 and s.CONTACTID=:contactId");
        sqlQuery.setParameter("contactId", contactId);
        return (String) sqlQuery.uniqueResult();
    }
}
