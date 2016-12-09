package com.chinadrtv.erp.oms.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chinadrtv.erp.model.Company;
import com.chinadrtv.erp.model.Warehouse;
import com.chinadrtv.erp.model.agent.Province;
import com.chinadrtv.erp.oms.dto.ReceiptPaymentDto;
import com.chinadrtv.erp.oms.service.CompanyService;
import com.chinadrtv.erp.oms.service.ProvinceService;
import com.chinadrtv.erp.oms.service.ShipmentHeaderService;
import com.chinadrtv.erp.oms.service.SystemConfigure;
import com.chinadrtv.erp.oms.service.WarehouseService;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-4-8
 * Time: 下午1:48
 * To change this template use File | Settings | File Templates.
 * 统计报表--核帐管理
 */
@Controller
@RequestMapping("report")
public class ReportController {
    
    @Autowired
    private ShipmentHeaderService shipmentHeaderService;
    
    @Autowired
    private CompanyService companyService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private ProvinceService provinceService;
    @Autowired
    private SystemConfigure systemConfigure;

    //茶马订单报表
    @RequestMapping(value = "orderChama", method = RequestMethod.GET)
    public ModelAndView orderChama(@RequestParam(required = false, defaultValue = "") String from, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        ModelAndView mav = new ModelAndView("report/orderChama");

        String reportServerUrl = systemConfigure.getReportServerUrl();
        mav.addObject("reportServerUrl", reportServerUrl);

        AgentUser user = SecurityHelper.getLoginUser();
        mav.addObject("r_username", user.getUserId());
        mav.addObject("r_password", user.getPassword());
        return mav;
    }
    //发包清单查询
    @RequestMapping(value = "sendPackageInventory", method = RequestMethod.GET)
    public ModelAndView sendPackageInventory(@RequestParam(required = false, defaultValue = "") String from, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        ModelAndView mav = new ModelAndView("report/sendPackageInventory");
        List<Company> list = companyService.getAllCompanies();
        List<Warehouse> wList = warehouseService.getAllWarehouses();
        String reportServerUrl = systemConfigure.getReportServerUrl();
        mav.addObject("companyList", list);
        mav.addObject("warehouseList",wList);
        mav.addObject("reportServerUrl",reportServerUrl);
        AgentUser user = SecurityHelper.getLoginUser();
        mav.addObject("r_username", user.getUserId());
        mav.addObject("r_password", user.getPassword());
        return mav;
    }
    //退包清单查询
    @RequestMapping(value = "dishonourOrderList", method = RequestMethod.GET)
    public ModelAndView dishonourOrderList(@RequestParam(required = false, defaultValue = "") String from, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        ModelAndView mav = new ModelAndView("report/dishonourOrderList");
        List<Company> cList = companyService.getAllCompanies();
        List<Warehouse> wList = warehouseService.getAllWarehouses();
        String reportServerUrl = systemConfigure.getReportServerUrl();
        mav.addObject("companyList",cList);
        mav.addObject("warehouseList",wList);
        mav.addObject("reportServerUrl", reportServerUrl);
        AgentUser user = SecurityHelper.getLoginUser();
        mav.addObject("r_username", user.getUserId());
        mav.addObject("r_password", user.getPassword());
        return mav;
    }
    //发包日报核对表
    @RequestMapping(value = "contractDailyCheck", method = RequestMethod.GET)
    public ModelAndView contractDailyCheck(@RequestParam(required = false, defaultValue = "") String from, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        ModelAndView mav = new ModelAndView("report/contractDailyCheck");
        List<Company> cList = companyService.getAllCompanies();
        List<Warehouse> wList = warehouseService.getAllWarehouses();
        String reportServerUrl = systemConfigure.getReportServerUrl();
        mav.addObject("companyList",cList);
        mav.addObject("warehouseList",wList);
        mav.addObject("reportServerUrl", reportServerUrl);
        AgentUser user = SecurityHelper.getLoginUser();
        mav.addObject("r_username", user.getUserId());
        mav.addObject("r_password", user.getPassword());
        return mav;
    }
    //拒收退包日报核对表
    @RequestMapping(value = "dishonourDailyCheck", method = RequestMethod.GET)
    public ModelAndView dishonourDailyCheck(@RequestParam(required = false, defaultValue = "") String from, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        ModelAndView mav = new ModelAndView("report/dishonourDailyCheck");
        List<Company> cList = companyService.getAllCompanies();
        List<Warehouse> wList = warehouseService.getAllWarehouses();
        String reportServerUrl = systemConfigure.getReportServerUrl();
        mav.addObject("companyList",cList);
        mav.addObject("warehouseList",wList);
        mav.addObject("reportServerUrl", reportServerUrl);
        AgentUser user = SecurityHelper.getLoginUser();
        mav.addObject("r_username", user.getUserId());
        mav.addObject("r_password", user.getPassword());
        return mav;
    }
    /**
     *运费对账报表
     * @param from
     * @param request
     * @param response
     * @param model
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "freightCheck", method = RequestMethod.GET)
    public ModelAndView freightCheck(@RequestParam(required = false, defaultValue = "") String from, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        ModelAndView mav = new ModelAndView("report/freightCheck");
        List<Company> cList = companyService.getAllCompanies();
        String reportServerUrl = systemConfigure.getReportServerUrl();
        mav.addObject("companyList",cList);
        mav.addObject("reportServerUrl", reportServerUrl);
        AgentUser user = SecurityHelper.getLoginUser();
        mav.addObject("r_username", user.getUserId());
        mav.addObject("r_password", user.getPassword());
        return mav;
    }

    /**
     * 订单详细信息查询报表
     * @param from
     * @param request
     * @param response
     * @param model
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "orderDetailsSearch", method = RequestMethod.GET)
    public ModelAndView orderDetailsSearch(@RequestParam(required = false, defaultValue = "") String from, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        ModelAndView mav = new ModelAndView("report/orderDetailsSearch");
        List<Company> cList = companyService.getAllCompanies();
        String reportServerUrl = systemConfigure.getReportServerUrl();
        mav.addObject("companyList",cList);
        mav.addObject("reportServerUrl", reportServerUrl);
        AgentUser user = SecurityHelper.getLoginUser();
        mav.addObject("r_username", user.getUserId());
        mav.addObject("r_password", user.getPassword());
        return mav;
    }

    /**
     * 半拒收免运费报表
     * @param from
     * @param request
     * @param response
     * @param model
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "rejectionAndPostfee", method = RequestMethod.GET)
    public ModelAndView rejectionAndPostfee(@RequestParam(required = false, defaultValue = "") String from, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        ModelAndView mav = new ModelAndView("report/rejectionAndPostfee");
        List<Company> cList = companyService.getAllCompanies();
        List<Province> provinceList = provinceService.getAllProvinces();
        String reportServerUrl = systemConfigure.getReportServerUrl();
        mav.addObject("companyList",cList);
        mav.addObject("provinceList",provinceList);
        mav.addObject("reportServerUrl", reportServerUrl);
        AgentUser user = SecurityHelper.getLoginUser();
        mav.addObject("r_username", user.getUserId());
        mav.addObject("r_password", user.getPassword());
        return mav;
    }

    /**
     * 返款报表
     * @param from
     * @param request
     * @param response
     * @param model
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "rebates", method = RequestMethod.GET)
    public ModelAndView rebates(@RequestParam(required = false, defaultValue = "") String from, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        ModelAndView mav = new ModelAndView("report/rebates");
        String reportServerUrl = systemConfigure.getReportServerUrl();
        mav.addObject("reportServerUrl", reportServerUrl);
        AgentUser user = SecurityHelper.getLoginUser();
        mav.addObject("r_username", user.getUserId());
        mav.addObject("r_password", user.getPassword());
        return mav;
    }
    //应收应付款核对查询
    @RequestMapping(value = "paymentCheck", method = RequestMethod.GET)
    public ModelAndView paymentCheck(@RequestParam(required = false, defaultValue = "") String from, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        ModelAndView mav = new ModelAndView("report/paymentCheck");
        mav.addObject("url", "/report/payment/load");
        return mav;
    }
    
    /**
     * 加载
     * @param page
     * @param rows
     * @param shipmentId
     * @param mailId
     * @param entityId
     * @param warehouseId
     * @param fbDtStrS
     * @param fbDtStrE
     * @param exclude
     * @param accounted
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/payment/load", method = RequestMethod.POST)
    public String load(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam Integer rows,
            @RequestParam(required = false, defaultValue="") String shipmentId,
            @RequestParam(required = false, defaultValue="") String mailId,
            @RequestParam(required = false, defaultValue="") String entityId,
            @RequestParam(required = false, defaultValue="") String warehouseId,
            @RequestParam(required = false, defaultValue="") String fbDtStrS,
            @RequestParam(required = false, defaultValue="") String fbDtStrE,
            @RequestParam(required = false, defaultValue="") boolean exclude,
            @RequestParam(required = false, defaultValue="") boolean accounted,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
    	
    	if("".equals(shipmentId) && "".equals(mailId) && "".equals(entityId) && "".equals(fbDtStrS) && "".equals(fbDtStrE)){
    		return null;
    	}
    	
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(shipmentId!=null && !shipmentId.equals("")){
			if(shipmentId.indexOf("V")>0){
				shipmentId += "%";
			}else{
				shipmentId += "V%";
			}
			params.put("shipmentId", shipmentId);
		}
		if(mailId!=null && !mailId.equals("")){
			params.put("mailId", mailId);
		}
		if(entityId!=null && !entityId.equals("")){
			params.put("entityId", entityId);
		}
		if(warehouseId!=null && !warehouseId.equals("")){
			params.put("warehouseId", warehouseId);
		}
		if(fbDtStrS!=null && !fbDtStrS.equals("")){
			Date fbDtS = dateFormat.parse(fbDtStrS);
			params.put("fbDtS", fbDtS);
		}
		if(fbDtStrE!=null && !fbDtStrS.equals("")){
			Date accDtE = dateFormat.parse(fbDtStrE);
			Calendar cal = Calendar.getInstance();
			cal.setTime(accDtE);
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			params.put("fbDtE", cal.getTime());
		}
		
		params.put("exclude", exclude);
		params.put("accounted", accounted);

        Long totalRecords = shipmentHeaderService.getReceiptPaymentCount(params);
        List<ReceiptPaymentDto> list = shipmentHeaderService.getReceiptPayment(params,(page - 1) * rows, rows);
        if(list != null){
            String jsonData = "{\"rows\":" +gson.toJson(list) + ",\"total\":" + totalRecords + " }";
            response.setContentType("text/json; charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().print(jsonData);
        }
        return null;
    }
    
	/**
	 * 导出
	 * @Description: 
	 * @return void
	 * @throws IOException
	 */
	@RequestMapping(value = "/export")
	public void export(@RequestParam(required = false, defaultValue="") String shipmentId,
            @RequestParam(required = false, defaultValue="") String mailId,
            @RequestParam(required = false, defaultValue="") String entityId,
            @RequestParam(required = false, defaultValue="") String warehouseId,
            @RequestParam(required = false, defaultValue="") String fbDtStrS,
            @RequestParam(required = false, defaultValue="") String fbDtStrE, 
            @RequestParam(required = false, defaultValue="") boolean exclude,
            @RequestParam(required = false, defaultValue="") boolean accounted,
            HttpServletResponse response) throws Exception{
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(shipmentId!=null && !shipmentId.equals("")){
			if(shipmentId.indexOf("V")>0){
				shipmentId += "%";
			}else{
				shipmentId += "V%";
			}
			params.put("shipmentId", shipmentId);
		}
		if(mailId!=null && !mailId.equals("")){
			params.put("mailId", mailId);
		}
		if(entityId!=null && !entityId.equals("")){
			params.put("entityId", entityId);
		}
		if(warehouseId!=null && !warehouseId.equals("")){
			params.put("warehouseId", warehouseId);
		}
		if(fbDtStrS!=null && !fbDtStrS.equals("")){
			Date fbDtS = dateFormat.parse(fbDtStrS);
			params.put("fbDtS", fbDtS);
		}
		if(fbDtStrE!=null && !fbDtStrE.equals("")){
			Date fbDtE = dateFormat.parse(fbDtStrE);
			Calendar cal = Calendar.getInstance();
			cal.setTime(fbDtE);
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			params.put("fbDtE", cal.getTime());
		}
		
		params.put("exclude", exclude);
		params.put("accounted", accounted);
		
		HSSFWorkbook wb = null;
		try {
			wb = shipmentHeaderService.export(params);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (wb != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String fileName = "Report" +sdf.format(new Date()) + ".xls";
			response.setContentType("application/ms-excel");
			response.setHeader("Content-Disposition", "attachment;filename="+ fileName);

			wb.write(response.getOutputStream());
			response.getOutputStream().flush();
		}
	}
	
    @RequestMapping(value = "/payment/createId", method = RequestMethod.POST)
    public ModelAndView load(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	String[] ids = request.getParameterValues("ids[]");
    	String[] shipmentIds = request.getParameterValues("shipmentIds[]");
    	AgentUser agentUser = SecurityHelper.getLoginUser();
    	String msg = "批号生成成功！";
    	if(ids!=null && ids.length>0){
    		String msgDtal = "";
    		for(int i=0;i<ids.length;i++){
    			String error = shipmentHeaderService.accAble(ids[i]);
    			if(error==null){
    				shipmentHeaderService.paymentCheck(ids[i],agentUser.getUserId());
    			}else{
    				msgDtal += shipmentIds[i] + error;
    			}
    		}
    		if(msgDtal!=null && !"".equals(msgDtal)){
    			msg = "部分订单无法生成结算:" + msgDtal;
    		}
    	}
        response.setContentType("text/plain; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().print("{status:1,message:'" + msg + "'}");
    	return null;
    }

    //入库作业绩效
    @RequestMapping(value = "inBoundPerformance", method = RequestMethod.GET)
    public ModelAndView inBoundPerformance(@RequestParam(required = false, defaultValue = "") String from, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        ModelAndView mav = new ModelAndView("report/inBoundPerformance");

        List<Warehouse> wList = warehouseService.getAllWarehouses();
        String reportServerUrl = systemConfigure.getReportServerUrl();

        mav.addObject("warehouseList",wList);
        mav.addObject("reportServerUrl", reportServerUrl);
        AgentUser user = SecurityHelper.getLoginUser();
        mav.addObject("r_username", user.getUserId());
        mav.addObject("r_password", user.getPassword());
        return mav;
    }
    //出库作业绩效
    @RequestMapping(value = "outBoundPerformance", method = RequestMethod.GET)
    public ModelAndView outBoundPerformance(@RequestParam(required = false, defaultValue = "") String from, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        ModelAndView mav = new ModelAndView("report/outBoundPerformance");

        List<Warehouse> wList = warehouseService.getAllWarehouses();
        String reportServerUrl = systemConfigure.getReportServerUrl();

        mav.addObject("warehouseList",wList);
        mav.addObject("reportServerUrl", reportServerUrl);
        AgentUser user = SecurityHelper.getLoginUser();
        mav.addObject("r_username", user.getUserId());
        mav.addObject("r_password", user.getPassword());
        return mav;
    }
    //仓库库存差异(纸张盘点)报表
    @RequestMapping(value = "inventoryDifferencePapers", method = RequestMethod.GET)
    public ModelAndView inventoryDifferencePapers(@RequestParam(required = false, defaultValue = "") String from, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        ModelAndView mav = new ModelAndView("report/inventoryDifferencePapers");

        List<Warehouse> wList = warehouseService.getAllWarehouses();
        String reportServerUrl = systemConfigure.getReportServerUrl();

        mav.addObject("warehouseList",wList);
        mav.addObject("reportServerUrl", reportServerUrl);
        AgentUser user = SecurityHelper.getLoginUser();
        mav.addObject("r_username", user.getUserId());
        mav.addObject("r_password", user.getPassword());
        return mav;
    }
    //仓库库存差异(RF盘点)报表
    @RequestMapping(value = "inventoryDifferenceRF", method = RequestMethod.GET)
    public ModelAndView inventoryDifferenceRF(@RequestParam(required = false, defaultValue = "") String from, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        ModelAndView mav = new ModelAndView("report/inventoryDifferenceRF");

        List<Warehouse> wList = warehouseService.getAllWarehouses();
        String reportServerUrl = systemConfigure.getReportServerUrl();

        mav.addObject("warehouseList",wList);
        mav.addObject("reportServerUrl", reportServerUrl);
        AgentUser user = SecurityHelper.getLoginUser();
        mav.addObject("r_username", user.getUserId());
        mav.addObject("r_password", user.getPassword());
        return mav;
    }
    //仓库在库损耗报表
    @RequestMapping(value = "warehouseInventoryDamaged", method = RequestMethod.GET)
    public ModelAndView warehouseInventoryDamaged(@RequestParam(required = false, defaultValue = "") String from, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        ModelAndView mav = new ModelAndView("report/warehouseInventoryDamaged");

        List<Warehouse> wList = warehouseService.getAllWarehouses();
        String reportServerUrl = systemConfigure.getReportServerUrl();

        mav.addObject("warehouseList",wList);
        mav.addObject("reportServerUrl", reportServerUrl);
        AgentUser user = SecurityHelper.getLoginUser();
        mav.addObject("r_username", user.getUserId());
        mav.addObject("r_password", user.getPassword());
        return mav;
    }
    //仓库订单作业报表
    @RequestMapping(value = "orderTaskKpi", method = RequestMethod.GET)
    public ModelAndView orderTask(@RequestParam(required = false, defaultValue = "") String from, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        ModelAndView mav = new ModelAndView("report/orderTaskKpi");

        List<Warehouse> wList = warehouseService.getAllWarehouses();
        String reportServerUrl = systemConfigure.getReportServerUrl();

        mav.addObject("warehouseList",wList);
        mav.addObject("reportServerUrl", reportServerUrl);
        AgentUser user = SecurityHelper.getLoginUser();
        mav.addObject("r_username", user.getUserId());
        mav.addObject("r_password", user.getPassword());
        return mav;
    }
    		

}
