package com.chinadrtv.erp.oms.service.impl;



import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.CompanyContract;
import com.chinadrtv.erp.model.Orderdet;
import com.chinadrtv.erp.model.Orderhist;
import com.chinadrtv.erp.model.trade.ShipmentDetail;
import com.chinadrtv.erp.model.trade.ShipmentHeader;
import com.chinadrtv.erp.oms.dao.OrderdetDao;
import com.chinadrtv.erp.oms.dao.OrderhistDao;
import com.chinadrtv.erp.oms.dao.ShipmentHeaderDao;
import com.chinadrtv.erp.oms.dao.SqlDao;
import com.chinadrtv.erp.oms.dto.LogisticsDto;
import com.chinadrtv.erp.oms.dto.ReceiptPaymentDto;
import com.chinadrtv.erp.oms.service.CompanyContractService;
import com.chinadrtv.erp.oms.service.ShipmentHeaderService;
import com.chinadrtv.erp.oms.service.ShipmentSettlementService;
import com.chinadrtv.erp.oms.service.SourceConfigure;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.SecurityHelper;

@Service("shipmentHeaderService")
public class ShipmentHeaderServiceImpl extends GenericServiceImpl<ShipmentHeader, Long> implements ShipmentHeaderService {
	
	private final String SH_EMS_ID = "36";

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ShipmentHeaderServiceImpl.class);

    @Autowired
    private ShipmentHeaderDao shipmentHeaderDao;
    @Autowired
    private OrderhistDao orderhistDao;

    @Autowired
    private OrderdetDao orderdetDao;

    @Autowired
    private RestTemplate template;

    @Autowired
    private SourceConfigure sourceConfigure;
    
    @Autowired
    private CompanyContractService companyContractService;
    
    @Autowired
    private ShipmentSettlementService shipmentSettlementService;
    
	@Autowired
	private SqlDao sqlDao;
    
	@Override
	protected GenericDao<ShipmentHeader, Long> getGenericDao() {
		return shipmentHeaderDao;
	}

    public ShipmentHeader getShipmentById(Long id){
       return shipmentHeaderDao.getShipmentById(id);
    }

    public Long getShipmentCount(String shipmentId, String orderId){
        return shipmentHeaderDao.getShipmentCount(shipmentId, orderId);
    }
    public List<ShipmentHeader> getShipments(String shipmentId, String orderId, int index, int size){
        return shipmentHeaderDao.getShipments(shipmentId, orderId, index, size);
    }

    public Long getLogisticsShipmentCount(Map<String, Object> params){
        return shipmentHeaderDao.getLogisticsShipmentCount(params);
    }

    public List<LogisticsDto> getLogisticsShipments(Map<String, Object> params, int index, int size){
        List objList = shipmentHeaderDao.getLogisticsShipments(params, index, size);
        List<LogisticsDto> logisticsDtoList = new ArrayList<LogisticsDto>();
        Object[] obj = null;
        LogisticsDto logisticsDto = null;
        if(objList.size() > 0){
            for(int i=0;i<objList.size();i++){
                obj = (Object[])objList.get(i);
                logisticsDto = new LogisticsDto();
                logisticsDto.setId(Long.parseLong(obj[0].toString()));
                logisticsDto.setOrderId(obj[1] != null ? obj[1].toString() : null);
                logisticsDto.setShipmentId(obj[2] != null ? obj[2].toString() : null);
                logisticsDto.setAddress(obj[3] != null ? obj[3].toString() : null);
                logisticsDto.setTotalPrice(Double.parseDouble(obj[4] != null ? obj[4].toString() : "0.00"));
                logisticsDto.setWarehouseId(obj[5] != null ? obj[5].toString() : null);
                logisticsDto.setEntityId(obj[6] != null ? obj[6].toString() : null);
                logisticsDto.setLogisticsStatusId(obj[7] != null ? obj[7].toString() : null);
                logisticsDto.setStatus(obj[8] != null ? obj[8].toString() : null);
                logisticsDto.setProdName(obj[9] != null ? obj[9].toString() : null);
                logisticsDto.setConfirmdt(obj[10] != null ? obj[10].toString() : null);
                logisticsDtoList.add(logisticsDto);
            }
        }
        return logisticsDtoList;
    }


    /**
     * 获取制定发运单号或订单的发运单
     * @param shipmentId
     * @return
     */
    public List<ShipmentHeader> getShipments(String shipmentId, String orderId){
       return shipmentHeaderDao.getShipments(shipmentId, orderId);
    }

    /**
     * 更换送货公司(无发货单)
     * @param orderId
     * @param warehouseId
     * @param entityId
     */
    public void alternate(String orderId, String warehouseId, String entityId){
        Orderhist order = orderhistDao.get(orderId);
        if(order == null)
        {
            throw new RuntimeException("订单"+order.getOrderid()+"不存在!");
        }
        else if(!isValidOrder(order))
        {
            throw new RuntimeException("订单状态必须是订购或分拣状态!");
        }
        if(!isAvailableInStock(order, warehouseId)) {
            throw new RuntimeException("当前库存数量不足!");
        }
        String url = sourceConfigure.getDeliverIdupUrl();
        AgentUser agentUser = SecurityHelper.getLoginUser();
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("orderId", order.getOrderid());
        params.put("shipmentId", "");
        params.put("logisticsStatusId", order.getCustomizestatus());
        params.put("newentityId", entityId);
        params.put("newwarehouseId",warehouseId);
        params.put("mdusr", agentUser.getUserId());
        send(url, params);

    }

    /**
     * 变更送货公司(有发货单)
     * @param shipmentId
     * @param warehouseId
     * @param entityId
     */
    public void alternate(Long shipmentId, String warehouseId, String entityId){
        ShipmentHeader shipment = shipmentHeaderDao.getShipmentById(shipmentId);
        if(shipment != null){
            Orderhist order = orderhistDao.get(shipment.getOrderId());
            if(order == null) {
                throw new RuntimeException("订单"+shipment.getOrderId()+"不存在!");
            } else if(!isValidOrder(order)) {
                throw new RuntimeException("订单状态必须是订购或分拣状态!");
            }
            if(!isAvailableInStock(shipment, warehouseId)) {
                throw new RuntimeException("当前库存数量不足!");
            }
            String url = sourceConfigure.getDeliverIdupUrl();
            AgentUser agentUser = SecurityHelper.getLoginUser();
            try
            {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("orderId", shipment.getOrderId());
                params.put("shipmentId", shipment.getShipmentId());
                params.put("logisticsStatusId", shipment.getLogisticsStatusId());
                params.put("newentityId", entityId);
                params.put("newwarehouseId",warehouseId);
                params.put("mdusr", agentUser.getUserId());
                send(url, params);
                //承运商手工指派,不需要自动分拣
                /*order.setIsassign("2");
                orderhistDao.saveOrUpdate(order);*/
            }
            catch (RuntimeException ex) {
                throw ex;
            }

        }
    }

    /**
     * 检查发运单在新仓库内是否有足够数量发货
     * @param shipmentHeader
     * @param warehouse
     * @return
     */
    private Boolean isAvailableInStock(ShipmentHeader shipmentHeader, String warehouse) {
        boolean result = true;
        for(ShipmentDetail detail: shipmentHeader.getShipmentDetails()) {
             result = result && isAvailableInStock(detail, warehouse);
             if(!result) break;
        }
        return result;
    }

    private Boolean isAvailableInStock(Orderhist orderhist, String warehouse) {
        boolean result = true;

        List<Orderdet> details = orderdetDao.getAllOrderdet(orderhist.getOrderid());
        for(Orderdet detail: details) {
            result = result && isAvailableInStock(detail, warehouse);
            if(!result) break;
        }
        return result;
    }

    /**
     *
     * @param detail
     * @param warehouse
     * @return
     */
    private Boolean isAvailableInStock(ShipmentDetail detail, String warehouse) {

        return true;
    }

    private Boolean isAvailableInStock(Orderdet detail, String warehouse) {

        return true;
    }

    /**
     * 是否有效订单
     * @param order
     * @return
     */
    private Boolean isValidOrder(Orderhist order){
        //判断订单是否处于订购状态或分拣状态
        if("1".equals(order.getStatus())) {
            return true;
        } else if("2".equals(order.getStatus())) {
            //检查订单是否未出库
            return null == order.getCustomizestatus() ||
                    "".equals(order.getCustomizestatus()) ||
                    "0".equals(order.getCustomizestatus()) ||
                    "100".equals(order.getCustomizestatus());
        }
        return false;
    }

    /**
     *
     * @param params
     * @return
     * 参数格式:
     * {"orderId":"997700222",
     *  "shipmentId":"997700222001",
     *  "logisticsStatusId":"0",
     *  "newentityId":"23",
     *  "newwarehouseId":"4",
     *  "mdusr":"UR007"}
     */
    public void send(String url, Map<String, Object> params){

        Map<java.lang.String,Object> resultMap = template.postForObject(url, params, Map.class);
        if(resultMap.containsKey("code") && !resultMap.get("code").equals("000")){ //不成功
             throw new RuntimeException(String.format("%s[%s]", resultMap.get("desc"), resultMap.get("code")));
        }
    }

    //start by xzk
    public  ShipmentHeader getShipmentFromShipmentId(String shipmentId)
    {
        if(StringUtils.isEmpty(shipmentId))
            return null;
        return shipmentHeaderDao.getShipmentFromShipmentId(shipmentId);
    }


    public ShipmentHeader getShipmentFromOrderId(String orderId)
    {
        if(StringUtils.isEmpty(orderId))
        {
            return null;
        }

        return shipmentHeaderDao.getShipmentFromOrderId(orderId);
    }

    /**
     * 应收应付款核对查询
     * @return
     */
	public List<ReceiptPaymentDto> getReceiptPayment(Map<String, Object> params,int index, int size) {
		
		List<Object[]> list = shipmentHeaderDao.getReceiptPayments(params, index, size);
		List<ReceiptPaymentDto> rpList = new ArrayList<ReceiptPaymentDto>();
		
		for(Object[] objs : list){
			ReceiptPaymentDto dto = new ReceiptPaymentDto();
			
			BigDecimal id = (BigDecimal) objs[0];
			if(id!=null){
				dto.setId(id.longValue());
			}
			
			Date fbdt = (Date) objs[1];
			if(fbdt!=null){
				dto.setFbdt(fbdt);
			}
			
			String shipmentId = (String) objs[2];
			if(shipmentId!=null){
				dto.setShipmentId(shipmentId);
			}
			
			String mailId = (String) objs[3];
			if(mailId!=null){
				dto.setMailId(mailId);
			}
			
			BigDecimal a = (BigDecimal) objs[4];
			if(a!=null){
				dto.setA(a.doubleValue());
			}
			
			BigDecimal b = (BigDecimal) objs[5];
			if(b!=null){
				dto.setB(b.doubleValue());
			}
			
			BigDecimal c = (BigDecimal) objs[6];
			if(c!=null){
				dto.setC(c.doubleValue());
			}
			
			BigDecimal d = null;
			if(a!=null && b!=null && c!=null){
				d = a.add(b).add(c);
				dto.setD(d.doubleValue());
			}
			
			BigDecimal e = (BigDecimal) objs[7];
			if(e!=null){
				dto.setE(e.doubleValue());
			}
			
			BigDecimal f = (BigDecimal) objs[8];
			if(f!=null){
				dto.setF(f.doubleValue());
			}
			
			BigDecimal g = (BigDecimal) objs[9];
			if(g!=null){
				dto.setG(g.doubleValue());
			}
			
			BigDecimal h = (BigDecimal) objs[10];
			if(h!=null){
				dto.setH(h.doubleValue());
			}
			
			if(d!=null && g!=null){
				dto.setI(d.subtract(g).doubleValue());
			}
			
			BigDecimal j = (BigDecimal) objs[11];
			if(h!=null){
				dto.setJ(j.doubleValue());
			}
			
			BigDecimal k = (BigDecimal) objs[12];
			if(h!=null){
				dto.setK(k.doubleValue());
			}
			
			try {
				if(objs[13]!=null){
					dto.setWeight(Double.parseDouble((String) objs[13]));
				}
			} catch (NumberFormatException e1) {
				dto.setWeight(0D);
			}
			
			rpList.add(dto);
		}
		
		return rpList;
	}

    /**
     * 获取应收应付款记录数
     * @param params
     * @return
     */
	public Long getReceiptPaymentCount(Map<String, Object> params) {
		return shipmentHeaderDao.getReceiptPaymentCount(params);
	}

	/**
	 * 导出excel
	 * @param params
	 * @return
	 */
	public HSSFWorkbook export(Map<String, Object> params) {
		List<ReceiptPaymentDto> list = this.getReceiptPayment(params, 0, Integer.MAX_VALUE);
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		HSSFRow title = sheet.createRow(0);
		title.createCell(0).setCellValue("结账反馈日期 ");
		title.createCell(1).setCellValue("发运单号");
		title.createCell(2).setCellValue("邮件号");
		title.createCell(3).setCellValue("原始应收款（A）");
		title.createCell(4).setCellValue("已登记的免运费(B)");
		title.createCell(5).setCellValue("已登记的半拒收收金额(C)");
		title.createCell(6).setCellValue("实际应收款D=（A-B-C）");
		title.createCell(7).setCellValue("基准投递费（E）");
		title.createCell(8).setCellValue("成功/拒收服务费(F)");
		
		title.createCell(9).setCellValue("代收手续费(J)");
		title.createCell(10).setCellValue("总投递费(K)");
		
		title.createCell(11).setCellValue("反馈导入应收款(G)");
		title.createCell(12).setCellValue("反馈的投递费(H)");
		title.createCell(13).setCellValue("应收款差异I=(D-G)");
		title.createCell(14).setCellValue("重量");
		
		
		//保留两位小数
		HSSFCellStyle cellStyle = wb.createCellStyle();
		HSSFDataFormat format = wb.createDataFormat();
        cellStyle.setDataFormat(format.getFormat("0.00"));
        
        //保留3位小数
        HSSFCellStyle cellStyle2 = wb.createCellStyle();
        cellStyle2.setDataFormat(format.getFormat("0.000"));
		
		for(int i=0; i<list.size(); i++){
			HSSFRow row = sheet.createRow(i+1);
			ReceiptPaymentDto dto = list.get(i);
			
			if(dto.getFbdt()!=null){
				HSSFCell cell0 = row.createCell(0);
				cell0.setCellValue(sdf.format(dto.getFbdt()));
			}
			
			if(dto.getShipmentId()!=null){
				HSSFCell cell1 = row.createCell(1);
				cell1.setCellValue(dto.getShipmentId());
			}
			
			if(dto.getMailId()!=null){
				HSSFCell cell2 = row.createCell(2);
				cell2.setCellValue(dto.getMailId());
			}
			
			if(dto.getA()!=null){
				HSSFCell cell3 = row.createCell(3);
				cell3.setCellValue(dto.getA());
				cell3.setCellStyle(cellStyle);
			}
			
			if(dto.getB()!=null){
				HSSFCell cell4 = row.createCell(4);
				cell4.setCellValue(dto.getB());
				cell4.setCellStyle(cellStyle);
			}
			
			if(dto.getC()!=null){
				HSSFCell cell5 = row.createCell(5);
				cell5.setCellValue(dto.getC());
				cell5.setCellStyle(cellStyle);
			}
			
			if(dto.getD()!=null){
				HSSFCell cell6 = row.createCell(6);
				cell6.setCellValue(dto.getD());
				cell6.setCellStyle(cellStyle);
			}
			
			if(dto.getE()!=null){
				HSSFCell cell7 = row.createCell(7);
				cell7.setCellValue(dto.getE());
				cell7.setCellStyle(cellStyle);
			}
			
			if(dto.getF()!=null){
				HSSFCell cell8 = row.createCell(8);
				cell8.setCellValue(dto.getF());
				cell8.setCellStyle(cellStyle);
			}
			
			if(dto.getJ()!=null){
				HSSFCell cell9 = row.createCell(9);
				cell9.setCellValue(dto.getJ());
				cell9.setCellStyle(cellStyle);
			}
			
			if(dto.getK()!=null){
				HSSFCell cell10 = row.createCell(10);
				cell10.setCellValue(dto.getK());
				cell10.setCellStyle(cellStyle);
			}

			if(dto.getG()!=null){
				HSSFCell cell11 = row.createCell(11);
				cell11.setCellValue(dto.getG());
				cell11.setCellStyle(cellStyle);
			}
			
			if(dto.getH()!=null){
				HSSFCell cell12 = row.createCell(12);
				cell12.setCellValue(dto.getH());
				cell12.setCellStyle(cellStyle);
			}
			
			if(dto.getI()!=null){
				HSSFCell cell13 = row.createCell(13);
				cell13.setCellValue(dto.getI());
				cell13.setCellStyle(cellStyle);
			}
			
			HSSFCell cell14 = row.createCell(14);
			if(dto.getWeight()!=null){
				cell14.setCellValue(dto.getWeight());
			}else{
				cell14.setCellValue(0);
			}
			cell14.setCellStyle(cellStyle2);
			
		}
		
		//统计
		int num = sheet.getLastRowNum()+1;
		HSSFRow sumRow = sheet.createRow(num);
		
		HSSFCell cell0 = sumRow.createCell(0);
		cell0.setCellValue("统计");
		
		HSSFCell cell3 = sumRow.createCell(3);
		cell3.setCellType(HSSFCell.CELL_TYPE_FORMULA); 
		cell3.setCellFormula("SUM(D2:D" + num + ")");
		cell3.setCellStyle(cellStyle);
		
		HSSFCell cell4 = sumRow.createCell(4);
		cell4.setCellType(HSSFCell.CELL_TYPE_FORMULA); 
		cell4.setCellFormula("SUM(E2:E" + num + ")");
		cell4.setCellStyle(cellStyle);
		
		HSSFCell cell5 = sumRow.createCell(5);
		cell5.setCellType(HSSFCell.CELL_TYPE_FORMULA); 
		cell5.setCellFormula("SUM(F2:F" + num + ")");
		cell5.setCellStyle(cellStyle);
		
		HSSFCell cell6 = sumRow.createCell(6);
		cell6.setCellType(HSSFCell.CELL_TYPE_FORMULA); 
		cell6.setCellFormula("SUM(G2:G" + num + ")");
		cell6.setCellStyle(cellStyle);
		
		HSSFCell cell7 = sumRow.createCell(7);
		cell7.setCellType(HSSFCell.CELL_TYPE_FORMULA); 
		cell7.setCellFormula("SUM(H2:H" + num + ")");
		cell7.setCellStyle(cellStyle);
		
		HSSFCell cell8 = sumRow.createCell(8);
		cell8.setCellType(HSSFCell.CELL_TYPE_FORMULA); 
		cell8.setCellFormula("SUM(I2:I" + num + ")");
		cell8.setCellStyle(cellStyle);
		
		HSSFCell cell9 = sumRow.createCell(9);
		cell9.setCellType(HSSFCell.CELL_TYPE_FORMULA); 
		cell9.setCellFormula("SUM(J2:J" + num + ")");
		cell9.setCellStyle(cellStyle);
		
		HSSFCell cell10 = sumRow.createCell(10);
		cell10.setCellType(HSSFCell.CELL_TYPE_FORMULA); 
		cell10.setCellFormula("SUM(K2:K" + num + ")");
		cell10.setCellStyle(cellStyle);
		
		HSSFCell cell11 = sumRow.createCell(11);
		cell11.setCellType(HSSFCell.CELL_TYPE_FORMULA); 
		cell11.setCellFormula("SUM(L2:L" + num + ")");
		cell11.setCellStyle(cellStyle);
		
		HSSFCell cell12 = sumRow.createCell(12);
		cell12.setCellType(HSSFCell.CELL_TYPE_FORMULA); 
		cell12.setCellFormula("SUM(M2:M" + num + ")");
		cell12.setCellStyle(cellStyle);
		
		HSSFCell cell13 = sumRow.createCell(13);
		cell13.setCellType(HSSFCell.CELL_TYPE_FORMULA); 
		cell13.setCellFormula("SUM(N2:N" + num + ")");
		cell13.setCellStyle(cellStyle);
		
		HSSFCell cell14 = sumRow.createCell(14);
		cell14.setCellType(HSSFCell.CELL_TYPE_FORMULA); 
		cell14.setCellFormula("SUM(O2:O" + num + ")");
		cell14.setCellStyle(cellStyle2);
		
		return wb;
	}

	/**
	 * 通过AssociatedId查找ShipmentHeader
	 * @param id
	 * @return
	 */
	public List<ShipmentHeader> getShipmentByAssociatedId(String id) {
		return shipmentHeaderDao.getShipmentByAssociatedId(id);
	}

	/**
	 * 检查是否可以结算
	 * @param shipmentHeaderId
	 * @return
	 */
	public String accAble(String shipmentHeaderId) {
		String error = null;
		List<Object[]> list = shipmentHeaderDao.accAble(shipmentHeaderId);
		if(list!=null && list.size()>0){
			Object[] obj = list.get(0);
			BigDecimal i = (BigDecimal) obj[0];
			String entityId = (String) obj[1];
			if(i!=null){
				float ifl = i.floatValue();
				if(ifl!=0){
					if(SH_EMS_ID.equals(entityId)){
						if((ifl<-1 || ifl>1)){
							error = "差异额为" + ifl + ",不在上下浮动一元的范围内。";
						}
					}else{
						
					}error = "差异额为" + ifl + ",必须等于零元。";
				}
			}
		}
		return error;
	}

	/**
	 * 生成一个结算
	 * @param id
	 * @param userId
	 * @return
	 */
	@Transactional
	public String paymentCheck(String id, String userId) {
		String msg = null;
		Date date = new Date();
		List<ShipmentHeader> shList = this.getShipmentByAssociatedId(id);
		ShipmentHeader thissh = this.getShipmentById(Long.parseLong(id));
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(thissh.getOrderId());
        paramList.add(userId);
		for(ShipmentHeader sh : shList){
			sh.setReconcilFlag(1);
			sh.setReconcilUser(userId);
			sh.setReconcilDate(date);
			sh.setAccount("Y");
			sh.setAccdt(date);
			Long entityId = Long.parseLong(sh.getEntityId());
			CompanyContract cc = companyContractService.getContractById(entityId.intValue());
			shipmentSettlementService.createShipmentSettlement(sh,cc);
			this.save(sh);
		}
		
        try {
        	if("36".equals(thissh.getEntityId())){
        		if(thissh.getOrderId()!=null){
        			sqlDao.execSql("UPDATE IAGENT.ORDERHIST SET RESULT='2' WHERE ORDERID=?",new Object[]{thissh.getOrderId()});
        	        List<Object> params2 = new ArrayList<Object>();
        	        params2.add(thissh.getOrderId());
        	        params2.add("");
        	        params2.add("");
        			sqlDao.exeStoredProcedure("IAGENT.p_updatemembertype", params2, 0);
        		}
        		sqlDao.exeStoredProcedure("IAGENT.P_N_Conpointfeedback", paramList, 0);
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}

}
