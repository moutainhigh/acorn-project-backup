package com.chinadrtv.erp.knowledge.service;

import java.util.List;

import com.chinadrtv.erp.knowledge.model.KnowledgeAttachment;

/**
 * 
 * @author dengqianyong
 * 
 */
public interface AttachmentService {

	/**
	 * 
	 * @param id
	 * @return
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

	/**
	 * 
	 * @param attachment
	 * @return
	 */
	KnowledgeAttachment save(KnowledgeAttachment attachment);

	/**
	 * 
	 * @param attachment
	 */
	void update(KnowledgeAttachment attachment);

	/**
	 * 
	 * @param id
	 */
	void remove(Long id);
}
