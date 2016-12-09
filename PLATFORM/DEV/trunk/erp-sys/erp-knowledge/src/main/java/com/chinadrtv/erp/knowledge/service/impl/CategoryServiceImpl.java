/**
 * 
 */
package com.chinadrtv.erp.knowledge.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.compass.core.util.CollectionUtils;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.knowledge.dao.CategoryDao;
import com.chinadrtv.erp.knowledge.dto.CategoryNode;
import com.chinadrtv.erp.knowledge.enums.NodeState;
import com.chinadrtv.erp.knowledge.model.KnowledgeArticle;
import com.chinadrtv.erp.knowledge.model.KnowledgeCategory;
import com.chinadrtv.erp.knowledge.service.CategoryService;
import com.chinadrtv.erp.knowledge.service.KnowledgeArticleService;

import edu.emory.mathcs.backport.java.util.Collections;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Resource
	private CategoryDao categoryDao;

	@Resource
	private KnowledgeArticleService articleService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinadrtv.erp.knowledge.service.CategoryService#treeCategories()
	 */
	@Override
	public CategoryNode treeCategories(String departType) {
		CategoryNode node = parse(categoryDao.recursion(departType), null, 0);
		sort(node);
		return node;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinadrtv.erp.knowledge.service.CategoryService#treeArticles(java
	 * .lang.Long, java.lang.String)
	 */
	@Override
	public List<CategoryNode> treeArticles(Long categoryId, String departType) {
		List<KnowledgeArticle> articles = articleService.getTrees(categoryId,
				departType);
		return convert(articles);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinadrtv.erp.knowledge.service.CategoryService#getArticleById(java
	 * .lang.Long)
	 */
	@Override
	public CategoryNode getArticleById(Long articleId) {
		return create(articleService.getById(articleId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinadrtv.erp.knowledge.service.CategoryService#addCategory(com.chinadrtv
	 * .erp.model.knowledge.KnowledgeCategory)
	 */
	@Override
	public CategoryNode addCategory(KnowledgeCategory category) {
		KnowledgeCategory parent = categoryDao.get(category.getParentId());
		KnowledgeCategory entity = categoryDao.save(category);
		entity.setSortPath("/" + entity.getId().toString());
		entity.setFullPath(parent.getFullPath() + entity.getSortPath());
		return create(categoryDao.merge(entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinadrtv.erp.knowledge.service.CategoryService#updateCategoryName
	 * (java.lang.Long, java.lang.String)
	 */
	@Override
	public CategoryNode updateCategoryName(Long id, String userId, String name) {
		KnowledgeCategory entity = categoryDao.get(id);
		entity.setName(name);
		entity.setUpdateUser(userId);
		entity.setUpdateDate(new Date());
		return create(categoryDao.merge(entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinadrtv.erp.knowledge.service.CategoryService#getCategoryById(java
	 * .lang.Long)
	 */
	@Override
	public KnowledgeCategory getCategoryById(Long categoryId) {
		return categoryDao.get(categoryId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.chinadrtv.erp.knowledge.service.CategoryService#removeCategory(com
	 * .chinadrtv.erp.model.knowledge.KnowledgeCategory)
	 */
	@Override
	public boolean removeCategory(Long id) {
		if (categoryDao.hasChildren(id) || articleService.isExist(id)) {
			return false;
		}
		categoryDao.remove(id);
		return true;
	}

	/*
	 * 循环解析目录集合
	 */
	private CategoryNode parse(List<KnowledgeCategory> categories,
			CategoryNode prevNode, int index) {

		KnowledgeCategory category = categories.get(index);
		CategoryNode node = create(category);
		index++;

		// 设置父节点
		if (prevNode != null) {
			if (isParent(prevNode, node)) {
				node.setParent(prevNode);
			} else {
				node.setParent(findParent(prevNode, node.getAttributes()
						.getParentId()));
			}
		}

		// 设置子节点集合
		if (index < categories.size()) {
			CategoryNode nextNode = parse(categories, node, index);
			if (isParent(node, nextNode)) {
				node.getChildren().add(nextNode);
			} else {
				CategoryNode parent = findParent(prevNode, nextNode
						.getAttributes().getParentId());
				parent.getChildren().add(nextNode);
			}
		}

		return node;
	}

	/*
	 * 创建目录json对象tree
	 */
	private CategoryNode create(KnowledgeCategory category) {
		CategoryNode node = new CategoryNode();
		node.setId(category.getId());
		node.setText(category.getName());
		node.setState(NodeState.open);
		node.setAttributes(node.new NodeAttributes());

		node.getAttributes().setParentId(category.getParentId());
		node.getAttributes().setDescription(category.getDescription());
		node.getAttributes().setDepartment(category.getDepartment());
		node.getAttributes().setGroupId(category.getGroupId());
		node.getAttributes().setDepartType(category.getDepartType());
		node.getAttributes().setPosition(category.getPosition());
		node.getAttributes().setIsDir(Boolean.TRUE);
		node.getAttributes().setType(category.getArticleType());
		node.getAttributes().setEditable(
				StringUtils.equals(category.getIsEdit(), "Y"));
		node.getAttributes().setAddable(
				StringUtils.equals(category.getIsAdd(), "Y"));

		return node;
	}

	/*
	 * 找父节点
	 */
	private CategoryNode findParent(CategoryNode node, Long parentId) {
		return node.getId().equals(parentId) ? node : findParent(
				node.getParent(), parentId);
	}

	/*
	 * 判断是否是父节点
	 */
	private boolean isParent(CategoryNode parent, CategoryNode child) {
		return parent.getId().equals(child.getAttributes().getParentId());
	}

	/*
	 * 对position属性进行排序 递归所有children对象
	 */
	private void sort(CategoryNode node) {
		if (!CollectionUtils.isEmpty(node.getChildren())) {
			Collections.sort(node.getChildren(),
					new Comparator<CategoryNode>() {
						@Override
						public int compare(CategoryNode o1, CategoryNode o2) {
							return o1.getAttributes().getPosition()
									- o2.getAttributes().getPosition();
						}
					});
			for (CategoryNode child : node.getChildren()) {
				sort(child);
			}
		}
	}

	private List<CategoryNode> convert(List<KnowledgeArticle> articles) {
		List<CategoryNode> nodes = new ArrayList<CategoryNode>();
		for (KnowledgeArticle art : articles) {
			nodes.add(create(art));
		}
		return nodes;
	}

	private CategoryNode create(KnowledgeArticle art) {
		CategoryNode node = new CategoryNode();
		node.setId(art.getId());
		node.setText(StringUtils.equals(art.getType(), "1") ? art
				.getProductName() : art.getTitle());
		node.setState(NodeState.open);
		node.setIconCls(CategoryNode.ICON_FILE);
		node.setAttributes(node.new NodeAttributes());
		node.getAttributes().setType(Integer.valueOf(art.getType()));
		node.getAttributes().setParentId(art.getCategoryId());
		node.getAttributes().setDepartment(art.getDepartment());
		node.getAttributes().setGroupId(art.getGroupId());
		node.getAttributes().setDepartType(art.getDepartType());
		node.getAttributes().setIsDir(Boolean.FALSE);
		node.getAttributes().setAddable(Boolean.FALSE);
		node.getAttributes().setEditable(Boolean.TRUE);
		return node;
	}
}
