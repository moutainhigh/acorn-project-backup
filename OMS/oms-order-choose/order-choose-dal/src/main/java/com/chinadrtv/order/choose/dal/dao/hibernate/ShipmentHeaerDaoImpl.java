package com.chinadrtv.order.choose.dal.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.model.trade.ShipmentHeader;
import com.chinadrtv.order.choose.dal.dao.ShipmentHeader2Dao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ShipmentHeaerDaoImpl extends GenericDaoHibernateBase<ShipmentHeader,Long> implements ShipmentHeader2Dao {
    public ShipmentHeaerDaoImpl() {
        super(ShipmentHeader.class);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

   
	public List<ShipmentHeader> queryShipment(Map<String, Parameter> param,Integer limit){
		
		String sql = "FROM ShipmentHeader o " +
				"WHERE o.accountStatusId=:oldStatus AND logisticsStatusId='0' AND o.crdt > SYSDATE-:days " +
				"AND exists (" +
					"SELECT c.companyId " +
					"FROM CompanyConfig c " +
					"WHERE c.manualActing=:manualActing and c.companyId = o.entityId " +
				")";

		
		// sql.append(" AND o.addressid = a.addressId ");
			Parameter page = new ParameterInteger("page", 0);
			page.setForPageQuery(true);

			Parameter rows = new ParameterInteger("rows", limit);
			rows.setForPageQuery(true);

			param.put("page", page);
			param.put("rows", rows);
			
			return this.findPageList(sql, param);
	}


	/**
	 * 
	* @Description: orderhist
	* @param param
	* @return
	* @return int
	* @throws
	 */
	public int updateShipmentHeaderById(Map<String, Parameter> param) {
		int result = this.execUpdate("UPDATE ShipmentHeader o " +
				"SET o.accountStatusId=:newStatus,o.senddt=:sendDate " +
				"WHERE o.id=:id ",param);
		return result;
	}
	
}
