package com.chinadrtv.erp.knowledge.dao;

import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.knowledge.model.KnowledgeAttachment;

/**
 * 附件DAO
 * 
 * @author dengqianyong
 * 
 */
public interface AttachmentDao extends GenericDao<KnowledgeAttachment, Long> {

	/**
	 * 
	 * @param id
	 */
	KnowledgeAttachment get(Long id);

	/**
	 * 
	 * @param articleId
	 * @return
	 */
	Integer countByArticleId(Long articleId);

	/**
	 * 
	 * @param articleId
	 * @return
	 */
	List<KnowledgeAttachment> listByArticleId(Long articleId);

}
