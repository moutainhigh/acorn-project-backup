package com.chinadrtv.erp.knowledge.service;

import java.util.List;

import com.chinadrtv.erp.knowledge.dto.CategoryNode;
import com.chinadrtv.erp.knowledge.model.KnowledgeCategory;

/**
 * 知识库目录Service
 * 
 * @author dengqianyong
 * 
 */
public interface CategoryService {

	/**
	 * 树形化目录结构
	 * 
	 * @param departType
	 *            (inbound or outbound)
	 * @return
	 */
	CategoryNode treeCategories(String departType);

	/**
	 * 树形化文件结构
	 * 
	 * @param categoryId
	 * @param departType
	 * @return
	 */
	List<CategoryNode> treeArticles(Long categoryId, String departType);

	/**
	 * 通过文件Id得到文件
	 * 
	 * @param articleId
	 * @return
	 */
	CategoryNode getArticleById(Long articleId);

	/**
	 * 增加目录并返回json对象
	 * 
	 * @param category
	 * @return
	 */
	CategoryNode addCategory(KnowledgeCategory category);

	/**
	 * 更新目录并返回json对象
	 * 
	 * @param id
	 * @param userId
	 * @param name
	 * @return
	 */
	CategoryNode updateCategoryName(Long id, String userId, String name);

	/**
	 * 通过目录id找目录对象
	 * 
	 * @param categoryId
	 * @return
	 */
	KnowledgeCategory getCategoryById(Long categoryId);

	/**
	 * 删除目录
	 * 
	 * @param id
	 * @return 是否成功
	 */
	boolean removeCategory(Long id);

}
