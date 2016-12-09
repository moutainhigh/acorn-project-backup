/**
 * 
 */
package com.chinadrtv.erp.marketing.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.servlet.ModelAndView;

/**
 * @author haoleitao
 * @date 2013-1-15 上午11:10:22
 *
 */
public class FileUtil {
//	public static fileUp(){
//
//		 try {
//	         //文件合法性检查
//	         if(isValid(multipartFile, fileType,  map)){
//	             String fileName = multipartFile.getOriginalFilename();
//	             //保存文件
//	             File savedir = new File(realPathDir);
//	             if (!savedir.exists()) {
//	                 savedir.mkdirs();
//	             }
//	             SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//	             String savepath = realPathDir + (realPathDir.endsWith(File.separator)?"":File.separator);
//	             savepath += sdf.format(new Date()) + "_" + fileName;
//	             File csvFile = new File(savepath);
//	             FileOutputStream fops = new FileOutputStream(new File(savepath));
//	             fops.write(multipartFile.getBytes());
//	             fops.close();
//	             map.put("msg", "文件上传成功:"+savepath);
//	             return new ModelAndView("fileUpload/success", map);
//	         }
//	         else
//	         {
//	             return new ModelAndView("fileUpload/failure", map);
//	         }
//	     } catch (IOException e) {
//	         map.put("msg", e.getMessage());
//	         e.printStackTrace();
//	         return new ModelAndView("fileUpload/failure");
//	     }
//
//	}
	
}
