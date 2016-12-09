package com.chinadrtv.erp.report.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.chinadrtv.erp.report.core.repository.jpa.BaseRepository;
import com.chinadrtv.erp.report.entity.CompanyAccount;

//@Repository
public interface CompanyAccountDao extends BaseRepository<CompanyAccount, Long>{
	
	/**
	 * 通过主键查找
	 * @param id
	 * @return
	 */
	public CompanyAccount findById(Long id);
	
	/**
	 * 分页查找
	 * @param accountCode
	 * @param pageable
	 * @return
	 */
	public Page<CompanyAccount> findByAccountCode(String accountCode,Pageable pageable);
	
	/**
	 * 使用hql
	 * @return
	 */
	//查询，如果是更新查询则需要添加@Modifying
	@Query("SELECT COUNT(*) FROM CompanyAccount")
	public Long getCompanyAccountCount();
	
}
