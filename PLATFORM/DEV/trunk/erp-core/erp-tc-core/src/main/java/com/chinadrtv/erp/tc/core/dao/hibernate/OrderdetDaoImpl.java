/*
 * @(#)OrderdetDaoImpl.java 1.0 2013-1-28下午2:47:20
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.tc.core.dao.hibernate;

import java.util.Date;
import java.util.List;

import com.chinadrtv.erp.tc.core.dto.OrderTopItem;
import com.google.code.ssm.api.InvalidateSingleCache;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DoubleType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.tc.core.constant.SchemaNames;
import com.chinadrtv.erp.tc.core.dao.OrderdetDao;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013-1-28 下午2:47:20 
 * 
 */
@Repository
public class OrderdetDaoImpl extends GenericDaoHibernateBase<OrderDetail, Long> implements OrderdetDao {

    @Autowired
    private SchemaNames schemaNames;

	public OrderdetDaoImpl() {
		super(OrderDetail.class);
	}
	
	@Autowired
	@Required
	@Qualifier("sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.doSetSessionFactory(sessionFactory);
	}

	/* 
	* <p>Title: 获取订单详细</p>
	* <p>Description: </p>
	* @param orderhist
	* @return
	* @see com.chinadrtv.erp.tc.dao.OrderdetDao#getOrderDetList(com.chinadrtv.erp.model.agent.Order)
	*/ 
	public List<OrderDetail> getOrderDetList(Order orderhist) {
		String hql = "select od from OrderDetail od where od.orderid=:orderid";
		Parameter<String> param = new ParameterString("orderid", orderhist.getOrderid());
		return this.findList(hql, param);
	}

    public OrderDetail getOrderdetFromOrderdetId(String orderdetId) throws Exception
    {
        String hql = "from OrderDetail where orderdetid=:orderdetid";
        Parameter<String> param = new ParameterString("orderdetid", orderdetId);
        return this.find(hql, param);
    }

    public String getOrderdetId() {
        //序列名称
        Query q = getSession().createSQLQuery("select "+ schemaNames.getAgentSchema()+"SEQORDERDET.nextval from dual");
        return  q.list().get(0).toString();
    }

	/* 
	* <p>Title: 批量订单明细</p>
	* <p>Description: </p>
	* @param orderdetList
	* @throws Exception
	* @see com.chinadrtv.erp.tc.dao.OrderdetDao#deleteList(java.util.List)
	*/ 
	public void deleteList(List<OrderDetail> orderdetList) throws Exception {
		for(OrderDetail od : orderdetList){
			od.setOrderhist(null);
			this.getHibernateTemplate().delete(od);
		}
	}

    /**
     * 得到组合商品信息
     * @param prodid
     * @return
     */
    public List<OrderDetail> searchOrderByProdid(String prodid) {
        String hql = " from OrderDetail a where a.prodid = :Prodid";
        ParameterString parm = new ParameterString("Prodid",prodid);
        return findList(hql,parm);
    }

    /**
     * 获取最近指定时间的销量(昨日/一周)
     * @param prodid
     * @param ncfreeName
     * @return
     */
    public Double getOrderSoldQty(String prodid, String ncfreeName, Integer days){
        if(StringUtils.isBlank(ncfreeName)){
            Query q = getSession().createSQLQuery(
                    "select /*+INDEX(A IDX_ORDERDET_ORDERID)*/ nvl(sum(a.upnum+a.spnum),0) as soldQty from iagent.orderdet a " +
                            "left join iagent.orderhist b on a.orderid=b.orderid " +
                            "left join iagent.producttype c on a.producttype=c.recid " +
                            "where a.status in (1,2,5,6) and b.crdt >= to_date(trunc(sysdate - :days)) and b.crdt < to_date(trunc(sysdate)) and " +
                            "a.prodid = :prodid");
            q.setParameter("prodid", prodid);
            q.setParameter("days", days);
            Object result = q.uniqueResult();
            return result != null ? Double.parseDouble(result.toString()) : 0D;
        }
        else
        {
            Query q = getSession().createSQLQuery(
                    "select /*+INDEX(A IDX_ORDERDET_ORDERID)*/ nvl(sum(a.upnum+a.spnum),0) as soldQty from iagent.orderdet a " +
                            "inner join iagent.orderhist b on a.orderid=b.orderid " +
                            "left join iagent.producttype c on a.producttype=c.recid " +
                            "where a.status in (1,2,5,6) and b.crdt >= to_date(trunc(sysdate - :days)) and b.crdt < to_date(trunc(sysdate)) and " +
                            "a.prodid = :prodid and nvl(c.dsc,'0') = nvl(:ncfreeName,'0')");
            q.setParameter("prodid", prodid);
            q.setParameter("ncfreeName", ncfreeName);
            q.setParameter("days", days);
            Object result = q.uniqueResult();
            return result != null ? Double.parseDouble(result.toString()) : 0D;
        }

    }

    public List<OrderTopItem> getOrderTopItems(Integer days){
        Query q = getSession().createSQLQuery(
                "select /*+INDEX(A IDX_ORDERDET_ORDERID)*/ a.prodId, a.prodName, c.dsc as ncfreename, sum(a.upnum+a.spnum) as soldQty from iagent.orderdet a " +
                "inner join iagent.orderhist b on a.orderid=b.orderid " +
                "left join iagent.producttype c on a.producttype=c.recid " +
                "where a.status in ('1','2','5','6') and b.crdt >= to_date(trunc(sysdate - :days)) and b.crdt < to_date(trunc(sysdate)) " +
                "group by a.prodid, a.prodName, c.dsc order by sum(a.upnum+a.spnum) desc")
                .addScalar("prodId", StringType.INSTANCE)
                .addScalar("prodName", StringType.INSTANCE)
                .addScalar("ncfreeName", StringType.INSTANCE)
                .addScalar("soldQty", DoubleType.INSTANCE)
                .setResultTransformer(Transformers.aliasToBean(OrderTopItem.class));
        q.setParameter("days", days);
        q.setFirstResult(0);
        q.setMaxResults(20);
        return q.list();
    }

    @ReadThroughSingleCache(namespace = CACHE_KEY, expiration = 86400)
    public List<OrderTopItem> getCacheOrderTopItems(@ParameterValueKeyProvider Integer days){
       return getOrderTopItems(days);
    }

    @InvalidateSingleCache(namespace = CACHE_KEY)
    public void updateCacheOrderTopItems(@ParameterValueKeyProvider Integer days) {
        // Nothing really to do here.
    }

    private static final String CACHE_KEY=  "com.chinadrtv.erp.tc.core.dao.hibernate.OrderdetDaoImpl";
}
