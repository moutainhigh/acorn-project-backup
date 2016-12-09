package com.chinadrtv.order.choose.dal.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.model.Orderhist;
import com.chinadrtv.order.choose.dal.dao.OrderChooseAutomaticDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class OrderChooseAutomaticDaoImpl extends GenericDaoHibernateBase<Orderhist,Long> implements OrderChooseAutomaticDao {
    public OrderChooseAutomaticDaoImpl() {
        super(Orderhist.class);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

    /**
	* <p>Title:</p>
	* <p>Description: </p>
	* @param param
	* @return
	*/ 
	public int automaticChooseOrder(Map<String,Parameter> param) {
		int result = this.execUpdate("UPDATE Orderhist o " +
						"SET o.status=:newStatus,o.senddt=:sendDate " +
						"WHERE  exists (" +
							"SELECT h.orderId  " +
							"FROM ShipmentHeader h " +
							"WHERE h.accountStatusId=:oldStatus AND h.logisticsStatusId='0' " +
							"AND h.orderId=o.orderid " +
							"AND exists (" +
								"SELECT c.companyId " +
								"FROM CompanyConfig c " +
								"WHERE c.manualActing=:manualActing and c.companyId = h.entityId " +
							")" +
						")",param);
		return result;
	}
	

	public int automaticChooseShipment(Map<String,Parameter> param) {
		
		int result = this.execUpdate("UPDATE ShipmentHeader o " +
						"SET o.accountStatusId=:newStatus,o.senddt=:sendDate " +
						"WHERE o.accountStatusId=:oldStatus AND logisticsStatusId='0' " +
						"AND exists (" +
							"SELECT c.companyId " +
							"FROM CompanyConfig c " +
							"WHERE c.manualActing=:manualActing and c.companyId = o.entityId " +
						")",param);
		return result;
	}




	public int automaticChooseOrderForOldData(Map<String, Parameter> param) {
		int result = this.execUpdate("UPDATE Orderhist o " +
				"SET o.status=:newStatus,o.senddt=:sendDate " +
				"WHERE o.status=:oldStatus " +
				"AND exists (" +
					"SELECT c.companyId " +
					"FROM CompanyConfig c " +
					"WHERE c.manualActing=:manualActing and c.companyId = o.entityid " +
				")",param);
		return result;
	}
	

	public int updateOderhistById(Map<String, Parameter> param) {
		int result = this.execUpdate("UPDATE Orderhist o " +
				"SET o.status=:newStatus,o.senddt=:sendDate " +
				"WHERE o.orderid=:orderId ",param);
		return result;
	}
	
	public List<Orderhist> queryOrderhist(Map<String, Parameter> param,Integer limit){
		
		String sql = "FROM Orderhist o " +
				"WHERE o.status=:oldStatus AND o.isassign=:isAssign " +
				"AND o.crdt > SYSDATE-:days " +
				"AND exists (" +
					"SELECT c.companyId " +
					"FROM CompanyConfig c " +
					"WHERE c.manualActing=:manualActing and c.companyId = o.entityid " +
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
}
