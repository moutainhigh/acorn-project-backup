package com.chinadrtv.erp.sales.dao.hibernate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.model.agent.Product;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.inventory.ProductType;
import com.chinadrtv.erp.sales.dao.ProductTypeDao;

/**
 * 
 * @author dengqianyong
 *
 */
@Repository
public class ProductTypeDaoImpl extends
		GenericDaoHibernate<ProductType, String> implements ProductTypeDao {

	public ProductTypeDaoImpl() {
		super(ProductType.class);
	}

	@Override
	public ProductType getProductType(final String productId, final String type) {
		return getHibernateTemplate().execute(new HibernateCallback<ProductType>() {
			@Override
			public ProductType doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(ProductType.class);
				criteria.add(Restrictions.eq("recid", type));
				criteria.add(Restrictions.eq("prodrecid", productId));
				return (ProductType) criteria.uniqueResult();
			}
		});
	}

    public List<ProductType> getProductTypes(List<OrderDetail> orderDetailList)
    {
        if(orderDetailList==null||orderDetailList.size()==0)
            return new ArrayList<ProductType>();
        int index=0;
        StringBuilder strBld=new StringBuilder("from ProductType where ");
        Map<String,Object> mapParms=new Hashtable<String, Object>();
        for(OrderDetail entry:orderDetailList)
        {
            String prodName="prodId"+index;
            String typeName="type"+index;
            if(index==0)
            {
                strBld.append(String.format("(prodrecid=:%s and recid=:%s)", prodName, typeName));
            }
            else
            {
                strBld.append(String.format(" or (prodrecid=:%s and recid=:%s)",prodName,typeName));
            }
            mapParms.put(prodName,entry.getProdid());
            mapParms.put(typeName,entry.getProducttype());
            index++;
        }

        Query query=this.getSession().createQuery(strBld.toString());
        for(Map.Entry<String,Object> entry:mapParms.entrySet())
        {
            query.setParameter(entry.getKey(),entry.getValue());
        }
        return query.list();
    }

	
}
