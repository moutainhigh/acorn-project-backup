package com.chinadrtv.erp.knowledge.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.chinadrtv.erp.knowledge.service.KnowledgeArticleService;

/**
 * Created with IntelliJ IDEA. User: liuhaidong Date: 13-5-14 Time: 下午4:41 To
 * change this template use File | Settings | File Templates.
 */
@Controller
public class IndexController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(IndexController.class);
	@Autowired
	private KnowledgeArticleService knowledgeArticleService;

	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request) throws Exception {
		ModelAndView modelAndView = new ModelAndView("/index");
		modelAndView.addObject("newAddsList",
				knowledgeArticleService.getNewAdds(11l, getGoupType(request)));
		return modelAndView;
	}

	@RequestMapping("/welcome")
	public ModelAndView welcome() throws Exception {
		ModelAndView modelAndView = new ModelAndView("/welcome");
		return modelAndView;
	}
}