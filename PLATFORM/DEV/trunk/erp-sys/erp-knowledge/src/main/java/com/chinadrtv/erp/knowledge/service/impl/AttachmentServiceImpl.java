package com.chinadrtv.erp.knowledge.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinadrtv.erp.knowledge.dao.AttachmentDao;
import com.chinadrtv.erp.knowledge.model.KnowledgeAttachment;
import com.chinadrtv.erp.knowledge.service.AttachmentService;

/**
 * 
 * @author dengqianyong
 * 
 */
@Service
public class AttachmentServiceImpl implements AttachmentService {

	@Resource
	private AttachmentDao attachmentDao;

	@Override
	public KnowledgeAttachment get(Long id) {
		return attachmentDao.get(id);
	}

	@Override
	public Integer countByArticleId(Long articleId) {
		return attachmentDao.countByArticleId(articleId);
	}

	@Override
	public List<KnowledgeAttachment> listByArticleId(Long articleId) {
		return attachmentDao.listByArticleId(articleId);
	}

	@Override
	public KnowledgeAttachment save(KnowledgeAttachment attachment) {
		return attachmentDao.save(attachment);
	}

	@Override
	public void update(KnowledgeAttachment attachment) {
		attachmentDao.saveOrUpdate(attachment);
	}

	@Override
	public void remove(Long id) {
		attachmentDao.remove(id);
	}
}
