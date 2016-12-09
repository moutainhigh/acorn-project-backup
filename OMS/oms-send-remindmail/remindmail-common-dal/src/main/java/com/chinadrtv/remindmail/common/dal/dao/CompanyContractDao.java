package com.chinadrtv.remindmail.common.dal.dao;

import com.chinadrtv.common.dal.BaseDao;
import com.chinadrtv.remindmail.common.dal.model.CompanyContract;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-11-15
 * Time: 下午4:02
 * To change this template use File | Settings | File Templates.
 */
public interface CompanyContractDao extends BaseDao<CompanyContract>{
    //获取承运商信息
    List<CompanyContract> findCompanyContract();

}
