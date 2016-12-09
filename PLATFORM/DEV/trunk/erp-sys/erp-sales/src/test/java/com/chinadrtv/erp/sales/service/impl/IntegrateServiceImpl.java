package com.chinadrtv.erp.sales.service.impl;

import com.chinadrtv.erp.sales.service.IntegrateService;
import com.chinadrtv.erp.sales.service.OrderChangeTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 橡果国际IT系统集成部
 * User: liuhaidong
 * Email:liuhaidong@chinadrtv.com
 * Date: 13-9-2
 * Time: 下午5:47
 * To add class comments
 */
@Service
public class IntegrateServiceImpl implements IntegrateService {

    @Autowired
    private OrderChangeTestService orderChangeTestService;

    @Override
    public void getMandatory(Long orderChangeId) {
        orderChangeTestService.mandatoryUpdatePostFee(orderChangeId);
    }

    @Override
    //TODO
    public void requiredSupportsNew(Long orderChangeId) {
       // try {
            orderChangeTestService.requiresNewPostFee(orderChangeId);
            orderChangeTestService.requiredForUpdate(orderChangeId);
       // } catch (Exception e){

       // }



    }

    @Override
    public void requiredTwo(Long orderChangeId) {
        orderChangeTestService.requiredForUpdate(orderChangeId);
        orderChangeTestService.requiredUpdatePostFee(orderChangeId);
    }

    @Override
    public void requiredTwoException(Long orderChangeId) {
        try{
            orderChangeTestService.requiredForUpdate(orderChangeId);
            orderChangeTestService.requiredUpdatePostFeeException(orderChangeId);
        }catch (Exception e) {

        }
    }

    @Override
    public void requiredCatchException(Long orderChangeId) {
        try{
            orderChangeTestService.requiredForUpdate(orderChangeId);
            orderChangeTestService.requiredUpdatePostFeeException(orderChangeId);
        }catch (Exception e) {
            orderChangeTestService.requiredUpdatePostFee(orderChangeId);
        }
    }

    @Override
    public void requiredSupports(Long orderChangeId) {
        orderChangeTestService.requiredForUpdate(orderChangeId);
        orderChangeTestService.supportsUpdatePostFee(orderChangeId);
    }

    @Override
    public void supportsReq(Long orderChangeId) {
        orderChangeTestService.supportsUpdatePostFee(orderChangeId);
        //requiredForUpdate(orderChangeId);
        throw new RuntimeException("error");
    }


    @Override
    public void requiredNested(Long orderChangeId) {
        try {
            orderChangeTestService.requiredForUpdate(orderChangeId);
            orderChangeTestService.nestedInvoiceTitleException(orderChangeId);
        } catch (Exception e){
            orderChangeTestService.nestedPostFee(orderChangeId);
        }
    }

}
