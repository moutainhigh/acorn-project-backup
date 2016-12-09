package com.chinadrtv.erp.oms.dao.hibernate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.Orderhist;
import com.chinadrtv.erp.model.trade.OrderPaymentConfirm;
import com.chinadrtv.erp.oms.dao.OrderPaymentConfirmDao;
import com.chinadrtv.erp.oms.dao.OrderhistDao;
import com.chinadrtv.erp.oms.dto.OrderPaymentDto;
import com.chinadrtv.erp.oms.util.Page;
import com.chinadrtv.erp.user.util.SecurityHelper;

/**
 * zhanguosheng
 */
@Repository
public class OrderPaymentConfirmDaoImpl extends GenericDaoHibernate<OrderPaymentConfirm, Long> implements OrderPaymentConfirmDao {
	
	@Autowired
	private OrderhistDao orderhistDao;

	public OrderPaymentConfirmDaoImpl() {
		super(OrderPaymentConfirm.class);
	}

	@Override
	public Page<OrderPaymentDto> queryOrderPayment(String orderId, String payUserName, String payUserTel, String payType, String orderDateS, String orderDateE, int index, int size) {
//		SELECT 
//		OH.ORDERID, 	--订单编号
//		OH.CRDT, 			--订单提交时间ORDERHIST.CRDT
//		NA.DSC,				--订单状态 NAMES.DSC NAMES.TID='ORDERSTATUS' NAMES.ID=STATUS
//		OH.TOTALPRICE,--订单金额ORDERHIST.TOTALPRICE
//		OH.CONTACTID,	--客户编号ORDERHIST.CONTACTID
//		C.NAME,				--客户姓名CONTACT.NAME
//		NB.ID,				--支付方式id
//		NB.DSC,				--支付方式 NAMES.DSC NAMES.TID='PAYTYPE' NAMES.ID=PAYYPE
//		OH.CONFIRM		--是否已付款 ORDERHIST.CONFIRM 不是-1 未确认
//		FROM IAGENT.ORDERHIST OH 
//		LEFT JOIN IAGENT.NAMES NA	ON NA.ID=OH.STATUS AND NA.TID='ORDERSTATUS'
//		LEFT JOIN IAGENT.NAMES NB	ON NB.ID=OH.PAYTYPE AND NB.TID='PAYTYPE'
//		LEFT JOIN IAGENT.CONTACT C ON C.CONTACTID=OH.CONTACTID
//		WHERE OH.STATUS='1' AND NVL(OH.CONFIRM,0)!='-1' AND NB.ID IN ('12','13')
//		AND OH.ORDERID='22586436'	--订单编号
//		AND C.NAME LIKE '%2%'				--订购人姓名 CONTACT.NAME
//		AND NB.ID='12'					--付款方式 PAYTYPE
//		AND OH.CRDT>TO_DATE('2010-01-01', 'yyyy-MM-dd')	--订购日期左 ORDERHIST.CRDT
//		AND OH.CRDT<TO_DATE('2014-01-01', 'yyyy-MM-dd')	--订购日期右ORDERHIST.CRDT
//		AND '18602188222' IN (SELECT DISTINCT P.PHN2 FROM IAGENT.PHONE P WHERE P.CONTACTID=OH.CONTACTID)	--订购人电话 
		
		Map<String, Object> params = new HashMap<String, Object>();
		if(orderId!=null && !"".equals(orderId)) params.put("orderId", orderId);
		if(payUserName!=null && !"".equals(payUserName)) params.put("payUserName", "%" + payUserName + "%");
		if(payType!=null && !"".equals(payType)) params.put("payType", payType);
		if(orderDateS!=null && !"".equals(orderDateS)) params.put("orderDateS", orderDateS);
		if(orderDateE!=null && !"".equals(orderDateE)) params.put("orderDateE", orderDateE);
		if(payUserTel!=null && !"".equals(payUserTel)) params.put("payUserTel", payUserTel);

		List<OrderPaymentDto> opds = new ArrayList<OrderPaymentDto>();
		Session session = getSession();
		String sql = 
			"SELECT OH.ORDERID AS ORDERID, OH.CRDT AS CRDT, NA.DSC AS DSCA, OH.TOTALPRICE AS TOTALPRICE, OH.CONTACTID AS CONTACTID, C.NAME AS NAME, NB.ID AS PAYTYPE, NB.DSC AS DSCB, OH.CONFIRM AS CONFIRM " +
			"FROM IAGENT.ORDERHIST OH " +
			"LEFT JOIN IAGENT.NAMES NA	ON NA.ID=OH.STATUS AND NA.TID='ORDERSTATUS' " +
			"LEFT JOIN IAGENT.NAMES NB	ON NB.ID=OH.PAYTYPE AND NB.TID='PAYTYPE' " +
			"LEFT JOIN IAGENT.CONTACT C ON C.CONTACTID=OH.CONTACTID " +
			"WHERE OH.STATUS='1' AND NVL(OH.CONFIRM,0)!='-1' AND NB.ID IN ('12','13') " + 
			(params.containsKey("orderId") ? " AND OH.ORDERID=:orderId " : "") +
			(params.containsKey("payUserName") ? " AND C.NAME LIKE :payUserName " : "") +
			(params.containsKey("payType") ? " AND NB.ID=:payType " : "") +
			(params.containsKey("orderDateS") ? " AND OH.CRDT>TO_DATE(:orderDateS, 'yyyy-MM-dd') " : "") +
			(params.containsKey("orderDateE") ? " AND OH.CRDT<TO_DATE(:orderDateE, 'yyyy-MM-dd') " : "") +
			(params.containsKey("payUserTel") ? " AND :payUserTel IN (SELECT DISTINCT P.PHN2 FROM IAGENT.PHONE P WHERE P.CONTACTID=OH.CONTACTID) " : "");
		
		//查询数量
		String sqlc = "SELECT COUNT(1) FROM ( " + sql + " )";
		Query qc = session.createSQLQuery(sqlc);
		//查询数据
		Query q = session.createSQLQuery(sql);
		List<String> parameters = Arrays.asList(q.getNamedParameters());
        for(String parameterName : parameters) {
            if(params.containsKey(parameterName)){
                q.setParameter(parameterName, params.get(parameterName));
                qc.setParameter(parameterName, params.get(parameterName));
            }
        }
		long count = Long.valueOf(qc.list().get(0).toString());
		q.setFirstResult(index);
		q.setMaxResults(size);
		List<Object[]> list = q.list();
		for(Object[] obj : list){
			OrderPaymentDto opd = new OrderPaymentDto();
			opd.setOrderId( (String) obj[0] );
			opd.setCrdt( (Date) obj[1] );
			opd.setDsca( (String) obj[2] );
			if(obj[3]!=null){
				opd.setTotalPrice( ((BigDecimal) obj[3]).floatValue() );
			}
			opd.setContactId( (String) obj[4] );
			opd.setName( (String) obj[5] );
			opd.setPayType((String) obj[6]);
			opd.setDscb( (String) obj[7] );
			
			String confirm = (String) obj[8];
			if("-1".equals(confirm)){
				opd.setConfirm( "已付款" );
			}else{
				opd.setConfirm( "未付款" );
			}
			
			opds.add(opd);
		}
		return new Page<OrderPaymentDto>(opds,count);
	}

	@Override
	public void confirm(String orderId, String payNo, String payUser, Date payDate) {
		
		Orderhist oh = orderhistDao.get(orderId);
		if(orderId!=null && oh!=null && !"-1".equals(oh.getConfirm())){
			OrderPaymentConfirm opc = new OrderPaymentConfirm();
			Date now = new Date();
			opc.setOrderId(oh.getOrderid());
			opc.setPayType(oh.getPaytype());
			opc.setCreateDate(now);
			opc.setUpdateDate(now);
			if(SecurityHelper.getLoginUser()!=null){
				opc.setCreateUser(SecurityHelper.getLoginUser().getUserId());
				opc.setUpdateUser(SecurityHelper.getLoginUser().getUserId());
			}
			if(oh.getPaytype()!=null && "12".equals(oh.getPaytype())){
				opc.setPayNo(payNo);
			}
			
			opc.setPayDate(payDate);
			opc.setPayUser(payUser);
			
			//保存邮购确认表
			this.save(opc);
			
			//更新ORDERHIST
			Session session = getSession();
			String sqlA = "UPDATE IAGENT.ORDERHIST oh set OH.CONFIRM='-1',OH.MDDT = sysdate where OH.ORDERID=:orderId ";
			Query qA = session.createSQLQuery(sqlA);
			qA.setParameter("orderId", orderId);
			qA.executeUpdate();
			oh.setConfirm("-1");
			
		}
		
	}

}
