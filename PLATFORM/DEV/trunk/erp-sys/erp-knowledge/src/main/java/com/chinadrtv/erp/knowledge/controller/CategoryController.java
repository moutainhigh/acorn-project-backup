/**
 * 
 */
package com.chinadrtv.erp.knowledge.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.compass.core.util.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.erp.knowledge.dto.CategoryNode;
import com.chinadrtv.erp.knowledge.model.KnowledgeCategory;
import com.chinadrtv.erp.knowledge.service.CategoryService;

/**
 * @author dengqianyong
 * 
 */
@Controller
@RequestMapping("/category")
public class CategoryController extends BaseController {

	@Resource
	private CategoryService categoryService;

	/**
	 * 得到目录结构
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	public CategoryNode[] getCategories(HttpServletRequest request) {
		CategoryNode node = categoryService
				.treeCategories(getGoupType(request)); // TODO 使用缓存
		addHomeLinks(node);
		CategoryNode[] nodes = new CategoryNode[1];
		nodes[0] = node;
		return nodes;
	}

	/**
	 * 得到目录下的知识或者问题
	 * 
	 * @param type
	 *            类型（知识或者问题）
	 * @param id
	 *            目录Id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/articles/{categoryId}", method = RequestMethod.GET)
	public List<CategoryNode> getArticlesByCategory(HttpServletRequest request,
			@PathVariable("categoryId") Long categoryId) {
		return categoryService.treeArticles(categoryId, getGoupType(request));
	}

	/**
	 * 通过Article的id得到节点
	 * 
	 * @param articleId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/article/{articleId}", method = RequestMethod.GET)
	public CategoryNode getArticleById(@PathVariable Long articleId) {
		return categoryService.getArticleById(articleId);
	}

	/**
	 * 增加目录
	 * 
	 * @param category
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public CategoryNode addCategory(HttpServletRequest request,
			@RequestBody KnowledgeCategory category) {
		category.setCreateUser(getUserId(request));
		category.setCreateDate(new Date());
		category.setDepartType(getGoupType(request));
		return categoryService.addCategory(category);
	}

	/**
	 * 更新目录
	 * 
	 * @param id
	 * @param name
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
	public CategoryNode updateCategory(HttpServletRequest request,
			@PathVariable("id") Long id, @RequestBody String name) {
		return categoryService.updateCategoryName(id, getUserId(request), name);
	}

	/**
	 * 删除目录
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.POST)
	public String removeCategory(@PathVariable Long id) {
		return String.valueOf(categoryService.removeCategory(id));
	}

	private void addHomeLinks(CategoryNode root) {
		if (root != null && !CollectionUtils.isEmpty(root.getChildren())) {
			CategoryNode indexNode = root.getChildren().get(0);
			indexNode.getChildren().add(
					staticCreate(Long.valueOf(-1001), "最新发布",
							CategoryNode.ICON_NEW));
			indexNode.getChildren().add(
					staticCreate(Long.valueOf(-1002), "我的收藏",
							CategoryNode.ICON_COLLECT));
			indexNode.getChildren().add(
					staticCreate(Long.valueOf(-1003), "收藏热门",
							CategoryNode.ICON_HOT));
			indexNode.getChildren().add(
					staticCreate(Long.valueOf(-1004), "全文搜索",
							CategoryNode.ICON_SEARCH));
			indexNode.getChildren().add(
					staticCreate(Long.valueOf(-1005), "知识点标签",
							CategoryNode.ICO_LABEL));

		}
	}

	private CategoryNode staticCreate(Long id, String text, String icon) {
		CategoryNode node = new CategoryNode();
		node.setId(id);
		node.setText(text);
		node.setIconCls(icon);
		return node;
	}

}
