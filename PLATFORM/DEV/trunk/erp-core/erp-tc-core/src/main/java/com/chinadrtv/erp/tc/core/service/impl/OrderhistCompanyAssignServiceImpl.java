package com.chinadrtv.erp.tc.core.service.impl;

import java.math.BigDecimal;
import java.util.*;

import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.model.trade.ShipmentDetail;
import com.chinadrtv.erp.model.trade.ShipmentHeader;
import com.chinadrtv.erp.tc.core.constant.AccountStatusConstants;
import com.chinadrtv.erp.tc.core.constant.OrderAssignConstants;
import com.chinadrtv.erp.tc.core.constant.cache.OrderAssignErrorCode;
import com.chinadrtv.erp.tc.core.dao.OrderAssignRuleDao;
import com.chinadrtv.erp.tc.core.dao.ShipmentHeaderDao;
import com.chinadrtv.erp.tc.core.model.CompanyAllocation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.model.AreaGroup;
import com.chinadrtv.erp.model.AreaGroupDetail;
import com.chinadrtv.erp.model.Company;
import com.chinadrtv.erp.model.OrderAssignRule;
import com.chinadrtv.erp.model.OrderChannel;
import com.chinadrtv.erp.model.OrderPayType;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.tc.core.constant.ShipmentConstants;
import com.chinadrtv.erp.tc.core.constant.model.OrderhistAssignInfo;
import com.chinadrtv.erp.tc.core.service.AreaGroupService;
import com.chinadrtv.erp.tc.core.service.CompanyQuotaService;
import com.chinadrtv.erp.tc.core.service.CompanyService;
import com.chinadrtv.erp.tc.core.service.OrderChannelService;
import com.chinadrtv.erp.tc.core.service.OrderSkuSplitService;
import com.chinadrtv.erp.tc.core.service.OrderhistCompanyAssignService;
import com.chinadrtv.erp.tc.core.utils.OrderAddressChecker;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 13-2-18
 */
@Service
public class OrderhistCompanyAssignServiceImpl implements OrderhistCompanyAssignService {

    private static Logger logger  = LoggerFactory.getLogger(OrderhistCompanyAssignServiceImpl.class);


    @Autowired
    private AreaGroupService areaGroupService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private OrderChannelService orderChannelService;

    public OrderhistAssignInfo findCompanyFromRule(Order orderhist)
    {
        if(!OrderAddressChecker.isValidAddress(orderhist))
        {
            return this.getNotMatchOrderHistAssignInfo(orderhist, OrderAssignErrorCode.ADDRESS_NOT_VALID, OrderAssignErrorCode.ADDRESS_NOT_VALID_DSC);
        }
        //查找算法，首先根据订单类型和付款类型，以及对应的地址信息来查找


        //首先根据地址信息来找到地址组
        /*AreaGroupDetail areaGroupDetail=new AreaGroupDetail();
        areaGroupDetail.setCityId(orderhist.getAddress().getCity().getCityid());
        areaGroupDetail.setCountyId(orderhist.getAddress().getCounty().getCountyid());
        areaGroupDetail.setAreaId(orderhist.getAddress().getArea().getAreaid());
        areaGroupDetail.setProvinceId(Integer.parseInt(orderhist.getAddress().getProvince().getProvinceid()));

        AreaGroup areaGroup=areaGroupService.getFromAreaGroupDetail(areaGroupDetail);*/
        //设置标志，表明此订单已经自动处理过了
        //orderhist.setAssign(true);
        List<OrderAssignRule> validOrderAssignRuleList = orderAssignRuleDao.getAllValidRules();
        List<Long> channelIdList=this.getOrderChannelList(orderhist);
        List<Long> areaGroupIdList=this.getAreaGroupList(orderhist);

        if(areaGroupIdList==null || areaGroupIdList.size()==0)
        {
            //没有设置地址
            return this.getNotMatchOrderHistAssignInfo(orderhist, OrderAssignErrorCode.ADDRESS_NOT_FOUND,OrderAssignErrorCode.ADDRESS_NOT_FOUND_DSC);
        }

        logger.debug("find channel:"+ orderhist.getOrderid());

        //根据订单信息，查找渠道
        //OrderPayType orderPayType=this.getOrderPayTypeFromOrder(orderhist);
        //List<OrderChannel> orderChannelList= orderChannelService.getOrderChannelFromOrderPayType(orderPayType);
        if(channelIdList==null||channelIdList.size()<=0)
        {
            OrderPayType orderPayType=this.getOrderPayTypeFromOrder(orderhist);
            //检查是否有订单类型、支付类型
            if(!orderChannelService.isExistOrderType(orderPayType.getOrderTypeId()))
            {
                this.getNotMatchOrderHistAssignInfo(orderhist, OrderAssignErrorCode.ORDER_TYPE_NOT_FOUND, OrderAssignErrorCode.ORDER_TYPE_NOT_FOUND_DSC);
            }
            else if(!orderChannelService.isExistPayType(orderPayType.getPayTypeId()))
            {
                return this.getNotMatchOrderHistAssignInfo(orderhist, OrderAssignErrorCode.ORDER_PAY_NOT_FOUND, OrderAssignErrorCode.ORDER_PAY_NOT_FOUND_DSC);
            }
            else
            {
                return this.getNotMatchOrderHistAssignInfo(orderhist, OrderAssignErrorCode.CHANNEL_NOT_FOUND, OrderAssignErrorCode.CHANNEL_NOT_FOUND_DSC);
            }
        }

        logger.debug("find rule:"+ orderhist.getOrderid());

        CompanyAllocation companyAllocation=this.findRuleInfoFromOrderCondition(validOrderAssignRuleList,orderhist, areaGroupIdList,channelIdList);

        if(companyAllocation.isSucc()==false)
        {
            //没有对应规则
            return this.getNotMatchOrderHistAssignInfo(orderhist, OrderAssignErrorCode.RULE_NOT_FOUND, OrderAssignErrorCode.RULE_NOT_FOUND_DSC);
        }
        //根据渠道和地址组来查找匹配的规则
        /*Set<OrderAssignRule> orderAssignRules =  areaGroup.getOrderAssignRules();
        if(orderAssignRules==null)
        {
            //没有对应规则
            return this.getNotMatchOrderHistAssignInfo(orderhist, OrderAssignErrorCode.RULE_NOT_FOUND, OrderAssignErrorCode.RULE_NOT_FOUND_DSC);
        }

        logger.debug("find match rule:"+ orderhist.getOrderid());

        //从地址匹配的规则中，找到渠道匹配的规则
        List<OrderAssignRule> matchOrderAssignRuleList = new ArrayList<OrderAssignRule>();
        for(OrderAssignRule orderAssignRule:orderAssignRules)
        {
            //首先检查有效性（根据有效标记和有效时间判断）
            if(orderAssignRule.getOrderChannel()!=null)
            {
                if(isValidOrderAssignRule(orderAssignRule))
                {
                    for(OrderChannel orderChannel: orderChannelList)
                    {
                        if(orderChannel.getId().equals(orderAssignRule.getOrderChannel().getId()))
                        {
                            matchOrderAssignRuleList.add(orderAssignRule);
                        }
                    }
                }
            }
        }*/

        OrderAssignRule orderAssignRule=null;
        if(companyAllocation.getOrderAssignRuleList()==null|| companyAllocation.getOrderAssignRuleList().size()==0)
        {
            //没有匹配的规则
            return this.getNotMatchOrderHistAssignInfo(orderhist, OrderAssignErrorCode.RULE_NOT_FOUND, OrderAssignErrorCode.RULE_NOT_FOUND_DSC);

        }
        else
        {
            //找优先级最高的（值最小的）
            orderAssignRule=this.findPriorityRule(companyAllocation.getOrderAssignRuleList());
            if(orderAssignRule==null)
                orderAssignRule=companyAllocation.getOrderAssignRuleList().get(0);
            //匹配规则重复
            //return this.getNotMatchOrderHistAssignInfo(orderhist, OrderAssignErrorCode.RULE_CONFIG_ERROR,OrderAssignErrorCode.RULE_CONFIG_ERROR_DSC);
        }

        //OrderAssignRule orderAssignRule=matchOrderAssignRuleList.get(0);
        Long warehouseId=1L;
        String warehouseName="";
        String companyId="";
        if(orderAssignRule.isStockFlag()!=null&&orderAssignRule.isStockFlag()==true)
        {
            //TODO:判断仓库是否有货，如果没有货，那么从第二个仓库发货
            //目前版本直接报错，后期版本添加此功能
            logger.debug("not stock function:"+ orderhist.getOrderid());
            return this.getNotMatchOrderHistAssignInfo(orderhist, OrderAssignErrorCode.STOCK_CHECK_NOT_SUPPORT,OrderAssignErrorCode.STOCK_CHECK_NOT_SUPPORT_DSC);
        }
        else
        {
            //
            if(orderAssignRule.getEntityId()==null||orderAssignRule.getWarehouseId()==null||orderAssignRule.getWarehouseName()==null)
            {
                return this.getNotMatchOrderHistAssignInfo(orderhist, OrderAssignErrorCode.RULE_COMPANY_CONFIG_ERROR,OrderAssignErrorCode.RULE_COMPANY_CONFIG_ERROR_DSC);
            }
            warehouseId=orderAssignRule.getWarehouseId();
            warehouseName=orderAssignRule.getWarehouseName();
            companyId=orderAssignRule.getEntityId().toString();
        }

        logger.debug("find company:"+ orderhist.getOrderid());
        //获取送货公司信息
        Company company=companyService.findCompany(companyId);
        if(company==null)
        {
            return this.getNotMatchOrderHistAssignInfo(orderhist, OrderAssignErrorCode.COMPANY_NOT_FOUND, OrderAssignErrorCode.COMPANY_NOT_FOUND_DSC);
        }
        else
        {
            return this.getMatchOrderHistAssignInfo(orderhist,orderAssignRule.getId().toString(),orderAssignRule.getName(),warehouseId,warehouseName,company);
        }
    }

    private OrderhistAssignInfo getMatchOrderHistAssignInfo(Order orderhist,String ruleId,String ruleName,Long warehouseId,String warehouseName, Company company)
    {
        //设置订单信息
        OrderhistAssignInfo orderHistAssignInfo=new OrderhistAssignInfo();
        orderHistAssignInfo.setSucc(true);
        orderHistAssignInfo.setEntityId(company.getCompanyid());
        orderHistAssignInfo.setMailType(company.getMailtype());
        orderHistAssignInfo.setRuleId(ruleId);
        orderHistAssignInfo.setRuleName(ruleName);
        orderHistAssignInfo.setWarehouseId(warehouseId);
        orderHistAssignInfo.setWarehouseName(warehouseName);

        return orderHistAssignInfo;
    }

    private OrderhistAssignInfo getNotMatchOrderHistAssignInfo(Order orderhist,String errorCodeId, String errorCodeDesc)
    {
        OrderhistAssignInfo orderHistAssignInfo=new OrderhistAssignInfo();
        orderHistAssignInfo.setSucc(false);
        orderHistAssignInfo.setErrorMsg(errorCodeDesc);
        orderHistAssignInfo.setErrorId(errorCodeId);

        return orderHistAssignInfo;
    }

    private OrderPayType getOrderPayTypeFromOrder(Order orderhist)
    {
        OrderPayType orderPayType=new OrderPayType();
        orderPayType.setPayTypeId(Long.parseLong(orderhist.getPaytype()));
        orderPayType.setOrderTypeId(Long.parseLong(orderhist.getOrdertype()));
        return orderPayType;
    }

    private boolean isValidOrderAssignRule(OrderAssignRule orderAssignRule)
    {
        if(orderAssignRule.isActive()==null||orderAssignRule.isActive()==true)
        {
            //判断时间
            Date currentDate=new Date();
            if(orderAssignRule.getStartDate()!=null)
            {
                if(currentDate.compareTo(orderAssignRule.getStartDate())<0)
                {
                    return false;
                }
            }
            if(orderAssignRule.getEndDate()!=null)
            {
                if(currentDate.compareTo(orderAssignRule.getEndDate())>0)
                {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    /*
      * 根据订单地址匹配仓库与送货公司
     * <p>Title: 根据订单地址匹配仓库与送货公司</p>
     * <p>Description: </p>
     * @param orderhist
     * @return
     * @see com.chinadrtv.erp.shipment.service.OrderhistCompanyAssignService#queryDefaultAssignInfo(com.chinadrtv.erp.model.agent.Orderhist)
     */
    public OrderhistAssignInfo queryDefaultAssignInfo(Order orderhist) {
        OrderhistAssignInfo ohai= this.findCompanyFromRule(orderhist);
        if (!ohai.isSucc()) {
            logger.info("产生运单调用地址规则失败，将核匹配默认仓库与送货公司");
            ohai.setWarehouseId(new Long(ShipmentConstants.DEFAULT_WAREHOUSE));
            ohai.setEntityId(ShipmentConstants.DEFAULT_ENTITY);
        }
        return ohai;
    }

    @Autowired
    private CompanyQuotaService companyQuotaService;

    @Autowired
    private ShipmentHeaderDao shipmentHeaderDao;

    @Autowired
    private OrderSkuSplitService orderSkuSplitService;

    @Autowired
    private OrderAssignRuleDao orderAssignRuleDao;

    /**
     * 查找满足特定条件的规则
     * 目前只支持金额和商品
     * @param orderAssignRuleList
     * @param orderhist
     * @param areaGroupIdList
     * @param orderChannelIdList
     * @return
     */
    public OrderAssignRule findRuleInfoFromSpecCondition(List<OrderAssignRule> orderAssignRuleList, Order orderhist, List<Long> areaGroupIdList, List<Long> orderChannelIdList)
    {
        //规则处理分两种情况，一直接读取数据库、二缓存(目前使用缓存所有的规则，因为规则不会太多，最多几千条，而且对于批次订单处理，首先缓存到本地批次上下文中，不会每条都去读取缓存)
        //首先判断金额和商品，如果有满足的条件，那么直接返回（注意优先级）
        //如果是老的订单，没有发运单，那么需要拆分来获取商品12位编码

        //拆分产品，获取12编码
        List<OrderAssignRule> matchOrderAssignRuleList = new ArrayList<OrderAssignRule>();
        List<String> prods = this.getProdsFromOrder(orderhist);
        for(OrderAssignRule orderAssignRule:orderAssignRuleList)
        {
            if(!this.isValidConditonOrderAssignRule(orderAssignRule))
            {
                logger.error("order assign rule is error:"+orderAssignRule.getId());
                continue;
            }

            int matchFlag=0;
            if(orderAssignRule.getMaxAmount()!=null||orderAssignRule.getMinAmount()!=null)
            {
                BigDecimal maxAmount=orderAssignRule.getMaxAmount()!=null ? orderAssignRule.getMaxAmount() : new BigDecimal("9999999999999");
                BigDecimal minAmount=orderAssignRule.getMinAmount()!=null ? orderAssignRule.getMinAmount() : new BigDecimal("-1");

                if(orderhist.getTotalprice().compareTo(maxAmount)<=0 && orderhist.getTotalprice().compareTo(minAmount)>=0)
                {
                    matchFlag=100;
                }
                else
                {
                    continue;
                }
            }

            if(StringUtils.isNotEmpty(orderAssignRule.getProdCode()))
            {
                if(prods.contains(orderAssignRule.getProdCode()))
                {
                    matchFlag+=10;
                }
                else
                {
                    continue;
                }
            }

            if(matchFlag>0)
            {
                if(orderAssignRule.getAreaGroup()!=null&&orderAssignRule.getAreaGroup().getId()!=null)
                {
                     if(!areaGroupIdList.contains(orderAssignRule.getAreaGroup().getId()))
                     {
                         continue;
                     }
                }
                if(orderAssignRule.getOrderChannel()!=null&&orderAssignRule.getOrderChannel().getId()!=null)
                {
                    if(!orderChannelIdList.contains(orderAssignRule.getOrderChannel().getId()))
                    {
                        continue;
                    }
                }
            }

            if(matchFlag>0)
            {
                matchOrderAssignRuleList.add(orderAssignRule);
            }
        }

        if(matchOrderAssignRuleList.size()>0)
        {
            return findPriorityRule(matchOrderAssignRuleList);
        }
        return null;
    }

    private boolean isValidConditonOrderAssignRule(OrderAssignRule orderAssignRule)
    {
        if(orderAssignRule.getMaxAmount()==null&&orderAssignRule.getMinAmount()==null
                &&(orderAssignRule.getAreaGroup()==null||orderAssignRule.getAreaGroup().getId()==null)
            &&(orderAssignRule.getOrderChannel()==null||orderAssignRule.getOrderChannel().getId()==null))
        {
            return false;
        }

        return true;
    }

    private List<String> getProdsFromOrder(Order order)
    {
        List<String> prods=new ArrayList<String>();

        //如果没有运单，直接调用拆分
        List<ShipmentHeader> shipmentHeaderList = shipmentHeaderDao.getByOrderId(order.getOrderid());
        ShipmentHeader shipmentHeaderMatch=null;
        for(ShipmentHeader shipmentHeader:shipmentHeaderList)
        {
            if(AccountStatusConstants.ACCOUNT_NEW.equals(shipmentHeader.getAccountStatusId()))
            {
                //检查版本
                int orderVersion=order.getRevision()!=null?order.getRevision().intValue():0;
                int shipmentRefVersion=shipmentHeader.getOrderRefRevisionId()!=null?shipmentHeader.getOrderRefRevisionId().intValue():0;
                if(orderVersion!=shipmentRefVersion)
                {
                    logger.error("shipment is not match order:"+order.getOrderid());
                    //异常
                }
                else
                {
                    shipmentHeaderMatch=shipmentHeader;
                    break;
                }
            }
        }
        //否则直接从运单获取

        if(shipmentHeaderMatch!=null)
        {
            for(ShipmentDetail shipmentDetail:shipmentHeaderMatch.getShipmentDetails())
            {
                if(!prods.contains(shipmentDetail.getItemId()))
                {
                    prods.add(shipmentDetail.getItemId());
                }
            }
        }
        else
        {
            try
            {
                for(OrderDetail orderDetail: order.getOrderdets())
                {
                    List<Map<String, Object>> mapList = orderSkuSplitService.orderSkuSplit(orderDetail);
                    for(Map<String,Object> map:mapList)
                    {
                        String prod=map.get("plucode").toString();
                        if(!prods.contains(prod))
                        {
                            prods.add(prod);
                        }
                    }
                }
            }catch (Exception exp)
            {
               logger.error("prod split error:",exp);
            }
        }

        return prods;
    }

    /**
     * 复杂分拣
     * @param orderhistList
     * @return
     */
    public List<OrderhistAssignInfo> findOrderMatchRule(List<Order> orderhistList)
    {
        List<OrderhistAssignInfo> orderhistAssignInfoList=new ArrayList<OrderhistAssignInfo>();

        List<CompanyAllocation> orderMatchRuleInfoList=new ArrayList<CompanyAllocation>();

        List<OrderAssignRule> validOrderAssignRuleList = orderAssignRuleDao.getAllValidRules();

        for(Order orderhist:orderhistList)
        {
            if(!OrderAddressChecker.isValidAddress(orderhist))
            {
                logger.error("order address is invalid:"+orderhist.getOrderid());

                OrderhistAssignInfo orderhistAssignInfo=new OrderhistAssignInfo();
                orderhistAssignInfo.setErrorId(OrderAssignErrorCode.ADDRESS_NOT_VALID);
                orderhistAssignInfo.setErrorMsg(OrderAssignErrorCode.ADDRESS_NOT_VALID_DSC);
                orderhistAssignInfo.setOrder(orderhist);
                orderhistAssignInfo.setSucc(false);

                orderhistAssignInfoList.add(orderhistAssignInfo);
            }
            else
            {
                //首先检查是否手工指定的，如果是的，那么直接分拣
                if(OrderAssignConstants.HAND_ASSIGN.equals(orderhist.getIsassign()))
                {
                    logger.debug("order is hand assign:"+orderhist.getOrderid());

                    List<OrderAssignRule> orderAssignRuleList=new ArrayList<OrderAssignRule>();
                    OrderAssignRule orderAssignRule=new OrderAssignRule();
                    orderAssignRule.setId(0L);
                    orderAssignRule.setName("手工分拣");
                    orderAssignRule.setEntityId(Long.parseLong(orderhist.getEntityid()));
                    orderAssignRuleList.add(orderAssignRule);
                    CompanyAllocation companyAllocation=new CompanyAllocation(orderhist,orderAssignRuleList);
                    companyAllocation.setAssigned(true);

                    orderMatchRuleInfoList.add(companyAllocation);
                }
                else
                {
                    List<Long> channelIdList=this.getOrderChannelList(orderhist);
                    List<Long> areaGroupIdList=this.getAreaGroupList(orderhist);
                    //判断是否满足金额和商品规则条件
                    OrderAssignRule orderAssignRule = findRuleInfoFromSpecCondition(validOrderAssignRuleList, orderhist,areaGroupIdList,channelIdList);
                    if(orderAssignRule!=null)
                    {
                        logger.debug("order match spec rule:"+orderhist.getOrderid());
                        OrderhistAssignInfo orderhistAssignInfo=new OrderhistAssignInfo();
                        orderhistAssignInfo.setErrorId(OrderAssignErrorCode.MATCH_SPEC_CONDITION);
                        orderhistAssignInfo.setErrorMsg(OrderAssignErrorCode.MATCH_SPEC_CONDITION_DSC);
                        orderhistAssignInfo.setOrder(orderhist);
                        orderhistAssignInfo.setEntityId(orderAssignRule.getEntityId().toString());
                        Company company = companyService.findCompany(orderAssignRule.getEntityId().toString());
                        if(company==null)
                        {
                            orderhistAssignInfo.setErrorId(OrderAssignErrorCode.COMPANY_NOT_FOUND);
                            orderhistAssignInfo.setErrorMsg(OrderAssignErrorCode.COMPANY_NOT_FOUND_DSC);
                        }
                        else
                        {
                            orderhistAssignInfo.setMailType(company.getMailtype());
                            orderhistAssignInfo.setWarehouseId(company.getWarehouseId());
                        }
                        orderhistAssignInfo.setSucc(false);
                        orderhistAssignInfo.setRuleId(orderAssignRule.getId().toString());
                        orderhistAssignInfo.setRuleName(orderAssignRule.getName());

                        orderhistAssignInfoList.add(orderhistAssignInfo);
                    }
                    else
                    {
                        CompanyAllocation companyAllocation=findRuleInfoFromOrderCondition(validOrderAssignRuleList, orderhist,areaGroupIdList,channelIdList);
                        if(!companyAllocation.isSucc())
                        {
                            logger.error("find rule is error:"+orderhist.getOrderid()+"-"+companyAllocation.getErrorCode());

                            OrderhistAssignInfo orderhistAssignInfo=new OrderhistAssignInfo();
                            orderhistAssignInfo.setErrorId(companyAllocation.getErrorCode());
                            orderhistAssignInfo.setErrorMsg(companyAllocation.getErrorDesc());
                            orderhistAssignInfo.setSucc(false);
                            orderhistAssignInfo.setOrder(orderhist);

                            orderhistAssignInfoList.add(orderhistAssignInfo);
                        }
                        else
                        {
                            orderMatchRuleInfoList.add(companyAllocation);
                        }
                    }
                }
            }
        }

        //对于匹配成功的订单，需要根据配比和能力来分配承运商(并且判断最大配送能力)
        //获取当天承运商发货的订单(entityId -- 当天要分配的订单就是要发货的订单)
        //批处理而不是每条处理的原因是，避免可能出现来回改动承运商的情况（比如订单1的承运商A改成了B，然后订单2的承运商B改成了A）
        List<CompanyAllocation> companyAllocationList = companyQuotaService.readjustCompanysAllocation(orderMatchRuleInfoList);
        orderhistAssignInfoList.addAll(getAssignRuleFromAllocation(companyAllocationList));

        return orderhistAssignInfoList;
    }

    private List<OrderhistAssignInfo> getAssignRuleFromAllocation(List<CompanyAllocation> companyAllocationList)
    {
        List<OrderhistAssignInfo> orderhistAssignInfoList=new ArrayList<OrderhistAssignInfo>();
        for(CompanyAllocation companyAllocation : companyAllocationList)
        {
            OrderhistAssignInfo orderhistAssignInfo=new OrderhistAssignInfo();

            orderhistAssignInfo.setOrder(companyAllocation.getOrderhist());

            if(companyAllocation.isSucc())
            {
                orderhistAssignInfo.setSucc(true);
                //判断匹配的承运商是否和原先的一致
                if(companyAllocation.getMatchCompanyId().equals(companyAllocation.getCompanyId()))
                {
                    OrderAssignRule orderAssignRule= getAssignRuleFromMatchCompany(companyAllocation);
                    if(orderAssignRule!=null)
                    {
                        orderhistAssignInfo.setEntityId(companyAllocation.getMatchCompanyId().toString());
                        orderhistAssignInfo.setRuleId(orderAssignRule.getId().toString());
                        orderhistAssignInfo.setRuleName(orderAssignRule.getName());
                        orderhistAssignInfo.setWarehouseId(orderAssignRule.getWarehouseId());
                        orderhistAssignInfo.setWarehouseName(orderAssignRule.getWarehouseName());
                    }
                    else
                    {
                        //出现这样的情况，开始就没有找到匹配的规则，目前的处理没有报错，而是不修改承运商
                        orderhistAssignInfo.setEntityId(companyAllocation.getMatchCompanyId().toString());
                        orderhistAssignInfo.setRuleId("0");
                        orderhistAssignInfo.setErrorMsg("no rule match");
                    }
                }
                else
                {
                    if(StringUtils.isEmpty(companyAllocation.getSwappedOrderId()))
                    {
                        Company company = companyService.findCompany(companyAllocation.getMatchCompanyId().toString());
                        if(company==null)
                        {
                            logger.error("company is not found:"+companyAllocation.getMatchCompanyId());
                            orderhistAssignInfo.setErrorId(OrderAssignErrorCode.COMPANYCONTRACT_NOT_FOUND);
                            orderhistAssignInfo.setErrorMsg(OrderAssignErrorCode.COMPANYCONTRACT_NOT_FOUND_DSC+"-companyId:"+companyAllocation.getMatchCompanyId().toString());
                            orderhistAssignInfo.setSucc(false);
                        }
                        else
                        {
                            OrderAssignRule orderAssignRule= getAssignRuleFromMatchCompany(companyAllocation);

                            orderhistAssignInfo.setMailType(company.getMailtype());
                            orderhistAssignInfo.setEntityId(companyAllocation.getMatchCompanyId().toString());
                            orderhistAssignInfo.setRuleId(orderAssignRule.getId().toString());
                            orderhistAssignInfo.setRuleName(orderAssignRule.getName());
                            orderhistAssignInfo.setWarehouseId(orderAssignRule.getWarehouseId());
                            orderhistAssignInfo.setWarehouseName(orderAssignRule.getWarehouseName());
                        }
                    }
                    else
                    {
                        //将信息添加到描述中
                        OrderAssignRule orderAssignRule= getAssignRuleFromMatchCompany(companyAllocation);

                        orderhistAssignInfo.setEntityId(companyAllocation.getCompanyId().toString());
                        orderhistAssignInfo.setRuleId(orderAssignRule.getId().toString());
                        orderhistAssignInfo.setRuleName(orderAssignRule.getName());
                        orderhistAssignInfo.setWarehouseId(orderAssignRule.getWarehouseId());
                        orderhistAssignInfo.setWarehouseName(orderAssignRule.getWarehouseName());
                        orderhistAssignInfo.setErrorMsg("与订单："+companyAllocation.getSwappedOrderId()+" 发生置换");
                    }
                }
            }
            else
            {
                orderhistAssignInfo.setErrorId(companyAllocation.getErrorCode());
                orderhistAssignInfo.setErrorMsg(companyAllocation.getErrorDesc());
                orderhistAssignInfo.setSucc(false);
            }

            orderhistAssignInfoList.add(orderhistAssignInfo);
        }

        return orderhistAssignInfoList;
    }

    private OrderAssignRule getAssignRuleFromMatchCompany(CompanyAllocation companyAllocation)
    {
        if(companyAllocation.getMatchCompanyId()!=null&&companyAllocation.getOrderAssignRuleList()!=null)
        {
            for(OrderAssignRule orderAssignRule : companyAllocation.getOrderAssignRuleList())
            {
                if(companyAllocation.getMatchCompanyId().equals(orderAssignRule.getEntityId().intValue()))
                {
                    return orderAssignRule;
                }
            }
        }
        return null;
    }

    private List<Long> getOrderChannelList(Order order)
    {
        Long orderType=Long.parseLong(order.getOrdertype());
        Long payType=Long.parseLong(order.getPaytype());

        List<Long> channelIdList=orderChannelService.getChannelIdsFromOrderPayType(orderType, payType);
        if(channelIdList==null)
        {
            channelIdList=new ArrayList<Long>();
        }
        return channelIdList;
    }

    private List<Long> getAreaGroupList(Order order)
    {
        AreaGroupDetail areaGroupDetail=getAreaGroupDetailFromOrder(order);
        List<Long> areaGroupIdList= areaGroupService.getAreaGroupsFromDetail(areaGroupDetail);
        if(areaGroupIdList==null)
            areaGroupIdList=new ArrayList<Long>();
        return areaGroupIdList;
    }

    /**
     * 根据一般条件（地址、渠道）来获取规则
     * @param orderAssignRuleList
     * @param orderhist
     * @param areaGroupIdList
     * @param orderChannelIdList
     * @return
     */
    private CompanyAllocation findRuleInfoFromOrderCondition(List<OrderAssignRule> orderAssignRuleList, Order orderhist, List<Long> areaGroupIdList, List<Long> orderChannelIdList)
    {
        //根据一般条件（地址、渠道）来获取规则
        if(orderChannelIdList==null||orderChannelIdList.size()==0)
        {
            logger.error("channel is not found:"+orderhist.getOrderid());

            CompanyAllocation companyAllocation=new CompanyAllocation(orderhist,null);
            companyAllocation.setErrorCode(OrderAssignErrorCode.CHANNEL_NOT_FOUND);
            companyAllocation.setErrorDesc(OrderAssignErrorCode.CHANNEL_NOT_FOUND_DSC);
            return companyAllocation;
        }
        if(areaGroupIdList==null||areaGroupIdList.size()==0)
        {
            logger.error("area group is not found:"+orderhist.getOrderid());

            CompanyAllocation companyAllocation=new CompanyAllocation(orderhist,null);
            companyAllocation.setErrorCode(OrderAssignErrorCode.ADDRESS_NOT_FOUND);
            companyAllocation.setErrorDesc(OrderAssignErrorCode.ADDRESS_NOT_FOUND_DSC);
            return companyAllocation;
        }

        List<OrderAssignRule> matchOrderAssignRuleList =new ArrayList<OrderAssignRule>();
        if(orderAssignRuleList!=null)
        {
            for(OrderAssignRule orderAssignRule:orderAssignRuleList)
            {
                if( orderAssignRule.getMaxAmount()==null&& orderAssignRule.getMinAmount()==null&& orderAssignRule.getProdCode()==null)
                {
                    if(orderAssignRule.getOrderChannel()!=null&&orderAssignRule.getOrderChannel().getId()!=null
                            &&orderAssignRule.getAreaGroup()!=null&&orderAssignRule.getAreaGroup().getId()!=null)
                    {
                        if(orderChannelIdList.contains(orderAssignRule.getOrderChannel().getId())
                                &&areaGroupIdList.contains(orderAssignRule.getAreaGroup().getId()))
                        {
                            matchOrderAssignRuleList.add(orderAssignRule);
                        }
                    }
                }
            }
        }

        if(matchOrderAssignRuleList.size()==0)
        {
            logger.error("normal rule is not found:"+orderhist.getOrderid());
            CompanyAllocation companyAllocation=new CompanyAllocation(orderhist,null);
            companyAllocation.setErrorCode(OrderAssignErrorCode.RULE_NOT_FOUND);
            companyAllocation.setErrorDesc(OrderAssignErrorCode.RULE_NOT_FOUND_DSC);
            return companyAllocation;
        }
        return new CompanyAllocation(orderhist,matchOrderAssignRuleList);
    }

    private OrderAssignRule findPriorityRule(List<OrderAssignRule> orderAssignRuleList)
    {
        if(orderAssignRuleList==null)
        {
            return null;
        }
        OrderAssignRule matchOrderAssignRule = null;
        for(OrderAssignRule orderAssignRule:orderAssignRuleList)
        {
            if(matchOrderAssignRule==null)
            {
                matchOrderAssignRule=orderAssignRule;
            }
            else
            {
                if(getPriorityFromRule(matchOrderAssignRule).compareTo(getPriorityFromRule(orderAssignRule))>0)
                {
                    matchOrderAssignRule=orderAssignRule;
                }
            }
        }

        return matchOrderAssignRule;
    }

    private Long getPriorityFromRule(OrderAssignRule orderAssignRule)
    {
        if(orderAssignRule==null)
        {
            return 0L;
        }
        if(orderAssignRule.getPriority()==null)
        {
            return Long.MAX_VALUE;
        }
        else
        {
            return orderAssignRule.getPriority();
        }
    }

    private AreaGroupDetail getAreaGroupDetailFromOrder(Order orderhist)
    {
        AreaGroupDetail areaGroupDetail=new AreaGroupDetail();
        areaGroupDetail.setCityId(orderhist.getAddress().getCity().getCityid());
        areaGroupDetail.setCountyId(orderhist.getAddress().getCounty().getCountyid());
        areaGroupDetail.setAreaId(orderhist.getAddress().getArea().getAreaid());
        areaGroupDetail.setProvinceId(Integer.parseInt(orderhist.getAddress().getProvince().getProvinceid()));
        return areaGroupDetail;
    }

}
