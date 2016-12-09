package com.chinadrtv.erp.sales.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.chinadrtv.erp.core.service.NamesService;
import com.chinadrtv.erp.model.Contact;
import com.chinadrtv.erp.model.PotentialContact;
import com.chinadrtv.erp.sales.core.util.DateUtil;
import com.chinadrtv.erp.sales.dto.CustomerFrontDto;
import com.chinadrtv.erp.uc.service.AddressService;
import com.chinadrtv.erp.uc.service.ContactService;
import com.chinadrtv.erp.uc.service.PhoneService;
import com.chinadrtv.erp.uc.service.PotentialContactService;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.chinadrtv.erp.util.Base64Encryptor;
import com.chinadrtv.erp.util.IpUtils;

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
    private ContactService contactService;

    @Autowired
    private PhoneService phoneService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private NamesService namesService;

    @Autowired
    private PotentialContactService potentialContactService;


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
        
//        Contact contact = contactService.get("910698284");
//        Double points = contactService.findJiFenByContactId("910698284");
//        String level = contactService.findLevelByContactId("14309038");
//        DataGridModel dataGridModel = new DataGridModel();
//        dataGridModel.setPage(1);
//        dataGridModel.setRows(10);
//        Map<String,Object> result = phoneService.findByContactId("910698284", dataGridModel);
//        mav.addObject("contact", contact);
//        mav.addObject("points", points);
//        mav.addObject("level", level);
//        mav.addObject("phoneList", result.get("rows"));
        
        mav.addObject("educations", namesService.getAllNames("EDUCATION"));
        mav.addObject("marriages", namesService.getAllNames("MARRIAGE"));
        mav.addObject("occupationStatuss", namesService.getAllNames("OCCUPATIONSTATUS"));
        mav.addObject("income", namesService.getAllNames("INCOME"));
        return mav;
    }
    
    //@RequestMapping("/mycustomer/mycustomer")
    public String myclients() throws Exception {
        return "mycustomer/mycustomer";
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
    @RequestMapping(value = "/callback/callback/{customerType}/{customerId}", method = RequestMethod.GET)
    public ModelAndView callback(@PathVariable("customerType") String customerType,
                           @PathVariable("customerId") String customerId) throws Exception {
        ModelAndView mav = new ModelAndView("callback/callback");


        CustomerFrontDto customerFrontDto = new CustomerFrontDto();
        double points = 0;
        String level = "";
        if (CustomerFrontDto.POTENTIAL_CONTACT.equals(customerType)) {
            customerFrontDto = queryPotentialContactConvertToDto(customerId);
        } else {
            customerFrontDto = queryContactConvertToDto(customerId);
        }
        mav.addObject("customer", customerFrontDto);

        mav.addObject("callbackFront", customerFrontDto);

        mav.addObject("payTypes", JSONSerializer.toJSON(namesService.getAllNames("PAYTYPE")));
        mav.addObject("orderTypes", JSONSerializer.toJSON(namesService.getAllNames("ORDERTYPE")));
        mav.addObject("orderStatuses", JSONSerializer.toJSON(namesService.getAllNames("ORDERSTATUS")));
        mav.addObject("logisticsTypes", JSONSerializer.toJSON(namesService.getAllNames("CUSTOMIZESTATUS")));
        return mav;
    }



    private CustomerFrontDto queryPotentialContactConvertToDto(String customerId) {
        CustomerFrontDto customerFrontDto = new CustomerFrontDto();
        PotentialContact potentialContact = null;
        try {
            potentialContact = potentialContactService.queryById(Long.valueOf(customerId));
        } catch (Exception e) {
            potentialContact = new PotentialContact();
        }
        customerFrontDto.setCustomerId(customerId);
        customerFrontDto.setName(potentialContact.getName());
        customerFrontDto.setSex(potentialContact.getGender());
        customerFrontDto.setBirthday(potentialContact.getBirthday());
        customerFrontDto.setCustomerType(CustomerFrontDto.POTENTIAL_CONTACT);
        return customerFrontDto;
    }

    private CustomerFrontDto queryContactConvertToDto(String customerId) {
        CustomerFrontDto customerFrontDto = new CustomerFrontDto();
        Contact contact = null;
        try {
            contact = contactService.get(customerId);
        } catch (Exception e) {
            contact = new Contact();
        }
        BeanUtils.copyProperties(contact, customerFrontDto);
        customerFrontDto.setCustomerId(customerId);
        customerFrontDto.setCustomerType(CustomerFrontDto.CONTACT);
        return customerFrontDto;
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