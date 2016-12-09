package com.chinadrtv.erp.oms.controller;

import com.chinadrtv.erp.model.CardAuthorization;
import com.chinadrtv.erp.model.Company;
import com.chinadrtv.erp.model.Orderhist;
import com.chinadrtv.erp.model.Orderdet;
import com.chinadrtv.erp.oms.service.*;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.chinadrtv.erp.util.JsonBinder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditorSupport;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 信用卡索权导入
 * User: Administrator
 * Date: 12-12-5
 * Time: 下午12:55
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class CardAuthorizationController{

    private static final Logger logger = LoggerFactory.getLogger(CardAuthorizationController.class);

    @Autowired
    private SystemConfigure systemConfigure;

    @Autowired
    private CardAuthorizationService cardAuthorizationService;

    @Autowired
    private OrderhistService orderhistService;

    @Autowired
    private OrderdetService orderdetService;

    @Autowired
    private CardtypeService cardtypeService;

        @InitBinder
        public void initBinder(WebDataBinder binder) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            dateFormat.setLenient(false);
            binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        }

    @RequestMapping(value = "/order/cardtype/lookup", method = RequestMethod.POST)
    public String lookupCardtypes(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        /*
        TODO:提供ComboBox绑定数据
        */
        Gson gson = new GsonBuilder().create();
        List<String> cardNames = cardtypeService.getCreditCardNames();
        List<Map<String, String>> maps = new ArrayList<Map<java.lang.String, java.lang.String>>();
        for(String name : cardNames){
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", name);
            map.put("name", name);
            maps.add(map);
        }
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().print(gson.toJson(maps)); //replaceAll("\"id\"", "id")
        return null;
    }

    @RequestMapping(value = "/order/auth")
    public ModelAndView auth()  {
        ModelAndView modelAndView = new ModelAndView("card/auth");
        modelAndView.addObject("url", "/order/auth/load");
        modelAndView.addObject("saveUrl", "#");
        modelAndView.addObject("updateUrl", "#");
        modelAndView.addObject("destroyUrl", "#");
        modelAndView.addObject("postUrl", "/order/auth/upload");
        return modelAndView;
    }

    @RequestMapping(value = "/order/auth/load", method = RequestMethod.POST)
    public String load(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam Integer rows,
            @RequestParam(required = false, defaultValue="") String cardtype,
            @RequestParam(required = false, defaultValue="") String orderid,
            @RequestParam(required = false, defaultValue="") String cardrightnum,
            @RequestParam(required = false, defaultValue="") String impdt1,
            @RequestParam(required = false, defaultValue="") String impdt2,
            @RequestParam(required = false, defaultValue="") String confirm,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        Map<String, Object> params = new HashMap<String, Object>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);

        if(cardtype != null && !cardtype.equals("")){
            params.put("cardtype", cardtype);
        }
        if(orderid != null && !orderid.equals("")){
            params.put("orderid", orderid);
        }
        if(cardrightnum != null && !cardrightnum.equals("")){
            params.put("cardrightnum", cardrightnum);
        }
        if(confirm != null && !confirm.equals("")){
            params.put("confirm", confirm);
        }
        if(impdt1 != null && !impdt1.equals("")){
            try
            {
                params.put("impdt1", dateFormat.parse(impdt1));
            }
            catch (ParseException ex) {
                /* do nothing */
            }
        }
        if(impdt2 != null && !impdt2.equals("")){
            try
            {
               params.put("impdt2", dateFormat.parse(impdt2));
            }
            catch (ParseException ex) {
                /* do nothing */
            }
        }

        Long totalRecords = cardAuthorizationService.getCardAuthorizationCount(params);
        List<CardAuthorization> auths = cardAuthorizationService.getCardAuthorizations(params, (page - 1) * rows, rows);
        if(auths != null){
            String jsonData = "{\"rows\":" +gson.toJson(auths) + ",\"total\":" + totalRecords + " }";
            response.setContentType("text/json; charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().print(jsonData);
        }
        return null;
    }

    @RequestMapping(value = "/order/auth/capture", method = RequestMethod.POST)
    public ModelAndView capture(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        StringBuilder error = new StringBuilder(); //记录失败单号
        try
        {
            String[] orderids=request.getParameterValues("orderid[]"); //订单号
            String[] cardrightnums = request.getParameterValues("cardrightnum[]");    //索权号
           // String cardType = request.getParameter("cardtype"); //卡类型
            for(int i=0;i<orderids.length;i++){

                Orderhist order = orderhistService.getOrderhistById(orderids[i]);
                if(order != null){
                   if(order.getConfirm() != null && order.getConfirm().equals("-1")){
                       error.append("订单号："+orderids[i]+"已索权，无法重复索权！<br/>");
                       continue;
                    }
                    CardAuthorization auth = cardAuthorizationService.getCardAuthorization(orderids[i],cardrightnums[i]);
                    if(auth != null){
                        double d = auth.getProdprice() != null ?  Double.parseDouble(auth.getProdprice()) : 0.0;
                        //校验金额、卡类型
                        if(order.getCardtype() == null || auth.getCardtype() == null){
                            error.append("订单号："+orderids[i]+"信用卡类型为空！<br/>");
                        }else {
                            if(order.getTotalprice() == d && order.getCardtype().equals(auth.getCardtype())){
                                order.setConfirm("-1");//索权标志
                                order.setCardrightnum(auth.getCardrightnum());
                                order.setMddt(new Date());  //修改日期
                                orderhistService.saveOrderhist(order);

                                auth.setConfirmdt(new Date());
                                AgentUser agentUser = SecurityHelper.getLoginUser();
                                if(agentUser != null)
                                    auth.setConfirmusr(agentUser.getUserId());

                                cardAuthorizationService.saveCardAuthorization(auth);
                            }else {
                                error.append("订单号："+orderids[i]+"金额或卡类型不匹配！<br/>");
                            }
                        }
                    }
                }else {
                    error.append("订单号："+orderids[i]+"不存在Orderhist表！<br/>");
                }
            }
            if("".equals(error.toString())){
                response.setContentType("text/plain; charset=UTF-8");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().print("{\"success\":\"订单索权成功！\"}");
            } else {
                response.setContentType("text/plain; charset=UTF-8");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().print("{\"success\":\"批处理失败单号：<br/>"+ error +"\"}");
            }
        } catch (Exception ex) {
            logger.error("订单索权失败!", ex);
            response.setContentType("text/plain; charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().print("{\"errorMsg\":\"订单索权失败异常：<br/>"+error+ ex.getMessage() +"\"}");
        }
        return null;
    }

    @RequestMapping(value = "/order/auth/upload", method = RequestMethod.POST)
    public ModelAndView upload(MultipartHttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return save(FileType.Xls, systemConfigure.getCcPathDir(), request, response);
    }

    public ModelAndView save(FileType fileType, String realPathDir, MultipartHttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        /*
        TODO:CVS文件导入
        */
        Map<String, Object> map = new HashMap<String, Object>();
        MultipartFile multipartFile = request.getFile("uploadfile");
        if(multipartFile!=null)
        {
            try {
                //文件合法性检查
                if(isValid(multipartFile, fileType,  map)){
                    String fileName = multipartFile.getOriginalFilename();
                    //保存文件
                    File savedir = new File(realPathDir);
                    if (!savedir.exists()) {
                        savedir.mkdirs();
                    }
                    byte[] buffer = multipartFile.getBytes();

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String savepath = realPathDir + (realPathDir.endsWith(File.separator)?"":File.separator);
                    savepath += sdf.format(new Date()) + "_" + fileName;
                    FileOutputStream fops = new FileOutputStream(new File(savepath));
                    try
                    {
                        fops.write(buffer);
                    }
                    finally {
                        fops.close();
                    }

                    List<String> errors = new ArrayList<String>();
                    handle(buffer, errors);
                    if(errors.size() == 0){
                        map.put("msg", "文件上传成功:"+savepath);
                        response.sendRedirect("");
                        return null;
                    }
                    else
                    {
                        StringBuilder result=new StringBuilder();
                        boolean flag=false;
                        for (String string : errors) {
                            if (flag) {
                                result.append("<br>");
                            }else {
                                flag=true;
                            }
                            result.append(string);
                        }
                        map.put("msg", "文件上传成功:"+savepath+"<br>但有如下数据错误:<br>"+ result.toString());
                        return new ModelAndView("fileUpload/success", map);
                    }
                }
                else
                {
                    return new ModelAndView("fileUpload/failure", map);
                }
            } catch (IOException e) {
                map.put("msg", e.getMessage());
                e.printStackTrace();
                return new ModelAndView("fileUpload/failure");
            }
        }
        else
        {
            return new ModelAndView("fileUpload/failure");
        }
    }

    public boolean isValid(MultipartFile multipartFile, FileType fileType, Map<String, Object> map) {
        String fileName = multipartFile.getOriginalFilename();
        String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

        switch (fileType){
            case Xls:
            {
                if(!fileExt.equals( "xls")){
                    map.put("msg", "信用卡索权文件上传失败,必须是Excel文件(*.xls)!");
                    return false;
                }
            }   break;
            default: break;
        }

        if(multipartFile.getSize() < 10){
            map.put("msg", "上传失败,文件不能为空!");
            return false;
        }
        return true;
    }

    @InitBinder
    public void initBinder(ServletRequestDataBinder binder)throws ServletException {
        binder.registerCustomEditor(byte[].class,new ByteArrayMultipartFileEditor());
    }

    public enum FileType {
        Xls
    }

    private void handle(byte[] buffer, List<String> errors){

        try
        {
            AgentUser agentUser = SecurityHelper.getLoginUser();
            Workbook book = WorkbookFactory.create(new ByteArrayInputStream(buffer));
            Sheet sheet = book.getSheetAt(0);     //工作薄是从0开始的
            logger.info(sheet.getPhysicalNumberOfRows() + "行");

            for(int rowIndex = 0; rowIndex < sheet.getPhysicalNumberOfRows();rowIndex++)
            {
                try
                {
                    Row row = sheet.getRow(rowIndex);
                    //至少三列(订单号,索劝号,金额)
                    if(row != null && row.getPhysicalNumberOfCells() > 2){
                        String orderId = getCellValueFromIndex(row, 0);
                        String transactionId = getCellValueFromIndex(row, 1);
                        String total = getCellValueFromIndex(row, 2);

                        if("".equals(orderId) || "".equals(transactionId) || "".endsWith(total))
                        {
                            errors.add(String.format("第%d行：所有项不能为空", rowIndex + 1, orderId));
                            continue;
                        }
                        Double totalValue = Double.valueOf(total);

                        Orderhist order = orderhistService.getOrderhistById(orderId);
                        if(order != null){
                            //是否信用卡付款
                            if(!"2".equals(order.getPaytype())){
                                errors.add(String.format("第%d行：订单%s不是信用卡付款!", rowIndex + 1, orderId));
                                continue;
                            }

                            if(totalValue.equals(order.getTotalprice()))
                            {
                                List<Orderdet> details = orderdetService.getAllOrderdet(orderId);
                                if(details != null && details.size() > 0){

                                    CardAuthorization auth = cardAuthorizationService.getCardAuthorization(orderId,transactionId);
                                    if(auth == null){
                                        auth = new CardAuthorization();
                                        auth.setOrderid(orderId);
                                    }
                                    auth.setOrderdetid(details.get(0).getOrderdetid());
                                    auth.setProdbankid(details.get(0).getProdbankid());
                                    auth.setScode(details.get(0).getProdscode());
                                    /*
                                    boolean flag = true;
                                    for(Orderdet det: details){
                                        if("".equals(auth.getOrderdetid()) || null == auth.getOrderdetid()) {
                                            auth.setOrderdetid(det.getOrderdetid());
                                        }  else {
                                            auth.setOrderdetid(auth.getOrderid() + "," + det.getOrderdetid());
                                        }
                                        if("".equals(auth.getProdbankid()) || null == auth.getProdbankid()) {
                                            auth.setProdbankid(det.getProdbankid());
                                        }  else {
                                            auth.setProdbankid(auth.getProdbankid() + "," + det.getProdbankid());
                                        }
                                        if("".equals(auth.getScode()) || null == auth.getScode()) {
                                            auth.setScode(det.getProdid());
                                        }  else {
                                            auth.setScode(auth.getScode() + "," + det.getProdid());
                                        }
                                    }
                                    */
                                    //auth.setCardapplynum();
                                    auth.setCardtype(order.getCardtype());
                                    auth.setImpdt(new Date());
                                    auth.setBankcardno(order.getCardnumber());
                                    auth.setCardrightnum(transactionId);
                                    auth.setProdprice(String.valueOf(total));
                                    if(agentUser != null){
                                        auth.setImpusr(agentUser.getUserId());
                                    }
                                    cardAuthorizationService.saveCardAuthorization(auth);
                                   // cardAuthorizationService.insertCardAuthorization(auth);
                                }
                                else
                                {
                                    errors.add(String.format("第%d行：找不到订单号%s相关明细", rowIndex + 1, orderId));
                                }
                            } else {
                                errors.add(String.format("第%d行：订单号%s金额不一致，应为%s", rowIndex + 1, orderId, String.valueOf(order.getTotalprice())));
                            }
                        }else{
                            errors.add(String.format("第%d行：订单号%s不存在", rowIndex + 1, orderId));
                        }
                    }
                }
                catch (Exception ex)
                {
                    errors.add(String.format("第%d行：发生错误，%s", rowIndex + 1, ex.getMessage()));
                }

            }
        }
        catch (Exception ex)
        {
            logger.error(ex.getMessage());
        }
    }

    private String getCellValueFromIndex(Row row, int colIndex){
        String result = "";
        Cell cell = row.getCell(colIndex);
        if(cell != null){
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:{
                    result = cell.getStringCellValue().trim();
                }  break;
                case Cell.CELL_TYPE_NUMERIC:{
                    if(DateUtil.isCellDateFormatted(cell))
                    {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d hh:mm");
                        result = sdf.format(cell.getDateCellValue());
                    }
                    else
                    {
                        result = String.valueOf(cell.getNumericCellValue());
                        if(result.contains("E")){
                            DecimalFormat df = new DecimalFormat("0");
                            result = df.format(cell.getNumericCellValue());
                        }
                        else if(result.endsWith(".0"))
                        {
                            result = result.substring(0, result.length() - 2);
                        }
                    }
                } break;
                default: break;
            }
        }
        return result;
    }
}
