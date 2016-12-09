package com.chinadrtv.erp.oms.controller;


import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.chinadrtv.erp.model.Company;
import com.chinadrtv.erp.model.Warehouse;
import com.chinadrtv.erp.oms.common.DataGridModel;
import com.chinadrtv.erp.oms.dto.OrderhistDto;
import com.chinadrtv.erp.oms.service.CompanyService;
import com.chinadrtv.erp.oms.service.OrderhistService;
import com.chinadrtv.erp.oms.service.WarehouseService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.chinadrtv.erp.util.DateUtil;

/**
 *   订单处理
 * <p/>
 * Created with IntelliJ IDEA.
 * User: haoleitao
 * Date: 12-12-20
 * Time: 上午11:34
 */

@Controller
@RequestMapping("orderhist")
public class OrderhistController {
    @Autowired
    private OrderhistService orderhistService;

    @Autowired
    private CompanyService companyService;
    
    @Autowired
    private WarehouseService warehouseService;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView main(@RequestParam(required = false, defaultValue = "") String from, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        ModelAndView mav = new ModelAndView("orderhist/index");
        List<Company> list = companyService.getAllCompaniesForManual();
        mav.addObject("companyList", list);
        String startDate = DateUtil.addDays(new Date(), -9);
        String endDate = DateUtil.dateToString(new Date());
        mav.addObject("startDate", startDate);
        mav.addObject("endDate", endDate);
        List<Warehouse> warehouse = warehouseService.getAllWarehouses();
        
        mav.addObject("warehouseList", warehouse);
        
        return mav;
    }

    /**
     * 获取订单列表
     *
     * @param orderhistDto
     * @param dataModel
     * @throws IOException
     * @throws JSONException
     * @throws ParseException
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> list(HttpServletRequest request,OrderhistDto orderhistDto,DataGridModel dataModel)
    				throws IOException, JSONException, ParseException {
    	
    	Map<String,Object> result = new HashMap<String, Object>();
    	
    	try{
    		if(!StringUtils.isEmpty(orderhistDto.getStartDate())){
    			long days = DateUtil.getDateDeviations(orderhistDto.getStartDate(), orderhistDto.getEndDate(), "yyyy-MM-dd");
    			if(days>10){
    				result.put("status", "-1");
    				result.put("total", 0);
    				result.put("rows", new ArrayList());
    			}else{
        			if(StringUtils.isEmpty(orderhistDto.getStatus())){
            			orderhistDto.setStatus("1");
            		}
            		if(orderhistDto.getQueryType().equals("shipment")){
            			result = orderhistService.getOrderhistList(orderhistDto, dataModel);
            		}else if(orderhistDto.getQueryType().equals("order")){
            			result = orderhistService.getOrderhistListForOldData(orderhistDto, dataModel);
            		}
            		
            		result.put("status", "1");
        		}
    		}
    		
    		
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	return result;
    }
    
    /**
     * 保存手动选择订单列表
     *
     * @param orderhistDto
     * @param dataModel
     * @throws IOException
     * @throws JSONException
     * @throws ParseException
     */
    @RequestMapping(value = "saveOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> saveOrder(HttpServletRequest request,String idList,String orderList,String statusList,String companyId)
    				throws IOException, JSONException, ParseException {
    	
//    	String companyId = "36";//承运商Id，从session中获取
    	String userId = SecurityHelper.getLoginUser().getUserId();//操作用户id,从session中获取
    	Map<String,String> result = new HashMap<String, String>();
    	if(!StringUtils.isEmpty(orderList)){
    		String[] ids = idList.split(",");
    		String[] orders = orderList.split(",");
    		String[] statusArray = statusList.split(",");
    		int f = 0;
    		long id = 0l;
    		for(int i =0;i<orders.length;i++){
    			id =Long.valueOf(ids[i]);
    			if(id != -1){
    				orderhistService.changeOrderStatus(companyId,Long.valueOf(ids[i]), orders[i], statusArray[i], userId);
    			}else{
    				orderhistService.changeOrderStatus(companyId,orders[i], statusArray[i], userId);
    			}
    		}
    		
    	}
    	result.put("result", "success");
    	return result;
    }
    
    
    /**
     * 手动选择订单_下载订单列表
     *
     * @param orderhistDto
     * @param dataModel
     * @throws IOException
     * @throws JSONException
     * @throws ParseException
     */
    @RequestMapping(value = "download", method = RequestMethod.POST)
    public void download(HttpServletRequest request,HttpServletResponse response,
    		OrderhistDto orderhistDto,DataGridModel dataModel)
    				throws IOException, JSONException, ParseException {
    	
    	if(StringUtils.isEmpty(orderhistDto.getStatus())){
			orderhistDto.setStatus("1");
		}

    	HSSFWorkbook book = null;
    	if(orderhistDto.getQueryType().equals("shipment")){
    		 book = orderhistService.getOrderhistsForDownload(orderhistDto);  
		}else if(orderhistDto.getQueryType().equals("order")){
			 book = orderhistService.getOrderhistsForDownloadForOldData(orderhistDto); 
		}
    	
		
    	  
        if(book!=null){  
            response.setContentType ( "application/ms-excel" ) ;  
            response.setHeader ( "Content-Disposition" ,  
                                 "attachment;filename="+new String("导出Excel.xls".getBytes(),"utf-8")) ;  
            
            book.write(response.getOutputStream());  
        }  
        
    }


    /**
     * 手动选择订单_下载订单列表
     *
     * @param orderhistDto
     * @param dataModel
     * @throws IOException
     * @throws JSONException
     * @throws ParseException
     */
    @RequestMapping(value = "upload")
    public void upload(HttpServletRequest request,HttpServletResponse response,
    		 @RequestParam("fileToUpload") CommonsMultipartFile mFile,String companyId)
    				throws IOException, JSONException, ParseException {
    	
    	Map<String,String> result = new HashMap<String, String>();
    	String error = "";
    	ServletOutputStream out = response.getOutputStream();
    	HSSFWorkbook book = null;
    	 if (!mFile.isEmpty()) {
    		   try {
	    		   String fileName =  mFile.getFileItem().getName(); //将上传的文件写入新建的文件中
	    		   if(fileName==null){
	    			   error = "上传文件不能为空";
	    		   }else if(!fileName.endsWith(".xls")
	    				   && !fileName.endsWith(".xlsx")){
	    			   error = "上传文件不是Excel文件";
	    		   }else{
//		    			String companyId = "36";//承运商Id，从session中获取
		    		    String userId =  SecurityHelper.getLoginUser().getUserId();//操作用户id,从session中获取
		    		    book =  orderhistService.upload(mFile.getInputStream(),companyId,userId);
		    		    
	    		   }
    		   } catch (Exception e) {
    			   error = "上传失败,错误信息"+e.getMessage();
    			   e.printStackTrace();
    		   }
    	 }
    	 
    	// out.print("{\"error\":\""+error+"\",\"msg\":\"success\"}");
    	 if(book!=null){  
             response.setContentType ( "application/ms-excel" ) ;  
             response.setHeader ( "Content-Disposition" ,  
                                  "attachment;filename="+new String("Result.xls".getBytes(),"utf-8")) ;  
             
             book.write(out);
         } 
    	 response.flushBuffer();
    	 if(book==null){
	    	 response.getOutputStream().print("{\"error\":\""+error+"\",\"msg\":\"success\"}");
	    	 response.getOutputStream().flush();
	    	 response.getOutputStream().close();
    	 }
    	 
    }
    
    
    /**
     * 保存手动选择订单列表
     *
     * @param orderhistDto
     * @param dataModel
     * @throws IOException
     * @throws JSONException
     * @throws ParseException
     */
    @RequestMapping(value = "getCompanyList")
    @ResponseBody
    public List<Company> getCompanyList(HttpServletRequest request)
    				throws IOException, JSONException, ParseException {
    	
    	List<Company> list = companyService.getAllCompanies();
    	return list;
    }
    
}