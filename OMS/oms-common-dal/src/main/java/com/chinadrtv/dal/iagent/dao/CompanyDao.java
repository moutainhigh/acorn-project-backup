package com.chinadrtv.dal.iagent.dao;

import com.chinadrtv.model.iagent.Company;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-11-5
 * Time: 上午10:35
 * To change this template use File | Settings | File Templates.
 */
public interface CompanyDao {
    Company findByCompanyId(String companyId);
}
