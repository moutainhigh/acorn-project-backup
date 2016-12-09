/**
 * 
 */
package com.chinadrtv.erp.sales.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.chinadrtv.erp.ic.model.CompanyDeliverySpan;
import com.chinadrtv.erp.model.AddressExt;
import com.chinadrtv.erp.model.Contact;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.model.agent.Product;

/**
 * @author dengqianyong
 *
 */
public class OrderDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6501736837215804754L;

	private Order order;
	
	private String contactName;
	
	private List<OrderDetailDto> orderDetails;
	
	private String orderStatusName;
	
	private String customizeStatusName;
	
	private String orderTypeName;
	
	private CompanyDeliverySpan delivery;
	
	public OrderDto(Order order, List<OrderDetailDto> orderDetails) {
		this.order = order;
		this.orderDetails = orderDetails;
	}
	
	public OrderDto(Order order, Set<OrderDetail> orderdets) {
		List<OrderDetailDto> orderDetails = new ArrayList<OrderDetailDto>(orderdets.size());
		for (OrderDetail det : orderdets) {
			orderDetails.add(new OrderDetailDto(det));
		}
		this.order = order;
		this.orderDetails = orderDetails;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public Long getId() {
		return order.getId();
	}

	public void setId(Long id) {
		order.setId(id);
	}

	public String getOrderid() {
		return order.getOrderid();
	}

	public void setOrderid(String orderid) {
		order.setOrderid(orderid);
	}

	public String getMailid() {
		return order.getMailid();
	}

	public void setMailid(String mailid) {
		order.setMailid(mailid);
	}

	public String getEntityid() {
		return order.getEntityid();
	}

	public void setEntityid(String entityid) {
		order.setEntityid(entityid);
	}

	public Integer getSpellid() {
		return order.getSpellid();
	}

	public void setSpellid(Integer spellid) {
		order.setSpellid(spellid);
	}

	public String getProvinceid() {
		return order.getProvinceid();
	}

	public void setProvinceid(String provinceid) {
		order.setProvinceid(provinceid);
	}

	public String getCityid() {
		return order.getCityid();
	}

	public void setCityid(String cityid) {
		order.setCityid(cityid);
	}

	public String getContactid() {
		return order.getContactid();
	}

	public void setContactid(String contactid) {
		order.setContactid(contactid);
	}

	public String getPaycontactid() {
		return order.getPaycontactid();
	}

	public void setPaycontactid(String paycontactid) {
		order.setPaycontactid(paycontactid);
	}

	public String getGetcontactid() {
		return order.getGetcontactid();
	}

	public void setGetcontactid(String getcontactid) {
		order.setGetcontactid(getcontactid);
	}

	public String getCrusr() {
		return order.getCrusr();
	}

	public void setCrusr(String crusr) {
		order.setCrusr(crusr);
	}

	public String getMdusr() {
		return order.getMdusr();
	}

	public void setMdusr(String mdusr) {
		order.setMdusr(mdusr);
	}

	public Date getMddt() {
		return order.getMddt();
	}

	public void setMddt(Date mddt) {
		order.setMddt(mddt);
	}

	public String getCbcrusr() {
		return order.getCbcrusr();
	}

	public void setCbcrusr(String cbcrusr) {
		order.setCbcrusr(cbcrusr);
	}

	public String getParcelnm() {
		return order.getParcelnm();
	}

	public void setParcelnm(String parcelnm) {
		order.setParcelnm(parcelnm);
	}

	public String getStatus() {
		return order.getStatus();
	}

	public void setStatus(String status) {
		order.setStatus(status);
	}

	public String getAccount() {
		return order.getAccount();
	}

	public void setAccount(String account) {
		order.setAccount(account);
	}

	public String getResult() {
		return order.getResult();
	}

	public void setResult(String result) {
		order.setResult(result);
	}

	public String getOrdertype() {
		return order.getOrdertype();
	}

	public void setOrdertype(String ordertype) {
		order.setOrdertype(ordertype);
	}

	public String getMailtype() {
		return order.getMailtype();
	}

	public void setMailtype(String mailtype) {
		order.setMailtype(mailtype);
	}

	public String getPaytype() {
		return order.getPaytype();
	}

	public void setPaytype(String paytype) {
		order.setPaytype(paytype);
	}

	public String getUrgent() {
		return order.getUrgent();
	}

	public void setUrgent(String urgent) {
		order.setUrgent(urgent);
	}

	public String toString() {
		return order.toString();
	}

	public String getConfirm() {
		return order.getConfirm();
	}

	public void setConfirm(String confirm) {
		order.setConfirm(confirm);
	}

	public Date getCrdt() {
		return order.getCrdt();
	}

	public void setCrdt(Date crdt) {
		order.setCrdt(crdt);
	}

	public Date getSenddt() {
		return order.getSenddt();
	}

	public void setSenddt(Date senddt) {
		order.setSenddt(senddt);
	}

	public Date getFbdt() {
		return order.getFbdt();
	}

	public void setFbdt(Date fbdt) {
		order.setFbdt(fbdt);
	}

	public Date getOutdt() {
		return order.getOutdt();
	}

	public void setOutdt(Date outdt) {
		order.setOutdt(outdt);
	}

	public Date getAccdt() {
		return order.getAccdt();
	}

	public void setAccdt(Date accdt) {
		order.setAccdt(accdt);
	}

	public BigDecimal getTotalprice() {
		return order.getTotalprice();
	}

	public void setTotalprice(BigDecimal totalprice) {
		order.setTotalprice(totalprice);
	}

	public BigDecimal getMailprice() {
		return order.getMailprice();
	}

	public void setMailprice(BigDecimal mailprice) {
		order.setMailprice(mailprice);
	}

	public BigDecimal getProdprice() {
		return order.getProdprice();
	}

	public void setProdprice(BigDecimal prodprice) {
		order.setProdprice(prodprice);
	}

	public BigDecimal getNowmoney() {
		return order.getNowmoney();
	}

	public void setNowmoney(BigDecimal nowmoney) {
		order.setNowmoney(nowmoney);
	}

	public BigDecimal getPostfee() {
		return order.getPostfee();
	}

	public void setPostfee(BigDecimal postfee) {
		order.setPostfee(postfee);
	}

	public BigDecimal getClearfee() {
		return order.getClearfee();
	}

	public void setClearfee(BigDecimal clearfee) {
		order.setClearfee(clearfee);
	}

	public String getBill() {
		return order.getBill();
	}

	public void setBill(String bill) {
		order.setBill(bill);
	}

	public String getNote() {
		return order.getNote();
	}

	public void setNote(String note) {
		order.setNote(note);
	}

	public String getCardtype() {
		return order.getCardtype();
	}

	public void setCardtype(String cardtype) {
		order.setCardtype(cardtype);
	}

	public String getCardnumber() {
		return order.getCardnumber();
	}

	public void setCardnumber(String cardnumber) {
		order.setCardnumber(cardnumber);
	}

	public String getMedia() {
		return order.getMedia();
	}

	public void setMedia(String media) {
		order.setMedia(media);
	}

	public String getCallid() {
		return order.getCallid();
	}

	public void setCallid(String callid) {
		order.setCallid(callid);
	}

	public String getCallbackid() {
		return order.getCallbackid();
	}

	public void setCallbackid(String callbackid) {
		order.setCallbackid(callbackid);
	}

	public String getParentid() {
		return order.getParentid();
	}

	public void setParentid(String parentid) {
		order.setParentid(parentid);
	}

	public String getChildid() {
		return order.getChildid();
	}

	public void setChildid(String childid) {
		order.setChildid(childid);
	}

	public Date getStarttm() {
		return order.getStarttm();
	}

	public void setStarttm(Date starttm) {
		order.setStarttm(starttm);
	}

	public Date getEndtm() {
		return order.getEndtm();
	}

	public void setEndtm(Date endtm) {
		order.setEndtm(endtm);
	}

	public String getLaststatus() {
		return order.getLaststatus();
	}

	public void setLaststatus(String laststatus) {
		order.setLaststatus(laststatus);
	}

	public String getRemark() {
		return order.getRemark();
	}

	public void setRemark(String remark) {
		order.setRemark(remark);
	}

	public String getCardrightnum() {
		return order.getCardrightnum();
	}

	public void setCardrightnum(String cardrightnum) {
		order.setCardrightnum(cardrightnum);
	}

	public String getEmsclearstatus() {
		return order.getEmsclearstatus();
	}

	public void setEmsclearstatus(String emsclearstatus) {
		order.setEmsclearstatus(emsclearstatus);
	}

	public String getRefuse() {
		return order.getRefuse();
	}

	public void setRefuse(String refuse) {
		order.setRefuse(refuse);
	}

	public Date getParcdt() {
		return order.getParcdt();
	}

	public void setParcdt(Date parcdt) {
		order.setParcdt(parcdt);
	}

	public String getDnis() {
		return order.getDnis();
	}

	public void setDnis(String dnis) {
		order.setDnis(dnis);
	}

	public String getAreacode() {
		return order.getAreacode();
	}

	public void setAreacode(String areacode) {
		order.setAreacode(areacode);
	}

	public String getNetorderid() {
		return order.getNetorderid();
	}

	public void setNetorderid(String netorderid) {
		order.setNetorderid(netorderid);
	}

	public Date getNetcrdt() {
		return order.getNetcrdt();
	}

	public void setNetcrdt(Date netcrdt) {
		order.setNetcrdt(netcrdt);
	}

	public String getJifen() {
		return order.getJifen();
	}

	public void setJifen(String jifen) {
		order.setJifen(jifen);
	}

	public Integer getTicket() {
		return order.getTicket();
	}

	public void setTicket(Integer ticket) {
		order.setTicket(ticket);
	}

	public Integer getTicketcount() {
		return order.getTicketcount();
	}

	public void setTicketcount(Integer ticketcount) {
		order.setTicketcount(ticketcount);
	}

	public String getAni() {
		return order.getAni();
	}

	public void setAni(String ani) {
		order.setAni(ani);
	}

	public String getAdusr() {
		return order.getAdusr();
	}

	public void setAdusr(String adusr) {
		order.setAdusr(adusr);
	}

	public Date getAddt() {
		return order.getAddt();
	}

	public void setAddt(Date addt) {
		order.setAddt(addt);
	}

	public String getErrcode() {
		return order.getErrcode();
	}

	public void setErrcode(String errcode) {
		order.setErrcode(errcode);
	}

	public AddressExt getAddress() {
		return order.getAddress();
	}

	public void setAddress(AddressExt address) {
		order.setAddress(address);
	}

	public String getGrpid() {
		return order.getGrpid();
	}

	public void setGrpid(String grpid) {
		order.setGrpid(grpid);
	}

	public String getCompanyid() {
		return order.getCompanyid();
	}

	public void setCompanyid(String companyid) {
		order.setCompanyid(companyid);
	}

	public String getSpid() {
		return order.getSpid();
	}

	public void setSpid(String spid) {
		order.setSpid(spid);
	}

	public String getInvoicetitle() {
		return order.getInvoicetitle();
	}

	public void setInvoicetitle(String invoicetitle) {
		order.setInvoicetitle(invoicetitle);
	}

	public String getCustomizestatus() {
		return order.getCustomizestatus();
	}

	public void setCustomizestatus(String customizestatus) {
		order.setCustomizestatus(customizestatus);
	}

	public String getScratchcard() {
		return order.getScratchcard();
	}

	public void setScratchcard(String scratchcard) {
		order.setScratchcard(scratchcard);
	}

	public BigDecimal getSccardamount() {
		return order.getSccardamount();
	}

	public void setSccardamount(BigDecimal sccardamount) {
		order.setSccardamount(sccardamount);
	}

	public Date getRfoutdat() {
		return order.getRfoutdat();
	}

	public void setRfoutdat(Date rfoutdat) {
		order.setRfoutdat(rfoutdat);
	}

	public BigInteger getExpscm() {
		return order.getExpscm();
	}

	public void setExpscm(BigInteger expscm) {
		order.setExpscm(expscm);
	}

	public BigInteger getExpwms() {
		return order.getExpwms();
	}

	public void setExpwms(BigInteger expwms) {
		order.setExpwms(expwms);
	}

	public BigInteger getIssf() {
		return order.getIssf();
	}

	public void setIssf(BigInteger issf) {
		order.setIssf(issf);
	}

	public Date getConfirmexpdt() {
		return order.getConfirmexpdt();
	}

	public void setConfirmexpdt(Date confirmexpdt) {
		order.setConfirmexpdt(confirmexpdt);
	}

	public String getAlipayid() {
		return order.getAlipayid();
	}

	public void setAlipayid(String alipayid) {
		order.setAlipayid(alipayid);
	}

	public String getIstrans() {
		return order.getIstrans();
	}

	public void setIstrans(String istrans) {
		order.setIstrans(istrans);
	}

	public Date getTransdate() {
		return order.getTransdate();
	}

	public void setTransdate(Date transdate) {
		order.setTransdate(transdate);
	}

	public String getTransversion() {
		return order.getTransversion();
	}

	public void setTransversion(String transversion) {
		order.setTransversion(transversion);
	}

	public Integer getLastLockSeqid() {
		return order.getLastLockSeqid();
	}

	public void setLastLockSeqid(Integer lastLockSeqid) {
		order.setLastLockSeqid(lastLockSeqid);
	}

	public Integer getLastUpdateSeqid() {
		return order.getLastUpdateSeqid();
	}

	public void setLastUpdateSeqid(Integer lastUpdateSeqid) {
		order.setLastUpdateSeqid(lastUpdateSeqid);
	}

	public Date getLastUpdateTime() {
		return order.getLastUpdateTime();
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		order.setLastUpdateTime(lastUpdateTime);
	}

	public String getIsassign() {
		return order.getIsassign();
	}

	public void setIsassign(String isassign) {
		order.setIsassign(isassign);
	}

	public Integer getContactidRefId() {
		return order.getContactidRefId();
	}

	public void setContactidRefId(Integer contactidRefId) {
		order.setContactidRefId(contactidRefId);
	}

	public Integer getPaycontactidRefId() {
		return order.getPaycontactidRefId();
	}

	public void setPaycontactidRefId(Integer paycontactidRefId) {
		order.setPaycontactidRefId(paycontactidRefId);
	}

	public Integer getGetcontactidRefId() {
		return order.getGetcontactidRefId();
	}

	public void setGetcontactidRefId(Integer getcontactidRefId) {
		order.setGetcontactidRefId(getcontactidRefId);
	}

	public String getReturnsstatus() {
		return order.getReturnsstatus();
	}

	public void setReturnsstatus(String returnsstatus) {
		order.setReturnsstatus(returnsstatus);
	}

	public Integer getRevision() {
		return order.getRevision();
	}

	public void setRevision(Integer revision) {
		order.setRevision(revision);
	}

	public Long getVersion() {
		return order.getVersion();
	}

	public void setVersion(Long version) {
		order.setVersion(version);
	}

	public String getReqEMS() {
		return order.getReqEMS();
	}

	public void setReqEMS(String reqEMS) {
		order.setReqEMS(reqEMS);
	}

	public String getReqUsr() {
		return order.getReqUsr();
	}

	public void setReqUsr(String reqUsr) {
		order.setReqUsr(reqUsr);
	}
	
	public List<OrderDetailDto> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetailDto> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public String getOrderStatusName() {
		return orderStatusName;
	}

	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}

	public String getCustomizeStatusName() {
		return customizeStatusName;
	}

	public void setCustomizeStatusName(String customizeStatusName) {
		this.customizeStatusName = customizeStatusName;
	}

	public String getOrderTypeName() {
		return orderTypeName;
	}

	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}

	public CompanyDeliverySpan getDelivery() {
		return delivery;
	}

	public void setDelivery(CompanyDeliverySpan delivery) {
		this.delivery = delivery;
	}

	public Integer getCheckResult() {
		return order.getCheckResult();
	}

	public void setCheckResult(Integer checkResult) {
		order.setCheckResult(checkResult);
	}


	
	// ************************************************* //

	public static class OrderDetailDto implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -3126974460408299224L;
		
		private OrderDetail orderDetail;
		
		private String productTypeName;

		public OrderDetailDto(OrderDetail orderDetail) {
			this.orderDetail = orderDetail;
		}

		public Long getId() {
			return orderDetail.getId();
		}

		public void setId(Long id) {
			orderDetail.setId(id);
		}

		public String getOrderdetid() {
			return orderDetail.getOrderdetid();
		}

		public void setOrderdetid(String orderdetid) {
			orderDetail.setOrderdetid(orderdetid);
		}

		public Order getOrderhist() {
			return orderDetail.getOrderhist();
		}

		public void setOrderhist(Order orderhist) {
			orderDetail.setOrderhist(orderhist);
		}

		public String getOrderid() {
			return orderDetail.getOrderid();
		}

		public void setOrderid(String orderid) {
			orderDetail.setOrderid(orderid);
		}

		public String getProdid() {
			return orderDetail.getProdid();
		}

		public void setProdid(String prodid) {
			orderDetail.setProdid(prodid);
		}

		public String getContactid() {
			return orderDetail.getContactid();
		}

		public void setContactid(String contactid) {
			orderDetail.setContactid(contactid);
		}

		public String getProdscode() {
			return orderDetail.getProdscode();
		}

		public void setProdscode(String prodscode) {
			orderDetail.setProdscode(prodscode);
		}

		public String getProdname() {
			return orderDetail.getProdname();
		}

		public void setProdname(String prodname) {
			orderDetail.setProdname(prodname);
		}

		public String getSoldwith() {
			return orderDetail.getSoldwith();
		}

		public void setSoldwith(String soldwith) {
			orderDetail.setSoldwith(soldwith);
		}

		public String getStatus() {
			return orderDetail.getStatus();
		}

		public void setStatus(String status) {
			orderDetail.setStatus(status);
		}

		public String getReckoning() {
			return orderDetail.getReckoning();
		}

		public void setReckoning(String reckoning) {
			orderDetail.setReckoning(reckoning);
		}

		public Date getReckoningdt() {
			return orderDetail.getReckoningdt();
		}

		public void setReckoningdt(Date reckoningdt) {
			orderDetail.setReckoningdt(reckoningdt);
		}

		public Date getFbdt() {
			return orderDetail.getFbdt();
		}

		public void setFbdt(Date fbdt) {
			orderDetail.setFbdt(fbdt);
		}

		public BigDecimal getUprice() {
			return orderDetail.getUprice();
		}

		public void setUprice(BigDecimal uprice) {
			orderDetail.setUprice(uprice);
		}

		public Integer getUpnum() {
			return orderDetail.getUpnum();
		}

		public void setUpnum(Integer upnum) {
			orderDetail.setUpnum(upnum);
		}

		public BigDecimal getSprice() {
			return orderDetail.getSprice();
		}

		public void setSprice(BigDecimal sprice) {
			orderDetail.setSprice(sprice);
		}

		public Integer getSpnum() {
			return orderDetail.getSpnum();
		}

		public void setSpnum(Integer spnum) {
			orderDetail.setSpnum(spnum);
		}

		public BigDecimal getPayment() {
			return orderDetail.getPayment();
		}

		public void setPayment(BigDecimal payment) {
			orderDetail.setPayment(payment);
		}

		public BigDecimal getFreight() {
			return orderDetail.getFreight();
		}

		public void setFreight(BigDecimal freight) {
			orderDetail.setFreight(freight);
		}

		public BigDecimal getPostfee() {
			return orderDetail.getPostfee();
		}

		public void setPostfee(BigDecimal postfee) {
			orderDetail.setPostfee(postfee);
		}

		public BigDecimal getClearfee() {
			return orderDetail.getClearfee();
		}

		public void setClearfee(BigDecimal clearfee) {
			orderDetail.setClearfee(clearfee);
		}

		public Date getOrderdt() {
			return orderDetail.getOrderdt();
		}

		public void setOrderdt(Date orderdt) {
			orderDetail.setOrderdt(orderdt);
		}

		public String getProvinceid() {
			return orderDetail.getProvinceid();
		}

		public void setProvinceid(String provinceid) {
			orderDetail.setProvinceid(provinceid);
		}

		public String getState() {
			return orderDetail.getState();
		}

		public void setState(String state) {
			orderDetail.setState(state);
		}

		public String getCity() {
			return orderDetail.getCity();
		}

		public void setCity(String city) {
			orderDetail.setCity(city);
		}

		public String getMdusr() {
			return orderDetail.getMdusr();
		}

		public void setMdusr(String mdusr) {
			orderDetail.setMdusr(mdusr);
		}

		public String getBreason() {
			return orderDetail.getBreason();
		}

		public void setBreason(String breason) {
			orderDetail.setBreason(breason);
		}

		public String getFeedback() {
			return orderDetail.getFeedback();
		}

		public void setFeedback(String feedback) {
			orderDetail.setFeedback(feedback);
		}

		public String getGoodsback() {
			return orderDetail.getGoodsback();
		}

		public void setGoodsback(String goodsback) {
			orderDetail.setGoodsback(goodsback);
		}

		public String getProducttype() {
			return orderDetail.getProducttype();
		}

		public void setProducttype(String producttype) {
			orderDetail.setProducttype(producttype);
		}

		public Date getBackdt() {
			return orderDetail.getBackdt();
		}

		public void setBackdt(Date backdt) {
			orderDetail.setBackdt(backdt);
		}

		public String toString() {
			return orderDetail.toString();
		}

		public Integer getBackmoney() {
			return orderDetail.getBackmoney();
		}

		public void setBackmoney(Integer backmoney) {
			orderDetail.setBackmoney(backmoney);
		}

		public String getOldprod() {
			return orderDetail.getOldprod();
		}

		public void setOldprod(String oldprod) {
			orderDetail.setOldprod(oldprod);
		}

		public BigDecimal getCompensate() {
			return orderDetail.getCompensate();
		}

		public void setCompensate(BigDecimal compensate) {
			orderDetail.setCompensate(compensate);
		}

		public String getPurpose() {
			return orderDetail.getPurpose();
		}

		public void setPurpose(String purpose) {
			orderDetail.setPurpose(purpose);
		}

		public String getJifen() {
			return orderDetail.getJifen();
		}

		public void setJifen(String jifen) {
			orderDetail.setJifen(jifen);
		}

		public Integer getTicket() {
			return orderDetail.getTicket();
		}

		public void setTicket(Integer ticket) {
			orderDetail.setTicket(ticket);
		}

		public String getNum1() {
			return orderDetail.getNum1();
		}

		public void setNum1(String num1) {
			orderDetail.setNum1(num1);
		}

		public String getNum2() {
			return orderDetail.getNum2();
		}

		public void setNum2(String num2) {
			orderDetail.setNum2(num2);
		}

		public String getBaleprodid() {
			return orderDetail.getBaleprodid();
		}

		public void setBaleprodid(String baleprodid) {
			orderDetail.setBaleprodid(baleprodid);
		}

		public String getCardrightnum() {
			return orderDetail.getCardrightnum();
		}

		public void setCardrightnum(String cardrightnum) {
			orderDetail.setCardrightnum(cardrightnum);
		}

		public BigDecimal getAccountingcost() {
			return orderDetail.getAccountingcost();
		}

		public void setAccountingcost(BigDecimal accountingcost) {
			orderDetail.setAccountingcost(accountingcost);
		}

		public String getSpid() {
			return orderDetail.getSpid();
		}

		public void setSpid(String spid) {
			orderDetail.setSpid(spid);
		}

		public String getProdbankid() {
			return orderDetail.getProdbankid();
		}

		public void setProdbankid(String prodbankid) {
			orderDetail.setProdbankid(prodbankid);
		}

		public String getScratchcard() {
			return orderDetail.getScratchcard();
		}

		public void setScratchcard(String scratchcard) {
			orderDetail.setScratchcard(scratchcard);
		}

		public BigDecimal getSccardamount() {
			return orderDetail.getSccardamount();
		}

		public void setSccardamount(BigDecimal sccardamount) {
			orderDetail.setSccardamount(sccardamount);
		}

		public String getCatalogno() {
			return orderDetail.getCatalogno();
		}

		public void setCatalogno(String catalogno) {
			orderDetail.setCatalogno(catalogno);
		}

		public String getPromotionsdocno() {
			return orderDetail.getPromotionsdocno();
		}

		public void setPromotionsdocno(String promotionsdocno) {
			orderDetail.setPromotionsdocno(promotionsdocno);
		}

		public Integer getPromotionsdetruid() {
			return orderDetail.getPromotionsdetruid();
		}

		public void setPromotionsdetruid(Integer promotionsdetruid) {
			orderDetail.setPromotionsdetruid(promotionsdetruid);
		}

		public Integer getLastLockSeqid() {
			return orderDetail.getLastLockSeqid();
		}

		public void setLastLockSeqid(Integer lastLockSeqid) {
			orderDetail.setLastLockSeqid(lastLockSeqid);
		}

		public Integer getLastUpdateSeqid() {
			return orderDetail.getLastUpdateSeqid();
		}

		public void setLastUpdateSeqid(Integer lastUpdateSeqid) {
			orderDetail.setLastUpdateSeqid(lastUpdateSeqid);
		}

		public Date getLastUpdateTime() {
			return orderDetail.getLastUpdateTime();
		}

		public void setLastUpdateTime(Date lastUpdateTime) {
			orderDetail.setLastUpdateTime(lastUpdateTime);
		}

		public Product getProduct() {
			return orderDetail.getProduct();
		}

		public void setProduct(Product product) {
			orderDetail.setProduct(product);
		}

		public Integer getRevision() {
			return orderDetail.getRevision();
		}

		public void setRevision(Integer revision) {
			orderDetail.setRevision(revision);
		}

		public String getProductTypeName() {
			return productTypeName;
		}

		public void setProductTypeName(String productTypeName) {
			this.productTypeName = productTypeName;
		}
		
	}
	
	public static class ContactDto implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 2478637938303630094L;

		private Contact contact;
		
		private String bindingGroup;
		
		private String levelName;
		
		public ContactDto(Contact contact) {
			this.contact = contact;
		}

		public void setContactid(String value) {
			contact.setContactid(value);
		}

		public String getContactid() {
			return contact.getContactid();
		}

		public String getName() {
			return contact.getName();
		}

		public void setName(String value) {
			contact.setName(value);
		}

		public String getSex() {
			return contact.getSex();
		}

		public void setSex(String value) {
			contact.setSex(value);
		}

		public String getTitle() {
			return contact.getTitle();
		}

		public void setTitle(String value) {
			contact.setTitle(value);
		}

		public String getDept() {
			return contact.getDept();
		}

		public void setDept(String value) {
			contact.setDept(value);
		}

		public String getContacttype() {
			return contact.getContacttype();
		}

		public void setContacttype(String value) {
			contact.setContacttype(value);
		}

		public String getEmail() {
			return contact.getEmail();
		}

		public void setEmail(String value) {
			contact.setEmail(value);
		}

		public String getWebaddr() {
			return contact.getWebaddr();
		}

		public void setWebaddr(String value) {
			contact.setWebaddr(value);
		}

		public String getCrdtString() {
			return contact.getCrdtString();
		}

		public void setCrdtString(String value) {
			contact.setCrdtString(value);
		}

		public Date getCrdt() {
			return contact.getCrdt();
		}

		public void setCrdt(Date value) {
			contact.setCrdt(value);
		}

		public String getCrtmString() {
			return contact.getCrtmString();
		}

		public void setCrtmString(String value) {
			contact.setCrtmString(value);
		}

		public Date getCrtm() {
			return contact.getCrtm();
		}

		public void setCrtm(Date value) {
			contact.setCrtm(value);
		}

		public String getCrusr() {
			return contact.getCrusr();
		}

		public void setCrusr(String value) {
			contact.setCrusr(value);
		}

		public String getMddtString() {
			return contact.getMddtString();
		}

		public void setMddtString(String value) {
			contact.setMddtString(value);
		}

		public Date getMddt() {
			return contact.getMddt();
		}

		public void setMddt(Date value) {
			contact.setMddt(value);
		}

		public String getMdtmString() {
			return contact.getMdtmString();
		}

		public void setMdtmString(String value) {
			contact.setMdtmString(value);
		}

		public Date getMdtm() {
			return contact.getMdtm();
		}

		public void setMdtm(Date value) {
			contact.setMdtm(value);
		}

		public String getMdusr() {
			return contact.getMdusr();
		}

		public void setMdusr(String value) {
			contact.setMdusr(value);
		}

		public String getEntityid() {
			return contact.getEntityid();
		}

		public void setEntityid(String value) {
			contact.setEntityid(value);
		}

		public Date getBirthday() {
			return contact.getBirthday();
		}

		public void setBirthday(Date value) {
			contact.setBirthday(value);
		}

		public String getAges() {
			return contact.getAges();
		}

		public void setAges(String value) {
			contact.setAges(value);
		}

		public String getEducation() {
			return contact.getEducation();
		}

		public void setEducation(String value) {
			contact.setEducation(value);
		}

		public String getIncome() {
			return contact.getIncome();
		}

		public void setIncome(String value) {
			contact.setIncome(value);
		}

		public String getMarriage() {
			return contact.getMarriage();
		}

		public void setMarriage(String value) {
			contact.setMarriage(value);
		}

		public String getOccupation() {
			return contact.getOccupation();
		}

		public void setOccupation(String value) {
			contact.setOccupation(value);
		}

		public String getConsumer() {
			return contact.getConsumer();
		}

		public void setConsumer(String value) {
			contact.setConsumer(value);
		}

		public String getPin() {
			return contact.getPin();
		}

		public void setPin(String value) {
			contact.setPin(value);
		}

		public Long getTotal() {
			return contact.getTotal();
		}

		public void setTotal(Long value) {
			contact.setTotal(value);
		}

		public String getPurpose() {
			return contact.getPurpose();
		}

		public void setPurpose(String value) {
			contact.setPurpose(value);
		}

		public String getNetcontactid() {
			return contact.getNetcontactid();
		}

		public void setNetcontactid(String value) {
			contact.setNetcontactid(value);
		}

		public String getJifen() {
			return contact.getJifen();
		}

		public void setJifen(String value) {
			contact.setJifen(value);
		}

		public String getAreacode() {
			return contact.getAreacode();
		}

		public void setAreacode(String value) {
			contact.setAreacode(value);
		}

		public Long getTicketvalue() {
			return contact.getTicketvalue();
		}

		public void setTicketvalue(Long value) {
			contact.setTicketvalue(value);
		}

		public String getFancy() {
			return contact.getFancy();
		}

		public void setFancy(String value) {
			contact.setFancy(value);
		}

		public String getIdcardFlag() {
			return contact.getIdcardFlag();
		}

		public void setIdcardFlag(String value) {
			contact.setIdcardFlag(value);
		}

		public String getAttitude() {
			return contact.getAttitude();
		}

		public void setAttitude(String value) {
			contact.setAttitude(value);
		}

		public String getMembertype() {
			return contact.getMembertype();
		}

		public void setMembertype(String value) {
			contact.setMembertype(value);
		}

		public String getMemberlevel() {
			return contact.getMemberlevel();
		}

		public void setMemberlevel(String value) {
			contact.setMemberlevel(value);
		}

		public String getLastdateString() {
			return contact.getLastdateString();
		}

		public void setLastdateString(String value) {
			contact.setLastdateString(value);
		}

		public Date getLastdate() {
			return contact.getLastdate();
		}

		public void setLastdate(Date value) {
			contact.setLastdate(value);
		}

		public Integer getTotalfrequency() {
			return contact.getTotalfrequency();
		}

		public void setTotalfrequency(Integer value) {
			contact.setTotalfrequency(value);
		}

		public Long getTotalmonetary() {
			return contact.getTotalmonetary();
		}

		public void setTotalmonetary(Long value) {
			contact.setTotalmonetary(value);
		}

		public String getSundept() {
			return contact.getSundept();
		}

		public void setSundept(String value) {
			contact.setSundept(value);
		}

		public Integer getCredit() {
			return contact.getCredit();
		}

		public void setCredit(Integer value) {
			contact.setCredit(value);
		}

		public String getCustomersource() {
			return contact.getCustomersource();
		}

		public void setCustomersource(String value) {
			contact.setCustomersource(value);
		}

		public String getDatatype() {
			return contact.getDatatype();
		}

		public void setDatatype(String value) {
			contact.setDatatype(value);
		}

		public String getCar() {
			return contact.getCar();
		}

		public void setCar(String value) {
			contact.setCar(value);
		}

		public String getCreditcard() {
			return contact.getCreditcard();
		}

		public void setCreditcard(String value) {
			contact.setCreditcard(value);
		}

		public String getChildren() {
			return contact.getChildren();
		}

		public void setChildren(String value) {
			contact.setChildren(value);
		}

		public Date getChildrenage() {
			return contact.getChildrenage();
		}

		public void setChildrenage(Date value) {
			contact.setChildrenage(value);
		}

		public Integer getCarmoney1() {
			return contact.getCarmoney1();
		}

		public void setCarmoney1(Integer value) {
			contact.setCarmoney1(value);
		}

		public Integer getCarmoney2() {
			return contact.getCarmoney2();
		}

		public void setCarmoney2(Integer value) {
			contact.setCarmoney2(value);
		}

		public String getDnis() {
			return contact.getDnis();
		}

		public void setDnis(String value) {
			contact.setDnis(value);
		}

		public String getCaseid() {
			return contact.getCaseid();
		}

		public void setCaseid(String value) {
			contact.setCaseid(value);
		}

		public String getHasElder() {
			return contact.getHasElder();
		}

		public void setHasElder(String hasElder) {
			contact.setHasElder(hasElder);
		}

		public Date getElderBirthday() {
			return contact.getElderBirthday();
		}

		public void setElderBirthday(Date elderBirthday) {
			contact.setElderBirthday(elderBirthday);
		}

		public String getOccupationStatus() {
			return contact.getOccupationStatus();
		}

		public void setOccupationStatus(String occupationStatus) {
			contact.setOccupationStatus(occupationStatus);
		}

		public String getBindingGroup() {
			return bindingGroup;
		}

		public void setBindingGroup(String bindingGroup) {
			this.bindingGroup = bindingGroup;
		}

		public String getLevelName() {
			return levelName;
		}

		public void setLevelName(String levelName) {
			this.levelName = levelName;
		}
		
	}
}
