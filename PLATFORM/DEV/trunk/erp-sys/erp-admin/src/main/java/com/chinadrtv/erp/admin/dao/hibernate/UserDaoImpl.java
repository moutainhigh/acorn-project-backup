package com.chinadrtv.erp.admin.dao.hibernate;

import com.chinadrtv.erp.admin.dao.UserDao;
import com.chinadrtv.erp.admin.model.User;
import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: taoyawei
 * Date: 12-8-10
 * Time: 上午10:19
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class UserDaoImpl extends GenericDaoHibernate<User, String> implements UserDao {

    public UserDaoImpl() {
        super(User.class);
    }
    public List<User> getAllUser()
    {
        Session session = getSession();
        Query query = session.createQuery("from User");
        return query.list();
    }
}
