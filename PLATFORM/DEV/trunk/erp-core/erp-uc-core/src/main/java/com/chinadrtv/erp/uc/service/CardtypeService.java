package com.chinadrtv.erp.uc.service;

import com.chinadrtv.erp.model.Cardtype;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Title: CardtypeService
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
public interface CardtypeService {
    /**
     * 查找所有目前支持的卡类型
     *
     * @return
     */
    List<Cardtype> queryUsefulCardtypes();

    /**
     * 查找所有目前支持的卡类型对的银行
     *
     * @return
     */
    List<String> getUsefulBanks();

    /**
     * 根据ID获取卡类型
     * @param cardTypeId
     * @return
     */
    Cardtype getCardType(String cardTypeId);
    
    /**
     * 通过银行类型
     * 
     * @return
     */
    List<Map<String, String>> getBanktypes();
    
    /**
     * 得到所有银行卡类型
     * 
     * @return
     */
    List<Cardtype> getAllBankCards();

    List<Cardtype> getCardtypes(List<String> cardtypeList);

}
