package com.chinadrtv.erp.sales.controller;

import com.chinadrtv.erp.core.service.NamesService;
import com.chinadrtv.erp.sales.core.util.DateUtil;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.chinadrtv.erp.util.Base64Encryptor;
import com.chinadrtv.erp.util.IpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liuhaidong
 * Date: 13-5-14
 * Time: 下午4:41
 * To change this template use File | Settings | File Templates.
 */
@Controller
@SessionAttributes(value = "BASE64_PASSWORD")
public class IndexController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private NamesService namesService;


    @Value("${knowledge.url}")
    private String knowledgeUrl;

    @Value("${distribution.url}")
    private String distributionUrl;

    @RequestMapping("/callbackAssign/callbackAssign")
    public ModelAndView callbackAssign() throws Exception {
    	ModelAndView mav = new ModelAndView("callbackAssign/callbackAssign");
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	String curDate = sdf.format(new Date());
    	mav.addObject("curDate", curDate);
        return mav;
    }

    @RequestMapping("/home/home")
    public ModelAndView home() throws Exception {
    	ModelAndView mav = new ModelAndView("home/home");
    	AgentUser user = SecurityHelper.getLoginUser();
		String userId = user.getUserId();
		String department = user.getDepartment();
    	//TODO change to the real data
    	mav.addObject("userID", userId);
		mav.addObject("department", department);
        return mav;
    }

    @RequestMapping(value = "/inbound/inbound", method = RequestMethod.GET)
    public ModelAndView inbound() throws Exception {
        ModelAndView mav = new ModelAndView("inbound/inbound");

        mav.addObject("educations", namesService.getAllNames("EDUCATION"));
        mav.addObject("marriages", namesService.getAllNames("MARRIAGE"));
        mav.addObject("occupationStatuss", namesService.getAllNames("OCCUPATIONSTATUS"));
        mav.addObject("income", namesService.getAllNames("INCOME"));
        return mav;
    }

    @RequestMapping("/test")
    public String test() throws Exception {
        return "common/test";
    }

  @RequestMapping("/integral/integral")
  public ModelAndView integral(
		  @RequestParam(required = false, defaultValue = "") String name,
		  @RequestParam(required = false, defaultValue = "") String customerId
			) throws Exception {
	  ModelAndView mav = new ModelAndView("integral/integral");
	  mav.addObject("name", java.net.URLDecoder.decode(name , "UTF-8"));
	  mav.addObject("customerId", customerId);
      return mav;
  }
    
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request,
			@ModelAttribute("BASE64_PASSWORD") String password)
			throws Exception {
		logger.info("index is display");
		AgentUser user = SecurityHelper.getLoginUser();
		ModelAndView mav = new ModelAndView("index");
		
		String usr = user.getUserId();
		String requestIp = IpUtils.getRequestIp(request);
		String ts = DateUtil.formatCurrentDate(DateUtil.FORMAT_DATE_LINE);

		mav.addObject("knowledgeUrl", String.format(knowledgeUrl + "?%s",
				Base64Encryptor.encrypt(encrypt(usr, password, requestIp, ts))));
		mav.addObject("distributionUrl", String.format(distributionUrl + "?%s",
				Base64Encryptor.encrypt(encrypt(usr, password, requestIp, ts))));
		return mav;
	}
    
    @RequestMapping("/userManagement/userManagement")
    public String userManagement() throws Exception {
        return "userManagement/userManagement";
    }

	private String encrypt(String usr, String pwd, String ip, String ts) {
		StringBuilder sb = new StringBuilder();
		sb.append(usr);
		sb.append("|");
		sb.append(pwd);
		sb.append("|");
		sb.append(ip != null ? ip : "");
		sb.append("|");
		sb.append(ts);
		return sb.toString();
	}

}