package com.chinadrtv.erp.report.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GeneralController {

	/**
	 * 报表列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/listReport")
	public String listReport(HttpServletRequest request, HttpServletResponse response, Model model){
		String contextPath = request.getSession().getServletContext().getRealPath("/reports/");
		File directory = new File(contextPath);
		File[] files = directory.listFiles();
		List<String> reports = new ArrayList<String>();
		for (File file : files) {
			if(!file.isDirectory()){
				String name = file.getName();
				if(name.endsWith(".rptdesign")){
					reports.add(file.getName());
				}
			}
		}
		model.addAttribute("reports",reports);
		return "listReport";
	}
	
	/**
	 * 测试报表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/testReport")
	public String testReport(HttpServletRequest request, HttpServletResponse response, Model model){
		return "redirect:/frameset?__report=reports/test.rptdesign&__format=HTML";
	}
	
}
