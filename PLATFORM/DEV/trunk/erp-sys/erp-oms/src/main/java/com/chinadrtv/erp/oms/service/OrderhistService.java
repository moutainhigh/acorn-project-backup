package com.chinadrtv.erp.oms.service;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.chinadrtv.erp.model.Orderhist;
import com.chinadrtv.erp.oms.common.DataGridModel;
import com.chinadrtv.erp.oms.dto.OrderhistDto;

/**
 * 
 * @author haoleitao
 * @date 2012-12-28 下午4:36:26
 * 
 */
public interface OrderhistService {
	Orderhist getOrderhistById(String orderhistId);

	List<Orderhist> getAllOrderhist(int state, Date beginDate, Date endDate,
			String orderid, String mailid, String status, String paytype,
			String mailtype, String ordertype, String result, String companyid,
			String cardtype, String cityid, int index, int size);

	int getOrderhistCount(int state, Date beginDate, Date endDate,
			String orderid, String mailid, String status, String paytype,
			String mailtype, String ordertype, String result, String companyid,
			String cardtype, String cityid);

	void saveOrderhist(Orderhist orderhist);

	void addOrderhist(Orderhist orderhist);

	void removeOrderhist(Orderhist orderhist);

	public Map<String, Object> getOrderhistList(OrderhistDto orderhistDto,
			DataGridModel dataModel);

	public int changeOrderStatus(String companyId, Long id, String orderId,
			String status, String userId);

	public HSSFWorkbook getOrderhistsForDownload(OrderhistDto orderhistDto);
	
	public HSSFWorkbook getOrderhistsForDownloadNew(OrderhistDto orderhistDto);

	public HSSFWorkbook upload(InputStream is, String companyId, String userId)
			throws Exception;
	
	public HSSFWorkbook uploadNew(InputStream is, String companyId, String userId)
			throws Exception;

	public Map<String, Object> getOrderhistListForOldData(
			OrderhistDto orderhistDto, DataGridModel dataModel);

	public int changeOrderStatus(String companyId,String orderId,String status,String userId);

	public HSSFWorkbook getOrderhistsForDownloadForOldData(
			OrderhistDto orderhistDto);

	public HSSFWorkbook uploadForOldData(InputStream is, String companyId,
			String userId) throws Exception;

    List<Orderhist> getOrderhistFromGetContact(String getContactId);

    Orderhist getOrderFromMailId(String mailId);
    
    public String getCompanyNameByOrderId(String orderId);
    
       

}
