package com.chinadrtv.manual.controller;

import com.chinadrtv.manual.service.ManualService;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-12-5
 * Time: 下午1:07
 * To change this template use File | Settings | File Templates.
 * 手工订单导入
 */
@Controller
@RequestMapping({ "/manual" })
public class ManualImportController {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ManualImportController.class);
    public ManualImportController(){
        logger.info("ManualImportController is Create !");
    }
    @Autowired
    private ManualService manualService;
    @RequestMapping(value = "/upload")
    @ResponseBody
    public void uploadExcel(HttpServletRequest request,HttpServletResponse response,@RequestBody MultipartFile mFile)
            throws Exception{
        Map<String,String> result = new HashMap<String, String>();
        String error = "";
        ServletOutputStream out = response.getOutputStream();
        HSSFWorkbook book = null;
        if(!mFile.isEmpty()){
            try{
                String fileName = mFile.getOriginalFilename();
                logger.info("filename:"+fileName);
                InputStream is = mFile.getInputStream();
                manualService.doProcessXLS(is);
            }catch (Exception e){
                logger.error("error:",e);
            }
        }

    }

   /* public void uploadExcel(HttpServletRequest request,@RequestParam("fileToUpload") CommonsMultipartFile mFile, HttpServletResponse response){
        try{

            InputStream is = null;
            manualService.doProcessXLS(is);

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }*/

}
