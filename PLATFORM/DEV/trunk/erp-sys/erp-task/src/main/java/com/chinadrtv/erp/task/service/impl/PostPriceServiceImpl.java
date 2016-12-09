package com.chinadrtv.erp.task.service.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinadrtv.erp.task.core.repository.jpa.BaseRepository;
import com.chinadrtv.erp.task.core.service.BaseServiceImpl;
import com.chinadrtv.erp.task.dao.oms.ShipmentHeaderDao;
import com.chinadrtv.erp.task.entity.LogisticsFeeRuleDetail;
import com.chinadrtv.erp.task.entity.ShipmentHeader;
import com.chinadrtv.erp.task.jobs.postpricecalculate.PostPriceItem;
import com.chinadrtv.erp.task.service.PostPriceService;

@Service
public class PostPriceServiceImpl extends BaseServiceImpl<ShipmentHeader, Long> implements PostPriceService{

	@Autowired
	private ShipmentHeaderDao shipmentHeaderDao;
	
	@Resource
	DataSource wmsDS;

	@Override
	public BaseRepository<ShipmentHeader, Long> getDao() {
		return shipmentHeaderDao;
	}

	@Override
	public List<PostPriceItem> loadPostPriceItems() {
		
//		SELECT SH.ID, SH.ENTITY_ID,SH.ACCOUNT_STATUS_ID,SH.ORDER_REF_HIS_ID,SH.ORDER_REF_REVISION,SH.ORDER_ID,SUM(NVL(SH.PROD_PRICE,0)+NVL(SH.MAIL_PRICE,0)+NVL(SH2.PROD_PRICE,0)+NVL(SH2.MAIL_PRICE,0)+NVL(SH3.PROD_PRICE,0)+NVL(SH3.MAIL_PRICE,0))
//		FROM SHIPMENT_HEADER SH
//		LEFT JOIN SHIPMENT_HEADER SH2 ON ( SH2.ASSOCIATED_ID=SH.ID AND SH2.ID!=SH.ID AND SH2.ACCOUNT_TYPE='2' )
//		LEFT JOIN SHIPMENT_HEADER SH3 ON ( SH3.ASSOCIATED_ID=SH.ID AND SH3.ID!=SH.ID AND SH3.ACCOUNT_TYPE='3' )
//		WHERE
//		ROWNUM<500 AND 
//		SH.ACCOUNT='Y' AND 
//		SH.RECONCIL_FLAG=1 AND 
//		SH.ACCOUNT_TYPE='1' AND 
//		 ( SH.ACCOUNT_STATUS_ID='5' OR SH.ACCOUNT_STATUS_ID='6' ) AND 
//		SH.POST_FEE1 IS NULL AND SH.POST_FEE2 IS NULL AND SH.POST_FEE3 IS NULL AND SH.RECONCIL_DATE>SYSDATE-30
//		GROUP BY SH.ID, SH.ENTITY_ID,SH.ACCOUNT_STATUS_ID,SH.ORDER_REF_HIS_ID,SH.ORDER_REF_REVISION,SH.ORDER_ID
		
		Object[] values = {500};//最近30天的前500条数据
		String sql = "SELECT SH.ID, SH.ENTITY_ID,SH.ACCOUNT_STATUS_ID,SH.ORDER_REF_HIS_ID,SH.ORDER_REF_REVISION,SH.ORDER_ID," +
				"SUM(NVL(SH.PROD_PRICE,0)+NVL(SH.MAIL_PRICE,0)+NVL(SH2.PROD_PRICE,0)+NVL(SH2.MAIL_PRICE,0)+NVL(SH3.PROD_PRICE,0)+NVL(SH3.MAIL_PRICE,0)) " +
				"FROM SHIPMENT_HEADER SH " +
				"LEFT JOIN SHIPMENT_HEADER SH2 ON ( SH2.ASSOCIATED_ID=SH.ID AND SH2.ID!=SH.ID AND SH2.ACCOUNT_TYPE='2' ) " +
				"LEFT JOIN SHIPMENT_HEADER SH3 ON ( SH3.ASSOCIATED_ID=SH.ID AND SH3.ID!=SH.ID AND SH3.ACCOUNT_TYPE='3' ) " +
				"WHERE ROWNUM<=? AND SH.ACCOUNT='Y' AND SH.RECONCIL_FLAG=1 AND SH.ACCOUNT_TYPE='1' AND ( SH.ACCOUNT_STATUS_ID='5' OR SH.ACCOUNT_STATUS_ID='6' ) AND SH.POST_FEE1 IS NULL AND SH.POST_FEE2 IS NULL AND SH.POST_FEE3 IS NULL AND SH.RECONCIL_DATE>SYSDATE-30 " +
				"GROUP BY SH.ID, SH.ENTITY_ID,SH.ACCOUNT_STATUS_ID,SH.ORDER_REF_HIS_ID,SH.ORDER_REF_REVISION,SH.ORDER_ID";
		
		List<Object[]> objs = shipmentHeaderDao.nativeQuery(sql, values, null, null);
		
		List<PostPriceItem> items = new ArrayList<PostPriceItem>();
		for(Object[] obj : objs){
			PostPriceItem item = new PostPriceItem();
			if(obj[0]!=null) item.setShipmentHeadId(((BigDecimal) obj[0]).longValue());
			if(obj[1]!=null) item.setEntityId(Long.valueOf(obj[1].toString()));
			if(obj[2]!=null) item.setAccountStatusId(obj[2].toString());
			if(obj[3]!=null) item.setOrderRefHisId(((BigDecimal) obj[3]).longValue());
			if(obj[4]!=null) item.setOrderRefRevision(((BigDecimal) obj[4]).intValue());
			if(obj[5]!=null) item.setOrderId(obj[5].toString());
			if(obj[6]!=null) item.setTotlePrice(((BigDecimal) obj[6]).doubleValue());
			items.add(item);
		}
		items = this.setWeights(items);
		return items;
	}
	
	private List<PostPriceItem> setWeights(List<PostPriceItem> items){
		
//		SELECT sc.TOTAL_WEIGHT
//		FROM SHIPMENT_HEADER sh(NOLOCK)
//		JOIN SHIPMENT_HEADER2 sh2(NOLOCK) ON sh.SHIPMENT_ID = sh2.WMS_SHIPMENT_ID
//			JOIN(
//					SELECT INTERNAL_SHIPMENT_NUM, CASE WHEN SUM(ISNULL(WEIGHT, 0)) > 1000 THEN 0 ELSE SUM(ISNULL(WEIGHT, 0)) END AS TOTAL_WEIGHT
//					FROM SHIPPING_CONTAINER SC(NOLOCK)
//					WHERE PARENT IS NULL
//					GROUP BY INTERNAL_SHIPMENT_NUM
//				) sc ON  sh.INTERNAL_SHIPMENT_NUM = sc.INTERNAL_SHIPMENT_NUM 
//		WHERE sh2.SCM_SHIPMENT_ID = N'902025280' AND sh2.SCM__REVISION = N'1'
		
		if(items!=null && items.size()>0){
			String sql = 
			"SELECT sc.TOTAL_WEIGHT " +
			"FROM SHIPMENT_HEADER sh(NOLOCK) " +
			"JOIN SHIPMENT_HEADER2 sh2(NOLOCK) ON sh.SHIPMENT_ID = sh2.WMS_SHIPMENT_ID " +
				"JOIN( " +
						"SELECT INTERNAL_SHIPMENT_NUM, CASE WHEN SUM(ISNULL(WEIGHT, 0)) > 1000 THEN 0 ELSE SUM(ISNULL(WEIGHT, 0)) END AS TOTAL_WEIGHT " +
						"FROM SHIPPING_CONTAINER SC(NOLOCK) " +
						"WHERE PARENT IS NULL " +
						"GROUP BY INTERNAL_SHIPMENT_NUM " +
					") sc ON  sh.INTERNAL_SHIPMENT_NUM = sc.INTERNAL_SHIPMENT_NUM  " +
			"WHERE sh2.SCM_SHIPMENT_ID = ? AND sh2.SCM__REVISION = ? ";
			
			Connection con = null;
			try {
				con = wmsDS.getConnection();
				PreparedStatement stmt = null;
				for(PostPriceItem item : items){
					try {
						stmt = con.prepareStatement(sql);// 创建SQL命令对象
						stmt.setString(1, item.getOrderId());
						stmt.setInt(2, item.getOrderRefRevision());
						ResultSet rs = stmt.executeQuery();// 返回SQL语句查询结果集(集合)
						while (rs.next()) {
							item.setWeight(rs.getDouble(1));
						}
						//如果没找到，按照0计算
						if(item.getWeight()==null){
							item.setWeight(0d);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}finally{
						try {
							if(stmt!=null && !stmt.isClosed()){
								stmt.close();
								stmt=null;
							}
						} catch (SQLException e) {
							stmt=null;
							e.printStackTrace();
						}
					}	
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				try {
					if(con!=null && !con.isClosed()){
						con.close();
						con=null;
					}
				} catch (SQLException e) {
					con=null;
					e.printStackTrace();
				}
			}
		}
		return items;
	}

	@Transactional(readOnly=false)
	@Override
	public void updatePostPrice(List<? extends PostPriceItem> items) {
		
		for(PostPriceItem item : items){
			//处理订单
			Object[] values = {  item.getShipmentHeadId() };
			String sql = "UPDATE SHIPMENT_HEADER SET WEIGHT=" + 
					(item.getWeight()!=null?item.getWeight().toString():"null") + ",POST_FEE1=" + 
					(item.getPostFee1()!=null?item.getPostFee1():"null") + ",POST_FEE2=" + 
					(item.getPostFee2()!=null?item.getPostFee2():"null") + ",POST_FEE3=" + 
					(item.getPostFee3()!=null?item.getPostFee3():"null") + " WHERE ID=?";
			shipmentHeaderDao.nativeUpdate(sql, values);
		}

	}

	@Override
	public LogisticsFeeRuleDetail getLogisticsFeeRuleDetail(PostPriceItem item) {

//		SELECT T.* FROM
//		(
//					(
//						SELECT LFRD.* FROM LOGISTICS_FEE_RULE_DETAIL LFRD 
//						LEFT JOIN LOGISTICS_FEE_RULE LFR ON LFR.ID = LFRD.LOGISTICS_FEE_RULE_ID 
//						WHERE 
//						LFR.CONTRACT_ID=262 AND --承运商
//						LFRD.RULE_TYPE='PRICE' AND	--计算规则：按单
//						(LFRD.AMOUNT_FLOOR IS NULL OR 500>=LFRD.AMOUNT_FLOOR) AND 	--计算规则：金额下限
//						(LFRD.AMOUNT_CEIL IS NULL OR 500<LFRD.AMOUNT_CEIL) 	--计算规则：金额上限
//					)
//					UNION
//					(
//						SELECT LFRD.* FROM LOGISTICS_FEE_RULE_DETAIL LFRD 
//						LEFT JOIN LOGISTICS_FEE_RULE LFR ON LFR.ID = LFRD.LOGISTICS_FEE_RULE_ID	
//						LEFT JOIN IAGENT.ORDERHIST OH ON OH.ORDERID = '517453'	--连接订单
//						LEFT JOIN IAGENT.ADDRESS_EXT AE ON AE.ADDRESSID=OH.ADDRESSID	--连接地址
//						WHERE 
//						LFR.CONTRACT_ID=262 AND --承运商
//						LFRD.RULE_TYPE='WEIGHT' AND	--计算规则：按重量
//						(LFRD.DESTINATION_PROVINCE IS NULL OR AE.PROVINCEID=LFRD.DESTINATION_PROVINCE) AND	--匹配省
//						(LFRD.DESTINATION_CITY IS NULL OR AE.CITYID=LFRD.DESTINATION_CITY) AND	--匹配市
//						(LFRD.DESTINATION_COUNTY IS NULL OR AE.COUNTYID=LFRD.DESTINATION_COUNTY) AND	--匹配县
//						(LFRD.WEIGHT_FLOOR IS NULL OR 25>=LFRD.WEIGHT_FLOOR) AND 	--重量下限
//						(LFRD.WEIGHT_CEIL IS NULL OR 25<LFRD.WEIGHT_CEIL)	--重量上限
//					)
//		) T
//		WHERE ROWNUM<2	--取一条数据
//		ORDER BY T.DESTINATION_COUNTY,T.DESTINATION_CITY,T.DESTINATION_PROVINCE	--排序将省市县较完整的排到最前面

		Double weigth = item.getWeight();
		if(weigth==null){
			weigth = 0D;
		}
//		String entityId = null;
//		if(item.getOrderRefHisId()!=null){
//			entityId = item.getOrderRefHisId().toString();
//		}
		Object[] values = { item.getEntityId(), item.getTotlePrice(), item.getTotlePrice(), 
				item.getOrderId().toString(), item.getEntityId(), weigth,weigth };
		
		String sql = 
				"SELECT T.* FROM " +
				"( " +
					"( " +
						"SELECT LFRD.* FROM LOGISTICS_FEE_RULE_DETAIL LFRD " +
						"LEFT JOIN LOGISTICS_FEE_RULE LFR ON LFR.ID = LFRD.LOGISTICS_FEE_RULE_ID " +
						"WHERE " +
						"LFR.CONTRACT_ID=? AND " +
						"LFRD.RULE_TYPE='PRICE' AND " +
						"(LFRD.AMOUNT_FLOOR IS NULL OR ?>=LFRD.AMOUNT_FLOOR) AND " +
						"(LFRD.AMOUNT_CEIL IS NULL OR ?<LFRD.AMOUNT_CEIL) " +
					") " +
					"UNION " +
					"( " +
						"SELECT LFRD.* FROM LOGISTICS_FEE_RULE_DETAIL LFRD " +
						"LEFT JOIN LOGISTICS_FEE_RULE LFR ON LFR.ID = LFRD.LOGISTICS_FEE_RULE_ID " +
						"LEFT JOIN	IAGENT.ORDERHIST OH ON OH.ORDERID = ? " +
						"LEFT JOIN IAGENT.ADDRESS_EXT AE ON AE.ADDRESSID=OH.ADDRESSID " +
						"WHERE " +
						"LFR.CONTRACT_ID=? AND " +
						"LFRD.RULE_TYPE='WEIGHT' AND " +
						"(LFRD.DESTINATION_PROVINCE IS NULL OR AE.PROVINCEID=LFRD.DESTINATION_PROVINCE) AND " +
						"(LFRD.DESTINATION_CITY IS NULL OR AE.CITYID=LFRD.DESTINATION_CITY) AND " +
						"(LFRD.DESTINATION_COUNTY IS NULL OR AE.COUNTYID=LFRD.DESTINATION_COUNTY) AND " +
						"(LFRD.WEIGHT_FLOOR IS NULL OR ?>=LFRD.WEIGHT_FLOOR) AND " +
						"(LFRD.WEIGHT_CEIL IS NULL OR ?<LFRD.WEIGHT_CEIL) " +
					") " +
				") T " +
				"WHERE ROWNUM<2 " +
				"ORDER BY T.DESTINATION_COUNTY,T.DESTINATION_CITY,T.DESTINATION_PROVINCE ";
		
		PageRequest pageable = new PageRequest(0, 1) ;
		Page<LogisticsFeeRuleDetail> page = shipmentHeaderDao.nativeQuery(LogisticsFeeRuleDetail.class, sql, values, pageable);
		if(page.getContent().size()>0){
			return page.getContent().get(0);
		}else{
			return null;
		}
	}


}
