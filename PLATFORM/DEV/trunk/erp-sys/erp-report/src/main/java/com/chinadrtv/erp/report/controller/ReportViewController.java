package com.chinadrtv.erp.report.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("reportview")
@Controller
public class ReportViewController {
	
	/**
	 * 访问报表
	 * @param reportId
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/common")
	public String common(@PathVariable String reportId, HttpServletRequest request, HttpServletResponse response, Model model){
		String params = request.getQueryString();
		return "redirect:/frameset?" + params;
	}

	/**
	 * 访问报表
	 * @param reportId
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/frameset/{reportId}")
	public String frameset(@PathVariable String reportId, HttpServletRequest request, HttpServletResponse response, Model model){
		String params = request.getQueryString();
		return "redirect:/frameset?__report=reports/" + reportId + ".rptdesign&__format=HTML&__showtitle=false&" + params;
	}
	
	/**
	 * 访问报表
	 * @param reportId
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/run/{reportId}/{params}")
	public String run(@PathVariable String reportId, @PathVariable String params, Model model){
		return "redirect:/run?__report=reports/" + reportId + ".rptdesign&__format=HTML&__showtitle=false&" + params;
	}
	
}
