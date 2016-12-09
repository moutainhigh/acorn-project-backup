package com.chinadrtv.erp.report.service;

import org.springframework.data.domain.Page;

import com.chinadrtv.erp.report.entity.CompanyAccount;


/**
 * @author zhangguosheng
 *
 */
public interface CompanyAccountService{
	
	/**
	 * 查找所有
	 * @return
	 */
	public Page<CompanyAccount> findAll();
	
	/**
	 * @return
	 */
	public Long getCompanyAccountCount();
	
	/**
	 * 通过主键查询
	 * @param id
	 * @return
	 */
	public CompanyAccount get(Long id);
	
	/**
	 * 使用sql查询
	 * @return
	 */
	public String testNativeQuery();
	
}
