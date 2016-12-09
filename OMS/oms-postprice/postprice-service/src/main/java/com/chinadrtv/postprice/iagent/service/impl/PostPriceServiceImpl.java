package com.chinadrtv.postprice.iagent.service.impl;

import com.chinadrtv.model.constant.AccountStatusConstants;
import com.chinadrtv.postprice.dal.iagent.dao.LogisticsFeeRuleDetailDao;
import com.chinadrtv.postprice.dal.iagent.model.LogisticsFeeRuleDetail;
import com.chinadrtv.postprice.dal.iagent.model.PostPriceItem;
import com.chinadrtv.postprice.iagent.service.PostPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-21
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class PostPriceServiceImpl implements PostPriceService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PostPriceServiceImpl.class);

    private static final BigDecimal percentDecimal=new BigDecimal("0.01");

    private final String BY_WEIGHT = "WEIGHT"; //按重量
    private final String BY_PRICE = "PRICE";	//按单，按金额

    private final String REFUSED_INCLUDE_POSTFEE = "1"; //拒收包含邮费
    private final String REFUSED_INCLUDE_POSTFEE_NO = "0"; //拒收不包含邮费

    private final String UP_ROUNDING = "1"; 	//向上取整
    private final String UP_ROUNDING_NO = "1"; 	//不向上取整

    private final static int scale=6;

    @Autowired
    private LogisticsFeeRuleDetailDao logisticsFeeRuleDetailDao;

    private PostPriceItem processByWeigth(PostPriceItem item, LogisticsFeeRuleDetail lfrd) {
        if(item!=null && lfrd!=null){

            //设置默认值
            if(lfrd.getRefusedIncludePostfee()==null){
                lfrd.setRefusedIncludePostfee(REFUSED_INCLUDE_POSTFEE_NO);
            }

            //设置默认值
            if(lfrd.getRoundType()==null){
                lfrd.setRoundType(UP_ROUNDING_NO);
            }

            BigDecimal postFee1 = BigDecimal.ZERO;
            BigDecimal postFee2 = BigDecimal.ZERO;
            BigDecimal postFee3 = BigDecimal.ZERO;

            BigDecimal a = BigDecimal.ZERO; 	//邮费：首重费用+【（包裹重量-首重）/续重单位】*单位续重费用
            BigDecimal b = BigDecimal.ZERO;	//成功服务费：固定值
            BigDecimal c = BigDecimal.ZERO;	//代收手续费：成功费率*代收金额
            BigDecimal d = BigDecimal.ZERO;	//拒收服务费：固定值
//			Double e = new Double(0);	//保价费：固定值或订单金额*保价费率

            //计算邮费
            BigDecimal extWeight = null; //续重

            if(item.getWeight()!=null && lfrd.getWeightInitial()!=null){

                extWeight = item.getWeight().subtract(lfrd.getWeightInitial()); //续重

                if(extWeight.compareTo(BigDecimal.ZERO)<=0 && lfrd.getWeightInitialFee()!=null){
                    a = lfrd.getWeightInitialFee();
                }else if(lfrd.getWeightInitialFee()!=null && lfrd.getWeightIncrease()!=null && lfrd.getWeightIncreaseFee()!=null && lfrd.getWeightIncrease().compareTo(BigDecimal.ZERO)>0){
                    BigDecimal extFree;
                    if(UP_ROUNDING.equals(lfrd.getRoundType())){	//向上取整
                        extFree=extWeight.divide(lfrd.getWeightIncreaseFee(),0, RoundingMode.CEILING);
                        //a = lfrd.getWeightInitialFee() + ( Math.ceil(extWeight/lfrd.getWeightIncrease()) * lfrd.getWeightIncreaseFee() );
                    }else{	//不向上取整
                        //TODO:不向上取整时，是否保留小数
                        extFree=extWeight.divide(lfrd.getWeightIncreaseFee(),scale, RoundingMode.HALF_UP);
                        //a = lfrd.getWeightInitialFee().add(extWeight.divide(lfrd.getWeightIncrease(), 6, RoundingMode.CEILING).multiply(lfrd.getWeightIncreaseFee()));
                    }
                    a=lfrd.getWeightIncrease().add(extFree.multiply(lfrd.getWeightIncreaseFee()));
                }

                if(lfrd.getSucceedFeeAmount()!=null){
                    //计算成功服务费
                    b = lfrd.getSucceedFeeAmount();
                }

                if(lfrd.getRefusedFeeAmount()!=null){
                    //计算拒收服务费
                    d = lfrd.getRefusedFeeAmount();
                }

                if( AccountStatusConstants.ACCOUNT_FINI.equals(item.getAccountStatusId()) ){
                    //成功
                    if(item.getTotlePrice()!=null && lfrd.getSucceedFeeRatio()!=null){
                        //计算代收手续费
                        c = item.getTotlePrice().multiply(lfrd.getSucceedFeeRatio()).multiply(percentDecimal);
                    }
                    postFee1 = a;
                    postFee2 = b;
                    postFee3 = c;
                }else if( AccountStatusConstants.ACCOUNT_REJECTION.equals(item.getAccountStatusId()) ){
                    //拒收
                    if(item.getTotlePrice()!=null && lfrd.getRefusedFeeRatio()!=null){
                        //计算代收手续费
                        c = item.getTotlePrice().multiply(lfrd.getRefusedFeeRatio()).multiply(percentDecimal);
                    }
                    if(REFUSED_INCLUDE_POSTFEE.equals(lfrd.getRefusedIncludePostfee())){
                        postFee1 = a;
                        postFee2 = d;
                        postFee3 = c;
                    }else{
                        postFee1 = BigDecimal.ZERO;
                        postFee2 = d;
                        postFee3 = c;
                    }
                }
            }

            item.setPostFee1(postFee1);
            item.setPostFee2(postFee2);
            item.setPostFee3(postFee3);
        }
        return item;
    }

    private PostPriceItem processByPrice(PostPriceItem item, LogisticsFeeRuleDetail lfrd) {
        if(item!=null && lfrd!=null){

            BigDecimal totlePrice = null;

            if(item.getTotlePrice()!=null){
                totlePrice = item.getTotlePrice();	//订单代收费用
            }

            BigDecimal a = BigDecimal.ZERO; //成功费用
            BigDecimal b = BigDecimal.ZERO;	//成功费率
            BigDecimal c = BigDecimal.ZERO;	//拒收费用
            BigDecimal d = BigDecimal.ZERO;	//拒收费率

            BigDecimal postFee1 = BigDecimal.ZERO;
            BigDecimal postFee2 = BigDecimal.ZERO;
            BigDecimal postFee3 = BigDecimal.ZERO;

            if(lfrd.getSucceedFeeAmount()!=null){
                a = lfrd.getSucceedFeeAmount();
            }

            if(lfrd.getSucceedFeeRatio()!=null){
                b = lfrd.getSucceedFeeRatio();
            }

            if(lfrd.getRefusedFeeAmount()!=null){
                c = lfrd.getRefusedFeeAmount();
            }

            if(lfrd.getRefusedFeeRatio()!=null){
                d = lfrd.getRefusedFeeRatio();
            }

            if(AccountStatusConstants.ACCOUNT_FINI.equals(item.getAccountStatusId()) ){	//成功
                postFee2 = a;
                postFee3 = b.multiply(totlePrice).multiply(percentDecimal);
            }else if( AccountStatusConstants.ACCOUNT_REJECTION.equals(item.getAccountStatusId()) ){	//拒收
                postFee2 = c;
                postFee3 = d.multiply(totlePrice).multiply(percentDecimal);
            }
            item.setPostFee1(postFee1);
            item.setPostFee2(postFee2);
            item.setPostFee3(postFee3);
        }
        return item;
    }

    @Override
    public void calcPostprice(List<PostPriceItem> postPriceItemList) {
        for(PostPriceItem postPriceItem:postPriceItemList)
        {
            logger.debug("begin calc:"+postPriceItem.getOrderId()+"-"+postPriceItem.getOrderRefRevision());
            //获取符合的规则
            if(postPriceItem.getWeight()==null)
                postPriceItem.setWeight(BigDecimal.ZERO);
            LogisticsFeeRuleDetail lfrd = logisticsFeeRuleDetailDao.matchLogisticsFeeRuleDetail(postPriceItem);

            if(lfrd!=null)
            {
                logger.debug("begin calc price:"+postPriceItem.getOrderId());
                if(BY_WEIGHT.equals(lfrd.getRuleType())){
                    postPriceItem = processByWeigth(postPriceItem,lfrd);
                }else if(BY_PRICE.equals(lfrd.getRuleType())){
                    postPriceItem = processByPrice(postPriceItem,lfrd);
                }
                logger.debug("end calc price:"+postPriceItem.getOrderId());
            }
            else
            {
                logger.debug("end calc with no result:"+postPriceItem.getOrderId());

                postPriceItem.setPostFee1(BigDecimal.ZERO);
                postPriceItem.setPostFee2(BigDecimal.ZERO);
                postPriceItem.setPostFee3(BigDecimal.ZERO);
            }
        }
    }
}
