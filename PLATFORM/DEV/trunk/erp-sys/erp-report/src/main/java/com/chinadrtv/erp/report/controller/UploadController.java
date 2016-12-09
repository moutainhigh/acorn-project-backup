package com.chinadrtv.erp.report.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller//声明该类为控制器类
@RequestMapping("/upload")
public class UploadController {
	
	/**
	 * 跳转到上传页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/toUploadReport")
	public String toUploadReport(HttpServletRequest request, HttpServletResponse response, Model model){
		return "uploadReport";
	}

	/**
	 * 上传报表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/report", method = RequestMethod.POST)
	public String uploadReport(MultipartHttpServletRequest request,HttpServletResponse response, Model model) {
		String contextPath = request.getSession().getServletContext().getRealPath("/reports/");
		String msg = "";
		List<MultipartFile> files = request.getFiles("file");
		for (MultipartFile file : files) {
			
			if (file.isEmpty()){
				msg += "请选择要上传的报表文件.<br />";
				continue;
			}
			
			String fileName = file.getOriginalFilename();
			File f = new File(contextPath + "/" + fileName);
			if(!fileName.endsWith(".rptdesign")){
				msg += f.getName() + "不是合法报表文件.<br />";
				continue;
			}
			
			if(f.exists()){
				msg += f.getName() + "已存在.<br />";
				continue;
			}
			
			FileOutputStream fileOS = null;
			try {
				fileOS = new FileOutputStream(f);
				fileOS.write(file.getBytes());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(fileOS!=null){
					try {
						fileOS.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
		}
		model.addAttribute("msg", msg);
		if(msg==null || "".equals(msg)){
			return "redirect:/listReport";
		}
		return "uploadReport";
	}

}