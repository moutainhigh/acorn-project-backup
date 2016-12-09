/*
 * @(#)ShipmentRefundServiceImpl.java 1.0 2013-3-28上午10:41:11
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.oms.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.dao.NamesDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.Company;
import com.chinadrtv.erp.model.Contact;
import com.chinadrtv.erp.model.Names;
import com.chinadrtv.erp.model.Orderhist;
import com.chinadrtv.erp.model.Phone;
import com.chinadrtv.erp.model.Warehouse;
import com.chinadrtv.erp.model.trade.RefundHead;
import com.chinadrtv.erp.model.trade.ShipmentDetail;
import com.chinadrtv.erp.model.trade.ShipmentHeader;
import com.chinadrtv.erp.model.trade.ShipmentRefund;
import com.chinadrtv.erp.model.trade.ShipmentRefundDetail;
import com.chinadrtv.erp.oms.dao.ContactDao;
import com.chinadrtv.erp.oms.dao.RefundHeadDao;
import com.chinadrtv.erp.oms.dao.ShipmentHeaderDao;
import com.chinadrtv.erp.oms.dao.ShipmentRefundDao;
import com.chinadrtv.erp.oms.dao.ShipmentRefundDetailDao;
import com.chinadrtv.erp.oms.dto.OrderRefundDto;
import com.chinadrtv.erp.oms.dto.OrderdetRefundDto;
import com.chinadrtv.erp.oms.dto.OrderhistRefundDto;
import com.chinadrtv.erp.oms.dto.RefundSearchDto;
import com.chinadrtv.erp.oms.dto.ShipmentRefundDto;
import com.chinadrtv.erp.oms.exception.OmsException;
import com.chinadrtv.erp.oms.exception.ShipmentRefundErrorCode;
import com.chinadrtv.erp.oms.service.CompanyService;
import com.chinadrtv.erp.oms.service.OrderhistService;
import com.chinadrtv.erp.oms.service.PhoneService;
import com.chinadrtv.erp.oms.service.ShipmentHeaderService;
import com.chinadrtv.erp.oms.service.ShipmentRefundService;
import com.chinadrtv.erp.oms.service.WarehouseService;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.SecurityHelper;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013-3-28 上午10:41:11 
 * 
 */
@Service
public class ShipmentRefundServiceImpl extends GenericServiceImpl<ShipmentRefund, Long> implements
		ShipmentRefundService {
	@Autowired
	private ShipmentRefundDao shipmentRefundDao;
	@Autowired
	private ShipmentHeaderDao shipmentHeaderDao;
	@Autowired
	private ShipmentRefundDetailDao shipmentRefundDetailDao;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private WarehouseService warehouseService;
	@Autowired
	private NamesDao namesDao;
	@Autowired
	private RefundHeadDao refundHeadDao;
    @Autowired
    private ContactDao contactDao;
	
	@Override
	protected GenericDao<ShipmentRefund, Long> getGenericDao() {
		return shipmentRefundDao;
	}

	/* 
	 * 查询当前用户已检验数量
	* <p>Title: queryCheckCount</p>
	* <p>Description: </p>
	* @return
	* @see com.chinadrtv.erp.oms.service.ShipmentRefundService#queryCheckCount()
	*/ 
	public Map<String, Object> queryCheckCount(String entityId) {
		AgentUser agentUser = SecurityHelper.getLoginUser();
		Map<String,  Object> rsMap = shipmentRefundDao.queryCheckCount(entityId, agentUser);
		return rsMap;
	}

	/**
	* <p>Title: queryRefundList</p>
	* <p>Description: </p>
	* @param mailId
	* @param shipmentId
	* @return Map<String, Object>
	* @see com.chinadrtv.erp.oms.service.ShipmentRefundService#queryRefundList(java.lang.String, java.lang.String)
	*/ 
	public Map<String, Object> queryRefundList(String mailId, String shipmentId, String entityId) {
		
		Map<String, Object> rsMap = new HashMap<String, Object>();
		List<ShipmentRefundDetail> detailList = new ArrayList<ShipmentRefundDetail>();
		
		if((null==mailId || "".equals(mailId)) && (null==shipmentId || "".equals(shipmentId))){
			rsMap.put("rows", detailList);
			return rsMap;
		}
		ShipmentRefund sr = shipmentRefundDao.queryByParam(mailId, shipmentId, entityId);
		if(null==sr){
			rsMap.put("rows", detailList);
			return rsMap;
		}
		detailList = shipmentRefundDetailDao.getDetailList(sr);
		
		rsMap.put("rows", detailList);
		rsMap.put("shipmentRefund", sr);
		
		return rsMap;
	}



	/** 
	 * <p>Title: queryRefundSendList</p>
	 * <p>Description: </p>
	 * @param shipmentRefund
	 * @return Map<String, Object>
	 * @see com.chinadrtv.erp.oms.service.ShipmentRefundService#queryRefundSendList(com.chinadrtv.erp.model.trade.ShipmentRefund)
	 */ 
	public Map<String, Object> queryRefundSendList(ShipmentRefundDto srDto) {
		Map<String, Object> rsMap = new HashMap<String, Object>();
		List<Map<String, Object>> srList = shipmentRefundDao.queryRefundSendList(srDto);
		for(Map<String, Object> rowMap : srList){
			rowMap.put("id", rowMap.get("ID"));
			rowMap.put("shipmentId", rowMap.get("SHIPMENT_ID"));
			rowMap.put("mailId", rowMap.get("MAIL_ID"));
			rowMap.put("entityId", rowMap.get("ENTITY_ID"));
			rowMap.put("createDate", rowMap.get("CREATE_DATE"));
			rowMap.put("agentUser", rowMap.get("AGENT_USER"));
			rowMap.put("name", rowMap.get("NAME"));
			rowMap.put("warehouseId", rowMap.get("WAREHOUSE_ID"));
			rowMap.put("warehouseName", rowMap.get("WAREHOUSE_NAME"));
			rowMap.put("rejectCode", rowMap.get("REJECT_CODE"));
			//rowMap.put("qty", rowMap.get("QTY"));
			BigDecimal qty = (BigDecimal) rowMap.get("QTY");
			String isComplete = "";
			if(qty.intValue()==0){
				isComplete = "完整";
			}else{
				isComplete = "不完整";
			}
			rowMap.put("isComplete", isComplete);
			
			String rejectCode = (String) rowMap.get("REJECT_CODE");
			
			Names names = namesDao.getNamesById("REJECT_CODE", rejectCode);
			rowMap.put("rejectName", names.getTdsc());
			
			Company cp = companyService.getCompanyByID(rowMap.get("ENTITY_ID").toString());
			rowMap.put("entityName", cp.getName());
			
			rowMap.remove("ID");
			rowMap.remove("SHIPMENT_ID");
			rowMap.remove("MAIL_ID");
			rowMap.remove("ENTITY_ID");
			rowMap.remove("CREATE_DATE");
			rowMap.remove("AGENT_USER");
			rowMap.remove("NAME");
			rowMap.remove("WAREHOUSE_ID");
			rowMap.remove("WAREHOUSE_NAME");
			rowMap.remove("QTY");
			rowMap.remove("REJECT_CODE");
		}
		
		rsMap.put("rows", srList);
		return rsMap;
	}



	/** 
	 * <p>Title: exportRefundSendList</p>
	 * <p>Description: </p>
	 * @param srDto
	 * @return
	 * @see com.chinadrtv.erp.oms.service.ShipmentRefundService#exportRefundSendList(com.chinadrtv.erp.oms.dto.ShipmentRefundDto)
	 */
	public HSSFWorkbook exportRefundSendList(String ids) {
		
		List<Map<String, Object>>  srList = shipmentRefundDao.exportRefundSendList(ids);
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		
		HSSFRow title = sheet.createRow(0);
		
		title.createCell(0).setCellValue("序号");
		title.createCell(1).setCellValue("货品编码");
		title.createCell(2).setCellValue("货品描述 ");
		title.createCell(3).setCellValue("应收量");
		title.createCell(4).setCellValue("正品数");
		title.createCell(5).setCellValue("残品数");
		title.createCell(6).setCellValue("异常数");
		
		for(int i=0; i<srList.size(); i++){
			Map<String, Object> rsMap = srList.get(i);
			HSSFRow row = sheet.createRow(i+1);
			HSSFCell cell0 = row.createCell(0);
			cell0.setCellValue((i + 1) + "");
			
			HSSFCell cell1 = row.createCell(1);
			cell1.setCellValue(rsMap.get("ITEM_ID").toString());
			
			HSSFCell cell2 = row.createCell(2);
			cell2.setCellValue(rsMap.get("ITEM_DESC").toString());
			
			HSSFCell cell3 = row.createCell(3);
			cell3.setCellValue(new Integer(rsMap.get("TOTAL_QTY").toString()));
		}
		
		return wb;
	}


	/** 
	 * <p>Title: 生成退包入库单</p>
	 * <p>Description: </p>
	 * @param ids
	 * @see com.chinadrtv.erp.oms.service.ShipmentRefundService#generateRefundList(java.lang.String)
	 */ 
	public String generateRefundList(ShipmentRefundDto srDto) {
		/**
		 * 1、点 生成退包入库单， 将status设为3,  将生成refund_head 的主键 更新到对应的shipment_refund的 refund_head_id
		   2、生成 发运单， shipmentID 加前缀 H, Account_Type 填 "红充单", crusr 为当前用户, Account_status_id填5, Account_status_remark填完成 senddate=null, 
			shipment_detail 对应的 shipment_refund_datail 表中的记录，
		 */
		String message = "";
		String[] idArr =srDto.getIds().split(",");
		String warehouseId = srDto.getWarehouseId();
		String companyId = srDto.getCompanyId();
		
		RefundHead rh = new RefundHead();
		Warehouse wh = warehouseService.findById(new Long(warehouseId));
		rh.setWarehouse(wh);
		Company company = companyService.getCompanyByID(companyId);
		rh.setCompany(company);
		rh.setFrfundType("12");
		AgentUser user = SecurityHelper.getLoginUser();
		rh.setCreateUser(user.getUserId());
		rh.setCreateDate(new Date());
        rh.setIsToWms("0");
		rh = refundHeadDao.save(rh);
		
		for(String id : idArr){
			ShipmentRefund sr = shipmentRefundDao.get(new Long(id));
			sr.setRefundHeadId(rh.getId());
			sr.setStatus(3L);
			shipmentRefundDao.saveOrUpdate(sr);
			
			String shipmentId = sr.getShipmentId();
			ShipmentHeader oldSh = shipmentHeaderService.getShipmentFromShipmentId(shipmentId);
			
			if("1".equals(oldSh.getReconcilFlag())){
				message += oldSh.getShipmentId() + ",";
				continue;
			}
			
			ShipmentHeader revertSh = new ShipmentHeader();
			BeanUtils.copyProperties(oldSh, revertSh);
			revertSh.setId(null);
			
			String newShipmentId = "H" + shipmentId;
			revertSh.setShipmentId(newShipmentId);
			revertSh.setAccountType("2");
			revertSh.setAccountStatusId("5");
			revertSh.setLogisticsStatusId("5");
			revertSh.setAccountStatusRemark(null);
			revertSh.setLogisticsStatusRemark(null);
			revertSh.setSenddt(null);
			revertSh.setCrusr(user.getUserId());
			revertSh.setMdusr(null);
			revertSh.setMddt(null);
			revertSh.setMailPrice(new BigDecimal(0));
			revertSh.setTotalPrice(null);
			revertSh.setAssociatedId(oldSh.getId());
			
			BigDecimal totalAmount = new BigDecimal(0);
			
			Set<ShipmentRefundDetail> srdSet = sr.getShipmentRefundDetails();
			Set<ShipmentDetail> sdSet = new HashSet<ShipmentDetail>();
			
			for(ShipmentRefundDetail srd : srdSet){
				if(0==srd.getAgentAmount().doubleValue() || srd.getWarehouseQty()==null || 0 == srd.getWarehouseQty()){
					continue;
				}
				ShipmentDetail sd = new ShipmentDetail();
				sd.setShipmentId(newShipmentId);
				sd.setShipmentHeader(revertSh);
				sd.setShipmentLineNum(srd.getShipmentLineNum());
				sd.setItemId(srd.getItemId());
				sd.setItemDesc(srd.getItemDesc());
				//负的库存
				sd.setTotalQty(-srd.getWarehouseQty());
				Double unitPrice = srd.getAgentAmount().doubleValue() / srd.getAgentQty();
				sd.setUnitPrice(new BigDecimal(unitPrice));
				sdSet.add(sd);
				
				Double amount = unitPrice * srd.getWarehouseQty();
				totalAmount = totalAmount.add(new BigDecimal(amount));
			}
			revertSh.setShipmentDetails(sdSet);
			//红冲单为负
			revertSh.setProdPrice(totalAmount.negate());
			
			shipmentHeaderDao.saveOrUpdate(revertSh);
		}
		if(message!=null && !"".equals(message)){
			message += "已经结算，将不生成退包入库单！";
		}
		return message;
		
	}




	
	

	/*================================================================================*/













    //start by xzk
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ShipmentRefundServiceImpl.class);

    @Autowired
    private ShipmentHeaderService shipmentHeaderService;

    @Autowired
    private OrderhistService orderhistService;

    @Autowired
    private PhoneService phoneService;

    @Transactional(readOnly=false)
    public void addShipmentRefund(OrderRefundDto orderRefundDto) {
        //首先检查数据
        if(orderRefundDto==null) return;
        if(StringUtils.isEmpty(orderRefundDto.getRemark())){
            throw new OmsException(ShipmentRefundErrorCode.FIELD_NOT_VALID);
        }
        boolean bHaveRefusedQuantity=false;
        if(orderRefundDto.getOrderdetRefundDtoSet()!=null){
            for(OrderdetRefundDto orderdetRefundDto:orderRefundDto.getOrderdetRefundDtoSet()){
                //检查数据的合法性
                if(orderdetRefundDto.getCheckInQuantity()==null||orderdetRefundDto.getCheckInPrice()==null
                        ||orderdetRefundDto.getProdPrice()==null||orderdetRefundDto.getProdQuantity()==null){
                    throw new OmsException(ShipmentRefundErrorCode.FIELD_NOT_VALID);
                }
                
                if(orderdetRefundDto.getCheckInQuantity()!=null && orderdetRefundDto.getProdQuantity()!=null && orderdetRefundDto.getCheckInQuantity().intValue()>orderdetRefundDto.getProdQuantity().intValue()){
                    throw new OmsException(ShipmentRefundErrorCode.REFUND_QUANTITY_MORETHAN);
                }
//                //半签收单价不能小于订单单价(不需要控制，注销掉)
//                if(orderdetRefundDto.getCheckInQuantity()!=null && orderdetRefundDto.getCheckInQuantity().intValue()>0 && orderdetRefundDto.getCheckInPrice().compareTo(orderdetRefundDto.getProdPrice())>0){
//                    throw new OmsException(ShipmentRefundErrorCode.REFUND_PRICE_LESSTHAN);
//                }

                if(orderdetRefundDto.getCheckInQuantity()!=null && orderdetRefundDto.getCheckInQuantity().intValue()>0){
                    bHaveRefusedQuantity=true;
                }
            }
        }
        if(bHaveRefusedQuantity==false){
            throw new OmsException(ShipmentRefundErrorCode.REFUND_REFUSE_ALL);
        }
        ShipmentHeader shipmentHeader= shipmentHeaderService.getShipmentFromOrderId(orderRefundDto.getOrderId());
        if(shipmentHeader==null){
            throw new OmsException(ShipmentRefundErrorCode.SHIPMENT_NOT_FOUND);
        }
        if(shipmentHeader.getReconcilFlag()!=null && shipmentHeader.getReconcilFlag()==1){
        	throw new OmsException(ShipmentRefundErrorCode.RECONCIL_CONFIRM);
        }

        Orderhist orderhist=orderhistService.getOrderhistById(orderRefundDto.getOrderId());
        if(orderhist==null){
            throw new OmsException(ShipmentRefundErrorCode.ORDER_NOT_FOUND);
        }
        if(orderRefundDto.getOrderdetRefundDtoSet()==null||orderRefundDto.getOrderdetRefundDtoSet().size()==0){
            throw new OmsException(ShipmentRefundErrorCode.HAVE_NOT_REFUND_DETAIL);
        }

        if(!orderhistCanRefund(orderhist)){
            throw new OmsException(ShipmentRefundErrorCode.CAN_NOT_REFUND);
        }

        BigDecimal refundPrice=BigDecimal.ZERO;

        ShipmentRefund shipmentRefund =new ShipmentRefund();
        //首先判断一下是否已经存在了
        List<ShipmentRefund> shipmentRefundList = shipmentRefundDao.getShipmentRefundFromOrderId(orderRefundDto.getOrderId());
        if(shipmentRefundList!=null&&shipmentRefundList.size()>0){
            for(ShipmentRefund shipmentRefund1:shipmentRefundList){
                final Long newStatus=3L;
                final Long newStatus2=4L;
                if(newStatus.equals(shipmentRefund1.getStatus()) || newStatus2.equals(shipmentRefund1.getStatus())){
                    throw new OmsException(ShipmentRefundErrorCode.REFUND_HAVE_EXIST);
                }
            }
            shipmentRefund=shipmentRefundList.get(0);
            shipmentRefund.getShipmentRefundDetails().clear();
        }
        //将对象转换成提交对象

        shipmentRefund.setShipmentId(shipmentHeader.getShipmentId());
        //订单历史表ID
        //shipmentRefund.setOrderRefHisId(shipmentHeader.getOrderRefHisId());
        shipmentRefund.setOrderId(orderRefundDto.getOrderId());
        shipmentRefund.setMailId(orderhist.getMailid());
        shipmentRefund.setEntityId(orderhist.getEntityid());
        shipmentRefund.setAgentCreateDate(new Date());
        //获取agent用户
        shipmentRefund.setAgentUser(orderRefundDto.getUserId());
        shipmentRefund.setStatus(1L);
        shipmentRefund.setRemark(orderRefundDto.getRemark());

        for(OrderdetRefundDto orderdetRefundDto:orderRefundDto.getOrderdetRefundDtoSet()){
            if(orderdetRefundDto.getCheckInQuantity()==null) continue;

            if(orderdetRefundDto.getCheckInPrice()==null||orderdetRefundDto.getCheckInPrice().compareTo(BigDecimal.ZERO)<0){
                throw new OmsException(ShipmentRefundErrorCode.REFUND_PRICE_LESSTHAN);
            }
            ShipmentDetail shipmentDetailMatch=null;
            for(ShipmentDetail shipmentDetail:shipmentHeader.getShipmentDetails()){
                if(shipmentDetail.getId().equals(orderdetRefundDto.getId())){
                    shipmentDetailMatch=shipmentDetail;
                    break;
                }
            }
            if(shipmentDetailMatch==null){
                throw new OmsException(ShipmentRefundErrorCode.CORRESPONDING_SHIPMENT_DETAIL_NOT_EXIST);
            }
            ShipmentRefundDetail shipmentRefundDetail=new ShipmentRefundDetail();
            shipmentRefundDetail.setShipmentId(shipmentHeader.getShipmentId());
            shipmentRefundDetail.setShipmentLineNum(shipmentDetailMatch.getId());
            shipmentRefundDetail.setItemDesc(shipmentDetailMatch.getItemDesc());
            shipmentRefundDetail.setItemId(shipmentDetailMatch.getItemId());
            //
            shipmentRefundDetail.setTotalQty(shipmentDetailMatch.getTotalQty());
            shipmentRefundDetail.setUnitPrice(orderdetRefundDto.getProdPrice());
            shipmentRefundDetail.setFreeFlag(shipmentDetailMatch.getFreeFlag()!=null? Long.parseLong(shipmentDetailMatch.getFreeFlag().toString()):null);
            shipmentRefundDetail.setRemark(shipmentDetailMatch.getRemark());
            //如果数量没有变化，那么不记录
            if(shipmentDetailMatch.getTotalQty().equals(orderdetRefundDto.getCheckInQuantity()))
            {
                continue;
            }
            shipmentRefundDetail.setAgentQty(shipmentDetailMatch.getTotalQty()-orderdetRefundDto.getCheckInQuantity());
            BigDecimal orgDetPayment=shipmentDetailMatch.getUnitPrice().multiply(new BigDecimal(shipmentDetailMatch.getTotalQty()));
            shipmentRefundDetail.setAgentAmount(orgDetPayment.subtract(orderdetRefundDto.getCheckInPayment()));
            //shipmentRefundDetail.setUnitName(shipmentDetailMatch.get);


            if(shipmentRefundDetail.getAgentAmount()==null
                /*||shipmentRefundDetail.getAgentAmount().compareTo(BigDecimal.ZERO)<0*/){
                throw new OmsException(ShipmentRefundErrorCode.CHECKINPAYMENT_NOT_EXIST);
            }

            refundPrice=refundPrice.add(shipmentRefundDetail.getAgentAmount());

            /*//注意行金额没有变化，那么不记录
            if(shipmentRefundDetail.getAgentAmount().compareTo(BigDecimal.ZERO)==0){
                continue;
            }*/

            shipmentRefund.getShipmentRefundDetails().add(shipmentRefundDetail);
            shipmentRefundDetail.setShipmentRefund(shipmentRefund);
        }
        
        shipmentRefund.setAmount(refundPrice);
        if(shipmentRefund.getAmount()!=null&& shipmentRefund.getAmount().compareTo(new BigDecimal(orderhist.getProdprice()))>0){
            throw new OmsException(ShipmentRefundErrorCode.REFUND_TOTAL_MORETHAN);
        }

        if(shipmentRefund.getShipmentRefundDetails().size()>0){
            shipmentRefundDao.addShipmentRefund(shipmentRefund);
        }

    }

    public void updateShipmentRefund(ShipmentRefund shipmentRefund) {
        shipmentRefundDao.updateShipmentRefund(shipmentRefund);
    }

    public ShipmentRefund getShipmentRefund(Long id) {
        return shipmentRefundDao.get(id);
    }

	public List<OrderhistRefundDto> findContactOrderhist(RefundSearchDto refundSearchDto) {
		List<Orderhist> orderhistList = new ArrayList<Orderhist>();

		// 首先根据订单或者运单号查找，如果没有设置，那么根据联系人相关信息查找，最后返回结果
		if (StringUtils.isNotEmpty(refundSearchDto.getOrderId())) {
			// 区分是订单号还是运单号
			String orderId = null;
			if (isShipmentId(refundSearchDto.getOrderId())) {
				// 根据发运单查找到相关订单 - 目前直接从运单号中获取订单号
				orderId = getOrderIdFromShipmentId(refundSearchDto.getOrderId());
				// ShipmentHeader shipmentHeader = shipmentHeaderService.getShipmentFromShipmentId(refundSearchDto.getOrderId());
			} else {
				orderId = refundSearchDto.getOrderId();
			}

			if (StringUtils.isNotEmpty(orderId)) {
				// 直接从订单表中获取
				Orderhist orderhist = orderhistService.getOrderhistById(orderId);
				if (orderhist == null) {
					logger.error("order not found id:" + orderId);
					throw new OmsException(ShipmentRefundErrorCode.ORDER_NOT_FOUND);
				} else {
					// 最后校验其他信息-目前不校验其他信息（忽略掉输入联系人名称或者电话的检查，但订单状态需要判断）
					if (!orderhistCanRefund(orderhist)) {
						// 后面抛异常
						throw new OmsException(ShipmentRefundErrorCode.CAN_NOT_REFUND);
					}
				}
				if (StringUtils.isNotEmpty(refundSearchDto.getMailId())) {
					if (!refundSearchDto.getMailId().equalsIgnoreCase(orderhist.getMailid())) {
						throw new OmsException(ShipmentRefundErrorCode.MAIL_NOT_EQUAL_FROM_ORDER);
					}
				}
				orderhistList.add(orderhist);
			}
		} else if (StringUtils.isNotEmpty(refundSearchDto.getMailId())) {
			Orderhist orderhist = orderhistService.getOrderFromMailId(refundSearchDto.getMailId().toUpperCase());
			if (orderhist != null) {
				orderhistList.add(orderhist);
			}
		} else {
			// 首先确定联系人（根据电话）
			// 然后根据联系人信息来查找订单
			if (StringUtils.isEmpty(refundSearchDto.getContactId()) && StringUtils.isEmpty(refundSearchDto.getPhone())) {
				return null;
			}

			List<String> contactIdList = new ArrayList<String>();

			if (StringUtils.isEmpty(refundSearchDto.getContactId())) {
				// 首先根据电话找到联系人(目前只限制查找手机)
				List<Phone> phoneList = phoneService.findPhoneFromPhn(refundSearchDto.getPhone(), "4");
				if (phoneList == null || phoneList.size() == 0) {
					logger.error("no phone find phn:" + refundSearchDto.getPhone());
					throw new OmsException(ShipmentRefundErrorCode.PHONE_NOT_FOUND);
				}
				for (Phone phone : phoneList) {
					if (StringUtils.isNotEmpty(phone.getContactid())) {
						if (!contactIdList.contains(phone.getContactid())) {
							contactIdList.add(phone.getContactid());
						}
					}
				}
			} else {
				// 根据联系人名称获取联系人Id
				List<Contact> contactList = contactDao.getContactFromName(refundSearchDto.getContactId());
				if (contactList != null) {
					for (Contact contact : contactList) {
						contactIdList.add(contact.getContactid());
					}
				}
			}

			for (String contactId : contactIdList) {
				List<Orderhist> itemOrderhistList = orderhistService.getOrderhistFromGetContact(contactId);
				if (itemOrderhistList != null && itemOrderhistList.size() > 0) {
					for (Orderhist orderhist : itemOrderhistList) {
						if (orderhistCanRefund(orderhist)) {
							orderhistList.add(orderhist);
						}
					}
				}
			}
		}

		// 最后返回订单列表信息
		if (StringUtils.isNotEmpty(refundSearchDto.getEntityId()) && orderhistList != null) {
			List<Orderhist> newOrderhistList = new ArrayList<Orderhist>();
			for (Orderhist orderhist : orderhistList) {
				if (refundSearchDto.getEntityId().equals(orderhist.getEntityid())) {
					newOrderhistList.add(orderhist);
				}
			}
			orderhistList = newOrderhistList;
		}
		return convertFromOrderhist(orderhistList);
	}

    public List<OrderdetRefundDto> findRefundOrderdet(String orderId)
    {
        ShipmentHeader shipmentHeader=shipmentHeaderService.getShipmentFromOrderId(orderId);
        if(shipmentHeader!=null)
        {
            List<ShipmentRefund> shipmentRefundList = shipmentRefundDao.getShipmentRefundFromOrderId(orderId);
            ShipmentRefund shipmentRefund = null;
            if(shipmentRefundList!=null&&shipmentRefundList.size()>0)
            {
                shipmentRefund = shipmentRefundList.get(0);
            }

            List<OrderdetRefundDto> orderdetRefundDtoList=new ArrayList<OrderdetRefundDto>();
            for(ShipmentDetail shipmentDetail:shipmentHeader.getShipmentDetails())
            {
                OrderdetRefundDto orderdetRefundDto=new OrderdetRefundDto();
                orderdetRefundDto.setId(shipmentDetail.getId());
                orderdetRefundDto.setProdName(shipmentDetail.getItemDesc());
                orderdetRefundDto.setProdPrice(shipmentDetail.getUnitPrice());
                orderdetRefundDto.setProdPayment(shipmentDetail.getUnitPrice().multiply(new BigDecimal(shipmentDetail.getTotalQty())));
                orderdetRefundDto.setProdQuantity(shipmentDetail.getTotalQty());


                if(shipmentRefund!=null)
                {
                    //找到对应行
                    ShipmentRefundDetail shipmentRefundDetailMatch=null;
                    for(ShipmentRefundDetail shipmentRefundDetail:shipmentRefund.getShipmentRefundDetails())
                    {
                        if(shipmentDetail.getId().equals(shipmentRefundDetail.getShipmentLineNum()))
                        {
                            shipmentRefundDetailMatch=shipmentRefundDetail;
                            break;
                        }
                    }
                    this.convertFromShipmentRefund(orderdetRefundDto,shipmentDetail,shipmentRefundDetailMatch);
                }
                orderdetRefundDtoList.add(orderdetRefundDto);
            }
            return orderdetRefundDtoList;
        }
        return null;
    }

    private void convertFromShipmentRefund(OrderdetRefundDto orderdetRefundDto, ShipmentDetail shipmentDetail,  ShipmentRefundDetail shipmentRefundDetail){
        if(shipmentRefundDetail!=null)
        {
            //如果不存在，说明此行都签收了，此时注意行金额的计算
            //如果存在，那么
            if(shipmentRefundDetail.getAgentQty()!=null)
            {
                orderdetRefundDto.setCheckInQuantity(shipmentDetail.getTotalQty()-shipmentRefundDetail.getAgentQty());
            }

            if(orderdetRefundDto.getCheckInQuantity()!=null&&orderdetRefundDto.getCheckInQuantity().intValue()>0)
            {
                 orderdetRefundDto.setCheckInPayment(orderdetRefundDto.getProdPayment().subtract(shipmentRefundDetail.getAgentAmount()));
                 orderdetRefundDto.setCheckInPrice(orderdetRefundDto.getCheckInPayment().divide(new BigDecimal(orderdetRefundDto.getCheckInQuantity().toString())));
            }
        }
        else
        {
            orderdetRefundDto.setCheckInQuantity(shipmentDetail.getTotalQty());
            orderdetRefundDto.setCheckInPrice(shipmentDetail.getUnitPrice());
            orderdetRefundDto.setCheckInPayment(orderdetRefundDto.getCheckInPrice().multiply(new BigDecimal(orderdetRefundDto.getCheckInQuantity().toString())));
        }
    }

    public List<OrderhistRefundDto> convertFromOrderhist(List<Orderhist> orderhistList)
    {
        if(orderhistList==null)
        {
            return null;
        }
        List<OrderhistRefundDto> orderhistRefundDtoList =new ArrayList<OrderhistRefundDto>();
        for(Orderhist orderhist:orderhistList)
        {
            OrderhistRefundDto orderhistRefundDto=new OrderhistRefundDto();
            orderhistRefundDto.setOrderId(orderhist.getOrderid());
            orderhistRefundDto.setRfOutDate(orderhist.getRfoutdat());
            ShipmentHeader shipmentHeader = shipmentHeaderService.getShipmentFromOrderId(orderhist.getOrderid());
            if(shipmentHeader!=null)
                orderhistRefundDto.setShipmentId(shipmentHeader.getShipmentId());
            orderhistRefundDto.setMailId(orderhist.getMailid());
            orderhistRefundDto.setOrderTypeId(orderhist.getOrdertype());
            orderhistRefundDto.setTotalPrice(orderhist.getTotalprice().toString());
            orderhistRefundDto.setMailPrice(orderhist.getMailprice().toString());
            orderhistRefundDto.setProdPrice(orderhist.getProdprice().toString());
            if(orderhist.getGetcontact()!=null)
            {
                String contactId=orderhist.getGetcontact().getContactid();
                orderhistRefundDto.setPhone(phoneService.findMainPhoneFromContactId(contactId));
                Contact contact=contactDao.get(contactId);

                String name=contact.getName();
                if(StringUtils.isNotEmpty(name))
                {
                    int len=name.length();
                    String str=name.substring(0,1)+"********************************";
                    str=str.substring(0,len);
                    orderhistRefundDto.setName(str);
                }

            }
            if(orderhist.getAddress()!=null)
            {
                String strAddress="";
                if(orderhist.getAddress().getProvince()!=null)
                {
                    strAddress+=orderhist.getAddress().getProvince().getChinese();
                }
                if(orderhist.getAddress().getCity()!=null)
                {
                    strAddress+=orderhist.getAddress().getCity().getCityname();
                }
                strAddress+="";
                strAddress+= orderhist.getAddress().getAddressDesc();
                if(strAddress.length()>12)
                    strAddress=strAddress.substring(0,12);
                orderhistRefundDto.setAddress(strAddress);
            }

            //半签收金额
            List<ShipmentRefund> shipmentRefundList = shipmentRefundDao.getShipmentRefundFromOrderId(orderhistRefundDto.getOrderId());
            if(shipmentRefundList!=null&&shipmentRefundList.size()>0)
            {
                ShipmentRefund shipmentRefund=shipmentRefundList.get(0);
                BigDecimal mailPrice=orderhist.getMailprice()!=null?new BigDecimal(orderhist.getMailprice().toString()):BigDecimal.ZERO;
                BigDecimal prodPrice=orderhist.getProdprice()!=null?new BigDecimal(orderhist.getProdprice().toString()):BigDecimal.ZERO;
                //if(orderhist.getTotalprice()!=null)
                {
                    //BigDecimal totalPrice=new BigDecimal(orderhist.getTotalprice().toString());
                    orderhistRefundDto.setRefundPrice(prodPrice.subtract(shipmentRefund.getAmount()).toString());
                }
                orderhistRefundDto.setRemark(shipmentRefund.getRemark());
            }
            orderhistRefundDtoList.add(orderhistRefundDto);
        }
        return orderhistRefundDtoList;
    }

    private boolean orderhistCanRefund(Orderhist orderhist){
    	boolean flag = false;
    	if("2".equals(orderhist.getStatus()) || "5".equals(orderhist.getStatus())){
    		if(orderhist.getCustomizestatus()!=null){
                int customizeStatus=Integer.parseInt(orderhist.getCustomizestatus());
                if(customizeStatus>=2){
                	flag = true;
                }
    		}
    	}
        return flag;
    }


    private boolean isShipmentId(String id)
    {
        if(StringUtils.isEmpty(id))
        {
            return false;
        }
        //目前运单编号是订单号+V+3位版本号
        Matcher matcher=pattern.matcher(id);
        if(matcher.matches())
        {
            return true;
        }
        return false;
    }

    private String getOrderIdFromShipmentId(String shipmentId)
    {
        if(StringUtils.isEmpty(shipmentId))
        {
            return null;
        }
        int index=shipmentId.indexOf("V");
        if(index>0)
        {
            return shipmentId.substring(0,index);
        }
        return null;
    }

    private static Pattern pattern = Pattern.compile("[0-9]*V[0-9]*");


}
