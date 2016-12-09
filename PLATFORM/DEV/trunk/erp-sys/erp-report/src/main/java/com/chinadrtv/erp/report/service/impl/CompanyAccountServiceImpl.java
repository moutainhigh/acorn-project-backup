package com.chinadrtv.erp.report.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.chinadrtv.erp.report.dao.CompanyAccountDao;
import com.chinadrtv.erp.report.entity.CompanyAccount;
import com.chinadrtv.erp.report.service.CompanyAccountService;

//@Service
/**
 * @author zhangguosheng
 *
 */
public class CompanyAccountServiceImpl implements CompanyAccountService{

	@Autowired
	private CompanyAccountDao companyAccountDao;
	
	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.report.service.CompanyAccountService#findAll()
	 */
	@Override
	public Page<CompanyAccount> findAll(){
		PageRequest pageable = new PageRequest(0, 10) ;
		Page<CompanyAccount> page = companyAccountDao.findAll(pageable);
		return page;
	}

	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.report.service.CompanyAccountService#getCompanyAccountCount()
	 */
	@Override
	public Long getCompanyAccountCount() {
		return companyAccountDao.getCompanyAccountCount();
	}
	
	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.report.service.CompanyAccountService#get(java.lang.Long)
	 */
	@Override
	public CompanyAccount get(Long id) {
		return companyAccountDao.get(CompanyAccount.class, id);
	}
	
	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.report.service.CompanyAccountService#testNativeQuery()
	 */
	@Override
	public String testNativeQuery(){
		String code = "";
//		List<Object[]> list = companyAccountDao.nativeQuery("select * from COMPANY_ACCOUNT where id=?", new Object[]{6L});
//		if(list.size()>0 && list.get(0).length>0){
//			code = (String) list.get(0)[1];
//		}
		PageRequest pageable = new PageRequest(0, 10) ;
		Page<CompanyAccount> page = companyAccountDao.nativeQuery(CompanyAccount.class, "select * from COMPANY_ACCOUNT where id!=?", new Object[]{0L}, pageable);
		if(page.getContent().size()>0){
			code = page.getContent().get(0).getAccountCode();
		}
		return code;
	}

}
