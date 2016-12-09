package com.chinadrtv.companymail.common.dal.dao;

import com.chinadrtv.common.dal.BaseDao;
import com.chinadrtv.companymail.common.dal.model.CompanyContract;
import org.apache.ibatis.jdbc.SqlBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;


/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-11-15
 * Time: 下午4:02
 * To change this template use File | Settings | File Templates.
 */
public interface CompanyContractDao extends BaseDao<CompanyContract> {
    CompanyContract findCompanyContractByName(String name);


}
