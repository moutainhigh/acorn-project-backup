package com.chinadrtv.erp.oms.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.OrderSettlementType;
import com.chinadrtv.erp.oms.dao.OrderSettlementTypeDao;
import org.springframework.stereotype.Repository;

/**
 * 订单结算单数据访问
 * User: Administrator
 * Date: 13-1-11
 * Time: 上午11:23
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class OrderSettlementTypeDaoImpl extends GenericDaoHibernate<OrderSettlementType, Long> implements OrderSettlementTypeDao {
    public OrderSettlementTypeDaoImpl() {
        super(OrderSettlementType.class);
    }
}
