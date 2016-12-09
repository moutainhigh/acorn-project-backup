package com.chinadrtv.uam.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.uam.model.cas.Site;
import com.chinadrtv.uam.service.SiteService;

@Controller
@RequestMapping("/site")
public class SiteController extends BaseController {
	
	@Resource
	private SiteService siteService;
	
	private static final Site UAM_SITE;
	static {
		UAM_SITE = new Site();
		UAM_SITE.setName("uam");
	}

	@ResponseBody
	@RequestMapping(value = "/list")
	public List<Site> list() {
		return siteService.getAll(Site.class);
	}
	
	@ResponseBody
	@RequestMapping(value = "/listuam")
	public List<Site> listIncludeUam() {
		List<Site> sites = siteService.getAll(Site.class);
		sites.add(UAM_SITE);
		return sites;
	}
	
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(Site site) {
		site.setCreateDate(new Date());
		site.setEvaluationOrder(0);
		Long id = null;
		try {
			id = siteService.save(site);
		} catch (Exception e) {
			return e.getMessage();
		}
		if (id != null) {
			return SUCCESS;
		} else {
			return "Add site failure";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Site site) {
		site.setUpdateDate(new Date());
		site.setEvaluationOrder(0);
		try {
			siteService.update(site);
		} catch (Exception e) {
			return e.getMessage();
		}
		return SUCCESS;
	}
	
	@ResponseBody
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String add(@PathVariable Long id) {
		try {
			siteService.delete(Site.class, id);
		} catch (Exception e) {
			return e.getMessage();
		}
		return SUCCESS;
	}
}
