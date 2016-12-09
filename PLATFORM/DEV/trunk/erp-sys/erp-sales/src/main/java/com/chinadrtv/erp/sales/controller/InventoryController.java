package com.chinadrtv.erp.sales.controller;

import com.chinadrtv.erp.ic.model.*;
import com.chinadrtv.erp.ic.service.ItemInventoryIntransitService;
import com.chinadrtv.erp.ic.service.ItemInventoryService;
import com.chinadrtv.erp.ic.service.PlubasInfoService;
import com.chinadrtv.erp.marketing.core.dto.CampaignTaskVO;
import com.chinadrtv.erp.marketing.core.service.CampaignBPMTaskService;
import com.chinadrtv.erp.marketing.core.service.LeadInterActionService;
import com.chinadrtv.erp.model.inventory.PlubasInfo;
import com.chinadrtv.erp.sales.core.model.OrderSoldDto;
import com.chinadrtv.erp.sales.core.service.OrderSoldService;
import com.chinadrtv.erp.tc.core.service.UserProductService;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.chinadrtv.erp.util.JsonBinder;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 商品库存
 * User: gaodejian
 * Date: 13-5-22
 * Time: 下午4:19
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class InventoryController extends BaseController {

    @Autowired
    private PlubasInfoService plubasInfoService;

    @Autowired
    private ItemInventoryService itemInventoryService;

    @Autowired
    private OrderSoldService orderSoldService;

    @Autowired
    private UserProductService userProductService;

    @Autowired
    private LeadInterActionService leadInterActionService;

    @Autowired
    private CampaignBPMTaskService campaignBPMTaskService;

    @Autowired
    private ItemInventoryIntransitService itemInventoryIntransitService;

    @RequestMapping(value = "/inventory/summary")
    public ModelAndView summary(){
        return new ModelAndView("/inventory/summary");
    }

    @ResponseBody
    @RequestMapping(value = "/product/search")
    public List<NcPlubasInfo> getNcPlubasInfos(
            @RequestParam(required = true) String q,
            @RequestParam(required = true, defaultValue = "0") Integer index,
            @RequestParam(required = true, defaultValue = "12") Integer limit,
            @RequestParam(required = true) Long timestamp)  {
        //q = new String(q.getBytes("iso-8859-1"),"utf-8");
        AgentUser agentUser = SecurityHelper.getLoginUser();
        if(agentUser != null) {
            return plubasInfoService.getNcPlubasInfos(agentUser.getWorkGrp(), q.replaceAll("\\s", "&&"), index * limit, limit);
        } else {
            return new ArrayList<NcPlubasInfo>();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/product/attributes",method= RequestMethod.POST)
    public List<NcPlubasInfoAttribute> getNcAttributes(@RequestParam(required = true) String nccode)  throws Exception {
        return plubasInfoService.getNcAttributes(nccode);
    }

    @ResponseBody
    @RequestMapping(value = "/inventory/item",method= RequestMethod.POST)
    public List<NcRealTimeStockItem> getNcInventoryItems(@RequestParam(required = true) String nccode,
                                                         @RequestParam(required = false) String instId) {
        List<NcRealTimeStockItem> items = itemInventoryService.getNcRealTimeStock(nccode);
        if(StringUtils.isNotBlank(instId)){
            //变更当前销售线索为咨询
            CampaignTaskVO taskVo = campaignBPMTaskService.queryMarketingTask(instId);
            if(taskVo != null){
                leadInterActionService.updateLeadInteraction(taskVo.getLeadId(), "2");
            }
        }
        return items;
    }

    @ResponseBody
    @RequestMapping(value = "/warehouse/sku",method= RequestMethod.POST)
    public List<WmsRealTimeStockItem> getWarehouseItems(
            @RequestParam(required = true) String nccode,
            @RequestParam(required = false) String ncfree,
            @RequestParam(required = false) String ncfreename
    ) {
        return itemInventoryService.getWmsRealTimeStock(nccode, ncfree, ncfreename);
    }

    @ResponseBody
    @RequestMapping(value = "/inventory/sku",method= RequestMethod.POST)
    public List<RealTimeStockItem> getInventoryItems(
            @RequestParam(required = true) String nccode,
            @RequestParam(required = false) String ncfree,
            @RequestParam(required = false) String ncfreename) {
        return itemInventoryService.getDbRealTimeStock(nccode, ncfree, ncfreename);
    }

    @ResponseBody
    @RequestMapping(value = "/inventory/favorites",method= RequestMethod.POST)
    public List<OrderSoldDto> getFavorites() {
        AgentUser agentUser = SecurityHelper.getLoginUser();
        if(agentUser != null) {
            return orderSoldService.getTop20Favorites(agentUser.getUserId());
        }
        else return new ArrayList<OrderSoldDto>();
    }

    @ResponseBody
    @RequestMapping(value = "/inventory/topsale",method= RequestMethod.POST)
    public List<OrderSoldDto> getTopSale() {
        /*
        AgentUser agentUser = SecurityHelper.getLoginUser();
        if(agentUser != null) {
            return orderSoldService.getTop20Sales(agentUser.getUserId());
        }
        else return new ArrayList<OrderSoldDto>();
        */
        return orderSoldService.getTop20Sales("0");
    }

    @ResponseBody
    @RequestMapping(value = "/inventory/intransit",method= RequestMethod.POST)
    public List<NcIntransitItem> getNcIntransitItems(
            @RequestParam(required = true) String nccode,
            @RequestParam(required = false) String ncfreename) {
        return itemInventoryIntransitService.GetNcIntransitItems(nccode, ncfreename);
    }

    @ResponseBody
    @RequestMapping(value = "/inventory/addFavorite",method= RequestMethod.POST)
    public String addFavorite(
            @RequestParam(required = true) String nccode,
            @RequestParam(required = false) String ncfree,
            @RequestParam(required = false) String ncfreename) {
        try
        {
            AgentUser agentUser = SecurityHelper.getLoginUser();
            if(agentUser != null) {
                userProductService.addFavorite(agentUser.getUserId(), nccode, ncfree, ncfreename);
                orderSoldService.updateTop20Favorites(agentUser.getUserId());
            }
            return "收藏成功";
        }
        catch (Exception ex){
            return String.format("收藏失败：%s", ex.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/inventory/removeFavorite",method= RequestMethod.POST)
    public String removeFavorite(
            @RequestParam(required = true) Long favoriteId) {
        try
        {
            AgentUser agentUser = SecurityHelper.getLoginUser();
            if(agentUser != null) {
                userProductService.removeFavorite(agentUser.getUserId(), favoriteId);
                orderSoldService.updateTop20Favorites(agentUser.getUserId());
            }
            return "删除收藏成功";
        }
        catch (Exception ex){
            return String.format("添加收藏失败：%s", ex.getMessage());
        }
    }
}
