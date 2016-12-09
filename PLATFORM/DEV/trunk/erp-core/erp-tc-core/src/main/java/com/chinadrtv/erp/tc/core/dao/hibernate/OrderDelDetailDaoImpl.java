package com.chinadrtv.erp.tc.core.dao.hibernate;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.hibernate.SessionFactory;
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
import com.chinadrtv.erp.tc.core.dao.OrderDelDetailDao;
import com.chinadrtv.erp.tc.core.dao.OrderhistDao;

@Repository("orderDelDetailDao")
public class OrderDelDetailDaoImpl extends GenericDaoHibernateBase<OrderDetail, String> implements OrderDelDetailDao {

	public OrderDelDetailDaoImpl() {
		super(OrderDetail.class);
	}

	@Autowired
	private SchemaNames schemaNames;

/*	@Autowired
	public OrderhistService orderhistService;*/
    @Autowired
	private OrderhistDao orderhistDao;

	/**
	 * 查询单个订单明细
	 * 
	 * @param orderdetid
	 * @return
	 */
	public OrderDetail queryOrderdetbydetid(String orderdetid) {
		OrderDetail orderdet = this.queryOrderdetBydtid(orderdetid); // 查询单个订单商品明细
		return orderdet;
	}

	/**
	 * 删除订单明细
	 * 
	 * @param orderdet
	 * @return
	 * @throws java.sql.SQLException
	 */
	public int delOrderDetail(OrderDetail orderdet) throws SQLException {
		int detid = 0;
		if (orderdet.getId() != null) {
			String hql = "delete from OrderDetail where id=:id";
			Parameter<String> parameter = new ParameterString("id", orderdet.getId().toString());
			detid = this.execUpdate(hql, parameter);
		}
		return detid;
	}

	/**
	 * 查询订单明细表获得单个商品的信息
	 * 
	 * @param orderdetid
	 *            订单明细表的明细编号
	 * @return
	 */
	public OrderDetail queryOrderdetBydtid(String orderdetid) {

		String hql = "select t from OrderDetail t where t.soldwith <>'1' and t.orderdetid=:orderdetid";
		Parameter<String> parameter = new ParameterString("orderdetid", orderdetid);
		OrderDetail orderdet = this.find(hql, parameter);

		return orderdet;
	}

	/**
	 * 查询订单表的orderid获得全部信息
	 */
	public Order queryOrderhistbyorid(String orderid) {
		return orderhistDao.getOrderHistByOrderid(orderid);
	}

	/**
	 * 订单编号查询订单信息全部明细
	 * 
	 * @param orderid
	 */
	public List<OrderDetail> queryOrderdetbyorid(String orderid) {
		String hql = "select t from OrderDetail t where t.orderid=:orderid";
		Parameter<String> parameter = new ParameterString("orderid", orderid);
		return this.findList(hql, parameter);

	}

	/**
	 * 重算订单总金额 重算订单实际支付金额
	 * 
	 * @throws Exception
	 */
	public void totalOrderHistprice(List<OrderDetail> orderdetList, String orderid) throws Exception {

		Order orderhist = orderhistDao.getOrderHistByOrderid(orderid);
		BigDecimal bigDecprice = new BigDecimal(0);
		BigDecimal bigmailprice = new BigDecimal(0);
		for (OrderDetail orderdet1 : orderdetList) {
			BigDecimal b1 = orderdet1.getUprice();
			BigDecimal b2 = new BigDecimal(Double.toString(orderdet1.getUpnum()));
			BigDecimal b3 = orderdet1.getSprice();
			BigDecimal b4 = new BigDecimal(Double.toString(orderdet1.getSpnum()));
			BigDecimal b5 = orderdet1.getFreight();
			bigDecprice = bigDecprice.add(b2.multiply(b1).add(b3.multiply(b4)));
			bigmailprice = bigDecprice.add(b5);
		}
		if (bigDecprice != null && bigmailprice != null) {
			orderhist.setNowmoney(bigDecprice);
			orderhist.setTotalprice(bigDecprice.add(bigmailprice));
			orderhistDao.saveOrUpdate(orderhist);
		}
	}

	/**
	 * 重算积分
	 * 
	 * @return
	 * @throws SQLException
	 */
	public String useconpointProc(String sa, String orderid) throws SQLException {

		Order orderhist = orderhistDao.getOrderHistByOrderid(orderid);
		Connection conn = getSession().connection();
		String str = "";
		CallableStatement proc = conn.prepareCall("{call " + schemaNames.getAgentSchema()
				+ "p_n_useconpoint(?,?,?,?,?)}");
		proc.setString(1, "0");
		proc.setString(2, orderhist.getOrderid());
		proc.setString(3, orderhist.getContactid());
		proc.setDouble(4, Double.parseDouble(orderhist.getJifen()));
		proc.setString(5, sa);// 当前登录人
		proc.registerOutParameter(6, Types.NVARCHAR);
		proc.execute();
		if (proc.getObject(6) != null) {
			str = proc.getString(6);
		}
		if (conn != null)
			conn.close();

		return str;
	}

	/**
	 * 重算返券
	 * 
	 * @return
	 * @throws SQLException
	 * @throws NumberFormatException
	 */
	public String conticketProc(String sa, String orderid) throws SQLException {

		Order orderhist = orderhistDao.getOrderHistByOrderid(orderid);
		Connection conn = getSession().connection();
		String str = "";
		CallableStatement proc = conn
				.prepareCall("{call " + schemaNames.getAgentSchema() + "p_n_conticket(?,?,?,?,?)}");
		proc.setString(1, "0");
		proc.setDouble(2, Double.parseDouble(orderhist.getTotalprice().toString()));
		proc.setDouble(3, Double.parseDouble(String.valueOf(orderhist.getTicket() * orderhist.getTicketcount())));
		proc.setString(4, orderhist.getOrderid());
		proc.setString(5, orderhist.getContactid());// 当前登录人
		proc.setString(6, sa);// 当前登录人
		proc.registerOutParameter(6, Types.NVARCHAR);
		proc.execute();
		if (proc.getObject(6) != null) {
			str = proc.getString(6);
		}
		if (conn != null)
			conn.close();
		return str;
	}

	@Autowired
	@Required
	@Qualifier("sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.doSetSessionFactory(sessionFactory);
	}

}
