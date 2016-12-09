/*
 * Copyright (c) 2012 Acorn International.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http:www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or  implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.chinadrtv.erp.admin.controller;

import com.chinadrtv.erp.admin.model.CompanyType;
import com.chinadrtv.erp.admin.model.MailType;
import com.chinadrtv.erp.admin.service.ChannelTypeService;
import com.chinadrtv.erp.admin.service.PlubasInfoService;
import com.chinadrtv.erp.admin.util.MyConstantUtils;
import com.chinadrtv.erp.core.service.AuditLogService;
import com.chinadrtv.erp.core.service.NamesService;
import com.chinadrtv.erp.core.service.PromotionAdminService;
import com.chinadrtv.erp.core.service.PromotionCacheService;
import com.chinadrtv.erp.core.service.PromotionRuleAdminService;
import com.chinadrtv.erp.core.service.PromotionRuleOptionService;
import com.chinadrtv.erp.exception.PromotionException;
import com.chinadrtv.erp.model.ChannelType;
import com.chinadrtv.erp.model.Promotion;
import com.chinadrtv.erp.model.PromotionOption;
import com.chinadrtv.erp.model.PromotionRule;
import com.chinadrtv.erp.model.inventory.PlubasInfo;
import com.chinadrtv.erp.util.DateUtil;
import com.chinadrtv.erp.util.JsonBinder;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

/**
 * User: Haidong.Liu
 */

@Controller
public class PromotionController extends BaseController {
    private static final Logger log =
            Logger.getLogger(PromotionController.class.getName());

    @Autowired
    private PromotionAdminService promotionAdminService;

    @Autowired
    private PromotionRuleAdminService promotionRuleAdminService;

    @Autowired
    AuditLogService auditLogService;

    @Autowired
    private PromotionCacheService promotionCacheService;
    
    @Autowired
    private PlubasInfoService plubasInfoService;
    
    @Autowired
    private ChannelTypeService channelTypeService;
    

    
    @RequestMapping("/admin/promotions")
    public String handlepromotionsRequest(HttpServletRequest request) throws Exception {
    	//namesSerivce.getAllNames("ORDERTYPE");
    	//String str =jsonBinder.toJson(MyConstantUtils.obj.channelType).replace("\"", "'");
    	//System.out.println(str);
    	//System.out.println(namesSerivce.getAllNames("ORDERTYPE"));
    	//request.getSession().setAttribute("channelType_json",str);
        return "promotion/promotionList";
    	
    }

    private static JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();

    @RequestMapping(value = "/admin/promotion", method = RequestMethod.GET)
    public ModelAndView handleNewpromotionRequest(@RequestParam(required = false) String promotionId,
    		@RequestParam(required = false) String roleId,
    		HttpServletRequest request) {

        Promotion promotion = null;
        if(roleId == null) roleId="2";
        try {
            if (promotionId != null && !promotionId.trim().equals("")) {
                promotion = promotionAdminService.getPromotionById(Long.valueOf(promotionId));
            } else {

                promotion = new Promotion();
                promotion.setMaxuse(-1);
                promotion.setCalcRound(1);
                if(roleId != null){
                PromotionRule promotionRule  = promotionRuleAdminService.findById(Long.valueOf(roleId));
                promotion.setPromotionRule(promotionRule);
                }
           
            }
            
        } catch (PromotionException e) {
            log.severe(e.getMessage());
        }

        List<PromotionRule> promotionRules = promotionRuleAdminService.getAllPromotionRules(true);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("promotion/promotion");
        modelAndView.addObject("promotion", promotion);
        modelAndView.addObject("promotionRules", promotionRules);
        return modelAndView;
    }
    
    
    @RequestMapping(value = "/admin/promotionInfo", method = RequestMethod.GET)
    public ModelAndView handlePromotionInfoRequest(@RequestParam(required = false) String promotionId,
    		@RequestParam(required = false) String roleId,
    		HttpServletRequest request) {

        Promotion promotion = null;
        if(roleId == null) roleId="2";
        try {
            if (promotionId != null && !promotionId.trim().equals("")) {
                promotion = promotionAdminService.getPromotionById(Long.valueOf(promotionId));
            } else {

                promotion = new Promotion();
                promotion.setMaxuse(-1);
                promotion.setCalcRound(1);
                if(roleId != null){
                PromotionRule promotionRule  = promotionRuleAdminService.findById(Long.valueOf(roleId));
                promotion.setPromotionRule(promotionRule);
                }
           
            }
            
        } catch (PromotionException e) {
            log.severe(e.getMessage());
        }

        List<PromotionRule> promotionRules = promotionRuleAdminService.getAllPromotionRules(true);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("promotion/promotionInfo");
        modelAndView.addObject("promotion", promotion);
        modelAndView.addObject("promotionRules", promotionRules);
        return modelAndView;
    }

    @RequestMapping(value = "/admin/promotionOption", method = RequestMethod.GET)
    public ModelAndView getPromotionOption(@RequestParam(required = false) String promotionId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Promotion promotion = null;
        JSONArray jsonArray = new JSONArray();
        try {
            if (promotionId != null && !promotionId.trim().equals("")) {
                promotion = promotionAdminService.getPromotionById(Long.valueOf(promotionId));
                for(PromotionOption promotionOption : promotion.getPromotionOptions()){
                    JSONObject jsonObject = new JSONObject();
                    
                    try {
                        jsonObject.put("optionKey", promotionOption.getOptionKey());
                        jsonObject.put("optionValue", promotionOption.getOptionValue());
                        jsonArray.put(jsonObject);
                    } catch (JSONException e) {
                        log.severe(e.getMessage());
                    }
                }
            }
        } catch (PromotionException e) {
            log.severe(e.getMessage());
        }

        response.setContentType("text/json; charset=UTF-8");
        response.getWriter().print(jsonArray.toString());
        response.setHeader("Cache-Control", "no-cache");
        return null;
    }

    private Set<PromotionOption> getRuleOptionSetFromJson(String options_json, Promotion promotion) throws JSONException {
        Set<PromotionOption> promotionOptionHashSet = new HashSet<PromotionOption>();
        if (options_json != null && !options_json.equals("")) {
            JSONObject jsonObject = new JSONObject(options_json);
            String key;
            Object value;
            Iterator it = jsonObject.keys();
            while (it.hasNext()) {
                key = (String)it.next();
                if (key.indexOf("-")<0){
                    continue;
                }
                value = jsonObject.get(key);
                key = key.substring(0,key.indexOf("-"));
                PromotionOption promotionOption = new PromotionOption();
                promotionOption.setOptionKey(key);
                promotionOption.setOptionValue(value.toString());
                promotionOption.setPromotion(promotion);
                promotionOptionHashSet.add(promotionOption);
            }
        }
        return  promotionOptionHashSet;
    }

    @RequestMapping(value = "/admin/promotion", method = RequestMethod.POST)
    public ModelAndView saveNewpromotionRequest(@RequestParam(required = false) String promotionId,
                                                Promotion promotion, HttpServletRequest request) throws PromotionException {
        //String options_json = request.getParameter("options_json");
    	//System.out.println("sss:"+promotion.getChannel());
        String ruleOptions_json = request.getParameter("ruleOptions_json");
        String rejectIds = request.getParameter("pproducts");
        System.out.println("rejectIds:"+rejectIds);
        //Set<PromotionOption> promotionOptions = new HashSet<PromotionOption>();
        Set<PromotionOption> ruleOptions = new HashSet<PromotionOption>();
        try {

            //promotionOptions = getPromotionOptionSetFromJson(options_json, promotion);
            ruleOptions = getRuleOptionSetFromJson(ruleOptions_json, promotion);
            promotion.setPromotionOptions(ruleOptions);
            if( StringUtils.isNotBlank(rejectIds)) promotion.setPromotionProdcuts(plubasInfoService.getPlubasInfosByIds(rejectIds));
            //TODO
           // promotion.setPromotionProdcuts(null);
        } catch (JSONException je) {
            log.severe(je.getMessage());
        }
        //promotionAdminService.resetExecOrder();
        if (promotion.getMaxuse() == null){
            promotion.setMaxuse(-1);
        }
        
      //  promotion.setExecOrder(execOrder);
        promotionAdminService.savePromotion(promotion);

        return new ModelAndView("promotion/promotionList");
    }


    @RequestMapping(value = "/admin/plubasInfoJson", method = RequestMethod.POST)
    public String getPlubasInfoJson(@RequestParam(required = false, defaultValue = "0") int page,
                                    @RequestParam Integer rows,
                                    @RequestParam(required = false, defaultValue = "") String catcode,
                                    @RequestParam(required = false, defaultValue = "") String pluname,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        List<PlubasInfo> plubasInfoList = plubasInfoService.getAllPlubasInfos(catcode,pluname,page - 1 ,rows);

        JSONArray promotionsJsonArray = new JSONArray();
        Gson gson = new GsonBuilder()
           //     .setExclusionStrategies(new PromotionStrategy())
                .create();
       
        int totalRecords = plubasInfoService.getAllPlubasInfosCount(catcode,pluname);
        response.setContentType("text/json;charset=UTF-8");
        String jsonData = "{\"rows\":" + gson.toJson(plubasInfoList)+ ",\"total\":" + totalRecords + " }";

          response.getWriter().print(jsonData);
        response.setHeader("Cache-Control", "no-cache");

        return null;
    }
    
    
    @RequestMapping(value = "/admin/saveGiftInfo", method = RequestMethod.POST)
    public String saveGiftInfo(@RequestParam(required = false, defaultValue = "") String promotionId,
    		                  @RequestParam(required = false, defaultValue = "") String giftIds,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
    	Promotion promotion =promotionAdminService.getPromotionById(Long.valueOf(promotionId));
    	promotion.setProduct(giftIds);
    	promotionAdminService.savePromotion(promotion);
        return null;
    }
    

    @RequestMapping(value = "/admin/saveRejectInfo", method = RequestMethod.POST)
    public String saveRejectInfo(@RequestParam(required = false, defaultValue = "") String promotionId,
    		                  @RequestParam(required = false, defaultValue = "") String rejectIds,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
    	Promotion promotion =promotionAdminService.getPromotionById(Long.valueOf(promotionId));
    	promotion.setPromotionProdcuts(plubasInfoService.getPlubasInfosByIds(rejectIds));
    	promotionAdminService.savePromotion(promotion);
        return null;
    }
    
    @RequestMapping(value = "/admin/initGiftList", method = RequestMethod.POST)
    public String initGiftList(@RequestParam(required = false, defaultValue = "") String promotionId,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
    	if(promotionId.equals("")){
    		return "";
    	}
    	Promotion promotion =promotionAdminService.getPromotionById(Long.valueOf(promotionId));
        String giftIds = promotion.getProduct();
        Set<PlubasInfo> set = null;
        if(giftIds != null) set = plubasInfoService.getPlubasInfosByIds(giftIds);
    
        	  Gson gson = new GsonBuilder()
              //     .setExclusionStrategies(new PromotionStrategy())
                   .create();
             
        
        
        response.setContentType("text/json;charset=UTF-8");
       String jsonData = "{\"rows\":" + gson.toJson(set) + " }";
        response.getWriter().print(jsonData);
        response.setHeader("Cache-Control", "no-cache");
        
        return null;
    }
    
    @RequestMapping(value = "/admin/getGiftJSON", method = RequestMethod.GET)
    public String getGiftJSON(@RequestParam(required = false, defaultValue = "") String giftids,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
 
        Set<PlubasInfo> set = null;
        if(!giftids.equals("")) set = plubasInfoService.getPlubasInfosByIds(giftids);
        	  Gson gson = new GsonBuilder()
              //     .setExclusionStrategies(new PromotionStrategy())
                   .create();
             
        
        
        response.setContentType("text/json;charset=UTF-8");
       String jsonData = "{\"rows\":" + gson.toJson(set) + " }";
        response.getWriter().print(jsonData);
        response.setHeader("Cache-Control", "no-cache");
        
        return null;
    }
    

    @RequestMapping(value = "/admin/initRejectList", method = RequestMethod.POST)
    public String initRejectList(@RequestParam(required = false, defaultValue = "") String promotionId,
    		                  @RequestParam(required = false, defaultValue = "") String giftIds,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
    	if(promotionId.equals("")) return "";
    	Promotion promotion =promotionAdminService.getPromotionById(Long.valueOf(promotionId));
        Set<PlubasInfo> set = promotion.getPromotionProdcuts();
        	  Gson gson = new GsonBuilder()
              //     .setExclusionStrategies(new PromotionStrategy())
                   .create();
  
        response.setContentType("text/json;charset=UTF-8");
        String jsonData = "{\"rows\":" + gson.toJson(set) + " }";
        response.getWriter().print(jsonData);
        response.setHeader("Cache-Control", "no-cache");
        return null;
    }
    
    

        @RequestMapping(value = "/admin/promotionDelete/{promotionIDs}", method = RequestMethod.GET)
    public String delete(@PathVariable String promotionIDs, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            if (!promotionIDs.contains("_")) {
                promotionAdminService.deletePromotion(Long.valueOf(promotionIDs));
            } else {
                promotionAdminService.deletePromotionByIds(promotionIDs);
            }
        } catch (PromotionException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
            //
        return "promotion/promotionList";
    }

    @RequestMapping(value = "/admin/promotionExecOrder", method = RequestMethod.GET)
    public String execOrder(       @RequestParam String promotionId,
                                    @RequestParam Integer direction,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {

        Promotion promotion = promotionAdminService.getPromotionById(Long.valueOf(promotionId));
        promotionAdminService.resetExecOrder();
        promotionAdminService.updateExecOrder(promotion,direction);
        return "promotion/promotionList";
    }
    
    
    @RequestMapping(value = "/admin/promotionActive", method = RequestMethod.GET)
    public String updateActive( @RequestParam Long promotionId,
                                    @RequestParam Boolean active,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        promotionAdminService.updatePromotinActive(promotionId, active);
        return "promotion/promotionList";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        
        binder.registerCustomEditor(ChannelType.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (!org.springframework.util.StringUtils.hasText(text)) {
                    return;
                } else {
                    try
                    {
                    	ChannelType channelType  = channelTypeService.findById(text);
                       setValue(channelType);
                    }
                    catch(Exception ex) { /* do nothing */}
                }
            }
        });
        
    }
    
    /**
     *获取渠道信息
     */
    @RequestMapping(value = "/admin/getChannelInfo", method = RequestMethod.GET)
    public String getChannelInfo(       @RequestParam String promotionId,
                                    @RequestParam Integer direction,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {

        Promotion promotion = promotionAdminService.getPromotionById(Long.valueOf(promotionId));
        promotionAdminService.resetExecOrder();
        promotionAdminService.updateExecOrder(promotion,direction);
        return "promotion/promotionList";
    }
    
    
    @RequestMapping(value = "/admin/promotionsJson", method = RequestMethod.POST)
    public String getPromotionsJSON(@RequestParam(required = false, defaultValue = "0") int page,
                                    @RequestParam Integer rows,
                                    @RequestParam(required = false, defaultValue = "2") int active,
                                    @RequestParam(required = false, defaultValue = "") String promotionName,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {

        List<Promotion> promotionList = promotionAdminService.getPaginatedPromotionsListByNameActive(page-1, rows,promotionName,active);

        JSONArray promotionsJsonArray = new JSONArray();
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new PromotionStrategy())
                .create();
        try {
            for (Promotion promotion : promotionList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("channel", gson.toJson(promotion.getChannel()));
                jsonObject.put("createdBy", promotion.getCreatedBy());
                jsonObject.put("active", promotion.getActive());
                jsonObject.put("cumulative", promotion.getCumulative());
                jsonObject.put("description", promotion.getDescription());
                jsonObject.put("endDate",  DateUtil.date2FormattedString(promotion.getEndDate(), "yyyy-MM-dd hh:mm:ss"));
                jsonObject.put("execOrder", promotion.getExecOrder());
                jsonObject.put("maxuse", promotion.getMaxuse());
                jsonObject.put("name", promotion.getName());
                jsonObject.put("promotionid", promotion.getPromotionid());
                jsonObject.put("requiresCoupon", promotion.getRequiresCoupon());
                jsonObject.put("startDate", DateUtil.date2FormattedString(promotion.getStartDate(), "yyyy-MM-dd hh:mm:ss"));
                jsonObject.put("calcRound", promotion.getCalcRound());
                jsonObject.put("pprodcuts", promotion.getPromotionProdcuts());
                promotionsJsonArray.put(jsonObject);
            }
        } catch (Exception e) {
            log.severe(e.getMessage());
        }
        int totalRecords = promotionAdminService.getPromotionsCountByNameActive(page-1, rows,promotionName,active);
        response.setContentType("text/json;charset=UTF-8");
        String jsonData = "{\"rows\":" +promotionsJsonArray.toString()+ ",\"total\":" + totalRecords + " }";

          response.getWriter().print(jsonData);
        response.setHeader("Cache-Control", "no-cache");

        return null;
    }
    
    
    
    
}

class PromotionStrategy implements ExclusionStrategy {

    public boolean shouldSkipClass(Class<?> arg0) {
        return false;
    }

    public boolean shouldSkipField(FieldAttributes f) {
        return (f.getName().equals("products")) ||
                (f.getName().equals("promotionOptions"));
    }

}



