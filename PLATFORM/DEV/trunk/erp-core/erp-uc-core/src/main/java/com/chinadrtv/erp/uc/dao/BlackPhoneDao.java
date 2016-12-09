package com.chinadrtv.erp.uc.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.BlackPhone;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Title: BlackPhoneDao
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
public interface BlackPhoneDao extends GenericDao<BlackPhone, Long> {

    BlackPhone findByCustomer(String phoneNum);

    List<BlackPhone> queryAll();

    void saveBlackPhoneToCti(BlackPhone blackPhone);

    void deleteBlackPhoneFromCti(BlackPhone blackPhone);

    List<BlackPhone> queryPageList(String phoneNum, Integer addTimes, Integer status, int startRow, int rows);

    Integer queryCountPageList(String phoneNum, Integer addTimes, Integer status);
}
