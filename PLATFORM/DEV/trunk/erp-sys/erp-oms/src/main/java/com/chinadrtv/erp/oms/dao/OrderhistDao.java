package com.chinadrtv.erp.oms.dao;
import java.util.Date;
import java.util.List;
import com.chinadrtv.erp.model.Orderhist;
import com.chinadrtv.erp.oms.common.DataGridModel;
import com.chinadrtv.erp.oms.dto.OrderhistDto;
import com.chinadrtv.erp.core.dao.GenericDao;

/**
 * agent订单Dao 
 * OrderhistDao
 * 
 * @author haoleitao
 *
 */
public interface OrderhistDao extends GenericDao<Orderhist,java.lang.String>{
	   public List<Orderhist> getAllOrderhist(int state,Date beginDate,Date endDate,String orderid,String mailid,String status,String paytype,String mailtype,String ordertype,String result,String companyid,String cardtype,String cityid,int index, int size);
	   public int getOrderhistCount(int state,Date beginDate,Date endDate,String orderid,String mailid,String status,String paytype,String mailtype,String ordertype,String result,String companyid,String cardtype,String cityid);
	   public List<OrderhistDto> query(OrderhistDto orderhistDto, DataGridModel dataModel);
	   public Integer queryCount(OrderhistDto orderhistDto);
	   public Orderhist getOrderhistById(String orderhistId);
	   public List<OrderhistDto> queryOrderhistList(OrderhistDto orderhist);
	   
	   
	   public List<OrderhistDto> queryForOldData(OrderhistDto orderhistDto, DataGridModel dataModel);
	   public Integer queryCountForOldData(OrderhistDto orderhistDto);
	   public List<OrderhistDto> queryOrderhistListForOldData(OrderhistDto orderhist);
	   
}
