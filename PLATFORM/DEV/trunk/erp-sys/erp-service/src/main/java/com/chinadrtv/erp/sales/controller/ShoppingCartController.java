package com.chinadrtv.erp.sales.controller;

import com.chinadrtv.erp.constant.SecurityConstants;
import com.chinadrtv.erp.core.model.ScmPromotion;
import com.chinadrtv.erp.core.service.EmsService;
import com.chinadrtv.erp.core.service.NamesService;
import com.chinadrtv.erp.exception.ErpException;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.ic.model.CompanyDeliverySpan;
import com.chinadrtv.erp.ic.service.CompanyDeliverySpanService;
import com.chinadrtv.erp.ic.service.PlubasInfoService;
import com.chinadrtv.erp.marketing.core.common.AuditTaskType;
import com.chinadrtv.erp.marketing.core.service.LeadInterActionService;
import com.chinadrtv.erp.marketing.core.service.LeadInteractionOrderService;
import com.chinadrtv.erp.marketing.core.service.LeadService;
import com.chinadrtv.erp.marketing.core.service.SmsApiService;
import com.chinadrtv.erp.marketing.core.util.HttpUtil;
import com.chinadrtv.erp.model.*;
import com.chinadrtv.erp.model.agent.*;
import com.chinadrtv.erp.model.marketing.Lead;
import com.chinadrtv.erp.model.marketing.LeadInteraction;
import com.chinadrtv.erp.model.marketing.LeadInteractionOrder;
import com.chinadrtv.erp.model.security.FieldPermission;
import com.chinadrtv.erp.sales.common.JsonUtil;
import com.chinadrtv.erp.sales.core.constant.MessageType;
import com.chinadrtv.erp.sales.core.model.OrderCreateResult;
import com.chinadrtv.erp.sales.core.service.OrderhistService;
import com.chinadrtv.erp.sales.core.service.SysMessageService;
import com.chinadrtv.erp.sales.dto.ProductDto;
import com.chinadrtv.erp.sales.dto.ScmPromotionDto;
import com.chinadrtv.erp.sales.dto.ShoppingCartProductDto;
import com.chinadrtv.erp.service.core.service.KfServicesContext;
import com.chinadrtv.erp.service.core.util.AgentUserHelpler;
import com.chinadrtv.erp.smsapi.model.SmsTemplate;
import com.chinadrtv.erp.tc.core.constant.model.OrderhistAssignInfo;
import com.chinadrtv.erp.tc.core.service.OrderhistCompanyAssignService;
import com.chinadrtv.erp.uc.dto.AddressDto;
import com.chinadrtv.erp.uc.service.*;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.PermissionHelper;
import com.chinadrtv.erp.user.util.SecurityHelper;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.*;

/**
 *购物车controller 购物车地址相关处理被转移
 * @author zhoutaotao
 * @version 1.0
 * @since 2013-5-23 下午1:46:41
 *
 */
@Controller
@RequestMapping(value="cart")
public class ShoppingCartController extends BaseController {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ShoppingCartController.class);
	@Autowired
	private ShoppingCartService shoppingCartService;
	@Autowired
	private PlubasInfoService plubasInfoService;
    @Autowired
    private OrderhistService orderhistService;
    @Autowired
    private SmsApiService smsApiService;
    @Autowired
    public NamesService namesService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private EmsService emsService;
    @Autowired
    private LeadInterActionService leadInterActionService;
    @Autowired
    private LeadInteractionOrderService leadInteractionOrderService;
    @Autowired
    private LeadService leadService;
    @Autowired
    private CardtypeService cardtypeService;
    @Autowired
    private SysMessageService sysMessageService;
    @Autowired
    private UserService userService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private CardService cardService;

    @Autowired
    private KfServicesContext kfServicesContext;
    /**
     * 初始化购物车
     * @param contactId
     * @param cartType
     * @param response
     */
    @RequestMapping(value = "/shoppingCartCreate/{cartType}/{contactId}", method = RequestMethod.POST)
    public void shoppingCartCreate(@PathVariable("cartType") String cartType,@PathVariable("contactId") Long contactId,
                                   HttpServletResponse response) {
        AgentUser user = SecurityHelper.getLoginUser();
        ShoppingCart shoppingCart = shoppingCartService.findShoppingCartByContactId(contactId, cartType);
        if (shoppingCart == null) {
            shoppingCart = shoppingCartService.createShoppingCart(contactId, user.getUserId(), cartType);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("shoppingCartId", shoppingCart.getId());
        map.put("shoppingCartContactId", contactId);
        map.put("cart_cartType", cartType);

        List<Cardtype> cardtypes = cardtypeService.queryUsefulCardtypes();
        if (cardtypes != null) {
            //001=身份证 002=护照 011=军官证 014=台胞证
            for (Object ct : cardtypes.toArray()) {
                Cardtype cardtype = (Cardtype) ct;
                if (cardtype != null && ("014".equalsIgnoreCase(cardtype.getCardtypeid()))) {
                    cardtypes.remove(cardtype);
                }
            }
            map.put("cardTypes", cardtypes);
            map.put("cardBanks", getUsefulBanks(cardtypes));
        }
        //修改地址修改电话权限
        boolean approveManager =false;
        try {
            String groupType = userService.getGroupType(user.getUserId());
            approveManager ="in".equalsIgnoreCase(groupType)  ||  user.hasRole(SecurityConstants.ROLE_APPROVE_MANAGER);
        } catch (ServiceException e) {
            logger.error("新增或修改客户主地址获取权限", e);
        }
        map.put("approveManager", approveManager);

        HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
    }

    private List<String> getUsefulBanks(List<Cardtype> cardtypes) {
        List<String> banks = new ArrayList<String>();
        if (cardtypes != null) {
            for (Cardtype type : cardtypes) {
                if (StringUtils.isNotBlank(type.getBankName())) {
                    if (!banks.contains(type.getBankName())) {
                        banks.add(type.getBankName());
                    }
                }
            }
        }
        return banks;
    }
    /**
     * 刷新购物车
     * @param shoppingCartId
     * @param response
     */
    @RequestMapping(value = "/shoppingCartReload/{shoppingCartId}", method = RequestMethod.GET)
    public void  reloadShoppingCartProduct(@PathVariable("shoppingCartId") Long shoppingCartId, HttpServletResponse response){
        ShoppingCart shoppingCart =  shoppingCartService.findShoppingCartById(shoppingCartId);
        Map<String, Object> map = new HashMap<String, Object>();
        List<ShoppingCartProductDto> list = new ArrayList<ShoppingCartProductDto>();
        if(shoppingCart!=null && !shoppingCart.getShoppingCartProducts().isEmpty()){
            AgentUser user = SecurityHelper.getLoginUser();
            List<ScmPromotion> scmPromotions = shoppingCartService.getScmPromotions(shoppingCart, user.getUserId());
            for(ShoppingCartProduct scp:shoppingCart.getShoppingCartProducts()) {
                ShoppingCartProductDto shoppingCartProductDto = new ShoppingCartProductDto(scp);
                if(3 ==scp.getIsGift()){
                    shoppingCartProductDto.setProductTypes(plubasInfoService.getNcAllAttributes(scp.getSkuCode()));
                }else {
                    shoppingCartProductDto.setProductTypes(plubasInfoService.getNcAttributes(scp.getSkuCode()));
                }
                //判断是否赠品
                boolean isScmGift = getScmGift(scp, scmPromotions);
                shoppingCartProductDto.setIsScmGift(isScmGift);
                list.add(shoppingCartProductDto);
            }
        }

        map.put("result", JsonUtil.jsonArrayWithFilterObject(list, "shoppingCart"));
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
    }
    //判断是否促销规则产品
    private boolean getScmGift(ShoppingCartProduct scp, List<ScmPromotion> scmPromotions) {
        if(scp.getIsGift()==3){
            for (ScmPromotion scmPromotion :scmPromotions) {
                if(scp.getSkuCode().equals(scmPromotion.getPluid())){
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * 删除购物车产品
     * @param shoppingCartId
     * @param shoppingCartProductId
     * @param response
     */
	@RequestMapping(value="deleteProduct/{shoppingCartId}/{shoppingCartProductId}/{parentId}",method=RequestMethod.GET)
	public void deleteShoppingCartProduct(@PathVariable("shoppingCartId")Long shoppingCartId,
                                          @PathVariable("shoppingCartProductId")Long shoppingCartProductId , @PathVariable("parentId")String parentOrderId,HttpServletResponse response){
        kfServicesContext.setParentOrderId(parentOrderId);

        AgentUser user = SecurityHelper.getLoginUser();
        Map<String,String> map = new HashMap<String,String>();
		try {
            List<ShoppingCartProduct> list = shoppingCartService.deleteShoppingCartProductById(shoppingCartId, shoppingCartProductId, user.getUserId());

            map.put("result", JsonUtil.jsonArrayWithFilterObject(list, "shoppingCart"));
		}catch (ErpException e) {
            map.put("message", e.getMessage());
           logger.error("删除购物车失败", e);
		} catch(Exception e1){
            map.put("message", "删除购物车产品失败");
            logger.error("删除购物车失败", e1);
		}
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
	}

    /**
     * 添加促销产品
     * @param shoppingCartId
     * @param shoppingCartProduct
     * @param response
     */
    @RequestMapping(value = "addScmPromotion", method = RequestMethod.POST)
    public void addShoppingCartProduct(Long shoppingCartId, ShoppingCartProduct shoppingCartProduct,String parentOrderId, HttpServletResponse response) {
        kfServicesContext.setParentOrderId(parentOrderId);

        AgentUser user = SecurityHelper.getLoginUser();
        Map<String, String> map = new HashMap<String, String>();
        try {
            if(StringUtils.isNotBlank(shoppingCartProduct.getProductType()) && shoppingCartProduct.getProductType().contains("%")){
                 shoppingCartProduct.setProductType(URLDecoder.decode(shoppingCartProduct.getProductType(),"utf-8"));
            }
            shoppingCartProduct.setIsGift(3);
            if(shoppingCartProduct.getSalePrice() ==null){
                shoppingCartProduct.setSalePrice(BigDecimal.ZERO);
            }
            shoppingCartProduct.setCreateUser(user.getUserId());
            shoppingCartProduct.setCreateDate(new Date());
            shoppingCartProduct.setIsSelected(true);
            shoppingCartProduct.setProductQuantity(1);
            Map<String, Object> resultMap = shoppingCartService.addShoppingCartProduct(shoppingCartId, user.getUserId(), shoppingCartProduct);

            map.put("reload", resultMap.get("reload") == null ? "false" : resultMap.get("reload").toString());

            if("false".equals(map.get("reload"))){
                ShoppingCartProductDto shoppingCartProductDto = new ShoppingCartProductDto(shoppingCartProduct);
                shoppingCartProductDto.setProductTypes(plubasInfoService.getNcAllAttributes(shoppingCartProduct.getSkuCode()));
                shoppingCartProductDto.setIsScmGift(true);
                map.put("result", JsonUtil.jsonObjectWithFilterObject(shoppingCartProductDto, "shoppingCart"));
            }

        } catch (ErpException e) {
            logger.error("添加促销产品失败", e);
            map.put("message", e.getMessage());
        } catch (Exception e1) {
            logger.error("添加促销产品失败", e1);
            map.put("message", "添加促销产品失败");
        }
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
    }

    /**
     * 添加购物车产品
     * @param shoppingCartId
     * @param ncCode
     * @param ncFreeName
     * @param shoppingCartProduct
     * @param response
     */
	@RequestMapping(value="addShoppingCartProduct",method=RequestMethod.POST)
	public void addShoppingCartProduct(Long shoppingCartId,String ncCode,String ncFreeName,ShoppingCartProduct shoppingCartProduct , String parentOrderId,
                                       HttpServletResponse response) {
        kfServicesContext.setParentOrderId(parentOrderId);
        AgentUser user = SecurityHelper.getLoginUser();
        Map<String,String> map = new HashMap<String,String>();
		try {
            if(StringUtils.isNotBlank(ncFreeName) && ncFreeName.contains("%")){
                ncFreeName = URLDecoder.decode(ncFreeName,"utf-8");
            }
            shoppingCartProduct.setSkuCode(ncCode);
            shoppingCartProduct.setProductType(ncFreeName);
            if(StringUtils.isNotBlank(kfServicesContext.getParentOrderId()))
                shoppingCartProduct.setIsGift(5);
            else
                shoppingCartProduct.setIsGift(1);
            shoppingCartProduct.setCreateUser(user.getUserId());
            shoppingCartProduct.setCreateDate(new Date());
            shoppingCartProduct.setIsSelected(true);
            shoppingCartProduct.setProductQuantity(1);
            Map<String, Object> resultMap = shoppingCartService.addShoppingCartProduct(shoppingCartId,user.getUserId(),shoppingCartProduct);

            map.put("reload",resultMap.get("reload")==null?"false":resultMap.get("reload").toString());

            if("false".equals(map.get("reload"))){
                ShoppingCartProductDto shoppingCartProductDto  =new ShoppingCartProductDto(shoppingCartProduct);
                shoppingCartProductDto.setProductTypes(plubasInfoService.getNcAttributes(shoppingCartProduct.getSkuCode()));
                shoppingCartProductDto.setIsScmGift(false);
                map.put("result",JsonUtil.jsonObjectWithFilterObject(shoppingCartProductDto, "shoppingCart"));
            }
		} catch (ErpException e) {
            map.put("message", e.getMessage());
            logger.error("添加库存产品失败", e);
		}catch (Exception e1) {
            map.put("message", "添加库存产品失败");
            logger.error("添加库存产品失败", e1);
		}
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
	}

    /**
     * 更新购物车产品
     * @param shoppingCartProduct
     * @param response
     */
    @RequestMapping(value = "updateShoppingCartProduct", method = RequestMethod.POST)
    public void updateShoppingCartProduct(ShoppingCartProduct shoppingCartProduct, String parentOrderId, HttpServletResponse response) {
        kfServicesContext.setParentOrderId(parentOrderId);
        AgentUser user = SecurityHelper.getLoginUser();
        Map<String, String> map = new HashMap<String, String>();
        try {
            if (StringUtils.isNotBlank(shoppingCartProduct.getProductType()) && shoppingCartProduct.getProductType().contains("%")) {
                shoppingCartProduct.setProductType(URLDecoder.decode(shoppingCartProduct.getProductType(), "utf-8"));
            }

            Map<String, Object> resultMap = shoppingCartService.updateShoppingCartProduct(shoppingCartProduct, user.getUserId());

            map.put("reload",resultMap.get("reload")==null?"false":resultMap.get("reload").toString());
            if("false".equals(resultMap.get("reload").toString())){
                List<ShoppingCartProduct> list = (List<ShoppingCartProduct>) resultMap.get("list");
                map.put("result", JsonUtil.jsonArrayWithFilterObject(list, "shoppingCart"));
            }
        } catch (ErpException e) {
            logger.error("更新购物车产品失败", e);
            map.put("message", e.getMessage());
        } catch (Exception e1) {
            logger.error("更新购物车产品失败", e1);
            map.put("message", "更新购物车产品失败");
        }
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
    }

    /**
     * 查询购物车产品
     * @param shoppingCartProductId
     * @param response
     */
    @RequestMapping(value="getShoppingCartProduct/{shoppingCartProductId}",method=RequestMethod.GET)
    public void getShoppingCartProduct(@PathVariable("shoppingCartProductId") Long shoppingCartProductId,HttpServletResponse response) {

       ShoppingCartProduct shoppingCartProduct = shoppingCartService.findShoppingCartProductById(shoppingCartProductId);
       HttpUtil.ajaxReturn(response, JsonUtil.jsonObjectWithFilterObject(shoppingCartProduct, "shoppingCart"), "application/json");
    }

    /**
     * 查询购物车价格
     * @param shoppingCartId
     * @param response
     */
	@RequestMapping(value="getSalePrice/{shoppingCartId}",method=RequestMethod.GET)
    public void getShoppingCartSalePrice(@PathVariable("shoppingCartId") Long shoppingCartId,HttpServletResponse response) {
        BigDecimal result = shoppingCartService.getShoppingCartSalePrice(shoppingCartId);

        Map<String,BigDecimal> map = new HashMap<String,BigDecimal>();
        map.put("result",result);
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
    }

    /**
     * 查询购物车产品数量
     * @param shoppingCartId
     * @param response
     */
	@RequestMapping(value="getProductQuantity/{shoppingCartId}",method=RequestMethod.GET)
    public void getShoppingCartProductQuantity(@PathVariable("shoppingCartId") Long shoppingCartId,HttpServletResponse response) {
        Long result = shoppingCartService.getShoppingCartProductQuantity(shoppingCartId);

        Map<String,Long> map = new HashMap<String,Long>();
        map.put("result",result);
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
    }

    /**
     * 查询促销产品
     * @param shoppingCartId
     * @param response
     */
    @RequestMapping(value = "getScmPromotions/{shoppingCartId}", method = RequestMethod.GET)
    public void getScmPromotions(@PathVariable("shoppingCartId") Long shoppingCartId, HttpServletResponse response) {
        AgentUser user = SecurityHelper.getLoginUser();
        ShoppingCart shoppingCart = shoppingCartService.findShoppingCartById(shoppingCartId);
        List<ScmPromotion> scmPromotions = shoppingCartService.getScmPromotions(shoppingCart, user.getUserId());
        for (ShoppingCartProduct scp : shoppingCart.getShoppingCartProducts()) {
            for (ScmPromotion scmPromotion : scmPromotions) {
                if (scmPromotion.getPluid().equals(scp.getSkuCode()) && scp.getIsGift() == 3) {
                    scmPromotions.remove(scmPromotion);
                    break;
                }
            }
        }

        List<ScmPromotionDto> scmPromotionDtoList = new ArrayList<ScmPromotionDto>();
        if (scmPromotions != null && !scmPromotions.isEmpty()) {
            for (ScmPromotion scmPromotion : scmPromotions) {
                ScmPromotionDto scmPromotionDto = new ScmPromotionDto(scmPromotion);
                scmPromotionDto.setProductTypes(plubasInfoService.getNcAllAttributes(scmPromotion.getPluid()));
                scmPromotionDtoList.add(scmPromotionDto);
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", JsonUtil.jsonArrayWithFilterObject(scmPromotionDtoList, ""));
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
    }

    /**
     * 赠险确认
     */
    @RequestMapping(value="/insureValidate",method = RequestMethod.POST)
    public void insureValidate(String dnis,String provinceName,String cityName,HttpServletResponse response){
        AgentUser user = SecurityHelper.getLoginUser();

        //赠送权限  被叫号 + 权限
        boolean  dnisValidate =false;
        if(StringUtils.isNotBlank(dnis)){
            List<Names> dnisList = namesService.getAllNames("INSURE_DNIS");
            for(Names names : dnisList){
                if(names.getDsc().equals(dnis) && "-1".equals(names.getValid())){
                    dnisValidate =true;
                }
            }
        }else {
            dnisValidate = true;
        }

        boolean roleValidate = user.hasRole(SecurityConstants.ROLE_INSURE_PROMPT);//

        //被赠权限       指定区域(厦门被特殊过滤)
        List<Names> provinceList = namesService.getAllNames("INSURE_PROVINCE");
        boolean  provinceValidate =false;
        for(Names names : provinceList){
            if(names.getDsc().equals(provinceName) && !"厦门".equals(cityName) && "-1".equals(names.getValid()) ){
                provinceValidate =true;
            }
        }

        boolean insure = dnisValidate && roleValidate && provinceValidate;

        HttpUtil.ajaxReturn(response, jsonBinder.toJson(insure), "application/json");
    }


    /**
     * 查询保险产品
     * @param shoppingCartId
     * @param response
     */
    @RequestMapping(value = "getInsurePromotions/{shoppingCartId}", method = RequestMethod.GET)
    public void getInsurePromotions(@PathVariable("shoppingCartId") Long shoppingCartId, HttpServletResponse response) {
        AgentUser user = SecurityHelper.getLoginUser();
        ShoppingCart shoppingCart = shoppingCartService.findShoppingCartById(shoppingCartId);
        List<Product> productList = shoppingCartService.getInsureList(String.valueOf(shoppingCart.getContactId()), user.getDefGrp(),shoppingCart.getOrderid());
        List<ProductDto> productDtoList = new ArrayList<ProductDto>();

        if(productList!=null && !productList.isEmpty()){
            for(ShoppingCartProduct scp :shoppingCart.getShoppingCartProducts()){
                for(Product product:productList){
                    if(product.getProdid().equals(scp.getSkuCode())){
                        productList.remove(product);
                        break;
                    }
                }
            }
            for(Product product :productList){
                ProductDto productDto = new ProductDto(product);
                productDto.setProductTypes(plubasInfoService.getNcAttributes(product.getProdid()));
                productDtoList.add(productDto);
            }
        }


        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", JsonUtil.jsonArrayWithFilterObject(productDtoList, ""));
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
    }

    @ResponseBody
    @RequestMapping(value = "shoppingCartProductSelectChange/{shoppingCartId}/{selected}", method = RequestMethod.GET)
    public String shoppingCartProductSelectChange(@PathVariable("shoppingCartId") Long shoppingCartId,
                                    @PathVariable("selected") boolean selected) {
        try {
            shoppingCartService.shoppingCartProductSelectChange(shoppingCartId, selected);
            return "success";
        } catch (Exception e) {
            return "fail";
        }
    }


    /**
     * 查询短信模版
     * @param themeTemplate
     * @param response
     */
    @RequestMapping(value = "getSmsTemple", method = RequestMethod.POST)
    public void getSmsTemple(String themeTemplate, HttpServletResponse response) {
       // AgentUser user = SecurityHelper.getLoginUser();
        Map<String, String> map = new HashMap<String, String>();
        try {
           // List<SmsTemplate> smsTemplateList = smsApiService.getSmsTemplateList(user.getDepartment(), themeTemplate);
           List<SmsTemplate> smsTemplateList = smsApiService.getSmsTemplateList(null, themeTemplate);
            if (smsTemplateList == null || smsTemplateList.isEmpty()) {
                map.put("message", "查询短信模版为空");
            } else {
                map.put("result", JSONArray.fromObject(smsTemplateList).toString());
            }
        } catch (Exception e) {
            logger.error("查询短信模板失败", e);
            map.put("message", "查询短信模板失败");

        }
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
    }

    @Autowired
    private OrderhistCompanyAssignService orderhistCompanyAssignService;
    @Autowired
    private CompanyDeliverySpanService companyDeliverySpanService;

    @RequestMapping(value = "getOrderMessage", method = RequestMethod.POST)
    public void getOrderMessage(Long shoppingCartId, Order pageOrder, String addressId, HttpServletResponse response) {
        AgentUser user = SecurityHelper.getLoginUser();
        Map<String, Object> map = new HashMap<String, Object>();
        BigDecimal mailPrice = BigDecimal.ZERO ;
        String deliveryDays = null;
        try {
            Order order = shoppingCartService.createMailOrderbyShoppingCart(shoppingCartId, user.getUserId(), user.getDefGrp());
            this.setOrderValue(pageOrder, addressId, order, null, null);

            mailPrice = getMailPrice(addressId, user, order);
            deliveryDays = getDeliveryDays(pageOrder, order);
        } catch (ErpException e) {
            logger.error("计算运费设置订单数据失败ErpException:", e);
        } catch (Exception e) {
            logger.error("计算运费设置订单数据失败Exception:", e);
        }

        map.put("mailPrice", mailPrice);
        map.put("deliveryDays", deliveryDays);

        HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
    }
    private BigDecimal getMailPrice(String addressId, AgentUser user,Order order){
        try {
            if(StringUtils.isNotBlank(addressId)){
                //获取运费
                if(!order.getOrderdets().isEmpty()){
                    BigDecimal mailPrice = orderhistService.calcOrderMailPrice(order, user.getDepartment());
                    return  mailPrice;
                }
            }
        } catch (ErpException e) {
            logger.error("获取运费失败ErpException:", e);
        } catch (Exception e) {
            logger.error("获取运费失败Exception:", e);
        }
        return  BigDecimal.ZERO;
    }

    private String getDeliveryDays(Order pageOrder, Order order) {
        try {
            order.setOrderid("-1");
            Long orderType = StringUtils.isNotBlank(pageOrder.getOrdertype()) ? Long.parseLong(pageOrder.getOrdertype()) : null;
            Long payType = StringUtils.isNotBlank(order.getPaytype()) ? Long.parseLong(order.getPaytype()) : null;

            AddressExt address = order.getAddress();
            Long provinceId = null == address.getProvince() ? null : Long.parseLong(address.getProvince().getProvinceid());
            Long cityId = null == address.getCity() ? null : new Long(address.getCity().getCityid());
            Long countyId = null == address.getCounty() ? null : new Long(address.getCounty().getCountyid());
            Long areaId = null == address.getArea() ? null : new Long(address.getArea().getAreaid());

            OrderhistAssignInfo oai = orderhistCompanyAssignService.queryDefaultAssignInfo(order);

            CompanyDeliverySpan cds = companyDeliverySpanService
                    .getDeliverySpan(oai.getEntityId(), orderType, payType, provinceId, cityId, countyId, areaId);
            if (cds != null) {
                return cds.getUserDef1();
            }

        } catch (Exception e) {
            logger.error("获取运费失败Exception:", e);
        }

        return "";
    }


    private boolean getMailPriceChangePermission(){
        FieldPermission fieldPermission = PermissionHelper.getFieldPermission("mailprice");
        if(fieldPermission!=null && fieldPermission.getIs_editable() !=null){
            if( fieldPermission.getIs_editable()!=0){
                return true;
            }
        }
        return  false;
    }

    /**
     * 创建订单
     * @param shoppingCartId
     * @param pageOrder 页面订单信息，运费为真实运费
     * @param addressId 收获id
     * @param freight  页面运费
     * @param leadId
     * @param response
     */
    @RequestMapping(value = "createOrder",method = RequestMethod.POST)
    public void createOrder(Long shoppingCartId, Order pageOrder, String addressId, BigDecimal freight, Long leadId,String extraCode, String parentOrderId, HttpServletResponse response) {
        AgentUser user = SecurityHelper.getLoginUser();
        Map<String, String> map = new HashMap<String, String>();

        kfServicesContext.setParentOrderId(parentOrderId);

        try {
            Order order = shoppingCartService.createOrderbyShoppingCart(shoppingCartId,user.getUserId(),user.getDefGrp());

            boolean bRight=false;
            if(leadId!=null)
                bRight=true;
            else
            {
                if(AgentUserHelpler.canCreateOrderNoLead(user)&&StringUtils.isNotBlank(parentOrderId))
                    bRight=true;
            }
            if (order != null && bRight==true) {
                boolean mailPriceChangePermission = getMailPriceChangePermission();
                if (mailPriceChangePermission) {
                    pageOrder.setMailprice(freight);
                }
                if(pageOrder.getMailprice()==null){
                    pageOrder.setMailprice(BigDecimal.ZERO);
                }
                this.setOrderValue(pageOrder, addressId, order,extraCode, parentOrderId);

                if (mailPriceChangePermission || pageOrder.getMailprice().compareTo(freight) == 0) {
                    freight = null;
                }
                OrderCreateResult orderCreateResult = orderhistService.createOrderExt(order, freight);
                map.put("result", order.getOrderid());
                map.put("audit",String.valueOf(orderCreateResult.isAudit()));
                this.messageSend(user, order, orderCreateResult);

            } else {
                map.put("message", "针对该客户无法生成新订单");
            }
        } catch (ErpException e) {
            logger.error("创建订单失败", e);
            if (StringUtils.isNotBlank(e.getMessage())) {
                map.put("message", e.getMessage());
            } else {
                map.put("message", "创建订单失败");
            }
        } catch (Exception e) {
            logger.error("创建订单失败", e);
            map.put("message", "创建订单失败");
        }
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
    }


    private void messageSend(AgentUser user, Order order, OrderCreateResult orderCreateResult) {
        try {
            if (orderCreateResult.isAudit()) {
                SysMessage msg = getSysMessage(user, order,orderCreateResult);
                sysMessageService.addMessage(msg);
            } else {
                smsService.sendOrderMessage(user, order);
            }
        } catch (Exception ex) {
            logger.error("订单创建后续处理sendMessage失败", ex);
        }
    }

    @RequestMapping(value = "afterCreateOrder",method = RequestMethod.POST)
    public void afterCreateOrder(Long shoppingCartId, Long leadId, String orderId, HttpServletResponse response) {
        AgentUser user = SecurityHelper.getLoginUser();
        StringBuffer message = new StringBuffer();
        try {
            shoppingCartService.deleteShoppingCartById(shoppingCartId);
        } catch (Exception ex) {
            logger.error("订单创建后续处理购物车失败", ex);
            message.append("订单创建后续处理购物车失败");
        }
        if(leadId!=null)
        {
            try {
                Lead lead = new Lead();
                lead.setId(leadId);
                lead.setLastOrderId(Long.valueOf(orderId));
                lead.setUpdateDate(new Date());
                lead.setUpdateUser(user.getUserId());
                lead.setStatus(5L);
                lead.setPriority(2L);
                leadService.updateLead(lead);
            } catch (Exception ex) {
                logger.error("订单创建后续处理Lead失败", ex);
                message.append("订单创建后续处理线索失败");
            }

            try {
                leadInterActionService.updateLeadInteraction(leadId, "8");
            } catch (Exception ex) {
                logger.error("订单创建后续处理LeadInteraction失败", ex);
                message.append("订单创建后续处理线索交互历史失败");
            }
            try {
                LeadInteraction leadInteraction = leadInterActionService.getLatestPhoneInterationByLeadId(leadId);
                LeadInteractionOrder leadInteractionOrder = new LeadInteractionOrder();
                leadInteractionOrder.setOrderId(orderId);
                leadInteractionOrder.setCreateUser(user.getUserId());
                leadInteractionOrder.setCreateDate(new Date());
                leadInteractionOrder.setLeadInteraction(leadInteraction);
                leadInteractionOrderService.save(leadInteractionOrder);
            } catch (Exception ex) {
                logger.error("订单创建后续处理LeadInteractionOrder失败", ex);
                message.append("订单创建后续处理线索交互历史订单失败");
            }
        }

        HttpUtil.ajaxReturn(response, jsonBinder.toJson(message.toString()), "application/json");
    }

    private SysMessage getSysMessage(AgentUser user, Order order,OrderCreateResult orderCreateResult) {
        SysMessage msg = new SysMessage();
        if(AuditTaskType.ORDERADD.equals(orderCreateResult.getAuditTaskType()) ){
            msg.setSourceTypeId(String.valueOf(MessageType.ADD_ORDER.getIndex()));
        }else{
            msg.setSourceTypeId(String.valueOf(MessageType.MODIFY_ORDER.getIndex()));
        }
        msg.setRecivierGroup(user.getDefGrp());
        msg.setDepartmentId(user.getDepartment());
        msg.setContent(order.getOrderid());
        msg.setCreateUser(SecurityHelper.getLoginUser().getUserId());
        msg.setCreateDate(new Date());
        return msg;
    }

    private void setOrderValue(Order pageOrder, String addressId,Order order,String extraCode, String parentOrderId) throws ServiceException {
        AddressDto addressDto = addressService.queryAddress(addressId);
        if(addressDto.getAreaid() ==null){
            throw  new  ServiceException("收货地址四级对应不正确，请确认");
        }
        AreaAll areaAll = areaService.get(addressDto.getAreaid());
        if(areaAll==null  || areaAll.getAreaid() ==0  || areaAll.getCountyid() ==0
                || !addressDto.getCountyId().equals(areaAll.getCountyid())
                || !addressDto.getCityId().equals(Integer.valueOf(areaAll.getCityid()))
                || !addressDto.getState().equals(areaAll.getProvid()) ){
            throw  new  ServiceException("收货地址四级对应不正确，请确认");
        }

        Integer spellid = addressDto.getAreaSpellid() == null ? addressDto.getCountySpellid() : addressDto.getAreaSpellid();
        order.setSpellid(spellid);

        order.setGetcontactid(addressDto.getContactid());
        if ("11".equals(pageOrder.getPaytype())) {
            order.setPaycontactid(order.getContactid());
        } else {
            order.setPaycontactid(addressDto.getContactid());
        }
        order.setProvinceid(addressDto.getState());
        order.setCityid(addressDto.getCity());

        AddressExt addressExt = this.getAddressExt(addressId, addressDto, areaAll);
        order.setAddress(addressExt);

        if(pageOrder.getMailprice()!=null){
            order.setMailprice(pageOrder.getMailprice());
            order.setNowmoney(order.getProdprice().add(pageOrder.getMailprice()));
            order.setTotalprice(order.getProdprice().add(pageOrder.getMailprice()));
        }

        Ems ems = emsService.getEmsBySpellid(spellid);
        if (ems != null) {
            order.setCompanyid(String.valueOf(ems.getCompanyid()));
        }

        order.setInvoicetitle(pageOrder.getInvoicetitle());
        order.setNote(pageOrder.getNote());
        order.setPaytype(pageOrder.getPaytype());
        order.setOrdertype(pageOrder.getOrdertype());
        order.setCardnumber(pageOrder.getCardnumber());
        if(StringUtils.isNotBlank(pageOrder.getCardnumber()) && StringUtils.isNotBlank(extraCode)){
            cardService.updateExtraCode(Long.parseLong(pageOrder.getCardnumber()),extraCode);
        }
        order.setCardtype(pageOrder.getCardtype());
        order.setLaststatus(pageOrder.getLaststatus());

        BigDecimal totalPoint = new BigDecimal(0);
        //设置运费
        Set<OrderDetail> orderdets = order.getOrderdets();
        boolean contain = false;
        for (OrderDetail orderDetail : orderdets) {
            orderDetail.setProvinceid(addressDto.getState());
            orderDetail.setState(addressDto.getProvinceName());
            orderDetail.setCity(addressDto.getCityName());
            orderDetail.setFreight(BigDecimal.ZERO);
            if ("1".equals(orderDetail.getSoldwith())) {
                contain = true;
                orderDetail.setFreight(order.getMailprice());
            }
            totalPoint = totalPoint.add(new BigDecimal(orderDetail.getJifen()));
        }
        order.setJifen(totalPoint.toString());

        if (!contain) {
            for (OrderDetail orderDetail : orderdets) {
                orderDetail.setFreight(order.getMailprice());
                break;
            }
        }

        if(StringUtils.isNotBlank(parentOrderId))
            order.setParentid(parentOrderId);

    }

    private AddressExt getAddressExt(String addressId, AddressDto addressDto, AreaAll areaAll) {
        AddressExt addressExt = new AddressExt();
        addressExt.setAddressId(addressId);
        Province province =new Province();
        province.setProvinceid(addressDto.getState());
        addressExt.setProvince(province);
        CityAll cityAll = new CityAll();
        cityAll.setCityid(addressDto.getCityId());
        addressExt.setCity(cityAll);
        CountyAll countyAll =new CountyAll();
        countyAll.setCountyid(addressDto.getCountyId());
        addressExt.setCounty(countyAll);
        addressExt.setArea(areaAll);
        return addressExt;
    }

    /**
     * 潜客购物车升级
     * @param potentialContactCode
     * @param contactCode
     * @param response
     */
    @RequestMapping(value = "updateShoppingCartContact/{potentialContactCode}/{contactCode}", method = RequestMethod.GET)
    public void updateShoppingCartContact(@PathVariable("potentialContactCode")Long potentialContactCode,
                                          @PathVariable("contactCode")Long contactCode,HttpServletResponse response) {
        String  message="";
        try {
            shoppingCartService.updateShoppingCartContact(potentialContactCode,contactCode);
        }catch (Exception e){
           logger.error("更新购物车失败", e);
            message="潜客升级购物车失败";
        }
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(message), "application/json");
    }

    /**
     * 验证购物车产品
     * @param shoppingCartId
     * @param response
     */
    @RequestMapping(value = "validateShoppingCart/{shoppingCartId}", method = RequestMethod.GET)
    public void validateShoppingCart(@PathVariable("shoppingCartId") Long shoppingCartId, @RequestParam(required=false) String parentOrderId, HttpServletResponse response) {
        kfServicesContext.setParentOrderId(parentOrderId);
        String message = null;
        try {
            AgentUser user = SecurityHelper.getLoginUser();
            shoppingCartService.validateShoppingCart(shoppingCartId,user.getUserId(),user.getDefGrp(),user.hasRole(SecurityConstants.ROLE_APPROVE_MANAGER));
        } catch (ErpException e) {
            message = e.getMessage();
        } catch (Exception e) {
            logger.error("购物车验证失败", e);
            message = "购物车验证失败,请刷新重试";
        }

        HttpUtil.ajaxReturn(response, jsonBinder.toJson(message), "application/json");
    }

    /**
     * 修改运费权限
     * @param response
     */
    @RequestMapping(value = "getMailPricePermission", method = RequestMethod.GET)
    public  void  getFieldPermission(HttpServletResponse response){
        boolean  mailPriceChangePermission = getMailPriceChangePermission();
        HttpUtil.ajaxReturn(response,jsonBinder.toJson(mailPriceChangePermission), "application/json");
    }

    /**
     * 删除购物车
     * @param response
     * @param contactId
     * @param cartType
     */
    @RequestMapping(value = "deleteShoppingCart/{contactId}/{cartType}", method = RequestMethod.GET)
    public void getFieldPermission(HttpServletResponse response, @PathVariable("contactId") Long contactId, @PathVariable("cartType") String cartType) {
        String message = null;
        try {
            shoppingCartService.deleteShoppingCartByContactId(contactId, cartType);
        } catch (Exception e) {
            logger.error("购物车删除失败", e);
            message = "取消修改失败";
        }
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(message), "application/json");
    }

    //赠险判断
    @RequestMapping(value = "insureProductValidate/{shoppingCartId}", method = RequestMethod.GET)
    public  void   insureProductValidate(HttpServletResponse response, @PathVariable("shoppingCartId") Long shoppingCartId){
        String message = null;
        try {
            AgentUser user = SecurityHelper.getLoginUser();
            message = shoppingCartService.insureProductValidate(shoppingCartId,user.getDefGrp());
        } catch (Exception e) {
            logger.error("购物车赠险判断失败", e);
            message = "";
        }
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(message), "application/json");

    }
}
