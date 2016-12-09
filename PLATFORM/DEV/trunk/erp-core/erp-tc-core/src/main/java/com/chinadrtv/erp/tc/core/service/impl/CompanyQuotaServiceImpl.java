package com.chinadrtv.erp.tc.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.chinadrtv.erp.tc.core.model.CompanyAssignQuantity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.model.CompanyContract;
import com.chinadrtv.erp.tc.core.constant.cache.OrderAssignErrorCode;
import com.chinadrtv.erp.tc.core.dao.CompanyContractDao;
import com.chinadrtv.erp.tc.core.model.CompanyAllocation;
import com.chinadrtv.erp.tc.core.service.CompanyAssignQuantityService;
import com.chinadrtv.erp.tc.core.service.CompanyQuotaService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-4-21
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class CompanyQuotaServiceImpl implements CompanyQuotaService {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CompanyQuotaServiceImpl.class);

    @Autowired
    private CompanyContractDao companyContractDao;

    @Autowired
    private CompanyAssignQuantityService companyAssignQuantityService;

    private CompanyAssignQuantity getCompanyQuantityHelper(Long companyId, Map<Long,CompanyAssignQuantity> mapCompanyQuantity)
    {
        if(mapCompanyQuantity.containsKey(companyId))
        {
            return mapCompanyQuantity.get(companyId);
        }
        else
        {
            CompanyAssignQuantity companyAssignQuantity =companyAssignQuantityService.getCompanyAssignQuantity(companyId);
            if(companyAssignQuantity==null)
            {
                companyAssignQuantity=new CompanyAssignQuantity();
                companyAssignQuantity.setCompanyId(companyId);
            }
            else
            {
                companyAssignQuantity=companyAssignQuantity.copy();
            }
            mapCompanyQuantity.put(companyId, companyAssignQuantity);
            return companyAssignQuantity;
        }
    }

    private void updateCompanyQuantityHelper(Long companyId,Long addQuantity,BigDecimal addAmount, Map<Long,CompanyAssignQuantity> mapCompanyQuantity)
    {
        CompanyAssignQuantity companyAssignQuantity=null;
        if(mapCompanyQuantity.containsKey(companyId))
        {
            companyAssignQuantity=mapCompanyQuantity.get(companyId);
        }
        else{
            companyAssignQuantity=new CompanyAssignQuantity();
        }
        companyAssignQuantity.setQuantity(companyAssignQuantity.getQuantity()+addQuantity);
        companyAssignQuantity.setTotalPrice(companyAssignQuantity.getTotalPrice().add(addAmount));
        mapCompanyQuantity.put(companyId,companyAssignQuantity);
    }

    public List<CompanyAllocation> readjustCompanysAllocation(List<CompanyAllocation> companyAllocationList) {
        //根据配送比和配送能力来确定最终的承运商
        //避免可能出现来回改动承运商的情况（比如订单1的承运商A改成了B，然后订单2的承运商B改成了A）
        //目前算法，简单的顺序处理每条数据
        List<CompanyContract> companyContractList=this.getCompanyContracts(companyAllocationList);

        List<CompanyAllocation> matchCompanyAllocationList = new ArrayList<CompanyAllocation>();

        Map<Long,CompanyAssignQuantity> mapCompanyQuantity = new HashMap<Long, CompanyAssignQuantity>();

        for(CompanyAllocation companyAllocation : companyAllocationList)
        {
            //如果没有可选的，直接返回
            if(companyAllocation.getCompanyIdList() == null||companyAllocation.getCompanyIdList().size()==0)
            {
                //没有出现的情况
                logger.error("not company allocation company:"+companyAllocation.getOrderId());
            }
            else
            {
                if(companyAllocation.getCompanyIdList().size()==1&&companyAllocation.isAssigned()==true)
                {
                    Integer companyId=companyAllocation.getCompanyIdList().get(0);
                    companyAllocation.setMatchCompanyId(companyId);
                    this.updateCompanyQuantityHelper(companyId.longValue(),1L, companyAllocation.getOrderhist().getTotalprice(), mapCompanyQuantity);

                    matchCompanyAllocationList.add(companyAllocation);
                }
                else
                {
                    //根据配比系数来分配
                    CompanyAllocation matchCompanyAllocation=assignCompanyFromQuota(companyAllocation,companyContractList,mapCompanyQuantity);

                    if(matchCompanyAllocation!=null)
                    {
                        if(matchCompanyAllocation.isSucc())
                        {
                            //后续处理，避免不必要的来回更改
                            if(matchCompanyAllocation.getMatchCompanyId()!=null)
                            {
                                this.updateCompanyQuantityHelper(matchCompanyAllocation.getMatchCompanyId().longValue(),1L, matchCompanyAllocation.getOrderhist().getTotalprice(), mapCompanyQuantity);
                                adjustCompanyAssign(matchCompanyAllocation,matchCompanyAllocationList);
                            }
                        }
                        matchCompanyAllocationList.add(matchCompanyAllocation);
                    }
                }
            }
        }

        return matchCompanyAllocationList;
    }

    /*private Map<Integer,Integer> getCompanyAssignQuantitys()
    {
        List<CompanyAssignQuantity> companyAssignQuantityList=companyAssignQuantityxService.getAllCompanyAssignQuantity();
        Map<Integer,Integer> mapQuantity=new HashMap<Integer, Integer>();
        for(CompanyAssignQuantity companyAssignQuantity: companyAssignQuantityList)
        {
            mapQuantity.put(companyAssignQuantity.getCompanyId().intValue(),companyAssignQuantity.getQuantity().intValue());
        }
        return mapQuantity;
    }*/

    private void adjustCompanyAssign(CompanyAllocation companyAllocation,List<CompanyAllocation> companyAllocationList)
    {
        Integer matchCompanyId = companyAllocation.getMatchCompanyId();
        if(matchCompanyId==null || companyAllocation.getCompanyId().equals(matchCompanyId))
        {
            return;
        }
        for(CompanyAllocation companyAllocation1 : companyAllocationList)
        {
            if(companyAllocation1.isSucc() && StringUtils.isEmpty(companyAllocation1.getSwappedOrderId()))
            {
                Integer matchCompanyId1 = companyAllocation1.getMatchCompanyId();

                if(matchCompanyId.equals(companyAllocation1.getCompanyId())
                        &&companyAllocation.getCompanyId().equals(matchCompanyId1))
                {
                    logger.debug("swapp order:"+companyAllocation1.getOrderId()+"-"+companyAllocation.getOrderId());
                    companyAllocation.setSwappedOrderId(companyAllocation1.getOrderId());
                    companyAllocation1.setSwappedOrderId(companyAllocation.getOrderId());
                    return;
                }
            }
        }
    }

    private CompanyAllocation assignCompanyFromQuota(CompanyAllocation companyAllocation, List<CompanyContract> companyContractList,Map<Long,CompanyAssignQuantity> mapCompanyQuantity)
    {
        List<CompanyContract> matchCompanyContractList =new ArrayList<CompanyContract>();
        for(Integer companyId : companyAllocation.getCompanyIdList())
        {
            //boolean  bFind=false;
            for(CompanyContract companyContract : companyContractList)
            {
                if(companyContract.getId().equals(companyId))
                {
                    //忽略掉小于等于0的承运商配额

                    if(companyContract.getMatchingRatio()!=null&&companyContract.getMatchingRatio().intValue()>0)
                    {
                        logger.error("company id:"+companyContract.getId()+" -ratio is "+companyContract.getMatchingRatio());
                        matchCompanyContractList.add(companyContract);
                    }
                    else
                        logger.error("company ratio is null or 0 id:"+companyId);
                    //bFind=true;
                    break;
                }
            }
            //if(bFind==false)
            //{
                //默认
            //}
        }

        if(matchCompanyContractList.size()==0)
        {
            logger.error("no company contract- orderId:"+companyAllocation.getOrderId());

            CompanyAllocation errorCompanyAllocation =companyAllocation.clone();
            errorCompanyAllocation.setErrorCode(OrderAssignErrorCode.COMPANYCONTRACT_NOT_FOUND);
            errorCompanyAllocation.setErrorDesc(OrderAssignErrorCode.COMPANYCONTRACT_NOT_FOUND_DSC);
            return errorCompanyAllocation;
        }
        else
        {
            List<CompanyContract> matchCompanyContractList1 =new ArrayList<CompanyContract>();
            //去掉配送能力饱和的承运商
            for(CompanyContract companyContract : matchCompanyContractList)
            {
                CompanyAssignQuantity companyAssignQuantity=this.getCompanyQuantityHelper(companyContract.getId().longValue(),mapCompanyQuantity);
                Long shipmentQuantity=companyAssignQuantity.getQuantity();
                Integer quantity=shipmentQuantity.intValue();
                if(companyContract.getCarryCapacity()!=null&&quantity.compareTo(companyContract.getCarryCapacity())<0)
                {
                    logger.info("company id:"+companyContract.getId()+" - quantity:"+quantity);
                    //去掉总金额超量的承运商
                    if(companyContract.getAmountCapacity()==null)
                    {
                        logger.info("company id:"+companyContract.getId()+" - AmountCapacity is null");
                        matchCompanyContractList1.add(companyContract);
                    }else{
                        BigDecimal amount=companyAssignQuantity.getTotalPrice();
                        if(amount!=null)
                        {
                            if(amount.add(companyAllocation.getOrderhist().getTotalprice()).compareTo(companyContract.getAmountCapacity())<=0)
                            {
                                //还没有超过总金额最大值
                                matchCompanyContractList1.add(companyContract);
                            }
                            else
                            {
                               logger.info("company id:"+companyContract.getId()+" totalprice is exceed:"+amount);
                            }
                        }
                        else
                        {
                            logger.error("company id:"+companyContract.getId()+"current amount is null");
                        }
                    }
                }
                else
                {
                    if(companyContract.getCarryCapacity()==null)
                        logger.error("company exceed - id:"+companyContract.getId()+ " CarryCapacity is null");
                    else
                        logger.error("company exceed - id:"+companyContract.getId()+ " CarryCapacity is "+companyContract.getCarryCapacity());
                    logger.error("company exceed - id:"+companyContract.getId()+ " >  current quantity:" + quantity);
                }
            }

            if(matchCompanyContractList1.size()==0)
            {
                logger.error("company contract exceed- orderId:"+companyAllocation.getOrderId()+"-companyId:"+matchCompanyContractList.get(0).getId());

                CompanyAllocation errorCompanyAllocation =companyAllocation.clone();
                errorCompanyAllocation.setErrorCode(OrderAssignErrorCode.COMPANYCONTRACT_EXCEED_CARRYCAPACITY);
                errorCompanyAllocation.setErrorDesc(OrderAssignErrorCode.COMPANYCONTRACT_EXCEED_CARRYCAPACITY_DSC+"-companyId:"+matchCompanyContractList.get(0).getId());
                return errorCompanyAllocation;
            }

            List<CompanyContract> otherCompanyContractList =new ArrayList<CompanyContract>();
            for(int i=1;i<matchCompanyContractList1.size();i++)
            {
                otherCompanyContractList.add(matchCompanyContractList1.get(i));
            }
            CompanyContract matchCompanyContract = assignFromMatchingRatio(matchCompanyContractList1.get(0),otherCompanyContractList,mapCompanyQuantity);
            CompanyAllocation matchCompanyAllocation = companyAllocation.clone();
            matchCompanyAllocation.setMatchCompanyId(matchCompanyContract.getId());

            logger.debug("match company order id:"+companyAllocation.getOrderId()+"- company id:"+matchCompanyContract.getId());
            return matchCompanyAllocation;
        }
    }

    private CompanyContract assignFromMatchingRatio(CompanyContract companyContract, List<CompanyContract> companyContractList,Map<Long,CompanyAssignQuantity> mapCompanyQuanity)
    {
        //递归进行
        if(companyContractList==null||companyContractList.size() ==0 )
        {
            return companyContract;
        }

        Integer matchingRatioRight=0;
        Integer assignCountRight=0;
        for(CompanyContract companyContract1 : companyContractList)
        {
            matchingRatioRight+=(companyContract1.getMatchingRatio()!=null?companyContract1.getMatchingRatio():0);
            assignCountRight+=this.getCompanyQuantityHelper(companyContract1.getId().longValue(),mapCompanyQuanity).getQuantity().intValue();
        }
        Integer assignCountLeft=0;
        assignCountLeft+=this.getCompanyQuantityHelper(companyContract.getId().longValue(),mapCompanyQuanity).getQuantity().intValue();

        if(assignFromMatchingRatioCompare(assignCountLeft,companyContract.getMatchingRatio(),assignCountRight,matchingRatioRight)==0)
        {
            return companyContract;
        }
        else
        {
            List<CompanyContract> companyContractList1=new ArrayList<CompanyContract>();
            for(int i=1;i<companyContractList.size();i++)
            {
                companyContractList1.add(companyContractList.get(i));
            }
            return this.assignFromMatchingRatio(companyContractList.get(0),companyContractList1,mapCompanyQuanity);
        }
    }

    private Integer assignFromMatchingRatioCompare(Integer assignCountLeft, Integer matchingRatioLeft,Integer assignCountRight, Integer matchingRatioRight)
    {
        if(matchingRatioLeft==null) matchingRatioLeft=0;
        if(matchingRatioRight==null) matchingRatioRight=0;
        if(assignCountLeft.equals(0)&&assignCountRight.equals(0))
        {
            //按照配比分
            if(matchingRatioLeft.compareTo(matchingRatioRight)>=0)
                return 0;
            else
                return 1;
        }
        if(assignCountLeft*matchingRatioRight>assignCountRight*matchingRatioLeft)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }

    private List<CompanyContract> getCompanyContracts(List<CompanyAllocation> companyAllocationList)
    {
        List<Integer> companyIdList = new ArrayList<Integer>();
        for(CompanyAllocation companyAllocation : companyAllocationList)
        {
            if(companyAllocation.getCompanyIdList()!=null)
            {
                for(Integer companyId : companyAllocation.getCompanyIdList())
                {
                    if(!companyIdList.contains(companyId))
                    {
                        companyIdList.add(companyId);
                    }
                }
            }
        }
        if(companyIdList.size()>0)
        {
            return companyContractDao.findCompanyContracts(companyIdList);
        }
        else
        {
            return new ArrayList<CompanyContract>();
        }
    }
}
