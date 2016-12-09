package com.chinadrtv.erp.uc.dao;

import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.Cardtype;

/**
 * Created with IntelliJ IDEA.
 * Title: CardtypeDao
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
public interface CardtypeDao extends GenericDao<Cardtype, String> {
    /**
     * 查找所有目前支持的卡类型
     *
     * @return
     */
    List<Cardtype> queryUsefulCardtypes();
    
    /**
     * 通过类别找卡, 对应cardtype表中的type1
     * 
     * @param category
     * @return
     */
    List<String> getByCategory(String category);
    
    /**
     * 
     * @return
     */
    List<Cardtype> getAllBankCards();

    List<Cardtype> getCardtypes(List<String> cardtypeList);
}
