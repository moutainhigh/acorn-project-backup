package com.chinadrtv.erp.oms.service.impl;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.Company;
import com.chinadrtv.erp.model.CompanyConfig;
import com.chinadrtv.erp.model.OrderExt;
import com.chinadrtv.erp.model.Orderhist;
import com.chinadrtv.erp.model.OrderhistAssignLog;
import com.chinadrtv.erp.model.trade.ShipmentHeader;
import com.chinadrtv.erp.oms.common.DataGridModel;
import com.chinadrtv.erp.oms.dao.CompanyConfigDao;
import com.chinadrtv.erp.oms.dao.CompanyDao;
import com.chinadrtv.erp.oms.dao.OrderExtDao;
import com.chinadrtv.erp.oms.dao.OrderhistAssignLogDao;
import com.chinadrtv.erp.oms.dao.OrderhistDao;
import com.chinadrtv.erp.oms.dao.ShipmentHeaderDao;
import com.chinadrtv.erp.oms.dto.OrderhistDto;
import com.chinadrtv.erp.oms.service.OrderhistService;
import com.chinadrtv.erp.oms.service.SourceConfigure;

/**
 * 订单服务
 * @author haoleitao
 * @modify zhaizhanyi
 * @date 2012-12-28 下午4:36:04
 *
 */
@Service("orderhistService")
public class OrderhistServiceImpl implements OrderhistService{

    @Autowired
    private OrderhistDao orderhistDao;
    
    @Autowired
    private OrderExtDao orderExtDao;
    
    @Autowired
    private CompanyConfigDao companyConfigDao;
    
    @Autowired
    private OrderhistAssignLogDao orderhistAssignLogDao;
    
    @Autowired
    private ShipmentHeaderDao shipmentHeaderDao;

    @Autowired
    private RestTemplate template;
    
    @Autowired
    private SourceConfigure sourceConfigure;
    
    @Autowired
    private CompanyDao companyDao;
    
    public void addOrderhist(Orderhist orderhist) {
        orderhistDao.save(orderhist);
    }



    public void saveOrderhist(Orderhist orderhist) {
        orderhistDao.saveOrUpdate(orderhist);
    }

    public void delOrderhist(Long id) {
        //orderhistDao.remove()(id);
    }



	public Orderhist getOrderhistById(String orderhistId) {
		// TODO Auto-generated method stub
        return orderhistDao.getOrderhistById(orderhistId);
	}



	public List<Orderhist> getAllOrderhist(int state,Date beginDate,Date endDate,String orderid,String mailid,String status,String paytype,String mailtype,String ordertype,String result,String companyid,String cardtype,String cityid,int index, int size) {
		// TODO Auto-generated method stub
		return orderhistDao.getAllOrderhist(state, beginDate, endDate, orderid,mailid,status, paytype, mailtype, ordertype, result, companyid, cardtype, cityid, index, size);
	}



	public int getOrderhistCount(int state,Date beginDate,Date endDate,String orderid,String mailid,String status,String paytype,String mailtype,String ordertype,String result,String companyid,String cardtype,String cityid) {
		// TODO Auto-generated method stub
		return orderhistDao.getOrderhistCount(state, beginDate, endDate, orderid,mailid,status, paytype, mailtype, ordertype, result, companyid, cardtype, cityid);
	}



	public void removeOrderhist(Orderhist orderhist) {
		// TODO Auto-generated method stub
		
	}



	/**
	 * 分页查询订单
	 */
	public Map<String, Object> getOrderhistList(OrderhistDto orderhistDto,
			DataGridModel dataModel) {
		Map<String,Object> result = new HashMap<String, Object>();
		List<OrderhistDto> list = orderhistDao.query(orderhistDto, dataModel);
		int count = orderhistDao.queryCount(orderhistDto);
		
		result.put("total", count);
		result.put("rows", list);
		
		return result;
	}
	
	/**
	 * 手工挑单-按运单
	 * @par companyid 承运商id
	 * @param orderId 订单id
	 * @param status 是否可送：1可送2不可送
	 * @param userId 操作用户id
	 */
	public int changeOrderStatus(String companyId,Long id,String orderId,String status,String userId){
		int result = -1;
		ShipmentHeader shipmentHeader = shipmentHeaderDao.getShipmentById(id);
		String reson = "";
		OrderhistAssignLog log = new OrderhistAssignLog();
		if(shipmentHeader==null){
			reson=orderId+"运单不存在";
		}else if(!shipmentHeader.getAccountStatusId().equals("1")){
			reson=orderId+"运单不具备手工挑单状态";
		}else if(!shipmentHeader.getEntityId().equals(companyId)){
			reson=orderId+"运单不属于承运商"+companyId;
		}else{
			
			if(status.equals("1")){//可送货
				Orderhist orderhist = orderhistDao.get(shipmentHeader.getOrderId());
				orderhist.setStatus("2");
				orderhist.setSenddt(new Date());
				orderhist.setIsassign("2");
                orderhist.setMddt(new Date());  //修改日期
				orderhistDao.saveOrUpdate(orderhist);
				
				shipmentHeader.setAccountStatusId("2");
				shipmentHeader.setSenddt(new Date());
				
	            shipmentHeader.setMddt(new Date());//修改日期
				shipmentHeaderDao.saveOrUpdate(shipmentHeader);
				
			}else{
				CompanyConfig companyConfig = companyConfigDao.get(companyId);
				if(companyConfig!=null){
					
					Map<String, Object> params = new HashMap<String, Object>();

		            params.put("orderId", shipmentHeader.getOrderId());
		            params.put("shipmentId", shipmentHeader.getShipmentId());
		            params.put("logisticsStatusId", shipmentHeader.getLogisticsStatusId());
		            params.put("newentityId", companyConfig.getOptionCompanyId());
		            params.put("newwarehouseId",companyConfig.getOptionWareHouseId());
		            params.put("mdusr", userId);
		            send(sourceConfigure.getDeliverIdupUrl(), params);
					
				}else{
					reson="没有找到承运商配置";
				}
			}
			
			result = 1;
		}
		
		log.setOrderId(orderId);
		log.setStatus(status);
		log.setMdDate(new Date());
		log.setUserId(userId);
		log.setRemark(reson);
		orderhistAssignLogDao.saveOrUpdate(log);
		
		return result;
	}
	
	
	public HSSFWorkbook getOrderhistsForDownload(OrderhistDto orderhistDto){
		
		
			//创建新的Excel工作簿
		      HSSFWorkbook workbook = new HSSFWorkbook();
		     
		      //在excel中新建一个工作表，起名字为jsp
		      HSSFSheet sheet = workbook.createSheet("运单");
		     
		    //创建第一行
		      HSSFRow row = sheet.createRow(0);
		      
		      //创建第一列
		      HSSFCell cell = row.createCell((short)0);
		      //定义单元格为字符串类型
		      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		      //在单元格中输入一些内容
		      cell.setCellValue("ID编号");
		      
		      
		      cell = row.createCell((short)1);
		      //定义单元格为字符串类型
		      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		      //在单元格中输入一些内容
		      cell.setCellValue("运单编号");
		      
		      //创建第二列
		      cell = row.createCell((short)2);
		      //定义单元格为字符串类型
		      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		      //在单元格中输入一些内容
		      cell.setCellValue("省");
		      
		      //创建第三列
		      cell = row.createCell((short)3);
		      //定义单元格为字符串类型
		      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		      //在单元格中输入一些内容
		      cell.setCellValue("市");
		      
		    //创建第四列
		      cell = row.createCell((short)4);
		      //定义单元格为字符串类型
		      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		      //在单元格中输入一些内容
		      cell.setCellValue("县");
		      
		    //创建第五列
		      cell = row.createCell((short)5);
		      //定义单元格为字符串类型
		      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		      //在单元格中输入一些内容
		      cell.setCellValue("街道乡镇");
		      
		    //创建第六列
		      cell = row.createCell((short)6);
		      //定义单元格为字符串类型
		      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		      //在单元格中输入一些内容
		      cell.setCellValue("详细地址");
		      
		    //创建第七列
		      cell = row.createCell((short)7);
		      //定义单元格为字符串类型
		      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		      //在单元格中输入一些内容
		      cell.setCellValue("是否可送");
		      List list = orderhistDao.queryOrderhistList(orderhistDto);
				if(!list.isEmpty()){
					 Object[] obj = null;
				      for(int i=0;i<list.size();i++){
				    	  obj =  (Object[])list.get(i);
				    	//创建第一行
					      row = sheet.createRow(i+1);
					      
					      //创建第一列
					      cell = row.createCell((short)0);
					      //定义单元格为字符串类型
					      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					      //在单元格中输入一些内容
					      cell.setCellValue(obj[0]!=null?((BigDecimal)obj[0]).toString():"");
					      
					      //创建第二列
					      cell = row.createCell((short)1);
					      //定义单元格为字符串类型
					      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					      //在单元格中输入一些内容
					      cell.setCellValue(obj[1]!=null?(String)obj[1]:"");
					      
					      //创建第三列
					      cell = row.createCell((short)2);
					      //定义单元格为字符串类型
					      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					      //在单元格中输入一些内容
					      cell.setCellValue(obj[2]!=null?(String)obj[2]:"");
					      
					    //创建第四列
					      cell = row.createCell((short)3);
					      //定义单元格为字符串类型
					      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					      //在单元格中输入一些内容
					      cell.setCellValue(obj[3]!=null?(String)obj[3]:"");
					      
					    //创建第五列
					      cell = row.createCell((short)4);
					      //定义单元格为字符串类型
					      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					      //在单元格中输入一些内容
					      cell.setCellValue(obj[4]!=null?(String)obj[4]:"");
					      
					    //创建第六列
					      cell = row.createCell((short)5);
					      //定义单元格为字符串类型
					      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					      //在单元格中输入一些内容
					      cell.setCellValue(obj[5]!=null?(String)obj[5]:"");
					      
					    //创建第六列
					      cell = row.createCell((short)6);
					      //定义单元格为字符串类型
					      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					      //在单元格中输入一些内容
					      cell.setCellValue(obj[6]!=null?(String)obj[6]:"");
				      }
				}
	     
				CellRangeAddressList regions = new CellRangeAddressList(1, 65535, 7, 7);  //excel下拉菜单作用域，例子中是第12列(从0开始)  
		          String [] selectlist= {"可送","不可送"}; ;  
		           DVConstraint constraint = DVConstraint.createExplicitListConstraint(selectlist); //将list中的内容写入到excel中  
		       HSSFDataValidation data_validation = new HSSFDataValidation(regions,constraint); //绑定下拉菜单和作用域  
		       sheet.addValidationData(data_validation); //对sheet生效  
		       
				return workbook;
	}
	
	
	public HSSFWorkbook getOrderhistsForDownloadNew(OrderhistDto orderhistDto){
		
		
		//创建新的Excel工作簿
	      HSSFWorkbook workbook = new HSSFWorkbook();
	     
	      //在excel中新建一个工作表，起名字为jsp
	      HSSFSheet sheet = workbook.createSheet("运单");
	     
	    //创建第一行
	      HSSFRow row = sheet.createRow(0);
	      
	      //创建第一列
	      HSSFCell cell = row.createCell((short)0);
	      //定义单元格为字符串类型
	      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	      //在单元格中输入一些内容
	      cell.setCellValue("ID编号");
	      
	      
	      cell = row.createCell((short)1);
	      //定义单元格为字符串类型
	      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	      //在单元格中输入一些内容
	      cell.setCellValue("运单编号");
	      
	      //创建第二列
	      cell = row.createCell((short)2);
	      //定义单元格为字符串类型
	      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	      //在单元格中输入一些内容
	      cell.setCellValue("省");
	      
	      //创建第三列
	      cell = row.createCell((short)3);
	      //定义单元格为字符串类型
	      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	      //在单元格中输入一些内容
	      cell.setCellValue("市");
	      
	    //创建第四列
	      cell = row.createCell((short)4);
	      //定义单元格为字符串类型
	      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	      //在单元格中输入一些内容
	      cell.setCellValue("县");
	      
	    //创建第五列
	      cell = row.createCell((short)5);
	      //定义单元格为字符串类型
	      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	      //在单元格中输入一些内容
	      cell.setCellValue("街道乡镇");
	      
	    //创建第六列
	      cell = row.createCell((short)6);
	      //定义单元格为字符串类型
	      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	      //在单元格中输入一些内容
	      cell.setCellValue("详细地址");
	      

	      cell = row.createCell((short)7);
	      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	      cell.setCellValue("订单总金额");
	      
	      cell = row.createCell((short)8);
	      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	      cell.setCellValue("商品明细");
	      
	      cell = row.createCell((short)9);
	      //定义单元格为字符串类型
	      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	      //在单元格中输入一些内容
	      cell.setCellValue("是否可送");
	      
	      
	      List list = orderhistDao.queryOrderhistList(orderhistDto);
			if(!list.isEmpty()){
				 Object[] obj = null;
			      for(int i=0;i<list.size();i++){
			    	  obj =  (Object[])list.get(i);
			    	//创建第一行
				      row = sheet.createRow(i+1);
				      
				      //创建第一列
				      cell = row.createCell((short)0);
				      //定义单元格为字符串类型
				      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				      //在单元格中输入一些内容
				      cell.setCellValue(obj[0]!=null?((BigDecimal)obj[0]).toString():"");
				      
				      //创建第二列
				      cell = row.createCell((short)1);
				      //定义单元格为字符串类型
				      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				      //在单元格中输入一些内容
				      cell.setCellValue(obj[1]!=null?(String)obj[1]:"");
				      
				      //创建第三列
				      cell = row.createCell((short)2);
				      //定义单元格为字符串类型
				      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				      //在单元格中输入一些内容
				      cell.setCellValue(obj[2]!=null?(String)obj[2]:"");
				      
				    //创建第四列
				      cell = row.createCell((short)3);
				      //定义单元格为字符串类型
				      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				      //在单元格中输入一些内容
				      cell.setCellValue(obj[3]!=null?(String)obj[3]:"");
				      
				    //创建第五列
				      cell = row.createCell((short)4);
				      //定义单元格为字符串类型
				      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				      //在单元格中输入一些内容
				      cell.setCellValue(obj[4]!=null?(String)obj[4]:"");
				      
				    //创建第六列
				      cell = row.createCell((short)5);
				      //定义单元格为字符串类型
				      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				      //在单元格中输入一些内容
				      cell.setCellValue(obj[5]!=null?(String)obj[5]:"");
				      
				    //创建第六列
				      cell = row.createCell((short)6);
				      //定义单元格为字符串类型
				      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				      //在单元格中输入一些内容
				      cell.setCellValue(obj[6]!=null?(String)obj[6]:"");
				      
				      BigDecimal totalPrice = new BigDecimal(null == obj[8] ? "0" : obj[8].toString());
				      DecimalFormat df = new DecimalFormat("###.00");
				      
				      cell = row.createCell((short)7);
				      cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				      cell.setCellValue(df.format(totalPrice));
				      
				      cell = row.createCell((short)8);
				      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				      cell.setCellValue(obj[9] == null ? "0" : String.valueOf(obj[9]));
			      }
			}
     
		CellRangeAddressList regions = new CellRangeAddressList(1, 65535, 9, 9); // excel下拉菜单作用域，例子中是第12列(从0开始)
		String[] selectlist = { "可送", "不可送" };
		DVConstraint constraint = DVConstraint.createExplicitListConstraint(selectlist); // 将list中的内容写入到excel中
		HSSFDataValidation data_validation = new HSSFDataValidation(regions, constraint); // 绑定下拉菜单和作用域
		sheet.addValidationData(data_validation); // 对sheet生效
	       
		return workbook;
	}
	
	/**
	 * 处理上传手工挑单文件
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public HSSFWorkbook upload(InputStream is,String companyId,String userId) throws Exception{
		
		
		//创建新的Excel工作簿
	      HSSFWorkbook workbook = new HSSFWorkbook(is);
	     
	      int sheetNum=workbook.getNumberOfSheets();  
          
	      String orderId = "";
	      Long id=0l;
	      String status = "";
	      int result = 0;
          for(int i=0;i<sheetNum;i++)  {  
              HSSFSheet childSheet = workbook.getSheetAt(i);  
              int rowNum = childSheet.getLastRowNum();  
                
              for(int j=1;j<=rowNum;j++)  
              {  
                  HSSFRow row = childSheet.getRow(j);   
                  int cellNum=row.getLastCellNum();
                  id = Long.valueOf(row.getCell(0).toString());
                  orderId = row.getCell(1).toString();
                  status = row.getCell(7)!=null?row.getCell(7).toString():"";
                 
                  if(!StringUtils.isEmpty(status)){
                	  if(status.equals("可送")){
                    	  status = "1";
                      }else if(status.equals("不可送")){
                    	  status = "2";
                      }
//                	  result =  changeOrderStatus(companyId,id, orderId, status, userId);
                	  
	                	if(id != -1){//按运单
	                		result = changeOrderStatus(companyId,id, orderId, status, userId);
		          		}else{//按订单
		          			result = changeOrderStatus(companyId,orderId, status, userId);
		          		}
	                	
	                    if(result!=-1)  {
	                    	row.createCell(8).setCellValue("成功");
	                    }else{
	                    	row.createCell(8).setCellValue("失败");
	                    }
//	                    else{
//	                    	result = changeOrderStatus(companyId, orderId, status, userId);
//		                    if(result!=-1){  
//		                    	row.createCell(8).setCellValue("成功");
//		                    }else{
//		                    	row.createCell(8).setCellValue("失败");
//		                    }
//	                    }
                  }else{
                	  row.createCell(8).setCellValue("失败");
                  }
                  
              }  
                
          }  
		
          return workbook;
	}
	
	
	/**
	 * 处理上传手工挑单文件
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public HSSFWorkbook uploadNew(InputStream is,String companyId,String userId) throws Exception{
		
		
		//创建新的Excel工作簿
	      HSSFWorkbook workbook = new HSSFWorkbook(is);
	     
	      int sheetNum=workbook.getNumberOfSheets();  
          
	      String orderId = "";
	      Long id=0l;
	      String status = "";
	      int result = 0;
          for(int i=0;i<sheetNum;i++)  {  
              HSSFSheet childSheet = workbook.getSheetAt(i);  
              int rowNum = childSheet.getLastRowNum();  
                
              for(int j=1;j<=rowNum;j++)  
              {  
                  HSSFRow row = childSheet.getRow(j);   
                  int cellNum=row.getLastCellNum();
                  id = Long.valueOf(row.getCell(0).toString());
                  orderId = row.getCell(1).toString();
                  status = row.getCell(9)!=null?row.getCell(9).toString():"";
                 
                  if(!StringUtils.isEmpty(status)){
                	  if(status.equals("可送")){
                    	  status = "1";
                      }else if(status.equals("不可送")){
                    	  status = "2";
                      }
//                	  result =  changeOrderStatus(companyId,id, orderId, status, userId);
                	  
	                	if(id != -1){//按运单
	                		result = changeOrderStatus(companyId,id, orderId, status, userId);
		          		}else{//按订单
		          			result = changeOrderStatus(companyId,orderId, status, userId);
		          		}
	                	
	                    if(result!=-1)  {
	                    	row.createCell(10).setCellValue("成功");
	                    }else{
	                    	row.createCell(10).setCellValue("失败");
	                    }
//	                    else{
//	                    	result = changeOrderStatus(companyId, orderId, status, userId);
//		                    if(result!=-1){  
//		                    	row.createCell(8).setCellValue("成功");
//		                    }else{
//		                    	row.createCell(8).setCellValue("失败");
//		                    }
//	                    }
                  }else{
                	  row.createCell(10).setCellValue("失败");
                  }
                  
              }  
                
          }  
		
          return workbook;
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
       if(resultMap.get("code").equals("100")){
            throw new RuntimeException((String)resultMap.get("100"));
       }
   }
   


	/**
	* <p>Title: getOrderhistListForOldData</p>
	* <p>Description: </p>
	* @param orderhistDto
	* @param dataModel
	* @return
	* @see com.chinadrtv.erp.oms.service.OrderhistService#getOrderhistListForOldData(com.chinadrtv.erp.oms.dto.OrderhistDto, com.chinadrtv.erp.oms.common.DataGridModel)
	*/ 
	public Map<String, Object> getOrderhistListForOldData(
			OrderhistDto orderhistDto, DataGridModel dataModel) {
		Map<String,Object> result = new HashMap<String, Object>();
		List<OrderhistDto> list = orderhistDao.queryForOldData(orderhistDto, dataModel);
		int count = orderhistDao.queryCountForOldData(orderhistDto);
		
		result.put("total", count);
		result.put("rows", list);
		
		return result;
	}



	/**
	* <p>Title: changeOrderStatusForOldData</p>
	* <p>Description:手工挑单-按订单 </p>
	* @param companyId
	* @param id
	* @param orderId
	* @param status
	* @param userId
	* @return
	* @see com.chinadrtv.erp.oms.service.OrderhistService#changeOrderStatusForOldData(java.lang.String, java.lang.Long, java.lang.String, java.lang.String, java.lang.String)
	*/ 
	public int changeOrderStatus(String companyId,String orderId,String status,String userId){
			int result = -1;
			Orderhist orderhist = orderhistDao.get(orderId);
			String reson = "";
			OrderhistAssignLog log = new OrderhistAssignLog();
			if(orderhist==null){
				reson=orderId+"订单不存在";
			}else if(!orderhist.getStatus().equals("1")){
				reson=orderId+"订单不具备手工挑单状态";
			}else if(!orderhist.getEntityid().equals(companyId)){
				reson=orderId+"订单不属于承运商"+companyId;
			}else{
				
				if(status.equals("1")){//可送货
					orderhist.setStatus("2");
					orderhist.setSenddt(new Date());
					
				}else{
					CompanyConfig companyConfig = companyConfigDao.get(companyId);
					if(companyConfig!=null){
						Company company = companyDao.get(companyId);
						OrderExt orderExt = orderExtDao.get(orderId);
						if(orderExt == null){
							reson=orderId+"没有找到分拣信息";
						}else{
							orderExt.setWarehouseIdExt(companyConfig.getOptionWareHouseId());
							orderExt.setWarehouseNameExt(companyConfig.getOptionWareHouseName());
							orderExtDao.saveOrUpdate(orderExt);
						}
						log.setCurr_companyId(companyConfig.getOptionCompanyId());
						log.setCurr_warehouseId(companyConfig.getOptionWareHouseId());
						
						orderhist.setEntityid(companyConfig.getOptionCompanyId());
						String newCompanyId = companyConfig.getOptionCompanyId();
						Company newCompany = companyDao.getCompanyById(newCompanyId);
						orderhist.setMailtype(newCompany.getMailtype());
					}else{
						reson="没有找到承运商配置";
					}
				}
				
				orderhist.setIsassign("2");
                orderhist.setMddt(new Date());//修改日期
				orderhistDao.saveOrUpdate(orderhist);
				result = 1;
				
			}
			
			log.setOrderId(orderId);
			log.setStatus(status);
			log.setMdDate(new Date());
			log.setUserId(userId);
			log.setRemark(reson);
			orderhistAssignLogDao.saveOrUpdate(log);
			
			return result;
	}



	/**
	* <p>Title: getOrderhistsForDownloadForOldData</p>
	* <p>Description: </p>
	* @param orderhistDto
	* @return
	* @see com.chinadrtv.erp.oms.service.OrderhistService#getOrderhistsForDownloadForOldData(com.chinadrtv.erp.oms.dto.OrderhistDto)
	*/ 
	public HSSFWorkbook getOrderhistsForDownloadForOldData(
			OrderhistDto orderhistDto) {

		//创建新的Excel工作簿
	      HSSFWorkbook workbook = new HSSFWorkbook();
	     
	      //在excel中新建一个工作表，起名字为jsp
	      HSSFSheet sheet = workbook.createSheet("运单");
	     
	    //创建第一行
	      HSSFRow row = sheet.createRow(0);
	      
	      //创建第一列
	      HSSFCell cell = row.createCell((short)0);
	      //定义单元格为字符串类型
	      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	      //在单元格中输入一些内容
	      cell.setCellValue("ID编号");
	      
	      
	      cell = row.createCell((short)1);
	      //定义单元格为字符串类型
	      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	      //在单元格中输入一些内容
	      cell.setCellValue("订单编号");
	      
	      //创建第二列
	      cell = row.createCell((short)2);
	      //定义单元格为字符串类型
	      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	      //在单元格中输入一些内容
	      cell.setCellValue("省");
	      
	      //创建第三列
	      cell = row.createCell((short)3);
	      //定义单元格为字符串类型
	      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	      //在单元格中输入一些内容
	      cell.setCellValue("市");
	      
	    //创建第四列
	      cell = row.createCell((short)4);
	      //定义单元格为字符串类型
	      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	      //在单元格中输入一些内容
	      cell.setCellValue("县");
	      
	    //创建第五列
	      cell = row.createCell((short)5);
	      //定义单元格为字符串类型
	      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	      //在单元格中输入一些内容
	      cell.setCellValue("街道乡镇");
	      
	    //创建第六列
	      cell = row.createCell((short)6);
	      //定义单元格为字符串类型
	      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	      //在单元格中输入一些内容
	      cell.setCellValue("详细地址");
	      
	    //创建第七列
	      cell = row.createCell((short)7);
	      //定义单元格为字符串类型
	      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	      //在单元格中输入一些内容
	      cell.setCellValue("是否可送");
	      List list = orderhistDao.queryOrderhistListForOldData(orderhistDto);
			if(!list.isEmpty()){
				 Object[] obj = null;
			      for(int i=0;i<list.size();i++){
			    	  obj =  (Object[])list.get(i);
			    	//创建第一行
				      row = sheet.createRow(i+1);
				      
				      //创建第一列
				      cell = row.createCell((short)0);
				      //定义单元格为字符串类型
				      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				      //在单元格中输入一些内容
				      cell.setCellValue("-1");
				      
				      //创建第二列
				      cell = row.createCell((short)1);
				      //定义单元格为字符串类型
				      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				      //在单元格中输入一些内容
				      cell.setCellValue(obj[1]!=null?(String)obj[1]:"");
				      
				      //创建第三列
				      cell = row.createCell((short)2);
				      //定义单元格为字符串类型
				      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				      //在单元格中输入一些内容
				      cell.setCellValue(obj[2]!=null?(String)obj[2]:"");
				      
				    //创建第四列
				      cell = row.createCell((short)3);
				      //定义单元格为字符串类型
				      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				      //在单元格中输入一些内容
				      cell.setCellValue(obj[3]!=null?(String)obj[3]:"");
				      
				    //创建第五列
				      cell = row.createCell((short)4);
				      //定义单元格为字符串类型
				      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				      //在单元格中输入一些内容
				      cell.setCellValue(obj[4]!=null?(String)obj[4]:"");
				      
				    //创建第六列
				      cell = row.createCell((short)5);
				      //定义单元格为字符串类型
				      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				      //在单元格中输入一些内容
				      cell.setCellValue(obj[5]!=null?(String)obj[5]:"");
				      
				    //创建第六列
				      cell = row.createCell((short)6);
				      //定义单元格为字符串类型
				      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				      //在单元格中输入一些内容
				      cell.setCellValue(obj[6]!=null?(String)obj[6]:"");
			      }
			}
     
			CellRangeAddressList regions = new CellRangeAddressList(1, 65535, 7, 7);  //excel下拉菜单作用域，例子中是第12列(从0开始)  
	          String [] selectlist= {"可送","不可送"}; ;  
	           DVConstraint constraint = DVConstraint.createExplicitListConstraint(selectlist); //将list中的内容写入到excel中  
	       HSSFDataValidation data_validation = new HSSFDataValidation(regions,constraint); //绑定下拉菜单和作用域  
	       sheet.addValidationData(data_validation); //对sheet生效  
	       
			return workbook;
	}



	/**
	* <p>Title: uploadForOldData</p>
	* <p>Description: </p>
	* @param is
	* @param companyId
	* @param userId
	* @return
	* @throws Exception
	* @see com.chinadrtv.erp.oms.service.OrderhistService#uploadForOldData(java.io.InputStream, java.lang.String, java.lang.String)
	*/ 
	public HSSFWorkbook uploadForOldData(InputStream is, String companyId,
			String userId) throws Exception {

		//创建新的Excel工作簿
	      HSSFWorkbook workbook = new HSSFWorkbook(is);
	     
	      int sheetNum=workbook.getNumberOfSheets();  
          
	      String orderId = "";
	      String status = "";
          for(int i=0;i<sheetNum;i++)  {  
              HSSFSheet childSheet = workbook.getSheetAt(i);  
              int rowNum = childSheet.getLastRowNum();  
                
              for(int j=1;j<=rowNum;j++)  
              {  
                  HSSFRow row = childSheet.getRow(j);   
                  int cellNum=row.getLastCellNum();  
                  orderId = row.getCell(0).toString();
                  status = row.getCell(6)!=null?row.getCell(6).toString():"";
                 
                  if(!StringUtils.isEmpty(status)){
                	  if(status.equals("可送")){
                    	  status = "1";
                      }else if(status.equals("不可送")){
                    	  status = "2";
                      }
                	  int result = changeOrderStatus(companyId, orderId, status, userId);
                      if(result!=-1)  
                      {  
                    	  row.createCell(7).setCellValue("成功");
                      }else{
                    	  row.createCell(7).setCellValue("失败");
                      }
                  }else{
                	  row.createCell(7).setCellValue("失败");
                  }
                  
              }  
                
          }  
		
          return workbook;
	}




    /**
     * 根据收货人信息，查找相关订单
     * @param getContactId
     * @return
     */
    public List<Orderhist> getOrderhistFromGetContact(String getContactId)
    {
        return orderhistDao.findList("from com.chinadrtv.erp.model.Orderhist where getcontact.contactid=:contactId and status=2",new ParameterString("contactId",getContactId));
    }

    public Orderhist getOrderFromMailId(String mailId)
    {
        return orderhistDao.find("from com.chinadrtv.erp.model.Orderhist where mailid=:mailid", new ParameterString("mailid",mailId));
    }



	public String getCompanyNameByOrderId(String orderId) {
		return companyDao.getCompanyNameByID(orderhistDao.getOrderhistById(orderId).getEntityid());
	}
}
