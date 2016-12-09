/*
 * @(#)CompanyPaymentDaoImpl.java 1.0 2013-4-8下午2:35:54
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.oms.dao.hibernate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.oms.dao.CompanyPaymentDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.core.dao.query.ParameterTimestamp;
import com.chinadrtv.erp.model.CompanyPayment;
import com.chinadrtv.erp.oms.common.DataGridModel;
import com.chinadrtv.erp.oms.dto.CompanyPaymentDto;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013-4-8 下午2:35:54 
 * 
 */
@Repository
public class CompanyPaymentDaoImpl extends GenericDaoHibernate<CompanyPayment, Long> implements CompanyPaymentDao {

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	*/ 
	public CompanyPaymentDaoImpl() {
		super(CompanyPayment.class);
	}

	/* 
	* <p>Title: queryPageCount</p>
	* <p>Description: </p>
	* @param cpDto
	* @return
	* @see com.chinadrtv.erp.oms.dao.CompanyPaymentDao#queryPageCount(com.chinadrtv.erp.oms.dto.CompanyPaymentDto)
	*/ 
	@SuppressWarnings("rawtypes")
	public int queryPageCount(CompanyPaymentDto cpDto) {
		StringBuffer sb = new StringBuffer();
		Map<String, Parameter> params = new HashMap<String, Parameter>();
		sb.append("select count(cp) from CompanyPayment cp where 1=1 ");
		if(null != cpDto.getPaymentCode() && !"".equals(cpDto.getPaymentCode())){
			sb.append(" and cp.paymentCode=:paymentCode ");
			params.put("paymentCode", new ParameterString("paymentCode", cpDto.getPaymentCode()));
		}
		if(null != cpDto.getBeginPaymentDate()){
			sb.append(" and cp.paymentDate >= :beginPaymentDate ");
			params.put("beginPaymentDate", new ParameterTimestamp("beginPaymentDate", cpDto.getBeginPaymentDate()));
		}
		if(null != cpDto.getEndPaymentDate()){
			sb.append(" and cp.paymentDate <= :endPaymentDate ");
			params.put("endPaymentDate", new ParameterTimestamp("endPaymentDate", cpDto.getEndPaymentDate()));
		}
		if(null != cpDto.getCompanyContract() && null != cpDto.getCompanyContract().getId()){
			sb.append(" and cp.companyContract.id=:companyContractId ");
			params.put("companyContractId", new ParameterInteger("companyContractId", cpDto.getCompanyContract().getId()));
		}
		if(null != cpDto.getIsSettled() && !"".equals(cpDto.getIsSettled())){
			sb.append(" and cp.isSettled=:isSettled");
			params.put("isSettled", new ParameterString("isSettled", cpDto.getIsSettled()));
		}
		return this.findPageCount(sb.toString(), params);
	}

	/* 
	* <p>Title: queryPageList</p>
	* <p>Description: </p>
	* @param cpDto
	* @param dataModel
	* @return
	* @see com.chinadrtv.erp.oms.dao.CompanyPaymentDao#queryPageList(com.chinadrtv.erp.oms.dto.CompanyPaymentDto, com.chinadrtv.erp.oms.common.DataGridModel)
	*/ 
	@SuppressWarnings("rawtypes")
	public List<CompanyPayment> queryPageList(CompanyPaymentDto cpDto, DataGridModel dataModel) {
		StringBuffer sb = new StringBuffer();
		Map<String, Parameter> params = new HashMap<String, Parameter>();
		sb.append("select cp from CompanyPayment cp where 1=1 ");
		if(null != cpDto.getPaymentCode() && !"".equals(cpDto.getPaymentCode())){
			sb.append(" and cp.paymentCode=:paymentCode ");
			params.put("paymentCode", new ParameterString("paymentCode", cpDto.getPaymentCode()));
		}
		if(null != cpDto.getBeginPaymentDate()){
			sb.append(" and cp.paymentDate >= :beginPaymentDate ");
			params.put("beginPaymentDate", new ParameterTimestamp("beginPaymentDate", cpDto.getBeginPaymentDate()));
		}
		if(null != cpDto.getEndPaymentDate()){
			sb.append(" and cp.paymentDate <= :endPaymentDate ");
			params.put("endPaymentDate", new ParameterTimestamp("endPaymentDate", cpDto.getEndPaymentDate()));
		}
		if(null != cpDto.getCompanyContract() && null != cpDto.getCompanyContract().getId()){
			sb.append(" and cp.companyContract.id=:companyContractId ");
			params.put("companyContractId", new ParameterInteger("companyContractId", cpDto.getCompanyContract().getId()));
		}
		if(null != cpDto.getIsSettled() && !"".equals(cpDto.getIsSettled())){
			sb.append(" and cp.isSettled=:isSettled");
			params.put("isSettled", new ParameterString("isSettled", cpDto.getIsSettled()));
		}
        sb.append(" order by cp.createDate desc");  //按录入时间排序

		Parameter page = new ParameterInteger("page", dataModel.getStartRow());
		page.setForPageQuery(true);
		params.put("page", page);

		Parameter rows = new ParameterInteger("rows", dataModel.getRows());
		rows.setForPageQuery(true);
		params.put("rows", rows);

		return this.findPageList(sb.toString(), params);
	}

	/* 
	 * 导出查询
	* <p>Title: queryList</p>
	* <p>Description: </p>
	* @param cpDto
	* @return
	* @see com.chinadrtv.erp.oms.dao.CompanyPaymentDao#queryList(com.chinadrtv.erp.oms.dto.CompanyPaymentDto)
	*/ 
	@SuppressWarnings("rawtypes")
	public List<CompanyPayment> queryList(CompanyPaymentDto cpDto) {
		StringBuffer sb = new StringBuffer();
		Map<String, Parameter> params = new HashMap<String, Parameter>();
		sb.append("select cp from CompanyPayment cp where 1=1 ");
		if(null != cpDto.getPaymentCode() && !"".equals(cpDto.getPaymentCode())){
			sb.append(" and cp.paymentCode=:paymentCode ");
			params.put("paymentCode", new ParameterString("paymentCode", cpDto.getPaymentCode()));
		}
		if(null != cpDto.getBeginPaymentDate()){
			sb.append(" and cp.paymentDate >= :beginPaymentDate ");
			params.put("beginPaymentDate", new ParameterTimestamp("beginPaymentDate", cpDto.getBeginPaymentDate()));
		}
		if(null != cpDto.getEndPaymentDate()){
			sb.append(" and cp.paymentDate <= :endPaymentDate ");
			params.put("endPaymentDate", new ParameterTimestamp("endPaymentDate", cpDto.getEndPaymentDate()));
		}
		if(null != cpDto.getCompanyContract() && null != cpDto.getCompanyContract().getId()){
			sb.append(" and cp.companyContract.id=:companyContractId ");
			params.put("companyContractId", new ParameterInteger("companyContractId", cpDto.getCompanyContract().getId()));
		}
		if(null != cpDto.getIsSettled() && !"".equals(cpDto.getIsSettled())){
			sb.append(" and cp.isSettled=:isSettled");
			params.put("isSettled", new ParameterString("isSettled", cpDto.getIsSettled()));
		}
		return this.findList(sb.toString(), params);
	}

	/**
         * 水单数量
         * @param params
         * @return
         */
        public Long getPaymentCount(Map<String, Object> params){
            if(params.size() > 0){
                Session session = getSession();
                Query q = session.createQuery("select count(a.id) from CompanyPayment a "+
                        "where a.id > 0 " +
                        (params.containsKey("companyId") ? "and a.companyContract.id  = :companyId " : "")+
                        (params.containsKey("paymentCode") ? "and a.paymentCode  = :paymentCode " : "")+
                        (params.containsKey("paymentStart") ? "and a.paymentDate >= :paymentStart " : "")+
                        (params.containsKey("paymentEnd") ? "and a.paymentDate <= :paymentEnd " : "") +
                        (params.containsKey("isSettled") ? "and a.isSettled in ('0','1',:isSettled) " : "and a.isSettled in ('0','1') ")
                );

                List<String> parameters = Arrays.asList(q.getNamedParameters());
                for(String parameterName : parameters) {
                    if(params.containsKey(parameterName)){
                        q.setParameter(parameterName, params.get(parameterName));
                    }
                }

                return Long.valueOf(q.list().get(0).toString());
            } else {
                return 0L;
            }
        }
        /**
         * 水单列表(可分页)
         * @param params
         * @param index
         * @param size
         * @return
         */
        public List<CompanyPayment> getPayments(Map<String, Object> params, int index, int size) {
            if(params.size() > 0){
                Session session = getSession();
                Query q = session.createQuery("from CompanyPayment a "+
                        "where a.id > 0 " +
                        (params.containsKey("companyId") ? "and a.companyContract.id  = :companyId " : "")+
                        (params.containsKey("paymentCode") ? "and a.paymentCode  = :paymentCode " : "")+
                        (params.containsKey("paymentStart") ? "and a.paymentDate >= :paymentStart " : "")+
                        (params.containsKey("paymentEnd") ? "and a.paymentDate <= :paymentEnd " : "") +
                        (params.containsKey("isSettled") ? "and a.isSettled in ('0','1',:isSettled) " : "and a.isSettled in ('0','1') ")
                );

                List<String> parameters = Arrays.asList(q.getNamedParameters());
                for(String parameterName : parameters) {
                    if(params.containsKey(parameterName)){
                        q.setParameter(parameterName, params.get(parameterName));
                    }
                }
                q.setFirstResult(index);
                q.setMaxResults(size);

                return q.list();
            }
            else{
                return new ArrayList<CompanyPayment>();
            }
        }

        public List<CompanyPayment> getPayments(Long[] paymentIds)
        {
            Session session = getSession();
            Query q = session.createQuery("from CompanyPayment a where a.id in (:paymentIds)");
            q.setParameterList("paymentIds", paymentIds);
            return q.list();
        }

		/* (none Javadoc)
		* <p>Title: getByPaymentCode</p>
		* <p>Description: </p>
		* @param paymentCode
		* @return Boolean
		* @see com.chinadrtv.erp.oms.dao.CompanyPaymentDao#getByPaymentCode(java.lang.String)
		*/ 
		@SuppressWarnings("unchecked")
		public Boolean checkPaymentCode(CompanyPayment cp) {
			StringBuffer sb  = new StringBuffer(); 
			sb.append(" select count(cp) from CompanyPayment cp where cp.paymentCode='"+cp.getPaymentCode()+"'");
			if(null!=cp.getId()){
				sb.append(" and cp.id!='"+cp.getId()+"'");
			}
			
			List<Object> rsList = this.getSession().createQuery(sb.toString()).list();
			Long count = (Long) rsList.get(0);
			if(count>0){
				return true;
			}
			return false;
		}

}
