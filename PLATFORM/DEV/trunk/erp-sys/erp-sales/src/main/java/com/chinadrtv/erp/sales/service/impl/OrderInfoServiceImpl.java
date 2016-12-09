package com.chinadrtv.erp.sales.service.impl;

import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.model.Address;
import com.chinadrtv.erp.model.AddressExt;
import com.chinadrtv.erp.model.Contact;
import com.chinadrtv.erp.model.Phone;
import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.model.inventory.ProductType;
import com.chinadrtv.erp.sales.dao.ProductTypeDao;
import com.chinadrtv.erp.sales.dto.OrderDto;
import com.chinadrtv.erp.sales.dto.OrderInfoDto;
import com.chinadrtv.erp.sales.dto.ReceiptDto;
import com.chinadrtv.erp.sales.service.OrderInfoService;
import com.chinadrtv.erp.tc.core.dao.ContactDao;
import com.chinadrtv.erp.uc.dao.AddressDao;
import com.chinadrtv.erp.uc.dao.AddressExtDao;
import com.chinadrtv.erp.uc.service.PhoneService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderInfoServiceImpl.class);

    private static final String SELF_NAME = "上门自提点";
	
	@Autowired
	private ContactDao contactDao;
	
	@Autowired
	private AddressDao addressDao;
	
	@Autowired
	private AddressExtDao addressExtDao;
	
	@Autowired
	private ProductTypeDao productTypeDao;

	@Autowired
	private PhoneService phoneService;

	@Override
	public ReceiptDto getReceipt(String getContactId, String addressId) {
		AddressExt ae = addressExtDao.get(addressId);
		Address address = addressDao.get(addressId);
		String name = getContactNameByContactId(getContactId);
		List<Phone> phones = phoneService.getPhonesByContactId(getContactId);
		return createReceipt(name, ae, address, phones);
	}

	@Override
	public List<ReceiptDto> getReceiptsByContactId(String contactId) {
		List<AddressExt> addressExts = addressExtDao.queryAllAddressByContact(contactId);
		List<ReceiptDto> receipts = new ArrayList<ReceiptDto>();
		String name = getContactNameByContactId(contactId);
		List<Phone> phones = phoneService.getPhonesByContactId(contactId);
		for(AddressExt ae : addressExts){
			Address address = addressDao.get(ae.getAddressId());
			receipts.add(createReceipt(name, ae, address, phones));
		}
		return receipts;
	}
	
	@Override
	public List<ReceiptDto> getPickUpSelfReceipts() {
		List<Contact> contacts = contactDao.findByContactName(SELF_NAME);
		if (CollectionUtils.isEmpty(contacts)) return Collections.emptyList();
		
		List<ReceiptDto> result = new ArrayList<ReceiptDto>();
		for (Contact c : contacts) {
			result.addAll(getReceiptsByContactId(c.getContactid()));
		}
		return result;
	}
	
	private ReceiptDto createReceipt(String contactName, AddressExt ae, Address address, List<Phone> phones) {
		ReceiptDto receipt = new ReceiptDto();
		receipt.setContactName(contactName);
		receipt.setContactId(ae.getContactId());
        try {
            receipt.setProvinceId(ae.getProvince() != null ? ae.getProvince().getProvinceid() : "");
            receipt.setProvinceName(ae.getProvince() != null ? ae.getProvince().getChinese() : "");
            receipt.setCityId(ae.getCity() != null ? ae.getCity().getCityid().toString() : "");
            receipt.setCityName(ae.getCity() != null ? ae.getCity().getCityname() : "");
            receipt.setCountyId(ae.getCounty() != null ? ae.getCounty().getCountyid().toString() : "");
            receipt.setCountyName(ae.getCounty() != null ? ae.getCounty().getCountyname() : "");
            receipt.setAreaId(ae.getArea() != null ? ae.getArea().getAreaid().toString() : "");
            receipt.setAreaName(ae.getArea() != null ? ae.getArea().getAreaname() : "");
        } catch (Exception e) {
            logger.error("四级地址配置表中没有对应的数据。");
        }
        receipt.setAddressId(ae.getAddressId());
		receipt.setAddressDesc(ae.getAddressDesc());
		receipt.setZipcode(address.getZip());
		receipt.setIsdefault(address.getIsdefault());
		receipt.setAuditState(ae.getAuditState());
		receipt.setPhones(phones);
        return receipt;
	}

	@Override
	public String getContactNameByContactId(String contactId) {
		return contactDao.findByContactId(contactId).getName();
	}
	
	@Override
	public ProductType getProductType(String productId, String type) {
		return productTypeDao.getProductType(productId, type);
	}

	@Override
    public List<ProductType> getProductTypes(List<OrderDetail> orderDetailList) {
        return productTypeDao.getProductTypes(orderDetailList);
    }
	

	/*
	 * 处理比较页面中的产品明细, 'modify': 修改, 'create': 新增, 'remove': 删除, 'none': 未改变
	 */
	@Override
	public Map<OrderDto.OrderDetailDto, String> compare(Set<OrderDetail> orderDetails, Set<OrderDetail> cartDetails) {
		Map<OrderDto.OrderDetailDto, String> editMap = new LinkedHashMap<OrderDto.OrderDetailDto, String>();
		
		Set<OrderDetail> copyCartDetails = new HashSet<OrderDetail>(cartDetails.size());
		for (OrderDetail cartDet : cartDetails) {
			OrderDetail copyCartDetail = new OrderDetail();
			BeanUtils.copyProperties(cartDet, copyCartDetail);
			copyCartDetails.add(copyCartDetail);
		}
		
		outter: for (OrderDetail orderdet : orderDetails) {
			for (Iterator<OrderDetail> iter = copyCartDetails.iterator(); iter.hasNext();) {
				OrderDetail cartdet = iter.next();
				if (isSameProduct(orderdet, cartdet)) { // 是相同的产品
					OrderDetail detail = new OrderDetail();
					BeanUtils.copyProperties(orderdet, detail);
					OrderDto.OrderDetailDto detDto = new OrderDto.OrderDetailDto(detail);
					ProductType pt = getProductType(detDto.getProdid(), detDto.getProducttype());
					if (pt != null) detDto.setProductTypeName(pt.getDsc());
					if (!isSameDetail(orderdet, cartdet)) {
						detDto.setSprice(cartdet.getSprice());
						detDto.setSpnum(cartdet.getSpnum());
						detDto.setUprice(cartdet.getUprice());
						detDto.setUpnum(cartdet.getUpnum());
						detDto.setJifen(cartdet.getJifen());
						detDto.setSoldwith(cartdet.getSoldwith());
						detDto.setPayment(cartdet.getPayment());
						
						editMap.put(detDto, DET_MODIFY);
					} else {
						editMap.put(detDto, DET_NONE);
					}
					
					iter.remove(); // 将相同的产品从新的列表中移除
					continue outter;
				}
			}
			// old detail没在new detail列表出现，说明是删除的
			OrderDetail detail = new OrderDetail();
			BeanUtils.copyProperties(orderdet, detail);
			OrderDto.OrderDetailDto detDto = new OrderDto.OrderDetailDto(detail);
			ProductType pt = getProductType(detDto.getProdid(), detDto.getProducttype());
			if (pt != null) detDto.setProductTypeName(pt.getDsc());
			editMap.put(detDto, DET_REMOVE);
		}
		// 剩下都是新增的
		for (OrderDetail detail : copyCartDetails) {
			OrderDto.OrderDetailDto detDto = new OrderDto.OrderDetailDto(detail);
			ProductType pt = getProductType(detDto.getProdid(), detDto.getProducttype());
			if (pt != null) detDto.setProductTypeName(pt.getDsc());
			editMap.put(detDto, DET_CREATE);
		}
		return editMap;
	}
	

	/*
	 * 修改产品明细
	 */
	@Override
	public Set<OrderDetail> editOrderDetails(Set<OrderDetail> orderDetails, Set<OrderDetail> cartDetails) {
		Map<OrderDto.OrderDetailDto, String> compareResults = compare(orderDetails, cartDetails);
		boolean isSameOrderDetails = true;
		if (!CollectionUtils.isEmpty(compareResults)) {
			for (String val : compareResults.values()) {
				if (!StringUtils.equals(DET_NONE, val)) {
					isSameOrderDetails = false;
					break;
				}
			}
		}
		
		if (isSameOrderDetails) {
			return Collections.emptySet();
		}
		
		Set<OrderDetail> editDetails = new HashSet<OrderDetail>();
		outter: for (OrderDetail cartdet : cartDetails) {
			for (OrderDetail orderdet : orderDetails) {
				if (isSameProduct(orderdet, cartdet)) {
					OrderDetail editdet = new OrderDetail();
					BeanUtils.copyProperties(orderdet, editdet);
					editdet.setSprice(cartdet.getSprice());
					editdet.setSpnum(cartdet.getSpnum());
					editdet.setUprice(cartdet.getUprice());
					editdet.setUpnum(cartdet.getUpnum());
					editdet.setJifen(cartdet.getJifen());
					editdet.setSoldwith(cartdet.getSoldwith());
					editdet.setPayment(cartdet.getPayment());
					editDetails.add(editdet);
					continue outter;
				}
			}
			
			// 没找到相同的产品，新增产品
			editDetails.add(cartdet);
		}
		return editDetails;
	}
	
	@Override
	public void validateCommit(OrderInfoDto orderInfo) throws ServiceException {
		if (StringUtils.isEmpty(orderInfo.getOrderId())) {
			throw new ServiceException("订单号不能为空");
		}
		if (StringUtils.isEmpty(orderInfo.getContactId())) {
			throw new ServiceException("联系人不能为空");
		}
		if (orderInfo.getOrder().getProductTotalNum() == null 
				|| orderInfo.getOrder().getProductTotalNum().intValue() == 0) {
			throw new ServiceException("订单商品数量不能少于1件");
		}
		if (orderInfo.getOrder().getMailPrice() == null 
				|| orderInfo.getOrder().getMailPrice().intValue() < 0) {
			throw new ServiceException("邮费不能为负数");
		}
		
		// 信用卡
		if (StringUtils.equals(orderInfo.getOrder().getPayType(), "2")) {
			if (orderInfo.getCard() == null
					|| (orderInfo.getCard().getCardId() == null
							&& orderInfo.getCard().getCardNumber() == null && orderInfo
							.getCard().getType() == null)) {
				throw new ServiceException("该订单必须选择或者添加一张信用卡");
			}
		}
		
		// 验证地址
		
		// 验证电话
	}

	private boolean isSameDetail(OrderDetail orderdet, OrderDetail cartdet) {
		return orderdet.getSprice() != null && orderdet.getSprice().compareTo(cartdet.getSprice()) == 0
				&& orderdet.getSpnum() != null && orderdet.getSpnum().equals(cartdet.getSpnum())
				&& orderdet.getUprice() != null && orderdet.getUprice().compareTo(cartdet.getUprice()) == 0
				&& orderdet.getUpnum() != null && orderdet.getUpnum().equals(cartdet.getUpnum())
				&& orderdet.getJifen() != null && orderdet.getJifen().equals(cartdet.getJifen())
				&& orderdet.getSoldwith() != null && orderdet.getSoldwith().equals(cartdet.getSoldwith());
	}

	/*
	 * 根据prodid和producttype比较两个明细单是否相同
	 */
	private boolean isSameProduct(OrderDetail source, OrderDetail target) {
		return StringUtils.equals(source.getProdid(), target.getProdid())
				&& StringUtils.equals(source.getProducttype(), target.getProducttype())
				&& StringUtils.equals(source.getSoldwith(), target.getSoldwith());
	}
	
}
