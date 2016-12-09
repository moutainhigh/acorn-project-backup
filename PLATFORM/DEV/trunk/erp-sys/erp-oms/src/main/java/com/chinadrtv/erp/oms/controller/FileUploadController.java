package com.chinadrtv.erp.oms.controller;

import com.chinadrtv.erp.oms.service.OmsBackorderService;
import com.chinadrtv.erp.oms.service.SystemConfigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-5
 * Time: 下午12:55
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class FileUploadController {
    @Autowired
    private SystemConfigure systemConfigure;

    @Autowired
    private OmsBackorderService omsBackorderService;


    @RequestMapping("/order/fileUpload")
    public ModelAndView index() throws Exception {
        ModelAndView modelAndView = new ModelAndView("fileUpload/index");
        modelAndView.addObject("caption", "手工订单导入");
        modelAndView.addObject("postUrl", "/order/fileUpload/submit");
        modelAndView.addObject("fileUrl", "/static/files/manual.xls");
        return modelAndView;
    }

    @RequestMapping("/taobao/fileUpload")
    public ModelAndView taobao() throws Exception {
        ModelAndView modelAndView = new ModelAndView("fileUpload/index");
        modelAndView.addObject("caption", "淘宝结算单导入");
        modelAndView.addObject("postUrl", "/taobao/fileUpload/submit");
        modelAndView.addObject("fileUrl", "#");
        return modelAndView;
    }

    @RequestMapping("/jingdong/fileUpload")
    public ModelAndView jingdong() throws Exception {
        ModelAndView modelAndView = new ModelAndView("fileUpload/index");
        modelAndView.addObject("caption", "京东结算单导入");
        modelAndView.addObject("postUrl", "/jingdong/fileUpload/submit");
        modelAndView.addObject("fileUrl", "#");
        return modelAndView;
    }

    @RequestMapping("/chama/fileUpload")
    public ModelAndView chama() throws Exception {
        ModelAndView modelAndView = new ModelAndView("fileUpload/index");
        modelAndView.addObject("caption", "茶马结算单导入");
        modelAndView.addObject("postUrl", "/chama/fileUpload/submit");
        modelAndView.addObject("fileUrl", "#");
        return modelAndView;
    }

    @RequestMapping("/amazon/fileUpload")
    public ModelAndView amazon() throws Exception {
        ModelAndView modelAndView = new ModelAndView("fileUpload/index");
        modelAndView.addObject("caption", "卓越结算单导入");
        modelAndView.addObject("postUrl", "/amazon/fileUpload/submit");
        modelAndView.addObject("fileUrl", "#");
        return modelAndView;
    }

    @RequestMapping("/order/backorder/fileUpload")
    public ModelAndView backorder() throws Exception {
        ModelAndView modelAndView = new ModelAndView("fileUpload/index");
        modelAndView.addObject("caption", "压单取消导入");
        modelAndView.addObject("postUrl", "/order/backorder/fileUpload/submit");
        modelAndView.addObject("fileUrl", "/static/files/backorder.xls");
        return modelAndView;
    }

    @RequestMapping("/logisticsStatus/fileUpload")
    public ModelAndView logisticsStatus() throws Exception {
        ModelAndView modelAndView = new ModelAndView("fileUpload/index");
        modelAndView.addObject("caption", "物流状态反馈");
        modelAndView.addObject("postUrl", "/logisticsStatus/fileUpload/submit");
        modelAndView.addObject("fileUrl", "/static/files/logisticsback.xls");
        return modelAndView;
    }

    @RequestMapping(value = "/order/fileUpload/submit", method = RequestMethod.POST)
    public ModelAndView submit(MultipartHttpServletRequest request, HttpServletResponse response) {
        return save(FileType.Csv, systemConfigure.getRealPathDir(), request, response);
    }

    @RequestMapping(value = "/taobao/fileUpload/submit", method = RequestMethod.POST)
    public ModelAndView submitTaobao(MultipartHttpServletRequest request, HttpServletResponse response) {
        return save(FileType.Taobao, systemConfigure.getTaobaoPathDir(), request, response);
    }

    @RequestMapping(value = "/jingdong/fileUpload/submit", method = RequestMethod.POST)
    public ModelAndView submitJingdong(MultipartHttpServletRequest request, HttpServletResponse response) {
        return save(FileType.Jingdong, systemConfigure.getJingdongPathDir(), request, response);
    }

    @RequestMapping(value = "/chama/fileUpload/submit", method = RequestMethod.POST)
    public ModelAndView submitChama(MultipartHttpServletRequest request, HttpServletResponse response) {
        return save(FileType.Chama, systemConfigure.getChamaPathDir(), request, response);
    }

    @RequestMapping(value = "/amazon/fileUpload/submit", method = RequestMethod.POST)
    public ModelAndView submitAmazon(MultipartHttpServletRequest request, HttpServletResponse response) {
        return save(FileType.Amazon, systemConfigure.getAmazonPathDir(), request, response);
    }

    @RequestMapping(value = "/order/backorder/fileUpload/submit", method = RequestMethod.POST)
    public ModelAndView submitBackorder(MultipartHttpServletRequest request, HttpServletResponse response) {
        return save(FileType.Backorder, systemConfigure.getBackorderPathDir(), request, response);
    }

    @RequestMapping(value = "/logisticsStatus/fileUpload/submit", method = RequestMethod.POST)
    public ModelAndView submitLogistics(MultipartHttpServletRequest request, HttpServletResponse response) {
        return save(FileType.LogisticsStatus, systemConfigure.getLogisticsStatusPathDir(), request, response);
    }

    public ModelAndView save(FileType fileType, String realPathDir, MultipartHttpServletRequest request, HttpServletResponse response) {
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
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String savepath = realPathDir + (realPathDir.endsWith(File.separator)?"":File.separator);
                    savepath += sdf.format(new Date()) + "_" + fileName;
                    FileOutputStream fops = new FileOutputStream(new File(savepath));
                    try
                    {
                        fops.write(multipartFile.getBytes());
                    }
                    finally {
                        fops.close();
                    }
                    handle(fileType, savepath);
                    map.put("msg", "文件上传成功:"+savepath);
                    return new ModelAndView("fileUpload/success", map);
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
            case Csv:
            {
                if(!fileExt.equals( "xls")){
                    map.put("msg", "手工订单上传失败,必须是Excel文件(*.xls)!");
                    return false;
                }
            } break;
            case Taobao:
            {
                if(!fileExt.equals( "xls")){
                    map.put("msg", "淘宝结算单上传失败,必须是Excel文件(*.xls)!");
                    return false;
                }
            }   break;
            case Jingdong:
            {
                if(!fileExt.equals( "xls")){
                    map.put("msg", "京东结算单上传失败,必须是Excel文件(*.xls)!");
                    return false;
                }
            }   break;
            case Chama:
            {
                if(!fileExt.equals( "xls")){
                    map.put("msg", "茶马结算单上传失败,必须是Excel文件(*.xls)!");
                    return false;
                }
            } break;
            case Amazon:
            {
                if(!fileExt.equals( "xls")){
                    map.put("msg", "卓越结算单上传失败,必须是Excel文件(*.xls)!");
                    return false;
                }
            } break;
            case Backorder:
            {
                if(!fileExt.equals( "xls")){
                    map.put("msg", "压单取消上传失败,必须是Excel文件(*.xls)!");
                    return false;
                }
            } break;
            case LogisticsStatus:
            {
                if(!fileExt.equals( "xls")){
                    map.put("msg", "物流状态导入上传失败,必须是Excel文件(*.xls)!");
                    return false;
                }
            } break;
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
        Csv, Taobao, Jingdong, Chama, Amazon, Backorder,LogisticsStatus
    }

    private void handle(FileType fileType, String filePath){
        switch (fileType){
            case Backorder:{
               if(omsBackorderService != null){
                   omsBackorderService.saveXlsFile(filePath);
               }
            } break;
            default: break;
        }
    }
}
