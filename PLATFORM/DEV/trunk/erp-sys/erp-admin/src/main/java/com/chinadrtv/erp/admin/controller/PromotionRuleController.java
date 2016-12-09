/**
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

import static org.junit.Assert.assertTrue;

import com.chinadrtv.erp.core.service.PromotionRuleAdminService;
import com.chinadrtv.erp.core.service.ruleoption.RuleOptionUIFactory;
import com.chinadrtv.erp.exception.PromotionException;
import com.chinadrtv.erp.model.PreTrade;
import com.chinadrtv.erp.model.PreTradeDetail;
import com.chinadrtv.erp.model.PromotionRule;
import com.chinadrtv.erp.model.PromotionRuleResult;
import com.chinadrtv.erp.model.PromotionRuleResult_FreeProduct;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * User: Haidong.Liu
 */

@Controller
public class PromotionRuleController extends BaseController {
    private static final Logger log =
            Logger.getLogger(PromotionRuleController.class.getName());

    @Autowired
    private PromotionRuleAdminService promotionRuleAdminService;

    @RequestMapping("/admin/promotionRules")
    public String handlepromotionRulesRequest() {
        return "promotionRules/promotionRuleList";
    }

    @RequestMapping(value = "/admin/promotionRule", method = RequestMethod.GET)
    public ModelAndView handleNewpromotionRuleRequest(@RequestParam(required = false) String promotionRuleId) {
        PromotionRule promotionRule = null;
        if (promotionRuleId != null && !promotionRuleId.trim().equals("")) {
            //edit promotionRule.
            promotionRule = promotionRuleAdminService.findById(Long.valueOf(promotionRuleId));
        } else {
            //new promotionRule.
            promotionRule = new PromotionRule();
        }
        return new ModelAndView("promotionRules/promotionRule", "promotionRule", promotionRule);
    }


    @RequestMapping(value = "/admin/promotionRule", method = RequestMethod.POST)
    public ModelAndView saveNewpromotionRuleRequest(@RequestParam(required = false) String promotionRuleId,
                                          PromotionRule promotionRule, HttpServletRequest request) {
        try {
            promotionRuleAdminService.savePromotionRule(promotionRule);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ModelAndView("promotionRules/promotionRuleList");
    }

    @RequestMapping(value = "/admin/promotionRule/{promotionRuleId}", method = RequestMethod.GET)
    public ModelAndView viewpromotionRule(@PathVariable String promotionRuleId) throws Exception {
        PromotionRule promotionRule = promotionRuleAdminService.findById(Long.valueOf(promotionRuleId));
        if (promotionRule == null) {
            promotionRule = new PromotionRule();
        }
        return new ModelAndView("promotionRules/promotionRule-preview", "promotionRule", promotionRule);
    }

    @RequestMapping(value = "/admin/ruleOptionUI/{promotionRuleId}", method = RequestMethod.GET)
    public ModelAndView ruleOptionUI(@PathVariable String promotionRuleId,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
        PromotionRule promotionRule = promotionRuleAdminService.findById(Long.valueOf(promotionRuleId));
        if (promotionRule == null) {
            throw new IllegalArgumentException("PromotionRule not found which Id is: " + promotionRuleId) ;
        }
        
        
      if(promotionRule.getRuleName().equals("赠品")){
    	  RuleOptionUIFactory.buildUI(promotionRule,true);
    }else{
    	  RuleOptionUIFactory.buildUI(promotionRule,false);
    }	
        
        JSONObject jsonData = new JSONObject();
        jsonData.put("html", RuleOptionUIFactory.getHtml());
        jsonData.put("initScript",RuleOptionUIFactory.getInitScript());
        jsonData.put("validateScript",RuleOptionUIFactory.getValidateScript());
        response.setContentType("text/json; charset=UTF-8");
        response.getWriter().print(jsonData.toString());
        System.out.println(jsonData.toString());
        response.setHeader("Cache-Control", "no-cache");
        return null;
    }
    
    @RequestMapping(value = "/admin/ruleOptionUIFile/{promotionRuleId}", method = RequestMethod.GET)
    public ModelAndView ruleOptionUIFile(@PathVariable String promotionRuleId,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
        PromotionRule promotionRule = promotionRuleAdminService.findById(Long.valueOf(promotionRuleId));
        if (promotionRule == null) {
            throw new IllegalArgumentException("PromotionRule not found which Id is: " + promotionRuleId) ;
        }
        
        if(promotionRule.getRuleName().equals("赠品")){
      	  RuleOptionUIFactory.buildUI(promotionRule,true);
      }else{
      	  RuleOptionUIFactory.buildUI(promotionRule,false);
      }
        
        JSONObject jsonData = new JSONObject();
        jsonData.put("html", RuleOptionUIFactory.getHtml());
        jsonData.put("initScript",RuleOptionUIFactory.getInitScript());
        jsonData.put("validateScript",RuleOptionUIFactory.getValidateScript());
        response.setContentType("text/json; charset=UTF-8");
        response.getWriter().print(jsonData.toString());
        response.setHeader("Cache-Control", "no-cache");
        return null;
    }


    @RequestMapping(value = "/admin/promotionRules/json", method = RequestMethod.GET)
    public String getpromotionRulesJSON(@RequestParam(required = false, defaultValue = "0") int start_index,
                              @RequestParam Integer num_per_page,
                              HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        List<PromotionRule> promotionRuleList = promotionRuleAdminService.searchPaginatedPromotionRulesList(start_index,num_per_page);

        JSONArray promotionRulesJsonArray = new JSONArray();
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new PromotionRuleStrategy())
                .create();
        try {
            for (PromotionRule promotionRule : promotionRuleList) {
                promotionRulesJsonArray.put(new JSONObject(gson.toJson(promotionRule)));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        int totalRecords = promotionRuleAdminService.getPromotionRulesCount();
        response.setContentType("text/json;charset=UTF-8");
        String jsonData = "{\"records\":" + promotionRulesJsonArray.toString() + ",\"totalRecords\":" + totalRecords + ",\"startIndex\":" + start_index + " }";
        response.getWriter().print(jsonData);
        response.setContentType("text/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        return null;
    }

    @RequestMapping(value = "/admin/promotionRules/delete/{promotionRuleIDs}", method = RequestMethod.GET)
    public String delete(@PathVariable String promotionRuleIDs, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!promotionRuleIDs.contains("_")) {
            promotionRuleAdminService.deletePromotionRuleById(Long.valueOf(promotionRuleIDs));
        } else {
            promotionRuleAdminService.deletePromotionRuleByIds(promotionRuleIDs);
        }
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().print("{\"delete\":\"true\"}");
        return null;
    }
    
  

}

class PromotionRuleStrategy implements ExclusionStrategy {

    public boolean shouldSkipClass(Class<?> arg0) {
        return false;
    }

    public boolean shouldSkipField(FieldAttributes f) {

        return (f.getName().equals("products")) ||
                (f.getName().equals("promotionRuleAttributes"));
    }

}
