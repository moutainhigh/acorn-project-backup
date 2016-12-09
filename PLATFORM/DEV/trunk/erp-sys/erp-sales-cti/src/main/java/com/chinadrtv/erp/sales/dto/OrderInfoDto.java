/**
 * 
 */
package com.chinadrtv.erp.sales.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dengqianyong
 *
 */
public class OrderInfoDto implements Serializable {
	
	private static final long serialVersionUID = 7812366257034425857L;
	private String orderId;
	private String contactId;
	private Cart cart;
	private String batchId;
	private Order order;
	private Address address;
	private List<Phone> phones = new ArrayList<Phone>();
	private Card card;
	private String remark;
	private List<String> relateds = new ArrayList<String>(); // 相关联的订单编号列表

	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getContactId() {
		return contactId;
	}
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public List<Phone> getPhones() {
		return phones;
	}
	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}
	public Card getCard() {
		return card;
	}
	public void setCard(Card card) {
		this.card = card;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<String> getRelateds() {
		return relateds;
	}
	public void setRelateds(List<String> relateds) {
		this.relateds = relateds;
	}


	public static class Cart implements Serializable {
		private static final long serialVersionUID = 7556321736646878002L;
		private Long cartId;
		public Long getCartId() {
			return cartId;
		}
		public void setCartId(Long cartId) {
			this.cartId = cartId;
		}
	}
	
	public static class Order implements Serializable {
		private static final long serialVersionUID = 4042881106329225968L;
		private String payType;
		private String invoiceTitle;
		private String note;
		private String jifen;
		private BigDecimal mailPrice;
		private BigDecimal productTotalPrice;
		private Integer productTotalNum;
		private BigDecimal totalPrice;
		private String ems;
		private String cardId;
		private String cardType;
		private Integer amortisation;
		public String getPayType() {
			return payType;
		}
		public void setPayType(String payType) {
			this.payType = payType;
		}
		public String getInvoiceTitle() {
			return invoiceTitle;
		}
		public void setInvoiceTitle(String invoiceTitle) {
			this.invoiceTitle = invoiceTitle;
		}
		public String getNote() {
			return note;
		}
		public void setNote(String note) {
			this.note = note;
		}
		public String getJifen() {
			return jifen;
		}
		public void setJifen(String jifen) {
			this.jifen = jifen;
		}
		public BigDecimal getMailPrice() {
			return mailPrice;
		}
		public void setMailPrice(BigDecimal mailPrice) {
			this.mailPrice = mailPrice;
		}
		public BigDecimal getProductTotalPrice() {
			return productTotalPrice;
		}
		public void setProductTotalPrice(BigDecimal productTotalPrice) {
			this.productTotalPrice = productTotalPrice;
		}
		public Integer getProductTotalNum() {
			return productTotalNum;
		}
		public void setProductTotalNum(Integer productTotalNum) {
			this.productTotalNum = productTotalNum;
		}
		public BigDecimal getTotalPrice() {
			return totalPrice;
		}
		public void setTotalPrice(BigDecimal totalPrice) {
			this.totalPrice = totalPrice;
		}
		public String getEms() {
			return ems;
		}
		public void setEms(String ems) {
			this.ems = ems;
		}
		public String getCardId() {
			return cardId;
		}
		public void setCardId(String cardId) {
			this.cardId = cardId;
		}
		public String getCardType() {
			return cardType;
		}
		public void setCardType(String cardType) {
			this.cardType = cardType;
		}
		public Integer getAmortisation() {
			return amortisation;
		}
		public void setAmortisation(Integer amortisation) {
			this.amortisation = amortisation;
		}
	}
	
	public static class Address implements Serializable {
		private static final long serialVersionUID = 1112619266305110822L;
		private String addressId;
		private String addressDesc;
		private String provinceId;
		private String provinceName;
		private String cityId;
		private String cityName;
		private String countyId;
		private String countyName;
		private String areaId;
		private String areaName;
		private String zipcode;
		public String getAddressId() {
			return addressId;
		}
		public void setAddressId(String addressId) {
			this.addressId = addressId;
		}
		public String getAddressDesc() {
			return addressDesc;
		}
		public void setAddressDesc(String addressDesc) {
			this.addressDesc = addressDesc;
		}
		public String getProvinceId() {
			return provinceId;
		}
		public void setProvinceId(String provinceId) {
			this.provinceId = provinceId;
		}
		public String getProvinceName() {
			return provinceName;
		}
		public void setProvinceName(String provinceName) {
			this.provinceName = provinceName;
		}
		public String getCityId() {
			return cityId;
		}
		public void setCityId(String cityId) {
			this.cityId = cityId;
		}
		public String getCityName() {
			return cityName;
		}
		public void setCityName(String cityName) {
			this.cityName = cityName;
		}
		public String getCountyId() {
			return countyId;
		}
		public void setCountyId(String countyId) {
			this.countyId = countyId;
		}
		public String getCountyName() {
			return countyName;
		}
		public void setCountyName(String countyName) {
			this.countyName = countyName;
		}
		public String getAreaId() {
			return areaId;
		}
		public void setAreaId(String areaId) {
			this.areaId = areaId;
		}
		public String getAreaName() {
			return areaName;
		}
		public void setAreaName(String areaName) {
			this.areaName = areaName;
		}
		public String getZipcode() {
			return zipcode;
		}
		public void setZipcode(String zipcode) {
			this.zipcode = zipcode;
		}
	}
	
	public static class Phone implements Serializable {
		private static final long serialVersionUID = 794470512194993119L;
		private String phoneType;
		private String phn1;
		private String phn2;
		private String phn3;
		private String prmphn;
		private String phoneNumber;
		public String getPhoneType() {
			return phoneType;
		}
		public void setPhoneType(String phoneType) {
			this.phoneType = phoneType;
		}
		public String getPhn1() {
			return phn1;
		}
		public void setPhn1(String phn1) {
			this.phn1 = phn1;
		}
		public String getPhn2() {
			return phn2;
		}
		public void setPhn2(String phn2) {
			this.phn2 = phn2;
		}
		public String getPhn3() {
			return phn3;
		}
		public void setPhn3(String phn3) {
			this.phn3 = phn3;
		}
		public String getPrmphn() {
			return prmphn;
		}
		public void setPrmphn(String prmphn) {
			this.prmphn = prmphn;
		}
		public String getPhoneNumber() {
			return phoneNumber;
		}
		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
	}

	public static class Card implements Serializable {
		private static final long serialVersionUID = -4352786088147521017L;
		private Long cardId;
		private String contactId;
		private String type;
		private String cardNumber;
		private String validDate;
		private String extraCode;
		public Long getCardId() {
			return cardId;
		}
		public void setCardId(Long cardId) {
			this.cardId = cardId;
		}
		public String getContactId() {
			return contactId;
		}
		public void setContactId(String contactId) {
			this.contactId = contactId;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getCardNumber() {
			return cardNumber;
		}
		public void setCardNumber(String cardNumber) {
			this.cardNumber = cardNumber;
		}
		public String getValidDate() {
			return validDate;
		}
		public void setValidDate(String validDate) {
			this.validDate = validDate;
		}
		public String getExtraCode() {
			return extraCode;
		}
		public void setExtraCode(String extraCode) {
			this.extraCode = extraCode;
		}
	}
}
