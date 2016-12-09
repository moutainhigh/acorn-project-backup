package com.chinadrtv.erp.knowledge.dao;

import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.knowledge.model.KnowledgeCategory;

/**
 * 目录DAO
 * 
 * @author dengqianyong
 * 
 */
public interface CategoryDao extends GenericDao<KnowledgeCategory, Long> {

	/**
	 * 递归查询目录树
	 * 
	 * @param departType
	 * @return
	 */
	List<KnowledgeCategory> recursion(String departType);

	/**
	 * 是否有子目录
	 * 
	 * @param id
	 * @return
	 */
	boolean hasChildren(Long id);

}
