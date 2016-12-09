/**
 * 
 */
package com.chinadrtv.erp.sales.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.model.Phone;
import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.model.inventory.ProductType;
import com.chinadrtv.erp.sales.dto.OrderDto.OrderDetailDto;
import com.chinadrtv.erp.sales.dto.OrderInfoDto;
import com.chinadrtv.erp.sales.dto.ReceiptDto;

/**
 * @author dengqianyong
 *
 */
public interface OrderInfoService {
	
	
	String DET_NONE = "none";
	String DET_CREATE = "create";
	String DET_MODIFY = "modify";
	String DET_REMOVE = "remove";
	
	
	/**
	 * 
	 * @param addressId
	 * @return
	 */
	ReceiptDto getReceipt(String getContactId, String addressId);
	
	/**
	 * 得到联系人地址列表
	 * 
	 * @param contactId
	 * @return
	 */
	List<ReceiptDto> getReceiptsByContactId(String contactId);
	
	/**
	 * 得到上门自提点的联系信息
	 * 
	 * @return
	 */
	List<ReceiptDto> getPickUpSelfReceipts();
	
	/**
	 * 得到联系人姓名
	 * 
	 * @param contactId
	 * @return
	 */
	String getContactNameByContactId(String contactId);

	/**
	 * 得到产品类型
	 * 
	 * @param productId
	 * @param type
	 * @return
	 */
	ProductType getProductType(String productId, String type);

	/**
	 * 
	 * @param orderDetailList
	 * @return
	 */
    List<ProductType> getProductTypes(List<OrderDetail> orderDetailList);

    /**
     * 比较两个产品明细，并返回比较结果
     * 
     * @param orderDetails
     * @param cartDetails
     * @return
     */
	Map<OrderDetailDto, String> compare(Set<OrderDetail> orderDetails,
			Set<OrderDetail> cartDetails);

	/**
	 * 编辑订单明细, 并返回修改后的明细列表
	 * 
	 * @param orderDetails
	 * @param cartDetails
	 * @return
	 */
	Set<OrderDetail> editOrderDetails(Set<OrderDetail> orderDetails,
			Set<OrderDetail> cartDetails);
	
	/**
	 * 验证提交的修改请求
	 * 
	 * @param orderInfo
	 */
	void validateCommit(OrderInfoDto orderInfo) throws ServiceException;
	
}
