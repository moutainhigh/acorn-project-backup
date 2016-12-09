package com.chinadrtv.erp.marketing.controller;


/**
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author andrew
 * @version 1.0
 * @since 2013-1-22 下午2:05:59
 * 
 */
public class SmsTemplateController extends BaseController {

	// @Autowired
	// private SmsTemplateService smsTemplateService;
	// @Autowired
	// private NamesMarketingService namesMarketingService;

	/**
	 * 
	 * @Description: 导航
	 * @param request
	 * @param response
	 * @return
	 * @return ModelAndView
	 * @throws
	 */
	// @RequestMapping(value = "templateIndex", method = RequestMethod.GET)
	// public ModelAndView index(HttpServletRequest request,
	// HttpServletResponse response) {
	// ModelAndView mav = new ModelAndView("sms/templateList");
	// // List<NamesMarketing> namesList =
	// // namesMarketingService.queryNamesList();
	// // mav.addObject("namesList", namesList);
	// return mav;
	// }

	/**
	 * 
	 * @Description: 分页查询
	 * @param request
	 * @param response
	 * @param smsTemplateDto
	 * @param dataModel
	 * @return
	 * @throws IOException
	 * @return Map<String,Object>
	 * @throws
	 */
	// @RequestMapping(value = "templateList", method = RequestMethod.POST)
	// @ResponseBody
	// public Map<String, Object> list(HttpServletRequest request,
	// HttpServletResponse response, SmsTemplateDto smsTemplateDto,
	// DataGridModel dataModel) throws IOException {
	//
	// Map<String, Object> resutlMap = smsTemplateService.findPageList(
	// smsTemplateDto, dataModel);
	//
	// return resutlMap;
	// }

	/**
	 * 
	 * @Description: 保存
	 * @param smsTemplate
	 * @param request
	 * @param response
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	// @RequestMapping(value = "templateSave", method = RequestMethod.POST)
	// @ResponseBody
	// public Map<String, Object> save(SmsTemplate smsTemplate,
	// HttpServletRequest request, HttpServletResponse response) {
	// Map<String, Object> resultMap = new HashMap<String, Object>();
	//
	// Long uid = new Long(this.getUserId(request));
	// if (null == smsTemplate || smsTemplate.getId() == null) {
	// smsTemplate.setCrtuid(uid);
	// smsTemplate.setCrttime(new Date());
	// } else {
	// smsTemplate.setUpduid(uid);
	// smsTemplate.setUpdtime(new Date());
	// }
	// try {
	// smsTemplateService.saveSmsTemplate(smsTemplate);
	// resultMap.put("success", true);
	// } catch (Exception e) {
	// resultMap.put("success", false);
	// e.printStackTrace();
	// }
	// return resultMap;
	// }

	/**
	 * 
	 * @Description: 删除
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	// @RequestMapping(value = "templateDelete", method = RequestMethod.POST)
	// @ResponseBody
	// public Map<String, Object> delete(
	// @RequestParam(required = true) String ids,
	// HttpServletRequest request, HttpServletResponse response) {
	// Map<String, Object> resultMap = new HashMap<String, Object>();
	//
	// try {
	// smsTemplateService.deleteSmsTemplate(ids);
	// resultMap.put("success", true);
	// } catch (Exception e) {
	// resultMap.put("success", false);
	// e.printStackTrace();
	// }
	//
	// return resultMap;
	// }
	//
	// public SmsTemplateService getSmsTemplateService() {
	// return smsTemplateService;
	// }
	//
	// public void setSmsTemplateService(SmsTemplateService smsTemplateService)
	// {
	// this.smsTemplateService = smsTemplateService;
	// }
	//
	// public NamesMarketingService getNamesService() {
	// return namesMarketingService;
	// }
	//
	// public void setNamesService(NamesMarketingService namesMarketingService)
	// {
	// this.namesMarketingService = namesMarketingService;
	// }

}