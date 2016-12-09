package com.chinadrtv.erp.tc.core.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.UserProduct;
import com.chinadrtv.erp.tc.core.dao.UserProductDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-7-8
 * Time: 上午11:02
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class UserProductDaoImpl
        extends GenericDaoHibernate<UserProduct, Long>
        implements UserProductDao {

    public UserProductDaoImpl() {
        super(UserProduct.class);
    }

    @Override
    public List<UserProduct> getUserProducts(String userId) {
        Query q = getSession().createQuery("from UserProduct a where a.userId =:userId order by a.createDate desc");
        q.setParameter("userId", userId);
        return q.list();
    }

    @Override
    public Long getUserProductCount(String userId) {
        Query q = getSession().createQuery("select count(a.id) from UserProduct a where a.userId =:userId order by a.createDate desc");
        q.setParameter("userId", userId);
        return Long.parseLong(String.valueOf(q.uniqueResult()));
    }

    @Override
    public UserProduct getUserProduct(String userId, String productId) {
        Query q = getSession().createQuery(
                "from UserProduct a " +
                "where a.userId =:userId and a.productId = :productId order by a.createDate desc");
        q.setParameter("userId", userId);
        q.setParameter("productId", productId);
        List<UserProduct> list = q.list();
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public UserProduct getUserProduct(String userId, String productId, String productType) {
        Query q = getSession().createQuery(
                "from UserProduct a " +
                "where a.userId =:userId and a.productId = :productId and a.productType = :productType order by a.createDate desc");
        q.setParameter("userId", userId);
        q.setParameter("productId", productId);
        q.setParameter("productType", productType);
        List<UserProduct> list = q.list();
        return list.size() > 0 ? list.get(0) : null;
    }
}
